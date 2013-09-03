package com.sq.model.vo;

import java.util.Date;
import java.util.Set;

/**
 * SqGroupTab entity. @author MyEclipse Persistence Tools
 */

public class SqGroupTab implements java.io.Serializable, Comparable<SqGroupTab>{

	// Fields

	private String groupNo;
	private String groupName;
//	private String groupUser;
	private String status;
	private Date succDate;
	private String modifyDate;
	private String remark;
	private SqUserTab sqUserTab;
	private SqDeptTab sqDeptTab ;
	private Set<SqUserTab> userSet;

	// Constructors

	/** default constructor */
	public SqGroupTab() {
	}

	/** minimal constructor */
	public SqGroupTab(String groupNo) {
		this.groupNo = groupNo;
	}

	/** full constructor */
	public SqGroupTab(String groupNo, String groupName, String groupUser,
			String status, Date succDate, String modifyDate, String remark) {
		this.groupNo = groupNo;
		this.groupName = groupName;
		this.status = status;
		this.succDate = succDate;
		this.modifyDate = modifyDate;
		this.remark = remark;
	}

	// Property accessors

	public String getGroupNo() {
		return this.groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
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

	public Set<SqUserTab> getUserSet() {
		return userSet;
	}

	public void setUserSet(Set<SqUserTab> userSet) {
		this.userSet = userSet;
	}

	public SqDeptTab getSqDeptTab() {
		return sqDeptTab;
	}

	public void setSqDeptTab(SqDeptTab sqDeptTab) {
		this.sqDeptTab = sqDeptTab;
	}

	public int compareTo(SqGroupTab sqGroupTab) {
		// TODO Auto-generated method stub
		if(this.getGroupNo().compareToIgnoreCase(sqGroupTab.getGroupNo()) > 0){
			return 1 ;
		}
		if(this.getGroupNo().compareToIgnoreCase(sqGroupTab.getGroupNo()) < 0){
			return -1 ;
		}
		return 0;
	}

}