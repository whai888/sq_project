package com.sq.logic.system.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.sq.exception.SystemException;
import com.sq.logic.system.IPageService;
import com.sq.service.IBaseDAO;
import com.sq.tools.Constant;
import com.sq.tools.PageHqlConstant;
import com.sq.tools.Public;

@Transactional(rollbackFor=SystemException.class) //指定回滚,遇到异常SystemException时回滚
public class PageService implements IPageService {
	@Resource
	private IBaseDAO iBaseDao;
	private String hqlName = "" ;
	private Logger log = Logger.getLogger(PageService.class);
	
	/**
	 * 功能描述：前台页面上传的值和当前页数，返回查询结果
	 * @param map			前台页面上传的值
	 * @param currentStr	当前页数
	 * @return				返回结果集
	 * @throws Exception
	 */
	public List findAllList(Map map , int currentStr) throws Exception {
		hqlName = (String) map.get("HQL") ;
		String queryString = this.getValue(hqlName);
		StringBuffer countString = new StringBuffer() ;
		String [] paramNames = new String[map.size()-2] ;
		Object [] values = new Object[map.size()-2] ;
		
		Set<Map.Entry<String, String>> set = map.entrySet();
		int i =0 ;
		for (Iterator<Map.Entry<String, String>> it = set.iterator(); it
				.hasNext();) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) it
					.next();
			if(entry.getKey().equals("HQL") || entry.getKey().equals("currentStr") ) continue ;
			//如果传入的值为-1，则去掉改查询条件
			if(entry.getValue().equals("-99")){
				int end = queryString.indexOf("and" , queryString.indexOf(":" + entry.getKey())) ;
				int start = queryString.lastIndexOf("and" , queryString.indexOf(":" + entry.getKey())) ;
				if(end != -1)
					queryString = queryString.substring(0 ,start) + queryString.substring(end, queryString.length()) ;
				else
					queryString = queryString.substring(0 , start) ;
			}
			paramNames[i] = entry.getKey() ;
			if(entry.getKey().equals("enterYear") || entry.getKey().startsWith("start") || entry.getKey().startsWith("end")){
				values[i] = Public.getStringToDate(entry.getValue(), "yyyy-MM-dd") ;
			}else if(entry.getKey().startsWith("int")){
				values[i] = Integer.valueOf(entry.getValue());
			}else{
				values[i] = entry.getValue() ;
			}
			i++;
		}
		List listData  = new ArrayList() ;
		if(!(hqlName.equals("PROJECT_TO_STEPINFO") || currentStr==-99 ))
			listData = iBaseDao.findByNamedParam(queryString, paramNames, values  , (currentStr -1 )*Constant.PAGE_COUNT  , Constant.PAGE_COUNT );
		else
			listData = iBaseDao.findByNamedParam(queryString, paramNames, values  , 0  , 10000 );
		
		return listData ;
	}

	/**
	 * 根据指定的信息查询
	 */
	public List findAllList(String hql, String[] paramNames, Object[] values,
			int currentStr) throws Exception {
		List listData  = new ArrayList() ;
		if(!(hqlName.equals("PROJECT_TO_STEPINFO") || currentStr==-99))
			listData = iBaseDao.findByNamedParam(hql, paramNames, values  , (currentStr -1 )*Constant.PAGE_COUNT  , Constant.PAGE_COUNT );
		else
			listData = iBaseDao.findByNamedParam(hql, paramNames, values  , 0  , 10000 );
		
		return listData ;
	}
	/**
	 * 功能描述：取数据量的总大小
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int findAllCount(Map map) throws Exception {
		hqlName = (String) map.get("HQL") ;
		String queryString = this.getValue(hqlName);
		String [] paramNames = new String[map.size()-2] ;
		Object [] values = new Object[map.size()-2] ;
		
		Set<Map.Entry<String, String>> set = map.entrySet();
		int i =0 ;
		for (Iterator<Map.Entry<String, String>> it = set.iterator(); it
				.hasNext();) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) it
					.next();
			if(entry.getKey().equals("HQL") || entry.getKey().equals("currentStr") ) continue ;
			//如果传入的值为-1，则去掉改查询条件
			if(entry.getValue().equals("-99")){
				int end = queryString.indexOf("and" , queryString.indexOf(":" + entry.getKey())) ;
				int start = queryString.lastIndexOf("and" , queryString.indexOf(":" + entry.getKey())) ;
				if(end != -1)
					queryString = queryString.substring(0 ,start) + queryString.substring(end, queryString.length()) ;
				else
					queryString = queryString.substring(0 , start) ;
			}
			paramNames[i] = entry.getKey() ;
			if(entry.getKey().equals("enterYear") || entry.getKey().startsWith("start") || entry.getKey().startsWith("end")){
				values[i] = Public.getStringToDate(entry.getValue(), "yyyy-MM-dd") ;
			}else if(entry.getKey().startsWith("int")){
				values[i] = Integer.valueOf(entry.getValue());
			}else{
				values[i] = entry.getValue() ;
			}
			i++;
		}
		int totalCount = iBaseDao.findByNamedParam(queryString, paramNames, values , 0 , 100000000).size() ;
		
		return totalCount ;
	}
	
	public int findAllCount(String hql ,String[] paramNames, Object[] values )
	throws Exception {
		int totalCount = iBaseDao.findByNamedParam(hql, paramNames, values , 0 , 100000000).size() ;
		return totalCount ;
	}
	
	private Object setClassPara(Object obj, String methodStr, Object[] objList)
			throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		Class[] argclass = new Class[objList.length];
		for (int i = 0, j = argclass.length; i < j; i++) {
			argclass[i] = objList[i].getClass();
		}
		Method method = obj.getClass().getMethod(methodStr, argclass);
		Object objRet = method.invoke(obj, objList);
		return objRet;
	}

	public static String getValue(String sqlName) throws SecurityException,
			NoSuchFieldException, IllegalArgumentException,
			IllegalAccessException {
		Field f = PageHqlConstant.class.getField(sqlName);
		return f.get(null).toString();
	}

}
