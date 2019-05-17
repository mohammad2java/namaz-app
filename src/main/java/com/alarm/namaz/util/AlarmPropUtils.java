package com.alarm.namaz.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class AlarmPropUtils {
	
	
	
	private static String getPath(String propName)  {
		String ret  = null ;
		String path =  "%s/%s.properties";
		String startPart = System.getProperty("user.home"); 
		ret = String.format(path, startPart,propName);
		File file = new File(ret);
		if (!file.isFile()){
			try {
			file.createNewFile();
			}
			catch (Exception e ) {
				;
			}
		}
		return ret;	
	}
	
public static String getPropValue(String key)  {
	String ret =  null ;
	Properties prop =  new Properties();
	try {
		prop.load(new FileInputStream(getPath("inout")));
		} catch (Exception e) {
			;
		}
	ret  =  prop.getProperty(key);
	return ret;
	
}
	

public static void putPropValue(String key ,String val)  {
	Properties prop =  new Properties();
	try {
	prop.load(new FileInputStream(getPath("inout")));
	prop.put(key, val);
	prop.store(new FileOutputStream(getPath("inout")), "na");
	} catch (Exception e) {
		;
	}	

}
	



}
