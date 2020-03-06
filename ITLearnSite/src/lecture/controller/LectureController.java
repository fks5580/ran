package lecture.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import lecture.db.CommentsBean;
import lecture.db.LectureBean;
import lecture.db.LectureDAOImpl;
import lecture.service.LectureServiceImpl;

public class LectureController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	LectureDAOImpl lDao = null;
	LectureBean lBean = null;
	LectureServiceImpl lServ = null;
	CommentsBean cBean = null;

	@Override
	public void init(ServletConfig sc) throws ServletException {
		lDao = new LectureDAOImpl();
		lBean = new LectureBean();
		lServ = new LectureServiceImpl();
		cBean = new CommentsBean();
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
			if (path.equals("/lectures.lec")) {
				nextPage = "/main.jsp";
				paging = "/pages/main/center/lecture/lectures.jsp";
				request.setAttribute("paging", paging);

			// ##########강의실 ########## Start
			} else if (path.equals("/lectureList.lec")) {
				String _section = request.getParameter("section");
				String _pageNum = request.getParameter("pageNum");

				int section = Integer.parseInt(((_section == null) ? "1" : _section));
				int pageNum = Integer.parseInt(((_pageNum == null) ? "1" : _pageNum));

				Map pagingMap = new HashMap();

				pagingMap.put("section", section);
				pagingMap.put("pageNum", pageNum);
				
				Map lecturesMap = lServ.listLecture(pagingMap);
				lecturesMap.put("section", section);
				lecturesMap.put("pageNum", pageNum);
				
				request.setAttribute("lecturesMap", lecturesMap);
				
				nextPage = "/main.jsp";
				paging = "/pages/main/center/lecture/lectureList.jsp";
				request.setAttribute("paging", paging);

			// ##########강의실 ########## End
			// ##########강의 등록 페이지 ########## Start
			}else if (path.equals("/lectureForm.lec")) {
				nextPage = "/main.jsp";
				paging = "/pages/main/center/lecture/lectureForm.jsp";
				request.setAttribute("paging", paging);
			// ##########강의 등록페이지 ########## End
	        // ##########강의 등록 ########## Start
			} else if (path.equals("/lectureRegister.lec")) {
				request.setCharacterEncoding("UTF-8");
				int max = 1000 * 1024 * 1024;
				String realFoldar = request.getServletContext().getRealPath("pages/main/center/lecture/temp");
				MultipartRequest multi = new MultipartRequest(request, realFoldar, max, "UTF-8",
						new DefaultFileRenamePolicy());

				ArrayList<String> list_title = new ArrayList<String>();
				ArrayList<String> saveFiles = new ArrayList<String>();
				ArrayList<String> originFiles = new ArrayList<String>();

				int cnt = Integer.parseInt(multi.getParameter("cnt"));

				for (int i = 1; i <= cnt; i++) {
					list_title.add(multi.getParameter("list_title" + i));
				}

				Enumeration e = multi.getFileNames();

				while (e.hasMoreElements()) {
					String InputName = (String) e.nextElement();

					// 서버에 업로드한 실제 파일명 받아오기
					String RealName = multi.getFilesystemName(InputName);
					saveFiles.add(RealName);

					// 클라이언트가 업로드한 파일의 원본이름을 얻어서.. 하나씩 ArrayList에 추가!
					originFiles.add(multi.getOriginalFileName(InputName));
				}

				Collections.sort(saveFiles);
				Collections.sort(originFiles);

				lBean.setList_title(list_title);
				lBean.setOriginFiles(originFiles);
				lBean.setSaveFiles(saveFiles);
				lBean.setLec_title(multi.getParameter("lec_title"));
				lBean.setLec_price(Integer.parseInt(multi.getParameter("lec_price")));
				lBean.setLec_teacher(multi.getParameter("lec_teacher"));
				lBean.setLec_content(multi.getParameter("lec_content"));

				lServ.lectureRegister(lBean);

				PrintWriter pw = response.getWriter();
				pw.print("<script>" + " alert('강의를 등록했습니다.');" + " location.href='" + request.getContextPath()
						+ "/lectureList.lec';" + "</script>");
				return;

			// ##########강의 등록 ########## End
	        // ##########강의 상세보기 ########## Start
			} else if (path.equals("/lectureDetail.lec")) {
				request.setCharacterEncoding("UTF-8");

				int lec_no = Integer.parseInt(request.getParameter("lec_no"));

				Map lec_DetailMap = lServ.lectureDetail(lec_no);

				request.setAttribute("lec_DetailMap", lec_DetailMap);

				nextPage = "/main.jsp";
				paging = "/pages/main/center/lecture/lectureDetail.jsp";
				request.setAttribute("paging", paging);
			// ##########강의 상세보기 ########## End
	        // ##########강의 삭제 ########## Start
			} else if (path.equals("/deleteLecture.lec")) {
				int lec_no = Integer.parseInt(request.getParameter("lec_no"));
				lServ.deleteLecture(lec_no);

				PrintWriter pw = response.getWriter();
				pw.print("<script>" + " alert('강의를 삭제했습니다.');" + " location.href='" + request.getContextPath()
						+ "/lectureList.lec';" + "</script>");
				return;
			// ##########강의 삭제 ########## End
	        // ##########내 강의실 ########## Start
			} else if (path.equals("/myLecture.lec")) {
				String email = (String) request.getSession().getAttribute("email");
				if (email == null) {
					PrintWriter out = response.getWriter();
					out.println("<script>");
					out.println("alert('로그인을 해주세요.');");
					out.println("history.back();");
					out.println("</script>");
					out.close();
				}else{
				System.out.println("myLecture.lec");

				List myList = lServ.myLecture(email);

				request.setAttribute("myList", myList);

				nextPage = "/main.jsp";
				paging = "/pages/main/center/lecture/myLecture.jsp";
				request.setAttribute("paging", paging);
				}
			// ##########강의 상세보기 ########## End
	        // ##########강의 재생 ########## Start	
			} else if (path.equals("/lecturePlay.lec")) {
				String lec_title = request.getParameter("lec_title");

				Map lec_DetailMap = lServ.lectureDetail(lec_title);

				request.setAttribute("lec_DetailMap", lec_DetailMap);
				nextPage = "/pages/main/center/lecture/lecturePlay.jsp";

			// ##########강의 등록 ########## End
	        // ##########강의 후기 댓글  작성########## Start
			}else if (path.equals("/commentsWrite.lec")) {
				int check = 0;
				cBean = getCommentsBeanProperty(request, response);
				check = lServ.insertComments(cBean);

				if (check == 1) {
					PrintWriter out = response.getWriter();
					out.print(1);
				}
			// ##########강의 후기 댓글 작성 ########## End
	        // ##########강의 후기 댓글 리스트 ########## Start	
			} else if (path.equals("/commentsList.lec")) {
				cBean = getCommentsBeanProperty(request, response);

				ArrayList<CommentsBean> list = lServ.selectCommentsList(cBean);

				JSONObject jsondata = new JSONObject();
				JSONArray arr = new JSONArray();
				String jsonString = null;
				Date date = null;

				for (int i = 0; i < list.size(); i++) {
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
			// ##########강의 후기 댓글 리스트 ########## End
	        // ##########강의 후기 댓글 삭제 ########## Start	
			} else if (path.equals("/commentsDelete.lec")) {
				cBean = getCommentsBeanProperty(request, response);
				HttpSession session = request.getSession();
				String email = (String) session.getAttribute("email");
				int check = lServ.commentsDelete(cBean.getCo_no(), email);
				PrintWriter out = response.getWriter();
				//성공한 경우
				if (check == 1) {
					out.print(1);
				//실패한 경우
				}else{
					out.print(0);
				}
			}
			// ##########강의 후기 댓글 삭제 ########## End
			
			if (nextPage != null) {
				RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
				dispatch.forward(request, response);
			}
		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}

	private CommentsBean getCommentsBeanProperty(HttpServletRequest request, HttpServletResponse response) {
		int co_no = 0;// 댓글 번호
		int list_no = 0;
		int lec_no = 0;
		String co_email = null;// 댓글 다는 사람
		String co_content = null;// 댓글 내용

		cBean = new CommentsBean();

		if (request.getParameter("co_no") != null) {
			co_no = Integer.parseInt(request.getParameter("co_no"));
			cBean.setCo_no(co_no);
		}
		if (request.getParameter("list_no") != null) {
			list_no = Integer.parseInt(request.getParameter("list_no"));
			cBean.setList_no(list_no);
		}
		if (request.getParameter("lec_no") != null) {
			lec_no = Integer.parseInt(request.getParameter("lec_no"));
			cBean.setLec_no(lec_no);
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
