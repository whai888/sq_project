<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.sq.sys.ApplicationDate"%>
<%@page import="java.util.Arrays"%>
<%@page import="com.sq.model.vo.SqProjectInfo"%>
<%@page import="com.sq.tools.Public"%>
<%@page import="java.util.List"%>
<%@page import="com.sq.model.vo.SqProjectUser"%>
<%@page import="java.util.Map"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt"       uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>项目信息阶段详情显示</title>
		<link type="text/css" rel="stylesheet" href="../css/style-home.css" />
		<link type="text/css" rel="stylesheet" href="../css/style-sys.css" />
	</head>
<%
	ApplicationDate.getRequestData("SYS_PARA_DATA" , Arrays.asList("sq_project_stepstep_status") , request , "sq_project_stepstep_status" );
	ApplicationDate.getRequestData("SYS_PARA_DATA" , Arrays.asList("sq_project_stepday_status") , request , "sq_project_stepday_status" );
	ApplicationDate.getRequestData("SYS_PARA_DATA" , Arrays.asList("sq_project_stepkey_status") , request , "sq_project_stepkey_status" );
	ApplicationDate.getRequestData("SYS_PARA_DATA" , Arrays.asList("sq_project_infostatus") , request , "sq_project_infostatus" );
	Map projectStatusMap = (Map)request.getAttribute("sq_project_infostatus");
	SqProjectInfo sqProjectInfo = (SqProjectInfo)request.getAttribute("sqProjectInfo");
%>
	<body>
    <div class="right">
			<div class="right_bt">
				<h1 class="right_bth1">
					项目管理
				</h1>
			</div>
			<div class="right_centerwk">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="from_btimg form_bt">
							<span>&nbsp;</span>
							<h1>
								项目信息
							</h1>
						</td>
					</tr>
					<tr>
						<td>
							<table border="0" width="100%" class="form_table_l">
     <tr>
        <td height="32" style="font-weight: bold; text-align: right" >项目名称：</td>
        <td height="32" colspan="3">
        	<%=sqProjectInfo.getProjectName() %>
        </td>
      </tr>
      <tr>
        <td height="32" style="font-weight: bold; text-align: right" width="200px;">项目经理：</td>
        <td height="32">
        	<%=sqProjectInfo.getSqUserTab().getUserName() %>
        </td>
        <td height="32" style="font-weight: bold; text-align: right;">所属项目组：</td>
        <td height="32" width="200px;">
        	<%=sqProjectInfo.getSqGroupTab().getGroupName() %>
        </td>
      </tr>
       <tr>
        <td height="32" style="font-weight: bold; text-align: right">计划开始时间：</td>
        <td height="32">
        	<%=Public.getTimeToFormat(sqProjectInfo.getStartDate(),"yyyy-MM-dd") %></td>
        <td height="32" style="font-weight: bold; text-align: right">计划结束时间：</td>
        <td height="32">
        	<%=Public.getTimeToFormat(sqProjectInfo.getAdvanceDate(),"yyyy-MM-dd") %>
        </td>
      </tr>
      <tr>
        <td height="32" style="font-weight: bold; text-align: right">项目成员：</td>
        <td height="32">
        	<% 
        		List userList = sqProjectInfo.getSqProjectUserList();
        		StringBuffer userMgn = new StringBuffer();
        		StringBuffer userTrem = new StringBuffer();
        		for(int i=0; i<userList.size(); i++){
        			SqProjectUser sqProjectUser = (SqProjectUser)userList.get(i);
        			if(sqProjectUser.getId().getUserType().equals("2")){
        				userMgn.append(sqProjectUser.getSqUserTab().getUserName() + "  ");
        			}else if(sqProjectUser.getId().getUserType().equals("3")){
        				userTrem.append(sqProjectUser.getSqUserTab().getUserName() + "  ");
        			}
        		}
        		out.println("管理组：" + userMgn.toString() + "<br/><br/>");
        		out.println("成员组：" + userTrem.toString());
        	%>
        </td>
        <td height="32" style="font-weight: bold; text-align: right">项目状态：</td>
        <td height="32">
        	<%=projectStatusMap.get(sqProjectInfo.getStatus()) %>
        </td>
      </tr>
      <tr>
        <td height="32" style="font-weight: bold; text-align: right">项目摘要</td>
        <td height="32" colspan="3"><%=sqProjectInfo.getResume() %></td>
      </tr>
      </table>
						</td>
					</tr>
				</table>
				<br/><br/>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="from_btimg form_bt">
							<span>&nbsp;</span>
							<h1>
								项目里程碑详情
							</h1>
						</td>
					</tr>
					<tr>
						<td>
							<table align="center" border="0" width="100%" class="form_table" id="comm">
      <tr id="tab">
        <td height="22" style="font-weight: bold" width="15%">里程碑名称</td>
        <td height="22" style="font-weight: bold" width="8%">开始日期</td>
        <td height="22" style="font-weight: bold" width="8%">结束日期</td>
        <td height="22" style="font-weight: bold" width="6%">日报节<br/>点标识</td>
        <td height="22" style="font-weight: bold" width="6%">关键路<br/>径标识</td>
        <td height="22" style="font-weight: bold" width="6%">占上级里程<br/>碑百分比</td>
        <td height="22" style="font-weight: bold" width="6%">任务完成<br/>百分比</td>
        <td height="22" style="font-weight: bold" width="6%">任务提前<br/>滞后天数</td>
        <td height="22" style="font-weight: bold">里程碑描述</td>
        <td height="22" style="font-weight: bold" width="6%x">状态</td>
        <td height="22" style="font-weight: bold" width="7%">文档操作</td>
      </tr>
      <s:iterator value="#request.projectStepList" var="sqProjectStep" >
		  <tr height="22">
		    <td style="text-align: left">
		    <!-- 项目是否延迟的标志 	1 红色 2 黄色 3 绿色-->
		    <s:if test="%{#sqProjectStep.status == 1}"><font color="#FF0000">？</font> </s:if>
		    <s:if test="%{#sqProjectStep.status == 2}"><font color="#FFFF00">？</font> </s:if>
		    <s:if test="%{#sqProjectStep.status == 3}"><font color="#00FF00">？</font></s:if>
		    <s:property value="stepOrder"/>——<s:property value="stepName"/></td>
		    <td><fmt:formatDate value="${startDate}" type="date" pattern="yyyy-MM-dd" /></td>
		    <td><fmt:formatDate value="${endDate}" type="date" pattern="yyyy-MM-dd" /></td>
		    <td><s:property value="#request.sq_project_stepday_status[dayStatus]"/></td>
		    <td><s:property value="#request.sq_project_stepkey_status[keyStatus]"/></td>
		    <td><s:property value="upPercent"/></td>
		    <td><s:property value="succPercent"/></td>
		    <td><s:property value="lagDay"/></td>
		    <td style="text-align: left"><s:property value="stepRemark"/></td>
		    <td><s:property value="#request.sq_project_stepstep_status[stepStatus]"/></td>
		     <td><img src="../images/details.gif" width="16" height="16" />
		     <a href="projectdocfindinfo.shtml?sqDocmentManager.projectId=<s:property value="projectId"/>&sqDocmentManager.stepId=<s:property value="stepOrder"/>" class="form_font">文档管理</a>&nbsp;&nbsp;&nbsp;&nbsp;
		  </tr>
		  </s:iterator>
    </table>
						</td>
					</tr>
				</table>
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					bgcolor="#e9f2f7">
					<tr height="35">
						<td align="center" width="30%">
							&nbsp;
						</td>
						<td width="70%" align="right">
							&nbsp;
						</td>
					</tr>
				</table>
			</div>
		</div>
	</body>
</html>