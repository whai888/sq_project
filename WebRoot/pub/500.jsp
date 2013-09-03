<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统异常界面</title>
<link type="text/css" rel="stylesheet" href="<%=basePath %>css/style-home.css" />
<link type="text/css" rel="stylesheet" href="<%=basePath %>css/style-sys.css" />
</head>
<body>
<div class="right">
        <div class="msgbox">
        <table border="0" style="margin-top: 60px;margin-left: 25px;display: inline-table;">
        	<tr><td rowspan="2" width="40px;"><img alt="系统异常" src="<%=basePath %>images/error.gif" width="48" height="48" /></td>
        	<td height="90px;" style="padding-left: 20px;">登陆已超时，请重新登陆</td></tr>
        	<tr>
        	<td style="text-align: right;width: 350px;">
        		<div style="width: 100px;float: right;">
        		<a class="btn" onClick="javascript:window.parent.location='<%=basePath%>prof_login.shtml'">
					<cite>重新登陆</cite></a>
					</div>
			</td></tr>
        </table>
        </div>
 </div>
</body>
</html>