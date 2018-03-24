package com.dream.framework.dao;
import org.hibernate.Query;
import org.hibernate.SQLQuery;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
public interface BaseDao {
	void save(Object entity);
	void delete(Object entity);
	void update(Object entity);
	public <T> T find(Class className, Serializable id);
	public <T> List<T> query(final String hql, final List<Object> params);
	public int getResultNumber(final String hql, final Object... params);
	public <T> List<T> query(final String hql, final IPager pager, final Object... params);
	public <T> List<T> query(final String hql, final int start,
							 final int limit, final Object... params);
	public <T> List<T> queryBySql(final String sql, final int start,
								  final int limit, final Object... params);
	public <T> List<T> queryByPage(final String hql,
								   final List<Object> condition, final IPager pager);
	public void executeUpdate(final String hql, final Object... args);
	public List findList(String hql);
	public  Object queryBySql(final String sql);
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
	public Query createQuery(final String queryString, final Object... values) ;
	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 * @param queryString
	 * @param queryString 数量可变的参数,按顺序绑定.
	 * @return Query
	 */
	public Query createQuery(final String queryString) ;
	/**
	 * 根据查询SQL与参数列表创建Query对象.
	 * @param queryString
	 * @param queryString 数量可变的参数,按顺序绑定.
	 * @return Query
	 */
	public SQLQuery createSQLQuery(final String queryString) ;
	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 * 与find()函数可进行更加灵活的操作.
	 * @param queryString
	 * @param values 命名参数,按名称绑定.
	 * @return Query
	 */
	public Query createQuery(final String queryString, final Map<String, ?> values);

	/**
	 * 根据查询SQL与参数列表创建Query对象.
	 * @param queryString
	 * @param values 数量可变的参数,按顺序绑定.
	 * @return SQLQuery
	 */
	public SQLQuery createSQLQuery(final String queryString, final Object... values);

	/**
	 * 根据查询SQL与参数列表创建Query对象.
	 * @param queryString
	 * @param values 命名参数,按名称绑定.
	 * @return SQLQuery
	 */
	public SQLQuery createSQLQuery(final String queryString, final Map<String, ?> values) ;
}
