package article.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import article.dao.ArticleDao;
import article.model.Article;
import jdbc.Connection.ConnectionProvider;

public class ListArticleService {
	private static ListArticleService instance = new ListArticleService();
	private ListArticleService() {}
	public static ListArticleService getInstance() {
		return instance;
	}
	private int size = 10;//한페이지에 보여줄 게시물 개수
	private int blockSize = 5;//한페이지에서 보여줄 하단 페이지 링크의 개수
	
	public ArticlePage getArticlePage(int pageNum){
		try(Connection conn = ConnectionProvider.getConnection()){
			ArticleDao articleDao = ArticleDao.getInstance();
			int total = articleDao.selectCount(conn);
			List<Article> artList = articleDao.select(conn, (pageNum - 1) * size, size);
			return new ArticlePage(artList, pageNum, total, size, blockSize);
		}catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}
}
