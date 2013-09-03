package com.sq.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;

import com.sq.exception.SystemException;

/**
 * Data access interface for domain model
 * 
 * @author MyEclipse Persistence Tools
 */
public abstract interface IBaseDAO {

	public abstract void save(Object paramObject);

	public abstract void update(Object paramObject);

	public abstract void saveOrUpdate(Object paramObject);

	public abstract void saveOrUpdateAll(Collection paramCollection);

	public abstract void delete(Object paramObject);

	public abstract void delete(Class paramClass, Serializable paramSerializable);

	public abstract void deleteAll(Collection paramCollection);

	public abstract void flush();

	public abstract void clear();

	public void evict(Object o);
	
	public Object load(Class entityClass, Serializable id);

	public List loadAll(Class entityClass);

	public List findByExample(Object obj) ;
	
	public abstract List findBy(Class paramClass, String paramString,
			Object paramObject);

	public abstract List createQuery(String paramString,
			Object[] paramArrayOfObject);

	public abstract List findBy(Class paramClass, String paramString1,
			Object paramObject, String paramString2, boolean paramBoolean);

	public abstract List findByLike(Class paramClass, String paramString1,
			String paramString2);

	public abstract List findByLike(Class paramClass, String paramString1,
			String paramString2, String paramString3);

	public abstract Object findUniqueBy(Class paramClass, String paramString,
			Object paramObject);

	public abstract List findByObject(Object paramObject , String orderByVal);

	public abstract Object findFirstVO(Object paramObject , String orderByVal);

	public abstract List find(String paramString, Object[] paramArrayOfObject);

	public abstract List find(String paramString);

	public abstract List findByNamedParam(String paramString, String [] paramNames , Object [] values , int paramInt1, int paramInt2);

	public abstract int getRecordCount(String paramString, String [] paramNames ,Object [] values);

	public abstract int updateByHql(String paramString);

	public abstract Object findFirstVO(String paramString);

	public abstract boolean isUniquePropertyNames(Class paramClass,
			Object paramObject, String paramString);

	public abstract List findWithSQL(String paramString, Object obj);

	public abstract List sqlQuery(String paramString);

	public abstract List sqlQueryAnaity(String paramString, Class paramClass);

	public abstract void sqlUpdate(String paramString);

	public abstract void saveOrUpdateAllEntities(Collection paramCollection);
	
	public String sequenceToId(String seqId) ;
	
	public List findWithSQL(final String sql , final Object obj, final String filterName ,final String [] para , final Object[] objVal) ;
	
	public Map<String [], List<String[]>> excuteSql(String sql) throws SystemException;
}