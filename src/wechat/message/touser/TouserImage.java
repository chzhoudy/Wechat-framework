package wechat.message.touser;

import java.util.HashMap;
import java.util.Map;

public class TouserImage{
	private Map<String, Object> image;

	public TouserImage(Mediaid image1,String touser) {
		image = new HashMap<String, Object>();
		image.put("msgtype", "image");
		image.put("touser", touser);
		image.put("image",image1);
	}

	public Map<String, Object> getImage() {
		return image;
	}

	public void setImage(Map<String, Object> image) {
		this.image = image;
	}

}
