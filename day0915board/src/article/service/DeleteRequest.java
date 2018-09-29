package article.service;

import java.util.Map;

public class DeleteRequest {
	private int userId;
	private int articleId;
	private String title;
	private String content;
	
	public DeleteRequest(int userId, int articleId) {
		super();
		this.userId = userId;
		this.articleId = articleId;
	}

	public int getUserId() {
		return userId;
	}

	public int getArticleId() {
		return articleId;
	}
}
