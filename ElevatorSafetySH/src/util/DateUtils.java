package util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	private static SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public static String now(){
		return format.format(new Date());
	}
}
