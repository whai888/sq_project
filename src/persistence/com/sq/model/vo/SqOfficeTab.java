package com.sq.model.vo;

/**
 * SqOfficeTab entity. @author MyEclipse Persistence Tools
 */

public class SqOfficeTab implements java.io.Serializable {

	// Fields

	private String officeId;
	private String officeName;
	private String status;
	private String type ;
	private Integer indexNo ;

	// Constructors

	/** default constructor */
	public SqOfficeTab() {
	}

	/** full constructor */
	public SqOfficeTab(String officeName, String status) {
		this.officeName = officeName;
		this.status = status;
	}

	// Property accessors

	public String getOfficeId() {
		return this.officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getOfficeName() {
		return this.officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getIndexNo() {
		return indexNo;
	}

	public void setIndexNo(Integer indexNo) {
		this.indexNo = indexNo;
	}

}