package com.sq.logic.workday.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.sq.exception.SystemException;
import com.sq.logic.group.IGroupService;
import com.sq.logic.project.IProjectDocService;
import com.sq.logic.project.IProjectService;
import com.sq.logic.tools.ISysparaTab;
import com.sq.logic.tools.impl.SysparaTab;
import com.sq.logic.user.IUserService;
import com.sq.logic.workday.IWorkDayService;
import com.sq.model.vo.SqDeptTab;
import com.sq.model.vo.SqDocmentManager;
import com.sq.model.vo.SqGroupTab;
import com.sq.model.vo.SqProjectInfo;
import com.sq.model.vo.SqProjectStep;
import com.sq.model.vo.SqProjectUser;
import com.sq.model.vo.SqUserTab;
import com.sq.model.vo.SqWorkdayManager;
import com.sq.service.IBaseDAO;
import com.sq.tools.Constant;
import com.sq.tools.Public;

/**
 *
 *@autor whai
 */
@Transactional(rollbackFor=SystemException.class) //指定回滚,遇到异常SystemException时回滚
public class WorkDayService implements IWorkDayService{
	@Resource
	private IBaseDAO iBaseDao;
	@Resource
	private IProjectService iProjectService;
	@Resource
	private IUserService iUserService;
	@Resource
	private ISysparaTab iSysparaTab;
	@Resource
	private IGroupService iGroupService;
	@Resource
	private IProjectDocService iProjectDocService ;
	private Logger log = Logger.getLogger(WorkDayService.class);
	
	/**
	 * 1:根据员工编号查询员工下所有的日报内容
	 * 2:根据项目编号查询当天项目下未审核的日报内容
	 * @param sqWorkdayManager
	 * @return
	 * @throws SystemException 
	 */
	public List findByExample(SqWorkdayManager sqWorkdayManager , String flag ) throws SystemException{
		String HQL = "" ;
		if(flag.equals("1")){
			//根据项目编号查询日报的所有信息
			HQL = "from SqWorkdayManager sqWorkdayManager where sqWorkdayManager.sqProjectInfo.projectId='"+sqWorkdayManager.getSqProjectInfo().getProjectId()+"' and sqWorkdayManager.type='0' and sqWorkdayManager.workCycle='OKK' order by sqWorkdayManager.sqUserTab.userId";
		}else{
			HQL = "from SqWorkdayManager sqWorkdayManager where sqWorkdayManager.sqUserTab.userId='"+sqWorkdayManager.getSqUserTab().getUserId()+"' and sqWorkdayManager.type='"+sqWorkdayManager.getType()+"'";
		}
		List sqWorkdayManagerList = iBaseDao.find(HQL);
//		List workTempList = new ArrayList();
//		for (int i = 0; i < sqWorkdayManagerList.size(); i++) {
//			SqWorkdayManager temp = (SqWorkdayManager)sqWorkdayManagerList.get(i) ;
//			temp = workDayTrans(temp);
//			workTempList.add(temp);
//		}
		return sqWorkdayManagerList;
	}
	
	/**
	 * 根据主键查询项目信息
	 */
	public SqWorkdayManager findById(SqWorkdayManager sqWorkdayManager) throws SystemException{
		String HQL = "from SqWorkdayManager sqWorkdayManager where sqWorkdayManager.workdayId='"+sqWorkdayManager.getWorkdayId()+"'";
		List workDayList = iBaseDao.find(HQL);
		if(workDayList.size() > 0 ){
			sqWorkdayManager = (SqWorkdayManager)workDayList.get(0);
		}
		return sqWorkdayManager;
	}
	
	/**
	 * 查询项目下所有的项目周报
	 * @param sqGroupTab
	 * @return
	 */
	public List projectWorkDayList(SqUserTab sqUserTab , SqWorkdayManager sqWorkdayManager , String type){
		String HQL = "" ;
		if(type.equals("1")){
			//项目周报查询
			HQL = "select sqWorkdayManager from SqWorkdayManager as sqWorkdayManager where sqWorkdayManager.type='1' and sqWorkdayManager.sqUserTab.userId='"+sqUserTab.getUserId()+"' and sqWorkdayManager.sqProjectInfo.projectId='"+sqWorkdayManager.getSqProjectInfo().getProjectId()+"' and sqWorkdayManager.workCycle is null order by sqWorkdayManager.sqUserTab.userName ";
		}
		List workTempList = iBaseDao.find(HQL) ;
		return workTempList ;
	}
	
	/**
	 * 查询项目组下所有的项目周报
	 * @param sqGroupTab
	 * @return
	 */
	public List groupWorkDayList(SqUserTab sqUserTab , SqWorkdayManager sqWorkdayManager , String type){
		String HQL = "" ;
		if(type.equals("1")){
			//项目周报查询
			HQL = "select sqWorkdayManager from SqWorkdayManager as sqWorkdayManager where sqWorkdayManager.type='2' and sqWorkdayManager.sqGroupTab.groupNo='"+sqUserTab.getSqGroupTab().getGroupNo()+"' and sqWorkdayManager.sqProjectInfo.projectId='"+sqWorkdayManager.getSqProjectInfo().getProjectId()+"' and sqWorkdayManager.workCycle is null order by sqWorkdayManager.sqUserTab.userName ";
		}
		List workTempList = iBaseDao.find(HQL) ;
		return workTempList ;
	}
	
	/**
	 * 新增我的日报内容
	 * @param sqWorkdayManager
	 * @throws SystemException 
	 */
	public void myworkDayEditAdd(SqUserTab sqUserTabTemp , SqWorkdayManager sqWorkdayManager) throws SystemException{
		sqWorkdayManager.setWorkdayId(iBaseDao.sequenceToId(Constant.SEQUENCE_WORK_DAY)) ;
		
		//查询保存中的项目周报是否有存在相同的项目
//		String hql = "from SqWorkdayManager sqWorkdayManager where sqWorkdayManager.sqUserTab.userId='" +sqUserTabTemp.getUserId()+"' and sqWorkdayManager.sqProjectInfo.projectId = '" +sqWorkdayManagerId.getSqProjectInfo().getProjectId()+"' and sqWorkdayManager.sqProjectStep.stepId='"+sqWorkdayManager.getSqProjectStep().getStepId()+"' and sqWorkdayManager.type='0' and sqWorkdayManager.workDate='"+Public.getTimeToFormat(sqWorkdayManager.getWorkDate(), "yyyy-MM-dd")+"' and sqWorkdayManager.workCycle is null";
//		List listTemp = iBaseDao.find(hql);
//		if(listTemp.size()>0){
//			throw new SystemException(WorkDayService.class ,"对应的项目日报当天中存在未确认的项目，不能新增。");
//		}
		//目前周报不需要审核，所以设置为“1”
		//查询项目信息
		SqProjectInfo sqProjectInfo =iProjectService.findById(sqWorkdayManager.getSqProjectInfo(),"1");
		
		sqWorkdayManager.setWorkTime(Public.getStringToDate(null, "HHmmss"));
		//新增日报的时候，项目组以项目所属项目组来定义
		sqWorkdayManager.setSqGroupTab(sqProjectInfo.getSqGroupTab());
		sqWorkdayManager.setAuditStatus("1");
		iBaseDao.save(sqWorkdayManager) ;
	}
	
	/**
	 * 提交我的日报,如果type为3，表示为部门周报，则更新work_cycle
	 * @throws SystemException 
	 */
	public void myWorkSub(SqUserTab sqUserTab, String type) throws SystemException{
		String sql = "";
		String weekDate = iSysparaTab.findById("99999");
		//将本周所写的日报内容cycle全部更新为OKK
		if(type.equals("3")){
			//如果员工的部门周报类型标准为1（必须为部门负责人）.则需要将本周一提交的周报日期处理
			if(sqUserTab.getSqDeptTab().getWeekStatus().equals("1")){
				if(Public.dayForWeek(Calendar.DAY_OF_WEEK) == 2 && sqUserTab.getUserId().equals(sqUserTab.getSqDeptTab().getDeptUser())){
					weekDate = iSysparaTab.findById("99999");
				}else{
					throw new SystemException(WorkDayService.class ,"管理层周报必须在每周一提交，谢谢配合！");
				}
			}
			sql = "update sq_workday_manager set work_cycle='"+weekDate+"',week_date='"+weekDate+"',week_date1='"+Public.getSystemTimeToFormat("yyyy-MM-dd")+"' , week_time='"+Public.getSystemTimeToFormat("HHmmss")+"' where user_id='"+sqUserTab.getUserId()+"' and type='"+type+"' and work_cycle is null";
			
		}else if(type.equals("2")){
			//提交项目组周报时，提交项目组下面所有的周报信息
			sql = "update sq_workday_manager set work_cycle='OKK',week_date='"+weekDate+"',week_date1='"+Public.getSystemTimeToFormat( "yyyy-MM-dd")+"' , week_time='"+Public.getSystemTimeToFormat("HHmmss")+"' where group_no='"+sqUserTab.getSqGroupTab().getGroupNo()+"' and type='"+type+"' and work_cycle is null";
		}else{
			sql = "update sq_workday_manager set work_cycle='OKK',week_date='"+weekDate+"',week_date1='"+Public.getSystemTimeToFormat("yyyy-MM-dd")+"' , week_time='"+Public.getSystemTimeToFormat("HHmmss")+"' where user_id='"+sqUserTab.getUserId()+"' and type='"+type+"' and work_cycle is null";
		}
		iBaseDao.sqlUpdate(sql);
	}
	
	/**
	 * 提交部门周报
	 * @throws SystemException 
	 */
	public SqWorkdayManager myworkDeptEditAdd(SqUserTab sqUserTabTemp , SqWorkdayManager sqWorkdayManager) throws SystemException{
		sqWorkdayManager.setWorkdayId(iBaseDao.sequenceToId(Constant.SEQUENCE_WORK_DAY)) ;
		sqWorkdayManager.setWorkDate(Public.getStringToDate("null", "yyyy-MM-dd"));
		sqWorkdayManager.setAuditStatus("2");
		
		//查询保存中的项目组是否存在相同的项目组
		String hql = "from SqWorkdayManager sqWorkdayManager where sqWorkdayManager.sqUserTab.userId='" +sqUserTabTemp.getUserId()+"' and sqWorkdayManager.sqGroupTab.groupNo = '" +sqWorkdayManager.getSqGroupTab().getGroupNo()+"' and sqWorkdayManager.type='3' and sqWorkdayManager.workCycle is null";
		List listTemp = iBaseDao.find(hql);
		if(listTemp.size()>0){
			throw new SystemException(WorkDayService.class ,"部门周报中存在未确认的项目，不能新增。");
		}
		
		//修改项目周报提交人的周报周期,
		String sql = "update sq_workday_manager as sq_workday_manager set sq_workday_manager.work_cycle='" +sqWorkdayManager.getWorkCycle()+ "' where sq_workday_manager.group_no='" +sqWorkdayManager.getSqGroupTab().getGroupNo() +"' and sq_workday_manager.type='2' and sq_workday_manager.work_cycle = 'OKK'";
		iBaseDao.sqlUpdate(sql);
		if(sqWorkdayManager.getRemark1() == null || sqWorkdayManager.getRemark1().equals("")){
			//保存的时候，需要将项目组下的所有员工保存。加载项目组员工信息
			List userList = iGroupService.findUserByGroupNo(sqWorkdayManager.getSqGroupTab().getGroupNo());
			StringBuffer strBuff = new StringBuffer();
			for (Iterator iterator = userList.iterator(); iterator.hasNext();) {
				SqUserTab sqUserTab = (SqUserTab) iterator.next();
				strBuff.append(sqUserTab.getUserName() + "   ") ;
			}
			sqWorkdayManager.setRemark1(strBuff.toString());
		}
		sqWorkdayManager.setWorkTime(Public.getStringToDate(null, "HHmmss"));
		sqWorkdayManager.setWorkCycle(null);
		sqWorkdayManager.setWeekDate(null);
		iBaseDao.save(sqWorkdayManager) ;
		return sqWorkdayManager ;
	}
	/**
	 * 新增项目日报
	 * @param sqWorkdayManager
	 * @throws SystemException 
	 */
	public void myworkProjectEditAdd(SqUserTab sqUserTabTemp , SqWorkdayManager sqWorkdayManager) throws SystemException{
		sqWorkdayManager.setWorkdayId(iBaseDao.sequenceToId(Constant.SEQUENCE_WORK_DAY)) ;
		sqWorkdayManager.setWorkDate(Public.getStringToDate("null", "yyyy-MM-dd"));
		sqWorkdayManager.setAuditStatus("1");
		
		//查询保存中的项目周报是否有存在相同的项目
		String hql = "from SqWorkdayManager sqWorkdayManager where sqWorkdayManager.sqUserTab.userId='" +sqUserTabTemp.getUserId()+"' and sqWorkdayManager.sqProjectInfo.projectId = '" +sqWorkdayManager.getSqProjectInfo().getProjectId()+"' and sqWorkdayManager.type='1' and sqWorkdayManager.workCycle is null";
		List listTemp = iBaseDao.find(hql);
		if(listTemp.size()>0){
			throw new SystemException(WorkDayService.class ,"项目周报中存在未确认的项目，不能新增。");
		}
		
		//修改日报提交人的周报周期
		String sql = "update sq_workday_manager set work_cycle='" +sqWorkdayManager.getWorkCycle()+ "' where project_id='" +sqWorkdayManager.getSqProjectInfo().getProjectId() +"' and work_cycle ='OKK' and type='0'";
		iBaseDao.sqlUpdate(sql);
		
		//查询项目信息
		SqProjectInfo sqProjectInfo =iProjectService.findById(sqWorkdayManager.getSqProjectInfo(),"1");
		
		//保存的时候，需要将项目下的所有员工保存。
		List userList = sqProjectInfo.getSqProjectUserList();
		StringBuffer strBuff = new StringBuffer();
		for (Iterator iterator = userList.iterator(); iterator.hasNext();) {
			SqProjectUser sqProjectUser = (SqProjectUser) iterator.next();
			strBuff.append(sqProjectUser.getSqUserTab().getUserName() + "   ") ;
		}
		sqWorkdayManager.setRemark1(strBuff.toString());
		//修改项目阶段的任务时间
		SqProjectStep sqProjectStep = new SqProjectStep();
		sqProjectStep.setStepId(sqWorkdayManager.getSqProjectStep().getStepId());
		sqProjectStep = (SqProjectStep)iProjectService.projectStepFind(sqProjectStep).get(0);
		//任务完成百分比
		sqProjectStep.setSuccPercent(sqWorkdayManager.getComplePercen());
		//任务提前滞后天数
		if(!sqWorkdayManager.getTaskStatus().equals("9") )
		sqProjectStep.setLagDay(Integer.valueOf(sqWorkdayManager.getTaskStatus()));
		iBaseDao.update(sqProjectStep);
		
		if(!sqWorkdayManager.getTaskStatus().equals("9") )
			sqWorkdayManager.setTaskStatus("0");
		//新增项目日报的时候，项目组以项目所属项目组来定义
		sqWorkdayManager.setSqGroupTab(sqProjectInfo.getSqGroupTab());
		sqWorkdayManager.setWorkTime(Public.getStringToDate(null, "HHmmss"));
		sqWorkdayManager.setWorkCycle(null);
		sqWorkdayManager.setWeekDate(null);
		iBaseDao.save(sqWorkdayManager) ;
	}
	
	/**
	 * 新增项目组日报
	 * @param sqWorkdayManager
	 * @throws SystemException 
	 */
	public void myworkGroupEditAdd(SqUserTab sqUserTabTemp  , SqWorkdayManager sqWorkdayManager) throws SystemException{
		sqWorkdayManager.setWorkdayId(iBaseDao.sequenceToId(Constant.SEQUENCE_WORK_DAY)) ;
		sqWorkdayManager.setWorkDate(Public.getStringToDate("null", "yyyy-MM-dd"));
		sqWorkdayManager.setAuditStatus("2");
		
		//查询保存中的项目组周报是否有存在相同的项目
		String hql = "from SqWorkdayManager sqWorkdayManager where sqWorkdayManager.sqUserTab.userId='" +sqUserTabTemp.getUserId()+"' and sqWorkdayManager.sqProjectInfo.projectId = '" +sqWorkdayManager.getSqProjectInfo().getProjectId()+"' and sqWorkdayManager.sqProjectStep.stepId='"+sqWorkdayManager.getSqProjectStep().getStepId()+"' and sqWorkdayManager.type='2' and sqWorkdayManager.workCycle is null";
		List listTemp = iBaseDao.find(hql);
		if(listTemp.size()>0){
			throw new SystemException(WorkDayService.class ,"项目组周报中存在未确认的项目，不能新增。");
		}
		
		//修改项目周报提交人的周报周期
		String sql = "update sq_workday_manager set work_cycle='" +sqWorkdayManager.getWorkCycle()+ "' where project_id='" +sqWorkdayManager.getSqProjectInfo().getProjectId() +"' and type='1' and work_cycle = 'OKK'";
		iBaseDao.sqlUpdate(sql);
		
		if(sqWorkdayManager.getRemark1()==null || sqWorkdayManager.getRemark1().equals("")){
			//保存的时候，需要将项目组下的所有员工保存。加载项目组员工信息
			List userList = iGroupService.findUserByGroupNo(sqUserTabTemp.getSqGroupTab().getGroupNo());
			StringBuffer strBuff = new StringBuffer();
			for (Iterator iterator = userList.iterator(); iterator.hasNext();) {
				SqUserTab sqUserTab = (SqUserTab) iterator.next();
				strBuff.append(sqUserTab.getUserName() + "   ") ;
			}
			sqWorkdayManager.setRemark1(strBuff.toString());
		}
		sqWorkdayManager.setSqGroupTab(sqUserTabTemp.getSqGroupTab());
		sqWorkdayManager.setWorkTime(Public.getStringToDate(null, "HHmmss"));
		sqWorkdayManager.setWorkCycle(null);
		sqWorkdayManager.setWeekDate(null);
		iBaseDao.save(sqWorkdayManager) ;
	}
	
	/**
	 * 删除我的日报内容
	 * @param sqWorkdayManager
	 */
	public void myworkDayEditDelete(String workId , String projectId) throws SystemException{
		String sql ="delete from sq_workday_manager where workday_id = '"+workId+"'" ;
		iBaseDao.sqlUpdate(sql) ;
	}
	
	/**
	 * 更新我的项目内容
	 * @param sqWorkdayManager
	 * @throws SystemException
	 */
	public void myworkDayEditUpdate(SqWorkdayManager sqWorkdayManager , SqUserTab sqUserTabTemp , String flag ) throws SystemException{
		//查询原有记录，然后再更新新的内容
		
		String hql = "";
		if(flag.equals("2")){
			//员工周报
			hql = "from SqWorkdayManager sqWorkdayManager where sqWorkdayManager.workdayId='" +sqWorkdayManager.getWorkdayId()+ "' ";
		}else if(flag.equals("1")){
			//部门周报
			hql = "from SqWorkdayManager sqWorkdayManager where sqWorkdayManager.workdayId='" +sqWorkdayManager.getWorkdayId()+ "' and type ='3'";
		}
		
		SqWorkdayManager temp = (SqWorkdayManager)iBaseDao.find(hql).get(0);
		if(sqUserTabTemp != null){
			//如果修改不是提交人，则不让修改
			if(!temp.getSqUserTab().getUserId().equals(sqUserTabTemp.getUserId())){
				throw new SystemException(WorkDayService.class ,"日报的提交人与修改人不符，请确认后再修改。");
			}
		}
		SqGroupTab sqGroupTab = new SqGroupTab();
		//查询项目信息
		if(sqWorkdayManager.getSqProjectInfo() !=null ){
			SqProjectInfo sqProjectInfo =iProjectService.findById(sqWorkdayManager.getSqProjectInfo(),"1");
			sqGroupTab = sqProjectInfo.getSqGroupTab() ;
		}else if(!Public.isEmpty(sqWorkdayManager.getSqGroupTab().getGroupNo())){
			sqGroupTab = iGroupService.findById(sqWorkdayManager.getSqGroupTab().getGroupNo());
		}else{
			sqGroupTab = sqUserTabTemp.getSqGroupTab() ;
		}
		
		sqWorkdayManager.setSqGroupTab(sqGroupTab);
		//不要更新提交的日期
		sqWorkdayManager.setWorkDate(temp.getWorkDate());
		sqWorkdayManager.setRemark1(temp.getRemark1());
		sqWorkdayManager.setDocumentId(temp.getDocumentId());
		sqWorkdayManager.setWorkTime(Public.getStringToDate(null, "HHmmss"));
		sqWorkdayManager.setWorkCycle(null);
		sqWorkdayManager.setSqDocumentManageSet(temp.getSqDocumentManageSet());
		sqWorkdayManager.setAuditStatus(temp.getAuditStatus());
		//清除对象的缓存
		iBaseDao.evict(temp);
		iBaseDao.update(sqWorkdayManager);
		
		//如果日报类型为项目日报，则更新项目阶段的任务时间和滞后天数
		if(sqWorkdayManager.getType().equals("1")){
			//修改项目阶段的任务时间
			SqProjectStep sqProjectStep = new SqProjectStep();
			sqProjectStep.setStepId(sqWorkdayManager.getSqProjectStep().getStepId());
			sqProjectStep = (SqProjectStep)iProjectService.projectStepFind(sqProjectStep).get(0);
			//任务完成百分比
			sqProjectStep.setSuccPercent(sqWorkdayManager.getComplePercen());
			//任务提前滞后天数
			sqProjectStep.setLagDay(Integer.valueOf(sqWorkdayManager.getTaskStatus()));
			iBaseDao.update(sqProjectStep);
		}
	}
	
	/**
	 * 生成项目周报查询
	 * @param sqWorkdayManager
	 */
	public Map<SqProjectInfo, List<SqWorkdayManager>> auditWorkDay(SqUserTab sqUserTab) throws SystemException{
		Map<SqProjectInfo, List<SqWorkdayManager>> map = new HashMap<SqProjectInfo, List<SqWorkdayManager>>();
		//查询该用户下管理的所有项目
		List projectList = iProjectService.findByAll(sqUserTab, "2");
		for (int i = 0; i < projectList.size(); i++) {
			SqProjectInfo sqProjectInfo = (SqProjectInfo)projectList.get(i);
			//根据项目编号查询项目所对应的日报信息
			String hql = "from SqWorkdayManager sqWorkdayManager where sqWorkdayManager.sqProjectInfo.projectId='" +sqProjectInfo.getProjectId()+ "' and sqWorkdayManager.type='0' and sqWorkdayManager.workCycle='OKK' order by sqWorkdayManager.sqUserTab.userId";
			List sqWorkdayManagerList = iBaseDao.find(hql);
			//如果本周没有周报则不需要添加
			if(sqWorkdayManagerList.size() > 0 )
			map.put(sqProjectInfo, sqWorkdayManagerList) ;
			
		}
		return map;
	}
	
	/**
	 * 查询所有提交过项目的员工，并且状态为正常的
	 * @return
	 */
	public List findWorkDayByUserAll(){
		String HQL = "select sqUserTab from SqUserTab as sqUserTab , SqWorkdayManager as sqWorkdayManager " +
				"where sqUserTab.userId = sqWorkdayManager.userId and sqUserTab.status='0' and sqWorkdayManager.type='0'"  ;
		return iBaseDao.find(HQL) ;
	}
	
	/**
	 * 加载本人本周的项目内容,根据时间排序，只查询审核通过的
	 * @param sqUserTabTemp
	 * @param startDate
	 * @param endDate
	 * @param type 0个人日报 1项目日报 2项目组日报
	 * @return
	 * @throws SystemException 
	 */
	public List myWorkView(SqUserTab sqUserTabTemp , String startDate , String flag , String type) throws SystemException{
		String HQL = "";
		//如果type为9表示查询员工所属项目组的所有项目日报信息,主要用于生成项目组周报用
		if(flag !=null && flag.equals("9")){
			HQL = "from SqWorkdayManager sqWorkdayManager where sqWorkdayManager.type='"+type+"' and sqWorkdayManager.sqGroupTab.groupNo='"+sqUserTabTemp.getSqGroupTab().getGroupNo()+"' and sqWorkdayManager.workCycle ='OKK' order by sqWorkdayManager.sqProjectInfo.projectId  " ;
		}else if(flag !=null && flag.equals("8")){
			//根据周报周期查询进10次提交的项目周报
//			if(!type.equals("0")){
//				HQL  = "select distinct sqWorkdayManager.week_date from sq_workday_manager sqWorkdayManager where sqWorkdayManager.type = '"+type+"' and sqWorkdayManager.week_date is not null order by sqWorkdayManager.week_date asc limit 0,10 " ;
//			}else{
				HQL  = "select distinct sqWorkdayManager.week_date from sq_workday_manager sqWorkdayManager where sqWorkdayManager.type = '"+type+"' and sqWorkdayManager.week_date is not null order by sqWorkdayManager.workday_id desc limit 0,10 " ;
//			}
		}else if(flag !=null && flag.equals("7")){
			//周报预览的时候，需要按照项目编号进行排序
			HQL = "from SqWorkdayManager sqWorkdayManager where sqWorkdayManager.sqUserTab.userId='" +sqUserTabTemp.getUserId()+"' and type = '"+type+"' and sqWorkdayManager.workCycle is null order by sqWorkdayManager.sqProjectInfo.projectId " ;
		}else if(type.equals("2")){
			//项目组周报预览
			HQL = "from SqWorkdayManager sqWorkdayManager where sqWorkdayManager.sqGroupTab.groupNo='" +sqUserTabTemp.getSqGroupTab().getGroupNo()+"' and type = '"+type+"' and sqWorkdayManager.workCycle is null order by sqWorkdayManager.workDate desc " ;
		}else{
			//项目组周报预览
			HQL = "from SqWorkdayManager sqWorkdayManager where sqWorkdayManager.sqUserTab.userId='" +sqUserTabTemp.getUserId()+"' and type = '"+type+"' and sqWorkdayManager.workCycle is null order by sqWorkdayManager.workDate desc " ;
		}
		List sqWorkdayManagerList = new ArrayList();
		if(flag!=null && flag.equals("8")){
			sqWorkdayManagerList = iBaseDao.sqlQuery(HQL) ;
		}else{
			sqWorkdayManagerList = iBaseDao.find(HQL) ;
//			for (int i = 0; i < sqWorkdayManagerList.size(); i++) {
//				SqWorkdayManager temp = (SqWorkdayManager)sqWorkdayManagerList.get(i) ;
//				temp = workDayTrans(temp);
//				workTempList.add(temp);
//			}
		}
		return sqWorkdayManagerList;
	}
	
	/**
	 * 查询项目组下所有的项目周报
	 * @param sqGroupTab
	 * @return
	 */
	public List deptWorkDayList(SqGroupTab sqGroupTab , SqWorkdayManager sqWorkdayManager , String type){
		String HQL = "" ;
		if(type.equals("1")){
			//部门周报预览
			HQL = "select sqWorkdayManager from SqWorkdayManager as sqWorkdayManager where sqWorkdayManager.type='3' and sqWorkdayManager.sqGroupTab.groupNo='"+sqGroupTab.getGroupNo()+"' and sqWorkdayManager.workCycle is null order by sqWorkdayManager.sqGroupTab.groupNo ";
		}else if(type.equals("2")){
			//部门周报查询
			HQL = "select sqWorkdayManager from SqWorkdayManager as sqWorkdayManager where sqWorkdayManager.type='3' and sqWorkdayManager.sqGroupTab.groupNo='"+sqGroupTab.getGroupNo()+"' and sqWorkdayManager.weekDate ='"+sqWorkdayManager.getWeekDate()+"' order by sqWorkdayManager.sqProjectInfo.projectId ";
		}else if(type.equals("3")){
			//生成部门周报时的查询
			HQL = "select sqWorkdayManager from SqWorkdayManager as sqWorkdayManager where sqWorkdayManager.type='2' and sqWorkdayManager.sqGroupTab.groupNo='"+sqGroupTab.getGroupNo()+"' and sqWorkdayManager.workCycle ='OKK' order by sqWorkdayManager.sqProjectInfo.projectId ";
		}
		List workTempList = iBaseDao.find(HQL) ;
		return workTempList ;
	}
	
	/**
	 * 查询员工所操作的员工集合
	 * @param sqUserTabTemp
	 * @return
	 */
	public List findUserQuery(SqUserTab sqUserTabTemp, SqGroupTab sqGroupTab, SqProjectInfo sqProjectInfo ){
		List userList = new ArrayList();
		List userTempList = new ArrayList();
		String HQL = "" ;
		//检查员工是否为部门负责人。如果为部门负责人，查询部门下所有的员工
		HQL = "From SqDeptTab sqDeptTab where sqDeptTab.deptNo in (select sqDeptUsermanager.id.deptNo from SqDeptUsermanager sqDeptUsermanager where sqDeptUsermanager.id.userTab.userId='"+sqUserTabTemp.getUserId()+"') and sqDeptTab.status='0'";
		userTempList = iBaseDao.find(HQL) ;
		if(userTempList.size()>0){
			String tempHql  = "'" + 0 + "'" ;
			//有数据，则返回部门下所有的员工信息
			for (Iterator iterator = userTempList.iterator(); iterator
					.hasNext();) {
				SqDeptTab sqDeptTab = (SqDeptTab) iterator.next();
				tempHql = tempHql + ",'" + sqDeptTab.getDeptNo() + "'";
				
			}
			if(sqGroupTab == null || sqGroupTab.getGroupNo().equals("-99")){
				//如果项目编号为空，则查询部门下所有的员工
				if(sqProjectInfo == null || sqProjectInfo.getProjectId().equals("-99")){
					HQL = "From SqUserTab sqUserTab where sqUserTab.sqDeptTab.deptNo in ("+tempHql+") and sqUserTab.status!='1' order by sqUserTab.pyName asc";
				}else{
					HQL = "From SqUserTab sqUserTab where sqUserTab.sqDeptTab.deptNo in ("+tempHql+") and sqUserTab.userId in (select distinct sqProjectUser.id.userId from SqProjectUser sqProjectUser where sqProjectUser.id.projectId='"+sqProjectInfo.getProjectId()+"') and sqUserTab.status!='1' order by sqUserTab.pyName asc";
				}
			}else{
				//如果项目编号为空，则查询部门下所有的员工
				if(sqProjectInfo == null || sqProjectInfo.getProjectId().equals("-99") || !iProjectService.isProjectToGroup(sqGroupTab, sqProjectInfo)){
					HQL = "From SqUserTab sqUserTab where sqUserTab.sqDeptTab.deptNo in ("+tempHql+") and sqUserTab.status!='1' and sqUserTab.sqGroupTab.groupNo='"+sqGroupTab.getGroupNo()+"' order by sqUserTab.pyName asc";
				}else{
					HQL = "From SqUserTab sqUserTab where sqUserTab.sqDeptTab.deptNo in ("+tempHql+") and sqUserTab.status!='1' and sqUserTab.sqGroupTab.groupNo='"+sqGroupTab.getGroupNo()+"' and sqUserTab.userId in (select distinct sqProjectUser.id.userId from SqProjectUser sqProjectUser where sqProjectUser.id.projectId='"+sqProjectInfo.getProjectId()+"') order by sqUserTab.pyName asc";
				}
			}
				userList = iBaseDao.find(HQL) ;
			return userList ;
		}
		//检查员工是否为项目组负责人，如果为项目组负责人，查询项目组下所有的员工
		HQL = "From SqGroupTab sqGroupTab where sqGroupTab.sqUserTab.userId = '"+sqUserTabTemp.getUserId()+"' and sqGroupTab.status='0'";
		userTempList = iBaseDao.find(HQL) ;
		if(userTempList.size()>0){
			String tempHql  = "'" + 0 + "'" ;
			//有数据，则返回项目组下所有的员工信息
			for (Iterator iterator = userTempList.iterator(); iterator
					.hasNext();) {
				SqGroupTab sqGroupTemp = (SqGroupTab) iterator.next();
				tempHql = tempHql + ",'" + sqGroupTemp.getGroupNo() + "'";
				
			}
			if(sqGroupTab == null || sqGroupTab.getGroupNo().equals("-99")){
				if(sqProjectInfo == null || sqProjectInfo.getProjectId().equals("-99")){
					HQL = "From SqUserTab sqUserTab where sqUserTab.sqGroupTab.groupNo in ("+tempHql+") and sqUserTab.status!='1' order by sqUserTab.pyName asc";
				}else{
					HQL = "From SqUserTab sqUserTab where sqUserTab.sqGroupTab.groupNo in ("+tempHql+") and sqUserTab.userId in (select distinct sqProjectUser.id.userId from SqProjectUser sqProjectUser where sqProjectUser.id.projectId='"+sqProjectInfo.getProjectId()+"') and sqUserTab.status!='1' order by sqUserTab.pyName asc";
				}
			}else{
				//如果项目编号为空，则查询部门下所有的员工
				if(sqProjectInfo == null || sqProjectInfo.getProjectId().equals("-99")|| !iProjectService.isProjectToGroup(sqGroupTab, sqProjectInfo)){
					HQL = "From SqUserTab sqUserTab where sqUserTab.sqGroupTab.groupNo in ("+tempHql+") and sqUserTab.status!='1' and sqUserTab.sqGroupTab.groupNo='"+sqGroupTab.getGroupNo()+"' order by sqUserTab.pyName asc";
				}else{
					HQL = "From SqUserTab sqUserTab where sqUserTab.sqGroupTab.groupNo in ("+tempHql+") and sqUserTab.status!='1' and sqUserTab.sqGroupTab.groupNo='"+sqGroupTab.getGroupNo()+"' and sqUserTab.userId in (select distinct sqProjectUser.id.userId from SqProjectUser sqProjectUser where sqProjectUser.id.projectId='"+sqProjectInfo.getProjectId()+"') order by sqUserTab.pyName asc";
				}
			}
				userList = iBaseDao.find(HQL) ;
			return userList ;
		}
		//检查员工是否为项目经理或者管理组，如果为项目经理，查询项目经理下管理项目的所有员工信息
		HQL = "select distinct sqProjectInfo from SqProjectInfo sqProjectInfo where sqProjectInfo.status not in ('2','3') and sqProjectInfo.projectId in (select sqProjectUser.id.projectId from SqProjectUser sqProjectUser where sqProjectUser.id.userId='"+sqUserTabTemp.getUserId() + "' and (sqProjectUser.id.userType='1' or sqProjectUser.id.userType ='2') and sqProjectUser.status!='1') " ;
		userTempList = iBaseDao.find(HQL) ;
		if(userTempList.size()>0){
			String tempHql  = "'" + 0 + "'" ;
			//有数据，则返回项目组下所有的员工信息
			for (Iterator iterator = userTempList.iterator(); iterator
					.hasNext();) {
				SqProjectInfo sqProjectInfoTemp = (SqProjectInfo) iterator.next();
				tempHql = tempHql + ",'" + sqProjectInfoTemp.getProjectId() + "'";
				
			}
			if(sqGroupTab == null || sqGroupTab.getGroupNo().equals("-99"))
				HQL = "From SqUserTab sqUserTab where sqUserTab.userId in (select sqProjectUser.id.userId from SqProjectUser sqProjectUser where sqProjectUser.status!='1' and sqProjectUser.id.projectId in ("+tempHql+") ) and sqUserTab.status!='1' order by sqUserTab.pyName asc";
			else
				HQL = "From SqUserTab sqUserTab where sqUserTab.userId in (select sqProjectUser.id.userId from SqProjectUser sqProjectUser where sqProjectUser.status!='1' and sqProjectUser.id.projectId in ("+tempHql+") ) and sqUserTab.status!='1' and sqUserTab.sqGroupTab.groupNo='"+sqGroupTab.getGroupNo()+"' order by sqUserTab.pyName asc";
			userList = iBaseDao.find(HQL) ;
			return userList ;
		}
		//如果是普通员工，则只能查询本人的项目信息
		userList.add(sqUserTabTemp);
		return userList ;
	}
	
	/**
	 * 项目周报查询时  查询员工所对应的项目信息
	 * @param sqUserTabTemp
	 * @return
	 */
	public Map<SqGroupTab , List<SqProjectInfo>> findProjectQuery(SqUserTab sqUserTabTemp){
		Map<SqGroupTab , List<SqProjectInfo>> userList = new TreeMap<SqGroupTab , List<SqProjectInfo>>();
		List userTempList = new ArrayList();
		String HQL = "" ;
		//检查员工是否为部门负责人。如果为部门负责人，查询部门下所有的项目
		HQL = "select sqGroupTab From SqGroupTab sqGroupTab,SqDeptTab sqDeptTab where sqDeptTab.deptNo in (select sqDeptUsermanager.id.deptNo from SqDeptUsermanager sqDeptUsermanager where sqDeptUsermanager.id.userTab.userId='"+sqUserTabTemp.getUserId()+"') and sqGroupTab.sqDeptTab.deptNo=sqDeptTab.deptNo and sqGroupTab.status='0' order by sqGroupTab.groupNo asc ";
		userTempList = iBaseDao.find(HQL) ;
		if(userTempList.size()>0){
			//有数据，则返回部门下所有的项目
			for (Iterator iterator = userTempList.iterator(); iterator.hasNext();) {
				SqGroupTab sqGroupTab = (SqGroupTab) iterator.next();
				HQL = "From SqProjectInfo sqProjectInfo where sqProjectInfo.sqGroupTab.groupNo='"+sqGroupTab.getGroupNo()+"'  and sqProjectInfo.status not in ('2','3') order by sqProjectInfo.projectId desc ";
				userTempList = iBaseDao.find(HQL) ;
				userList.put(sqGroupTab, userTempList);
			}
			return userList ;
		}
		//检查员工是否为项目组负责人，如果为项目组负责人，查询项目组下所有的项目
		HQL = "From SqGroupTab sqGroupTab where sqGroupTab.sqUserTab.userId = '"+sqUserTabTemp.getUserId()+"' and sqGroupTab.status='0'";
		userTempList = iBaseDao.find(HQL) ;
		if(userTempList.size()>0){
			//有数据，则返回项目组下所有的员工信息
			for (Iterator iterator = userTempList.iterator(); iterator
					.hasNext();) {
				SqGroupTab sqGroupTab = (SqGroupTab) iterator.next();
				HQL = "From SqProjectInfo sqProjectInfo where sqProjectInfo.sqGroupTab.groupNo='"+sqGroupTab.getGroupNo()+"' and sqProjectInfo.status not in ('2','3') order by sqProjectInfo.startDate desc ";
				userTempList = iBaseDao.find(HQL) ;
				userList.put(sqGroupTab, userTempList);
			}
			return userList ;
		}
		//检查员工是否为项目经理或者管理组普通员工，则可以查询自己所参与的所有项目
		HQL = "From SqProjectInfo sqProjectInfo where sqProjectInfo.projectId in (select sqProjectUser.id.projectId from SqProjectUser sqProjectUser where sqProjectUser.id.userId = '"+sqUserTabTemp.getUserId()+"' and sqProjectUser.status='0' ) and sqProjectInfo.status not in ('2','3') order by sqProjectInfo.startDate desc";
		userTempList = iBaseDao.find(HQL) ;
		userList.put(sqUserTabTemp.getSqGroupTab(), userTempList);
		return userList ;
	}
	
	/**
	 * 项目组周报查询时  查询员工所对应的项目组信息
	 * @param sqUserTabTemp
	 * @return
	 */
	public List findGroupQuery(SqUserTab sqUserTabTemp){
		List userList = new ArrayList();
		List userTempList = new ArrayList();
		String HQL = "" ;
		//检查员工是否为部门负责人。如果为部门负责人，查询部门下所有的项目组
		HQL = "From SqDeptTab sqDeptTab where sqDeptTab.deptNo in (select sqDeptUsermanager.id.deptNo from SqDeptUsermanager sqDeptUsermanager where sqDeptUsermanager.id.userTab.userId='"+sqUserTabTemp.getUserId()+"')";
		userTempList = iBaseDao.find(HQL) ;
		if(userTempList.size()>0){
			String tempHql  = "'" + 0 + "'" ;
			//有数据，则返回部门下所有的项目组
			for (Iterator iterator = userTempList.iterator(); iterator
					.hasNext();) {
				SqDeptTab sqDeptTab = (SqDeptTab) iterator.next();
				tempHql = tempHql + ",'" + sqDeptTab.getDeptNo() + "'";
				
			}
			HQL = "From SqGroupTab sqGroupTab where sqGroupTab.sqDeptTab.deptNo in ("+tempHql+") order by sqGroupTab.groupNo desc ";
			userList = iBaseDao.find(HQL) ;
			return userList ;
		}
		//检查员工是否为拥有项目经理和项目组负责人的角色，拥有的时候，返回本部门下所有的项目组
		HQL = "From SqUserRole sqUserRole where sqUserRole.id.sqRole.roleId in ('004','005') and sqUserRole.id.userId='"+sqUserTabTemp.getUserId()+"'";
		userTempList = iBaseDao.find(HQL) ;
		if(userTempList.size()>0){
			HQL = "From SqGroupTab sqGroupTab where sqGroupTab.sqDeptTab.deptNo = '"+sqUserTabTemp.getSqDeptTab().getDeptNo()+"'";
			userTempList = iBaseDao.find(HQL) ;
			if(userTempList.size()>0){
				return userTempList ;
			}
		}
		
		//如果为普通员工，只能查询自己所属项目组信息
		userTempList.add(sqUserTabTemp.getSqGroupTab());
		return userTempList ;
	}
	
	/**
	 * 1：项目周报、2：项目组周报、3：部门周报提交时新增提醒的功能
	 * 1）查询所有待提交的项目周报
	 * 2）根据项目查询每个项目对应的所有开发人员
	 * 3）根据开发人员和项目编号查询开发人员提交的周报是否包含存在
	 * @param flag
	 * @param sqUserTab	登陆的用户信息
	 * @param weekDate	待提交的周报周期
	 * @return
	 */
	public String isUserSubWork(SqUserTab sqUserTab , String type , String weekDate){
		StringBuffer strBuff = new StringBuffer();
		boolean flag = false ;	//标示是否有未提交的周报人员
		if(type.equals("1")){
			//项目周报提醒功能
			//1）得到所有待提交的项目编号
			strBuff.append("以下项目的参与人员未提交本周周报，请确认是否提交：\r\n");
			String sql = "select distinct project_info.project_id,project_info.project_name from sq_workday_manager workday_manager,sq_project_info project_info  where workday_manager.user_id='"+sqUserTab.getUserId()+"' and workday_manager.type='"+type+"' and workday_manager.work_cycle is null and workday_manager.project_id=project_info.project_id and project_info.status not in ('2','3')";;
			//2）根据项目编号查询项目对应的所有开发人员,根据开发人员和项目编号查询开发人员提交的周报是否包含存在
			List projectList = iBaseDao.sqlQuery(sql);
			for (int i = 0; i < projectList.size(); i++) {
				Object[] temp = (Object[])projectList.get(i);
				sql = "select distinct user_tab.user_Name from sq_user_tab user_tab,sq_project_user project_user where project_user.user_id=user_tab.user_id and project_user.status='0' and project_user.project_id='"+(String)temp[0]+"' and project_user.user_id not in (select user_id from sq_workday_manager where project_id='"+temp[0]+"' and type='0' and work_cycle='"+weekDate+"' and week_date='"+weekDate+"')";
				List userList = iBaseDao.sqlQuery(sql);
				if(userList.size() > 0 ){
					flag = true ;
					strBuff.append(temp[1] + "：") ;
				}
				for (int j = 0; j < userList.size(); j++) {
					Object sqUserTab2 = (Object)userList.get(j);
					strBuff.append(sqUserTab2 + "   ");
					if(j == userList.size() -1 ){
						strBuff.append("\r\n");
					}
				}
			}
		}else if(type.equals("2")){
			//项目组周报提醒功能
			
			//1）查询项目组下所有的项目。
			String sql = "select project_info.* from sq_project_info as project_info where project_id in (select project_id from sq_workday_manager where group_no='"+sqUserTab.getSqGroupTab().getGroupNo()+"' and type='2' and week_date is null and work_cycle is null)";
			List projectList = iBaseDao.sqlQueryAnaity(sql , SqProjectInfo.class);
			boolean groupFlag = false ;
			strBuff.append("本项目组中有以下项目已经提交，请于下周提交或者删除该项目后再提交：\r\n");
			for (int i = 0; i < projectList.size(); i++) {
				SqProjectInfo sqPorjectInfo = (SqProjectInfo)projectList.get(i);
				String sqlTemp = "select project_id from sq_workday_manager where project_id='"+sqPorjectInfo.getProjectId()+"' and group_no='"+sqUserTab.getSqGroupTab().getGroupNo()+"' and type='2' and week_date='"+weekDate+"'";
				List projectListTemp = iBaseDao.sqlQueryAnaity(sqlTemp , SqProjectInfo.class);
				if(projectListTemp.size() > 0 ){
					groupFlag = true ;
					strBuff.append("	" + sqPorjectInfo.getProjectName() + "，负责人：" + sqPorjectInfo.getSqUserTab().getUserName() + "\r\n") ;
				}
			}
			
			if(groupFlag){
				return "ERROR" + strBuff.toString();
			}
			
			//2）查询项目组下所有的项目，并确认这些正常状态下的项目是否提交了项目周报
			sql = "select distinct project_info.* from sq_project_info project_info  where project_group='"+sqUserTab.getSqGroupTab().getGroupNo()+"' and status not in ('2','3') and project_info.project_id not in (select project_id from sq_workday_manager where group_no='"+sqUserTab.getSqGroupTab().getGroupNo()+"' and type='2' and work_cycle is null and week_date is null)";
			projectList = iBaseDao.sqlQueryAnaity(sql , SqProjectInfo.class);
			if(projectList.size() > 0 ){
				flag = true ;
			}
			if(!groupFlag){
				strBuff = new StringBuffer();
				strBuff.append("本项目组中有以下项目未提交周报，请确认是否提交：\r\n");
				for (int i = 0; i < projectList.size(); i++) {
					SqProjectInfo sqPorjectInfo = (SqProjectInfo)projectList.get(i);
					strBuff.append("	" + sqPorjectInfo.getProjectName() + "，负责人：" + sqPorjectInfo.getSqUserTab().getUserName() + "\r\n") ;
				}
			}
		}else if(type.equals("3")){
			//部门周报提醒功能
			//1）根据项目编号查询项目对应的所有开发人员,根据开发人员和项目编号查询开发人员提交的周报是否包含存在
			strBuff.append("本部门有以下项目组未提交周报，请确认是否提交：\r\n");
			String sql = "select distinct group_tab.* from sq_group_tab group_tab where group_tab.status='0' and group_tab.dept_no='"+sqUserTab.getSqDeptTab().getDeptNo()+"' and group_tab.group_no not in (select group_no from sq_workday_manager where type='3' and work_cycle is null and week_date is null )";
			List groupList = iBaseDao.sqlQueryAnaity(sql , SqGroupTab.class);
			if(groupList.size() > 0 ){
				flag = true ;
			}
			for (int j = 0; j < groupList.size(); j++) {
				SqGroupTab sqGroupTab = (SqGroupTab)groupList.get(j);
				strBuff.append("	"+sqGroupTab.getGroupName() + "，负责人： " + sqGroupTab.getSqUserTab().getUserName() + "\r\n");
			}
		}
		if(!flag)
			strBuff = new StringBuffer("OKK");
		return "SUCCE" + strBuff.toString();
	}
}
