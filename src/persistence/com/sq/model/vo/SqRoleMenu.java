package com.sq.model.vo;

import java.util.Set;

/**
 * SqRoleMenu entity. @author MyEclipse Persistence Tools
 */

public class SqRoleMenu implements java.io.Serializable {

	// Fields

	private SqRoleMenuId id;
	private Set menuSet ;

	// Constructors

	/** default constructor */
	public SqRoleMenu() {
	}

	/** full constructor */
	public SqRoleMenu(SqRoleMenuId id) {
		this.id = id;
	}

	// Property accessors

	public SqRoleMenuId getId() {
		return this.id;
	}

	public void setId(SqRoleMenuId id) {
		this.id = id;
	}

	public Set getMenuSet() {
		return menuSet;
	}

	public void setMenuSet(Set menuSet) {
		this.menuSet = menuSet;
	}

}