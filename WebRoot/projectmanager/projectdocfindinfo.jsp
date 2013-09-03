<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.sq.sys.ApplicationDate"%>
<%@page import="java.util.Arrays"%>
<%@page import="com.sq.model.vo.SqProjectInfo"%>
<%@page import="com.sq.model.vo.SqProjectStep"%>
<%@page import="com.sq.model.vo.SqDocmentManager"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>项目文档查询详情显示</title>
		<link type="text/css" rel="stylesheet" href="../css/style-home.css" />
		<link type="text/css" rel="stylesheet" href="../css/style-sys.css" />
		<script type="text/javascript" src="../js/public.js"></script>
		<script type="text/javascript" src="../js/jquery.js"></script>
		<script type="text/javascript">
<!--
function onSub(){
	if(!isNull($("input[type='file'][name='fileName']") , '上传的文件名')){
		return false;
	}
	$("#jform").attr("action" , "projectdocupload.shtml");
	$("#jform").submit();
}
function onUpdateChange(docId , status){
	$("input[type='hidden'][name='sqDocmentManager.docId']").val(docId);
	$("select[name='sqDocmentManager.status']").attr("value" , status);
}
function onUpdate(){
	if($("input[type='hidden'][name='sqDocmentManager.docId']").val() == ''){
		alert('请选择需要修改的文件状态');
		return false ;
	}
	$("#jform").attr("action" , "projectdocupdate.shtml");
	$("#jform").submit();
}
//-->
</script>
	</head>
	<%
		ApplicationDate.getRequestData("SYS_PARA_DATA", Arrays
				.asList("sq_docment_managerstatus"), request,
				"sq_docment_managerstatus");
		SqProjectInfo sqProjectInfo = (SqProjectInfo)request.getAttribute("sqProjectInfo");
		SqDocmentManager sqDocmentManager = (SqDocmentManager)request.getAttribute("sqDocmentManager");
		boolean flag = false ;	//是否为周例会
		for (SqProjectStep temp : sqProjectInfo.getSqProjectStepSet()) {
			if(temp.getStepName().equals("周会记录") && temp.getStepOrder().equals(sqDocmentManager.getStepId())){
				flag = true ;
				break;
			}
		}
	%>
	<body>
		<div class="right">
			<div class="right_bt">
				<h1 class="right_bth1">
					文档信息查询
				</h1>
			</div>
			<form action="projectdocupload.shtml" method="post" name="jform"
				id="jform" enctype="multipart/form-data">
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
										<td height="32" style="font-weight: bold; text-align: right"
											width="150px;">
											文件上传：
										</td>
										<td height="32" class="bgcolor1 fontleft fontbold"
											width="200px;" colspan="3">
											<input type="file" name="fileName">
										</td>
										<%if(flag){ %>
											<td height="32" style="font-weight: bold; text-align: right"
												width="100px;">
												周会记录周期：
											</td>
											<td height="32" class="bgcolor1 fontleft fontbold"
												width="50px;" colspan="3">
												<s:select list="#request.weekDayList"
													theme="simple" name="sqDocmentManager.remark1"></s:select>
											</td>
										<%}%>
										<td colspan="2" height="52" style="text-align: center" align="center">
								<div style="text-align: center;">
									<div style="width:200px;margin: 0 auto;">
									<a class="btn" onClick="onSub()" style="padding-right: 60px;">
									<cite>确&nbsp;&nbsp;&nbsp;定</cite></a>
								<!--<a class="btn" onClick="onUpdate();">
									<cite>修&nbsp;&nbsp;&nbsp;改</cite></a>
									--></div>
									</div>
							</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
				<input type="hidden" name="sqDocmentManager.projectId"
					value="<s:property value="sqDocmentManager.projectId"/>">
				<input type="hidden" name="sqDocmentManager.stepId"
					value="<s:property value="sqDocmentManager.stepId"/>">
				<input type="hidden" name="sqDocmentManager.docId"
					value="<s:property value="sqDocmentManager.docId"/>">
				<input type="hidden" name="returnPage" value="projectdocupload">
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
									<td height="22" style="font-weight:bold"
										width="10%">
										文档编号
									</td>
									<td height="22" style="font-weight:bold"
										width="40%">
										文档名称
									</td>
									<td height="22" style="font-weight:bold"
										width="10%">
										上传员工
									</td>
									<td height="22" style="font-weight:bold"
										width="10%">
										文件版本
									</td>
									<td height="22" style="font-weight:bold"
										width="10%">
										文件状态
									</td>
									<td height="22" style="font-weight:bold" width="6%">
										下载
									</td>
								</tr>
								<s:iterator value="#request.projectDocList" var="sqProjectDoc">
									<tr height="22" style="cursor: pointer;"
										onclick="onUpdateChange('<s:property value="docId"/>','<s:property value="status"/>')">
										<td class="bgcolor1">
											<s:property value="docId" />
										</td>
										<td class="bgcolor1">
											<s:property value="backfileName" />
										</td>
										<td class="bgcolor1">
											<s:property value="sqUserTab.userName" />
										</td>
										<td class="bgcolor1">
											<s:property value="fileVersion" />
										</td>
										<td class="bgcolor1">
											<s:property value="#request.sq_docment_managerstatus[status]" />
										</td>
										<td class="bgcolor1">
											<img src="../images/dload.gif" width="16" height="16" /><a
												href="projectmanager/filedown.shtml?filenameInCN=<s:property value="backfileName"/>&fileNameCN=<s:property value="fileName"/>&projectId=<s:property value="projectId"/>" class="form_font">下载</a>
										</td>
									</tr>
								</s:iterator>
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