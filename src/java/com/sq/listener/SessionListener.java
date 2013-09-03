package com.sq.listener;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.struts2.ServletActionContext;

import com.sq.model.vo.SqUserTab;

/**
 *
 *@autor whai
 */
public class SessionListener implements HttpSessionAttributeListener,
		HttpSessionListener {
	HttpServletRequest request = null;
	HttpServletResponse response = null;
	
	public void attributeAdded(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void attributeRemoved(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void attributeReplaced(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void sessionCreated(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
//		request = ServletActionContext.getRequest();
//		response = ServletActionContext.getResponse();
	}

	public void sessionDestroyed(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		HttpSession session = arg0.getSession(); 
	}

}
