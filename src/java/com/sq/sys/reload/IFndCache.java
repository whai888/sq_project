package com.sq.sys.reload;


public interface IFndCache{

	/**
	 * 加载岗位表
	 */
	public void initJobTab();

	/**
	 * 加载职位表
	 */
	public void initOfficeTab();

	/**
	 * 加载部门表
	 */
	public void initDeptTab();

	/**
	 * 加载系统参数表
	 */
	public void initSysParaTab();
}
