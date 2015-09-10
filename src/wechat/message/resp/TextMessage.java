package wechat.message.resp;

/**
 * 文字消息
 * 
 */
public class TextMessage extends BaseMessage {
	private String Content;
	private String MsgType = "text";
	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
}