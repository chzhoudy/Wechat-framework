package wechat.message.req;

public class VoiceRecoMessage extends VoiceMessage {
	/*
	 * 开通语音识别功能，用户每次发送语音给公众号时，
	 * 微信会在推送的语音消息XML数据包中，增加一个Recongnition字段
	 */
    private String Recognition;

	public String getRecognition() {
		return Recognition;
	}

	public void setRecognition(String recognition) {
		Recognition = recognition;
	}
     
}
