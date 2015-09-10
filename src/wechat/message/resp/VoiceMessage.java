package wechat.message.resp;

/**
 * 语音消息
 * 
 */
public class VoiceMessage extends BaseMessage {
	// 媒体文件id
	private String MediaId;
	private String MsgType = "voice";
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
