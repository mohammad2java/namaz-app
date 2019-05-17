package com.alarm.wtc;

import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.alarm.namaz.util.AlarmPropUtils;

public class WTCUtils {
	
	
	
	
	public static Date getInTime() {
		Date ret = null;
		String res =  null ;
		Date now  =  new Date();
		SimpleDateFormat sdf =  new SimpleDateFormat("ddMMyyyy");
		String todate = sdf.format(now);
		SimpleDateFormat  outSdf =  new SimpleDateFormat("M/d/yyyy h:m:s a");
		 String retInString = AlarmPropUtils.getPropValue(todate);
		 if (StringUtils.isNotBlank(retInString)) {
			 try {
					ret =  outSdf.parse(retInString);
				} catch (Exception e) {
					;
				} 
			 return ret;
		 }
		
		String strimBootTime =  outSdf.format(new Date());
		AlarmPropUtils.putPropValue(todate, strimBootTime);
		
		try {
			ret =  outSdf.parse(strimBootTime);
		} catch (Exception e) {
			;
		} 
		return ret;	
	}
	
	
	public static Date getOutTime() {
		Date ret =  null ;
		Date now  =  new Date();
		SimpleDateFormat sdf =  new SimpleDateFormat("ddMMyyyy");
		String todate = sdf.format(now);
		String inTime = AlarmPropUtils.getPropValue(todate);
		if (StringUtils.isBlank(inTime)) {
			getInTime();
		}
		 Calendar calendar =  Calendar.getInstance();
		 calendar.setTime(WTCUtils.getInTime());
		 calendar.add(Calendar.MINUTE, 510);
		  ret  =  calendar.getTime();
		
		return ret;
		
		
	}
	
	public static String workedTime() {
		String workedTime = null;
		Date inTime = getInTime();
		Date currentTime = new Date();
		long duration  = currentTime.getTime() - inTime.getTime();
		long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
		long diffHrs = diffInMinutes/60;
		long diffInMinOnly = diffInMinutes%60;
		workedTime = String.format("WorkedTime: %s hrs-%s mins", diffHrs,diffInMinOnly);
		return workedTime;
	}

	public static String getLeftTime() {
		String ret = null;
		Date inTime  = getInTime();
		Date outTime  = getOutTime();
		Long diffInMinuts = (outTime.getTime()-inTime.getTime())/60000;
		ret = diffInMinuts.toString();
		return ret;	
	}
}
