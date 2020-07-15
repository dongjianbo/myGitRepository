var pgErrCode = {
    PG_ERR_Normal: 0,
    PG_ERR_System: 1,
    PG_ERR_BadParam: 2,
    PG_ERR_BadClass: 3,
    PG_ERR_BadMethod: 4,
    PG_ERR_BadObject: 5,
    PG_ERR_BadStatus: 6,
    PG_ERR_BadFile: 7,
    PG_ERR_BadUser: 8,
    PG_ERR_BadPass: 9,
    PG_ERR_NoLogin: 10,
    PG_ERR_Network: 11,
    PG_ERR_Timeout: 12,
    PG_ERR_Reject: 13,
    PG_ERR_Busy: 14,
    PG_ERR_Opened: 15,
    PG_ERR_Closed: 16,
    PG_ERR_Exist: 17,
    PG_ERR_NoExist: 18,
    PG_ERR_NoSpace: 19,
    PG_ERR_BadType: 20,
    PG_ERR_CheckErr: 21,
    PG_ERR_BadServer: 22,
    PG_ERR_BadDomain: 23,
    PG_ERR_NoDate: 24,
    PG_ERR_Unknown: 255
};


function NodeEventHook(extVideo) {
    this.ExtVideo = extVideo;
    this.OnExtRequest = function(sObj, uMeth, sData, uHandle, sObjPeer) {
        //if( this.mOnEvent.onGetObjPeer && typeof( this.mOnEvent.onGetObjPeerNotify) == "function"){
        if (this.ExtVideo.mOnEvent != null &&
            this.ExtVideo.mOnEvent.onVideoSync && typeof(this.ExtVideo.mOnEvent.onVideoSync) == "function" &&
            this.ExtVideo.mOnEvent.onVideoStart && typeof(this.ExtVideo.mOnEvent.onVideoStart) == "function" &&
            this.ExtVideo.mOnEvent.onVideoStop && typeof(this.ExtVideo.mOnEvent.onVideoStop) == "function" &&
            this.ExtVideo.mOnEvent.onVideoFrameStat && typeof(this.ExtVideo.mOnEvent.onVideoFrameStat) == "function" &&
            this.ExtVideo.mOnEvent.onAudioSync && typeof(this.ExtVideo.mOnEvent.onAudioSync) == "function" &&
            this.ExtVideo.mOnEvent.onAudioStart && typeof(this.ExtVideo.mOnEvent.onAudioStart) == "function" &&
            this.ExtVideo.mOnEvent.onAudioStop && typeof(this.ExtVideo.mOnEvent.onAudioStop) == "function" &&
            this.ExtVideo.onSvrMessage && typeof(this.ExtVideo.onSvrMessage) == "function") {
            var sAct;
            if (sObj.indexOf("EXT_VIDEO_") == 0) {
                if (uMeth == 0) {
                    sAct = this.ExtVideo.mNode.omlGetContent(sData, "Action");
                    return this.ExtVideo.mOnEvent.onVideoSync(sObj, sAct, sObjPeer);
                }

                if (uMeth == 32) {
                    return this.ExtVideo.mOnEvent.onVideoStart(sObj, uHandle, sObjPeer);
                }

                if (uMeth == 33) {
                    if (this.ExtVideo._ObjPeerListExist(sObjPeer)) {
                        this.ExtVideo._ObjPeerListSet(sObjPeer, "OpenV", "0");
                    }

                    this.ExtVideo.prvwStop();
                    return this.ExtVideo.mOnEvent.onVideoStop(sObj, sObjPeer);
                }

                if (uMeth == 40) {
                    this.ExtVideo.mOnEvent.onVideoFrameStat(sObj, sData);
                    return 0;
                }

                return 0;
            }

            if (sObj.indexOf("EXT_AUDIO_") == 0) {
                if (uMeth == 0) {
                    sAct = this.ExtVideo.mNode.omlGetContent(sData, "Action");
                    return this.ExtVideo.mOnEvent.onAudioSync(sObj, sAct);
                }

                if (uMeth == 32) {
                    return this.ExtVideo.mOnEvent.onAudioStart(sObj, uHandle, sObjPeer);
                }

                if (uMeth == 33) {
                    if (this.ExtVideo._ObjPeerListExist(sObjPeer)) {
                        this.ExtVideo._ObjPeerListSet(sObjPeer, "OpenA", "0");
                    }

                    return this.ExtVideo.mOnEvent.onAudioStop(sObj);
                }
            }

            if (this.ExtVideo.mObjSelf == (sObj) && uMeth == 36 && "pgConnectSvr" == (sObjPeer)) {
                return this.ExtVideo.onSvrMessage(sData, sObjPeer);
            }
        }

        return 255;
    };

    this.OnReply = function(sObj, uErr, sData, sParam) {
        if (this.ExtVideo.mOnEvent != null &&
            this.ExtVideo.mOnEvent.onVideoStartReply && typeof(this.ExtVideo.mOnEvent.onVideoStartReply) == "function" &&
            this.ExtVideo.mOnEvent.onAudioStartRelay && typeof(this.ExtVideo.mOnEvent.onAudioStartRelay) == "function" &&
            this.ExtVideo.mOnEvent.onGetObjPeerReply && typeof(this.ExtVideo.mOnEvent.onGetObjPeerReply) == "function") {
            if ("EXT_SDK_VIDEO_OPEN" == (sParam)) {
                return this.ExtVideo.mOnEvent.onVideoStartReply(sObj, uErr);
            }

            if ("EXT_SDK_AUDIO_OPEN" == (sParam)) {
                return this.ExtVideo.mOnEvent.onAudioStartRelay(sObj, uErr);
            }

            if (sParam.indexOf("EXT_SVR_REQ:") == 0) {
                this.ExtVideo.mOnEvent.onGetObjPeerReply(uErr, sParam.substr(12));
                return 1;
            }
        }

        return -1;
    };
}

function pgLibExtVideo() {
    this.LIB_VER = "2";
    this.GET_OBJPEER = "VCMD_GetObjPeer:";
    this.GET_OBJPEER_NOTIFY = "VCMD_GetObjPeerNotify:";
    this.PG_METH_VIDEO_START = 32;
    this.PG_METH_VIDEO_STOP = 33;
    this.PARAM_EXT_SDK_VIDEO_OPEN = "EXT_SDK_VIDEO_OPEN";
    this.PARAM_EXT_SDK_AUDIO_OPEN = "EXT_SDK_AUDIO_OPEN";
    this.mObjSvr = "pgConnectSvr";
    this.mObjPrvw = "VPrvw";
    this.isStateInit = false;
    this.isStatePrvw = false;
    this.mNode = null;
    this.sObjVSelfHash = "";
    this.mOnEvent = null;
    this.mObjSelf = "";
    this.mVideoParam = "";
    this.mAudioParam = "";
    this.sObjASelfHash = "";

    this.mListObjPeer = "";

    this.mHook = new NodeEventHook(this);

    this.mDivPrvw = "";

    this.version = function() {
        return "v2.4";
    };

    this.getHook = function() {
        return this.mHook;
    };

    this.setOnEvent = function(event) {
        this.mOnEvent = event;
    };

    this.initialize = function(node, sObjSelf, sVideoParam, sAudioParam) {
        if (node != null && sObjSelf != null && sObjSelf.length > 5) {
            if (!this.isStateInit) {
                this.mNode = node;
                this.mObjSelf = sObjSelf;
                this.mVideoParam = sVideoParam;
                this.mAudioParam = sAudioParam;
                var sPeer = sObjSelf.length > 100 ? sObjSelf.substr(5, 100) : sObjSelf.substr(5);
                var iFlagSelf = (0x10000 | 0x4 | 0x10 | 0x20);
                var iFlagSelfA = (0x10000 | 0x4);
                this.sObjVSelfHash = "EXT_VIDEO_" + sPeer;
                if (!this.mNode.ObjectAdd(this.sObjVSelfHash, "PG_CLASS_Video", "", iFlagSelf)) {
                    this.OutString("pgExtVideo", "Ext Video Object Add failed");
                    return 1;
                }

                this.sObjASelfHash = "EXT_AUDIO_" + sPeer;
                if (!this.mNode.ObjectAdd(this.sObjASelfHash, "PG_CLASS_Audio", "", iFlagSelfA)) {
                    this.OutString("pgExtVideo", "Ext Video Object Add failed");
                    return 1;
                }

                this.mListObjPeer = "";
                this._ObjPeerListAdd(sObjSelf, this.sObjVSelfHash, this.sObjASelfHash);
                this._VideoOption(sVideoParam);
            }

            return 0;
        } else {
            this.OutString("initialize param error , live = " + node + " sObjSelf = " + sObjSelf);
            return 2;
        }
    };

    this.clean = function() {
        if (this.mNode != null) {
            var i = 0;

            while (true) {
                var sEle = this.mNode.omlGetEle(this.mListObjPeer, "", 1, i);
                if (sEle.length <= 0) {
                    this.mListObjPeer = "";
                    break;
                }

                var sObjPeer = this.mNode.omlGetName(sEle, "");
                var sOpenV = this._ObjPeerListGet(sObjPeer, "OpenV");
                if ("1" == (sOpenV)) {
                    this.videoStop(sObjPeer, this.isStatePrvw);
                }

                var sOpenA = this._ObjPeerListGet(sObjPeer, "OpenA");
                if ("1" == (sOpenA)) {
                    this.audioStop(sObjPeer);
                }

                this.mNode.ObjectSetGroup(this.sObjVSelfHash, "");
                this.mNode.ObjectSetGroup(this.sObjASelfHash, "");
                var sObjVPeerHash = this._ObjPeerListGet(sObjPeer, "ObjV");
                var sObjAPeerHash = this._ObjPeerListGet(sObjPeer, "ObjA");
                this.mNode.ObjectDelete(sObjAPeerHash);
                this.mNode.ObjectDelete(sObjVPeerHash);
                ++i;
            }
        }

        this.mNode = null;
        this.isStateInit = false;
    };

    this.setPrewview = function(divid) {

        this.mDivPrvw = divid;

    };

    this.sendGetObjPeer = function(sPeer, sParam) {
        var sMsg = "(CMD){VCMD_GetObjPeer:}(Param){" + sParam + "}(ObjPeer){" + this.mObjSelf + "}";
        var sData = "Forward?(User){" + this.mNode.omlEncode(sPeer) + "}(Msg){" + this.mNode.omlEncode(sMsg) + "}";
        return this.svrRequest(sData, sParam);
    };

    this.sendGetObjPeerNotify = function(sPeer, sParam) {
        var sMsg = "(CMD){VCMD_GetObjPeerNotify:}(Param){" + sParam + "}(ObjPeer){" + this.mObjSelf + "}";
        var sData = "Forward?(User){" + this.mNode.omlEncode(sPeer) + "}(Msg){" + this.mNode.omlEncode(sMsg) + "}";
        return this.svrRequest(sData, sParam);
    };

    this.start = function(sObjPeer) {
        if (sObjPeer.length > 5) {
            var iFlagPeer = (0x10000 | 0x8 | 0x10 | 0x20);
            var iFlagPeerA = (0x10000 | 0x8);
            if (!this._ObjPeerListExist(sObjPeer)) {
                var sClass = this.mNode.ObjectGetClass(sObjPeer);
                if ("PG_CLASS_Peer" != (sClass) && !this.mNode.ObjectAdd(sObjPeer, "PG_CLASS_Peer", "", 65536)) {
                    this.OutString("pgExtVideo", "Peer Add failed");
                    return 1;
                }

                this.mNode.ObjectSetGroup(this.sObjVSelfHash, sObjPeer);
                this.mNode.ObjectSetGroup(this.sObjASelfHash, sObjPeer);
                var sPeer = sObjPeer.length > 100 ? sObjPeer.substr(5, 100) : sObjPeer.substr(5);
                var sObjVPeerHash = "EXT_VIDEO_" + sPeer;
                if (!this.mNode.ObjectAdd(sObjVPeerHash, "PG_CLASS_Video", sObjPeer, iFlagPeer)) {
                    this.OutString("pgExtVideo", "Peer Add failed");
                    return 1;
                }

                var sObjAPeerHash = "EXT_AUDIO_" + sPeer;
                if (!this.mNode.ObjectAdd(sObjAPeerHash, "PG_CLASS_Audio", sObjPeer, iFlagPeerA)) {
                    this.OutString("pgExtVideo", "Peer Add failed");
                    return 1;
                }

                this._ObjPeerListAdd(sObjPeer, sObjVPeerHash, sObjAPeerHash);
            }

            return 0;
        } else {
            return 2;
        }
    };

    this.stop = function(sObjPeer) {
        if (this._ObjPeerListExist(sObjPeer)) {
            var sOpenV = this._ObjPeerListGet(sObjPeer, "OpenV");
            if ("1" == (sOpenV)) {
                this.videoStop(sObjPeer, this.isStatePrvw);
            }

            var sOpenA = this._ObjPeerListGet(sObjPeer, "OpenA");
            if ("1" == (sOpenA)) {
                this.audioStop(sObjPeer);
            }

            this.mNode.ObjectSetGroup(this.sObjVSelfHash, "");
            this.mNode.ObjectSetGroup(this.sObjASelfHash, "");

            var sObjVPeerHash = this._ObjPeerListGet(sObjPeer, "ObjV");
            var sObjAPeerHash = this._ObjPeerListGet(sObjPeer, "ObjA");

            this.mNode.ObjectDelete(sObjAPeerHash);
            this.mNode.ObjectDelete(sObjVPeerHash);

            this._ObjPeerListDelete(sObjPeer);
        }

    };

    this._VideoOption = function(sParam) {
        if ("" == (sParam)) {
            return pgErrCode.PG_ERR_BadParam;
        }

        if (!this.mNode.ObjectAdd("_vTemp", "PG_CLASS_Video", "", 0)) {
            return 1;
        } else {
            var iPortrait = this.ParseInt(this.mNode.omlGetContent(sParam, "Portrait"), 0);
            if (iPortrait != 0) {
                this.mNode.ObjectRequest("_vTemp", 2, "(Item){2}(Value){90}", "");
            }

            var iCameraNo = this.ParseInt(this.mNode.omlGetContent(sParam, "CameraNo"), -1);
            if (iCameraNo >= 0) {
                var sData = "(Item){0}(Value){" + iCameraNo + "}";
                this.mNode.ObjectRequest("_vTemp", 2, sData, "VideoOption");
            }

            this.mNode.ObjectDelete("_vTemp");
            return 0;
        }
    };

    this.prvwStart = function() {
        if (!this.isStatePrvw) {
            if (!this.mNode.ObjectAdd("VPrvw", "PG_CLASS_Video", "", 2)) {
                this.OutString("Add 'Prvw' obj failed");
                return 1;
            }
            var sWndPrvw = "";
            if (this.mNode != null && this.mDivPrvw != "") {
                sWndPrvw = this.mNode.WndCreate(this.mDivPrvw);
            }

            var iMode = this.ParseInt(this.mNode.omlGetContent(this.mVideoParam, "Mode"), -1);
            if (iMode < 0 || iMode > 11) {
                return pgErrCode.PG_ERR_BadParam;
            }
            var iRate = this.ParseInt(this.mNode.omlGetContent(this.mVideoParam, "Rate"), 0);
            if (iRate < 0) {
                return pgErrCode.PG_ERR_BadParam;
            }

            var sWndRect = "(Code){0}(Mode){" + iMode + "}(Rate){" + iRate + "}(Wnd){" + sWndPrvw + "}";

            var uErr = this.mNode.ObjectRequest("VPrvw", 32, sWndRect, "EXT_SDK_PrvwStart");
            if (uErr > 0) {
                this.OutString("EXT_SDK,PrvwStart: Prvw, uErr=" + uErr);
                this.mNode.ObjectDelete("VPrvw");
                return uErr;
            }

            this.isStatePrvw = true;
        }

        return pgErrCode.PG_ERR_Normal;
    };

    this.prvwStop = function() {
        if (this.isStatePrvw) {

            this.mNode.ObjectRequest("VPrvw", 33, "", "EXT_SDK_PrvwStop");
            this.mNode.ObjectDelete("VPrvw");
            if (this.mNode != null && this.mDivPrvw != "") {
                this.mNode.WndDestroy(this.mDivPrvw);
            }
        }

        this.isStatePrvw = false;
    };

    this.videoStart = function(sObjPeer, peerView, bPrvw) {
        if (sObjPeer == null || sObjPeer.length <= 5 || peerView == null) {
            this.OutString("Param error : sObjPeer = " + sObjPeer + " peerView = " + peerView);
            return pgErrCode.PG_ERR_BadParam;
        }
        if (this._ObjPeerListExist(sObjPeer)) {

            var sWndEle = "";
            if (peerView != "") {
                sWndEle = this.mNode.WndCreate(peerView);
            }
            var sObjV = this._ObjPeerListGet(sObjPeer, "ObjV");

            var iCode = this.ParseInt(this.mNode.omlGetContent(this.mVideoParam, "Code"), -1);
            if (iCode < 0 || iCode > 4) {
                return pgErrCode.PG_ERR_BadParam;
            }
            var iMode = this.ParseInt(this.mNode.omlGetContent(this.mVideoParam, "Mode"), -1);
            if (iMode < 0 || iMode > 11) {
                return pgErrCode.PG_ERR_BadParam;
            }
            var iRate = this.ParseInt(this.mNode.omlGetContent(this.mVideoParam, "Rate"), 0);
            if (iRate < 0) {
                return pgErrCode.PG_ERR_BadParam;
            }

            var iBitRate = this.ParseInt(this.mNode.omlGetContent(this.mVideoParam, "BitRate"), 0);
            if (iBitRate > 0) {
                var sEncodeData = "(Code){" + iCode + "}(Mode){" + iMode + "}(BitRate){" + iBitRate + "}(FrmRate){0}(KeyFrmRate){0}(LossAllow){0}";
                var sDateBtiRate = "(Item){5}(Value){" + this.mNode.omlEncode(sEncodeData) + "}";
                this.mNode.ObjectRequest(sObjV, 2, sDateBtiRate, "ParamCodeMode");
            }

            var sSetData = "(Item){4}(Value){" + iRate + "}";
            this.mNode.ObjectRequest(sObjV, 2, sSetData, "SetFrmRate");

            if (bPrvw) {
                this.prvwStart();
            }

            var sWndRect = "(Code){" + iCode + "}(Mode){" + iMode + "}(Rate){" + iRate + "}(Wnd){" + sWndEle + "}";
            var uErr = this.mNode.ObjectRequest(sObjV, 32, sWndRect, "EXT_SDK_VIDEO_OPEN");
            if (uErr > 0) {
                this.OutString("EXT_SDK,StartVideo, Video, uErr=" + uErr);
            } else {
                this._ObjPeerListSet(sObjPeer, "OpenV", "1");
            }

            return uErr;

        }
        return pgErrCode.PG_ERR_NoExist;
    };

    this.videoHandle = function(sObjV, uErrCode, uHandle, sObjPeer, peerView, bPrvw) {
        if ("" == (sObjV) || uHandle <= 0) {
            this.OutString("EXT_SDK : videoHandle : sObj = " + sObjV + " uHandle = " + uHandle);
            return pgErrCode.PG_ERR_BadParam;
        }
        if (this._ObjPeerListExist(sObjPeer)) {

            var sWndRect = "";
            if (uErrCode <= 0) {


                var sWndEle = "";
                if (peerView != "") {
                    sWndEle = this.mNode.WndCreate(peerView);
                }
                var iCode = this.ParseInt(this.mNode.omlGetContent(this.mVideoParam, "Code"), -1);
                if (iCode < 0 || iCode > 4) {
                    return PG_ERR_BadParam;
                }
                var iMode = this.ParseInt(this.mNode.omlGetContent(this.mVideoParam, "Mode"), -1);
                if (iMode < 0 || iMode > 11) {
                    return PG_ERR_BadParam;
                }
                var iRate = this.ParseInt(this.mNode.omlGetContent(this.mVideoParam, "Rate"), 0);
                if (iRate < 0) {
                    return PG_ERR_BadParam;
                }

                var iBitRate = this.ParseInt(this.mNode.omlGetContent(this.mVideoParam, "BitRate"), 0);
                if (iBitRate > 0) {
                    var sEncodeData = "(Code){" + iCode + "}(Mode){" + iMode + "}(BitRate){" + iBitRate + "}(FrmRate){0}(KeyFrmRate){0}(LossAllow){0}";
                    var sDateBtiRate = "(Item){5}(Value){" + this.mNode.omlEncode(sEncodeData) + "}";
                    this.mNode.ObjectRequest(sObjV, 2, sDateBtiRate, "ParamCodeMode");
                }

                if (bPrvw) {
                    this.prvwStart();
                }

                sWndRect = "(Code){" + iCode + "}(Mode){" + iMode + "}(Rate){" + iRate + "}(Wnd){" + sWndEle + "}";
            }

            var iErr = this.mNode.ObjectExtReply(sObjV, uErrCode, sWndRect, uHandle);
            if (iErr > 0) {
                this.mNode.WndDestroy(peerView);
                this.OutString("EXT_SDK : VideoHandle : ObjectExtReply iErr = " + iErr);

            } else if (uErrCode <= 0) {
                this._ObjPeerListSet(sObjPeer, "OpenV", "1");
                this._ObjPeerListSet(sObjPeer, "Div", peerView);
            }

            return iErr;
        }

    };

    this.videoStop = function(sObjPeer, bPrvw) {
        if (this._ObjPeerListExist(sObjPeer)) {
            var sObjV = this._ObjPeerListGet(sObjPeer, "ObjV");
            var divVideo = this._ObjPeerListGet(sObjPeer, "Div");
            this.mNode.ObjectRequest(sObjV, 33, "", "EXT_SDK_VIDEO_CLOSE");
            if (bPrvw) {
                this.prvwStop();
            }
            this.mNode.WndDestroy(divVideo);
            this._ObjPeerListSet(sObjPeer, "OpenV", "0");
            this._ObjPeerListSet(sObjPeer, "Div", "");
        }

    };

    this.audioStart = function(sObjPeer) {
        if (sObjPeer != null && "" != (sObjPeer)) {
            if (!this._ObjPeerListExist(sObjPeer)) {
                return 18;
            } else {
                var sObjA = this._ObjPeerListGet(sObjPeer, "ObjA");
                var iCode = this.ParseInt(this.mNode.omlGetContent(this.mAudioParam, "Code"), 0);
                if (iCode < 0 || iCode > 3) {
                    iCode = 0;
                }

                var iMode = this.ParseInt(this.mNode.omlGetContent(this.mAudioParam, "Code"), 0);
                if (iMode < 0 || iMode > 1) {
                    iMode = 0;
                }

                var sWndRect = "(Code){" + iCode + "}(Mode){" + iMode + "}";
                var uErr = this.mNode.ObjectRequest(sObjA, 32, sWndRect, "EXT_SDK_AUDIO_OPEN");
                if (uErr > 0) {
                    this.OutString("EXT_SDK,audioOpen, Video, uErr=" + uErr);
                } else {
                    this._ObjPeerListSet(sObjPeer, "OpenA", "1");
                }

                return uErr;
            }
        } else {
            this.OutString("Param error : sObjPeer = " + sObjPeer);
            return 2;
        }
    };

    this.audioHandle = function(sObjA, uErrCode, uHandle, sObjPeer) {
        if (sObjA != null && uHandle >= 0) {
            if (!this._ObjPeerListExist(sObjPeer)) {
                this.mNode.ObjectExtReply(sObjA, 13, "", uHandle);
                return 18;
            } else {
                var iCode = this.ParseInt(this.mNode.omlGetContent(this.mVideoParam, "Code"), 0);
                if (iCode < 0 || iCode > 3) {
                    iCode = 0;
                }

                var iMode = this.ParseInt(this.mNode.omlGetContent(this.mVideoParam, "Mode"), 0);
                if (iMode < 0 || iMode > 1) {
                    iMode = 0;
                }

                var sWndRect = "(Code){" + iCode + "}(Mode){" + iMode + "}";
                var iErr = this.mNode.ObjectExtReply(sObjA, uErrCode, sWndRect, uHandle);
                if (iErr > 0) {
                    this.OutString("VideoHandle ObjectExtReply iErr = " + iErr);
                } else {
                    this._ObjPeerListSet(sObjPeer, "OpenA", "1");
                }

                return iErr;
            }
        } else {
            return 2;
        }
    };

    this.audioStop = function(sObjPeer) {
        if (this._ObjPeerListExist(sObjPeer)) {
            var sObjA = this._ObjPeerListGet(sObjPeer, "ObjA");
            this.mNode.ObjectRequest(sObjA, 33, "", "EXT_SDK_AUDIO_CLOSE");
            this._ObjPeerListSet(sObjPeer, "OpenA", "0");
        }

    };

    this.svrRequest = function(sData, sParam) {
        var sDataReq = "1024:" + sData;
        var sParamReq = "EXT_SVR_REQ:" + sParam;
        var iErr = this.mNode.ObjectRequest("pgConnectSvr", 35, sDataReq, sParamReq);
        if (iErr > 0) {
            this.OutString("pgLibLiveMultiRender.SvrRequest: iErr=" + iErr);
        }

        return iErr;
    };

    this._OnTimeout = function(sParam) {
        this.OutString(sParam);
        this.mNode.omlGetContent(sParam, "Act");
    };

    this.onSvrMessage = function(sData, sPeer) {
        var sCmd = "";
        var sParam = "";
        var iInd = sData.indexOf("?");
        if (iInd > 0) {
            sCmd = sData.substr(0, iInd);
            sParam = sData.substr(iInd + 1);
        } else {
            sParam = sData;
        }

        if ("UserExtend" == (sCmd)) {
            this.mNode.omlGetContent(sParam, "User");
            var sMsg = this.mNode.omlGetContent(sParam, "Msg");
            var sMsgEle = this.mNode.omlDecode(sMsg);
            var sCmdRemote = this.mNode.omlGetContent(sMsgEle, "CMD");
            var sObjPeer = this.mNode.omlGetContent(sMsgEle, "ObjPeer");
            var sParamRemote = this.mNode.omlGetContent(sMsgEle, "Param");
            if (sCmdRemote == ("VCMD_GetObjPeer:")) {
                if (this.mOnEvent.onGetObjPeer && typeof(this.mOnEvent.onGetObjPeerNotify) == "function") {
                    this.mOnEvent.onGetObjPeer(sObjPeer, sParamRemote);
                }

                return pgErrCode.PG_ERR_Normal;
            } else if (sCmdRemote == ("VCMD_GetObjPeerNotify:")) {
                if (this.mOnEvent.onGetObjPeer && typeof(this.mOnEvent.onGetObjPeerNotify) == "function") {
                    this.mOnEvent.onGetObjPeerNotify(sObjPeer, sParam);
                }
                return pgErrCode.PG_ERR_Normal;
            } else {
                return pgErrCode.PG_ERR_Unknown;
            }
        } else {
            return pgErrCode.PG_ERR_Unknown;
        }
    };

    this._ObjPeerListSearch = function(sObjPeer) {
        var sPath = "\n*" + sObjPeer;
        return this.mNode.omlGetEle(this.mListObjPeer, sPath, 1, 0);
    };

    this._ObjPeerListAdd = function(sObjPeer, sObjV, sObjA) {
        var sCapture = this._ObjPeerListSearch(sObjPeer);
        if ("" == (sCapture)) {
            this.mListObjPeer = this.mListObjPeer + "(" + this.mNode.omlEncode(sObjPeer) + "){" +
                "(ObjV){" + this.mNode.omlEncode(sObjV) + "}" +
                "(OpenV){0}" +
                "(ObjA){" + this.mNode.omlEncode(sObjA) + "}" +
                "(OpenA){0}" +
                "(Div){}" +
                "}";
        }

    };

    this._ObjPeerListDelete = function(sObjPeer) {
        var sCapture = this._ObjPeerListSearch(sObjPeer);
        if ("" != (sCapture)) {
            var sPath = "\n*" + sObjPeer;
            this.mListObjPeer = this.mNode.omlDeleteEle(this.mListObjPeer, sPath, 1, 0);
        }

    };

    this._ObjPeerListSet = function(sObjPeer, sItem, sValue) {
        var sCapture = this._ObjPeerListSearch(sObjPeer);
        if ("" != (sCapture)) {
            var sPath = "\n*" + sObjPeer + "*" + sItem;
            this.mListObjPeer = this.mNode.omlSetContent(this.mListObjPeer, sPath, sValue);
            return true;
        } else {
            return false;
        }
    };

    this._ObjPeerListGet = function(sObjPeer, sItem) {
        var sPath = "\n*" + sObjPeer + "*" + sItem;
        return this.mNode.omlGetContent(this.mListObjPeer, sPath);
    };

    this._ObjPeerListExist = function(sObjPeer) {
        var sCapture = this._ObjPeerListSearch(sObjPeer);
        return "" != (sCapture);
    };

    this.OutString = function(sOut) {
        console.log("ExtVideo : " + sOut);
    };
    this.ParseInt = function(sVal, idefVal) {
        try {
            if (sVal != "") {
                return parseInt(sVal);
            }
            return idefVal;
        } catch (e) {
            return idefVal;
        }
    };
}