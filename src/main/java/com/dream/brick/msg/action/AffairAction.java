package com.dream.brick.msg.action;

import com.dream.framework.dao.Pager;
import com.dream.brick.listener.SessionData;
import com.dream.brick.msg.bean.Affair;
import com.dream.brick.msg.dao.AffairDao;
import com.dream.util.AppMsg;
import com.dream.util.FormatDate;
import com.dream.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 工作日志
 * 
 * @author ml
 * 
 */
@Controller()
@Scope("prototype")
@RequestMapping("/affair")
public class AffairAction {
	@Resource
	private AffairDao affairDao;
	@RequestMapping("/index")
	public String index(ModelMap model){
		model.addAttribute("startTime",FormatDate.getYMd());
		return "affair/rili_my";
	}
	
	@RequestMapping("/more")
	public String more(String startTime, ModelMap model, String type){
		if(startTime==null){
			startTime="";
		}
		model.addAttribute("startTime", startTime);
		if("1".equals(type)){
			return "affair/list";
		}else{
			return "affair/all_affair";
		}
		
	}
	
	@RequestMapping("/allAffair")
	public String allAffair(ModelMap model){
		model.addAttribute("startTime", FormatDate.getYMd());
		return "affair/rili_all";
	}
	
	@RequestMapping("/wannianli")
	public String wannianli(ModelMap model){
		return "affair/wnl";
	}
	
	@RequestMapping("/prAdd")
	public String prAdd(String startTime, ModelMap model){
		model.addAttribute("startTime",startTime);
		return "affair/add";
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public String add(Affair affair, HttpServletRequest request){
		affair.setCreateTime(FormatDate.getYMdHHmmss());
		affair.setUser(SessionData.getLoginAdmin(request));
		affair.setTitle(affair.getTitle().trim());
		affair.setContent(affair.getContent().trim());
		affair.setState(0);
		String message="";
		try{
			affairDao.save(affair);
			message=StringUtil.jsonValue("1",AppMsg.ADD_SUCCESS+","+affair.getId());
			//添加成功
		}catch(Exception e){
			message=StringUtil.jsonValue("0",AppMsg.ADD_ERROR+",0");
			//添加失败
		}
		return message;
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public String list(HttpServletRequest request, int page, int rows,
					   Pager pager) throws Exception {
		pager.setCurrentPage(page);
		pager.setPageSize(rows);
		JSONObject datas = new JSONObject();
		String deptId=request.getParameter("deptId");
		String userId=request.getParameter("userId");
		String date=request.getParameter("date");
		pager.addParam("userId",userId);
		pager.addParam("deptId",deptId);
		pager.addParam("date",date);
		List<Affair> msgs =affairDao.findArrair(pager);
		datas.put("total", pager.getTotalRow());
		datas.put("rows", msgs);
		return datas.toString();
	}
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(String ids) throws Exception {
		String message="";
		try{
			if (StringUtils.isNotBlank(ids)) {
				for (String id : ids.split(",")) {
					Affair msg = new Affair();
					msg.setId(id);
					affairDao.delete(msg);
				}
			}
			message=StringUtil.jsonValue("1",AppMsg.DEL_SUCCESS);
		}catch(Exception e){
			message=StringUtil.jsonValue("0",AppMsg.DEL_ERROR);
		}
		return message;
	}
	
	@RequestMapping("/prUpdate")
	public String prUpdate(ModelMap model, String id){
		Affair affair=affairDao.find(Affair.class, id);
		model.addAttribute("affair",affair);
		return "affair/update";
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public String update(Affair affair, HttpServletRequest request){
		Affair oldAffair=affairDao.find(Affair.class, affair.getId());
		oldAffair.setTitle(affair.getTitle().trim());
		oldAffair.setContent(affair.getContent().trim());
		String message="";
		try{
			affairDao.update(oldAffair);
			message=StringUtil.jsonValue("1",AppMsg.UPDATE_SUCCESS);
			//修改成功
		}catch(Exception e){
			message=StringUtil.jsonValue("0",AppMsg.UPDATE_ERROR);
			//修改失败
		}
		return message;
	}
	
	
	@RequestMapping("/prView")
	public String prView(ModelMap model, String id){
		Affair affair=affairDao.find(Affair.class, id);
		model.addAttribute("affair",affair);
		return "affair/view";
	}
	@RequestMapping("/findByYearMonth")
	@ResponseBody
	public String findByYearMonth(HttpServletRequest request) throws Exception {
		Pager pager=new Pager();
		String yearMonth=request.getParameter("yearMonth");
		String date=request.getParameter("date");
		String userId=request.getParameter("userId");
		pager.addParam("userId",userId);
		pager.addParam("yearMonth",yearMonth);
		pager.addParam("date",date);
		JSONObject datas = new JSONObject();
		List<Affair> msgs =affairDao.findByYearMonth(pager);
		for(Affair affair:msgs){
			affair.setContent("");
			String title=affair.getTitle();
			if(title==null||"".equals(title)){
				
			}else{
				if(title.length()>8){
					title=title.substring(0,8)+"...";
					affair.setTitle(title);
				}
			}
		}
		datas.put("rows", msgs);
		return datas.toString();
	}
}
