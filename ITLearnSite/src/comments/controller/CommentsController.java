package comments.controller;

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
public class CommentsController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	CommentsServiceImpl cServ = null;
	CommentsDAOImpl cDao = null;
	CommentsBean cBean = null;

	int result = 0; // 상태를 나타낼 변수

	@Override
	public void init(ServletConfig sc) throws ServletException {
		cServ = new CommentsServiceImpl();
		cDao = new CommentsDAOImpl();
		cBean = new CommentsBean();
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
			if(path.equals("/commentsWrite.co")){
				int check = 0;
				cBean=getCommentsBeanProperty(request, response);
				check = cServ.insertComments(cBean);
				
				if(check == 1){
					PrintWriter out = response.getWriter();
					out.print(1);
				}
			// ##########댓글 작성########## End
			// ##########댓글 리스트########## Start
			}else if(path.equals("/commentsList.co")){
				cBean = getCommentsBeanProperty(request, response);
				ArrayList<CommentsBean> list = cServ.selectCommentsList(cBean);

				JSONObject jsondata = new JSONObject();
				JSONArray arr = new JSONArray();
				String jsonString = null;
				Date date = null;
 
				for(int i = 0; i< list.size(); i++){
					jsondata = new JSONObject();
					jsondata.put("co_email", list.get(i).getCo_email());
					jsondata.put("co_no", list.get(i).getCo_no());
					jsondata.put("co_content", list.get(i).getCo_content());
					date = list.get(i).getCo_date();
					jsondata.put("co_date", date.toString());
					arr.add(jsondata);
				 }
				 JSONObject commentlist = new JSONObject();
				 commentlist.put("list", arr);
				 jsonString = commentlist.toJSONString();
				 PrintWriter out = response.getWriter();
				 out.print(jsonString);
			// ##########댓글 리스트########## End
		    // ##########댓글 삭제########## Start		 
			}else if(path.equals("/commentsDelete.co")){
				cBean = getCommentsBeanProperty(request, response);
				HttpSession session= request.getSession();
				String email = (String)session.getAttribute("email");
				int check = cServ.commentsDelete(cBean.getCo_no(), email);
				PrintWriter out = response.getWriter();
				if(check == 1)
				{
					out.print(1);
				}
				else {
					out.print(0);
				}
			}
			// ##########댓글 삭제########## End
			// null PointException
			if (nextPage != null) {
				RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
				dispatch.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private CommentsBean getCommentsBeanProperty(HttpServletRequest request, HttpServletResponse response) {
		int co_no = 0;//댓글 번호
		int res_no = 0;//부모글 번호
		String co_email = null;//댓글 다는 사람
		String co_content = null;//댓글 내용

		cBean = new CommentsBean();
		
		if (request.getParameter("co_no") != null) {
			co_no = Integer.parseInt(request.getParameter("co_no"));
			cBean.setCo_no(co_no);
		}
		if (request.getParameter("res_no") != null) {
			res_no = Integer.parseInt(request.getParameter("res_no"));
			cBean.setRes_no(res_no);
		}
		if (request.getParameter("co_email") != null) {
			co_email = request.getParameter("co_email");
			cBean.setCo_email(co_email);
		}
		
		if (request.getParameter("content") != null) {
			co_content = request.getParameter("content");
			cBean.setCo_content(co_content);
		}
		return cBean;
	}
}
