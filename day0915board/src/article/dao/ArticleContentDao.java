package article.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import article.model.ArticleContent;

public class ArticleContentDao {
	private static ArticleContentDao instance = new ArticleContentDao();
	private ArticleContentDao() {}
	public static ArticleContentDao getInstance() {
		return instance;
	}
	
	public ArticleContent insert(Connection conn, ArticleContent content) throws SQLException {
		String sql = "insert into article_content(articleId, content) values(?,?)";
		try(PreparedStatement pst = conn.prepareStatement(sql)){
			pst.setInt(1, content.getArticleId());
			pst.setString(2, content.getContent());
			int insertedCount = pst.executeUpdate();
			if(insertedCount > 0) {
				return content;
			}else {
				return null;
			}
		
		}
	}

	//게시글 내용을 가져오는 메소드
	public ArticleContent selectById(Connection conn, int articleId) throws SQLException {
		String sql = "select * from article_content where articleId = ?";
		try(PreparedStatement pst = conn.prepareStatement(sql)){
			pst.setInt(1, articleId);
			try(ResultSet rs = pst.executeQuery()){
				ArticleContent content = null;
				if(rs.next()) {
					content = new ArticleContent(rs.getInt("articleId"), rs.getString("content"));
				}
				return content;
			}
		}
	}
	
	//내용을 수정하는 메소드
	public int update(Connection conn, int articleId, String content) throws SQLException {
		String sql = "update article_content set content = ? where articleId=?";
		try(PreparedStatement pst = conn.prepareStatement(sql)){
			pst.setString(1, content);
			pst.setInt(2, articleId);
			return pst.executeUpdate();
		}
	}
	public int delete(Connection conn, int articleId) throws SQLException{
		String sql = "delete from article where articleId = ?";
		try(PreparedStatement pst = conn.prepareStatement(sql)){
			pst.setInt(1, articleId);
			return pst.executeUpdate();
		}
	}
}
