package article.handler;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.dao.ArticleContentDao;
import article.dao.ArticleDao;
import article.model.Article;
import article.model.ArticleContent;
import article.service.ArticleData;
import article.service.ReadArticleService;
import common.exception.ArticleContentNotFoundException;
import common.exception.ArticleNotFoundException;
import common.handler.CommandHandler;
import jdbc.Connection.ConnectionProvider;

public class ReadArticleHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//서비스 받아오고
		ReadArticleService readArticleService = ReadArticleService.getInstance();
		//사용자가 요청한 데이터 받아오고
		int articleId = Integer.parseInt(req.getParameter("no"));
		try(Connection conn = ConnectionProvider.getConnection()) {
			//boolean increaseReadCount = true;
			//화면에 보여줄 데이터 만들기
			ArticleData articleData = readArticleService.getArtice(articleId, true);
			//화면에 보여줄 데이터 넘기기
			req.setAttribute("articleData", articleData);
			req.setAttribute("a", articleData.getContent());
			return "/WEB-INF/view/readArticle.jsp";
		}catch(ArticleNotFoundException | ArticleContentNotFoundException e){
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}	
		
	}

}
