package comments.db;

import java.util.ArrayList;

public interface CommentsDAO{
	//댓글 쓰기
	public int insertComments(CommentsBean cBean);
	
	//댓글 번호 
	public int getCommentsNo();
	
	//댓글 가져오기
	public ArrayList<CommentsBean> selectCommentsList(CommentsBean cBean);
	
	//댓글 삭제
	public int commentsDelete(int co_no, String co_email);
}

