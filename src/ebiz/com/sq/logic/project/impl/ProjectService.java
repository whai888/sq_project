package com.sq.logic.project.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.sq.exception.SystemException;
import com.sq.logic.dept.impl.DeptService;
import com.sq.logic.project.IProjectService;
import com.sq.logic.project.IProjectUserService;
import com.sq.logic.user.IUserService;
import com.sq.model.vo.SqDeptTab;
import com.sq.model.vo.SqDocmentManager;
import com.sq.model.vo.SqGroupTab;
import com.sq.model.vo.SqProjectInfo;
import com.sq.model.vo.SqProjectStep;
import com.sq.model.vo.SqProjectUser;
import com.sq.model.vo.SqProjectUserId;
import com.sq.model.vo.SqUserTab;
import com.sq.service.IBaseDAO;
import com.sq.tools.Constant;
import com.sq.tools.Public;
import com.sq.tools.SortList;

@Transactional(rollbackFor=SystemException.class) //指定回滚,遇到异常SystemException时回滚
public class ProjectService implements IProjectService {
	@Resource
	private IBaseDAO iBaseDao;
	@Resource
	private IUserService iUserService;
	@Resource
	private IProjectUserService iProjectUserService;
	private Logger log = Logger.getLogger(DeptService.class);
	
	/**
	 * 保存项目信息
	 */
	public void saveProject(SqProjectInfo sqProjectInfo, String mgnUser ,String memUser) throws SystemException {
		//获取项目编号
		sqProjectInfo.setProjectId(iBaseDao.sequenceToId(Constant.SEQUENCE_PROJECT_INFO));
		iBaseDao.save(sqProjectInfo);
		StringTokenizer token = null;
		token = new StringTokenizer(mgnUser , "|");
		//新增管理组
		while(token.hasMoreTokens()){
			String userId = token.nextToken();
			//查询新增项目的员工是否存在，如果不存在，则跳过
			try {
				SqUserTab sqUserTab = iUserService.findById(userId);
			} catch (SystemException e) {
				continue ;
			}
			//需要向项目成员关联表中新增员工数据
			iBaseDao.sqlUpdate("insert into sq_project_user values ('" +sqProjectInfo.getProjectId()+ "','"+userId+"','2','',str_to_date('"+Public.getTimeToFormat(sqProjectInfo.getStartDate(), "yyyy-MM-dd")+"','%Y-%m-%d'),str_to_date('"+Public.getTimeToFormat(sqProjectInfo.getStartDate(), "yyyy-MM-dd")+"','%Y-%m-%d'),'0');");
		}
		
		token = new StringTokenizer(memUser , "|");
		//新增成员组
		while(token.hasMoreTokens()){
			String userId = token.nextToken();
			//查询新增项目的员工是否存在，如果不存在，则跳过
			try {
				SqUserTab sqUserTab = iUserService.findById(userId);
			} catch (SystemException e) {
				continue ;
			}
			//需要向项目成员关联表中新增员工数据
			iBaseDao.sqlUpdate("insert into sq_project_user values ('" +sqProjectInfo.getProjectId()+ "','"+userId+"','3','',str_to_date('"+Public.getTimeToFormat(sqProjectInfo.getStartDate(), "yyyy-MM-dd")+"','%Y-%m-%d'),str_to_date('"+Public.getTimeToFormat(sqProjectInfo.getStartDate(), "yyyy-MM-dd")+"','%Y-%m-%d'),'0');");
		}
		//新增项目经理
		SqProjectUser sqProjectUser = new SqProjectUser();
		SqProjectUserId sqProjectUserId = new SqProjectUserId();
		sqProjectUserId.setProjectId(sqProjectInfo.getProjectId());
		sqProjectUserId.setUserId(sqProjectInfo.getSqUserTab().getUserId());
		//成员组
		sqProjectUserId.setUserType("1");
		//员工编号
		sqProjectUser.setId(sqProjectUserId);
		sqProjectUser.setStartDate(sqProjectInfo.getStartDate());
		//员工状态 0正常
		sqProjectUser.setStatus("0");
		iBaseDao.save(sqProjectUser);
	}

	/**
	 * 项目信息更新
	 */
	public void updateProject(SqProjectInfo sqProjectInfo, String mgnUser ,String memUser )
			throws SystemException {
		StringTokenizer token = null;
		String userId = "" ;
		//检查管理组和成员组的员工不能有相同的
		token = new StringTokenizer(mgnUser , "|");
		while(token.hasMoreTokens()){
			userId = token.nextToken();
			StringTokenizer temp = new StringTokenizer(memUser , "|");
			while(temp.hasMoreTokens()){
				String userIdTemp = temp.nextToken();
				if(userId.equals(userIdTemp)){
					throw new SystemException(ProjectService.class ,"管理组的员工不能与成员组的员工相同。");
				}
			}
		}
		//在新增管理组的时候先根据项目编号和员工编号查询，如果没有就insert，否则update
		List temp = iBaseDao.find("from SqProjectUser sqProjectUser where sqProjectUser.id.projectId = '" + sqProjectInfo.getProjectId() + "' and sqProjectUser.id.userType != '1'");
		for (int j = 0; j < temp.size(); j++) {
			SqProjectUser sqProjectUser = (SqProjectUser)temp.get(j);
			//检查项目组的所有员工，如果在管理组和成员组中则更新状态
			if(sqProjectUser.getId().getUserType().equals("2")){
				token = new StringTokenizer(mgnUser , "|");
			}else if(sqProjectUser.getId().getUserType().equals("3")){
				token = new StringTokenizer(memUser , "|");
			}
			boolean flag = false ;
			while(token.hasMoreTokens()){
				userId = token.nextToken();
				if(sqProjectUser.getId().getUserId().equals(userId)){
					flag = true ;	//表示改员工现在认为管理组
					break ;
				}
			}
			//查询新增项目的员工是否存在，如果不存在，则跳过
			try {
				if(userId.equals(""))continue ;
				SqUserTab sqUserTab = iUserService.findById(userId);
			} catch (SystemException e) {
				continue ;
			}
			//如果管理组中剔除了此员工，则更新状态为注销，否则更新为正常
			if(!flag){
				iBaseDao.sqlUpdate("update sq_project_user set status='1' where project_id='" +sqProjectInfo.getProjectId() + "' and user_id='" + sqProjectUser.getId().getUserId() + "' and user_type = '" +sqProjectUser.getId().getUserType()+"'");
			}else if(sqProjectUser.getStatus().equals("1")){
				iBaseDao.sqlUpdate("update sq_project_user set status='0' where project_id='" +sqProjectInfo.getProjectId() + "' and user_id='" + sqProjectUser.getId().getUserId() + "' and user_type = '" +sqProjectUser.getId().getUserType()+"'");
			}
		}
		//新增管理组
		token = new StringTokenizer(mgnUser , "|");
		while(token.hasMoreTokens()){
			userId = token.nextToken();
			if(userId.equals("undefined")){
				continue ;
			}
			boolean flag = false ;	//标识员工在项目中所属的状态，是否应该修改，还是新增
			SqProjectUser sqProjectUser = null ;
			for (int j = 0; j < temp.size(); j++) {
				sqProjectUser = (SqProjectUser)temp.get(j);
				if(sqProjectUser.getId().getUserId().equals(userId) && sqProjectUser.getId().getUserType().equals("2")){
					flag = true ;
					break ;
				}
			}
			//没有的时候新增
			if(!flag){
				//查询新增项目的员工是否存在，如果不存在，则跳过
				try {
					SqUserTab sqUserTab = iUserService.findById(userId);
				} catch (SystemException e) {
					continue ;
				}
				//为0 表示里面有数据，不许要管
				iBaseDao.sqlUpdate("insert into sq_project_user values ('" +sqProjectInfo.getProjectId()+ "','"+userId+"','2','',str_to_date('"+Public.getTimeToFormat(sqProjectInfo.getStartDate(), "yyyy-MM-dd")+"','%Y-%m-%d'),str_to_date('"+Public.getTimeToFormat(sqProjectInfo.getStartDate(), "yyyy-MM-dd")+"','%Y-%m-%d'),'0');");
			}
		}
		token = new StringTokenizer(memUser , "|");
		//新增成员组
		while(token.hasMoreTokens()){
			userId = token.nextToken();
			if(userId.equals("undefined")){
				continue ;
			}
			boolean flag = false ;	//标识员工在项目中所属的状态，是否应该修改，还是新增
			SqProjectUser sqProjectUser = null ;
			for (int j = 0; j < temp.size(); j++) {
				sqProjectUser = (SqProjectUser)temp.get(j);
				if(sqProjectUser.getId().getUserId().equals(userId) && sqProjectUser.getId().getUserType().equals("3")){
					flag = true ;
					break ;
				}
			}
			//没有的时候新增
			if(!flag){
				//查询新增项目的员工是否存在，如果不存在，则跳过
				try {
					SqUserTab sqUserTab = iUserService.findById(userId);
				} catch (SystemException e) {
					continue ;
				}
				//为0 表示里面有数据，不需要管
				iBaseDao.sqlUpdate("insert into sq_project_user values ('" +sqProjectInfo.getProjectId()+ "','"+userId+"','3','',str_to_date('"+Public.getTimeToFormat(sqProjectInfo.getStartDate(), "yyyy-MM-dd")+"','%Y-%m-%d'),str_to_date('"+Public.getTimeToFormat(sqProjectInfo.getStartDate(), "yyyy-MM-dd")+"','%Y-%m-%d'),'0');");
			}
		}
		SqProjectInfo sqProjectTemp = this.findById(sqProjectInfo, "1");
		//可修改的项目内容如下
		sqProjectTemp.setProjectName(sqProjectInfo.getProjectName());
		//如果项目经理有变动，则需更新sq_project_user
		if(!sqProjectInfo.getSqUserTab().equals(sqProjectTemp.getSqUserTab())){
			//更新原项目经理的状态为注销状态
			iBaseDao.sqlUpdate("update sq_project_user set status='1' where project_id='" +sqProjectInfo.getProjectId() + "' and user_id='" + sqProjectTemp.getSqUserTab().getUserId() + "' and user_type = '1'");
			//将现项目经理插入项目信息表，如果已经存在则更新，负责更新
			String sql ="select puser.* from sq_project_user as puser where puser.project_id='"+sqProjectInfo.getProjectId()+"' and puser.user_id='"+sqProjectInfo.getSqUserTab().getUserId()+"' and puser.user_type='1' ";
			List userList = iBaseDao.sqlQuery(sql) ;
			if(userList.size() > 0 ){
				iBaseDao.sqlUpdate("update sq_project_user set status='0' where project_id='" +sqProjectInfo.getProjectId() + "' and user_id='" + sqProjectInfo.getSqUserTab().getUserId() + "' and user_type = '1'");
			}else{
				iBaseDao.sqlUpdate("insert into sq_project_user values ('" +sqProjectInfo.getProjectId()+ "','"+sqProjectInfo.getSqUserTab().getUserId()+"','1','',str_to_date('"+Public.getTimeToFormat(sqProjectInfo.getStartDate(), "yyyy-MM-dd")+"','%Y-%m-%d'),str_to_date('"+Public.getTimeToFormat(sqProjectInfo.getStartDate(), "yyyy-MM-dd")+"','%Y-%m-%d'),'0');");
			}
		}
		sqProjectTemp.setSqUserTab(sqProjectInfo.getSqUserTab());
		sqProjectTemp.setSqGroupTab(sqProjectInfo.getSqGroupTab());
		sqProjectTemp.setStartDate(sqProjectInfo.getStartDate());
		sqProjectTemp.setAdvanceDate(sqProjectInfo.getAdvanceDate());
		sqProjectTemp.setStatus(sqProjectInfo.getStatus());
		sqProjectTemp.setModel(sqProjectInfo.getModel());
		sqProjectTemp.setResume(sqProjectInfo.getResume());
		iBaseDao.update(sqProjectTemp);
	}

	/**
	 * 根据用户编号查询自己所属的项目
	 * @param userId 用户编号
	 * @param flag 1:查找用户下当前正在开发的项目	2：查找用户下所有的项目
	 */
	public List findByAll(SqUserTab sqUserTab , String flag) throws SystemException {
		//不用判断项目的状态，因为如果项目结束须将该项目所有的员工改成非正常状态
		String hql = "";
		if(flag.equals("1")){
			//去掉结束和关闭的项目
			hql = "select distinct sqProjectInfo from SqProjectInfo sqProjectInfo , SqProjectUser sqProjectUser where sqProjectInfo.projectId=sqProjectUser.id.projectId and sqProjectUser.id.userId='"+sqUserTab.getUserId() + "' and sqProjectInfo.status not in ('2','3') and sqProjectUser.status='0'" ;
		}else if(flag.equals("All")){
			hql = "from SqProjectInfo sqProjectInfo" ;
		}else if(flag.equals("3")){
			//查询当前项目组下所有的项目
			hql = "select distinct sqProjectInfo from SqProjectInfo sqProjectInfo , SqProjectStep sqProjectStep where sqProjectStep.projectId = sqProjectInfo.projectId and sqProjectStep.stepStatus='1' and sqProjectInfo.status not in ('2','3') and sqProjectInfo.sqGroupTab.groupNo = '"+sqUserTab.getSqGroupTab().getGroupNo()+"'" ;
		}else if(flag.equals("2")){
			//查询项目日报的所有项目，只有管理员和项目经理才可以查询，关闭和结束的项目不能查询
			hql = "select distinct sqProjectInfo from SqProjectInfo sqProjectInfo , SqProjectUser sqProjectUser,SqProjectStep sqProjectStep where sqProjectInfo.projectId=sqProjectUser.id.projectId and sqProjectUser.id.projectId=sqProjectStep.projectId and sqProjectStep.stepStatus='1' and sqProjectUser.id.userType in ('1','2') and sqProjectInfo.status not in ('2','3') and sqProjectUser.id.userId='"+sqUserTab.getUserId()+"' and sqProjectUser.status='0'" ;
		}
		List listmyProject = iBaseDao.find(hql);
//		List temp = new ArrayList();
//		for (int i = 0; i < listmyProject.size(); i++) {
//			SqProjectInfo sqProjectInfo = (SqProjectInfo)listmyProject.get(i);
			//加载员工信息
//			sqProjectInfo.setSqUserTab(iUserService.findById(sqProjectInfo.getUserId()));
			
			//查询项目下对应的阶段
//			SqProjectStep sqProjectStep = new SqProjectStep();
//			sqProjectStep.setProjectId(sqProjectInfo.getProjectId());
//			sqProjectInfo.setSqProjectStepList(this.findProjectStepById(sqProjectStep));
			
			//设置项目当前的阶段
//			List tempStepList = sqProjectInfo.getSqProjectStepList();
//			for (int j = 0; j < tempStepList.size(); j++) {
//				SqProjectStep sqProjectStepTemp = (SqProjectStep)tempStepList.get(j);
//				if(sqProjectStepTemp.getStepStatus().equals("1")){
//					sqProjectInfo.setSqProjectStep(sqProjectStepTemp) ;
//				}
//			}
			//如果当前的阶段为空  则选择第一个作为项目的节点
//			SortList<SqProjectStep> sortList = new SortList<SqProjectStep>();  
//			sortList.Sort(tempStepList, "getStepId", "asc");  
//			if(sqProjectInfo.getSqProjectStep() == null && tempStepList.size()>= 1 ){
//				sqProjectInfo.setSqProjectStep((SqProjectStep)tempStepList.get(0));
//			}
			
//			temp.add(sqProjectInfo);
//		}
		return listmyProject;
	}

	/**
	 * 根据项目编号查找项目
	 */
	public SqProjectInfo findById(SqProjectInfo sqProjectInfo , String condition) throws SystemException {
		StringBuffer HQL = new StringBuffer() ;
		HQL.append("from SqProjectInfo sqProjectInfo where sqProjectInfo.projectId='"+sqProjectInfo.getProjectId()+"' ");
		//过滤掉关闭的项目1：过滤  0：不过滤
		if(condition.equals("1")){
			HQL.append("and sqProjectInfo.status not in ('2','3')");
		}
		List projectList = (List) iBaseDao.find(HQL.toString());
		if(projectList.size()>0){
			sqProjectInfo = (SqProjectInfo)projectList.get(0);
		}
		//查询项目所对应的员工
		SqProjectUser sqProjectUser = new SqProjectUser();
		SqProjectUserId sqProjectUserId = new SqProjectUserId();
		sqProjectUserId.setProjectId(sqProjectInfo.getProjectId());
		sqProjectUser.setId(sqProjectUserId);
		sqProjectUser.setStatus("0");	//只查询状态正常的
		//加载项目下所有的员工详细信息
		sqProjectInfo.setSqProjectUserList(iProjectUserService.findByProjectId(sqProjectUser)) ;
		//加载本项目的项目经理信息
//		sqProjectInfo.setSqUserTab(iUserService.findById(sqProjectInfo.getUserId()));
		//加载本项目的阶段信息
//		SqProjectStep sqProjectStep = new SqProjectStep();
//		sqProjectStep.setProjectId(sqProjectInfo.getProjectId());
//		sqProjectInfo.setSqProjectStepList(this.findProjectStepById(sqProjectStep));
		return sqProjectInfo;
	}

	/**
	 * 查询项目阶段的信息
	 * @param sqProjectStep
	 * @return
	 */
	public List projectStepFind(SqProjectStep sqProjectStep){
		return iBaseDao.findByObject(sqProjectStep, null) ;
	}
	/**
	 * 新增项目阶段信息
	 */
	public void projectAddStep(SqProjectStep sqProjectStep)
			throws SystemException {
		sqProjectStep.setStepId(iBaseDao.sequenceToId(Constant.SEQUENCE_PROJECT_STEP));
		
		//如果为0，表示添加的是1级节点
		if(!sqProjectStep.getStepOrder().equals("0")){
			//检查该阶段的上级阶段是否为日报阶段，如果为日报节点不允许新增下级节点
			String hql = "from SqProjectStep as sqProjectStep where sqProjectStep.projectId = '" + sqProjectStep.getProjectId() + "' and sqProjectStep.stepOrder = '" + sqProjectStep.getStepOrder() + "'" ;
			List stepOrderList = (List)iBaseDao.find(hql);
			if(stepOrderList.size() !=0 ){
				SqProjectStep temp = (SqProjectStep)stepOrderList.get(0) ;
				//该阶段的上级节点为日报节点，不能新增下级确定
				if(temp.getDayStatus().equals("0")){
					throw new SystemException(ProjectService.class ,"该阶段的上级节点为日报节点，不能新增下级确定。");
				}
				//新增的节点开始时间要小于等于结束时间
				if((Public.getTimeToFormat(sqProjectStep.getEndDate(), "yyyy-MM-dd").compareTo(Public.getTimeToFormat(sqProjectStep.getStartDate(), "yyyy-MM-dd")) < 0)){
					throw new SystemException(ProjectService.class ,"新增的节点开始时间要小于等于结束时间。");
				}
				//新增的下级节点必须在上级节点的时间区间内
				//如果参数字符串等于此字符串，则返回 0 值；如果按字典顺序此字符串小于字符串参数，则返回一个小于 0 的值；如果按字典顺序此字符串大于字符串参数，则返回一个大于 0 的值。
				if((Public.getTimeToFormat(temp.getStartDate(), "yyyy-MM-dd").compareTo(Public.getTimeToFormat(sqProjectStep.getStartDate(), "yyyy-MM-dd")) > 0 
						&&Public.getTimeToFormat(temp.getEndDate(), "yyyy-MM-dd").compareTo(Public.getTimeToFormat(sqProjectStep.getStartDate(), "yyyy-MM-dd") ) < 0 )
					&& (Public.getTimeToFormat(temp.getStartDate(), "yyyy-MM-dd").compareTo(Public.getTimeToFormat(sqProjectStep.getEndDate(), "yyyy-MM-dd")) < 0
						&&Public.getTimeToFormat(temp.getEndDate(), "yyyy-MM-dd").compareTo(Public.getTimeToFormat(sqProjectStep.getEndDate(), "yyyy-MM-dd")) < 0	) ){
					throw new SystemException(ProjectService.class ,"新增的下级节点必须在上级节点的时间区间内。");
				}
			}
			
			//根据项目ID和上级阶段ID查询该上级阶段下最大的阶段序号，并将阶段序号+1，如果不存在，则默认为1
			hql = "from SqProjectStep as sqProjectStep where sqProjectStep.projectId = '" + sqProjectStep.getProjectId() + "' and sqProjectStep.upStepId = '" + sqProjectStep.getStepOrder() + "' and sqProjectStep.upStepId <> sqProjectStep.stepOrder order by sqProjectStep.stepId desc " ;
			stepOrderList = (List)iBaseDao.find(hql);
			//将本阶段的序号转变为保存阶段的上级阶段
			sqProjectStep.setUpStepId(sqProjectStep.getStepOrder());
			if(stepOrderList == null || stepOrderList.size() == 0 ){
				sqProjectStep.setStepOrder(sqProjectStep.getUpStepId() + "."+1);
			}else{
				SqProjectStep stepTemp = (SqProjectStep)stepOrderList.get(0);
				sqProjectStep.setStepOrder(sqProjectStep.getUpStepId() + "."+ (Integer.parseInt(stepTemp.getStepOrder().substring(stepTemp.getStepOrder().length() -1 )) + 1 ));
			}
		}else{
			String hql = "from SqProjectStep as sqProjectStep where sqProjectStep.projectId = '" + sqProjectStep.getProjectId() + "' and sqProjectStep.upStepId = sqProjectStep.stepOrder order by sqProjectStep.upStepId desc" ;
			List upStepOrderList = (List)iBaseDao.find(hql);
			if(upStepOrderList == null || upStepOrderList.size() == 0 ){
				//如果该项目为新项目，则添加为1级节点
				sqProjectStep.setUpStepId("1");
				sqProjectStep.setStepOrder("1");
			}else{
				//否则上级节点与本级节点相同,由于是根据排序的，所以可以取到最大的节点
				SqProjectStep stepTemp = (SqProjectStep)upStepOrderList.get(0);
				sqProjectStep.setStepOrder((Integer.parseInt(stepTemp.getStepOrder()) + 1 ) + "");
				sqProjectStep.setUpStepId(sqProjectStep.getStepOrder());
			}
		}
		iBaseDao.save(sqProjectStep);
		
	}

	/**
	 * 项目新增员工信息
	 */
	public void projectAddUser(SqProjectUser sqProjectUser)
			throws SystemException {
		//检查是否已经存在该员工信息，否则报错
		SqProjectUser sqProjectUsertemp = new SqProjectUser();
		SqProjectUserId sqProjectUserId = new SqProjectUserId();
		sqProjectUserId.setUserId(sqProjectUser.getId().getUserId());
		sqProjectUserId.setProjectId(sqProjectUser.getId().getProjectId());
		sqProjectUsertemp.setId(sqProjectUserId);
		sqProjectUsertemp = (SqProjectUser)iBaseDao.findFirstVO(sqProjectUsertemp, null);
		if(sqProjectUsertemp != null ){
			throw new SystemException(ProjectService.class ,"项目中已经存在编号"+sqProjectUser.getId().getUserId()+"对应的员工。");
		}
		//检查员工表中是否存在改员工
		SqUserTab sqUserTab = new SqUserTab();
		sqUserTab.setUserId(sqProjectUser.getId().getUserId());
		sqUserTab = (SqUserTab)iBaseDao.findFirstVO(sqUserTab, null);
		if(sqUserTab == null ){
			throw new SystemException(ProjectService.class ,"此员工不存在，请检查输入的员工号："+sqProjectUser.getId().getUserId()+"。");
		}
		if(sqUserTab.getStatus().equals("1")){
			throw new SystemException(ProjectService.class ,"此员工已经离职，请检查输入的员工号："+sqProjectUser.getId().getUserId()+"。");
		}
		iBaseDao.save(sqProjectUser);
		
	}

	/*
	 * 修改项目阶段信息
	 * (non-Javadoc)
	 * @see com.sq.logic.project.IProjectService#projectUpdateStep(com.sq.model.vo.SqProjectStep)
	 */
	public void projectUpdateStep(SqProjectStep sqProjectStep)
			throws SystemException {
		SqProjectStep stepTemp = new SqProjectStep();
		stepTemp.setStepId(sqProjectStep.getStepId());
		stepTemp = (SqProjectStep)iBaseDao.findFirstVO(stepTemp , null);
		
		//修改的节点开始时间要小于结束时间
		if((Public.getTimeToFormat(sqProjectStep.getEndDate(), "yyyy-MM-dd").compareTo(Public.getTimeToFormat(sqProjectStep.getStartDate(), "yyyy-MM-dd")) < 0)){
			throw new SystemException(ProjectService.class ,"修改的节点开始时间要小于结束时间。");
		}
		//修改的下级节点必须在上级节点的时间区间内
		//如果参数字符串等于此字符串，则返回 0 值；如果按字典顺序此字符串小于字符串参数，则返回一个小于 0 的值；如果按字典顺序此字符串大于字符串参数，则返回一个大于 0 的值。
		if((Public.getTimeToFormat(stepTemp.getStartDate(), "yyyy-MM-dd").compareTo(Public.getTimeToFormat(sqProjectStep.getStartDate(), "yyyy-MM-dd")) > 0 
				&&Public.getTimeToFormat(stepTemp.getEndDate(), "yyyy-MM-dd").compareTo(Public.getTimeToFormat(sqProjectStep.getStartDate(), "yyyy-MM-dd") ) < 0 )
			&& (Public.getTimeToFormat(stepTemp.getStartDate(), "yyyy-MM-dd").compareTo(Public.getTimeToFormat(sqProjectStep.getEndDate(), "yyyy-MM-dd")) < 0
				&&Public.getTimeToFormat(stepTemp.getEndDate(), "yyyy-MM-dd").compareTo(Public.getTimeToFormat(sqProjectStep.getEndDate(), "yyyy-MM-dd")) < 0	) ){
			throw new SystemException(ProjectService.class ,"修改的下级节点必须在上级节点的时间区间内。");
		}
		stepTemp.setDayStatus(sqProjectStep.getDayStatus());
		stepTemp.setEndDate(sqProjectStep.getEndDate());
		stepTemp.setKeyStatus(sqProjectStep.getKeyStatus());
		stepTemp.setLagDay(sqProjectStep.getLagDay());
		stepTemp.setStepStatus(sqProjectStep.getStepStatus());
		stepTemp.setStartDate(sqProjectStep.getStartDate());
		stepTemp.setStepName(sqProjectStep.getStepName());
		stepTemp.setStepRemark(sqProjectStep.getStepRemark());
		stepTemp.setSuccPercent(sqProjectStep.getSuccPercent());
		stepTemp.setUpPercent(sqProjectStep.getUpPercent());
		
		iBaseDao.update(stepTemp);
		
	}
	
	/*
	 * 删除项目阶段信息
	 * (non-Javadoc)
	 * @see com.sq.logic.project.IProjectService#projectDeleteStep(com.sq.model.vo.SqProjectStep)
	 */
	public void projectDeleteStep(SqProjectStep sqProjectStep)
			throws SystemException {
		//检查该里程碑下是否存在日报信息，如果有日报信息，则提示不能删除
		List workDayList = iBaseDao.find("from SqWorkdayManager sqWorkdayManager where sqWorkdayManager.sqProjectStep.stepId = '"+sqProjectStep.getStepId()+"'");
		if(workDayList.size() > 0)
			throw new SystemException(ProjectService.class , "当前里程碑下存在员工日报信息，不能删除。" );
		
		sqProjectStep = (SqProjectStep)iBaseDao.find("From SqProjectStep where stepId = " + sqProjectStep.getStepId()).get(0);
		//判断是否还存在已该阶段为上级阶段的节点
//		SqProjectStep stepTemp = new SqProjectStep();
//		stepTemp.setProjectId(sqProjectStep.getProjectId());
//		stepTemp.setUpStepId(sqProjectStep.getStepOrder());
		String sql ="select step.* from sq_project_step as step where step.up_step_id='"+sqProjectStep.getStepOrder()+"' and step.project_id='"+sqProjectStep.getProjectId()+"' and step.up_step_id!=step.step_order ";
		List stepList = iBaseDao.sqlQuery(sql) ;
		if(stepList.size() > 0)
			throw new SystemException(ProjectService.class , "删除该阶段前，请先将该阶段下所有的阶段删除。" );
		iBaseDao.delete(sqProjectStep);
		
	}
	
	/*
	 * 修改项目员工信息
	 * (non-Javadoc)
	 * @see com.sq.logic.project.IProjectService#projectUpdateUser(com.sq.model.vo.SqProjectUser)
	 */
	public void projectUpdateUser(SqProjectUser sqProjectUser)
			throws SystemException {
		//检查是否已经存在该员工信息，否则报错
		SqProjectUser sqProjectUsertemp = new SqProjectUser();
		SqProjectUserId sqProjectUserId = new SqProjectUserId();
		sqProjectUserId.setUserId(sqProjectUser.getId().getUserId());
		sqProjectUserId.setProjectId(sqProjectUser.getId().getProjectId());
		sqProjectUsertemp.setId(sqProjectUserId);
		sqProjectUsertemp = (SqProjectUser)iBaseDao.findFirstVO(sqProjectUsertemp, null);
		if(sqProjectUsertemp == null ){
			throw new SystemException(ProjectService.class ,"项目中不存在编号"+sqProjectUser.getId().getUserId()+"对应的员工。");
		}
		
		//检查员工表中是否存在改员工
		SqUserTab sqUserTab = new SqUserTab();
		sqUserTab.setUserId(sqProjectUser.getId().getUserId());
		sqUserTab = (SqUserTab)iBaseDao.findFirstVO(sqUserTab, null);
		if(sqUserTab == null ){
			throw new SystemException(ProjectService.class ,"此员工不存在，请检查输入的员工号："+sqProjectUser.getId().getUserId()+"。");
		}
		if(sqUserTab.getStatus().equals("1")){
			throw new SystemException(ProjectService.class ,"此员工已经离职，请检查输入的员工号："+sqProjectUser.getId().getUserId()+"。");
		}
		
		sqProjectUsertemp.setStartDate(sqProjectUser.getStartDate());
		sqProjectUsertemp.setEndDate(sqProjectUser.getEndDate());
		sqProjectUsertemp.setRemark(sqProjectUser.getRemark());
		sqProjectUsertemp.setStatus(sqProjectUser.getStatus());
		
		iBaseDao.update(sqProjectUsertemp);
		
	}

	/**
	 * 根据项目编号查询项目阶段
	 */
	public List findProjectStepById(SqProjectStep sqProjectStep)
			throws SystemException {
		List projectStepList = new ArrayList();
		StringBuffer hql = new StringBuffer() ;
		hql.append("from SqProjectStep as sqProjectStep where 1=1 ");
		if(sqProjectStep.getProjectId()!=null)
			hql.append("and sqProjectStep.projectId = '" + sqProjectStep.getProjectId() + "' ");
		if(sqProjectStep.getStepId() !=null)
			hql.append("and sqProjectStep.stepId = " + sqProjectStep.getStepId() + " ");
		hql.append("order by sqProjectStep.stepOrder") ;
		projectStepList =  iBaseDao.find(hql.toString());
		//如果项目的路径上有滞后，则给予一个标识，红色是1（关键路劲）	黄色是2（非关键路径）	绿色为3，判断的标准是
		for (int i = 0; i < projectStepList.size(); i++) {
			SqProjectStep sqProjectStepTemp = (SqProjectStep)projectStepList.get(i);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(sqProjectStepTemp.getStartDate());
			calendar.set(Calendar.DAY_OF_YEAR , calendar.get(Calendar.DAY_OF_YEAR) + sqProjectStepTemp.getLagDay());
			//默认都设置为3
			sqProjectStepTemp.setStatus("3");
			if((Public.getTimeToFormat(sqProjectStepTemp.getEndDate(), "yyyy-MM-dd").compareTo(Public.getTimeToFormat(calendar.getTime(), "yyyy-MM-dd")) < 0)){
				if(sqProjectStepTemp.getKeyStatus().equals("0"))
					sqProjectStepTemp.setStatus("1");
				else
					sqProjectStepTemp.setStatus("2");
			}
			projectStepList.remove(i);
			projectStepList.add(i , sqProjectStepTemp ) ;
		}
		//按照getStepOrder来进行排序
		SortList<SqDeptTab> sortList = new SortList<SqDeptTab>();  
		sortList.Sort(projectStepList, "getStepOrder", "asc");  
		return projectStepList;
	}
	
	/**
	 * 根据项目编号和项目阶段查询该阶段下的文档
	 */
	public List projectDocFindInfo(SqDocmentManager sqDocmentManager)
			throws SystemException {
		List projectDocList = new ArrayList();
		List projectDocTemp = new ArrayList();
		String hql = "from SqDocmentManager as sqDocmentManager where sqDocmentManager.projectId = '" + sqDocmentManager.getProjectId() + "' and sqDocmentManager.stepId = '" + sqDocmentManager.getStepId() +"' order by sqDocmentManager.docId desc limit 0,20";
		projectDocList =  iBaseDao.find(hql);
		
		for (int i = 0; i < projectDocList.size(); i++) {
			SqDocmentManager sqDocTemp = (SqDocmentManager)projectDocList.get(i);
			//检查员工表中是否存在改员工
			SqUserTab sqUserTab = new SqUserTab();
			sqUserTab.setUserId(sqDocTemp.getUserId());
			sqUserTab = (SqUserTab)iBaseDao.findFirstVO(sqUserTab, null);
			sqDocTemp.setSqUserTab(sqUserTab);
			projectDocTemp.add(sqDocTemp);
		}
		return projectDocTemp;
	}
	
	/**
	 * 根据项目组编号查询项目组下所有的项目
	 * @param sqGroupTab
	 * @return
	 */
	public List findProjectByGroupNo(SqGroupTab sqGroupTab){
		List projectList = new ArrayList();
		String hql = "" ;
		//如果项目组编号为-99 则查询所有的项目
		if(sqGroupTab==null || sqGroupTab.getGroupNo().equals("-99")){
			hql = "from SqProjectInfo sqProjectInfo where sqProjectInfo.status in ('0','1','4') order by sqProjectInfo.startDate asc ";
		}else{
			hql = "from SqProjectInfo sqProjectInfo where sqProjectInfo.status in ('0','1','4') and sqProjectInfo.sqGroupTab.groupNo='"+sqGroupTab.getGroupNo()+"'  order by sqProjectInfo.startDate asc";
		}
		projectList = iBaseDao.find(hql);
		return projectList ;
	}

	/**
	 * 检查该项目是否为项目组下面的
	 * @param sqGroupTab
	 * @param sqProject
	 * @return
	 */
	public boolean isProjectToGroup(SqGroupTab sqGroupTab , SqProjectInfo sqProject){
		List projectList = findProjectByGroupNo(sqGroupTab);
		for (Object object : projectList) {
			SqProjectInfo sqProjectInfo = (SqProjectInfo)object;
			if(sqProjectInfo.getProjectId().equals(sqProject.getProjectId())){
				return true ;
			}
		}
		return false ;
	}
	
	public List findByAll() throws SystemException {
		// TODO Auto-generated method stub
		return null;
	}

}
