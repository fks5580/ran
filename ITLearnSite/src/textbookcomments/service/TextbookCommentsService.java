package textbookcomments.service;

import java.util.ArrayList;

import comments.db.CommentsBean;
import textbookcomments.db.TextbookCommentsBean;

public interface TextbookCommentsService {
	//후기 작성
	public int insertTextbookComments(TextbookCommentsBean cbBean);
	
	//후기 가져오기
	public ArrayList<TextbookCommentsBean> selectTextbookCommentsList(TextbookCommentsBean cbBean);
	
	//후기 삭제
	public int TextbookcommentsDelete(int bo_no, String bo_email);
}
