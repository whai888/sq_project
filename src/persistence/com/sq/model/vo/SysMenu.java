package com.sq.model.vo;

import java.util.Set;

/**
 * SysMenu entity. @author MyEclipse Persistence Tools
 */

public class SysMenu implements java.io.Serializable {

	// Fields

	private String menuId;
	private String menuName;
	private String showUrl;
	private String showInfo ;
	private String upId;
	private Integer indexNo;
	private String status;
	private Set<SysMenuTools> menuTools;

	// Constructors

	/** default constructor */
	public SysMenu() {
	}

	/** minimal constructor */
	public SysMenu(String menuId, String menuName,String showUrl, String showInfo , String upId, String status) {
		this.menuId = menuId;
		this.menuName = menuName;
		this.upId = upId;
		this.status = status;
		this.showUrl = showUrl ;
		this.showInfo = showInfo ;
	}

	/** full constructor */
	public SysMenu(String menuId, String menuName, String upId,
			Integer indexNo, String status) {
		this.menuId = menuId;
		this.menuName = menuName;
		this.upId = upId;
		this.indexNo = indexNo;
		this.status = status;
	}

	// Property accessors

	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getUpId() {
		return this.upId;
	}

	public void setUpId(String upId) {
		this.upId = upId;
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

	public String getShowUrl() {
		return showUrl;
	}

	public void setShowUrl(String showUrl) {
		this.showUrl = showUrl;
	}

	public String getShowInfo() {
		return showInfo;
	}

	public void setShowInfo(String showInfo) {
		this.showInfo = showInfo;
	}

	public Set getMenuTools() {
		return menuTools;
	}

	public void setMenuTools(Set<SysMenuTools> menuTools) {
		this.menuTools = menuTools;
	}

}