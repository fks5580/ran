package textbookcomments.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class TextbookCommentsDAOImpl implements TextbookCommentsDAO {
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
    //후기 번호
    @Override
    public int getTextbookCommentsNo() {
    	int check = 0;
    	try {
    		con = getConnection();
    	 	sql = "select max(bo_no) from book_comments";
    		pstmt = con.prepareStatement(sql);
    		rs = pstmt.executeQuery();
    		
    		if(rs.next()){
        		check = rs.getInt(1);
    		}
    	}catch(Exception e){
    	    System.out.println("getTextbookCommentsNo()메소드에서 오류  : " +e);
    	}finally{
    		closeConnection();
    	}
    	return check;
    }
    
    //후기 작성
    @Override
    public int insertTextbookComments(TextbookCommentsBean tcBean) {
    	int check = 0;
    	try {
			int bo_no = getTextbookCommentsNo();
			con = getConnection();
			sql = "insert into book_comments values(?,?,?,sysdate,?,?)";
			pstmt = con.prepareStatement(sql);		
			pstmt.setInt(1, bo_no+1);
			pstmt.setInt(2, tcBean.getProduct_no());
			pstmt.setString(3,tcBean.getBo_email());
			pstmt.setString(4, tcBean.getBo_content());
			pstmt.setInt(5, tcBean.getBo_evaluation());			
			check = pstmt.executeUpdate();
			if(check == 1){
				check = 1;
			}
		}catch (Exception e) {
		    System.out.println("insertTextbookComments()메소드에서 오류  : " +e);
		}finally{
    		closeConnection();
    	}
    	return check;
    }
    //후기 가져오기
    @Override
    public ArrayList<TextbookCommentsBean> selectTextbookCommentsList(TextbookCommentsBean tcBean) {
    	ArrayList<TextbookCommentsBean> list = new  ArrayList<TextbookCommentsBean>();
    	try {
    		con = getConnection();
			sql = "select * from book_comments where product_no = ? order by bo_date desc";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, tcBean.getProduct_no());
			rs = pstmt.executeQuery();			
			while(rs.next()){
				tcBean = new TextbookCommentsBean();
				tcBean.setBo_no(rs.getInt(1));
				tcBean.setProduct_no(rs.getInt(2));
				tcBean.setBo_email(rs.getString(3));
				tcBean.setBo_date(rs.getDate(4));
				tcBean.setBo_content(rs.getString(5));
				tcBean.setBo_evaluation(rs.getInt(6));
				list.add(tcBean);
			}
			
		}catch (Exception e) {
		    System.out.println("selectTextbookCommentsList()메소드에서 오류  : " +e);
		}finally{
    		closeConnection();
    	}
    	return list;
    }
    
    //후기 삭제
    @Override
    public int TextbookcommentsDelete(int bo_no, String bo_email) {
    	int check = 0;
    	try {
    		con = getConnection();
			sql = "delete from book_comments where bo_no = ? and bo_email = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, bo_no);
			pstmt.setString(2, bo_email);
			check = pstmt.executeUpdate();
			if(check != 0) {
				check = 1;
			}else {
				check = 0;
			}
		} catch (Exception e) {
		    System.out.println("TextbookcommentsDelete()메소드에서 오류  : " +e);
		}finally{
    		closeConnection();
    	}
    	return check;
    }
}
