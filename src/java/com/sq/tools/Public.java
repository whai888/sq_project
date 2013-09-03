package com.sq.tools;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class Public {
	private static Logger log = Logger.getLogger(Public.class);
	public static Map mapInt = new TreeMap();
	static {
		mapInt.put(1 , "一");
		mapInt.put(2 , "二");
		mapInt.put(3 , "三");
		mapInt.put(4 , "四");
		mapInt.put(5 , "五");
		mapInt.put(6 , "六");
		mapInt.put(7 , "七");
		mapInt.put(8 , "八");
		mapInt.put(9 , "九");
		mapInt.put(10 , "十");
		mapInt.put(11 , "十一");
		mapInt.put(12 , "十二");
		mapInt.put(13 , "十三");
		mapInt.put(14 , "十四");
		mapInt.put(15 , "十五");
		mapInt.put(16 , "十六");
		mapInt.put(17 , "十七");
		mapInt.put(18 , "十八");
		mapInt.put(19 , "十九");
		mapInt.put(20 , "二十");
	}
	/**
	 * 得到应用程序的路径
	 * 
	 * @param request
	 * @return
	 */
	public static String getServerPath(HttpServletRequest request) {
		String path = request.getContextPath();
		return request.getScheme() + "://" + request.getServerName() + ":"
				+ request.getServerPort() + path + "/";
	}
	
	
	/**
	 * 取系统时间，根据给定的格式返回系统时间
	 * 
	 * @return
	 */
	public static String getSystemTimeToFormat(String format) {
		String retStr = "";
		try {
			SimpleDateFormat lFormat = new SimpleDateFormat(format);
			retStr = lFormat.format(new Date());
		} catch (Exception e) {
			return retStr ;
		}
		return retStr;
	}

	/**
	 * 根据给定的obj对象返回format格式化的字符串
	 * @param obj	可以为Date 或 String	
	 * @param format	需要格式化的日期
	 * @return
	 */
	public static String getTimeToFormat(Object obj , String format) {
		String retStr = "" ;
		if(obj == null)
			return retStr ;
		Date dateTemp = null;
		try {
			if(obj instanceof Date){
				dateTemp = (Date)obj ;
			}else if(obj instanceof String ){
				DateFormat format1 = new SimpleDateFormat(format);
				dateTemp = format1.parse((String)obj);
			}
			SimpleDateFormat lFormat = new SimpleDateFormat(format);
			retStr = lFormat.format(dateTemp);
		} catch (ParseException e) {
			log.debug("类型转换异常");
		}
		return retStr;
	}
	
	/**
	 * 将字符串转换为日期格式
	 * @param dateObj	需要转换的字符串
	 * @param format	需要格式化的字符串
	 * @return
	 */
	public static Date getStringToDate(String dateObj , String format){
		Date dateTemp = null ;
		try {
			if(dateObj==null || dateObj.equals("null"))
				dateObj = Public.getSystemTimeToFormat(format);
			DateFormat format1 = new SimpleDateFormat(format);
			dateTemp = format1.parse(dateObj);
		} catch (ParseException e) {
			log.debug("类型转换异常");
		}
		return dateTemp ;
	}
	
	/**
	 * 将日期进行加减
	 * @param date	需要加减的日期。如果传NULL  则标示为当天的
	 * @param format	需要格式化输入的日期
	 * @param day		加减的天数	如果传99，则标示为每月的第一天
	 * @return
	 */
	public static Date getDateToAddRed(Date date , String format , int day){
		if(date == null){
			date = new Date();
		}
		SimpleDateFormat df=new SimpleDateFormat(format); 
		String dateStr =  "" ;
		if(day == 99){
			date.setDate(1);
			dateStr = df.format(date);
		}else if(day == 0){
			dateStr = df.format(date);
		}else{
			dateStr = df.format(new Date(date.getTime() - (long)day * 24 * 60 *60 * 1000));
		}
		return getStringToDate(dateStr , format) ;
	}
	/**
	 * 获得访问者的IP
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * 判断当前日期是星期几
	 * @return
	 */
	public  static  int  dayForWeek(int dayPara){
	    Date tmpDate = new Date();
	    int week = 0 ;
	    Calendar cal=Calendar.getInstance(Locale.CHINA);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
	    cal.setTime(tmpDate);
	    if(getSystemTimeToFormat("MM-dd").equals("12-31")){
	    	cal.setTime(getDateToAddRed(null, "yyyy-MM-dd", 7));
	    	week = cal.get(dayPara) +1 ;
	    }else{
	    	week = cal.get(dayPara) ;
	    }
	    return  week;   
	}  

	public static String dayForWeekStr(int dayPara){
		String weekDay = dayForWeek(dayPara) + "";
		if(weekDay.length() == 1) weekDay = "0" + weekDay ;
		return weekDay;
	}
	public static Date weekForDay(String format , int week){
		Calendar cal=Calendar.getInstance(Locale.CHINA);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.set(Calendar.DAY_OF_WEEK, week);
		return cal.getTime() ;
	}

	/**
	 * 检查字符串是否为空
	 * @param str  str为null 或"" 则返回true
	 * @return
	 */
	public static boolean isEmpty (String str){
		if(str ==null || str.equals(""))
			return true ;
		else 
			return false ;
	}
	
	/**
	 * 生成随机数
	 * @param icount	生成随机数的位数
	 * @return
	 */
	public static String randomStr(){
		StringBuffer strBuff = new StringBuffer();
		Random r=new Random(); 
        strBuff.append(Math.abs(r.nextInt()));
        return strBuff.toString();
	}
	public static void main(String[] args) {
//		System.out.println(Public.getDateToAddRed(null, "yyyy-MM-dd" , 3*60));
//		SimpleDateFormat simple=new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
//		Calendar cal=Calendar.getInstance(Locale.CHINA);
//		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
//		System.out.println(simple.format(cal.getTime()));
//		Calendar cal=Calendar.getInstance(); cal.setTime(new Date());
//		System.out.println(cal.get(Calendar.WEEK_OF_YEAR));
		
		System.out.println(Public.getSystemTimeToFormat("yyyy年")+"第"+Public.dayForWeek(Calendar.WEEK_OF_YEAR) + "周(" +Public.getSystemTimeToFormat("MM月") +")");
	}
}
