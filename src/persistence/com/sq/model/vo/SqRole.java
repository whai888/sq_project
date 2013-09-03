package com.sq.model.vo;

import java.util.Date;

/**
 * SqRole entity. @author MyEclipse Persistence Tools
 */

public class SqRole implements java.io.Serializable {

	// Fields

	private String roleId;
	private String roleName;
	private String remark;
//	private String creatUserId;
	private Date createDate;
//	private String modifyUserId;
	private Date modifyDate;
	private SqUserTab createUserTab;
	private SqUserTab modifyUserTab;

	// Constructors

	/** default constructor */
	public SqRole() {
	}

	/** minimal constructor */
	public SqRole(String roleId) {
		this.roleId = roleId;
	}

	/** full constructor */
	public SqRole(String roleId, String roleName, String remark,
			String creatUserId, Date createDate, String modifyUserId,
			Date modifyDate) {
		this.roleId = roleId;
		this.roleName = roleName;
		this.remark = remark;
//		this.creatUserId = creatUserId;
		this.createDate = createDate;
//		this.modifyUserId = modifyUserId;
		this.modifyDate = modifyDate;
	}

	// Property accessors

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

//	public String getCreatUserId() {
//		return this.creatUserId;
//	}
//
//	public void setCreatUserId(String creatUserId) {
//		this.creatUserId = creatUserId;
//	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

//	public String getModifyUserId() {
//		return this.modifyUserId;
//	}
//
//	public void setModifyUserId(String modifyUserId) {
//		this.modifyUserId = modifyUserId;
//	}

	public Date getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public SqUserTab getCreateUserTab() {
		return createUserTab;
	}

	public void setCreateUserTab(SqUserTab createUserTab) {
		this.createUserTab = createUserTab;
	}

	public SqUserTab getModifyUserTab() {
		return modifyUserTab;
	}

	public void setModifyUserTab(SqUserTab modifyUserTab) {
		this.modifyUserTab = modifyUserTab;
	}

}