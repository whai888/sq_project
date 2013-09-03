package com.sq.model.vo;

/**
 * SqUserRole entity. @author MyEclipse Persistence Tools
 */

public class SqUserRole implements java.io.Serializable {

	// Fields

	private SqUserRoleId id;

	// Constructors

	/** default constructor */
	public SqUserRole() {
	}

	/** full constructor */
	public SqUserRole(SqUserRoleId id) {
		this.id = id;
	}

	// Property accessors

	public SqUserRoleId getId() {
		return this.id;
	}

	public void setId(SqUserRoleId id) {
		this.id = id;
	}

}