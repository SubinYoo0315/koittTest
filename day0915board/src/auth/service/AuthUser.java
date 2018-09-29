package auth.service;

//로그인 인증에 사용할 객체.
//로그인상태 확인용 객체.
public class AuthUser {
	private int userId;
	private String loginId;
	private String name;
	
	public AuthUser(int userId, String loginId, String name) {
		this.userId = userId;
		this.loginId = loginId;
		this.name = name;
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getLoginId() {
		return loginId;
	}
	
	public String getName() {
		return name;
	}
}
