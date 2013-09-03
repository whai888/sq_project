package com.sq.model.vo;

import java.util.Date;

/**
 * SqProjectStep entity. @author MyEclipse Persistence Tools
 */

public class SqProjectStep implements java.io.Serializable {

	// Fields

	private String stepId;
	private String upStepId;
	private String projectId;
	private String stepOrder;
	private String stepName;
	private String stepRemark;
	private Date startDate;
	private Date endDate;
	private String dayStatus;
	private String keyStatus;
	private Integer upPercent;
	private Integer lagDay;
	private Integer succPercent;
	private String stepStatus;
	private String status ;	//新增一个标志,用来标识显示阶段的颜色

	// Constructors

	/** default constructor */
	public SqProjectStep() {
	}

	/** minimal constructor */
	public SqProjectStep(String stepId) {
		this.stepId = stepId;
	}

	/** full constructor */
	public SqProjectStep(String stepId, String upStepId, String projectId,
			String stepOrder, String stepName, String stepRemark,
			Date startDate, Date endDate, String dayStatus, String keyStatus,
			Integer upPercent, Integer lagDay, Integer succPercent,
			String stepStatus) {
		this.stepId = stepId;
		this.upStepId = upStepId;
		this.projectId = projectId;
		this.stepOrder = stepOrder;
		this.stepName = stepName;
		this.stepRemark = stepRemark;
		this.startDate = startDate;
		this.endDate = endDate;
		this.dayStatus = dayStatus;
		this.keyStatus = keyStatus;
		this.upPercent = upPercent;
		this.lagDay = lagDay;
		this.succPercent = succPercent;
		this.stepStatus = stepStatus;
	}

	// Property accessors

	public String getStepId() {
		return this.stepId;
	}

	public void setStepId(String stepId) {
		this.stepId = stepId;
	}

	public String getUpStepId() {
		return this.upStepId;
	}

	public void setUpStepId(String upStepId) {
		this.upStepId = upStepId;
	}

	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getStepOrder() {
		return this.stepOrder;
	}

	public void setStepOrder(String stepOrder) {
		this.stepOrder = stepOrder;
	}

	public String getStepName() {
		return this.stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public String getStepRemark() {
		return this.stepRemark;
	}

	public void setStepRemark(String stepRemark) {
		this.stepRemark = stepRemark;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDayStatus() {
		return this.dayStatus;
	}

	public void setDayStatus(String dayStatus) {
		this.dayStatus = dayStatus;
	}

	public String getKeyStatus() {
		return this.keyStatus;
	}

	public void setKeyStatus(String keyStatus) {
		this.keyStatus = keyStatus;
	}

	public Integer getUpPercent() {
		return this.upPercent;
	}

	public void setUpPercent(Integer upPercent) {
		this.upPercent = upPercent;
	}

	public Integer getLagDay() {
		return this.lagDay;
	}

	public void setLagDay(Integer lagDay) {
		this.lagDay = lagDay;
	}

	public Integer getSuccPercent() {
		return this.succPercent;
	}

	public void setSuccPercent(Integer succPercent) {
		this.succPercent = succPercent;
	}

	public String getStepStatus() {
		return this.stepStatus;
	}

	public void setStepStatus(String stepStatus) {
		this.stepStatus = stepStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}