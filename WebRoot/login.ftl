<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<#-- 定义/WEB-INF/struts-tags.tld文件对应的标签库前缀为S-->
<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"]>
<#assign basePath=request.contextPath>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>周日报系统</title>
<link href="${basePath}/css/login.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"> 
    document.onkeydown=function(event){            
    	var e = event || window.event || arguments.callee.caller.arguments[0];            
    	if(e && e.keyCode==13){ 
    		document.jform.submit()
    	}
    }; 
</script>
</head>
<body>
    <div id="login">
	     <div id="top">
		      <div id="top_left"><img src="${basePath}/images/login_03.gif" /></div>
			  <div id="top_center"></div>
		 </div>
		 <form action="system/login.shtml" name="jform" method="post">
		 <div id="center">
		      <div id="center_left"></div>
			  <div id="center_middle">
			  
			       <div id="user">用 户
			       <@s.textfield name="loginForm.userName" value=""/>
			       </div>
				   <div id="password">密   码
				   <@s.password name="loginForm.password" value=""/>
				   </div>
				   <div id="btn"><a href="#" onClick="document.jform.submit()">登录</a><a href="#" onclick="document.jform.reset();">清空</a></div>
			  
			  </div>
			  <div id="center_right"></div>		 
		 </div>
		 </form>
		 <div id="down">
		      <div id="down_left">
			      <div id="inf">
                       <span class="inf_text">版本信息</span>
					   <span class="copyright">周日报系统 2013 v1.1</span>
			      </div>
			  </div>
			  <div id="down_center"></div>		 
		 </div>
	</div>
</body>
</html>
