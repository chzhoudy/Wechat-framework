package wechat.req.handler;

import java.util.Map;

import wechat.message.event.BaseEvent;
import wechat.message.event.SendPicEvent;

public class EventHandlerSendPic extends EventHandlerHelper{

	@Override
	protected void parseSpecialEvent(BaseEvent baseEvent,
			Map<String, String> requestMap) {
		sendPicEvent = (SendPicEvent)baseEvent;
		sendPicEvent.setCount(Integer.parseInt(requestMap.get("Count")));
	}
}
