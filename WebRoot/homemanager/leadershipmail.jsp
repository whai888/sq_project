<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.sq.sys.ApplicationDate"%>
<%@page import="java.util.Arrays"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s"  uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>领导邮箱</title>
<link href="../css/index.css" rel="stylesheet" type="text/css" />
</head>
<body>
<form action="" method="post" name="jform" id="jform">
	<table width="1024" border="0" cellpadding="0">
      <tr>
        <td height="32" class="bgcolor1 fontleft" width="15%">领导邮箱：</td>
        <td height="32" class="bgcolor1 fontleft fontbold" width="70%">
        	<input type="checkbox">王海
        </td>
        <td height="32" class="bgcolor1 fontcenter fontbold" width="15%">
        	&nbsp;
        </td>
      </tr>
      <tr>
        <td height="32" class="bgcolor1 fontleft" width="15%">标题：</td>
        <td height="32" class="bgcolor1 fontleft fontbold" width="70%">
        	<input type="text" size="60">
        </td>
        <td height="32" width="15%" style="text-align: center">
        	<div style="text-align: center;">
									<div style="width:70px;margin: 0 auto;">
					<a class="btn" onClick="onSub('1')" >
					<cite>提&nbsp;&nbsp;&nbsp;交</cite></a>
					</div>
					</div>
        </td>
      </tr>
      <tr>
       <td class="bgcolor1 fontcenter fontbold" colspan="3">
       		<textarea rows="50" cols="130%"></textarea>
       </td>
      </tr>
    </table>
    </form>
</body>
</html>