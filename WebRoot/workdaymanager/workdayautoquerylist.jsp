<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.sq.sys.ApplicationDate"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="com.sq.model.vo.SqWorkdayManager"%>
<%@page import="com.sq.tools.Public"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Set"%>
<%@page import="com.sq.model.vo.SqDocmentManager"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>首页周日报查询</title>
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
	%>
	<body>
	<div class="right">
			<div class="right_bt">
				<h1 class="right_bth1">
					日报首页平台管理
				</h1>
			</div>
    <div class="right_centerwk">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="from_btimg form_bt">
							<span>&nbsp;</span>
							<h1>
								当日日报提交结果
							</h1>
						</td>
					</tr>
					<tr>
						<td>
						<table align="center" border="0" width="100%" class="form_table" id="comm">
      <tr>
      	<td height="22" style="font-weight:bold" width="7%">日报日期</td>
      	<td height="22" style="font-weight:bold" width="7%">日报时间</td>
      	<td height="22" style="font-weight:bold" width="7%">姓名</td>
      	<td height="22" style="font-weight:bold" width="7%">项目组</td>
      	<td height="22" style="font-weight:bold" width="10%">项目名称</td>
        <td height="22" style="font-weight:bold" width="52%">日报内容</td>
        <td height="22" style="font-weight:bold" width="10%">日报文件</td>
      </tr>
      <%if(workViewList.size()>0){
       for(int i=0; i<workViewList.size() ; i++){
       	SqWorkdayManager sqWorkdayManager = (SqWorkdayManager)workViewList.get(i);%>
	  <tr>
	  		<td height="22">
	  			<%=Public.getTimeToFormat(sqWorkdayManager.getWorkDate() , "yyyy-MM-dd") %>
	    	</td>
	    	<td height="22">
	    		<%=Public.getTimeToFormat(sqWorkdayManager.getWorkTime() , "HH:mm:ss") %>
	    	</td>
	    	<td height="22">
	    		<%=sqWorkdayManager.getSqUserTab().getUserName() %>
	    	</td>
	    	<td height="22">
	    		<%if(sqWorkdayManager.getSqGroupTab()!=null)out.println(sqWorkdayManager.getSqGroupTab().getGroupName()); %>
	    	</td>
	    	<td height="22">
	  			<%=sqWorkdayManager.getSqProjectInfo().getProjectName() %>
	    	</td>
	    	<td height="22" style="text-align: left" id="workContent">
			    <%=sqWorkdayManager.getWorkContent().replaceAll("\\r\\n" , "<br/>") %><br/>
	    	</td>
	    	<td height="22" style="text-align: left" id="workSug">
	    		<%Set<SqDocmentManager> sqDocumentManageSet = sqWorkdayManager.getSqDocumentManageSet(); 
	    	for (SqDocmentManager sqDocmentManager : sqDocumentManageSet) {%>
	    		&nbsp;&nbsp;version:<%=sqDocmentManager.getFileVersion()%>&nbsp;&nbsp;<a href="projectmanager/filedown.shtml?filenameInCN=<%=sqDocmentManager.getBackfileName() %>&fileNameCN=<%=sqDocmentManager.getFileName()%>&projectId=<%=sqDocmentManager.getProjectId() %>" class="form_font"><%=sqDocmentManager.getBackfileName()%></a></br>
	    	<%} %>
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
   <input type="hidden" name="pageTag" value="<s:property value="pageTag"/>">
	</body>
</html>