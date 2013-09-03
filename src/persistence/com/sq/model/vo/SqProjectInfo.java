package com.sq.model.vo;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sq.tools.Public;

/**
 * SqProjectInfo entity. @author MyEclipse Persistence Tools
 */

public class SqProjectInfo implements java.io.Serializable , Comparable<SqProjectInfo>{

	// Fields

	private String projectId;
	private String projectName;
	private Date startDate;
	private Date advanceDate;
	private String model ;
//	private String userId;
	private String resume;
	private Integer forward ;
	private String status;
	private Map<String, String> currStepName ;
	private List sqProjectUserList ;
	private SqUserTab sqUserTab ;
	private Set<SqProjectStep> sqProjectStepSet ;
	private SqProjectStep sqProjectStep ;
	private SqDocmentManager sqDocmentManager ;
//	private String projectGroup ;
	//项目所属项目组
	private SqGroupTab sqGroupTab ;
	
	// Constructors

	/** default constructor */
	public SqProjectInfo() {
	}

	/** minimal constructor */
	public SqProjectInfo(String projectId) {
		this.projectId = projectId;
	}

	/** full constructor */
	public SqProjectInfo(String projectId, String projectName,
			Date startDate, Date advanceDate, String userId, String resume,
			String status) {
		this.projectId = projectId;
		this.projectName = projectName;
		this.startDate = startDate;
		this.advanceDate = advanceDate;
		this.resume = resume;
		this.status = status;
	}

	// Property accessors

	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getAdvanceDate() {
		return this.advanceDate;
	}

	public void setAdvanceDate(Date advanceDate) {
		this.advanceDate = advanceDate;
	}

	public String getResume() {
		return this.resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public String getStatus() {
		return this.status;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List getSqProjectUserList() {
		return sqProjectUserList;
	}

	public void setSqProjectUserList(List sqProjectUserList) {
		this.sqProjectUserList = sqProjectUserList;
	}

	
	public Set<SqProjectStep> getSqProjectStepSet() {
		return sqProjectStepSet;
	}

	public void setSqProjectStepSet(Set<SqProjectStep> sqProjectStepSet) {
		this.sqProjectStepSet = sqProjectStepSet;
	}

	public SqDocmentManager getSqDocmentManager() {
		return sqDocmentManager;
	}

	public void setSqDocmentManager(SqDocmentManager sqDocmentManager) {
		this.sqDocmentManager = sqDocmentManager;
	}

	public SqUserTab getSqUserTab() {
		return sqUserTab;
	}

	public void setSqUserTab(SqUserTab sqUserTab) {
		this.sqUserTab = sqUserTab;
	}

	public Integer getForward() {
		return forward;
	}

	public void setForward(Integer forward) {
		this.forward = forward;
	}

	public SqGroupTab getSqGroupTab() {
		return sqGroupTab;
	}

	public void setSqGroupTab(SqGroupTab sqGroupTab) {
		this.sqGroupTab = sqGroupTab;
	}

	public SqProjectStep getSqProjectStep() {
		return sqProjectStep;
	}

	public void setSqProjectStep(SqProjectStep sqProjectStep) {
		this.sqProjectStep = sqProjectStep;
	}

	public SqProjectStep getCurrProjectStep() {
		for (SqProjectStep temp : sqProjectStepSet) {
			if(temp.getStepStatus().equals("1")){
				return temp ;
			}
		}
		//没有里程碑的处理
		SqProjectStep sqProjectStep = new SqProjectStep();
		sqProjectStep.setStepId("");
		sqProjectStep.setStepName("无");
		return sqProjectStep;
	}
	public int compareTo(SqProjectInfo sqProjectInfo) {
		// TODO Auto-generated method stub
		if(Public.getTimeToFormat(this.getStartDate(), "yyyy-MM-dd").compareToIgnoreCase(Public.getTimeToFormat(sqProjectInfo.getStartDate(), "yyyy-MM-dd")) > 0){
			return -1 ;
		}
		if(Public.getTimeToFormat(this.getStartDate(), "yyyy-MM-dd").compareToIgnoreCase(Public.getTimeToFormat(sqProjectInfo.getStartDate(), "yyyy-MM-dd")) < 0){
			return 1 ;
		}
		return 0;
	}

}