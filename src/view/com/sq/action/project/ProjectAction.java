package com.sq.action.project;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.sq.exception.SystemException;
import com.sq.logic.group.IGroupService;
import com.sq.logic.project.IProjectService;
import com.sq.logic.project.impl.ProjectService;
import com.sq.logic.tools.IUserLog;
import com.sq.logic.user.IUserService;
import com.sq.logic.workday.IWorkDayService;
import com.sq.model.vo.SqDocmentManager;
import com.sq.model.vo.SqProjectInfo;
import com.sq.model.vo.SqProjectStep;
import com.sq.model.vo.SqProjectUser;
import com.sq.model.vo.SqUserTab;
import com.sq.tools.Public;
import com.sq.vo.ErrorForm;

public class ProjectAction {

	@Resource
	private IProjectService iProjectService;
	@Resource
	private IUserLog iUserLog;
	@Resource
	private IUserService iUserService;
	@Resource
	private IGroupService iGroupService;
	@Resource
	private IWorkDayService iWorkDayService;
	private SqProjectInfo sqProjectInfo;
	private SqProjectUser sqProjectUser;
	private SqProjectStep sqProjectStep;
	private SqDocmentManager sqDocmentManager;
	private List<SqProjectStep> projectStepList = new ArrayList<SqProjectStep>();
	private List<SqDocmentManager> projectDocList = new ArrayList<SqDocmentManager>();
	private ErrorForm errorForm = new ErrorForm();
	private HttpServletRequest request = ServletActionContext.getRequest();
	private HttpSession session = request.getSession(false);
	SqUserTab sqUserTabTemp = (SqUserTab) session.getAttribute("sqUserTab");

	/**
	 * 项目信息保存
	 * 
	 * @return
	 */
	public String saveProject() {
		try {
			//得到所有的管理组
			String mgnuser = request.getParameter("mgnuser");
			//得到所有的成员组
			String memuser = request.getParameter("memuser");
			
			iProjectService.saveProject(sqProjectInfo , mgnuser , memuser);
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "success";
	}

	/**
	 * 新增项目的时候查询所有的员工
	 * @return
	 */
	public String listUser() {
		List list = iUserService.findByAll() ;
		request.setAttribute("listUser", list) ;
		
		//查找项目组信息
		List groupList = iGroupService.findByAll() ;
		request.setAttribute("grouplist", groupList) ;
		
		return "projectfinduser" ;
	}
	
	/**
	 * 我的项目列表
	 * 
	 * @return
	 */
	public String myProjectList() {
		List myprojectlist = null;
		try {
			myprojectlist = iProjectService.findByAll(sqUserTabTemp, "1");
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		request.setAttribute("myprojectlist", myprojectlist);
		return "myprojectlist";
	}

	/**
	 * 项目信息更新
	 * 
	 * @return
	 */
	public String updateProject() {
		try {
			//得到所有的管理组
			String mgnuser = request.getParameter("mgnuser");
			//得到所有的成员组
			String memuser = request.getParameter("memuser");
			iProjectService.updateProject(sqProjectInfo , mgnuser , memuser);
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "success";
	}

	/**
	 * 项目员工信息主页面
	 * 
	 * @return
	 */
	public String projectAddUserInfo() {
		try {
			sqProjectInfo = iProjectService.findById(sqProjectInfo,"0");
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "projectadduserinfo";
	}

	/**
	 * 项目员工新增
	 * 
	 * @return
	 */
	public String projectAddUser() {
		try {
			iProjectService.projectAddUser(sqProjectUser);
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "projectadduserinfo";
	}

	/**
	 * 项目员工修改
	 * 
	 * @return
	 */
	public String projectUpdateUser() {
		try {
			iProjectService.projectUpdateUser(sqProjectUser);
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "projectadduserinfo";
	}

	/**
	 * 项目员工查询
	 * 
	 * @return
	 */
	public String projectAddUserList() {
		try {
			sqProjectInfo = iProjectService.findById(sqProjectInfo,"0");
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "projectadduserinfo";
	}

	/**
	 * 项目信息阶段主页面
	 * 
	 * @return
	 */
	public String projectAddStepInfo() {
		try {
			// 查询指定的项目阶段
			sqProjectInfo = iProjectService.findById(sqProjectInfo,"0");
			//检查修改的员工信息必须为项目的项目经理或者管理组才能修改
			List userTab = sqProjectInfo.getSqProjectUserList();
			boolean flag = false ;
			for (int i = 0; i < userTab.size(); i++) {
				SqProjectUser sqProjectUser = (SqProjectUser)userTab.get(i);
				if(sqProjectUser.getId().getUserId().equals(sqUserTabTemp.getUserId())&&(sqProjectUser.getId().getUserType().equals("1")||sqProjectUser.getId().getUserType().equals("2"))){
					flag = true ;
					break ;
				}
			}
			if(!flag)
				throw new SystemException(ProjectAction.class ,"只有管理组和项目经理才能修改。");
			
			// 查询项目阶段内容
			sqProjectStep = new SqProjectStep();
			sqProjectStep.setProjectId(sqProjectInfo.getProjectId());
			projectStepList = iProjectService.findProjectStepById(sqProjectStep);

		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "projectaddstepinfo";
	}

	/**
	 * 项目信息阶段新增
	 * 
	 * @return
	 */
	public String projectAddStep() {
		try {
			iProjectService.projectAddStep(sqProjectStep);
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "projectaddstepinfo";
	}

	/**
	 * 项目阶段修改
	 * 
	 * @return
	 */
	public String projectUpdateStep() {
		try {
			iProjectService.projectUpdateStep(sqProjectStep);
			// 查询项目阶段内容
			projectStepList = iProjectService.findProjectStepById(sqProjectStep);
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "projectaddstepinfo";
	}

	/**
	 * 项目阶段删除
	 * 
	 * @return
	 */
	public String projectDeleteStep() {
		try {
			iProjectService.projectDeleteStep(sqProjectStep);
			// 查询项目阶段内容
			projectStepList = iProjectService.findProjectStepById(sqProjectStep);
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "projectdeletestep";
	}

	/**
	 * 项目信息阶段查询（详情显示）
	 * 
	 * @return
	 */
	public String projectStepfFindInfo() {
		try {
			// 查询指定的项目阶段
			sqProjectInfo = iProjectService.findById(sqProjectInfo,"0");
			// 查询项目阶段内容
			sqProjectStep = new SqProjectStep();
			sqProjectStep.setProjectId(sqProjectInfo.getProjectId());
			projectStepList = iProjectService.findProjectStepById(sqProjectStep);

		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "projectstepfindinfo";
	}

	/**
	 * 根据项目编号查询项目信息
	 * 
	 * @return
	 */
	public String findUpdateId() {
		try {
			sqProjectInfo = iProjectService.findById(sqProjectInfo,"0");
			//检查修改的员工信息必须为项目的项目经理或者管理组才能修改
			List userTab = sqProjectInfo.getSqProjectUserList();
			boolean flag = false ;
			for (int i = 0; i < userTab.size(); i++) {
				SqProjectUser sqProjectUser = (SqProjectUser)userTab.get(i);
				if(sqProjectUser.getId().getUserId().equals(sqUserTabTemp.getUserId())&&(sqProjectUser.getId().getUserType().equals("1")||sqProjectUser.getId().getUserType().equals("2"))){
					flag = true ;
					break ;
				}
			}
			if(!flag)
				throw new SystemException(ProjectAction.class ,"只有项目经理和管理组才能修改。");
			
			//加载所有的员工信息
			List list = iUserService.findByAll() ;
			request.setAttribute("listUser", list) ;
			
			//查找项目组信息
			List groupList = iGroupService.findByAll() ;
			request.setAttribute("grouplist", groupList) ;
			
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "myprojectlist";
	}

	/**
	 * 根据项目编号查询项目阶段下的所有文档
	 * 
	 * @return
	 */
	public String projectDocFindInfo() {
		try {
			SqProjectInfo sqProjectInfo = new SqProjectInfo();
			sqProjectInfo.setProjectId(sqDocmentManager.getProjectId());
			sqProjectInfo = iProjectService.findById(sqProjectInfo, "1");
			request.setAttribute("sqProjectInfo", sqProjectInfo);
			List weekDayList = new ArrayList();
			
			//查询提交的周报中最近5次的周会周期
			weekDayList = iWorkDayService.myWorkView(null, null, "8", "0").subList(0, 5);
//			if(Public.dayForWeek(Calendar.WEEK_OF_YEAR) >= 5){
//				for (int i = Public.dayForWeek(Calendar.WEEK_OF_YEAR); i > Public.dayForWeek(Calendar.WEEK_OF_YEAR)-5; i--) {
//					String temp = i + "";
//					if(temp.length() ==1) temp = "0" + temp ;
//					weekDayList.add(Public.getSystemTimeToFormat("yyyy年")+"第"+temp+"周(" + Public.getTimeToFormat(Public.getDateToAddRed(null, "yyyy-MM-dd", (Public.dayForWeek(Calendar.WEEK_OF_YEAR)-i)*7),"MM月") +")");
//				}
//			}else{
//				for (int i = Public.dayForWeek(Calendar.WEEK_OF_YEAR); i > 0; i--) {
//					weekDayList.add(Public.getSystemTimeToFormat("yyyy年")+"第0"+i+"周(" +Public.getSystemTimeToFormat("MM月") +")");
//				}
//				for (int i = 5; i > Public.dayForWeek(Calendar.WEEK_OF_YEAR); i--) {
//					weekDayList.add(Integer.parseInt(Public.getSystemTimeToFormat("yyyy"))-1+"年第0"+i+"周(12月)");
//				}
//			}
			
			request.setAttribute("weekDayList", weekDayList);
			
			projectDocList = iProjectService.projectDocFindInfo(sqDocmentManager);
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "projectdocfindinfo";
	}

	/**
	 * 查询所有项目的项目经理
	 * @return
	 */
	public String projectAllUser(){
		try {
			List projectAllList = iProjectService.findByAll(null, "All");
			request.setAttribute("projectAllList", projectAllList) ;
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "myprojecthistorylist" ;
	}
	public SqProjectInfo getSqProjectInfo() {
		return sqProjectInfo;
	}

	public void setSqProjectInfo(SqProjectInfo sqProjectInfo) {
		this.sqProjectInfo = sqProjectInfo;
	}

	public SqProjectUser getSqProjectUser() {
		return sqProjectUser;
	}

	public void setSqProjectUser(SqProjectUser sqProjectUser) {
		this.sqProjectUser = sqProjectUser;
	}

	public SqProjectStep getSqProjectStep() {
		return sqProjectStep;
	}

	public void setSqProjectStep(SqProjectStep sqProjectStep) {
		this.sqProjectStep = sqProjectStep;
	}

	public List<SqProjectStep> getProjectStepList() {
		return projectStepList;
	}

	public void setProjectStepList(List<SqProjectStep> projectStepList) {
		this.projectStepList = projectStepList;
	}

	public ErrorForm getErrorForm() {
		return errorForm;
	}

	public void setErrorForm(ErrorForm errorForm) {
		this.errorForm = errorForm;
	}

	public SqDocmentManager getSqDocmentManager() {
		return sqDocmentManager;
	}

	public void setSqDocmentManager(SqDocmentManager sqDocmentManager) {
		this.sqDocmentManager = sqDocmentManager;
	}

	public List<SqDocmentManager> getProjectDocList() {
		return projectDocList;
	}

	public void setProjectDocList(List<SqDocmentManager> projectDocList) {
		this.projectDocList = projectDocList;
	}

}
