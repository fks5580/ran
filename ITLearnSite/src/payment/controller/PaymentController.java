package payment.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cart.db.CartBean;
import cart.service.CartServiceImpl;
import member.db.MemberBean;
import member.db.MemberDAOImpl;
import member.service.MemberServiceImpl;
import payment.db.PaymentBean;
import payment.db.PaymentDAOImpl;
import payment.service.PaymentServiceImpl;
import textbook.controller.TextbookController;

public class PaymentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PaymentServiceImpl pServ = null;
	PaymentBean pBean = null;
	MemberBean mBean = null;
	MemberServiceImpl mServ = null;
	ArrayList<CartBean> cartlist = null;
	CartServiceImpl caServ = null;

	@Override
	public void init(ServletConfig sc) throws ServletException {	
		pServ = new PaymentServiceImpl();
		pBean = new PaymentBean();
		mBean = new MemberBean();
		mServ = new MemberServiceImpl();
		caServ = new CartServiceImpl();
	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

		String url = request.getRequestURI();
		String contextPath = request.getContextPath();
		String path = url.substring(contextPath.length());
		String nextPage = null;
		String paging = null;
		HttpSession session = null;
		try {
            // ##########주문페이지########## Start
			if(path.equals("/payment.pay")){
				session = request.getSession();
				String email = (String)session.getAttribute("email");
				mBean = mServ.callMember(email);
				request.setAttribute("mBean", mBean);
				
				cartlist = caServ.getcartlist(email);
				request.setAttribute("cartlist", cartlist);
				
				int check = pServ.cartnullChk(email);
				//주문페이지 이동 성공
				if(check==1){
					nextPage = "/main.jsp";
					paging = "/pages/main/center/payment/payment.jsp";
					request.setAttribute("paging", paging);
				//주문페이지 이동 실패(장바구니 상품x)
				}else{
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.println("<script>");
					out.println("alert('장바구니가 비었습니다.');");
					out.println("window.location.href='/ITLearnSite/cart.cart'");
					out.println("</script>");
					out.close();
				}
			// ##########주문페이지########## End
			// ##########주문########## Start
			}else if(path.equals("/payment1.pay")){
				pBean = getPaymentBeanProperty(request, response);
				session = request.getSession();
				String email = (String)session.getAttribute("email");
				String pro_name;
				int result=0;
				int product_no;
				int book_stock;
				//구분이 강의라면 강의 이름을 가지고 와서 강의가 존재하는지 알아보기						
				if(pBean.getPay_pro1_sort()!=null && pBean.getPay_pro1_sort().equals("강의")){
					pro_name = pBean.getPay_pro1_name();
					result = pServ.payDupChk(pro_name,email);
				}
				if(pBean.getPay_pro2_sort()!=null && pBean.getPay_pro2_sort().equals("강의")){
					pro_name = pBean.getPay_pro2_name();
					result = pServ.payDupChk(pro_name,email);
				}
				if(pBean.getPay_pro3_sort()!=null && pBean.getPay_pro3_sort().equals("강의")){
					pro_name = pBean.getPay_pro3_name();
					result = pServ.payDupChk(pro_name,email);
				}
				if(pBean.getPay_pro4_sort()!=null && pBean.getPay_pro4_sort().equals("강의")){
					pro_name = pBean.getPay_pro4_name();
					result = pServ.payDupChk(pro_name,email);
				}
				if(pBean.getPay_pro5_sort()!=null && pBean.getPay_pro5_sort().equals("강의")){
					pro_name = pBean.getPay_pro5_name();
					result = pServ.payDupChk(pro_name,email);
				}
				//주문실패(주문한 강의가 존재할 경우)
				if(result==1){
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.println("<script>");
					out.println("alert('이미 주문한 강의가 존재합니다.');");
					out.println("window.location.href='/ITLearnSite/index.do'");
					out.println("</script>");
					out.close();
				//주문 성공
				}else{
					pServ.insertPayment(pBean);
					//재고 줄이기
			        if(pBean.getPay_pro1_name()!=null && pBean.getPay_pro1_sort().equals("도서")){      	
			        	product_no = pBean.getPay_pro1_no();
			        	book_stock = pBean.getPay_pro1_cnt();
			        	pServ.deleteBookstock(product_no,book_stock);
			        }
			        if(pBean.getPay_pro2_name()!=null && pBean.getPay_pro2_sort().equals("도서")){      	
			        	product_no = pBean.getPay_pro2_no();
			        	book_stock = pBean.getPay_pro2_cnt();
			        	pServ.deleteBookstock(product_no,book_stock);
			        }
			        if(pBean.getPay_pro3_name()!=null && pBean.getPay_pro3_sort().equals("도서")){      	
			        	product_no = pBean.getPay_pro3_no();
			        	book_stock = pBean.getPay_pro3_cnt();
			        	pServ.deleteBookstock(product_no,book_stock);
			        }
			        if(pBean.getPay_pro4_name()!=null && pBean.getPay_pro4_sort().equals("도서")){      	
			        	product_no = pBean.getPay_pro4_no();
			        	book_stock = pBean.getPay_pro4_cnt();
			        	pServ.deleteBookstock(product_no,book_stock);
			        }
			        if(pBean.getPay_pro5_name()!=null && pBean.getPay_pro5_sort().equals("도서")){      	
			        	product_no = pBean.getPay_pro5_no();
			        	book_stock = pBean.getPay_pro5_cnt();
			        	pServ.deleteBookstock(product_no,book_stock);
			        }
					 
					 
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.println("<script>");
					out.println("alert('주문완료');");
					out.println("window.location.href='/ITLearnSite/index.do'");
					out.println("</script>");
					out.close();
				}
			// ##########주문########## End
	        // ##########주문 확인########## Start	
			}else if(path.equals("/paymentCheck.pay")){
				session = request.getSession();
				String email = (String)session.getAttribute("email");
				if(request.getParameter("email")!=null){
					email = request.getParameter("email");
				}
				List<PaymentBean> paymentList = pServ.callPayment(email);
				
				request.setAttribute("paymentList", paymentList);				
				nextPage = "/main.jsp";
				paging = "/pages/main/center/payment/paymentCheck.jsp";
				request.setAttribute("paging", paging);
			// ##########주문 확인########## End
	        // ##########주문 삭제########## Start  
			}else if(path.equals("/paymentDelete.pay")){
				session = request.getSession();
				String email = (String)session.getAttribute("email");
				int pay_no = Integer.parseInt(request.getParameter("pay_no"));
				int product_no;
				int book_stock;
				pBean = pServ.selectPay(pay_no);
				//재고 늘리기
		        if(pBean.getPay_pro1_name()!=null && pBean.getPay_pro1_sort().equals("도서")){      	
		        	product_no = pBean.getPay_pro1_no();
		        	book_stock = pBean.getPay_pro1_cnt();
		        	pServ.originBookstock(product_no,book_stock);
		        }
		        if(pBean.getPay_pro2_name()!=null && pBean.getPay_pro2_sort().equals("도서")){      	
		        	product_no = pBean.getPay_pro2_no();
		        	book_stock = pBean.getPay_pro2_cnt();
		        	pServ.originBookstock(product_no,book_stock);
		        }
		        if(pBean.getPay_pro3_name()!=null && pBean.getPay_pro3_sort().equals("도서")){      	
		        	product_no = pBean.getPay_pro3_no();
		        	book_stock = pBean.getPay_pro3_cnt();
		        	pServ.originBookstock(product_no,book_stock);
		        }
		        if(pBean.getPay_pro4_name()!=null && pBean.getPay_pro4_sort().equals("도서")){      	
		        	product_no = pBean.getPay_pro4_no();
		        	book_stock = pBean.getPay_pro4_cnt();
		        	pServ.originBookstock(product_no,book_stock);
		        }
		        if(pBean.getPay_pro5_name()!=null && pBean.getPay_pro5_sort().equals("도서")){      	
		        	product_no = pBean.getPay_pro5_no();
		        	book_stock = pBean.getPay_pro5_cnt();
		        	pServ.originBookstock(product_no,book_stock);
		        }
				
				pServ.deletePayment(email,pay_no);				
					
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('주문취소');");
				out.println("window.location.href='/ITLearnSite/paymentCheck.pay'");
				out.println("</script>");
				out.close();
			// ##########주문 확인########## End
	        // ##########바로 주문########## Start  
			}else if(path.equals("/directPay.pay")){
				session = request.getSession();
				String email = (String)session.getAttribute("email");
				mBean = mServ.callMember(email);
				request.setAttribute("mBean", mBean);
				
				int product_no =  Integer.parseInt(request.getParameter("product_no"));
				String pro_name = request.getParameter("pro_name");
				int pro_price = Integer.parseInt(request.getParameter("pro_price"));
				String pro_sort = request.getParameter("pro_sort");
				int pro_cnt = Integer.parseInt(request.getParameter("pro_cnt"));
				
				request.setAttribute("product_no", product_no);
				request.setAttribute("pro_name", pro_name);
				request.setAttribute("pro_price", pro_price);
				request.setAttribute("pro_sort", pro_sort);
				request.setAttribute("pro_cnt", pro_cnt);
				
				nextPage = "/main.jsp";
				paging = "/pages/main/center/payment/directpayment.jsp";
				request.setAttribute("paging", paging);
			}
			// ##########바로 주문########## End
			// null PointException
			if (nextPage != null) {
				RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
				dispatch.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private PaymentBean getPaymentBeanProperty(HttpServletRequest request, HttpServletResponse response) {
		int pay_no = 0;
		String pay_email = null;
		String pay_name = null;
		String pay_phonenumber = null;
		String pay_address = null;
		String pay_address1 = null;
		String pay_address2 = null;
		
		int pay_pro1_no = 0;
		String pay_pro1_name = " ";
		int pay_pro1_cnt = 0;
		int pay_pro1_price = 0;
		String pay_pro1_sort = null;
		
		int pay_pro2_no = 0;
		String pay_pro2_name = null;
		int pay_pro2_cnt = 0;
		int pay_pro2_price = 0;
		String pay_pro2_sort = null;
		
		int pay_pro3_no = 0;
		String pay_pro3_name = null;
		int pay_pro3_cnt = 0;
		int pay_pro3_price = 0;
		String pay_pro3_sort = null;
		
		int pay_pro4_no = 0;
		String pay_pro4_name = null;
		int pay_pro4_cnt = 0;
		int pay_pro4_price = 0;
		String pay_pro4_sort = null;
		
		int pay_pro5_no = 0;
		String pay_pro5_name = null;
		int pay_pro5_cnt = 0;
		int pay_pro5_price = 0;
		String pay_pro5_sort = null;
		
		String pay_total = null;
		int pay_option = 0;
		
		if(request.getParameter("pay_no") != null)
		{
			pay_no = Integer.parseInt(request.getParameter("pay_no"));
			pBean.setPay_no(pay_no);
		}
		if(request.getParameter("pay_email") != null)
		{
			pay_email = request.getParameter("pay_email");
			pBean.setPay_email(pay_email);
		}
		if(request.getParameter("pay_name") != null)
		{
			pay_name = request.getParameter("pay_name");
			pBean.setPay_name(pay_name);
		}
		if(request.getParameter("pay_phonenumber")!= null)
		{
			pay_phonenumber = request.getParameter("pay_phonenumber");
			pBean.setPay_phonenumber(pay_phonenumber);
		}
		if(request.getParameter("address") != null)
		{
			pay_address = request.getParameter("address");
			pBean.setPay_address(pay_address);
		}
		if(request.getParameter("address1") != null)
		{
			pay_address1 = request.getParameter("address1");
			pBean.setPay_address1(pay_address1);
		}
		if(request.getParameter("address2") != null)
		{
			pay_address2 = request.getParameter("address2");
			pBean.setPay_address2(pay_address2);
		}		
		if(request.getParameter("pay_pro1_name")!=null)
		{
			pay_pro1_name = request.getParameter("pay_pro1_name");
			pBean.setPay_pro1_name(pay_pro1_name);
		}
		if(request.getParameter("pay_pro1_cnt")!=null)
		{
			pay_pro1_cnt =Integer.parseInt(request.getParameter("pay_pro1_cnt"));
			pBean.setPay_pro1_cnt(pay_pro1_cnt);
		}
		if(request.getParameter("pay_pro1_price")!=null)
		{
			pay_pro1_price = Integer.parseInt(request.getParameter("pay_pro1_price"));
			pBean.setPay_pro1_price(pay_pro1_price);
		}
		if(request.getParameter("pay_pro1_sort")!=null)
		{
			pay_pro1_sort = request.getParameter("pay_pro1_sort");
			pBean.setPay_pro1_sort(pay_pro1_sort);
		}
		
		if(request.getParameter("pay_pro2_name")!=null)
		{
			pay_pro2_name = request.getParameter("pay_pro2_name");
			pBean.setPay_pro2_name(pay_pro2_name);
		}
		if(request.getParameter("pay_pro2_cnt")!=null)
		{
			pay_pro2_cnt =Integer.parseInt(request.getParameter("pay_pro2_cnt"));
			pBean.setPay_pro2_cnt(pay_pro2_cnt);
		}
		if(request.getParameter("pay_pro2_price")!=null)
		{
			pay_pro2_price = Integer.parseInt(request.getParameter("pay_pro2_price"));
			pBean.setPay_pro2_price(pay_pro2_price);
		}
		if(request.getParameter("pay_pro2_sort")!=null)
		{
			pay_pro2_sort = request.getParameter("pay_pro2_sort");
			pBean.setPay_pro2_sort(pay_pro2_sort);
		}
		
		if(request.getParameter("pay_pro3_name")!=null)
		{
			pay_pro3_name = request.getParameter("pay_pro3_name");
			pBean.setPay_pro3_name(pay_pro3_name);
		}
		if(request.getParameter("pay_pro3_cnt")!=null)
		{
			pay_pro3_cnt =Integer.parseInt(request.getParameter("pay_pro3_cnt"));
			pBean.setPay_pro3_cnt(pay_pro3_cnt);
		}
		if(request.getParameter("pay_pro3_price")!=null)
		{
			pay_pro3_price = Integer.parseInt(request.getParameter("pay_pro3_price"));
			pBean.setPay_pro3_price(pay_pro3_price);
		}
		if(request.getParameter("pay_pro3_sort")!=null)
		{
			pay_pro3_sort = request.getParameter("pay_pro3_sort");
			pBean.setPay_pro3_sort(pay_pro3_sort);
		}
		
		if(request.getParameter("pay_pro4_name")!=null)
		{
			pay_pro4_name = request.getParameter("pay_pro4_name");
			pBean.setPay_pro4_name(pay_pro4_name);
		}
		if(request.getParameter("pay_pro4_cnt")!=null)
		{
			pay_pro4_cnt =Integer.parseInt(request.getParameter("pay_pro4_cnt"));
			pBean.setPay_pro4_cnt(pay_pro4_cnt);
		}
		if(request.getParameter("pay_pro4_price")!=null)
		{
			pay_pro4_price = Integer.parseInt(request.getParameter("pay_pro4_price"));
			pBean.setPay_pro4_price(pay_pro4_price);
		}
		if(request.getParameter("pay_pro4_sort")!=null)
		{
			pay_pro4_sort = request.getParameter("pay_pro4_sort");
			pBean.setPay_pro4_sort(pay_pro4_sort);
		}
		
		if(request.getParameter("pay_pro5_name")!=null)
		{
			pay_pro5_name = request.getParameter("pay_pro5_name");
			pBean.setPay_pro5_name(pay_pro5_name);
		}
		if(request.getParameter("pay_pro5_cnt")!=null)
		{
			pay_pro5_cnt =Integer.parseInt(request.getParameter("pay_pro5_cnt"));
			pBean.setPay_pro5_cnt(pay_pro5_cnt);
		}
		if(request.getParameter("pay_pro5_price")!=null)
		{
			pay_pro5_price = Integer.parseInt(request.getParameter("pay_pro5_price"));
			pBean.setPay_pro5_price(pay_pro5_price);
		}
		if(request.getParameter("pay_pro5_sort")!=null)
		{
			pay_pro5_sort = request.getParameter("pay_pro5_sort");
			pBean.setPay_pro5_sort(pay_pro5_sort);
		}
		
		if(request.getParameter("pay_total") != null)
		{
			pay_total = request.getParameter("pay_total");
			pBean.setPay_total(pay_total);
		}		
		if(request.getParameter("pay_option") != null)
		{
			pay_option = Integer.parseInt(request.getParameter("pay_option"));
			pBean.setPay_option(pay_option);
		}	
		if(request.getParameter("pay_pro1_no") != null)
		{
			pay_pro1_no = Integer.parseInt(request.getParameter("pay_pro1_no"));
			pBean.setPay_pro1_no(pay_pro1_no);
		}
		if(request.getParameter("pay_pro2_no") != null)
		{
			pay_pro2_no = Integer.parseInt(request.getParameter("pay_pro2_no"));
			pBean.setPay_pro2_no(pay_pro2_no);
		}
		if(request.getParameter("pay_pro3_no") != null)
		{
			pay_pro3_no = Integer.parseInt(request.getParameter("pay_pro3_no"));
			pBean.setPay_pro3_no(pay_pro3_no);
		}
		if(request.getParameter("pay_pro4_no") != null)
		{
			pay_pro4_no = Integer.parseInt(request.getParameter("pay_pro4_no"));
			pBean.setPay_pro4_no(pay_pro4_no);
		}
		if(request.getParameter("pay_pro5_no") != null)
		{
			pay_pro5_no = Integer.parseInt(request.getParameter("pay_pro5_no"));
			pBean.setPay_pro5_no(pay_pro5_no);
		}
		return pBean;
	}
}
