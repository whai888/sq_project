package com.sq.model.vo;

import java.util.Date;

/**
 * SqUserlogTab entity. @author MyEclipse Persistence Tools
 */

public class SqUserlogTab implements java.io.Serializable {

	// Fields

	private String seqNo;
	private String userNo;
	private String ip;
	private String opDesc;
	private Date opDate;
	private String opTime;
	private String status;
	private String exception ;

	// Constructors

	/** default constructor */
	public SqUserlogTab() {
	}

	/** minimal constructor */
	public SqUserlogTab(String userNo, String opDesc, Date opDate,
			String opTime, String status) {
		this.userNo = userNo;
		this.opDesc = opDesc;
		this.opDate = opDate;
		this.opTime = opTime;
		this.status = status;
	}

	/** full constructor */
	public SqUserlogTab(String userNo, String ip, String opDesc, Date opDate,
			String opTime, String status) {
		this.userNo = userNo;
		this.ip = ip;
		this.opDesc = opDesc;
		this.opDate = opDate;
		this.opTime = opTime;
		this.status = status;
	}

	// Property accessors

	public String getSeqNo() {
		return this.seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getUserNo() {
		return this.userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getOpDesc() {
		return this.opDesc;
	}

	public void setOpDesc(String opDesc) {
		this.opDesc = opDesc;
	}

	public Date getOpDate() {
		return this.opDate;
	}

	public void setOpDate(Date opDate) {
		this.opDate = opDate;
	}

	public String getOpTime() {
		return this.opTime;
	}

	public void setOpTime(String opTime) {
		this.opTime = opTime;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

}