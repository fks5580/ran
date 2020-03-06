package member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.cj.Session;

import member.db.MemberBean;
import member.db.MemberDAOImpl;
import member.email.JoinMail;
import member.service.MemberServiceImpl;
import sun.security.action.GetIntegerAction;
import sun.security.jca.GetInstance;
import sun.security.provider.NativePRNG.NonBlocking;

public class MemberController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	MemberServiceImpl serv = null;
	MemberDAOImpl dao = null;
	MemberBean mBean = null;

	int result = 0; // 상태를 나타낼 변수

	@Override
	public void init(ServletConfig sc) throws ServletException {
		serv = new MemberServiceImpl();
		mBean = new MemberBean();
	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String url = request.getRequestURI();
		String contextPath = request.getContextPath();
		String path = url.substring(contextPath.length());
		String nextPage = null;
		String paging = null;
		
		try {
		    // ##########메인페이지########## Start
			if (path == null) {
				nextPage = "/main.jsp";
			}else if (path.equals("/index.do")){
				nextPage = "/main.jsp";
				paging = "/pages/main/center/default.jsp";
				request.setAttribute("paging", paging);
			
			// ##########메인페이지########## End
			// ##########회원가입 페이지 이동########## Start
		    }else if (path.equals("/joinMember.do")){
				nextPage = "/main.jsp";
				paging = "/pages/main/center/member/join.jsp";
				request.setAttribute("paging", paging);
			
			// ##########회원가입 페이지 이동########## End
	        // ##########회원 중복확인########## Start
		    }else if (path.equals("/emailDupChk.do")){
				mBean = getMemberBeanProperty(request, response);
				result = serv.emailDupChk(mBean);
				PrintWriter out = response.getWriter();
				//회원가입 성공
				if (result == 1){
					out.print(1);
				//회원가입 실패
				}else if (result == 0){
					out.print(0);
				}
			// ##########회원 중복확인########## End
            // ##########회원 가입########## Start
		    }else if (path.equals("/insertMember.do")){
			
				mBean = getMemberBeanProperty(request, response);
				result = serv.InsertMember(mBean);
				//이메일 전송 쓰레드 이메일 보내는것이 오래걸리기 때문에 페이지가 대기하는것을 해결(BLOCKING방지)
				String message = "<a href = " + request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/emailAuth.do?email="+mBean.getEmail()+">" +"링크"+ "</a>";
				JoinMail mail = new JoinMail(mBean.getEmail(), message);
				
				ExecutorService executorService = Executors.newCachedThreadPool();
				
				Future<Double> future = (Future<Double>) executorService.submit(() -> {
					try {
					    //10초 정도 소요
						mail.sendMail();
					} catch (UnsupportedEncodingException | MessagingException e) {
						e.printStackTrace();
					}
				});
				nextPage = "/main.jsp";
				paging = "/pages/main/center/member/joinSuccess.jsp";
				request.setAttribute("paging", paging);
			
			// ##########회원 가입########## End
            // ##########이메일 인증########## Start
		    }else if(path.equals("/emailAuth.do")){
				//이메일로 온 링크 클릭시 나오는 주소에 email=? 파라미터 값
				String email = request.getParameter("email");
				serv.emailAuth(email);
				
				nextPage = "/pages/main/center/member/emailAuthSuccess.jsp";
			
			// ##########이메일 인증########## End
	
			// ##########로그인 페이지 이동########## Start
		    }else if (path.equals("/login.do")){
				nextPage = "/main.jsp";
				paging = "/pages/main/center/member/login.jsp";
				request.setAttribute("paging", paging);
			// ##########로그인 페이지 이동########## End
			// ##########로그인########## Start	
			}else if (path.equals("/login1.do")){
				String email = request.getParameter("email");
				String pw = request.getParameter("pw");

				MemberDAOImpl mDao = MemberDAOImpl.getInstance();
				int loginResult = mDao.login(email, pw);
				
				//로그인 성공
				if (loginResult == 1) {
					request.setAttribute("loginResult", loginResult);
					HttpSession session = request.getSession();
					session.setAttribute("email", email);
					session.setAttribute("loginAuth", "normal");
					
					nextPage = "/main.jsp";
					paging = "/pages/main/center/default.jsp";
					request.setAttribute("paging", paging);
				 
				//로그인 실패(비밀번호x)
			    }else if(loginResult == 0){
					request.setAttribute("loginResult", loginResult);
					nextPage = "/login.do";
				
				//로그인 실패(이메일 인증x)
			    }else if(loginResult == -1){
					request.setAttribute("loginResult", loginResult);
					nextPage = "/login.do";
				}
			// ##########로그인########## End
	        // ##########네이버 로그인########## Start    
			}else if (path.equals("/nvlogin.do"))
			{
				mBean.setEmail(request.getParameter("email"));
				mBean.setName(request.getParameter("name"));
				
				int gen=0;
				if(request.getParameter("gender")!=null){
				    //성별 남자
					if(request.getParameter("gender").equals("M")){
						gen = 1;
						mBean.setGender(gen);
					//성별 여자
					}else{
						gen = 2;
						mBean.setGender(gen);
					}
				//성별이 설정되어 있지 않은 경우 남자로 설정	
				}else{
					gen = 1;
					mBean.setGender(gen);
				}
				int check = serv.emailDupChk(mBean);
				
				//실패(이메일 중복)
				if(check == 1){
					PrintWriter out = response.getWriter();
					out.print(1);
					HttpSession session = request.getSession();
					session.setAttribute("email", mBean.getEmail());
					session.setAttribute("loginAuth", "naver");
					return;
				//성공(이메일 중복x)
				}else {
					PrintWriter out = response.getWriter();
					out.print(0);
					result = serv.InsertMember(mBean);
					HttpSession session = request.getSession();
					session.setAttribute("email", mBean.getEmail());
					session.setAttribute("loginAuth", "naver");
					return;
				}   	
			}else if(path.equals("/callback.do")){
				nextPage = "/main.jsp";
				paging = "/pages/main/center/member/callback.jsp";
				request.setAttribute("paging", paging);
			}else if(path.equals("/callbackModify.do")){
				nextPage = "/main.jsp";
				paging = "/pages/main/center/member/callbackModify.jsp";
				request.setAttribute("paging", paging);
			}else if(path.equals("/naverModifylogin.do")){
				
				HttpSession session = request.getSession();
				String email = (String) session.getAttribute("email");
				if(email.equals(session.getAttribute("email")))
				{
					PrintWriter out = response.getWriter();
					out.print("success");
				}
				
			}else if(path.equals("/naverModify.do")){
				HttpSession session = request.getSession();
				String email = (String) session.getAttribute("email");
				mBean = serv.callMember(email);
				
				request.setAttribute("mBean", mBean);
				nextPage = "/main.jsp";
				paging = "/pages/main/center/member/naverModify.jsp?email="+email;
				request.setAttribute("paging", paging);
			// ##########네이버 로그인####### End
            // ##########로그아웃########## Start    
		    }else if (path.equals("/logout.do")){
				HttpSession session = request.getSession();
				System.out.println("로그아웃되었습니다.");
				session.invalidate();
				nextPage = "/index.jsp";
			
			// ##########로그아웃########## END
			// ##########회원탈퇴 ############## Start
		    }else if (path.equals("/MemberDeleteAction.do")){
				nextPage = "/pages/main/center/member/memberDelete.jsp";
			}else if (path.equals("/MemberDeleteAction1.do")){
				HttpSession session = request.getSession();
				String email = (String) session.getAttribute("email");
				int check = serv.deleteMember(email);
				//탈퇴 성공
				if (check != 0){
					session = request.getSession();
					session.invalidate();
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.println("<script>");
					out.println("alert('탈퇴되었습니다');");
					out.println("window.location.href='/ITLearnSite/index.do'");
					out.println("</script>");
					out.close();
				//탈퇴 실패
			    }else{
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.println("<script>");
					out.println("alert('탈퇴실패');");
					out.println("history.back();");
					out.println("</script>");
					out.close();
					nextPage = "/main.jsp";
				}
			// ##########회원탈퇴 ############## End
			// ##########회원수정 페이지 이동 ############## Start
		    }else if(path.equals("/relogin.do")){
				nextPage = "/main.jsp";
				paging = "/pages/main/center/member/relogin.jsp";
				request.setAttribute("paging", paging);
			// ##########회원수정 페이지 이동########## End
	        // ##########회원수정위해 비밀번호 확인 페이지 이동########## Start    
		    }else if (path.equals("/relogin1.do")) 
			{
				HttpSession session = request.getSession();
				String email = (String)session.getAttribute("email");
				String pw = request.getParameter("pw");

				MemberDAOImpl mDao = MemberDAOImpl.getInstance();
				int loginResult = mDao.login(email, pw);
				//비밀번호 확인 성공
				if (loginResult == 1){
					request.setAttribute("loginResult", loginResult);
					nextPage = "/main.jsp";
					paging = "/pages/main/center/member/modify.jsp";
					request.setAttribute("paging", paging);
					mBean = serv.callMember(email);
					request.setAttribute("mBean", mBean);
				//비밀번호 확인 실패
				}else if(loginResult == 0){
					request.setAttribute("loginResult", loginResult);
					nextPage = "/relogin.do";
				}
			// ##########회원수정위해 비밀번호 확인 페이지 이동########## End	
			// ##########회원정보 수정########## Start 	
			}else if(path.equals("/UpdateMember.do")){
					mBean=getMemberBeanProperty(request, response);
					int check = serv.updateMember(mBean);
					//회원정보 수정 성공
					if(check == 1){
						response.setContentType("text/html; charset=utf-8");
						PrintWriter out = response.getWriter();
						out.println("<script>");
						out.println("alert('수정되었습니다.');");
						out.println("</script>");

	                    nextPage="/main.jsp";
	                    paging = "/pages/main/center/default.jsp";
	                    request.setAttribute("paging", paging);
	                    HttpSession session = request.getSession();
	                    session.invalidate();
					//회원정보 수정 실패	
					}else{
					    response.setContentType("text/html; charset=utf-8");
                        PrintWriter out = response.getWriter();
                        out.println("<script>");
                        out.println("alert('수정실패하였습니다.');");
                        out.println("history.back();");
                        out.println("</script>");
                        out.close();  
					}
			}
			// ##########회원정보 수정########## End	
			// null PointException
			if (nextPage != null) 
			{
				RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
				dispatch.forward(request, response);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private MemberBean getMemberBeanProperty(HttpServletRequest request, HttpServletResponse response) {
		String email = null;
		String pw = null;
		String name = null;
		int gender = 0;
		String gender1 = null;
		String birth_year = null;
		String birth_month = null;
		String birth_day = null;
		String phonenumber = null;
		String address = null;
		String address1 = null;
		String address2 = null;
		int sms = 0;
		mBean = new MemberBean();
		if (request.getParameter("email") != null) {
			email = request.getParameter("email");
			mBean.setEmail(email);
		}
		if (request.getParameter("pw1") != null) {
			pw = request.getParameter("pw1");
			mBean.setPw(pw);
		}
		if (request.getParameter("name") != null) {
			name = request.getParameter("name");
			mBean.setName(name);
		}
		if(request.getParameter("gender") != null)
		{
				gender = Integer.parseInt(request.getParameter("gender"));
				mBean.setGender(gender);
		}
		if (request.getParameter("birth_year") != null) {
			birth_year = request.getParameter("birth_year");
			mBean.setBirth_year(birth_year);
		}
		if (request.getParameter("birth_month") != null) {
			birth_month = request.getParameter("birth_month");
			mBean.setBirth_month(birth_month);
		}
		if (request.getParameter("birth_day") != null) {
			birth_day = request.getParameter("birth_day");
			mBean.setBirth_day(birth_day);
		}
		if (request.getParameter("phonenumber") != null) {
			phonenumber = request.getParameter("phonenumber");
			mBean.setPhonenumber(phonenumber);
		}
		if (request.getParameter("address") != null) {
			address = request.getParameter("address");
			mBean.setAddress(address);
		}
		if (request.getParameter("address1") != null) {
			address1 = request.getParameter("address1");
			mBean.setAddress1(address1);
		}
		if (request.getParameter("address2") != null) {
			address2 = request.getParameter("address2");
			mBean.setAddress2(address2);
		}
		if ((request.getParameter("sms")) != null) {
			sms = Integer.parseInt(request.getParameter("sms"));
			mBean.setSms(sms);
		}
		return mBean;
	}
}
