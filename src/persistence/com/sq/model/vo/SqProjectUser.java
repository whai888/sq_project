package com.sq.model.vo;

import java.util.Date;

/**
 * SqProjectUser entity. @author MyEclipse Persistence Tools
 */

public class SqProjectUser implements java.io.Serializable {

	// Fields

	private SqProjectUserId id;
	private String remark;
	private Date startDate;
	private Date endDate;
	private String status;
	private SqUserTab sqUserTab ;

	// Constructors

	/** default constructor */
	public SqProjectUser() {
	}

	/** minimal constructor */
	public SqProjectUser(SqProjectUserId id) {
		this.id = id;
	}

	/** full constructor */
	public SqProjectUser(SqProjectUserId id, String remark, Date startDate,
			Date endDate, String status) {
		this.id = id;
		this.remark = remark;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
	}

	// Property accessors

	public SqProjectUserId getId() {
		return this.id;
	}

	public void setId(SqProjectUserId id) {
		this.id = id;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public SqUserTab getSqUserTab() {
		return sqUserTab;
	}

	public void setSqUserTab(SqUserTab sqUserTab) {
		this.sqUserTab = sqUserTab;
	}

}