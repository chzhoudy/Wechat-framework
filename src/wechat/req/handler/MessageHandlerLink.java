package wechat.req.handler;

import java.util.Map;
import org.apache.logging.log4j.Logger;
import wechat.message.req.BaseMessage;
import wechat.message.req.LinkMessage;

public class MessageHandlerLink extends MessageHandlerHelper {

	@Override
	protected void parseSpecialMessage(BaseMessage baseMessage,
			Map<String, String> requestMap, Logger logger) {
		linkMessage = (LinkMessage) baseMessage;
		linkMessage.setDescription(requestMap.get("Description"));
		linkMessage.setTitle(requestMap.get("Title"));
		linkMessage.setUrl(requestMap.get("Url"));
		logger.info(linkMessage.getToUserName() + ":" + " 发送者:"
				+ linkMessage.getFromUserName() + ",链接消息" + ",标题:"
				+ linkMessage.getTitle() + ",描述:"
				+ linkMessage.getDescription() + ",url:" 
				+ linkMessage.getUrl());

	}

}
