package wechat.message.touser;

import java.util.HashMap;
import java.util.Map;

public class TouserNew {
	private Map<String, Object> news;

	public TouserNew(ArticleList article, String touser) {
		news = new HashMap<String, Object>();
		news.put("news", article);
		news.put("msgtype", "news");
		news.put("touser", touser);
	}

	public Map<String, Object> getNews() {
		return news;
	}

	public void setNews(Map<String, Object> news) {
		this.news = news;
	}

}
