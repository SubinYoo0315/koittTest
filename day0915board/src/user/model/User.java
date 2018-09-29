package user.model;

import java.time.LocalDateTime;

public class User {
	private int userId;
	private String loginId;
	private String name;
	private String password;
	private LocalDateTime regdate;
	//생성자 하나는 디비에서 가져올때 사용할 생성자.
	//5개 데이터를 담을수 있게 만들면 되지만.
	//생성자 하나는 루비에서 입덕할 때 쓸생정자,
	//자동으로 들어가는 데이터를 넣을 필요가 없어서.
	public User(int userId, String loginId, String name, String password, LocalDateTime regdate) {
		super();
		this.userId = userId;
		this.loginId = loginId;
		this.name = name;
		this.password = password;
		this.regdate = regdate;
	}
	public User(String loginId, String name, String password) {
		super();
		this.loginId = loginId;
		this.name = name;
		this.password = password;
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

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDateTime getRegdate() {
		return regdate;
	}

	public void setRegdate(LocalDateTime regdate) {
		this.regdate = regdate;
	}
	
	public boolean matchPassword(String pwd) {
		return password.equals(pwd);
	}
}
