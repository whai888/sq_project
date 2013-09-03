package com.sq.logic.project;

import java.util.List;

import com.sq.exception.SystemException;
import com.sq.model.vo.SqDocmentManager;
import com.sq.model.vo.SqProjectInfo;
import com.sq.model.vo.SqProjectStep;
import com.sq.model.vo.SqProjectUser;

public interface IProjectUserService {
	public List findByProjectId(SqProjectUser sqProjectUser) throws SystemException ;
}
