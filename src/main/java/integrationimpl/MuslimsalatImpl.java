package integrationimpl;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import integration.IntegrationService;

public class MuslimsalatImpl  implements IntegrationService {
	
	final static Logger logger = Logger.getLogger(MuslimsalatImpl.class);
	
	private static  String request = "http://muslimsalat.com/%s/yearly.json?key=%s";
	private static String key = "a79177a73f5073a66085d0c5fcb3f6e3";
	private static String city = "mumbai";
	
	public String getYearSalat(String countryCity, String... extra)  {
		logger.info("fetching namaz data for city "+countryCity);
		String ret = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		if (StringUtils.isNotBlank(countryCity))
			city = countryCity;
		String prepairedRequest = String.format(request, city,key);
		HttpGet httpGet = new HttpGet(prepairedRequest);
		CloseableHttpResponse httpResponse;
		try {
		httpResponse = httpClient.execute(httpGet);
		System.out.println("GET Response Status:: " + httpResponse.getStatusLine().getStatusCode());
		ret = EntityUtils.toString(httpResponse.getEntity());
		httpClient.close();
		
		} catch (Exception e) {
			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
			
				}
			}
		}
		return ret;

	}

}
