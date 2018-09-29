package common.exception;

//비밀번호 변경할때 그 사용자 정보가 없을 시.
public class UserNotFoundException extends RuntimeException {
	
	public UserNotFoundException(String message) {
		super(message);
	}
	
}
