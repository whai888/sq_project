<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.sq.sys.ApplicationDate"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Arrays"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	ApplicationDate.getRequestData("DEPT_DATA", Arrays.asList("1"), request, "sq_dept_tab");
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>部门员工信息</title>
		<link type="text/css" rel="stylesheet" href="../css/style-home.css" />
		<link type="text/css" rel="stylesheet" href="../css/style-sys.css" />
		<script type="text/javascript" src="../js/public.js"></script>
		<script type="text/javascript" src="../js/jquerybak.js"></script>
		<script type="text/javascript" src="js/deptgrouplist.js"></script>
	</head>
	<body>
		<div class="right">
			<div class="right_bt">
				<h1 class="right_bth1">
					部门项目组管理
				</h1>
			</div>
			<div class="right_centerwk">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="form_btimg form_bt">
							<span>&nbsp;</span>
							<h1>
								查询
							</h1>
						</td>
					</tr>
					<tr>
						<td>
							<table border="0" width="100%" class="form_table_l">
								<tr>
									<td height="32" style="font-weight: bold; text-align: right">
										部门：
									</td>
									<td height="32" class="bgcolor1 fontleft fontbold">
										<s:select name="sqGroupTab.sqDeptTab.deptNo" theme="simple"
											list="#request.sq_dept_tab" listKey="key" listValue="value" headerKey="-99" headerValue="——全部——"></s:select>
									</td>
									<td style="text-align: center">
								<div style="text-align: center;">
								<div style="width:70px;margin: 0 auto;">
									<a class="btn" onClick="onSub('1')" >
									<cite>查&nbsp;&nbsp;&nbsp;询</cite></a>
									</div>
									</div>
							</td>
								</tr>
							</table>
							<input type="hidden" name="HQL" value="DEPT_GROUP_LIST" />
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="right_centerwk">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="from_btimg form_bt">
						<span>&nbsp;</span>
						<h1>
							查询结果
						</h1>
					</td>
				</tr>
				<tr>
					<td>
						<table align="center" border="0" width="100%" class="form_table" id="comm">
							<tr id="tab">
									<td height="22" style="font-weight:bold">
										项目组名称
									</td>
									<td height="22" style="font-weight:bold">
										成立时间
									</td>
									<td height="22" style="font-weight:bold">
										所属部门
									</td>
									<td height="22" style="font-weight:bold">
										负责人
									</td>
									<td height="22" style="font-weight: bold">
										项目组状态
									</td>
								</tr>
						</table>
					</td>
				</tr>
			</table>
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				bgcolor="#e9f2f7">
				<tr height="35">
					<td width="70%" align="right">
						<div id="pageTag"></div>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>