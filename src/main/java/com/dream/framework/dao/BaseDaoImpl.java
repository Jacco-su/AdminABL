package com.dream.framework.dao;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;
import org.hibernate.*;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * 所有Dao继承此类
 * @author maolei
 * 
 */
@Transactional
@Repository
@SuppressWarnings("unchecked")
public class BaseDaoImpl  implements BaseDao {
	@Autowired
	protected SessionFactory sessionFactory;
	protected Class<T> entityClass;
	/**
	 * 取得当前Session.
	 * @return Session
	 */
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void save(Object entity) {
		// TODO Auto-generated method stub
		getSession().save(entity);
	}


	@Override
	public void delete(Object entity) {
		// TODO Auto-generated method stub
		getSession().delete(entity);
	}

	@Override
	public void update(Object entity) {
		// TODO Auto-generated method stub
		getSession().merge(entity);
	}
	
	public Object find(Class className, Serializable id){
		Object object = null;
		try {
			object = getSession().get(className, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> query(final String hql, final List<Object> params){
		Query query = getSession().createQuery(hql);
		query.setCacheable(true);
		if (params != null) {
			initParameter(params, query);
		}
		return query.list();

	}
	
	
	private void initParameter(List<?> params, Query query) {
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i, params.get(i));
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int getResultNumber(final String hql, final Object... params) {
		Query query = getSession().createQuery(hql);
		if(params!=null){
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		int count=0;
		try{
			List results=query.list();
			if(results.size()==1){
				Number number=(Number)results.get(0);
				count=number.intValue();
			}else if(results.size()>1){
				count=Integer.parseInt(""+results.get(0));
			}

		}catch(Exception e){
			e.printStackTrace();
		}
		return count;

	}
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> queryByPage(final String hql,
			final List<Object> condition, final IPager pager){
		int num = 0;
		Query qq = getSession().createQuery("select count(*) " + hql);
		if (condition != null) {
			initParameter(condition, qq);
		}
		num = ((Number) qq.uniqueResult()).intValue();
		pager.setTotalRow(num);
		Query q = getSession().createQuery(hql);
		q.setCacheable(true);
		if (condition != null) {
			initParameter(condition, q);
		}
		q.setFirstResult(pager.startRow());
		q.setMaxResults(pager.getPageSize());
		return q.list();

	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> query(final String hql, final IPager pager,
			final Object... params) {
		String countHql=hql+"";
		int idxFrom = hql.indexOf("from");
		countHql = "select count(*) "+ hql.substring(idxFrom,countHql.length());
		int num = getResultNumber(countHql, params);
		pager.setTotalRow(num);
		try{
			Query query = getSession().createQuery(hql);
			query.setCacheable(true);
			if(params!=null){
				for (int i = 0; i < params.length; i++) {
					query.setParameter(i, params[i]);
				}
			}
			query.setFirstResult(pager.startRow());
			query.setMaxResults(pager.getPageSize());
			return query.list();
		}catch(Exception e){
			e.printStackTrace();
			return new ArrayList<T>();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> query(final String hql, final int start,
			final int limit, final Object... args) {
		Query query = getSession().createQuery(hql);
		query.setCacheable(true);
		if(args!=null){
			for (int i = 0; i < args.length; i++) {
				query.setParameter(i, args[i]);
			}
		}
		query.setFirstResult(start);
		if (limit > 0) {
			query.setMaxResults(limit);
		}
		return query.list();

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> queryBySql(final String sql, final int start,
			final int limit, final Object... args) {
		Query query = getSession().createSQLQuery(sql);
		query.setCacheable(true);
		if(args!=null){
			for (int i = 0; i < args.length; i++) {
				query.setParameter(i, args[i]);
			}
		}
		query.setFirstResult(start);
		if(limit>0){
			query.setMaxResults(limit);
		}
		return query.list();

	}
	@SuppressWarnings("unchecked")
	@Override
	public Object queryBySql(final String sql) {
					Query query = getSession().createSQLQuery(sql);
					query.setCacheable(true);
					return query.uniqueResult();
	}
	@Override
	@SuppressWarnings("unchecked")
	public void executeUpdate(final String hql, final Object... args){
		//createQuery(hql, args).executeUpdate();
		Query query = getSession().createQuery(hql);
		if(args!=null){
			for (int i = 0; i < args.length; i++) {
				query.setParameter(i, args[i]);
			}
		}
		query.executeUpdate();
	}

	public List findList(String hql){
		return createQuery(hql).list();
	}

	/**
	 *
	 * 与find()函数可进行更加灵活的操作.
	 * @param queryString 查询
	 * @param values
	 */
	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 * @param queryString
	 * @param values 数量可变的参数,按顺序绑定.
	 * @return Query
	 */
	public Query createQuery(final String queryString, final Object... values) {
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(String.valueOf(i), values[i]);
			}
		}
		return query;
	}
	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 * @param queryString
	 * @param queryString 数量可变的参数,按顺序绑定.
	 * @return Query
	 */
	public Query createQuery(final String queryString) {
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		return query;
	}
	/**
	 * 根据查询SQL与参数列表创建Query对象.
	 * @param queryString
	 * @param queryString 数量可变的参数,按顺序绑定.
	 * @return Query
	 */
	public SQLQuery createSQLQuery(final String queryString) {
		Assert.hasText(queryString, "queryString不能为空");
		SQLQuery query = getSession().createSQLQuery(queryString);
		return query;
	}
	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 * 与find()函数可进行更加灵活的操作.
	 * @param queryString
	 * @param values 命名参数,按名称绑定.
	 * @return Query
	 */
	public Query createQuery(final String queryString, final Map<String, ?> values) {
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}

	/**
	 * 根据查询SQL与参数列表创建Query对象.
	 * @param queryString
	 * @param values 数量可变的参数,按顺序绑定.
	 * @return SQLQuery
	 */
	public SQLQuery createSQLQuery(final String queryString, final Object... values){
		SQLQuery sqlQuery = getSession().createSQLQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				sqlQuery.setParameter(String.valueOf(i), values[i]);
			}
		}
		return sqlQuery;
	}

	/**
	 * 根据查询SQL与参数列表创建Query对象.
	 * @param queryString
	 * @param values 命名参数,按名称绑定.
	 * @return SQLQuery
	 */
	public SQLQuery createSQLQuery(final String queryString, final Map<String, ?> values) {
		SQLQuery sqlQuery = getSession().createSQLQuery(queryString);
		if (values != null) {
			sqlQuery.setProperties(values);
		}
		return sqlQuery;
	}
	/**
	 * 根据Criterion条件创建Criteria.
	 * 与find()函数可进行更加灵活的操作.
	 * @param criterions 数量可变的Criterion.
	 * @return Criteria
	 */
	public Criteria createCriteria(final Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	public Criteria createCriteria(Boolean isCache,final Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		criteria.setCacheable(isCache);
		return criteria;
	}
}
