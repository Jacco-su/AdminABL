package com.dream.brick.equipment.dao.impl;

import com.dream.framework.dao.BaseDaoImpl;
import com.dream.framework.dao.Pager;
import com.dream.brick.equipment.bean.Log;
import com.dream.brick.equipment.dao.LogDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public class LogDaoImpl extends BaseDaoImpl implements LogDao {
	public List<Log> findLogList(Pager pager){
		 String areacode=pager.getParamValue("areacode");
		 //地区编码，不同地区只能查看自己管辖的地区数据	
		 String hql="from Log t order by createTime desc";
		 return query(hql, pager);
	}
}
