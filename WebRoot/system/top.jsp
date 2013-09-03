<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>项目管理系统</title>
<link href="../css/style-home.css" rel="stylesheet" type="text/css" />
</head>

<body>
 <div id="top">
        <div id="top_logo"><img src="../images/toplogo.gif" height="72" /></div>
        <div id="top_user">
           <img src="../images/user.gif"/>
           <p>欢迎您：<s:property value="#session.sqUserTab.userName"/> &nbsp;&nbsp;&nbsp; 员工编号：<s:property value="#session.sqUserTab.userId"/>&nbsp;&nbsp;&nbsp; 职位：<s:property value="#session.sqUserTab.sqJobTab.jobName"/>&nbsp;&nbsp;&nbsp; 部门：<s:property value="#session.sqUserTab.sqDeptTab.deptName"/>&nbsp;&nbsp;&nbsp; IP：<s:property value="#session.sqUserTab.ip"/></p>
            
        </div>
        
    </div>
</body>
</html>
