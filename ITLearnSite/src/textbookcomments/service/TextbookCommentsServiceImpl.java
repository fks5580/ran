package textbookcomments.service;

import java.util.ArrayList;

import comments.db.CommentsBean;
import comments.db.CommentsDAOImpl;
import textbookcomments.db.TextbookCommentsBean;
import textbookcomments.db.TextbookCommentsDAOImpl;

public class TextbookCommentsServiceImpl implements TextbookCommentsService {
	TextbookCommentsDAOImpl tcDao;
	
	public TextbookCommentsServiceImpl() {
		tcDao = new TextbookCommentsDAOImpl();
	}
	
	//후기 작성
	@Override
	public int insertTextbookComments(TextbookCommentsBean tcBean) {
		int check = tcDao.insertTextbookComments(tcBean);
		return check;
	}
	
	//후기 가져오기
	@Override
	public ArrayList<TextbookCommentsBean> selectTextbookCommentsList(TextbookCommentsBean tcBean) {
		return tcDao.selectTextbookCommentsList(tcBean);
	}
	
	//후기 삭제
	@Override
	public int TextbookcommentsDelete(int bo_no, String bo_email) {
		return tcDao.TextbookcommentsDelete(bo_no, bo_email);
	}
}
