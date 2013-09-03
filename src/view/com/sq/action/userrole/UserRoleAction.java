package com.sq.action.userrole;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.sq.exception.SystemException;
import com.sq.logic.tools.IUserLog;
import com.sq.logic.user.IUserService;
import com.sq.logic.userrole.IUserRoleService;
import com.sq.model.vo.SqRole;
import com.sq.model.vo.SqUserTab;
import com.sq.model.vo.SysMenu;
import com.sq.model.vo.SysMenuTools;
import com.sq.tools.Public;
import com.sq.vo.ErrorForm;

/**
 * 
 *@autor whai
 */
public class UserRoleAction {
	@Resource
	private IUserRoleService iUserRoleService;
	@Resource
	private IUserService iUserService ;
	@Resource
	private IUserLog iUserLog ;
	private SqRole sqRole = new SqRole(); 
	private SqUserTab sqUserTab ;
	private String [] treeData ;
	private ErrorForm errorForm = new ErrorForm() ;
	private HttpServletRequest request = ServletActionContext.getRequest();
	private HttpSession session = request.getSession(false);
	/**
	 * 角色权限维护
	 * @return
	 */
	public String roleQuery() {
		try {
			List roleList = iUserRoleService.roleQuery();
			request.setAttribute("sqRoleList", roleList);
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error" ;
		}
		return "rolequery";
	}

	/**
	 * 角色新增
	 * @return
	 */
	public String roleAdd() {
		SqUserTab sqUserTabTemp = (SqUserTab) session.getAttribute("sqUserTab");
		try {
			iUserRoleService.roleAdd(sqRole, sqUserTabTemp);
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error" ;
		}
		return "success";
	}

	/**
	 * 角色修改查询
	 * @return
	 */
	public String roleUpdateQuery() {
		try {
			sqRole = iUserRoleService.roleUpdateQuery(sqRole);
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error" ;
		}
		return "roleupdatequery";
	}

	/**
	 * 角色修改
	 * @return
	 */
	public String roleUpdate() {
		SqUserTab sqUserTabTemp = (SqUserTab) session.getAttribute("sqUserTab");
		try {
			iUserRoleService.roleUpdate(sqRole, sqUserTabTemp);
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error" ;
		}
		return "success";
	}

	/**
	 * 角色删除
	 * 
	 * @return
	 */
	public String roleDelete() {
		try {
			iUserRoleService.roleDelete(sqRole);
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error" ;
		}
		return "roledelete";
	}
	/**
	 * 角色菜单分配查询
	 * @return
	 */
	public String roleMenuQuery() {
		try {
			//查询所有的角色
			List roleList = iUserRoleService.roleQuery();
			request.setAttribute("roleList", roleList);
			//查询所有的菜单
			List menuList = iUserRoleService.menuAll();
			String tree = generateTree(menuList , "treediv");
			request.setAttribute("tree", tree);
			//查询角色所拥有的菜单
			List roleMenuList = new ArrayList();
			if(sqRole!=null && sqRole.getRoleId()!=null && !Public.isEmpty(sqRole.getRoleId())){
				roleMenuList = iUserRoleService.roleMenuQuery(sqRole);
			}else{
				sqRole = (SqRole)roleList.get(0) ;
				roleMenuList = iUserRoleService.roleMenuQuery(sqRole);
			}
			String roleTree = generateTree(roleMenuList , "roleMenuList");
			request.setAttribute("roleTree", roleTree);
			request.setAttribute("sqRole", sqRole);
			
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error" ;
		}
		
		return "rolemenuquery";
	}

	/**
	 * 角色菜单分配确认
	 * @return
	 */
	public String roleMenuConfirm() {
		try {
			String flag = request.getParameter("flag");
			if(treeData != null)
				iUserRoleService.roleMenuConfirm(sqRole ,treeData ,flag);
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error" ;
		}
		return "rolemenuconfirm";
	}

	/**
	 * 查询角色下所有的员工
	 * @return
	 */
	public String roleUserQuery(){
		try {
			//查询所有的角色
			List roleList = iUserRoleService.roleQuery();
			request.setAttribute("roleList", roleList);
			//查询所有角色下对应的员工
			List roleUserList = new ArrayList();
			if(sqRole!=null && sqRole.getRoleId()!=null && !Public.isEmpty(sqRole.getRoleId())){
				roleUserList = iUserRoleService.roleUserQuery(sqRole);
			}else{
				sqRole = (SqRole)roleList.get(0) ;
				roleUserList = iUserRoleService.roleUserQuery(sqRole);
			}
			request.setAttribute("roleUserList", roleUserList) ;
			request.setAttribute("sqRole", sqRole);
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error" ;
		}
		
		return "roleuserquery";
	}
	/**
	 * 员工菜单分配查询
	 * @return
	 */
	public String userMenuQuery() {
		try {
			//查询角色下拥有的菜单
			List roleMenuList = iUserRoleService.roleMenuQuery(sqRole);
			String tree = generateTree(roleMenuList , "treediv");
			request.setAttribute("tree", tree);
			//查询用户所拥有的菜单权限
			List userMenuList = iUserRoleService.userMenuQuery(sqUserTab , sqRole);
			tree = generateTree(userMenuList , "roleMenuList");
			request.setAttribute("roleTree", tree);
			
			sqUserTab = iUserService.findById(sqUserTab.getUserId());
			request.setAttribute("sqUserTab", sqUserTab);
			
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error" ;
		}
		return "usermenuquery";
	}

	/**
	 * 员工菜单分配确认
	 * @return
	 */
	public String userMenuConfirm() {
		try {
			String flag = request.getParameter("flag");
			if(treeData != null)
				iUserRoleService.userMenuConfirm(sqUserTab ,treeData ,flag);
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error" ;
		}
		return "usermenuconfirm";
	}

	/**
	 * 员工角色分配查询
	 * @return
	 */
	public String userRoleQuery() {
		try {
			//查询员工所拥有的角色
			List userRoleList = iUserRoleService.userRoleQuery(sqUserTab);
			request.setAttribute("userRoleList", userRoleList);
			//查询所有的角色
			List roleList = iUserRoleService.roleQuery();
			request.setAttribute("sqRoleList", roleList);
			sqUserTab = iUserService.findById(sqUserTab.getUserId());
			request.setAttribute("sqUserTab", sqUserTab);
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error" ;
		}
		
		return "userrolequery";
	}

	/**
	 * 员工角色分配确认
	 * @return
	 */
	public String userRoleConfirm() {
		
		try {
			iUserRoleService.userRoleConfirm(sqUserTab, treeData);
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error" ;
		}
		return "success";
	}

	public String generateTree(List menuList , String treediv ) {
		StringBuffer tree=new StringBuffer();
		tree.append("var "+treediv+" = new MzTreeView(\""+treediv+"\");\n");
		tree.append(treediv+".icons[\"property\"] = \"property.gif\";\n");
		tree.append(treediv+".icons[\"css\"] = \"collection.gif\";\n");
		tree.append(treediv+".icons[\"book\"]  = \"book.gif\";\n");
		tree.append(treediv+".iconsExpand[\"book\"] = \"bookopen.gif\";\n");
		tree.append(treediv+".setIconPath(\"../js/webtree/\");\n");
		boolean flag = false ;
		for (int i = 0; i < menuList.size(); i++) {
			SysMenu sysMenu = (SysMenu)menuList.get(i);
			if(!flag){
				tree.append(treediv+".nodes[\"0_"+sysMenu.getUpId()+"\"] = \"text:角色菜单;icon:book; data:"+sysMenu.getUpId()+"\";\n");
				flag = true ;
			}
			tree.append(treediv+".nodes[\""+sysMenu.getUpId()+"_"+sysMenu.getMenuId()+"\"] = \"text:"+sysMenu.getMenuName()+";icon:book; data:"+sysMenu.getMenuId()+"\"; \n") ;
			Set<SysMenuTools> menuTools = sysMenu.getMenuTools(); 
			for (SysMenuTools tools : menuTools) {
				tree.append(treediv+".nodes[\""+Integer.parseInt(tools.getMenuId())+"_"+Integer.parseInt(tools.getToolsId())+"\"] = \"text:"+tools.getRemark()+";data:"+Integer.parseInt(tools.getToolsId())+"\"; \n") ;
			}
		}
		tree.append("var obj=document.getElementById(\""+treediv+"\");\n");
		tree.append("obj.innerHTML="+treediv+".toString();\n");
		return tree.toString();
	}
	
	public ErrorForm getErrorForm() {
		return errorForm;
	}

	public void setErrorForm(ErrorForm errorForm) {
		this.errorForm = errorForm;
	}

	public SqRole getSqRole() {
		return sqRole;
	}

	public void setSqRole(SqRole sqRole) {
		this.sqRole = sqRole;
	}

	public String[] getTreeData() {
		return treeData;
	}

	public void setTreeData(String[] treeData) {
		this.treeData = treeData;
	}

	public SqUserTab getSqUserTab() {
		return sqUserTab;
	}

	public void setSqUserTab(SqUserTab sqUserTab) {
		this.sqUserTab = sqUserTab;
	}

}
