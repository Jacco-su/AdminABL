package com.dream.brick.admin.dao.impl;

import com.dream.framework.dao.BaseDaoImpl;
import com.dream.brick.admin.dao.DBBarkDao;
import com.dream.util.AppMsg;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Transactional
@Repository
public class DBBarkDaoImpl extends BaseDaoImpl implements DBBarkDao{
	/***
	 * 每天晚上23:30备份数据
	 * **/
	@Scheduled(cron="0 30 23 * * ? ")
	@Override
	public void jixiaoBark(){
        BufferedReader br = null;
        String batPath=AppMsg.getMessage("batpath");
        //D:\\bat\\jixiaobark.bat
        try {
            Process p = Runtime.getRuntime().exec("cmd /c "+batPath);
            //执行完命令后关闭命令窗口
            br = new BufferedReader(new InputStreamReader(p.getInputStream(),"GBK"));
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
	}
	/***
	 * 每天晚上23:50删除数据
	 * **/
	@Scheduled(cron="0 50 23 * * ? ")
	@Override
	public void clearTempScore(){
		String hql="delete from ScoreTemp t";
		executeUpdate(hql);
	}
}
