<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.sq.sys.ApplicationDate"%>
<%@page import="java.util.Arrays"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s"  uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>成长心路</title>
<link type="text/css" rel="stylesheet" href="../css/style-home.css" />
	<link type="text/css" rel="stylesheet" href="../css/style-sys.css" />
	<script type="text/javascript" src="../js/public.js"></script>
	<script type="text/javascript" src="../js/jquery.js"></script>
	<script type="text/javascript" src="../homemanager/js/systemnotify.js"></script>
</head>
<%
	ApplicationDate.getRequestData("SYS_PARA_DATA" , Arrays.asList("sq_article_managerplate") , request , "sq_article_managerplate" );
%>
<body>
<div class="right">
			<div class="right_bt">
				<h1 class="right_bth1">
					成长心路
				</h1>
			</div>
				<div class="right_centerwk">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="from_btimg form_bt">
							<span>&nbsp;</span>
							<h1>
								成长心路列表
							</h1>
						</td>
					</tr>
					<tr>
						<td>
							<table align="center" border="0" width="100%" class="form_table"
								id="comm">
      <tr>
        <td height="22" style="font-weight:bold" width="10%">发布日期</td>
        <td height="22" style="font-weight:bold" width="70%">标题</td>
        <td height="22" style="font-weight:bold" width="10%">撰写人</td>
        <td height="22" style="font-weight:bold" width="10%">发布人</td>
      </tr>
      <s:iterator value="#request.sqArticleManagerList">
      <tr>
        <td height="22">
        	<s:date name="publishDate" format="yyyy-MM-dd"/>
        </td>
        <td height="22" style="text-align: left">
        	<a href="findartidforcontent.shtml?sqArticleManager.artId=<s:property value="artId"/>&returnPage=groupupmind" class="form_font" >
        		<s:property value="artTitle"/>
        	</a>
		</td>
		<td height="22">
        	<s:property value="delivefyUser.userName"/>
		</td>
        <td height="22">
        	<s:property value="publishUser.userName"/>
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