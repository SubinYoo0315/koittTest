package article.service;

import java.util.Map;

public class ModifyRequest {
	private int userId;
	private int articleId;
	private String title;
	private String content;
	
	public ModifyRequest(int userId, int articleId, String title, String content) {
		super();
		this.userId = userId;
		this.articleId = articleId;
		this.title = title;
		this.content = content;
	}

	public int getUserId() {
		return userId;
	}

	public int getArticleId() {
		return articleId;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}
	
	public void validate(Map<String, Boolean> errors) {
		if(title == null || title.trim().isEmpty()) {
			errors.put("title", true);
		}
	}
	
}
