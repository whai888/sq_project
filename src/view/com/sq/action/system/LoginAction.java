package com.sq.action.system;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.sq.exception.SystemException;
import com.sq.logic.system.ISystemService;
import com.sq.logic.tools.IUserLog;
import com.sq.model.vo.SqUserTab;
import com.sq.model.vo.SysMenu;
import com.sq.model.vo.SysMenuTools;
import com.sq.tools.Public;
import com.sq.vo.ErrorForm;
import com.sq.vo.LoginForm;

/**
 * @version 1.1
 * @author whai
 * 
 */
public class LoginAction {
	@Resource
	private ISystemService isystemService;
	@Resource
	private IUserLog iUserLog ;
	public LoginForm loginForm;
	private String menuId ;
	private String toolsStr ;
	private List toolsList ;
	private ErrorForm errorForm = new ErrorForm() ;
	private Logger log = Logger.getLogger(LoginAction.class);
	private HttpServletRequest request = ServletActionContext.getRequest();
	private HttpSession session = request.getSession();
	

	/**
	 * 登录
	 * 
	 * @return
	 */
	public String login() {
		loginForm.setIp(Public.getIpAddr(request));
		log.info(loginForm.getUserName());
		SqUserTab sqUserTab;
		try {
			sqUserTab = isystemService.isLogin(loginForm);
		} catch (SystemException e) {
			e.printStackTrace();
			// 记录日志
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error" ;
		}
		session.setAttribute("sqUserTab", sqUserTab); // 将员工信息保存到session中
		//加载员工菜单信息
		List menuList = isystemService.findMenuAll(sqUserTab);
		request.setAttribute("menuList", menuList) ;
		// 检查是否需要修改密码,强制用户先修改密码
		if (sqUserTab.getStatus().equals("3")) {
			iUserLog.logwrite(request, new SystemException(LoginAction.class,"强制用户" + sqUserTab.getUserId() +":" + sqUserTab.getUserName()+ "修改密码" ));
			return "modifyPwd";
		}
		iUserLog.logwrite(request, new SystemException(LoginAction.class,"员工登录" ));
		return "login";
	}
	
	/**
	 * 根据菜单编号查询菜单对应的工具栏
	 * @return
	 */
	public String tools() {
		SqUserTab sqUserTab = (SqUserTab)session.getAttribute("sqUserTab");
		String menuName = request.getParameter("menuName");
		List toolsList = isystemService.findToolsByMenuId(sqUserTab , menuId);
		this.toolsStr = toolsMenuStr(menuName, toolsList);
		return "tools" ;
	}

	private String toolsMenuStr(String menuName, List toolsList) {
		StringBuffer strBuff = new StringBuffer();
		strBuff.append("<ul class='sf-menu'>");
		for (int i = 0; i < toolsList.size(); i++) {
			SysMenuTools sysMenuTools = (SysMenuTools)toolsList.get(i);
			//先暂时疯掉
//			if(sysMenuTools.getToolsId().equals(sysMenuTools.getUpToolsId())){
				//查找是否还存在下级节点
				strBuff.append("<li class='current'><a href='"+sysMenuTools.getJumpPath()+"' target='mainFrame' onclick=showPathPos('"+menuName+"','"+sysMenuTools.getRemark()+"') title='"+sysMenuTools.getRemark()+"'>"+sysMenuTools.getRemark()+"</a>");
//				boolean flag = false;
//				for (int j = 0; j < toolsList.size(); j++) {
//					SysMenuTools sysMenuToolsTemp = (SysMenuTools)toolsList.get(j);
//					if(sysMenuToolsTemp.getUpToolsId().endsWith(sysMenuTools.getToolsId()) 
//							&& !sysMenuToolsTemp.getToolsId().equals(sysMenuToolsTemp.getUpToolsId())){
//						if(!flag)
//							strBuff.append("<ul>");
//							flag = true ;
//						//存在下级，则设置下级的级别
//						strBuff.append("<li class='current'><a href='"+sysMenuToolsTemp.getJumpPath()+"' target='mainFrame' onclick='showPathPos('"+menuName+"','"+sysMenuToolsTemp.getRemark()+"')' title='"+sysMenuToolsTemp.getRemark()+"'>"+sysMenuToolsTemp.getRemark()+"</a></li>");
//					}
//				}
//				if(flag)
//					strBuff.append("</ul>");
				strBuff.append("</li>");
			}
//		}
		strBuff.append("</ul>");
		return strBuff.toString();
	}
	
	/*
	 * 系统退出
	 */
	public String loginOut(){
		session.removeAttribute("sqUserTab");
		return "loginout";
	}
	public LoginForm getLoginForm() {
		return loginForm;
	}

	public void setLoginForm(LoginForm loginForm) {
		this.loginForm = loginForm;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getToolsStr() {
		return toolsStr;
	}

	public ErrorForm getErrorForm() {
		return errorForm;
	}

	public List getToolsList() {
		return toolsList;
	}

	public void setErrorForm(ErrorForm errorForm) {
		this.errorForm = errorForm;
	}

}
