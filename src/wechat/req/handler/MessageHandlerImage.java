package wechat.req.handler;

import java.util.Map;

import org.apache.logging.log4j.Logger;

import wechat.message.req.BaseMessage;
import wechat.message.req.ImageMessage;

public class MessageHandlerImage extends MessageHandlerHelper{

	@Override
	protected void parseSpecialMessage(BaseMessage baseMessage,
			Map<String, String> requestMap,Logger logger) {
		imageMessage = (ImageMessage)baseMessage;
		imageMessage.setMediaId(requestMap.get("MediaId"));
		imageMessage.setPicUrl(requestMap.get("PicUrl"));
		logger.info(imageMessage.getToUserName() + ":" + " 发送者:"
				+ imageMessage.getFromUserName() + ",图片消息," + "MediaId:"
				+ imageMessage.getMediaId());
	}
}
