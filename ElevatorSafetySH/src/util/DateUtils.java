package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	private static SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public static String now(){
		return format.format(new Date());
	}
	public static String format(Date date){
		return format.format(date);
	}
	public static Date parse(String str){
		try {
			return format.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
