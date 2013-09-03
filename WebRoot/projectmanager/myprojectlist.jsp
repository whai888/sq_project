<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.sq.sys.ApplicationDate"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="com.sq.model.vo.SqProjectInfo"%>
<%@page import="com.sq.tools.Public"%>
<%@page import="java.util.Map"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>我的项目列表</title>
		<link type="text/css" rel="stylesheet" href="../css/style-home.css" />
		<link type="text/css" rel="stylesheet" href="../css/style-sys.css" />
	</head>
	<%
		ApplicationDate.getRequestData("SYS_PARA_DATA", Arrays
				.asList("sq_project_infostatus"), request,
				"sq_project_infostatus");
				Map projectStatus = (Map)request.getAttribute("sq_project_infostatus") ;
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
								我的项目明细
							</h1>
						</td>
					</tr>
					<tr>
						<td>
							<table align="center" border="0" width="100%" class="form_table">
								<tr>
									<td height="22"  style="font-weight: bold"
										width="15%">
										项目名称
									</td>
									<td height="22"  style="font-weight: bold"
										width="10%">
										项目组
									</td>
									<td height="22" style="font-weight: bold"
										width="7%">
										项目经理
									</td>
									<td height="22" style="font-weight: bold"
										width="6%">
										计划开始时间
									</td>
									<td height="22" style="font-weight: bold"
										width="6%">
										计划结束时间
									</td>
									<td height="22" style="font-weight: bold"
										width="10%">
										当前里程碑
									</td>
									<td height="22" style="font-weight: bold" width="4%">
										项目状态
									</td>
									<td height="22" style="font-weight: bold"
										width="10%">
										里程碑维护
									</td>
								</tr>
								<% List myprojectlist = (List)request.getAttribute("myprojectlist") ;
								for(int i =0 ; i<myprojectlist.size() ; i++ ){ 
									SqProjectInfo sqProjectInfo = (SqProjectInfo)myprojectlist.get(i);%>
									<tr style="height: 30px; text-overflow: ellipsis;">
										<td height="22"
											style="height: 30px; text-overflow: ellipsis;">
											<img src="../images/edt.gif" width="16" height="16" /><a
												href="projectfindvo.shtml?sqProjectInfo.projectId=<%=sqProjectInfo.getProjectId() %>" class="form_font">
												<%=sqProjectInfo.getProjectName() %> </a>
										</td>
										<td height="22" style="height: 30px; text-overflow: ellipsis;">
											<%=sqProjectInfo.getSqGroupTab().getGroupName() %>
										</td>
										<td height="22" style="height: 30px; text-overflow: ellipsis;">
											<%=sqProjectInfo.getSqUserTab().getUserName() %>
										</td>
										<td height="22" style="height: 30px; text-overflow: ellipsis;">
											<%=Public.getTimeToFormat(sqProjectInfo.getStartDate() , "yyyy-MM-dd") %>
										</td>
										<td height="22" style="height: 30px; text-overflow: ellipsis;">
											<s:date name="advanceDate" format="yyyy-MM-dd" />
											<%=Public.getTimeToFormat(sqProjectInfo.getAdvanceDate() , "yyyy-MM-dd")  %>
										</td>
										<td height="22" style="height: 30px; text-overflow: ellipsis;">
												<%=sqProjectInfo.getCurrProjectStep().getStepName()  %>
										</td>
										<td height="22" style="height: 30px; text-overflow: ellipsis;">
											<%=projectStatus.get(sqProjectInfo.getStatus()) %>
										</td>
										<td height="22" style="height: 30px; text-overflow: ellipsis;">
											<img src="../images/edt.gif" width="16" height="16" />
											<a
												href="projectaddstepinfo.shtml?sqProjectInfo.projectId=<%=sqProjectInfo.getProjectId() %>" class="form_font">里程碑维护</a>&nbsp;&nbsp;
											<img src="../images/details.gif" width="16" height="16" />
											<a
												href="projectstepfindinfo.shtml?sqProjectInfo.projectId=<%=sqProjectInfo.getProjectId() %>" class="form_font">
												里程碑详情 </a>
										</td>
									</tr>
									<%} %>
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