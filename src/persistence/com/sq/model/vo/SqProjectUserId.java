package com.sq.model.vo;

/**
 * SqProjectUserId entity. @author MyEclipse Persistence Tools
 */

public class SqProjectUserId implements java.io.Serializable {

	// Fields

	private String projectId;
	private String userId;
	private String userType;

	// Constructors

	/** default constructor */
	public SqProjectUserId() {
	}

	/** full constructor */
	public SqProjectUserId(String projectId, String userId) {
		this.projectId = projectId;
		this.userId = userId;
	}

	// Property accessors

	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SqProjectUserId))
			return false;
		SqProjectUserId castOther = (SqProjectUserId) other;

		return ((this.getProjectId() == castOther.getProjectId()) || (this
				.getProjectId() != null
				&& castOther.getProjectId() != null && this.getProjectId()
				.equals(castOther.getProjectId())))
				&& ((this.getUserId() == castOther.getUserId()) || (this
						.getUserId() != null
						&& castOther.getUserId() != null && this.getUserId()
						.equals(castOther.getUserId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getProjectId() == null ? 0 : this.getProjectId().hashCode());
		result = 37 * result
				+ (getUserId() == null ? 0 : this.getUserId().hashCode());
		return result;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

}