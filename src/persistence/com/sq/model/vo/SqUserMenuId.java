package com.sq.model.vo;

/**
 * SqUserMenuId entity. @author MyEclipse Persistence Tools
 */

public class SqUserMenuId implements java.io.Serializable {

	// Fields

	private String userId;
	private String menuId;

	// Constructors

	/** default constructor */
	public SqUserMenuId() {
	}

	/** full constructor */
	public SqUserMenuId(String userId, String menuId) {
		this.userId = userId;
		this.menuId = menuId;
	}

	// Property accessors

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SqUserMenuId))
			return false;
		SqUserMenuId castOther = (SqUserMenuId) other;

		return ((this.getUserId() == castOther.getUserId()) || (this
				.getUserId() != null
				&& castOther.getUserId() != null && this.getUserId().equals(
				castOther.getUserId())))
				&& ((this.getMenuId() == castOther.getMenuId()) || (this
						.getMenuId() != null
						&& castOther.getMenuId() != null && this.getMenuId()
						.equals(castOther.getMenuId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUserId() == null ? 0 : this.getUserId().hashCode());
		result = 37 * result
				+ (getMenuId() == null ? 0 : this.getMenuId().hashCode());
		return result;
	}

}