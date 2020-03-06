package member.email.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import member.db.MemberBean;

public class MailDAOImpl implements MailDAO {
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
    
	//이메일 중복 체크
	@Override
	public int mailDupChk(MemberBean bean) {
		int check = 0;
		try {
			con = getConnection();
			sql = "SELECT COUNT(email) FROM MEMBER WHERE EMAIL=?";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, bean.getEmail());
			rs = pstmt.executeQuery();
		
			if(rs.next()){
				if(rs.getInt(1) == 1){
					check = 1;	
				}else{
					check = 0;
				}	
			}	
		}catch (Exception e){
		    System.out.println("mailDupChk()메소드에서 오류 : " +e);
		}finally{
			closeConnection();
		}
		
		return check;
		
	}
}
