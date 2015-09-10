package wechat.req.handler;

import java.util.Map;
import org.apache.logging.log4j.Logger;
import wechat.message.req.BaseMessage;
import wechat.message.req.ImageMessage;
import wechat.message.req.LinkMessage;
import wechat.message.req.LocationMessage;
import wechat.message.req.TextMessage;
import wechat.message.req.VideoMessage;
import wechat.message.req.VoiceMessage;
import wechat.message.req.VoiceRecoMessage;

public abstract class MessageHandlerHelper {
	protected BaseMessage baseMessage;
	protected TextMessage textMessage;
	protected ImageMessage imageMessage;
	protected VideoMessage videoMessage;
	protected VoiceMessage voiceMessage;
	protected VoiceRecoMessage voiceRecoMessage;
	protected LocationMessage locationMessage;
	protected LinkMessage linkMessage;

	protected abstract void parseSpecialMessage(BaseMessage baseMessage,
			Map<String, String> requestMap,Logger logger);

	public void parseMessage(BaseMessage baseMessage,
			Map<String, String> requestMap,Logger logger) {
		baseMessage.setMsgType(requestMap.get("MsgType"));
		baseMessage.setFromUserName(requestMap.get("FromUserName"));
		baseMessage.setToUserName(requestMap.get("ToUserName"));
		baseMessage.setCreateTime(Long.parseLong(requestMap.get("CreateTime")));
		parseSpecialMessage(baseMessage, requestMap,logger);
	}

	public TextMessage getTextMessage() {
		return textMessage;
	}

	public ImageMessage getImageMessage() {
		return imageMessage;
	}

	public VoiceMessage getVoiceMessage() {
		return voiceMessage;
	}

	public LinkMessage getLinkMessage() {
		return linkMessage;
	}

	public VideoMessage getVideoMessage() {
		return videoMessage;
	}

	public VoiceRecoMessage getVoiceRecoMessage() {
		return voiceRecoMessage;
	}

	public LocationMessage getLocationMessage() {
		return locationMessage;
	}

}
