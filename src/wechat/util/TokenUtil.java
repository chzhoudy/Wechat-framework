package wechat.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;

public class TokenUtil {
	private final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    private final static SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static void getAccessToken(String module) {
		String appid = Config.getValue(module,"appID");
		String appsecret = Config.getValue(module,"appsecret");
		String requestUrl = access_token_url.replace("APPID", appid).replace(
				"APPSECRET", appsecret);
		JSONObject jsonObject = HttpUtil.httpsRequest(requestUrl, "GET", null);
		if (null != jsonObject) {
			Config.updateValue("token",jsonObject.getString("access_token"));
			Calendar now = Calendar.getInstance();
			now.add(Calendar.SECOND, jsonObject.getIntValue("expires_in"));
			String expires = sdf.format(now.getTime()).toString();
			Config.updateValue("expires", expires);
		}
	}

	public static String getToken(String module){
		Calendar now = Calendar.getInstance();
		Calendar expire = Calendar.getInstance();
		String expires = Config.getValue(module,"expires");
		if(!expires.equalsIgnoreCase("")){
			Date date = null;
			try {
				date = sdf.parse(expires);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			expire.setTime(date);
		}
		if (now.after(expire) || expires.equals("")) {
			getAccessToken(module);
		}
		return Config.getValue(module,"token");
	}

}
