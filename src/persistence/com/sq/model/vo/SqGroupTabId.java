package com.sq.model.vo;

import java.util.Date;

/**
 * SqGroupTabId entity. @author MyEclipse Persistence Tools
 */

public class SqGroupTabId implements java.io.Serializable {

	// Fields

	private String groupNo;
	private String groupName;
	private String groupUser;
	private String status;
	private Date succDate;
	private String modifyDate;
	private String remark;

	// Constructors

	/** default constructor */
	public SqGroupTabId() {
	}

	/** minimal constructor */
	public SqGroupTabId(String groupNo) {
		this.groupNo = groupNo;
	}

	/** full constructor */
	public SqGroupTabId(String groupNo, String groupName, String groupUser,
			String status, Date succDate, String modifyDate, String remark) {
		this.groupNo = groupNo;
		this.groupName = groupName;
		this.groupUser = groupUser;
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

	public String getGroupUser() {
		return this.groupUser;
	}

	public void setGroupUser(String groupUser) {
		this.groupUser = groupUser;
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

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SqGroupTabId))
			return false;
		SqGroupTabId castOther = (SqGroupTabId) other;

		return ((this.getGroupNo() == castOther.getGroupNo()) || (this
				.getGroupNo() != null
				&& castOther.getGroupNo() != null && this.getGroupNo().equals(
				castOther.getGroupNo())))
				&& ((this.getGroupName() == castOther.getGroupName()) || (this
						.getGroupName() != null
						&& castOther.getGroupName() != null && this
						.getGroupName().equals(castOther.getGroupName())))
				&& ((this.getGroupUser() == castOther.getGroupUser()) || (this
						.getGroupUser() != null
						&& castOther.getGroupUser() != null && this
						.getGroupUser().equals(castOther.getGroupUser())))
				&& ((this.getStatus() == castOther.getStatus()) || (this
						.getStatus() != null
						&& castOther.getStatus() != null && this.getStatus()
						.equals(castOther.getStatus())))
				&& ((this.getSuccDate() == castOther.getSuccDate()) || (this
						.getSuccDate() != null
						&& castOther.getSuccDate() != null && this
						.getSuccDate().equals(castOther.getSuccDate())))
				&& ((this.getModifyDate() == castOther.getModifyDate()) || (this
						.getModifyDate() != null
						&& castOther.getModifyDate() != null && this
						.getModifyDate().equals(castOther.getModifyDate())))
				&& ((this.getRemark() == castOther.getRemark()) || (this
						.getRemark() != null
						&& castOther.getRemark() != null && this.getRemark()
						.equals(castOther.getRemark())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getGroupNo() == null ? 0 : this.getGroupNo().hashCode());
		result = 37 * result
				+ (getGroupName() == null ? 0 : this.getGroupName().hashCode());
		result = 37 * result
				+ (getGroupUser() == null ? 0 : this.getGroupUser().hashCode());
		result = 37 * result
				+ (getStatus() == null ? 0 : this.getStatus().hashCode());
		result = 37 * result
				+ (getSuccDate() == null ? 0 : this.getSuccDate().hashCode());
		result = 37
				* result
				+ (getModifyDate() == null ? 0 : this.getModifyDate()
						.hashCode());
		result = 37 * result
				+ (getRemark() == null ? 0 : this.getRemark().hashCode());
		return result;
	}

}