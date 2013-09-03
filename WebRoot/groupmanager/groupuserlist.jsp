<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.sq.sys.ApplicationDate"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Arrays"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>项目组员工信息</title>
		<link type="text/css" rel="stylesheet" href="../css/style-home.css" />
		<link type="text/css" rel="stylesheet" href="../css/style-sys.css" />
		<script type="text/javascript" src="../js/public.js"></script>
		<script type="text/javascript" src="../js/jquerybak.js"></script>
		<jsp:include page="../js/index.jsp"></jsp:include>
		<script type="text/javascript" src="js/groupuserlist.js"></script>
	</head>
	<body>
		<div class="right">
			<div class="right_bt">
				<h1 class="right_bth1">
					项目组员工查询
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
										<td height="32" style="font-weight: bold; text-align: right">
											项目组名称：
										</td>
										<td height="32" class="bgcolor1 fontleft fontbold">
											<s:select name="sqUserTab.sqGroupTab.groupNo" theme="simple"
												list="#request.grouplist" listKey="groupNo"
												listValue="groupName" headerKey="-99" headerValue="——全部——"></s:select>
										</td>
										<td height="32"  style="font-weight: bold; text-align: right">
											员工姓名：
										</td>
										<td height="32" class="bgcolor1 fontleft fontbold">
											<input type="text" name="sqUserTab.userName"
												value="<s:property value="sqUserTab.userName"/>"
												onblur="isCheckMaxLenth(this)" maxlength="20">
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
							</td>
						</tr>
					</table>
				</div>
				<input type="hidden" name="HQL" value="GROUP_USER_LIST" />
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
							<table align="center" border="0" width="100%" class="form_table" id="comm">
								<tr id="tab">
									<td height="22" style="font-weight:bold">
										员工姓名
									</td>
									<td height="22" style="font-weight:bold">
										入职日期
									</td>
									<td height="22" style="font-weight:bold">
										所属部门
									</td>
									<td height="22" style="font-weight:bold">
										项目组
									</td>
									<td height="22" style="font-weight:bold" >
										员工职级
									</td>
									<td height="22" style="font-weight:bold" >
										主要技能
									</td>
									<td height="22" style="font-weight:bold">
										联系方式
									</td>
									<td height="22" style="font-weight:bold" >
										EMAIL
									</td>
									<td height="22" style="font-weight:bold">
										状态
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					bgcolor="#e9f2f7" id="comm">
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