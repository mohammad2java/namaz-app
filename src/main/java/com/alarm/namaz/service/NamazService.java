package com.alarm.namaz.service;

import java.util.Map;

import org.json.simple.parser.ParseException;

import com.alarm.namaz.model.Namaz;

public interface NamazService {
	
	Map<String ,Namaz> getSalatMapDateWise() throws ParseException;

	Map<String, Namaz> getSalatMapDateWise(String city) throws ParseException;

}
