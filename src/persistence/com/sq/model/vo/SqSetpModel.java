package com.sq.model.vo;

/**
 * SqSetpModel entity. @author MyEclipse Persistence Tools
 */

public class SqSetpModel implements java.io.Serializable {

	// Fields

	private String stepId;
	private String upStepId;
	private String stepOrder;
	private String stepName;
	private String stepRemark;
	private Integer stepDay;
	private String dayStatus;
	private String keyStatus;
	private Integer upPercent;
	private Integer lagDay;
	private Integer succPercent;

	// Constructors

	/** default constructor */
	public SqSetpModel() {
	}

	/** minimal constructor */
	public SqSetpModel(String stepId) {
		this.stepId = stepId;
	}

	/** full constructor */
	public SqSetpModel(String stepId, String upStepId, String stepOrder,
			String stepName, String stepRemark, Integer stepDay,
			String dayStatus, String keyStatus, Integer upPercent,
			Integer lagDay, Integer succPercent) {
		this.stepId = stepId;
		this.upStepId = upStepId;
		this.stepOrder = stepOrder;
		this.stepName = stepName;
		this.stepRemark = stepRemark;
		this.stepDay = stepDay;
		this.dayStatus = dayStatus;
		this.keyStatus = keyStatus;
		this.upPercent = upPercent;
		this.lagDay = lagDay;
		this.succPercent = succPercent;
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

	public Integer getStepDay() {
		return this.stepDay;
	}

	public void setStepDay(Integer stepDay) {
		this.stepDay = stepDay;
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

}