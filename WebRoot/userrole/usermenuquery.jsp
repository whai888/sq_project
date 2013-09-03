<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.sq.sys.ApplicationDate"%>
<%@page import="java.util.Arrays"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>员工菜单分配</title>
		<link type="text/css" rel="stylesheet" href="../css/style-home.css" />
		<link type="text/css" rel="stylesheet" href="../css/style-sys.css" />
		<script type="text/javascript" src="../js/public.js"></script>
		<script type="text/javascript" src="../js/jquery.js"></script>
		<script type="text/javascript" src="js/usermenuquery.js"></script>
		<script type="text/javascript" src="../js/MzTreeView10.js"></script>
	</head>
	<body>
		<div class="right">
			<div class="right_bt">
				<h1 class="right_bth1">
					员工菜单分配
				</h1>
			</div>
			<div class="right_centerwk">
				<table border="0" width="100%" class="form_table_l">

					<tr>
						<td height="32" style="font-weight: bold; text-align: right" width="300px;">
							员工姓名：
						</td>
						<td height="32" class="bgcolor1 fontleft fontbold">
							<s:property value="#request.sqUserTab.userName" />
						</td>
					</tr>
				</table>
				<br />
				<br />
				<form action="usermenuconfirm.shtml" method="post" name="assignForm"
					id="assignForm">
					<div style="margin-left: auto; margin-right: auto; width: 1000px;">
						<div id="treediv"
							style="border: 1px solid #6889EA; width: 400px; height: auto; float: left; min-height: 400px;">
						</div>
						<div
							style="border: width :   170px; height: auto; float: left; min-height: 300px; padding-top: 100px; padding-left: 30px; padding-right: 30px;">
								<div style="text-align: center;">
									<div style="margin: 0 auto;">
								<a class="btn" onClick="onAssign()" >
								<cite>分配权限</cite></a>
								</div>
							<br />
							<br />
							<br />
							<br />
							<br />
							<div style="text-align: center;width:120px;">
								<a class="btn" onClick="onRemove()" >
								<cite>解除权限</cite></a>
								</div></div>
						</div>
						<div id="roleMenuList"
							style="border: 1px solid #6889EA; width: 400px; height: auto; float: left; min-height: 400px;">
						</div>
					</div>
					<input type="hidden" name="sqRole.roleId"
						value="<s:property value="sqRole.roleId"/>">
					<input type="hidden" name="flag" value="2">
					<input type="hidden" name="sqUserTab.userId"
						value="<s:property value="#request.sqUserTab.userId"/>">
				</form>
				<script type="text/javascript">
     <%=request.getAttribute("tree") %>
     <%=request.getAttribute("roleTree") %>
     </script>
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