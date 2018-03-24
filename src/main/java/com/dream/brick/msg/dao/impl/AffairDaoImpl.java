package com.dream.brick.msg.dao.impl;

import com.dream.framework.dao.BaseDaoImpl;
import com.dream.framework.dao.Pager;
import com.dream.brick.msg.bean.Affair;
import com.dream.brick.msg.dao.AffairDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 个人事物数据库访问实现类
 * @author maolei
 */
@Transactional
@Repository()
public class AffairDaoImpl  extends BaseDaoImpl implements AffairDao{
	public List<Affair> findArrair(Pager pager){
		StringBuilder sb=new StringBuilder("");
		sb.append("select t from Affair t where 1=1 ");
		String userId=pager.getParamValue("userId");
		String deptId=pager.getParamValue("deptId");
		String date=pager.getParamValue("date");
		if(userId!=null&&!"".equals(userId)){
			sb.append(" and t.user.id='").append(userId).append("' ");
		}
		if(deptId!=null&&!"".equals(deptId)){
			sb.append(" and t.user.dept.id='").append(deptId).append("' ");
		}
		if(date!=null&&!"".equals(date)){
			sb.append(" and t.startTime='").append(date).append("' ");
		}
		
		sb.append(" order by t.startTime desc,t.createTime desc ");
		return query(sb.toString(),pager);
	}
	public List<Affair> findByYearMonth(Pager pager){
		StringBuilder sb=new StringBuilder("");
		sb.append("select t from Affair t where 1=1 ");
		String userId=pager.getParamValue("userId");
		String deptId=pager.getParamValue("deptId");
		String yearMonth=pager.getParamValue("yearMonth");
		String date=pager.getParamValue("date");
		if(userId!=null&&!"".equals(userId)){
			sb.append(" and t.user.id='").append(userId).append("' ");
		}
		if(deptId!=null&&!"".equals(deptId)){
			sb.append(" and t.user.dept.id='").append(deptId).append("' ");
		}
		if(yearMonth!=null&&!"".equals(yearMonth)){
			sb.append(" and t.startTime like '").append(yearMonth).append("%' ");
		}
		if(date!=null&&!"".equals(date)){
			sb.append(" and t.startTime='").append(date).append("' ");
		}
		sb.append(" order by t.startTime desc,t.createTime desc ");
		return query(sb.toString(),0,0);
	}
}
