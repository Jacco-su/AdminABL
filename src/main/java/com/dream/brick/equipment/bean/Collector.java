package com.dream.brick.equipment.bean;

import javax.persistence.*;

/**
 * 采集器 数据库防问实体 类
 *
 * @author
 * @date 2017-12-05
 */
@Entity
@Table(name = "t_collector")
public class Collector {
    /**
     *
     */

    private String id;
    private String ccode; //采集器ID
    private String cip;// 采集器IP地址
    //    private String disId;// 所在站点
//    private Qgdis disName; // 所在站点名称
    private Qgdis dis;
    // private String collectorDiss;
    private String cdate;// 日期

//    private int sortorder;// 采集器显示顺序

    // private Set<Employee> emps = new HashSet<>();
    // private List<Qgdis> dis=new ArrayList<Qgdis>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCcode() {
        return ccode;
    }

    public void setCcode(String ccode) {
        this.ccode = ccode;
    }



//    @ManyToOne(optional = false, fetch = FetchType.EAGER)
//    @JoinColumn(name = "disId", nullable = false, updatable = true)
//    public Qgdis getDisName() {
//        return disName;
//    }
//
//    public void setDisName(Qgdis disName) {
//        this.disName = disName;
//    }

    public String getCdate() {
        return cdate;
    }

    public void setCdate(String cdate) {
        this.cdate = cdate;
    }


//    public int getSortorder() {
//        return sortorder;
//    }
//
//    public void setSortorder(int sortorder) {
//        this.sortorder = sortorder;
//    }

    public String getCip() {
        return cip;
    }

    public void setCip(String cip) {
        this.cip = cip;
    }


//    @Column(name = "collector_diss")
//    public String getCollectorDiss() {
//        return collectorDiss;
//    }
//
//    public void setCollectorDiss(String collectorDiss) {
//        this.collectorDiss = collectorDiss;
//    }
@ManyToOne(optional = false, fetch = FetchType.EAGER)
@JoinColumn(name = "disId", nullable = false, updatable = true)
public Qgdis getDis() {
    return dis;
}

    public void setDis(Qgdis dis) {
        this.dis = dis;
    }

}
