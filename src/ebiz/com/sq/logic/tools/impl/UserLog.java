package com.sq.logic.tools.impl;

import java.sql.Date;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sq.exception.SystemException;
import com.sq.logic.tools.IUserLog;
import com.sq.model.vo.SqUserTab;
import com.sq.model.vo.SqUserlogTab;
import com.sq.service.IBaseDAO;
import com.sq.service.imp.BaseDAO;
import com.sq.tools.Constant;
import com.sq.tools.Public;

@Transactional(propagation=Propagation.REQUIRED) //如果有事务,那么加入事务,没有的话新建一个(不写的情况下) 
public class UserLog implements IUserLog{
	@Resource
	private IBaseDAO iBaseDao ;
	private Logger log = Logger.getLogger(UserLog.class);
	private HttpServletRequest request ; 
	private String message ;
	private String exception ;
	
	public UserLog() {
		super();
	}

	public void save(){
		synchronized (this) {
			HttpSession session = request.getSession(false);
			SqUserTab sqUserTab = (SqUserTab)session.getAttribute("sqUserTab");
			//获得IP
			SqUserlogTab sqUserLogTab = new SqUserlogTab();
			//如果登录的时候用户名或者密码错误，此时sqUserTab为空，因为session中还没有存在用户数据
			if(sqUserTab == null ){
				sqUserLogTab.setUserNo(request.getParameter("loginForm.userName"));
			}else{
				sqUserLogTab.setUserNo(sqUserTab.getUserId());
			}
//			sqUserLogTab.setSeqNo(iBaseDao.sequenceToId(Constant.SEQUENCE_USERLOG));
			String seqNo = Public.getSystemTimeToFormat("MMddHHmmss") + Public.randomStr();
			sqUserLogTab.setSeqNo(seqNo);
			sqUserLogTab.setIp(Public.getIpAddr(request));
			sqUserLogTab.setOpDate(Date.valueOf((Public.getSystemTimeToFormat("yyyy-MM-dd"))));
			sqUserLogTab.setOpTime(Public.getSystemTimeToFormat("HH:mm:ss"));
			sqUserLogTab.setOpDesc(message);
			sqUserLogTab.setException(exception);
			sqUserLogTab.setStatus("0");
			iBaseDao.save(sqUserLogTab);
		}
	}
//
//	public void run() {
//		this.save();
//		
//	}
	public void logwrite(HttpServletRequest request, Exception e) {
		if(e instanceof SystemException){
			SystemException sysTempException = (SystemException)e ;
			this.getTraceInfo(sysTempException);
		}
		
		this.request = request ;
		this.message = e.getMessage() ;
		
//		iBaseDao = (IBaseDAO)WebApplicationContextUtils.getWebApplicationContext(ServletActionContext.getServletContext()).getBean("iBaseDao");
//		new Thread(this).run();
		this.save();
	}
	
	public void getTraceInfo(SystemException sysTempException) {
		  StringBuffer sb = new StringBuffer();
		  StackTraceElement[] stacks = sysTempException.getStackTrace();
		  for (int i = 0; i < stacks.length; i++) {
		   if (stacks[i].getClassName().contains(sysTempException.getClass1())) {
		    sb.append("class: ").append(stacks[i].getClassName())
		      .append("; method: ").append(stacks[i].getMethodName())
		      .append("; line: ").append(stacks[i].getLineNumber())
		      .append(";  Exception: ");
		    break;
		   }
		  }
		  this.exception = sb.toString();
		}
}
