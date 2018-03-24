package com.dream.brick.equipment.dao.impl;


import com.dream.brick.equipment.bean.Area;
import com.dream.brick.equipment.bean.Qgdis;
import com.dream.brick.equipment.dao.QgdisDao;
import com.dream.framework.dao.BaseDaoImpl;
import com.dream.framework.dao.Pager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库操作实现类
 */


@Transactional
@Repository
public class QgdisDaoImpl extends BaseDaoImpl implements QgdisDao {
    public List<Area> findAllArea() {
        String hql = "from Area t ";
        return findList(hql);
    }

    public List<Qgdis> findAllQgdis() {
        String hql = "from Qgdis t ";
        return findList(hql);
    }

    public Area findAreaByAreacode(String areacode) {
        return (Area) find(Area.class, areacode);
    }

    public List<Qgdis> findQgdisByAreacode(String areacode) {
        String hql = "from Qgdis t where deptId="+areacode;
        return findList(hql);
    }

    public List<Qgdis> findQgdisList(Pager pager) {
        String hql = "from Qgdis t ";
        return query(hql, pager);
    }

    public List<Qgdis> findDisIdAndName() {
        String hql = "select id,name,deptId from t_qgdis";
        List<Object[]> results = query(hql, 0, 0);
        List<Qgdis> list = new ArrayList<Qgdis>();
        for (Object[] objs : results) {
            Qgdis dis = new Qgdis();
            dis.setId(String.valueOf(objs[0]));
            dis.setName(String.valueOf(objs[1]));
            //dept.setParentId(String.valueOf(objs[2]));
            // dept.setHaskh((Integer)objs[3]);
            list.add(dis);
        }
        return list;
    }

    @Override
    public void addDis(Qgdis qgdis) {
        save(qgdis);
    }

    @Override
    public List<Qgdis> findQgdisList(String deptId, String dissName, Pager pager) {
        StringBuilder hql=new StringBuilder();
        hql.append("from Qgdis t  where 1=1 ");
        if(StringUtils.isNotEmpty(deptId)){
            hql.append("and t.dept.areacode  like '").append(deptId).append("%'");
        }
        if(StringUtils.isNotEmpty(dissName)){
            hql.append("and t.name like '%").append(dissName).append("%'");
        }
        return query(hql.toString(), pager);
    }
    @Override
    public List<Qgdis> findQgdisList(String deptId, String dissName) {
        StringBuilder hql=new StringBuilder();
        hql.append("from Qgdis t  where 1=1 ");
        if(StringUtils.isNotEmpty(deptId)){
            hql.append("and t.dept.areacode  like '").append(deptId).append("%'");
        }
        if(StringUtils.isNotEmpty(dissName)){
            hql.append("and t.name like '%").append(dissName).append("%'");
        }
        return findList(hql.toString());
    }
}
