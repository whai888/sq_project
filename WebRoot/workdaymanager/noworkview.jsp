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
<%@page import="com.sq.model.vo.SqProjectInfo"%>
<%@page import="com.sq.model.vo.SqGroupTab"%>
<%@page import="com.sq.model.vo.SqDeptTab"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt"       uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>日报查询</title>
		<link type="text/css" rel="stylesheet" href="../css/style-home.css" />
		<link type="text/css" rel="stylesheet" href="../css/style-sys.css" />
		<link rel="stylesheet" type="text/css" href="../css/jquery.editable-select.css">
		<script type="text/javascript" src="../js/public.js"></script>
		<script type="text/javascript" src="../js/jquerybak.js"></script>
		<script type="text/javascript" src="js/myworkdaylist.js"></script>
		<script type="text/javascript" src="../js/jquery.editable-select.pack.js"></script>
		<jsp:include page="../js/index.jsp"></jsp:include>
<script type="text/javascript">
  $(function() {
    $('.editable-select').editableSelect(
      {
        //bg_iframe: true,
        onSelect: function(list_item) {
         // $('#results').html('List item text: '+ list_item.text() +'<br> \
         // Input value: '+ this.text.val());
        }
      }
    );
  });
</script>
	</head>
	<%
		 ApplicationDate.getRequestData("SYS_PARA_DATA" , Arrays.asList("sq_workday_managertype") , request , "sq_workday_managertype" );
		List workViewList = (List)request.getAttribute("workViewList");
		String weekType = request.getParameter("weekType");
	%>
	<body>
	<div class="right">
			<div class="right_bt">
				<h1 class="right_bth1">
					未提交周报查询
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
	        <s:select list="#request.tempList" theme="simple" name="startDate" cssClass="editable-select" cssStyle="width:180px;"></s:select>
        </td>
        <td height="32" style="font-weight: bold; text-align: right" width="150px;">结束日期：</td>
        <td height="32" width="100px;">
        		<s:select list="#request.tempList" theme="simple" name="endDate" cssClass="editable-select" cssStyle="width:180px;" ></s:select>
        </td>
        <td height="32" style="font-weight: bold; text-align: right" width="150px;">周报类型：</td>
        <td height="32" width="100px;">
        		<s:select list="#request.sq_workday_managertype" theme="simple" name="weekType" listKey="key" listValue="value"></s:select>
        </td>
        <td height="32" style="font-weight: bold; text-align: right" width="150px;">项目组：</td>
        <td height="32" width="100px;">
        		<s:select list="#request.groupList" theme="simple" name="sqGroupTab.groupNo" listKey="groupNo" listValue="groupName" headerKey="-99" headerValue="——全部——"></s:select>
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
    <input type="hidden" name="returnPage" value="noworkview">
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
      	<td height="22" style="font-weight:bold" width="10%">项目组</td>
      	<td height="22" style="font-weight:bold" width="10%">员工姓名</td>
      	<td height="22" style="font-weight:bold">周报类型</td>
      </tr>
      <%if(workViewList.size()>0){
       for(int i=0; i<workViewList.size() ; i++){
       		SqUserTab sqUserTab = (SqUserTab)workViewList.get(i) ;
       		%>
	  <tr>
	  		<td height="22" id="noWorkContent">
	  			<%=sqUserTab.getIp() %>
	    	</td>
	    	<td height="22">
	    		<%=sqUserTab.getUserName() %>
	    	</td>
	    	<td height="22">
	    		<%=sqUserTab.getRemark() %>
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
							<div id="pageTag"><%=request.getAttribute("pageTag") %></div></td>
					</tr>
				</table>
			</div>
   </div>
	</body>
</html>