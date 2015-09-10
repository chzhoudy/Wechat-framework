package wechat.message.touser;

import java.util.List;

public class ArticleList {
	private List<New> articles;
	
	public ArticleList(List<New> articles){
		this.articles = articles;
	}

	public List<New> getArticles() {
		return articles;
	}

	public void setArticles(List<New> articles) {
		this.articles = articles;
	}	
	
}
