package com.sq.model.vo;

import java.util.Set;

/**
 * SysMenuTools entity. @author MyEclipse Persistence Tools
 */

public class SysMenuTools implements java.io.Serializable {

	// Fields

	private String toolsId;
	private String menuId;
	private String remark;
	private String imgPath;
	private String jumpPath;
	private Integer indexNo;
	private String status;
	private String upToolsId;
	private Set<SysMenuTools> toolsSet ;

	// Constructors

	/** default constructor */
	public SysMenuTools() {
	}

	/** minimal constructor */
	public SysMenuTools(String toolsId, String menuId, String remark,
			String imgPath, String jumpPath, String status) {
		this.toolsId = toolsId;
		this.menuId = menuId;
		this.remark = remark;
		this.imgPath = imgPath;
		this.jumpPath = jumpPath;
		this.status = status;
	}

	/** full constructor */
	public SysMenuTools(String toolsId, String menuId, String remark,
			String imgPath, String jumpPath, Integer indexNo, String status) {
		this.toolsId = toolsId;
		this.menuId = menuId;
		this.remark = remark;
		this.imgPath = imgPath;
		this.jumpPath = jumpPath;
		this.indexNo = indexNo;
		this.status = status;
	}

	// Property accessors

	public String getToolsId() {
		return this.toolsId;
	}

	public void setToolsId(String toolsId) {
		this.toolsId = toolsId;
	}

	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getImgPath() {
		return this.imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getJumpPath() {
		return this.jumpPath;
	}

	public void setJumpPath(String jumpPath) {
		this.jumpPath = jumpPath;
	}

	public Integer getIndexNo() {
		return this.indexNo;
	}

	public void setIndexNo(Integer indexNo) {
		this.indexNo = indexNo;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUpToolsId() {
		return upToolsId;
	}

	public void setUpToolsId(String upToolsId) {
		this.upToolsId = upToolsId;
	}

	public Set getToolsSet() {
		return toolsSet;
	}

	public void setToolsSet(Set<SysMenuTools> toolsSet) {
		this.toolsSet = toolsSet;
	}

}