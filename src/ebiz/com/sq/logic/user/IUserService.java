package com.sq.logic.user;

import java.util.List;

import com.sq.exception.SystemException;
import com.sq.model.vo.SqUserTab;
import com.sq.vo.LoginForm;

public interface IUserService {
	public void saveUser(SqUserTab sqUserTab) throws SystemException;

	public void updateUser(SqUserTab sqUserTab);

	public SqUserTab findById(String userId) throws SystemException;

	public List findByAll();
	
	public List findByExample(SqUserTab sqUserTab);
	
	public List userByDept(SqUserTab sqUserTab) ;
	
	public List userByGroup(SqUserTab sqUserTab) ;
	
	public void updatePwd(LoginForm loginForm) throws SystemException ;
	
	public void updatePwdReset(SqUserTab sqUserTab) throws SystemException ;

}
