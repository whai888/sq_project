<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.sq.sys.ApplicationDate"%>
<%@page import="java.util.Arrays"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>项目信息员工新增</title>
		<link type="text/css" rel="stylesheet" href="../css/style-home.css" />
		<link type="text/css" rel="stylesheet" href="../css/style-sys.css" />
		<script type="text/javascript" src="../js/public.js"></script>
		<script type="text/javascript" src="../js/jquery.js"></script>
		<jsp:include page="../js/index.jsp"></jsp:include>
	</head>
<%
	ApplicationDate.getRequestData("SYS_PARA_DATA" , Arrays.asList("sq_project_userstatus") , request , "sq_project_userstatus" );
%>
<script type="text/javascript">
<!--
function onAdd(){
	if(!isNull($("input[type='text'][name='sqProjectUser.id.userId']") , '员工编号')){
		return false;
	}
	if(!isNull($("input[type='text'][name='sqProjectUser.startDate']") , '开始时间')){
		return false;
	}
	$("#jform").attr("action" , "projectadduser.shtml");
	$("#jform").submit();
}
function onUpdate(){
	if(!isNull($("input[type='text'][name='sqProjectUser.id.userId']") , '员工编号')){
		return false;
	}
	if(!isNull($("input[type='text'][name='sqProjectUser.startDate']") , '开始时间')){
		return false;
	}
	$("#jform").attr("action" , "projectupdateuser.shtml");
	$("#jform").submit();
}
function userlist(currentPage){
	
	//请求地址
	var url = 'page.shtml' ;
	//传送的值
	var params = {projectId: "<s:property value="sqProjectInfo.projectId"/>",
				  HQL:       "PROJECT_USER_LIST"     ,
				  currentStr:currentPage}; 
	//使用$.post方式	
		$.post(url, //服务器要接受的url
			params, //传递的参数		
			function cbf(data) { //服务器返回后执行的函数 参数 data保存的就是服务器发送到客户端的数据
				var toolsVal = '';
				$("#comm tr:not(:first)").remove();	//保留第一行，删除其它所有行
					for(var i=data.listData.length-1 ; i>=0 ; i-- ){
						$("#tab").after("<tr style='cursor:pointer' onClick=\"updateUser(\'"+data.listData[i][0]+"\',\'"+data.listData[i][1]+"\',\'"+data.listData[i][7]+"\',\'"+data.listData[i][7]+"\',\'"+data.listData[i][8]+"\',\'"+data.listData[i][10]+"\')\"><td height='22' class='bgcolor1 fontcenter fontcolor3'>"+data.listData[i][0]+"</td><td height='22' class='bgcolor1 fontcenter fontcolor3'>"+data.listData[i][1]+"</td><td height='22' class='bgcolor1 fontcenter fontcolor3'>"+data.listData[i][2]+"</td><td height='22' class='bgcolor1 fontcenter fontcolor3'>"+data.listData[i][3]+"</td><td height='22' class='bgcolor1 fontcenter fontcolor3'>"+data.listData[i][4]+"</td><td height='22' class='bgcolor1 fontcenter fontcolor3'>"+data.listData[i][5]+"</td><td height='22' class='bgcolor1 fontcenter fontcolor3'>"+data.listData[i][6]+"</td><td height='22' class='bgcolor1 fontcenter fontcolor3'>"+data.listData[i][7]+"</td><td height='22' class='bgcolor1 fontcenter fontcolor3'>"+data.listData[i][8]+"</td><td height='22' class='bgcolor1 fontcenter fontcolor3'>"+data.listData[i][9]+"</td></tr>") ;
					}
					$("#pageTag").html(data.pageTag);
			}, 
			'json' //数据传递的类型  json
		);
}
function updateUser(userId , userName , startDate , endDate , remark , status){
	$("input[type='text'][name='sqProjectUser.id.userId']").val(userId);
	$("input[type='text'][name='sqProjectUser.startDate']").val(startDate);
	$("input[type='text'][name='sqProjectUser.endDate']").val(endDate);
	$("input[type='text'][name='sqProjectUser.remark']").val(remark);
	$("input[type='text'][name='sqProjectUser.status']").val(status);
}
function retPopupValue(){
	//先将值情况，然后再填充
	$("input[type='text'][name='sqProjectInfo.sqUserTab.userId']").val('');
	$("input[type='text'][name='sqProjectInfo.sqUserTab.userId']").val($("input[type='radio'][name='sqUserTab.userId']").val());
	disablePopup();
}
//-->
</script>
	<body onload="userlist(0)">
	<div class="right">
			<div class="right_bt">
				<h1 class="right_bth1">
					项目管理
				</h1>
			</div>
			<form action="projectadduser.shtml" method="post" name="jform" id="jform">
				<div class="right_centerwk">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="form_btimg form_bt">
								<span>&nbsp;</span>
								<h1>
									项目信息员工新增
								</h1>
							</td>
						</tr>
						<tr>
							<td>
								<table border="0" width="100%" class="form_table_l">
     <tr>
        <td height="32" style="font-weight: bold; text-align: right" width="150px;">项目名称：</td>
        <td height="32"  width="200px;">
        	<s:property value="sqProjectInfo.projectName"/>
        	<input type="hidden" name="sqProjectInfo.projectName" value="<s:property value="sqProjectInfo.projectName"/>">
        </td>
        <td height="32" style="font-weight: bold; text-align: right" width="150px;">员工编号：</td>
        <td height="32" width="200px;">
        	<input type="text" value="<s:property value="sqProjectUser.id.userId"/>" name="sqProjectUser.id.userId"  id="openpopup" readonly="readonly">
        </td>
        <td colspan="2" height="52" style="text-align: center" align="center">
								<div style="text-align: center;">
									<div style="width:200px;margin: 0 auto;">
									<a class="btn" onClick="onAdd()" style="padding-right: 60px;">
									<cite>新&nbsp;&nbsp;&nbsp;增</cite></a>
								<a class="btn" onClick="onUpdate();">
									<cite>修&nbsp;&nbsp;&nbsp;改</cite></a>
									</div>
									</div>
							</td>
      </tr>
       <tr>
        <td height="32" style="font-weight: bold; text-align: right" >开始时间：</td>
        <td height="32">
        	<input type="text" id="datepick1" maxlength="10" onfocus="$(this).calendar()" value="<s:date name="sqProjectUser.startDate" format="yyyy-MM-dd"/>" name="sqProjectUser.startDate"></td>
        <td height="32" style="font-weight: bold; text-align: right" >结束时间：</td>
        <td height="32">
        	<input type="text" id="datepick2" maxlength="10" onfocus="$(this).calendar()" value="<s:date name="sqProjectUser.endDate" format="yyyy-MM-dd"/>" name="sqProjectUser.endDate"></td>
      	<td height="32" style="font-weight: bold; text-align: right" >状态：</td>
        <td height="32">
        	<s:select name="sqProjectUser.status" theme="simple" list="#request.sq_project_userstatus"></s:select>
        </td>
       </tr>
      <tr>
        <td height="32" style="font-weight: bold; text-align: right" >员工成员描述：</td>
        <td height="32" colspan="5">
        	<textarea rows="3" cols="60" name="sqProjectUser.remark" onblur="isCheckMaxLenth(this)" maxlength="100"><s:property value="sqProjectUser.remark"/></textarea>
        </td>
      </tr>
      </table>
      </td>
					</tr>
				</table>
				</div>
			<input type="hidden" name="sqProjectInfo.projectId" value="<s:property value="sqProjectInfo.projectId"/>">
		    <input type="hidden" name="sqProjectUser.id.projectId" value="<s:property value="sqProjectInfo.projectId"/>">
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
      <tr id="tab">
        <td height="22" style="font-weight:bold" width="10%">员工编号</td>
        <td height="22" style="font-weight:bold" width="10%">员工姓名</td>
        <td height="22" style="font-weight:bold" width="10%">所属部门</td>
        <td height="22" style="font-weight:bold" width="10%">职位</td>
        <td height="22" style="font-weight:bold" width="10%">岗位</td>
        <td height="22" style="font-weight:bold" width="10%">级别</td>
        <td height="22" style="font-weight:bold" width="10%">开始时间</td>
        <td height="22" style="font-weight:bold" width="10%">结束时间</td>
        <td height="22" style="font-weight:bold" width="10%">成员描述</td>
        <td height="22" style="font-weight:bold" width="10%">状态</td>
      </tr>
    </table>
						</td>
					</tr>
				</table>
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					bgcolor="#e9f2f7">
					<tr height="35">
						<td align="right">
							&nbsp;
						</td>
					</tr>
				</table>
			</div>
		</div>
     <div id="backgroundPopup"/>
    <jsp:include page="../usermanager/userpopuplist.jsp"></jsp:include>
    </div>
	</body>
</html>