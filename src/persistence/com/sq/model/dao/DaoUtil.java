package com.sq.model.dao;

import java.beans.PropertyDescriptor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

public class DaoUtil {
	protected static final Logger log = Logger.getLogger(DaoUtil.class
			.getName());

	public static String getHQL4Count(String hql) {
		int formPos = hql.toLowerCase().indexOf("from ");

		int distPos = hql.toLowerCase().indexOf(" distinct");
		if (distPos < 0) {
			return (hql = " select count(*) "
					+ removeSelect(removeOrders(hql)));
		}

		hql = " select count(" + hql.substring(distPos, formPos).trim() + ") "
				+ removeSelect(removeOrders(hql));
		return hql;
	}

	public static String removeSelect(String hql) {
		int beginPos = hql.toLowerCase().indexOf("from");
		return hql.substring(beginPos);
	}

	public static String removeOrders(String hql) {
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", 2);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find())
			m.appendReplacement(sb, "");

		m.appendTail(sb);
		return sb.toString();
	}

	public static String getHqlFromVOProperty(Object findObject , String orderByVal) {
		StringBuffer sql = new StringBuffer();
		sql.append(" From " + findObject.getClass().getName());
		// 利用反射机制获取类中的属性
		PropertyDescriptor[] pd = PropertyUtils
				.getPropertyDescriptors(findObject.getClass());

		StringBuffer whereString = new StringBuffer();
		for (int i = 0; i < pd.length; ++i) {
			PropertyDescriptor p = pd[i];

			if (p.getWriteMethod() != null) {
				if (p.getReadMethod() == null)
					continue;
				try {
					if (BeanUtils.getProperty(findObject, p.getName()) == null)
						continue;

//					if (BeanUtils.getProperty(findObject, p.getName()).equals(
//							"0"))
//						continue;
//					if (!(BeanUtils.getProperty(findObject, p.getName())
//							.equals("0.0")))
//						continue;
				} catch (Exception e) {
				}
				if (whereString.length() == 0)
					whereString.append(" WHERE ");
				else {
					whereString.append(" AND ");
				}

				whereString.append(p.getName() + " = " + ":" + p.getName());
			}
		}
		if(orderByVal != null ){
			whereString.append(" order by " + orderByVal ) ;
		}
		sql.append(whereString.toString());

		log.debug("hql=" + sql.toString());
		return sql.toString();
	}
}
