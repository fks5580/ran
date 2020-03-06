package textbookcomments.db;

import java.util.ArrayList;

public interface TextbookCommentsDAO{
	//후기 작성
	public int insertTextbookComments(TextbookCommentsBean tcBean);
	
	//후기 번호
	public int getTextbookCommentsNo();
	
	//후기 가져오기
	public ArrayList<TextbookCommentsBean> selectTextbookCommentsList(TextbookCommentsBean tcBean);
	
	//후기 삭제
	public int TextbookcommentsDelete(int bo_no, String bo_email);
}

