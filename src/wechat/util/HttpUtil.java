package wechat.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

public class HttpUtil {
	public static JSONObject httpsRequest(String requestUrl,
			String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		TrustManager[] tm = { new TCIWX509TrustManager() };
		try {
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
					.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			httpUrlConn.setRequestMethod(requestMethod);
			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.parseObject(buffer.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	public static JSONObject httpRequest(String module,String requestUrl, String method,
			Object enty) {
		JSONObject jsonObject = null;
		HttpGet httpGet = null;
		HttpPost httpPost = null;
		CloseableHttpResponse response = null;
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			Map<String, String> parameters = BaseUtil.convertEntytoMap(enty);
			if (method.equals("GET")) {
				StringBuffer param = new StringBuffer();
				for (String key : parameters.keySet()) {
					param.append("&");
					param.append(key).append("=").append(parameters.get(key));
				}
				requestUrl = Config.getValue(module,"path") + requestUrl + param;
				httpGet = new HttpGet(requestUrl);
				response = httpClient.execute(httpGet);
			}
			if (method.equals("POST")) {
				List<NameValuePair> param = new ArrayList<NameValuePair>();
				for(String key : parameters.keySet()){
					param.add(new BasicNameValuePair(key,parameters.get(key)));
				}
				requestUrl = Config.getValue(module,"path") + requestUrl;
				httpPost = new HttpPost(requestUrl);
				httpPost.setEntity(new UrlEncodedFormEntity(param, "UTF-8"));
				response = httpClient.execute(httpPost);
			}
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				jsonObject = JSONObject.parseObject(EntityUtils.toString(entity));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	// 标准重写方法,由于我们传入的已经带有?flag= 所以不用
//	public static JSONObject httpRequest2(String requestUrl, String method,
//			Object enty) {
//		JSONObject jsonObject = null;
//		HttpGet httpGet = null;
//		HttpPost httpPost = null;
//		CloseableHttpResponse response = null;
//		try {
//			CloseableHttpClient httpClient = HttpClients.createDefault();
//			StringBuffer param = new StringBuffer();
//			Map<String, String> parameters = BaseUtil.convertEntytoMap(enty);
//			if (method.equals("GET")) {
//				int i = 0;
//				for (String key : parameters.keySet()) {
//					if (i == 0)
//						param.append("?");
//					else
//						param.append("&");
//					param.append(key).append("=").append(parameters.get(key));
//					i++;
//				}
//				httpGet = new HttpGet(requestUrl);
//				response = httpClient.execute(httpGet);
//			}
//			if (method.equals("POST")) {
//				for (String key : parameters.keySet()) {
//					param.append("&");
//					param.append(key).append("=").append(parameters.get(key));
//				}
//				requestUrl = Config.getValue("path") + requestUrl + param;
//				httpPost = new HttpPost(requestUrl);
//				response = httpClient.execute(httpPost);
//			}
//			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//				HttpEntity entity = response.getEntity();
//				jsonObject = JSONObject.parseObject(EntityUtils
//						.toString(entity));
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return jsonObject;
//	}

	public static String upload(String url, File file) throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		FileBody filebody = new FileBody(file);
		HttpEntity entity = MultipartEntityBuilder.create()
				.addPart("file", filebody).build();
		httpPost.setEntity(entity);
		CloseableHttpResponse reponse = httpClient.execute(httpPost);
		return EntityUtils.toString(reponse.getEntity());
	}

	/**
	 * url和jsonObj一起POST请求
	 * 
	 * @param url
	 * @param jsonObj
	 * @return
	 * @throws Exception
	 */
	public static String postJSONObj(String url, JSONObject jsonObj)
			throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		StringEntity se = new StringEntity(jsonObj.toString(),"UTF-8");
		se.setContentType("text/json; charset=UTF-8");
		httpPost.setEntity(se);
		CloseableHttpResponse reponse = httpClient.execute(httpPost);
		return EntityUtils.toString(reponse.getEntity());
	}

	public static boolean download(String url, String path) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
			File downfile = new File(path);
			FileOutputStream outputStream = new FileOutputStream(downfile);
			InputStream inputStream = response.getEntity().getContent();
			byte b[] = new byte[1024];
			int count = 0;
			while ((count = inputStream.read(b)) != -1) {
				outputStream.write(b, 0, count);
			}
			outputStream.flush();
			outputStream.close();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return false;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			httpClient = null;
		}
		return true;
	}
}

class TCIWX509TrustManager implements X509TrustManager {

	@Override
	public void checkClientTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkServerTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
		// TODO Auto-generated method stub

	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		// TODO Auto-generated method stub
		return null;
	}
}
