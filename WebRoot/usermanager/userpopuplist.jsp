<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.sq.sys.ApplicationDate"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Arrays"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>员工信息</title>
		<link rel="stylesheet" type="text/css" href="../js/tree_themes/SimpleTree.css" />
		<script type="text/javascript" src="../js/jquery.js"></script>
		<script type="text/javascript" src="../js/Tree.js"></script>
		<script type="text/javascript">
<!--
$(function(){
	$(".st_tree").SimpleTree({
		click:function(a){
			if(!$(a).attr("hasChild")){
				var valStr = $(a).attr("ref") ;
				if(valStr.length <= 5){
					$("#"+valStr+"").attr("show" , "true") ;
				}else{
					window.returnValue=valStr ;
					window.close();
				}
			}
		}
	});
	window.returnValue="0000" ;
});
//-->
</script>
</head>
	<body>
		<div class="st_tree">
			<ul>
				<s:iterator value="#request.deptMap" id="deptTab" >
					<li><a href="#" ref="<s:property value="key.deptNo"/>"><s:property value="key.deptName"/></a></li>
						<ul id="<s:property value="key.deptNo"/>">
							<s:iterator value="#deptTab.value" id="userTab" >
						            <li><a href="#" ref="<s:property value="userId"/>|<s:property value="userName"/>"><s:property value="userName"/></a></li>
							</s:iterator>
						 </ul>
				</s:iterator>
			</ul>
		</div>
	</body>
</html>