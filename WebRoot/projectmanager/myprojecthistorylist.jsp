<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.sq.sys.ApplicationDate"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Arrays"%>
<%@page import="com.sq.model.vo.SqUserTab"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s"  uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>历史项目查询</title>
		<link type="text/css" rel="stylesheet" href="../css/style-home.css" />
		<link type="text/css" rel="stylesheet" href="../css/style-sys.css" />
		<script type="text/javascript" src="../js/public.js"></script>
		<script type="text/javascript" src="../js/jquerybak.js"></script>
		<jsp:include page="../js/index.jsp"></jsp:include>
		<script type="text/javascript" src="js/myprojecthistorylist.js"></script>
</head>
<%
	SqUserTab sqUserTabTemp = (SqUserTab)session.getAttribute("sqUserTab");	
	ApplicationDate.getRequestData("SYS_PARA_DATA" , Arrays.asList("sq_project_infostatus") , request , "sq_project_infostatus" );
%>
<body>
<div class="right">
			<div class="right_bt">
				<h1 class="right_bth1">
					历史项目查询
				</h1>
			</div>
			<form action="" method="post" name="jform" id="jform">
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
        <td height="32" style="font-weight: bold; text-align: right">项目开始时间：</td>
        <td height="32"  align="center" colspan="3">
        	<input type="text" name="startData" value="" maxlength="10" onfocus="$(this).calendar()"  id="datepick1">至
        	<input type="text" name="endData" value="" maxlength="10" onfocus="$(this).calendar()"  id="datepick2">
        </td>
        <td rowspan="3" style="text-align: center">
        <div style="text-align: center;">
									<div style="width:70px;margin: 0 auto;">
				<a class="btn" onClick="onSub('1')" >
				<cite>查&nbsp;&nbsp;&nbsp;询</cite></a>
				</div></div>
				</td>
      </tr>
      <tr>
        <td height="32" style="font-weight: bold; text-align: right">项目状态：</td>
        <td height="32" >
        	<s:select name="sqProjectInfo.status" theme="simple" list="#request.sq_project_infostatus" listKey="key" listValue="value" headerKey="-99" headerValue="——请选择——"></s:select>
        </td>
        <td height="32" style="font-weight: bold; text-align: right">项目名称关键字：
        </td>
        <td height="32" >
        	<input type="text" name="sqProjectInfo.projectName" value="" onblur="isCheckMaxLenth(this)" maxlength="20">
        </td>
      </tr>
    </table>
							</td>
						</tr>
					</table>
				</div>
				<input type="hidden" name="HQL" value="USER_LIST" />
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
							<table align="center" border="0" width="100%" class="form_table"
								id="comm">
      <tr id="tab">
        <td height="22" style="font-weight:bold"width="200px">项目名称</td>
        <td height="22" style="font-weight: bold" width="100px">项目组</td>
        <td height="22" style="font-weight:bold"width="100px">项目经理</td>
        <td height="22" style="font-weight:bold"width="100px">计划开始时间</td>
        <td height="22" style="font-weight:bold"width="100px">计划结束时间</td>
        <td height="22" style="font-weight:bold"width="400px">项目摘要</td>
        <td height="22" style="font-weight:bold"width="100px">项目状态</td>
        <td height="22" style="font-weight:bold"width="100px">详情</td>
      </tr>
    </table>
						</td>
					</tr>
				</table>
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					bgcolor="#e9f2f7">
					<tr height="35">
						<td align="right">
							<div id="pageTag"></div>
						</td>
					</tr>
				</table>
			</div>
		</div>
</body>
</html>