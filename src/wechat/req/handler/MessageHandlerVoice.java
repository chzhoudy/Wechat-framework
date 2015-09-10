package wechat.req.handler;

import java.util.Map;
import org.apache.logging.log4j.Logger;
import wechat.message.req.BaseMessage;
import wechat.message.req.VoiceMessage;

public class MessageHandlerVoice extends MessageHandlerHelper {

	@Override
	protected void parseSpecialMessage(BaseMessage baseMessage,
			Map<String, String> requestMap,Logger logger) {
		voiceMessage = (VoiceMessage)baseMessage;
		voiceMessage.setFormat(requestMap.get("Format"));
		voiceMessage.setMediaId(requestMap.get("MediaId"));
		logger.info(voiceMessage.getToUserName() + ":" + " 发送者:"
				+ voiceMessage.getFromUserName() + ",音频消息," + "MediaId:"
				+ voiceMessage.getMediaId());
	}
      
}
