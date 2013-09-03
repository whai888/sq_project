<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib uri="/birt.tld" prefix="birt" %>
<html>
	<head>
		<title>项目组人员工作成果统计</title>
		<link href="../css/index.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../js/public.js"></script>
		<script type="text/javascript" src="../js/jquery.js"></script>
		<script type="text/javascript" src="../js/My97DatePicker/WdatePicker.js"></script>
		<jsp:include page="../js/index.jsp"></jsp:include>
	</head>
  
	<body>
    <birt:viewer id="birtViewer" reportDesign="reports/projectgroupuserwork.rptdesign"
			pattern="frameset"
			format="HTML"
			isHostPage = "true"
			showTitle="false"
			showNavigationBar="false">
	</birt:viewer>
 	</body>
</html>