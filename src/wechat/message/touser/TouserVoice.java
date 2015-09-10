package wechat.message.touser;

import java.util.HashMap;
import java.util.Map;

public class TouserVoice{
	private Map<String, Object> voice;

	public TouserVoice(Mediaid voice1,String touser) {
		voice = new HashMap<String, Object>();
		voice.put("media_id",voice1);
		voice.put("msgtype", "voice");
		voice.put("touser", touser);
	}

	public Map<String, Object> getVoice() {
		return voice;
	}

	public void setVoice(Map<String, Object> voice) {
		this.voice = voice;
	}

}
