package wechat.req.handler;

import java.util.Map;
import org.apache.logging.log4j.Logger;
import wechat.message.req.BaseMessage;
import wechat.message.req.TextMessage;

public class MessageHandlerText extends MessageHandlerHelper {
	@Override
	protected void parseSpecialMessage(BaseMessage baseMessage,
			Map<String, String> requestMap, Logger logger) {
		textMessage = (TextMessage) baseMessage;
		textMessage.setContent(requestMap.get("Content"));
		logger.info(textMessage.getToUserName() + ":" + " 发送者:"
				+ textMessage.getFromUserName() + ",文字消息," + "内容:"
				+ textMessage.getContent());
	}
}
