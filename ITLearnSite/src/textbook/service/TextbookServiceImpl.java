package textbook.service;

import java.util.ArrayList;

import textbook.db.TextbookBean;
import textbook.db.TextbookDAOImpl;

public class TextbookServiceImpl implements TextbookService {
	
	TextbookDAOImpl tbDAO;
	TextbookBean tBean;
	public TextbookServiceImpl() {
		tbDAO = new TextbookDAOImpl();
		tBean = new TextbookBean();
	}
	
	//도서 리스트
	@Override
	public ArrayList<TextbookBean> selectBookList(int startRow, int endRow) {
		return tbDAO.selectBookList(startRow, endRow);
	}
	
	//도서 등록
	@Override
	public int insertBook(TextbookBean tBean) {
		int result = tbDAO.insertBook(tBean);
		return result;
	}
	
	//도서 자세히 보기
	@Override
	public TextbookBean bookdetail(int product_no) {
		tBean = tbDAO.bookdetail(product_no);
		return tBean;
	}
	
	//도서 삭제
	@Override
	public int deletebook(int product_no) {
		int check = tbDAO.bookdelete(product_no);
		return check;
	}
	
	//도서 정보 수정
	@Override
	public int stockModify(int p_no, int price, int stock) {
		int result = tbDAO.stockmodify(p_no, price, stock);
		return result;
	}
	
	//전체 글 갯수 얻기 - 페이징 목적
	@Override
	public int count() {
		int count = tbDAO.count();
		return count;
	}
}
