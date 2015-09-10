package wechat.enums;

import java.util.HashMap;

public class Methods {
	private static HashMap<String,String> methods = new HashMap<String,String>();
	static{
		methods.put("text", "textReq");
		methods.put("image", "imageReq");
		methods.put("voice", "voiceReq");
		methods.put("video", "videoReq");
		methods.put("location", "locationReq");
		methods.put("link", "linkReq");
		methods.put("event", "eventReq");
		methods.put("subscribe", "subscribeEvent");
		methods.put("unsubscribe", "unsubscribeEvent");
		methods.put("scan", "scanEvent");
		methods.put("LOCATION", "locationEvent");
		methods.put("CLICK", "click");
//		methods.put("location_select", "sendLocation");
//		methods.put("pic_photo_or_album", "sendPic");
		methods.put("scancode_waitmsg", "scanCode");
	}
	
	
	public static String methodis(String msgType){
		if(methods.containsKey(msgType)){
			return methods.get(msgType);
		}
		return null;
	}
}
