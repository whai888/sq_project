<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.sq.sys.ApplicationDate"%>
<%@page import="java.util.Arrays"%>
<%@page import="com.sq.model.vo.SqWorkdayManager"%>
<%@page import="java.util.List"%>
<%@page import="com.sq.tools.Public"%>
<%@page import="java.util.Set"%>
<%@page import="com.sq.model.vo.SqDocmentManager"%>
<%@page import="java.util.Map"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt"       uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>项目报告</title>
		<link type="text/css" rel="stylesheet" href="../css/style-home.css" />
		<link type="text/css" rel="stylesheet" href="../css/style-sys.css" />
		<script type="text/javascript" src="../js/public.js"></script>
		<script type="text/javascript" src="../js/jquery.js"></script>
		<jsp:include page="../js/index.jsp"></jsp:include>
		<script type="text/javascript" src="js/workdayprojectreport.js"></script>
	</head>
<%
	ApplicationDate.getRequestData("SYS_PARA_DATA" , Arrays.asList("sq_workday_managertask_id") , request , "sq_workday_managertask_id" );
	ApplicationDate.getRequestData("SYS_PARA_DATA" , Arrays.asList("sq_workday_managertask_status") , request , "sq_workday_managertask_status" );
	ApplicationDate.getRequestData("SYS_PARA_DATA" , Arrays.asList("sq_project_stepwork_units") , request , "sq_project_stepwork_units" );
	SqWorkdayManager sqWorkdayManager = (SqWorkdayManager)request.getAttribute("sqWorkdayManager");
	Map taskStatusMap = (Map)request.getAttribute("sq_workday_managertask_status");
%>
	<body>
	<div class="right">
			<div class="right_bt">
				<h1 class="right_bth1">
					项目周报编写
				</h1>
			</div>
			<form action="workdayproeditadd.shtml" method="post" name="jform" id="jform" enctype="application/x-www-form-urlencoded">
				<div class="right_centerwk">
					<table width="100%" border="0" width="100%" class="form_table_l">
     <tr>
        <td height="32" style="font-weight: bold; text-align: right" width="150px">上报周期：</td>
        <td height="32" class="bgcolor1 fontleft"  width="350px;">
        	<input type="text" id="datepick1" maxlength="10" onfocus="$(this).calendar()" name="sqWorkdayManager.workStartDate" value="<s:property value="sqWorkdayManager.workStartDate"/>" size="10">到
        	<input type="text" id="datepick2" maxlength="10" onfocus="$(this).calendar()" name="sqWorkdayManager.workEndDate" value="<s:property value="sqWorkdayManager.workEndDate"/>" size="10">
        	<s:property value="sqWorkdayManager.workCycle"/>
        	<input type="hidden" name="sqWorkdayManager.workCycle" value="<s:property value="sqWorkdayManager.workCycle"/>">
        </td>
        <td height="32" style="text-align: center" colspan="2">
				<div style="text-align: center;">
									<div style="width:150px;margin: 0 auto;">
				<a class="btn" onClick="onProjectView();">
					<cite>周报预览</cite></a>
					</div></div>
        </td>
      </tr>
      <tr>
        <td height="32" style="font-weight: bold; text-align: right" width="150px;">项目名称：</td>
        <td height="32"  width="200px;">
        	<s:select list="#request.projectList" theme="simple" listKey="projectId" listValue="projectName" name="sqWorkdayManager.sqProjectInfo.projectId" onchange="onChanageProject()"></s:select>
        </td>
         <td height="32" style="font-weight: bold; text-align: right" width="150px;">项目里程碑：</td>
        <td height="32"  width="200px;">
        	<%=sqWorkdayManager.getSqProjectInfo().getCurrProjectStep().getStepName() %>
        </td>
      </tr>
      <tr>
        <td height="32" style="font-weight: bold; text-align: right" width="150px;">完成比例：</td>
        <td height="32"  width="200px;">
        	<input type="text" name="sqWorkdayManager.complePercen" value="<s:property value="sqWorkdayManager.complePercen"/>" size="3" maxlength="5">%
        </td>
         <td height="32" style="font-weight: bold; text-align: right" width="150px;">任务状态：</td>
        <td height="32"  width="200px;">
        	<s:select theme="simple" list="#request.sq_workday_managertask_status" listKey="key" listValue="value" name="sqWorkdayManager.taskStatus"></s:select>
        </td>
      </tr>
      <tr>
        <td height="32" style="font-weight: bold;" colspan="2">工作任务简要说明：</td>
        <td height="32" colspan="2" style="text-align: center">
      		<div style="text-align: center;">
									<div style="width:100px;margin: 0 auto;">
				<a class="btn" onClick="onAdd()" >
				<cite>保&nbsp;&nbsp;&nbsp;存</cite></a>
				</div></div>
        </td>
      </tr>
      <tr>
        <td height="32" colspan="4">
        	<textarea rows="12" cols="120" name="sqWorkdayManager.workContent"><s:property value="sqWorkdayManager.workContent"/></textarea>
        </td>
      </tr>
      <tr>
        <td height="32" style="font-weight: bold;" colspan="2">未完成工作：</td>
        <td height="32" style="font-weight: bold;" colspan="2">下周计划：</td>
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
      <!--<tr>
        <td height="32" colspan="3" style="font-weight: bold;">
      		日报文件：<input type="file" name="fileName" value="" size="70">
        </td>
        <td height="32" style="text-align: center">
		<div style="text-align: center;">
									<div style="width:100px;margin: 0 auto;">
				<a class="btn" onClick="onAddFile()" >
				<cite>上传附件</cite></a>
				</div></div>
      	</td>
      </tr>
    --></table>
				</div>
	<input type="hidden" name="sqWorkdayManager.workdayId" value="<s:property value="sqWorkdayManager.workdayId"/>">
	<input type="hidden" name="sqWorkdayManager.workDate" value="<s:date name="sqWorkdayManager.workDate" format="yyyy-MM-dd"/>">
    <input type="hidden" name="sqWorkdayManager.sqUserTab.userId" value="<s:property value="#session.sqUserTab.userId"/>">
    <input type="hidden" name="sqWorkdayManager.type" value="1"><!-- 日报类型 1 项目日报 -->
    <input type="hidden" name="sqDocmentManager.projectId" value="<s:property value="sqWorkdayManager.sqProjectInfo.projectId"/>">
     <input type="hidden" name="sqDocmentManager.stepId" value="<s:property value="sqWorkdayManager.workdayId"/>">
     <input type="hidden" name="sqWorkdayManager.sqProjectStep.stepId" value="<%=sqWorkdayManager.getSqProjectInfo().getCurrProjectStep().getStepId() %>">
    <input type="hidden" name="returnPage" value="workdayproeditlist"><!-- 文件上传后调用的-->
    </form>
    <div class="right_centerwk">
	<table align="center" border="0" width="100%" class="form_table" id="comm">
      <tr id="tab">
        <td height="22" style="font-weight:bold"width="10%">上报日期</td>
        <td height="22" style="font-weight:bold"width="15%">周报项目</td>
        <td height="22" style="font-weight:bold"width="10%">项目里程碑</td>
        <td height="22" style="font-weight:bold"width="37%">工作任务</td>
        <td height="22" style="font-weight:bold"width="10%">完成比例</td>
        <td height="22" style="font-weight:bold"width="10%">任务状态</td>
        <td height="22" style="font-weight:bold"width="8%">操作</td>
      </tr>
       <%List workDayManagerList = (List)request.getAttribute("sqWorkdayManagerList"); 
      	for(int i=0 ; i<workDayManagerList.size() ; i++){
     	SqWorkdayManager sqWorkdayManagerTemp = (SqWorkdayManager) workDayManagerList.get(i);%>
	  <tr id="tab">
	    <td height="22"><%=Public.getTimeToFormat(sqWorkdayManagerTemp.getWorkDate() , "yyyy-MM-dd")%></td>
	    <td height="22"><%=sqWorkdayManagerTemp.getSqProjectInfo().getProjectName()%></td>
	    <td height="22"><%=sqWorkdayManagerTemp.getSqProjectStep().getStepName()%></td>
	    <td height="22" style="text-align: left"><%=sqWorkdayManagerTemp.getWorkContent().replaceAll("\\r\\n" , "<br/>")%></td>
	    <td height="22"><%=sqWorkdayManagerTemp.getComplePercen()%>%</td>
	    <td height="22"><%=taskStatusMap.get(sqWorkdayManagerTemp.getTaskStatus())%></td><!--
	    <td height="22">
	    	<%Set<SqDocmentManager> sqDocumentManageSet = sqWorkdayManagerTemp.getSqDocumentManageSet(); 
	    	for (SqDocmentManager sqDocmentManager : sqDocumentManageSet) {%>
	    		<img src="../images/del.gif" width="16" height="16" /><a href="projectdocfiledelete.shtml?sqDocmentManager.docId=<%=sqDocmentManager.getDocId() %>&returnPage=projectdocfiledelete" class="form_font">删除</a>&nbsp;&nbsp;version:<%=sqDocmentManager.getFileVersion()%>&nbsp;&nbsp;<%=sqDocmentManager.getBackfileName()%></br>
	    	<%} %></td>
	    --><td height="22"><img src="../images/edt.gif" width="16" height="16" /><a href="workdayproeditlist.shtml?sqWorkdayManager.sqProjectInfo.projectId=<%=sqWorkdayManagerTemp.getSqProjectInfo().getProjectId() %>" class="form_font">修改</a>&nbsp;&nbsp;<img src="../images/del.gif" width="16" height="16" /><a href="myworkprojecteditdelete.shtml?workdayId=<%=sqWorkdayManagerTemp.getWorkdayId() %>&projectId=<%=sqWorkdayManagerTemp.getSqProjectInfo().getProjectId() %>" class="form_font">删除</a></td>
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