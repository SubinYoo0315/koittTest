package article.model;

//작성자의 정보를 담는 객체.
public class Writer {
	private int writerId;
	private String name;
	public Writer(int writerId, String name) {
		this.writerId = writerId;
		this.name = name;
	}
	public int getWriterId() {
		return writerId;
	}
	public String getName() {
		return name;
	}
	
}
