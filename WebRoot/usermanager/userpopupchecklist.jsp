<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.sq.sys.ApplicationDate"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.sq.model.vo.SqDeptTab"%>
<%@page import="java.util.List"%>
<%@page import="com.sq.model.vo.SqUserTab"%>
<%@page import="java.util.Set"%>
<%@page import="com.sq.model.vo.SqDeptUsermanager"%>
<%@page import="java.util.HashSet"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>员工信息</title>
		<link type="text/css" rel="stylesheet" href="../css/style-home.css" />
		<link type="text/css" rel="stylesheet" href="../css/style-sys.css" />
		<link rel="stylesheet" type="text/css" href="../js/tree_themes/SimpleTree.css" />
		<script type="text/javascript" src="../js/jquery.js"></script>
		<script type="text/javascript" src="../js/Tree.js"></script>
		<script type="text/javascript">
<!--
function onSub(){
	var str="";
	for(i =0; i<document.all('userName').length; i++){
		if(document.all('userName')(i).checked){
		 	str+=document.all('userName')(i).value+"#";
		 }
	}
	window.returnValue=str ;
	window.close();
}
$(function(){
	$(".st_tree").SimpleTree({
		click:function(a){
			if(!$(a).attr("hasChild")){
				var valStr = $(a).attr("ref") ;
				$("#"+valStr+"").attr("show" , "true") ;
			}
		}
	});
});
//-->
</script>
</head>
	<body>
		<div class="st_tree">
			<ul>
			<%SqDeptTab sqDeptTemp = (SqDeptTab)request.getAttribute("sqDeptTab") ;
			Map deptMap = (Map)request.getAttribute("deptMap");
			Iterator itTemp = deptMap.entrySet().iterator();
			Set<SqDeptUsermanager> sqDeptUser = new HashSet<SqDeptUsermanager>();
			String deptUserId = "" ;
			while (itTemp.hasNext()) {
		          Map.Entry entry = (Map.Entry) itTemp.next();
		          SqDeptTab sqDeptTab = (SqDeptTab)entry.getKey();
		          if(sqDeptTemp!=null && sqDeptTemp.getDeptNo().equals(sqDeptTab.getDeptNo())){
					sqDeptUser = sqDeptTab.getUserSet();
					deptUserId = sqDeptTab.getSqUserTab().getUserId();
				 }
		     }
			Iterator it = deptMap.entrySet().iterator();
			while (it.hasNext()) {
		          Map.Entry entry = (Map.Entry) it.next();
		          SqDeptTab sqDeptTab = (SqDeptTab)entry.getKey();
		          List userList = (List)entry.getValue(); %>
					<li><a href="#" ref="<%=sqDeptTab.getDeptNo() %>"><%=sqDeptTab.getDeptName() %></a></li>
						<ul id="<%=sqDeptTab.getDeptNo() %>">
							<% 
								for(int i=0 ; i<userList.size() ; i++){
								SqUserTab sqUserTab = (SqUserTab)userList.get(i);
								boolean flag = false ;
									for(SqDeptUsermanager sqDeptUserMan : sqDeptUser){
										if(sqDeptUserMan.getId().getUserTab().getUserId().equals(sqUserTab.getUserId())
										&&!deptUserId.equals(sqDeptUserMan.getId().getUserTab().getUserId()))
										flag = true ;
								}%>
						            <li><input type="checkbox" name="userName" value="<%=sqUserTab.getUserId() %>|<%=sqUserTab.getUserName() %>" <%if(flag)out.println("checked"); %>><A href="javascript:void('0')"><%=sqUserTab.getUserName() %></A></li>
							<%} %>
						 </ul>
			<%} %>
			</ul>
		</div>
		<div style="text-align: center;">
			<div style="width:200px;margin: 0 auto;">
				<a class="btn" onClick="onSub()" style="padding-right: 60px;">
				<cite>确&nbsp;&nbsp;&nbsp;定</cite></a>
			<a class="btn" onClick="window.close();">
				<cite>关&nbsp;&nbsp;&nbsp;闭</cite></a>
			</div>
		</div>
	</body>
</html>