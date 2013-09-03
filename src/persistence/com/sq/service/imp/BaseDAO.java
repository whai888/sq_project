package com.sq.service.imp;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.Filter;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sq.exception.SystemException;
import com.sq.model.dao.DaoUtil;
import com.sq.model.vo.Sequence;
import com.sq.model.vo.SequenceId;
import com.sq.service.IBaseDAO;
import com.sq.tools.Public;

/**
 * Data access object (DAO) for domain model
 * 
 * @author whai
 */
public class BaseDAO extends HibernateDaoSupport implements IBaseDAO {

	public Session getCurrentSession() {
		return getHibernateTemplate().getSessionFactory().getCurrentSession();
	}

	/**
	 * 功能描述：清理缓存
	 */
	public void clear() {
		getHibernateTemplate().clear();
	}

	/**
	 * 从一级缓存中移除对象
	 * @param o
	 */
	public void evict(Object o){
		getCurrentSession().evict(o);
	}
	/**
	 * 功能描述：更加给定的HQL语句查询指定的数据
	 * 
	 * @param paramString
	 *            hql语句
	 * @param paramArrayOfObject
	 *            预定义语句中的变量
	 * @return 返回查询的结果
	 */
	public List createQuery(String hql, Object[] values) {
		Query query = getSession().createQuery(hql);
		for (int i = 0; i < values.length; ++i)
			query.setParameter(i, values[i]);
		return query.list();
	}

	/**
	 * 功能描述：根据制定的内容删除 删除
	 */
	public void delete(Object o) {
		getHibernateTemplate().delete(o);

	}

	/**
	 * 功能描述：根据实体对象给定的ID 查询实体属性，然后再删除
	 */
	public void delete(Class entityClass, Serializable id) {
		delete(load(entityClass, id));

	}

	/**
	 * 删除所有的数据
	 */
	public void deleteAll(Collection paramCollection) {
		// TODO Auto-generated method stub

	}

	/**
	 * 功能描述：
	 */
	public List find(String hql, Object[] values) {
		 return getHibernateTemplate().find(hql, values);
	}

	/**
	 * 根据对象去查询
	 * @param obj
	 * @return
	 */
	public List findByExample(Object obj){
		return getHibernateTemplate().findByExample(obj) ;
	}
	/**
	 * 功能描述：根据指定的hql查询
	 * @param	hql	HQL语句
	 * @return	返回查询的结果集
	 */
	public List find(String hql) {
		return getHibernateTemplate().find(hql);
	}

	/**
	 * 功能描述：根据HQL显示 从index开始，总长度为length
	 * @param	values			参数名称的值
	 * @param	paramNames		参数名称
	 * @param	hql				查询HQL语句
	 * @param	index			起始位置
	 * @param	length			总长度
	 * @return					返回查询的结果集
	 */
	public List findByNamedParam(String hql, final String [] paramNames ,final Object [] values , int index, int length) {
		final String queryString = hql;
		final int first = index;
		final int maxLength = length;
	    List voList = getHibernateTemplate().executeFind(
	      new HibernateCallback() {
	      public Object doInHibernate(Session session ) throws HibernateException, SQLException {
	    	  session = getCurrentSession();
	        Query q = session.createQuery(queryString);
	        for (int i = 0; i < paramNames.length; i++) {
	        	if(paramNames[i].startsWith("list")){
	        		q.setParameterList(paramNames[i].substring(4), (String [])values[i]);
	        	}else if(!values[i].equals("-99") )
	        		q.setParameter(paramNames[i], values[i]);
			}
	        q.setMaxResults(maxLength);
	        q.setFirstResult(first);
	        List page = q.list();
	        return page;
	      }
	    });
	    return voList;
	}

	public List findBy(Class paramClass, String paramString, Object paramObject) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findBy(Class paramClass, String paramString1,
			Object paramObject, String paramString2, boolean paramBoolean) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 功能描述：模糊查找
	 */
	public List findByLike(Class entityClass, String fieldName,
			String fieldValue) {
		return find("From " + entityClass.getName() + " WHERE " + fieldName + " like '" + fieldValue + "'");
	}

	/**
	 * 功能描述：按照排序模糊查找
	 */
	public List findByLike(Class entityClass, String fieldName,
			String fieldValue, String orderFieldName) {
		return find("From " + entityClass.getName() + " WHERE " + fieldName + " like '" + fieldValue + "' Order by " + orderFieldName);

	}

	public List findByObject(Object vo , String orderByVal) {
		return getHibernateTemplate().findByValueBean(DaoUtil.getHqlFromVOProperty(vo , orderByVal ), vo);
	}

	public Object findFirstVO(Object vo , String orderByVal) {
		List list = findByObject(vo ,orderByVal);
	    if ((list == null) || (list.size() == 0))
	      return null;
	    return list.get(0);
	}

	public List findFirstVO(String paramString) {
		return getHibernateTemplate().find(paramString) ;
	}

	public Object findUniqueBy(Class paramClass, String paramString,
			Object paramObject) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 功能描述：根据指定的SQL查询
	 * 
	 * @param sql
	 *            定义的sql
	 * @return 返回查询的结果集
	 */
	public List findWithSQL(final String sql , final Object obj ) {
		List list = (List) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						session = getCurrentSession();
						Query query ;
						if(obj != null)
							query = session.createSQLQuery(sql).addEntity(obj.getClass());
						else
							query = session.createSQLQuery(sql);
						return query.list();
					}

				});
		return list;
	}

	/**
	 * 刷新
	 */
	public void flush() {
		getHibernateTemplate().flush();

	}

	/**
	 * 功能描述：得到查询数据的总数
	 * @param	queryString	sql 语句
	 * @return	返回总数
	 */
	public int getRecordCount(String queryString , final String [] paramNames ,final Object [] values ) {
		final String countQueryString = DaoUtil.getHQL4Count(queryString);
	    Long count = (Long)getHibernateTemplate().execute(
	      new HibernateCallback() {
	      public Object doInHibernate(Session session ) throws HibernateException, SQLException {
	    	  session = getCurrentSession();
	        Query q = session.createQuery(countQueryString);
	        for (int i = 0; i < paramNames.length; i++) {
				q.setParameter(paramNames[i], values[i]);
			}
	        return q.uniqueResult();
	      }
	    });
	    if (count == null)
	      return 0;
	    return count.intValue();
	}

	public boolean isUniquePropertyNames(Class paramClass, Object paramObject,
			String paramString) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 功能描述：保存指定的对象
	 */
	public void save(Object o) {
		getHibernateTemplate().save(o);

	}

	/**
	 * 功能描述：保存或更新
	 */
	public void saveOrUpdate(Object o) {
		getHibernateTemplate().saveOrUpdate(o);
	}

	public void saveOrUpdateAll(Collection paramCollection) {
		// TODO Auto-generated method stub

	}

	public void saveOrUpdateAllEntities(Collection paramCollection) {
		// TODO Auto-generated method stub

	}

	/**
	 * 功能描述：根据指定的SQL 查询
	 * @param	sql		需要查询的SQL语句
	 * @return	返回查询的结果集
	 */
	public List sqlQuery(final String sql) {
		List list = (List) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws SQLException, HibernateException {
						session = getCurrentSession();
						SQLQuery query = session.createSQLQuery(sql);
						List children = query.list();
						return children;
					}
				});
		return list;
	}

	/**
	 * 功能描述：根据指定的SQL 查询
	 * @param	sql		需要查询的SQL语句
	 * @return	返回查询的结果集
	 */
	public List findWithSQL(final String sql , final Object obj, final String filterName ,final String [] para , final Object[] objVal) {
		List list = (List) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						session = getCurrentSession();
						Filter filter = session.enableFilter(filterName);
						for (int i = 0; i < para.length; i++) {
							filter.setParameter(para[i], objVal[i]);
						}
						Query query ;
						if(obj != null)
							query = session.createSQLQuery(sql).addEntity(obj.getClass());
						else
							query = session.createSQLQuery(sql);
						return query.list();
					}

				});
		return list;
	}
	
	/**
	 * 功能描述：根据SQL语句将查询出来的结果转换为实体
	 * 
	 * @param sql
	 *            需要更新的SQL语句
	 * @return 返回更新结果
	 */
	public List sqlQueryAnaity(final String sql, final Class paramClass) {
		List list = (List) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws SQLException, HibernateException {
						session = getCurrentSession();
						SQLQuery query = session.createSQLQuery(sql).addEntity(
								paramClass);
						List children = query.list();
						return children;
					}
				});
		return list;
	}

	/**
	 * 功能描述：根据SQL语句更新
	 * 
	 * @param sql
	 *            需要更新的SQL语句
	 * @return 返回更新结果
	 */
	public void sqlUpdate(final String sql) {
		Object result = getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException,
					HibernateException {
				session = getCurrentSession();
				 return session.createSQLQuery(sql).executeUpdate();
			}
		});
	}

	/**
	 * 更新指定的实体类
	 */
	public void update(Object o) {
		getHibernateTemplate().update(o);
	}

	/**
	 * 功能描述：根据指定的HQL语句更新数据库，返回int类型
	 */
	public int updateByHql(final String hql) {
		Integer count = (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						session = getCurrentSession();
						Query q = session.createQuery(hql);
						return new Integer(q.executeUpdate());
					}
				});
		return count.intValue();
	}

	/**
	 * 功能描述：根据实体类和id加载对象
	 */
	public Object load(Class entityClass, Serializable id) {
		return getHibernateTemplate().load(entityClass, id);
	}

	/**
	 * 功能描述：根据实体加载对象
	 */
	public List loadAll(Class entityClass) {
		return getHibernateTemplate().loadAll(entityClass);
	}
	
	public Map<String [], List<String[]>> excuteSql(String sql) throws SystemException{
		//不支持delete
		Map<String [], List<String[]>> strMap = new HashMap<String [], List<String[]>>();
		if(sql.toLowerCase(Locale.CHINA).startsWith("update".toLowerCase(Locale.CHINA)) ||
				sql.toLowerCase(Locale.CHINA).startsWith("insert".toLowerCase(Locale.CHINA))){
			this.sqlUpdate(sql);
		}else{
			Connection conn = null ;
			PreparedStatement ps = null ;
			ResultSet rs = null ;
			try {
				conn = getCurrentSession().connection();
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();  
				ResultSetMetaData rsmd = rs.getMetaData();  
				 //获取当前表共有多少列   
				int tableLength = rsmd.getColumnCount();
	//			Map<String , String >  columnMap = new TreeMap<String , String >() ;
				String [] column = new String[tableLength];
				String [] columnType = new String[tableLength];
				for(int i=1 ;i<=tableLength ;i++){  
				    //获取数据库类型   
				    String rolumnDbClass = rsmd.getColumnTypeName(i);  
				      
				    //获取数据库类型与java相对于的类型   
				    String rolumnClass = rsmd.getColumnClassName(i);  
				    columnType[i-1] = rolumnClass;
				      
				    //获取列名   
				    String columnName = rsmd.getColumnLabel(i);
				    column[i-1] = columnName ;
	//			    columnMap.put(columnName, rolumnClass);
				}
				List<String[]> dataList = new ArrayList();
				//读取数据
				while(rs.next()){
	//				Iterator<String> keys = columnMap.keySet().iterator();
					String [] columValue = new String[column.length];
					for(int count=0 ; count<column.length ; count++ ){
						String key = column[count];//keys.next();//key
						String keyType = columnType[count] ;//columnMap.get(key);//上面key对应的value
						String tempValue = "";
						if(keyType.equals("java.lang.String")){
							tempValue = rs.getString(key);
						}else if(!Public.isEmpty(key) && keyType.equals("java.sql.Date")){
							tempValue = Public.getTimeToFormat(rs.getDate(key),"yyyy-MM-dd");
						}else if(keyType.equals("java.sql.Time")){
							Time value = rs.getTime(key);
							if(value!=null)
								tempValue = value.getHours() + ":" +value.getMinutes()+":" +value.getSeconds();
						}else if(keyType.equals("java.lang.Long")||keyType.equals("java.lang.Integer")){
							tempValue = String.valueOf(rs.getInt(key));
						}else if(keyType.equals("java.lang.Float")){
							tempValue = String.valueOf(rs.getFloat(key));
						}
						if(Public.isEmpty(tempValue)){
							tempValue = "";
						}
						columValue[count] = tempValue ;
					}
					dataList.add(columValue) ;
				}
				strMap.put(column, dataList);
				
			} catch (HibernateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new SystemException(BaseDAO.class ,e.getMessage());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new SystemException(BaseDAO.class ,e.getMessage());
			}finally{
				try {
					if(rs !=null)
						rs.close();
					if(ps !=null)
						ps.close();
					if(conn !=null)
						conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new SystemException(BaseDAO.class ,e.getMessage());
				}
			}
		}
		return strMap ;
	}
	
	/**
	 * 获得序列的主键ID
	 */
	public String sequenceToId(String seqName){
		synchronized(this){
			StringBuffer seqNoRet = new StringBuffer() ;
			//查询序列
			List list = this.sqlQueryAnaity("select * from sequence where name='"+seqName+"'" , Sequence.class);   
	        Sequence sequence = (Sequence)list.get(0);
	        SequenceId  sequenceId= sequence.getId() ;
	        sequenceId.setSeqNo(sequence.getId().getSeqNo() + 1) ;
	        sequence.setId(sequenceId);
	        //转换序列
	        String[] seqTemp = sequence.getFormat().split("@");
	        if(seqTemp[0].equalsIgnoreCase("yyyyMMdd")||seqTemp[0].equalsIgnoreCase("yyyy-MM-dd") ||
	        		seqTemp[0].equalsIgnoreCase("MMdd"))
	        	seqNoRet.append(Public.getSystemTimeToFormat(seqTemp[0]));
	        else
	        	seqNoRet.append(seqTemp[0]);
	        //左边补'0'，否则右边补'0'
	        if(seqTemp[1].charAt(0) == 'L'){
	        	for (int i = 0; i < Integer.parseInt(seqTemp[1].substring(1)) - (sequence.getId().getSeqNo()+"").length()  ; i++) {
	        		seqNoRet.append("0");
				}
	        	seqNoRet.append(sequenceId.getSeqNo() );
	        }else{
	        	seqNoRet.append(sequenceId.getSeqNo());
	        	for (int i = 0; i < Integer.parseInt(seqTemp[1].substring(1)) ; i++) {
	        		seqNoRet.append("0");
				}
	        }
	        Integer icount = 1 ;
	        if(seqTemp[1].charAt(1) == 3){
	        	icount = 999 ;
	        }else if(seqTemp[1].charAt(1) == 4){
	        	icount = 9999 ;
	        }else if(seqTemp[1].charAt(1) == 5){
	        	icount = 99999 ;
	        }else if(seqTemp[1].charAt(1) == 6){
	        	icount = 999999 ;
	        }
	        if(sequence.getId().getSeqNo() == icount)
	    		sequence.getId().setSeqNo(1);
	        
	        //更新
	        this.sqlUpdate("update sequence set seq_no = " +sequence.getId().getSeqNo()+ " where name = '" +seqName+ "'") ;
	        
	        return seqNoRet.toString();
		}
	}
}