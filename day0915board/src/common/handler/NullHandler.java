package common.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//CommandHandler를 상속받아서 생성!!
//널이면 사용할 핸들러!
public class NullHandler implements CommandHandler {

	//404에러를 응답으로 전송하는 핸들러 클래스
	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//response 에 응답을 404로 응답!!!
		resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		return null;
	}

}
