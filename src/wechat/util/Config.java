package wechat.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

public class Config {
	private static Properties props = null;
	private static Properties props2 = null;
	
	public static void init(String module){
		props = new Properties();
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource resouce = resolver.getResource("classpath:" + module + "/conf/Base.properties");
		try {
			props.load(resouce.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void initMethod(String module){
		props2 = new Properties();
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource resouce = resolver.getResource("classpath:" + module + "/conf/Method.properties");
		try {
			props2.load(resouce.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getValue(String module,String key) {
		if(props == null){			
			init(module);
		}
		if(!props.getProperty("name").equalsIgnoreCase(module)){
			init(module);
		}
		return props.getProperty(key);
	}
	
	public static Map<String,String> keyValue(String module){
	    initMethod(module);
		Map<String,String> map = new Hashtable<String, String>();
		Iterator<Entry<Object, Object>> it = props2.entrySet().iterator();  
		while(it.hasNext()){
			Entry<Object,Object> entry = it.next();
			String key = entry.getKey().toString();
			String value = entry.getValue().toString();
			String[] methods = value.split(" ");
			if(methods.length != 1){
				for (String method : methods) {
					map.put(method,key);	
				}
			}
			map.put(methods[0],key);
		}
		props2 = null;
		return map;
	}
	
	
	public static void updateValue(String key,String value){
		try {
			FileOutputStream output = new FileOutputStream("Base.properties");
			props.setProperty(key, value);
			props.store(output, null);
			output.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
