package article.service;

import java.sql.Connection;
import java.sql.SQLException;

import article.dao.ArticleContentDao;
import article.dao.ArticleDao;
import article.model.Article;
import article.model.ArticleContent;
import common.exception.ArticleContentNotFoundException;
import common.exception.ArticleNotFoundException;
import jdbc.Connection.ConnectionProvider;

public class ReadArticleService {
	private static ReadArticleService instance = new ReadArticleService();
	private ReadArticleService() {}
	public static ReadArticleService getInstance() {
		return instance;
	}
	
	public ArticleData getArtice(int articleId, boolean increaseReadCount) {
		ArticleDao articleDao = ArticleDao.getInstance();
		ArticleContentDao articleContentDao = ArticleContentDao.getInstance();
		try(Connection conn = ConnectionProvider.getConnection()){
			Article article = articleDao.selectById(conn, articleId);
			if(article == null) {
				throw new ArticleNotFoundException("article이 없다");
			}
			ArticleContent content = articleContentDao.selectById(conn, articleId);
			if(content==null) {
				throw new ArticleContentNotFoundException("content가 없다");
			}
			if(increaseReadCount) {
				articleDao.increaseReadCount(conn, articleId);
			}
			return new ArticleData(article, content);
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
}
