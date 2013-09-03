<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.sq.sys.ApplicationDate"%>
<%@page import="java.util.Arrays"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>角色维护</title>
		<link type="text/css" rel="stylesheet" href="../css/style-home.css" />
		<link type="text/css" rel="stylesheet" href="../css/style-sys.css" />
	</head>
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
						<td class="from_btimg form_bt">
							<span>&nbsp;</span>
							<h1>
								角色明细
							</h1>
						</td>
					</tr>
					<tr>
						<td>
							<table align="center" border="0" width="100%" class="form_table">
								<tr id="tab">
									<td height="22" style="font-weight: bold" width="15%">
										角色名称
									</td>
									<td height="22" style="font-weight: bold" width="55%">
										备注
									</td>
									<td height="22" style="font-weight: bold" width="10%">
										创建人
									</td>
									<td height="22" style="font-weight: bold" width="10%">
										创建时间
									</td>
									<td height="22" style="font-weight: bold" width="10%">
										操作
									</td>
								</tr>
								<s:iterator value="#request.sqRoleList" id="rolelist">
									<tr id="tab">
										<td height="22" class="bgcolor1 fontcenter fontcolor3">
											<s:property value="roleName" />
										</td>
										<td height="22" class="bgcolor1 fontcenter fontcolor3">
											<s:property value="remark" />
										</td>
										<td height="22" class="bgcolor1 fontcenter fontcolor3">
											<s:property value="createUserTab.userName" />
										</td>
										<td height="22" class="bgcolor1 fontcenter fontcolor3">
											<s:date name="createDate" format="yyyy-MM-dd" />
										</td>
										<td height="22" class="bgcolor1 fontcenter fontcolor3">
											<img src="../images/edt.gif" width="16" height="16" /><a href="roleupdatequery.shtml?sqRole.roleId=<s:property value="roleId"/>" class="form_font">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;
											<img src="../images/del.gif" width="16" height="16" /><a href="roledelete.shtml?sqRole.roleId=<s:property value="roleId"/>" class="form_font">删除</a>
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
						<td align="center" width="30%">
							&nbsp;
						</td>
						<td width="70%" align="right">
							&nbsp;
						</td>
					</tr>
				</table>
			</div>
		</div>
	</body>
</html>