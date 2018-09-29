package auth.service;

import java.sql.Connection;
import java.sql.SQLException;

import common.exception.LoginFailException;
import jdbc.Connection.ConnectionProvider;
import user.dao.UserDao;
import user.model.User;

//사용자로 부터 입력받은 데이터를 통해
//정보가 정확한지 또는 사용자가 존재하는지 확인!
public class LoginService {
	private static LoginService instance = new LoginService();
	private LoginService() {}
	public static LoginService getInstance() {
		return instance;
	}
	
	//성공시 AuthUser 객체를 반환시켜주는 메소드
	public AuthUser login(String loginId, String password) throws SQLException {
		UserDao userDao = UserDao.getInstance();
		try(Connection conn = ConnectionProvider.getConnection()){
			User user = userDao.selectByLoginId(conn, loginId);
			//사용자가 없다면!(아이디로 DB에 없을 시)
			if(user == null) {
				throw new LoginFailException("없는 사용자");
			}
			
			//비밀번호랑 아이디가 맞는지
			if(!user.matchPassword(password)) {
				throw new LoginFailException("비밀번호가 틀림");
			}
			
			//인증이 완료되었으니 auth 객체를 반환
			return new AuthUser(user.getUserId(),user.getLoginId(), user.getName());
		}
		//아이디를 통해 사용자 객체를 select 해서 가져옴.
		//객체가 있으면, 비밀번호를 비교하고
		//객체가 없으면 없는 사용자라고 예외를 날림
	}
}
