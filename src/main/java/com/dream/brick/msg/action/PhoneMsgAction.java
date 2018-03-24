package com.dream.brick.msg.action;

import com.dream.brick.admin.bean.User;
import com.dream.brick.admin.dao.IUserDao;
import com.dream.brick.listener.BasicData;
import com.dream.brick.listener.SessionData;
import com.dream.brick.msg.bean.PhoneMessage;
import com.dream.brick.msg.dao.PhoneMsgDao;
import com.dream.util.FormatDate;
import com.dream.util.StringUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller()
@Scope("prototype")
@RequestMapping("/phonemsg")
public class PhoneMsgAction {
	
	@Resource
	public PhoneMsgDao phoneMsgDao;
	@Resource
	public IUserDao userDao;
	
	@RequestMapping("/sendMessageUi")
	public String sendMessageUi(){
		return "phonemsg/add";
	}
	
	@RequestMapping(value = "sendMessage")
	@ResponseBody
	public String sendMessage(HttpServletRequest request){
		String message="";
		String toUserIds=request.getParameter("toUser");
		String toNames=request.getParameter("toUserShow");
		String inputPhones=request.getParameter("inputPhones").trim();
		String content=request.getParameter("content").trim();
		if("".equals(content)){
			message=StringUtil.jsonValue("0", BasicData.getAppMsg("msg102"));
			//短信内容不能为空
			return message;
		}
		PhoneMessage phoneMsg=new PhoneMessage();
		phoneMsg.setToUserIds(toUserIds);
		phoneMsg.setToNames(toNames);
		
		
		User user= SessionData.getLoginAdmin(request);
		phoneMsg.setUser(user);
		phoneMsg.setCreateTime(FormatDate.getYMdHHmm());
		phoneMsg.setContent(content);
		StringBuilder sb=new StringBuilder();
		try{
			Map<String,String> phoneMap=userDao.findAllPhone();
			String[] userIdArray=toUserIds.split(";");
			for(String userId:userIdArray){
				String phone=phoneMap.get(userId);
				if(phone==null){
					continue;
				}
				if(phone.matches("\\d{11}")==false){
					continue;
				}
				sb.append(phone).append(";");
			}
			String phones=sb.toString();
			phoneMsg.setPhones(phones);
			inputPhones=inputPhones.replaceAll(" ", "");
			inputPhones=inputPhones.replaceAll("\uFF1B", ";");
			//中文逗号
			String[] inphones=inputPhones.split(";");
			phoneMsg.setInputPhones(inputPhones);
			for(String inphone:inphones){
				inphone=inphone.trim();
				if("".equals(inphone)){
					continue;
				}
				if(inphone.matches("\\d{11}")){
					
				}
			}
			phoneMsgDao.save(phoneMsg);
			message=StringUtil.jsonValue("1", BasicData.getAppMsg("msg100"));
			//发送成功
		}catch(Exception e){
			message=StringUtil.jsonValue("0", BasicData.getAppMsg("msg101"));
			//发送失败
		}
		return message;
	}
}
