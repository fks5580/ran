package comments.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class CommentsDAOImpl implements CommentsDAO {
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
    
    //댓글 번호
    @Override
    public int getCommentsNo() {
    	int check = 0;
    	try {
    		con = getConnection();
    	 	sql = "select max(co_no) from resource_comments";
    		pstmt = con.prepareStatement(sql);
    		rs = pstmt.executeQuery();
    		if(rs.next()){
        		check = rs.getInt(1);
    		}
    		
    	}
    	catch(Exception e){
    	    System.out.println("getCommentsNo()메소드에서 오류  : " +e);
    	}
    	finally{
    		closeConnection();
    	}
    	return check;
    }
    
    //댓글 쓰기
    @Override
    public int insertComments(CommentsBean cBean) {
    	int check = 0;
    	try {
			int co_no = getCommentsNo();
			con = getConnection();
			sql = "insert into resource_comments values(?,?,?,sysdate,?)";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, co_no+1);
			pstmt.setInt(2, cBean.getRes_no());
			pstmt.setString(3, cBean.getCo_email());
			pstmt.setString(4, cBean.getCo_content());
			
			check = pstmt.executeUpdate();
		} 
    	catch (Exception e) {
    	    System.out.println("insertComments()메소드에서 오류  : " +e);
		}
    	finally{
    		closeConnection();
    	}
    	return check;
    }
    
    //댓글 가져오기
    @Override
    public ArrayList<CommentsBean> selectCommentsList(CommentsBean cBean) {
    	ArrayList<CommentsBean> list = new  ArrayList<CommentsBean>();
    	try {
			con = getConnection();
			sql = "select * from resource_comments where res_no = ? order by co_date desc";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cBean.getRes_no());
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				CommentsBean bean = new CommentsBean();
				
				bean.setCo_no(rs.getInt(1));
				bean.setRes_no(rs.getInt(2));
				bean.setCo_email(rs.getString(3));
				bean.setCo_date(rs.getDate(4));
				bean.setCo_content(rs.getString(5));
				
				list.add(bean);
			}
			
		} 
    	catch (Exception e) {
    	    System.out.println("selectCommentsList()메소드에서 오류  : " +e);
		}
    	finally{
    		closeConnection();
    	}
    	return list;
    }
    
    //댓글 삭제
    @Override
    public int commentsDelete(int co_no, String email) {
    	int check = 0;
    	try {
    		
			con = getConnection();
			sql = "delete from resource_comments where co_no = ? and co_email= ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, co_no);
			pstmt.setString(2, email);
			check = pstmt.executeUpdate();
			if(check != 0) {
				check = 1;
			}
			else {
				check = 0;
			}
		} 
    	catch (Exception e) {
    	    System.out.println("commentsDelete()메소드에서 오류  : " +e);
		}
    	finally{
    		closeConnection();
    	}
    	return check;
    }
}
