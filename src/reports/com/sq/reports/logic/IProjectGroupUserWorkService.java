package com.sq.reports.logic;

import java.util.List;

import com.sq.reports.vo.ProjectGroupUserWork;

/**
 *
 *@autor whai
 */
public interface IProjectGroupUserWorkService {
	public List<ProjectGroupUserWork> getPorjectGroupList(String groupNo , String starDate , String endDate);
}
