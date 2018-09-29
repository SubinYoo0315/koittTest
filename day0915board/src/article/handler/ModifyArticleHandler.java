package article.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.model.ArticleContent;
import article.service.ArticleData;
import article.service.ModifyArticleService;
import article.service.ModifyRequest;
import article.service.ReadArticleService;
import auth.service.AuthUser;
import common.exception.ArticleNotFoundException;
import common.exception.PermissionDeniedException;
import common.handler.CommandHandler;

public class ModifyArticleHandler implements CommandHandler{
	private static final String FORM_VIEW = "/WEB-INF/view/modifyForm.jsp";

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
	
	private String processForm(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		ReadArticleService readArticleService = ReadArticleService.getInstance();
		try {
			//게시글의 아이디를 받아서
			String noStr = req.getParameter("no");
			int no = Integer.parseInt(noStr);
			//게시글을 읽어오는 서비스를 이용하여 데이터를 받아온다.
			ArticleData articleData = readArticleService.getArtice(no, false);
			//사용자가 수정을 할수 있는 권한이 있는지 확인하고
			AuthUser authUser = (AuthUser)req.getSession().getAttribute("authUser");
			if(!canModify(authUser, articleData)) {
				//없으면 에러를 response에 담아서 보낸다 SC_FORBIDDEN
				resp.sendError(HttpServletResponse.SC_FORBIDDEN);
				return null;
			}
			//있으면 modifyRequest에 담아서 보여줌.
			ModifyRequest modifyRequest = new ModifyRequest(authUser.getUserId(), no, 
					articleData.getArticle().getTitle(), articleData.getContent());
			req.setAttribute("modReq", modifyRequest);
			return FORM_VIEW;
		}catch(ArticleNotFoundException e) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}	
	}
	
	
	private boolean canModify(AuthUser authUser, ArticleData articleData) {
		int writeId = articleData.getArticle().getWriter().getWriterId();
		return authUser.getUserId() == writeId;
	}

	private String processSubmit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		AuthUser authUser = (AuthUser)req.getSession().getAttribute("authUser");
		String noStr = req.getParameter("no");
		int no = Integer.parseInt(noStr);
		ModifyRequest modifyRequest = new ModifyRequest(authUser.getUserId(), no, 
				req.getParameter("title"), req.getParameter("content"));
		req.setAttribute("modReq", modifyRequest);
		Map<String, Boolean>errors = new HashMap<String, Boolean>();
		req.setAttribute("errors", errors);
		modifyRequest.validate(errors);
		if(!errors.isEmpty()) {
			return FORM_VIEW;
		}
		//modifyRequest에 수정 내용들을 파라미터에서 받아서 담고
		//에러를 체크하고
		//서비스를 통해서 수정을 실행후
		//수정 성공화면으로 전환.
		//게시글이 없거나, 권한이 없으면 에러를 반환.
		try {
			ModifyArticleService modifyArticleService = ModifyArticleService.getInstance();
			modifyArticleService.modify(modifyRequest);
			return "/WEB-INF/view/modifySuccess.jsp";
		}catch(ArticleNotFoundException e) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}catch(PermissionDeniedException e) {
			resp.sendError(HttpServletResponse.SC_FORBIDDEN);
			return null;
		}
		
	}

	
}
