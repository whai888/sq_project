package com.sq.logic.tools.impl;

import java.util.List;

import javax.annotation.Resource;

import com.sq.logic.tools.ISysparaTab;
import com.sq.model.vo.SqSysparaTab;
import com.sq.service.IBaseDAO;

/**
 * @作者 whai
 * @创建日期 2013-7-2
 * @版本 V1.0
 * @文件名 SysparaTab.java
 */
public class SysparaTab implements ISysparaTab{
	@Resource
	private IBaseDAO iBaseDao;
	
	/**
	 * 取系统参数
	 * @param sysId
	 * @return
	 */
	public String findById(String sysId){
		String HQL = "From SqSysparaTab sqSysparaTab where sqSysparaTab.sysId='"+sysId+"'";
		List sysParaTab =  iBaseDao.find(HQL);
		SqSysparaTab sqSysparaTab = (SqSysparaTab)sysParaTab.get(0);
		return sqSysparaTab.getFieldDesc() ;
	}

}
