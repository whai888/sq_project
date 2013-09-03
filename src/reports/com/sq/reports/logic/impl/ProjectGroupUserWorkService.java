package com.sq.reports.logic.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import com.sq.reports.logic.IProjectGroupUserWorkService;
import com.sq.reports.vo.ProjectGroupUserWork;
import com.sq.service.IBaseDAO;

/**
 *
 *@autor whai
 */
@Transactional
public class ProjectGroupUserWorkService implements IProjectGroupUserWorkService {
	private IBaseDAO iBaseDao;
	
	public List<ProjectGroupUserWork> getPorjectGroupList(String groupNo , String startDate , String endDate){
		String SQL = "select UNNAMED1,projectId,projectName,userId,userName from (select sum(task_time) as UNNAMED1 ,d.project_id as projectId , e.project_name as projectName,e.user_id as userId,e.user_name as userName" +
				" from sq_workday_manager d," +
				" (select distinct a.project_id as project_id, c.project_name as project_name,a.user_id as user_id ,b.user_name as user_name" +
				" from sq_project_user a ,sq_user_tab b ,sq_project_info c" +
				" where a.user_id=b.user_id and c.project_id=a.project_id and b.project_group ='"+groupNo+"' and b.status !='1' and a.status='0') e" +
				" where d.project_id = e.project_id and e.user_id = d.user_id " +
				" and d.work_date >=str_to_date('"+startDate+"','%Y-%m-%d') and d.work_date<=str_to_date('"+endDate+"','%Y-%m-%d')" +
				" group by d.project_id, e.project_name,e.user_id ,e.user_name) as projectGroup";
		List projectGroup = iBaseDao.findWithSQL(SQL , null );
		List<ProjectGroupUserWork> projectGroupUserWork = new ArrayList<ProjectGroupUserWork>();
		for (int i = 0; i < projectGroup.size(); i++) {
			Object[] object = (Object[]) projectGroup.get(i);
			ProjectGroupUserWork projectGrouptemp = new ProjectGroupUserWork();
			projectGrouptemp.setUNNAMED_1(String.valueOf(object[0]));
			projectGrouptemp.setProjectId(String.valueOf(object[1]));
			projectGrouptemp.setProjectName(String.valueOf(object[2]));
			projectGrouptemp.setUserId(String.valueOf(object[3]));
			projectGrouptemp.setUserName(String.valueOf(object[4]));
			projectGroupUserWork.add(projectGrouptemp);
		}
		return projectGroupUserWork;
		
	}

	public IBaseDAO getiBaseDao() {
		return iBaseDao;
	}

	public void setiBaseDao(IBaseDAO iBaseDao) {
		this.iBaseDao = iBaseDao;
	}

}
