package wechat.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.alibaba.fastjson.JSONObject;

public class JsapiUtil {
	private final static String TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	private final static SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static JSONObject getSignature(String module,String url){
		JSONObject rets = new JSONObject();
		String noncestr = create_nonce_str(),
		       timestamp = create_timestamp(),
		       jsapi_ticket = getTicket(module),
		       string1 = "jsapi_ticket=" + jsapi_ticket 
                       + "&noncestr=" + noncestr 
                       + "&timestamp=" + timestamp 
                       + "&url=" + url,
		       signature = DigestUtils.sha1Hex(string1);
		rets.put("url", url);
		rets.put("jsapi_ticket", jsapi_ticket);
		rets.put("nonceStr", noncestr);
		rets.put("timestamp", timestamp);
		rets.put("signature", signature);
		rets.put("appId", Config.getValue(module, "appID"));
		return rets;
	}
	
	private static void jsapi_ticket(String module) {
		String token = TokenUtil.getToken(module);
		JSONObject jsonObj = HttpUtil.httpsRequest(
				TICKET_URL.replace("ACCESS_TOKEN", token), "GET", null);
		if (jsonObj.getIntValue("errcode") == 0) {
			Config.updateValue("jsapi_ticket", jsonObj.getString("ticket"));
			Calendar now = Calendar.getInstance();
			now.add(Calendar.SECOND, jsonObj.getIntValue("expires_in"));
			Config.updateValue("ticket_expires", sdf.format(now.getTime()).toString());
		}
	}
	
	private static String getTicket(String module){
		Calendar now = Calendar.getInstance();
		Calendar expire = Calendar.getInstance();
		String expires = Config.getValue(module,"ticket_expires");
		System.out.println("ticket_expires" + expires);
		if(!expires.equalsIgnoreCase("")){
			Date date = null;
			try {
				date = sdf.parse(expires);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			expire.setTime(date);
		}
		if(now.after(expire) || expires.equals("")){
			jsapi_ticket(module);
		}
		return Config.getValue(module,"jsapi_ticket");
		
	}

	private static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}

	private static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}
}
