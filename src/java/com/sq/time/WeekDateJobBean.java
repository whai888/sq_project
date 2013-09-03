package com.sq.time;

import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.sq.logic.workday.impl.WorkDayService;
import com.sq.model.vo.SqSysparaTab;
import com.sq.service.IBaseDAO;
import com.sq.tools.Public;

/**
 * @作者 whai
 * @创建日期 2013-6-20
 * @版本 V1.0
 * @文件名 WeekDateJobBean.java
 */
public class WeekDateJobBean{
	private Logger log = Logger.getLogger(WorkDayService.class);
	
	@Resource
	private IBaseDAO iBaseDao;

	/**
	 * 每周一晚上23:59:00开始执行，更新项目的周期
	 */
	public void weekDateTime(){
		// TODO Auto-generated method stub
		System.out.println("TestJobBean..run................"); 
		String weekDate = Public.getSystemTimeToFormat("yyyy年")+"第"+Public.dayForWeekStr(Calendar.WEEK_OF_YEAR) + "周(" +Public.getSystemTimeToFormat("MM月") +")";
		String HQL = "From SqSysparaTab sqSysparaTab where sqSysparaTab.sysId='99999'";
		List sysParaTab = iBaseDao.find(HQL);
		SqSysparaTab sqSysparaTab = (SqSysparaTab)sysParaTab.get(0);
		sqSysparaTab.setFieldDesc(weekDate);
		iBaseDao.update(sqSysparaTab);
		log.info("系统周期已经更新为:" + weekDate );
	}

}
