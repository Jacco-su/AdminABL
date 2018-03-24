package com.dream.brick.equipment.action;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dream.brick.admin.bean.Department;
import com.dream.brick.admin.dao.IDeptDao;
import com.dream.brick.equipment.bean.*;
import com.dream.brick.equipment.dao.*;
import com.dream.brick.listener.SessionData;
import com.dream.framework.dao.Pager;
import com.dream.socket.entity.AuthModel;
import com.dream.socket.entity.DataProtocol;
import com.dream.socket.entity.JsonDataProtocol;
import com.dream.socket.utils.ByteUtil;
import com.dream.socket.utils.Constants;
import com.dream.util.AppMsg;
import com.dream.util.RedisTemplateUtil;
import com.dream.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;

/**
 * 授权记录 操作 实现类
 *
 * @author ml
 */
@Controller
@Scope("prototype")
@RequestMapping("/authorization")
public class AuthorizationAction {
    @Resource
    private IAuthorizationDao authorizationDao;
    @Resource
    private IKeyssDao ikeyssDao;
    @Resource
    private QgdisDao disDao;

    @Resource
    private CollectorDao collectorDao;

    @Resource
    private CollectoreDao collectoreDao;

    @Resource
    private ILocksDao ilocksDao;

    @Resource
    private RedisTemplate redisTemplate;
    private RedisTemplateUtil redisTemplateUtil = null;

    private boolean checkTime=true;

    @Resource
    private IDeptDao deptDao;
    @RequestMapping("/prList")
    public String prList(String authorizationId, HttpServletRequest request)
            throws Exception {
        return "admin/authorization/list";
    }

    @RequestMapping("/list")
    @ResponseBody
    public String list(int page, int rows, String name, String username, Pager pager)
            throws Exception {
        pager.setCurrentPage(page);
        pager.setPageSize(rows);
        JSONObject datas = new JSONObject();
        List<Authorization> list = authorizationDao.findAuthorizationList(pager);
        List<Authorization> list2 = authorizationDao.findAllAuthorization();
        datas.put("total", pager.getTotalRow());
        datas.put("rows", list);
        datas.put("rows2", list2);
        return datas.toString();
    }


    @RequestMapping("/prView")
    public String prView(String id, ModelMap model) {
        Authorization authorizationa = authorizationDao.find(Authorization.class, id);
        model.addAttribute("authorizationa", authorizationa);
        return "admin/authorization/view";
    }
    //在线授权
    @RequestMapping("/prViewAuth")
    public String prViewAuth( ModelMap model) {
        return "admin/authorization/authList";
    }
    //离线授权
    @RequestMapping("/prViewOfflineAuth")
    public String prViewOfflineAuth( ModelMap model) {
        return "admin/authorization/authOfflineList";
    }
    //离线区域授权
    @RequestMapping("/prViewAreaOfflineAuth")
    public String prViewAreaOfflineAuth( ModelMap model) {
        return "admin/authorization/authAreaOfflineList";
    }
    //区域授权
    @RequestMapping("/prAreaAuth")
    public String prAreaAuth( ModelMap model) {
        return "admin/authorization/authAreaList";
    }
    @RequestMapping("/workView")
    public String workView(String id, ModelMap model) {
        Authorization authorizationa = authorizationDao.find(Authorization.class, id);
        model.addAttribute("authorizationa", authorizationa);
        return "admin/authorization/workview";
    }


    @RequestMapping("/prUpdate")
    public String prUpdate(String Id, ModelMap model) {
//        Authorization authorizationa = authorizationDao.find(Authorization.class, Id);
//        model.addAttribute("authorization", authorizationa);
        return "admin/authorization/update";
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String update(@ModelAttribute Authorization authorizationa) {
        String message = "";
        try {
//				Area area= BasicData.findAreaByAreacode(orga.getAreacode());
//				authorizationa.setAreaname(area.getAreaname());
            //authorizationa.setAddress(authorizationa.getAddress().trim());
            //authorizationa.setName(authorizationa.getName().trim());
            authorizationDao.update(authorizationa);
            message = StringUtil.jsonValue("1", AppMsg.UPDATE_SUCCESS);
        } catch (Exception e) {
            message = StringUtil.jsonValue("0", AppMsg.UPDATE_ERROR);
        }
        return message;
    }


    @RequestMapping("/prAdd")
    public String prAdd(String deptId, ModelMap model) {
        //List<Role> roles = roleDao.findRoleName(false);
//        List<Authorization> deptList = authorizationDao.findDeptIdAndName();
//        model.addAttribute("deptId", deptId);
        //model.addAttribute("roles", roles);
//        model.addAttribute("deptList", deptList);

        return "admin/authorization/add";
    }

    // 离线授权


    @RequestMapping("/prOffLine")
    public String proffLine(String authorizationId, HttpServletRequest request)
            throws Exception {
        return "commons/notBuild";
    }

    //在线授权
    @RequestMapping("/prOnLine")

    public String pronLine(String authorizationId, HttpServletRequest request)
            throws Exception {
        return "admin/authorization/onLine";
    }

    @RequestMapping("/onLine")
    @ResponseBody
    public String offLine(int page, int rows, String authorizationId, String uName, Pager pager) throws Exception {
        return null;
    }
    @RequestMapping("/distribution")
    @ResponseBody
    public String getDistributionAction(String disaId){
        if (StringUtils.isNotEmpty(disaId)) {
            Department department =deptDao.find(Department.class,disaId);
            disaId=department.getAreacode();
            return JSON.toJSONString(authorizationDao.findListDisa(disaId));
        } else {

            return JSON.toJSONString(disDao.findAllQgdis());
        }

    }
//    @RequestMapping("/disloke")
//    @ResponseBody
//    public String getDisLoke(){
//            return JSON.toJSONString(disDao.findAllQgdis());
//    }



    @RequestMapping("/user")
    @ResponseBody
    public String getUserAction(String disaId){
        if(StringUtils.isNotEmpty(disaId)){
            Department department =deptDao.find(Department.class,disaId);
            disaId=department.getAreacode();
        }
        return JSON.toJSONString(authorizationDao.findList("from User where  name != 'admin' and name != 'alluser' and status!=0 and dept.areacode like '"+disaId+"%'"));
    }
    @RequestMapping("/disa/collector")
    @ResponseBody
    public String getCollectorAction(String disaId){
        if(StringUtils.isNotEmpty(disaId)){
            if(!disaId.contains("请选择")){
                return JSON.toJSONString(authorizationDao.findList("from Collector where disId="+disaId));
            }
        }
        return null;
    }
    @RequestMapping("collector/collectore")
    @ResponseBody
    public String getCollectoreAction(String collectorId){
        if(StringUtils.isNotEmpty(collectorId)) {
            if (!collectorId.contains("请选择")) {
                return JSON.toJSONString(authorizationDao.findList("from Collectore where cid=" + collectorId));
            }
        }
        return null;

    }

    @RequestMapping("/keys")
    @ResponseBody
    public String getKeysAction(){
        return JSON.toJSONString(ikeyssDao.findAllKeyss());
    }

    @RequestMapping("/disa/locks")
    @ResponseBody
    public String getLocksAction(String disaId) {
        if(StringUtils.isNotEmpty(disaId)) {
            if (!disaId.contains("请选择")) {
                return JSON.toJSONString(authorizationDao.findList("from Locks where dissId=" + disaId));
            }
        }
        return null;
    }
    @RequestMapping("/keys/user")
    @ResponseBody
    public String getKeysUserAction(String userId){

        return JSON.toJSONString(ikeyssDao.findKeyssUserList(userId));
    }


    @RequestMapping("/allCollector")
    @ResponseBody
    public String allCollector(String deptId) throws ParseException {
        if(StringUtils.isNotEmpty(deptId)){
            Department department =deptDao.find(Department.class,deptId);
            deptId=department.getAreacode();
        }
        return JSON.toJSONString(collectorDao.findCollectorList(deptId), SerializerFeature.DisableCircularReferenceDetect);
    }


    @RequestMapping("/areaAuth")
    @ResponseBody
    public String areaAuth(String areaId, String startDate,String endDate,String userId,HttpServletRequest request) {
        String adminId=SessionData.getAdminId(request);
        List<Qgdis> qgdisList=disDao.findQgdisByAreacode(areaId);
       //System.out.println(qgdisList);
        if(qgdisList!=null&&qgdisList.size()>0){
            for (int i = 0; i < qgdisList.size(); i++) {
                Qgdis qgdis=qgdisList.get(i);
                //读取采集器
                List<Collector> collectorList=collectorDao.findCollectorByQgdisid(qgdis.getId());
                //System.out.println(collectorList);
                if(collectorList!=null&&collectorList.size()>0) {
                    for (int j = 0; j < collectorList.size(); j++) {
                        Collector collector=collectorList.get(j);
                        //读取控制器
                       List<Collectore>  collectoreList=collectoreDao.findCollectoreByCollector(collector.getId());
                       // System.out.println(collectoreList);
                        if(collectoreList!=null&&collectoreList.size()>0) {
                            for (int z= 0; z < collectoreList.size(); z++) {
                                Collectore collectore=collectoreList.get(z);
                                System.out.println(collectore.getCeMAC());
                                if(StringUtils.isNotEmpty(collectore.getCeMAC())){
                                    String macAddess=collectore.getCeMAC().replace(":","");
                                    macAddess="00000000000000000000"+macAddess;
                                    String  authModel=null;
                                    if(checkTime){
                                        //钥匙绑定
                                        authModel=new AuthModel(new byte[]{7}, AuthModel.toData(7,14), Constants.KEY).toString();
                                        auth("7",macAddess,collector.getCcode(),adminId,authModel);
                                        authModel=new AuthModel(new byte[]{12},AuthModel.toData(12,14),Constants.LOCK_KEY).toString();//校时成功
                                        auth("12",macAddess,collector.getCcode(),adminId,authModel);
                                        checkTime=false;
                                    }
                                   List<Locks> locksList=ilocksDao.findLocksByDis(qgdis.getId());
                                    if(locksList!=null&&locksList.size()>0) {
                                        for (int ii = 0; ii < locksList.size(); ii++) {
                                            Locks locks=locksList.get(ii);
                                            System.out.println(locks.getLockCode());
                                            authModel=new AuthModel(new byte[]{5},AuthModel.AuthorizationKey(ByteUtil.hexStrToByteArray(ByteUtil.addZeroForNum(userId,8)),locks.getLockCode(),collectore.getCeMAC(),startDate,endDate),Constants.LOCK_KEY).toString();//
                                            System.out.println("开始授权！");
                                            auth("5",macAddess,collector.getCcode(),adminId,authModel);
                                        }
                                    }
                                }
                            }
                       }
                    }
                }
            }
        }
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return StringUtil.jsonValue("1", "授权成功！");
    }
    private String  auth(String t,String macAddess,String collectorId,String adminId,String authModel){
        redisTemplateUtil = new RedisTemplateUtil(redisTemplate);
        DataProtocol dataProtocol=new DataProtocol(new byte[]{00,01}, ByteUtil.hexToBytes(macAddess),ByteUtil.hexToBytes(authModel));
        JsonDataProtocol jsonDataProtocol=new JsonDataProtocol();
        jsonDataProtocol.setCollectorId(collectorId);
        jsonDataProtocol.setContent(dataProtocol.toString());
        jsonDataProtocol.setDataType("client");
        System.out.println(dataProtocol.toString());
        String authKey=JSON.toJSONString(jsonDataProtocol)+";"+adminId;
        for (int i = 0; i < 3; i++) {
            redisTemplateUtil.setList("lanya-lite", authKey); }
            Object o = redisTemplateUtil.get(authKey);
            if (o == null) {
                return   "";
            } else {
                if("5".equals(t)){
                   /* if("授权成功!".equals(o.toString())){
                    }*/

                }
                return  StringUtil.jsonValue("1", o.toString());
            }

    }
}
