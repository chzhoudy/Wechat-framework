package wechat.req.handler;

import java.util.Map;

import wechat.message.event.BaseEvent;
import wechat.message.event.QRCodeEvent;

public class EventHandlerQRCode extends EventHandlerHelper{

	@Override
	protected void parseSpecialEvent(BaseEvent baseEvent,
			Map<String, String> requestMap) {
		qrCodeEvent = (QRCodeEvent)baseEvent;
		qrCodeEvent.setEventKey(requestMap.get("EventKey"));
		qrCodeEvent.setTicket(requestMap.get("Ticket"));
	}

}
