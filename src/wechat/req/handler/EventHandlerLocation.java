package wechat.req.handler;

import java.util.Map;

import wechat.message.event.BaseEvent;
import wechat.message.event.LocationEvent;

public class EventHandlerLocation extends EventHandlerHelper{

	@Override
	protected void parseSpecialEvent(BaseEvent baseEvent,
			Map<String, String> requestMap) {
		locationEvent = (LocationEvent)baseEvent;
		locationEvent.setLatitude(requestMap.get("Latitude"));
		locationEvent.setLongitude(requestMap.get("Longitude"));
		locationEvent.setPrecision(requestMap.get("Precision"));
	}
}
