package wechat.req.handler;

import java.util.Map;
import wechat.message.event.BaseEvent;
import wechat.message.event.ScanCodeEvent;

public class EventHandlerScanCode extends EventHandlerHelper{

	@Override
	protected void parseSpecialEvent(BaseEvent baseEvent,
			Map<String, String> requestMap) {
		scanCodeEvent = (ScanCodeEvent)baseEvent;
		scanCodeEvent.setEventKey(requestMap.get("EventKey"));
		scanCodeEvent.setScanResult(requestMap.get("ScanResult"));
		scanCodeEvent.setScanType(requestMap.get("ScanType"));
	}

}
