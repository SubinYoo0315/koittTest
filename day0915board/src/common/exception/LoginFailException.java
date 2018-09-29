package common.exception;

//로그인 실패시 던져질 예외
public class LoginFailException extends RuntimeException{
	public LoginFailException(String message) {
		super(message);
	}
}
