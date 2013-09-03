<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.sq.sys.ApplicationDate"%>
<%@page import="java.util.Arrays"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>角色员工查询</title>
		<link type="text/css" rel="stylesheet" href="../css/style-home.css" />
		<link type="text/css" rel="stylesheet" href="../css/style-sys.css" />
		<script type="text/javascript" src="../js/public.js"></script>
		<script type="text/javascript" src="../js/jquery.js"></script>
		<script type="text/javascript" src="js/usermenulist.js"></script>
	</head>
	<%
		ApplicationDate.getRequestData("SYS_PARA_DATA", Arrays
				.asList("sq_user_tabstatus"), request, "sq_user_tabstatus");
	%>
	<body>
		<body>
			<div class="right">
				<div class="right_bt">
					<h1 class="right_bth1">
						角色员工查询
					</h1>
				</div>
				<form action="roleuserquery.shtml" method="post" name="jform"
					id="jform">
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
											<td height="32" style="font-weight: bold; text-align: right">
												角色名称：
											</td>
											<td height="32" class="bgcolor1 fontleft fontbold"
												width="400px;">
												<s:select name="sqRole.roleId" theme="simple"
													list="#request.roleList" listKey="roleId"
													listValue="roleName"></s:select>
											</td>
											<td height="32" style="text-align: center">
												<div style="text-align: center;">
									<div style="width:70px;margin: 0 auto;">
												<a class="btn" onClick="onQuery()" >
												<cite>查&nbsp;&nbsp;&nbsp;询</cite></a>
												</div></div>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</div>
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
										<td height="22" style="font-weight:bold"
											width="10%">
											入职日期
										</td>
										<td height="22" style="font-weight:bold"
											width="10%">
											员工姓名
										</td>
										<td height="22" style="font-weight:bold"
											width="20%">
											所属部门
										</td>
										<td height="22" style="font-weight:bold"
											width="15%">
											岗位
										</td>
										<td height="22" style="font-weight:bold"
											width="10%">
											联系方式
										</td>
										<td height="22" style="font-weight:bold"
											width="15%">
											EMAIL
										</td>
										<td height="22" style="font-weight:bold"
											width="10%">
											状态
										</td>
										<td height="22" style="font-weight:bold"
											width="10%">
											操作
										</td>
									</tr>
									<s:if
										test="#request.roleUserList !=null && #request.roleUserList.size()!=0">
										<s:iterator value="#request.roleUserList" status="spin1">
											<tr>
												<td height="22" class="bgcolor1 fontcenter fontcolor3">
													<s:date name="enterYear" format="yyyy-MM-dd" />
												</td>
												<td height="22" class="bgcolor1 fontcenter fontcolor3">
													<s:property value="userName" />
												</td>
												<td height="22" class="bgcolor1 fontcenter fontcolor3">
													<s:property value="sqDeptTab.deptName" />
												</td>
												<td height="22" class="bgcolor1 fontcenter fontcolor3">
													<s:property value="sqJobTab.jobName" />
												</td>
												<td height="22" class="bgcolor1 fontcenter fontcolor3">
													<s:property value="mobile" />
												</td>
												<td height="22" class="bgcolor1 fontcenter fontcolor3">
													<s:property value="email" />
												</td>
												<td height="22" class="bgcolor1 fontcenter fontcolor3">
													<s:property value="#request.sq_user_tabstatus[status]" />
												</td>
												<td height="22" class="bgcolor1 fontcenter fontcolor3">
													<img src="../images/edt.gif" width="16" height="16" />
													<a href="usermenuquery.shtml?sqUserTab.userId=<s:property value="userId"/>&sqRole.roleId=<s:property value="sqRole.roleId"/>" class="form_font">菜单权限分配</a>
												</td>
											</tr>
										</s:iterator>
									</s:if>
								</table>
							</td>
						</tr>
					</table>
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						bgcolor="#e9f2f7">
						<tr height="35">
							<td align="right">
								&nbsp;
							</td>
						</tr>
					</table>
				</div>
			</div>
			<form action="usermenuquery.shtml" method="post" name="jmform"
				id="jmform">
				<input type="hidden" name="sqRole.roleId" value="">
				<input type="hidden" name="sqUserTab.userId"
					value="<s:property value="userId"/>">
			</form>
		</body>
</html>