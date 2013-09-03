<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.sq.sys.ApplicationDate"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="com.sq.model.vo.SqWorkdayManager"%>
<%@page import="com.sq.tools.Public"%>
<%@page import="java.util.Map"%>
<%@page import="com.sq.model.vo.SqUserTab"%>
<%@page import="com.sq.logic.workday.impl.WorkDaySortList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	ApplicationDate.getRequestData("SYS_PARA_DATA", Arrays.asList("sq_workday_managertask_status"), request,"sq_workday_managertask_status");
	Map workManageMap = (Map)request.getAttribute("sq_workday_managertask_status");
	List workViewList = (List)request.getAttribute("workViewList");
	session.removeAttribute("myWorkDayViewList");
	session.setAttribute("myWorkDayViewList",workViewList);
	WorkDaySortList workDay = new WorkDaySortList();
	workDay.Sort(workViewList , "desc") ;
	SqUserTab sqUserTab = (SqUserTab)request.getAttribute("sqUserTab");
	int contentSize = workViewList.size() ;
	int noworkSize = 0;
	int nextPlanSize = 0;
	int discussSize = 0;
	int workSugSize = 0 ;
	int repeat = 0 ;
	boolean isShow = false ;
	String startDate = "";
	String endDate = "" ;
	String tempProjectId = "";
	String tempProjectId1 = "";
	String tempProjectId2 = "";
	String tempProjectId3 = "";
	String tempProjectId4 = "";
	for(int i =0 ; i<workViewList.size() ; i++){
		SqWorkdayManager sqWorkdayManager = (SqWorkdayManager)workViewList.get(i);
		boolean flag = tempProjectId.equals(sqWorkdayManager.getSqProjectInfo().getProjectId()) ;
		if(!flag){
			tempProjectId = sqWorkdayManager.getSqProjectInfo().getProjectId();
		}else{
			repeat ++ ;
		}
		if(sqWorkdayManager.getNoWorkContent()!=null && !sqWorkdayManager.getNoWorkContent().equals("") && !tempProjectId1.equals(sqWorkdayManager.getSqProjectInfo().getProjectId())){
			noworkSize += 1;
			tempProjectId1 = sqWorkdayManager.getSqProjectInfo().getProjectId();
		}
		if(sqWorkdayManager.getDiscussProblem()!=null && !sqWorkdayManager.getDiscussProblem().equals("") && !tempProjectId2.equals(sqWorkdayManager.getSqProjectInfo().getProjectId())){
			discussSize += 1;
			tempProjectId2 = sqWorkdayManager.getSqProjectInfo().getProjectId();
		}
		if(sqWorkdayManager.getWorkNextPlan()!=null && !sqWorkdayManager.getWorkNextPlan().equals("") && !tempProjectId3.equals(sqWorkdayManager.getSqProjectInfo().getProjectId())){
			nextPlanSize += 1;
			tempProjectId3 = sqWorkdayManager.getSqProjectInfo().getProjectId();
		}
		if(sqWorkdayManager.getWorkSug()!=null && !sqWorkdayManager.getWorkSug().equals("") && !tempProjectId4.equals(sqWorkdayManager.getSqProjectInfo().getProjectId())){
			workSugSize += 1;
			tempProjectId4 = sqWorkdayManager.getSqProjectInfo().getProjectId();
		}
		if(sqWorkdayManager.getWeekDate()==null || sqWorkdayManager.getWeekDate().equals("")){
			isShow = true ;
		}
		String startDateTemp = Public.getTimeToFormat(sqWorkdayManager.getWorkStartDate() , "yyyy-MM-dd") ;
		String endDateTemp = Public.getTimeToFormat(sqWorkdayManager.getWorkEndDate() , "yyyy-MM-dd") ;
		if(startDate.equals("")){
			startDate = startDateTemp;
		}
		if(endDate.equals("")){
			endDate = endDateTemp;
		}
		//根据指定 String 大于、等于还是小于此 String（不考虑大小写），分别返回一个负整数、0 或一个正整数。
		if(startDateTemp.compareToIgnoreCase(startDate) < 0)
			startDate = startDateTemp ;
		if(endDateTemp.compareToIgnoreCase(endDate) < 0)
			endDate = endDateTemp ;
	}
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="<%=basePath %>css/index.css" />
		<link type="text/css" rel="stylesheet" href="<%=basePath %>css/style-home.css" />
		<link type="text/css" rel="stylesheet" href="<%=basePath %>css/style-sys.css" />
		<script type="text/javascript" src="<%=basePath %>js/public.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/jquery.js"></script>
		<script type="text/javascript" src="<%=basePath %>workdaymanager/js/workdayprojectreport.js"></script>
		<title>我的项目周报预览</title>
	</head>
	<body>
	<div style="padding-right: 135px;padding-top:20px;float: right;height: 30px;">
									<div style="width:650px;">
	<%if(isShow){ %>
	<form action=""  name="qform" id="qform">
		<a class="btn" onClick="javascript:window.history.go(-1)" style="margin-left: 270px;">
			<cite>返&nbsp;&nbsp;&nbsp;回</cite></a>
		<a class="btn" onClick="onProjectSub()" style="margin-left: 50px;">
					<cite>提交周报</cite></a>
	</form>
	<% }%>
	<form id="exportform" name="exportform">
		<a class="btn" href="projectmanager/workdayfiledown.shtml?project=9996&returnPage=9996&filenameInCN=<s:property value="#request.sqUserTab.userName"/>&filenameCN=<s:property value="#request.sqGroupTab.groupName"/>" style="float: right">
					<cite>导出周报到Word</cite></a>
	</div></div>
		<div style="margin-left:auto;margin-right:auto;width:80%">
			<table id="workviewtabhead" width="100%" cellpadding="0" cellspacing="0">
  <tr>
      <td height="50" colspan="8" align="center" bgcolor="#FFFFFF"><font  style="font-weight:bold" size="6">项目周报</font></td>
    </tr>
    <tr>
      <td height="50" colspan="3" align="left" nowrap bgcolor="#FFFFFF"><strong>姓名：</strong><s:property value="#request.sqUserTab.userName"/></td>
        <td height="50" colspan="2" align="left" nowrap bgcolor="#FFFFFF"><strong>所属项目组：</strong><s:property value="#request.sqGroupTab.groupName"/></td>
        <td height="50" colspan="3" align="right" nowrap bgcolor="#FFFFFF"><strong> 周报日期：</strong><%=startDate %>至<%=endDate %></td>
    </tr>
</table>
<table id="workviewtab" width="100%" cellpadding="0" cellspacing="0">
   
  <tr>
  <td width="4%" rowspan="<%=12+contentSize+noworkSize+nextPlanSize+discussSize-repeat %>" bgcolor="#FFFFFF"><font  style="font-weight:bold; padding-left:10px;" size="5">本</font>
        <font  style="font-weight:bold; padding-left:10px;" size="5">周</font>
        <font  style="font-weight:bold; padding-left:10px;" size="5">工</font>
        <font  style="font-weight:bold; padding-left:10px;" size="5">作</font>
        <font  style="font-weight:bold; padding-left:10px;" size="5">小</font>
      <font  style="font-weight:bold; padding-left:10px;" size="5">结</font></td>
    <td width="5%" id="workviewtitlehead" align="center" bgcolor="#C0C0C0" style="text-align: center"><font  style="font-weight:bold" size="4">序号</font></td>
    <td width="10%" id="workviewtitlehead" align="center" bgcolor="#C0C0C0" style="text-align: center"><font  style="font-weight:bold" size="4">项目名称</font></td>
    <td width="10%" id="workviewtitlehead" align="center" bgcolor="#C0C0C0" style="text-align: center"><font  style="font-weight:bold" size="4">项目阶段</font></td>
    <td id="workviewtitlehead" align="center" bgcolor="#C0C0C0" style="text-align: center"><font  style="font-weight:bold" size="4">工作完成情况及成果</font></td>
    <td width="10%" id="workviewtitlehead" align="center" bgcolor="#C0C0C0" style="text-align: center"><font  style="font-weight:bold" size="4">项目进度</font></td>
    </tr>
  <%int count1 = 0 ;
    String temp = "";	//记录项目编号
    for(int i=0 ; i<workViewList.size() ; i++){
		   SqWorkdayManager sqWorkdayManager = (SqWorkdayManager)workViewList.get(i);
		   if(sqWorkdayManager.getWorkContent()!=null && !sqWorkdayManager.getWorkContent().equals("")){
		   count1 ++ ;
		   temp = sqWorkdayManager.getSqProjectInfo().getProjectId();%>
		  <tr>
		    <td height="60"><%=count1 %></td>
		    <td height="60"><%=sqWorkdayManager.getSqProjectInfo().getProjectName() %></td>
		    <td height="60"><%=sqWorkdayManager.getSqProjectStep().getStepName() %></td>
		    <td height="60" style="text-align: left">
		    <%}
		    int icount = 1;
		    for(int j=i ; j<workViewList.size() ; j++){
		    	SqWorkdayManager sqWorkdayManagerTemp = (SqWorkdayManager)workViewList.get(j);
		    	if(temp.equals(sqWorkdayManagerTemp.getSqProjectInfo().getProjectId())){
		    		out.print(sqWorkdayManagerTemp.getWorkContent().replaceAll("\\r\\n" , "<br/>")+"<br/>");
		    		i = j ;
		    	}
		    }%>
		    	</td>
		    <td height="60"><%=workManageMap.get(sqWorkdayManager.getTaskStatus()) %></td>
		    </tr>
	   <%} %>
   <%
   for(int i=0 ; i<2 ; i++){ %>
  <tr>
    <td height="60">&nbsp;</td>
    <td height="60">&nbsp;</td>
    <td height="60">&nbsp;</td>
    <td height="60">&nbsp;</td>
    <td height="60" style="text-align: left">&nbsp;</td>
    </tr>
   <%}%>
  <tr>
    <td width="5%" id="workviewtitlehead" align="center" bgcolor="#C0C0C0" style="text-align: center"><font  style="font-weight:bold" size="4">序号</font></td>
    <td width="10%" id="workviewtitlehead" align="center" bgcolor="#C0C0C0" style="text-align: center"><font  style="font-weight:bold" size="4">项目名称</font></td>
    <td colspan="3" id="titlehead" align="center" bgcolor="#C0C0C0" style="text-align: center"><font  style="font-weight:bold" size="4">未完成的工作及情况说明</font></td>
    </tr>
  <%count1 = 0 ;
    temp = "";	//记录项目编号
    for(int i=0 ; i<workViewList.size() ; i++){
		   SqWorkdayManager sqWorkdayManager = (SqWorkdayManager)workViewList.get(i);
		   if(sqWorkdayManager.getNoWorkContent()!=null && !sqWorkdayManager.getNoWorkContent().equals("")){
		   count1 ++ ;
		   temp = sqWorkdayManager.getSqProjectInfo().getProjectId();%>
		  <tr>
		    <td height="60"><%=count1 %></td>
		    <td height="60"><%=sqWorkdayManager.getSqProjectInfo().getProjectName() %></td>
		    <td height="60" colspan="3" style="text-align: left">
		    <%}int icount = 1;
		    for(int j=i ; j<workViewList.size() ; j++){
		    	SqWorkdayManager sqWorkdayManagerTemp = (SqWorkdayManager)workViewList.get(j);
		    	if(temp.equals(sqWorkdayManagerTemp.getSqProjectInfo().getProjectId())){
		    		out.print(sqWorkdayManagerTemp.getNoWorkContent().replaceAll("\\r\\n" , "<br/>")+"<br/>");
		    		i = j ;
		    	}
		    }%>
		    	</td></tr>
	   <%} %>
   <%
   for(int i=0 ; i<2 ; i++){ %>
  <tr>
    <td height="60">&nbsp;</td>
    <td height="60">&nbsp;</td>
    <td height="60" colspan="3" style="text-align: left">&nbsp;</td>
    </tr>
   <%}%>
    <tr>
    <td width="5%" id="workviewtitlehead" align="center" bgcolor="#C0C0C0" style="text-align: center"><font  style="font-weight:bold" size="4">序号</font></td>
    <td width="10%" id="workviewtitlehead" align="center" bgcolor="#C0C0C0" style="text-align: center"><font  style="font-weight:bold" size="4">项目名称</font></td>
    <td colspan="3" id="titlehead" align="center" bgcolor="#C0C0C0" style="text-align: center"><font  style="font-weight:bold" size="4">下周计划</font></td>
    </tr>
  <%count1 = 0 ;
    temp = "";	//记录项目编号
    for(int i=0 ; i<workViewList.size() ; i++){
		   SqWorkdayManager sqWorkdayManager = (SqWorkdayManager)workViewList.get(i);
		   if(sqWorkdayManager.getWorkNextPlan()!=null && !sqWorkdayManager.getWorkNextPlan().equals("")){
		   count1 ++ ;
		   temp = sqWorkdayManager.getSqProjectInfo().getProjectId();%>
		  <tr>
		    <td height="60"><%=count1 %></td>
		    <td height="60"><%=sqWorkdayManager.getSqProjectInfo().getProjectName() %></td>
		    <td height="60" colspan="3" style="text-align: left">
		    <%}int icount = 1;
		    for(int j=i ; j<workViewList.size() ; j++){
		    	SqWorkdayManager sqWorkdayManagerTemp = (SqWorkdayManager)workViewList.get(j);
		    	if(temp.equals(sqWorkdayManagerTemp.getSqProjectInfo().getProjectId())){
		    		out.print(sqWorkdayManagerTemp.getWorkNextPlan().replaceAll("\\r\\n" , "<br/>")+"<br/>");
		    		i = j ;
		    	}
		    }%>
		    	</td></tr>
	   <%} %>
   <%
   for(int i=0 ; i<2 ; i++){ %>
  <tr>
    <td height="60">&nbsp;</td>
    <td height="60">&nbsp;</td>
    <td height="60" colspan="3" style="text-align: left">&nbsp;</td>
    </tr>
   <%}%>
    <tr>
    <td width="5%" id="workviewtitlehead" align="center" bgcolor="#C0C0C0" style="text-align: center"><font  style="font-weight:bold" size="4">序号</font></td>
    <td width="10%" id="workviewtitlehead" align="center" bgcolor="#C0C0C0" style="text-align: center"><font  style="font-weight:bold" size="4">项目名称</font></td>
    <td colspan="3" id="workviewtitlehead" align="center" bgcolor="#C0C0C0" style="text-align: center"><font  style="font-weight:bold" size="4">需协调解决的问题</font></td>
    </tr>
 <%count1 = 0 ;
    temp = "";	//记录项目编号
    for(int i=0 ; i<workViewList.size() ; i++){
		   SqWorkdayManager sqWorkdayManager = (SqWorkdayManager)workViewList.get(i);
		   if(sqWorkdayManager.getDiscussProblem()!=null && !sqWorkdayManager.getDiscussProblem().equals("")){
		   count1 ++ ;
		   temp = sqWorkdayManager.getSqProjectInfo().getProjectId();%>
		  <tr>
		    <td height="60"><%=count1 %></td>
		    <td height="60"><%=sqWorkdayManager.getSqProjectInfo().getProjectName() %></td>
		    <td height="60" colspan="3" style="text-align: left">
		    <%}int icount = 1;
		    for(int j=i ; j<workViewList.size() ; j++){
		    	SqWorkdayManager sqWorkdayManagerTemp = (SqWorkdayManager)workViewList.get(j);
		    	if(temp.equals(sqWorkdayManagerTemp.getSqProjectInfo().getProjectId())){
		    		out.print(sqWorkdayManagerTemp.getDiscussProblem().replaceAll("\\r\\n" , "<br/>")+"<br/>");
		    		i = j ;
		    	}
		    }%>
		    	</td></tr>
	   <%} %>
   <%
   for(int i=0 ; i<2 ; i++){ %>
  <tr>
    <td height="60">&nbsp;</td>
    <td height="60">&nbsp;</td>
    <td height="60" colspan="3" style="text-align: left">&nbsp;</td>
    </tr>
   <%}%>
   <% 
   StringBuffer userName = new StringBuffer();
   int userCout = 0 ;
   for(int i=0 ; i<workViewList.size() ; i++){
   		SqWorkdayManager sqWorkdayManager = (SqWorkdayManager)workViewList.get(i);
   		if(sqWorkdayManager.getRemark1() !=null) {
   			String [] userTemp =  sqWorkdayManager.getRemark1().split("   ");
   			for(int j=0 ; j<userTemp.length ; j++){
   				if(userName. indexOf(userTemp[j]) == -1 ){
   					userName.append(userTemp[j] + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
   					userCout ++ ;
   				}
   			}
   		}
   	} %>
   <tr>
    <td height="35" align="center" bgcolor="#C0C0C0" style="text-align: center"><font style="font-size:16px;">人数</font></td>
    <td width="602" colspan="5" align="center" bgcolor="#C0C0C0" style="text-align: center"><font style="font-size:16px;">名单</font></td>
  </tr>
  <tr>
    <td height="35" width="58" align="center"><p><%=userCout %></p></td>
    <td colspan="5">
    <%=userName%>&nbsp;
    </td>
  </tr>
  <tr>
    <td height="60" colspan="2" bgcolor="#C0C0C0"><font  style="font-weight:bold">其它事项说明<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;或建议</font></td>
    <td height="60" style="text-align: left" colspan="4"><%
    count1 = 0 ;
    temp = "";	//记录项目编号
    for(int i=0 ; i<workViewList.size() ; i++){
		   SqWorkdayManager sqWorkdayManager = (SqWorkdayManager)workViewList.get(i);
		   if(sqWorkdayManager.getWorkSug()!=null && !sqWorkdayManager.getWorkSug().equals("")){
		    		out.print(sqWorkdayManager.getWorkSug().replaceAll("\\r\\n" , "<br/>")+"<br/>");
		    }%>
	   <%} %>&nbsp;</td>
  </tr>
  </table>
			<p>
				&nbsp;
			</p>
			</div>
		</form>
	</body>
</html>