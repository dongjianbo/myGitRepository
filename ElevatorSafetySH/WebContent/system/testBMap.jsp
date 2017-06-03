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
        </style>
        <script type="text/javascript" src="http://api.map.baidu.com/api?ak=7cEfQTOD2S32tvOBCAI8CCT1OxWtQNRp&v=2.0&services=false"></script>
    	<script type="text/javascript" src="http://code.jquery.com/jquery-1.8.0.min.js"></script>
    	<script type="text/javascript">
    		$().ready(function(){
    			//创建Map实例,参数为Map层的ID
                var map = new BMap.Map("container");
                //默认的坐标为杭州
                var point = new BMap.Point(120.162268,30.28193);
                //设置地图的默认中心点和缩放级别
                map.centerAndZoom(point,15);
                //启用鼠标滚动缩放
                map.enableScrollWheelZoom();
                
                //添加缩略图控件
                map.addControl(new BMap.OverviewMapControl({
                	isOpen:false,
                	anchor:BMAP_ANCHOR_BOTTOM_RIGHT
                	}));
                //添加缩放平移控件
                map.addControl(new BMap.NavigationControl());
                //添加比例尺控件
                map.addControl(new BMap.ScaleControl());
                //添加地图类型控件
                map.addControl(new BMap.MapTypeControl());
                
                //设置标注的图标
                var icon = new BMap.Icon("http://api.map.baidu.com/img/markers.png",new BMap.Size(23,25));
                //设置标注的经纬度
                var marker = new BMap.Marker(new BMap.Point(120.162052,30.278796),{icon:icon});
                //把标注添加到地图上
                map.addOverlay(marker);
                var content = "<table>";  
                    content = content + "<tr><td> 编号：001</td></tr>";  
                    content = content + "<tr><td> 地点：杭州市政府</td></tr>"; 
                    content = content + "<tr><td> 时间：2016-06-12</td></tr>";  
                    content += "</table>";
                var infowindow = new BMap.InfoWindow(content);
                //为标记添加点击事件
                marker.addEventListener("click",function(){
                    this.openInfoWindow(infowindow);
                });
                
                //为map添加右键事件
                var div_addmark=$("#addmarker").html();
               
                var inforwindow_addmark=new BMap.InfoWindow(div_addmark,{width:200,height:200,title:"添加项目标记"});
                map.addEventListener("rightclick",function(e){
                	map.openInfoWindow(inforwindow_addmark,e.point);
                	
                });
                //点击地图，获取经纬度坐标
                map.addEventListener("click",function(e){
                    $("#aa").html("经度坐标："+e.point.lng+" &nbsp;纬度坐标："+e.point.lat);
                });
                
                //查询地图事件
                $("#searchMap").click(function(){
                	var keyword = $("#keyword").val();
                    var local = new BMap.LocalSearch(map, {
                    	renderOptions:{map: map}
                    });
                    local.search(keyword);
                });
    		});
    		
    		
    	</script>
    </head>
    <body>
        <div id="content" class="content">
        <input type="text" value="" id="keyword" />
        <input type="button" name="" id="searchMap" value="查询"  />
        <div align="center" style="width:900px;height:700px;border:0px solid gray" id="container"></div>
        <p id="aa"></p>
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