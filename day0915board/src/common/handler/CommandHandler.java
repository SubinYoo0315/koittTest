package common.handler;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//커맨드 패턴에서 명령을 처리하는 애들이 동일한
//인터페이스를 상속받아서 처리하도록 인터페이스 생성.
public interface CommandHandler {
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception;
}
