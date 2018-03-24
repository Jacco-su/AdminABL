package com.dream.brick.equipment.dao.impl;

import com.dream.brick.equipment.bean.Area;
import com.dream.brick.equipment.dao.AreaInfoDao;
import com.dream.framework.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by taller on 17/11/11.
 */
@Transactional
@Repository
public class AreaInfoDaoImpl extends BaseDaoImpl implements AreaInfoDao {
    public List<Area> findAreaByParentId(String parentcode){
        String hql = "select t from Area t where t.parentcode='" + parentcode + "'order by t.areacode";
        return findList(hql);
    }
    public List<Area> findAreaByCode(String areacode){
        String hql="";
        if("86".equals(areacode)){
            //查询全部省级地区
            hql="select t from Area t where t.parentcode='0' order by t.areacode";
        }else{
            //查询编码对应的地区
            hql="select t from Area t where t.areacode='"+areacode+"' ";
        }
        return findList(hql);
    }

    public Area cacheAreaById(String id) {
        return (Area)find(Area.class,id);
    }


}
