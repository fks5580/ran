package lecture.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lecture.db.CommentsBean;
import lecture.db.LectureBean;
import lecture.db.LectureDAOImpl;
import lecture.db.PaylecBean;

public class LectureServiceImpl implements LectureService {

	LectureDAOImpl lDao;

	public LectureServiceImpl() {
		lDao = new LectureDAOImpl();
	}

	//강의실 목록
	@Override
	public Map listLecture(Map<String, Integer> pagingMap) {
		Map lecturesMap = new HashMap();
		List<LectureBean> lecturesList = lDao.selectAllLectures(pagingMap);
		int totLectures = lDao.selectTotLectures();
		lecturesMap.put("lecturesList", lecturesList);
		lecturesMap.put("totLectures", totLectures);		
		return lecturesMap;
	}

	//강의 등록
	@Override
	public void lectureRegister(LectureBean lBean) {
		lDao.lectureRegister(lBean);
	}

	//강의 상세보기
	@Override
	public Map lectureDetail(int lec_no) {
		Map lec_DetailMap = new HashMap();
		LectureBean lec_Detail = lDao.lectureDetail(lec_no);
		List<LectureBean> lec_list = lDao.lectureList(lec_no);
		lec_DetailMap.put("lec_Detail", lec_Detail);
		lec_DetailMap.put("lec_list", lec_list);
		return lec_DetailMap;
	}
	
	//강의 삭제
	@Override
	public void deleteLecture(int lec_no) {
		lDao.deleteLecture(lec_no);
	}

	//내 강의실
	@Override
	public List myLecture(String email) {
		List myList = lDao.myLecture(email);
		return myList;
	}

	//강의 정보
	@Override
	public Map lectureDetail(String lec_title) {
		Map lec_DetailMap = new HashMap();
		LectureBean lec_Detail = lDao.lectureDetail(lec_title);
		List<LectureBean> lec_list = lDao.lectureList(lec_title);
		lec_DetailMap.put("lec_Detail", lec_Detail);
		lec_DetailMap.put("lec_list", lec_list);
		return lec_DetailMap;
	}

	//댓글 작성
	@Override
	public int insertComments(CommentsBean cBean) {
		int check = lDao.insertComments(cBean);
		return check;
	}

	//댓글 가져오기
	@Override
	public ArrayList<CommentsBean> selectCommentsList(CommentsBean cBean) {
		return lDao.selectCommentsList(cBean);
	}

	//댓글 삭제
	@Override
	public int commentsDelete(int co_no, String email) {
		return lDao.commentsDelete(co_no, email);
	}
}
