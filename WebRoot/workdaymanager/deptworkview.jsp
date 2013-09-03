<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.sq.sys.ApplicationDate"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="com.sq.model.vo.SqWorkdayManager"%>
<%@page import="com.sq.tools.Public"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.sq.model.vo.SqGroupTab"%>
<%@page import="java.util.Set"%>
<%@page import="com.sq.model.vo.SqUserTab"%>
<%@page import="com.sq.model.vo.SqDeptTab"%>
<%@page import="com.sq.model.vo.SqDocmentManager"%>
<%@page import="com.sq.tools.WordBean"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.struts2.ServletActionContext"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="<%=basePath %>css/index.css" />
		<link type="text/css" rel="stylesheet" href="<%=basePath %>css/style-home.css" />
		<link type="text/css" rel="stylesheet" href="<%=basePath %>css/style-sys.css" />
		<script type="text/javascript" src="<%=basePath %>js/public.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/jquerybak.js"></script>
		<script type="text/javascript" src="<%=basePath %>workdaymanager/js/workdaydeptreport.js"></script>
		<title>部门周报预览</title>
<style>
#deptworkview td{
	border-left: 1px solid #F79646;
    border-bottom: 1px solid #F79646;  
}
#deptworkview {
	border-right:1px solid #F79646;
	border-top:1px solid #F79646;
}
</style>
	</head>
	<%
	boolean isShow = false ;
	boolean isDoc = false ;
	String docFileName = "" ;
	String docFileNamePath = "" ;
	String docFileNameZN = "" ;
	String projectId = "";
	int count = 1 ;
	SqUserTab sqUserTabTemp = (SqUserTab)request.getAttribute("sqUserTab");
	SqDeptTab sqDeptTab = (SqDeptTab)request.getAttribute("sqDeptTab");
	Map deptWorkMap = (Map)request.getAttribute("deptWorkMap");
	session.removeAttribute("myWorkDayViewList");
	session.setAttribute("myWorkDayViewList",deptWorkMap);
	Iterator it = deptWorkMap.entrySet().iterator();
	String startDate = "";
	String endDate="";
	while (it.hasNext()) {
          Map.Entry entry = (Map.Entry) it.next();
          List workDayList = (List)entry.getValue();
          for(int i=0 ; i<workDayList.size() ; i++){
    		SqWorkdayManager sqWorkdayManager = (SqWorkdayManager)workDayList.get(i);
    		if(sqWorkdayManager.getWeekDate()==null || sqWorkdayManager.getWeekDate().equals("")){
    			isShow = true ;
    		}
    		sqUserTabTemp = sqWorkdayManager.getSqUserTab();
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
				
			if(sqWorkdayManager.getSqDocumentManageSet().size() > 0){
				isDoc = true ;
				Set<SqDocmentManager> sqDocumentManageSet = sqWorkdayManager.getSqDocumentManageSet(); 
	    		for (SqDocmentManager sqDocmentManager : sqDocumentManageSet) {
	    			docFileName = sqDocmentManager.getFilePath() + sqDocmentManager.getFileName();
	    			projectId = sqDocmentManager.getProjectId();
	    			docFileNameZN = sqDocmentManager.getBackfileName();
	    			docFileNamePath = sqDocmentManager.getFileName();
	    		}
			}
	    	}
     }
%>
	<body>
	<div style="padding-top:20px;float: right;height: 30px;width: 100%;text-align: right">
	<form id="exportform" name="exportform">
	<%if(isDoc){ %>
		<a class="btn" href="projectmanager/filedown.shtml?projectId=<%=projectId %>&fileNameCN=<%=docFileNamePath %>&filenameInCN=<%=docFileNameZN %>" style="float: right;margin-right: 150px;">
					<cite>导出周报到Word</cite></a>
	<%}else{%>
		<a class="btn" href="projectmanager/workdayfiledown.shtml?project=9999&returnPage=9999&filenameInCN=<%=sqUserTabTemp.getUserName() %>&filenameCN=<%=sqDeptTab.getDeptName() %>" style="float: right;margin-right: 150px;">
					<cite>导出周报到Word</cite></a>
	<%} %>
	</form>
	<%if(isShow){ %>
	<form action=""  name="qform" id="qform">
		<a class="btn" onClick="onDeptSub()" style="margin-right: 50px;float: right">
					<cite>提交周报</cite></a>
		<a class="btn" onClick="javascript:window.history.go(-1)" style="margin-right: 50px;float: right">
			<cite>返&nbsp;&nbsp;&nbsp;回</cite></a>
	</form>
	<% }%>
	</div>
	<div style="margin-left:auto;margin-right:auto;width:80%">
	<%if(isDoc){
	String targetDirectory = ServletActionContext.getServletContext().getRealPath("\\");
	WordBean msWordManager = new WordBean();
	msWordManager.saveAsHtmlFile(targetDirectory+docFileName, targetDirectory+docFileName.substring(0,docFileName.lastIndexOf(".")) + ".htm");
	msWordManager.close();
	%>
		<jsp:include page='<%="/"+docFileName.substring(0,docFileName.lastIndexOf(".")) + ".htm" %>'></jsp:include>
	<%}else{ %>
		<jsp:include page="deptworkviewbody.jsp"></jsp:include>
	<%} %>
	</div>
	</body>
</html>