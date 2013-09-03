package com.sq.model.vo;

/**
 * SqSysparaTab entity. @author MyEclipse Persistence Tools
 */

public class SqSysparaTab implements java.io.Serializable {

	// Fields

	private String sysId;
	private String sysTable;
	private String field;
	private String fieldValue;
	private String fieldDesc;

	// Constructors

	/** default constructor */
	public SqSysparaTab() {
	}

	/** minimal constructor */
	public SqSysparaTab(String sysTable, String field, String fieldValue) {
		this.sysTable = sysTable;
		this.field = field;
		this.fieldValue = fieldValue;
	}

	/** full constructor */
	public SqSysparaTab(String sysTable, String field, String fieldValue,
			String fieldDesc) {
		this.sysTable = sysTable;
		this.field = field;
		this.fieldValue = fieldValue;
		this.fieldDesc = fieldDesc;
	}

	// Property accessors

	public String getSysId() {
		return this.sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public String getSysTable() {
		return this.sysTable;
	}

	public void setSysTable(String sysTable) {
		this.sysTable = sysTable;
	}

	public String getField() {
		return this.field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getFieldValue() {
		return this.fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public String getFieldDesc() {
		return this.fieldDesc;
	}

	public void setFieldDesc(String fieldDesc) {
		this.fieldDesc = fieldDesc;
	}

}