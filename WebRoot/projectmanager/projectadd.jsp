<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.sq.sys.ApplicationDate"%>
<%@page import="java.util.Arrays"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>项目信息新增</title>
		<link type="text/css" rel="stylesheet" href="../css/style-home.css" />
		<link type="text/css" rel="stylesheet" href="../css/style-sys.css" />
		<script type="text/javascript" src="../js/public.js"></script>
		<script type="text/javascript" src="../js/jquery.js"></script>
		<jsp:include page="../js/index.jsp"></jsp:include>
		<script type="text/javascript" src="js/projectadd.js"></script>
	</head>
<%
	ApplicationDate.getRequestData("SYS_PARA_DATA" , Arrays.asList("sq_system_paramodel") , request , "sq_system_paramodel" );
	ApplicationDate.getRequestData("SYS_PARA_DATA" , Arrays.asList("sq_project_infostatus") , request , "sq_project_infostatus" );
%>
	<body>
	<div class="right">
			<div class="right_bt">
				<h1 class="right_bth1">
					项目信息新增
				</h1>
			</div>
			<form action="addproject.shtml" method="post" name="jform" id="jform">
				<div class="right_centerwk">
								<table border="0" width="100%" class="form_table_l">
     <tr>
        <td height="32" style="font-weight: bold; text-align: right">项目名称：</td>
        <td height="32" class="bgcolor1 fontleft" colspan="3">
        	<input type="text" value="<s:property value="sqProjectInfo.projectName"/>" name="sqProjectInfo.projectName" onblur="isCheckMaxLenth(this)" maxlength="100" size="40">
        </td>
      </tr>
      <tr>
        <td height="32" style="font-weight: bold; text-align: right">项目经理：</td>
        <td height="32" class="bgcolor1 fontleft">
        	<input type="text" value="" name="userId"  id="openpopup" readonly="readonly" >
        	<input type="hidden" value="" name="sqProjectInfo.sqUserTab.userId">
        </td>
        <td height="32" style="font-weight: bold; text-align: right">所属项目组：</td>
        <td height="32" class="bgcolor1 fontleft">
        	<s:select name="sqProjectInfo.sqGroupTab.groupNo" theme="simple" list="#request.grouplist" listKey="groupNo" listValue="groupName"></s:select>
        </td>
      </tr>
       <tr>
        <td height="32" style="font-weight: bold; text-align: right">计划开始时间：</td>
        <td height="32" class="bgcolor1 fontleft">
        	<input type="text" id="datepick1" maxlength="10" onfocus="$(this).calendar()" value="<s:date name="sqProjectInfo.startDate" format="yyyy-MM-dd"/>" name="sqProjectInfo.startDate"></td>
        <td height="32" style="font-weight: bold; text-align: right">计划结束时间：</td>
        <td height="32" class="bgcolor1 fontleft">
        <input type="text" id="datepick2" maxlength="10" onfocus="$(this).calendar()" value="<s:date name="sqProjectInfo.advanceDate" format="yyyy-MM-dd"/>" name="sqProjectInfo.advanceDate"></td>
      </tr>
      <tr>
        <td height="32" style="font-weight: bold; text-align: right">项目状态：</td>
        <td height="32" class="bgcolor1 fontleft">
        	<s:select name="sqProjectInfo.status" theme="simple" list="#request.sq_project_infostatus"></s:select>
        </td>
        <td height="32" style="font-weight: bold; text-align: right">项目模式：</td>
        <td height="32" class="bgcolor1 fontleft">
        	<s:select theme="simple" list="#request.sq_system_paramodel" headerKey="-1" headerValue="——请选择——"></s:select>
        </td>
      </tr>
      <tr>
        <td height="32" style="font-weight: bold;" colspan="2">项目摘要信息</td>
        <td height="32" style="font-weight: bold;" colspan="2">项目成员管理</td>
      </tr>
      <tr>
        <td class="bgcolor1 fontcenter" colspan="2">
        	<textarea rows="15" cols="40" name="sqProjectInfo.resume" onblur="isCheckMaxLenth(this)" maxlength="100"><s:property value="sqProjectInfo.resume"/></textarea>
        </td>
        <td height="40" colspan="2" class="bgcolor1 fontcenter">
	      <table width="100%" border="0" height="250px" align="center">
	    <tr>
	      <td width="50%" rowspan="10"><s:select theme="simple" list="#request.listUser" multiple="true" id="select1" name="select1" cssStyle="width: 150px; height: 250px;" listKey="userId" listValue="userName"></s:select></td>
	      <td width="10%">&nbsp;</td>
	      <td width="40%">管理组：</td>
        </tr>
	    <tr>
	      <td>
	      <div style="text-align: center;width:70px;">
				<a class="btn" id="button1">
				<cite>》》</cite></a>
				</div></td>
	      <td rowspan="4"><select multiple="multiple" id="select2" style="width: 90px; height: 100px;">
			    </select></td>
        </tr>
	    <tr>
	      <td>&nbsp;</td>
        </tr>
	    <tr>
	      <td>
	      <div style="text-align: center;width:70px;">
				<a class="btn" id="button2" >
				<cite>《《</cite></a>
				</div></td>
        </tr>
	    <tr>
	      <td>&nbsp;</td>
        </tr>
	    <tr>
	      <td>&nbsp;</td>
	      <td>成员组：</td>
        </tr>
	    <tr>
	      <td>
	      <div style="text-align: center;width:70px;">
				<a class="btn" id="button3" >
				<cite>》》</cite></a>
				</div>
	      </td>
	      <td rowspan="4"><select multiple="multiple" id="select3" style="width: 90px; height: 100px;">
			    </select></td>
        </tr>
	    <tr>
	      <td>&nbsp;</td>
        </tr>
	    <tr>
	      <td>
	      <div style="text-align: center;width:70px;">
				<a class="btn" id="button4"  >
				<cite>《《</cite></a>
				</div>
	      </td>
        </tr>
	    <tr>
	      <td>&nbsp;</td>
        </tr>
      </table>
        </td>
      </tr>
      <tr>
      <td colspan="4" height="52" style="text-align: center" align="center">
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
			<input type="hidden" name="mgnuser" value="">
    	<input type="hidden" name="memuser" value="">
    </form>
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
	</body>
</html>