<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.sq.sys.ApplicationDate"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.Map"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	ApplicationDate.getRequestData("SYS_PARA_DATA", Arrays
			.asList("sq_user_tabuser_sex"), request,
			"sq_user_tabuser_sex");

	ApplicationDate.getRequestData("SYS_PARA_DATA", Arrays
			.asList("sq_user_tabstatus"), request, "sq_user_tabstatus");
	ApplicationDate.getRequestData("SYS_PARA_DATA", Arrays
			.asList("sq_user_tablevel"), request, "sq_user_tablevel");
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>员工信息修改</title>
		<link type="text/css" rel="stylesheet" href="../css/style-home.css" />
		<link type="text/css" rel="stylesheet" href="../css/style-sys.css" />
	</head>
	<body>
		<div class="right">
			<div class="right_bt">
				<h1 class="right_bth1">
					员工个人详细信息
				</h1>
			</div><br/><br/>
			<form action="updateuser.shtml" method="post" name="jform" id="jform">
				<table border="0" width="100%" class="form_table_l">
					<tr>
						<td height="32" style="font-weight: bold; text-align: right">
							员工编号：
						</td>
						<td height="32" class="bgcolor1 fontleft" align="center">
							<s:property value="sqUserTab.userId" />
						</td>
						<td height="32" style="font-weight: bold; text-align: right">
							员工姓名：
						</td>
						<td height="32" class="bgcolor1 fontleft">
							<s:property value="sqUserTab.userName" />
						</td>
					</tr>
					<tr>
						<td height="32" style="font-weight: bold; text-align: right">
							性别：
						</td>
						<td height="32" class="bgcolor1 fontleft">
							<s:iterator value="#request.sq_user_tabuser_sex" id="column">
								<s:if test="#column.key==#request.sqUserTab.userSex">
									<s:property value="value" />
								</s:if>
							</s:iterator>
						</td>
						<td height="32" style="font-weight: bold; text-align: right">
							生日：
						</td>
						<td height="32" class="bgcolor1 fontleft">
							<s:date name="sqUserTab.birthday" format="yyyy-MM-dd" />
						</td>
					</tr>
					<tr>
						<td height="32" style="font-weight: bold; text-align: right">
							所属部门：
						</td>
						<td height="32" class="bgcolor1 fontleft">
							<s:property value="sqUserTab.sqDeptTab.deptName" />
						</td>
						<td height="32" style="font-weight: bold; text-align: right">
							所属项目组：
						</td>
						<td height="32" class="bgcolor1 fontleft">
							<s:property value="sqUserTab.sqGroupTab.groupName" />
						</td>
					</tr>
					<tr>
						<td height="32" style="font-weight: bold; text-align: right">
							员工岗位：
						</td>
						<td height="32" class="bgcolor1 fontleft">
							<s:iterator value="#request.sq_user_tablevel" id="column">
								<s:if test="#column.key==#request.sqUserTab.level">
									<s:property value="value" />
								</s:if>
							</s:iterator>
						</td>
						<td height="32" style="font-weight: bold; text-align: right">
							员工职级：
						</td>
						<td height="32" class="bgcolor1 fontleft">
							<s:property value="sqUserTab.sqOfficeTab.officeName" />
						</td>
					</tr>
					<tr>
						<td height="32" style="font-weight: bold; text-align: right">
							主要技能：
						</td>
						<td height="32" class="bgcolor1 fontleft">
							<s:property value="sqUserTab.sqJobTab.jobName" />
						</td>
						<td height="32" style="font-weight: bold; text-align: right">
							从业年份：
						</td>
						<td height="32" class="bgcolor1 fontleft">
							<s:date name="sqUserTab.practitionersYear" format="yyyy-MM-dd" />
						</td>
					</tr>
					<tr>
						<td height="32" style="font-weight: bold; text-align: right">
							手机号码：
						</td>
						<td height="32" class="bgcolor1 fontleft">
							<s:property value="sqUserTab.mobile" />
						</td>
						<td height="32" style="font-weight: bold; text-align: right">
							E-MAIL：
						</td>
						<td height="32" class="bgcolor1 fontleft">
							<s:property value="sqUserTab.email" />
						</td>
					</tr>
					<tr>
						<td height="32" style="font-weight: bold; text-align: right">
							入职年份：
						</td>
						<td height="32" class="bgcolor1 fontleft">
							<s:date name="sqUserTab.enterYear" format="yyyy-MM-dd" />
						</td>
						<td height="32" style="font-weight: bold; text-align: right">
							状态：
						</td>
						<td height="32" class="bgcolor1 fontleft">
							<s:iterator value="#request.sq_user_tabstatus" id="column">
								<s:if test="#column.key==#request.sqUserTab.status">
									<s:property value="value" />
								</s:if>
							</s:iterator>
						</td>
					</tr>
					<tr>
						<td height="32" style="font-weight: bold; text-align: right">
							备注：
						</td>
						<td height="32" class="bgcolor1 fontleft" colspan="3">
							<s:property value="sqUserTab.remark" />
						</td>
					</tr>
				</table>
				<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#e9f2f7">
				<tr height="35">
					<td>
						&nbsp;
					</td>
					<td></td>
				</tr>
			</table>
			</form>
		</div>
	</body>
</html>