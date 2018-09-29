package article.service;

import java.sql.Connection;
import java.sql.SQLException;

import article.dao.ArticleContentDao;
import article.dao.ArticleDao;
import article.model.Article;
import common.exception.ArticleNotFoundException;
import common.exception.PermissionDeniedException;
import jdbc.Connection.ConnectionProvider;

public class DeleteArticleService {
	private static DeleteArticleService instance = new DeleteArticleService();

	private DeleteArticleService() {
	}

	public static DeleteArticleService getInstance() {
		return instance;
	}

	public void delete(DeleteRequest mr) {
		ArticleDao articleDao = ArticleDao.getInstance();
		ArticleContentDao articleContentDao = ArticleContentDao.getInstance();

		try (Connection conn = ConnectionProvider.getConnection()) {
			try {
				conn.setAutoCommit(false);
				// 게시글이 있는지 확인
				Article article = articleDao.selectById(conn, mr.getArticleId());
				if (article == null) {
					throw new ArticleNotFoundException("없는 게시글입니다.");
				}
				// 사용자 권한이 있는지 확인.
				if (article.getWriter().getWriterId() != mr.getUserId()) {
					throw new PermissionDeniedException("사용자 권한이 없음");
				}
				articleDao.delete(conn, mr.getArticleId());
				articleContentDao.delete(conn, mr.getArticleId());
				// articleDao, articleContentDao를 이용해서 게시글 수정 메소드를 실행
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				throw new RuntimeException(e);
			} catch (PermissionDeniedException e) {
				conn.rollback();
				throw e;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
	
	
}
