package wechat.message.resp;

/**
 * 图片消息
 * 
 */
public class ImageMessage extends BaseMessage {
	// 图片
	private String MediaId;
	private String MsgType = "image";
	public String getMediaId() {
		return MediaId;
	}
	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	
}
