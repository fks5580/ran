package lecture.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface LectureDAO {

	//강의실 목록 
	public List selectAllLectures(Map pagingMap);

	//전체 글 개수
	public int selectTotLectures();

	//강의 등록 
	public void lectureRegister(LectureBean lBean);

	//강의 정보 
	public LectureBean lectureDetail(int lec_no);

	public LectureBean lectureDetail(String lec_title);

	//강의 상세 리스트(파일 저장 테이블) //
	public List lectureList(int lec_no);

	public List lectureList(String lec_title);

	//강의 삭제 
	public void deleteLecture(int lec_no);

	//내 강의실 
	public List myLecture(String email);

	//댓글 작성
	public int insertComments(CommentsBean cBean);

	//댓글 번호
	public int getCommentsNo();

	//댓글 가져오기
	public ArrayList<CommentsBean> selectCommentsList(CommentsBean cBean);

	//댓글 삭제
	public int commentsDelete(int co_no, String co_email);

}
