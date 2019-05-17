package com.alarm.namaz.model;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;

public class Namaz {
	
	private String date_for;
	private String fajr;
	private String shurooq;
	private String dhuhr;
	private String asr;
	private String maghrib;
	private String isha;
	private JSONObject jonsNamaz;
	
	public JSONObject getJonsNamaz() {
		return jonsNamaz;
	}
	public void setJonsNamaz(JSONObject jonsNamaz) {
		this.jonsNamaz = jonsNamaz;
	}
	public String getDate_for() {
		if (StringUtils.isNotBlank(date_for))
			return date_for;
		return (String) jonsNamaz.get("date_for");
	}
	public void setDate_for(String date_for) {
		this.date_for = date_for;
	}
	public String getFajr() {
		if (StringUtils.isNotBlank(fajr))
			return fajr;
		return (String) jonsNamaz.get("fajr");
	}
	public void setFajr(String fajr) {
		this.fajr = fajr;
	}
	public String getShurooq() {
		if (StringUtils.isNotBlank(shurooq))
			return shurooq;
		return (String) jonsNamaz.get("shurooq");
	}
	public void setShurooq(String shurooq) {
		this.shurooq = shurooq;
	}
	public String getDhuhr() {
		if (StringUtils.isNotBlank(dhuhr))
			return dhuhr;
		return (String) jonsNamaz.get("dhuhr");
	}
	public void setDhuhr(String dhuhr) {
		this.dhuhr = dhuhr;
	}
	public String getAsr() {
		if (StringUtils.isNotBlank(asr))
			return asr;
		return (String) jonsNamaz.get("asr");
	}
	public void setAsr(String asr) {
		this.asr = asr;
	}
	public String getMaghrib() {
		if (StringUtils.isNotBlank(maghrib))
			return maghrib;
		return (String) jonsNamaz.get("maghrib");
	}
	public void setMaghrib(String maghrib) {
		this.maghrib = maghrib;
	}
	public String getIsha() {
		if (StringUtils.isNotBlank(isha))
			return isha;
		return (String) jonsNamaz.get("isha");
	}
	public void setIsha(String isha) {
		this.isha = isha;
	}
	

}
