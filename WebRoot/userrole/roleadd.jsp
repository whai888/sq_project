<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.sq.sys.ApplicationDate"%>
<%@page import="java.util.Arrays"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>角色新增</title>
		<link type="text/css" rel="stylesheet" href="../css/style-home.css" />
		<link type="text/css" rel="stylesheet" href="../css/style-sys.css" />
		<script type="text/javascript" src="../js/public.js"></script>
		<script type="text/javascript" src="../js/jquery.js"></script>
		<script type="text/javascript" src="js/roleadd.js"></script>
	</head>
	<body>
		<div class="right">
			<div class="right_bt">
				<h1 class="right_bth1">
					角色新增
				</h1>
			</div>
			<div class="right_centerwk">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="form_btimg form_bt">
							<span>&nbsp;</span>
							<h1>
								新增角色
							</h1>
						</td>
					</tr>
					<tr>
						<td>
							<form action="roleadd.shtml" method="post" name="jform"
								id="jform">
								<table border="0" width="100%" class="form_table_l">
									<tr>
										<td height="32" style="font-weight: bold; text-align: right">
											角色名称：
										</td>
										<td height="32" class="bgcolor1 fontleft fontbold">
											<input type="text" name="sqRole.roleName"
												value="<s:property value="sqRole.roleName"/>"
												onblur="isCheckMaxLenth(this)" maxlength="50">
										</td>
									</tr>
									<tr>
										<td height="32" style="font-weight: bold; text-align: right">
											备注：
										</td>
										<td height="32" class="bgcolor1 fontleft fontbold">
											<textarea rows="5" cols="40" name="sqRole.remark" onblur="isCheckMaxLenth(this)" maxlength="500"><s:property value="sqRole.remark" /></textarea>
										</td>
									</tr>
									<tr>
										<td colspan="4" height="52" style="text-align: center">
											<div style="text-align: center;">
									<div style="width:200px;margin: 0 auto;">
									<a class="btn" onClick="onSub()" style="padding-right: 60px;">
									<cite>确&nbsp;&nbsp;&nbsp;定</cite></a>
								<a class="btn" onClick="document.jform.reset();">
									<cite>重&nbsp;&nbsp;&nbsp;置</cite></a>
									</div></div>
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