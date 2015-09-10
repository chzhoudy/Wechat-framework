package wechat.req.handler;

import java.util.Map;
import org.apache.logging.log4j.Logger;
import wechat.message.req.BaseMessage;
import wechat.message.req.VoiceRecoMessage;

public class MessageHandlerVoiceReco extends MessageHandlerHelper{

	@Override
	protected void parseSpecialMessage(BaseMessage baseMessage,
			Map<String, String> requestMap,Logger logger) {
		voiceRecoMessage = (VoiceRecoMessage)baseMessage;
		voiceRecoMessage.setFormat(requestMap.get("Format"));
		voiceRecoMessage.setMediaId(requestMap.get("MediaId"));
		voiceRecoMessage.setRecognition(requestMap.get("Recognition") == null ? "" : requestMap.get("Recognition"));
		logger.info(voiceRecoMessage.getToUserName() + ":" + " 发送者:"
				+ voiceRecoMessage.getFromUserName() + ",音频消息," + "MediaId:"
				+ voiceRecoMessage.getMediaId() + ",Recognition:"
				+ voiceRecoMessage.getRecognition());
	}

}
