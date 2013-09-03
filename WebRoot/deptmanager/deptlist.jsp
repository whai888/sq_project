<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.sq.sys.ApplicationDate"%>
<%@page import="java.util.Arrays"%>
<%@page import="com.sq.model.vo.SqUserTab"%>
<%@page import="java.util.Map"%>
<%@page import="com.sq.model.vo.SqDeptTab"%>
<%@page import="com.sq.tools.Public"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="com.sq.model.vo.SqDeptUsermanager"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>部门信息</title>
		<link type="text/css" rel="stylesheet" href="../css/style-home.css" />
		<link type="text/css" rel="stylesheet" href="../css/style-sys.css" />
	</head>
	<%
		ApplicationDate.getRequestData("SYS_PARA_DATA", Arrays.asList("sq_dept_tabstatus"), request, "sq_dept_tabstatus");
		Map deptTabStatus = (Map)request.getAttribute("sq_dept_tabstatus");
		SqUserTab sqUserTab = (SqUserTab)session.getAttribute("sqUserTab");
		Map deptMap = (Map)request.getAttribute("deptMap");
		
	%>
	<body>
		<div class="right">
			<div class="right_bt">
				<h1 class="right_bth1">
					部门管理
				</h1>
			</div>
			<div class="right_centerwk">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="from_btimg form_bt">
							<span>&nbsp;</span>
							<h1>
								部门明细
							</h1>
						</td>
					</tr>
					<tr>
						<td>
							<table align="center" border="0" width="100%" class="form_table">
								<tr>
									<td height="22" style="font-weight: bold">
										成立时间
									</td>
									<td height="22" style="font-weight: bold">
										部门名称
									</td>
									<td height="22" style="font-weight: bold">
										负责人
									</td>
									<td height="22" style="font-weight: bold">
										部门领导
									</td>
									<td height="22" style="font-weight: bold">
										部门状态
									</td>
								</tr>
								<%
								Iterator it = deptMap.entrySet().iterator();
								while (it.hasNext()) {
							          Map.Entry entry = (Map.Entry) it.next();
							          SqDeptTab sqDeptTab = (SqDeptTab)entry.getKey();
							           %>
									<tr>
										<td height="22">
											<%=Public.getTimeToFormat(sqDeptTab.getSuccDate(),"yyyy-MM-dd") %>
										</td>
										<td height="22">
										<%if(sqUserTab.isSysMagnUser("001")){ %>
										<img src="../images/edt.gif" width="16" height="16" />
											<a class="form_font" href="deptfindvo.shtml?sqDeptTab.deptNo=<%=sqDeptTab.getDeptNo() %>">
												<%=sqDeptTab.getDeptName() %></a>
										<%}else{ %>
											<%=sqDeptTab.getDeptName() %>
										<%} %>
										</td>
										<td height="22">
											<%=sqDeptTab.getSqUserTab().getUserName() %>
										</td>
										<td height="22">
											<%Set<SqDeptUsermanager> deptSet = sqDeptTab.getUserSet();
											for (SqDeptUsermanager deptUserSet : deptSet) {
												if(!deptUserSet.getId().getUserTab().getUserId().equals(sqDeptTab.getDeptUser()))
												out.println(deptUserSet.getId().getUserTab().getUserName() + "  ");
											}%>
										</td>
										<td height="22">
											<%=deptTabStatus.get(sqDeptTab.getStatus()) %>
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