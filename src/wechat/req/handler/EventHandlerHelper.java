package wechat.req.handler;

import java.util.Map;
import wechat.message.event.BaseEvent;
import wechat.message.event.LocationEvent;
import wechat.message.event.MenuEvent;
import wechat.message.event.QRCodeEvent;
import wechat.message.event.ScanCodeEvent;
import wechat.message.event.SendLocationEvent;
import wechat.message.event.SendPicEvent;
import wechat.message.event.SubscribeEvent;

public abstract class EventHandlerHelper {
	protected BaseEvent baseEvent;
	protected MenuEvent menuEvent;
	protected LocationEvent locationEvent;
	protected SubscribeEvent subscribeEvent;
	protected QRCodeEvent qrCodeEvent;
	protected ScanCodeEvent scanCodeEvent;
	protected SendPicEvent sendPicEvent;
	protected SendLocationEvent sendLocationEvent;
	
    protected abstract void parseSpecialEvent(BaseEvent baseEvent, Map<String, String> requestMap); 
    
    public void parseEvent(BaseEvent baseEvent, Map<String, String> requestMap) {
    	baseEvent.setMsgType(requestMap.get("MsgType"));
    	baseEvent.setFromUserName(requestMap.get("FromUserName"));
    	baseEvent.setToUserName(requestMap.get("ToUserName"));
    	baseEvent.setCreateTime(Long.parseLong(requestMap.get("CreateTime")));
    	String eventType = requestMap.get("Event");
    	baseEvent.setEvent(eventType);
    	parseSpecialEvent(baseEvent, requestMap);
	}

	public MenuEvent getMenuEvent() {
		return menuEvent;
	}

	public LocationEvent getLocationEvent() {
		return locationEvent;
	}

	public SubscribeEvent getSubscribeEvent() {
		return subscribeEvent;
	}

	public QRCodeEvent getQrCodeEvent() {
		return qrCodeEvent;
	}

	public ScanCodeEvent getScanCodeEvent() {
		return scanCodeEvent;
	}

	public SendPicEvent getSendPicEvent() {
		return sendPicEvent;
	}

	public SendLocationEvent getSendLocationEvent() {
		return sendLocationEvent;
	}
    
}
