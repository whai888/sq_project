<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.sq.sys.ApplicationDate"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="com.sq.model.vo.SqWorkdayManager"%>
<%@page import="com.sq.tools.Public"%>
<%@page import="java.util.Map"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%
	ApplicationDate.getRequestData("SYS_PARA_DATA", Arrays.asList("sq_workday_managertask_status"), request,"sq_workday_managertask_status");
	Map managerTaskStatus = (Map)request.getAttribute("sq_workday_managertask_status");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>生成项目组周报明细</title>
<link type="text/css" rel="stylesheet" href="../css/style-home.css" />
<link type="text/css" rel="stylesheet" href="../css/style-sys.css" />
<script type="text/javascript" src="../js/jquery.js"></script>
<script type="text/javascript" src="js/groupthdeptlist.js"></script>
</head>
<body>
<form action="growthlist.shtml" method="post" name="qform" id="qform">
<div class="right">
			<div class="right_bt">
				<h1 class="right_bth1">
					项目组周报管理
				</h1>
			</div>
			<div class="right_centerwk">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="from_btimg form_bt">
							<span>&nbsp;</span>
							<h1>
								项目周报明细
							</h1>
						</td>
					</tr>
					<tr>
						<td>
							<table align="center" border="0" width="100%" class="form_table">
      <tr id="tab">
        <td height="22" style="font-weight: bold" width="6%">上报日期</td>
        <td height="22" style="font-weight: bold" width="6%">项目提交人</td>
        <td height="22" style="font-weight: bold" width="10%">项目名称</td>
        <td height="22" style="font-weight: bold" width="10%">项目里程碑</td>
        <td height="22" style="font-weight: bold" width="21%">工作完成情况</td>
        <td height="22" style="font-weight: bold" width="10%">未完成任务</td>
        <td height="22" style="font-weight: bold" width="8%">下周计划</td>
        <td height="22" style="font-weight: bold" width="10%">需协调解决的问题</td>
        <td height="22" style="font-weight: bold" width="8%">工作建议</td>
        <td height="22" style="font-weight: bold" width="6%">项目进度</td>
        <td height="22" style="font-weight: bold" width="5%">操作</td>
      </tr>
      <%List workDayManagerList = (List)request.getAttribute("workViewList"); 
      for(int i=0 ; i<workDayManagerList.size() ; i++){
     SqWorkdayManager sqWorkdayManager = (SqWorkdayManager) workDayManagerList.get(i);%>
	  <tr id="tab">
	    <td height="22"><%=Public.getTimeToFormat(sqWorkdayManager.getWorkDate() , "yyyy-MM-dd")%></td>
	    <td height="22"><%=sqWorkdayManager.getSqUserTab().getUserName()%></td>
	    <td height="22"><%=sqWorkdayManager.getSqProjectInfo().getProjectName()%></td>
	    <td height="22"><%=sqWorkdayManager.getSqProjectStep().getStepName()%></td>
	    <td height="22" style="text-align: left"><%=sqWorkdayManager.getWorkContent().replaceAll("\\r\\n" , "<br/>")%></td>
	    <td height="22" style="text-align: left"><%=sqWorkdayManager.getNoWorkContent().replaceAll("\\r\\n" , "<br/>")%></td>
	    <td height="22" style="text-align: left"><%=sqWorkdayManager.getWorkNextPlan().replaceAll("\\r\\n" , "<br/>")%></td>
	    <td height="22" style="text-align: left"><%=sqWorkdayManager.getDiscussProblem().replaceAll("\\r\\n" , "<br/>")%></td>
	    <td height="22" style="text-align: left"><%=sqWorkdayManager.getWorkSug().replaceAll("\\r\\n" , "<br/>")%></td>
	    <td height="22"><%=managerTaskStatus.get(sqWorkdayManager.getTaskStatus())%></td>
	    <td height="22"><input type="checkbox" name="workData" value="<%=sqWorkdayManager.getWorkdayId()%>|<%=sqWorkdayManager.getSqProjectInfo().getProjectId()%>" checked="checked"><img src="../images/lead.gif" width="16" height="16" /><!--<a href="myworkgroupeditupdatefind.shtml?sqWorkdayManager.workdayId=<s:property value="workdayId"/>&sqWorkdayManager.sqProjectInfo.projectId=<s:property value="sqProjectInfo.projectId"/>" class="form_font">导出</a>--></td>
	  </tr>
  <%} %>
    </table>
						</td>
					</tr>
				</table>
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					bgcolor="#e9f2f7">
					<tr height="35">
						<td width="70%" align="right" style="text-align: center">
							<div style="text-align: center;">
									<div style="width:350px;margin: 0 auto;">
									<a class="btn" onClick="allCheck()" style="padding-right: 60px;">
									<cite>全&nbsp;&nbsp;&nbsp;选</cite></a>
									<a class="btn" onClick="allRemoveCheck()" style="padding-right: 60px;">
									<cite>反&nbsp;&nbsp;&nbsp;选</cite></a>
								<a class="btn" onClick="onExportAll()">
									<cite>全部导出</cite></a>
									</div></div>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<input type="hidden" name="returnPage" value="growthgrouplist">
</form>
</body>
</html>