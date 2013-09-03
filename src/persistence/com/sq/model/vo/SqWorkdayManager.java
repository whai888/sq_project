package com.sq.model.vo;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * SqWorkdayManager entity. @author MyEclipse Persistence Tools
 */

public class SqWorkdayManager implements java.io.Serializable {

	// Fields

	private String workdayId ;
	private String type;
	private Date workDate;
	private Date workTime ;
//	private String userId;
	private String auditUserId;
	private Float taskTime;
	private String workLoad;
	private String workUnits;
	private Integer complePercen;
	private String workContent;
	private String noWorkContent;
	private String workNextPlan ;
	private String discussProblem ;
	private String workSug ;
	private String documentId;
	private String auditStatus;
	private String taskStatus;
	private String workStartDate;
	private String workEndDate;
	private String workCycle ;
	private String remark1 ;
	private String weekDate ;
	private Date weekDate1;
	private Date weekTime ;
	private SqProjectInfo sqProjectInfo ;
	private SqUserTab sqUserTab ;
	private SqGroupTab sqGroupTab ;
	private Set<SqDocmentManager> sqDocumentManageSet;
	private SqProjectStep sqProjectStep ;

	// Constructors

	/** default constructor */
	public SqWorkdayManager() {
	}

	/** full constructor */
	public SqWorkdayManager(String type,
			Date workDate, String userId, String auditUserId, String taskId,
			Float taskTime, String workLoad, Integer complePercen,
			String workContent, String documentId, String auditStatus,
			String taskStatus) {
		this.type = type;
		this.workDate = workDate;
		this.auditUserId = auditUserId;
		this.taskTime = taskTime;
		this.workLoad = workLoad;
		this.complePercen = complePercen;
		this.workContent = workContent;
		this.documentId = documentId;
		this.auditStatus = auditStatus;
		this.taskStatus = taskStatus;
	}

	// Property accessors

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getWorkDate() {
		return workDate;
	}

	public void setWorkDate(Date workDate) {
		this.workDate = workDate;
	}

	public String getAuditUserId() {
		return this.auditUserId;
	}

	public String getWorkUnits() {
		return workUnits;
	}

	public void setWorkUnits(String workUnits) {
		this.workUnits = workUnits;
	}

	public void setAuditUserId(String auditUserId) {
		this.auditUserId = auditUserId;
	}

	public Float getTaskTime() {
		return this.taskTime;
	}

	public void setTaskTime(Float taskTime) {
		this.taskTime = taskTime;
	}

	public String getWorkLoad() {
		return this.workLoad;
	}

	public void setWorkLoad(String workLoad) {
		this.workLoad = workLoad;
	}

	public Integer getComplePercen() {
		return this.complePercen;
	}

	public void setComplePercen(Integer complePercen) {
		this.complePercen = complePercen;
	}

	public String getWorkContent() {
		if(this.workContent == null)
			return "";
		return this.workContent;
	}

	public void setWorkContent(String workContent) {
		this.workContent = workContent;
	}

	public String getDocumentId() {
		return this.documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getAuditStatus() {
		return this.auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getTaskStatus() {
		return this.taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	
	public String getWeekDate() {
		return weekDate;
	}

	public void setWeekDate(String weekDate) {
		this.weekDate = weekDate;
	}

	public Set<SqDocmentManager> getSqDocumentManageSet() {
		return sqDocumentManageSet;
	}

	public void setSqDocumentManageSet(Set<SqDocmentManager> sqDocumentManageSet) {
		this.sqDocumentManageSet = sqDocumentManageSet;
	}

	public SqUserTab getSqUserTab() {
		return sqUserTab;
	}

	public void setSqUserTab(SqUserTab sqUserTab) {
		this.sqUserTab = sqUserTab;
	}

	public SqProjectStep getSqProjectStep() {
		return sqProjectStep;
	}

	public void setSqProjectStep(SqProjectStep sqProjectStep) {
		this.sqProjectStep = sqProjectStep;
	}

	public String getNoWorkContent() {
		if(this.noWorkContent == null)
			return "";
		return noWorkContent;
	}

	public void setNoWorkContent(String noWorkContent) {
		this.noWorkContent = noWorkContent;
	}

	public String getWorkNextPlan() {
		if(this.workNextPlan == null)
			return "";
		return workNextPlan;
	}

	public void setWorkNextPlan(String workNextPlan) {
		this.workNextPlan = workNextPlan;
	}

	public String getDiscussProblem() {
		if(this.discussProblem == null)
			return "";
		return discussProblem;
	}

	public void setDiscussProblem(String discussProblem) {
		this.discussProblem = discussProblem;
	}

	public String getWorkSug() {
		if(this.workSug == null)
			return "";
		return workSug;
	}

	public void setWorkSug(String workSug) {
		this.workSug = workSug;
	}

	public String getWorkStartDate() {
		return workStartDate;
	}

	public void setWorkStartDate(String workStartDate) {
		this.workStartDate = workStartDate;
	}

	public String getWorkEndDate() {
		return workEndDate;
	}

	public void setWorkEndDate(String workEndDate) {
		this.workEndDate = workEndDate;
	}

	public String getWorkCycle() {
		return workCycle;
	}

	public void setWorkCycle(String workCycle) {
		this.workCycle = workCycle;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public SqGroupTab getSqGroupTab() {
		return sqGroupTab;
	}

	public void setSqGroupTab(SqGroupTab sqGroupTab) {
		this.sqGroupTab = sqGroupTab;
	}

	public String getWorkdayId() {
		return workdayId;
	}

	public void setWorkdayId(String workdayId) {
		this.workdayId = workdayId;
	}

	public SqProjectInfo getSqProjectInfo() {
		return sqProjectInfo;
	}

	public void setSqProjectInfo(SqProjectInfo sqProjectInfo) {
		this.sqProjectInfo = sqProjectInfo;
	}

	public Date getWorkTime() {
		return workTime;
	}

	public void setWorkTime(Date workTime) {
		this.workTime = workTime;
	}

	public Date getWeekDate1() {
		return weekDate1;
	}

	public void setWeekDate1(Date weekDate1) {
		this.weekDate1 = weekDate1;
	}

	public Date getWeekTime() {
		return weekTime;
	}

	public void setWeekTime(Date weekTime) {
		this.weekTime = weekTime;
	}
	
}