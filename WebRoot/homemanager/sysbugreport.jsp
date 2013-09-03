<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.sq.sys.ApplicationDate"%>
<%@page import="java.util.Arrays"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s"  uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统BUG报告</title>
<link type="text/css" rel="stylesheet" href="../css/style-home.css" />
<link type="text/css" rel="stylesheet" href="../css/style-sys.css" />
<script type="text/javascript" src="../js/public.js"></script>
<script type="text/javascript" src="../js/jquery.js"></script>
<script type="text/javascript" src="js/sysbugreport.js"></script>
</head>
<body>
<div class="right">
			<div class="right_bt">
				<h1 class="right_bth1">
					系统BUG管理
				</h1>
			</div>
			<form action="sysbugsubmit.shtml" method="post" name="jform" id="jform">
				<div class="right_centerwk">
								<table border="0" width="100%" class="form_table_l">
      <tr>
        <td height="32" style="font-weight: bold; text-align: right" width="10%">标题：</td>
        <td height="32" width="70%">
        	<input type="text" size="60" name="sqArticleManager.artTitle" onblur="isCheckMaxLenth(this)" maxlength="60">
        </td>
        <td height="32" style="text-align: center" width="15%">
        	<div style="text-align: center;">
									<div style="width:200px;margin: 0 auto;">
				<a class="btn" onClick="onSub()" >
				<cite>提&nbsp;&nbsp;&nbsp;交</cite></a>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="sysbugquery.shtml" class="form_font">报告管理</a>
				</div>
				</div>
        </td>
      </tr>
      <tr>
       <td class="bgcolor1 fontcenter fontbold" colspan="3">
       		<textarea rows="50" cols="130%" name="sqArticleManager.artContent"></textarea>
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
			</form>
		</div>
</body>
</html>