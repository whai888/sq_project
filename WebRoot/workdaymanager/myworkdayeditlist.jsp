<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.sq.sys.ApplicationDate"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="com.sq.model.vo.SqWorkdayManager"%>
<%@page import="com.sq.tools.Public"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Set"%>
<%@page import="com.sq.model.vo.SqDocmentManager"%>
<%@page import="com.sq.tools.WorkDayTools"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt"       uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>我的日报信息填写</title>
		<link type="text/css" rel="stylesheet" href="../css/style-home.css" />
		<link type="text/css" rel="stylesheet" href="../css/style-sys.css" />
		<script type="text/javascript" src="../js/public.js"></script>
		<script type="text/javascript" src="../js/jquerybak.js"></script>
		<jsp:include page="../js/index.jsp"></jsp:include>
		<script type="text/javascript" src="js/myworkdayeditlist.js"></script>
	</head>
<%
	ApplicationDate.getRequestData("SYS_PARA_DATA" , Arrays.asList("sq_workday_managertask_id") , request , "sq_workday_managertask_id" );
	ApplicationDate.getRequestData("SYS_PARA_DATA" , Arrays.asList("sq_workday_managertask_status") , request , "sq_workday_managertask_status" );
	Map taskStatusMap = (Map)request.getAttribute("sq_workday_managertask_status");
	ApplicationDate.getRequestData("SYS_PARA_DATA" , Arrays.asList("sq_project_stepwork_units") , request , "sq_project_stepwork_units" );
	Map workUnitsMap = (Map)request.getAttribute("sq_project_stepwork_units");
%>
	<body onload="onload();">
	<div class="right">
			<div class="right_bt">
				<h1 class="right_bth1">
					员工日报编写
				</h1>
			</div>
			<form action="projectadduser.shtml" method="post" name="jform" id="jform" enctype="multipart/form-data">
				<div class="right_centerwk">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<table border="0" width="100%" class="form_table_l">
     <tr>
        <td height="32" style="font-weight: bold; text-align: right" width="150px;">日报日期：</td>
        <td height="32">
        	<select name="sqWorkdayManager.workDate"></select>
        </td>
        <td height="32" colspan="2" style="text-align: center">
				<div style="text-align: center;">
									<div style="width:150px;margin: 0 auto;">
				<a class="btn" onClick="onWorkView();">
					<cite>周报预览</cite></a>
					</div></div>
        </td>
      </tr>
      <tr>
        <td height="32" style="font-weight: bold; text-align: right;width: 10%">日报项目：</td>
        <td height="32" style="width: 40%">
        	<s:select list="#request.projectList" theme="simple" listKey="projectId" listValue="projectName" name="sqWorkdayManager.sqProjectInfo.projectId" onchange="onChanageProject()"></s:select>
        </td>
         <td height="32" style="font-weight: bold; text-align: right;width: 10%">项目里程碑：</td>
        <td height="32">
        	<select name="sqWorkdayManager.sqProjectStep.stepId"></select>
        </td>
      </tr>
      <tr>
        <td height="32" style="font-weight: bold; text-align: right">任务时长：</td>
        <td height="32">
        	<input type="text" name="sqWorkdayManager.taskTime" value="<s:property value="sqWorkdayManager.taskTime"/>" size="3" maxlength="5" onblur="isCheckMaxLenth(this)">小时
        </td>
         <td height="32" style="font-weight: bold; text-align: right">完成比例：</td>
        <td height="32" >
        	<input type="text" name="sqWorkdayManager.complePercen" value="<s:property value="sqWorkdayManager.complePercen"/>" size="3" maxlength="5" onblur="isCheckMaxLenth(this)">%
        </td>
      </tr>
      <tr>
         <td height="32" style="font-weight: bold; text-align: right">工作成果：</td>
        <td height="32">
        	<input type="text" name="workLoad0" value="" size="1" maxlength="4" onblur="isCheckMaxLenth(this)"><%=workUnitsMap.get("0") %>&nbsp;&nbsp;&nbsp;
        	<input type="text" name="workLoad1" value="" size="1" maxlength="4" onblur="isCheckMaxLenth(this)"><%=workUnitsMap.get("1") %>&nbsp;&nbsp;&nbsp;
        	<input type="text" name="workLoad2" value="" size="1" maxlength="4" onblur="isCheckMaxLenth(this)"><%=workUnitsMap.get("2") %>
        </td>
         <td height="32" style="font-weight: bold; text-align: right">任务状态：</td>
        <td height="32">
        	<s:select theme="simple" list="#request.sq_workday_managertask_status" listKey="key" listValue="value" name="sqWorkdayManager.taskStatus"></s:select>
        </td>
      </tr>
      <tr>
        <td height="32" style="font-weight: bold; text-align: right" width="150px;">日报文件：</td>
        <td height="32" colspan="3">
        	<input type="file" name="fileName" value="" size="30">
        </td>
        <!--<td height="32" colspan="2" style="text-align: center">
				<div style="text-align: center;width:100px;">
				<a class="btn" onClick="onAddFile()" >
				<cite>上传附件</cite></a>
				</div>
        </td>
      --></tr>
      <tr>
        <td height="32" style="font-weight: bold; " colspan="2">工作任务简要说明：</td>
        <td height="32" colspan="2" style="text-align: center">
			<div style="text-align: center;width:100px;">
				<a class="btn" onClick="onAdd()" >
				<cite>保&nbsp;&nbsp;&nbsp;存</cite></a>
				</div>
        </td>
      </tr>
      <tr>
        <td height="32" colspan="4">
        	<textarea rows="12" cols="120" id="sqWorkdayManager.workContent" name="sqWorkdayManager.workContent" maxlength="1000" onblur="isCheckMaxLenth(this)"><s:property value="sqWorkdayManager.workContent"/></textarea>
        </td>
      </tr>
      <tr>
        <td height="32" style="font-weight: bold;" colspan="2">未完成工作：</td>
        <td height="32"style="font-weight: bold;" colspan="2">下日计划：</td>
      </tr>
      <tr>
        <td height="32" colspan="2">
        	<textarea rows="2" cols="50" name="sqWorkdayManager.noWorkContent" maxlength="1000" onblur="isCheckMaxLenth(this)"><s:property value="sqWorkdayManager.noWorkContent"/></textarea>
        </td>
        <td height="32" colspan="2">
        	<textarea rows="2" cols="50" name="sqWorkdayManager.workNextPlan" maxlength="1000" onblur="isCheckMaxLenth(this)"><s:property value="sqWorkdayManager.workNextPlan"/></textarea>
        </td>
      </tr>
      <tr>
        <td height="32" style="font-weight: bold;" colspan="2">需协调解决问题：</td>
        <td height="32" style="font-weight: bold;" colspan="2">工作建议：</td>
      </tr>
      <tr>
        <td height="32" colspan="2">
        	<textarea rows="2" cols="50" name="sqWorkdayManager.discussProblem" maxlength="1000" onblur="isCheckMaxLenth(this)"><s:property value="sqWorkdayManager.discussProblem"/></textarea>
        </td>
        <td height="32" colspan="2">
        	<textarea rows="2" cols="50" name="sqWorkdayManager.workSug" maxlength="1000" onblur="isCheckMaxLenth(this)"><s:property value="sqWorkdayManager.workSug"/></textarea>
        </td>
      </tr>
      </table>
							</td>
						</tr>
					</table>
				</div>
	<input type="hidden" name="sqWorkdayManager.workLoad" value="<s:property value="sqWorkdayManager.workLoad"/>">
	<input type="hidden" name="sqWorkdayManager.workUnits" value="<s:property value="sqWorkdayManager.workUnits"/>">
	<input type="hidden" name="sqWorkdayManager.workdayId" value="<s:property value="sqWorkdayManager.workdayId"/>">
	<input type="hidden" name="stepId" value="<s:property value="sqWorkdayManager.sqProjectStep.stepId"/>">
    <input type="hidden" name="sqWorkdayManager.sqUserTab.userId" value="<s:property value="#session.sqUserTab.userId"/>">
    <input type="hidden" name="sqWorkdayManager.type" value="0"><!-- 日报类型 0 个人日报 -->
    <input type="hidden" name="sqDocmentManager.projectId" value="<s:property value="sqWorkdayManager.sqProjectInfo.projectId"/>">
     <input type="hidden" name="sqDocmentManager.stepId" value="<s:property value="sqWorkdayManager.workdayId"/>">
    <input type="hidden" name="returnPage" value="myworkdayeditdelete"><!-- 文件上传后调用的-->
    </form>
			<div class="right_centerwk">
			 <table align="center" border="0" width="100%" class="form_table">
      <tr id="tab">
        <td height="22" style="font-weight:bold" width="8%">日报日期</td>
        <td height="22" style="font-weight:bold" width="15%">日报项目</td>
        <td height="22" style="font-weight:bold" width="10%">项目里程碑</td>
        <td height="22" style="font-weight:bold" width="35%">工作任务</td>
        <td height="22" style="font-weight:bold" width="10%">任务状态</td>
        <td height="22" style="font-weight:bold" width="12%">日报文件</td>
        <td height="22" style="font-weight:bold" width="10%">操作</td>
      </tr>
      <%List workDayManagerList = (List)request.getAttribute("sqWorkdayManagerList"); 
      for(int i=0 ; i<workDayManagerList.size() ; i++){
     SqWorkdayManager sqWorkdayManager = (SqWorkdayManager) workDayManagerList.get(i);%>
	  <tr id="tab">
	    <td height="22"><%=Public.getTimeToFormat(sqWorkdayManager.getWorkDate() , "yyyy-MM-dd")%></td>
	    <td height="22"><%=sqWorkdayManager.getSqProjectInfo().getProjectName()%></td>
	    <td height="22"><%=sqWorkdayManager.getSqProjectStep().getStepName()%></td>
	    <td height="22" style="text-align: left">任务时长<%=sqWorkdayManager.getTaskTime()%>小时，工作成果：<%=WorkDayTools.workLoadStr(workUnitsMap ,sqWorkdayManager.getWorkLoad(),sqWorkdayManager.getWorkUnits())%>，完成比例：<%=sqWorkdayManager.getComplePercen()%>%<br/>
	    <%=sqWorkdayManager.getWorkContent().replaceAll("\\r\\n" , "<br/>")%></td>
	    <td height="22"><%=taskStatusMap.get(sqWorkdayManager.getTaskStatus())%></td>
	    <td height="22">
	    	<%Set<SqDocmentManager> sqDocumentManageSet = sqWorkdayManager.getSqDocumentManageSet(); 
	    	for (SqDocmentManager sqDocmentManager : sqDocumentManageSet) {%>
	    		<a href="projectdocfiledelete.shtml?sqDocmentManager.docId=<%=sqDocmentManager.getDocId() %>&returnPage=projectdocfiledelete" class="form_font"><img src="../images/del.gif" width="16" height="16" /></a>&nbsp;&nbsp;version:<%=sqDocmentManager.getFileVersion()%>&nbsp;&nbsp;<a href="projectmanager/filedown.shtml?filenameInCN=<%=sqDocmentManager.getBackfileName() %>&fileNameCN=<%=sqDocmentManager.getFileName()%>&projectId=<%=sqDocmentManager.getProjectId() %>" class="form_font"><%=sqDocmentManager.getBackfileName()%></a><br/>
	    	<%} %></td>
	    <td height="22"><img src="../images/edt.gif" width="16" height="16" /><a href="myworkdayeditupdatefind.shtml?sqWorkdayManager.workdayId=<%=sqWorkdayManager.getWorkdayId() %>&sqWorkdayManager.sqProjectInfo.projectId=<%=sqWorkdayManager.getSqProjectInfo().getProjectId() %>&flag=1" class="form_font">修改</a>&nbsp;&nbsp;<img src="../images/del.gif" width="16" height="16" /><a href="myworkdayeditdelete.shtml?workdayId=<%=sqWorkdayManager.getWorkdayId() %>&projectId=<%=sqWorkdayManager.getSqProjectInfo().getProjectId() %>" class="form_font">删除</a></td>
	  </tr>
	  <%} %>
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
	</body>
</html>