package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CharacterEncodingFilter implements Filter {
	
	private String encoding;
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		System.out.println("encoding filter start");
		req.setCharacterEncoding(encoding);
		chain.doFilter(req, resp);
		System.out.println("encoding filter end");
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub
		// web.xml의 설정값으로 인코딩을 설정하는데, 없다면! 기본으로 utf-8을 하겠다.
		encoding = config.getInitParameter("encoding");
		if(encoding == null) {
			encoding = "utf-8";
		}
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}
}
