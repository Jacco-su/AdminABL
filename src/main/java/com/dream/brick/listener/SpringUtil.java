package com.dream.brick.listener;

import com.dream.util.AppMsg;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringUtil implements ApplicationContextAware {
    /**
     * 当前IOC
     */
     private static ApplicationContext context;
 
     /**
      * 设置当前上下文环境，此方法由spring自动装配
      */
     @Override
    public void setApplicationContext(ApplicationContext appContext) throws BeansException {
    	 context = appContext;
    	 AppMsg.loadMessage();
     }
     public static Object getObject(String id) {
         Object object = null;
         object = context.getBean(id);
         return object;
     }
}