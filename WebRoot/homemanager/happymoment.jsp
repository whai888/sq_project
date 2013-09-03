<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.sq.sys.ApplicationDate"%>
<%@page import="java.util.Arrays"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s"  uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>开心一刻</title>
<link type="text/css" rel="stylesheet" href="../css/style-home.css" />
<link type="text/css" rel="stylesheet" href="../css/style-sys.css" />
</head>
<%
	ApplicationDate.getRequestData("SYS_PARA_DATA" , Arrays.asList("sq_article_managerplate") , request , "sq_article_managerplate" );
%>
<body>
<div class="right">
			<div class="right_bt">
				<h1 class="right_bth1">
					开心一刻
				</h1>
			</div>
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
                          <td colspan="4"><table width="100%" border="0" class="form_news_nr">
                            <tr bgcolor="#f4f8fc">
                              <td><s:property value="sqArticleManager.artContent"/></td>
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
		</div>
</body>
</html>