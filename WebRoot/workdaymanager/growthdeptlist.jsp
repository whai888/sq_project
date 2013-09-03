<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.sq.sys.ApplicationDate"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.Map"%>
<%@page import="com.sq.model.vo.SqGroupTab"%>
<%@page import="java.util.List"%>
<%@page import="com.sq.model.vo.SqWorkdayManager"%>
<%@page import="java.util.Iterator"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt"       uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>生成部门周报</title>
		<link type="text/css" rel="stylesheet" href="../css/style-home.css" />
		<link type="text/css" rel="stylesheet" href="../css/style-sys.css" />
		<script type="text/javascript" src="../js/jquery.js"></script>
		<script type="text/javascript" src="js/groupthdeptlist.js"></script>
	</head>
<%
	ApplicationDate.getRequestData("SYS_PARA_DATA" , Arrays.asList("sq_workday_managertask_id") , request , "sq_workday_managertask_id" );
	ApplicationDate.getRequestData("SYS_PARA_DATA" , Arrays.asList("sq_workday_managertask_status") , request , "sq_workday_managertask_status" );
	ApplicationDate.getRequestData("SYS_PARA_DATA" , Arrays.asList("sq_project_stepwork_units") , request , "sq_project_stepwork_units" );
%>
	<body>
	<div class="right">
			<div class="right_bt">
				<h1 class="right_bth1">
					生成部门周报
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
      <tr id="tab">
        <td height="22" style="font-weight:bold" width="8%">项目组</td>
        <td height="22" style="font-weight:bold" width="49%">工作完成情况</td>
        <td height="22" style="font-weight:bold" width="8%">未完成任务</td>
        <td height="22" style="font-weight:bold" width="10%">下周计划</td>
        <td height="22" style="font-weight:bold" width="10%">需协调解决的问题</td>
        <td height="22" style="font-weight:bold" width="10%">工作建议</td>
        <td height="22" style="font-weight:bold" width="5%">操作</td>
      </tr>
       <%
      	Map<SqGroupTab, List<SqWorkdayManager>> workDayMap = (Map<SqGroupTab, List<SqWorkdayManager>>)request.getAttribute("workDayMap");
      	Iterator ite = workDayMap.entrySet().iterator();
      	while(ite.hasNext()){
      		Map.Entry<SqGroupTab, List<SqWorkdayManager>> entry = (Map.Entry<SqGroupTab, List<SqWorkdayManager>>) ite.next();
      		SqGroupTab sqGroupTab = entry.getKey();//map中的key			
      		List<SqWorkdayManager> sqWorkdayManagerList = entry.getValue();//上面key对应的value
      %>
	  <tr>
	  		<td height="22">
	    		<%=sqGroupTab.getGroupName() %>
	    	</td>
	    	<td height="22" style="text-align: left" id="workContent">
	    		<%for(int i=0 ; i<sqWorkdayManagerList.size() ; i++ ){ 
	    			SqWorkdayManager sqWorkdayManager = sqWorkdayManagerList.get(i);
	    			out.println(sqWorkdayManager.getSqProjectInfo().getProjectName()+"&nbsp;&nbsp;&nbsp;&nbsp;当前里程碑："+sqWorkdayManager.getSqProjectStep().getStepName()+"，完成比例："+sqWorkdayManager.getComplePercen()+"%，任务状态："+sqWorkdayManager.getTaskStatus()+"<br/>");
	    			out.println("&nbsp;&nbsp;&nbsp;&nbsp;" + sqWorkdayManager.getWorkContent().replaceAll("\\r\\n" , "<br/>"));
		    	} %>
	    	</td>
	    	<td height="22">
	    		<%for(int i=0 ; i<sqWorkdayManagerList.size() ; i++ ){ 
	    			SqWorkdayManager sqWorkdayManager = sqWorkdayManagerList.get(i);
	    			out.println("&nbsp;&nbsp;&nbsp;&nbsp;" + sqWorkdayManager.getNoWorkContent().replaceAll("\\r\\n" , "<br/>"));
		    	}%>
	    	</td>
	    	<td height="22" id="workNextPlan">
	    		<%for(int i=0 ; i<sqWorkdayManagerList.size() ; i++ ){ 
	    			SqWorkdayManager sqWorkdayManager = sqWorkdayManagerList.get(i);
	    			out.println("&nbsp;&nbsp;&nbsp;&nbsp;" + sqWorkdayManager.getWorkNextPlan().replaceAll("\\r\\n" , "<br/>"));
		    	}%>
			</td>
	    	<td height="22" id="discussProblem">
	    		<%for(int i=0 ; i<sqWorkdayManagerList.size() ; i++ ){ 
	    			SqWorkdayManager sqWorkdayManager = sqWorkdayManagerList.get(i);
	    			out.println("&nbsp;&nbsp;&nbsp;&nbsp;" + sqWorkdayManager.getDiscussProblem().replaceAll("\\r\\n" , "<br/>"));
		    	}%>
	    	</td>
	    	<td height="22" id="workSug">
	    		<%for(int i=0 ; i<sqWorkdayManagerList.size() ; i++ ){ 
	    			SqWorkdayManager sqWorkdayManager = sqWorkdayManagerList.get(i);
	    			out.println("&nbsp;&nbsp;&nbsp;&nbsp;" + sqWorkdayManager.getWorkSug().replaceAll("\\r\\n" , "<br/>"));
		    	}%>
	    	</td>
	    	<td height="22"><input type="checkbox" name="workData" value="|<%=sqGroupTab.getGroupNo() %>" checked="checked"><img src="../images/lead.gif" width="16" height="16" /></td>
	  </tr>
	  <%} %>
    </table>
						</td>
					</tr>
				</table>
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					bgcolor="#e9f2f7">
					<tr height="35">
						<td align="right"style="text-align:center">
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
			<input type="hidden" name="returnPage" value="growthdeptlist">
		</form>
		</div>
	</body>
</html>