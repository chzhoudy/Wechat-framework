package wechat.base;

import wechat.enums.IMessage;
import wechat.message.req.Request_serv;
import wechat.message.resp.RespMsg_serv;
import wechat.req.handler.EventHandlerHelper;
import wechat.req.handler.MessageHandlerHelper;

public class Base_json implements IMessage{
	protected String fromUserName;
	protected String toUserName;
	protected EventHandlerHelper eventHandlerHelper;
	protected MessageHandlerHelper messageHandlerHelper;
	protected RespMsg_serv respMsg;
	
	public Base_json() {
		respMsg = new RespMsg_serv();
	}
	
	public void init(Request_serv reqServ){
		fromUserName = reqServ.getFromUserName();
		toUserName = reqServ.getToUserName();
		eventHandlerHelper = reqServ.getEventHandlerHelper();
		messageHandlerHelper = reqServ.getMessageHandlerHelper();
	}
}
