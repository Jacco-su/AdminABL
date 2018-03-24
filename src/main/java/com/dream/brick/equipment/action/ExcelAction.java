package com.dream.brick.equipment.action;

import com.dream.brick.admin.bean.User;
import com.dream.brick.equipment.bean.*;
import com.dream.brick.equipment.dao.*;
import com.dream.brick.listener.BasicData;
import com.dream.brick.listener.SessionData;
import com.dream.framework.dao.Pager;
import com.dream.util.FormatDate;
import com.dream.util.PropertyNote;
import com.dream.util.StringUtils;
import com.dream.util.excelTools.ExcelUtil;
import com.dream.util.excelTools.ExcelUtils;
import com.dream.util.excelTools.JsGridReportBase;
import com.dream.util.excelTools.TableData;
import com.google.gson.Gson;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Excel controller
 */
@Controller
@RequestMapping("excel")
public class ExcelAction extends ExcelUtil{
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private HttpServletRequest request;
    private int P_INDEX=1;
    public ExcelAction(){
        //dataMap.put("",userService.getAll());
    }
    /**
     * 导入excel跳转
     *
     * @param model
     * @param
     * @return
     */
    @RequestMapping(value = "import", method = RequestMethod.GET)
    public String importExcel(Model model) {
        return "excel/import";
    }



    /**
     * 导出excel预览
     *
     * @param uploadExcel
     * @param
     * @return
     */
    @RequestMapping(value = "import/{clazz}", method = RequestMethod.POST)
    @ResponseBody
    public String usersImportShow(@RequestParam MultipartFile uploadExcel,
                                  @PathVariable String clazz, Model model) {
        String filePath = "";
        if (!uploadExcel.isEmpty()) {
            try {
                String path = "uploads/excel/" + System.currentTimeMillis() + (int) (Math.random() * 100) + "." + StringUtils.getFileSuffix(uploadExcel.getOriginalFilename());
                // 文件保存路径
                filePath = request.getSession().getServletContext().getRealPath("/")
                        + path;
                // 转存文件
                uploadExcel.transferTo(new File(filePath));
            } catch (Exception e) {
                e.printStackTrace();
                return "error";
            }
        }
        // 获取前台excel的输入流
        ExcelUtil excelUtil = new ExcelUtil();
        excelUtil.setStartReadPos(1);
        List<Row> rowList = null;
        try {
            rowList = excelUtil.readExcel(filePath);
            //saveExcel(rowList,clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
        model.addAttribute("list",rowList);
        model.addAttribute("clazz", clazz);
        return "success";
    }
    /**
     * 导出excel，自定义导出excel
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("export/exportExcel")
    public String exportExcel(HttpServletRequest request,
                            HttpServletResponse response,
                            String title,
                            String hearder,
                            String field, String clazz,String queryParams) throws Exception {
        response.setContentType("application/msexcel;charset=UTF-8");
/*
        TableData td = ExcelUtils.createTableData(list, ExcelUtils.createTableHeader(hearders), fields);
        JsGridReportBase report = new JsGridReportBase(request, response);
        report.exportToExcel(title, SessionData.getLoginAdmin(request).getUsername(), td);*/
        return null;
    }

  /* //*
    @RequestMapping(value = "import/{pack}/{clazz}", method = RequestMethod.GET)
    public String usersImport(@PathVariable String pack, @PathVariable String clazz, Model model) throws ClassNotFoundException {
        Class forName = Class.forName("com.dream.brick."+pack+".bean."+clazz+"");
        model.addAttribute("map", getFiledName(forName));//获取自定义属性值
        model.addAttribute("clazz", clazz);//类
        model.addAttribute("clazzNote", getClazzNote(forName));//excel表名
        return "excel/import";
    }*/
    /**
     * 导入excel跳转
     * @param clazz->需要导入excel对应的实体名
     * @param pack->需要导入excel对应的上层包名
     * @param queryParams 查询参数
     * @return 自定义属性
     */
    @RequestMapping(value = "{type}/{pack}/{clazz}", method = RequestMethod.GET)
    public String reportForm(@PathVariable String type,
                             @PathVariable String pack,
                             @PathVariable String clazz,
                             String  queryParams,
                             Model model) throws ClassNotFoundException {
       /**
        * @author       陶乐乐(wangyiqianyi@qq.com)
        * @Description: 导出excel,导入excel跳转
        * @date         2016-03-26 11:50:08
        * @params       [type:import/export,pack:包, clazz:类, model]
        * @return       java.lang.String
        * @throws       ClassNotFoundException
        */
        model.addAttribute("clazz", clazz);
        if(StringUtils.isNotEmpty(clazz)){
            if (clazz.indexOf("Projflow")>-1) {
                clazz="Projflow";
            }
        }
        Class forName = Class.forName("com.dream.brick."+pack+".bean."+clazz+"");
        model.addAttribute("map", getFiledName(forName));
        model.addAttribute("clazzNote",getClazzNote(forName));
        model.addAttribute("queryParams",queryParams);
        return "excel/"+type;
    }
    private Map getFiledName(Class<?> clazz) {
        /**
         * @author       陶乐乐(wangyiqianyi@qq.com)
         * @Description: 获取自定义注解属性 指向实体自定义属性
         * @date         2016-03-26 12:48:30
         * @params       [clazz]
         * @return       java.util.Map
         * @throws
         */
        Map map = new LinkedHashMap();
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0,len=fields.length; i <len ; i++) {
            PropertyNote propertyNote = fields[i].getAnnotation(PropertyNote.class);
            if (propertyNote != null) map.put(fields[i].getName(), propertyNote.name());
        }
        return map;
    }

    private String[] getHearderNames(Class<?> clazz) {
        /**
         * @author       陶乐乐(wangyiqianyi@qq.com)
         * @Description: 获取自定义注解属性 指向实体自定义属性
         * @date         2016-03-26 12:48:30
         * @params       [clazz]
         * @return       java.util.Map
         * @throws
         */
        Field[] fields = clazz.getDeclaredFields();
        String str[]=new String[fields.length];
        for (int i = 0,len=fields.length; i <len ; i++) {
            PropertyNote propertyNote = fields[i].getAnnotation(PropertyNote.class);
            if (propertyNote != null) str[i]=propertyNote.name();
        }
        return str;
    }

    private String[] getFiledNames(Class<?> clazz) {
        /**
         * @author       陶乐乐(wangyiqianyi@qq.com)
         * @Description: 获取自定义注解属性 指向实体自定义属性
         * @date         2016-03-26 12:48:30
         * @params       [clazz]
         * @return       java.util.Map
         * @throws
         */
        Field[] fields = clazz.getDeclaredFields();
        String str[]=new String[fields.length];
        for (int i = 0,len=fields.length; i <len ; i++) {
            PropertyNote propertyNote = fields[i].getAnnotation(PropertyNote.class);
            if (propertyNote != null) str[i]=fields[i].getName();
        }
        return str;
    }

    public static void main(String[] args) {
        String s=" , , , , ";
        System.out.println(s.length());
        System.out.println(s.split(",")[4]);
    }
    private String getClazzNote(Class<?> clazz) {
        /**
         * @author       陶乐乐(wangyiqianyi@qq.com)
         * @Description: 获取类自定义注解 指向excel表名
         * @date         2016-03-26 11:22:43
         * @params       [clazz]
         * @return       java.lang.String
         * @throws
         */
        String clazzNote = "excel";
        PropertyNote propertyNote = clazz.getAnnotation(PropertyNote.class);
        if (propertyNote != null) clazzNote = propertyNote.name();
        return clazzNote;
    }

    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

}
