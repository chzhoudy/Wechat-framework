package wechat.req.handler;

import java.util.Map;

import wechat.message.event.BaseEvent;
import wechat.message.event.MenuEvent;

public class EventHandlerMenu extends EventHandlerHelper{
	@Override
	protected void parseSpecialEvent(BaseEvent baseEvent,
			Map<String, String> requestMap) {
	    menuEvent = (MenuEvent) baseEvent;
		menuEvent.setEventKey(requestMap.get("EventKey"));
	}
}
