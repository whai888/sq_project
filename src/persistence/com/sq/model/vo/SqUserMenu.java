package com.sq.model.vo;

/**
 * SqUserMenu entity. @author MyEclipse Persistence Tools
 */

public class SqUserMenu implements java.io.Serializable {

	// Fields

	private SqUserMenuId id;

	// Constructors

	/** default constructor */
	public SqUserMenu() {
	}

	/** full constructor */
	public SqUserMenu(SqUserMenuId id) {
		this.id = id;
	}

	// Property accessors

	public SqUserMenuId getId() {
		return this.id;
	}

	public void setId(SqUserMenuId id) {
		this.id = id;
	}

}