package com.sq.reports.factory;

import java.util.List;


import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sq.reports.logic.IProjectGroupUserWorkService;
import com.sq.reports.logic.impl.ProjectGroupUserWorkService;
import com.sq.reports.servlet.InitServlet;
import com.sq.reports.vo.ProjectGroupUserWork;


/**
 *
 *@autor whai
 */
public class ProjectGroupFactory {
	
	 private static ProjectGroupFactory instance;
	 
	 private static ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(InitServlet.SERVLET_CONTEXT);
	 
	 public static ProjectGroupFactory getInstance() {

	  if (instance == null) {

	   instance = new ProjectGroupFactory();
	  }

	  return instance;
	 }

	 public static List<ProjectGroupUserWork> getPorjectGroupLst(String groupNo , String startDate , String endDate) {
		 IProjectGroupUserWorkService iProjectGroupUserWorkService = (IProjectGroupUserWorkService)context.getBean("iProjectGroupUserWorkService");
		 return iProjectGroupUserWorkService.getPorjectGroupList(groupNo , startDate , endDate);

	 }

}
