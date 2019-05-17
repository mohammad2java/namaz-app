package com.alarm.namaz.serviceimpl;

import integration.IntegrationService;
import integrationimpl.MuslimsalatImpl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.alarm.namaz.model.Namaz;
import com.alarm.namaz.service.NamazService;
import com.alarm.namaz.util.FileManagerUtil;

public class NamazServiceImpl implements NamazService{
	private IntegrationService intService = new  MuslimsalatImpl();

	public Map<String, Namaz> getSalatMapDateWise(String city) throws ParseException {
		
		Map<String, Namaz>  retMap =  new HashMap<String, Namaz>();
		String jsonRes = null;
		try {
			String storeLoc = FileManagerUtil.getStoreLoc(city,"json");
			File jsonFile = new File(storeLoc);
			if(jsonFile.isFile()) {
				jsonRes = FileManagerUtil.readJosnFile(storeLoc);
			}
			else  {
			jsonRes = intService.getYearSalat(city);
			FileManagerUtil.storeJson(jsonRes, storeLoc);
			}
		} catch (IOException e) {
			System.out.println(e);
			return retMap;
		}
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = (JSONObject) parser.parse(jsonRes); 
		JSONArray result = (JSONArray)jsonObject.get("items");
		for (Object res : result.toArray()) {
			JSONObject jsonElement = (JSONObject) parser.parse(res.toString()); 
			String key = (String) jsonElement.get("date_for");
			Namaz namaz = new Namaz();
			namaz.setJonsNamaz(jsonElement);
			retMap.put(key, namaz);
		}

		return retMap;
	}

	public Map<String, Namaz> getSalatMapDateWise() throws ParseException {
		
		return getSalatMapDateWise("mumbai");
	}

}
