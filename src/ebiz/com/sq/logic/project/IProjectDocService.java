package com.sq.logic.project;

import java.util.List;

import com.sq.exception.SystemException;
import com.sq.model.vo.SqDocmentManager;

public interface IProjectDocService {

	public void saveDocManager(SqDocmentManager sqDocmentManager);
	public void updateDocManager(SqDocmentManager sqDocmentManager);
	public List projectDocFindInfo(SqDocmentManager sqDocmentManager)throws SystemException  ;
	public void deleteDocManager(SqDocmentManager sqDocmentManager) ;
	public SqDocmentManager findDocManagerToRemark1(SqDocmentManager sqDocmentManager);
}
