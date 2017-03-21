package com.utstar.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeConvert {
	public static final String FORMAT="yyyyMMddHHmmssSSS";
	public static String timeConvert(Date date,String format){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		String convertDate = simpleDateFormat.format(date);
		return convertDate;
	}
}
