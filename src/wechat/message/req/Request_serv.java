package wechat.message.req;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import wechat.enums.IMessage;
import wechat.enums.Methods;
import wechat.message.event.BaseEvent;
import wechat.message.event.LocationEvent;
import wechat.message.event.MenuEvent;
import wechat.message.event.QRCodeEvent;
import wechat.message.event.ScanCodeEvent;
import wechat.message.event.SendLocationEvent;
import wechat.message.event.SendPicEvent;
import wechat.req.handler.EventHandlerHelper;
import wechat.req.handler.EventHandlerLocation;
import wechat.req.handler.EventHandlerMenu;
import wechat.req.handler.EventHandlerQRCode;
import wechat.req.handler.EventHandlerScanCode;
import wechat.req.handler.EventHandlerSendLocation;
import wechat.req.handler.EventHandlerSendPic;
import wechat.req.handler.EventHandlerSubscribe;
import wechat.req.handler.MessageHandlerHelper;
import wechat.req.handler.MessageHandlerImage;
import wechat.req.handler.MessageHandlerLink;
import wechat.req.handler.MessageHandlerLocation;
import wechat.req.handler.MessageHandlerText;
import wechat.req.handler.MessageHandlerVideo;
import wechat.req.handler.MessageHandlerVoiceReco;
import wechat.util.Config;
import wechat.util.MessageUtil;

public class Request_serv implements IMessage {
	private static String token = null;
	private Map<String, String> requestMap;
	private final static Logger logger = LogManager.getLogger(Request_serv.class.getName());  
	protected String fromUserName;
	protected String toUserName;
	protected String msgType;
	protected BaseMessage baseMessage;
	protected BaseEvent baseEvent;
	protected EventHandlerHelper eventHandlerHelper;
	protected MessageHandlerHelper messageHandlerHelper;

	public Request_serv() {
		
	}

	/**
	 * 处理发送来的请求信息，先用check方法判断是否是微信服务器发来，若是返回解析后消息
	 * @param token
	 * @param request
	 * @return
	 */
	public void init(String module,HttpServletRequest request) {
		token = Config.getValue(module, "name");
		if (!MessageUtil.check(token, request)) {
			requestMap = null;
		}
		try {
			requestMap = MessageUtil.parseXml(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		processRequest();
	}

	/***
	 * 解析map类型的请求信息
	 */
	public void processRequest() {
		fromUserName = requestMap.get("FromUserName");
		toUserName = requestMap.get("ToUserName");
		msgType = requestMap.get("MsgType");
		System.out.println(msgType);
		if (msgType.equalsIgnoreCase(REQ_MESSAGE_TYPE_TEXT)) {
			baseMessage = new TextMessage();
			messageHandlerHelper = new MessageHandlerText();
		} else if (msgType.equalsIgnoreCase(REQ_MESSAGE_TYPE_IMAGE)) {
			baseMessage = new ImageMessage();
			messageHandlerHelper = new MessageHandlerImage();
		} else if (msgType.equalsIgnoreCase(REQ_MESSAGE_TYPE_LINK)) {
			baseMessage = new LinkMessage();
			messageHandlerHelper = new MessageHandlerLink();
		} else if (msgType.equalsIgnoreCase(REQ_MESSAGE_TYPE_LOCATION)) {
			baseMessage = new LocationMessage();
			messageHandlerHelper = new MessageHandlerLocation();
		} else if (msgType.equalsIgnoreCase(REQ_MESSAGE_TYPE_VIDEO)) {
			baseMessage = new VideoMessage();
			messageHandlerHelper = new MessageHandlerVideo();
		} else if (msgType.equalsIgnoreCase(REQ_MESSAGE_TYPE_VOICE)) {
			baseMessage = new VoiceRecoMessage();
			messageHandlerHelper = new MessageHandlerVoiceReco();
		} else if (msgType.equalsIgnoreCase(REQ_MESSAGE_TYPE_EVENT)) {
			String eventType = requestMap.get("Event");
			System.out.println(eventType);
			if (eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)) {
				baseEvent = new MenuEvent();
				eventHandlerHelper = new EventHandlerMenu();
			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_LOCATION)) {
				baseEvent = new LocationEvent();
				eventHandlerHelper = new EventHandlerLocation();
			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_SCAN)) {
				baseEvent = new QRCodeEvent();
				eventHandlerHelper = new EventHandlerQRCode();
			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_PIC_ALBUM)){
				baseEvent = new SendPicEvent();
				eventHandlerHelper = new EventHandlerSendPic();
			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_SCANCODE_WAITMSG)){
				baseEvent = new ScanCodeEvent();
				eventHandlerHelper = new EventHandlerScanCode();
			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_LOCATION_SELECT)){
				baseEvent = new SendLocationEvent();
				eventHandlerHelper = new EventHandlerSendLocation();
			} else {
				baseEvent = new BaseEvent();
				eventHandlerHelper = new EventHandlerSubscribe();
			}
			eventHandlerHelper.parseEvent(baseEvent, requestMap);
			return;
		}
		messageHandlerHelper.parseMessage(baseMessage, requestMap,logger);
	}
	
	/**
	 * @return 返回方法名称
	 */
	public String findMethod() {
		String methodName = null;
		if (msgType.equals(REQ_MESSAGE_TYPE_EVENT)) {
			String eventType = baseEvent.getEvent();
			methodName = Methods.methodis(eventType);
			if (eventType.equals(EVENT_TYPE_CLICK)) {
				System.out.println(eventHandlerHelper.getMenuEvent().getEventKey().length());
				if(eventHandlerHelper.getMenuEvent().getEventKey().length() > 2 ){
					methodName += eventHandlerHelper.getMenuEvent().getEventKey();
					System.out.println("method:" + methodName);
				}
			}
		} else {
			methodName = Methods.methodis(msgType);
		}
		return methodName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public String getToUserName() {
		return toUserName;
	}

	public EventHandlerHelper getEventHandlerHelper() {
		return eventHandlerHelper;
	}

	public MessageHandlerHelper getMessageHandlerHelper() {
		return messageHandlerHelper;
	}
	
}
