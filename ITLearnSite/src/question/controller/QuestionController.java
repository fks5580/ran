package question.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import question.db.QuestionBean;
import question.db.QuestionDAOImpl;
import question.service.QuestionServiceImpl;

public class QuestionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	QuestionBean qBean = null;
	QuestionDAOImpl qDao = null;
	QuestionServiceImpl qServ = null;

	@Override
	public void init(ServletConfig sc) throws ServletException {
		qBean = new QuestionBean();
		qDao = new QuestionDAOImpl();
		qServ = new QuestionServiceImpl();
	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");

		String url = request.getRequestURI();
		String contextPath = request.getContextPath();
		String path = url.substring(contextPath.length());
		String nextPage = null;
		String paging = null;
		try {
            // ##########고객센터 리스트########## Start  
			if(path.equals("/questionList.ques")){
				String opt = request.getParameter("opt");
				String condition = request.getParameter("condition");
				String _section=request.getParameter("section");
				String _pageNum=request.getParameter("pageNum");
				String email = (String)request.getSession().getAttribute("email");
				
				int section = Integer.parseInt(((_section==null)? "1":_section) );
				int pageNum = Integer.parseInt(((_pageNum==null)? "1":_pageNum));
				
				Map pagingMap=new HashMap();
				
				pagingMap.put("section", section);
				pagingMap.put("pageNum", pageNum);
				
				Map questionsMap1=qServ.listQuestion1();
				Map questionsMap2=qServ.listQuestion2(pagingMap, opt, condition);
				
				questionsMap2.put("section", section);
				questionsMap2.put("pageNum", pageNum);
				questionsMap2.put("opt", opt);
				questionsMap2.put("condition", condition);
				
				int countNotice = qServ.countNotice();
				
				request.setAttribute("questionsMap1", questionsMap1);
				request.setAttribute("questionsMap2", questionsMap2);
				request.setAttribute("countNotice", countNotice);
				request.setAttribute("email", email);
				
				nextPage = "/main.jsp";
				paging = "/pages/main/center/question/questionList.jsp";
				request.setAttribute("paging", paging);
				
			// ##########고객센터 리스트########## End
	        // ##########문의글 내용보기########## Start  
			}else if(path.equals("/questionView.ques")){
				int ques_no = Integer.parseInt(request.getParameter("ques_no"));
				qServ.updateReadcount(ques_no);
				qBean = qServ.questionView(ques_no);
				request.setAttribute("qBean", qBean);	
				request.setAttribute("ques_no", ques_no);
				
				nextPage = "/main.jsp";
				paging= "/pages/main/center/question/questionView.jsp?ques_no="+ques_no;
				request.setAttribute("paging", paging);	
			// ##########문의 글 내용보기########## End
	        // ##########문의 글 작성 페이지 이동########## Start  
			}else if(path.equals("/questionWrite.ques")){
				nextPage = "/main.jsp";
				paging = "/pages/main/center/question/questionWrite.jsp";
				request.setAttribute("paging", paging);
			// ##########문의 글 작성 페이지 이동########## End
	        // ##########문의 글 작성########## Start
			}else if(path.equals("/addQuestion.ques")){
				qBean = getQuestionBeanProperty(request, response);
				int ques_no = 0;
				ques_no = qServ.addQuestion(qBean);
				PrintWriter pw = response.getWriter();
				pw.print("<script>" + "  alert('글쓰기를 완료했습니다.');" + " location.href='" + 
				"questionList.ques';" + "</script>");
				return;
			// ##########문의 글 작성########## End
	        // ##########문의 글 수정 페이지 이동########## Start
			}else if(path.equals("/questionModify.ques")){
				int ques_no = Integer.parseInt(request.getParameter("ques_no"));
				
				qBean = qServ.questionView(ques_no);
				request.setAttribute("qBean", qBean);
				nextPage = "/main.jsp";
				paging= "/pages/main/center/question/questionModify.jsp?ques_no="+ques_no;
				request.setAttribute("paging", paging);	
			// ##########문의 글 수정 페이지 이동########## End
	        // ##########문의 글 수정########## Start
			}else if(path.equals("/updateQuestion.ques")){

				qBean = getQuestionBeanProperty(request, response);
				int ques_no = qBean.getQues_no();
				qServ.modQuestion(qBean);
				
				PrintWriter pw = response.getWriter();
				pw.print("<script>" + "  alert('수정되었습니다.');" + " location.href='" 
				+"questionView.ques?ques_no="+ques_no+"';" + "</script>");
				return;
			// ##########문의 글 수정########## End
            // ##########문의 글 검색########## Start
			}else if(path.equals("/questionSelect.ques")){
				String select_subject = request.getParameter("select_subject");
				String select_content = request.getParameter("select_content");
				
				List<QuestionBean> ResourceList = qServ.questionSelect(select_subject,select_content);
				request.setAttribute("ResourceList", ResourceList);
				nextPage = "/main.jsp";
				paging= "/pages/main/center/quesiton/questionSelect.jsp";
				request.setAttribute("paging", paging);
			// ##########문의 글 검색########## End
            // ##########문의 글 삭제########## Start
		    }else if(path.equals("/questionDelete.ques")){ 
				System.out.println("questionDelete.ques");				
				int ques_no = Integer.parseInt(request.getParameter("ques_no"));				
				qServ.questionDelete(ques_no);				
				
				PrintWriter pw = response.getWriter();
				pw.print("<script>" + " alert('글을 삭제했습니다.');" 
				         + " location.href='questionList.ques'" + "</script>");
				return;
			// ##########문의 글 삭제########## End
	        // ##########문의 글 답글 작성 페이지 이동########## Start
		    }else if(path.equals("/questionReply.ques")){
				String ques_parentemail = request.getParameter("ques_parentemail");
				int ques_no = Integer.parseInt(request.getParameter("ques_no"));
				
				qBean = getQuestionBeanProperty(request, response);
				qServ.getContent(ques_no);
				qBean.setQues_parentemail(ques_parentemail);
				request.setAttribute("qBean", qBean);
				
				nextPage = "/main.jsp";
				paging= "/pages/main/center/question/questionReply.jsp";
				request.setAttribute("paging", paging);
			// ##########문의 글 답글 작성 페이지 이동########## End
            // ##########문의 글 답글 작성########## Start
		    }else if(path.equals("/addReply.ques")){
				qBean = getQuestionBeanProperty(request, response);
				int ques_no = qBean.getQues_no();
				qBean.setQues_parentno(ques_no);
				qBean.setQues_ref(ques_no);
				
				ques_no = qServ.addReply(qBean);
				PrintWriter pw = response.getWriter();
				pw.print("<script>" + "  alert('답글쓰기를 완료했습니다.');" + " location.href='" + 
				"questionList.ques';" + "</script>");
				return;
			}	
            // ##########문의 글 답글 작성########## End
			
			if (nextPage != null) {
				RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
				dispatch.forward(request, response);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
			

	private QuestionBean getQuestionBeanProperty(HttpServletRequest request, HttpServletResponse response) {
		int ques_no = 0;
		int ques_parentno = 0;
		String ques_title = null;
		String ques_email = null;
		String ques_content = null;
		int ques_readcount = 0;
		String isSecret = "n";
		String isNotice = "n";
		int ques_ref = 0;
		
		if(request.getParameter("ques_no") != null){
			ques_no = Integer.parseInt(request.getParameter("ques_no"));
			qBean.setQues_no(ques_no);
		}
		if(request.getParameter("ques_parentno") != null){
			ques_parentno = Integer.parseInt(request.getParameter("ques_no"));
			qBean.setQues_parentno(ques_parentno);
		}
		if(request.getParameter("ques_title") != null){
			ques_title = request.getParameter("ques_title");
			qBean.setQues_title(ques_title);
		}
		if(request.getParameter("ques_email") != null){
			ques_email = request.getParameter("ques_email");
			qBean.setQues_email(ques_email);
		}
		if(request.getParameter("ques_content") != null){
			ques_content = request.getParameter("ques_content");
			qBean.setQues_content(ques_content);
		}
		if(request.getParameter("ques_readcount") != null){
			ques_readcount = Integer.parseInt(request.getParameter("ques_readcount"));
			qBean.setQues_readcount(ques_readcount);
		}
		if(request.getParameter("isSecret") != null){
			isSecret = request.getParameter("isSecret");
			qBean.setIsSecret("y");
		}
		if(request.getParameter("isSecret") == null){
			isSecret = request.getParameter("isSecret");
			qBean.setIsSecret("n");
		}
		if(request.getParameter("isNotice") != null){
			isNotice = request.getParameter("isNotice");
			qBean.setIsNotice("y");
		}
		if(request.getParameter("isNotice") == null){
			isNotice = request.getParameter("isNotice");
			qBean.setIsNotice("n");
		}
		if(request.getParameter("ques_ref") != null){
			ques_ref = Integer.parseInt(request.getParameter("ques_ref"));
			qBean.setQues_ref(ques_ref);
		}
		return qBean;
	}
}