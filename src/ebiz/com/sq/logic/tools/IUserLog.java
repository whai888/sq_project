package com.sq.logic.tools;

import javax.servlet.http.HttpServletRequest;

import com.sq.logic.tools.impl.UserLog;

public interface IUserLog  {
	
	public void logwrite(HttpServletRequest request, Exception e);
	
}
