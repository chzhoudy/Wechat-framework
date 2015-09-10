package wechat.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class BaseUtil {
	private static final String UPLOAD_MEDIA_URL = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=";
	private static final String DOWNLOAD_FILE_URL = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=";
	private static final String CUSTOM_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";
	
	/**
	 * 客服消息发送
	 * @param jsonObj
	 * @return
	 */
	public static String customSend(String module,JSONObject jsonObj){
	    String url = CUSTOM_SEND_URL + TokenUtil.getToken(module);
		String resmsg = null;
		try {
			resmsg = HttpUtil.postJSONObj(url, jsonObj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resmsg;
	}
	/**
	 * 上传多媒体文件
	 * @param accessToken
	 * @param type 媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb）
	 * @param file form-data中媒体文件标识，有filename、filelength、content-type等信息
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> uploadMedia(String module,String type,File file) throws Exception{
        String url = UPLOAD_MEDIA_URL + TokenUtil.getToken(module) +"&type="+type;
        String jsonStr = HttpUtil.upload(url,file);
        return JSON.parseObject(jsonStr, Map.class);
    }
	/**
	 *下载多媒体文件
	 * @param accessToken
	 * @param media_id  
	 * @param type 文件类型
	 * @param filePath 文件保存路径
	 * @return
	 * @throws Exception
	 */
	public static boolean downloadFile(String module,String media_id,String type,String filePath) throws Exception{
		String url = DOWNLOAD_FILE_URL + TokenUtil.getToken(module) + "&media_id=" + media_id;
		HttpUtil.download(url, filePath + "/" +  media_id + "." + type);
		return true;
	}
	
	
	/**
	 * 实体转化为Map
	 * @param enty
	 * @return
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
    public static Map convertEntytoMap(Object enty) throws Exception{
    	Class clazz = enty.getClass();
    	Map map = new HashMap<String,String>();
    	BeanInfo bean = Introspector.getBeanInfo(clazz);
    	PropertyDescriptor[] properDescriptors = bean.getPropertyDescriptors();
    	for(int i=0;i<properDescriptors.length;i++){
    		PropertyDescriptor descriptor = properDescriptors[i];
    		String propertyName = descriptor.getName();
    		 if (!propertyName.equals("class")) {  
                 Method readMethod = descriptor.getReadMethod();  
                 Object result = readMethod.invoke(enty, new Object[0]);  
                 if (result != null) {  
                     map.put(propertyName, result);  
                 } else {  
                     map.put(propertyName, "");  
                 }  
             }  
    	}
		return map;
    	
    }
	
}
