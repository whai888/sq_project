package com.sq.action.dept;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.sq.exception.SystemException;
import com.sq.logic.dept.IDeptService;
import com.sq.logic.tools.IUserLog;
import com.sq.logic.user.IUserService;
import com.sq.model.vo.SqDeptTab;
import com.sq.model.vo.SqUserTab;
import com.sq.tools.Public;
import com.sq.tools.SortList;
import com.sq.vo.ErrorForm;

public class DeptAction {
	@Resource
	private IDeptService iDeptService;
	@Resource
	private IUserService iUserService;
	@Resource
	private IUserLog iUserLog ;
	private SqDeptTab sqDeptTab;
	private String zhuliuserId ;
	private ErrorForm errorForm = new ErrorForm() ;
	private HttpServletRequest request = ServletActionContext.getRequest();

	public String saveDept() {
		try {
			iDeptService.saveDept(sqDeptTab , zhuliuserId);
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error" ;
		}
		
		return "success";
	}

	/**
	 * 部门信息修改，可修改部门名称、部门负责人工号、部门成立时间、备注信息和状态
	 * @return
	 */
	public String updateDept() {
		try {
			iDeptService.updateDept(sqDeptTab , zhuliuserId);
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error" ;
		}
		return "success";
	}

	/**
	 * 查询部门信息，及部门下所有的员工
	 * @return
	 */
	public String listDept() {
		List list = iDeptService.findByAll() ;
		Map map = new LinkedHashMap();
		for (int i = 0; i < list.size(); i++) {
			SqDeptTab sqDeptTab = (SqDeptTab)list.get(i);
			SqUserTab sqUserTab = new SqUserTab();
			sqUserTab.setSqDeptTab(sqDeptTab) ;
			//按照员工编号排序
			List userList = iUserService.userByDept(sqUserTab) ;
//			SortList<SqDeptTab> sortList = new SortList<SqDeptTab>();  
//			sortList.Sort(userList, "getUserId", "desc");  
			
			map.put(sqDeptTab, userList);
		}
		request.setAttribute("deptMap", map) ;
		return "deptlist" ;
	}
	
	public String findUpdateId() {
		sqDeptTab = iDeptService.findById(sqDeptTab.getDeptNo()) ;
		request.setAttribute("sqUserTab", sqDeptTab) ;
		return "deptupdate" ;
	}

	public SqDeptTab getSqDeptTab() {
		return sqDeptTab;
	}

	public void setSqDeptTab(SqDeptTab sqDeptTab) {
		this.sqDeptTab = sqDeptTab;
	}

	public ErrorForm getErrorForm() {
		return errorForm;
	}

	public void setErrorForm(ErrorForm errorForm) {
		this.errorForm = errorForm;
	}

	public String getZhuliuserId() {
		return zhuliuserId;
	}

	public void setZhuliuserId(String zhuliuserId) {
		this.zhuliuserId = zhuliuserId;
	}

	
}
