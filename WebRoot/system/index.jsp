<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.sq.model.vo.SysMenu"%>
<%@page import="java.util.Set"%>
<%@page import="com.sq.model.vo.SysMenuTools"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>周日报系统</title>
		<link type="text/css" rel="stylesheet" href="../css/style-home.css"/>
		<link rel="stylesheet" type="text/css" href="../css/superfish.css" media="screen"/>
		<script language="javascript" type="text/javascript" src="../js/jquery.js"></script>
		<script type="text/javascript" src="../js/superfish/js/hoverIntent.js"></script>
		<script type="text/javascript" src="../js/superfish/js/superfish.js"></script>
		<script type="text/javascript" src="../js/superfish/js/jquery.bgiframe.min.js"></script> 
<script type="text/javascript"> 
    $(document).ready(function(){ 
        $("ul.sf-menu").superfish().find('ul').bgIframe({opacity:false}); 
    }); 
    function dyniframesize(down) { 
var pTar = null; 
if (document.getElementById){ 
pTar = document.getElementById(down); 
} 
else{ 
eval('pTar = ' + down + ';'); 
} 
if (pTar && !window.opera){ 
//begin resizing iframe 
pTar.style.display="block" 
if (pTar.contentDocument && pTar.contentDocument.body.offsetHeight){ 
//ns6 syntax 
pTar.height = pTar.contentDocument.body.offsetHeight; 
if(pTar.height <900)
	pTar.height = 900 ;
} 
else if (pTar.Document && pTar.Document.body.scrollHeight){ 
//ie5+ syntax 
pTar.height = pTar.Document.body.scrollHeight; 
if(pTar.height <900)
	pTar.height = 900 ;
} 
} 
}
</script>
</head>
<body >
<!--     top     -->
   <div id="top">
        <div id="top_logo"><img src="../images/toplogo.gif" height="72" /></div>
        <div id="top_user">
           <img src="../images/user.gif"/>
           <p>欢迎您：<s:property value="#session.sqUserTab.userName"/> &nbsp;&nbsp;&nbsp; 所属项目组：<s:property value="#session.sqUserTab.sqGroupTab.groupName"/>&nbsp;&nbsp;&nbsp; 部门：<s:property value="#session.sqUserTab.sqDeptTab.deptName"/></p> 
        </div>        
    </div>
    
<!--     menu和time 注销     -->
   <div class="toolbar"> 
      <ul id="sample-menu-2" class="sf-menu">
      	<%List menuList = (List)request.getAttribute("menuList");
			for(int i=0  ; i < menuList.size() ; i++){
				SysMenu sysMenu = (SysMenu)menuList.get(i);
				Set<SysMenuTools> menuSet = sysMenu.getMenuTools();%>
				<li class="current"><a href="<%=sysMenu.getShowUrl() %>" target="mainframe" <%if(menuSet.size()>0)out.println("class='sf-with-ul'"); %>><%=sysMenu.getMenuName() %><span class="sf-sub-indicator"> &#187;</span></a>
					<%if(menuSet.size()>0)out.println("<ul>");%>
					<%for(SysMenuTools menuTools : menuSet){
						Set<SysMenuTools> menuTemp = menuTools.getToolsSet();
						%>
						<li ><a href="<%if(menuTools.getJumpPath().equals("#"))out.println("javascript:void(0)"); else out.println(menuTools.getJumpPath()); %>" target="mainframe" ><%=menuTools.getRemark() %><%if(menuTemp.size()>0)out.println("<span class='sf-sub-indicator'> &#187;</span>"); %></a>
							<%if(menuTemp.size()>0){
									out.println("<ul>");
									for(SysMenuTools temp : menuTemp){
									out.println("<li ><a href="+temp.getJumpPath()+" target='mainframe' >" + temp.getRemark()+"</a></li>");
									}
								out.println("</ul>");
							}%>
						</li>
					<%} %>
					<%if(menuSet.size()>0)out.println("</ul>");%>
				</li>
				<%} %>
	</ul>
      <div class="toolbar01">            
            <div class="toolbar01_01">
            <span><s:property value="@com.sq.tools.Public@getSystemTimeToFormat('yyyy-MM-dd EEE')"/></span><img src="../images/loginout.gif" />
            	<span><a href="../system/loginout.shtml"">注销</a></span>
            </div>                                
        </div>
    </div>
  <!--     iframe            -->
  <div style="height: 1024px;width: 100%;">
  	<iframe name="mainframe" src="systemnotify.shtml" class="iframe01" frameborder="0" scrolling="yes" width="100%" height="100%" onload="dyniframesize('mainframe')"></iframe>
  </div>
</body>
</html>
