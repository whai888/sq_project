package com.sq.logic.userrole.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.sq.exception.SystemException;
import com.sq.logic.userrole.IUserRoleService;
import com.sq.model.vo.SqRole;
import com.sq.model.vo.SqRoleMenu;
import com.sq.model.vo.SqUserRole;
import com.sq.model.vo.SqUserTab;
import com.sq.model.vo.SysMenu;
import com.sq.model.vo.SysMenuTools;
import com.sq.service.IBaseDAO;
import com.sq.tools.Constant;
import com.sq.tools.Public;

/**
 * 
 *@autor whai
 */
@Transactional(rollbackFor=SystemException.class) //指定回滚,遇到异常SystemException时回滚
public class UserRoleService implements IUserRoleService{
	@Resource IBaseDAO iBaseDao ;
	private Logger log = Logger.getLogger(UserRoleService.class);
	
	/**
	 * 角色权限维护
	 * 
	 * @return
	 */
	public List roleQuery() {
		List roleList = iBaseDao.loadAll(SqRole.class);
		return roleList;
	}

	/**
	 * 角色新增
	 * 
	 * @return
	 */
	public void roleAdd(SqRole sqRole , SqUserTab sqUserTab) {
		sqRole.setRoleId(iBaseDao.sequenceToId(Constant.SEQUENCE_SQ_ROLE));
		sqRole.setCreateUserTab(sqUserTab);
		sqRole.setCreateDate(Public.getStringToDate(null, "yyyy-MM-dd"));
		iBaseDao.save(sqRole);
	}

	/**
	 * 角色修改查询
	 * 
	 * @return
	 */
	public SqRole roleUpdateQuery(SqRole sqRole) {
		return (SqRole)iBaseDao.findFirstVO(sqRole, "roleId" );
	}

	/**
	 * 角色修改
	 * 
	 * @return
	 */
	public void roleUpdate(SqRole sqRole , SqUserTab sqUserTab) {
		sqRole.setModifyUserTab(sqUserTab);
		sqRole.setModifyDate(Public.getStringToDate(null, "yyyy-MM-dd"));
		iBaseDao.update(sqRole);
	}

	/**
	 * 角色删除
	 * 
	 * @return
	 */
	public void roleDelete(SqRole sqRole) {
		//删除角色对应的所有员工
		String sql = "delete from sq_user_role where role_id='"+sqRole.getRoleId()+"'";
		iBaseDao.sqlUpdate(sql);
		
		//删除角色对应的菜单
		sql = "delete from sq_role_menu where role_id='"+sqRole.getRoleId()+"'";
		iBaseDao.sqlUpdate(sql);
		
		iBaseDao.delete(sqRole);
	}
	/**
	 * 角色菜单分配查询
	 * 
	 * @return
	 */
	public List roleMenuQuery(SqRole sqRole) {
		String hql = "select sys_menu.* from sq_role_menu sq_role_menu,sys_menu sys_menu where sq_role_menu.role_id = '"+sqRole.getRoleId()+"' and sq_role_menu.menu_id = sys_menu.menu_id order by sq_role_menu.menu_id";
		String [] para = new String[1];
		para[0] = "role_id" ;
		String [] obj = new String[1];
		obj[0] = sqRole.getRoleId();
		List sqRoleMenuList = iBaseDao.findWithSQL(hql , new SysMenu(), "myfilter", para, obj);
		return sqRoleMenuList;
	}

	/**
	 * 查询所有的菜单
	 * 
	 * @return
	 */
	public List menuAll() {
		String hql = "from SysMenu sysMenu order by sysMenu.menuId";
		List sysMenuList = iBaseDao.find(hql);
		return sysMenuList;
	}
	/**
	 * 角色菜单分配确认
	 * 
	 * @return
	 * @throws SystemException 
	 */
	public void roleMenuConfirm(SqRole sqRole ,String [] menuId , String flag) throws SystemException {
		try {
			if(flag.equals("1")){
				//给角色分配菜单权限
				for (int i = 0; i < menuId.length; i++) {
					String sql = "insert into sq_role_menu values ('"+sqRole.getRoleId()+"' , '"+menuId[i]+"')";
					iBaseDao.sqlUpdate(sql);
					//判断是否已经存在该员工菜单
//					sql = "select distinct a.user_id from sq_user_role a , sq_role_menu b , sq_user_menu c where a.role_id = b.role_id and a.role_id='"+sqRole.getRoleId()+"' and c.user_id=a.user_id and c.menu_id ='"+menuId[i]+"'" ;
//					List tempList = iBaseDao.sqlQuery(sql);
//					if(tempList.size() == 0 ){
						//将该角色下所有的菜单分配给员工(如果该员工其它角色下有该菜单，则跳过)
					sql = "select distinct a.menu_id from sq_user_menu a , sq_user_role b where b.role_id!='"+sqRole.getRoleId()+"' and a.user_id=b.user_id and a.menu_id='"+menuId[i]+"'";
						List roleMenuList = iBaseDao.sqlQuery(sql);
						if(roleMenuList.size() > 0) continue ;
						
						sql = "insert into sq_user_menu(user_id,menu_id) select distinct a.user_id,'"+menuId[i]+"' as menu_id from sq_user_role a , sq_role_menu b , sq_user_menu c where a.role_id = b.role_id and a.role_id='"+sqRole.getRoleId()+"' and c.user_id=a.user_id and c.menu_id !='"+menuId[i]+"'" ;
						iBaseDao.sqlUpdate(sql);
//					}
				}
			}else if(flag.equals("2")){
				//给角色解除菜单权限
				for (int i = 0; i < menuId.length; i++) {
					String sql = "delete from sq_role_menu where role_id='"+sqRole.getRoleId()+"' and menu_id="+menuId[i]+"";
					iBaseDao.sqlUpdate(sql);
					//将该角色下所有的员工菜单权限取消（先查询员工其它的角色下是否有该菜单，如果有，不需要删除，否则删除）
					sql = "select distinct a.menu_id from sq_user_menu a , sq_user_role b where b.role_id!='"+sqRole.getRoleId()+"' and a.user_id=b.user_id and a.menu_id='"+menuId[i]+"'";
					List roleMenuList = iBaseDao.sqlQuery(sql);
					if(roleMenuList.size() > 0) continue ;
					sql = "delete from sq_user_menu where menu_id  ='"+menuId[i]+"'" ;
					iBaseDao.sqlUpdate(sql);
				}
			}
		} catch (Exception e) {
			throw new SystemException(UserRoleService.class , "角色权限分配错误："+e.getMessage());
		}
	}

	/**
	 * 查询角色下对应的员工
	 * 
	 * @return
	 */
	public List roleUserQuery(SqRole sqRole){
		String sql = "select userTab.* from sq_user_tab userTab , sq_user_role userRole where userTab.user_id = userRole.user_id and userRole.role_id='"+sqRole.getRoleId()+"'";
		List roleUserList = iBaseDao.findWithSQL(sql, new SqUserTab());
		return roleUserList ;
	}
	/**
	 * 员工菜单分配查询
	 * 
	 * @return
	 */
	public List userMenuQuery(SqUserTab sqUserTab , SqRole sqRole) {
		String hql = "select sys_menu.* from sq_user_menu sq_user_menu,sys_menu sys_menu where sq_user_menu.user_id = '"+sqUserTab.getUserId()+"' and sq_user_menu.menu_id = sys_menu.menu_id order by sq_user_menu.menu_id";
		String [] para = new String[1];
		para[0] = "user_id" ;
		String [] obj = new String[1];
		obj[0] = sqUserTab.getUserId();
		List sqUserMenuList = iBaseDao.findWithSQL(hql , new SysMenu(), "mytoolsId", para, obj);
		return sqUserMenuList;
	}

	/**
	 * 员工菜单分配确认
	 * 
	 * @return
	 * @throws SystemException 
	 */
	public String userMenuConfirm(SqUserTab sqUserTab ,String [] menuId , String flag) throws SystemException {
		try {
			if(flag.equals("1")){
				//给员工分配菜单权限
				for (int i = 0; i < menuId.length; i++) {
					String sql = "insert into sq_user_menu values ('"+sqUserTab.getUserId()+"' , '"+menuId[i]+"')";
					iBaseDao.sqlUpdate(sql);
				}
			}else if(flag.equals("2")){
				//给员工解除菜单权限
				for (int i = 0; i < menuId.length; i++) {
					String sql = "delete from sq_user_menu where user_id='"+sqUserTab.getUserId()+"' and menu_id="+menuId[i]+"";
					iBaseDao.sqlUpdate(sql);
				}
			}
		} catch (Exception e) {
			throw new SystemException(UserRoleService.class , "员工菜单权限分配错误："+e.getMessage());
		}
		return "usermenuconfirm";
	}

	/**
	 * 员工角色分配查询
	 * 
	 * @return
	 */
	public List userRoleQuery(SqUserTab sqUserTab) {
		String hql = "from SqUserRole sqUserRole where sqUserRole.id.userId='"+sqUserTab.getUserId()+"'";
		List userRoleList = iBaseDao.find(hql);
		return userRoleList;
	}

	/**
	 * 员工角色分配确认
	 * 
	 * @return
	 * @throws SystemException 
	 */
	public void userRoleConfirm(SqUserTab sqUserTab , String [] roleId ) throws SystemException {
		try {
			String sql = "";
			//查询员工所有的角色
			sql = "from SqUserRole sqUserRole where sqUserRole.id.userId='"+sqUserTab.getUserId()+"'";
			List temp = iBaseDao.find(sql);
			//先删除员工的角色
			for (int i = 0; i < temp.size(); i++) {
				SqUserRole sqUserRole = (SqUserRole)temp.get(i);
				boolean flag = false ;
				for (int j = 0; j < roleId.length; j++) {
					if(sqUserRole.getId().getSqRole().getRoleId().equals(roleId[j])){
						flag = true ;
						continue ;
					}
				}
				//删除员工的角色
				if(!flag){
					sql = "delete from sq_user_role where user_id = '"+sqUserTab.getUserId()+"' and role_id='"+sqUserRole.getId().getSqRole().getRoleId()+"'";
					iBaseDao.sqlUpdate(sql);
					//删除员工对应的菜单,对于员工拥有多级角色的，删除时不能影响到其它角色菜单
					sql = "delete from sq_user_menu where user_id = '"+sqUserTab.getUserId()+"' and menu_id not in (select sq_role_menu.menu_id from sq_user_role sq_user_role , sq_role_menu sq_role_menu where sq_user_role.role_id != '"+sqUserRole.getId().getSqRole().getRoleId()+"' and sq_user_role.user_id='"+sqUserTab.getUserId()+"' and sq_user_role.role_id = sq_role_menu.role_id)";
					iBaseDao.sqlUpdate(sql);
				}
			}
			//给员工分配角色
			for (int i = 0; i < roleId.length; i++) {
				//判断员工是否存在对应的角色，如果有则跳过
				boolean flag = false ;
				for (int j = 0; j < temp.size(); j++) {
					SqUserRole sqUserRole = (SqUserRole)temp.get(j);
					if(sqUserRole.getId().getSqRole().getRoleId().equals(roleId[i])){
						flag = true ;
						break ;
					}
				}
				if(flag)
					continue ;
				//新增员工对应的角色
				sql = "insert into sq_user_role values ('"+sqUserTab.getUserId()+"' , '"+roleId[i]+"')";
				iBaseDao.sqlUpdate(sql);
				//将角色对应的所有菜单赋值给员工,查询语句过滤掉员工已经拥有的菜单
				sql = "insert into sq_user_menu(user_id,menu_id) select '"+sqUserTab.getUserId()+"' as user_id,sq_role_menu.menu_id from sq_role_menu sq_role_menu  where  sq_role_menu.role_id='"+roleId[i]+"' and sq_role_menu.menu_id not in (select sq_user_menu.menu_id from sq_user_menu sq_user_menu where sq_user_menu.user_id='"+sqUserTab.getUserId()+"')";
				iBaseDao.sqlUpdate(sql);
			}
		} catch (Exception e) {
			throw new SystemException(UserRoleService.class , "角色权限分配错误："+e.getMessage());
		}
	}
}
