////////////////////////////////////////////////////
var __IP_PORT__ = "127.0.0.1:17885";
var __WS__URL__ = "ws://" + __IP_PORT__;
var __WS_PARAM__ = "peergine-node";

var __ACTION_URL__ = "http://" + __IP_PORT__ + "/peergine.do";
var m_NodeList = new Array();
var ws; //websocket实例
var lockReconnect = false; //避免重复连接
var bCreateWSed = false;

var __GENGRATE_VIEW_ID = 1;

function generateViewID() {
    var ID = 1;
    for (var i = 1; i < 32; i++) {
        if ((0x1 & (__GENGRATE_VIEW_ID >> i)) == 0) {
            ID = i;
            __GENGRATE_VIEW_ID += (0x1 << i);
            break;
        }
    }
    return ID;
}

function destroyViewID(iViewID) {
    __GENGRATE_VIEW_ID = __GENGRATE_VIEW_ID - (0x1 << iViewID);
}

function FindNode(sSession) {
    for (var i = 0; i < m_NodeList.length; i++) {
        if (m_NodeList[i].sSession == sSession) {
            return m_NodeList[i];
        }
    }
    return null;
}

function createWebSocket(url, param) {
    if (bCreateWSed == false) {
        try {
            ws = new WebSocket(url, param);
            initEventHandle();
        } catch (e) {
            reconnect(url);
        }
    }
    bCreateWSed = true;
}

function initEventHandle() {
    ws.onclose = function() {
        reconnect(__WS__URL__, __WS_PARAM__);
        console.log("onclose");
    };

    ws.onerror = function() {
        reconnect(__WS__URL__, __WS_PARAM__);
        console.log("onerror");
    };

    ws.onopen = function() {
        //心跳检测重置
        heartCheck.reset();
        console.log("onopen");

        //设置所有Node对Ws的关联
        for (var i = 0; i < m_NodeList.length; i++) {
            var sSessionData = "sSession:" + m_NodeList[i].sSession;
            try {
                ws.send(sSessionData);
            } catch (error) {
                console.error("error = " + error);
                window.setTimeout(function() { TimeoutWSSendSession(sSessionData); }, 100);
            }
        }
    };

    ws.onmessage = function(msg) {
        // 如果获取到消息，心跳检测重置
        // 拿到任何消息都说明当前连接是正常的

        // console.log("msg = " + msg.data);
        heartCheck.reset();

        if (msg.data != "HeartBeat") {

            //console.log("msg = " + msg.data);
            var strjs = decodeURIComponent(msg.data);

            //console.log("string js = " + strjs);
            var js = JSON.parse(strjs);
            var node = FindNode(js.sSession);
            var sObj;
            var sData;
            if (node != null) {
                if (js.OnFun == "OnExtRequest") {
                    if (node.OnExtRequest != null) {

                        sObj = js.sObj;
                        var uMeth = js.uMeth;
                        sData = js.sData;
                        var uHandle = js.uHandle;
                        var sPeer = js.sPeer;

                        console.log("sObj: " + sObj + " uMeth: " + uMeth + " sData: " + sData + " uHandle: " + uHandle + " sPeer: " + sPeer);
                        node.OnExtRequest(sObj, uMeth, sData, uHandle, sPeer);
                    }
                } else if (js.OnFun == ("OnReply")) {
                    if (node.OnReply != null) {

                        sObj = js.sObj;
                        var uErr = js.uErr;
                        sData = js.sData;
                        var sParam = js.sParam;

                        console.log("sObj: " + sObj + " uErr: " + uErr + " sData: " + sData + " sParam: " + sParam);
                        node.OnReply(sObj, uErr, sData, sParam);
                    }
                } else {
                    console.log("string js = " + strjs);
                }
            } else {
                console.error("can't found node sSession error");
            }
        } else {
            console.log("HeartBeat reply!");
        }
    };
}

//private
function reconnect(url, param) {
    if (lockReconnect) {
        return;
    }

    lockReconnect = true;

    // 没连接上会一直重连，设置延迟避免请求过多
    setTimeout(function() {
        createWebSocket(url, param);
        lockReconnect = false;
    }, 2000);
}


//心跳检测
//private
var heartCheck = {

    timeout: 5000, // 心跳间隔5秒
    serverTimeout: 8000, // 等待服务器响应8秒

    timeoutObj: null,
    serverTimeoutObj: null,

    reset: function() {
        clearTimeout(heartCheck.timeoutObj);
        clearTimeout(heartCheck.serverTimeoutObj);
        heartCheck.start();
    },

    start: function() {
        heartCheck.timeoutObj = setTimeout("heartCheck.send()", heartCheck.timeout);
    },

    send: function() {
        // 这里发送一个心跳，服务器端收到后，返回一个心跳应答，
        // onmessage 接收到返回的心跳应答就说明连接正常
        ws.send("HeartBeat");
        heartCheck.serverTimeoutObj = setTimeout("heartCheck.sendTimeout()", heartCheck.serverTimeout);
    },

    sendTimeout: function() {
        // 如果onclose会执行reconnect，我们执行ws.close()就行了.
        // 如果直接执行reconnect 会触发onclose导致重连两次
        ws.close();
    }
};


// private
createWebSocket(__WS__URL__, __WS_PARAM__);

// private
function TimeoutWSSendSession(sData) {
    try {
        ws.send(sData);
    } catch (error) {
        console.error("error = " + error);
        window.setTimeout(function() { TimeoutWSSendSession(sData); }, 100);
    }
}


function pgLibJSNode(sSession) {

    //public
    this.Control = "";
    this.Node = "";
    this.Class = "";
    this.Local = "";
    this.Server = "";
    this.Relay = "";
    this.OnExtRequest = null;
    this.OnReply = null;

    this.sSession = sSession;
    m_NodeList.push(this);

    createWebSocket(__WS__URL__, __WS_PARAM__);

    /*
     * @return {int}
     */
    this.ObjectAdd = function(sObject, sClass, sGroup, uFlag) {
        var sParamJson = {
            "sObject": sObject,
            "sClass": sClass,
            "sGroup": sGroup,
            "uFlag": uFlag
        };
        var sParam1 = "method=ObjectAdd" +
            "&sSession=" + this.sSession +
            "&sParam=" + encodeURIComponent(JSON.stringify(sParamJson)); //encode64(JSON.stringify(sParamJson));
        return GetFunRes(__ACTION_URL__, sParam1);
    };


    /*
     * @return {void}
     */
    this.ObjectDelete = function(sObject) {
        var sParamJson = {
            "sObject": sObject,
        };
        var sParam1 = "method=ObjectDelete" +
            "&sSession=" + this.sSession +
            "&sParam=" + encodeURIComponent(JSON.stringify(sParamJson)); //encode64(JSON.stringify(sParamJson));

        return GetFunRes(__ACTION_URL__, sParam1);
    };


    /*
     * @return {String}
     */
    this.ObjectGetClass = function(sObject) {
        var sParamJson = {
            "sObject": sObject,
        };
        var sParam1 = "method=ObjectGetClass" +
            "&sSession=" + this.sSession +
            "&sParam=" + encodeURIComponent(JSON.stringify(sParamJson)); //encode64(JSON.stringify(sParamJson));
        return GetFunRes(__ACTION_URL__, sParam1);
    };


    /*
     * @return {int}
     */
    this.ObjectSetGroup = function(sObject, sGroup) {
        var sParamJson = {
            "sObject": sObject,
            "sGroup": sGroup
        };
        var sParam1 = "method=ObjectSetGroup" +
            "&sSession=" + this.sSession +
            "&sParam=" + encodeURIComponent(JSON.stringify(sParamJson)); //encode64(JSON.stringify(sParamJson));
        return GetFunRes(__ACTION_URL__, sParam1);
    };


    /*
     * @return {String}
     */
    this.ObjectGetGroup = function(sObject) {
        var sParamJson = {
            "sObject": sObject,
        };
        var sParam1 = "method=ObjectGetGroup" +
            "&sSession=" + this.sSession +
            "&sParam=" + encodeURIComponent(JSON.stringify(sParamJson)); //encode64(JSON.stringify(sParamJson));

        return GetFunRes(__ACTION_URL__, sParam1);
    };


    /*
     * @return {int}
     */
    this.ObjectSync = function(sObject, sPeer, uAction) {

        var sParamJson = {
            "sObject": sObject,
            "sPeer": sPeer,
            "uAction": uAction
        };
        var sParam1 = "method=ObjectSync" +
            "&sSession=" + this.sSession +
            "&sParam=" + encodeURIComponent(JSON.stringify(sParamJson)); //encode64(JSON.stringify(sParamJson));
        return GetFunRes(__ACTION_URL__, sParam1);
    };


    /*
     * @return {int}
     */
    this.ObjectRequest = function(sObject, uMeth, sIn, sParam) {
        var sParamJson = {
            "sObject": sObject,
            "uMeth": uMeth,
            "sIn": sIn,
            "sParam": sParam
        };
        var sParam1 = "method=ObjectRequest" +
            "&sSession=" + this.sSession +
            "&sParam=" + encodeURIComponent(JSON.stringify(sParamJson)); //encode64(JSON.stringify(sParamJson));



        return GetFunRes(__ACTION_URL__, sParam1);
    };



    /*
     * @return {int}
     */
    this.ObjectExtReply = function(sObject, uErr, sOut, uHandle) {
        var sParamJson = {
            "sObject": sObject,
            "uErr": uErr,
            "sOut": sOut,
            "uHandle": uHandle
        };
        var sParam1 = "method=ObjectExtReply" +
            "&sSession=" + this.sSession +
            "&sParam=" + encodeURIComponent(JSON.stringify(sParamJson)); //encode64(JSON.stringify(sParamJson));

        return GetFunRes(__ACTION_URL__, sParam1);
    };


    /*
     * @return {String}
     */
    this.ObjectEnum = function(sObject, sClass) {

        var sParamJson = {
            "sObject": sObject,
            "sClass": sClass
        };
        var sParam1 = "method=ObjectEnum" +
            "&sSession=" + this.sSession +
            "&sParam=" + encodeURIComponent(JSON.stringify(sParamJson)); //encode64(JSON.stringify(sParamJson));
        return GetFunRes(__ACTION_URL__, sParam1);
    };

    /*
     * @return {String}
     */
    this.PostMessage = function(sMsg) {

        var sParamJson = {
            "sMsg": sMsg
        };
        var sParam1 = "method=ObjectEnum" +
            "&sSession=" + this.sSession +
            "&sParam=" + encodeURIComponent(JSON.stringify(sParamJson)); //encode64(JSON.stringify(sParamJson));
        return GetFunRes(__ACTION_URL__, sParam1);
    };


    /*
     * @return {int}
     */
    this.Start = function(uOption) {
        var sSessionData = "sSession:" + this.sSession;

        window.setTimeout(function() { TimeoutWSSendSession(sSessionData); }, 100);

        var sParamJson = {
            "uOption": uOption,
            "Control": this.Control,
            "Node": this.Node,
            "Class": this.Class,
            "Local": this.Local,
            "Server": this.Server,
            "Relay": this.Relay,
        };
        var sParam1 = "method=Start" +
            "&sSession=" + this.sSession +
            "&sParam=" + encodeURIComponent(JSON.stringify(sParamJson)); //encode64(JSON.stringify(sParamJson));
        return GetFunRes(__ACTION_URL__, sParam1);


    };



    /*
     * @return {void}
     */
    this.Stop = function() {

        var sParamJson = {

        };
        var sParam1 = "method=Stop" +
            "&sSession=" + this.sSession +
            "&sParam=" + encodeURIComponent(JSON.stringify(sParamJson)); //encode64(JSON.stringify(sParamJson));
        GetFunRes(__ACTION_URL__, sParam1);
    };


    /*
     * @return {String}
     */
    this.omlEncode = function(sEle) {
        var sParamJson = {
            "sEle": sEle,
        };
        var sParam1 = "method=omlEncode" +
            "&sSession=" + this.sSession +
            "&sParam=" + encodeURIComponent(JSON.stringify(sParamJson)); //encode64(JSON.stringify(sParamJson));

        return GetFunRes(__ACTION_URL__, sParam1);
    };


    /*
     * @return {String}
     */
    this.omlDecode = function(sEle) {
        var sParamJson = {
            "sEle": sEle,
        };
        var sParam1 = "method=omlDecode" +
            "&sSession=" + this.sSession +
            "&sParam=" + encodeURIComponent(JSON.stringify(sParamJson)); //encode64(JSON.stringify(sParamJson));
        return GetFunRes(__ACTION_URL__, sParam1);
    };


    /*
     * @return {String}
     */
    this.omlSetName = function(sEle, sPath, sName) {
        var sParamJson = {
            "sEle": sEle,
            "sPath": sPath,
            "sName": sName,
        };
        var sParam1 = "method=omlSetName" +
            "&sSession=" + this.sSession +
            "&sParam=" + encodeURIComponent(JSON.stringify(sParamJson)); //encode64(JSON.stringify(sParamJson));


        return GetFunRes(__ACTION_URL__, sParam1);
    };


    /*
     * @return {String}
     */
    this.omlSetClass = function(sEle, sPath, sClass) {
        var sParamJson = {
            "sEle": sEle,
            "sPath": sPath,
            "sClass": sClass,
        };
        var sParam1 = "method=omlSetClass" +
            "&sSession=" + this.sSession +
            "&sParam=" + encodeURIComponent(JSON.stringify(sParamJson)); //encode64(JSON.stringify(sParamJson));
        return GetFunRes(__ACTION_URL__, sParam1);
    };


    /*
     * @return {String}
     */
    this.omlSetContent = function(sEle, sPath, sContent) {
        var sParamJson = {
            "sEle": sEle,
            "sPath": sPath,
            "sContent": sContent,
        };
        var sParam1 = "method=omlSetContent" +
            "&sSession=" + this.sSession +
            "&sParam=" + encodeURIComponent(JSON.stringify(sParamJson)); //encode64(JSON.stringify(sParamJson));

        return GetFunRes(__ACTION_URL__, sParam1);
    };


    /*
     * @return {String}
     */
    this.omlNewEle = function(sName, sClass, sContent) {
        var sParamJson = {
            "sName": sName,
            "sClass": sClass,
            "sContent": sContent,
        };
        var sParam1 = "method=omlNewEle" +
            "&sSession=" + this.sSession +
            "&sParam=" + encodeURIComponent(JSON.stringify(sParamJson)); //encode64(JSON.stringify(sParamJson));
        return GetFunRes(__ACTION_URL__, sParam1);
    };


    /*
     * @return {String}
     */
    this.omlGetEle = function(sEle, sPath, uSize, uPos) {
        var sParamJson = {
            "sEle": sEle,
            "sPath": sPath,
            "uSize": uSize,
            "uPos": uPos,
        };
        var sParam1 = "method=omlGetEle" +
            "&sSession=" + this.sSession +
            "&sParam=" + encodeURIComponent(JSON.stringify(sParamJson)); //encode64(JSON.stringify(sParamJson));
        return GetFunRes(__ACTION_URL__, sParam1);
    };


    /*
     * @return {String}
     */
    this.omlDeleteEle = function(sEle, sPath, uSize, uPos) {
        var sParamJson = {
            "sEle": sEle,
            "sPath": sPath,
            "uSize": uSize,
            "uPos": uPos,
        };
        var sParam1 = "method=omlDeleteEle" +
            "&sSession=" + this.sSession +
            "&sParam=" + encodeURIComponent(JSON.stringify(sParamJson)); //encode64(JSON.stringify(sParamJson));
        return GetFunRes(__ACTION_URL__, sParam1);
    };

    /*
     * @return {String}
     */
    this.omlGetName = function(sEle, sPath) {
        var sParamJson = {
            "sEle": sEle,
            "sPath": sPath,
        };
        var sParam1 = "method=omlGetName" +
            "&sSession=" + this.sSession +
            "&sParam=" + encodeURIComponent(JSON.stringify(sParamJson)); //encode64(JSON.stringify(sParamJson));

        return GetFunRes(__ACTION_URL__, sParam1);
    };


    /*
     * @return {String}
     */
    this.omlGetClass = function(sEle, sPath) {
        var sParamJson = {
            "sEle": sEle,
            "sPath": sPath,
        };
        var sParam1 = "method=omlGetClass" +
            "&sSession=" + this.sSession +
            "&sParam=" + encodeURIComponent(JSON.stringify(sParamJson)); //encode64(JSON.stringify(sParamJson));

        return GetFunRes(__ACTION_URL__, sParam1);
    };

    /*
     * @return {String}
     */
    this.omlGetContent = function(sEle, sPath) {
        var sParamJson = {
            "sEle": sEle,
            "sPath": sPath,
        };
        var sParam1 = "method=omlGetContent" +
            "&sSession=" + this.sSession +
            "&sParam=" + encodeURIComponent(JSON.stringify(sParamJson)); //encode64(JSON.stringify(sParamJson));

        return GetFunRes(__ACTION_URL__, sParam1);
    };


    /*
     * @return {String}
     
    this.utilGetWndRect = function(imgDivID) {
        var odiv = document.getElementById(imgDivID);
        if (odiv == undefined) {
            return "";
        }
        var h = odiv.offsetHeight; //高度
        var w = odiv.offsetWidth; //宽度
        var iGViewID = generateViewID();
        var iNewViewID = (this.sSession >> 16) | (iGViewID << 16);
        var sWnd = "(Wnd){(PosX){0}(PosY){0}(SizeX){" + w + "}(SizeY){" + h + "}(Handle){" + iNewViewID + "}}";

        var pgview = new _PG_VIEW();
        pgview.odiv = odiv;
        pgview.GViewID = iGViewID;
        pgview.RViewID = iNewViewID;
        pgview.m_Node = this;
        _pgView._PG_VIEW_LIST.push(pgview);

        return sWnd;
    },
    */


    /*
     * @return {String}
     */
    this.WndCreate = function(divId) {

        var oDiv = document.getElementById(divId);
        if (oDiv != null) {
            console.log("divID = " + divId);
            var GViewID = -1;
            var oImg = document.getElementById(divId + "_img");
            if (oImg == null) {
                oImg = document.createElement("img");
                oDiv.appendChild(oImg);
                GViewID = generateViewID();
                oDiv.setAttribute("ViewID", GViewID);
                oDiv.setAttribute("NodeSess", this.sSession);
                oDiv.setAttribute("Count", 0);

                oImg.id = divId + "_img";
                oImg.style = "width:100%;height:100%;margin:0px;display:block;overflow:hidden;";
                oImg.setAttribute("onload", "_pgView.imgonload('" + divId + "')");
                oImg.setAttribute("onerror", "_pgView.imgonerror('" + divId + "')");
                window.setTimeout(function() { _pgView.imgload(divId); }, 10);
            } else {
                GViewID = oDiv.getAttribute("ViewID");
            }

            var RViewID = (this.sSession >> 16) | (GViewID << 16);

            // var oview = new _PG_VIEW();
            // oview.divid = divId;
            // oview.odiv = oDiv
            // oview.sizeX = sizeX;
            // oview.sizeY = sizeY;
            // oview.oimg = oImg;
            // oview.GViewID = GViewID;
            // oview.RViewID = RViewID;
            // oview.m_Node = this;
            // oview.iCount = 0;
            // _pgView._PG_VIEW_LIST[i] = oview;

            var sWnd = "(PosX){0}(PosY){0}(SizeX){" + 320 + "}(SizeY){" + 240 + "}(Handle){" + RViewID + "}";
            console.log("sWnd = " + sWnd);
            return sWnd;
        }
        return "";
    };


    /*
     * @return {null}
     */
    this.WndDestroy = function(divId) {
        var oDiv = document.getElementById(divId);
        if (oDiv != null) {
            var oImg = document.getElementById(divId + "_img");
            if (oImg != null) {
                oImg.id = "";
                oDiv.removeChild(oImg);
                destroyViewID(oDiv.getAttribute("ViewID"));
            }
        }
    };


    /*
     * @return {String}
     */
    this.utilCmd = function(sCmd, sParam) {
        var sParamJson = {
            "sCmd": sCmd,
            "sParam": sParam,
        };
        var sParam1 = "method=utilCmd" +
            "&sSession=" + this.sSession +
            "&sParam=" + encodeURIComponent(JSON.stringify(sParamJson)); //encode64(JSON.stringify(sParamJson));

        return GetFunRes(__ACTION_URL__, sParam1);
    };
}

/*
 *@return {Object}
 */
function pgNewJSNode(sSession) {
    var random = parseInt(0xffff * Math.random());
    var tmp = (sSession << 16) | random;

    var sParam = "method=pgNewJSNode&sSession=" + tmp;
    var sSession1 = GetFunRes(__ACTION_URL__, sParam);

    var node = new pgLibJSNode();
    node.sSession = sSession1;
    return node;
}

/*
 *@return {Object}
 */
function pgDeleteJSNode(Node) {
    if (Node != null && typeof(Node.sSession) != "undefined") {
        var sParam = "method=pgDeleteJSNode&sSession=" + Node.sSession;
        return GetFunRes(__ACTION_URL__, sParam);
    }
    return "";
}

/**
 * prive
 */
function GetXmlHttpObject() {
    var xmlHttp = null;
    try {
        // Firefox, Opera 8.0+, Safari  
        xmlHttp = new XMLHttpRequest();
    } catch (e) {
        // Internet Explorer  
        try {
            xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
        } catch (e) {
            xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
    }
    return xmlHttp;
}

function GetFunRes(sUrl, sParam) {

    var xmlhttp = GetXmlHttpObject();
    xmlhttp.open("POST", sUrl, false);
    xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
    //console.log("GetText URL = " + sUrl + " Param = " + sParam)
    try {
        xmlhttp.send(sParam);
    } catch (error) {
        console.error("xmlhttp.send = " + error);
    }

    var ret = xmlhttp.responseText;
    ////console.log("GetText RET = " + ret)
    return ret;
}

//第一个参数是当前对象this，第二个是宽，第三个是高
//private
function datuIMG(datu, kuan, chang) {
    datu.width = kuan;
    datu.height = chang;
    datu.style.padding = "0px";


    datu.width = kuan * 100;
    datu.height = chang * 100;
    //图片等比例缩小
    if (datu.width * chang > datu.height * kuan) {
        datu.width = kuan;
        datu.height = (kuan * chang) / kuan;
    } else {
        datu.width = (chang * kuan) / chang;
        datu.height = chang;
    }
    //图片居中显示
    //图片宽小于设定的边框宽
    if (datu.width <= kuan) {
        var kk = parseInt((kuan - datu.width) / 2);
        datu.style.paddingRight = kk + "px";
        datu.style.paddingLeft = kk + "px";
    }
    //图片高小于设定的边框高
    if (datu.height <= chang) {
        var gg = parseInt((chang - datu.height) / 2);
        datu.style.paddingTop = gg + "px";
        datu.style.paddingBottom = gg + "px";
    }

}

//private
function AutoResizeImage(maxWidth, maxHeight, objImg) {
    var img = new Image();
    img.src = objImg.src;
    var hRatio;
    var wRatio;
    var Ratio = 1;
    var w = img.width;
    var h = img.height;
    wRatio = maxWidth / w;
    hRatio = maxHeight / h;
    if (maxWidth == 0 && maxHeight == 0) {
        Ratio = 1;
    } else if (maxWidth == 0) { //
        if (hRatio < 1) Ratio = hRatio;
    } else if (maxHeight == 0) {
        if (wRatio < 1) Ratio = wRatio;
    } else if (wRatio < 1 || hRatio < 1) {
        Ratio = (wRatio <= hRatio ? wRatio : hRatio);
    }
    if (Ratio < 1) {
        w = w * Ratio;
        h = h * Ratio;
    }
    objImg.height = h;
    objImg.width = w;
}
//private
function _PG_VIEW() {
    var divid = null;
    var odiv = null;
    var oimg = null;
    var GViewID = -1;
    var RViewID = -1;
    var m_Node = null;
    var iCount = 0;
}

//private
var _pgView = {
    url: "http://" + __IP_PORT__ + "/video.do",
    _PG_VIEW_LIST: new Array(),

    imgload: function(divid) {
        var oDiv = document.getElementById(divid);

        if (oDiv) {
            var oImg = document.getElementById(divid + "_img");
            var iViewID = oDiv.getAttribute("ViewID");
            var sSession = oDiv.getAttribute("NodeSess");
            var iCount = oDiv.getAttribute("Count");
            // var sSession = oDiv.NodeSess;
            // var iViewID = oDiv.ViewID;
            // var iCount = oDiv.Count;
            //console.log()
            if (oImg) {
                oImg.src = _pgView.url + "/Session" + sSession + "/ViewID" + iViewID + "/Count" + iCount;
            }
        }
    },

    imgonload: function(divid) {
        var oDiv = document.getElementById(divid);

        if (oDiv) {
            var oImg = document.getElementById(divid + "_img");
            if (oImg) {
                datuIMG(oImg, oDiv.offsetWidth, oDiv.offsetHeight);
            }


            oDiv.setAttribute("Count", (parseInt(oDiv.getAttribute("Count")) + 1));
            window.setTimeout(function() { _pgView.imgload(divid); }, 1);
        }
    },

    imgonerror: function(divid) {
        var oDiv = document.getElementById(divid);

        if (oDiv) {
            var oImg = document.getElementById(divid + "_img");
            if (oImg) {
                datuIMG(oImg, oDiv.offsetWidth, oDiv.offsetHeight);
            }

            oDiv.setAttribute("Count", (parseInt(oDiv.getAttribute("Count")) + 1));
            window.setTimeout(function() { _pgView.imgload(divid); }, 1);
        }
        return false;
    }
};