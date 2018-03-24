package com.dream.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期时间工具类
 * @author ml
 * @date 2015-05-20
 * **/
public class FormatDate {
	
	public static String[] months=new String[]{"1","2","3","4","5","6","7","8","9","10","11","12"};
	public static String[] years=new String[]{"2015","2016","2017","2018"};
	public static String[] weekDays = { "一", "二", "三", "四", "五", "六", "日" };
	//所有月份
	public static SimpleDateFormat sdfHHmm=new SimpleDateFormat("yyyy-MM-dd HH:mm");
	//生成到分的日期
	public static SimpleDateFormat sdfyMd=new SimpleDateFormat("yyyy-MM-dd");
	//生成到日的日期
	public static SimpleDateFormat sdfHHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//生成到月日
	public static SimpleDateFormat sdfyd=new SimpleDateFormat("MMdd");
	//生成到分的日期
	public static SimpleDateFormat sdfyM=new SimpleDateFormat("yyyy-MM");

	public static SimpleDateFormat YYYYMMDDHHMMSS_DEFAULT_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");


	private static final int year;
	static {
		Calendar a= Calendar.getInstance();
		year=a.get(Calendar.YEAR);
	}
	/**
	 * 获取年月 如2015-05-01
	 * **/
	public static String getYM() {
		// 生成具体日期
		String strDate = "";
		Date date = new Date(System.currentTimeMillis());
		if (date != null) {
			strDate = sdfyM.format(date);
		}
		return strDate;
	}
	/**
	 * 获取年月日 如2015-05-01
	 * **/
	public static String getMd() {
		// 生成具体日期
		String strDate = "";
		Date date = new Date(System.currentTimeMillis());
		if (date != null) {
			strDate = sdfyd.format(date);
		}
		return strDate;
	}
	/**
	 * 获取年月日 如2015-05-01
	 * **/
	public static String getYMd() {
		// 生成具体日期
		String strDate = "";
		Date date = new Date(System.currentTimeMillis());
		if (date != null) {
			strDate = sdfyMd.format(date);
		}
		return strDate;
	}
	/**
	 * 获取时分 如 2015-05-01 11:20
	 * **/
	public static String getYMdHHmm() {
		String strDate = "";
		Date date = new Date(System.currentTimeMillis());
		if (date != null) {
			strDate = sdfHHmm.format(date);
		}
		return strDate;
	}
	/**
	 * 获取时分秒 如 2015-05-01 11:20:32
	 * **/
	public static String getYMdHHmmss() {
		String strDate = "";
		Date date = new Date(System.currentTimeMillis());
		if (date != null) {
			strDate = sdfHHmmss.format(date);
		}
		return strDate;
	}
	/**
	 * 获取当前年度，如2015
	 * **/
	public static String getCurrentYear(){
		String date=getYMd();
		if(!"".equals(date)){
			return date.substring(0,4);
		}
		return "1900";
	}
	
	/**
	 *  计算两个日期相隔的天数
	 *  **/
	public static int getDaysBetweenTwoDate(String firstString,
			String secondString) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date firstDate = null;
		Date secondDate = null;
		try {
			firstDate = df.parse(firstString);
			secondDate = df.parse(secondString);
		} catch (Exception e) {
			// 日期型字符串格式错误
		}
		int nDay = (int) ((secondDate.getTime() - firstDate.getTime()) / (24 * 60 * 60 * 1000));
		return nDay;
	}
	
	public static int getWeek(String inputDate) {
		// 根据所给日期得到对应的星期几
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		int dayForWeek = 0;
		try {
			cal.setTime(format.parse(inputDate));
		} catch (Exception e) {
			return dayForWeek;
		}
		int weeki = cal.get(Calendar.DAY_OF_WEEK);
		if (weeki == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = weeki - 1;
		}
		return dayForWeek;
	}
	
	public static int getHoursBetweenTwoTime(String firstString,
			String secondString) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date firstDate = null;
		Date secondDate = null;
		try {
			firstDate = df.parse(firstString);
			secondDate = df.parse(secondString);
		} catch (Exception e) {
			// 日期型字符串格式错误
		}
		int nHours = (int) ((secondDate.getTime() - firstDate.getTime()) / (60 * 60 * 1000));
		return nHours;
	}
	public static String formatTime(String firstString) {
		if(firstString.length()<6){
			firstString=year+"-"+firstString;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date firstDate = null;
		String date="";
		try {
			firstDate = df.parse(firstString);
			date=df.format(firstDate);
		} catch (Exception e) {
			// 日期型字符串格式错误
			date=firstString;
		}
		return date;
	}
	public static Date formatDate(String firstString) {
		SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
		Date firstDate = null;
		try {
			firstDate = df.parse(firstString.replaceAll("/","-"));
		} catch (Exception e) {
			// 日期型字符串格式错误
			e.printStackTrace();
		}
		return firstDate;
	}
	//实现日期加一天的方法
	public static String addMonth(int n) {
		Calendar cd = Calendar.getInstance();
		cd.add(Calendar.MONTH, n);//增加一个月
		return sdfyM.format(cd.getTime());
	}
	/**
	 * 获取时分秒 如 2015-05-01 11:20:32
	 * **/
	public static Date getYMdHHmmss(String date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			return df.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
/**
       * 字符串解析成时间对象
       * @param dateTimeString String
       * @return 2018-03-04 10:30:30
       * @throws ParseException
       */
      public static String dateParse(String dateTimeString){
	      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	      try {
		      return YYYYMMDDHHMMSS_DEFAULT_FORMAT.format(sdf.parse(dateTimeString));
	      } catch (ParseException e) {
		      e.printStackTrace();
		      return null;
	      }
      }
      //20180304103030
	public static String dateSdfHHmmssParse(String dateTimeString){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			return sdfHHmmss.format(sdf.parse(dateTimeString));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static void main(String[] args){
		System.out.println("0012".substring(2,4));
		System.out.println(FormatDate.dateParse("2018-03-04 10:30:30"));
		System.out.println(FormatDate.dateSdfHHmmssParse("20180304103030"));

	}
}
