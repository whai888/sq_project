package com.sq.logic.dept;

import java.util.List;

import com.sq.exception.SystemException;
import com.sq.model.vo.SqDeptTab;
import com.sq.model.vo.SqUserTab;

public interface IDeptService {
	public void saveDept(SqDeptTab sqDeptTab , String userDeptList) throws SystemException;

	public void updateDept(SqDeptTab sqDeptTab, String userDeptList) throws SystemException ;

	public SqDeptTab findById(String deptId);

	public List findUserByDept(SqUserTab sqUserTab);
	
	public List findByAll();

}
