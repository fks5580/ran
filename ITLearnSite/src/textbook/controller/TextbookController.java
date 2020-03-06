package textbook.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.URIDereferencer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import textbook.db.TextbookBean;
import textbook.db.TextbookDAOImpl;
import textbook.naverAPI.NaverSearchAPI;
import textbook.service.TextbookServiceImpl;

public class TextbookController extends HttpServlet {
   private static final long serialVersionUID = 1L;
   TextbookDAOImpl tDao = null;
   TextbookServiceImpl tServ = null;
   TextbookBean tBean = null;

   @Override
   public void init(ServletConfig sc) throws ServletException {
      tDao = new TextbookDAOImpl();
      tServ = new TextbookServiceImpl();
      tBean = new TextbookBean();
   }

   @Override
   public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      request.setCharacterEncoding("utf-8");
      response.setContentType("text/html;charset=UTF-8");
      String url = request.getRequestURI();
      String contextPath = request.getContextPath();
      String path = url.substring(contextPath.length());
      String nextPage = null;
      String paging = null;

      try {
         // ##########도서 페이지 리스트########## Start
         if (path.equals("/bookList.text")) {
            nextPage = "/main.jsp";
            paging = "/pages/main/center/books/bookList.jsp";
            request.setAttribute("paging", paging);
         // ##########도서 페이지 리스트########## End
         // ##########도서 추가 페이지 리스트########## Start
         } else if (path.equals("/bookaddpage.text")) {
            nextPage = "/main.jsp";
            paging = "/pages/main/center/books/bookadd.jsp";
            request.setAttribute("paging", paging);
         // ##########도서 추가 페이지 리스트########## End
         // ##########도서 재고관리 페이지 리스트########## Start
         } else if (path.equals("/bookstock.text")) {
            nextPage = "/main.jsp";
            paging = "/pages/main/center/books/bookstock.jsp";
            int num = 1;
            
            if (request.getParameter("num") != null) {
               num = Integer.parseInt(request.getParameter("num"));
            }

            final int pageSize = 5;
            final int pageGroupSize = 2;
            String pageNum = request.getParameter("pageNum");
            
            if(pageNum == null){
               pageNum = "1";
            }
            
            int currentPage = Integer.parseInt(pageNum);
            int startRow = (currentPage - 1) * pageSize + 1;
            int endRow = currentPage * pageSize;
            
            int count = tServ.count();
            
            int number = 0;
            ArrayList<TextbookBean> list = null;
            if(count > 0){
               if(endRow > count){
                  endRow = count;
               }
               list = tServ.selectBookList(startRow, endRow);
               
            }
            number = count - (currentPage - 1) *pageSize;
            
            int pageGroupCount = count / (pageSize * pageGroupSize) + (count % (pageSize*pageGroupSize) == 0 ? 0 : 1);   
            int numPageGroup = (int)Math.ceil((double)currentPage/pageGroupSize);
            
            request.setAttribute("currentPage", new Integer(currentPage));
            request.setAttribute("startRow", new Integer(startRow));
            request.setAttribute("endRow", new Integer(endRow));
            request.setAttribute("count", new Integer(count));
            request.setAttribute("pageSize", new Integer(pageSize));
            request.setAttribute("number", new Integer(number));
            request.setAttribute("pageGroupSize", new Integer(pageGroupSize));
            request.setAttribute("numPageGroup", new Integer(numPageGroup));
            request.setAttribute("pageGroupCount", new Integer(pageGroupCount));

            JSONObject jsondata = new JSONObject();
            Date date = null;
            String jsonString = null;
            JSONArray arr = new JSONArray();
            try {
               for (int i = 0; i < list.size(); i++) {
                  jsondata = new JSONObject();
                  jsondata.put("num", list.get(i).getNum());
                  jsondata.put("product_no", list.get(i).getProduct_no());
                  jsondata.put("book_title", list.get(i).getBook_title());
                  jsondata.put("book_link", list.get(i).getBook_link());
                  jsondata.put("book_image", list.get(i).getBook_image());
                  jsondata.put("book_author", list.get(i).getBook_author());
                  jsondata.put("book_price", list.get(i).getBook_price());
                  jsondata.put("book_discount", list.get(i).getBook_discount());
                  jsondata.put("book_publisher", list.get(i).getBook_pubdate());
                  jsondata.put("book_pubdate", list.get(i).getBook_pubdate());
                  jsondata.put("book_isbn", list.get(i).getBook_isbn());
                  jsondata.put("book_description", list.get(i).getBook_description());
                  jsondata.put("book_stock", list.get(i).getBook_stock());
                  jsondata.put("product_type", list.get(i).getProduct_type());
                  arr.add(jsondata);
               }
            } catch (Exception e) {
               e.printStackTrace();
            }finally {
               JSONObject booklist = new JSONObject();
               booklist.put("list", arr);
               booklist.put("count", count);
               
               jsonString = booklist.toJSONString();
               
               HttpSession session = request.getSession();
               session.setAttribute("list", arr);
               request.setAttribute("count", count);
               request.setAttribute("paging", paging);
               request.setAttribute("num" , num);
            }
         // ##########도서 재고 관리 페이지 리스트########## End
         // ##########도서 등록########## Start   
         } else if (path.equals("/insertBook.text")) {
            tBean = getTextbookBeanProperty(request, response);
            int result = tServ.insertBook(tBean);
            if (result == 1) {
               response.sendRedirect(request.getContextPath() + "/bookList.text");
            }else{
               PrintWriter out = response.getWriter();
               out.println("<script>");
               out.println("alert('도서 등록 실패.');");
               out.println("history.back();");
               out.println("</script>");
               out.close();
            }
         // ##########도서 등록########## End
         // ##########도서 페이지 리스트########## Start   
         } else if (path.equals("/bookselect.text")) {
            int num = 1;
            if (request.getParameter("num") != null) {
               num = Integer.parseInt(request.getParameter("num"));
            }
           
            final int pageSize = 5;
            final int pageGroupSize = 2;
            String pageNum = request.getParameter("pageNum");
            
            if(pageNum == null){
               pageNum = "1";
            }
            
            int currentPage = Integer.parseInt(pageNum);
            int startRow = (currentPage - 1) * pageSize + 1;
            int endRow = currentPage * pageSize; 
            
            int count = tServ.count();
            
            int number = 0;
            ArrayList<TextbookBean> list = null;
            if(count > 0){
               if(endRow > count){
                  endRow = count;
               }
               list = tServ.selectBookList(startRow, endRow);
               
            }
            number = count - (currentPage - 1) *pageSize;
            
            int pageGroupCount = count / (pageSize * pageGroupSize) + (count % (pageSize*pageGroupSize) == 0 ? 0 : 1);
            int numPageGroup = (int)Math.ceil((double)currentPage/pageGroupSize);
            HashMap<String, Integer> page = new HashMap<String, Integer>();
            
            page.put("currentPage", new Integer(currentPage));
            page.put("startRow", new Integer(startRow));
            page.put("endRow", new Integer(endRow));
            page.put("count", new Integer(count));
            page.put("pageSize", new Integer(pageSize));
            page.put("number", new Integer(number));
            page.put("pageGroupSize", new Integer(pageGroupSize));
            page.put("numPageGroup", new Integer(numPageGroup));
            page.put("pageGroupCount", new Integer(pageGroupCount));

            JSONObject jsondata = new JSONObject();
            Date date = null;
            String jsonString = null;
            JSONArray arr = new JSONArray();
           
                for (int i = 0; i < list.size(); i++) {
                   jsondata = new JSONObject();
                   jsondata.put("num", list.get(i).getNum());
                   jsondata.put("product_no", list.get(i).getProduct_no());
                   jsondata.put("book_title", list.get(i).getBook_title());
                   jsondata.put("book_link", list.get(i).getBook_link());
                   jsondata.put("book_image", list.get(i).getBook_image());
                   jsondata.put("book_author", list.get(i).getBook_author());
                   jsondata.put("book_price", list.get(i).getBook_price());
                   jsondata.put("book_discount", list.get(i).getBook_discount());
                   jsondata.put("book_publisher", list.get(i).getBook_pubdate());
                   jsondata.put("book_pubdate", list.get(i).getBook_pubdate());
                   jsondata.put("book_isbn", list.get(i).getBook_isbn());
                   jsondata.put("book_description", list.get(i).getBook_description());
                   jsondata.put("book_stock", list.get(i).getBook_stock());
                   jsondata.put("product_type", list.get(i).getProduct_type());
    
                   arr.add(jsondata);
                }
    
            JSONObject booklist = new JSONObject();
            booklist.put("list", arr);
            booklist.put("count", count);
            booklist.put("page", page);
            
            jsonString = booklist.toJSONString();

            PrintWriter out = response.getWriter();
            out.print(jsonString);

            HttpSession session = request.getSession();
            request.setAttribute("count", count);
            request.setAttribute("paging", paging);
            paging = "/pages/main/center/books/bookList.jsp";

            
         // ##########도서 페이지 리스트########## End
         // ##########도서 상세보기########## Start   
         } else if (path.equals("/bookdetail.text")) {
            tBean = getTextbookBeanProperty(request, response);
            int product_no = Integer.parseInt(request.getParameter("product_no"));
            tBean = tServ.bookdetail(product_no);

            request.setAttribute("detail", tBean);

            nextPage = "/main.jsp";
            paging = "/pages/main/center/books/bookdetail.jsp?product_no=" + product_no;
            request.setAttribute("paging", paging);
         // ##########도서 상세보기########## End
         // ##########네이버 도서 정보 가져오기########## Start
         } else if (path.equals("/bookSearch.text")) {
            // 네이버 api
            NaverSearchAPI search = new NaverSearchAPI();
            String word = request.getParameter("word");
            String result = search.Search(word);

            JSONParser paser = new JSONParser();
            Object obj = paser.parse(result);
            JSONObject jsonObj = (JSONObject) obj;
            request.setAttribute("result", jsonObj);

            PrintWriter out = response.getWriter();
            out.print(jsonObj);
         // ##########네이버 도서 정보 가져오기########## End
         // ##########도서 삭제########## Start   
         } else if (path.equals("/bookdelete.text")) {
            tBean = getTextbookBeanProperty(request, response);
            int product_no = Integer.parseInt(request.getParameter("product_no"));
            int check = tServ.deletebook(product_no);

            response.sendRedirect("bookstock.text");
         // ##########도서 삭제########## End
         // ##########도서 정보 수정########## Start
         } else if (path.equals("/stockmodify.text")) {

            int price = Integer.parseInt(request.getParameter("price"));
            int stock = Integer.parseInt(request.getParameter("stock"));
            int p_no = Integer.parseInt(request.getParameter("p_no"));

            int result = tServ.stockModify(p_no, price, stock);
            PrintWriter out = response.getWriter();
            if (result == 1) {

               out.print(1);
            } else if (result == 0) {
               out.print(0);
            }
         }
         // ##########도서 정보 수정########## End
         
         // null PointException
         if (nextPage != null) {
            RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
            dispatch.forward(request, response);
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   private TextbookBean getTextbookBeanProperty(HttpServletRequest request, HttpServletResponse response) {
      String book_title = null;
      String book_link = null;
      String book_image = null;
      String book_author = null;
      int book_price = 0;
      int book_discount = 0;
      String book_publisher = null;
      String book_pubdate = null;
      String book_isbn = null;
      String book_description = null;
      int book_stock = 0;
      String product_type = null;

      if (request.getParameter("book_title") != null) {
         book_title = request.getParameter("book_title");
         tBean.setBook_title(book_title);
      }

      if (request.getParameter("book_link") != null) {
         book_link = request.getParameter("book_link");
         tBean.setBook_link(book_link);
      }

      if (request.getParameter("book_image") != null) {
         book_image = request.getParameter("book_image");
         tBean.setBook_image(book_image);
      }

      if (request.getParameter("book_author") != null) {
         book_author = request.getParameter("book_author");
         tBean.setBook_author(book_author);
      }

      if (request.getParameter("book_price") != null) {
         book_price = Integer.parseInt(request.getParameter("book_price"));
         tBean.setBook_price(book_price);
      }

      if (request.getParameter("book_discount") != null) {
         book_discount = Integer.parseInt(request.getParameter("book_discount"));
         tBean.setBook_discount(book_discount);
      }

      if (request.getParameter("book_publisher") != null) {
         book_publisher = request.getParameter("book_publisher");
         tBean.setBook_publisher(book_publisher);
      }

      if (request.getParameter("book_pubdate") != null) {
         book_pubdate = request.getParameter("book_pubdate");
         tBean.setBook_pubdate(book_pubdate);
      }

      if (request.getParameter("book_isbn") != null) {
         book_isbn = request.getParameter("book_isbn");
         tBean.setBook_isbn(book_isbn);
      }

      if (request.getParameter("book_description") != null) {
         book_description = request.getParameter("book_description");
         tBean.setBook_description(book_description);
      }

      if (request.getParameter("book_stock") != null) {
         book_stock = Integer.parseInt(request.getParameter("book_stock"));
         tBean.setBook_stock(book_stock);
      }
      
      if (request.getParameter("product_type") != null) {
         product_type = request.getParameter("product_type");
         tBean.setProduct_type(product_type);
      }

      return tBean;
   }
}
