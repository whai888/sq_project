package com.sq.logic.workday;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sq.exception.SystemException;
import com.sq.model.vo.SqGroupTab;
import com.sq.model.vo.SqProjectInfo;
import com.sq.model.vo.SqUserTab;
import com.sq.model.vo.SqWorkdayManager;

/**
 *
 *@autor whai
 */
public interface IWorkDayService {

	public void myworkDayEditAdd(SqUserTab sqUserTabTemp , SqWorkdayManager sqWorkdayManager) throws SystemException ;
	public void myworkProjectEditAdd(SqUserTab sqUserTabTemp , SqWorkdayManager sqWorkdayManager) throws SystemException ;
	public List findByExample(SqWorkdayManager sqWorkdayManager, String flag ) throws SystemException ;
	public void myworkDayEditDelete(String workId , String projectId) throws SystemException ;
	public SqWorkdayManager findById(SqWorkdayManager sqWorkdayManager) throws SystemException;
	public void myworkDayEditUpdate(SqWorkdayManager sqWorkdayManager , SqUserTab sqUserTabTemp, String flag) throws SystemException;
	public Map<SqProjectInfo, List<SqWorkdayManager>> auditWorkDay(SqUserTab sqUserTab) throws SystemException ;
	public List myWorkView(SqUserTab sqUserTabTemp , String startDate , String endDate , String type)  throws SystemException;
	public void myworkGroupEditAdd(SqUserTab sqUserTabTemp , SqWorkdayManager sqWorkdayManager) throws SystemException ;
	public List findWorkDayByUserAll() ;
	public void myWorkSub(SqUserTab sqUserTab, String type) throws SystemException;
	public List deptWorkDayList(SqGroupTab sqGroupTab , SqWorkdayManager sqWorkdayManager , String type) throws SystemException;
	public SqWorkdayManager myworkDeptEditAdd(SqUserTab sqUserTabTemp , SqWorkdayManager sqWorkdayManager) throws SystemException ;
	public List findUserQuery(SqUserTab sqUserTabTemp, SqGroupTab sqGroupTab, SqProjectInfo sqProjectInfo) ;
	public List projectWorkDayList(SqUserTab sqUserTab , SqWorkdayManager sqWorkdayManager , String type) ;
	public List groupWorkDayList(SqUserTab sqUserTab , SqWorkdayManager sqWorkdayManager , String type);
	public Map<SqGroupTab , List<SqProjectInfo>> findProjectQuery(SqUserTab sqUserTabTemp) ;
	public List findGroupQuery(SqUserTab sqUserTabTemp) ;
	public String isUserSubWork(SqUserTab sqUserTab , String type , String weekDate) ;
}
