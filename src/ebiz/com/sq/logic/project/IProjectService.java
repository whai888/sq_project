package com.sq.logic.project;

import java.util.List;

import com.sq.exception.SystemException;
import com.sq.model.vo.SqDocmentManager;
import com.sq.model.vo.SqGroupTab;
import com.sq.model.vo.SqProjectInfo;
import com.sq.model.vo.SqProjectStep;
import com.sq.model.vo.SqProjectUser;
import com.sq.model.vo.SqUserTab;

public interface IProjectService {
	public void saveProject(SqProjectInfo sqProjectInfo, String mgnUser ,String memUser) throws SystemException;
	public void updateProject(SqProjectInfo sqProjectInfo, String mgnUser ,String memUser ) throws SystemException ;
	public List findByAll() throws SystemException ;
	public List findByAll(SqUserTab sqUserTab , String flag) throws SystemException ;
	public SqProjectInfo findById(SqProjectInfo sqProjectInfo, String condition) throws SystemException ;
	public List findProjectStepById(SqProjectStep sqProjectStep) throws SystemException ;
	public void projectAddUser(SqProjectUser sqProjectUser) throws SystemException ;
	public void projectUpdateUser(SqProjectUser sqProjectUser) throws SystemException ;
	public void projectAddStep(SqProjectStep sqProjectStep) throws SystemException ;
	public void projectUpdateStep(SqProjectStep sqProjectStep) throws SystemException ;
	public void projectDeleteStep(SqProjectStep sqProjectStep) throws SystemException ;
	public List projectDocFindInfo(SqDocmentManager sqDocmentManager) throws SystemException;
	public List projectStepFind(SqProjectStep sqProjectStep);
	public List findProjectByGroupNo(SqGroupTab sqGroupTab) ;
	public boolean isProjectToGroup(SqGroupTab sqGroupTab , SqProjectInfo sqProject);
}
