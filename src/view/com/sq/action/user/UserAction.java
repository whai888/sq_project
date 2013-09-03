package com.sq.action.user;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.sq.exception.SystemException;
import com.sq.logic.group.IGroupService;
import com.sq.logic.tools.IUserLog;
import com.sq.logic.user.IUserService;
import com.sq.logic.userrole.IUserRoleService;
import com.sq.model.vo.SqUserTab;
import com.sq.tools.Public;
import com.sq.tools.SQMD5;
import com.sq.vo.ErrorForm;
import com.sq.vo.LoginForm;

public class UserAction {
	@Resource
	private IUserService iUserService;
	@Resource
	private IUserLog iUserLog ;
	@Resource
	private IGroupService iGroupService;
	@Resource
	private IUserRoleService iUserRoleService;
	private SqUserTab sqUserTab;
	private LoginForm loginForm ;
	private List userTabList ;
	private ErrorForm errorForm = new ErrorForm() ;
	
	private HttpServletRequest request = ServletActionContext.getRequest();
	private HttpSession session = request.getSession(false);

	public String saveUser() {
		//新增员工的时候初始化一些数据
		sqUserTab.setAddDate(Public.getSystemTimeToFormat("yyyy-MM-dd HH:mm:ss"));
		sqUserTab.setModifyDate(Public.getSystemTimeToFormat("yyyy-MM-dd HH:mm:ss"));
		sqUserTab.setPwdError(0);
		sqUserTab.setUserType("1");
		sqUserTab.setPasswd(SQMD5.getEncryMD5("88888888"));
		sqUserTab.setStatus("3");
		try {
			iUserService.saveUser(sqUserTab);
			String [] treeData = new String[1];
			treeData[0] = "008" ;
			//为员工赋值为普通员工的权限和菜单
			iUserRoleService.userRoleConfirm(sqUserTab, treeData);
			
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error" ;
		}
		return "success";
	}
	
	/**
	 * 新增员工时的查询
	 * @return
	 */
	public String saveUserFind() {
		//查找项目组信息
		List list = iGroupService.findByAll() ;
		request.setAttribute("grouplist", list) ;
		return "useraddfind";
	}
	/**
	 * 员工信息更新
	 * @return
	 */
	public String updateUser() {
		try {
			SqUserTab sqUserTabTemp = iUserService.findById(sqUserTab.getUserId());
			sqUserTabTemp.setBirthday(sqUserTab.getBirthday());
			sqUserTabTemp.setEmail(sqUserTab.getEmail());
			sqUserTabTemp.setSqDeptTab(sqUserTab.getSqDeptTab());
			sqUserTabTemp.setSqGroupTab(sqUserTab.getSqGroupTab());
			sqUserTabTemp.setEnterYear(sqUserTab.getEnterYear());
			sqUserTabTemp.setSqJobTab(sqUserTab.getSqJobTab());
			sqUserTabTemp.setLevel(sqUserTab.getLevel());
			sqUserTabTemp.setPyName(sqUserTab.getPyName());
			sqUserTabTemp.setMobile(sqUserTab.getMobile());
			sqUserTabTemp.setSqOfficeTab(sqUserTab.getSqOfficeTab());
			sqUserTabTemp.setUserSex(sqUserTab.getUserSex());
			sqUserTabTemp.setUserName(sqUserTab.getUserName());
			sqUserTabTemp.setPractitionersYear(sqUserTab.getPractitionersYear());
			sqUserTabTemp.setRemark(sqUserTab.getRemark());
			if(sqUserTab.getStatus().equals("0")){
				//如果状态正常，需要更新员工的密码错误次数
				sqUserTabTemp.setPwdError(0);
			}
			sqUserTabTemp.setStatus(sqUserTab.getStatus());
			sqUserTabTemp.setModifyDate(Public.getSystemTimeToFormat("yyyy-MM-dd HH:mm:ss"));
			sqUserTabTemp.setIp(Public.getIpAddr(request));
			iUserService.updateUser(sqUserTabTemp);
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error" ;
		}
		return "success";
	}

	public String listUser() {
		List list = iUserService.findByAll() ;
		request.setAttribute("listUser", list) ;
		return "userlist" ;
	}
	
	public String findUpdateId() {
		try {
			sqUserTab = iUserService.findById(sqUserTab.getUserId()) ;
			request.setAttribute("sqUserTab", sqUserTab) ;
			//查找项目组信息
			List list = iGroupService.findByAll() ;
			request.setAttribute("grouplist", list) ;
			
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error" ;
		}
		return "userupdate" ;
	}
	
	/**
	 * 查询我的个人信息
	 * @return
	 */
	public String myInfo() {
		try {
			SqUserTab sqUserTabTemp = (SqUserTab)session.getAttribute("sqUserTab");
			sqUserTab = iUserService.findById(sqUserTabTemp.getUserId()) ;
			request.setAttribute("sqUserTab", sqUserTab) ;
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error" ;
		}
		return "myinfo" ;
	}
	
	/**
	 * 密码修改
	 * @return
	 */
	public String updatePwd() {
		try {
			loginForm.setIp(Public.getIpAddr(request));
			iUserService.updatePwd(loginForm) ;
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error" ;
		}
		//检查输入密码与原密码是否一致
		return "success" ;
	}
	
	/**
	 * 管理员做密码重置
	 * @return
	 */
	public String updatePwdReset(){
		SqUserTab sqUserTabTemp = (SqUserTab)session.getAttribute("sqUserTab");
		if(!sqUserTabTemp.getUserType().equals("0")){
			iUserLog.logwrite(request, new SystemException(UserAction.class, "非管理员不允许做密码重置。" ));
			errorForm.setMessage("非管理员不允许做密码重置。");
			return "error";
		}
		sqUserTab.setIp(Public.getIpAddr(request));
		try {
			iUserService.updatePwdReset(sqUserTab);
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error" ;
		}
		return "success";
	}
	
	/**
	 * 根据部门编号查询部门所有的员工
	 * @return
	 */
	public String userByDept(){
		this.userTabList = iUserService.userByDept(sqUserTab);
		return "userDetp";
	}
	
	public SqUserTab getSqUserTab() {
		return sqUserTab;
	}

	public void setSqUserTab(SqUserTab sqUserTab) {
		this.sqUserTab = sqUserTab;
	}

	public LoginForm getLoginForm() {
		return loginForm;
	}

	public void setLoginForm(LoginForm loginForm) {
		this.loginForm = loginForm;
	}

	public ErrorForm getErrorForm() {
		return errorForm;
	}

	public void setErrorForm(ErrorForm errorForm) {
		this.errorForm = errorForm;
	}

	public List getUserTabList() {
		return userTabList;
	}

	public void setUserTabList(List userTabList) {
		this.userTabList = userTabList;
	}
	
}
