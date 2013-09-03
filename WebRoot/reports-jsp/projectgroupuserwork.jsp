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
		<script type="text/javascript" src="../reports-jsp/js/projectgroupuserwork.js"></script>
	</head>
	<body>
	<birt:parameterPage id="parameterPage"
				name="jform"
				isCustom="true"
				pattern="frameset"
				reportDesign="reports/projectgroupuserwork.rptdesign"
				target="_self"
				showTitle="false"
				showNavigationBar="false"
				format="html">
		<table width="1024px" border="0" cellpadding="0" cellspacing="1" bgcolor="#c9c9c9">
	      <tr>
	        <td height="32" class="bgcolor1 fontright fontbold" align="center">项目组：</td>
	        <td height="32" class="bgcolor1 fontleft fontbold" colspan="3">
	        	<s:select name="groupNo" theme="simple" list="#request.groupMap" listKey="key.groupNo" listValue="key.groupName"></s:select>
	        </td>
	      </tr>
	      <tr>
	      	<td height="32" class="bgcolor1 fontright fontbold">开始日期：</td>
	        <td height="32" class="bgcolor1 fontleft fontbold">
	        	<input type="text" id="datepick2" name="startDate" value="" >
	        </td>
	        <td height="32" class="bgcolor1 fontright fontbold">结束日期：</td>
	        <td height="32" class="bgcolor1 fontleft fontbold">
	        	<input type="text" id="datepick1" name="endDate" value="">
	        </td>
	      </tr>
	      <tr>
	      <td colspan="4" height="52" style="text-align: center">
				<div style="text-align: center;width:200px;">
					<a class="btn" onClick="onQuery()" style="padding-right: 60px;">
					<cite>确&nbsp;&nbsp;&nbsp;定</cite></a>
				<a class="btn" onClick="document.jform.reset();">
					<cite>重&nbsp;&nbsp;&nbsp;置</cite></a>
					</div>
			</td>
	      </tr>
	    </table>
	    <input type="hidden" id="groupName" name="groupName" value="">
    </birt:parameterPage>
 	</body>
</html>