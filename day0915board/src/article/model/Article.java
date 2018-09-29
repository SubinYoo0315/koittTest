package article.model;

import java.time.LocalDateTime;

//게시글의 정보를 담는 객체
public class Article {
	private int articleId;
	private Writer writer;
	private String title;
	private LocalDateTime wdate;
	private LocalDateTime udate;
	private int readCnt;
	
	public Article(int articleId, Writer writer, String title, LocalDateTime wdate, LocalDateTime udate, int readCnt) {
		super();
		this.articleId = articleId;
		this.writer = writer;
		this.title = title;
		this.wdate = wdate;
		this.udate = udate;
		this.readCnt = readCnt;
	}
	
	public Article(Writer writer, String title) {
		this.writer = writer;
		this.title = title;
	}
	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public Writer getWriter() {
		return writer;
	}

	public void setWriter(Writer writer) {
		this.writer = writer;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDateTime getWdate() {
		return wdate;
	}

	public void setWdate(LocalDateTime wdate) {
		this.wdate = wdate;
	}

	public LocalDateTime getUdate() {
		return udate;
	}

	public void setUdate(LocalDateTime udate) {
		this.udate = udate;
	}

	public int getReadCnt() {
		return readCnt;
	}

	public void setReadCnt(int readCnt) {
		this.readCnt = readCnt;
	}
	
	
}
