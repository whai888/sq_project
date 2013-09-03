package com.sq.action.workday;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.sq.action.system.PageAction;
import com.sq.exception.SystemException;
import com.sq.logic.dept.IDeptService;
import com.sq.logic.group.IGroupService;
import com.sq.logic.project.IProjectDocService;
import com.sq.logic.project.IProjectService;
import com.sq.logic.tools.ISysparaTab;
import com.sq.logic.tools.IUserLog;
import com.sq.logic.user.IUserService;
import com.sq.logic.workday.IWorkDayService;
import com.sq.model.vo.SqDeptTab;
import com.sq.model.vo.SqDocmentManager;
import com.sq.model.vo.SqGroupTab;
import com.sq.model.vo.SqProjectInfo;
import com.sq.model.vo.SqUserTab;
import com.sq.model.vo.SqWorkdayManager;
import com.sq.sys.ApplicationDate;
import com.sq.tools.Constant;
import com.sq.tools.Public;
import com.sq.vo.ErrorForm;

/**
 * 
 *@autor whai
 */
public class WorkDayAction extends PageAction{
	private Logger log = Logger.getLogger(WorkDayAction.class);
	@Resource
	private IWorkDayService iWorkDayService;
	@Resource
	private IProjectService iProjectService;
	@Resource
	private IProjectDocService iProjectDocService ;
	@Resource
	private IUserService iUserService;
	@Resource
	private IGroupService iGroupService;
	@Resource
	private IDeptService iDeptService;
	@Resource
	private IUserLog iUserLog ;
	@Resource
	private ISysparaTab iSysparaTab;
	private String startDate ;
	private String endDate ;
	private SqUserTab sqUserTab;
	private String taskQu ;
	private String complePercenQu ;
	private String taskStatus ;
	private String ajaxStr ;
	private String [] workData ;
	private SqWorkdayManager sqWorkdayManager;
	private SqDocmentManager sqDocmentManager ;
	private SqGroupTab sqGroupTab ;
	private SqDeptTab sqDeptTab ;
	private SqProjectInfo sqProjectInfo ;
	private List tempList ;
	private String weekType ;
	private ErrorForm errorForm = new ErrorForm();
	private HttpServletRequest request = ServletActionContext.getRequest();
	private HttpSession session = request.getSession(false);
	private SqUserTab sqUserTabTemp = (SqUserTab) session.getAttribute("sqUserTab");
	
	/**
	 * 日报查询，根据指定的条件拼装SQL 进行查询
	 * @return
	 */
	public String workDayList() {
		String returnPage = request.getParameter("returnPage");
		try {
			if(returnPage.equals("workdaylist")){
				//日报查询
				//查询登陆员工对应的操作员工权限集合
				List userList = iWorkDayService.findUserQuery(sqUserTabTemp , sqGroupTab , sqProjectInfo);
				request.setAttribute("userList", userList) ;
				
				List groupList = iGroupService.findGroupBydeptNo(sqUserTabTemp.getSqDeptTab());
				request.setAttribute("groupList", groupList) ;
				
				List projectList = iProjectService.findProjectByGroupNo(sqGroupTab);
				request.setAttribute("projectList", projectList) ;
				
				if(startDate != null && !startDate.equals("")&& endDate != null && !endDate.equals("")){
					StringBuffer hql = new StringBuffer();
					String [] paramNames = new String[3];
					Object[] values = new Object[3] ;
					hql.append("select sqWorkdayManager from SqWorkdayManager sqWorkdayManager,SqUserTab sqUserTab where sqWorkdayManager.type ='0' ") ;
					//日报开始日期
					hql.append("and sqWorkdayManager.workDate >= :startDate ") ;
					paramNames[0] = "startDate" ;
					values[0] = Public.getStringToDate(startDate, "yyyy-MM-dd") ;
					//日报开始日期
					hql.append("and sqWorkdayManager.workDate <= :endDate ") ;
					paramNames[1] = "endDate" ;
					values[1] =Public.getStringToDate(endDate, "yyyy-MM-dd")  ;
					//日报提交人
					if(sqUserTab.getUserId().equals("-99")){
						hql.append("and sqWorkdayManager.sqUserTab.userId in (:userId) ") ;
						paramNames[2] = "listuserId" ;
						String [] strBuff = null ;
						if(userList.size() == 0 ){
							strBuff = new String[1];
							strBuff[0] = "0" ;
						}else{
							strBuff = new String[userList.size()];
							for (int i = 0; i < userList.size(); i++) {
								SqUserTab sqUserTab =  (SqUserTab)userList.get(i);
								strBuff[i] = sqUserTab.getUserId();
							}
						}
						values[2] = strBuff;
					}else{
						hql.append("and sqWorkdayManager.sqUserTab.userId = :userId ") ;
						paramNames[2] = "userId" ;
						values[2] = sqUserTab.getUserId();
					}
					hql.append(" and sqUserTab.userId = sqWorkdayManager.sqUserTab.userId order by sqUserTab.pyName asc");
					
					this.executeHql(hql.toString(), paramNames, values, Integer.parseInt(this.getCurrentStr())) ;
				}
				request.setAttribute("workViewList", this.getListData()) ;
				
			}else if(returnPage.equals("weekdaylist")){
				//周报信息查询
				//查询登陆员工对应的操作员工权限集合
				List userList = iWorkDayService.findUserQuery(sqUserTabTemp, sqGroupTab , sqProjectInfo);
				request.setAttribute("userList", userList) ;
				//查询本人所管理的项目组信息
				List groupList = iGroupService.findGroupBydeptNo(sqUserTabTemp.getSqDeptTab());
				request.setAttribute("groupList", groupList) ;
				
				//查询日报系统中近10次提交的日报信息
				List userWorkList = iWorkDayService.myWorkView(sqUserTabTemp, null, "8", "0");
				request.setAttribute("userWorkList", userWorkList) ;
				
				Map<SqUserTab , List> weekDayMap = new LinkedHashMap<SqUserTab , List> ();
				boolean flag = false ;	//页面传过来的员工编号与循环的相符
				for (int i = 0; i < userList.size(); i++) {
					SqUserTab userTemp = (SqUserTab)userList.get(i);
					//如果传进来的有值，则按照传进行来的值
					if(sqUserTab!=null && !sqUserTab.getUserId().equals("")&&!sqUserTab.getUserId().equals("-99")){
						if(!userTemp.getUserId().equals(sqUserTab.getUserId())){
							flag = true ;
							continue;
						}
					}
					//查询最近提交的一次周报
					StringBuffer hql = new StringBuffer();
					String [] paramNames = new String[2];
					Object[] values = new Object[2] ;
					hql.append("select distinct sqWorkdayManager.weekDate from SqWorkdayManager sqWorkdayManager where sqWorkdayManager.type ='0' and sqWorkdayManager.weekDate is not null ") ;
					//周报周期
					if(sqWorkdayManager != null && !sqWorkdayManager.getWeekDate().equals("")&&!sqWorkdayManager.getWeekDate().equals("-99")){
						hql.append("and sqWorkdayManager.weekDate = :weekDate ") ;
						paramNames[0] = "weekDate" ;
						values[0] = sqWorkdayManager.getWeekDate() ;
					}else if((sqWorkdayManager == null || sqWorkdayManager.getWeekDate().equals(""))&&userWorkList.size()>0){
						hql.append("and sqWorkdayManager.weekDate = :weekDate ") ;
						paramNames[0] = "weekDate" ;
						values[0] = userWorkList.get(0) ;
					}else{
						hql.append("and '1'=:weekDate ") ;
						paramNames[0] = "weekDate" ;
						values[0] = "1" ;
					}
					//日报提交人
					hql.append("and sqWorkdayManager.sqUserTab.userId = :userId ") ;
					paramNames[1] = "userId" ;
					values[1] = userTemp.getUserId();
					hql.append(" order by sqWorkdayManager.workdayId desc limit 0,10 ");
					
					this.executeHql(hql.toString(), paramNames, values, Integer.parseInt(this.getCurrentStr())) ;
					//如果员工没有日报信息，则不显示
					if(this.getListData().size()>0 || flag)
						weekDayMap.put(userTemp, this.getListData());
				}
				request.setAttribute("weekDayMap", weekDayMap) ;
			}else if(returnPage.equals("deptweekdaylist")){
				//部门周报信息查询
				//查询所有的部门信息
				List deptList = iDeptService.findByAll();
				request.setAttribute("deptList", deptList) ;
				//查询本人所管理的项目组信息
				List groupList = iGroupService.findGroupBydeptNo(sqUserTabTemp.getSqDeptTab());
				request.setAttribute("groupList", groupList) ;
				
				//查询日报系统中近10次提交的日报信息
				List userWorkList = iWorkDayService.myWorkView(sqUserTabTemp, null, "8", "3");
				request.setAttribute("userWorkList", userWorkList) ;
				
				Map<SqDeptTab , List> weekDayMap = new LinkedHashMap<SqDeptTab , List> ();
				boolean flag = false ;	//页面传过来的员工编号与循环的相符
				for (int i = 0; i < deptList.size(); i++) {
					SqDeptTab deptTemp = (SqDeptTab)deptList.get(i);
					//如果传进来的有值，则按照传进行来的值
					if(sqDeptTab!=null && !sqDeptTab.getDeptNo().equals("")&&!sqDeptTab.getDeptNo().equals("-99")){
						if(!deptTemp.getDeptNo().equals(sqDeptTab.getDeptNo())){
							flag = true ;
							continue;
						}
					}
					//查询最近提交的一次周报
					StringBuffer hql = new StringBuffer();
					String [] paramNames = new String[2];
					Object[] values = new Object[2] ;
					hql.append("select distinct sqWorkdayManager.weekDate from SqWorkdayManager sqWorkdayManager where sqWorkdayManager.type ='3' and sqWorkdayManager.weekDate is not null ") ;
					//周报周期
					if(sqWorkdayManager != null && !sqWorkdayManager.getWeekDate().equals("")&&!sqWorkdayManager.getWeekDate().equals("-99")){
						hql.append("and sqWorkdayManager.weekDate = :weekDate ") ;
						paramNames[0] = "weekDate" ;
						values[0] = sqWorkdayManager.getWeekDate() ;
					}else if((sqWorkdayManager == null || sqWorkdayManager.getWeekDate().equals(""))&&userWorkList.size()>0){
						hql.append("and sqWorkdayManager.weekDate = :weekDate ") ;
						paramNames[0] = "weekDate" ;
						values[0] = userWorkList.get(0) ;
					}else{
						hql.append("and '1'=:weekDate ") ;
						paramNames[0] = "weekDate" ;
						values[0] = "1" ;
					}
					//日报提交人
					hql.append("and sqWorkdayManager.sqUserTab.userId = :userId ") ;
					paramNames[1] = "userId" ;
					values[1] = deptTemp.getSqUserTab().getUserId();
					hql.append(" order by sqWorkdayManager.workdayId desc ");
					
					this.executeHql(hql.toString(), paramNames, values, Integer.parseInt(this.getCurrentStr())) ;
					//如果员工没有日报信息，则不显示
					if(this.getListData().size()>0 || flag)
						weekDayMap.put(deptTemp, this.getListData());
				}
				request.setAttribute("weekDayMap", weekDayMap) ;
			}else if(returnPage.equals("myworkview")){
				StringBuffer hql = new StringBuffer();
				String [] paramNames = new String[2];
				Object[] values = new Object[2] ;
				hql.append("from SqWorkdayManager sqWorkdayManager where sqWorkdayManager.type ='0' ") ;
				//周报周期
				hql.append("and sqWorkdayManager.weekDate = :weekDate ") ;
				paramNames[0] = "weekDate" ;
				values[0] = sqWorkdayManager.getWeekDate() ;
				//日报提交人
				hql.append("and sqWorkdayManager.sqUserTab.userId = :userId ") ;
				paramNames[1] = "userId" ;
				values[1] = sqUserTab.getUserId();
				hql.append(" order by sqWorkdayManager.weekDate desc");
				
				this.executeHql(hql.toString(), paramNames, values, Integer.parseInt(this.getCurrentStr())) ;
				request.setAttribute("workViewList", this.getListData()) ;
			}else if(returnPage.equals("autoworkdaylist")){
				//信息首页自动将本日提交的员工信息显示出来
				StringBuffer hql = new StringBuffer();
				String [] paramNames = new String[1];
				Object[] values = new Object[1] ;
				hql.append("from SqWorkdayManager sqWorkdayManager where sqWorkdayManager.type ='0' ") ;
				//日报开始日期
				hql.append("and sqWorkdayManager.workDate = :startDate ") ;
				paramNames[0] = "startDate" ;
				values[0] = Public.getStringToDate(null, "yyyy-MM-dd") ;
				hql.append(" order by sqWorkdayManager.workDate desc, sqWorkdayManager.workTime desc");
				this.executeHql(hql.toString(), paramNames, values, Integer.parseInt(this.getCurrentStr())) ;
				request.setAttribute("workViewList", this.getListData()) ;
			}else if(returnPage.equals("noworkview")){
				//未提交周报查询
				List noWorkList = new ArrayList();
				//查询最近15次提交的周报信息
				this.tempList = iWorkDayService.myWorkView(null, null, "8", "0");
				//周报类型
				List groupList = iGroupService.findGroupBydeptNo(sqUserTabTemp.getSqDeptTab());
				request.setAttribute("groupList", groupList) ;
				
				if(startDate != null && !startDate.equals("")&& endDate != null && !endDate.equals("")){
					//如果列表中不包含知道的元素
					if(!tempList.contains(startDate))
						tempList.add(0, startDate);
					if(!tempList.contains(endDate))
						tempList.add(0, endDate);
						
					StringBuffer hql = new StringBuffer();
					//查询出时间段内所有的周期
					hql.append("select distinct sqWorkdayManager.weekDate  from SqWorkdayManager sqWorkdayManager where 1=1 ") ;
					//周报开始日期
					hql.append("and sqWorkdayManager.weekDate >= '"+startDate+"' ") ;
					//周报开始日期
					hql.append("and sqWorkdayManager.weekDate <= '"+endDate+"' ") ;
					//周报类型
					hql.append("and sqWorkdayManager.type = '"+weekType+"' order by sqWorkdayManager.weekDate desc");
					this.executeHql(hql.toString(), new String[0], new String[0], -99) ;
					
					List tempWeekDate = this.getListData() ;
					for(int i=0 ; i<tempWeekDate.size() ; i++){
						hql = new StringBuffer();
						String sqWorkdayManagerTempWeekDate = (String)tempWeekDate.get(i);
						String [] paramNames = new String[2];
						Object[] values = new Object[2] ;
						if(weekType.equals("0")){
							//个人周报
							hql.append("from SqUserTab sqUserTab where sqUserTab.sqDeptTab.deptNo='"+sqUserTabTemp.getSqDeptTab().getDeptNo()+"' and sqUserTab.userId not in (");
						}else if(weekType.equals("1")){
							//项目周报
							hql.append("from SqProjectInfo sqProjectInfo where sqProjectInfo.sqGroupTab.groupNo in (select groupNo from SqGroupTab sqGroupTab where sqGroupTab.sqDeptTab.deptNo='"+sqUserTabTemp.getSqDeptTab().getDeptNo()+"') and sqProjectInfo.sqUserTab.userId not in (");
						}else if(weekType.equals("2")){
							//项目组周报
							hql.append("from SqGroupTab sqGroupTab where sqGroupTab.sqDeptTab.deptNo='"+sqUserTabTemp.getSqDeptTab().getDeptNo()+"' and sqGroupTab.sqUserTab.userId not in (");
						}else if(weekType.equals("3")){
							//部门周报
							hql.append("select sqUserTab from SqUserTab sqUserTab , SqDeptTab sqDeptTab where sqDeptTab.deptNo='"+sqUserTabTemp.getSqDeptTab().getDeptNo()+"' and sqUserTab.userId = sqDeptTab.deptUser and sqDeptTab.deptUser not in (");
						}
						hql.append("select distinct sqWorkdayManager.sqUserTab.userId  from SqWorkdayManager sqWorkdayManager where 1=1 ") ;
						//周报开始日期
						hql.append("and sqWorkdayManager.weekDate >= :weekDate ") ;
						paramNames[0] = "weekDate" ;
						values[0] = sqWorkdayManagerTempWeekDate ;
						//周报类型
						hql.append("and sqWorkdayManager.type = :weekType ");
						paramNames[1] = "weekType" ;
						values[1] =weekType  ;
						if(!sqGroupTab.getGroupNo().equals("-99")){
							//项目组信息
							hql.append("and sqWorkdayManager.sqGroupTab.groupNo = '"+sqGroupTab.getGroupNo()+"'");
							if(weekType.equals("0")){
								//个人周报
								hql.append(") and sqUserTab.status!='1' and sqUserTab.sqGroupTab.groupNo = '"+sqGroupTab.getGroupNo()+"' order by sqUserTab.email");
							}else if(weekType.equals("1")){
								//项目周报
								hql.append(") and sqProjectInfo.status not in ('2','3') and sqProjectInfo.sqGroupTab.groupNo = '"+sqGroupTab.getGroupNo()+"' order by sqProjectInfo.projectId");
							}else if(weekType.equals("2")){
								//项目组周报
								hql.append(") and sqGroupTab.status!='1' and sqGroupTab.groupNo = '"+sqGroupTab.getGroupNo()+"' order by sqGroupTab.groupNo");
							}else if(weekType.equals("3")){
								//部门周报
								hql.append(") and sqDeptTab.status!='1' order by sqDeptTab.deptNo");
							}
						}else{
							if(weekType.equals("0")){
								//个人周报
								hql.append(") and sqUserTab.status!='1' order by sqUserTab.email");
							}else if(weekType.equals("1")){
								//项目周报
								hql.append(") and sqProjectInfo.status not in ('2','3') order by sqProjectInfo.projectId");
							}else if(weekType.equals("2")){
								//项目组周报
								hql.append(") and sqGroupTab.status!='1' order by sqGroupTab.groupNo");
							}else if(weekType.equals("3")){
								//部门周报
								hql.append(") and sqDeptTab.status!='1' order by sqDeptTab.deptNo");
							}
						}
						this.executeHql(hql.toString(), paramNames, values, -99) ;
						List listDate = this.getListData();
						for (int j = 0; j < listDate.size(); j++) {
							SqUserTab sqUserTab = null ;
				       		if(listDate.get(j) instanceof SqUserTab)
				       			sqUserTab = (SqUserTab)listDate.get(j);
				       		else if(listDate.get(j) instanceof SqProjectInfo)
				       			sqUserTab = ((SqProjectInfo)listDate.get(j)).getSqUserTab();
				       		else if(listDate.get(j) instanceof SqGroupTab)
				       			sqUserTab = ((SqGroupTab)listDate.get(j)).getSqUserTab();
				       		else if(listDate.get(j) instanceof SqDeptTab)
				       			sqUserTab = ((SqDeptTab)listDate.get(j)).getSqUserTab();
				       		//如果为部门周报，则项目组一栏显示部门名称，否则显示项目组名称
				       		if(weekType.equals("3"))
				       			sqUserTab.setIp(sqUserTab.getSqDeptTab().getDeptName());
				       		else
				       			sqUserTab.setIp(sqUserTab.getSqGroupTab().getGroupName());
				       		//周报类型显示
				       		if(weekType.equals("0"))sqUserTab.setRemark("个人周报->" + sqWorkdayManagerTempWeekDate);
				       		if(weekType.equals("1"))sqUserTab.setRemark("项目周报("+((SqProjectInfo)listDate.get(j)).getProjectName()+")->" + sqWorkdayManagerTempWeekDate);
				       		if(weekType.equals("2"))sqUserTab.setRemark("项目组周报->" + sqWorkdayManagerTempWeekDate);
				       		if(weekType.equals("3"))sqUserTab.setRemark("部门周报->" + sqWorkdayManagerTempWeekDate);
				       		noWorkList.add(sqUserTab);
						}
					}
				}
				this.setChanagePageTab(noWorkList);
				List noWorkListTemp = new ArrayList();
				if(Integer.parseInt(this.getCurrentStr())*Constant.PAGE_COUNT > noWorkList.size())
					noWorkListTemp = noWorkList.subList((Integer.parseInt(this.getCurrentStr())-1)*Constant.PAGE_COUNT , noWorkList.size()) ;
				else
					noWorkListTemp = noWorkList.subList((Integer.parseInt(this.getCurrentStr())-1)*Constant.PAGE_COUNT , Integer.parseInt(this.getCurrentStr())*Constant.PAGE_COUNT) ;
				this.getHtmlPageTag();
				request.setAttribute("pageTag", this.getPageTag());
				request.setAttribute("workViewList", noWorkListTemp) ;
				
			}
		} catch (SystemException e) {
			e.printStackTrace();
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		
		return returnPage;
	}
	/**
	 * 生成项目周报查询
	 * @return
	 */
	public String workDayAuditList() {
		try {
			Map<SqProjectInfo, List<SqWorkdayManager>> workDayMap = iWorkDayService.auditWorkDay(sqUserTabTemp) ;
			request.setAttribute("workDayMap", workDayMap) ;
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "workdayauditlist";
	}

	/**
	 * 我的日报查询
	 * @return
	 */
	public String myworkDayEditList(){
		try {
			//查询当前用户下所有的项目
			List projectList = iProjectService.findByAll(sqUserTabTemp, "1");
			request.setAttribute("projectList", projectList) ;
			
			//查询当前用户下本周的所有的日报
			String startDate = Public.getTimeToFormat(Public.getDateToAddRed(null, "yyyy-MM-dd", (Public.dayForWeek(Calendar.DAY_OF_WEEK)-1)),"yyyy-MM-dd");
			String endDate = Public.getSystemTimeToFormat("yyyy-MM-dd");
			List sqWorkdayManagerList = iWorkDayService.myWorkView(sqUserTabTemp, startDate, endDate, "0");
			
			request.setAttribute("sqWorkdayManagerList", sqWorkdayManagerList) ;
			sqWorkdayManager = new SqWorkdayManager();
			sqWorkdayManager.setComplePercen(100);
			request.setAttribute("sqWorkdayManager", sqWorkdayManager);
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "myworkdayeditlist" ;
	}
	/**
	 * 提交我的周报
	 * @return
	 */
	public String myWorkSub(){
		try {
			iWorkDayService.myWorkSub(sqUserTabTemp , "0");
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "myworksub";
	}
	/**
	 * 我的周报预览
	 * @return
	 */
	public String myWorkView(){
		try {
			//加载本周的项目内容
			
			List workViewList = iWorkDayService.myWorkView(sqUserTabTemp, null, "7","0");
			request.setAttribute("workViewList", workViewList);
			//加载项目组信息
			request.setAttribute("sqGroupTab", sqUserTabTemp.getSqGroupTab()) ;
			
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "myworkview";
	}
	
	/**
	 * 项目周报预览
	 * @return
	 */
	public String projectWorkView(){
		try {
			List workViewList = iWorkDayService.myWorkView(sqUserTabTemp, null, "7","1");
			request.setAttribute("workViewList", workViewList);
			
			//加载员工信息
			request.setAttribute("sqUserTab", sqUserTabTemp);
			
			//加载项目组信息
			request.setAttribute("sqGroupTab", sqUserTabTemp.getSqGroupTab()) ;
			
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "projectworkview";
	}
	
	/**
	 * 项目组周报预览
	 * @return
	 */
	public String groupWorkView(){
		String returnPage = request.getParameter("returnPage");
		try {
			//项目组周报查询
			if(returnPage.equals("growthgrouplist")){
				//加载本周的项目内容
				List workViewList = iWorkDayService.myWorkView(sqUserTabTemp, null, "9","1");
				request.setAttribute("workViewList", workViewList);
			}
			//项目组周报预览
			if(returnPage.equals("groupworkview")){
				//加载本周的项目内容
				List workViewList = iWorkDayService.myWorkView(sqUserTabTemp, null, null,"2");
				request.setAttribute("workViewList", workViewList);
				//加载员工信息
				SqUserTab sqUserTab = iUserService.findById(sqUserTabTemp.getUserId());
				request.setAttribute("sqUserTab", sqUserTab);
				//加载项目组信息
//				SqGroupTab sqGroupTab = iGroupService.findById(sqUserTab.getProjectGroup());
				request.setAttribute("sqGroupTab", sqUserTabTemp.getSqGroupTab());
			}
			
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return returnPage;
	}
	/**
	 * 日报详细信息查询
	 * @return
	 */
	public String workDayQueryListInfo(){
		String returnPage = request.getParameter("returnPage");
		try {
			//根据日报主键查询日报内容
			this.sqWorkdayManager = (SqWorkdayManager)iWorkDayService.findById(sqWorkdayManager);
			//查询日报对应的文档信息
			SqDocmentManager sqDocmentManager = new SqDocmentManager();
			sqDocmentManager.setProjectId(sqWorkdayManager.getWorkdayId());
			List projectDocList = iProjectDocService.projectDocFindInfo(sqDocmentManager);
			request.setAttribute("projectDocList", projectDocList) ;
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return returnPage ;
	}
	/**
	 * 新增我的日报内容
	 * @return
	 */
	public String myworkDayEditAdd(){
		try {
			iWorkDayService.myworkDayEditAdd(sqUserTabTemp,sqWorkdayManager);
			//填写的工作成果为文档或者案例，则需要上传文件
			String [] unitsSplit = sqWorkdayManager.getWorkUnits().split("\\|");
			if(unitsSplit!=null && unitsSplit.length>2 && (unitsSplit[0].equals("1") || unitsSplit[2].equals("1"))){
				if(fileName == null)
					throw new SystemException(WorkDayAction.class ,"您选择的工作成果为文档或者案例，请上传指定的文件。");
				sqDocmentManager.setProjectId(sqWorkdayManager.getSqProjectInfo().getProjectId());
				
				if(fileNameFileName!=null){
					this.projectDocUpload(sqDocmentManager);
					//保存上传的文件信息
					SqUserTab sqUserTabTemp = (SqUserTab) session.getAttribute("sqUserTab");
					sqDocmentManager.setStepId(sqWorkdayManager.getWorkdayId());
					sqDocmentManager.setUserId(sqUserTabTemp.getUserId());
					sqDocmentManager.setFilePath(directory) ;
					sqDocmentManager.setBackfileName(fileNameFileName);
					sqDocmentManager.setFileName(targetFileName) ;
					iProjectDocService.saveDocManager(sqDocmentManager );
				}
			}
		} catch (SystemException e) {
			e.printStackTrace();
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "myworkdayeditadd";
	}

	/**
	 * 修改我的日报时的查询
	 * @return
	 */
	public String myworkDayEditUpdateFind(){
		try {
			//查询当前用户下所有的项目
			List projectList = iProjectService.findByAll(sqUserTabTemp , "1");
			request.setAttribute("projectList", projectList) ;
			
			//根据日报主键查询日报内容
			this.sqWorkdayManager = iWorkDayService.findById(sqWorkdayManager);
			request.setAttribute("sqWorkdayManager", this.sqWorkdayManager) ;
			//查询当前用户下所有的日报
			//查询当前用户下本周的所有的日报
			List sqWorkdayManagerList = iWorkDayService.myWorkView(sqUserTabTemp, null, null, "0");
			
			request.setAttribute("sqWorkdayManagerList", sqWorkdayManagerList) ;
		} catch (SystemException e) {
			e.printStackTrace();
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "myworkdayeditupdatefind";
	}
	
	/**
	 * 更新我的日报
	 * @return
	 */
	public String myworkDayEditUpdate(){
		try {
			iWorkDayService.myworkDayEditUpdate(sqWorkdayManager,sqUserTabTemp , "2");
			sqDocmentManager.setProjectId(sqWorkdayManager.getSqProjectInfo().getProjectId());
			if(fileNameFileName!=null){
				this.projectDocUpload(sqDocmentManager);
				//保存上传的文件信息
				SqUserTab sqUserTabTemp = (SqUserTab) session.getAttribute("sqUserTab");
				sqDocmentManager.setStepId(sqWorkdayManager.getWorkdayId());
				sqDocmentManager.setUserId(sqUserTabTemp.getUserId());
				sqDocmentManager.setFilePath(directory) ;
				sqDocmentManager.setBackfileName(fileNameFileName);
				sqDocmentManager.setFileName(targetFileName) ;
				iProjectDocService.saveDocManager(sqDocmentManager );
			}
			
		} catch (SystemException e) {
			e.printStackTrace();
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "myworkdayeditupdate" ;
	}
	
	/**
	 * 删除我的日报内容
	 * @return
	 */
	public String myworkDayEditDelete(){
		try {
			String workdayId = request.getParameter("workdayId");
			String projectId = request.getParameter("projectId");
			iWorkDayService.myworkDayEditDelete(workdayId , projectId);
		} catch (SystemException e) {
			e.printStackTrace();
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "myworkdayeditdelete";
	}
	
	/**
	 * 项目日报提交查询
	 * @return
	 */
	public String workDayProjectReport(){
		try {
			//查询当前用户下所有的项目
			List projectList = iProjectService.findByAll(sqUserTabTemp, "2");
			if(projectList.size() ==0 )
				throw new SystemException(WorkDayAction.class ,"您没有可管理的项目，不能编写项目周报。");
			
			request.setAttribute("projectList", projectList) ;
			SqProjectInfo sqProjectInfo = null ;
			if(sqWorkdayManager == null){
				sqWorkdayManager = new SqWorkdayManager();
				sqProjectInfo = (SqProjectInfo)projectList.get(0) ;
				sqWorkdayManager.setSqProjectInfo(sqProjectInfo);
			}else{
				sqProjectInfo = iProjectService.findById(sqWorkdayManager.getSqProjectInfo(),"1") ;
				sqWorkdayManager.setWorkCycle(null);
			}
			//根据日报主键查询日报内容
			List workDayList = iWorkDayService.projectWorkDayList(sqUserTabTemp , sqWorkdayManager , "1") ;
			if(workDayList.size() > 0 ){
				sqWorkdayManager = (SqWorkdayManager)workDayList.get(0);
			}else{
				this.sqWorkdayManager = new  SqWorkdayManager();
				this.sqWorkdayManager.setSqProjectInfo(sqProjectInfo);
			}
			sqWorkdayManager.setWorkCycle(iSysparaTab.findById("99999"));
			sqWorkdayManager.setWorkStartDate(Public.getTimeToFormat(Public.weekForDay("yyyy-MM-dd", Calendar.MONDAY),"yyyy-MM-dd"));
			sqWorkdayManager.setWorkEndDate(Public.getSystemTimeToFormat("yyyy-MM-dd"));
			sqWorkdayManager.setComplePercen(100);
			
			List sqWorkdayManagerList = iWorkDayService.myWorkView(sqUserTabTemp, null, null, "1");
			request.setAttribute("sqWorkdayManagerList", sqWorkdayManagerList) ;
		} catch (SystemException e) {
			e.printStackTrace();
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "workdayproeditlist" ;
	}
	
	/**
	 * 项目组周报提交查询
	 * @return
	 */
	public String workDayGroupReport(){
		try {
			//查询当前用户所在项目组下所有的项目
			List projectList = iProjectService.findByAll(sqUserTabTemp, "3");
			if(projectList.size() ==0 )
				throw new SystemException(WorkDayAction.class ,"您所在的项目组没有项目，不能编写项目组周报。");
			
			request.setAttribute("projectList", projectList) ;
			SqProjectInfo sqProjectInfo = null ;
			if(sqWorkdayManager == null){
				sqWorkdayManager = new SqWorkdayManager();
				sqProjectInfo = (SqProjectInfo)projectList.get(0) ;
				sqWorkdayManager.setSqProjectInfo(sqProjectInfo);
			}else{
				sqWorkdayManager.setWorkCycle(null);
				sqProjectInfo = iProjectService.findById(sqWorkdayManager.getSqProjectInfo(),"1") ;
			}
			
			//根据日报主键查询日报内容
			List workDayList = iWorkDayService.groupWorkDayList(sqUserTabTemp , sqWorkdayManager , "1") ;
			if(workDayList.size() > 0 ){
				sqWorkdayManager = (SqWorkdayManager)workDayList.get(0);
			}else{
				this.sqWorkdayManager = new  SqWorkdayManager();
				this.sqWorkdayManager.setSqProjectInfo(sqProjectInfo);
			}
			sqWorkdayManager.setWorkStartDate(Public.getTimeToFormat(Public.weekForDay("yyyy-MM-dd", Calendar.MONDAY),"yyyy-MM-dd"));
			sqWorkdayManager.setWorkEndDate(Public.getSystemTimeToFormat("yyyy-MM-dd"));
			sqWorkdayManager.setWorkCycle(iSysparaTab.findById("99999"));
			sqWorkdayManager.setComplePercen(100);
			List sqWorkdayManagerList = iWorkDayService.myWorkView(sqUserTabTemp, null, null, "2");
			
			request.setAttribute("sqWorkdayManagerList", sqWorkdayManagerList) ;
		} catch (SystemException e) {
			e.printStackTrace();
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "groupworkdaylist" ;
	}
	/**
	 * 项目日报提交
	 * @return
	 */
	public String workDayProjectAdd(){
		try {
			sqWorkdayManager.setSqUserTab(sqUserTabTemp);
			iWorkDayService.myworkProjectEditAdd(sqUserTabTemp,sqWorkdayManager);
		} catch (SystemException e) {
			e.printStackTrace();
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "workdayproeditadd" ;
	}
	/**
	 * 提交项目周报
	 * @return
	 */
	public String projectWorkSub(){
		try {
			iWorkDayService.myWorkSub(sqUserTabTemp , "1");
		} catch (SystemException e) {
			e.printStackTrace();
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "projectworksub";
	}
	/**
	 * 项目组周报提交
	 * @return
	 */
	public String workDayGroupAdd(){
		try {
			sqWorkdayManager.setSqUserTab(sqUserTabTemp);
			iWorkDayService.myworkGroupEditAdd(sqUserTabTemp,sqWorkdayManager);
		} catch (SystemException e) {
			e.printStackTrace();
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "workdaygroupeditadd" ;
	}
	/**
	 * 提交项目组周报
	 * @return
	 */
	public String groupWorkSub(){
		try {
			iWorkDayService.myWorkSub(sqUserTabTemp , "2");
		} catch (SystemException e) {
			e.printStackTrace();
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "groupworksub";
	}
	/**
	 * 修改我的项目日报时的查询
	 * @return
	 */
	public String myworkProjectEditUpdateFind(){
		try {
			//查询当前用户下所有的项目
			List projectList = iProjectService.findByAll(sqUserTabTemp , "1");
			request.setAttribute("projectList", projectList) ;
			
			//根据日报主键查询日报内容
			this.sqWorkdayManager = iWorkDayService.findById(sqWorkdayManager);
			sqWorkdayManager.setWorkCycle(iSysparaTab.findById("99999"));
			request.setAttribute("sqWorkdayManager", this.sqWorkdayManager) ;

			List sqWorkdayManagerList = iWorkDayService.myWorkView(sqUserTabTemp, null,null, "1");
			
			request.setAttribute("sqWorkdayManagerList", sqWorkdayManagerList) ;
		} catch (SystemException e) {
			e.printStackTrace();
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "myworkprojecteditupdatefind";
	}
	
	/**
	 * 修改项目组日报时的查询
	 * @return
	 */
	public String myworkGroupEditUpdateFind(){
		try {
			//查询当前用户下所有的项目
			List projectList = iProjectService.findByAll(sqUserTabTemp , "3");
			request.setAttribute("projectList", projectList) ;
			
			//根据日报主键查询日报内容
			this.sqWorkdayManager = iWorkDayService.findById(sqWorkdayManager);
			sqWorkdayManager.setWorkCycle(iSysparaTab.findById("99999"));
			if(sqWorkdayManager.getWorkStartDate() == null )
				sqWorkdayManager.setWorkStartDate(Public.getTimeToFormat(Public.weekForDay("yyyy-MM-dd", Calendar.MONDAY),"yyyy-MM-dd"));
			if(sqWorkdayManager.getWorkEndDate() == null )
				sqWorkdayManager.setWorkEndDate(Public.getSystemTimeToFormat("yyyy-MM-dd"));
				
			request.setAttribute("sqWorkdayManager", this.sqWorkdayManager) ;

			List sqWorkdayManagerList = iWorkDayService.myWorkView(sqUserTabTemp, null, null, "2");
			
			request.setAttribute("sqWorkdayManagerList", sqWorkdayManagerList) ;
		} catch (SystemException e) {
			e.printStackTrace();
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "myworkgroupeditupdatefind";
	}
	/**
	 * 修改项目组日报时的查询
	 * @return
	 */
	public String workDayGroupEditUpdateFind(){
		try {
			//查询当前用户下所有的项目
			List projectList = iProjectService.findByAll(sqUserTabTemp , "3");
			request.setAttribute("projectList", projectList) ;
			
			//根据日报主键查询日报内容
			this.sqWorkdayManager = iWorkDayService.findById(sqWorkdayManager);
			request.setAttribute("sqWorkdayManager", this.sqWorkdayManager) ;
			
			List sqWorkdayManagerList = iWorkDayService.myWorkView(sqUserTabTemp, null, null, "2");
			
			request.setAttribute("sqWorkdayManagerList", sqWorkdayManagerList) ;
		} catch (SystemException e) {
			e.printStackTrace();
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "workdaygroupeditupdatefind";
	}
	/**
	 * 更新我的项目日报
	 * @return
	 */
	public String myworkProjectEditUpdate(){
		try {
			iWorkDayService.myworkDayEditUpdate(sqWorkdayManager ,sqUserTabTemp , "2");
		} catch (SystemException e) {
			e.printStackTrace();
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "myworkprojecteditupdate" ;
	}
	
	/**
	 * 更新项目组日报
	 * @return
	 */
	public String myworkGroupEditUpdate(){
		try {
			iWorkDayService.myworkDayEditUpdate(sqWorkdayManager ,sqUserTabTemp , "2");
		} catch (SystemException e) {
			e.printStackTrace();
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "myworkgroupeditupdate" ;
	}
	/**
	 * 删除我的项目日报
	 * @return
	 */
	public String myworkProjectEditDelete(){
		try {
			String workdayId = request.getParameter("workdayId");
			String projectId = request.getParameter("projectId");
			iWorkDayService.myworkDayEditDelete(workdayId , projectId);
		} catch (SystemException e) {
			e.printStackTrace();
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "myworkprojecteditdelete";
	}
	/**
	 * 删除项目组的项目日报
	 * @return
	 */
	public String myworkGroupEditDelete(){
		try {
			String workdayId = request.getParameter("workdayId");
			String projectId = request.getParameter("projectId");
			iWorkDayService.myworkDayEditDelete(workdayId , projectId);
		} catch (SystemException e) {
			e.printStackTrace();
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "myworkgroupeditdelete";
	}
	/**
	 * 项目周报查询
	 * @return
	 */
	public String workDayProjectList() {
		String returnPage = request.getParameter("returnPage");
		try {
			//查询日报系统中近10次提交的日报信息
			List userWorkList = iWorkDayService.myWorkView(null, null, "8", "1");
			request.setAttribute("userWorkList", userWorkList) ;
			
			//根据员工的信息，查询员工对应的项目信息
			Map<SqGroupTab , List<SqProjectInfo>> projectList = iWorkDayService.findProjectQuery(sqUserTabTemp);
			request.setAttribute("projectList", projectList) ;
			
			List sqWorkdayManagerList = new ArrayList();
			
			if(sqWorkdayManager != null && !sqWorkdayManager.getWeekDate().equals("")){
				StringBuffer hql = new StringBuffer();
				String [] paramNames = new String[2];
				Object[] values = new Object[2] ;
				hql.append("from SqWorkdayManager as sqWorkdayManager where sqWorkdayManager.type ='1' ") ;
				//周报周期
				hql.append("and sqWorkdayManager.weekDate = :weekDate ") ;
				paramNames[0] = "weekDate" ;
				values[0] = sqWorkdayManager.getWeekDate() ;
				//周报周期
				hql.append("and sqWorkdayManager.sqProjectInfo.projectId = :projectId ") ;
				paramNames[1] = "projectId" ;
				values[1] = sqWorkdayManager.getSqProjectInfo().getProjectId() ;
				hql.append(" order by sqWorkdayManager.sqProjectInfo.projectId asc");
				
				this.executeHql(hql.toString(), paramNames, values, Integer.parseInt(this.getCurrentStr())) ;
				
				//将得到的数据进行解析
				try {
					if(this.getListData().size() >=1){
						SqWorkdayManager temp = (SqWorkdayManager)this.getListData().get(0);
						//加载员工信息
						SqUserTab sqUserTab = iUserService.findById(temp.getSqUserTab().getUserId());
						request.setAttribute("sqUserTab", sqUserTab);
						//加载项目组信息
//						SqGroupTab sqGroupTab = iGroupService.findById(sqUserTab.getProjectGroup());
						request.setAttribute("sqGroupTab", temp.getSqGroupTab());
					}
					
				} catch (SystemException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			request.setAttribute("workViewList", this.getListData()) ;
			
		} catch (SystemException e) {
			e.printStackTrace();
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		if(Public.isEmpty(returnPage))
			returnPage = "workdayprolist" ;
		return returnPage;
	}
	
	/**
	 * 项目组周报查询
	 * @return
	 */
	public String workDayGroupList() {
		String returnPage = request.getParameter("returnPage");
		try {
			//查询日报系统中近10次提交的项目组信息
			List userWorkList = iWorkDayService.myWorkView(null, null, "8", "2");
			request.setAttribute("userWorkList", userWorkList) ;
			
			//根据员工的信息，查询员工对应的项目组信息
			List groupList = iWorkDayService.findGroupQuery(sqUserTabTemp);
			request.setAttribute("groupList", groupList) ;
			
			if(sqWorkdayManager != null && !sqWorkdayManager.getWeekDate().equals("")){
				StringBuffer hql = new StringBuffer();
				String [] paramNames = new String[2];
				Object[] values = new Object[2] ;
				hql.append("from SqWorkdayManager sqWorkdayManager where sqWorkdayManager.type ='2' ") ;
				//周报周期
				hql.append("and sqWorkdayManager.weekDate = :weekDate ") ;
				paramNames[0] = "weekDate" ;
				values[0] = sqWorkdayManager.getWeekDate() ;
				//项目组信息
				hql.append("and sqWorkdayManager.sqGroupTab.groupNo = :groupNo ") ;
				paramNames[1] = "groupNo" ;
				values[1] = sqWorkdayManager.getSqGroupTab().getGroupNo() ;
				hql.append(" order by sqWorkdayManager.workdayId asc");
				
				this.executeHql(hql.toString(), paramNames, values, Integer.parseInt(this.getCurrentStr())) ;
				
				//将得到的数据进行解析
				try {
					if(this.getListData().size() >=1){
						SqWorkdayManager temp = (SqWorkdayManager)this.getListData().get(this.getListData().size()-1);
						//加载员工信息
						SqUserTab sqUserTab = iUserService.findById(temp.getSqUserTab().getUserId());
						request.setAttribute("sqUserTab", sqUserTab);
						//加载项目组信息
//						SqGroupTab sqGroupTab = iGroupService.findById(sqUserTab.getProjectGroup());
						//项目组取传过来的项目组信息
						request.setAttribute("sqGroupTab", iGroupService.findById(sqWorkdayManager.getSqGroupTab().getGroupNo()));
						
						//查询周例会信息
						SqDocmentManager sqDocmentManager = new SqDocmentManager();
						sqDocmentManager.setRemark1(sqWorkdayManager.getSqGroupTab().getGroupNo() + sqWorkdayManager.getWeekDate());
						sqDocmentManager = iProjectDocService.findDocManagerToRemark1(sqDocmentManager);
						request.setAttribute("sqDocmentManager", sqDocmentManager);
					}
					
				} catch (SystemException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			request.setAttribute("workViewList", this.getListData()) ;
			
		} catch (SystemException e) {
			e.printStackTrace();
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		
		if(Public.isEmpty(returnPage))
			returnPage = "workdaygrouplist" ;
		return returnPage;
	}
	
	/**
	 * 部门周报预览
	 * @return
	 */
	public String deptWorkView(){
		try {
			//加载项目组信息
			List groupList = iGroupService.findGroupBydeptNo(sqUserTabTemp.getSqDeptTab()) ;
			request.setAttribute("groupList", groupList);
			
			Map<SqGroupTab, List> deptWorkMap = new TreeMap<SqGroupTab, List>();
			for (int i = 0; i < groupList.size(); i++)
			{
				SqGroupTab sqGroupTabTemp = (SqGroupTab)groupList.get(i);
				//查询项目组下所有的项目日报
				List workDayList = iWorkDayService.deptWorkDayList(sqGroupTabTemp , null , "1") ;
				deptWorkMap.put(sqGroupTabTemp, workDayList);
			}
			request.setAttribute("sqDeptTab", sqUserTabTemp.getSqDeptTab()) ;
			request.setAttribute("sqUserTab", sqUserTabTemp) ;
			request.setAttribute("deptWorkMap", deptWorkMap);
		} catch (SystemException e) {
			e.printStackTrace();
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "deptworkview";
	}
	/**
	 * 删除部门周报
	 * @return
	 */
	public String myworkDeptEditDelete(){
		try {
			String workdayId = request.getParameter("workdayId");
			iWorkDayService.myworkDayEditDelete(workdayId , null );
		} catch (SystemException e) {
			e.printStackTrace();
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "myworkdepteditdelete";
	}
	
	/**
	 * 提交本周部门周报
	 * @return
	 */
	public String deptWorkSub(){
		try {
			iWorkDayService.myWorkSub(sqUserTabTemp , "3");
		} catch (SystemException e) {
			e.printStackTrace();
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "deptworksub";
	}
	
	/**
	 * 部门周报提交
	 * @return
	 */
	public String workDayDeptAdd(){
		try {
			sqWorkdayManager = iWorkDayService.myworkDeptEditAdd(sqUserTabTemp,sqWorkdayManager);
			if(fileName != null){
				//部门周报的时候填写项目组信息
				sqDocmentManager.setProjectId(sqWorkdayManager.getSqGroupTab().getGroupNo());
				
				if(fileNameFileName!=null){
					this.projectDocUpload(sqDocmentManager);
					//保存上传的文件信息
					SqUserTab sqUserTabTemp = (SqUserTab) session.getAttribute("sqUserTab");
					sqDocmentManager.setStepId(sqWorkdayManager.getWorkdayId());
					sqDocmentManager.setUserId(sqUserTabTemp.getUserId());
					sqDocmentManager.setFilePath(directory) ;
					sqDocmentManager.setBackfileName(fileNameFileName);
					sqDocmentManager.setFileName(targetFileName) ;
					iProjectDocService.saveDocManager(sqDocmentManager );
				}
			}
		} catch (SystemException e) {
			e.printStackTrace();
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "workdaydepteditadd" ;
	}
	
	/**
	 * 修改部门周报时的查询
	 * @return
	 */
	public String myworkDeptEditUpdateFind(){
		try {
			//根据日报主键查询日报内容
			SqGroupTab sqGroupTab = sqWorkdayManager.getSqGroupTab();
			List workDayList  = iWorkDayService.deptWorkDayList(sqGroupTab , null , "1") ;
			if(workDayList.size() >0)
				sqWorkdayManager = (SqWorkdayManager)workDayList.get(0);
			else{
				sqWorkdayManager = new SqWorkdayManager() ;
				sqWorkdayManager.setSqGroupTab(sqGroupTab);
			}
			
		} catch (SystemException e) {
			e.printStackTrace();
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "myworkdepteditupdatefind";
	}
	
	/**
	 * 修改部门周报
	 * @return
	 */
	public String myworkDeptEdit(){
		try {
			List groupList = iGroupService.findGroupBydeptNo(sqUserTabTemp.getSqDeptTab()) ;
			if(groupList.size() == 0)
				throw new SystemException(WorkDayAction.class ,"您所在的部门没有项目组，不能编写部门周报。");
			
			request.setAttribute("groupList", groupList);
			SqGroupTab sqGroupTab = null ;
			//根据日报主键查询日报内容
			if(sqWorkdayManager == null){
				sqWorkdayManager = new SqWorkdayManager();
				sqGroupTab = (SqGroupTab)groupList.get(0) ;
			}else{
				sqGroupTab = sqWorkdayManager.getSqGroupTab();
			}
			
			//根据日报主键查询日报内容
			List workDayList  = iWorkDayService.deptWorkDayList(sqGroupTab , null , "1") ;
			if(workDayList.size() >0){
				this.sqWorkdayManager = (SqWorkdayManager)workDayList.get(0);
			}else{
				this.sqWorkdayManager = new SqWorkdayManager();
				this.sqWorkdayManager.setSqGroupTab(sqGroupTab);
			}
			
			sqWorkdayManager.setWorkCycle(iSysparaTab.findById("99999"));
			if(sqWorkdayManager.getWorkStartDate() == null ){
				sqWorkdayManager.setWorkStartDate(Public.getTimeToFormat(Public.weekForDay("yyyy-MM-dd", Calendar.MONDAY),"yyyy-MM-dd"));
			}
			if(sqWorkdayManager.getWorkEndDate() == null )
			sqWorkdayManager.setWorkEndDate(Public.getSystemTimeToFormat("yyyy-MM-dd"));
			sqWorkdayManager.setComplePercen(100);
			request.setAttribute("sqWorkdayManager", this.sqWorkdayManager) ;

			List sqWorkdayManagerList = iWorkDayService.myWorkView(sqUserTabTemp, null, null, "3");
			
			request.setAttribute("sqWorkdayManagerList", sqWorkdayManagerList) ;
		} catch (SystemException e) {
			e.printStackTrace();
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "myworkdeptedit";
	}
	/**
	 * 部门周报更新
	 * @return
	 */
	public String myworkDeptEditUpdate(){
		try {
			if(fileName != null){
				//部门周报的时候填写项目组信息
				sqDocmentManager.setProjectId(sqWorkdayManager.getSqGroupTab().getGroupNo());
				
				if(fileNameFileName!=null){
					this.projectDocUpload(sqDocmentManager);
					//保存上传的文件信息
					SqUserTab sqUserTabTemp = (SqUserTab) session.getAttribute("sqUserTab");
					sqDocmentManager.setStepId(sqWorkdayManager.getWorkdayId());
					sqDocmentManager.setUserId(sqUserTabTemp.getUserId());
					sqDocmentManager.setFilePath(directory) ;
					sqDocmentManager.setBackfileName(fileNameFileName);
					sqDocmentManager.setFileName(targetFileName) ;
					iProjectDocService.saveDocManager(sqDocmentManager );
				}
			}
			iWorkDayService.myworkDayEditUpdate(sqWorkdayManager ,sqUserTabTemp , "1");
		} catch (SystemException e) {
			e.printStackTrace();
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "myworkdepteditupdate" ;
	}
	
	/**
	 * 部门周报查询
	 * @return
	 */
	public String workDayDeptList() {
		String returnPage = request.getParameter("returnPage");
		try {
			//查询日报系统中近10次提交的项目组信息
			List userWorkList = iWorkDayService.myWorkView(null, null, "8", "3");
			request.setAttribute("userWorkList", userWorkList) ;
			
			//加载员工所属的部门信息
			List deptList = iDeptService.findUserByDept(sqUserTabTemp);
			request.setAttribute("deptList", deptList) ;
			
			Map<SqGroupTab, List> deptWorkMap = new TreeMap<SqGroupTab, List>();
			if(sqWorkdayManager != null && !sqWorkdayManager.getWeekDate().equals("")){
				//加载项目组信息
				List groupList = iGroupService.findGroupBydeptNo(sqDeptTab) ;
				request.setAttribute("groupList", groupList);
				for (int i = 0; i < groupList.size(); i++)
				{
					SqGroupTab sqGroupTabTemp = (SqGroupTab)groupList.get(i);
					//查询项目组下所有的项目日报
					List workDayList = iWorkDayService.deptWorkDayList(sqGroupTabTemp , sqWorkdayManager , "2") ;
					deptWorkMap.put(sqGroupTabTemp, workDayList);
				}
				request.setAttribute("sqDeptTab", iDeptService.findById(sqDeptTab.getDeptNo())) ;
			}
			request.setAttribute("sqUserTab", sqUserTabTemp) ;
			request.setAttribute("deptWorkMap", deptWorkMap);
			
		} catch (SystemException e) {
			e.printStackTrace();
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		if(Public.isEmpty(returnPage))
			returnPage = "workdaydeptlist";
			
		return returnPage;
	}
	
	/**
	 * 生成部门周报查询
	 * @return
	 */
	public String growthDeptList() {
		try {
			Map<SqGroupTab,List<SqWorkdayManager>> workDayMap = new TreeMap<SqGroupTab,List<SqWorkdayManager>>();
			//查询所有的项目组
			List groupList = iGroupService.findGroupBydeptNo(sqUserTabTemp.getSqDeptTab());
			for (int i = 0; i < groupList.size(); i++) {
				SqGroupTab sqGroupTab = (SqGroupTab)groupList.get(i);
				List workViewList = iWorkDayService.deptWorkDayList(sqGroupTab, null ,"3");
				//如果项目组下没有对应的周报，则不写进去
				if(workViewList.size() > 0)
				workDayMap.put(sqGroupTab, workViewList);
			}
			request.setAttribute("workDayMap", workDayMap) ;
			
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "growthdeptlist";
	}
	
	/**
	 * 将项目周报、项目组周报、部门周报全部导出
	 * @return
	 */
	public String growthList(){
		String returnPage = request.getParameter("returnPage") ;
		ApplicationDate.getRequestData("SYS_PARA_DATA" , Arrays.asList("sq_project_stepwork_units") , request , "sq_project_stepwork_units" );
		Map workUnitsMap = (Map)request.getAttribute("sq_project_stepwork_units");
		try {
			if(returnPage.equals("growthdeptlist")){
				//部门周报全部导出
				//查询所有的项目组
				List groupList = iGroupService.findGroupBydeptNo(sqUserTabTemp.getSqDeptTab());
				for (int i = 0; i < groupList.size(); i++) {
					SqGroupTab sqGroupTab = (SqGroupTab)groupList.get(i);
					boolean flag = false ;
					for (int j = 0; j < workData.length; j++) {
						String [] workDayTemp = workData[j].split("\\|");
						if(sqGroupTab.getGroupNo().equals(workDayTemp[1])){
							flag = true ;
							break ;
						}
					}
					
					if(!flag)
						continue ;
					List workViewList = iWorkDayService.deptWorkDayList(sqGroupTab, null ,"3");
					
					SqWorkdayManager sqWorkdayManagerTemp = new SqWorkdayManager ();
		      		StringBuffer strBuffWorkContent = new StringBuffer();
		      		StringBuffer strBuffDiscussProblem = new StringBuffer();
		      		StringBuffer strBuffWorkSug = new StringBuffer();
		      		for (int j = 0; j < workViewList.size(); j++) {
		      			SqWorkdayManager temp = (SqWorkdayManager)workViewList.get(j);
		      			strBuffWorkContent.append(temp.getSqProjectInfo().getProjectName() + "    当前阶段："+temp.getSqProjectStep().getStepName()+"\r\n");
		      			strBuffWorkContent.append(temp.getWorkContent()+"\r\n");
		      			
		      			if(temp.getDiscussProblem() !=null && !temp.getDiscussProblem().equals("")){
		      				strBuffDiscussProblem.append(temp.getDiscussProblem()+"\r\n");
		      			}
		      			if(temp.getWorkSug() !=null && !temp.getWorkSug().equals("")){
		      				strBuffWorkSug.append(temp.getWorkSug()+"\r\n");
		      			}
		      			
					}
		      		//调用保存
		      		sqWorkdayManagerTemp.setWorkDate(Public.getStringToDate(null, "yyyy-MM-dd"));
		      		sqWorkdayManagerTemp.setWorkTime(Public.getStringToDate(null, "HHmmss"));
		      		sqWorkdayManagerTemp.setTaskTime(Float.parseFloat("0"));	//任务时长
		      		sqWorkdayManagerTemp.setWorkLoad("||||");	//工作成果
		      		sqWorkdayManagerTemp.setWorkUnits("0|0|0|0|");	//工作单元
		      		sqWorkdayManagerTemp.setComplePercen(100);	//完成比例
		      		sqWorkdayManagerTemp.setTaskStatus("9");	//任务状态
		      		sqWorkdayManagerTemp.setSqGroupTab(sqGroupTab);	//项目组
		      		sqWorkdayManagerTemp.setWorkContent(strBuffWorkContent.toString());
		      		sqWorkdayManagerTemp.setNoWorkContent("");
		      		sqWorkdayManagerTemp.setWorkNextPlan("");
		      		sqWorkdayManagerTemp.setDiscussProblem(strBuffDiscussProblem.toString());
		      		sqWorkdayManagerTemp.setWorkSug(strBuffWorkSug.toString());
		      		sqWorkdayManagerTemp.setType("3");
		      		sqWorkdayManagerTemp.setWorkStartDate(Public.getTimeToFormat(Public.weekForDay("yyyy-MM-dd",Calendar.MONDAY),"yyyy-MM-dd"));
		      		sqWorkdayManagerTemp.setWorkEndDate(Public.getSystemTimeToFormat("yyyy-MM-dd"));
		      		sqWorkdayManagerTemp.setSqUserTab(sqUserTabTemp);
		      		sqWorkdayManagerTemp.setWorkCycle(iSysparaTab.findById("99999"));
		      		iWorkDayService.myworkDeptEditAdd(sqUserTabTemp,sqWorkdayManagerTemp);
					
				}
				
			}else if(returnPage.equals("growthgrouplist")){
				//项目组周报全部导出
				for (int i = 0; i < workData.length; i++) {
					String [] workDayTemp = workData[i].split("\\|");
					//根据日报主键查询日报内容
					SqWorkdayManager sqWorkDayTemp = new SqWorkdayManager();
					sqWorkDayTemp.setWorkdayId(workDayTemp[0]);
					SqProjectInfo sqProjectInfo = new SqProjectInfo();
					sqProjectInfo.setProjectId(workDayTemp[1]);
					sqWorkDayTemp.setSqProjectInfo(sqProjectInfo);
					sqWorkdayManager = iWorkDayService.findById(sqWorkDayTemp);
					//调用保存
					sqWorkdayManager.setWorkTime(Public.getStringToDate(null, "HHmmss"));
					sqWorkdayManager.setType("2");
					sqWorkdayManager.setSqGroupTab(sqUserTabTemp.getSqGroupTab());
					sqWorkdayManager.setSqUserTab(sqUserTabTemp);
					sqWorkdayManager.setWorkStartDate(Public.getTimeToFormat(Public.weekForDay("yyyy-MM-dd",Calendar.MONDAY),"yyyy-MM-dd"));
					sqWorkdayManager.setWorkEndDate(Public.getSystemTimeToFormat("yyyy-MM-dd"));
					sqWorkdayManager.setWorkCycle(iSysparaTab.findById("99999"));
					sqWorkdayManager.setComplePercen(100);	//完成比例
					iWorkDayService.myworkGroupEditAdd(sqUserTabTemp,sqWorkdayManager);
				}
				
			}else if(returnPage.equals("growthprolist")){
				//项目周报全部导出
				Map<SqProjectInfo, List<SqWorkdayManager>> workDayMap =  iWorkDayService.auditWorkDay(sqUserTabTemp) ;
				Iterator ite = workDayMap.entrySet().iterator();
		      	while(ite.hasNext()){
		      		Map.Entry<SqProjectInfo, List<SqWorkdayManager>> entry = (Map.Entry<SqProjectInfo, List<SqWorkdayManager>>) ite.next();
		      		SqProjectInfo sqProjectInfo = entry.getKey();//map中的key
		      		boolean flag = false ;
		      		for (int i = 0; i < workData.length; i++) {
						String [] workDayTemp = workData[i].split("\\|");
						if(sqProjectInfo.getProjectId().equals(workDayTemp[1])){
							flag = true ;
						}
		      		}
					//如果查询不到，则继续
		      		if(!flag)
		      			continue ;
					SqWorkdayManager sqWorkdayManagerTemp = new SqWorkdayManager ();
					sqWorkdayManagerTemp.setSqProjectInfo(sqProjectInfo);
		      		List<SqWorkdayManager> sqWorkdayManagerList = entry.getValue();//上面key对应的value
		      		StringBuffer strBuffWorkContent = new StringBuffer();
		      		StringBuffer strBuffNoWorkContent = new StringBuffer();
		      		StringBuffer strBuffWorkNextPlan = new StringBuffer();
		      		StringBuffer strBuffDiscussProblem = new StringBuffer();
		      		StringBuffer strBuffWorkSug = new StringBuffer();
		      		for (int i = 0; i < sqWorkdayManagerList.size(); i++) {
		      			SqWorkdayManager temp = sqWorkdayManagerList.get(i);
//		      			strBuffWorkContent.append(temp.getSqUserTab().getUserName() + "：  "+Public.getTimeToFormat(temp.getWorkDate() , "yyyy-MM-dd")+"    项目阶段：" + temp.getSqProjectStep().getStepName()+"\r\n");
		      			strBuffWorkContent.append(temp.getWorkContent()+"\r\n");
		      			
		      			if(temp.getNoWorkContent() !=null && !temp.getNoWorkContent().equals("")){
		      				strBuffNoWorkContent.append(temp.getNoWorkContent()+"\r\n");
		      			}
		      			if(temp.getWorkNextPlan() !=null && !temp.getWorkNextPlan().equals("")){
		      				strBuffWorkNextPlan.append(temp.getWorkNextPlan()+"\r\n");
		      			}
		      			if(temp.getDiscussProblem() !=null && !temp.getDiscussProblem().equals("")){
		      				strBuffDiscussProblem.append(temp.getDiscussProblem()+"\r\n");
		      			}
		      			if(temp.getWorkSug() !=null && !temp.getWorkSug().equals("")){
		      				strBuffWorkSug.append(temp.getWorkSug()+"\r\n");
		      			}
		      			
					}
		      		//调用保存
		      		sqWorkdayManagerTemp.setWorkDate(Public.getStringToDate(null, "yyyy-MM-dd"));
		      		sqWorkdayManagerTemp.setWorkStartDate(Public.getTimeToFormat(Public.weekForDay("yyyy-MM-dd",Calendar.MONDAY),"yyyy-MM-dd"));
		      		sqWorkdayManagerTemp.setWorkEndDate(Public.getSystemTimeToFormat("yyyy-MM-dd"));
		      		sqWorkdayManagerTemp.setSqProjectStep(sqProjectInfo.getCurrProjectStep());
		      		sqWorkdayManagerTemp.setWorkTime(Public.getStringToDate(null, "HHmmss"));
		      		sqWorkdayManagerTemp.setTaskTime(Float.parseFloat("0"));	//任务时长
		      		sqWorkdayManagerTemp.setWorkLoad("||||");	//工作成果
		      		sqWorkdayManagerTemp.setWorkUnits("0|0|0|0|");	//工作单元
		      		sqWorkdayManagerTemp.setComplePercen(100);	//完成比例
		      		sqWorkdayManagerTemp.setTaskStatus("0");	//任务状态
		      		sqWorkdayManagerTemp.setSqGroupTab(sqUserTabTemp.getSqGroupTab());	//项目组
		      		sqWorkdayManagerTemp.setWorkContent(strBuffWorkContent.toString());
		      		sqWorkdayManagerTemp.setNoWorkContent(strBuffNoWorkContent.toString());
		      		sqWorkdayManagerTemp.setWorkNextPlan(strBuffWorkNextPlan.toString());
		      		sqWorkdayManagerTemp.setDiscussProblem(strBuffDiscussProblem.toString());
		      		sqWorkdayManagerTemp.setWorkSug(strBuffWorkSug.toString());
		      		sqWorkdayManagerTemp.setType("1");
		      		sqWorkdayManagerTemp.setSqGroupTab(sqUserTabTemp.getSqGroupTab());
		      		sqWorkdayManagerTemp.setSqUserTab(sqUserTabTemp);
		      		sqWorkdayManagerTemp.setWorkCycle(iSysparaTab.findById("99999"));
					iWorkDayService.myworkProjectEditAdd(sqUserTabTemp,sqWorkdayManagerTemp);
					
		      	}
		      	
			}
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "success" ;
	}
	
	public String isUserSubWork(){
		String type = request.getParameter("type");
		String weekDate = iSysparaTab.findById("99999");
		this.ajaxStr = iWorkDayService.isUserSubWork(sqUserTabTemp, type, weekDate);
		return "ajax";
	}
	public SqWorkdayManager getSqWorkdayManager() {
		return sqWorkdayManager;
	}

	public void setSqWorkdayManager(SqWorkdayManager sqWorkdayManager) {
		this.sqWorkdayManager = sqWorkdayManager;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getTaskQu() {
		return taskQu;
	}
	
	public void setTaskQu(String taskQu) {
		this.taskQu = taskQu;
	}

	public String getComplePercenQu() {
		return complePercenQu;
	}

	public void setComplePercenQu(String complePercenQu) {
		this.complePercenQu = complePercenQu;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	public ErrorForm getErrorForm() {
		return errorForm;
	}
	public void setErrorForm(ErrorForm errorForm) {
		this.errorForm = errorForm;
	}
	public SqGroupTab getSqGroupTab() {
		return sqGroupTab;
	}
	public void setSqGroupTab(SqGroupTab sqGroupTab) {
		this.sqGroupTab = sqGroupTab;
	}
	public String[] getWorkData() {
		return workData;
	}
	public void setWorkData(String[] workData) {
		this.workData = workData;
	}
	public SqDocmentManager getSqDocmentManager() {
		return sqDocmentManager;
	}
	public void setSqDocmentManager(SqDocmentManager sqDocmentManager) {
		this.sqDocmentManager = sqDocmentManager;
	}
	public SqUserTab getSqUserTab() {
		return sqUserTab;
	}
	public void setSqUserTab(SqUserTab sqUserTab) {
		this.sqUserTab = sqUserTab;
	}
	public SqDeptTab getSqDeptTab() {
		return sqDeptTab;
	}
	public void setSqDeptTab(SqDeptTab sqDeptTab) {
		this.sqDeptTab = sqDeptTab;
	}
	public String getAjaxStr() {
		return ajaxStr;
	}
	public void setAjaxStr(String ajaxStr) {
		this.ajaxStr = ajaxStr;
	}
	public String getWeekType() {
		return weekType;
	}
	public void setWeekType(String weekType) {
		this.weekType = weekType;
	}
	public List getTempList() {
		return tempList;
	}
	public void setTempList(List tempList) {
		this.tempList = tempList;
	}
	public SqProjectInfo getSqProjectInfo() {
		return sqProjectInfo;
	}
	public void setSqProjectInfo(SqProjectInfo sqProjectInfo) {
		this.sqProjectInfo = sqProjectInfo;
	}
	
}
