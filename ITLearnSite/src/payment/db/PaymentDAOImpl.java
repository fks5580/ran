package payment.db;

import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import lecture.db.PaylecBean;
import member.db.MemberBean;

public class PaymentDAOImpl implements PaymentDAO {
	Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    DataSource ds = null;
    String sql = "";
    Statement stmt = null;
    
    private Connection getConnection() throws Exception {
  	  Context ctx = new InitialContext();
      Context envContext =(Context)ctx.lookup("java:/comp/env");
      ds = (DataSource)envContext.lookup("jdbc/oracle");
      return ds.getConnection();
    }
    
    private void closeConnection(){
        try {
            if(rs != null) rs.close();
            if(pstmt != null) pstmt.close();
            if(con != null) con.close();
            if(stmt != null) stmt.close();
          
        } catch (SQLException e) {
            System.out.println("closeConnection()메소드에서 오류  : " +e);
        }
    }
    
    //전체 주문 목록 조회(관리자)
    @Override
    public List<PaymentBean> getPaymentlist(){
    	List<PaymentBean> paymentList = new ArrayList<PaymentBean>();
        try{
            con = getConnection();
            sql = "select * from payment_table";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()){
            	PaymentBean paymentBean = new PaymentBean();
            	paymentBean.setPay_no(rs.getInt("pay_no"));
            	paymentBean.setPay_email(rs.getString("pay_email"));
            	paymentBean.setPay_name(rs.getString("pay_name"));
            	paymentBean.setPay_phonenumber(rs.getString("pay_phonenumber"));
            	paymentBean.setPay_address(rs.getString("pay_address"));
            	paymentBean.setPay_address1(rs.getString("pay_address1"));
            	paymentBean.setPay_address2(rs.getString("pay_address2"));
            	paymentBean.setPay_pro1_name(rs.getString("pay_pro1_name"));
            	paymentBean.setPay_pro1_cnt(rs.getInt("pay_pro1_cnt"));
            	paymentBean.setPay_pro1_price(rs.getInt("pay_pro1_price"));
            	paymentBean.setPay_pro1_sort(rs.getString("pay_pro1_sort"));
            	paymentBean.setPay_pro2_name(rs.getString("pay_pro2_name"));
            	paymentBean.setPay_pro2_cnt(rs.getInt("pay_pro2_cnt"));
            	paymentBean.setPay_pro2_price(rs.getInt("pay_pro2_price"));
            	paymentBean.setPay_pro2_sort(rs.getString("pay_pro2_sort"));          	
            	paymentBean.setPay_pro3_name(rs.getString("pay_pro3_name"));
            	paymentBean.setPay_pro3_cnt(rs.getInt("pay_pro3_cnt"));
            	paymentBean.setPay_pro3_price(rs.getInt("pay_pro3_price"));
            	paymentBean.setPay_pro3_sort(rs.getString("pay_pro3_sort"));         	
            	paymentBean.setPay_pro4_name(rs.getString("pay_pro4_name"));
            	paymentBean.setPay_pro4_cnt(rs.getInt("pay_pro4_cnt"));
            	paymentBean.setPay_pro4_price(rs.getInt("pay_pro4_price"));
            	paymentBean.setPay_pro4_sort(rs.getString("pay_pro4_sort"));          	
            	paymentBean.setPay_pro5_name(rs.getString("pay_pro5_name"));
            	paymentBean.setPay_pro5_cnt(rs.getInt("pay_pro5_cnt"));
            	paymentBean.setPay_pro5_price(rs.getInt("pay_pro5_price"));
            	paymentBean.setPay_pro5_sort(rs.getString("pay_pro5_sort"));
            	paymentBean.setPay_total(rs.getString("pay_total"));
            	paymentBean.setPay_option(rs.getInt("pay_option"));
            	paymentBean.setPay_pro1_no(rs.getInt("pay_pro1_no"));
            	paymentBean.setPay_pro2_no(rs.getInt("pay_pro2_no"));
            	paymentBean.setPay_pro3_no(rs.getInt("pay_pro3_no"));
            	paymentBean.setPay_pro4_no(rs.getInt("pay_pro4_no"));
            	paymentBean.setPay_pro5_no(rs.getInt("pay_pro5_no"));          	
            	paymentList.add(paymentBean);
            	
            }                    
        }catch(Exception e){
                System.out.println("getPaymentlist()메소드에서 오류 :"+e);
        }finally{
            closeConnection();  
        }
            return paymentList;
    }

  	//회원 전체주문 확인
    @Override
    public List<PaymentBean> callPayment(String email){
    	List<PaymentBean> paymentList = new ArrayList<PaymentBean>();
        try{
            con = getConnection();
            sql = "select * from payment_table where pay_email='"+email+"'";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()){
            	PaymentBean paymentBean = new PaymentBean();
            	paymentBean.setPay_no(rs.getInt("pay_no"));
            	paymentBean.setPay_email(rs.getString("pay_email"));
            	paymentBean.setPay_name(rs.getString("pay_name"));
            	paymentBean.setPay_phonenumber(rs.getString("pay_phonenumber"));
            	paymentBean.setPay_address(rs.getString("pay_address"));
            	paymentBean.setPay_address1(rs.getString("pay_address1"));
            	paymentBean.setPay_address2(rs.getString("pay_address2"));
            	paymentBean.setPay_pro1_name(rs.getString("pay_pro1_name"));
            	paymentBean.setPay_pro1_cnt(rs.getInt("pay_pro1_cnt"));
            	paymentBean.setPay_pro1_price(rs.getInt("pay_pro1_price"));
            	paymentBean.setPay_pro1_sort(rs.getString("pay_pro1_sort"));
            	paymentBean.setPay_pro2_name(rs.getString("pay_pro2_name"));
            	paymentBean.setPay_pro2_cnt(rs.getInt("pay_pro2_cnt"));
            	paymentBean.setPay_pro2_price(rs.getInt("pay_pro2_price"));
            	paymentBean.setPay_pro2_sort(rs.getString("pay_pro2_sort"));          	
            	paymentBean.setPay_pro3_name(rs.getString("pay_pro3_name"));
            	paymentBean.setPay_pro3_cnt(rs.getInt("pay_pro3_cnt"));
            	paymentBean.setPay_pro3_price(rs.getInt("pay_pro3_price"));
            	paymentBean.setPay_pro3_sort(rs.getString("pay_pro3_sort"));         	
            	paymentBean.setPay_pro4_name(rs.getString("pay_pro4_name"));
            	paymentBean.setPay_pro4_cnt(rs.getInt("pay_pro4_cnt"));
            	paymentBean.setPay_pro4_price(rs.getInt("pay_pro4_price"));
            	paymentBean.setPay_pro4_sort(rs.getString("pay_pro4_sort"));          	
            	paymentBean.setPay_pro5_name(rs.getString("pay_pro5_name"));
            	paymentBean.setPay_pro5_cnt(rs.getInt("pay_pro5_cnt"));
            	paymentBean.setPay_pro5_price(rs.getInt("pay_pro5_price"));
            	paymentBean.setPay_pro5_sort(rs.getString("pay_pro5_sort"));
            	paymentBean.setPay_total(rs.getString("pay_total"));
            	paymentBean.setPay_option(rs.getInt("pay_option"));
            	paymentBean.setPay_pro1_no(rs.getInt("pay_pro1_no"));
            	paymentBean.setPay_pro2_no(rs.getInt("pay_pro2_no"));
            	paymentBean.setPay_pro3_no(rs.getInt("pay_pro3_no"));
            	paymentBean.setPay_pro4_no(rs.getInt("pay_pro4_no"));
            	paymentBean.setPay_pro5_no(rs.getInt("pay_pro5_no"));
            	
            	paymentList.add(paymentBean);
            	
            }                    
        }catch(Exception e){
                System.out.println("getPaymentlist()메소드에서 오류 :"+e);
        }finally{
            closeConnection();  
        }
            return paymentList;
    }
  	
  	//회원 주문 하기
    @Override
  	public void insertPayment(PaymentBean pBean){
    	int pay_no;
    	try{
			con = getConnection();
            sql = "select max(pay_no) from payment_table";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if(rs.next()){
                pay_no = rs.getInt(1) + 1;
            }else{
                pay_no = 1;             
            }
			
            //주문 DB
			sql = "insert into payment_table(pay_no,pay_email,pay_name,pay_phonenumber,pay_address,pay_address1,pay_address2,"
            		+ "pay_pro1_no,pay_pro1_name,pay_pro1_cnt,pay_pro1_price,pay_pro1_sort,"
            		+ "pay_pro2_no,pay_pro2_name,pay_pro2_cnt,pay_pro2_price,pay_pro2_sort,"
            		+ "pay_pro3_no,pay_pro3_name,pay_pro3_cnt,pay_pro3_price,pay_pro3_sort,"
            		+ "pay_pro4_no,pay_pro4_name,pay_pro4_cnt,pay_pro4_price,pay_pro4_sort,"
            		+ "pay_pro5_no,pay_pro5_name,pay_pro5_cnt,pay_pro5_price,pay_pro5_sort,"
            		+ "pay_total,pay_option) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	        pstmt = con.prepareStatement(sql);
	        
	        pstmt.setInt(1,pay_no);
	        pstmt.setString(2,pBean.getPay_email());
	        pstmt.setString(3,pBean.getPay_name() );
	        pstmt.setString(4,pBean.getPay_phonenumber());
	        pstmt.setString(5,pBean.getPay_address());
	        pstmt.setString(6,pBean.getPay_address1());
	        pstmt.setString(7,pBean.getPay_address2());
	        int j;
	        if(pBean.getPay_pro1_no()!=0){
	        	pstmt.setInt(8, pBean.getPay_pro1_no());
		        pstmt.setString(9,pBean.getPay_pro1_name());
		        pstmt.setInt(10,pBean.getPay_pro1_cnt());
		        pstmt.setInt(11,pBean.getPay_pro1_price());
		        pstmt.setString(12,pBean.getPay_pro1_sort());
		                       
		        j=13;
		      	while(j<32){
		      		pstmt.setInt(j, 0);
		      		j++;
		        	pstmt.setString(j,"x");
		        	j++;
					pstmt.setInt(j,0);
					j++;
					pstmt.setInt(j,0);
					j++;
					pstmt.setString(j,"x");
					j++;
		      	}
		      	if(pBean.getPay_pro2_no()!=0){
		      		pstmt.setInt(13, pBean.getPay_pro2_no());
			        pstmt.setString(14,pBean.getPay_pro2_name());
			        pstmt.setInt(15,pBean.getPay_pro2_cnt());
			        pstmt.setInt(16,pBean.getPay_pro2_price());
			        pstmt.setString(17,pBean.getPay_pro2_sort());
			        
			        j=18;
			      	while(j<32){
			      		pstmt.setInt(j, 0);
			      		j++;
			        	pstmt.setString(j,"x");
			        	j++;
						pstmt.setInt(j,0);
						j++;
						pstmt.setInt(j,0);
						j++;
						pstmt.setString(j,"x");
						j++;
			      	}
			      	if(pBean.getPay_pro3_no()!=0){
			      		pstmt.setInt(18, pBean.getPay_pro3_no());
			        	pstmt.setString(19,pBean.getPay_pro3_name());
					    pstmt.setInt(20,pBean.getPay_pro3_cnt());
					    pstmt.setInt(21,pBean.getPay_pro3_price());
					    pstmt.setString(22,pBean.getPay_pro3_sort());
					    
					    j=23;
				      	while(j<32){
				      		pstmt.setInt(j, 0);
				      		j++;
				        	pstmt.setString(j,"x");
				        	j++;
							pstmt.setInt(j,0);
							j++;
							pstmt.setInt(j,0);
							j++;
							pstmt.setString(j,"x");
							j++;
				      	}
				      	if(pBean.getPay_pro4_no()!=0){
				      		pstmt.setInt(23, pBean.getPay_pro4_no());
			        		pstmt.setString(24,pBean.getPay_pro4_name());
						    pstmt.setInt(25,pBean.getPay_pro4_cnt());
						    pstmt.setInt(26,pBean.getPay_pro4_price());
						    pstmt.setString(27,pBean.getPay_pro4_sort());   
						   
						    j=28;
						    while(j<32){
						    	pstmt.setInt(j, 0);
					      		j++;
					        	pstmt.setString(j,"x");
					        	j++;
								pstmt.setInt(j,0);
								j++;
								pstmt.setInt(j,0);
								j++;
								pstmt.setString(j,"x");
								j++;
					      	}
						    if(pBean.getPay_pro5_no()!=0){
						    	pstmt.setInt(28, pBean.getPay_pro5_no());
			        			pstmt.setString(29,pBean.getPay_pro5_name());
			 			        pstmt.setInt(30,pBean.getPay_pro5_cnt());
			 			        pstmt.setInt(31,pBean.getPay_pro5_price());
			 			        pstmt.setString(32,pBean.getPay_pro5_sort());
			 			       
					        }
				      	}
			      	}
		      	} 	
	        }
	        pstmt.setString(33,pBean.getPay_total());
	        pstmt.setInt(34,0);
	        
			pstmt.executeUpdate();	
			
			//장바구니 DB비우기
			sql = "delete from cart_table where email='"+pBean.getPay_email()+"'";
            stmt = con.createStatement();
            stmt.executeUpdate(sql);   	
	
		} 
		catch (Exception e) 
		{
			System.out.println("insertPayment()메소드에서 오류 : "+e);
			e.printStackTrace();
		}
		finally
		{
			closeConnection();
		}
    }
  	
    //주문 후 도서 재고 원래대로 하기 위해 상품 주문 정보 가져오기
    @Override
    public PaymentBean selectPay(int pay_no){
    	PaymentBean pBean = null;
        try{
            con = getConnection();
            sql = "select * from payment_table where pay_no="+pay_no;
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()){
            	pBean = new PaymentBean();
            	pBean.setPay_no(rs.getInt("pay_no"));
            	pBean.setPay_email(rs.getString("pay_email"));
            	pBean.setPay_name(rs.getString("pay_name"));
            	pBean.setPay_phonenumber(rs.getString("pay_phonenumber"));
            	pBean.setPay_address(rs.getString("pay_address"));
            	pBean.setPay_address1(rs.getString("pay_address1"));
            	pBean.setPay_address2(rs.getString("pay_address2"));
            	pBean.setPay_pro1_name(rs.getString("pay_pro1_name"));
            	pBean.setPay_pro1_cnt(rs.getInt("pay_pro1_cnt"));
            	pBean.setPay_pro1_price(rs.getInt("pay_pro1_price"));
            	pBean.setPay_pro1_sort(rs.getString("pay_pro1_sort"));
            	pBean.setPay_pro2_name(rs.getString("pay_pro2_name"));
            	pBean.setPay_pro2_cnt(rs.getInt("pay_pro2_cnt"));
            	pBean.setPay_pro2_price(rs.getInt("pay_pro2_price"));
            	pBean.setPay_pro2_sort(rs.getString("pay_pro2_sort"));          	
            	pBean.setPay_pro3_name(rs.getString("pay_pro3_name"));
            	pBean.setPay_pro3_cnt(rs.getInt("pay_pro3_cnt"));
            	pBean.setPay_pro3_price(rs.getInt("pay_pro3_price"));
            	pBean.setPay_pro3_sort(rs.getString("pay_pro3_sort"));         	
            	pBean.setPay_pro4_name(rs.getString("pay_pro4_name"));
            	pBean.setPay_pro4_cnt(rs.getInt("pay_pro4_cnt"));
            	pBean.setPay_pro4_price(rs.getInt("pay_pro4_price"));
            	pBean.setPay_pro4_sort(rs.getString("pay_pro4_sort"));          	
            	pBean.setPay_pro5_name(rs.getString("pay_pro5_name"));
            	pBean.setPay_pro5_cnt(rs.getInt("pay_pro5_cnt"));
            	pBean.setPay_pro5_price(rs.getInt("pay_pro5_price"));
            	pBean.setPay_pro5_sort(rs.getString("pay_pro5_sort"));
            	pBean.setPay_total(rs.getString("pay_total"));
            	pBean.setPay_option(rs.getInt("pay_option"));
            	pBean.setPay_pro1_no(rs.getInt("pay_pro1_no"));
            	pBean.setPay_pro2_no(rs.getInt("pay_pro2_no"));
            	pBean.setPay_pro3_no(rs.getInt("pay_pro3_no"));
            	pBean.setPay_pro4_no(rs.getInt("pay_pro4_no"));
            	pBean.setPay_pro5_no(rs.getInt("pay_pro5_no"));
            	
            }                    
        }catch(Exception e){
                System.out.println("selectPay()메소드에서 오류 :"+e);
        }finally{
            closeConnection();  
        }
            return pBean;
    }
    
    //주문 후 도서 재고 줄이기
    @Override
    public void deleteBookstock(int product_no,int book_stock){
    	try{
    		con = getConnection();   		
    		sql = "update book_table set book_stock=book_stock-"+book_stock+" where product_no=?"; 
    		pstmt = con.prepareStatement(sql);
    	    pstmt.setInt(1,product_no); 
    	    pstmt.executeUpdate(); 	   
    	}catch(Exception e){
    		System.out.println("deleteBookstock()메소드에서 오류 :"+e);
    	}finally{
	    	   closeConnection();  			
	    }
    	
       
    }
    
  	//회원 주문 취소
    @Override
  	public void deletePayment(String email,int pay_no){
    	try{	         
	  		 con = getConnection();
	  		 sql = "delete from payment_table where pay_email=? and pay_no=?";
			 pstmt = con.prepareStatement(sql);		     
		     pstmt.setString(1, email.trim());
		     pstmt.setInt(2,pay_no);
		     pstmt.executeUpdate();
		     
    	}catch(Exception e){
	    	   System.out.println("deletePayment()메소드에서 오류 :"+e);
	    }finally{
	    	   closeConnection();  			
	    }
    }
    
    //주문 취소 후 도서 재고 원래대로 
    @Override
    public void originBookstock(int product_no,int book_stock){
    	try{
    		con = getConnection();   		
    		sql = "update book_table set book_stock=book_stock+"+book_stock+" where product_no=?"; 
    		pstmt = con.prepareStatement(sql);
    	    pstmt.setInt(1,product_no); 
    	    pstmt.executeUpdate();
      	    
    	}catch(Exception e){
    		System.out.println("originBookstock()메소드에서 오류 :"+e);
    	}finally{
	    	   closeConnection();  			
	    }
    }
    
    
    //회원 결제 확인
    @Override
    public void updatePayment(int pay_no,int pay_option){
 		try{
			con = getConnection();
			pstmt = con.prepareStatement("update payment_table set pay_option=? where pay_no="+pay_no);
			pstmt.setInt(1, pay_option);
			pstmt.executeUpdate();
			
		}catch(Exception e){
			System.out.println("updatePayment()메소드에서 오류 : "+e);
			
		}finally{
			closeConnection();  			
		}
	
    }
    
    //결제 확인 시 강의리스트 insert
    @Override
    public PaylecBean setPay_lec(int pay_no){
    	PaylecBean plBean = new PaylecBean();
    	int set_no;
    	try{
    		con = getConnection();
    		
    		sql = "select max(set_no) from set_pay_lec_table";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if(rs.next()){
                set_no = rs.getInt(1) + 1;
            }else{
                set_no = 1;             
            }		
    		
            sql = "select * from payment_table where pay_no="+pay_no;
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()){		
            	plBean.setSet_no(set_no);
            	plBean.setSet_email(rs.getString("pay_email"));
            	if(rs.getString("pay_pro1_sort").equals("강의"))
            	{
            		plBean.setSet_lec_title(rs.getString("pay_pro1_name"));
            		sql = "insert into set_pay_lec_table(set_no,set_email,set_lec_title, set_endDate) values(?,?,?,(sysdate + 30))";
            		pstmt = con.prepareStatement(sql);
         	        pstmt.setInt(1,plBean.getSet_no());
         	        pstmt.setString(2,plBean.getSet_email() );
         	        pstmt.setString(3,plBean.getSet_lec_title());	        
         	        pstmt.executeUpdate();
            	}
            	if(rs.getString("pay_pro2_sort").equals("강의"))
            	{
            		plBean.setSet_lec_title(rs.getString("pay_pro2_name"));
            		sql = "insert into set_pay_lec_table(set_no,set_email,set_lec_title, set_endDate) values(?,?,?,(sysdate + 30))";
                    pstmt = con.prepareStatement(sql);
         	        pstmt.setInt(1,plBean.getSet_no());
         	        pstmt.setString(2,plBean.getSet_email() );
         	        pstmt.setString(3,plBean.getSet_lec_title());	        
         	        pstmt.executeUpdate();
            	}
            	if(rs.getString("pay_pro3_sort").equals("강의"))
            	{
            		plBean.setSet_lec_title(rs.getString("pay_pro3_name"));
            		sql = "insert into set_pay_lec_table(set_no,set_email,set_lec_title, set_endDate) values(?,?,?,(sysdate + 30))";
                    pstmt = con.prepareStatement(sql);
         	        pstmt.setInt(1,plBean.getSet_no());
         	        pstmt.setString(2,plBean.getSet_email() );
         	        pstmt.setString(3,plBean.getSet_lec_title());	        
         	        pstmt.executeUpdate();
            	}
            	if(rs.getString("pay_pro4_sort").equals("강의"))
            	{
            		plBean.setSet_lec_title(rs.getString("pay_pro4_name"));
            		sql = "insert into set_pay_lec_table(set_no,set_email,set_lec_title, set_endDate) values(?,?,?,(sysdate + 30))";
                    pstmt = con.prepareStatement(sql);
         	        pstmt.setInt(1,plBean.getSet_no());
         	        pstmt.setString(2,plBean.getSet_email() );
         	        pstmt.setString(3,plBean.getSet_lec_title());	        
         	        pstmt.executeUpdate();
            	}
            	if(rs.getString("pay_pro5_sort").equals("강의"))
            	{
            		plBean.setSet_lec_title(rs.getString("pay_pro5_name"));
            		sql = "insert into set_pay_lec_table(set_no,set_email,set_lec_title, set_endDate) values(?,?,?,(sysdate + 30))";
                    pstmt = con.prepareStatement(sql);
         	        pstmt.setInt(1,plBean.getSet_no());
         	        pstmt.setString(2,plBean.getSet_email() );
         	        pstmt.setString(3,plBean.getSet_lec_title());	        
         	        pstmt.executeUpdate();
            	}         	      	         	
            }  

	      
	        
		}catch(Exception e){
			System.out.println("setPay_lec()메소드에서 오류 : "+e);
			
		}finally{
			closeConnection();  			
		}
    	return plBean;
    }
    
    //주문 강의 중복 체크
    @Override
    public int payDupChk(String pro_name, String email) {
       int check = 0;
       try {
          con = getConnection();
          sql = "SELECT COUNT(*) FROM set_pay_lec_table WHERE set_lec_title=? and set_email=? and sysdate<=set_enddate";
          pstmt = con.prepareStatement(sql);
          pstmt.setString(1, pro_name);
          pstmt.setString(2, email);
          rs = pstmt.executeQuery();

          if (rs.next()) {
             if (rs.getInt(1) != 0) {
                //강의가 존재함
                check = 1;
             } else {
                //강의가 존재하지않음
                check = 0;
             }
          }

       } catch (Exception e) {
          System.out.println("payDupChk()메소드에서 오류 : " + e);
       } finally {
          closeConnection();
       }

       return check;

    }
    
    //장바구니에 상품이 있는지 체크
    @Override
    public int cartnullChk(String email){
    	int check = 0;
        try {
           con = getConnection();
           sql = "SELECT pro_name FROM cart_table WHERE email=?";
           pstmt = con.prepareStatement(sql);
           pstmt.setString(1, email);
           rs = pstmt.executeQuery();
           if (rs.next()) {
              check=1;
           }
           
        } catch (Exception e) {
           System.out.println("cartnullChk()메소드에서 오류 : " + e);
        } finally {
           closeConnection();
        }

        return check;
    }
}
