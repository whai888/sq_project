package com.sq.logic.user.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.sq.exception.SystemException;
import com.sq.logic.dept.IDeptService;
import com.sq.logic.dept.impl.DeptService;
import com.sq.logic.system.impl.SystemService;
import com.sq.logic.user.IUserService;
import com.sq.model.vo.SqDeptTab;
import com.sq.model.vo.SqGroupTab;
import com.sq.model.vo.SqJobTab;
import com.sq.model.vo.SqUserTab;
import com.sq.service.IBaseDAO;
import com.sq.tools.Constant;
import com.sq.tools.Public;
import com.sq.tools.SQMD5;
import com.sq.vo.LoginForm;

@Transactional(rollbackFor=SystemException.class) //指定回滚,遇到异常SystemException时回滚
public class UserService implements IUserService {
	@Resource IBaseDAO iBaseDao ;
	@Resource
	private IDeptService iDeptService;
	private Logger log = Logger.getLogger(UserService.class);
	
	/**
	 * 查找所有的用户
	 */
	public List findByAll() {
		String hql = "From SqUserTab order by email";
		List userList = iBaseDao.find(hql) ;
		return userList ;
	}

	public List findByExample(SqUserTab sqUserTab){
		return iBaseDao.findByExample(sqUserTab);
	}
	/**
	 * 根据员工ID查询员工信息
	 * @throws SystemException 
	 */
	public SqUserTab findById(String userId) throws SystemException {
		SqUserTab sqUserTab = new SqUserTab();
		sqUserTab.setUserId(userId);
		sqUserTab = (SqUserTab)iBaseDao.findFirstVO(sqUserTab , "userId");
		if(sqUserTab == null ){
			throw new SystemException(UserService.class ,"查找员工信息失败" + sqUserTab.getUserId() );
		}
//		//加载员工岗位信息
//		SqJobTab sqJobTab = new SqJobTab();
//		sqJobTab.setJobId(sqUserTab.getJob());
//		sqUserTab.setSqJobTab((SqJobTab)iBaseDao.findFirstVO( sqJobTab , "jobId")) ;
		
//		//加载项目组信息
//		SqGroupTab sqGroupTab = new SqGroupTab();
//		sqGroupTab.setGroupNo(sqUserTab.getProjectGroup());
//		sqUserTab.setSqGroupTab((SqGroupTab)iBaseDao.findFirstVO( sqGroupTab , "groupNo"));
		
		return sqUserTab;
	}

	/**
	 * 保存员工信息
	 * @throws SystemException 
	 */
	public void saveUser(SqUserTab sqUserTab) throws SystemException {
		sqUserTab.setUserId(iBaseDao.sequenceToId(Constant.SEQUENCE_USER_INFO));
		sqUserTab.setSqDeptTab(iDeptService.findById(sqUserTab.getSqDeptTab().getDeptNo()));
		sqUserTab.setPyName(sqUserTab.getPyName().toUpperCase());
		iBaseDao.save(sqUserTab);
		
	}

	/**
	 * 修改员工信息
	 */
	public void updateUser(SqUserTab sqUserTab) {
//		sqUserTab.setSqDeptTab(iDeptService.findById(sqUserTab.getSqDeptTab().getDeptNo()));
		sqUserTab.setPyName(sqUserTab.getPyName().toUpperCase());
		iBaseDao.update(sqUserTab);
	}

	/**
	 * 修改密码
	 * @throws SystemException 
	 */
	public void updatePwd(LoginForm loginForm) throws SystemException {
		SqUserTab sqUserTab = this.findById(loginForm.getUserId()) ;
		//比较密码与原密码是否一致
		if(!sqUserTab.getPasswd().equals(SQMD5.getEncryMD5(loginForm.getPassword()))){
			throw new SystemException(UserService.class ,"原密码与输入密码不一致，请重新输入。");
		}
		sqUserTab.setPasswd(SQMD5.getEncryMD5(loginForm.getPwd()));
		sqUserTab.setModifyDate(Public.getSystemTimeToFormat("yyyy-MM-dd HH:mm:ss"));
		sqUserTab.setIp(loginForm.getIp());
		//只要修改密码，如果状态为3，就将用户状态修改为1
		if(sqUserTab.getStatus().equals("3")){
			sqUserTab.setStatus("0");
		}
		iBaseDao.update(sqUserTab);
	}
	
	/**
	 * 管理员做密码重置
	 * @return
	 * @throws SystemException 
	 */
	public void updatePwdReset(SqUserTab sqUserTab) throws SystemException{
		//检查两次输入密码是否一直
		if(!sqUserTab.getPasswd().equals(sqUserTab.getRemark())){
			throw new SystemException(UserService.class ,"新密码与确认新密码不一直，请重新输入。");
		}
		String pwd = sqUserTab.getPasswd() ;
		sqUserTab = this.findById(sqUserTab.getUserId()) ;
		if(sqUserTab == null ){
			throw new SystemException(UserService.class ,"员工不存在。");
		}
		if(sqUserTab.getStatus().equals("1")){
			throw new SystemException(UserService.class ,"离职的员工不能做密码重置。");
		}
		if(pwd.equals("")){
			sqUserTab.setPasswd(SQMD5.getEncryMD5("888888"));
		}
		sqUserTab.setStatus("3");
		sqUserTab.setPasswd(SQMD5.getEncryMD5(pwd));
		sqUserTab.setModifyDate(Public.getSystemTimeToFormat("yyyy-MM-dd HH:mm:ss"));
		this.updateUser(sqUserTab);
	}

	public List userByDept(SqUserTab sqUserTab){
		List userList = iBaseDao.find("From SqUserTab sqUserTab where sqUserTab.sqDeptTab.deptNo = '" + sqUserTab.getSqDeptTab().getDeptNo() + "' order by email asc") ;
		return userList ;
	}
	
	public List userByGroup(SqUserTab sqUserTab){
		List userList = iBaseDao.find("From SqUserTab sqUserTab where sqUserTab.sqGroupTab.groupNo = '" + sqUserTab.getSqGroupTab().getGroupNo()+"'") ;
		return userList ;
	}
}
