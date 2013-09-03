package com.sq.logic.userrole;

import java.util.List;

import com.sq.exception.SystemException;
import com.sq.model.vo.SqRole;
import com.sq.model.vo.SqUserTab;

/**
 *
 *@autor whai
 */
public interface IUserRoleService {
	public List roleQuery() throws SystemException ;
	public void roleAdd(SqRole sqRole , SqUserTab sqUserTab) throws SystemException ;
	public SqRole roleUpdateQuery(SqRole sqRole) throws SystemException ;
	public void roleUpdate(SqRole sqRole , SqUserTab sqUserTab)  throws SystemException ;
	public void roleDelete(SqRole sqRole) throws SystemException ;
	public List roleMenuQuery(SqRole sqRole) throws SystemException ;
	public List menuAll() throws SystemException ;
	public void roleMenuConfirm(SqRole sqRole ,String [] menuId , String flag) throws SystemException ;
	public List userRoleQuery(SqUserTab sqUserTab) throws SystemException ;
	public void userRoleConfirm(SqUserTab sqUserTab , String [] roleId ) throws SystemException ;
	public List roleUserQuery(SqRole sqRole) throws SystemException ;
	public List userMenuQuery(SqUserTab sqUserTab, SqRole sqRole) throws SystemException ;
	public String userMenuConfirm(SqUserTab sqUserTab ,String [] menuId , String flag) throws SystemException ;
}
