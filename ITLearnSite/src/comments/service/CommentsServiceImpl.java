package comments.service;

import java.util.ArrayList;

import comments.db.CommentsBean;
import comments.db.CommentsDAOImpl;

public class CommentsServiceImpl implements CommentsService {
	CommentsDAOImpl cDao;
	
	public CommentsServiceImpl() {
		cDao = new CommentsDAOImpl();
	}
	
	//댓글 쓰기
	@Override
	public int insertComments(CommentsBean cBean) {
		int check = cDao.insertComments(cBean);
		return check;
	}
	
	//댓글 가져오기
	@Override
	public ArrayList<CommentsBean> selectCommentsList(CommentsBean cBean) {
		return cDao.selectCommentsList(cBean);
	}
	
	//댓글 삭제
	@Override
	public int commentsDelete(int co_no, String email) {
		return cDao.commentsDelete(co_no, email);
	}
}
