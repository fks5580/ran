package cart.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class CartDAOImpl implements CartDAO {
   Connection con = null;
   PreparedStatement pstmt = null;
   ResultSet rs = null;
   DataSource ds = null;
   String sql = "";
   Statement stmt = null;

   private Connection getConnection() throws Exception {
      Context ctx = new InitialContext();
      Context envContext = (Context) ctx.lookup("java:/comp/env");
      ds = (DataSource) envContext.lookup("jdbc/oracle");
      return ds.getConnection();
   }

   private void closeConnection() {
      try {
         if (rs != null)
            rs.close();
         if (pstmt != null)
            pstmt.close();
         if (con != null)
            con.close();
         if (stmt != null)
            stmt.close();

      } catch (SQLException e) {
         System.out.println("closeConnection()메소드에서 오류  : " + e);
      }
   }

   //장바구니 조회
   @Override
   public ArrayList<CartBean> getcartlist(String email) {
      ArrayList<CartBean> cartlist = new ArrayList<CartBean>();
      try {
         con = getConnection();
         sql = "select * from cart_table where email=?";
         pstmt = con.prepareStatement(sql);
         pstmt.setString(1, email);
         rs = pstmt.executeQuery();
         while (rs.next()) {
            CartBean cbean = new CartBean();
            cbean.setProduct_no(rs.getInt("product_no"));
            cbean.setCart_num(rs.getInt("cart_num"));
            cbean.setEmail(rs.getString("email"));
            cbean.setPro_name(rs.getString("pro_name"));
            cbean.setPro_cnt(rs.getInt("pro_cnt"));
            cbean.setPro_price(rs.getInt("pro_price"));
            cbean.setPro_sort(rs.getString("pro_sort"));
            cbean.setPro_img(rs.getString("pro_img"));
            cartlist.add(cbean);
         }

      } catch (Exception e) {
         e.printStackTrace();

      } finally {
         closeConnection();
      }
      return cartlist;
   }

   //장바구니 중복 체크
   @Override
   public int cartDupChk(String pro_name, String email) {
      int check = 0;
      try {
         con = getConnection();
         sql = "SELECT COUNT(*) FROM cart_table WHERE pro_name=? and email=?";
         pstmt = con.prepareStatement(sql);
         pstmt.setString(1, pro_name);
         pstmt.setString(2, email);
         rs = pstmt.executeQuery();

         if (rs.next()) {
            //장바구니에 담겨있음
            if (rs.getInt(1) != 0) {
               check = 1;
            //장바구니에 담겨있지 않음
            } else {
               check = 0;
            }
         }
      } catch (Exception e) {
         System.out.println("cartDupChk()메소드에서 오류 : " + e);
      } finally {
         closeConnection();
      }
      return check;
   }

   //장바구니 상품 추가
   @Override
   public int addCart(CartBean caBean) {
      String sql = "";
      int num = 0;
      int result = 0;
      try {
         con = getConnection();
         sql = "select max(cart_num) from cart_table";
         pstmt = con.prepareStatement(sql);
         rs = pstmt.executeQuery();
         result = 1;
         if (rs.next()) {
            num = rs.getInt(1) + 1;
         } else {
            num = 1;
         }
         sql = "insert into cart_table (cart_num, email, product_no, pro_name, pro_cnt, pro_price, pro_sort, pro_img)"
               + "values(?,?,?,?,?,?,?,?)";
         pstmt = con.prepareStatement(sql);
         pstmt.setInt(1, num);
         pstmt.setString(2, caBean.getEmail());
         pstmt.setInt(3, caBean.getProduct_no());
         pstmt.setString(4, caBean.getPro_name());
         pstmt.setInt(5, caBean.getPro_cnt());
         pstmt.setInt(6, caBean.getPro_price() * caBean.getPro_cnt());
         pstmt.setString(7, caBean.getPro_sort());
         pstmt.setString(8, caBean.getPro_img());

         pstmt.executeUpdate();

      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         closeConnection();

      }
      return result;
   }

   //장바구니 상품 개수 수정
   @Override
   public void editCart(int pro_cnt, int cart_num) {
      int rs = 0;
      
      try {
         con = getConnection();
         pstmt = con.prepareStatement(
               "update cart_table set pro_cnt=?, pro_price=(pro_price/pro_cnt)*? where cart_num=?");
         pstmt.setInt(1, pro_cnt);
         pstmt.setInt(2, pro_cnt);
         pstmt.setInt(3, cart_num);
         rs = pstmt.executeUpdate();
      } catch (Exception e) {
         System.out.println("editCart()메소드에서 오류 : " + e);
      } finally {
         closeConnection();
      }
   }
   
   //장바구니 상품 삭제
   @Override
   public int delCart(int cart_num) {
      int check = 0;
      try {
         con = getConnection();
         pstmt = con.prepareStatement("delete from cart_table where cart_num = ?");
         pstmt.setInt(1, cart_num);
         check = pstmt.executeUpdate(); // 쿼리실행으로 삭제된 레코드 수 반환
      } catch (Exception e) {
         System.out.println("delCart메소드()에서 오류 : " + e);
      } finally {
         closeConnection();
      }
      return check;
   }

   //장바구니 상품 전체 삭제
   @Override
   public int delAllCart(String email) {
	   int dch = 0;
	   try {
		   con = getConnection();
		   sql = "select pro_name from cart_table WHERE email = ?";
		   pstmt = con.prepareStatement(sql);
		   pstmt.setString(1, email);
		   rs = pstmt.executeQuery();
		   if(rs.next()){
			   con = getConnection();
	           pstmt = con.prepareStatement("delete from cart_table where email = ?");
	           pstmt.setString(1, email);
	           pstmt.executeUpdate();
	           dch = 1;
		   }  
	        
	      } catch (Exception e) {
	         System.out.println("delAllCart()메소드에서 오류 :" + e);
	      } finally {
	         closeConnection();
	      }
	  return dch;
   }
   
   //장바구니 담긴 갯수 확인
   @Override
   public int cartMaxChk(String email){
	   int check1 = 0;
	   try {
	         con = getConnection();
	         sql = "SELECT COUNT(*) AS NUM FROM cart_table WHERE email=?";
	         pstmt = con.prepareStatement(sql);
	         pstmt.setString(1, email);
	         int num ;
	         rs = pstmt.executeQuery();
	         if (rs.next()) {
	        	num=rs.getInt("NUM");
	        	//5개 존재
	        	if (num==5) {
	               check1 = 1;
	            //5개 존재 안함
	        	} else {
	               check1 = 0;
	            }
	         }
	      } catch (Exception e) {
	         System.out.println("cartMaxChk()메소드에서 오류 : " + e);
	      } finally {
	         closeConnection();
	      }

	  return check1;
   }
   

}