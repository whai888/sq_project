<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.sq.sys.ApplicationDate"%>
<%@page import="java.util.Arrays"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt"       uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>项目信息阶段新增</title>
		<link type="text/css" rel="stylesheet" href="../css/style-home.css" />
		<link type="text/css" rel="stylesheet" href="../css/style-sys.css" />
		<script type="text/javascript" src="../js/public.js"></script>
		<script type="text/javascript" src="../js/jquery.js"></script>
		<jsp:include page="../js/index.jsp"></jsp:include>
		<script type="text/javascript" src="js/projectaddstep.js"></script>
	</head>
<%
	ApplicationDate.getRequestData("SYS_PARA_DATA" , Arrays.asList("sq_project_stepstep_status") , request , "sq_project_stepstep_status" );
	ApplicationDate.getRequestData("SYS_PARA_DATA" , Arrays.asList("sq_project_stepday_status") , request , "sq_project_stepday_status" );
	ApplicationDate.getRequestData("SYS_PARA_DATA" , Arrays.asList("sq_project_stepkey_status") , request , "sq_project_stepkey_status" );
%>
	<body>
	<div class="right">
			<div class="right_bt">
				<h1 class="right_bth1">
					项目信息阶段新增
				</h1>
			</div>
			<form action="projectadduser.shtml" method="post" name="jform" id="jform">
				<div class="right_centerwk">
					<table width="100%" border="0" width="100%" class="form_table_l">
     <tr>
        <td height="32" style="font-weight: bold; text-align: right" width="150px;">项目名称：</td>
        <td height="32" class="bgcolor1 fontleft fontbold"  width="200px;" colspan="3">
        	<s:property value="sqProjectInfo.projectName"/>
        	<input type="hidden" name="sqProjectInfo.projectName" value="<s:property value="sqProjectInfo.projectName"/>">
        </td>
        <td colspan="2" height="52" style="text-align: center" align="center">
			<div style="text-align: center;">
									<div style="width:330px;margin: 0 auto;">
				<a class="btn" onClick="onAdd()" style="padding-right: 60px;">
				<cite>新&nbsp;&nbsp;&nbsp;增</cite></a>
			<a class="btn" onClick="onUpdate();" style="padding-right: 60px;">
				<cite>修&nbsp;&nbsp;&nbsp;改</cite></a>
			<a class="btn" onClick="onDelete();">
				<cite>删&nbsp;&nbsp;&nbsp;除</cite></a>
				</div>
				</div>
		</td>
      </tr>
      <tr>
        <td height="32" style="font-weight: bold; text-align: right" width="150px;">上级里程碑名称：</td>
        <td height="32" class="bgcolor1 fontleft fontbold" width="200px;">
        	<s:select list="#request.projectStepList" theme="simple" listKey="stepOrder" listValue="{stepOrder,stepName}" name="sqProjectStep.stepOrder" headerKey="0" headerValue="——请选择——"></s:select>
        </td>
        <td height="32" style="font-weight: bold; text-align: right" width="150px;">里程碑名称：</td>
        <td height="32" class="bgcolor1 fontleft fontbold" width="200px;" colspan="3">
        	<input type="text" value="<s:property value="sqProjectStep.stepName"/>" name="sqProjectStep.stepName" onblur="isCheckMaxLenth(this)" maxlength="100">
        </td>
      </tr>
       <tr>
        <td height="32" style="font-weight: bold; text-align: right">开始时间：</td>
        <td height="32" class="bgcolor1 fontleft fontbold">
        	<input type="text" id="datepick1" maxlength="10" onfocus="$(this).calendar()" value="<s:date name="sqProjectStep.startDate" format="yyyy-MM-dd"/>" name="sqProjectStep.startDate"></td>
        <td height="32" style="font-weight: bold; text-align: right">结束时间：</td>
        <td height="32" class="bgcolor1 fontleft fontbold" colspan="3">
        	<input type="text" id="datepick2" maxlength="10" onfocus="$(this).calendar()" value="<s:date name="sqProjectStep.endDate" format="yyyy-MM-dd"/>" name="sqProjectStep.endDate"></td>
      	
       </tr>
       <tr>
       		<td height="32" style="font-weight: bold; text-align: right">占上级里程碑百分比：</td>
	        <td height="32" class="bgcolor1 fontleft fontbold">
	        	<input type="text" name="sqProjectStep.upPercent" value="<s:property value="sqProjectStep.upPercent"/>" onblur="isCheckMaxLenth(this)" onkeyup="isInputCheckNumber(this)" maxlength="3" size="5">%
	        </td>
        	<td height="32" style="font-weight: bold; text-align: right">任务完成百分比：</td>
	        <td height="32" class="bgcolor1 fontleft fontbold">
	        	<input type="text" name="sqProjectStep.succPercent" value="<s:property value="sqProjectStep.succPercent"/>" onblur="isCheckMaxLenth(this)" onkeyup="isInputCheckNumber(this)" maxlength="3" size="5"/>%
	        </td>
	        <td height="32" style="font-weight: bold; text-align: right">任务提前滞后天数：</td>
	        <td height="32" class="bgcolor1 fontleft fontbold">
	        	<input type="text" name="sqProjectStep.lagDay" value="<s:property value="sqProjectStep.lagDay"/>" onblur="isCheckMaxLenth(this)" onkeyup="isInputCheckNumber(this)" maxlength="4" size="5">天
	        </td>
       </tr>
       <tr>
       		<td height="32" style="font-weight: bold; text-align: right">日报节点标志：</td>
	        <td height="32" class="bgcolor1 fontleft fontbold">
	        	<s:select name="sqProjectStep.dayStatus" theme="simple" list="#request.sq_project_stepday_status"></s:select>
	        </td>
        	<td height="32" style="font-weight: bold; text-align: right">关键路径标志：</td>
	        <td height="32" class="bgcolor1 fontleft fontbold">
	        	<s:select name="sqProjectStep.keyStatus" theme="simple" list="#request.sq_project_stepkey_status"></s:select>
	        </td>
	        <td height="32" style="font-weight: bold; text-align: right">里程碑状态：</td>
	        <td height="32" class="bgcolor1 fontleft fontbold">
	        	<s:select name="sqProjectStep.stepStatus" theme="simple" list="#request.sq_project_stepstep_status"></s:select>
	        </td>
       </tr>
      <tr>
        <td height="32" style="font-weight: bold; text-align: right">项目里程碑描述：</td>
        <td height="32" class="bgcolor1 fontleft fontbold" colspan="5">
        	<textarea rows="3" cols="60" name="sqProjectStep.stepRemark"><s:property value="sqProjectStep.stepRemark"/></textarea>
        </td>
      </tr>
      </table>
				</div>
			<input type="hidden" name="sqProjectStep.projectId" value="<s:property value="sqProjectStep.projectId"/>">
			<input type="hidden" name="sqProjectInfo.projectId" value="<s:property value="sqProjectInfo.projectId"/>">
		    <input type="hidden" name="sqProjectStep.stepId" value="<s:property value="sqProjectStep.stepId"/>">
		    </form>
		</div>
    
				<div class="right_centerwk">
					<table align="center" border="0" width="100%" class="form_table" id="comm"> 
      <tr id="tab">
        <td height="22" style="font-weight:bold" width="15%">里程碑名称</td>
        <td height="22" style="font-weight:bold" width="8%">开始日期</td>
        <td height="22" style="font-weight:bold" width="8%">结束日期</td>
        <td height="22" style="font-weight:bold" width="6%">日报节<br/>点标识</td>
        <td height="22" style="font-weight:bold" width="6%">关键路<br/>径标识</td>
        <td height="22" style="font-weight:bold" width="6%">占上级阶<br/>段百分比</td>
        <td height="22" style="font-weight:bold" width="6%">任务完成<br/>百分比</td>
        <td height="22" style="font-weight:bold" width="6%">任务提前<br/>滞后天数</td>
        <td height="22" style="font-weight:bold" width="29%">项目里程碑描述</td>
        <td height="22" style="font-weight:bold" width="10%">状态</td>
      </tr>
      <s:iterator value="#request.projectStepList" var="sqProjectStep" >
		  <tr height="22" style="cursor: pointer;" onclick="update('<s:property value="stepId"/>','<s:property value="upStepId"/>','<s:property value="stepOrder"/>','<s:property value="stepName"/>','<fmt:formatDate value="${startDate}" type="date" pattern="yyyy-MM-dd" />','<fmt:formatDate value="${endDate}" type="date" pattern="yyyy-MM-dd" />','<s:property value="upPercent"/>','<s:property value="succPercent"/>','<s:property value="lagDay"/>','<s:property value="stepRemark"/>','<s:property value="dayStatus"/>','<s:property value="keyStatus"/>','<s:property value="stepStatus"/>')">
		    <td style="text-align: left" >
		    <!-- 项目是否延迟的标志 	1 红色 2 黄色 3 绿色-->
		    <s:if test="%{#sqProjectStep.status == 1}"><font color="#FF0000">？</font> </s:if>
		    <s:if test="%{#sqProjectStep.status == 2}"><font color="#FFFF00">？</font> </s:if>
		    <s:if test="%{#sqProjectStep.status == 3}"><font color="#00FF00">？</font></s:if>
		    <s:property value="stepOrder"/>——<s:property value="stepName"/></td>
		    <td ><fmt:formatDate value="${startDate}" type="date" pattern="yyyy-MM-dd" /></td>
		    <td ><fmt:formatDate value="${endDate}" type="date" pattern="yyyy-MM-dd" /></td>
		    <td ><s:property value="#request.sq_project_stepday_status[dayStatus]"/></td>
		    <td ><s:property value="#request.sq_project_stepkey_status[keyStatus]"/></td>
		    <td ><s:property value="upPercent"/></td>
		    <td ><s:property value="succPercent"/></td>
		    <td ><s:property value="lagDay"/></td>
		    <td style="text-align: left" ><s:property value="stepRemark"/></td>
		    <td ><s:property value="#request.sq_project_stepstep_status[stepStatus]"/></td>
		    
		  </tr>
		  </s:iterator>
    </table>
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
					bgcolor="#e9f2f7">
					<tr height="35">
						<td>
							&nbsp;
						</td>
						<td></td>
					</tr>
				</table>
		</div>
	</body>
</html>