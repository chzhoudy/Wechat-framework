package wechat.req.handler;

import java.util.Map;

import wechat.message.event.BaseEvent;
import wechat.message.event.SendLocationEvent;

public class EventHandlerSendLocation extends EventHandlerHelper{

	@Override
	protected void parseSpecialEvent(BaseEvent baseEvent,
			Map<String, String> requestMap) {
		sendLocationEvent = (SendLocationEvent)baseEvent;
		sendLocationEvent.setLocation_X(requestMap.get("Location_X"));
		sendLocationEvent.setLocation_Y(requestMap.get("Location_Y"));
		sendLocationEvent.setEventKey(requestMap.get("EventKey"));
		sendLocationEvent.setScale(requestMap.get("Scale"));
		sendLocationEvent.setLabel(requestMap.get("Label"));
		sendLocationEvent.setPoiname(requestMap.get("Poiname"));
	}

}
