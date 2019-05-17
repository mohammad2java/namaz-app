package com.alarm.namaz.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GeneralUtil {
	final static Logger logger = Logger.getLogger(GeneralUtil.class);
	
	public static String convertDate(Date date, String format) {

		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String DateToStr = formatter.format(date);
		return DateToStr;

	}

	
	public static List<String> getCities()	{
		List<String> cities = new ArrayList<String>();
		String citiesJson = FileManagerUtil.readResourceJson(NamazConstant.CITIES);
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = null;
		try {
			jsonObject = (JSONObject) parser.parse(citiesJson);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		JSONArray result = (JSONArray)jsonObject.get(NamazConstant.CITIES);
		for (Object res : result.toArray()) {
			cities.add(res.toString());
		}
		
		return cities;
		
	}
	
	
	public static Properties loadProp(String name) {
		Properties prop = new Properties();
		String propPath = FileManagerUtil.getStoreLoc(name, "properties");
		InputStream input = null;
		File jsonFile = new File(propPath);
		if (jsonFile.isFile()) {
			try {
				input = new FileInputStream(propPath);
				prop.load(input);
			} catch (Exception e) {
				// TODO Auto-generated catch block

			}
		}
		return prop;

	}
	
	
	public static void storeIntoProp(String key, String value) {
		OutputStream output = null;
		if(StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
		String propPath = FileManagerUtil.getStoreLoc("setting", "properties");
		File propFile = new File(propPath);
		Properties prop = new Properties();
		if (propFile.isFile()) {
			prop = loadProp("setting");
		}
		try {
			output = new FileOutputStream(propFile);
			
			prop.put(key, value);
			prop.store(output, null);
			}
		 catch (Exception e) {
			if(output != null) 
				try {
					output.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
				}
			// TODO Auto-generated catch block
		}
		}
	}
	
	public static String timeToCronExpression(String time) {
		String cron = "*%s%s****";
		String[] splitTime = time.split(" ");
		String[] splitHrMin = splitTime[0].split(":");
		int hr = Integer.parseInt(splitHrMin[0]);
		String mins = splitHrMin[1];
		if ("pm".equalsIgnoreCase(splitTime[1]))
			hr = hr + 12;
		String hrs = String.valueOf(hr);
		return String.format(cron, mins, hrs);
	}
	
}
