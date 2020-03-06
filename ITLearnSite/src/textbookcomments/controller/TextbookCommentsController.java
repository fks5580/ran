package textbookcomments.controller;

import java.awt.print.Book;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.mysql.cj.Session;

import comments.db.CommentsBean;
import comments.db.CommentsDAOImpl;
import comments.service.CommentsServiceImpl;
import textbookcomments.db.TextbookCommentsBean;
import textbookcomments.db.TextbookCommentsDAOImpl;
import textbookcomments.service.TextbookCommentsServiceImpl;
public class TextbookCommentsController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	TextbookCommentsServiceImpl tcServ = null;
	TextbookCommentsDAOImpl tcDao = null;
	TextbookCommentsBean tcBean = null;

	int result = 0; // 상태를 나타낼 변수

	@Override
	public void init(ServletConfig sc) throws ServletException {
		tcServ = new TextbookCommentsServiceImpl();
		tcDao = new TextbookCommentsDAOImpl();
		tcBean = new TextbookCommentsBean();
	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		String url = request.getRequestURI();
		String contextPath = request.getContextPath();
		String path = url.substring(contextPath.length());
		String nextPage = null;

		
		try {
		    // ##########댓글 작성########## Start
			if(path.equals("/TextbookCommentsWrite.tbc"))
			{
				int check = 0;	
				//현재글에 대한 정보를 얻어오기
				tcBean=getTextbookCommentsBeanProperty(request, response);
				check = tcServ.insertTextbookComments(tcBean);
				if(check == 1)
				{
					PrintWriter out = response.getWriter();
					out.print(1);
				}
			}
			// #########댓글 작성########## End
			// ##########댓글 리스트########## Start
			else if(path.equals("/TextbookCommentsList.tbc"))
			{
				tcBean=getTextbookCommentsBeanProperty(request, response);
				
				 ArrayList<TextbookCommentsBean> list = tcServ.selectTextbookCommentsList(tcBean);
				 
				 JSONObject jsondata = new JSONObject();
				 JSONArray arr = new JSONArray();
				 String jsonString = null;
				 Date date = null;
				 for(int i = 0; i< list.size(); i++)
				 {					
					jsondata = new JSONObject();
					jsondata.put("bo_email", list.get(i).getBo_email());
					jsondata.put("bo_no", list.get(i).getBo_no());
					jsondata.put("bo_content", list.get(i).getBo_content());
					jsondata.put("bo_evaluation", list.get(i).getBo_evaluation());
					date = list.get(i).getBo_date();
					jsondata.put("bo_date", date.toString());
					arr.add(jsondata);
				 }
				 JSONObject commentlist = new JSONObject();
				 commentlist.put("list", arr);	 
				 jsonString = commentlist.toJSONString();
				
				 PrintWriter out = response.getWriter();
				 out.print(jsonString);
			// #########댓글 리스트########## End
            // ##########댓글 삭제########## Start
		    }else if(path.equals("/TextbookCommentsDelete.tbc")){
				int bo_no = Integer.parseInt(request.getParameter("bo_no"));
				HttpSession session= request.getSession();
				String email = (String)session.getAttribute("email");
				
				int check = tcServ.TextbookcommentsDelete(bo_no, email);
				PrintWriter out = response.getWriter();
				if(check == 1)
				{
					//지우기 성공
					out.print(1);
				}
				else {
					//지우기 실패 다른사람 글 지우려고 함	
					out.print(0);
				}
			}
			// #########댓글 삭제########## End
			// null PointException
			if (nextPage != null) {
				RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
				dispatch.forward(request, response);			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private TextbookCommentsBean getTextbookCommentsBeanProperty(HttpServletRequest request, HttpServletResponse response) {
		int bo_no = 0;//후기 번호
		int product_no = 0;//부모 교재 번호
		String bo_email = null;//후기 다는 사람
		String bo_content = null;//후기 내용
		int bo_evaluation = 0;//평점
		
		tcBean = new TextbookCommentsBean();
		
		if (request.getParameter("bo_no") != null) {
			bo_no = Integer.parseInt(request.getParameter("bo_no"));
			tcBean.setBo_no(bo_no);
		}
		if (request.getParameter("product_no") != null) {
			product_no = Integer.parseInt(request.getParameter("product_no"));
			tcBean.setProduct_no(product_no);
		}
		if (request.getParameter("bo_email") != null) {
			bo_email = request.getParameter("bo_email");
			tcBean.setBo_email(bo_email);
		}
		if (request.getParameter("bo_content") != null) {
			bo_content = request.getParameter("bo_content");
			tcBean.setBo_content(bo_content);
		}
		if (request.getParameter("bo_evaluation") != null) {
			bo_evaluation = Integer.parseInt(request.getParameter("bo_evaluation"));
			tcBean.setBo_evaluation(bo_evaluation);
		}
		
		return tcBean;
	}
}
