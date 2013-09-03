package com.sq.logic.home;

import java.util.List;
import java.util.Map;

import com.sq.exception.SystemException;
import com.sq.model.vo.SqArticleManager;
import com.sq.model.vo.SqUserTab;

/**
 *
 *@autor whai
 */
public interface ISystemHomeService {

	public void iWantSubSave(SqArticleManager sqArticleManager , SqUserTab sqUserTab);
	public SqArticleManager findArtIdForContent(SqArticleManager sqArticleManager) throws SystemException;
	public List findArticleList(String plate) throws SystemException;
	public void artAudit(SqArticleManager sqArticleManager , SqUserTab sqUserTab) throws SystemException ;
	public void sysBugSave(SqArticleManager sqArticleManager , SqUserTab sqUserTab) throws SystemException ;
	public Map<String [], List<String[]>> systemSql(String sql) throws SystemException ;
}
