<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.sq.sys.ApplicationDate"%>
<%@page import="java.util.Arrays"%>
<%@page import="com.sq.model.vo.SqUserTab"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s"  uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我要投稿</title>
<link type="text/css" rel="stylesheet" href="../css/style-home.css" />
<link type="text/css" rel="stylesheet" href="../css/style-sys.css" />
<script type="text/javascript" src="../js/public.js"></script>
<script type="text/javascript" src="../js/jquery.js"></script>
<script type="text/javascript" src="js/iwantsubmis.js"></script>
</head>
<%
	ApplicationDate.getRequestData("SYS_PARA_DATA" , Arrays.asList("sq_article_managerplate") , request , "sq_article_managerplate" );
	SqUserTab sqUserTab = (SqUserTab)session.getAttribute("sqUserTab");
%>
<body>
<div class="right">
			<div class="right_bt">
				<h1 class="right_bth1">
					投稿管理
				</h1>
			</div>
			<form action="iwantsubmis.shtml" method="post" name="jform" id="jform">
				<div class="right_centerwk">
								<table border="0" width="100%" class="form_table_l">
      <tr>
        <td height="32" style="font-weight: bold; text-align: right" width="15%">投稿模版：</td>
        <td height="32" width="70%">
        	<s:select list="#request.sq_article_managerplate"  theme="simple" name="sqArticleManager.plate" listKey="key" listValue="value"></s:select>
        </td>
        <td height="32" width="15%">
        	<%if(sqUserTab.isSysMagnUser("001")){ %>
        		<a href="artauditquery.shtml" class="form_font">投稿管理</a>
        	<%}else{%>
        		&nbsp;
        	<%}%>
        </td>
      </tr>
      <tr>
        <td height="32" style="font-weight: bold; text-align: right" width="15%">标题：</td>
        <td height="32" width="70%">
        	<input type="text" size="60" name="sqArticleManager.artTitle" onblur="isCheckMaxLenth(this)" maxlength="60">
        </td>
        <td style="text-align: center"width="15%">
			<div style="text-align: center;">
									<div style="width:70px;margin: 0 auto;">
				<a class="btn" onClick="onSub()" >
				<cite>确&nbsp;&nbsp;&nbsp;定</cite></a>
				</div>
				</div>
		</td>
      </tr>
      <tr>
       <td colspan="3">
       		<textarea rows="50" cols="130" name="sqArticleManager.artContent"></textarea>
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
			</form>
		</div>
</body>
</html>