package wechat.message.mass;

import java.util.HashMap;
import java.util.Map;

public class MassVideo extends MassMessage{

	private Map<String, String> mpvideo;

	public MassVideo(String media_id) {
		super();
		mpvideo = new HashMap<String, String>();
		mpvideo.put("media_id",media_id);
		super.msgtype = "mpvideo";
	}

	public Map<String, String> getMpvideo() {
		return mpvideo;
	}

	public void setMpvideo(Map<String, String> mpvideo) {
		this.mpvideo = mpvideo;
	}

}
