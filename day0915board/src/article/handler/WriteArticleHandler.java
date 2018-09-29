package article.handler;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.model.Writer;
import article.service.WriteArticleService;
import article.service.WriteRequest;
import auth.service.AuthUser;
import common.handler.CommandHandler;

public class WriteArticleHandler implements CommandHandler{
	private static final String FORM_VIEW = "/WEB-INF/view/newArticleForm.jsp";

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		if(req.getMethod().equalsIgnoreCase("GET")) {
			return processForm(req,resp);
		}else if(req.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(req, resp);
		}else {
			resp.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}
		return null;
	}

	private String processSubmit(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		Map<String, Boolean> errors = new HashMap<String ,Boolean>();
		req.setAttribute("errors", errors);
		AuthUser authUser = (AuthUser)req.getSession().getAttribute("authUser");
		WriteRequest writeRequest = new WriteRequest(new Writer(authUser.getUserId(), authUser.getName()),
				req.getParameter("title"),
				req.getParameter("content"));
		writeRequest.validate(errors);
		if(!errors.isEmpty()) {
			return FORM_VIEW;
		}
		WriteArticleService articleService = WriteArticleService.getInstance();
		int newArticleNum = articleService.write(writeRequest);
		req.setAttribute("newArticleNum", newArticleNum);
		return "/WEB-INF/view/newArticleSuccess.jsp";
	}

	private String processForm(HttpServletRequest req, HttpServletResponse resp) {
		return FORM_VIEW;
		// TODO Auto-generated method stub
		//사용자한테 입력받은 게시글 내용의 무결성을 체크하기 위해
		//writeRequest 객체에 담아서 비어있는지 체크하고 
		//이상이 있는 form_view로 다시 돌리고 없으면,
		//서비스를 이용해서 게시글 삽입 로직을 수행한다.
		
	}

}
