package user.handler;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.exception.DuplicateException;
import common.handler.CommandHandler;
import common.service.JoinRequest;
import common.service.JoinService;

//사용자의 요청을 받아서 form 화면을 보여줄지, 데이터로 회원가입을 시킬지 구분하여 처리한다.
public class JoinHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/joinForm.jsp";
	// 사용자는 board/join ->
	// form에서 전송할 action 역시 board/join
	// GET 방식으로 요청이 오면 form을 보여주는 view로 리턴을 하고
	// POST 방식으로 요청이 오면 회원가입을 처리하고 결과를 보여주는 뷰로 리턴

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		if (req.getMethod().equalsIgnoreCase("GET")) {
			return processForm(req, resp);
		} else if (req.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(req, resp);
		} else {
			resp.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

	private String processForm(HttpServletRequest req, HttpServletResponse resp) {

		return FORM_VIEW;
	}

	// 사용자로부터 회원가입데이터를 입력받아
	// submit버튼을 클릭해서 데이터가 넘어왔을 때 실행하는 메소드
	private String processSubmit(HttpServletRequest req, HttpServletResponse resp) {
		// 파라미터를 통해서 입력받은 데이터를 joinRequest 객체에 담는다.
		JoinRequest joinRequest = new JoinRequest();
		joinRequest.setLoginId(req.getParameter("loginId"));
		joinRequest.setName(req.getParameter("name"));
		joinRequest.setPassword(req.getParameter("password"));
		joinRequest.setConfirmPassword(req.getParameter("confirmPassword"));

		// joinRequest를 통해 입력받은 데이터가 제대로 입력되어있는지,
		// 잘못된 정보는 errors라는 맵에 넣어놓기 위해 errors라는 맵을 생성!
		Map<String, Boolean> errors = new HashMap<String, Boolean>();
		// errors 는 view에 표출해주기 위해 request 속성값으로 넣어줌!
		req.setAttribute("errors", errors);
		// 데이터 검증! 무결성 체크
		joinRequest.validate(errors);
		// validate메소드가 지나오면, errors 맵에는 잘못된 데이터필드명(키)과 함께 true value 값이 추가되어 있음!

		// 잘못 들어왔으면 다시 폼화면으로 반환한다.
		if (!errors.isEmpty()) {
			return FORM_VIEW;
		}
		// 그래서 잘 입력되면 joinService를 통해서 회원가입 로직을 수행한다.
		JoinService joinService = JoinService.getInstance();
		try {
			joinService.join(joinRequest);
			// 성공화면으로 반환
			return "/WEB-INF/view/joinSuccess.jsp";
		} catch (DuplicateException e) {
			errors.put("duplicateId", true);
		}
		return null;
	}

}
