package com.dream.brick.equipment.dao.impl;

import com.dream.framework.dao.BaseDaoImpl;
import com.dream.brick.equipment.bean.SysArea;
import com.dream.brick.equipment.dao.SysAreaDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public class SysAreaDaoImpl extends BaseDaoImpl implements SysAreaDao {
	public List<SysArea> findAreaList(int grade, String pid, String areaCode){
		StringBuilder sb=new StringBuilder();
		sb.append("from SysArea where 1=1");
		sb.append(" and grade=").append(grade);
        if(StringUtils.isNotEmpty(pid)){
			sb.append(" and pid='").append(pid).append("'");
			//return findList("from SysArea where grade="+grade+" and pid='"+pid+"'");
		}
		if (StringUtils.isNotEmpty(areaCode)){
			sb.append(" and areaCode like '").append(areaCode).append("%'");
		}
		return findList(sb.toString());
	}

}
