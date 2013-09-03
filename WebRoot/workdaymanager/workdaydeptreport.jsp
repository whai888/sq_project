<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.sq.sys.ApplicationDate"%>
<%@page import="java.util.Arrays"%>
<%@page import="com.sq.tools.Public"%>
<%@page import="com.sq.model.vo.SqWorkdayManager"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Set"%>
<%@page import="com.sq.model.vo.SqDocmentManager"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt"       uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="Cache-Control" content="no-cache">
		<title>部门周报</title>
		<link type="text/css" rel="stylesheet" href="../css/style-home.css" />
		<link type="text/css" rel="stylesheet" href="../css/style-sys.css" />
		<script type="text/javascript" src="../js/public.js"></script>
		<script type="text/javascript" src="../js/jquery.js"></script>
		<jsp:include page="../js/index.jsp"></jsp:include>
		<script type="text/javascript" src="js/workdaydeptreport.js"></script>
	</head>
<%
	ApplicationDate.getRequestData("SYS_PARA_DATA" , Arrays.asList("sq_workday_managertask_id") , request , "sq_workday_managertask_id" );
	ApplicationDate.getRequestData("SYS_PARA_DATA" , Arrays.asList("sq_workday_managertask_status") , request , "sq_workday_managertask_status" );
	ApplicationDate.getRequestData("SYS_PARA_DATA" , Arrays.asList("sq_project_stepwork_units") , request , "sq_project_stepwork_units" );
%>
	<body onload="onLoadFile()">
	<div class="right">
			<div class="right_bt">
				<h1 class="right_bth1">
					部门周报编写
				</h1>
			</div>
			<form action="workdaydepteditadd.shtml" method="post" name="jform" id="jform" enctype="multipart/form-data">
				<div class="right_centerwk">
					<table width="100%" border="0" width="100%" class="form_table_l">
     <tr>
        <td height="32" width="200px" style="font-weight: bold; text-align: right">请选择提交周报方式：</td>
        <td height="32" colspan="3">
        	<input type="radio" value="1" name="isFile" checked="checked" onclick="isFileCheck()"> 在线编写
        	<input type="radio" value="2" name="isFile" onclick="isFileCheck()"> 上传WORD附件
        </td>
      </tr>
     <tr>
        <td height="32" style="font-weight: bold; text-align: right">上报周期：</td>
        <td height="32" width="350px">
        	<input type="text" id="datepick1" maxlength="10" onfocus="$(this).calendar()" name="sqWorkdayManager.workStartDate" value="<s:property value="sqWorkdayManager.workStartDate"/>" size="10">到
        	<input type="text" id="datepick2" maxlength="10" onfocus="$(this).calendar()" name="sqWorkdayManager.workEndDate" value="<s:property value="sqWorkdayManager.workEndDate"/>" size="10">
        	<s:property value="sqWorkdayManager.workCycle"/>
        	<input type="hidden" name="sqWorkdayManager.workCycle" value="<s:property value="sqWorkdayManager.workCycle"/>">
        </td>
        <td height="32" style="text-align: center" colspan="2">
			<div style="text-align: center;">
									<div style="width:150px;margin: 0 auto;">
				<a class="btn" onClick="onDeptView();">
					<cite>周报预览</cite></a>
					</div></div>
        </td>
      </tr>
      <tr>
        <td height="32" style="font-weight: bold; text-align: right">项目组名称：</td>
        <td height="32" class="bgcolor1 fontleft fontbold"  width="200px;">
        	<s:select list="#request.groupList" theme="simple" listKey="groupNo" listValue="groupName" name="sqWorkdayManager.sqGroupTab.groupNo" onchange="onChanageProject()"></s:select>
        </td>
        <td height="32" style="font-weight: bold; text-align: right">完成比例：</td>
        <td height="32" class="bgcolor1 fontleft fontbold"  width="200px;">
        	<input type="text" name="sqWorkdayManager.complePercen" value="<s:property value="sqWorkdayManager.complePercen"/>" size="3" maxlength="5">%
        </td>
      </tr>
      <tr id="fileweek" style="display: none;">
        <td height="32" style="font-weight: bold; text-align: right" width="150px;">周报文件：</td>
        <td height="32" colspan="3">
        	<input type="file" name="fileName" value="" size="30">
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
        <td height="32" class="bgcolor1 fontleft fontbold" colspan="4">
        	<textarea rows="14" cols="120" name="sqWorkdayManager.workContent"><s:property value="sqWorkdayManager.workContent"/></textarea>
        </td>
      </tr>
      <tr>
        <td height="32" style="font-weight: bold;" colspan="2">需协调解决问题：</td>
        <td height="32" style="font-weight: bold;" colspan="2">工作建议：</td>
      </tr>
      <tr>
        <td height="32" class="bgcolor1 fontleft fontbold" colspan="2">
        	<textarea rows="2" cols="50" name="sqWorkdayManager.discussProblem" maxlength="1000" onblur="isCheckMaxLenth(this)"><s:property value="sqWorkdayManager.discussProblem"/></textarea>
        </td>
        <td height="32" class="bgcolor1 fontleft fontbold" colspan="2">
        	<textarea rows="2" cols="50" name="sqWorkdayManager.workSug" maxlength="1000" onblur="isCheckMaxLenth(this)"><s:property value="sqWorkdayManager.workSug"/></textarea>
        </td>
      </tr>
      </table>
				</div>
	<input type="hidden" name="sqWorkdayManager.workdayId" value="<s:property value="sqWorkdayManager.workdayId"/>">
	<input type="hidden" name="sqWorkdayManager.remark1" value="<s:property value="sqWorkdayManager.remark1"/>">
	<input type="hidden" name="sqWorkdayManager.workDate" value="<s:property value="sqWorkdayManager.workDate"/>">
    <input type="hidden" name="sqWorkdayManager.sqUserTab.userId" value="<s:property value="#session.sqUserTab.userId"/>">
    <input type="hidden" name="sqWorkdayManager.type" value="<s:property value="sqWorkdayManager.type"/>"><!-- 日报类型 3 部门日报 -->
    <!--<input type="hidden" name="sqDocmentManager.projectId" value="<s:property value="sqWorkdayManager.sqProjectInfo.projectId"/>">-->
     <input type="hidden" name="sqDocmentManager.stepId" value="<s:property value="sqWorkdayManager.workdayId"/>">
    <input type="hidden" name="projectId" value="<s:property value="sqWorkdayManager.sqProjectInfo.projectId"/>">
    <input type="hidden" name="returnPage" value="workdayproeditlist"><!-- 文件上传后调用的-->
    </form>
    
    <div class="right_centerwk">
    <table align="center" border="0" width="100%" class="form_table" id="comm">
      <tr id="tab">
        <td height="22" style="font-weight:bold" width="10%">上报周期</td>
        <td height="22" style="font-weight:bold" width="10%">所属项目组</td>
        <td height="22" style="font-weight:bold" width="50%">工作任务</td>
        <td height="22" style="font-weight:bold" width="12%">周报文件</td>
        <td height="22" style="font-weight:bold" width="10%">完成比例</td>
        <td height="22" style="font-weight:bold" width="10%">操作</td>
      </tr>
      <%List workDayManagerList = (List)request.getAttribute("sqWorkdayManagerList"); 
      boolean flag = false ;
      for(int i=0 ; i<workDayManagerList.size() ; i++){
      //如果是文件  则没有修改按钮
     SqWorkdayManager sqWorkdayManager = (SqWorkdayManager) workDayManagerList.get(i);%>
	  <tr id="tab">
	    <td height="22"><%=Public.getTimeToFormat(sqWorkdayManager.getWorkDate() , "yyyy-MM-dd")%></td>
	    <td height="22"><%=sqWorkdayManager.getSqGroupTab().getGroupName()%></td>
	    <td height="22" style="text-align: left"><%=sqWorkdayManager.getWorkContent().replaceAll("\\r\\n" , "<br/>")%></td>
	    <td height="22">
	    	<%Set<SqDocmentManager> sqDocumentManageSet = sqWorkdayManager.getSqDocumentManageSet(); 
	    	for (SqDocmentManager sqDocmentManager : sqDocumentManageSet) {
	    	flag = true ;%>
	    		<a href="projectdocfiledelete.shtml?sqDocmentManager.docId=<%=sqDocmentManager.getDocId() %>&returnPage=myworkdeptedit" class="form_font"><img src="../images/del.gif" width="16" height="16" /></a>&nbsp;&nbsp;version:<%=sqDocmentManager.getFileVersion()%>&nbsp;&nbsp;<a href="projectmanager/filedown.shtml?filenameInCN=<%=sqDocmentManager.getBackfileName() %>&fileNameCN=<%=sqDocmentManager.getFileName()%>&projectId=<%=sqDocmentManager.getProjectId() %>" class="form_font"><%=sqDocmentManager.getBackfileName()%></a><br/>
	    	<%} %></td>
	    <td height="22"><%=sqWorkdayManager.getComplePercen()%>%</td>
	    <td height="22">
	    <%if(!flag){ %><img src="../images/edt.gif" width="16" height="16" /><a href="myworkdeptedit.shtml?sqWorkdayManager.workdayId=<%=sqWorkdayManager.getWorkdayId() %>&sqWorkdayManager.sqGroupTab.groupNo=<%=sqWorkdayManager.getSqGroupTab().getGroupNo() %>" class="form_font">修改</a>&nbsp;&nbsp;<%} %>
	    <img src="../images/del.gif" width="16" height="16" /><a href="myworkdepteditdelete.shtml?workdayId=<%=sqWorkdayManager.getWorkdayId() %>&returnPage=workdaydepteditlist" class="form_font">删除</a></td>
	  </tr>
	  <%} %>
  </table>
</div>
</div>
<input type="hidden" name="onloadFlag" value="<%=flag %>">
	</body>
</html>