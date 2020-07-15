<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf8">
    <title>Live播放端</title>
    <style>
        body,
        td {
            font-size: 12px;
        }
    </style>
    <script language="javascript" src="${path}/js/pgHtml5Bridge.js"></script>
    <script language="javascript" src="${path}/js/pgLibExtVideo.js"></script>
    <script language="javascript" src="${path}/js/pgLibLiveMultiError.js"></script>
    <script language="javascript" src="${path}/js/pgLibLiveMultiRender.js"></script>

</head>

<body onload="pgInitialize()" onbeforeunload="pgClean()">
    <table width="640" border="0">

        <tr>
            <td style="padding:3px">
                <span>登录ID: </span>
                <input id="id_SelfID" type="text" value="test" />&nbsp;
                <!-- <input id="id_Init" type="button" value="初始化" onclick="pgInit()">&nbsp;&nbsp;&nbsp;&nbsp;-->
                <span>设备ID: </span>
                <input id="id_CaptureID" type="text" value='<%=request.getParameter("eno") %>' />
                <input id="id_Ctrl" type="button" value="开始" onclick="pgCtrl()">&nbsp;&nbsp;&nbsp;&nbsp;
                <!-- <input type="button" value="整幅" onclick="pgVideoShowMode(1)">&nbsp;
                <input type="button" value="裁剪" onclick="pgVideoShowMode(0)">&nbsp;-->
				<br/><br/>
                <input type="button" value="录像开始" onclick="pgVideoRecored(true)">&nbsp;
                <input type="button" value="录像停止" onclick="pgVideoRecored(false)">&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" value="静音启用" onclick="pgAudioMute(true)">&nbsp;
                <input type="button" value="静音取消" onclick="pgAudioMute(false)">&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" value="启用音频" onclick="pgAudioSpeech(true)">&nbsp;
                <input type="button" value="禁用音频" onclick="pgAudioSpeech(false)">&nbsp;&nbsp;&nbsp;&nbsp;
            </td>
            <td style="padding:3px">

                <!-- <input id="id_Get" type="button" value="获取" onclick="pgGet()">&nbsp;-->
                <input id="id_Video" type="button" value="通话呼叫" onclick="pgVideo()">&nbsp;
                <input id="id_HangUp" type="button" value="通话挂断" onclick="pgHangUp()">&nbsp;
            </td>
        </tr>
        <tr>
            <td style="padding:2px">
                <div id="divVideo" style="border:1px solid #cccccc;background:#000; color:#FFF; width:640px; height:480px"></div>
            </td>
            <td style="padding:0px;border:1px solid #cccccc;" align="center">
                <div id="divPrvw" style="border:1px solid #cccccc;background:#000; color:#FFF; width:320px; height:240px"></div>
            </td>
            <td style="padding:0px;border:1px solid #cccccc;" align="center">
                <div id="divOther" style="border:1px solid #cccccc;background:#000; color:#FFF; width:1px; height:1px"></div>
            </td>
        </tr>

        <tr>
            <td style="padding:2px">
                <span id="id_LiveSta"></span>
            </td>
        </tr>

        <tr>
            <td height="150" valign="top" style="padding:3px">
                <iframe id="id_NotifyOut" width="100%" height="100%" frameborder="0" scrolling="auto" style="overflow:visible;word-break:keep-all;border:1px solid #cccccc;">
	</iframe>
            </td>
            <td>
            	<form action="${path}/elevator/setFinished.do" method="post">
            		<input type="hidden" name="e_data_alarm_id" value="${param.edataalarmsid}"/>
            		<input type="submit" value="处理完毕"/>
            	</form>
            </td>
        </tr>

        <tr>
            <td style="padding:3px">
                <textarea id="id_Msg" rows="10" cols="60"></textarea>&nbsp;&nbsp;
                <input type="button" value="发送消息" onclick="pgMessage()">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" value="LAN扫描" onclick="pgLanScan()">
                <br><br>
            </td>
        </tr>

        <tr>
            <td>
                <input type="button" value="上传文件" onclick="btnPut()" />
                <input type="button" value="下载文件" onclick="btnGet()" />
            </td>
        </tr>
        <tr>
            <td>
                <p style="font-size:15px;color:red" id="info"></p>
            </td>
        </tr>
        <tr>
            <td>传输信息:
                <p id="FileProgress"></p>
            </td>
        </tr>

    </table>

    <br><br>

    <table width="646" border="1" cellpadding="0" cellspacing="0" bordercolor="#93A9E3">
        <tr>
            <td height="24" bgcolor="#93A9E3" class="txt">**调式信息：</td>
        </tr>
        <tr>
            <td height="200" valign="top">
                <iframe id="debugOut" width="100%" height="100%" frameborder="0" scrolling="auto" style="overflow:visible;word-break:keep-all;">
	  </iframe>
            </td>
        </tr>
        <tr>
            <td>
                <input id="debugSwitch" type='checkbox' checked="1">允许输出</input>
            </td>
        </tr>
    </table>

    <script language="javascript">
        var sReplysFile = "";
        var sReplysPeer = "";

        var iMethID = 32;
        var bTransfering = false;
        var bIsReplying = false;
        var bNeedCallback = false;

        var Node = pgNewJSNode(19292);

        var s_oExtVideo = null;
		

        function FileAccept(sPeer) {
            bNeedCallback = false;
            bIsReplying = false;
            bTransfering = true;
            $("info").innerHTML = sPeer + "接受了文件传输:..." + "<a href='#' onclick='btnRefuse()'>取消</a>";
        }

        function FileReject(sPeer) {
            bNeedCallback = false;
            bIsReplying = false;
            bTransfering = false;
            $("info").innerHTML = sPeer + "拒绝了文件传输:...";
        }

        var pgLiveUI = {
            OnEvent: function(sAct, sData, sCapID) {
                debugOutString("OnEvent: sAct=" + sAct + ", sData=" + sData + ", sCapID=" + sCapID);

                if (sAct == "VideoStatus") {
                    var sBitRate = getcontent(sData, "bitrate");
                    var sFrmRate = getcontent(sData, "frmrate");
                    var sFrmPlay = getcontent(sData, "frmplay");
                    id_LiveSta.innerText = "速率: " + sBitRate + "bps，帧速: " +
                        sFrmRate + "帧/秒，播放帧: " + sFrmPlay;
                } else if (sAct == "Notify") {
                    // 收到采集端发送的通知
                    pgNotifyOut(sData);
                } else if (sAct == "RenderJoin") {
                    pgNotifyOut("播放端：" + sCapID + "加入");
                } else if (sAct == "RenderLeave") {
                    pgNotifyOut("播放端：" + sCapID + "离开");
                } else if (sAct == "Login") {
                    if (sData == "0") {
                        pgNotifyOut("已经登录");
                    } else {
                        pgNotifyOut("登录失败: " + sData);
                    }
                } else if (sAct == "Logout") {
                    pgNotifyOut("已经注销");
                } else if (sAct == "Connect") {
                    pgNotifyOut("已经连接采集端");
                } else if (sAct == "Disconnect") {
                    pgNotifyOut("已经断开采集端");
                } else if (sAct == "Reject") {
                    pgNotifyOut("采集端主动拒绝连接");
                    if (s_bCtrl) {
                        s_bCtrl = false;
                        id_Ctrl.value = "开始";
                    } else {
                        s_bCtrl = true;
                        id_Ctrl.value = "停止";
                    }
                } else if (sAct == "Offline") {
                    pgNotifyOut("采集端不在线");
                } else if (sAct == "LanScanResult") {
                    // LAN扫描结果
                    pgNotifyOut(sData);
                } else if (sAct == "ForwardAllocReply") {

                } else if (sAct == "ForwardFreeReply") {

                } else if (sAct == "FilePutRequest") {
                    FilePutRequest(sData, sCapID);
                } else if (sAct == "FileGetRequest") {
                    FileGetRequest(sData, sCapID);
                } else if (sAct == "FileAccept") {
                    FileAccept(sCapID);
                } else if (sAct == "FileReject") {
                    FileReject(sCapID);
                } else if (sAct == "FileProgress") {
                    FileProgress(sData);
                } else if (sAct == "FileFinish") {
                    FileFinish(sCapID);
                } else if (sAct == "FileAbort") {
                    FileAbort(sData);
                }
            },

            OnOutString: function(sStr) {
                debugOutString(sStr);
            }
        };

        var ExtVideoEvent = {
            /**
             * 对端调用sendObjPeer产生的事件
             *
             * @param sObjPeer 对端的节点对象名
             * @param sParam   sendObjPeer 传入的参数，保留参数
             */
            onGetObjPeer: function(sObjPeer, sParam) {
                var con;
                con = confirm("收到对端获取本端节点对象名称的请求：\n" + "对端对象名称 sObjPeer = " + sObjPeer); //在页面上弹出对话框
                if (con == true) {

                    var iErr = s_oExtVideo.start(sObjPeer);
                    if (iErr > pgErrCode.PG_ERR_Normal) {
                        console.log("extVideo.start : iErr = " + iErr);
                    }
                    iErr = s_oExtVideo.sendGetObjPeerNotify(sObjPeer, sParam);
                    if (iErr > pgErrCode.PG_ERR_Normal) {
                        console.log("extVideo.sendGetObjPeerNotify : iErr = " + iErr);
                    }

                } else {

                }
            },

            /**
             * 调用sendObjPeer后产生的服务器回复结果事件
             * @param uErr 错误码
             * @param sParam 自定义参数
             */
            onGetObjPeerReply: function(uErr, sParam) {
                if (uErr > pgErrCode.PG_ERR_Normal) {
                    console.log(" ExtVideo.OnEvent onGetObjPeerReply : iErr = " + uErr + " sParam = " + sParam);
                }
            },

            /**
             * 接收sendObjPeerNotify 发送的对端的节点对象的值。
             * @param sObjPeer 节点对象名
             * @param sParam 自定义参数
             */
            onGetObjPeerNotify: function(sObjPeer, sParam) {
                var con;
                //con = confirm("收到对端获取本端节点对象名称的请求：\n" + "对端对象名称 sObjPeer = " + sObjPeer); //在页面上弹出对话框
               // if (con == true) {

                    var iErr = s_oExtVideo.start(sObjPeer);
                    if (iErr > pgErrCode.PG_ERR_Normal) {
                        console.log("extVideo.start : iErr = " + iErr);
                    }
               // } else {

               // }
            },

            /**
             * Video 对象同步
             * @param sObj 对象名称
             * @param sAct 0 是同步，1是去同步
             * @param sObjPeer 节点对象名称
             * @return 错误码 正常返回PG_ERR_Normal
             */
            onVideoSync: function(sObj, sAct, sObjPeer) {

            },


            /**
             * 收到视频请求
             * @param sObj 视频对象
             * @param uHandle 视频请求句柄
             * @param sObjPeer 对端的对象名称
             * @return 错误码 @link pgLibError， -1表示异步调用videoHandle 应答。 正常 返回PG_ERR_Normal
             */
            onVideoStart: function(sObj, uHandle, sObjPeer) {
                var con;
                con = confirm("视频呼叫 \n" + "对端对象名称 sObjPeer = " + sObjPeer); //在页面上弹出对话框
                var iErr;
                if (con == true) {
                    var iErr = s_oExtVideo.videoHandle(sObj, pgErrCode.PG_ERR_Normal, uHandle, sObjPeer, "divOther", true);
                    if (iErr > pgErrCode.PG_ERR_Normal) {
                        console.log("extVideo.videoHandle : iErr = " + iErr);
                    }
                } else {
                    var iErr = s_oExtVideo.videoHandle(sObj, pgErrCode.PG_ERR_Reject, uHandle, sObjPeer, "divOther", true);
                    if (iErr > pgErrCode.PG_ERR_Normal) {
                        console.log("extVideo.videoHandle : iErr = " + iErr);
                    }
                }
                return -1;
            },

            /**
             * 收到videoStart视频请求的结果。
             * @param sObj 视频对象名称
             * @param uErr 错误码， PG_ERR_Normal 表示打开成功
             * @return 错误码，正常返回 PG_ERR_Normal
             */
            onVideoStartReply: function(sObj, uErr) {
                if (uErr > pgErrCode.PG_ERR_Normal) {
                    console.log("ExtVideo.OnEvent onVideoStartReply : sObj = " + sObj + " uErr = " + uErr);
                } else {
                    console.log("视频打开成功！");
                    //otherSurfaceView.setZOrderOnTop(true);
                }

                return 0;
            },

            /**
             * 收到视频结束通知
             * @param sObj 视频对象名称
             * @param sObjPeer 对端节点对象名称
             * @return 错误码 ，正常返回 PG_ERR_Normal
             */
            onVideoStop: function(sObj, sObjPeer) {
                return 0;
            },

            /**
             * 视频传输状态
             * @param sObj 对象名称
             * @param sData 进度状态信息
             */
            onVideoFrameStat: function(sObj, sData) {

            },

            /**
             * 音频对象同步
             * @param sObj 对象名称
             * @param sAct 1同步，0 去同步
             */
            onAudioSync: function(sObj, sAct) {
                return 0;
            },


            /**
             * 音频对象 请求打开音频
             * @param sObj 音频对象名称
             * @param uHandle 请求句柄
             * @param sObjPeer 对端对象名称
             * @return 错误码
             */
            onAudioStart: function(sObj, uHandle, sObjPeer) {
                var con;
                con = confirm("收到对端获取本端节点对象名称的请求：\n" + "对端对象名称 sObjPeer = " + sObjPeer); //在页面上弹出对话框
                if (con == true) {} else {

                }
                return 0;
            },

            /**
             * 音频打开请求结果
             * @param sObj 音频对象名称
             * @param uErr 错误码
             * @return 返回错误码
             */
            onAudioStartRelay: function(sObj, uErr) {
                return 0;
            },

            /**
             * 音频已经被关闭
             * @param sObjPeer 节点对象名称
             * @return 错误码
             */
            onAudioStop: function(sObjPeer) {
                return 0;
            }
        }

        function FileFinish(sPeer) {
            bNeedCallback = false;
            bIsReplying = false;
            bTransfering = false;
            $("info").innerHTML = "与" + sPeer + "端文件传输完毕...";
        }

        function FileAbort(sData) {
            var sPath = getcontent(sData, "path");
            var sReqSize = getcontent(sData, "total");
            var sCurSize = getcontent(sData, "position");
            bTransfering = false;
            $("info").innerHTML = "文件传输中断了，文件信息:本地文件路径:" + sPath + " 已传输大小:" + sCurSize +
                "/文件总大小:" + sReqSize;
        }

        function btnPut() {
            if (bTransfering) {
                alert("系统正忙，请稍后再操作...");
                return;
            }

            bIsZhuDong = true;
            var sFileInfo = pgAtx.utilCmd("DlgFile", "(Open){1}(Ext){}(File){}(Flag){0}(Filter){}");
            var sPath = pgAtx.omlGetContent(sFileInfo, "Path");
            var iErr = s_oLive.FilePutRequest(id_CaptureID.value, sPath, "");
            if (iErr == -1) {
                bIsZhuDong = true;
                bTransfering = true;
                bNeedCallback = false;
                $("info").innerHTML = "您正在请求文件上传..." + "<a href='#' onclick='btnRefuse()'>取消</a>";
            } else {
                $("info").innerHTML = "文件上传请求失败(错误码:" + iErr + ")...";
            }
        }

        function btnGet() {
            if (bTransfering) {
                alert("系统正忙，请稍后再操作...");
                return;
            }
            bIsZhuDong = true;
            var sFileInfo = pgAtx.utilCmd("DlgFile", "(Open){0}(Ext){}(File){}(Flag){0}(Filter){}");
            var sPath = pgAtx.omlGetContent(sFileInfo, "Path");
            var iErr = s_oLive.FileGetRequest(id_CaptureID.value, sPath, "");
            if (iErr == -1) {
                bIsZhuDong = true;
                bTransfering = true;
                bNeedCallback = false;
                $("info").innerHTML = "您正在请求文件下载..." + "<a href='#' onclick='btnRefuse()'>取消</a>";
            } else {
                $("info").innerHTML = "文件下载请求失败(错误码:" + iErr + ")...";
            }
        }

        function FileProgress(sData) {
            var sPath = getcontent(sData, "path");
            var sReqSize = getcontent(sData, "total");
            var sCurSize = getcontent(sData, "position");
            $("FileProgress").innerHTML = "本地文件路径:" + sPath + " 已传输大小:" + sCurSize +
                "/文件总大小:" + sReqSize;
        }

        function $(ID) {
            return document.getElementById(ID);
        }

        function btnRefuse() {
            if (!bNeedCallback) {
                s_oLive.FileCancel(id_CaptureID.value);
                bTransfering = false;
                $("info").innerHTML = "";
            } else {
                var iErr = s_oLive.FileReject(sReplysPeer, 13);
                if (iErr != 0) {
                    outString("FileRequest defeated IErr = " + iErr);
                } else {
                    bNeedCallback = false;
                    dearFileRequest(sReplysPeer, sReplysFile, 1);
                }
            }
        }

        function FilePutRequest(sData, sPeer) {
            var file = getcontent(sData, "peerpath");
            sReplysPeer = sPeer;
            bIsZhuDong = false;
            sReplysFile = file;
            bIsReplying = true;
            bNeedCallback = true;
            $("info").innerHTML = "是否接受" + sPeer + "的文件(" + file + ")上传请求:<a href='#' onclick='btnAccept()'>接受</a>or<a href='#' onclick='btnRefuse()'>拒绝</a>";
            iMethID = 32;
        }

        function FileGetRequest(sData, sPeer) {
            var file = getcontent(sData, "peerpath");
            bNeedCallback = true;
            bIsZhuDong = false;
            sReplysPeer = sPeer;
            bIsReplying = true;
            sReplysFile = file;
            $("info").innerHTML = "是否接受" + sPeer + "的文件(" + file + ")下载请求:<a href='#' onclick='btnAccept()'>接受</a>or<a href='#' onclick='btnRefuse()'>拒绝</a>";
            iMethID = 33;
        }

        function btnAccept() {
            var sFileInfo = pgAtx.utilCmd("DlgFile",
                "(Open){" + (iMethID == 32 ? 0 : 1) + "}(Ext){}(File){}(Flag){0}(Filter){}");
            var sPath = pgAtx.omlGetContent(sFileInfo, "Path");
            var iErr = s_oLive.FileAccept(sReplysPeer, sPath);
            if (iErr != 0) {
                outString("FileRequest defeated IErr = " + iErr);
            } else {
                dearFileRequest(sReplysPeer, sReplysFile, 0);
            }
        }

        function dearFileRequest(sPeer, sPeerPath, iAction) {
            bNeedCallback = false;
            bIsReplying = false;
            if (iAction != 1) {
                bNeedCallback = false;
                bIsZhuDong = false;
                bTransfering = true;
                $("info").innerHTML = "您正在进行文件(" + sPeerPath + ")传输:...<a href='#' onclick='btnRefuse()'>取消</a>";
            } else {
                bTransfering = false;
                $("info").innerHTML = "您拒绝了文件(" + sPeerPath + ")传输:...";
            }
        }

        function getcontent(sData, skey) {
            var result = "";
            var values = sData.split("&");
            for (var i = 0; i < values.length; i++) {
                var entry = values[i].split("=");
                if (entry[0] == skey) {
                    result = entry[1];
                    break;
                }
            }
            return result;
        }

        var s_oLive = null;
        var s_bCtrl = false;

        function pgInitialize() {
            s_oExtVideo = new pgLibExtVideo();

            s_oLive = new pgLibLiveMultiRender(Node, pgLiveUI);

            s_oLive.SetNodeEventHook(s_oExtVideo.getHook());

            s_oExtVideo.setOnEvent(ExtVideoEvent);

			pgInit();
        }

        function pgClean() {
            if (s_oLive) {
                s_oLive.Clean();
                s_oLive = null;
            }
            pgDeleteJSNode(Node);
        }

        function pgInit() {
            if (s_oLive.Initialize(id_SelfID.value, "", "49.234.231.9:7781", "", 3, "(BufSize2){2048}") != 0) {
                s_oLive = null;
                return;
            }

            var sVerInfo = Node.utilCmd("Version", "");

            //alert("Ver = " + sVerInfo);

            //s_oLive.SetVideoDiv("divVideo");
            var sExtVideoParam = "(Code){3}(Mode){2}(Rate){66}(BitRate){300}";
            s_oExtVideo.initialize(Node, s_oLive.GetSelfPeer(), sExtVideoParam, "");

            s_oExtVideo.setPrewview("divPrvw");
        }

        function pgCtrl() {
            if (s_bCtrl) {
                if (bTransfering || bIsReplying) {
                    bIsReplying = false;
                    bTransfering = false;
                    btnRefuse();
                }
                s_oLive.VideoStop(id_CaptureID.value, 0);
                s_oLive.AudioStop(id_CaptureID.value, 0);
                s_oLive.Disconnect(id_CaptureID.value);
                s_bCtrl = false;
                id_Ctrl.value = "开始";
            } else {
                if (id_CaptureID.value == "") {
                    alert("需要指定ID");
                    return;
                }
                s_oLive.Connect(id_CaptureID.value);
                s_oLive.VideoStart(id_CaptureID.value, 0, "", "divVideo");
                s_oLive.AudioStart(id_CaptureID.value, 0, "");
                s_bCtrl = true;
                id_Ctrl.value = "停止";
            }
        }

        function pgVideoShowMode(iMode) {
		var iErr = s_oExtVideo.sendGetObjPeer(id_CaptureID.value, "1");
            if (s_oLive) {
                s_oLive.VideoShowMode(iMode);
            }
        }

        function pgVideoRecored(bAction) {
            if (s_oLive) {
                if (bAction) {
                    s_oLive.RecordStart(id_CaptureID.value, "e:\\temp\\1111.avi", 0, 0);
                } else {
                    s_oLive.RecordStop(id_CaptureID.value);
                }
            }
        }

        function outString(out) {
            debugOutString(out);
        }

        function pgMessage() {
            if (s_oLive) {
                s_oLive.MessageSend(id_CaptureID.value, id_Msg.value);
            }
        }

        function pgLanScan() {
            if (s_oLive) {
                s_oLive.LanScanStart(2);
            }
        }

        function pgNotifyOut(sMsg) {
            try {
                var sHtml = "<pre style={font-size:12px;word-wrap:break-word;word-break:break-all;white-space:normal;}>" + sMsg + "</pre>";
                var ofrm1 = document.getElementById("id_NotifyOut");
                if (ofrm1) {
                    ofrm1.contentWindow.document.body.insertAdjacentHTML("beforeEnd", sHtml);
                }
            } catch (e) {}
        }

        function pgAudioMute(bFlag) {
            if (s_oLive) {
                s_oLive.AudioMute(id_CaptureID.value, 0, false, bFlag);
            }
        }

        function pgAudioSpeech(bFlag) {
            if (s_oLive) {
                s_oLive.AudioSpeech(id_CaptureID.value, 0, bFlag);
            }
        }

        function debugOutHTML(sHtml) {
            try {
                var ofrm1 = document.getElementById("debugOut");
                if (ofrm1) {
                    ofrm1.contentWindow.document.body.insertAdjacentHTML("beforeEnd", sHtml);
                }
            } catch (e) {}
        }

        function debugOutString(sOut) {
            if (debugSwitch.checked) {
                debugOutHTML("<pre style={font-size:12px;word-wrap:break-word;word-break:break-all;white-space:normal;}>" + sOut + "</pre>");
            }
        }

        function pgGet() {
            var iErr = s_oExtVideo.sendGetObjPeer(id_CaptureID.value, "1");
            if (iErr > pgErrCode.PG_ERR_Normal) {
                console.log("sendGetObjPeerNotify : iErr = " + iErr);
            }
        }

        function pgVideo() {
		var iErr = s_oExtVideo.sendGetObjPeer(id_CaptureID.value, "1");
		 if (iErr > pgErrCode.PG_ERR_Normal) {
                console.log("sendGetObjPeerNotify : iErr = " + iErr);
				alert("sendGetObjPeerNotify : iErr = " + iErr);
            }
           else{
		   iErr = s_oExtVideo.videoStart(s_oLive.GetSelfPeer(), "divOther", true);
            if (iErr > pgErrCode.PG_ERR_Normal) {
                console.log("videoStart : iErr = " + iErr);
            }
		   }
            
        }

        function pgHangUp() {
            s_oExtVideo.videoStop(s_oLive.GetSelfPeer(), true);
        }
    </script>

</body>

</html>