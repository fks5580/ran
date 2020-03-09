package cart.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cart.db.CartBean;
import cart.db.CartDAOImpl;
import cart.service.CartServiceImpl;

public class CartController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CartDAOImpl caDao = null;
	CartServiceImpl caServ = null;
	CartBean caBean = null;

	@Override
	public void init(ServletConfig sc) throws ServletException {
		caDao = new CartDAOImpl();
		caServ = new CartServiceImpl();
		caBean = new CartBean();
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
		    // ##########장바구니 리스트########## Start
			if (path.equals("/cart.cart")){

				String email = (String) request.getSession().getAttribute("email");
				if (email == null) {
					PrintWriter out = response.getWriter();
					out.println("<script>");
					out.println("alert('로그인을 해주세요.');");
					out.println("history.back();");
					out.println("</script>");
					out.close();
				} else {
					ArrayList<CartBean> cartlist = caServ.getcartlist(email);
					request.setAttribute("cartlist", cartlist);
					nextPage = "/main.jsp";
					paging = "/pages/main/center/cart/cart.jsp";
					request.setAttribute("paging", paging);
				}
			
			// ##########장바구니 리스트########## End	
			// ##########장바구니 넣기########## Start
			}else if (path.equals("/cartAdd.cart")) {
				String email = (String) request.getSession().getAttribute("email");
				if (email == null) {
					PrintWriter out = response.getWriter();
					out.println("<script>");
					out.println("alert('로그인을 해주세요.');");
					out.println("history.back();");
					out.println("</script>");
					out.close();
				} else {
					int product_no = Integer.parseInt(request.getParameter("product_no"));
					String pro_name = request.getParameter("pro_name");
					String pro_sort = request.getParameter("pro_sort");
					String pro_img = request.getParameter("pro_img");
					String product_type = request.getParameter("product_type");

					int pro_price = Integer.parseInt(request.getParameter("pro_price"));
					int pro_cnt = Integer.parseInt(request.getParameter("pro_cnt"));
					int result = caServ.cartDupChk(pro_name, email);
					int result1 = caServ.cartMaxChk(email);

					if (result == 1) {
						PrintWriter out = response.getWriter();
						out.println("<script>");
						out.println("alert('이미 장바구니에 있는 아이템입니다.');");
						out.println("history.back();");
						out.println("</script>");
						out.close();
					} else {
						if (result1 == 1) {
							PrintWriter out = response.getWriter();
							out.println("<script>");
							out.println("alert('장바구니에 최대 5종류까지 담을 수 있습니다.');");
							out.println("history.back();");
							out.println("</script>");
							out.close();
						} else {
						    int rs = 0;
							if (pro_sort.equals("강의")) {

								caBean.setEmail(email);
								caBean.setProduct_no(product_no);
								caBean.setPro_name(pro_name);
								caBean.setPro_price(pro_price);
								caBean.setPro_sort(pro_sort);
								caBean.setPro_cnt(pro_cnt);
								caBean.setPro_img(pro_img);

								rs = caServ.addCart(caBean);
								PrintWriter pw = response.getWriter();
								pw.print("<script>" + " var result=confirm('장바구니로 이동하시겠습니까?');"
										+ " if(result){location.href='cart.cart'}else{history.back();}" + "</script>");
							} else {
								caBean.setProduct_no(product_no);
								caBean.setEmail(email);
								caBean.setPro_name(pro_name);
								caBean.setPro_price(pro_price);
								caBean.setPro_sort(pro_sort);
								caBean.setPro_cnt(pro_cnt);
								caBean.setPro_img(pro_img);
								rs = caServ.addCart(caBean);
								PrintWriter pw = response.getWriter();
								pw.print("<script>" + " var result=confirm('장바구니로 이동하시겠습니까?');"
										+ " if(result){location.href='cart.cart'}else{history.back();}" + "</script>");

							}
						}
					}
				}
			
			// ##########장바구니 넣기########## End	
			// ##########장바구니 상품 수량 변경########## Start
		    }else if (path.equals("/cartEdit.cart")) {
				int cart_num = Integer.parseInt(request.getParameter("cart_num"));
				int pro_cnt = Integer.parseInt(request.getParameter("pro_cnt"));
				caServ.cartEdit(pro_cnt, cart_num);
				PrintWriter pw = response.getWriter();
				pw.print("<script>" + "  alert('수정되었습니다.');" + " location.href='cart.cart'" + "</script>");

			}
			// ##########장바구니 상품 수량 변경########## End   
            // ##########장바구니 상품 삭제########## Start
			else if (path.equals("/cartDelete.cart")) {
				int cart_num = Integer.parseInt(request.getParameter("cart_num"));
				int check = caServ.delcart(cart_num);
				if (check != 0) {
					PrintWriter out = response.getWriter();
					response.setContentType("text/html; charset=UTF-8");
					out.println("<script>");
					out.println("alert('삭제되었습니다');");
					out.println("location.href='cart.cart'");
					out.println("</script>");
					out.close();
				}
			// ##########장바구니 상품 삭제########## End   
	        // ##########장바구니 상품 모두 삭제########## Start
			} else if (path.equals("/cartAllDelete.cart")){
				String email = (String) request.getSession().getAttribute("email");
				int dch = caServ.delAllcart(email);
				if (dch == 1) {
					PrintWriter out = response.getWriter();
					response.setContentType("text/html; charset=UTF-8");
					out.println("<script>");
					out.println("alert('장바구니를 비웠습니다.');");
					out.println("location.href='cart.cart'");
					out.println("</script>");
					out.close();
				} else {
					PrintWriter out = response.getWriter();
					response.setContentType("text/html; charset=UTF-8");
					out.println("<script>");
					out.println("alert('장바구니가 비었습니다.');");
					out.println("location.href='cart.cart'");
					out.println("</script>");
					out.close();
				}
			}
			// ##########장바구니 상품 모두 삭제########## End
			
			// null PointException
			if (nextPage != null) {
				RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
				dispatch.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}