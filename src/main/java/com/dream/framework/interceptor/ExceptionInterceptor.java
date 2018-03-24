package com.dream.framework.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.dream.util.StringPrintWriter;


/**
 * 全局异常处理
 * 
 * @author maolei
 * 
 */

@Component
public class ExceptionInterceptor implements HandlerExceptionResolver {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public ModelAndView resolveException(HttpServletRequest req,
			HttpServletResponse rsp, Object arg2, Exception e) {
		log.error("Catch Exception: ",e);//把漏网的异常信息记入日志  
        Map<String,Object> map = new HashMap<String,Object>();  
        StringPrintWriter strintPrintWriter = new StringPrintWriter();  
        e.printStackTrace(strintPrintWriter);  
        map.put("errorMsg", strintPrintWriter.getString());//将错误信息传递给view  
        return new ModelAndView("/commons/error",map);  
	}

	
}