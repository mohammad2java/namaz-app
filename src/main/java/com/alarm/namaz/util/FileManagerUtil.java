package com.alarm.namaz.util;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import org.apache.commons.io.IOUtils;
import org.json.simple.parser.JSONParser;

public class FileManagerUtil {
	
	public static String readJosnFile(String path) {
		String  ret = null;
			JSONParser parser = new JSONParser();
			try {
				Object obj = parser.parse(new FileReader(path));
				ret = obj.toString();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	return ret;
		
	}

	
	public static void storeJson(String res ,String path) throws IOException {
		
		FileWriter file = new FileWriter(path);
		file.write(res);
		file.flush();
		file.close();
		
	}
	
	
	public static String readResourceJson(String fileName)  {
		
		String resoucePath = "/%s.json";
		InputStream is = FileManagerUtil.class.getResourceAsStream(String.format(resoucePath, fileName));
		String resJson = null;
		try {
			resJson = IOUtils.toString(is, "UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		return resJson;
	}

	public static String getStoreLoc(String name,String extension) {
		return String.format("%s"+File.separator+"%s_%d.%s", System.getProperty("user.home"),name,Calendar.getInstance().get(Calendar.YEAR),extension);
	}
	
	

}
