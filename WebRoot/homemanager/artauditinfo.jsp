<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.sq.sys.ApplicationDate"%>
<%@page import="java.util.Arrays"%>
<%@page import="com.sq.model.vo.SqArticleManager"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s"  uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文章内容</title>
<link type="text/css" rel="stylesheet" href="../css/style-home.css" />
<link type="text/css" rel="stylesheet" href="../css/style-sys.css" />
<script type="text/javascript" src="../js/public.js"></script>
<script type="text/javascript" src="../js/jquery.js"></script>
<script type="text/javascript" src="js/artauditinfo.js"></script>
</head>
<%
	ApplicationDate.getRequestData("SYS_PARA_DATA" , Arrays.asList("sq_article_managerplate") , request , "sq_article_managerplate" );
	SqArticleManager sqArticleManager = (SqArticleManager)request.getAttribute("sqArticleManager");
%>
<body>
<div class="right">
			<div class="right_bt">
				<h1 class="right_bth1">
					文章内容
				</h1>
			</div>
			<form action="artaudit.shtml" method="post" id="jform" name="jform">
				<div class="right_centerwk">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td class="form_news"><h1><s:property value="sqArticleManager.artTitle"/></h1></td>
                </tr>
                <tr>
                  <td>
                     <table border="0" width="100%" class="form_table_l">
                        <tr bgcolor="#f4f8fc">
                          <td class="form_news_tit" style="	text-align:right; width: 8%">投稿人：</td>
                         <td style="width: 6%"><s:property value="sqArticleManager.delivefyUser.userName"/></td>
                          <td class="form_news_tit" style="	text-align:right;width: 76%">板块：</td>
                          <td style="width: 8%"><s:property value="#request.sq_article_managerplate[sqArticleManager.plate]"/></td>
                          
                        </tr>
      <tr>
       <td colspan="3">
       		<input type="radio" name="sqArticleManager.status" value="0">发表&nbsp;&nbsp;&nbsp;&nbsp;
       		<input type="radio" name="sqArticleManager.status" value="2">关闭&nbsp;&nbsp;&nbsp;&nbsp;
       		<input type="radio" name="sqArticleManager.status" value="1">拒绝&nbsp;&nbsp;&nbsp;&nbsp;
       		<input type="radio" name="sqArticleManager.status" value="9">删除&nbsp;&nbsp;&nbsp;&nbsp;
       </td>
        <td style="text-align: center" width="15%">
								<div style="text-align: center;">
									<div style="width:70px;margin: 0 auto;">
									<a class="btn" onClick="onSub()" >
									<cite>提&nbsp;&nbsp;&nbsp;交</cite></a>
									</div>
									</div>
							</td>
      </tr>
      <tr>
                          <td colspan="4"><table width="100%" border="0" class="form_news_nr">
                            <tr bgcolor="#f4f8fc">
                              <td><%=sqArticleManager.getArtContent().replaceAll("\\r\\n" , "<br/>") %></td>
                            </tr>
                          </table></td>
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
			 <input type="hidden" name="sqArticleManager.artId" value="<s:property value="sqArticleManager.artId"/>">
    <input type="hidden" name="stauts" value="<s:property value="sqArticleManager.status"/>">
    </form>
		</div>
</body>
</html>