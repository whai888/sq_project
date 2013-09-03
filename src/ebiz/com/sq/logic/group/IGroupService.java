package com.sq.logic.group;

import java.util.List;

import com.sq.exception.SystemException;
import com.sq.model.vo.SqDeptTab;
import com.sq.model.vo.SqGroupTab;

public interface IGroupService {
	public void saveGroup(SqGroupTab sqGroupTab) throws SystemException;

	public void updateGroup(SqGroupTab sqGroupTab) throws SystemException ;

	public SqGroupTab findById(String groupId) throws SystemException;

	public List findUserByGroupNo(String groupNo) ;
		
	public List findByAll();
	
	public List findGroupBydeptNo(SqDeptTab sqdeptTab);

}
