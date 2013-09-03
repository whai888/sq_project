package com.sq.model.vo;

/**
 * SqUserMenuId entity. @author MyEclipse Persistence Tools
 */

public class SqDeptUsermanagerId implements java.io.Serializable {

	// Fields

	private SqUserTab userTab;
	private String deptNo;

	// Constructors

	/** default constructor */
	public SqDeptUsermanagerId() {
	}

	/** full constructor */
	public SqDeptUsermanagerId(SqUserTab userTab, String deptNo) {
		this.userTab = userTab;
		this.deptNo = deptNo;
	}

	// Property accessors



	public String getDeptNo() {
		return deptNo;
	}

	public SqUserTab getUserTab() {
		return userTab;
	}

	public void setUserTab(SqUserTab userTab) {
		this.userTab = userTab;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SqDeptUsermanagerId))
			return false;
		SqDeptUsermanagerId castOther = (SqDeptUsermanagerId) other;

		return ((this.getUserTab() == castOther.getUserTab()) || (this
				.getUserTab() != null
				&& castOther.getUserTab() != null && this.getUserTab().equals(
				castOther.getUserTab())))
				&& ((this.getDeptNo() == castOther.getDeptNo()) || (this
						.getDeptNo() != null
						&& castOther.getDeptNo() != null && this.getDeptNo()
						.equals(castOther.getDeptNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUserTab() == null ? 0 : this.getUserTab().hashCode());
		result = 37 * result
				+ (getDeptNo() == null ? 0 : this.getDeptNo().hashCode());
		return result;
	}

}