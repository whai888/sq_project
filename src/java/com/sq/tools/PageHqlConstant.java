package com.sq.tools;

public class PageHqlConstant {
	/*PROJECT_TO_STEPINFO
	 * ORCLE中获取表中所有的列名SELECT COLUMN_NAME,DATA_TYPE,DATA_LENGTH,NULLABLE FROM
	 * USER_TAB_COLS where TABLE_NAME='FND_CITY';
	 */
	// 定义类的前缀
	// public static String CLASS_STRING = "com.sq.model.vo" ;

	// 员工信息查询
	public static String USER_LIST = "select sqUserTab.enterYear, sqUserTab.userName, " +
			"(select sqDeptTab.deptName from SqDeptTab as sqDeptTab where sqDeptTab.deptNo=sqUserTab.sqDeptTab.deptNo) , " +
			"sqUserTab.userId, " +
			"(select sqJobTab.jobName from SqJobTab as sqJobTab where sqJobTab.jobId=sqUserTab.sqJobTab.jobId), " +
			"sqUserTab.mobile ,sqUserTab.email, " +
			"(select sqSysparaTab.fieldDesc from SqSysparaTab as sqSysparaTab where sqSysparaTab.sysTable='sq_user_tab' and sqSysparaTab.fieldValue= sqUserTab.status and  sqSysparaTab.field='status' ), " +
			"(select sqGroupTab.groupName from SqGroupTab as sqGroupTab where sqGroupTab.groupNo = sqUserTab.sqGroupTab.groupNo), " +
			"(select sqOfficeTab.officeName from SqOfficeTab as sqOfficeTab where sqOfficeTab.officeId = sqUserTab.sqOfficeTab.officeId) " +
			"from SqUserTab as sqUserTab " +
			"where 1=1 and sqUserTab.userName like '%'||:userName||'%' and sqUserTab.enterYear >= :enterYear and sqUserTab.enterYear <= :end " +
			"and sqUserTab.sqDeptTab.deptNo = :deptno and sqUserTab.sqJobTab.jobId = :job " +
			"and ( sqUserTab.status = :status1 or sqUserTab.status = :status0 or sqUserTab.status = :status2 or sqUserTab.status = :status3) order by sqUserTab.pyName asc ";

	//查询部门下的员工信息
	public static String DEPT_USER_LIST = "select sqUserTab.enterYear, sqUserTab.userName, " +
			"(select sqDeptTab.deptName from SqDeptTab as sqDeptTab where sqDeptTab.deptNo=sqUserTab.sqDeptTab.deptNo) , " +
			"sqUserTab.userId, " +
			"(select sqJobTab.jobName from SqJobTab as sqJobTab where sqJobTab.jobId=sqUserTab.sqJobTab.jobId), " +
			"sqUserTab.mobile ,sqUserTab.email, " +
			"(select sqSysparaTab.fieldDesc from SqSysparaTab as sqSysparaTab where sqSysparaTab.sysTable='sq_user_tab' and sqSysparaTab.fieldValue= sqUserTab.status and  sqSysparaTab.field='status' ), " +
			"(select sqGroupTab.groupName from SqGroupTab as sqGroupTab where sqGroupTab.groupNo = sqUserTab.sqGroupTab.groupNo), " +
			"(select sqOfficeTab.officeName from SqOfficeTab as sqOfficeTab where sqOfficeTab.officeId = sqUserTab.sqOfficeTab.officeId) " +
			"from SqUserTab as sqUserTab " +
			"where sqUserTab.userName like '%'||:userName||'%' " +
			"and sqUserTab.sqDeptTab.deptNo = :deptno  and sqUserTab.status!='1' and 1=1 order by sqUserTab.pyName asc ";
	
	//查询部门下的项目组信息
	public static String DEPT_GROUP_LIST = "select sqGroupTab.groupName, sqGroupTab.succDate, " +
			"(select sqDeptTab.deptName from SqDeptTab as sqDeptTab where sqDeptTab.deptNo=sqGroupTab.sqDeptTab.deptNo) , " +
			"(select sqUserTab.userName from SqUserTab as sqUserTab where sqUserTab.userId=sqGroupTab.sqUserTab.userId ), " +
			"(select sqSysparaTab.fieldDesc from SqSysparaTab as sqSysparaTab where sqSysparaTab.sysTable='sq_group_tab' and sqSysparaTab.fieldValue= sqGroupTab.status and  sqSysparaTab.field='status' ) " +
			"from SqGroupTab as sqGroupTab " +
			"where  1=1 and sqGroupTab.sqDeptTab.deptNo = :deptno and sqGroupTab.status!='1' order by sqGroupTab.groupNo";
	
	//查询项目组下的员工信息
	public static String GROUP_USER_LIST = "select sqUserTab.enterYear, sqUserTab.userName, " +
			"(select sqDeptTab.deptName from SqDeptTab as sqDeptTab where sqDeptTab.deptNo=sqUserTab.sqDeptTab.deptNo) , " +
			"sqUserTab.userId, " +
			"(select sqJobTab.jobName from SqJobTab as sqJobTab where sqJobTab.jobId=sqUserTab.sqJobTab.jobId), " +
			"sqUserTab.mobile ,sqUserTab.email, " +
			"(select sqSysparaTab.fieldDesc from SqSysparaTab as sqSysparaTab where sqSysparaTab.sysTable='sq_user_tab' and sqSysparaTab.fieldValue= sqUserTab.status and  sqSysparaTab.field='status' ), " +
			"(select sqGroupTab.groupName from SqGroupTab as sqGroupTab where sqGroupTab.groupNo = sqUserTab.sqGroupTab.groupNo), " +
			"(select sqOfficeTab.officeName from SqOfficeTab as sqOfficeTab where sqOfficeTab.officeId = sqUserTab.sqOfficeTab.officeId) " +
			"from SqUserTab as sqUserTab " +
			"where sqUserTab.userName like '%'||:userName||'%' " +
			"and sqUserTab.sqGroupTab.groupNo = :groupno and sqUserTab.status!='1' and 1=1 order by sqUserTab.pyName asc ";
	
	// 操作日志信息查询
	public static String USER_LOG_LIST = "select log.seqNo,(select user.userId||user.userName from SqUserTab as user where user.userId=log.userNo),log.ip,log.opDate,log.opTime,log.opDesc," +
			"(select sqSysparaTab.fieldDesc from SqSysparaTab as sqSysparaTab where sqSysparaTab.sysTable='sq_userlog_tab' and sqSysparaTab.fieldValue= log.status and  sqSysparaTab.field='status' ) " +
			"from SqUserlogTab as log " +
			"where log.userNo like '%'||:userNo||'%' and log.opDate >= :start and log.opDate <= :end and ( log.status = :status1 or log.status = :status0) order by log.opDate desc , log.opTime desc";

	// 我的项目历史记录
	public static String PROJECT_HISTORY_LIST = "select sqProjectInfo.projectId,"
			+ "sqProjectInfo.projectName,"
			+ "sqProjectInfo.startDate,"
			+ "sqProjectInfo.advanceDate,"
			+ "(select user.userName from SqUserTab as user where user.userId=sqProjectInfo.sqUserTab.userId),"
			+ "sqProjectInfo.resume,"
			+ "(select sqSysparaTab.fieldDesc from SqSysparaTab as sqSysparaTab where sqSysparaTab.sysTable='sq_project_info' and sqSysparaTab.fieldValue= sqProjectInfo.status and  sqSysparaTab.field='status' ), "
			+ "(select sqGroupTab.groupName from SqGroupTab as sqGroupTab where sqProjectInfo.sqGroupTab.groupNo = sqGroupTab.groupNo ) "
			+ "from SqProjectInfo as sqProjectInfo "
			+ "where sqProjectInfo.startDate >= :start and sqProjectInfo.startDate <= :end " 
			+ "and sqProjectInfo.projectName like '%' || :projectName || '%' "
			+ "and sqProjectInfo.status = :status order by sqProjectInfo.sqGroupTab.groupNo desc ";

	// 根据项目编号查询所有的员工信息
	public static String PROJECT_USER_LIST = "	select sqUserTab.userId,"
			+ "sqUserTab.userName,"
			+ "(select sqDeptTab.deptName from SqDeptTab as sqDeptTab where sqDeptTab.deptNo =sqUserTab.sqDeptTab.deptNo ),"
			+ "(select sqOfficeTab.officeName from SqOfficeTab as sqOfficeTab where sqOfficeTab.officeId = sqUserTab.sqOfficeTab.officeId),"
			+ "(select sqJobTab.jobName from SqJobTab as sqJobTab where sqJobTab.jobId = sqUserTab.sqJobTab.jobId),"
			+ "(select sqSysparaTab.fieldDesc from SqSysparaTab as sqSysparaTab where  sqSysparaTab.sysTable='sq_user_tab' and sqSysparaTab.fieldValue= sqUserTab.level and  sqSysparaTab.field='level'),"
			+ "sqProjectUser.startDate,"
			+ "sqProjectUser.endDate,"
			+ "sqProjectUser.remark,"
			+ "(select sqSysparaTab.fieldDesc from SqSysparaTab as sqSysparaTab where sqSysparaTab.sysTable='sq_project_user' and sqSysparaTab.fieldValue=sqProjectUser.status and  sqSysparaTab.field='status' ) ,"
			+ "sqProjectUser.status, "
			+ "(select sqGroupTab.groupName from SqGroupTab as sqGroupTab where sqGroupTab.groupNo = sqUserTab.sqGroupTab.groupNo), "
			+ "(select sqOfficeTab.officeName from SqOfficeTab as sqOfficeTab where sqOfficeTab.officeId = sqUserTab.sqOfficeTab.officeId) "
			+ "from SqUserTab as sqUserTab , SqProjectUser as sqProjectUser "
			+ "where  sqUserTab.userId = sqProjectUser.id.userId and sqProjectUser.id.projectId = :projectId order by sqUserTab.pyName asc ";
	
	//根据日报编号查询日报对应的文档
	public static String WORK_DAY_ID = "select sqDocumentMgn.backfileName,sqDocumentMgn.fileName,sqDocumentMgn.filePath " 
			+ "from SqDocmentManager as sqDocumentMgn " 
			+ "where sqDocumentMgn.projectId = :workdayId";
	
	//根据项目编号查询项目的阶段,dayStatus=1日报节点的不能查出来
	public static String PROJECT_TO_STEPINFO = "select sqProjectStep.stepId,sqProjectStep.stepName from SqProjectStep sqProjectStep"
			+ " where sqProjectStep.projectId=:projectId  and sqProjectStep.dayStatus='0' and sqProjectStep.stepStatus=:status order by sqProjectStep.stepOrder desc";
	
	//根据项目编号查询项目提交的日报员工
	public static String PROJECT_TO_USERINFO = "select sqUserTab.userId,sqUserTab.userName from SqUserTab sqUserTab "
			+ " where sqUserTab.userId in (select distinct sqWorkdayManager.sqUserTab.userId from SqWorkdayManager sqWorkdayManager where sqWorkdayManager.id.sqProjectStep.projectId = :projectId and sqWorkdayManager.type='1')";
	
	//根据项目组查询该项目组下所有的项目
	public static String GROUP_TO_PROJECTINFO ="select sqProjectInfo.projectId,sqProjectInfo.projectName from SqProjectInfo sqProjectInfo " +
			"where sqProjectInfo.sqGroupTab.groupNo=:groupNo and sqProjectInfo.status not in ('2','3') order by sqProjectInfo.projectId";
	
	//根据员工岗位查询员工职级
	public static String LEVEL_TO_OFFICE = "select sqOfficeTab.officeId,sqOfficeTab.officeName from SqOfficeTab sqOfficeTab where sqOfficeTab.type=:type and sqOfficeTab.status='0' order by sqOfficeTab.indexNo desc ";
	
	//根据项目编号，查询项目日报的周期
	public static String WORKDAY_TO_PROJECTID = "select distinct '1',sqWorkdayManager.weekDate from SqWorkdayManager sqWorkdayManager where sqWorkdayManager.sqProjectInfo.projectId=:projectId and sqWorkdayManager.type=:type and sqWorkdayManager.weekDate is not null order by sqWorkdayManager.workdayId desc limit 0,50 ";

	//根据项目组编号，查询项目日报的周期
	public static String WORKDAY_TO_GROUPNO = "select distinct '1',sqWorkdayManager.weekDate from SqWorkdayManager sqWorkdayManager where sqWorkdayManager.sqGroupTab.groupNo=:groupNo and sqWorkdayManager.type=:type and sqWorkdayManager.weekDate is not null order by sqWorkdayManager.workdayId desc limit 0,50 ";

	//根据部门编号，查询部门周报所属周报周期
	public static String WORKDAY_TO_DEPTNO = "select distinct '1',sqWorkdayManager.weekDate from SqWorkdayManager sqWorkdayManager where sqWorkdayManager.type=:type and sqWorkdayManager.sqGroupTab.groupNo in (select groupNo from SqGroupTab sqGroupTab where sqGroupTab.sqDeptTab.deptNo=:deptNo and sqGroupTab.status ='0') and sqWorkdayManager.weekDate is not null order by sqWorkdayManager.workdayId desc limit 0,50 ";
}
