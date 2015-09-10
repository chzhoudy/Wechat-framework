package wechat.util;

import java.io.InputStream;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.codec.digest.DigestUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import wechat.message.mass.MassImage;
import wechat.message.mass.MassMessage;
import wechat.message.mass.MassNews;
import wechat.message.mass.MassText;
import wechat.message.mass.MassVideo;
import wechat.message.mass.MassVoice;
import wechat.message.resp.Article;
import wechat.message.resp.ImageMessage;
import wechat.message.resp.MusicMessage;
import wechat.message.resp.NewsMessage;
import wechat.message.resp.TextMessage;
import wechat.message.resp.VideoMessage;
import wechat.message.resp.VoiceMessage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;


public class MessageUtil {
	/**
	 * 解析微信发来的请求（XML）
	 * 
	 * @param request
	 * @return Map<String, String>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
		// 将解析结果存储在HashMap中
		Map<String, String> map = new ConcurrentHashMap<String, String>();
		// 从request中取得输入流
		InputStream inputStream = request.getInputStream();
		// 读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		List<Element> elementList = root.elements();
		// 遍历所有子节点
		for (Element e : elementList){
			if(e.elements().size() != 0){
				List<Element> elementList2 = e.elements();
				for (Element e2 : elementList2){
					map.put(e2.getName(), e2.getText());
				}
			}
			map.put(e.getName(), e.getText());
		}
		// 释放资源
		inputStream.close();
		inputStream = null;
		return map;
	}
	

	/**
	 * 扩展xstream使其支持CDATA
	 */
	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				// 对所有xml节点的转换都增加CDATA标记
				boolean cdata = true;
				@SuppressWarnings("unchecked")
				public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
				}
				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});

	/**
	 * 文本消息对象转换成xml
	 * 
	 * @param textMessage 文本消息对象
	 * @return xml
	 */
	public static String messageToXml(TextMessage textMessage) {
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}

	/**
	 * 图片消息对象转换成xml
	 * 
	 * @param imageMessage 图片消息对象
	 * @return xml
	 */
	public static String messageToXml(ImageMessage imageMessage) {
		xstream.alias("xml", imageMessage.getClass());
		return xstream.toXML(imageMessage);
	}

	/**
	 * 语音消息对象转换成xml
	 * 
	 * @param voiceMessage 语音消息对象
	 * @return xml
	 */
	public static String messageToXml(VoiceMessage voiceMessage) {
		xstream.alias("xml", voiceMessage.getClass());
		return xstream.toXML(voiceMessage);
	}

	/**
	 * 视频消息对象转换成xml
	 * 
	 * @param videoMessage 视频消息对象
	 * @return xml
	 */
	public static String messageToXml(VideoMessage videoMessage) {
		xstream.alias("xml", videoMessage.getClass());
		return xstream.toXML(videoMessage);
	}

	/**
	 * 音乐消息对象转换成xml
	 * 
	 * @param musicMessage 音乐消息对象
	 * @return xml
	 */
	public static String messageToXml(MusicMessage musicMessage) {
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}

	/**
	 * 图文消息对象转换成xml
	 * 
	 * @param newsMessage 图文消息对象
	 * @return xml
	 */
	public static String messageToXml(NewsMessage newsMessage) {
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new Article().getClass());
		return xstream.toXML(newsMessage);
	}
	
	public static String messageToXml(MassImage massImage) {
		xstream.alias("xml", massImage.getClass());
		return xstream.toXML(massImage);
	}
	
	
	public static String messageToXml(MassMessage massMessage) {
		xstream.alias("xml", massMessage.getClass());
		return xstream.toXML(massMessage);
	}
	
	
	public static String messageToXml(MassNews massNews) {
		xstream.alias("xml", massNews.getClass());
		return xstream.toXML(massNews);
	}
	
	public static String messageToXml(MassText massText) {
		xstream.alias("xml", massText.getClass());
		return xstream.toXML(massText);
	}
	
	public static String messageToXml(MassVideo massVideo) {
		xstream.alias("xml", massVideo.getClass());
		return xstream.toXML(massVideo);
	}
	
	public static String messageToXml(MassVoice massVoice) {
		xstream.alias("xml", massVoice.getClass());
		return xstream.toXML(massVoice);
	}
	
	public static boolean check(String module,HttpServletRequest request){
		String signature = request.getParameter("signature") ;
		String timestamp = request.getParameter("timestamp") ;
		String nonce = request.getParameter("nonce") ;
		String[] str = { Config.getValue(module, "name") , timestamp , nonce };
		Arrays.sort(str);
		String encodeStr = str[0] + str[1] + str[2];
		String sha1After = DigestUtils.sha1Hex(encodeStr);
		if(sha1After.equals(signature)){
			return true;
		}
		return false;
	}
}
