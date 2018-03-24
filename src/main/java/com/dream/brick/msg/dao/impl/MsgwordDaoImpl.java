package com.dream.brick.msg.dao.impl;

import com.dream.framework.dao.BaseDaoImpl;
import com.dream.framework.dao.Pager;
import com.dream.brick.msg.bean.Msgword;
import com.dream.brick.msg.dao.MsgwordDao;

import java.util.List;

public class MsgwordDaoImpl  extends BaseDaoImpl implements MsgwordDao{
	public List<Msgword> findWordList(Pager pager){
		String hql="select t from Msgword t where t.userId=? and t.type=? order by t.createTime desc";
		int start=pager.getCurrentPage();
		int limit=pager.getPageSize();
		String userId=pager.getParamValue("userId");
		int type= Integer.parseInt(pager.getParamValue("wordtype"));
		return query(hql,start,limit,new Object[]{userId,type});
	}
}
