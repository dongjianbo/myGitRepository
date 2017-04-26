package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	private static SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private static SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd");
	public static String now(){
		return format.format(new Date());
	}
	public static String format(Date date){
		return format.format(date);
	}
	public static String format1(Date date){
		return format1.format(date);
	}
	public static Date parse(String str){
		try {
			return format1.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 计算俩个日期之间相隔的天数
	 * 
	 */
	public static int daysBetween(Date smdate,Date bdate)throws ParseException{
		Calendar c=Calendar.getInstance();
		c.setTime(smdate);
		long time1=c.getTimeInMillis();
		c.setTime(bdate);
		long time2=c.getTimeInMillis();
		long between_days=(time2-time1)/(1000*3600*24);
		return Math.abs(Integer.parseInt(String.valueOf(between_days)));
	}
}
