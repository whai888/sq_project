package com.sq.model.vo;

import java.util.Date;
import java.util.Set;

/**
 * SqDeptTab entity. @author MyEclipse Persistence Tools
 */

public class SqDeptTab implements java.io.Serializable , Comparable<SqDeptTab>{

	// Fields

	private String deptNo;
	private String deptName;
	private String deptUser;
	private String status;
	private Date succDate;
	private String modifyDate;
	private String remark;
	private String weekStatus;
	//部门负责人信息
	private SqUserTab sqUserTab ;
	private Set<SqDeptUsermanager> userSet ;

	// Constructors

	/** default constructor */
	public SqDeptTab() {
	}

	/** full constructor */
	public SqDeptTab(String deptName, String deptUser, String status,
			Date succDate, String modifyDate, String remark) {
		this.deptName = deptName;
		this.deptUser = deptUser;
		this.status = status;
		this.succDate = succDate;
		this.modifyDate = modifyDate;
		this.remark = remark;
	}

	// Property accessors

	public String getDeptNo() {
		return this.deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptUser() {
		return this.deptUser;
	}

	public void setDeptUser(String deptUser) {
		this.deptUser = deptUser;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getSuccDate() {
		return this.succDate;
	}

	public void setSuccDate(Date succDate) {
		this.succDate = succDate;
	}

	public String getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public SqUserTab getSqUserTab() {
		return sqUserTab;
	}

	public void setSqUserTab(SqUserTab sqUserTab) {
		this.sqUserTab = sqUserTab;
	}

	public Set<SqDeptUsermanager> getUserSet() {
		return userSet;
	}

	public void setUserSet(Set<SqDeptUsermanager> userSet) {
		this.userSet = userSet;
	}

	public String getWeekStatus() {
		return weekStatus;
	}

	public void setWeekStatus(String weekStatus) {
		this.weekStatus = weekStatus;
	}

	public int compareTo(SqDeptTab sqDeptTab) {
		// TODO Auto-generated method stub
		if(this.getDeptNo().compareToIgnoreCase(sqDeptTab.getDeptNo()) > 0){
			return 1 ;
		}
		if(this.getDeptNo().compareToIgnoreCase(sqDeptTab.getDeptNo()) < 0){
			return -1 ;
		}
		return 0;
	}

}