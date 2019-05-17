package com.alarm.namaz.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;

import com.alarm.namaz.model.Namaz;
import com.alarm.namaz.service.NamazService;
import com.alarm.namaz.serviceimpl.NamazServiceImpl;

public class NamazUtil {
	final static Logger logger = Logger.getLogger(NamazUtil.class);
	private static NamazService namazService =  new NamazServiceImpl();
	private static  Map<String ,Object> resouces = new HashMap<String, Object>();
	
	
	public static void setResource(String key, Object value) {
		resouces.put(key, value);
	}
	
	public static Object getResource(String key) {
		return resouces.get(key);
	}
	
	
	
	public static Namaz findTodayNamaz(){
		Namaz namaz = null;
		String date = GeneralUtil.convertDate(new Date(), "yyyy-M-d");
		try {
			Map<String, Namaz> namazMap = namazService.getSalatMapDateWise();
			namaz = namazMap.get(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return namaz;
		
	}
	
	
	public static Namaz findTodayNamaz(String city){
		Namaz namaz = null;
		String date = GeneralUtil.convertDate(new Date(), "yyyy-M-d");
		try {
			Map<String, Namaz> namazMap = namazService.getSalatMapDateWise(city);
			namaz = namazMap.get(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return namaz;
		
	}
	
	
	public static void addminutsToNamaz(Namaz namaz) {
		
		String admins = GeneralUtil.loadProp("setting").getProperty("at");
		int mins = 10;
		if (admins != null) {
			try {
		 mins = Integer.parseInt(admins);	}
			catch(Exception e) {;}
		} else {
			GeneralUtil.storeIntoProp("at", String.valueOf(mins));
		}
		addminutsToNamaz(namaz,mins);
	}
	
	
	public static void addminutsToNamaz(Namaz namaz, int mins) {
		SimpleDateFormat df = new SimpleDateFormat("h:mm a");
		Calendar cal = Calendar.getInstance();
		Date updated = null;
		// for fazr
		logger.info("added minuts for fajr "+mins);
		try {
			updated = df.parse(namaz.getFajr());
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
		}
		cal.setTime(updated);
		cal.add(Calendar.MINUTE, mins);
		namaz.setFajr(df.format(cal.getTime()));
		
		// for Shurooq
				logger.info("added minuts for Shurooq "+mins);
				try {
					updated = df.parse(namaz.getShurooq());
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
				}
				cal.setTime(updated);
				cal.add(Calendar.MINUTE, mins);
				namaz.setShurooq(df.format(cal.getTime()));


		// for zohr
		logger.info("added minuts for zohr "+mins);
		try {
			updated = df.parse(namaz.getDhuhr());
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
		}
		cal.setTime(updated);
		cal.add(Calendar.MINUTE, mins);
		namaz.setDhuhr(df.format(cal.getTime()));
		
		// for Asr
				logger.info("added minuts for Asr "+mins);
				try {
					updated = df.parse(namaz.getAsr());
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
				}
				cal.setTime(updated);
				cal.add(Calendar.MINUTE, mins);
				namaz.setAsr(df.format(cal.getTime()));

		// for magrib
		logger.info("added minuts for magrib "+mins);
		try {
			updated = df.parse(namaz.getMaghrib());
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
		}
		cal.setTime(updated);
		cal.add(Calendar.MINUTE, mins);
		namaz.setMaghrib(df.format(cal.getTime()));
		
		
		// for Isha
				logger.info("added minuts for Isha "+mins);
				try {
					updated = df.parse(namaz.getIsha());
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
				}
				cal.setTime(updated);
				cal.add(Calendar.MINUTE, mins);
				namaz.setIsha(df.format(cal.getTime()));

	}

}
