<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.sq.sys.ApplicationDate"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="com.sq.model.vo.SqWorkdayManager"%>
<%@page import="com.sq.tools.Public"%>
<%@page import="java.util.Map"%>
<%@page import="com.sq.tools.WorkDayTools"%>
<%@page import="com.sq.model.vo.SqUserTab"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt"       uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>周日报查询</title>
		<link type="text/css" rel="stylesheet" href="../css/style-home.css" />
		<link type="text/css" rel="stylesheet" href="../css/style-sys.css" />
		<script type="text/javascript" src="../js/public.js"></script>
		<script type="text/javascript" src="../js/jquerybak.js"></script>
		<script type="text/javascript" src="js/myworkdaylist.js"></script>
		<jsp:include page="../js/index.jsp"></jsp:include>
	</head>
	<%
	ApplicationDate.getRequestData("SYS_PARA_DATA" , Arrays.asList("sq_project_stepwork_units") , request , "sq_project_stepwork_units" );
	Map workUnitsMap = (Map)request.getAttribute("sq_project_stepwork_units");
		List workViewList = (List)request.getAttribute("workViewList");
		SqUserTab sqUserTab = (SqUserTab)session.getAttribute("sqUserTab");
	%>
	<body>
	<div class="right">
			<div class="right_bt">
				<h1 class="right_bth1">
					员工日报管理
				</h1>
			</div>
			<form action="workdaylist.shtml" method="post" name="jform" id="jform" enctype="application/x-www-form-urlencoded">
				<div class="right_centerwk">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="form_btimg form_bt">
								<span>&nbsp;</span>
								<h1>
									查 询
								</h1>
							</td>
						</tr>
						<tr>
							<td>
								<table border="0" width="100%" class="form_table_l">
     <tr>
       <td height="32" style="font-weight: bold; text-align: right" width="150px;">开始日期：</td>
        <td height="32" width="100px;">
        		<input type="text" name="startDate" value="<s:property value="#request.startDate"/>" maxlength="10" onfocus="$(this).calendar()" id="datepick1">
        </td>
        <td height="32" style="font-weight: bold; text-align: right" width="150px;">结束日期：</td>
        <td height="32" width="100px;">
        		<input type="text" name="endDate" value="<s:property value="#request.endDate"/>" maxlength="10" onfocus="$(this).calendar()" id="datepick2">
        </td>
        <%if(sqUserTab.isSysMagnUser("006") || sqUserTab.isSysMagnUser("007")){ %>
        <td height="32" style="font-weight: bold; text-align: right" width="150px;">项目组：</td>
        <td height="32" width="100px;">
        		<s:select list="#request.groupList" theme="simple" name="sqGroupTab.groupNo" listKey="groupNo" listValue="groupName" headerKey="-99" headerValue="——全部——" onchange="onSub('1')"></s:select>
        </td>
        <td height="32" style="font-weight: bold; text-align: right" width="150px;">所属项目：</td>
        <td height="32" width="100px;">
        		<s:select list="#request.projectList" theme="simple" name="sqProjectInfo.projectId" listKey="projectId" listValue="projectName" headerKey="-99" headerValue="——全部——" onchange="onSub('1')"></s:select>
        </td>
        <%} %>
        <td height="32" style="font-weight: bold; text-align: right" width="150px;">员工姓名：</td>
        <td height="32" width="100px;">
        	<s:select list="#request.userList" theme="simple" name="sqUserTab.userId" listKey="userId" listValue="userName" headerKey="-99" headerValue="——全部——"></s:select>
        </td>
        <td height="32" style="text-align: center">
      		<div style="text-align: center;">
									<div style="width:70px;margin: 0 auto;">
				<a class="btn" onClick="onSub('1')" >
				<cite>查&nbsp;&nbsp;&nbsp;询</cite></a>
				</div></div>
        </td>
      </tr>
    </table></td>
      </tr>
    </table>
    </div>
    <input type="hidden" name="returnPage" value="workdaylist">
    <input type="hidden" name="pageTag" value="<s:property value="pageTag"/>">
	<input type="hidden" name="currentStr" value="">
    </form>
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
      	<td height="22" style="font-weight:bold" width="5%">日报日期</td>
      	<td height="22" style="font-weight:bold" width="5%">姓名</td>
      	<td height="22" style="font-weight:bold" width="6%">项目组</td>
      	<td height="22" style="font-weight:bold" width="9%">项目名称</td>
      	<td height="22" style="font-weight:bold" width="9%">里程碑</td>
        <td height="22" style="font-weight:bold" width="28%">日报内容</td>
        <td height="22" style="font-weight:bold" width="11%">未完成工作</td>
        <td height="22" style="font-weight:bold" width="9%">下日计划</td>
        <td height="22" style="font-weight:bold" width="9%">需协调解决问题</td>
        <td height="22" style="font-weight:bold" width="9%">工作建议</td>
      </tr>
      <%if(workViewList.size()>0){
       for(int i=0; i<workViewList.size() ; i++){
       	SqWorkdayManager sqWorkdayManager = (SqWorkdayManager)workViewList.get(i);%>
	  <tr>
	  		<td height="22" id="noWorkContent">
	  			<%=Public.getTimeToFormat(sqWorkdayManager.getWorkDate() , "yyyy-MM-dd") %>
	    	</td>
	    	<td height="22">
	    		<%=sqWorkdayManager.getSqUserTab().getUserName() %>
	    	</td>
	    	<td height="22">
	    		<%if(sqWorkdayManager.getSqGroupTab()!=null)out.println(sqWorkdayManager.getSqGroupTab().getGroupName()); %>
	    	</td>
	  		<td height="22" id="noWorkContent">
	  			<%=sqWorkdayManager.getSqProjectInfo().getProjectName() %>
	    	</td>
	    	<td height="22">
	    		<%=sqWorkdayManager.getSqProjectStep().getStepName() %>
	    	</td>
	    	<td height="22" style="text-align: left" id="workContent">
			    任务时长<%=sqWorkdayManager.getTaskTime()%>小时，工作成果：<%=WorkDayTools.workLoadStr(workUnitsMap ,sqWorkdayManager.getWorkLoad(),sqWorkdayManager.getWorkUnits()+"") %>，完成比例：<%=sqWorkdayManager.getComplePercen()%>%<br/>
			    <%=sqWorkdayManager.getWorkContent().replaceAll("\\r\\n" , "<br/>") %><br/>
	    	</td>
	    	<td height="22" style="text-align: left" id="noWorkContent">
	    		<%=sqWorkdayManager.getNoWorkContent().replaceAll("\\r\\n" , "<br/>") %>
	    	</td>
	    	<td height="22" style="text-align: left" id="workNextPlan">
	    		<%=sqWorkdayManager.getWorkNextPlan().replaceAll("\\r\\n" , "<br/>") %>
			</td>
	    	<td height="22" vid="discussProblem">
	    		<%=sqWorkdayManager.getDiscussProblem().replaceAll("\\r\\n" , "<br/>") %>
	    	</td>
	    	<td height="22" style="text-align: left" id="workSug">
	    		<%=sqWorkdayManager.getWorkSug().replaceAll("\\r\\n" , "<br/>") %>
	    	</td>
	  </tr>
	  <%}} %>
    </table>
						</td>
					</tr>
				</table>
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					bgcolor="#e9f2f7">
					<tr height="35">
						<td align="right" style="text-align: center">
							<div id="pageTag"></div></td>
					</tr>
				</table>
			</div>
   </div>
	</body>
</html>