<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.sq.sys.ApplicationDate"%>
<%@page import="java.util.Arrays"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>部门信息新增</title>
		<link type="text/css" rel="stylesheet" href="../css/style-home.css" />
		<link type="text/css" rel="stylesheet" href="../css/style-sys.css" />
		<script type="text/javascript" src="../js/public.js"></script>
		<script type="text/javascript" src="../js/jquery.js"></script>
		<jsp:include page="../js/index.jsp"></jsp:include>
		<script type="text/javascript" src="js/deptadd.js"></script>
	</head>
	<%
		ApplicationDate.getRequestData("SYS_PARA_DATA", Arrays
				.asList("sq_dept_tabstatus"), request, "sq_dept_tabstatus");
	%>
	<body>
		<div class="right">
			<div class="right_bt">
				<h1 class="right_bth1">
					新增部门信息
				</h1>
			</div>
			<div class="right_centerwk">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td>
							<form action="adddept.shtml" method="post" name="jform"
								id="jform">
								<table border="0" width="100%" class="form_table_l">
									<tr>
										<td height="32" style="font-weight: bold; text-align: right">
											部门名称：
										</td>
										<td height="32" class="bgcolor1 fontleft fontbold" colspan="3">
											<input type="text"
												value="<s:property value="sqDeptTab.deptName"/>"
												name="sqDeptTab.deptName" onblur="isCheckMaxLenth(this)"
												maxlength="50" size="50">
										</td>
									</tr>
									<tr>
										<td height="32" style="font-weight: bold; text-align: right">
											部门负责人：
										</td>
										<td height="32" class="bgcolor1 fontleft fontbold">
											<input type="text" value="" name="deptUser" id="openpopup"
												readonly="readonly">
											<input type="hidden"
												value="<s:property value="sqDeptTab.deptUser"/>"
												name="sqDeptTab.deptUser" id="openpopup" readonly="readonly">
										</td>
										<td height="32" style="font-weight: bold; text-align: right">
											部门领导：
										</td>
										<td height="32" class="bgcolor1 fontleft fontbold">
											<input type="text" value="" name="zhuliuserName" id="openzhuli" size="40"
												readonly="readonly">
											<input type="hidden"
												name="zhuliuserId" readonly="readonly">
										</td>
									</tr>
									<tr>
										<td height="32" style="font-weight: bold; text-align: right">
											部门成立时间：
										</td>
										<td height="32" class="bgcolor1 fontleft fontbold">
											<input type="text" id="datepick2" maxlength="10" onfocus="$(this).calendar()"
												value="<s:date name="sqDeptTab.succDate" format="yyyy-MM-dd"/>"
												name="sqDeptTab.succDate">
										</td>
										<td height="32" style="font-weight: bold; text-align: right">
											状态：
										</td>
										<td height="32" class="bgcolor1 fontleft fontbold">
											<s:select name="sqDeptTab.status" theme="simple"
												list="#request.sq_dept_tabstatus"></s:select>
										</td>
									</tr>
									<tr>
										<td height="32" style="font-weight: bold; text-align: right">
											部门信息：
										</td>
										<td height="32" class="bgcolor1 fontleft fontbold" colspan="3">
											<textarea rows="5" cols="40" name="sqDeptTab.remark" onblur="isCheckMaxLenth(this)" maxlength="100"><s:property value="sqDeptTab.remark" /></textarea>
										</td>
									</tr>
									<tr>
										<td colspan="4" height="52" style="text-align: center" align="center">
											<div style="text-align: center;">
											<div style="width:200px;margin: 0 auto;">
												<a class="btn" onClick="onSub()" style="padding-right: 60px;">
												<cite>确&nbsp;&nbsp;&nbsp;定</cite></a>
											<a class="btn" onClick="document.jform.reset();">
												<cite>重&nbsp;&nbsp;&nbsp;置</cite></a>
												</div>
												</div>
										</td>
									</tr>
								</table>
								<table width="100%" border="0" cellspacing="0" cellpadding="0"
									bgcolor="#e9f2f7">
									<tr height="35">
										<td>
											&nbsp;
										</td>
										<td></td>
									</tr>
								</table>
							</form>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</body>
</html>