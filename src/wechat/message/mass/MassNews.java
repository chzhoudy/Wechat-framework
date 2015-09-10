package wechat.message.mass;

import java.util.HashMap;
import java.util.Map;

public class MassNews extends MassMessage{

	private Map<String, String> msg;

	public MassNews(String media_id) {
		super();
		msg = new HashMap<String, String>();
		msg.put("media_id",media_id);
		super.msgtype = "mpnews";
	}

	public Map<String, String> getMpnews() {
		return msg;
	}

	public void setMpnews(Map<String, String> mpnews) {
		this.msg = mpnews;
	}

}
