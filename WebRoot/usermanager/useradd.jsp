<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.sq.sys.ApplicationDate"%>
<%@page import="java.util.Arrays"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    ApplicationDate.getRequestData("SYS_PARA_DATA" , Arrays.asList("sq_user_tabuser_sex") , request , "sq_user_tabuser_sex" );
    
    ApplicationDate.getRequestData("SYS_PARA_DATA" , Arrays.asList("sq_user_tablevel") , request , "sq_user_tablevel" );
    
    ApplicationDate.getRequestData("OFFICE_DATA" , Arrays.asList("1") , request , "sq_office_tab" );
    
    ApplicationDate.getRequestData("JOB_DATA" , Arrays.asList("1") , request , "sq_job_tab" );
    
    ApplicationDate.getRequestData("DEPT_DATA" , Arrays.asList("1") , request , "sq_dept_tab" );
    
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>员工信息新增</title>
		<link type="text/css" rel="stylesheet" href="../css/style-home.css" />
		<link type="text/css" rel="stylesheet" href="../css/style-sys.css" />
		<script type="text/javascript" src="../js/public.js"></script>
		<script type="text/javascript" src="../js/jquery.js"></script>
		<jsp:include page="../js/index.jsp"></jsp:include>
		<script type="text/javascript" src="js/useradd.js"></script>
	</head>
	<body>
		<div class="right">
			<div class="right_bt">
				<h1 class="right_bth1">
					新增员工详细信息
				</h1>
			</div>
			<form action="useradd.shtml" method="post" name="jform" id="jform">
				<div class="right_centerwk">
					<table width="100%" border="0" width="100%" class="form_table_l">
						<tr>
							<td height="32" style="font-weight: bold; text-align: right">
								员工姓名：
							</td>
							<td height="32" class="bgcolor1 fontleft fontbold" colspan="3">
								<input type="text" name="sqUserTab.userName"
									value="<s:property value="sqUserTab.userName"/>"
									onblur="isCheckMaxLenth(this)" maxlength="20">
							</td>
						</tr>
						<tr>
							<td height="32" style="font-weight: bold; text-align: right">
								员工拼音：
							</td>
							<td height="32" class="bgcolor1 fontleft fontbold">
								<input type="text" name="sqUserTab.pyName"
									value="<s:property value="sqUserTab.pyName"/>"
									onblur="isCheckMaxLenth(this)" maxlength="50">
							</td>
							<td height="32" style="font-weight: bold; text-align: right">
								性别：
							</td>
							<td height="32" class="bgcolor1 fontleft fontbold">
								<s:select name="sqUserTab.userSex" theme="simple"
									list="#request.sq_user_tabuser_sex" listKey="key"
									listValue="value"></s:select>
							</td>
						</tr>
						<tr>
							<td height="32" style="font-weight: bold; text-align: right">
								入职年份：
							</td>
							<td height="32" class="bgcolor1 fontleft fontbold">
								<input type="text" id="datepick2" maxlength="10" onfocus="$(this).calendar()"
									name="sqUserTab.enterYear" maxlength="10" onfocus="$(this).calendar()"
									value="<s:date name="sqUserTab.enterYear" format="yyyy-MM-dd"/>">
							</td>
							<td height="32" style="font-weight: bold; text-align: right">
								生日：
							</td>
							<td height="32" class="bgcolor1 fontleft fontbold">
								<input type="text" id="datepick1" maxlength="10" onfocus="$(this).calendar()" maxlength="10" onfocus="$(this).calendar()"
									value="<s:date name="sqUserTab.birthday" format="yyyy-MM-dd"/>"
									name="sqUserTab.birthday">
							</td>
						</tr>
						<tr>
							<td height="32" style="font-weight: bold; text-align: right">
								所属部门：
							</td>
							<td height="32" class="bgcolor1 fontleft fontbold">
								<s:select name="sqUserTab.sqDeptTab.deptno" theme="simple"
									list="#request.sq_dept_tab" listKey="key" listValue="value"></s:select>
							</td>
							<td height="32" style="font-weight: bold; text-align: right">
								所属项目组：
							</td>
							<td height="32" class="bgcolor1 fontleft fontbold">
								<s:select name="sqUserTab.sqGroupTab.groupNo" theme="simple"
									list="#request.grouplist" listKey="groupNo"
									listValue="groupName"></s:select>
							</td>
						</tr>
						<tr>
							<td height="32" style="font-weight: bold; text-align: right">
								员工岗位：
							</td>
							<td height="32" class="bgcolor1 fontleft fontbold">
								<s:select name="sqUserTab.level" theme="simple"
									list="#request.sq_user_tablevel" listKey="key"
									listValue="value" onchange="onChanageOffice()"></s:select>
							</td>
							<td height="32" style="font-weight: bold; text-align: right">
								员工职级：
							</td>
							<td height="32" class="bgcolor1 fontleft fontbold">
								<select name="sqUserTab.sqOfficeTab.officeId"></select>
							</td>
						</tr>
						<tr>
							<td height="32" style="font-weight: bold; text-align: right">
								主要技能：
							</td>
							<td height="32" class="bgcolor1 fontleft fontbold">
								<s:select name="sqUserTab.sqJobTab.jobId" theme="simple"
									list="#request.sq_job_tab" listKey="key" listValue="value"></s:select>
							</td>
							<td height="32" style="font-weight: bold; text-align: right">
								从业年份：
							</td>
							<td height="32" class="bgcolor1 fontleft fontbold">
								<input type="text" id="datepick3" maxlength="10" onfocus="$(this).calendar()"
									name="sqUserTab.practitionersYear"
									value="<s:date name="sqUserTab.practitionersYear" format="yyyy-MM-dd"/>">
							</td>
						</tr>
						<tr>
							<td height="32" style="font-weight: bold; text-align: right">
								手机号码：
							</td>
							<td height="32" class="bgcolor1 fontleft fontbold">
								<input type="text" name="sqUserTab.mobile"
									value="<s:property value="sqUserTab.mobile"/>"
									onblur="isCheckMaxLenth(this)" maxlength="12">
							</td>
							<td height="32" style="font-weight: bold; text-align: right">
								E-MAIL：
							</td>
							<td height="32" class="bgcolor1 fontleft fontbold">
								<input type="text" name="sqUserTab.email"
									value="<s:property value="sqUserTab.email"/>"
									onblur="isCheckMaxLenth(this)" maxlength="28">
							</td>
						</tr>
						<tr>
							<td height="32" style="font-weight: bold; text-align: right">
								备注：
							</td>
							<td height="32" class="bgcolor1 fontleft fontbold" colspan="3">
								<textarea rows="5" cols="40" name="sqUserTab.remark" onblur="isCheckMaxLenth(this)" maxlength="100"><s:property value="sqUserTab.remark" /></textarea>
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
									</div>
									</div>
							</td>
						</tr>
					</table>
				</div>
			</form>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#e9f2f7">
					<tr height="35">
						<td>
							&nbsp;
						</td>
						<td></td>
					</tr>
				</table>
		</div>
	</body>
</html>