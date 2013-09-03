package com.sq.model.vo;

/**
 * SqRoleMenuId entity. @author MyEclipse Persistence Tools
 */

public class SqRoleMenuId implements java.io.Serializable {

	// Fields

	private String roleId;
	private SysMenu sysMenu;

	// Constructors

	/** default constructor */
	public SqRoleMenuId() {
	}

	/** full constructor */
	public SqRoleMenuId(String roleId, SysMenu sysMenu) {
		this.roleId = roleId;
		this.sysMenu = sysMenu;
	}

	// Property accessors

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public SysMenu getSysMenu() {
		return sysMenu;
	}

	public void setSysMenu(SysMenu sysMenu) {
		this.sysMenu = sysMenu;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SqRoleMenuId))
			return false;
		SqRoleMenuId castOther = (SqRoleMenuId) other;

		return ((this.getRoleId() == castOther.getRoleId()) || (this
				.getRoleId() != null
				&& castOther.getRoleId() != null && this.getRoleId().equals(
				castOther.getRoleId())))
				&& ((this.getSysMenu() == castOther.getSysMenu()) || (this
						.getSysMenu() != null
						&& castOther.getSysMenu() != null && this.getSysMenu()
						.equals(castOther.getSysMenu())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getRoleId() == null ? 0 : this.getRoleId().hashCode());
		result = 37 * result
				+ (getSysMenu() == null ? 0 : this.getSysMenu().hashCode());
		return result;
	}

}