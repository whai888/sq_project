package com.sq.model.vo;

import java.util.Date;
import java.util.Set;

/**
 * SqUserTab entity. @author MyEclipse Persistence Tools
 */

public class SqUserTab implements java.io.Serializable, Comparable<SqUserTab> {

	// Fields

	private String userId;
	private String userType;
	private String userName;
	private String userSex;
	private Date birthday;
//	private String deptno;
	private Date enterYear;
	private Date practitionersYear;
//	private String office;
//	private String job;
	private Integer level;
	private String passwd;
	private String ip;
	private String loginpass;
	private String addDate;
	private String modifyDate;
	private String mobile;
	private String email;
	private Integer pwdError;
	private String status;
	private String remark;
//	private String projectGroup ;
	private boolean sysMagnUser ;
	private String pyName ;
	private SqDeptTab sqDeptTab ;
	private SqJobTab sqJobTab ;
	private SqOfficeTab sqOfficeTab ;
	//所属项目组
	private SqGroupTab sqGroupTab ;
	private Set<SqUserRole> userRoleSet;

	// Constructors

	/** default constructor */
	public SqUserTab() {
	}

	/** minimal constructor */
	public SqUserTab(String userType, String userName, String deptno,
			Date enterYear, Date practitionersYear, String office, String job,
			Integer level, String passwd, String loginpass, String addDate,
			String modifyDate, String mobile, String email, String status) {
		this.userType = userType;
		this.userName = userName;
		this.enterYear = enterYear;
		this.practitionersYear = practitionersYear;
		this.level = level;
		this.passwd = passwd;
		this.loginpass = loginpass;
		this.addDate = addDate;
		this.modifyDate = modifyDate;
		this.mobile = mobile;
		this.email = email;
		this.status = status;
	}

	/** full constructor */
	public SqUserTab(String userType, String userName, String userSex,
			Date birthday, String deptno, Date enterYear,
			Date practitionersYear, String office, String job, Integer level,
			String passwd, String ip, String loginpass, String addDate,
			String modifyDate, String mobile, String email, Integer pwdError,
			String status, String remark) {
		this.userType = userType;
		this.userName = userName;
		this.userSex = userSex;
		this.birthday = birthday;
		this.enterYear = enterYear;
		this.practitionersYear = practitionersYear;
		this.level = level;
		this.passwd = passwd;
		this.ip = ip;
		this.loginpass = loginpass;
		this.addDate = addDate;
		this.modifyDate = modifyDate;
		this.mobile = mobile;
		this.email = email;
		this.pwdError = pwdError;
		this.status = status;
		this.remark = remark;
	}

	// Property accessors

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserType() {
		return this.userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserSex() {
		return this.userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public Date getEnterYear() {
		return this.enterYear;
	}

	public void setEnterYear(Date enterYear) {
		this.enterYear = enterYear;
	}

	public Date getPractitionersYear() {
		return this.practitionersYear;
	}

	public void setPractitionersYear(Date practitionersYear) {
		this.practitionersYear = practitionersYear;
	}
	public SqOfficeTab getSqOfficeTab() {
		return sqOfficeTab;
	}

	public void setSqOfficeTab(SqOfficeTab sqOfficeTab) {
		this.sqOfficeTab = sqOfficeTab;
	}

	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getPasswd() {
		return this.passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getLoginpass() {
		return this.loginpass;
	}

	public void setLoginpass(String loginpass) {
		this.loginpass = loginpass;
	}

	public String getAddDate() {
		return this.addDate;
	}

	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}

	public String getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getPwdError() {
		return this.pwdError;
	}

	public void setPwdError(Integer pwdError) {
		this.pwdError = pwdError;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public SqDeptTab getSqDeptTab() {
		return sqDeptTab;
	}

	public void setSqDeptTab(SqDeptTab sqDeptTab) {
		this.sqDeptTab = sqDeptTab;
	}

	public SqJobTab getSqJobTab() {
		return sqJobTab;
	}

	public void setSqJobTab(SqJobTab sqJobTab) {
		this.sqJobTab = sqJobTab;
	}

	public SqGroupTab getSqGroupTab() {
		return sqGroupTab;
	}

	public void setSqGroupTab(SqGroupTab sqGroupTab) {
		this.sqGroupTab = sqGroupTab;
	}

	public Set<SqUserRole> getUserRoleSet() {
		return userRoleSet;
	}

	public void setUserRoleSet(Set<SqUserRole> userRoleSet) {
		this.userRoleSet = userRoleSet;
	}

	public String getPyName() {
		return pyName;
	}

	public void setPyName(String pyName) {
		this.pyName = pyName;
	}

	public int compareTo(SqUserTab sqUserTab) {
		// TODO Auto-generated method stub
		if(this.getUserId().compareToIgnoreCase(sqUserTab.getUserId()) > 0){
			return 1 ;
		}
		if(this.getUserId().compareToIgnoreCase(sqUserTab.getUserId()) < 0){
			return -1 ;
		}
		return 0;
	}
	
	/**
	 * 检查是否为管理员
	 * @return
	 */
	public boolean isSysMagnUser(String str) {
		for (SqUserRole sqUserRole : userRoleSet) {
			if(sqUserRole.getId().getSqRole().getRoleId().equals(str)){
				return true ;
			}
		}
		return false;
	}
}