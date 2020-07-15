
function pgLibLiveMultiRender(oAtx, oUI) {
    // Check peergine Activex object
    if (!oAtx || typeof(oAtx.Control) == "undefined") {
        alert("pgLibLive: oAtx is invalid.");
        return null;
    }

    // Check callback object.
    if (!oUI || typeof(oUI.OnEvent) != "function") {
        alert("pgLibLive: oUI is invalid.");
        return null;
    }


    ///------------------------------------------------------------------------------
    // API methods.

    this.SetNodeEventHook = function(eventHook) {
        this.m_eventHook = eventHook;
    };

    this.Initialize = function(sUser, sPass, sSvrAddr, sRelayAddr, iP2PTryTime, sInitParam) {

        if (sUser == null || sPass == null || sSvrAddr == null || sRelayAddr == null || sInitParam == null) {
            this._OutString("pgLibLiveMultiRender.Initialize: sUser, sPass, sSvrAddr, sRelayAddr, sInitParam is null");
            return pgErrCode.PG_ERR_BadParam;
        }

        if (sUser == "" || sSvrAddr == "") {
            this._OutString("pgLibLiveMultiRender.Initialize: User or SvrAddr is ''");
            return pgErrCode.PG_ERR_BadParam;
        }

        // Find empty live unit. 
        var iLiveInd = -1;
        for (var i = 0; i < _pgLiveMultiCallback.aLiveList.length; i++) {
            if (!_pgLiveMultiCallback.aLiveList[i]) {
                iLiveInd = i;
                break;
            }
        }
        if (iLiveInd < 0) {
            this._OutString("pgLibLiveMultiRender.Initialize: No empty live unit.");
            return pgErrCode.PG_ERR_NoSpace;
        }

        // Attach to live list.
        _pgLiveMultiCallback.aLiveList[iLiveInd] = this;
        this._iInd = iLiveInd;

        // Version
        this._LIVE_VER = "13";

        // Init status.
        this._sObjSvr = "";
        this._sObjSelf = "";
        this._bStarted = false;
        this._bLogin = false;
        this._sLanScanRes = "";
        this._iLanScanTimeout = 3;
        this._bAloneRenID = false;
        this._bSingleMode = false;
        this._sSingleCapID = "";
        this._bReportPeerInfo = true;
        this._iLoginFailCount = 0;
        this._iLoginDelayMax = 60;
        this._iIDTimerRelogin = -1;

        this._sInitSvrName = "pgConnectSvr";
        this._sInitSvrAddr = sSvrAddr;

        // Store parameters.
        this._sUser = sUser;
        this._sPass = sPass;
        this._sSvrAddr = sSvrAddr;
        this._sRelayAddr = sRelayAddr;
        this._iP2PTryTime = iP2PTryTime;

        this._InitPrivate(sInitParam);

        if (this._bAloneRenID) {
            this._sObjSelf = ("_RND_" + this._sUser);
        } else {
            var sSubffix = new String(parseInt(Math.random() * 1000000000));
            this._sObjSelf = ("_RND_" + this._sUser + '_' + sSubffix);
        }

        var iErr = this._NodeStart(sInitParam);
        if (iErr > pgErrCode.PG_ERR_Normal) {
            this._OutString("pgLibLiveMultiRender.Initialize: Node start failed.");
            return iErr;
        }

        return pgErrCode.PG_ERR_Normal;
    };

    this.Clean = function() {
        this._NodeStop();

        if (this._iInd >= 0) {
            _pgLiveMultiCallback.aLiveList[this._iInd] = null;
            this._iInd = -1;
        }

        this._sObjSvr = "";
        this._sObjSelf = "";
        this._sSvrAddr = "";
        this._sRelayAddr = "";
    };

    this.GetSelfPeer = function() {
        return this._sObjSelf;
    };


    //------------------------------------------------------
    // Scan the captures in the same lan.
    this.LanScanStart = function() {
        if (this._bApiLanScan) {
            return pgErrCode.PG_ERR_Normal;
        }

        var iInd = 0;
        while (true) {
            var sEle = this._oAtx.omlGetEle(this._sListCapture, "", 1, iInd);
            if (sEle == "") {
                break;
            }

            var sCapID = this._oAtx.omlGetName(sEle, "");
            this._CaptureListSet(sCapID, "Addr", "");

            iInd++;
        }

        var sData = "(Timeout){" + this._iLanScanTimeout + "}";
        var iErr = this._oAtx.ObjectRequest(this._sObjSvr, 42,
            sData, "pgLibLiveMultiRender.LanScan");
        if (iErr > pgErrCode.PG_ERR_Normal) {
            return iErr;
        }

        this._bApiLanScan = true;
        return pgErrCode.PG_ERR_Normal;
    };


    //------------------------------------------------------
    // Connect to capture and disconnec from capture.
    this.Connect = function(sCapID) {
        if (!this._bStarted) {
            this._OutString("pgLibLiveMultiRender.Connect: Not initialize");
            return pgErrCode.PG_ERR_BadStatus;
        }

        if (this._bSingleMode) {
            if (this._sSingleCapID != "") {
                return pgErrCode.PG_ERR_Opened;
            }
        }

        if (this._CaptureListExist(sCapID)) {
            return pgErrCode.PG_ERR_Normal;
        }

        this._CaptureListAdd(sCapID);
        var iErr = this._ServiceStart(sCapID);
        if (iErr > pgErrCode.PG_ERR_Normal) {
            this._CaptureListDelete(sCapID);
            return iErr;
        }

        if (this._bSingleMode) {
            this._sSingleCapID = sCapID;
        }

        return pgErrCode.PG_ERR_Normal;
    };

    this.Disconnect = function(sCapID) {
        if (!this._bStarted) {
            this._OutString("pgLibLiveMultiRender.Disconnect: Not initialize");
            return;
        }

        if (this._bSingleMode) {
            if (this._sSingleCapID != sCapID) {
                return;
            }
        }

        this._ServiceStop(sCapID, true);
        if (this._bSingleMode) {
            this._sSingleCapID = "";
        }
    };

    this.Connected = function(sCapID) {
        if (!this._bStarted) {
            this._OutString("pgLibLiveMultiRender.Connected: Not initialize");
            return pgErrCode.PG_ERR_BadStatus;
        }

        if (this._CaptureListGet(sCapID, "Cnnt") != "1") {
            return pgErrCode.PG_ERR_BadStatus;
        }

        var sObjCapture = this._CaptureBuildObject(sCapID);
        var iErr = this._oAtx.ObjectRequest(sObjCapture, 41, "(Check){1}(Value){3}(Option){}", "");
        if (iErr > pgErrCode.PG_ERR_Normal) {
            return pgErrCode.PG_ERR_BadStatus;
        }

        return pgErrCode.PG_ERR_Normal;
    };


    //------------------------------------------------------
    // Send message at capture side or render side
    this.MessageSend = function(sCapID, sData) {
        if (!this._bStarted) {
            this._OutString("pgLibLiveMultiRender.MessageSend: Not initialize");
            return pgErrCode.PG_ERR_BadStatus;
        }

        if (!this._CaptureListExist(sCapID)) {
            return pgErrCode.PG_ERR_BadStatus;
        }

        var sDataMsg = "Msg?" + sData;
        var sObjCapture = this._CaptureBuildObject(sCapID);
        var iErr = this._oAtx.ObjectRequest(sObjCapture, 36, sDataMsg, "pgLibLiveMultiRender.MessageSend");
        if (iErr > pgErrCode.PG_ERR_Normal) {
            if (iErr == pgErrCode.PG_ERR_BadObject) {
                this._CapturePeerCheck(sCapID);
            }
            return iErr;
        }

        return pgErrCode.PG_ERR_Normal;
    };


    //------------------------------------------------------
    // Video handle functions.

    this.VideoModeSize = function(iMode, iWidth, iHeight) {
        if (!this._bStarted) {
            this._OutString("pgLibLiveMultiRender.VideoModeSize: Not initialize");
            return pgErrCode.PG_ERR_BadStatus;
        }

        var iErr = pgErrCode.PG_ERR_System;
        if (this._oAtx.ObjectAdd("_vTemp", "PG_CLASS_Video", "", 0x0)) {
            var sValue = "(Mode){" + iMode + "}(Width){" + iWidth + "}(Height){" + iHeight + "}";
            var sData = "(Item){12}(Value){" + this._oAtx.omlEncode(sValue) + "}";
            iErr = this._oAtx.ObjectRequest("_vTemp", 2, sData, "");
            if (iErr > pgErrCode.PG_ERR_Normal) {
                this._OutString("pgLibLiveMultiRender.VideoModeSize: iErr=" + iErr);
            } else {
                iErr = pgErrCode.PG_ERR_Normal;
            }
            this._oAtx.ObjectDelete("_vTemp");
        }

        return iErr;
    };

    // Start and stop video
    this.VideoStart = function(sCapID, iVideoID, sParam, sViewDiv) {
        if (!this._bStarted) {
            return pgErrCode.PG_ERR_BadStatus;
        }

        var sWndEle = "";
        if (sViewDiv != "") {
            sWndEle = this._oAtx.WndCreate(sViewDiv);
        }
        if (sWndEle == "") {
            return pgErrCode.PG_ERR_BadParam;
        }

        if (this._VideoListExist(sCapID, iVideoID)) {
            return pgErrCode.PG_ERR_Normal;
        }

        this._VideoListAdd(sCapID, iVideoID);

        var iErr = this._VideoInit(sCapID, iVideoID, sParam, sWndEle);
        if (iErr > pgErrCode.PG_ERR_Normal) {
            this._VideoListDelete(sCapID, iVideoID);
            return iErr;
        }

        return pgErrCode.PG_ERR_Normal;
    };

    this.VideoStop = function(sCapID, iVideoID) {
        if (!this._bStarted) {
            return;
        }

        if (this._VideoListExist(sCapID, iVideoID)) {
            this._VideoClean(sCapID, iVideoID);
        }

        this._VideoListDelete(sCapID, iVideoID);
    };

    // Pull one MJPEG frame.
    this.VideoFramePull = function(sCapID, iVideoID) {
        if (!this._bStarted) {
            return pgErrCode.PG_ERR_BadStatus;
        }

        if (!this._VideoListExist(sCapID, iVideoID)) {
            return pgErrCode.PG_ERR_BadStatus;
        }

        var sData = "";
        if (this._bSingleMode) {
            sData = "FrmPull?";
        } else {
            sData = "FrmPull?VideoID=" + iVideoID;
        }

        var sObjCapture = this._CaptureBuildObject(sCapID);
        var iErr = this._oAtx.ObjectRequest(sObjCapture, 36, sData, "pgLibLiveMultiRender.FramePull");
        if (iErr > pgErrCode.PG_ERR_Normal) {
            return iErr;
        }

        return pgErrCode.PG_ERR_Normal;
    };

    this.VideoCamera = function(sCapID, iVideoID, sJpgPath) {
        if (!this._bStarted) {
            return pgErrCode.PG_ERR_BadStatus;
        }

        if (!this._VideoListExist(sCapID, iVideoID)) {
            return pgErrCode.PG_ERR_BadStatus;
        }

        var sPathTemp = sJpgPath;
        if (sPathTemp.lastIndexOf(".jpg") < 0 &&
            sPathTemp.lastIndexOf(".JPG") < 0) {
            sPathTemp += ".jpg";
        }

        var sData = "(Path){" + this._oAtx.omlEncode(sPathTemp) + "}";
        var sObjLive = this._VideoBuildObject(sCapID, iVideoID);
        var iErr = this._oAtx.ObjectRequest(sObjLive, 37, sData, "pgLibLiveMultiRender.VideoCamera");
        if (iErr > pgErrCode.PG_ERR_Normal) {
            this._OutString("pgLibLiveMultiRender.VideoCamera: iErr=" + iErr);
            return iErr;
        }

        return pgErrCode.PG_ERR_Normal;
    };

    this.VideoParam = function(sCapID, iVideoID, sParam) {
        if (!this._bStarted) {
            return pgErrCode.PG_ERR_BadStatus;
        }

        if (!this._VideoListExist(sCapID, iVideoID)) {
            return pgErrCode.PG_ERR_BadStatus;
        }

        this._VideoListSet(sCapID, iVideoID, "Param", sParam);

        var iMaxStream = this._ParseInt(this._oAtx.omlGetContent(sParam, "MaxStream"), 0);

        var sData = "(Item){0}(Value){" + iMaxStream + "}";
        var sObjLive = this._VideoBuildObject(sCapID, iVideoID);
        var iErr = this._oAtx.ObjectRequest(sObjLive, 2, sData, "pgLibLiveMultiRender.RelayNum");
        if (iErr > pgErrCode.PG_ERR_Normal) {
            return iErr;
        }

        return pgErrCode.PG_ERR_Normal;
    };

    this.VideoShowMode = function(iMode) {
        if (!this._bStarted) {
            return pgErrCode.PG_ERR_BadStatus;
        }

        if (!this._oAtx.ObjectAdd("_vTemp", "PG_CLASS_Video", "", 0)) {
            this._OutString("pgLibLiveMultiRender.VideoShowMode: Add object failed.");
            return pgErrCode.PG_ERR_System;
        }

        this._oAtx.ObjectRequest("_vTemp", 2, "(Item){10}(Value){" + iMode + "}", "");
        this._oAtx.ObjectDelete("_vTemp");

        return pgErrCode.PG_ERR_Normal;
    };


    //------------------------------------------------------
    // Audio handle functions.

    this.AudioStart = function(sCapID, iAudioID, sParam) {
        if (!this._bStarted) {
            return pgErrCode.PG_ERR_BadStatus;
        }

        if (this._AudioListExist(sCapID, iAudioID)) {
            return pgErrCode.PG_ERR_Normal;
        }

        this._AudioListAdd(sCapID, iAudioID);
        this._AudioListSet(sCapID, iAudioID, "Param", sParam);

        var iErr = this._AudioInit(sCapID, iAudioID, sParam);
        if (iErr > pgErrCode.PG_ERR_Normal) {
            this._AudioListDelete(sCapID, iAudioID);
            return iErr;
        }

        return pgErrCode.PG_ERR_Normal;
    };

    this.AudioStop = function(sCapID, iAudioID) {
        if (!this._bStarted) {
            return;
        }

        if (this._AudioListExist(sCapID, iAudioID)) {
            this._AudioClean(sCapID, iAudioID);
        }

        this._AudioListDelete(sCapID, iAudioID);
    };

    this.AudioSpeech = function(sCapID, iAudioID, bEnable) {
        if (!this._bStarted) {
            this._OutString("pgLibLiveMultiRender.AudioSpeech: Not initialize");
            return pgErrCode.PG_ERR_BadStatus;
        }

        if (!this._AudioListExist(sCapID, iAudioID)) {
            this._OutString("pgLibLiveMultiRender.AudioSpeech: Not audio start!");
            return pgErrCode.PG_ERR_BadStatus;
        }

        var iEnable = bEnable ? 1 : 0;
        var sObjCapture = this._CaptureBuildObject(sCapID);
        var sData = "(Peer){" + this._oAtx.omlEncode(sObjCapture) + "}(ActSelf){" + iEnable + "}(ActPeer){1}";
        var sObjAudio = this._AudioBuildObject(sCapID, iAudioID);
        var iErr = this._oAtx.ObjectRequest(sObjAudio, 36, sData, "pgLibLiveMultiRender.AudioSpeech");
        if (iErr > pgErrCode.PG_ERR_Normal) {
            this._OutString("pgLibLiveMultiRender.AudioSpeech: Set audio speech, iErr=" + iErr);
            return iErr;
        }

        return pgErrCode.PG_ERR_Normal;
    };

    this.AudioParam = function(sCapID, iAudioID, sParam) {
        if (!this._bStarted) {
            return pgErrCode.PG_ERR_BadStatus;
        }

        if (!this._AudioListExist(sCapID, iAudioID)) {
            return pgErrCode.PG_ERR_BadStatus;
        }

        this._AudioOption(sCapID, iAudioID, sParam);

        return pgErrCode.PG_ERR_Normal;
    };

    this.AudioMute = function(sCapID, iAudioID, bInput, bOutput) {
        if (!this._bStarted) {
            this._OutString("pgLibLiveMultiRender.AudioMute: Not initialize");
            return pgErrCode.PG_ERR_BadStatus;
        }

        if (!this._AudioListExist(sCapID, iAudioID)) {
            this._OutString("pgLibLiveMultiRender.AudioMute: Not audio start!");
            return pgErrCode.PG_ERR_BadStatus;
        }

        var sObjAudio = this._AudioBuildObject(sCapID, iAudioID);

        var iMuteInput = bInput ? 1 : 0;
        var sData = "(Item){12}(Value){" + iMuteInput + "}";
        var iErr = this._oAtx.ObjectRequest(sObjAudio, 2, sData, "pgLibLiveMultiRender.AudioMute");
        if (iErr > pgErrCode.PG_ERR_Normal) {
            this._OutString("pgLibLiveMultiRender.AudioMute: set input mute. iErr=" + iErr);
            return iErr;
        }

        var iMuteOutput = bOutput ? 1 : 0;
        sData = "(Item){13}(Value){" + iMuteOutput + "}";
        iErr = this._oAtx.ObjectRequest(sObjAudio, 2, sData, "pgLibLiveMultiRender.AudioMute");
        if (iErr > pgErrCode.PG_ERR_Normal) {
            this._OutString("pgLibLiveMultiRender.AudioMute: set output mute. iErr=" + iErr);
            return iErr;
        }

        return pgErrCode.PG_ERR_Normal;
    };


    //------------------------------------------------------
    // Record handle functions.

    this.RecordStart = function(sCapID, sAviPath, iVideoID, iAudioID) {
        if (!this._bStarted) {
            this._OutString("pgLibLiveMultiRender.RecordStart: Not initialize");
            return pgErrCode.PG_ERR_BadStatus;
        }

        if (sAviPath.lastIndexOf(".avi") <= 0 &&
            sAviPath.lastIndexOf(".mov") <= 0 &&
            sAviPath.lastIndexOf(".mp4") <= 0) {
            this._OutString("pgLibLiveMultiRender.RecordStart: invalid avi path. sAviPath=" + sAviPath);
            return pgErrCode.PG_ERR_BadParam;
        }

        var sRec = this._RecordListSearch(sCapID);
        if (sRec != "") {
            return pgErrCode.PG_ERR_Normal;
        }

        var iVideoIDTemp = iVideoID;
        if (this._bSingleMode && iVideoID > 0) {
            iVideoIDTemp = 0;
        }

        var iAudioIDTemp = iAudioID;
        if (this._bSingleMode && iAudioID > 0) {
            iAudioIDTemp = 0;
        }

        var sObjLive = this._VideoBuildObject(sCapID, iVideoIDTemp);
        var sObjAudio = this._AudioBuildObject(sCapID, iAudioIDTemp);

        var bRecord = false;

        var sData;
        var iErr;
        if (iVideoIDTemp >= 0) {
            var iHasAudio = (iAudioIDTemp < 0) ? 0 : 1;
            sData = "(Path){" + this._oAtx.omlEncode(sAviPath) +
                "}(HasAudio){" + iHasAudio + "}";
            iErr = this._oAtx.ObjectRequest(sObjLive,
                36, sData, "pgLibLiveMultiRender.RecordStartVideo");
            if (iErr > pgErrCode.PG_ERR_Normal) {
                this._OutString("pgLibLiveMultiRender.RecordStartVideo: iErr=" + iErr);
                return iErr;
            }
            bRecord = true;
        }

        if (iAudioIDTemp >= 0) {
            var iHasVideo = (iVideoIDTemp < 0) ? 0 : 1;
            var sObjCapture = this._CaptureBuildObject(sCapID);
            sData = "(Peer){" + this._oAtx.omlEncode(sObjCapture) + "}(Path){" +
                this._oAtx.omlEncode(sAviPath) + "}(HasVideo){" + iHasVideo + "}";
            iErr = this._oAtx.ObjectRequest(sObjAudio,
                37, sData, "pgLibLiveMultiRender.RecordStartAudio");
            if (iErr > pgErrCode.PG_ERR_Normal) {
                this._oAtx.ObjectRequest(sObjLive, 36, "(Path){}", "pgLibLiveMultiRender.RecordStopVideo");
                this._OutString("pgLibLiveMultiRender.RecordStartAudio: iErr=" + iErr);
                return iErr;
            }
            bRecord = true;
        }

        if (bRecord) {
            this._RecordListAdd(sCapID, iVideoIDTemp, iAudioIDTemp);
        }

        return pgErrCode.PG_ERR_Normal;
    };

    this.RecordStop = function(sCapID) {
        if (!this._bStarted) {
            this._OutString("pgLibLiveMultiRender.RecordStop: Not initialize");
            return;
        }

        var sRec = this._RecordListSearch(sCapID);
        if (sRec == "") {
            return;
        }

        var sVideoID = this._oAtx.omlGetContent(sRec, ".VideoID");
        var sAudioID = this._oAtx.omlGetContent(sRec, ".AudioID");

        var iVideoID = this._ParseInt(sVideoID, -1);
        var iAudioID = this._ParseInt(sAudioID, -1);
        var iErr = 0;
        if (iVideoID >= 0) {
            var sObjLive = this._VideoBuildObject(sCapID, iVideoID);
            iErr = this._oAtx.ObjectRequest(sObjLive,
                36, "(Path){}", "pgLibLiveMultiRender.RecordStopVideo");
            if (iErr > pgErrCode.PG_ERR_Normal) {
                this._OutString("pgLibLiveMultiRender.RecordStopVideo: iErr=" + iErr);
            }
        }

        if (iAudioID >= 0) {
            var sObjCapture = this._CaptureBuildObject(sCapID);
            var sObjAudio = this._AudioBuildObject(sCapID, iAudioID);
            iErr = this._oAtx.ObjectRequest(sObjAudio,
                37, "(Peer){" + this._oAtx.omlEncode(sObjCapture) + "}(Path){}",
                "pgLibLiveMultiRender.RecordStopAudio");
            if (iErr > pgErrCode.PG_ERR_Normal) {
                this._OutString("pgLibLiveMultiRender.RecordStopAudio: iErr=" + iErr);
            }
        }

        this._RecordListDelete(sCapID);
    };


    //------------------------------------------------------
    // Server handle functions.

    this.SvrRequest = function(sData, sParam) {
        if (!this._bStarted) {
            this._OutString("pgLibLiveMultiRender.SvrRequest: Not initialize");
            return pgErrCode.PG_ERR_BadStatus;
        }

        var sDataReq = ("1024:" + sData);
        var sParamReq = ("LIVE_SVR_REQ:" + sParam);
        var iErr = this._oAtx.ObjectRequest(this._sObjSvr, 35, sDataReq, sParamReq);
        if (iErr > pgErrCode.PG_ERR_Normal) {
            this._OutString("pgLibLiveMultiRender.SvrRequest: iErr=" + iErr);
            return iErr;
        }

        return pgErrCode.PG_ERR_Normal;
    };


    //------------------------------------------------------
    // File transfer functions.

    this.FilePutRequest = function(sCapID, sPath, sPeerPath) {
        return this._FileRequest(sCapID, sPath, sPeerPath, 32);
    };

    this.FileGetRequest = function(sCapID, sPath, sPeerPath) {
        return this._FileRequest(sCapID, sPath, sPeerPath, 33);
    };

    this.FileAccept = function(sCapID, sPath) {
        return this._FileReply(pgErrCode.PG_ERR_Normal, sCapID, sPath);
    };

    this.FileReject = function(sCapID, iErrCode) {
        var iErrTemp = (iErrCode > pgErrCode.PG_ERR_Normal) ? iErrCode : pgErrCode.PG_ERR_Reject;
        return this._FileReply(iErrTemp, sCapID, "");
    };

    this.FileCancel = function(sCapID) {
        return this._FileCancel(sCapID);
    };

    this.Version = function() {
        if (!this._bStarted) {
            this._OutString("pgLibLiveMultiRender.Version: Not initialize");
            return "";
        }

        var sVersion = "";
        var sVerTemp = this._oAtx.omlGetContent(this._oAtx.utilCmd("Version", ""), "Version");
        if (sVerTemp.length > 1) {
            sVersion = sVerTemp.substring(1);
        }

        return (sVersion + "." + this._LIVE_VER);
    };


    ///------------------------------------------------------------------------
    // Private member variables.

    // Store ActiveX object and UI callback object.
    this._oAtx = oAtx;
    this._oUI = oUI;
    this._iInd = -1;

    // Store init parameters
    this._sUser = "";
    this._sPass = "";
    this._sSvrAddr = "";
    this._sRelayAddr = "";
    this._iP2PTryTime = 0;

    // Server parameters
    this._sInitSvrName = "pgConnectSvr";
    this._sInitSvrAddr = "";

    // Status members.
    this._sObjSvr = "";
    this._sObjSelf = "";
    this._sLanScanRes = "";
    this._iLanScanTimeout = 3;
    this._bAloneRenID = false;
    this._bSingleMode = false;
    this._sSingleCapID = "";
    this._bStarted = false;
    this._bLogin = false;
    this._bReportPeerInfo = true;
    this._iLoginFailCount = 0;
    this._iLoginDelayMax = 60;
    this._iIDTimerRelogin = -1;

    // API status parameters
    this._bApiLanScan = false;

    this.m_eventHook = null;
    ///---------------------------------------------------------------------------------
    // Private methods.

    //------------------------------------------------------
    // Common functions.

    this._ParseInt = function(sVal, idefVal) {
        try {
            if (sVal != "") {
                return parseInt(sVal);
            }
            return idefVal;
        } catch (e) {
            return idefVal;
        }
    };

    this._OutString = function(sStr) {
        if (this._oUI.OnOutString && typeof(this._oUI.OnOutString) == "function") {
            this._oUI.OnOutString(sStr);
        }
    };


    //------------------------------------------------------
    // Callback functions.

    this._OnEvent = function(sAct, sData, sCapID) {
        try {
            if (this._oUI.OnEvent && typeof(this._oUI.OnEvent) == "function") {
                this._oUI.OnEvent(sAct, sData, sCapID);
            }
        } catch (ex) {
            this._OutString("pgLibLiveMultiRender._OnEvent: ex=" + ex.toString());
        }
    };

    this._OnTimer = function(sExec) {};

    this._OnTimeout = function(sExec) {
        var sAct = this._oAtx.omlGetContent(sExec, "Act");
        var sCapID = "";
        if (sAct == "CapPeerCheck") {
            sCapID = this._oAtx.omlGetContent(sExec, "CapID");
            this._CapturePeerCheckTimeout(sCapID);
        } else if (sAct == "Relogin") {
            this._iIDTimerRelogin = -1;
            this._NodeLogin();
        } else if (sAct == "PeerGetInfo") {
            var sPeer = this._oAtx.omlGetContent(sExec, "Peer");
            this._NodePeerGetInfo(sPeer);
        } else if (sAct == "Disconnect") {
            sCapID = this._oAtx.omlGetContent(sExec, "CapID");
            this._OnEvent("Disconnect", "", sCapID);
        }
    };


    //------------------------------------------------------
    // Node handle functions.

    this._PeerObjectParseRenID = function(sObject) {
        if (sObject.indexOf("_RND_") == 0) {
            return sObject.substring(5);
        }
        return "";
    };

    this._GroupBuildObject = function(sCapID) {
        if (this._bSingleMode) {
            return ("thisGroup_" + sCapID);
        } else {
            return ("Group_" + sCapID);
        }
    };

    this._GroupObjectIs = function(sObject) {
        if (this._bSingleMode) {
            return (sObject.indexOf("thisGroup_") == 0);
        } else {
            return (sObject.indexOf("Group_") == 0);
        }
    };

    this._GroupObjectParseCapID = function(sObject) {
        if (this._bSingleMode) {
            if (sObject.indexOf("thisGroup_") == 0) {
                return sObject.substring(10);
            }
            return "";
        } else {
            if (sObject.indexOf("Group_") == 0) {
                return sObject.substring(6);
            }
            return "";
        }
    };

    this._DataBuildObject = function(sCapID) {
        if (this._bSingleMode) {
            return ("thisData_" + sCapID);
        } else {
            return ("Data_" + sCapID);
        }
    };

    this._DataObjectIs = function(sObject) {
        if (this._bSingleMode) {
            return (sObject.indexOf("thisData_") == 0);
        } else {
            return (sObject.indexOf("Data_") == 0);
        }
    };

    this._DataObjectParseCapID = function(sObject) {
        if (this._bSingleMode) {
            if (sObject.indexOf("thisData_") == 0) {
                return sObject.substring(9);
            }
            return "";
        } else {
            if (sObject.indexOf("Data_") == 0) {
                return sObject.substring(5);
            }
            return "";
        }
    };

    this._NodeStart = function(sInitParam) {

        var iBufSize0 = this._ParseInt(this._oAtx.omlGetContent(sInitParam, "BufSize0"), 0);
        var iBufSize1 = this._ParseInt(this._oAtx.omlGetContent(sInitParam, "BufSize1"), 0);
        var iBufSize2 = this._ParseInt(this._oAtx.omlGetContent(sInitParam, "BufSize2"), 512);
        var iBufSize3 = this._ParseInt(this._oAtx.omlGetContent(sInitParam, "BufSize3"), 0);
        var iDigest = this._ParseInt(this._oAtx.omlGetContent(sInitParam, "Digest"), 1);

        var sNodeCfg = "Type=0;Option=1;SKTBufSize0=" + iBufSize0 +
            ";SKTBufSize1=" + iBufSize1 + ";SKTBufSize2=" + iBufSize2 +
            ";SKTBufSize3=" + iBufSize3 + ";P2PTryTime=" + this._iP2PTryTime;

        // Select server parameters.
        this._sObjSvr = this._sInitSvrName;
        this._sSvrAddr = this._sInitSvrAddr;

        // Config atx node.
        this._oAtx.Control = "Type=1;LogLevel0=1;LogLevel1=1";
        this._oAtx.Node = sNodeCfg;
        this._oAtx.Class = "PG_CLASS_Data:16;PG_CLASS_Video:16;PG_CLASS_Audio:16;PG_CLASS_Live:16;PG_CLASS_File:16";
        this._oAtx.Local = "Addr=0:0:0:127.0.0.1:0:0";
        this._oAtx.Server = "Name=" + this._sObjSvr + ";Addr=" + this._sSvrAddr + ";Digest=" + iDigest;
        if (this._sRelayAddr) {
            this._oAtx.Relay = "(Relay0){(Type){0}(Load){0}(Addr){" + this._sRelayAddr + "}}";
        } else {
            var iInd = this._sSvrAddr.lastIndexOf(':');
            if (iInd > 0) {
                var sSvrIP = this._sSvrAddr.substring(0, iInd);
                this._oAtx.Relay = "(Relay0){(Type){0}(Load){0}(Addr){" + sSvrIP + ":443}}";
            }
        }

        // Set node's callback
        this._oAtx.OnExtRequest = eval("_pgLiveMultiCallback.OnExtRequest" + this._iInd);
        this._oAtx.OnReply = eval("_pgLiveMultiCallback.OnReply" + this._iInd);

        // Start atx node.
        if (!this._oAtx.Start(0)) {
            this._OutString("pgLibLiveMultiRender._NodeStart: Start node failed.");
            return pgErrCode.PG_ERR_System;
        }

        // Login to server.
        var iErr = this._NodeLogin();
        if (iErr > pgErrCode.PG_ERR_Normal) {
            this._OutString("pgLibLiveMultiRender._NodeStart: login failed.");
            this._NodeStop();
            return iErr;
        }

        // Enable LAN scan.
        this._NodeEnableLANScan();

        this._NodeExternal(sInitParam);

        this._bStarted = true;
        return pgErrCode.PG_ERR_Normal;
    };

    this._NodeStop = function() {
        var iInd = 0;
        while (true) {
            var sEle = this._oAtx.omlGetEle(this._sListCapture, "", 1, iInd);
            if (sEle == "") {
                break;
            }

            var sCapID = this._oAtx.omlGetName(sEle, "");
            this._ServiceStop(sCapID, true);
            if (this._CaptureListExist(sCapID)) {
                iInd++;
            }
        }

        this._NodeLogout();

        this._sListCapture = "";
        this._bApiLanScan = false;
        this._bStarted = false;
    };

    this._NodeEnableLANScan = function() {
        // Enable LAN scan.
        var sLabel = this._bSingleMode ? "pgLive" : "pgLiveMulti";
        var sValue = "(Enable){1}(Peer){" + this._oAtx.omlEncode(this._sObjSelf) + "}(Label){" + sLabel + "}";
        var sData = "(Item){1}(Value){" + this._oAtx.omlEncode(sValue) + "}";
        var iErr = this._oAtx.ObjectRequest(this._sObjSvr, 2, sData, "pgLibLiveMultiRender.EnableLanScan");
        if (iErr > pgErrCode.PG_ERR_Normal) {
            this._OutString("pgLibLiveMultiRender._NodeEnableLANScan: Enable lan scan failed. iErr=" + iErr);
        }
    };

    this._NodeExternal = function(sInitParam) {
        var sVal = this._oAtx.omlGetContent(sInitParam, "ReportPeerInfo");
        if (sVal != "") {
            this._bReportPeerInfo = (this._ParseInt(sVal, 0) != 0) ? true : false;
        }
    };

    this._NodeLogin = function() {
        var sVersion = "";
        var sVerTemp = this._oAtx.omlGetContent(this._oAtx.utilCmd("Version", ""), "Version");
        if (sVerTemp.length > 1) {
            sVersion = sVerTemp.substring(1);
        }

        var sParam = "(Ver){" + sVersion + "." + this._LIVE_VER + "}";
        this._OutString("pgLibLiveMultiRender._NodeLogin: Version=" + sParam);

        var sData = "(User){" + this._oAtx.omlEncode(this._sObjSelf) + "}(Pass){" +
            this._oAtx.omlEncode(this._sPass) + "}(Param){" + this._oAtx.omlEncode(sParam) + "}";
        var iErr = this._oAtx.ObjectRequest(this._sObjSvr, 32, sData, "pgLibLiveMultiRender._NodeLogin");
        if (iErr > pgErrCode.PG_ERR_Normal) {
            this._OutString("pgLibLiveMultiRender._NodeLogin: Login failed. iErr=" + iErr);
            return iErr;
        }

        return pgErrCode.PG_ERR_Normal;
    };

    this._NodeLogout = function() {
        this._oAtx.ObjectRequest(this._sObjSvr, 33, "", "pgLibLiveMultiRender._NodeLogout");
        if (this._bLogin) {
            this._OnEvent("Logout", "", "");
        }

        this._bLogin = false;
    };

    this._NodeRelogin = function(uDelay) {
        this._NodeLogout();
        this._NodeTimerRelogin(uDelay);
    };

    this._NodeRedirect = function(sRedirect) {

        this._NodeLogout();

        var sSvrName = this._oAtx.omlGetContent(sRedirect, "SvrName");
        if (sSvrName != "" && sSvrName != this._sObjSvr) {
            this._oAtx.ObjectDelete(this._sObjSvr);
            if (!this._oAtx.ObjectAdd(sSvrName, "PG_CLASS_Peer", "", (0x10000 | 0x2))) {
                this._OutString("pgLibLiveMultiRender._NodeRedirect: Add server object failed");
                return;
            }
            this._sObjSvr = sSvrName;
            this._sSvrAddr = "";
        }

        var sSvrAddr = this._oAtx.omlGetContent(sRedirect, "SvrAddr");
        if (sSvrAddr != "" && sSvrAddr != this._sSvrAddr) {
            var sData = "(Addr){" + sSvrAddr + "}(Proxy){}";
            var iErr = this._oAtx.ObjectRequest(this._sObjSvr, 37, sData, "pgLibLiveMultiRender.Redirect");
            if (iErr > pgErrCode.PG_ERR_Normal) {
                this._OutString("pgLibLiveMultiRender._NodeRedirect: Set server address. iErr=" + iErr);
                return;
            }
            this._sSvrAddr = sSvrAddr;
        }

        this._OutString("pgLibLiveMultiRender._NodeRedirect: sSvrName=" + sSvrName + ", sSvrAddr=" + sSvrAddr);

        this._NodeTimerRelogin(1);
    };

    this._NodeRedirectReset = function(uDelay) {
        if (this._sSvrAddr != this._sInitSvrAddr) {
            var sRedirect = "(SvrName){" + this._sInitSvrName +
                "}(SvrAddr){" + this._sInitSvrAddr + "}";
            this._NodeRedirect(sRedirect);
        } else {
            if (uDelay != 0) {
                this._NodeRelogin(uDelay);
            }
        }
    };

    this._NodeLoginReply = function(uErr, sData) {
        if (uErr != pgErrCode.PG_ERR_Normal) {
            this._OutString("pgLibLiveMultiRender._NodeLoginReply: Login failed. uErr=" + uErr);

            this._OnEvent("Login", ("" + uErr), "");

            if (uErr == pgErrCode.PG_ERR_Network ||
                uErr == pgErrCode.PG_ERR_Timeout ||
                uErr == pgErrCode.PG_ERR_Busy) {
                var iDelay = this._NodeLoginFailDelay();
                this._NodeRedirectReset(iDelay);
            } else {
                this._NodeRelogin(this._iLoginDelayMax * 10);
            }

            return 1;
        }

        var sParam = this._oAtx.omlGetContent(sData, "Param");
        var sRedirect = this._oAtx.omlGetEle(sParam, "Redirect.", 10, 0);
        if (sRedirect != "") {
            this._NodeRedirect(sRedirect);
            return 1;
        }

        this._iLoginFailCount = 0;
        this._bLogin = true;
        this._CapturePeerCheckLogin();

        this._OnEvent("Login", "0", "");
        return 1;
    };

    this._NodePeerGetInfo = function(sPeer) {
        if (!this._bStarted) {
            return;
        }

        var iErr = this._oAtx.ObjectRequest(sPeer, 38, "", "pgLibLiveMultiRender.PeerGetInfo");
        if (iErr > pgErrCode.PG_ERR_Normal) {
            this._OutString("pgLibLiveMultiRender._NodePeerGetInfo: iErr=", iErr);
        }
    };

    this._NodeLoginFailDelay = function() {
        var iDelay = this._iLoginFailCount * 10;
        if (this._iLoginFailCount < this._iLoginDelayMax) {
            this._iLoginFailCount++;
        }
        return ((iDelay > 0) ? iDelay : 1);
    };

    this._NodeTimerRelogin = function(iDelay) {
        if (this._iIDTimerRelogin >= 0) {
            this._TimerStop(this._iIDTimerRelogin);
            this._iIDTimerRelogin = -1;
        }

        this._iIDTimerRelogin = this._TimerStart("(Act){Relogin}", iDelay);
    };

    this._InitPrivate = function(sInitParam) {
        var sValue = this._oAtx.omlGetContent(sInitParam, "AloneRenID");
        if (sValue != "") {
            var iAloneRenID = this._ParseInt(sValue, 0);
            this._bAloneRenID = (iAloneRenID != 0) ? true : false;
        } else {
            this._bAloneRenID = false;
        }

        sValue = this._oAtx.omlGetContent(sInitParam, "SingleMode");
        if (sValue != "") {
            var iSingeMode = this._ParseInt(sValue, 0);
            this._bSingleMode = (iSingeMode != 0) ? true : false;
        } else {
            this._bSingleMode = false;
        }

        sValue = this._oAtx.omlGetContent(sInitParam, "LanScanTimeout");
        if (sValue != "") {
            var iLanScanTimeout = this._ParseInt(sValue, 0);
            this._iLanScanTimeout = (iLanScanTimeout < 0) ? 3 : iLanScanTimeout;
        } else {
            this._iLanScanTimeout = 3;
        }

        sValue = this._oAtx.omlGetContent(sInitParam, "LoginDelayMax");
        if (sValue != "") {
            var iLoginDelayMax = (this._ParseInt(sValue, 0) / 10);
            this._iLoginDelayMax = (iLoginDelayMax <= 0) ? 60 : iLoginDelayMax;
        } else {
            this._iLoginDelayMax = 60;
        }
    };

    this._ServiceStart = function(sCapID) {

        // Add capture peer object.
        var sObjCapture = this._CaptureBuildObject(sCapID);
        var iErr = this._CapturePeerAdd(sCapID, false);
        if (iErr > pgErrCode.PG_ERR_Normal) {
            this._OutString("pgLibLiveMultiRender._ServiceStart: Add '" + sObjCapture + "' failed. iErr=" + iErr);
            return iErr;
        }

        // Add group object.
        var sObjGroup = this._GroupBuildObject(sCapID);
        if (!this._oAtx.ObjectAdd(sObjGroup, "PG_CLASS_Group", sObjCapture, (0x10000 | 0x10 | 0x1 | 0x40))) {
            this._OutString("pgLibLiveMultiRender._ServiceStart: Add '" + sObjGroup + "' failed.");
            this._oAtx.ObjectDelete(sObjCapture);
            return pgErrCode.PG_ERR_System;
        }

        // Add data object use to transfer message.
        var sObjData = this._DataBuildObject(sCapID);
        if (!this._oAtx.ObjectAdd(sObjData, "PG_CLASS_Data", sObjGroup, 0x10000)) {
            this._OutString("pgLibLiveMultiRender._ServiceStart: Add '" + sObjData + "' failed.");
            this._oAtx.ObjectDelete(sObjGroup);
            this._oAtx.ObjectDelete(sObjCapture);
            return pgErrCode.PG_ERR_System;
        }

        // Add file object.
        if (!this._FileListAdd(sCapID)) {
            this._oAtx.ObjectDelete(sObjData);
            this._oAtx.ObjectDelete(sObjGroup);
            this._oAtx.ObjectDelete(sObjCapture);
            return pgErrCode.PG_ERR_System;
        }

        return pgErrCode.PG_ERR_Normal;
    };

    this._ServiceStop = function(sCapID, bSendDiscnnt) {

        var sObjCapture = this._CaptureBuildObject(sCapID);
        if (bSendDiscnnt) {
            this._oAtx.ObjectRequest(sObjCapture, 36, "Discnnt?", "pgLibLiveMultiRender._Disconnect");
        }

        var iVideoID = 0;
        while (iVideoID < 16) {
            if (this._VideoListExist(sCapID, iVideoID)) {
                this._VideoClean(sCapID, iVideoID);
                this._VideoListDelete(sCapID, iVideoID);
            }
            iVideoID++;
        }

        var iAudioID = 0;
        while (iAudioID < 16) {
            if (this._AudioListExist(sCapID, iAudioID)) {
                this._AudioClean(sCapID, iAudioID);
                this._AudioListDelete(sCapID, iAudioID);
            }
            iAudioID++;
        }

        this._FileListDelete(sCapID);

        var sObjGroup = this._GroupBuildObject(sCapID);
        var sDataModify = "(Action){0}(PeerList){(" + this._oAtx.omlEncode(this._sObjSelf) + "){0}}";
        this._oAtx.ObjectRequest(sObjGroup, 32, sDataModify, "");

        var sObjData = this._DataBuildObject(sCapID);
        this._oAtx.ObjectDelete(sObjData);

        this._oAtx.ObjectDelete(sObjGroup);

        if (this._CaptureListSearch(sCapID) != "") {
            this._oAtx.ObjectDelete(sObjCapture);
        }

        if (this._CaptureListGet(sCapID, "Cnnt") == "1") {
            var sParam = "(Act){Disconnect}(CapID){" + this._oAtx.omlEncode(sCapID) + "}";
            this._TimerStart(sParam, 0);
        }

        this._CaptureListDelete(sCapID);
    };


    //------------------------------------------------------
    // Timer handles.

    this._TimerStart = function(sParam, iTimeout) {
        try {
            this._OutString("pgLibLiveMultiRender._TimerStart: sParam=" + sParam);
            var sJS = "_pgLiveMultiCallback.OnTimeout" + this._iInd + "('" + sParam + "')";
            return window.setTimeout(sJS, (iTimeout * 1000));
        } catch (e) {
            return -1;
        }
    };

    this._TimerStop = function(iTimerID) {
        window.clearTimeout(iTimerID);
    };


    //------------------------------------------------------
    // Capture list functions

    this._sListCapture = "";

    this._CaptureBuildObject = function(sCapID) {
        return ("_CAP_" + sCapID);
    };

    this._CaptureObjectIs = function(sObject) {
        return (sObject.indexOf("_CAP_") == 0);
    };

    this._CaptureObjectParseCapID = function(sObject) {
        if (sObject.indexOf("_CAP_") == 0) {
            return sObject.substring(5);
        }
        return "";
    };

    this._CaptureListSearch = function(sCapID) {
        var sPath = "\n*" + sCapID;
        return this._oAtx.omlGetEle(this._sListCapture, sPath, 1, 0);
    };

    this._CaptureListAdd = function(sCapID) {
        var sCapture = this._CaptureListSearch(sCapID);
        if (sCapture == "") {
            this._sListCapture += "(" + this._oAtx.omlEncode(sCapID) + "){(Cnnt){0}(Addr){}(Timer){0}}";
        }
    };

    this._CaptureListDelete = function(sCapID) {
        var sCapture = this._CaptureListSearch(sCapID);
        if (sCapture != "") {
            var sPath = "\n*" + sCapID;
            this._sListCapture = this._oAtx.omlDeleteEle(this._sListCapture, sPath, 1, 0);
        }
    };

    this._CaptureListSet = function(sCapID, sItem, sValue) {
        var sCapture = this._CaptureListSearch(sCapID);
        if (sCapture != "") {
            var sPath = "\n*" + sCapID + "*" + sItem;
            this._sListCapture = this._oAtx.omlSetContent(this._sListCapture, sPath, sValue);
            return true;
        }
        return false;
    };

    this._CaptureListGet = function(sCapID, sItem) {
        var sPath = "\n*" + sCapID + "*" + sItem;
        return this._oAtx.omlGetContent(this._sListCapture, sPath);
    };

    this._CaptureListExist = function(sCapID) {
        var sCapture = this._CaptureListSearch(sCapID);
        return (sCapture != "");
    };

    this._CapturePeerAdd = function(sCapID, bStatic) {
        if (!this._bStarted) {
            return pgErrCode.PG_ERR_BadStatus;
        }

        this._CaptureListSet(sCapID, "Addr", "");

        var sObjCapture = this._CaptureBuildObject(sCapID);
        this._oAtx.ObjectDelete(sObjCapture);

        var iErr = pgErrCode.PG_ERR_System;
        if (!this._bLogin || bStatic) {
            var sEle = this._oAtx.omlGetEle(this._sLanScanRes, sObjCapture, 1, 0);
            if (sEle != "") {
                if (this._oAtx.ObjectAdd(sObjCapture, "PG_CLASS_Peer", "", (0x10000 | 0x4))) {
                    // Set static peer's address.
                    var sAddr = this._oAtx.omlGetContent(sEle, "");
                    var sData = "(Type){0}(Addr){0:0:0:" + sAddr + ":0}(Proxy){}";

                    iErr = this._oAtx.ObjectRequest(sObjCapture, 37, sData, "pgLibLiveMultiRender.SetAddr");
                    if (iErr == pgErrCode.PG_ERR_Normal) {
                        this._OutString("pgLibLiveMultiRender._CapturePeerAdd: Set '" + sObjCapture + "' in static.");
                        this._CaptureListSet(sCapID, "Addr", sAddr);
                    } else {
                        this._OutString("pgLibLiveMultiRender._CapturePeerAdd: Set '" + sObjCapture + "' address failed. sAddr=" + sAddr);
                        this._oAtx.ObjectDelete(sObjCapture);
                    }
                } else {
                    this._OutString("pgLibLiveMultiRender._CapturePeerAdd: Add '" + sObjCapture + "' with static flag failed.");
                }
            }
        }

        if (iErr != pgErrCode.PG_ERR_Normal) {
            if (this._oAtx.ObjectAdd(sObjCapture, "PG_CLASS_Peer", "", 0x10000)) {
                this._OutString("pgLibLiveMultiRender._CapturePeerAdd: Add '" + sObjCapture + "' without static flag.");
                iErr = pgErrCode.PG_ERR_Normal;
            } else {
                this._OutString("pgLibLiveMultiRender._CapturePeerAdd: Add '" + sObjCapture + "' failed.");
            }
        }

        return iErr;
    };

    this._CapturePeerCheck = function(sCapID) {
        var sObjCapture = this._CaptureBuildObject(sCapID);
        var iErr = this._oAtx.ObjectRequest(sObjCapture, 41, "(Check){1}(Value){3}(Option){}", "");
        if (iErr <= pgErrCode.PG_ERR_Normal) {
            this._oAtx.ObjectRequest(sObjCapture, 36, "Active?", "pgLibLiveMultiRender.MessageSend");
            return;
        }
        if (iErr == pgErrCode.PG_ERR_BadObject) {
            if (this._CaptureListExist(sCapID)) {
                this._CapturePeerAdd(sCapID, false);
            }
        } else {
            this._oAtx.ObjectSync(sObjCapture, "", 1);
        }
    };

    this._CapturePeerCheckLogin = function() {
        var iInd = 0;
        while (true) {
            var sEle = this._oAtx.omlGetEle(this._sListCapture, "", 1, iInd);
            if (sEle == "") {
                break;
            }

            var sCapID = this._oAtx.omlGetName(sEle, "");
            if (this._bLogin) {
                this._CaptureListSet(sCapID, "Addr", "");
            }

            this._CapturePeerCheck(sCapID);
            iInd++;
        }
    };

    this._CapturePeerCheckTimeout = function(sCapID) {
        this._CapturePeerCheck(sCapID);
        if (this._CaptureListGet(sCapID, "Timer") == "1") {
            var sParam = "(Act){CapPeerCheck}(CapID){" + this._oAtx.omlEncode(sCapID) + "}";
            this._TimerStart(sParam, 5);
        }
        this._OutString("pgLibLiveMultiRender._CapPeerCheckTimeout: CapID=" + sCapID);
    };

    this._CapturePeerStatic = function(sCapID) {
        if (!this._bStarted) {
            return;
        }

        var sObjCapture = this._CaptureBuildObject(sCapID);
        var sEle = this._oAtx.omlGetEle(this._sLanScanRes, sObjCapture, 1, 0);
        if (sEle != "") {
            var sAddr = this._oAtx.omlGetContent(sEle, "");
            var sAddr1 = this._CaptureListGet(sCapID, "Addr");
            if (sAddr != sAddr1 && this._CaptureListExist(sCapID)) {
                this._CapturePeerAdd(sCapID, true);
            }
            this._OutString("pgLibLiveMultiRender._CapturePeerStatic: sAddr=" + sAddr + ", sAddr1=" + sAddr1);
        }

        this._OutString("pgLibLiveMultiRender._CapturePeerStatic: CapID=" + sCapID);
    };

    this._CaptureReject = function(sCapID) {
        if (this._bSingleMode) {
            if (this._sSingleCapID != sCapID) {
                return;
            }
        }

        this._ServiceStop(sCapID, false);
        if (this._bSingleMode) {
            this._sSingleCapID = "";
        }
    };


    //------------------------------------------------------
    // Video handles

    this._sListVideo = "";

    this._VideoBuildObject = function(sCapID, iVideoID) {
        if (this._bSingleMode) {
            return ("thisLive_" + sCapID);
        } else {
            var sCapVideo = (sCapID + "_" + iVideoID);
            return ("Live_" + sCapVideo);
        }
    };

    this._VideoObjectIs = function(sObject) {
        if (this._bSingleMode) {
            return (sObject.indexOf("thisLive_") == 0);
        } else {
            return (sObject.indexOf("Live_") == 0);
        }
    };

    this._VideoObjectParseCapID = function(sObject) {
        if (this._bSingleMode) {
            if (sObject.indexOf("thisLive_") == 0) {
                return sObject.substring(9);
            }
            return "";
        } else {
            var sCapVideo = "";
            if (sObject.indexOf("Live_") == 0) {
                sCapVideo = sObject.substring(5);
            }
            var iInd = sCapVideo.lastIndexOf("_");
            if (iInd > 0) {
                return sCapVideo.substring(0, iInd);
            }
            return "";
        }
    };

    this._VideoObjectParseVideoID = function(sObject) {
        if (this._bSingleMode) {
            return 0;
        } else {
            var sCapVideo = "";
            if (sObject.indexOf("Live_") == 0) {
                sCapVideo = sObject.substring(5);
            }
            var iInd = sCapVideo.lastIndexOf("_");
            if (iInd > 0) {
                return this._ParseInt(sCapVideo.substring(iInd + 1), -1);
            }
            return -1;
        }
    };

    this._VideoListSearch = function(sCapID, iVideoID) {
        var sCapVideo = (sCapID + "_" + iVideoID);
        return this._oAtx.omlGetEle(this._sListVideo, sCapVideo, 1, 0);
    };

    this._VideoListAdd = function(sCapID, iVideoID) {
        var iVideoIDTemp = iVideoID;
        if (this._bSingleMode) {
            iVideoIDTemp = 0;
        }
        var sVideo = this._VideoListSearch(sCapID, iVideoIDTemp);
        if (sVideo == "") {
            var sCapVideo = (sCapID + "_" + iVideoIDTemp);
            this._sListVideo += "(" + this._oAtx.omlEncode(sCapVideo) + "){(Param){}(Wnd){}}";
        }
    };

    this._VideoListDelete = function(sCapID, iVideoID) {
        var iVideoIDTemp = iVideoID;
        if (this._bSingleMode) {
            iVideoIDTemp = 0;
        }
        var sVideo = this._VideoListSearch(sCapID, iVideoIDTemp);
        if (sVideo != "") {
            var sCapVideo = (sCapID + "_" + iVideoIDTemp);
            this._sListVideo = this._oAtx.omlDeleteEle(this._sListVideo, sCapVideo, 1, 0);
            return true;
        }
        return false;
    };

    this._VideoListSet = function(sCapID, iVideoID, sItem, sValue) {
        var iVideoIDTemp = iVideoID;
        if (this._bSingleMode) {
            iVideoIDTemp = 0;
        }
        var sVideo = this._VideoListSearch(sCapID, iVideoIDTemp);
        if (sVideo != "") {
            var sCapVideo = (sCapID + "_" + iVideoIDTemp);
            var sPath = "\n*" + sCapVideo + "*" + sItem;
            this._sListVideo = this._oAtx.omlSetContent(this._sListVideo, sPath, sValue);
            return true;
        }
        return false;
    };

    this._VideoListGet = function(sCapID, iVideoID, sItem) {
        var iVideoIDTemp = iVideoID;
        if (this._bSingleMode) {
            iVideoIDTemp = 0;
        }
        var sCapVideo = (sCapID + "_" + iVideoIDTemp);
        var sPath = "\n*" + sCapVideo + "*" + sItem;
        return this._oAtx.omlGetContent(this._sListVideo, sPath);
    };

    this._VideoListExist = function(sCapID, iVideoID) {
        var iVideoIDTemp = iVideoID;
        if (this._bSingleMode) {
            iVideoIDTemp = 0;
        }
        var sVideo = this._VideoListSearch(sCapID, iVideoIDTemp);
        return (sVideo != "");
    };

    this._VideoInit = function(sCapID, iVideoID, sParam, sWndEle) {

        this._CapturePeerCheck(sCapID);

        var sObjVideo = this._VideoBuildObject(sCapID, iVideoID);
        var sObjGroup = this._GroupBuildObject(sCapID);

        if (!this._oAtx.ObjectAdd(sObjVideo, "PG_CLASS_Live", sObjGroup, 0x10000)) {
            this._OutString("pgLibLiveMultiRender._VideoInit: Add '" + sObjVideo + "' failed.");
            return pgErrCode.PG_ERR_System;
        }

        var iMaxStream = this._ParseInt(this._oAtx.omlGetContent(sParam, "MaxStream"), 0);

        var sWnd = "(Wnd){" + sWndEle + "}";
        var sData = "(Source){0}(Media){1}(Delay){300}(CacheSize){20}" +
            "(MaxPart){1}(TimerVal){1}(Param){" + this._oAtx.omlEncode(sWnd) + "}";

        var sData1 = "(Item){0}(Value){" + iMaxStream + "}";
        this._oAtx.ObjectRequest(sObjVideo, 2, sData1, "pgLibLiveMultiRender.RelayNum");

        var iErr = this._oAtx.ObjectRequest(sObjVideo, 32, sData, "pgLibLiveMultiRender.VideoStart");
        if (iErr > pgErrCode.PG_ERR_Normal) {
            this._OutString("pgLibLiveMultiRender._VideoInit: Open live failed. iErr=" + iErr);
            this._oAtx.ObjectDelete(sObjVideo);
            return iErr;
        }

        sData = "(Action){1}(Param){0}";
        iErr = this._oAtx.ObjectRequest(sObjVideo, 34, sData, "pgLibLiveMultiRender.VideoPlay");
        if (iErr > pgErrCode.PG_ERR_Normal) {
            this._OutString("pgLibLiveMultiRender._VideoInit: Play live failed. iErr=" + iErr);
            this._oAtx.ObjectDelete(sObjVideo);
            return iErr;
        }

        return pgErrCode.PG_ERR_Normal;
    };

    this._VideoClean = function(sCapID, iVideoID) {
        var sObjVideo = this._VideoBuildObject(sCapID, iVideoID);
        this._oAtx.ObjectRequest(sObjVideo, 33, "", "pgLibLiveMultiRender.VideoStop");
        this._oAtx.ObjectDelete(sObjVideo);
    };


    //------------------------------------------------------
    // Audio handles.

    this._sListAudio = "";

    this._AudioBuildObject = function(sCapID, iAudioID) {
        if (this._bSingleMode) {
            return ("thisAudio_" + sCapID);
        } else {
            var sCapAudio = (sCapID + "_" + iAudioID);
            return ("Audio_" + sCapAudio);
        }
    };

    this._AudioObjectIs = function(sObject) {
        if (this._bSingleMode) {
            return (sObject.indexOf("thisAudio_") == 0);
        } else {
            return (sObject.indexOf("Audio_") == 0);
        }
    };

    this._AudioObjectParseCapID = function(sObject) {
        if (this._bSingleMode) {
            if (sObject.indexOf("thisAudio_") == 0) {
                return sObject.substring(10);
            }
            return "";
        } else {
            var sCapAudio = "";
            if (sObject.indexOf("Audio_") == 0) {
                sCapAudio = sObject.substring(6);
            }
            var iInd = sCapAudio.lastIndexOf("_");
            if (iInd > 0) {
                return sCapAudio.substring(0, iInd);
            }
            return "";
        }
    };

    this._AudioObjectParseAudioID = function(sObject) {
        if (this._bSingleMode) {
            return 0;
        } else {
            var sCapAudio = "";
            if (sObject.indexOf("Audio_") == 0) {
                sCapAudio = sObject.substring(6);
            }
            var iInd = sCapAudio.lastIndexOf("_");
            if (iInd > 0) {
                return this._ParseInt(sCapAudio.substring(iInd + 1), -1);
            }
            return -1;
        }
    };

    this._AudioListSearch = function(sCapID, iAudioID) {
        var sCapAudio = (sCapID + "_" + iAudioID);
        return this._oAtx.omlGetEle(this._sListAudio, sCapAudio, 1, 0);
    };

    this._AudioListAdd = function(sCapID, iAudioID) {
        var iAudioIDTemp = iAudioID;
        if (this._bSingleMode) {
            iAudioIDTemp = 0;
        }
        var sAudio = this._AudioListSearch(sCapID, iAudioIDTemp);
        if (sAudio == "") {
            var sCapAudio = (sCapID + "_" + iAudioIDTemp);
            this._sListAudio += "(" + this._oAtx.omlEncode(sCapAudio) + "){(Forward){0}(Param){}}";
        }
    };

    this._AudioListDelete = function(sCapID, iAudioID) {
        var iAudioIDTemp = iAudioID;
        if (this._bSingleMode) {
            iAudioIDTemp = 0;
        }
        var sAudio = this._AudioListSearch(sCapID, iAudioIDTemp);
        if (sAudio != "") {
            var sCapAudio = (sCapID + "_" + iAudioIDTemp);
            this._sListAudio = this._oAtx.omlDeleteEle(this._sListAudio, sCapAudio, 1, 0);
            return true;
        }
        return false;
    };

    this._AudioListSet = function(sCapID, iAudioID, sItem, sValue) {
        var iAudioIDTemp = iAudioID;
        if (this._bSingleMode) {
            iAudioIDTemp = 0;
        }
        var sAudio = this._AudioListSearch(sCapID, iAudioIDTemp);
        if (sAudio != "") {
            var sCapAudio = (sCapID + "_" + iAudioIDTemp);
            var sPath = "\n*" + sCapAudio + "*" + sItem;
            this._sListAudio = this._oAtx.omlSetContent(this._sListAudio, sPath, sValue);
            return true;
        }
        return false;
    };

    this._AudioListGet = function(sCapID, iAudioID, sItem) {
        var iAudioIDTemp = iAudioID;
        if (this._bSingleMode) {
            iAudioIDTemp = 0;
        }
        var sCapAudio = (sCapID + "_" + iAudioIDTemp);
        var sPath = "\n*" + sCapAudio + "*" + sItem;
        return this._oAtx.omlGetContent(this._sListAudio, sPath);
    };

    this._AudioListExist = function(sCapID, iAudioID) {
        var iAudioIDTemp = iAudioID;
        if (this._bSingleMode) {
            iAudioIDTemp = 0;
        }
        var sAudio = this._AudioListSearch(sCapID, iAudioIDTemp);
        return (sAudio != "");
    };

    this._AudioInit = function(sCapID, iAudioID, sParam) {

        var iAddFlag = (0x10000 | 0x01);
        var iReliable = this._ParseInt(this._oAtx.omlGetContent(sParam, "Reliable"), 0);
        if (iReliable != 0) {
            iAddFlag |= 0x10;
        }

        var iMuteInput = this._ParseInt(this._oAtx.omlGetContent(sParam, "MuteInput"), 0);
        if (iMuteInput != 0) {
            iAddFlag |= 0x80;
        }

        var iMuteOutput = this._ParseInt(this._oAtx.omlGetContent(sParam, "MuteOutput"), 0);
        if (iMuteOutput != 0) {
            iAddFlag |= 0x100;
        }

        var sSpeechSelf = this._oAtx.omlGetContent(sParam, "SpeechSelf");
        if (sSpeechSelf != "") {
            var iSpeechSelf = this._ParseInt(sSpeechSelf, 0);
            if (iSpeechSelf == 0) {
                iAddFlag |= 0x20;
            }
        }

        var sSpeechPeer = this._oAtx.omlGetContent(sParam, "SpeechPeer");
        if (sSpeechPeer != "") {
            var iSpeechPeer = this._ParseInt(sSpeechPeer, 0);
            if (iSpeechPeer == 0) {
                iAddFlag |= 0x40;
            }
        }

        var sObjAudio = this._AudioBuildObject(sCapID, iAudioID);
        var sObjGroup = this._GroupBuildObject(sCapID);

        if (!this._oAtx.ObjectAdd(sObjAudio, "PG_CLASS_Audio", sObjGroup, iAddFlag)) {
            this._OutString("pgLibLiveMultiRender._AudioInit: Add '" + sObjAudio + "' failed.");
            return pgErrCode.PG_ERR_Normal;
        }

        this._AudioOption(sCapID, iAudioID, sParam);

        var iErr = this._oAtx.ObjectRequest(sObjAudio, 32, "(Code){1}(Mode){0}", "pgLibLiveMultiRender.AudioStart");
        if (iErr > pgErrCode.PG_ERR_Normal) {
            this._OutString("pgLibLiveMultiRender._AudioInit: Open audio failed. iErr=" + iErr);
            this._oAtx.ObjectDelete(sObjAudio);
            return iErr;
        }

        return pgErrCode.PG_ERR_Normal;
    };

    this._AudioClean = function(sCapID, iAudioID) {
        var sObjAudio = this._AudioBuildObject(sCapID, iAudioID);
        this._oAtx.ObjectRequest(sObjAudio, 33, "", "pgLibLiveMultiRender._AudioClean");
        this._oAtx.ObjectDelete(sObjAudio);
    };

    this._AudioOption = function(sCapID, iAudioID, sParam) {

        var sAudioParam = sParam;
        var sData = "";
        var iErr = 0;
        var sValue = "";
        if (sAudioParam == "") {
            sAudioParam = this._AudioListGet(sCapID, iAudioID, "Param");
        }

        if (this._oAtx.ObjectAdd("_aTemp", "PG_CLASS_Audio", "", 0) != 1) {
            return pgErrCode.PG_ERR_System;
        }

        var sMuteGate = this._oAtx.omlGetContent(sAudioParam, "MuteGate");
        var sMuteTail = this._oAtx.omlGetContent(sAudioParam, "MuteTail");
        if (sMuteGate != "" || sMuteTail != "") {
            var iMuteGate = this._ParseInt(sMuteGate, 65536);
            var iMuteTail = this._ParseInt(sMuteTail, 65536);
            sValue = "(VolGate){" + iMuteGate + "}(TailLen){" + iMuteTail + "}";
            sData = "(Item){3}(Value){" + this._oAtx.omlEncode(sValue) + "}";
            iErr = this._oAtx.ObjectRequest("_aTemp", 2, sData, "pgLibLiveMultiRender._AudioOption_Detect");
            if (iErr > pgErrCode.PG_ERR_Normal) {
                this._OutString("pgLibLiveMultiRender._AudioOption: Set Audio Detect, iErr=" + iErr);
            }
        }

        var sEchoCancel = this._oAtx.omlGetContent(sAudioParam, "EchoCancel");
        if (sEchoCancel != "") {
            var iEchoCancel = this._ParseInt(sEchoCancel, 0);
            sData = "(Item){6}(Value){" + iEchoCancel + "}";
            iErr = this._oAtx.ObjectRequest("_aTemp", 2, sData, "pgLibLiveMultiRender._AudioOption_EchoCancel");
            if (iErr > pgErrCode.PG_ERR_Normal) {
                this._OutString("pgLibLiveMultiRender._AudioOption: Set Audio EchoCancel, iErr=" + iErr);
            }
        }

        var sAecConfig = this._oAtx.omlGetContent(sAudioParam, "AecConfig");
        if (sAecConfig != "") {
            var sTempArray = sAecConfig.split(",", 5);

            var iInd = 0;
            var iAecParam = [-1, -1, -1, -1, -1];
            while (iInd < sTempArray.length && iInd < iAecParam.length) {
                iAecParam[iInd] = this._ParseInt(sTempArray[iInd], -1);
                iInd++;
            }

            sValue = "(Mobile){" + iAecParam[0] + "}(RouteMode){" + iAecParam[1] +
                "}(EchoSupLevel){" + iAecParam[2] + "}(NoiseSupLevel){" + iAecParam[3] +
                "}(VoiceDetLevel){" + iAecParam[4] + "}";
            sData = "(Item){14}(Value){" + this._oAtx.omlEncode(sValue) + "}";
            iErr = this._oAtx.ObjectRequest("_aTemp", 2, sData, "pgLibLiveMultiRender._AudioOption_AecConfig");
            if (iErr > pgErrCode.PG_ERR_Normal) {
                this._OutString("pgLibLiveMultiRender._AudioOption: Set Audio AecConfig, iErr=" + iErr);
            }
        }

        this._oAtx.ObjectDelete("_aTemp");
        return pgErrCode.PG_ERR_Normal;
    };


    //------------------------------------------------------
    // Record list handles

    this._sListRecord = "";

    this._RecordListSearch = function(sCapID) {
        return this._oAtx.omlGetEle(this._sListRecord, ("\n*" + sCapID), 1, 0);
    };

    this._RecordListAdd = function(sCapID, iVideoID, iAudioID) {
        var sRec = this._RecordListSearch(sCapID);
        if (sRec == "") {
            this._sListRecord += "(" + this._oAtx.omlEncode(sCapID) +
                "){(VideoID){" + iVideoID + "}(AudioID){" + iAudioID + "}}";
        }
    };

    this._RecordListDelete = function(sCapID) {
        var sRec = this._RecordListSearch(sCapID);
        if (sRec != "") {
            this._sListRecord = this._oAtx.omlDeleteEle(this._sListRecord, ("\n*" + sCapID), 1, 0);
            return true;
        }
        return false;
    };


    //------------------------------------------------------
    // File handles.

    this._sListFile = "";

    this._FileBuildObject = function(sCapID) {
        if (this._bSingleMode) {
            return ("File_" + this._sObjSelf);
        } else {
            var sRenID = this._PeerObjectParseRenID(this._sObjSelf);
            var sObjFile = ("File_" + sCapID + "\n" + sRenID);
            if (sObjFile.length > 127) {
                this._OutString("pgLibLiveMultiRender._FileBuildObject: '" + sObjFile + "' to long !");
            }
            return sObjFile;
        }
    };

    this._FileObjectIs = function(sObject) {
        return (sObject.indexOf("File_") == 0);
    };

    this._FileObjectParseCapID = function(sObject) {
        if (this._bSingleMode) {
            return this._sSingleCapID;
        } else {
            var sCapRender = "";
            if (sObject.indexOf("File_") == 0) {
                sCapRender = sObject.substring(5);
            }
            var iInd = sCapRender.indexOf("\n");
            if (iInd > 0) {
                return sCapRender.substring(0, iInd);
            }
            return "";
        }
    };

    this._FileListSearch = function(sCapID) {
        return this._oAtx.omlGetEle(this._sListFile, ("\n*" + sCapID), 1, 0);
    };

    this._FileListAdd = function(sCapID) {
        var sFile = this._FileListSearch(sCapID);
        if (sFile == "") {
            this._sListFile += "(" + this._oAtx.omlEncode(sCapID) + "){(Status){0}(Handle){0}}";
        }

        var sObjFile = this._FileBuildObject(sCapID);
        if (this._oAtx.ObjectGetClass(sObjFile) != "PG_CLASS_File") {
            var sObjCapture = this._CaptureBuildObject(sCapID);
            if (!this._oAtx.ObjectAdd(sObjFile, "PG_CLASS_File", sObjCapture, 0x10000)) {
                this._OutString("pgLibLiveMultiRender._FileListAdd: Add '" + sObjFile + "' failed!");
                return false;
            }
        }

        return true;
    };

    this._FileListDelete = function(sCapID) {
        var sObjFile = this._FileBuildObject(sCapID);

        this._oAtx.ObjectRequest(sObjFile, 35, "", "");
        this._oAtx.ObjectDelete(sObjFile);

        var sFile = this._FileListSearch(sCapID);
        if (sFile != "") {
            this._sListFile = this._oAtx.omlDeleteEle(this._sListFile, ("\n*" + sCapID), 1, 0);
            return true;
        }

        return false;
    };

    this._FileListSet = function(sCapID, sItem, sValue) {
        var sFile = this._FileListSearch(sCapID);
        if (sFile != "") {
            var sPath = "\n*" + sCapID + "*" + sItem;
            this._sListFile = this._oAtx.omlSetContent(this._sListFile, sPath, sValue);
            return true;
        }
        return false;
    };

    this._FileListGet = function(sCapID, sItem) {
        var sPath = "\n*" + sCapID + "*" + sItem;
        return this._oAtx.omlGetContent(this._sListFile, sPath);
    };

    this._FileRequest = function(sCapID, sPath, sPeerPath, iMethod) {
        if (this._FileListGet(sCapID, "Status") == "1") {
            return pgErrCode.PG_ERR_Opened;
        }

        var sData = "(Path){" + this._oAtx.omlEncode(sPath) + "}(PeerPath){" +
            this._oAtx.omlEncode(sPeerPath) + "}(TimerVal){1}(Offset){0}(Size){0}";

        var sParam = (iMethod == 32) ? "pgLibLiveMultiRender.FilePutRequest" : "pgLibLiveMultiRender.FileGetRequest";

        var sObjFile = this._FileBuildObject(sCapID);
        var iErr = this._oAtx.ObjectRequest(sObjFile, iMethod, sData, sParam);
        if (iErr > pgErrCode.PG_ERR_Normal) {
            this._OutString("pgLibLiveMultiRender._FileRequest: iMethod=" + iMethod + ", iErr=" + iErr);
            return iErr;
        }

        this._FileListSet(sCapID, "Status", "1");
        return pgErrCode.PG_ERR_Normal;
    };

    this._FileReply = function(iErrReply, sCapID, sPath) {

        var sData = "";
        if (iErrReply != pgErrCode.PG_ERR_Normal) {
            this._FileListSet(sCapID, "Status", "0");
        } else {
            this._FileListSet(sCapID, "Status", "1");
            sData = "(Path){" + this._oAtx.omlEncode(sPath) + "}(TimerVal){1}";
        }

        this._OutString("pgLibLiveMultiRender._FileReply: iErrReply=" + iErrReply + ", sCapID=" + sCapID + ", sData=" + sData);

        var sHandle = this._FileListGet(sCapID, "Handle");
        this._OutString("pgLibLiveMultiRender._FileReply: sHandle=" + sHandle);

        var iHandle = this._ParseInt(sHandle, 0);
        if (iHandle == 0) {
            this._FileListSet(sCapID, "Status", "0");
            return pgErrCode.PG_ERR_BadStatus;
        }


        var sObjFile = this._FileBuildObject(sCapID);
        var iErr = this._oAtx.ObjectExtReply(sObjFile, iErrReply, sData, iHandle);
        if (iErr <= pgErrCode.PG_ERR_Normal) {
            this._FileListSet(sCapID, "Handle", "0");
        }

        this._OutString("pgLibLiveMultiRender._FileReply: iErr=" + iErr);
        return iErr;
    };

    this._FileCancel = function(sCapID) {

        var sObjFile = this._FileBuildObject(sCapID);
        var iErr = this._oAtx.ObjectRequest(sObjFile, 35, "", "pgLibLiveMultiRender.FileCancel");
        if (iErr <= pgErrCode.PG_ERR_Normal) {
            this._FileListSet(sCapID, "Status", "0");
        }

        return iErr;
    };

    this._AddrToReadable = function(sAddr) {
        var iIP0 = 0;
        var iIP1 = 0;
        var iIP2 = 0;
        var iIP3 = 0;
        try {
            var sAddrSect = sAddr.split(":", 6);
            if (sAddrSect.length < 6) {
                return sAddr;
            }

            var sReadable = "";
            if (sAddrSect[0] == "0" &&
                sAddrSect[1] == "0" &&
                sAddrSect[2] == "0" &&
                sAddrSect[3] != "0" &&
                sAddrSect[3] != "1") {
                var iIP = parseInt(sAddrSect[3], 16);
                iIP0 = (iIP >> 24) & 0xff;
                iIP1 = (iIP >> 16) & 0xff;
                iIP2 = (iIP >> 8) & 0xff;
                iIP3 = (iIP & 0xff);
                sReadable = (iIP0 + "." + iIP1 + "." + iIP2 + "." + iIP3 + ":" + sAddrSect[4]);
            } else {
                iIP0 = parseInt(sAddrSect[0], 16);
                iIP1 = parseInt(sAddrSect[1], 16);
                iIP2 = parseInt(sAddrSect[2], 16);
                iIP3 = parseInt(sAddrSect[3], 16);

                var iWord0 = (iIP0 >> 16) & 0xffff;
                var iWord1 = (iIP0 & 0xffff);

                var iWord2 = (iIP1 >> 16) & 0xffff;
                var iWord3 = (iIP1 & 0xffff);

                var iWord4 = (iIP2 >> 16) & 0xffff;
                var iWord5 = (iIP2 & 0xffff);

                var iWord6 = (iIP3 >> 16) & 0xffff;
                var iWord7 = (iIP3 & 0xffff);

                sReadable = ("[" + iWord0.toString(16) + ":" + iWord1.toString(16) + ":" + iWord2.toString(16) +
                    ":" + iWord3.toString(16) + ":" + iWord4.toString(16) + ":" + iWord5.toString(16) +
                    ":" + iWord6.toString(16) + ":" + iWord7.toString(16) + "]:" + sAddrSect[4]);
            }

            return sReadable;
        } catch (e) {
            return sAddr;
        }
    };


    ///------------------------------------------------------------------------
    // Callback process functions.

    this._OnSelfSync = function(sData, sPeer) {
        var sAct = this._oAtx.omlGetContent(sData, "Action");
        if (sAct === "1") {
            if (sPeer == this._sObjSvr) {
                this._TimerStart("(Act){PeerGetInfo}(Peer){" + this._oAtx.omlEncode(sPeer) + "}", 5);
            }
        } else {
            if (sPeer == this._sObjSvr) {
                this._NodeRelogin(10);
            }
        }
    };

    this._OnSelfMessage = function(sData, sPeer) {
        var sCmd = "";
        var sParam = "";
        var iInd = sData.indexOf('?');
        if (iInd > 0) {
            sCmd = sData.substring(0, iInd);
            sParam = sData.substring(iInd + 1);
        } else {
            sParam = sData;
        }

        var sCapID = this._CaptureObjectParseCapID(sPeer);

        if (sCmd == "Active") {
            if (this._CaptureListExist(sCapID)) {
                if (this._CaptureListGet(sCapID, "Timer") == "1") {
                    this._CaptureListSet(sCapID, "Timer", "0");
                }
                this._oAtx.ObjectRequest(sPeer, 36, "Active?", "pgLibLiveMultiRender.MessageSend");
            }
            return 0;
        }

        if (sCmd == "Msg") {
            this._OnEvent("Message", sParam, sCapID);
            return 0;
        }

        if (sCmd == "Reject") {
            this._CaptureReject(sCapID);
            this._OnEvent("Reject", "", sCapID);
            return 0;
        }

        return 0;
    };

    this._OnServerMessage = function(sData, sPeer) {

        var sCmd = "";
        var sParam = "";
        var iInd = sData.indexOf('?');
        if (iInd > 0) {
            sCmd = sData.substring(0, iInd);
            sParam = sData.substring(iInd + 1);
        } else {
            sParam = sData;
        }

        if (sCmd == "UserExtend") {
            this._OnEvent("SvrNotify", sParam, "");
            return 0;
        }

        if (sCmd == "Restart") {
            if (sParam.indexOf("redirect=1") >= 0) {
                this._NodeRedirectReset(3);
            } else {
                var iDelay = 3;
                var iInd1 = sParam.indexOf("delay=");
                if (iInd1 >= 0) {
                    // Skip the leng of "delay="
                    var sValue = sParam.substring(iInd1 + 6);
                    var iValue = this._ParseInt(sValue, 3);
                    iDelay = (iValue < 3) ? 3 : iValue;
                }
                this._NodeRelogin(iDelay);
            }
            return 0;
        }

        return 0;
    };

    this._OnServerKickOut = function(sData) {
        var sParam = this._oAtx.omlGetContent(sData, "Param");
        this._OnEvent("KickOut", sParam, "");
    };

    this._OnServerError = function(sData, sPeer) {
        var sMeth = this._oAtx.omlGetContent(sData, "Meth");
        if (sMeth == "32") {
            var sError = this._oAtx.omlGetContent(sData, "Error");
            if (sError == ("" + pgErrCode.PG_ERR_NoLogin)) {
                this._NodeRelogin(3);
            } else if (sError == ("" + pgErrCode.PG_ERR_Network) ||
                sError == ("" + pgErrCode.PG_ERR_Timeout) ||
                sError == ("" + pgErrCode.PG_ERR_Busy)) {
                this._NodeRedirectReset(0);
            }
        }
    };

    this._OnServerRelogin = function(sData, sPeer) {
        var sError = this._oAtx.omlGetContent(sData, "ErrCode");
        if (sError == ("" + pgErrCode.PG_ERR_Normal)) {
            var sParam = this._oAtx.omlGetContent(sData, "Param");
            var sRedirect = this._oAtx.omlGetEle(sParam, "Redirect.", 10, 0);
            if (sRedirect != "") {
                this._NodeRedirect(sRedirect);
                return;
            }

            this._iLoginFailCount = 0;
            this._bLogin = true;
            this._OnEvent("Login", "0", "");
        } else if (sError == ("" + pgErrCode.PG_ERR_Network) ||
            sError == ("" + pgErrCode.PG_ERR_Timeout) ||
            sError == ("" + pgErrCode.PG_ERR_Busy)) {
            this._NodeRedirectReset(0);

            this._bLogin = false;
            this._OnEvent("Login", sError, "");
        } else {
            this._bLogin = false;
            this._OnEvent("Login", sError, "");
        }
    };

    this._OnServerSync = function(sData) {
        var sAct = this._oAtx.omlGetContent(sData, "Action");
        if (sAct != "1") {
            this._NodeRelogin(3);
        }
    };

    this._OnPeerSync = function(sObj, sData) {
        sAct = this._oAtx.omlGetContent(sData, "Action");
        if (sAct == "1") {
            if (this._bReportPeerInfo) {
                this._TimerStart("(Act){PeerGetInfo}(Peer){" + this._oAtx.omlEncode(sObj) + "}", 5);
            }
        }
    };

    this._OnGroupUpdate = function(sObject, sData) {
        var sAct = this._oAtx.omlGetContent(sData, "Action");
        var sPeerList = this._oAtx.omlGetEle(sData, "PeerList.", 256, 0);

        this._OutString(sPeerList);

        var sCapID = this._GroupObjectParseCapID(sObject);
        var sObjCapture = this._CaptureBuildObject(sCapID);

        var iInd = 0;
        while (true) {
            var sEle = this._oAtx.omlGetEle(sPeerList, "", 1, iInd);
            if (sEle == "") {
                break;
            }

            var sPeerTemp = this._oAtx.omlGetName(sEle, "");
            if (sPeerTemp == sObjCapture) {
                if (sAct == "1") {
                    if (this._CaptureListGet(sCapID, "Cnnt") == "0") {
                        this._CaptureListSet(sCapID, "Cnnt", "1");
                        this._OnEvent("Connect", "", sCapID);
                    }
                } else {
                    this._OnCaptureDisconnect(sObjCapture);
                }
            } else if (sPeerTemp == this._sObjSelf) {
                if (sAct != "1") {
                    this._OnCaptureDisconnect(sObjCapture);
                }
            }

            iInd++;
        }
    };

    this._OnCapturePeerSync = function(sObject) {
        var sCapID = this._CaptureObjectParseCapID(sObject);
        if (this._CaptureListExist(sCapID)) {
            this._CaptureListSet(sCapID, "Timer", "0");
            this._TimerStart("(Act){PeerGetInfo}(Peer){" + this._oAtx.omlEncode(sObject) + "}", 5);
        }
        this._OutString("pgLibLiveMultiRender._OnCapturePeerSync: sObjCap = " + sObject);
    };

    this._OnCaptureOffline = function(sObject, sError) {
        var sCapID = this._CaptureObjectParseCapID(sObject);
        this._CapturePeerStatic(sCapID);
        this._OnCaptureDisconnect(sObject);
        this._OnEvent("Offline", sError, sCapID);
    };

    this._OnCaptureDisconnect = function(sObject) {
        var sCapID = this._CaptureObjectParseCapID(sObject);
        if (this._CaptureListGet(sCapID, "Timer") == "0") {
            var sParam = "(Act){CapPeerCheck}(CapID){" + this._oAtx.omlEncode(sCapID) + "}";
            if (this._TimerStart(sParam, 3) >= 0) {
                this._CaptureListSet(sCapID, "Timer", "1");
            }
        }
        if (this._CaptureListGet(sCapID, "Cnnt") == "1") {
            this._CaptureListSet(sCapID, "Cnnt", "0");
            this._OnEvent("Disconnect", "", sCapID);
        }
    };

    this._OnVideoStatus = function(sObject, sData) {
        var iVideoID = this._VideoObjectParseVideoID(sObject);
        if (iVideoID < 0) {
            return;
        }

        var sBitRate = this._oAtx.omlGetContent(sData, "BitRate");
        var sFrmRate = this._oAtx.omlGetContent(sData, "FrmRate");
        var sFrmPlay = this._oAtx.omlGetContent(sData, "FrmPlay");

        var sDataTemp = "";
        if (this._bSingleMode) {
            sDataTemp = "bitrate=" + sBitRate + "&frmrate=" + sFrmRate + "&frmplay=" + sFrmPlay;
        } else {
            sDataTemp = "videoid=" + iVideoID + "&bitrate=" + sBitRate + "&frmrate=" + sFrmRate + "&frmplay=" + sFrmPlay;
        }

        var sCapID = this._VideoObjectParseCapID(sObject);
        this._OnEvent("VideoStatus", sDataTemp, sCapID);
    };

    this._OnAudioSync = function(sObject, sData, sPeer) {
        var sAct = this._oAtx.omlGetContent(sData, "Action");
        if (sAct != "1") {
            return;
        }

        if (this._CaptureObjectIs(sPeer)) {
            return;
        }

        var iAudioID = this._AudioObjectParseAudioID(sObject);
        if (iAudioID < 0) {
            return;
        }

        var sCapID = this._AudioObjectParseCapID(sObject);
        var sParam = this._AudioListGet(sCapID, iAudioID, "Param");

        var sRenderSpeech = this._oAtx.omlGetContent(sParam, "RenderSpeech");
        if (sRenderSpeech != "") {
            var iRenderSpeech = this._ParseInt(sRenderSpeech, 0);
            var sData1 = "(Peer){" + this._oAtx.omlEncode(sPeer) + "}(ActSelf){" +
                iRenderSpeech + "}(ActPeer){" + iRenderSpeech + "}";
            var iErr = this._oAtx.ObjectRequest(sObject, 36, sData1, "pgLibLiveMultiRender.RenderSpeech");
            if (iErr > pgErrCode.PG_ERR_Normal) {
                this._OutString("pgLibLiveMultiRender._OnAudioSync: Set audio speech, iErr=" + iErr);
            }
        }
    };

    this._OnFileRequest = function(sObject, iMethod, sData, iHandle) {
        var sCapID = this._FileObjectParseCapID(sObject);

        this._OutString("pgLibLiveMultiRender._OnFileRequest: sData=" + sData + ", iHandle=" + iHandle);

        if (this._FileListGet(sCapID, "Status") == "1") {
            return pgErrCode.PG_ERR_BadStatus;
        }

        this._FileListSet(sCapID, "Handle", (iHandle + ""));
        this._FileListSet(sCapID, "Status", "1");

        var sPeerPath = this._oAtx.omlGetContent(sData, "PeerPath");
        var sParam = "peerpath=" + sPeerPath;

        if (iMethod == 32) {
            this._OnEvent("FilePutRequest", sParam, sCapID);
        } else if (iMethod == 33) {
            this._OnEvent("FileGetRequest", sParam, sCapID);
        }

        return -1; // Async reply
    };

    this._OnFileStatus = function(sObject, sData) {
        var sCapID = this._FileObjectParseCapID(sObject);

        var sStatus = this._oAtx.omlGetContent(sData, "Status");
        var iStatus = this._ParseInt(sStatus, -1);
        var sPath = "";
        var sReqSize = 0;
        var sCurSize = 0;
        var sParam = "";

        if (iStatus != 3) {
            sPath = this._oAtx.omlGetContent(sData, "Path");
            sReqSize = this._oAtx.omlGetContent(sData, "ReqSize");
            sCurSize = this._oAtx.omlGetContent(sData, "CurSize");
            sParam = "path=" + sPath + "&total=" + sReqSize + "&position=" + sCurSize;
            this._OnEvent("FileProgress", sParam, sCapID);
        } else { // Stop
            this._FileListSet(sCapID, "Status", "0");

            sPath = this._oAtx.omlGetContent(sData, "Path");
            sReqSize = this._oAtx.omlGetContent(sData, "ReqSize");
            sCurSize = this._oAtx.omlGetContent(sData, "CurSize");

            sParam = "path=" + sPath + "&total=" + sReqSize + "&position=" + sCurSize;
            this._OnEvent("FileProgress", sParam, sCapID);

            var iCurSize = this._ParseInt(sCurSize, 0);
            var iReqSize = this._ParseInt(sReqSize, 0);
            if (iCurSize >= iReqSize && iReqSize > 0) {
                this._OnEvent("FileFinish", sParam, sCapID);
            } else {
                this._OnEvent("FileAbort", sParam, sCapID);
            }
        }

        return 0;
    };

    this._OnFileCancel = function(sObject) {
        var sCapID = this._FileObjectParseCapID(sObject);
        if (sCapID == "") {
            return;
        }

        this._FileListSet(sCapID, "Status", "0");
        this._OnEvent("FileAbort", "", sCapID);
    };


    ///------------------------------------------------------------------------
    // Node callback functions.
    this._OnExtRequest = function(sObj, uMeth, sData, uHandle, sPeer) {
        var sAct = "";
        if (!this._VideoObjectIs(sObj) && uMeth != 35) {
            this._OutString("pgLibLiveMultiRender._OnExtRequest: " + sObj + ", " + uMeth + ", " + sData + ", " + sPeer);
        }

        if (this.m_eventHook && this.m_eventHook.OnExtRequest && typeof(this.m_eventHook.OnExtRequest) == "function") {
            var iErr = this.m_eventHook.OnExtRequest(sObj, uMeth, sData, uHandle, sPeer);
            if (iErr != 255) {
                return iErr;
            }
        }

        if (this._VideoObjectIs(sObj)) {
            if (uMeth == 35) {
                this._OnVideoStatus(sObj, sData);
            }
            return 0;
        }

        if (this._FileObjectIs(sObj)) {
            if (uMeth == 32) { // put file request
                return this._OnFileRequest(sObj, uMeth, sData, uHandle);
            }

            if (uMeth == 33) { // get file request
                return this._OnFileRequest(sObj, uMeth, sData, uHandle);
            }

            if (uMeth == 34) { // File transfer status report.
                this._OnFileStatus(sObj, sData);
                return 0;
            }

            if (uMeth == 35) { // Cancel file request
                this._OnFileCancel(sObj);
                return 0;
            }

            return 0;
        }

        if (this._GroupObjectIs(sObj)) {
            if (uMeth == 33) {
                this._OnGroupUpdate(sObj, sData);
            }
            return 0;
        }
        var sCapID = "";
        if (this._DataObjectIs(sObj)) {
            if (uMeth == 32) {
                sCapID = this._DataObjectParseCapID(sObj);
                this._OnEvent("Notify", sData, sCapID);
                return 0;
            }

            if (uMeth == 0) {
                sCapID = this._DataObjectParseCapID(sObj);
                var sObjCapture = this._CaptureBuildObject(sCapID);
                if (sPeer == sObjCapture) {
                    sAct = this._oAtx.omlGetContent(sData, "Action");
                    if (sAct != "1") {
                        this._OnCaptureDisconnect(sObjCapture);
                    }
                }
                return 0;
            }

            return 0;
        }

        if (sObj == this._sObjSelf) {
            if (uMeth == 36) {
                if (sPeer == this._sObjSvr) {
                    this._OnServerMessage(sData, sPeer);
                } else {
                    this._OnSelfMessage(sData, sPeer);
                }
            } else if (uMeth == 0) {
                this._OnSelfSync(sData, sPeer);
            } else if (uMeth == 47) {
                if (sPeer == this._sObjSvr) {
                    this._OnServerKickOut(sData);
                }
            }
            return 0;
        }

        if (sObj == this._sObjSvr) {
            if (uMeth == 0) {
                this._OnServerSync(sData);
            } else if (uMeth == 1) {
                this._OnServerError(sData, sPeer);
            } else if (uMeth == 46) {
                this._OnServerRelogin(sData, sPeer);
            }
            return 0;
        }

        if (this._CaptureObjectIs(sObj)) {
            if (uMeth == 0) {
                sAct = this._oAtx.omlGetContent(sData, "Action");
                if (sAct == "1") {
                    this._OnCapturePeerSync(sObj);
                }
            } else if (uMeth == 1) {
                var sMeth = this._oAtx.omlGetContent(sData, "Meth");
                if (sMeth == "34") {
                    var sError = this._oAtx.omlGetContent(sData, "Error");
                    this._OnCaptureOffline(sObj, sError);
                }
            }
            return 0;
        }

        if (this._AudioObjectIs(sObj)) {
            if (uMeth == 0) {
                this._OnAudioSync(sObj, sData, sPeer);
            }
            return 0;
        }

        if (this._oAtx.ObjectGetClass(sObj) == "PG_CLASS_Peer") {
            if (uMeth == 0) {
                this._OnPeerSync(sObj, sData);
            }
            return 0;
        }

        return 0;
    };


    //------------------------------------------------------
    // OnReply callback process functions.

    this._OnSvrReply = function(iErr, sData, sParam) {
        // "LIVE_SVR_REQ:"
        var sParamTemp = sParam.substring(13);
        if (iErr != pgErrCode.PG_ERR_Normal) {
            this._OnEvent("SvrReplyError", (iErr + ""), sParamTemp);
        } else {
            this._OnEvent("SvrReply", sData, sParamTemp);
        }
    };

    this._OnLanScanResult = function(sData) {

        this._sLanScanRes = "";

        var iInd = 0;
        var sEle = "";
        while (true) {
            sEle = this._oAtx.omlGetEle(sData, "PeerList.", 1, iInd);
            if (sEle == "") {
                break;
            }

            var sPeer = this._oAtx.omlGetName(sEle, "");
            var iPos = sPeer.indexOf("_CAP_");
            if (iPos == 0) {
                var sAddr = this._oAtx.omlGetContent(sEle, ".Addr");
                if (this._bApiLanScan) {
                    var sID = sPeer.substring(5);
                    var sDataTemp = "id=" + sID + "&addr=" + sAddr;
                    this._OnEvent("LanScanResult", sDataTemp, "");
                }
                this._sLanScanRes += "(" + this._oAtx.omlEncode(sPeer) + "){" + sAddr + "}";
            }

            iInd++;
        }

        if (!this._bLogin) {
            iInd = 0;
            while (true) {
                sEle = this._oAtx.omlGetEle(this._sListCapture, "", 1, iInd);
                if (sEle == "") {
                    break;
                }

                var sCapID = this._oAtx.omlGetName(sEle, "");
                this._CapturePeerStatic(sCapID);
                iInd++;
            }
        }

        this._bApiLanScan = false;
    };

    this._OnVideoCameraReply = function(sObject, sData) {
        var sPath = this._oAtx.omlGetContent(sData, "Path");
        var sCapID = this._VideoObjectParseCapID(sObject);

        this._OnEvent("VideoCamera", sPath, sCapID);
    };

    this._OnPeerGetInfoReply = function(sObj, iErr, sData) {
        if (iErr != pgErrCode.PG_ERR_Normal) {
            return;
        }

        var sCapID = this._CaptureObjectParseCapID(sObj);
        if (sObj != this._sObjSvr) {
            if (!this._CaptureListExist(sCapID)) {
                return;
            }
        } else {
            sCapID = sObj;
        }

        var sThrough = this._oAtx.omlGetContent(sData, "Through");
        var sProxy = this._AddrToReadable(this._oAtx.omlGetContent(sData, "Proxy"));

        var sAddrLcl = this._AddrToReadable(this._oAtx.omlGetContent(sData, "AddrLcl"));

        var sAddrRmt = this._AddrToReadable(this._oAtx.omlGetContent(sData, "AddrRmt"));

        var sTunnelLcl = this._AddrToReadable(this._oAtx.omlGetContent(sData, "TunnelLcl"));

        var sTunnelRmt = this._AddrToReadable(this._oAtx.omlGetContent(sData, "TunnelRmt"));

        var sPrivateRmt = this._AddrToReadable(this._oAtx.omlGetContent(sData, "PrivateRmt"));

        var sDataInfo = "16:(" + this._oAtx.omlEncode(sObj) + "){(Through){" + sThrough + "}(Proxy){" +
            this._oAtx.omlEncode(sProxy) + "}(AddrLcl){" + this._oAtx.omlEncode(sAddrLcl) + "}(AddrRmt){" +
            this._oAtx.omlEncode(sAddrRmt) + "}(TunnelLcl){" + this._oAtx.omlEncode(sTunnelLcl) + "}(TunnelRmt){" +
            this._oAtx.omlEncode(sTunnelRmt) + "}(PrivateRmt){" + this._oAtx.omlEncode(sPrivateRmt) + "}}";

        var iErrTemp = this._oAtx.ObjectRequest(this._sObjSvr, 35, sDataInfo, "pgLibLiveMultiRender.ReportPeerInfo");
        if (iErrTemp > pgErrCode.PG_ERR_Normal) {
            this._OutString("pgLibLiveMultiRender._OnPeerGetInfoReply: iErr=" + iErrTemp);
        }

        // Report to app.
        sDataInfo = "peer=" + sCapID + "&through=" + sThrough + "&proxy=" + sProxy +
            "&addrlcl=" + sAddrLcl + "&addrrmt=" + sAddrRmt + "&tunnellcl=" + sTunnelLcl +
            "&tunnelrmt=" + sTunnelRmt + "&privatermt=" + sPrivateRmt;
        this._OnEvent("PeerInfo", sDataInfo, sCapID);
    };

    this._OnReply = function(sObj, uErr, sData, sParam) {

        this._OutString("pgLibLiveMultiRender._OnReply: " + sObj + ", " + uErr + ", " + sData + ", " + sParam);

        if (this.m_eventHook && this.m_eventHook.OnReply && typeof(this.m_eventHook.OnReply) == "function") {
            var iErr = this.m_eventHook.OnReply(sObj, uErr, sData, sParam);
            if (iErr >= 0) {
                return iErr;
            }
        }

        if (sObj == this._sObjSvr) {
            if (sParam == "pgLibLiveMultiRender._NodeLogin") {
                this._NodeLoginReply(uErr, sData);
            } else if (sParam.indexOf("LIVE_SVR_REQ:") == 0) {
                this._OnSvrReply(uErr, sData, sParam);
            } else if (sParam == "pgLibLiveMultiRender.LanScan") {
                this._OnLanScanResult(sData);
            } else if (sParam == "pgLibLiveMultiRender.PeerGetInfo") {
                this._OnPeerGetInfoReply(sObj, uErr, sData);
            }
            return 1;
        }

        if (this._FileObjectIs(sObj)) {
            if (sParam == "pgLibLiveMultiRender.FileGetRequest" ||
                sParam == "pgLibLiveMultiRender.FilePutRequest") {
                var sCapID = this._FileObjectParseCapID(sObj);
                if (uErr != pgErrCode.PG_ERR_Normal) {
                    this._FileListSet(sCapID, "Status", "0");
                    this._OnEvent("FileReject", (uErr + ""), sCapID);
                    return 1;
                } else {
                    this._FileListSet(sCapID, "Status", "1");
                    this._OnEvent("FileAccept", "0", sCapID);
                    return 1;
                }
            }

            return 1;
        }

        if (this._VideoObjectIs(sObj)) {
            if (sParam == "pgLibLiveMultiRender.VideoCamera") {
                this._OnVideoCameraReply(sObj, sData);
            }

            return 1;
        }

        if (this._CaptureObjectIs(sObj)) {
            if (sParam == "pgLibLiveMultiRender.PeerGetInfo") {
                this._OnPeerGetInfoReply(sObj, uErr, sData);
            }
            return 1;
        }

        return 1;
    };
}


// Live callback.
var _pgLiveMultiCallback = {

    aLiveList: new Array(null, null, null, null),

    OnExtRequest0: function(sObj, uMeth, sData, uHandle, sPeer) {
        if (_pgLiveMultiCallback.aLiveList[0]) {
            return _pgLiveMultiCallback.aLiveList[0]._OnExtRequest(sObj, uMeth, sData, uHandle, sPeer);
        }
        return 0;
    },
    OnReply0: function(sObj, uErr, sData, sParam) {
        if (_pgLiveMultiCallback.aLiveList[0]) {
            return _pgLiveMultiCallback.aLiveList[0]._OnReply(sObj, uErr, sData, sParam);
        }
        return 1;
    },
    OnTimer0: function(sExec) {
        if (_pgLiveMultiCallback.aLiveList[0]) {
            return _pgLiveMultiCallback.aLiveList[0]._OnTimer(sExec);
        }
        return 1;
    },
    OnTimeout0: function(sExec) {
        if (_pgLiveMultiCallback.aLiveList[0]) {
            return _pgLiveMultiCallback.aLiveList[0]._OnTimeout(sExec);
        }
        return 1;
    },

    OnExtRequest1: function(sObj, uMeth, sData, uHandle, sPeer) {
        if (_pgLiveMultiCallback.aLiveList[1]) {
            return _pgLiveMultiCallback.aLiveList[1]._OnExtRequest(sObj, uMeth, sData, uHandle, sPeer);
        }
        return 0;
    },
    OnReply1: function(sObj, uErr, sData, sParam) {
        if (_pgLiveMultiCallback.aLiveList[1]) {
            return _pgLiveMultiCallback.aLiveList[1]._OnReply(sObj, uErr, sData, sParam);
        }
        return 1;
    },
    OnTimer1: function(sExec) {
        if (_pgLiveMultiCallback.aLiveList[1]) {
            return _pgLiveMultiCallback.aLiveList[1]._OnTimer(sExec);
        }
        return 1;
    },
    OnTimeout1: function(sExec) {
        if (_pgLiveMultiCallback.aLiveList[1]) {
            return _pgLiveMultiCallback.aLiveList[1]._OnTimeout(sExec);
        }
        return 1;
    },

    OnExtRequest2: function(sObj, uMeth, sData, uHandle, sPeer) {
        if (_pgLiveMultiCallback.aLiveList[2]) {
            return _pgLiveMultiCallback.aLiveList[2]._OnExtRequest(sObj, uMeth, sData, uHandle, sPeer);
        }
        return 0;
    },
    OnReply2: function(sObj, uErr, sData, sParam) {
        if (_pgLiveMultiCallback.aLiveList[2]) {
            return _pgLiveMultiCallback.aLiveList[2]._OnReply(sObj, uErr, sData, sParam);
        }
        return 1;
    },
    OnTimer2: function(sExec) {
        if (_pgLiveMultiCallback.aLiveList[2]) {
            return _pgLiveMultiCallback.aLiveList[2]._OnTimer(sExec);
        }
        return 1;
    },
    OnTimeout2: function(sExec) {
        if (_pgLiveMultiCallback.aLiveList[2]) {
            return _pgLiveMultiCallback.aLiveList[2]._OnTimeout(sExec);
        }
        return 1;
    },

    OnExtRequest3: function(sObj, uMeth, sData, uHandle, sPeer) {
        if (_pgLiveMultiCallback.aLiveList[3]) {
            return _pgLiveMultiCallback.aLiveList[3]._OnExtRequest(sObj, uMeth, sData, uHandle, sPeer);
        }
        return 0;
    },
    OnReply3: function(sObj, uErr, sData, sParam) {
        if (_pgLiveMultiCallback.aLiveList[3]) {
            return _pgLiveMultiCallback.aLiveList[3]._OnReply(sObj, uErr, sData, sParam);
        }
        return 1;
    },
    OnTimer3: function(sExec) {
        if (_pgLiveMultiCallback.aLiveList[3]) {
            return _pgLiveMultiCallback.aLiveList[3]._OnTimer(sExec);
        }
        return 1;
    },
    OnTimeout3: function(sExec) {
        if (_pgLiveMultiCallback.aLiveList[3]) {
            return _pgLiveMultiCallback.aLiveList[3]._OnTimeout(sExec);
        }
        return 1;
    }
};