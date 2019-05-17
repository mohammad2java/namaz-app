import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;






public class App {
	private static String xmlRequest="<xml><access>lI+VuF6w9vQXsaHUXyjwGw==</access><imeinr>353339071949213</imeinr></xml>";
	private static String url = "http://www.n1s.dk/GSX_API/WebService.asmx/appleinfo";
	public static void main(String[] args) throws IOException  {
		 //  HTTP client
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		StringEntity entity = new StringEntity(xmlRequest);
		post.setEntity(entity);
		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(post);
			System.out.println("Response body: " + response);
			System.out.println("Response getStatusLine : " + response.getStatusLine());
			String resXML = EntityUtils.toString(response.getEntity());
			System.out.println("result:" + resXML);
			System.out.println("StringUtils.contains(resXML,<FMI> ): "+StringUtils.contains(resXML,"<FMI>" ));
			String FMI   = StringUtils.substringBetween(resXML, "<FMI>", "</FMI>");
			System.out.println(FMI.length());
		}
		catch (Exception exception) {

		} finally {
			if (response != null)
				response.close();
			if (post != null)
				post.releaseConnection();
			if (httpclient != null)
				httpclient.close();
		}

	}

}
