package com.sq.model.vo;

/**
 * SqDocmentManager entity. @author MyEclipse Persistence Tools
 */

public class SqDocmentManager implements java.io.Serializable {

	// Fields

	private String docId;
	private String stepId;
	private String projectId;
	private String userId;
	private String filePath;
	private String backfileName;
	private String fileName;
	private String fileVersion;
	private String status;
	private String remark1 ;
	private SqUserTab sqUserTab ;

	// Constructors

	/** default constructor */
	public SqDocmentManager() {
	}

	/** minimal constructor */
	public SqDocmentManager(String docId) {
		this.docId = docId;
	}

	/** full constructor */
	public SqDocmentManager(String docId, String stepId, String projectId,
			String userId, String filePath, String backfileName,
			String fileName, String fileVersion, String status) {
		this.docId = docId;
		this.stepId = stepId;
		this.projectId = projectId;
		this.userId = userId;
		this.filePath = filePath;
		this.backfileName = backfileName;
		this.fileName = fileName;
		this.fileVersion = fileVersion;
		this.status = status;
	}

	// Property accessors

	public String getDocId() {
		return this.docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getStepId() {
		return this.stepId;
	}

	public void setStepId(String stepId) {
		this.stepId = stepId;
	}

	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getBackfileName() {
		return this.backfileName;
	}

	public void setBackfileName(String backfileName) {
		this.backfileName = backfileName;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileVersion() {
		return this.fileVersion;
	}

	public void setFileVersion(String fileVersion) {
		this.fileVersion = fileVersion;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public SqUserTab getSqUserTab() {
		return sqUserTab;
	}

	public void setSqUserTab(SqUserTab sqUserTab) {
		this.sqUserTab = sqUserTab;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

}