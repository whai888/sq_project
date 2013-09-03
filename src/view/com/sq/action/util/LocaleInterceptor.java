package com.sq.action.util;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.sq.exception.SystemException;
import com.sq.logic.system.impl.SystemService;
import com.sq.logic.tools.IUserLog;
import com.sq.model.vo.SqUserTab;

public class LocaleInterceptor extends AbstractInterceptor {
	
	@Resource
	private IUserLog iUserLog ;
	private Logger log = Logger.getLogger(LocaleInterceptor.class);
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession() ;
		SqUserTab sqUserTab = (SqUserTab)session.getAttribute("sqUserTab");
		if(sqUserTab == null ){
			log.info("您还没有登录，请登录。");
			return "toLogin";
		}
		return invocation.invoke();
	}

}
