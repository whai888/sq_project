package com.sq.sys.reload.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.sq.model.vo.SqDeptTab;
import com.sq.model.vo.SqJobTab;
import com.sq.model.vo.SqOfficeTab;
import com.sq.model.vo.SqSysparaTab;
import com.sq.service.IBaseDAO;
import com.sq.service.imp.BaseDAO;
import com.sq.sys.ApplicationDate;
import com.sq.sys.reload.IFndCache;

public class FndCache implements ApplicationContextAware , IFndCache {
	private static Logger log = Logger.getLogger(FndCache.class);
	private static ApplicationContext context ;
	@Resource
	private IBaseDAO iBaseDao ;
	public static Map<String , Map<String, String>> SYS_PARA_DATE = new HashMap();
	
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		//在加载Spring时自动获得context   
		this.context = context;
		iBaseDao = (IBaseDAO) context.getBean("iBaseDao");
		init();
	}

	private void init(){
		log.info("开始加载系统参数");
		//加载系统参数表
		initSysParaTab();
		log.info("开始加载部门表");
		//加载部门信息表
		initDeptTab();
		log.info("开始加载职位表");
		//加载职位表
		initOfficeTab();
		log.info("开始加载岗位表");
		//加载岗位表
		initJobTab();
		log.info("开始加载属性文件");
		//加载属性文件
		initPara();
		log.info("所有参数已经加载完成");
	}

	/**
	 * 加载属性文件
	 */
	private void initPara() {
		// TODO Auto-generated method stub
		InputStream in=FndCache.class.getResourceAsStream("para.properties");
		try {
			ApplicationDate.PARA_DATA.load(in);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("属性文件加载失败");
		}
	}

	/**
	 * 加载岗位表
	 */
	public void initJobTab() {
		List jobList = iBaseDao.findByObject(new SqJobTab(), "indexNo");
		Map mapJob = new TreeMap<String, String>();
		for (Object obj : jobList) {
			SqJobTab sqJobTab = (SqJobTab)obj ;
			Map temp = new HashMap();
			temp.put(sqJobTab.getStatus() , sqJobTab.getJobName());
			mapJob.put(sqJobTab.getJobId(), temp);
		}
		ApplicationDate.JOB_DATA = mapJob ;
	}

	/**
	 * 加载职位表
	 */
	public void initOfficeTab() {
		List officeList = iBaseDao.findByObject(new SqOfficeTab(), "indexNo");
		Map mapOffice = new TreeMap<String, String>();
		for (Object obj : officeList) {
			SqOfficeTab sqOfficeTab = (SqOfficeTab)obj ;
			Map temp = new HashMap();
			temp.put(sqOfficeTab.getStatus() , sqOfficeTab.getOfficeName());
			mapOffice.put(sqOfficeTab.getOfficeId(), temp);
		}
		ApplicationDate.OFFICE_DATA = mapOffice ;
		
	}

	/**
	 * 加载部门表
	 */
	public void initDeptTab() {
		List deptList = iBaseDao.findByObject(new SqDeptTab(), null);
		Map mapDept = new TreeMap<String, String>();
		for (Object obj : deptList) {
			SqDeptTab sqDeptTab = (SqDeptTab)obj ;
			Map temp = new HashMap();
			temp.put(sqDeptTab.getStatus() , sqDeptTab.getDeptName());
			mapDept.put(sqDeptTab.getDeptNo(),  temp );
		}
		ApplicationDate.DEPT_DATA = mapDept ;
	}

	/**
	 * 加载系统参数表
	 */
	public void initSysParaTab() {
		Map mapParaList = new HashMap();
		List<SqSysparaTab> sysParaList = iBaseDao.find("from  SqSysparaTab as sqSysparaTab order by sqSysparaTab.fieldValue desc");
		for (Object obj : sysParaList) {
			SqSysparaTab sqSysParaTab = (SqSysparaTab)obj ;
			if(mapParaList.containsKey(sqSysParaTab.getSysTable()+sqSysParaTab.getField())){
				continue ;
			}
			//需要采用升序排列  所以采用sortedMap
			SortedMap<String, String> mapParaListTemp = new TreeMap<String, String>();
			for (Object objTemp : sysParaList) {
				SqSysparaTab sqSysParaTabTemp = (SqSysparaTab)objTemp ;
				if((sqSysParaTab.getSysTable()+sqSysParaTab.getField()).equals(sqSysParaTabTemp.getSysTable()+sqSysParaTabTemp.getField())){
					mapParaListTemp.put(sqSysParaTabTemp.getFieldValue(), sqSysParaTabTemp.getFieldDesc());
					
				}
			}
			mapParaList.put(sqSysParaTab.getSysTable()+sqSysParaTab.getField(), mapParaListTemp);
		}
		ApplicationDate.SYS_PARA_DATA = mapParaList ;
	}
}
