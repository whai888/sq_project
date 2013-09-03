package com.sq.logic.system;

import java.util.List;
import java.util.Map;

public interface IPageService {
	
	public List findAllList (Map map , int currentStr) throws Exception ;
	public List findAllList(String hql, String[] paramNames, Object[] values,int currentStr) throws Exception ;

	public int findAllCount(Map map) throws Exception ;
	public int findAllCount(String hql ,String[] paramNames, Object[] values) throws Exception ;
}
