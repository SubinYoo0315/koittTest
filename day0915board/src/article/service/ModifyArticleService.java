package article.service;

import java.sql.Connection;
import java.sql.SQLException;

import article.dao.ArticleContentDao;
import article.dao.ArticleDao;
import article.model.Article;
import common.exception.ArticleNotFoundException;
import common.exception.PermissionDeniedException;
import jdbc.Connection.ConnectionProvider;

public class ModifyArticleService {
	private static ModifyArticleService instance = new ModifyArticleService();

	private ModifyArticleService() {
	}

	public static ModifyArticleService getInstance() {
		return instance;
	}

	public void modify(ModifyRequest mr) {
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
				articleDao.update(conn, mr.getArticleId(), mr.getTitle());
				articleContentDao.update(conn, mr.getArticleId(), mr.getContent());
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
