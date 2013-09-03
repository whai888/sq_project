package com.sq.action.group;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.sq.exception.SystemException;
import com.sq.logic.group.IGroupService;
import com.sq.logic.tools.IUserLog;
import com.sq.logic.user.IUserService;
import com.sq.model.vo.SqGroupTab;
import com.sq.model.vo.SqUserTab;
import com.sq.tools.Public;
import com.sq.tools.SortList;
import com.sq.vo.ErrorForm;

public class GroupAction {
	@Resource
	private IGroupService iGroupService;
	@Resource
	private IUserService iUserService;
	@Resource
	private IUserLog iUserLog ;
	private SqGroupTab sqGroupTab;
	private ErrorForm errorForm = new ErrorForm() ;
	private HttpServletRequest request = ServletActionContext.getRequest();

	public String saveGroup() {
		try {
			iGroupService.saveGroup(sqGroupTab);
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error" ;
		}
		
		return "success";
	}

	/**
	 * 项目组信息修改，可修改项目组名称、项目组负责人工号、项目组成立时间、备注信息和状态
	 * @return
	 */
	public String updateGroup() {
		try {
			SqGroupTab sqGroupTabTemp = iGroupService.findById(sqGroupTab.getGroupNo()) ;
			sqGroupTabTemp.setGroupName(sqGroupTab.getGroupName());
			sqGroupTabTemp.setSqUserTab(sqGroupTab.getSqUserTab());
			sqGroupTabTemp.setSuccDate(sqGroupTab.getSuccDate());
			sqGroupTabTemp.setSqDeptTab(sqGroupTab.getSqDeptTab());
			sqGroupTabTemp.setRemark(sqGroupTab.getRemark());
			sqGroupTabTemp.setStatus(sqGroupTab.getStatus());
			sqGroupTabTemp.setModifyDate(Public.getSystemTimeToFormat("yyyy-MM-dd HH:mm:ss"));
			iGroupService.updateGroup(sqGroupTabTemp);
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error" ;
		}
		return "success";
	}

	/**
	 * 查询项目组信息，及项目组下所有的员工
	 * @return
	 */
	public String listGroup() {
		String returnPage = request.getParameter("returnPage");
		List list = iGroupService.findByAll() ;
		Map map = new LinkedHashMap();
		for (int i = 0; i < list.size(); i++) {
			SqGroupTab sqGroupTab = (SqGroupTab)list.get(i);
			SqUserTab sqUserTab = new SqUserTab();
			sqUserTab.setSqGroupTab(sqGroupTab) ;
			//按照员工编号排序
			List userList = iUserService.userByGroup((sqUserTab));
			SortList<SqGroupTab> sortList = new SortList<SqGroupTab>();  
			sortList.Sort(userList, "getEnterYear", "desc");  
			
			map.put(sqGroupTab, userList);
		}
		request.setAttribute("groupMap", map) ;
		request.setAttribute("grouplist", list) ;
		return returnPage;
	}
	
	public String findUpdateId() {
		try {
			sqGroupTab = iGroupService.findById(sqGroupTab.getGroupNo()) ;
			request.setAttribute("sqUserTab", sqGroupTab) ;
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error" ;
		}
		return "groupupdate" ;
	}

	public SqGroupTab getSqGroupTab() {
		return sqGroupTab;
	}

	public void setSqGroupTab(SqGroupTab sqGroupTab) {
		this.sqGroupTab = sqGroupTab;
	}

	public ErrorForm getErrorForm() {
		return errorForm;
	}

	public void setErrorForm(ErrorForm errorForm) {
		this.errorForm = errorForm;
	}

	
}
