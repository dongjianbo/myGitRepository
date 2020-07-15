

var pgLibLiveMode = {
    Render: 0,
    Capture: 1
};

function pgLibLive(oAtx, oUI) {
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

    this.Initialize = function(iMode, sUser, sPass, sSvrAddr, sRelayAddr, iP2PTryTime, sVideoParam) {
        return this.InitializeEx(iMode, sUser, sPass, sSvrAddr, sRelayAddr, iP2PTryTime, "", sVideoParam, "");
    };

    this.InitializeEx = function(iMode, sUser, sPass, sSvrAddr,
        sRelayAddr, iP2PTryTime, sInitParam, sVideoParam, sAudioParam) {
        if ((iMode != pgLibLiveMode.Capture) && (iMode != pgLibLiveMode.Render)) {
            this._OutString("pgLibLive.InitializeEx: invalid iMode.");
            return false;
        }

        // Store parameters.
        this._iMode = iMode;
        this._sVideoParam = sVideoParam;
        this._sAudioParam = sAudioParam;
        this._sCapID = "";

        var sInitParam1 = "(SingleMode){1}" + sInitParam;
        var iErr;
        if (this._iMode == pgLibLiveMode.Capture) {

            this._oLiveCapture = new pgLibLiveMultiCapture(this._oAtx, this._oUI);
            this._oLiveCapture.SetNodeEventHook(this.m_eventHook);
            iErr = this._oLiveCapture.Initialize(sUser, sPass,
                sSvrAddr, sRelayAddr, iP2PTryTime, sInitParam1);
            if (iErr <= pgErrCode.PG_ERR_Normal) {
                return true;
            }
            this._oLiveCapture = null;
            return false;
        } else if (this._iMode == pgLibLiveMode.Render) {

            this._oLiveRender = new pgLibLiveMultiRender(this._oAtx, this._oUI);
            this._oLiveRender.SetNodeEventHook(this.m_eventHook);
            iErr = this._oLiveRender.Initialize(sUser, sPass,
                sSvrAddr, sRelayAddr, iP2PTryTime, sInitParam1);
            if (iErr <= pgErrCode.PG_ERR_Normal) {
                return true;
            }
            this._oLiveRender = null;
            return false;
        } else {
            this._oLiveCapture = null;
            this._oLiveRender = null;
            return false;
        }
    };

    this.Clean = function() {
        if (this._iMode == pgLibLiveMode.Capture) {
            if (this._oLiveCapture != null) {
                this._oLiveCapture.Clean();
                this._oLiveCapture = null;
            }
        } else if (this._iMode == pgLibLiveMode.Render) {
            if (this._oLiveRender != null) {
                this._oLiveRender.Clean();
                this._oLiveRender = null;
            }
        }
    };

    this.SetVideoDiv = function(divid) {
        this._divid = divid;
    };

    this.GetSelfPeer = function() {
        if (this._iMode == pgLibLiveMode.Capture) {
            if (this._oLiveCapture != null) {
                return this._oLiveCapture.GetSelfPeer();
            }
        } else if (this._iMode == pgLibLiveMode.Render) {
            if (this._oLiveRender != null) {
                return this._oLiveRender.GetSelfPeer();
            }
        }
        return "";
    };

    // Start and stop live lib.
    this.Start = function(sCapID) {
        if (this._iMode == pgLibLiveMode.Capture) {
            if (this._oLiveCapture != null) {
                var sCapObj = this._oLiveCapture.GetSelfPeer();
                if (sCapObj.indexOf("_CAP_") == 0) {
                    this._sCapID = sCapObj.substring(5);
                    return true;
                }
            }
        } else if (this._iMode == pgLibLiveMode.Render) {
            if (this._oLiveRender != null) {
                var iErr = this._oLiveRender.Connect(sCapID);
                if (iErr <= pgErrCode.PG_ERR_Normal) {
                    this._sCapID = sCapID;
                    return true;
                }
            }
        }
        return false;
    };

    this.Stop = function() {
        if (this._iMode == pgLibLiveMode.Render) {
            if (this._oLiveRender != null) {
                this._oLiveRender.Disconnect(this._sCapID);
                this._sCapID = "";
            }
        }
    };

    this.Connected = function() {
        if (this._iMode == pgLibLiveMode.Render) {
            if (this._oLiveRender != null) {
                var iErr = this._oLiveRender.Connected(this._sCapID);
                if (iErr <= pgErrCode.PG_ERR_Normal) {
                    return true;
                }
            }
        }
        return false;
    };
    // Render handle
    this.RenderReject = function(sRenID) {
        if (this._iMode == pgLibLiveMode.Capture) {
            if (this._oLiveCapture != null) {
                var iErr = this._oLiveCapture.RenderReject(sRenID);
                if (iErr <= pgErrCode.PG_ERR_Normal) {
                    return true;
                }
            }
        }
        return false;
    };

    this.RenderAccess = function(sRenID, bVideo, bAudio) {
        if (this._iMode == pgLibLiveMode.Capture) {
            if (this._oLiveCapture != null) {
                var iErr = this._oLiveCapture.RenderAccess(sRenID, bVideo, bAudio);
                if (iErr <= pgErrCode.PG_ERR_Normal) {
                    return true;
                }
            }
        }
        return false;
    };

    this.RenderEnum = function(iIndex) {
        if (this._iMode == pgLibLiveMode.Capture) {
            if (this._oLiveCapture != null) {
                return this._oLiveCapture.RenderEnum(iIndex);
            }
        }
        return "";
    };

    this.RenderConnected = function(sRenID) {
        if (this._iMode == pgLibLiveMode.Capture) {
            if (this._oLiveCapture != null) {
                var iErr = this._oLiveCapture.RenderConnected(sRenID);
                if (iErr <= pgErrCode.PG_ERR_Normal) {
                    return true;
                }
            }
        }
        return false;
    };

    // Start and stop video
    this.VideoStart = function() {
        var iErr = 0;
        if (this._iMode == pgLibLiveMode.Capture) {
            if (this._oLiveCapture != null) {
                iErr = this._oLiveCapture.VideoStart(0, this._sVideoParam, this._divid);
                if (iErr <= pgErrCode.PG_ERR_Normal) {
                    return true;
                }
            }
        } else if (this._iMode == pgLibLiveMode.Render) {
            if (this._oLiveRender != null) {
                iErr = this._oLiveRender.VideoStart(this._sCapID, 0, this._sVideoParam, this._divid);
                if (iErr <= pgErrCode.PG_ERR_Normal) {
                    return true;
                }
            }
        }
        return false;
    };

    this.VideoStop = function() {
        if (this._iMode == pgLibLiveMode.Capture) {
            if (this._oLiveCapture != null) {
                this._oLiveCapture.VideoStop(0);
            }
        } else if (this._iMode == pgLibLiveMode.Render) {
            if (this._oLiveRender != null) {
                this._oLiveRender.VideoStop(this._sCapID, 0);
            }
        }
    };

    // Start and stop audio
    this.AudioStart = function() {
        var iErr = 0;
        if (this._iMode == pgLibLiveMode.Capture) {
            if (this._oLiveCapture != null) {
                iErr = this._oLiveCapture.AudioStart(0, this._sAudioParam);
                if (iErr <= pgErrCode.PG_ERR_Normal) {
                    return true;
                }
            }
        } else if (this._iMode == pgLibLiveMode.Render) {
            if (this._oLiveRender != null) {
                iErr = this._oLiveRender.AudioStart(this._sCapID, 0, this._sAudioParam);
                if (iErr <= pgErrCode.PG_ERR_Normal) {
                    return true;
                }
            }
        }
        return false;
    };

    this.AudioStop = function() {
        if (this._iMode == pgLibLiveMode.Capture) {
            if (this._oLiveCapture != null) {
                this._oLiveCapture.AudioStop(0);
            }
        } else if (this._iMode == pgLibLiveMode.Render) {
            if (this._oLiveRender != null) {
                this._oLiveRender.AudioStop(this._sCapID, 0);
            }
        }
    };

    // Send notify at capture side.
    this.NotifySend = function(sMsg) {
        if (this._iMode == pgLibLiveMode.Capture) {
            if (this._oLiveCapture != null) {
                var iErr = this._oLiveCapture.NotifySend(sMsg);
                if (iErr <= pgErrCode.PG_ERR_Normal) {
                    return true;
                }
            }
        }
        return false;
    };

    // Send message at capture side or render side
    this.MessageSend = function(sData, sRender) {
        var iErr = 0;
        if (this._iMode == pgLibLiveMode.Capture) {
            if (this._oLiveCapture != null) {
                iErr = this._oLiveCapture.MessageSend(sRender, sData);
                if (iErr <= pgErrCode.PG_ERR_Normal) {
                    return true;
                }
            }
        } else if (this._iMode == pgLibLiveMode.Render) {
            if (this._oLiveRender != null) {
                iErr = this._oLiveRender.MessageSend(this._sCapID, sData);
                if (iErr <= pgErrCode.PG_ERR_Normal) {
                    return true;
                }
            }
        }
        return false;
    };

    // Pull one MJPEG frame.
    this.FramePull = function() {
        if (this._iMode == pgLibLiveMode.Render) {
            if (this._oLiveRender != null) {
                var iErr = this._oLiveRender.VideoFramePull(this._sCapID, 0);
                if (iErr <= pgErrCode.PG_ERR_Normal) {
                    return true;
                }
            }
        }
        return false;
    };

    // Scan the captures in the same lan.
    this.LanScanStart = function() {
        if (this._iMode == pgLibLiveMode.Render) {
            if (this._oLiveRender != null) {
                var iErr = this._oLiveRender.LanScanStart();
                if (iErr <= pgErrCode.PG_ERR_Normal) {
                    return true;
                }
            }
        }
        return false;
    };

    this.VideoSource = function(iCameraNo) {
        if (this._iMode == pgLibLiveMode.Capture) {
            if (this._oLiveCapture != null) {
                var sVideoParam = "(CameraNo){" + iCameraNo + "}";
                var iErr = this._oLiveCapture.VideoParam(0, sVideoParam);
                if (iErr <= pgErrCode.PG_ERR_Normal) {
                    return true;
                }
            }
        }
        return false;
    };

    this.VideoCamera = function(sJpgPath) {
        var iErr = 0;
        if (this._iMode == pgLibLiveMode.Capture) {
            if (this._oLiveCapture != null) {
                iErr = this._oLiveCapture.VideoCamera(0, sJpgPath);
                if (iErr <= pgErrCode.PG_ERR_Normal) {
                    return true;
                }
            }
        } else if (this._iMode == pgLibLiveMode.Render) {
            if (this._oLiveRender != null) {
                iErr = this._oLiveRender.VideoCamera(this._sCapID, 0, sJpgPath);
                if (iErr <= pgErrCode.PG_ERR_Normal) {
                    return true;
                }
            }
        }
        return false;
    };

    this.VideoModeSize = function(iMode, iWidth, iHeight) {
        var iErr = 0;
        if (this._iMode == pgLibLiveMode.Capture) {
            if (this._oLiveCapture != null) {
                iErr = this._oLiveCapture.VideoModeSize(iMode, iWidth, iHeight);
                if (iErr <= pgErrCode.PG_ERR_Normal) {
                    return true;
                }
            }
        } else if (this._iMode == pgLibLiveMode.Render) {
            if (this._oLiveRender != null) {
                iErr = this._oLiveRender.VideoModeSize(iMode, iWidth, iHeight);
                if (iErr <= pgErrCode.PG_ERR_Normal) {
                    return true;
                }
            }
        }
        return false;
    };

    this.VideoShowMode = function(iMode) {
        if (this._iMode == pgLibLiveMode.Capture) {
            if (this._oLiveCapture != null) {
                //				var iErr = this._oLiveCapture.VideoShowMode(iMode);
                //				if (iErr <= pgErrCode.PG_ERR_Normal) {
                //					return true;
                //				}
            }
        } else if (this._iMode == pgLibLiveMode.Render) {
            if (this._oLiveRender != null) {
                var iErr = this._oLiveRender.VideoShowMode(iMode);
                if (iErr <= pgErrCode.PG_ERR_Normal) {
                    return true;
                }
            }
        }
        return false;
    };

    this.VideoParam = function(sParam) {
        var iErr = 0;
        if (this._iMode == pgLibLiveMode.Capture) {
            if (this._oLiveCapture != null) {
                iErr = this._oLiveCapture.VideoParam(0, sParam);
                if (iErr <= pgErrCode.PG_ERR_Normal) {
                    return true;
                }
            }
        } else if (this._iMode == pgLibLiveMode.Render) {
            if (this._oLiveRender != null) {
                iErr = this._oLiveRender.VideoParam(this._sCapID, 0, sParam);
                if (iErr <= pgErrCode.PG_ERR_Normal) {
                    return true;
                }
            }
        }
        return false;
    };

    this.VideoRecordStart = function(sAviPath) {
        if (this._iMode == pgLibLiveMode.Render) {
            if (this._oLiveRender != null) {
                var iErr = this._oLiveRender.RecordStart(this._sCapID, sAviPath, 0, -1);
                if (iErr <= pgErrCode.PG_ERR_Normal) {
                    return true;
                }
            }
        }
        return false;
    };

    this.VideoRecordStop = function() {
        if (this._iMode == pgLibLiveMode.Render) {
            if (this._oLiveRender != null) {
                this._oLiveRender.RecordStop(this._sCapID);
            }
        }
    };

    this.AudioSpeech = function(bEnable) {
        if (this._iMode == pgLibLiveMode.Render) {
            if (this._oLiveRender != null) {
                var iErr = this._oLiveRender.AudioSpeech(this._sCapID, 0, bEnable);
                if (iErr <= pgErrCode.PG_ERR_Normal) {
                    return true;
                }
            }
        }
        return false;
    };

    this.AudioParam = function(sParam) {
        var iErr = 0;
        if (this._iMode == pgLibLiveMode.Capture) {
            if (this._oLiveCapture != null) {
                iErr = this._oLiveCapture.AudioParam(0, sParam);
                if (iErr <= pgErrCode.PG_ERR_Normal) {
                    return true;
                }
            }
        } else if (this._iMode == pgLibLiveMode.Render) {
            if (this._oLiveRender != null) {
                iErr = this._oLiveRender.AudioParam(this._sCapID, 0, sParam);
                if (iErr <= pgErrCode.PG_ERR_Normal) {
                    return true;
                }
            }
        }
        return false;
    };

    this.AudioMute = function(bInput, bOutput) {
        var iErr = 0;
        if (this._iMode == pgLibLiveMode.Capture) {
            if (this._oLiveCapture != null) {
                iErr = this._oLiveCapture.AudioMute(0, bInput, bOutput);
                if (iErr <= pgErrCode.PG_ERR_Normal) {
                    return true;
                }
            }
        } else if (this._iMode == pgLibLiveMode.Render) {
            if (this._oLiveRender != null) {
                iErr = this._oLiveRender.AudioMute(this._sCapID, 0, bInput, bOutput);
                if (iErr <= pgErrCode.PG_ERR_Normal) {
                    return true;
                }
            }
        }
        return false;
    };

    this.AudioSpeakerVolume = function(iVolume, sPeer) {
        if (this._oLiveCapture == null && this._oLiveRender == null) {
            this._OutString("pgLibLive.AudioSpeakerVolume: Not initialize");
            return false;
        }

        var sPeerTemp = "";
        if (sPeer == "") {
            sPeerTemp = ("_CAP_" + this._sCapID);
        } else {
            if (sPeer.indexOf("_RND_") != 0) {
                sPeerTemp = ("_RND_" + sPeer);
            } else {
                sPeerTemp = sPeer;
            }
        }

        var sData = "(Peer){" + this._oAtx.omlEncode(sPeerTemp) +
            "}(Action){1}(Type){0}(Volume){" + iVolume + "}(Max){0}(Min){0}";
        var iErr = this._oAtx.ObjectRequest(("thisAudio_" + this._sCapID), 34, sData, "pgLibLive.AudioSpeakerVolume");
        if (iErr > 0) {
            this._OutString("pgLibLive.AudioSpeakerVolume: Set volume, iErr=" + iErr);
            return false;
        }

        return true;
    };

    this.RecordStart = function(sAviPath, bVideo, bAudio) {
        var iVideoID;
        var iAudioID;
        var iErr;
        if (this._iMode == pgLibLiveMode.Capture) {
            if (this._oLiveCapture != null) {
                iVideoID = bVideo ? 0 : -1;
                iAudioID = bAudio ? 0 : -1;
                iErr = this._oLiveCapture.RecordStart("Default", sAviPath, iVideoID, iAudioID);
                if (iErr <= pgErrCode.PG_ERR_Normal) {
                    return true;
                }
            }
        } else if (this._iMode == pgLibLiveMode.Render) {
            if (this._oLiveRender != null) {
                iVideoID = bVideo ? 0 : -1;
                iAudioID = bAudio ? 0 : -1;
                iErr = this._oLiveRender.RecordStart(this._sCapID, sAviPath, iVideoID, iAudioID);
                if (iErr <= pgErrCode.PG_ERR_Normal) {
                    return true;
                }
            }
        }
        return false;
    };

    this.RecordStop = function() {
        if (this._iMode == pgLibLiveMode.Capture) {
            if (this._oLiveCapture != null) {
                this._oLiveCapture.RecordStop("Default");
            }
        } else if (this._iMode == pgLibLiveMode.Render) {
            if (this._oLiveRender != null) {
                this._oLiveRender.RecordStop(this._sCapID);
            }
        }
    };

    this.SvrRequest = function(sData) {
        var iErr = 0;
        if (this._iMode == pgLibLiveMode.Capture) {
            if (this._oLiveCapture != null) {
                iErr = this._oLiveCapture.SvrRequest(sData, "");
                if (iErr <= pgErrCode.PG_ERR_Normal) {
                    return true;
                }
            }
        } else if (this._iMode == pgLibLiveMode.Render) {
            if (this._oLiveRender != null) {
                iErr = this._oLiveRender.SvrRequest(sData, "");
                if (iErr <= pgErrCode.PG_ERR_Normal) {
                    return true;
                }
            }
        }
        return false;
    };

    this.ForwardAlloc = function() {
        if (this._iMode == pgLibLiveMode.Capture) {
            if (this._oLiveCapture != null) {
                return this._oLiveCapture.VideoForwardAlloc(0, "");
            }
        }
        return pgErrCode.PG_ERR_BadStatus;
    };

    this.ForwardFree = function() {
        if (this._iMode == pgLibLiveMode.Capture) {
            if (this._oLiveCapture != null) {
                return this._oLiveCapture.VideoForwardFree(0, "");
            }
        }
        return pgErrCode.PG_ERR_BadStatus;
    };

    this.FilePutRequest = function(sPeer, sPath, sPeerPath) {
        if (this._iMode == pgLibLiveMode.Capture) {
            if (this._oLiveCapture != null) {
                return this._oLiveCapture.FilePutRequest(sPeer, sPath, sPeerPath);
            }
        } else if (this._iMode == pgLibLiveMode.Render) {
            if (this._oLiveRender != null) {
                return this._oLiveRender.FilePutRequest(sPeer, sPath, sPeerPath);
            }
        }
        return pgErrCode.PG_ERR_BadStatus;
    };

    this.FileGetRequest = function(sPeer, sPath, sPeerPath) {
        if (this._iMode == pgLibLiveMode.Capture) {
            if (this._oLiveCapture != null) {
                return this._oLiveCapture.FileGetRequest(sPeer, sPath, sPeerPath);
            }
        } else if (this._iMode == pgLibLiveMode.Render) {
            if (this._oLiveRender != null) {
                return this._oLiveRender.FileGetRequest(sPeer, sPath, sPeerPath);
            }
        }
        return pgErrCode.PG_ERR_BadStatus;
    };

    this.FileAccept = function(sPeer, sPath) {
        if (this._iMode == pgLibLiveMode.Capture) {
            if (this._oLiveCapture != null) {
                return this._oLiveCapture.FileAccept(sPeer, sPath);
            }
        } else if (this._iMode == pgLibLiveMode.Render) {
            if (this._oLiveRender != null) {
                return this._oLiveRender.FileAccept(sPeer, sPath);
            }
        }
        return pgErrCode.PG_ERR_BadStatus;
    };

    this.FileReject = function(sPeer) {
        if (this._iMode == pgLibLiveMode.Capture) {
            if (this._oLiveCapture != null) {
                return this._oLiveCapture.FileReject(sPeer, pgErrCode.PG_ERR_Reject);
            }
        } else if (this._iMode == pgLibLiveMode.Render) {
            if (this._oLiveRender != null) {
                return this._oLiveRender.FileReject(sPeer, pgErrCode.PG_ERR_Reject);
            }
        }
        return pgErrCode.PG_ERR_BadStatus;
    };

    this.FileCancel = function(sPeer) {
        if (this._iMode == pgLibLiveMode.Capture) {
            if (this._oLiveCapture != null) {
                return this._oLiveCapture.FileCancel(sPeer);
            }
        } else if (this._iMode == pgLibLiveMode.Render) {
            if (this._oLiveRender != null) {
                return this._oLiveRender.FileCancel(sPeer);
            }
        }
        return pgErrCode.PG_ERR_BadStatus;
    };


    ///------------------------------------------------------------------------
    // Private member variables.

    // Store ActiveX object and UI callback object.
    this._oAtx = oAtx;
    this._oUI = oUI;
    this._divid = "";

    // Store init parameters
    this._iMode = 0;
    this._sVideoParam = "";
    this._sAudioParam = "";
    this._sCapID = "";

    this._oLiveCapture = null;
    this._oLiveRender = null;
    this.m_eventHook = null;

    ///---------------------------------------------------------------------------------
    // Private methods.

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
}