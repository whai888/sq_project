<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>员工密码重置</title>
		<link type="text/css" rel="stylesheet" href="../css/style-home.css" />
		<link type="text/css" rel="stylesheet" href="../css/style-sys.css" />
		<script type="text/javascript" src="../js/jquery.js"></script>
		<script type="text/javascript" src="../js/public.js"></script>
	</head>
	<script type="text/javascript">
<!--
function goClick(){
	if(!isNull($("input[type='text'][name='sqUserTab.userId']") , '员工编号')){
		return false;
	}
	if(!isNull($("input[type='text'][name='sqUserTab.userName']") , '员工姓名')){
		return false;
	}
	if(!isNull($("input[type='password'][name='sqUserTab.passwd']") , '新密码')){
		return false;
	}
	if(!isNull($("input[type='password'][name='sqUserTab.remark']") , '确认新密码')){
		return false;
	}
	if($("input[type='password'][name='sqUserTab.passwd']").attr("value") != $("input[type='password'][name='sqUserTab.remark']").attr("value")){
		alert('两次输入密码不一致，请重新输入');
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
								员工密码重置
							</h1>
						</td>
					</tr>
					<tr>
						<td>
							<form action="updatepwdreset.shtml" method="post" name="jform"
								id="jform">
								<table border="0" width="100%" class="form_table_l">
									<tr>
										<td height="32" style="font-weight: bold; text-align: right">
											员工编号：
										</td>
										<td height="32" class="bgcolor1 fontleft fontbold">
											<input name="sqUserTab.userId" value="" type="text"
												onblur="isCheckMaxLenth(this)" maxlength="12">
										</td>
										<td height="32" style="font-weight: bold; text-align: right">
											员工姓名：
										</td>
										<td height="32" class="bgcolor1 fontleft fontbold">
											<input name="sqUserTab.userName" value="" type="text"
												onblur="isCheckMaxLenth(this)" maxlength="20">
										</td>
									</tr>
									<tr>
										<td height="32" style="font-weight: bold; text-align: right">
											新密码：
										</td>
										<td height="32" class="bgcolor1 fontleft fontbold">
											<input name="sqUserTab.passwd" value="" type="password" maxlength="20">
										</td>
										<td height="32" style="font-weight: bold; text-align: right">
											确认新密码：
										</td>
										<td height="32" class="bgcolor1 fontleft fontbold">
											<input name="sqUserTab.remark" value="" type="password" maxlength="20">
										</td>
									</tr>
									<tr>
										<td height="52"  style="text-align: center"
											colspan="4">
											<div style="text-align: center;">
									<div style="width:70px;margin: 0 auto;">
				<a class="btn" onClick="goClick()" >
				<cite>确&nbsp;&nbsp;&nbsp;定</cite></a>
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