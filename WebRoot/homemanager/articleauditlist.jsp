<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.sq.sys.ApplicationDate"%>
<%@page import="java.util.Arrays"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s"  uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统通知</title>
<link type="text/css" rel="stylesheet" href="../css/style-home.css" />
<link type="text/css" rel="stylesheet" href="../css/style-sys.css" />
<script type="text/javascript" src="../js/public.js"></script>
<script type="text/javascript" src="../js/jquery.js"></script>
<script type="text/javascript" src="js/articleauditlist.js"></script>
</head>
<%
	ApplicationDate.getRequestData("SYS_PARA_DATA" , Arrays.asList("sq_article_managerstatus") , request , "sq_article_managerstatus" );
%>
<body>
<div class="right">
			<div class="right_bt">
				<h1 class="right_bth1">
					系统通知查询
				</h1>
			</div>
			<form action="artauditquery.shtml" method="post" name="jform" id="jform">
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
        <td height="32" style="font-weight: bold; text-align: right">查询条件：</td>
        <td height="32" align="center" colspan="3">
        	<select name="FLAG">
        		<option value="1">提交日期</option>
        		<option value="3">撰写人</option>
        		<option value="2" selected="selected">标题</option>
        	</select>
        </td>
        <td height="32" class="bgcolor1 fontbold">
        	<input type="text" size="60" name="CONTENT" onblur="isCheckMaxLenth(this)" maxlength="30">
        </td>
		<td rowspan="3" style="text-align: center">
					<div style="text-align: center;">
									<div style="width:70px;margin: 0 auto;">
						<a class="btn" onClick="onSub('1')" >
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
				<input type="hidden" name="pageTag" value="<s:property value="pageTag"/>">
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
        <td height="22" style="font-weight:bold" width="10%">撰写日期</td>
        <td height="22" style="font-weight:bold" width="60%">标题</td>
        <td height="22" style="font-weight:bold" width="10%">投稿人</td>
        <td height="22" style="font-weight:bold" width="10%">状态</td>
      </tr>
      <s:iterator value="#request.sqArticleManagerList">
      <tr>
        <td height="22">
        	<s:date name="delivefyDate" format="yyyy-MM-dd"/>
        </td>
        <td height="22" style="text-align: left">
        	<a href="findartidforcontent.shtml?sqArticleManager.artId=<s:property value="artId"/>&returnPage=artauditinfo" class="form_font">
        		<s:property value="artTitle"/>
        	</a>
		</td>
		<td height="22">
        	<s:property value="delivefyUser.userName"/>
		</td>
		<td height="22">
        	<s:property value="#request.sq_article_managerstatus[status]"/>
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