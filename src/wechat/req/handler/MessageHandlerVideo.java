package wechat.req.handler;

import java.util.Map;
import org.apache.logging.log4j.Logger;
import wechat.message.req.BaseMessage;
import wechat.message.req.VideoMessage;

public class MessageHandlerVideo extends MessageHandlerHelper{

	@Override
	protected void parseSpecialMessage(BaseMessage baseMessage,
			Map<String, String> requestMap,Logger logger) {
		videoMessage = (VideoMessage)baseMessage;
		videoMessage.setMediaId(requestMap.get("MediaId"));
		videoMessage.setThumbMediaId(requestMap.get("ThumbMediaId"));
		logger.info(videoMessage.getToUserName() + ":" + " 发送者:"
				+ videoMessage.getFromUserName() + ",视频消息," + "MediaId:"
				+ videoMessage.getMediaId());
	}

}
