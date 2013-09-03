<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>员工密码修改</title>
		<link type="text/css" rel="stylesheet" href="../css/style-home.css" />
		<link type="text/css" rel="stylesheet" href="../css/style-sys.css" />
		<script type="text/javascript" src="../js/jquery.js"></script>
		<script type="text/javascript" src="../js/public.js"></script>
	</head>
	<script type="text/javascript">
<!--
function onSub(){
	if(!isNull($("input[type='password'][name='loginForm.password']") , '原密码')){
		return false;
	}
	if(!isNull($("input[type='password'][name='loginForm.pwd']") , '新密码')){
		return false;
	}
	if(!isNull($("input[type='password'][name='pwd']") , '第二次新密码')){
		return false;
	}
	//检查两次输入的密码是否一致
	if($("input[type='password'][name='loginForm.pwd']").attr("value") != $("input[type='password'][name='pwd']").attr("value")){
		alert("两次输入的新密码不一致，请重新输入");
		return false ;
	}
	$("#jform").submit();
}
	//-->
</script>
	<body>
		<div class="right">
			<div class="right_bt">
				<h1 class="right_bth1">
					用户管理
				</h1>
			</div>
			<div class="right_centerwk">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="form_btimg form_bt">
							<span>&nbsp;</span>
							<h1>
								员工密码修改
							</h1>
						</td>
					</tr>
					<tr>
						<td>
							<form action="updatepwd.shtml" method="post" name="jform"
								id="jform">
								<table border="0" width="100%" class="form_table_l">
									<tr>
										<td height="32" style="font-weight: bold; text-align: right">
											员工编号：
										</td>
										<td height="32" class="bgcolor1 fontleft">
											<s:property value="#session.sqUserTab.userId" />
											<input type="hidden"
												value="<s:property value="#session.sqUserTab.userId"/>"
												name="loginForm.userId">
										</td>
										<td height="32" style="font-weight: bold; text-align: right">
											员工姓名：
										</td>
										<td height="32" class="bgcolor1 fontleft">
											<s:property value="#session.sqUserTab.userName" />
										</td>
									</tr>
									<tr>
										<td height="32" style="font-weight: bold; text-align: right">
											请输入原密码：
										</td>
										<td height="32" class="bgcolor1 fontleft">
											<input type="password" value="" name="loginForm.password"
												onblur="isCheckMaxLenth(this)" maxlength="16">
										</td>
										<td height="32" style="font-weight: bold; text-align: right">
											请输入新密码：
										</td>
										<td height="32" class="bgcolor1 fontleft">
											<input type="password" value="" name="loginForm.pwd"
												onblur="isCheckMaxLenth(this)" maxlength="16">
										</td>
									</tr>
									<tr>
										<td height="32" style="font-weight: bold; text-align: right">
											请再次输入新密码：
										</td>
										<td height="32" class="bgcolor1 fontleft" colspan="3">
											<input type="password" value="" name="pwd"
												onblur="isCheckMaxLenth(this)" maxlength="16">
										</td>
									</tr>
									<tr>
										<td colspan="4" height="52" style="text-align: center">
											<div style="text-align: center;">
									<div style="width:200px;margin: 0 auto;">
									<a class="btn" onClick="onSub()" style="padding-right: 60px;">
									<cite>确&nbsp;&nbsp;&nbsp;定</cite></a>
								<a class="btn" onClick="document.jform.reset();">
									<cite>重&nbsp;&nbsp;&nbsp;置</cite></a>
									</div></div>
										</td>
									</tr>
								</table>
								<table width="100%" border="0" cellspacing="0" cellpadding="0"
									bgcolor="#e9f2f7">
									<tr height="35">
										<td>
											&nbsp;
										</td>
										<td></td>
									</tr>
								</table>
							</form>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</body>
</html>