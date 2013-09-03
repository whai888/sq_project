<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.sq.sys.ApplicationDate"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="com.sq.model.vo.SqUserTab"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.sq.model.vo.SqWorkdayManager"%>
<%@page import="com.sq.model.vo.SqDeptTab"%>
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
	Map<SqDeptTab,List> weekDayMap = (Map<SqDeptTab,List>)request.getAttribute("weekDayMap");
	%>
	<body>
	<div class="right">
			<div class="right_bt">
				<h1 class="right_bth1">
					部门周报管理
				</h1>
			</div>
			<form action="workdaylist.shtml" method="post" name="jform" id="jform">
				<div class="right_centerwk">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="form_btimg form_bt">
								<span>&nbsp;</span>
								<h1>
									部门周报查询
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
        <td height="32" style="font-weight: bold; text-align: right" width="150px;">部门名称：</td>
        <td height="32" width="100px;">
        	<s:select list="#request.deptList" theme="simple" name="sqDeptTab.deptNo" listKey="deptNo" listValue="deptName" headerKey="-99" headerValue="——全部——"></s:select>
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
				<input type="hidden" name="returnPage" value="deptweekdaylist">
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
      	<td height="22" style="font-weight:bold" width="10%">部门名称</td>
      	<td height="22" style="font-weight:bold" width="10%">部门负责人</td>
      	<td height="22" style="font-weight:bold" >周报周期</td>
      </tr>
      <%if(weekDayMap.size()>0){
      	Iterator<SqDeptTab> keys = weekDayMap.keySet().iterator();
      	while(keys.hasNext()){
				SqDeptTab sqDeptTab = keys.next();//key
				List list = weekDayMap.get(sqDeptTab);
       	%>
	  <tr>
	  		<td height="22" id="noWorkContent">
	  			<%=sqDeptTab.getDeptName() %>
	    	</td>
	    	<td height="22" id="noWorkContent">
	  			<%=sqDeptTab.getSqUserTab().getUserName() %>
	    	</td>
	    	<td height="22" align="left">
	    		<%for(int i=0; i<list.size(); i++){
	    			String sqWorkdayManager = (String)list.get(i);
	    				out.println("<a href='javascript:void(0)' onclick='window.open(\"workdaymanager/workdaydeptlist.shtml?sqWorkdayManager.weekDate="+sqWorkdayManager+"&sqDeptTab.deptNo="+sqDeptTab.getDeptNo()+"&returnPage=deptworkview\")' class=\"form_font\" >"+sqWorkdayManager + "</a>&nbsp;&nbsp;&nbsp;");
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