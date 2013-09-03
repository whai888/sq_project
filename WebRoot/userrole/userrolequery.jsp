<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.sq.sys.ApplicationDate"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="com.sq.model.vo.SqRole"%>
<%@page import="com.sq.model.vo.SqUserRole"%>
<%@page import="com.sq.model.vo.SqUserTab"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>员工角色分配</title>
		<link type="text/css" rel="stylesheet" href="../css/style-home.css" />
		<link type="text/css" rel="stylesheet" href="../css/style-sys.css" />
		<script type="text/javascript" src="../js/public.js"></script>
		<script type="text/javascript" src="../js/jquery.js"></script>
		<script type="text/javascript" src="../userrole/js/userrolequery.js"></script>
	</head>
	<%
		//员工所拥有的角色
		List userRoleList = (List) request.getAttribute("userRoleList");
		//系统所有的角色
		List sqRoleList = (List) request.getAttribute("sqRoleList");
		SqUserTab sqUserTab = (SqUserTab) request.getAttribute("sqUserTab");
	%>
	<body>
		<div class="right">
			<div class="right_bt">
				<h1 class="right_bth1">
					角色管理
				</h1>
			</div>
			<div class="right_centerwk">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="form_btimg form_bt">
							<span>&nbsp;</span>
							<h1>
								员工角色分配
							</h1>
						</td>
					</tr>
					<tr>
						<td>
							<form action="userroleconfirm.shtml" method="post" name="jform" id="jform">
								<table border="0" width="100%" class="form_table_l">
									<tr>
										<td height="32" style="font-weight:bold; text-align:right">
											员工姓名：
										</td>
										<td height="32" class="bgcolor1 fontleft fontbold"
											width="300px;">
											<%=sqUserTab.getUserName()%>
										</td>
										<td height="52" style="text-align:center">
										      	<div style="text-align: center;">
									<div style="width:200px;margin: 0 auto;">
									<a class="btn" onClick="onSub()" style="padding-right: 60px;">
									<cite>确&nbsp;&nbsp;&nbsp;定</cite></a>
								<a class="btn" onClick="document.jform.reset();">
									<cite>重&nbsp;&nbsp;&nbsp;置</cite></a>
									</div></div>
										</td>
									</tr>
									<%
										for (int i = 0; i < sqRoleList.size(); i++) {
											SqRole sqRole = (SqRole) sqRoleList.get(i);
											boolean flag = false;
											for (int j = 0; j < userRoleList.size(); j++) {
												SqUserRole sqUserRole = (SqUserRole) userRoleList.get(j);
												if (sqUserRole.getId().getSqRole().getRoleId().equals(
														sqRole.getRoleId()))
													flag = true;
											}
									%>
									<tr>
										<td height="32" style="font-weight:bold; text-align:right">
											<input type="checkbox" name="treeData"
												value="<%=sqRole.getRoleId()%>"
												<%if (flag)
					out.print("checked='checked'");%>>
										</td>
										<td height="32" class="bgcolor1 fontleft" colspan="2">
											<%=sqRole.getRoleName()%>
										</td>
									</tr>
									<%
										}
									%>
								</table>
								<input type="hidden" name="sqUserTab.userId"
									value="<%=sqUserTab.getUserId()%>">
							</form>
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