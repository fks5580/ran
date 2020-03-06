package question.service;

import java.util.ArrayList;
import java.util.Map;

import question.db.QuestionBean;

public interface QuestionService {

	//고객센터 글쓰기
	public int addQuestion(QuestionBean qBean);
	
	//고객센터 글수정
	public void modQuestion(QuestionBean qBean);
	
	//고객센터 글보기
	public QuestionBean questionView(int ques_no);
	
	//고객센터 글삭제
	public void questionDelete(int ques_no);
	
	//공지글 조회
	public Map listQuestion1();
	
	//일반글 조회 - 페이징 기능에 필요한 글목록과 전체 글 개수를 조회
	public Map listQuestion2(Map<String, Integer> pagingMap, String opt, String condition);
	
	//공지사항 글 개수
	public int countNotice();
	
	//조회수 증가
	public void updateReadcount(int ques_no);
	
	//고객센터 검색
	public ArrayList<QuestionBean> questionSelect(String opt,String condition);

	//고객센터 답글쓰기
	public int addReply(QuestionBean qBean);
	
	//부모글 내용 받아오기
	public QuestionBean getContent(int ques_no);

}