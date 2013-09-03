<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.sq.sys.ApplicationDate"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Arrays"%>
<%@page import="com.sq.model.vo.SqUserTab"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	ApplicationDate.getRequestData("JOB_DATA", Arrays.asList("1"),
			request, "sq_job_tab");

	ApplicationDate.getRequestData("DEPT_DATA", Arrays.asList("1"),
			request, "sq_dept_tab");
	SqUserTab sqUserTab = (SqUserTab)session.getAttribute("sqUserTab");
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>员工信息</title>
		<link type="text/css" rel="stylesheet" href="../css/style-home.css" />
		<link type="text/css" rel="stylesheet" href="../css/style-sys.css" />
		<script type="text/javascript" src="../js/public.js"></script>
		<script type="text/javascript" src="../js/jquerybak.js"></script>
		<jsp:include page="../js/index.jsp"></jsp:include>
		<script type="text/javascript" src="js/userlist.js"></script>
	</head>
	<body>
		<div class="right">
			<div class="right_bt">
				<h1 class="right_bth1">
					员工信息查询
				</h1>
			</div>
			<form action="" method="post" name="jform" id="jform">
				<div class="right_centerwk">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="form_btimg form_bt">
								<span>&nbsp;</span>
								<h1>
									查 询
								</h1>
							</td>
						</tr>
						<tr>
							<td>
								<table border="0" width="100%" class="form_table_l">
									<tr>
										<td style="font-weight: bold; text-align: right">
											<label>
												入职时间：
											</label>
										</td>
										<td colspan="3">
											<input type="text" name="sqUserTab.enterYear"
												maxlength="10" onfocus="$(this).calendar()" value="2000-01-01" id="datepick1">
											至
											<input type="text" name="endYear" value=""
												maxlength="10" onfocus="$(this).calendar()" id="datepick2">
										</td>
										<td rowspan="3" style="text-align: center">
											<div style="text-align: center;">
									<div style="width:70px;margin: 0 auto;">
											<a class="btn" onClick="onSub('1')" >
											<cite>查&nbsp;&nbsp;&nbsp;询</cite></a>
											</div>
											</div>
										</td>
									</tr>
									<tr>
										<td style="font-weight: bold; text-align: right">
											部门：
										</td>
										<td>
											<s:select name="sqUserTab.deptno" theme="simple"
												list="#request.sq_dept_tab" listKey="key" listValue="value" headerKey="-99" headerValue="——请选择——"></s:select>
										</td>
										<td style="font-weight: bold; text-align: right">
											员工姓名：
										</td>
										<td>
											<input type="text" name="sqUserTab.userName"
												value="<s:property value="sqUserTab.userName"/>"
												onblur="isCheckMaxLenth(this)" maxlength="20">
									</tr>
									<tr>
										<td style="font-weight: bold; text-align: right">
											主要技能：
										</td>
										<td>
											<s:select name="sqUserTab.sqJobTab.jobId" theme="simple"
												list="#request.sq_job_tab" listKey="key" listValue="value" headerKey="-99" headerValue="——请选择——"></s:select>
										</td>
										<td style="font-weight: bold; text-align: right">
											状态：
										</td>
										<td>
											<input type="checkbox" name="sqUserTab.status1" value="1"
												checked="checked">
											离职
											<input type="checkbox" name="sqUserTab.status0" value="0"
												checked="checked">
											正常
											<input type="checkbox" name="sqUserTab.status2" value="2"
												checked="checked">
											状态异常
											<input type="checkbox" name="sqUserTab.status3" value="3"
												checked="checked">
											密码待生效
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
				<input type="hidden" name="HQL" value="USER_LIST" />
				<input type="hidden" name="flag" value="<%=sqUserTab.isSysMagnUser("001") %>" />
			</form>
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
							<table align="center" border="0" width="100%" class="form_table"
								id="comm">
								<tr id="tab">
									<td height="22" style="font-weight:bold" width="8%">
										员工编号
									</td>
									<td height="22" style="font-weight:bold" width="8%">
										员工姓名
									</td>
									<td height="22" style="font-weight:bold" width="8%">
										入职日期
									</td>
									<td height="22" style="font-weight:bold" width="10%">
										所属部门
									</td>
									<td height="22" style="font-weight:bold" width="10%">
										项目组
									</td>
									<td height="22" style="font-weight:bold" width="10%">
										员工职级
									</td>
									<td height="22" style="font-weight:bold" width="10%">
										主要技能
									</td>
									<td height="22" style="font-weight:bold" width="10%">
										联系方式
									</td>
									<td height="22" style="font-weight:bold" width="10%">
										EMAIL
									</td>
									<td height="22" style="font-weight:bold" width="8%">
										状态
									</td>
									<td height="22" style="font-weight:bold" width="8%">
										操作
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#e9f2f7">
					<tr height="35">
						<td align="right">
							<div id="pageTag"></div>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</body>
</html>
