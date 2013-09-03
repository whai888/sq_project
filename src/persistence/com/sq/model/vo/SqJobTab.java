package com.sq.model.vo;

/**
 * SqJobTab entity. @author MyEclipse Persistence Tools
 */

public class SqJobTab implements java.io.Serializable {

	// Fields

	private String jobId;
	private String jobName;
	private String status;
	private Integer indexNo;

	// Constructors

	/** default constructor */
	public SqJobTab() {
	}

	/** full constructor */
	public SqJobTab(String jobName, String status) {
		this.jobName = jobName;
		this.status = status;
	}

	// Property accessors

	public String getJobId() {
		return this.jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getJobName() {
		return this.jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getIndexNo() {
		return indexNo;
	}

	public void setIndexNo(Integer indexNo) {
		this.indexNo = indexNo;
	}

}