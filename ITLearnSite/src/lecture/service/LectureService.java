package lecture.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lecture.db.CommentsBean;
import lecture.db.LectureBean;


public interface LectureService {

	//강의실 목록- 페이징 기능에 필요한 글목록과 전체 글 개를 각각 조회할 수 있도록 구성
	public Map listLecture(Map<String, Integer> pagingMap);

	//강의 등록
	public void lectureRegister(LectureBean lBean);

	//강의 상세보기
	public Map lectureDetail(int lec_no);

	//강의 삭제
	public void deleteLecture(int lec_no);

	//내 강의실
	public List myLecture(String email);

	//강의 정보
	public Map lectureDetail(String lec_title);

	//댓글 작성
	public int insertComments(CommentsBean cBean);

	//댓글 가져오기
	public ArrayList<CommentsBean> selectCommentsList(CommentsBean cBean);

	//댓글 삭제
	public int commentsDelete(int co_no, String co_email);

}
