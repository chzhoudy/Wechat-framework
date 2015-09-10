package wechat.message.touser;

import java.util.HashMap;
import java.util.Map;

public class TouserMusic {
	private Map<String, Object> music;

	public TouserMusic(Music music1, String touser) {
		music = new HashMap<String, Object>();
		music.put("music", music1);
		music.put("msgtype", "music");
		music.put("touser", touser);
	}

	public Map<String, Object> getMusic() {
		return music;
	}

	public void setMusic(Map<String, Object> music) {
		this.music = music;
	}
	
	
}
