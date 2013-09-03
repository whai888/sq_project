package com.sq.logic.system;

import java.util.List;

import com.sq.exception.SystemException;
import com.sq.model.vo.SqUserTab;
import com.sq.vo.LoginForm;

public abstract interface ISystemService {
	public SqUserTab isLogin(LoginForm loginForm) throws SystemException;
	
	public List findMenuAll(SqUserTab sqUserTab);
	
	public List findToolsByMenuId(SqUserTab sqUserTab ,String menuid);
	
}
