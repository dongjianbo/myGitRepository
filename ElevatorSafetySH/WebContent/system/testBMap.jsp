<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Simple Map</title>
        <style type="text/css">
            body, html {width: 100%;height: 100%;margin:0;font-family:"微软雅黑";}
            p{margin-left:5px; font-size:14px;}
            #container{width: 100%;height: 100%;
            }
        </style>
        <script type="text/javascript" src="http://api.map.baidu.com/api?ak=7cEfQTOD2S32tvOBCAI8CCT1OxWtQNRp&v=2.0&services=false"></script>
    	<script type="text/javascript" src="http://code.jquery.com/jquery-1.8.0.min.js"></script>
    	<script type="text/javascript">
    	
    		$().ready(function(){
    			//获取当前map信息
    			$.getJSON("${path}/elevator/map.do","rand="+Math.random(),function(r){
    				//创建Map实例,参数为Map层的ID
                    var map = new BMap.Map("container");
                    //默认的坐标
                    var point = new BMap.Point(r.distinctGis.gis_x,r.distinctGis.gis_y);
                   
                  //设置标注的图标
                     //var icon = new BMap.Icon("http://api.map.baidu.com/img/markers.png",new BMap.Size(23,25));
                     var icon = new BMap.Icon("${path}/images/markers.png",new BMap.Size(23,25), {
                         offset: new BMap.Size(10, 25), // 指定定位位置
                         imageOffset: new BMap.Size(0, 0 - 3 * 25) // 设置图片偏移
                     });
               
                   	//设置标注的经纬度
                     var markerCenter = new BMap.Marker(point,{icon:icon});
                     //把标注添加到地图上
           
                        map.addOverlay(markerCenter);
                    //设置地图的默认中心点和缩放级别
                    map.centerAndZoom(point,r.distinctGis.level);
                    //启用鼠标滚动缩放
                    map.enableScrollWheelZoom();
                    //添加缩略图控件
                    map.addControl(new BMap.OverviewMapControl({
                    	isOpen:true,
                    	anchor:BMAP_ANCHOR_BOTTOM_RIGHT
                    	}));
                    //添加缩放平移控件
                    map.addControl(new BMap.NavigationControl());
                    //添加比例尺控件
                    map.addControl(new BMap.ScaleControl());
                    //添加地图类型控件
                    map.addControl(new BMap.MapTypeControl());
                    for(var i=0;i<r.elList.length;i++){
                    	 //设置标注的图标
                        //var icon = new BMap.Icon("http://api.map.baidu.com/img/markers.png",new BMap.Size(23,25));
                        var icon=new BMap.Icon("${path}/images/markers.png",new BMap.Size(23,25));
                  		if(r.elList[i].markerType=="red"){
                  			icon = new BMap.Icon("${path}/images/markers.png",new BMap.Size(23,25),{
                  				offset: new BMap.Size(10, 25), // 指定定位位置
                                imageOffset: new BMap.Size(0, 0 - 11 * 25) // 设置图片偏移
                  			});
                  		}
                  		if(r.elList[i].markerType=="blue"){
                  			icon = new BMap.Icon("${path}/images/markers.png",new BMap.Size(23,25),{
                  				offset: new BMap.Size(10, 25), // 指定定位位置
                                imageOffset: new BMap.Size(0, 0 - 10 * 25) // 设置图片偏移
                  			});
                  		}
                  		if(r.elList[i].markerType=="green"){
                  			icon = new BMap.Icon("${path}/images/markers.png",new BMap.Size(23,25),{
                  				offset: new BMap.Size(10, 25), // 指定定位位置
                                imageOffset: new BMap.Size(0, 0 - 12 * 25) // 设置图片偏移
                  			});
                  		}
                      	//设置标注的经纬度
                        var marker = new BMap.Marker(new BMap.Point(r.elList[i].gis_x,r.elList[i].gis_y),{icon:icon});
                        //把标注添加到地图上
              
                        map.addOverlay(marker);
                        var label = new BMap.Label(r.elList[i].desc,{offset:new BMap.Size(-10,25)});  
                        marker.setLabel(label);  
                        marker.setTitle(r.elList[i].id_elevator);  
                    
                        //marker.addEventListener("click",ck1(r,i,marker));
                        marker.addEventListener("click",getAttr);
                      //获取指定标记的详细信息     
                        function getAttr() {
                        	for (var i=0;i<r.elList.length;i++) {
                        		if (r.elList[i].id_elevator == this.getTitle()) {   
                        			var content = "<table>";  
//                                  content = content + "<tr><td> 编号："+r.elList[i].id_elevator+"</td></tr>";
                                    content = content + "<tr><td> 电梯简称："+r.elList[i].desc+"</td></tr>";  
                                    content = content + "<tr><td> 登记机构："+r.elList[i].register_org+"</td></tr>"; 
                                    content = content + "<tr><td> 生产日期："+r.elList[i].date_manufer+"</td></tr>"; 
                                    content = content + "<tr><td> 出厂编号："+r.elList[i].code_manufer+"</td></tr>"; 
                                    content = content + "<tr><td> 土建施工单位："+r.elList[i].constucter+"</td></tr>"; 
                                    content = content + "<tr><td> 土建验收单位名称："+r.elList[i].accepter_construct+"</td></tr>"; 
                                    content = content + "<tr><td> 验收检验机构："+r.elList[i].check_construct+"</td></tr>"; 
                                    content = content + "<tr><td> 安装单位："+r.elList[i].installer.name+"</td></tr>"; 
                                    content = content + "<tr><td> 使用单位："+r.elList[i].user.name+"</td></tr>"; 
                                    content = content + "<tr><td> 电梯所在位置："+r.elList[i].address+"</td></tr>"; 
                                    content = content + "<tr><td> 开始使用时间："+r.elList[i].date_enable+"</td></tr>"; 
                                    content = content + "<tr><td> 保维单位："+r.elList[i].service.name+"</td></tr>"; 
                                    content = content + "<tr><td> 检测单位："+r.elList[i].test.name+"</td></tr>";
                                    content = content + "<tr><td> 电梯层数："+r.elList[i].num_floor_elevator+"</td></tr>"; 
                                    content += "</table>";
        	                        var infowindow = new BMap.InfoWindow(content); 
        	                        this.openInfoWindow(infowindow);
                        		}
                        	}
                        	
                                 
                             
                        }  
                    }
    			});
    			
    		});   
              
    		function ck1(r,i,m){
            	var content = "<table>";  
//              content = content + "<tr><td> 编号："+r.elList[i].id_elevator+"</td></tr>";
                content = content + "<tr><td> 电梯简称："+r.elList[i].desc+"</td></tr>";  
                content = content + "<tr><td> 登记机构："+r.elList[i].register_org+"</td></tr>"; 
                content = content + "<tr><td> 生产日期："+r.elList[i].date_manufer+"</td></tr>"; 
                content = content + "<tr><td> 出厂编号："+r.elList[i].code_manufer+"</td></tr>"; 
                content = content + "<tr><td> 土建施工单位："+r.elList[i].constucter+"</td></tr>"; 
                content = content + "<tr><td> 土建验收单位名称："+r.elList[i].accepter_construct+"</td></tr>"; 
                content = content + "<tr><td> 验收检验机构："+r.elList[i].check_construct+"</td></tr>"; 
                content = content + "<tr><td> 安装单位："+r.elList[i].installer.name+"</td></tr>"; 
                content = content + "<tr><td> 使用单位："+r.elList[i].user.name+"</td></tr>"; 
                content = content + "<tr><td> 电梯所在位置："+r.elList[i].address+"</td></tr>"; 
                content = content + "<tr><td> 开始使用时间："+r.elList[i].date_enable+"</td></tr>"; 
                content = content + "<tr><td> 保维单位："+r.elList[i].service.name+"</td></tr>"; 
                content = content + "<tr><td> 检测单位："+r.elList[i].test.name+"</td></tr>";
                content = content + "<tr><td> 电梯层数："+r.elList[i].num_floor_elevator+"</td></tr>"; 
                content += "</table>";
                var infowindow = new BMap.InfoWindow(content); 
                m.openInfoWindow(infowindow);
        	}
    		function marker_click(eid){
            	alert("${path}/maint_report_id/listForTask.do?maint_type=123&elevator_id="+eid);
        		location.href="${path}/maint_report_id/listForTask.do?maint_type=123&elevator_id="+eid;
            }
        	
    	</script>
    </head>
    <body>
        <div id="content" class="content">
        <!--  <p id="aa"></p> -->
       	<!-- <input type="text" value="" id="keyword" />
         <input type="button" name="" id="searchMap" value="查询"  /> -->
	        <div align="center" style="width:1370px;height:690px;border:0px solid gray" id="container"></div>
	        <div id="addmarker" style="display: none">
		      	<form id="addmarker" action="" method="post">
		      		<table>
		      			<tr>
		      				<td>请选择该标记对应的项目:</td>
		      			</tr>
		      			<tr>
		      				<td>
			      				<select style="width: 200px">
			      					<option>请选择</option>
			      				</select>
		      				</td>
		      			</tr>
		      			<tr>
		      				<td align="right"><input id="marksubmit" type="submit" value="确定"/></td>
		      			</tr>
		      		</table>
		      	</form>
	      	</div>
        </div>
    </body>
</html>