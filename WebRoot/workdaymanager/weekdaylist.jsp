<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.sq.sys.ApplicationDate"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="com.sq.model.vo.SqUserTab"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.sq.model.vo.SqWorkdayManager"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt"       uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>周报查询</title>
		<link type="text/css" rel="stylesheet" href="../css/style-home.css" />
		<link type="text/css" rel="stylesheet" href="../css/style-sys.css" />
		<script type="text/javascript" src="../js/public.js"></script>
		<script type="text/javascript" src="../js/jquerybak.js"></script>
		<script type="text/javascript" src="js/workdayquerylist.js"></script>
	</head>
	<%
	Map<SqUserTab,List> weekDayMap = (Map<SqUserTab,List>)request.getAttribute("weekDayMap");
	SqUserTab sqUserTabTemp = (SqUserTab)session.getAttribute("sqUserTab");
	%>
	<body>
	<div class="right">
			<div class="right_bt">
				<h1 class="right_bth1">
					周报管理
				</h1>
			</div>
			<form action="workdaylist.shtml" method="post" name="jform" id="jform">
				<div class="right_centerwk">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="form_btimg form_bt">
								<span>&nbsp;</span>
								<h1>
									周报查询
								</h1>
							</td>
						</tr>
						<tr>
							<td>
								<table border="0" width="100%" class="form_table_l">
     <tr>
        <td height="32" style="font-weight: bold; text-align: right" width="150px;">周报日期：</td>
        <td height="32" width="100px;">
        	<s:select list="#request.userWorkList" theme="simple" name="sqWorkdayManager.weekDate" headerKey="-99" headerValue="——全部——"></s:select>
        </td>
        <%if(sqUserTabTemp.isSysMagnUser("006") || sqUserTabTemp.isSysMagnUser("007")){ %>
        <td height="32" style="font-weight: bold; text-align: right" width="150px;">项目组：</td>
        <td height="32" width="100px;">
        		<s:select list="#request.groupList" theme="simple" name="sqGroupTab.groupNo" listKey="groupNo" listValue="groupName" headerKey="-99" headerValue="——全部——" onchange="onQuery()"></s:select>
        </td>
        <%} %>
        <td height="32" style="font-weight: bold; text-align: right" width="150px;">员工姓名：</td>
        <td height="32" width="100px;">
        	<s:select list="#request.userList" theme="simple" name="sqUserTab.userId" listKey="userId" listValue="userName" headerKey="-99" headerValue="——全部——"></s:select>
        </td>
        <td height="32" style="text-align: center">
      		<div style="text-align: center;">
									<div style="width:70px;margin: 0 auto;">
				<a class="btn" onClick="onQuery()" >
				<cite>查&nbsp;&nbsp;&nbsp;询</cite></a>
				</div></div>
        </td>
      </tr>
</table>
							</td>
						</tr>
					</table>
				</div>
				<input type="hidden" name="returnPage" value="weekdaylist">
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
      	<td height="22" style="font-weight:bold" width="10%">姓名</td>
      	<td height="22" style="font-weight:bold" >周报周期</td>
      </tr>
      <%if(weekDayMap.size()>0){
      	Iterator<SqUserTab> keys = weekDayMap.keySet().iterator();
      	while(keys.hasNext()){
				SqUserTab sqUserTab = keys.next();//key
				List list = weekDayMap.get(sqUserTab);
       	%>
	  <tr>
	  		<td height="22" id="noWorkContent">
	  			<%=sqUserTab.getUserName() %>
	    	</td>
	    	<td height="22" align="left">
	    		<%for(int i=0; i<list.size(); i++){
	    			String sqWorkdayManager = (String)list.get(i);
	    				out.println("<a href='javascript:void(0)' onclick='window.open(\"workdaymanager/workdaylist.shtml?sqWorkdayManager.weekDate="+sqWorkdayManager+"&sqUserTab.userId="+sqUserTab.getUserId()+"&returnPage=myworkview\")' class=\"form_font\" >"+sqWorkdayManager + "</a>&nbsp;&nbsp;&nbsp;");
	    		} %>
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