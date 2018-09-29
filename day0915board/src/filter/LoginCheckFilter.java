package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
//로그인이 되어있는지 체크하는 필터
public class LoginCheckFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		// 세션을 받아서, 세션이 살아있는지 확인을 한다.
		// req는 getSession을 사용할수 없음으로 getSession이 가능한
		// HttpServlet으로 다운캐스팅을 하여 사용하자
		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession session = request.getSession(false);
		// 세션이 없다면 로그인 페이지로 이동.
		if (session == null || session.getAttribute("authUser") == null) {
			HttpServletResponse response = (HttpServletResponse) resp;
			response.sendRedirect(request.getContextPath() + "/login");
		} else {
			// 세션이 있으면, 요청한 기능이 있는 곳으로 보낸다.
			chain.doFilter(req, resp);
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}
}
