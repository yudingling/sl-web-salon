package com.sl.web.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.util.StringUtils;

public class DateUtil {
	private static final String YMDHS = "yyyy-MM-dd HH:mm:ss";
	
	private DateUtil(){}
	
	/**
	 * date to yyyy-MM-dd HH:mm:ss
	 */
	public static String toYMDHS(Date tm) {
		return tm == null ? "" : (new SimpleDateFormat(YMDHS)).format(tm);
	}
	
	/**
	 * date to string
	 * @param tm
	 * @param formatStr yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String toString(Long tm, String formatStr){
		return tm == null ? "" : (new SimpleDateFormat(formatStr)).format(new Date(tm));
	}
	
	/**
	 * yyyy-MM-dd HH:mm:ss to date
	 */
	public static Date toDate(String str) {
		try{
			if(StringUtils.hasText(str)){
				return (new SimpleDateFormat(YMDHS)).parse(str);
			}
			
		}catch(Exception ex){
			throw new IllegalArgumentException(ex);
		}
		
		throw new IllegalArgumentException("value is empty");
	}
	
	/**
	 * yyyy-MM-dd HH:mm:ss to date
	 */
	public static Date toDate(String str, String formatStr) {
		try{
			if(StringUtils.hasText(str)){
				return (new SimpleDateFormat(formatStr)).parse(str);
			}
			
		}catch(Exception ex){
			throw new IllegalArgumentException(ex);
		}
		
		throw new IllegalArgumentException("value is empty");
	}
	
	public static Timestamp currentTs(){
		return new Timestamp((new Date()).getTime());
	}
	
	public static Long currentDate(){
		return new Date().getTime();
	}
	
	public static long addYear(long ts, int years) {
		return addTs(ts, Calendar.YEAR, years);
	}
	
	public static long addMonth(long ts, int months) {
		return addTs(ts, Calendar.MONTH, months);
	}
	
	public static long addDay(long ts, int days) {
		return addTs(ts, Calendar.DAY_OF_MONTH, days);
	}
	
	public static long addHour(long ts, int hours) {
		return addTs(ts, Calendar.HOUR, hours);
	}
	
	public static long addSecond(long ts, int seconds) {
		return addTs(ts, Calendar.SECOND, seconds);
	}
	
	public static long addTs(long ts, int field, int count) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(ts);
		cal.add(field, count);
		return cal.getTimeInMillis();
	}
}
