<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.sq.model.vo.SysMenu"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>项目管理平台首页</title>
		<link href="../css/main.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="../js/superfish/css/superfish.css" media="screen"/>
		<script language="javascript" type="text/javascript" src="../js/jquery.js"></script>
		<script type="text/javascript" src="../js/superfish/js/hoverIntent.js"></script>
		<script type="text/javascript" src="../js/superfish/js/superfish.js"></script>
	</head>
	<body>
		<div id="right_top">
			<%List menuList = (List)request.getAttribute("menuList");
			for(int i=0  ; i<menuList.size() ; i++){
				SysMenu sysMenu = (SysMenu)menuList.get(i);
				if(i==0){ %>
					<script type="text/javascript">
					<!--
						function load(){
							goClick('<%=sysMenu.getMenuId()%>','<%=sysMenu.getMenuName()%>','<%=sysMenu.getShowInfo()%>');
						}
						window.onload = load;
					//-->
					</script>
				<%} %>
				<span class="imgtext"> <a href="<%=sysMenu.getShowUrl() %>" target="mainFrame" 
					onclick="goClick('<%=sysMenu.getMenuId() %>','<%=sysMenu.getMenuName() %>','<%=sysMenu.getShowInfo() %>')"><%=sysMenu.getMenuName() %></a> </span>
				<%} %>
			<div id="loginout">
				<div id="loginoutimg">
					<span id="logintext"><s:property value="@com.sq.tools.Public@getSystemTimeToFormat('yyyy-MM-dd EEE')"/></span>&nbsp;&nbsp;&nbsp;&nbsp;<img src="../images/loginout.gif" />
				</div>
				<a href="#" onclick="javascript:parent.location='../index.jsp'"><span id="logintext">退出系统</span></a>
			</div>
		</div>
		<div id="right_font">
		</div>
		<div style="font-size: 12px; margin-left: 20px;padding-top:2px;z-index:21;" id="pathPos">
			<img src='../images/tops1.gif' />  您现在所在的位置： <span class="bfont">首页</span>
		</div>
	</body>
</html>
		<script type="text/javascript">
<!--
		// initialise plugins
		jQuery(function(){
			jQuery('ul.sf-menu').superfish();
		});
function showPathPos(menuName , showInfo){
	//所在位置显示
	var pathPos = "<img src='../images/tops1.gif' />  您现在所在的位置：" + menuName + "  → <span class='bfont'>" + showInfo +"</span>" ;
	$("#pathPos").html(pathPos) ;
}

function goClick(menuid , menuName , showInfo){
	//所在位置显示
	var pathPos = "<img src='../images/tops1.gif' />  您现在所在的位置：" + menuName + "  → <span class='bfont'>" + showInfo +"</span>" ;
	$("#pathPos").html(pathPos) ;
	
	//请求地址
	var url = 'tools.shtml' ;
	//传送的值
	var params = {menuId: menuid,
	menuName: menuName };
	//使用$.post方式	
		$.post(url, //服务器要接受的url
			params, //传递的参数		
			function cbf(data) { //服务器返回后执行的函数 参数 data保存的就是服务器发送到客户端的数据
				$("#right_font").html(data.toolsStr) ;
			}, 
			'json' //数据传递的类型  json
		);
}
//-->
</script>