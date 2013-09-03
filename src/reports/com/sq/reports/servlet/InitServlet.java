package com.sq.reports.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 *
 *@autor whai
 */
public class InitServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	 public static ServletContext SERVLET_CONTEXT;

	 public void init() throws ServletException {

	  SERVLET_CONTEXT = getServletContext();
	 }

}
