package article.service;
//한 페이지에서 보여줄 게시글 정보와 페이지 관련 정보를 담는 클래스/

//페이지(게시글 목록의 한 화면)에 여기에 담겨있는 내용을 출력할 예정.

import java.util.List;

import com.sun.org.apache.xml.internal.serializer.ToHTMLSAXHandler;

import article.model.Article;

public class ArticlePage {

	// 게시글id, 제목, 작성자, 작성자id, 작성일, 수정일, 조회수 -> article
	// 게시글 정보를 담고 있는 객체들의 리스트;
	private List<Article> artList;
	// 사용자가 요청한 페이지 번호.
	private int currentPage;
	// 전체 페이지 수
	// 한페이지에 10개씩 보여준다. 11개라면 -> 11/10 이므로 1페이지 + 1 이되서 2페이지가 된다.
	private int totalPages;
	// 게시글의 전체 개수
	private int total;
	// 화면 하단에 보여줄 페이지링크의 시작번호
	private int startPage;
	// 화면 하단에 보여줄 페이지링크의 끝 번호
	private int endPage;

	public ArticlePage(List<Article> artList, int currentPage, int total, int size, int blockSize) {
		// size는 한페이지에 보여줄 게시글의 개수
		// blockSize는 한 페이지에서 보여줄 하단 페이지 링크 블럭개수
		this.artList = artList;
		this.currentPage = currentPage;
		this.total = total;
		if (total == 0) {
			totalPages = 0;
			startPage = 0;
			endPage = 0;
		} else {
			totalPages = total / size;
			if (total % size > 0) {
				totalPages++;
			}
			startPage = currentPage / blockSize * blockSize + 1;
			// 페이지 번호와 블럭사이즈가 같아 페이지가 넘어가는것을 막기위함
			if ((currentPage % blockSize) == 0) {
				startPage -= blockSize;
			}
			endPage = startPage + (blockSize - 1);
			if (endPage > totalPages) {
				endPage = totalPages;
			}
		}
	}
	public boolean hasArticles() {
		return total > 0;
	}
	
	public List<Article> getArtList() {
		return artList;
	}

	public int getTotalPages() {
		return totalPages;
	}
	
	public int getCurrentPage() {
		return currentPage;
	}

	public int getTotal() {
		return total;
	}

	public int getStartPage() {
		return startPage;
	}

	public int getEndPage() {
		return endPage;
	}

}
