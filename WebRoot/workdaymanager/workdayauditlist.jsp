<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.sq.sys.ApplicationDate"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.Map"%>
<%@page import="com.sq.model.vo.SqProjectInfo"%>
<%@page import="java.util.List"%>
<%@page import="com.sq.model.vo.SqWorkdayManager"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="com.sq.tools.Public"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt"       uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>生成项目周报</title>
		<link type="text/css" rel="stylesheet" href="../css/style-home.css" />
		<link type="text/css" rel="stylesheet" href="../css/style-sys.css" />
		<script type="text/javascript" src="../js/public.js"></script>
		<script type="text/javascript" src="../js/jquery.js"></script>
		<script type="text/javascript" src="js/workdayauditlist.js"></script>
	</head>
<%
	ApplicationDate.getRequestData("SYS_PARA_DATA" , Arrays.asList("sq_workday_managertask_id") , request , "sq_workday_managertask_id" );
	ApplicationDate.getRequestData("SYS_PARA_DATA" , Arrays.asList("sq_workday_managertask_status") , request , "sq_workday_managertask_status" );
	ApplicationDate.getRequestData("SYS_PARA_DATA" , Arrays.asList("sq_project_stepwork_units") , request , "sq_project_stepwork_units" );
	Map workUnitsMap = (Map)request.getAttribute("sq_project_stepwork_units") ;
%>
<body>
	<div class="right">
			<div class="right_bt">
				<h1 class="right_bth1">
					项目周报管理
				</h1>
			</div>
			<form action="growthlist.shtml" method="post" name="qform" id="qform">
			<div class="right_centerwk">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="from_btimg form_bt">
							<span>&nbsp;</span>
							<h1>
								查询结果
							</h1>
						</td>
					</tr>
					<tr>
						<td>
						<table align="center" border="0" width="100%" class="form_table" id="comm">
      <tr>
      	<td height="22" style="font-weight:bold" width="10%">项目名称</td>
        <td height="22" style="font-weight:bold" width="37%">日报内容</td>
        <td height="22" style="font-weight:bold" width="12%">未完成工作</td>
        <td height="22" style="font-weight:bold" width="12%">下日计划</td>
        <td height="22" style="font-weight:bold" width="12%">需协调解决问题</td>
        <td height="22" style="font-weight:bold" width="12%">工作建议</td>
        <td height="22" style="font-weight:bold" width="5%">操作</td>
      </tr>
      <%
      	Map<SqProjectInfo, List<SqWorkdayManager>> workDayMap = (Map<SqProjectInfo, List<SqWorkdayManager>>)request.getAttribute("workDayMap");
      	Iterator ite = workDayMap.entrySet().iterator();
      	while(ite.hasNext()){
      		Map.Entry<SqProjectInfo, List<SqWorkdayManager>> entry = (Map.Entry<SqProjectInfo, List<SqWorkdayManager>>) ite.next();
      		SqProjectInfo sqProjectInfo = entry.getKey();//map中的key			
      		List<SqWorkdayManager> sqWorkdayManagerList = entry.getValue();//上面key对应的value
      %>
	  <tr>
	  		<td height="22" id="noWorkContent">
	    		<%=sqProjectInfo.getProjectName() %>
	    	</td>
	    	<td height="22" style="text-align: left" id="workContent">
	    		<%for(int i=0 ; i<sqWorkdayManagerList.size() ; i++ ){ 
	    			SqWorkdayManager sqWorkdayManager = sqWorkdayManagerList.get(i);
	    			out.println(sqWorkdayManager.getSqUserTab().getUserName() + "：&nbsp;&nbsp;"+Public.getTimeToFormat(sqWorkdayManager.getWorkDate() , "yyyy-MM-dd") + "&nbsp;&nbsp;项目阶段：" + sqWorkdayManager.getSqProjectStep().getStepName()+"<br/>");
	    			out.println("&nbsp;&nbsp;&nbsp;&nbsp;" + sqWorkdayManager.getWorkContent().replaceAll("\\r\\n" , "<br/>")+"<br/>");
		    	} %>
	    	</td>
	    	<td height="22" style="text-align: left" id="noWorkContent">
	    		<%for(int i=0 ; i<sqWorkdayManagerList.size() ; i++ ){ 
	    			SqWorkdayManager sqWorkdayManager = sqWorkdayManagerList.get(i);
	    			out.println("&nbsp;&nbsp;&nbsp;&nbsp;" + sqWorkdayManager.getNoWorkContent().replaceAll("\\r\\n" , "<br/>")+"<br/>");
		    	}%>
	    	</td>
	    	<td height="22" style="text-align: left" id="workNextPlan">
	    		<%for(int i=0 ; i<sqWorkdayManagerList.size() ; i++ ){ 
	    			SqWorkdayManager sqWorkdayManager = sqWorkdayManagerList.get(i);
	    			out.println("&nbsp;&nbsp;&nbsp;&nbsp;" + sqWorkdayManager.getWorkNextPlan().replaceAll("\\r\\n" , "<br/>")+"<br/>");
		    	}%>
			</td>
	    	<td height="22" style="text-align: left" id="discussProblem">
	    		<%for(int i=0 ; i<sqWorkdayManagerList.size() ; i++ ){ 
	    			SqWorkdayManager sqWorkdayManager = sqWorkdayManagerList.get(i);
		    	}%>
	    	</td>
	    	<td height="22" style="text-align: left" id="workSug">
	    		<%for(int i=0 ; i<sqWorkdayManagerList.size() ; i++ ){ 
	    			SqWorkdayManager sqWorkdayManager = sqWorkdayManagerList.get(i);
	    			out.println("&nbsp;&nbsp;&nbsp;&nbsp;" + sqWorkdayManager.getWorkSug().replaceAll("\\r\\n" , "<br/>")+"<br/>");
		    	}%>
	    	</td>
	    	<td height="22"><input type="checkbox" name="workData" value="|<%=sqProjectInfo.getProjectId() %>" checked="checked"><img src="../images/lead.gif" width="16" height="16" /></td>
	  </tr>
	  <%} %>
    </table>
						</td>
					</tr>
				</table>
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					bgcolor="#e9f2f7">
					<tr height="35">
						<td align="right" style="text-align: center">
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
			<input type="hidden" name="returnPage" value="growthprolist">
			</form>
		</div>
	<body><br></body>
</html>