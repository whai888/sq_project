<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.sq.sys.ApplicationDate"%>
<%@page import="java.util.Arrays"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt"       uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>项目报告查询</title>
		<link type="text/css" rel="stylesheet" href="../css/style-home.css" />
		<link type="text/css" rel="stylesheet" href="../css/style-sys.css" />
	</head>
<%
	ApplicationDate.getRequestData("SYS_PARA_DATA" , Arrays.asList("sq_workday_managertask_status") , request , "sq_workday_managertask_status" );
	ApplicationDate.getRequestData("SYS_PARA_DATA" , Arrays.asList("sq_project_stepwork_units") , request , "sq_project_stepwork_units" );
%>
	<body>
	<div class="right">
			<div class="right_bt">
				<h1 class="right_bth1">
					项目报告查询
				</h1>
			</div>
			<form action="projectadduser.shtml" method="post" name="jform" id="jform">
				<div class="right_centerwk">
					<table width="100%" border="0" width="100%" class="form_table_l">
     <tr>
        <td height="32" style="font-weight: bold; text-align: right" width="150px;">上报日期：</td>
        <td height="32" width="200px;">
        	<s:date name="sqWorkdayManager.workDate" format="yyyy-MM-dd"/>
        </td>
        <td height="32" style="font-weight: bold; text-align: right" width="150px;">日报提交人：</td>
        <td height="32" width="200px;">
        	<s:property value="sqWorkdayManager.sqUserTab.userName"/>
        </td>
        <td height="32" style="font-weight: bold; text-align: right" width="150px;">任务状态：</td>
        <td height="32" width="200px;">
        	<s:property value="#request.sq_workday_managertask_status[sqWorkdayManager.taskStatus]"/>
        </td>
      </tr>
      <tr>
        <td height="32" style="font-weight: bold; text-align: right" width="150px;">日报项目：</td>
        <td height="32" width="200px;">
        	<s:property value="sqWorkdayManager.sqProjectInfo.projectName"/>
        </td>
         <td height="32" style="font-weight: bold; text-align: right" width="150px;">日报任务：</td>
        <td height="32" width="200px;">
        	<s:property value="sqWorkdayManager.sqProjectStep.stepName"/>
        </td>
         <td height="32" style="font-weight: bold; text-align: right" width="150px;">完成比例：</td>
        <td height="32" width="200px;">
        	<s:property value="sqWorkdayManager.complePercen"/>%
        </td>
      </tr>
      <tr>
        <td height="32" style="font-weight: bold;" width="150px;" colspan="6">工作任务简要说明：</td>
      </tr>
      <tr>
        <td height="32" class="bgcolor1 fontbold" width="150px;"  colspan="6">
        	<textarea rows="5" cols="120" name="sqWorkdayManager.workContent" readonly="readonly"><s:property value="sqWorkdayManager.workContent"/></textarea>
        </td>
      </tr>
      <tr>
        <td height="32" style="font-weight: bold;" colspan="2">未完成工作：</td>
        <td height="32" style="font-weight: bold;" colspan="2">下日计划：</td>
      </tr>
      <tr>
        <td height="32" colspan="2">
        	<textarea rows="5" cols="50" name="sqWorkdayManager.noWorkContent" readonly="readonly"><s:property value="sqWorkdayManager.noWorkContent"/></textarea>
        </td>
        <td height="32" colspan="2">
        	<textarea rows="5" cols="50" name="sqWorkdayManager.workNextPlan" readonly="readonly"><s:property value="sqWorkdayManager.workNextPlan"/></textarea>
        </td>
      </tr>
      <tr>
        <td height="32" style="font-weight: bold;" colspan="2">需协调解决问题：</td>
        <td height="32" style="font-weight: bold;" colspan="2">工作建议：</td>
      </tr>
      <tr>
        <td height="32" colspan="2">
        	<textarea rows="5" cols="50" name="sqWorkdayManager.discussProblem" readonly="readonly"><s:property value="sqWorkdayManager.discussProblem"/></textarea>
        </td>
        <td height="32" colspan="2">
        	<textarea rows="5" cols="50" name="sqWorkdayManager.workSug" readonly="readonly"><s:property value="sqWorkdayManager.workSug"/></textarea>
        </td>
      </tr>
       <tr>
        <td height="32" style="font-weight: bold;" width="150px;" colspan="6">附件：</td>
      </tr>
     <s:iterator value="#request.projectDocList">
      <tr>
        <td height="32" class="bgcolor1 fontbold" width="150px;" colspan="6"><a href="<s:property value="filePath"/>/<s:property value="fileName"/>"><s:property value="backfileName"/></a></td>
      </tr>
      </s:iterator>
    </table>
				</div>
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