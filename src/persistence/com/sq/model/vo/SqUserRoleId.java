package com.sq.model.vo;

/**
 * SqUserRoleId entity. @author MyEclipse Persistence Tools
 */

public class SqUserRoleId implements java.io.Serializable {

	// Fields

	private String userId;
	private SqRole sqRole;

	// Constructors

	/** default constructor */
	public SqUserRoleId() {
	}

	/** full constructor */
	public SqUserRoleId(String userId, SqRole sqRole) {
		this.userId = userId;
		this.sqRole = sqRole;
	}

	// Property accessors

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public SqRole getSqRole() {
		return sqRole;
	}

	public void setSqRole(SqRole sqRole) {
		this.sqRole = sqRole;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SqUserRoleId))
			return false;
		SqUserRoleId castOther = (SqUserRoleId) other;

		return ((this.getUserId() == castOther.getUserId()) || (this
				.getUserId() != null
				&& castOther.getUserId() != null && this.getUserId().equals(
				castOther.getUserId())))
				&& ((this.getSqRole() == castOther.getSqRole()) || (this
						.getSqRole() != null
						&& castOther.getSqRole() != null && this.getSqRole()
						.equals(castOther.getSqRole())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUserId() == null ? 0 : this.getUserId().hashCode());
		result = 37 * result
				+ (getSqRole() == null ? 0 : this.getSqRole().hashCode());
		return result;
	}

}