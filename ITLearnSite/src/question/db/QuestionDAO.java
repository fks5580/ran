package question.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface QuestionDAO{
	
	//글 등록
	public int insertQuestion(QuestionBean qBean);
	
	//글 수정
	public void updateQuestion(QuestionBean qBean);
	
	//글 삭제
	public void questionDelete(int ques_no);
	
	//글 목록- 공지글 
	public List selectNotice();
	
	//글 목록- 일반글 
	public List selectQuestions(Map pagingMap,  String opt, String condition);
	
	//공지사항 제외 글 개수
	public int selectTotQuestions(String opt, String condition);
	
	//공지사항 글 개수
	public int countNotice();
	
	//글 내용
	public QuestionBean questionView(int ques_no);
	
	//조회수 증가
	public void updateReadcount(int ques_no);
	
	//글 검색
	public ArrayList<QuestionBean> questionSelect(HashMap<String, Object> listOpt);
	
	//답글 작성 시 부모글 내용 받아오기
	public QuestionBean getContent(int ques_no);
	
	
}