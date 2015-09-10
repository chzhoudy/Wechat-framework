package wechat.message.touser;

import java.util.HashMap;
import java.util.Map;

public class TouserVideo{
	private Map<String, Object> video;
	
	public TouserVideo(Video video1,String touser){
		video = new HashMap<String, Object>();
		video.put("video",video1);
		video.put("msgtype", "video");
		video.put("touser", touser);
	}
	
	public Map<String, Object> getVideo() {
		return video;
	}

	public void setVideo(Map<String, Object> video) {
		this.video = video;
	}
}
