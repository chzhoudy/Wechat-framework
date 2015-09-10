package wechat.message.touser;

import java.util.HashMap;
import java.util.Map;

public class TouserText {
	private Map<String, Object> text;

	public TouserText(Text text1, String touser) {
		text = new HashMap<String, Object>();
		text.put("msgtype", "text");
		text.put("touser", touser);
		text.put("text", text1);
	}

	public Map<String, Object> getText() {
		return text;
	}

	public void setText(Map<String, Object> text) {
		this.text = text;
	}
	
}
