<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.sq.sys.ApplicationDate"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.sq.tools.Public"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s"  uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SQL查询</title>
<link type="text/css" rel="stylesheet" href="../css/style-home.css" />
		<link type="text/css" rel="stylesheet" href="../css/style-sys.css" />
<script type="text/javascript" src="../js/public.js"></script>
<script type="text/javascript" src="../js/jquery.js"></script>
<script type="text/javascript" src="js/showsql.js"></script>
</head>
<body>
<%Map<String [], List<String[]>> strMap = (Map)request.getAttribute("strMap");
String sql = (String)request.getAttribute("sql");
if(Public.isEmpty(sql))
	sql = "" ;
%>
<div class="right">
			<div class="right_bt">
				<h1 class="right_bth1">
					系统SQL维护
				</h1>
			</div>
			<form action="systemsql.shtml" method="post" name="jform" id="jform">
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
        <td height="32" style="font-weight: bold; text-align: right">查询语句：</td>
        <td>
        	<textarea rows="7" cols="100" name="SQL"><%=sql %></textarea>
        </td>
        <td rowspan="3"  style="text-align: center">
				<div style="text-align: center;">
									<div style="width:70px;margin: 0 auto;">
				<a class="btn" onClick="onSub()" >
				<cite>查&nbsp;&nbsp;&nbsp;询</cite></a>
				</div>
				</div>
				</td>
      </tr>
</table>
							</td>
						</tr>
					</table>
				</div>
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
      <tr>
      <% 
      	if(strMap!=null){
      	Iterator<String []> keys = strMap.keySet().iterator();
      	while(keys.hasNext()){
				String [] mapTemp = keys.next();//key
				for(String temp : mapTemp){
      %>
        <td height="22" style="font-weight:bold"><%=temp %></td>
        <%}} %>
      </tr>
      <% 
      keys = strMap.keySet().iterator();
      	while(keys.hasNext()){
				String [] mapTemp = keys.next();//key
				List<String[]> listValue = strMap.get(mapTemp);
				for(String[] valueStr : listValue){
				out.println("<tr>");
				for(String temp : valueStr){
      %>
        <td height="22"><%=temp %></td>
        <%}out.println("</tr>");}}} %>
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