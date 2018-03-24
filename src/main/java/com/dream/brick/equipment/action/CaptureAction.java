package com.dream.brick.equipment.action;

import com.dream.framework.dao.Pager;
import com.dream.util.extend.FtpUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.util.ArrayList;

import static javax.xml.crypto.dsig.Transform.BASE64;


@Controller
@Scope("prototype")
@RequestMapping("/capture")
public class CaptureAction {

    @RequestMapping("/iList")
    public String iList(HttpServletRequest request) {
        ServletContext servletContext = request.getServletContext();//获取ServletContext的对象 代表当前WEB应用
        try {
            FtpUtil.downfile(servletContext.getRealPath("/uploads/pictures/"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "capture/plist";
    }
    protected ArrayList<String> CalculateGeoServlet(HttpServletRequest request,
                                                    HttpServletResponse response, String params) throws ServletException, IOException,
            MalformedURLException {
        ArrayList<String> fileList = new ArrayList<String>();
        fileList = getFiles(params);
        request.setAttribute("fileList", fileList);
//        datas.put("rows", fileList);
        response.sendRedirect("view/capture/ilist.jsp");
        request.getRequestDispatcher("ilist.jsp").forward(request, response);
        return fileList;
    }
    /**
     * 通过递归得到某一路径下所有的目录及其文件
     *
     * @param filePath 文件路径
     * @return
     */
    public static ArrayList<String> getFiles(String filePath) {
        ArrayList<String> fileList = new ArrayList<String>();
        File root = new File(filePath);
        File[] files = root.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                /*
                 * 递归调用
                 */
                getFiles(file.getAbsolutePath());
                fileList.add(file.getAbsolutePath());
            } else {
                String picPathStr = file.getAbsolutePath();
                fileList.add(picPathStr);
            }
        }
        return fileList;

    }
    @RequestMapping(value = "/toShowPic/{file}/{fileName}/{id}")
    public ModelAndView toShowPic(HttpServletRequest request, HttpServletResponse response, @PathVariable String
            file, @PathVariable String fileName, @PathVariable String id) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("id", id);
        mv.addObject("file", file);
        mv.addObject("fileName", fileName);
        mv.setViewName("/capture/qlist");
        return mv;
    }


    @RequestMapping(value = "/showPic/{file}/{fileName}/{id}")
    public void showPic(HttpServletRequest request, HttpServletResponse response, @PathVariable String file, @PathVariable String fileName, @PathVariable String id) {
        String uploadUrl = String.valueOf(PropertyUtils.getPropertyDescriptors("upload_reward_rule_path"));
//        String uploadUrl =
        byte[] b = new byte[20];
        try {
            b = BASE64.getBytes(fileName);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        String str = new String(b);
        String[] strArr = str.split("/");

        String fileUrl = uploadUrl + "/" + file + "/" + strArr[0] + "." + strArr[1];
        try {
            File filePath = new File(fileUrl);
            if (filePath.exists()) {
                //读图片
                FileInputStream inputStream = new FileInputStream(filePath);
                int available = inputStream.available();
                byte[] data = new byte[available];
                inputStream.read(data);
                inputStream.close();
                //写图片
                response.setContentType("image/" + strArr[1]);
                response.setCharacterEncoding("UTF-8");
                OutputStream stream = new BufferedOutputStream(response.getOutputStream());
                stream.write(data);
                stream.flush();
                stream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @RequestMapping("/eList")

    public String List(Pager pager) {
        return "/capture/elist";
    }


}