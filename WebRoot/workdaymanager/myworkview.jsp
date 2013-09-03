<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.sq.sys.ApplicationDate"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="com.sq.model.vo.SqWorkdayManager"%>
<%@page import="com.sq.tools.Public"%>
<%@page import="java.util.Map"%>
<%@page import="com.sq.model.vo.SqUserTab"%>
<%@page import="com.sq.tools.SortList"%>
<%@page import="com.sq.logic.workday.impl.WorkDaySortList"%>
<%@page import="java.util.Set"%>
<%@page import="com.sq.model.vo.SqDocmentManager"%>
<%@page import="com.sq.tools.WorkDayTools"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>我的周报预览</title>
		<link rel="stylesheet" type="text/css" href="<%=basePath %>css/index.css" />
		<link type="text/css" rel="stylesheet" href="<%=basePath %>css/style-home.css" />
		<link type="text/css" rel="stylesheet" href="<%=basePath %>css/style-sys.css" />
		<script type="text/javascript" src="<%=basePath %>js/public.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/jquerybak.js"></script>
		<script type="text/javascript" src="<%=basePath %>workdaymanager/js/myworkdayeditlist.js"></script>
	</head>
	<%
	ApplicationDate.getRequestData("SYS_PARA_DATA" , Arrays.asList("sq_project_stepwork_units") , request , "sq_project_stepwork_units" );
	Map mapStatus = (Map)request.getAttribute("sq_project_stepwork_units");
	SqUserTab sqUserTab = (SqUserTab)session.getAttribute("sqUserTab");
	List<SqWorkdayManager> workDayList = (List)request.getAttribute("workViewList");
	session.removeAttribute("workDayList");
	session.setAttribute("myWorkDayViewList",workDayList);
	WorkDaySortList workDay = new WorkDaySortList();
	workDay.Sort(workDayList , "desc") ;
	int contentSize = workDayList.size() ;
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
	String userName = "" ;
	String groupName = "" ;
	for(int i =0 ; i<workDayList.size() ; i++){
		SqWorkdayManager sqWorkdayManager = (SqWorkdayManager)workDayList.get(i);
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
		String temp = Public.getTimeToFormat(sqWorkdayManager.getWorkDate() , "yyyy年MM月dd日") ;
		if(startDate.equals("")){
			startDate = temp;
			endDate = temp;
		}
		if(sqWorkdayManager.getWeekDate()==null || sqWorkdayManager.getWeekDate().equals("")){
			isShow = true ;
		}
		if(sqWorkdayManager.getSqUserTab() !=null )
		userName = sqWorkdayManager.getSqUserTab().getUserName();
		if(sqWorkdayManager.getSqGroupTab() !=null)
		groupName = sqWorkdayManager.getSqGroupTab().getGroupName();
		//根据指定 String 大于、等于还是小于此 String（不考虑大小写），分别返回一个负整数、0 或一个正整数。
		if(temp.compareToIgnoreCase(startDate) < 0)
			startDate = temp ;
		if(temp.compareToIgnoreCase(endDate) > 0)
			endDate = temp ;
	}
%>
	<body>
	<div style="padding-right: 135px;padding-top:20px;float: right;height: 30px;">
									<div style="width:650px;">
	<%if(isShow){ %>
	<form action=""  name="qform" id="qform">
		<a class="btn" onClick="javascript:window.history.go(-1)" style="margin-left: 270px;">
			<cite>返&nbsp;&nbsp;&nbsp;回</cite></a>
		<a class="btn" onClick="onWorkSub()" style="margin-left: 50px;">
			<cite>提交周报</cite></a>
	</form>
	<% }%>
	<form id="exportform" name="exportform" action="">
		<a class="btn" href="projectmanager/workdayfiledown.shtml?project=9997&returnPage=9997" style="float: right">
					<cite>导出周报到Word</cite></a>
	</div></div>
	<div style="margin-left:auto;margin-right:auto;width:80%">
    <table id="workviewtabhead" width="100%" cellpadding="0" cellspacing="0">
  <tr>
      <td height="50" colspan="8" align="center" bgcolor="#FFFFFF"><font  style="font-weight:bold" size="6">开发人员工作周报</font></td>
    </tr>
    <tr>
      <td height="50" colspan="3" align="left" nowrap bgcolor="#FFFFFF"><strong>姓名：</strong><%=userName %></td>
        <td height="50" colspan="2" align="left" nowrap bgcolor="#FFFFFF"><strong>所属项目组：</strong><%=groupName %></td>
        <td height="50" colspan="3" align="right" nowrap bgcolor="#FFFFFF"><strong> 周报日期：</strong><%=startDate %>至<%=endDate %></td>
    </tr>
</table>
<table id="workviewtab" width="100%" cellpadding="0" cellspacing="0">
    <tr>
      <td width="4%" rowspan="<%=8+contentSize+noworkSize-repeat %>" bgcolor="#FFFFFF"><font  style="font-weight:bold; padding-left:10px;" size="5">本</font>
        <font  style="font-weight:bold; padding-left:10px;" size="5">周</font>
        <font  style="font-weight:bold; padding-left:10px;" size="5">工</font>
        <font  style="font-weight:bold; padding-left:10px;" size="5">作</font>
        <font  style="font-weight:bold; padding-left:10px;" size="5">小</font>
      <font  style="font-weight:bold; padding-left:10px;" size="5">结</font></td>
    <td id="workviewtitlehead" colspan="4" align="center" bgcolor="#C0C0C0"><font  style="font-weight:bold" size="5">工作完成情况</font></td>
  </tr>
  <tr>
    <td width="5%" id="workviewtitlehead" align="center" bgcolor="#C0C0C0" style="text-align: center"><font  style="font-weight:bold" size="4">序号</font></td>
    <td width="19%" id="workviewtitlehead" align="center" bgcolor="#C0C0C0" style="text-align: center"><font  style="font-weight:bold" size="4">项目名称</font></td>
    <td width="51%" id="workviewtitlehead" align="center" bgcolor="#C0C0C0" style="text-align: center"><font  style="font-weight:bold" size="4">工作任务</font></td>
    <td width="10%" id="workviewtitlehead" align="center" bgcolor="#C0C0C0" style="text-align: center"><font  style="font-weight:bold" size="4">完成情况</font></td>
    </tr>
    <%int count1 = 0 ;
    String temp = "";	//记录项目编号
    for(int i=0 ; i<workDayList.size() ; i++){
		   SqWorkdayManager sqWorkdayManager = (SqWorkdayManager)workDayList.get(i);
		   count1 ++ ;
		   temp = sqWorkdayManager.getSqProjectInfo().getProjectId();%>
		  <tr>
		    <td height="60"><%=count1 %></td>
		    <td height="60"><%=sqWorkdayManager.getSqProjectInfo().getProjectName() %></td>
		    <td height="60" style="text-align: left">
		    <%int icount = 1;
		      float totleTime = 0.00f; 
		      StringBuffer strBuff = new StringBuffer();
		    for(int j=i ; j<workDayList.size() ; j++){
		    	SqWorkdayManager sqWorkdayManagerTemp = (SqWorkdayManager)workDayList.get(j);
		    	if(temp.equals(sqWorkdayManagerTemp.getSqProjectInfo().getProjectId())){
		    		totleTime = totleTime + sqWorkdayManagerTemp.getTaskTime();
		    		strBuff.append(sqWorkdayManagerTemp.getWorkContent().replaceAll("\\r\\n" , "<br/>")+"<br/>");
		    		i = j ;
		    	}
		    }
		    out.print(Public.mapInt.get(icount++) + "、总工时："+ totleTime +"小时"+"<br/>"+ strBuff.toString());%>
		    	</td>
		    <td height="60" style="text-align: center"><%=sqWorkdayManager.getComplePercen() %>%</tr>
	   <%} %>
   <%for(int i=0 ; i<2 ; i++){ %>
  <tr>
    <td height="60">&nbsp;</td>
    <td height="60">&nbsp;</td>
    <td height="60" style="text-align: left">&nbsp;</td>
    <td height="60">&nbsp;</td>
    </tr>
   <%} %>
  <tr>
    <td id="workviewtitlehead" colspan="4" align="center" bgcolor="#C0C0C0"><font  style="font-weight:bold" size="5">未完成工作及情况</font></td>
  </tr>
  <tr>
    <td width="5%" id="workviewtitlehead" align="center" bgcolor="#C0C0C0" style="text-align: center"><font  style="font-weight:bold" size="4">序号</font></td>
    <td width="19%" id="workviewtitlehead" align="center" bgcolor="#C0C0C0" style="text-align: center"><font  style="font-weight:bold" size="4">项目名称</font></td>
    <td width="51%" id="workviewtitlehead" colspan="2" align="center" bgcolor="#C0C0C0" style="text-align: center"><font  style="font-weight:bold" size="4">工作任务说明</font></td>
    </tr>
    <% count1 = 0 ;
     temp = "";	//记录项目编号
    for(int i=0 ; i<workDayList.size() ; i++){
		   SqWorkdayManager sqWorkdayManager = (SqWorkdayManager)workDayList.get(i);
		   if(sqWorkdayManager.getNoWorkContent()!=null && !sqWorkdayManager.getNoWorkContent().equals("")){
		   count1 ++ ;
		   temp = sqWorkdayManager.getSqProjectInfo().getProjectId();%>
		  <tr>
		    <td height="60"><%=count1 %></td>
		    <td height="60"><%=sqWorkdayManager.getSqProjectInfo().getProjectName() %></td>
		    <td height="60" colspan="2" style="text-align: left">
		    <%int icount = 1;
		    	SqWorkdayManager sqWorkdayManagerTemp = (SqWorkdayManager)workDayList.get(i);
		    	if(temp.equals(sqWorkdayManagerTemp.getSqProjectInfo().getProjectId()) && sqWorkdayManagerTemp.getNoWorkContent()!=null && !sqWorkdayManagerTemp.getNoWorkContent().equals("")){
		    		out.print(Public.mapInt.get(icount++) + "、" + Public.getTimeToFormat(sqWorkdayManagerTemp.getWorkDate(),"yyyy-MM-dd")+"&nbsp;&nbsp;&nbsp;" + sqWorkdayManagerTemp.getNoWorkContent().replaceAll("\\r\\n" , "<br/>")+"<br/>");
		    	}
		    }%>
		    	</td></tr>
	   <%} %>
   <%for(int i=0 ; i<2 ; i++){ %>
  <tr>
    <td height="60">&nbsp;</td>
    <td height="60">&nbsp;</td>
    <td height="60" colspan="2" style="text-align: left">&nbsp;</td>
    </tr>
   <%}%>
    <tr>
      <td width="4%" rowspan="<%=7+nextPlanSize+discussSize%>" bgcolor="#FFFFFF"><font  style="font-weight:bold; padding-left:10px;" size="5">下</font>
      <font  style="font-weight:bold; padding-left:10px;" size="5">周</font>
      <font  style="font-weight:bold; padding-left:10px;" size="5">工</font>
      <font  style="font-weight:bold; padding-left:10px;" size="5">作</font>
      <font  style="font-weight:bold; padding-left:10px;" size="5">计</font>
      <font  style="font-weight:bold; padding-left:10px;" size="5">划</font></td>
  </tr>
  <tr>
    <td width="5%" id="workviewtitlehead" align="center" bgcolor="#C0C0C0" style="text-align: center"><font  style="font-weight:bold" size="4">序号</font></td>
    <td  id="workviewtitlehead" colspan="3" align="center" bgcolor="#C0C0C0" style="text-align: center"><font  style="font-weight:bold" size="4">工作事项</font></td>
    </tr>
  <%count1 = 0 ;
  for(int i=0 ; i<workDayList.size() ; i++){
		   SqWorkdayManager sqWorkdayManager = (SqWorkdayManager)workDayList.get(i);
		   if(sqWorkdayManager.getWorkNextPlan()!=null && !sqWorkdayManager.getWorkNextPlan().equals("")){
		   count1 ++ ;
			temp = sqWorkdayManager.getSqProjectInfo().getProjectId();%>
		  <tr>
		    <td height="60"><%=count1 %></td>
		    <td height="60"><%=sqWorkdayManager.getSqProjectInfo().getProjectName() %></td>
		    <td height="60" colspan="2" style="text-align: left">
		    <%int icount = 1;
		    	SqWorkdayManager sqWorkdayManagerTemp = (SqWorkdayManager)workDayList.get(i);
		    	if(temp.equals(sqWorkdayManagerTemp.getSqProjectInfo().getProjectId()) && sqWorkdayManagerTemp.getWorkNextPlan()!=null && !sqWorkdayManagerTemp.getWorkNextPlan().equals("")){
		    		out.print(Public.mapInt.get(icount++) + "、" + Public.getTimeToFormat(sqWorkdayManagerTemp.getWorkDate(),"yyyy-MM-dd")+"&nbsp;&nbsp;&nbsp;" + sqWorkdayManagerTemp.getWorkNextPlan().replaceAll("\\r\\n" , "<br/>")+"<br/>");
		    	}
		    }%>
		    </td>
		  </tr>
	   <%} %>
   <%for(int i=0 ; i<2 ; i++){ %>
  <tr>
    <td height="60">&nbsp;</td>
    <td height="60">&nbsp;</td>
    <td height="60" colspan="2" style="text-align: left">&nbsp;</td>
    </tr>
   <%}%>
  <tr>
    <td width="5%" id="titlehead" align="center" bgcolor="#C0C0C0" style="text-align: center"><font  style="font-weight:bold" size="4">序号</font></td>
    <td id="workviewtitlehead" colspan="3" align="center" bgcolor="#C0C0C0" style="text-align: center"><font  style="font-weight:bold" size="4">待协调及待解决事项说明</font></td>
    </tr>
  <%
  count1 = 0 ;
  for(int i=0 ; i<workDayList.size() ; i++){
		   SqWorkdayManager sqWorkdayManager = (SqWorkdayManager)workDayList.get(i);
		   if(sqWorkdayManager.getDiscussProblem()!=null && !sqWorkdayManager.getDiscussProblem().equals("")){
		   count1++;
		   temp = sqWorkdayManager.getSqProjectInfo().getProjectId();%>
		  <tr>
		    <td height="60"><%=count1 %></td>
		    <td height="60" colspan="3" style="text-align: left">
		    <%int icount = 1;
		    	SqWorkdayManager sqWorkdayManagerTemp = (SqWorkdayManager)workDayList.get(i);
		    	if(temp.equals(sqWorkdayManagerTemp.getSqProjectInfo().getProjectId()) && sqWorkdayManagerTemp.getDiscussProblem()!=null && !sqWorkdayManagerTemp.getDiscussProblem().equals("")){
		    		out.print(Public.mapInt.get(icount++) + "、" + Public.getTimeToFormat(sqWorkdayManagerTemp.getWorkDate(),"yyyy-MM-dd")+"&nbsp;&nbsp;&nbsp;" + sqWorkdayManagerTemp.getDiscussProblem().replaceAll("\\r\\n" , "<br/>")+"<br/>");
		    	}
		    }%>
		    </td>
		  </tr>
	   <%}%>
   <%for(int i=0 ; i<2 ; i++){ %>
  <tr>
    <td height="60">&nbsp;</td>
    <td height="60" colspan="3" style="text-align: left">&nbsp;</td>
    </tr>
   <%}%>
    <tr>
      <td width="4%" rowspan="<%=4+workSugSize %>" bgcolor="#FFFFFF"><font  style="font-weight:bold; padding-left:10px;" size="5">工</font>
      <font  style="font-weight:bold; padding-left:10px;" size="5">作</font>
      <font  style="font-weight:bold; padding-left:10px;" size="5">建</font>
      <font  style="font-weight:bold; padding-left:10px;" size="5">议</font></td>
  </tr>
  <tr>
    <td width="5%" id="workviewtitlehead" align="center" bgcolor="#C0C0C0" style="text-align: center"><font  style="font-weight:bold" size="4">序号</font></td>
    <td id="workviewtitlehead" colspan="3" align="center" bgcolor="#C0C0C0" style="text-align: center"><font  style="font-weight:bold" size="4">工作建议</font></td>
    </tr>
   <%count1 = 0;
   for(int i=0 ; i<workDayList.size() ; i++){
		   SqWorkdayManager sqWorkdayManager = (SqWorkdayManager)workDayList.get(i);
		   if(sqWorkdayManager.getWorkSug()!=null && !sqWorkdayManager.getWorkSug().equals("")){
		   count1++ ;
		   temp = sqWorkdayManager.getSqProjectInfo().getProjectId();%>
		  <tr>
		    <td height="60"><%=count1 %></td>
		    <td height="60" colspan="3" style="text-align: left">
		    <%int icount = 1;
		    	SqWorkdayManager sqWorkdayManagerTemp = (SqWorkdayManager)workDayList.get(i);
		    	if(temp.equals(sqWorkdayManagerTemp.getSqProjectInfo().getProjectId()) && sqWorkdayManagerTemp.getWorkSug()!=null && !sqWorkdayManagerTemp.getWorkSug().equals("")){
		    		out.print(Public.mapInt.get(icount++) + "、" + Public.getTimeToFormat(sqWorkdayManagerTemp.getWorkDate(),"yyyy-MM-dd")+"&nbsp;&nbsp;&nbsp;" + sqWorkdayManagerTemp.getWorkSug().replaceAll("\\r\\n" , "<br/>")+"<br/>");
		    	}
		    }%>
		    </td>
		  </tr>
	   <%}%>
   <%for(int i=0 ; i<2 ; i++){ %>
  <tr>
    <td height="60">&nbsp;</td>
    <td height="60" colspan="3" style="text-align: left">&nbsp;</td>
    </tr>
   <%}%>
   <tr>
      <td width="4%" rowspan="<%=4+workSugSize %>" bgcolor="#FFFFFF"><font  style="font-weight:bold; padding-left:10px;" size="5">周</font>
      <font  style="font-weight:bold; padding-left:10px;" size="5">报</font>
      <font  style="font-weight:bold; padding-left:10px;" size="5">附</font>
      <font  style="font-weight:bold; padding-left:10px;" size="5">件</font></td>
  </tr>
  <tr>
    <td id="workviewtitlehead" colspan="4" align="center" bgcolor="#C0C0C0" style="text-align: center"><font  style="font-weight:bold" size="4">附件内容</font></td>
    </tr>
    <tr>
		    <td height="60" colspan="4" style="text-align: left">
   <%
   for(int i=0 ; i<workDayList.size() ; i++){
   SqWorkdayManager sqWorkdayManager = (SqWorkdayManager)workDayList.get(i);
    Set<SqDocmentManager> sqDocumentManageSet = sqWorkdayManager.getSqDocumentManageSet(); 
	    	for (SqDocmentManager sqDocmentManager : sqDocumentManageSet) {
	    		if(sqDocmentManager.getFileName() != null && !sqDocmentManager.getFileName().equals(""))%>
	    		version:<%=sqDocmentManager.getFileVersion()%>&nbsp;&nbsp;<a href="projectmanager/filedown.shtml?filenameInCN=<%=sqDocmentManager.getBackfileName() %>&fileNameCN=<%=sqDocmentManager.getFileName()%>&projectId=<%=sqDocmentManager.getProjectId() %>" class="form_font"><%=sqDocmentManager.getBackfileName()%></a><br/>
	    	<%}
	    }%>&nbsp;
	   </td>
		  </tr>
  </table>
			<p>
				&nbsp;
			</p>
			</div>
			<input type="hidden" name="returnPage" value="9997">
			<input type="hidden" name="projectId" value="9997">
		</form>
	</body>
</html>