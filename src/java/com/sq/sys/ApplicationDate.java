package com.sq.sys;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

public class ApplicationDate {
	//表名+字段作为主键，后面为值和名称作为MAP
	public static Map<String , Map> SYS_PARA_DATA = new HashMap();
	//部门编号作为主键，后面为状态,名称
	public static Map<String , String> DEPT_DATA = new HashMap();
	//职位作为主键，后面为状态,名称
	public static Map<String , String> OFFICE_DATA = new TreeMap();
	//岗位作为主键，后面为状态,名称
	public static Map<String , String> JOB_DATA = new TreeMap();
	//属性文件
	public static Properties PARA_DATA = new Properties();;
	
	/**
	 * 取参数表的相关信息
	 * @param constant
	 * @param list
	 * @param request
	 * @param reqStr
	 */
	public static void getRequestData(String constant , List list , HttpServletRequest request , String reqStr ){
		 Map mapMapTemp = new HashMap();
		 Map mapMap = new TreeMap();
		 if(constant.equals("DEPT_DATA")){
			 mapMapTemp = ApplicationDate.DEPT_DATA;
		 }else if(constant.equals("OFFICE_DATA")){
			 mapMapTemp = ApplicationDate.OFFICE_DATA;
		 }else if(constant.equals("JOB_DATA")){
			 mapMapTemp = ApplicationDate.JOB_DATA;
		 }else if(constant.equals("SYS_PARA_DATA")){
			 mapMapTemp = ApplicationDate.SYS_PARA_DATA;
			 request.setAttribute(reqStr , mapMapTemp.get(list.get(0))) ;
			 return  ;
		 }
		 
		 Iterator it = mapMapTemp.entrySet().iterator();
			while (it.hasNext()) {
			       Map.Entry entry = (Map.Entry) it.next();
			       String key = (String)entry.getKey();
			       Map valueTemp = (Map)entry.getValue();
			       Map.Entry entrytemp = (Map.Entry)valueTemp.entrySet().iterator().next();
			       boolean flag = true ;
			       for (int i = 0; i < list.size(); i++) {
			    	   if(entrytemp.getKey().equals(list.get(i)))
			           		flag = false ;
			    	   		break ;
			       }
			       if(flag)
			    	   mapMap.put(key ,entrytemp.getValue());
			}
		    request.setAttribute(reqStr , mapMap) ;
	}

	public static Map getSysPara(List list) {
		Map mapMapTemp = new HashMap();
		mapMapTemp = ApplicationDate.SYS_PARA_DATA;
		return (Map) mapMapTemp.get(list.get(0));
	}
	
	/*
	 * 取属性文件的值
	 */
	public static String getParaPro(String key){
		return PARA_DATA.getProperty(key);
	}
}
