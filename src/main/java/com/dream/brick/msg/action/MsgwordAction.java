package com.dream.brick.msg.action;

import com.dream.framework.dao.Pager;
import com.dream.brick.listener.SessionData;
import com.dream.brick.msg.bean.Msgword;
import com.dream.brick.msg.dao.MsgwordDao;
import com.dream.util.FormatDate;
import com.dream.util.StringUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 消息关键字管理
 * 
 * @author ml
 * 
 */
@Controller()
@Scope("prototype")
@RequestMapping("/msgword")
public class MsgwordAction {
	@Resource
	public MsgwordDao wordDao;
	
	@RequestMapping("/addUi")
	public String addUi(ModelMap model, String wordtype){
		model.addAttribute("wordtype",wordtype);
		//type=1 指邮件关键字 2 代表日志关键字
		return "tools/word/add";
	}
	
	
	@RequestMapping("/addWord")
	@ResponseBody
	public String addWord(Msgword word, HttpServletRequest request){
		word.setCreateTime(FormatDate.getYMdHHmmss());
		word.setUserId(SessionData.getAdminId(request));
		word.setTitle(word.getTitle().trim());
		word.setContent(word.getContent().trim());
		String message="";
		try{
			wordDao.save(word);
			message=StringUtil.jsonValue("1","添加成功");
		}catch(Exception e){
			message=StringUtil.jsonValue("0","添加失败");
		}
		return message;
	}
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(String id) throws Exception {
		String message="";
		try{
			Msgword word = new Msgword();
			word.setId(id);
			wordDao.delete(word);
			message=StringUtil.jsonValue("1","删除成功");
		}catch(Exception e){
			message=StringUtil.jsonValue("0","删除失败");
		}
		return message;
	}
	@RequestMapping("/list")
	@ResponseBody
	public String list(HttpServletRequest request, int page, int rows, String wordtype,
					   Pager pager){
		pager.setCurrentPage(page);
		pager.setPageSize(rows);
		pager.addParam("userId",SessionData.getAdminId(request));
		pager.addParam("wordtype",wordtype);
		List<Msgword> wordList =wordDao.findWordList(pager);
		request.setAttribute("wordList",wordList);
		return "tools/word/list";
	}
}
