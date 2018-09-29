package article.service;

import article.model.Article;
import article.model.ArticleContent;

//게시글 보기에 필요한 데이타
public class ArticleData {
	private Article article;//article 테이블 내용
	private ArticleContent content;// article_content 테이블 내용
	public ArticleData(Article article, ArticleContent content) {
		this.article = article;
		this.content = content;
	}
	public Article getArticle() {
		return article;
	}
	public String getContent() {
		return content.getContent();
	}
	
}
