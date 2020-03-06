package textbook.service;

import java.util.ArrayList;

import textbook.db.TextbookBean;

public interface TextbookService {
	//도서 리스트
	public ArrayList<TextbookBean> selectBookList(int startRow , int endRow);
	
	//도서 등록
	public int insertBook(TextbookBean  tBean);
	
	//도서 자세히 보기
	public TextbookBean bookdetail(int product_no);
	
	//도서 삭제
	public int deletebook (int product_no);
	
	//도서 정보 수정
	public int stockModify(int p_no, int price , int stock);
	
	//전체 글 갯수 얻기 - 페이징 목적
	public int count();
}
