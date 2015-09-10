package wechat.req.handler;

import java.util.Map;

import org.apache.logging.log4j.Logger;

import wechat.message.req.BaseMessage;
import wechat.message.req.LocationMessage;

public class MessageHandlerLocation extends MessageHandlerHelper {

	@Override
	protected void parseSpecialMessage(BaseMessage baseMessage,
			Map<String, String> requestMap, Logger logger) {
		locationMessage = (LocationMessage) baseMessage;
		locationMessage.setLabel(requestMap.get("Label"));
		locationMessage.setLocation_X(requestMap.get("Location_X"));
		locationMessage.setLocation_Y(requestMap.get("Location_Y"));
		locationMessage.setScale(requestMap.get("Scale"));
		logger.info(locationMessage.getToUserName() + ":" + " 发送者:"
				+ locationMessage.getFromUserName() + ",位置消息," + "X:"
				+ locationMessage.getLocation_X() + ",Y:"
				+ locationMessage.getLocation_Y() + ",Label:"
				+ locationMessage.getLabel());
	}

}
