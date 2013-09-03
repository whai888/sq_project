<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.sq.sys.ApplicationDate"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="com.sq.model.vo.SqDeptTab"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>部门报告查询</title>
		<link type="text/css" rel="stylesheet" href="../css/style-home.css" />
		<link type="text/css" rel="stylesheet" href="../css/style-sys.css" />
		<script type="text/javascript" src="../js/public.js"></script>
		<script type="text/javascript" src="../js/jquerybak.js"></script>
		<script type="text/javascript" src="js/workdaydeptlist.js"></script>
	</head>
		<style>
<!--
div.roundedcorner{
background: #D1DDED;
width:320px;
z-index:99;
position:absolute;
} 
.roundedcorner.preview{background: #D1DDED;center;width:320px;
			border:1px solid #A1C0D3;}
			
b.rtop, b.rbottom{
display:block;
background: #FFFFFF
} 

b.rtop b, b.rbottom b{
display:block;
height: 1px;
overflow: hidden; 
background: #D1DDED
} 

b.r1{margin: 0 10px} 
b.r2{margin: 0 6px} 
b.r3{margin: 0 4px} 

b.rtop b.r4, b.rbottom b.r4{
margin: 0 1px;
height: 2px
}
-->
</style>
	<%
		List deptList = (List)request.getAttribute("deptList");
	%>
	<body>
		<div class="right">
			<div class="right_bt">
				<h1 class="right_bth1">
					部门周报查询
				</h1>
			</div>
			<%
			for(int i=0 ; i<deptList.size() ; i++){
			SqDeptTab sqDeptTab = (SqDeptTab)deptList.get(i);
			%>
			<div class="ashow">
				部门名称：<%=sqDeptTab.getDeptName() %><br/><br/>
				部门负责人：<%=sqDeptTab.getSqUserTab().getUserName() %><br/><br/>
				<div class="deptNo" style="display: none;"><%=sqDeptTab.getDeptNo() %></div>
			</div>
			<%} %>
    </div>
    <div class="roundedcorner" style="display: none;"> 
		<b class="rtop">
			<b class="r1"></b>
			<b class="r2"></b>
			<b class="r3"></b>
			<b class="r4"></b>
		</b>
		<br>
		<div ></div>
		<div class="preview"></div>
		<br><br>
		<b class="rbottom">
			<b class="r4"></b>
			<b class="r3"></b>
			<b class="r2"></b>
			<b class="r1"></b>
		</b> 
	</div> 
    <div class="right_centerwk">
   &nbsp;
   </div>
	</body>
</html>