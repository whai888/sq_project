<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.sq.sys.ApplicationDate"%>
<%@page import="java.util.Arrays"%>
<%@page import="com.sq.model.vo.SqUserTab"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>项目组信息</title>
		<link type="text/css" rel="stylesheet" href="../css/style-home.css" />
		<link type="text/css" rel="stylesheet" href="../css/style-sys.css" />
	</head>
	<%
		ApplicationDate.getRequestData("SYS_PARA_DATA", Arrays
				.asList("sq_group_tabstatus"), request,
				"sq_group_tabstatus");
		SqUserTab sqUserTab = (SqUserTab)session.getAttribute("sqUserTab");
	%>
	<body>
		<div class="right">
			<div class="right_bt">
				<h1 class="right_bth1">
					项目组管理
				</h1>
			</div>
			<div class="right_centerwk">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="form_btimg form_bt">
							<span>&nbsp;</span>
							<h1>
								项目组明细
							</h1>
						</td>
					</tr>
					<tr>
						<td>
							<table align="center" border="0" width="100%" class="form_table">
								<tr>
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
								<s:iterator value="#request.groupMap" id="groupTab">
									<tr>
										<td height="22" class="bgcolor1 fontcenter fontcolor3">
										<%if(sqUserTab.isSysMagnUser("006")){ %>
										<img src="../images/edt.gif" width="16" height="16" />
											<a class="form_font" href="groupfindvo.shtml?sqGroupTab.groupNo=<s:property value="key.groupNo"/>">
												<s:property value="key.groupName" /> </a>
										<%}else{ %>
											<s:property value="key.groupName" />
										<%} %>
										</td>
										<td height="22" class="bgcolor1 fontcenter fontcolor3">
											<s:date name="key.succDate" format="yyyy-MM-dd" />
										</td>
										<td height="22" class="bgcolor1 fontcenter fontcolor3">
											<s:property value="key.sqDeptTab.deptName" />
										</td>
										<td height="22" class="bgcolor1 fontcenter fontcolor3">
											<s:property value="key.sqUserTab.userName" />
										</td>
										<td height="22" class="bgcolor1 fontcenter fontcolor3">
											<s:property value="#request.sq_group_tabstatus[key.status]" />
										</td>
									</tr>
								</s:iterator>
							</table>
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
			</div>
		</div>
	</body>
</html>