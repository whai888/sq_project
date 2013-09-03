<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.sq.sys.ApplicationDate"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="com.sq.model.vo.SqWorkdayManager"%>
<%@page import="com.sq.tools.Public"%>
<%@page import="java.util.Map"%>
<%@page import="com.sq.model.vo.SqUserTab"%>
<%@page import="com.sq.model.vo.SqGroupTab"%>
<%@page import="com.sq.model.vo.SqDocmentManager"%>
<%@page import="com.sq.tools.WordBean"%>
<%@page import="org.apache.struts2.ServletActionContext"%>
<%@page import="java.io.File"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	ApplicationDate.getRequestData("SYS_PARA_DATA", Arrays.asList("sq_workday_managertask_status"), request,"sq_workday_managertask_status");
	Map workManageMap = (Map)request.getAttribute("sq_workday_managertask_status");
	SqUserTab sqUserTab =(SqUserTab)request.getAttribute("sqUserTab");
	SqGroupTab sqGroupTab = (SqGroupTab)request.getAttribute("sqGroupTab");
	List workViewList = (List)request.getAttribute("workViewList");
	session.removeAttribute("myWorkDayViewList");
	session.setAttribute("myWorkDayViewList",workViewList);
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
		<link type="text/css" rel="stylesheet" href=".<%=basePath %>css/style-home.css" />
		<link type="text/css" rel="stylesheet" href="<%=basePath %>css/style-sys.css" />
		<script type="text/javascript" src="<%=basePath %>js/public.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/jquery.js"></script>
		<script type="text/javascript" src="<%=basePath %>workdaymanager/js/workdaygroupreport.js"></script>
		<title>我的项目周报预览</title>
	</head>
	<body>
	<div style="padding-right: 135px;padding-top:20px;float: right;height: 30px;">
									<div style="width:650px;">
	<%if(isShow){ %>
	<form action=""  name="qform" id="qform">
		<a class="btn" onClick="javascript:window.history.go(-1)" style="margin-left: 270px;">
			<cite>返&nbsp;&nbsp;&nbsp;回</cite></a>
		<a class="btn" onClick="onGroupSub()" style="margin-left: 50px;">
					<cite>提交周报</cite></a>
	</form>
	<% }%>
	<form id="exportform" name="exportform">
		<a class="btn" href="projectmanager/workdayfiledown.shtml?project=9998&returnPage=9998&filenameInCN=<%=sqUserTab.getUserName() %>&filenameCN=<%=sqGroupTab.getGroupName() %>" style="float: right">
					<cite>导出周报到Word</cite></a>
	</div></div>
		<div style="margin-left:auto;margin-right:auto;width:80%">
			<table id="workviewtabhead" width="100%" cellpadding="0" cellspacing="0">
  <tr>
      <td height="50" colspan="8" align="center" bgcolor="#FFFFFF"><font  style="font-weight:bold" size="6"><%=sqGroupTab.getGroupName() %>周报</font></td>
    </tr>
    <tr>
    <td height="50" width="100px;" align="left" nowrap bgcolor="#FFFFFF"><strong> 周报日期：</strong></td>
    <td height="50" colspan="2" align="left" nowrap bgcolor="#FFFFFF"><%=startDate %>至<%=endDate %></td>
      <td height="50" width="100px;" align="left" nowrap bgcolor="#FFFFFF"><strong>编写人：</strong></td>
      <td height="50" colspan="2" align="left" nowrap bgcolor="#FFFFFF"><%=sqUserTab.getUserName() %></td>
        <td height="50" width="100px;" align="right" nowrap bgcolor="#FFFFFF"><strong>日期：</strong></td>
        <td height="50" width="100px;" align="right" nowrap bgcolor="#FFFFFF"><%=endDate %>&nbsp;</td>
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
		   count1 ++ ;
		   temp = sqWorkdayManager.getSqProjectInfo().getProjectId();%>
		  <tr>
		    <td height="60"><%=count1 %></td>
		    <td height="60"><%=sqWorkdayManager.getSqProjectInfo().getProjectName() %></td>
		    <td height="60"><%=sqWorkdayManager.getSqProjectStep().getStepName() %></td>
		    <td height="60" style="text-align: left">
		    <%
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
	   <%}%>
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
  <tr>
    <td height="60" colspan="2" bgcolor="#C0C0C0"><font  style="font-weight:bold">其它事项说明<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;或建议</font></td>
    <td height="60" style="text-align: left" colspan="4"><%count1 = 0 ;
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
		<%
		try{if(!isShow){
		int tableIndex = 1 ;
		SqDocmentManager sqDocmentManager = (SqDocmentManager)request.getAttribute("sqDocmentManager");
		session.removeAttribute("sqDocmentManager");
		session.setAttribute("sqDocmentManager" , sqDocmentManager);
		if(sqDocmentManager !=null ){
		WordBean msWordManager = new WordBean();
		//读取周例会内容
		msWordManager.openDocument(ServletActionContext.getServletContext().getRealPath(File.separator) + sqDocmentManager.getFilePath() + sqDocmentManager.getFileName());
		%>
<!-- 周会记录信息查询 -->
			<table id="workviewtab" width="100%" cellpadding="0" cellspacing="0">
			<tr>
		      <td height="50" colspan="6" align="center" bgcolor="#FFFFFF"><font  style="font-weight:bold" size="6">周会记录</font></td>
		    </tr>
			  <tr>
			    <td height="60" align="center" bgcolor="#C0C0C0" style="text-align: center;font-weight: bold" width="10%">周报</td>
			    <td align="center" style="text-align: center" width="40%"><%=msWordManager.getContent(tableIndex, 1, 2).substring(0,msWordManager.getContent(tableIndex, 1, 2).length()-2) %></td>
			    <td align="center" bgcolor="#C0C0C0" style="text-align: center;font-weight: bold" width="10%">组织人</td>
			    <td align="center" style="text-align: center" width="10%"><%=msWordManager.getContent(tableIndex, 1, 4).substring(0,msWordManager.getContent(tableIndex, 1, 4).length()-2) %></td>
			    <td align="center" bgcolor="#C0C0C0" style="text-align: center;font-weight: bold" width="10%">职位</td>
			    <td align="center" style="text-align: center" width="20%"><%=msWordManager.getContent(tableIndex, 1, 6).substring(0,msWordManager.getContent(tableIndex, 1, 5).length()-2) %></td>
			  </tr>
			  <tr>
			    <td height="60" align="center" bgcolor="#C0C0C0" style="text-align: center;font-weight: bold">与会人员名单</td>
			    <td colspan="5"><%=msWordManager.getContent(tableIndex, 2, 2).substring(0,msWordManager.getContent(tableIndex, 2, 2).length()-2) %></td>
			  </tr>
			  <tr>
			    <td height="30" colspan="6" align="center" bgcolor="#C0C0C0" style="text-align: center;font-weight: bold">发言记录</td>
			  </tr>
			  <tr>
			    <td colspan="6"><%=msWordManager.getContent(tableIndex, 4, 1).substring(0,msWordManager.getContent(tableIndex, 4, 1).length()-2).replaceAll("\\r" , "<br/>") %></td>
			  </tr>
			  <tr>
			    <td height="60" align="center" bgcolor="#C0C0C0" style="text-align: center;font-weight: bold">发言记录人</td>
			    <td colspan="3"><%=msWordManager.getContent(tableIndex, 5, 2).substring(0,msWordManager.getContent(tableIndex, 5, 2).length()-2) %></td>
			    <td align="center" bgcolor="#C0C0C0" style="text-align: center;font-weight: bold">日期</td>
			    <td><%=msWordManager.getContent(tableIndex, 5, 4).substring(0,msWordManager.getContent(tableIndex, 5, 4).length()-2) %></td>
			  </tr>
			  <tr>
			    <td height="30" colspan="6" align="center" bgcolor="#C0C0C0" style="text-align: center;font-weight: bold">会议决定</td>
			  </tr>
			  <tr>
			    <td colspan="6" style="min-height: 60px;"><%=msWordManager.getContent(tableIndex, 7, 1).substring(0,msWordManager.getContent(tableIndex, 7, 1).length()-2).replaceAll("\\r" , "<br/>") %></td>
			  </tr>
			</table>
		<%msWordManager.close();}}}catch(Exception e){e.printStackTrace(); System.out.println("周会记录文件上传错误");}%>
		<input type="hidden" name="returnPage">
			</div>
		</form>
		<br/>
		<br/>
	</body>
</html>