package com.sq.logic.system.impl;

import java.sql.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.sq.exception.SystemException;
import com.sq.logic.system.ISystemService;
import com.sq.logic.tools.impl.UserLog;
import com.sq.model.vo.SqDeptTab;
import com.sq.model.vo.SqJobTab;
import com.sq.model.vo.SqSysparaTab;
import com.sq.model.vo.SqUserTab;
import com.sq.model.vo.SysMenu;
import com.sq.model.vo.SysMenuTools;
import com.sq.service.IBaseDAO;
import com.sq.tools.Constant;
import com.sq.tools.Public;
import com.sq.tools.SQMD5;
import com.sq.vo.LoginForm;

@Transactional(rollbackFor=SystemException.class) //指定回滚,遇到异常SystemException时回滚
public class SystemService implements ISystemService{
	@Resource IBaseDAO iBaseDao ;
	private Logger log = Logger.getLogger(SystemService.class);

	/**
	 * 检查用户登录
	 * @throws SystemException 
	 */
	public SqUserTab isLogin(LoginForm loginForm) throws SystemException {
		SqUserTab sqUserTab = new SqUserTab();
		sqUserTab.setUserName(loginForm.getUserName());
		Object o = iBaseDao.findFirstVO(sqUserTab , null );
		if(o == null){
			throw new SystemException(SystemService.class ,"用户名或密码错误，请检查输入是否正确。");
		}
		sqUserTab = (SqUserTab)o ;
		//检查员工状态是否正常
		if(!(sqUserTab.getStatus().equals("0") || sqUserTab.getStatus().equals("3"))){
			throw new SystemException(SystemService.class ,"用户信息错误，请联系管理员或负责人。");
		}
		//检查密码错误的次数是否超过了预定义的次数
		if(sqUserTab.getPwdError() >= Constant.PWD_ERROR_COUNT){
			//更新员工状态为"员工状态异常（密码错误次数过多）"
			sqUserTab.setStatus("2");
			iBaseDao.saveOrUpdate(sqUserTab);
			throw new SystemException(SystemService.class ,"密码错误次数超过5次，系统无法让您登录，请联系管理员或负责人。");
		}
		//密码检查，如果密码错误需将密码的错误次数新增1
		if(!sqUserTab.getPasswd().equals(SQMD5.getEncryMD5(loginForm.getPassword()))){
			sqUserTab.setPwdError(sqUserTab.getPwdError() + 1) ;
			//密码错误更新
			iBaseDao.saveOrUpdate(sqUserTab);
			throw new SystemException(SystemService.class ,"用户名或密码错误，请检查输入是否正确。");
		}
		//验证成功，更新用户表中的数据
		sqUserTab.setIp(loginForm.getIp()) ;
		sqUserTab.setLoginpass((Public.getSystemTimeToFormat("yyyy-MM-dd HH:mm:ss")));
		iBaseDao.saveOrUpdate(sqUserTab);
		
//		//加载职位信息
//		SqJobTab sqJobTab = new SqJobTab();
//		sqJobTab.setJobId(sqUserTab.getJob());
//		sqUserTab.setSqJobTab((SqJobTab)iBaseDao.findFirstVO(sqJobTab, "null"));
//		//加载部门信息
//		SqDeptTab sqDeptTab = new SqDeptTab();
//		sqDeptTab.setDeptNo(sqUserTab.getSqDeptTab().getDeptNo());
//		sqUserTab.setSqDeptTab((SqDeptTab)iBaseDao.findFirstVO(sqDeptTab, "null"));
		return sqUserTab;
	}

	public List findMenuAll(SqUserTab sqUserTab) {
		String hql = "select sys_menu.* from sq_user_menu sq_user_menu,sys_menu sys_menu where sq_user_menu.user_id = '"+sqUserTab.getUserId()+"' and sq_user_menu.menu_id = sys_menu.menu_id order by sq_user_menu.menu_id";
		String [] para = new String[1];
		para[0] = "user_id" ;
		String [] obj = new String[1];
		obj[0] = sqUserTab.getUserId();
		List sqUserMenuList = iBaseDao.findWithSQL(hql , new SysMenu(), "myuserId", para, obj);
		return sqUserMenuList ;
	}

	public List findToolsByMenuId(SqUserTab sqUserTab ,String menuid) {
		String sql = "select sys_menu_tools.* from sys_menu_tools sys_menu_tools , sq_user_menu sq_user_menu where sq_user_menu.menu_id = sys_menu_tools.tools_id and sq_user_menu.user_id='"+sqUserTab.getUserId()+"' and sys_menu_tools.menu_id='"+menuid+"' and sys_menu_tools.status='0' order by sys_menu_tools.index_no";
		List sqUserMenuList = iBaseDao.findWithSQL(sql, new SysMenuTools());
		return sqUserMenuList;
	}
	
}
