package question.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import question.db.QuestionBean;
	
	public class QuestionDAOImpl implements QuestionDAO {
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
	    


	//새 글번호 검색
	private int getNewNo(){
		try {
			con = getConnection();
			String query = "SELECT  max(ques_no) from question_table ";
			System.out.println(query);
			pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery(query);
			if (rs.next())
				return (rs.getInt(1) + 1);
		} catch (Exception e) {
		    System.out.println("getNewNo()메소드에서 오류  : " +e);
		} finally{
			closeConnection();
		}
		return 0;
	}
	
	//글쓰기
	@Override
	public int insertQuestion(QuestionBean qBean){
		int ques_no = getNewNo();
		try {
			con = getConnection();
			int ques_parentno = qBean.getQues_parentno();
			String ques_title = qBean.getQues_title();
			String ques_email = qBean.getQues_email();
			String ques_content = qBean.getQues_content();
			int ques_readconut = 0;
			String isSecret = qBean.getIsSecret();
			String isNotice = qBean.getIsNotice();
			int ques_ref = qBean.getQues_ref(); 
			String ques_parentemail = qBean.getQues_parentemail();
	
			String query = "INSERT INTO question_table (QUES_NO, QUES_PARENTNO, QUES_TITLE, QUES_EMAIL, QUES_CONTENT, QUES_READCOUNT, ISSECRET, ISNOTICE, QUES_REF, QUES_PARENTEMAIL)"
					+ " VALUES (?, ? ,?, ?, ?, ?, ?, ?, ?, ?)";
			if(ques_parentno == 0) {
				ques_ref = ques_no;
				ques_parentemail = ques_email;
			}
			
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, ques_no);
			pstmt.setInt(2, ques_parentno);
			pstmt.setString(3, ques_title);
			pstmt.setString(4, ques_email);
			pstmt.setString(5, ques_content);
			pstmt.setInt(6, ques_readconut);
			pstmt.setString(7, isSecret);
			pstmt.setString(8, isNotice);
			pstmt.setInt(9, ques_ref);
			pstmt.setString(10, ques_parentemail);
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("insertQuestion()메소드에서 오류 :" + e);
		} finally{
			closeConnection();
		}
		return ques_no;
	}
	
	//글수정
	@Override
	public void updateQuestion(QuestionBean qBean){
		
		int ques_no = qBean.getQues_no();
		String ques_title = qBean.getQues_title();
		String ques_content = qBean.getQues_content();
		String isSecret = qBean.getIsSecret();
		
		try {
			con = getConnection();
			String query = "update question_table set ques_title=?, ques_content=?, isSecret=?"
						+ ", ques_writedate=sysdate where ques_no=?";

			pstmt = con.prepareStatement(query);
			pstmt.setString(1, ques_title);
			pstmt.setString(2, ques_content);
			pstmt.setString(3, isSecret);
			pstmt.setInt(4, ques_no);
			pstmt.executeUpdate();
			 
		} catch (Exception e) {
			System.out.println("updateQuestion()메소드에서 오류 :" + e);
		} finally{
			closeConnection();
		}
	}
	
	//글 내용 보기
	@Override
	public QuestionBean questionView(int ques_no) {
		QuestionBean qBean = new QuestionBean();
		try {
			con = getConnection();
			String sql = "select * from question_table where ques_no=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, ques_no);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				qBean.setQues_no(ques_no);
				qBean.setQues_title(rs.getString("ques_title"));
				qBean.setQues_email(rs.getString("ques_email"));
				qBean.setQues_content(rs.getString("ques_content"));
				qBean.setIsSecret(rs.getString("isSecret"));
				qBean.setQues_writedate(rs.getDate("ques_writedate"));
				qBean.setQues_readcount(rs.getInt("ques_readcount"));
			}

		} catch (Exception e) {
			System.out.println("questionView()메소드에서 오류 :" + e);
		} finally {
			closeConnection();
		}
		return qBean;
	}

	//글 삭제
	@Override
	public void questionDelete(int ques_no) {
		try {
			con = getConnection();
			String query = "Delete From question_table where ques_no = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, ques_no);
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("questionDelete()메소드에서 오류 :" + e);
		} finally {
			closeConnection();
		}
	}
	
	//공지글 가져오기
	@Override
	public List selectNotice() {
	    List questionsList1 = new ArrayList();
		try {
			con = getConnection();
			String query = "select * from question_table where isNotice = 'y'";

			pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int ques_no = rs.getInt("ques_no");
				String ques_title = rs.getString("ques_title");
				String ques_email = rs.getString("ques_email");
				Date ques_writedate = rs.getDate("ques_writedate");
				int ques_readcount = rs.getInt("ques_readcount");
				String isNotice = rs.getString("isNotice");
				String isSecret = rs.getString("isSecret");
					
				QuestionBean bean = new QuestionBean();

				bean.setQues_no(ques_no);
				bean.setQues_title(ques_title);
				bean.setQues_email(ques_email);
				bean.setQues_writedate(ques_writedate);
				bean.setQues_readcount(ques_readcount);
				bean.setIsNotice(isNotice);
				bean.setIsSecret(isSecret);
					
				questionsList1.add(bean);
			}

			} catch (Exception e) {
			    System.out.println("selectNotice()메소드에서 오류 :" + e);
			} finally {
				closeConnection();
			}
			return questionsList1;
		}
		
		
	//페이징 처리를 위해 공지사항 갯수 가져오기
	@Override
	public int countNotice(){
		try {
			con = getConnection();
			String query = "select count(*) from question_table where isNotice='y' ";
			pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery(query);
			if (rs.next())
					return (rs.getInt(1));
			} catch (Exception e) {
			    System.out.println("countNotice()메소드에서 오류 :" + e);
			} finally{
				closeConnection();
			}
			return 0;
		}
		
	//일반글 가져오기
	@Override
	public List selectQuestions(Map pagingMap, String opt, String condition) {
		List questionsList2 = new ArrayList();

		// 전달 받은 section 값과 pageNum 값을 가져옴
		int section = (Integer) pagingMap.get("section");
		int pageNum = (Integer) pagingMap.get("pageNum");
		int countNotice = countNotice();
		try {
			con = getConnection();
			if(opt == null || opt.isEmpty() == true ){
					String query = "select * from ( " + "select ROWNUM as recNum, lvl, "
					                + "ques_no, ques_parentno, ques_title, ques_email, ques_content, ques_writedate, ques_readcount, isSecret, isNotice, ques_ref, ques_parentemail " + "from (select level as lvl, "
									+ "ques_no, ques_parentno, ques_title, ques_email, ques_content, ques_writedate, ques_readcount, isSecret, isNotice, ques_ref, ques_parentemail " + "from question_table "
									+ "where isNotice = 'n'"
									+ "start with ques_parentno=0 "
									+ "connect by prior ques_no = ques_parentno " + "order siblings by ques_ref desc))"
									+ "where recNum between(?-1)*(10-?)*10+(?-1)*(10-?)+1 " + "and (?-1)*(10-?)*10+?*(10-?)";
					pstmt = con.prepareStatement(query);
					pstmt.setInt(1, section);
					pstmt.setInt(2, countNotice);
					pstmt.setInt(3, pageNum);
					pstmt.setInt(4, countNotice);
					pstmt.setInt(5, section);
					pstmt.setInt(6, countNotice);
					pstmt.setInt(7, pageNum);
					pstmt.setInt(8, countNotice);			
						
			}else{
					String query = "select * from ( " + "select ROWNUM as recNum, lvl, "
							        + "ques_no, ques_parentno, ques_title, ques_email, ques_content, ques_writedate, ques_readcount, isSecret, isNotice, ques_ref, ques_parentemail " + "from (select level as lvl, "
									+ "ques_no, ques_parentno, ques_title, ques_email, ques_content, ques_writedate, ques_readcount, isSecret, isNotice, ques_ref, ques_parentemail " + "from question_table "
									+ "where isNotice = 'n' and isSecret = 'n'"
									+ " and " + opt + " LIKE '%' || ? || '%' "
									+ "start with ques_parentno=0 "
									+ "connect by prior ques_no = ques_parentno " + "order siblings by ques_ref desc))"
									+ "where recNum between(?-1)*(10-?)*10+(?-1)*(10-?)+1 " + "and (?-1)*(10-?)*10+?*(10-?)";

					pstmt = con.prepareStatement(query);
					pstmt.setString(1, condition);
					pstmt.setInt(2, section);
					pstmt.setInt(3, countNotice);
					pstmt.setInt(4, pageNum);
					pstmt.setInt(5, countNotice);
					pstmt.setInt(6, section);
					pstmt.setInt(7, countNotice);
					pstmt.setInt(8, pageNum);
					pstmt.setInt(9, countNotice);		
							
			}
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				int level = rs.getInt("lvl");
				int ques_no = rs.getInt("ques_no");
				int ques_parentno = rs.getInt("ques_parentno");
				String ques_title = rs.getString("ques_title");
				String ques_email = rs.getString("ques_email");
				String ques_content = rs.getString("ques_content");
				Date ques_writedate = rs.getDate("ques_writedate");
				int ques_readcount = rs.getInt("ques_readcount");
				String isNotice = rs.getString("isNotice");
				String isSecret = rs.getString("isSecret");
				int ques_ref = rs.getInt("ques_ref");
				String ques_parentemail = rs.getString("ques_parentemail");
							
				QuestionBean bean = new QuestionBean();

				bean.setLevel(level);
				bean.setQues_no(ques_no);
				bean.setQues_parentno(ques_parentno);
				bean.setQues_title(ques_title);
				bean.setQues_email(ques_email);
				bean.setQues_content(ques_content);
				bean.setQues_writedate(ques_writedate);
				bean.setQues_readcount(ques_readcount);
				bean.setIsNotice(isNotice);
				bean.setIsSecret(isSecret);
				bean.setQues_ref(ques_ref);
				bean.setQues_parentemail(ques_parentemail);
							
				questionsList2.add(bean);
			}
					
		} catch (Exception e) {
		    System.out.println("selectQuestions()메소드에서 오류 :" + e);
		} finally {
			closeConnection();
		}
		return questionsList2;
	}
			
	//공지사항을 제외한 전체 글 개수
	@Override
	public int selectTotQuestions(String opt, String condition) {
	    try {
	        con = getConnection();
			if(opt == null || opt.isEmpty() == true ){
				String query = "select count(ques_no) from question_table where isNotice='n'";
				pstmt = con.prepareStatement(query);
			}else{
				String query = "select count(ques_no) from question_table where isNotice='n' and isSecret='n'"
								+ " and " + opt + " LIKE '%' || ? || '%' ";
				pstmt = con.prepareStatement(query);
				pstmt.setString(1, condition);
			}
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				return (rs.getInt(1));			
			}
		} catch (Exception e) {
		    System.out.println("selectTotQuestions()메소드에서 오류 :" + e);
		}finally {	
			closeConnection();		
		}
			return 0;	
		}

	//조회수 증가
	@Override
	public void updateReadcount(int ques_no){
		try {
			con = getConnection();
			sql = "update question_table set ques_readcount=ques_readcount+1 where ques_no=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, ques_no);	
			pstmt.executeUpdate();
			
		} catch (Exception e) {
            System.out.println("updateReadcount()메소드에서 오류 :" + e);
		} finally {
			closeConnection();	
		}
		
	}
	
	//자료실 검색
    @Override
    public ArrayList<QuestionBean> questionSelect(HashMap<String, Object> listOpt){	
    	 ArrayList<QuestionBean> QuestionList = new ArrayList<QuestionBean>();
    	 String opt=(String)listOpt.get("opt"); //검색옵션
    	 String condition = (String)listOpt.get("condition"); //검색내용
    	 try{
             con = getConnection();  
             //0:제목
             if(opt.equals("0")){
            	 sql="select * from question_table where ques_title LIKE '%' || ? || '%'";
            	 pstmt = con.prepareStatement(sql);
            	 pstmt.setString(1, condition);            	 
            	 rs=pstmt.executeQuery();            	 
             //1:내용
             }else if(opt.equals("1")){
            	 sql="select * from question_table where ques_content LIKE '%' || ? || '%'";
            	 pstmt = con.prepareStatement(sql);
            	 pstmt.setString(1, condition); 
            	 rs=pstmt.executeQuery(); 
             //2:글쓴이
             }else if(opt.equals("2")){
            	 sql="select * from question_table where ques_email LIKE '%' || ? || '%'";
            	 pstmt = con.prepareStatement(sql);
            	 pstmt.setString(1, condition); 
            	 rs=pstmt.executeQuery(); 
             }
             
             while(rs.next()){
                 QuestionBean qBean= new QuestionBean();
                 qBean.setQues_no(rs.getInt("ques_no"));
                 qBean.setQues_title(rs.getString("ques_title"));
                 qBean.setQues_email(rs.getString("ques_email"));
                 qBean.setQues_writedate(rs.getDate("ques_writedate"));             
                 QuestionList.add(qBean);
             }             
            
             }catch(Exception e){
                 System.out.println("questionSelect()메소드에서 오류 : " +e);
             }finally{
                 closeConnection();
             }
             return QuestionList;                    
         }
    
    //부모글 내용 가져오기
  	@Override
  	public QuestionBean getContent(int ques_no){
  		QuestionBean qBean = new QuestionBean();
  		try {
  			con = getConnection();
  			sql = "select ques_content from question_table where ques_no=?";
  			pstmt = con.prepareStatement(sql);
  			pstmt.setInt(1, ques_no);	
  			rs = pstmt.executeQuery();
  			if (rs.next()) {
  				qBean.setQues_content(rs.getString("ques_content"));}
  		} catch (Exception e) {
  		  System.out.println("getContent()메소드에서 오류 : " +e);
  		} finally {
  			closeConnection();	
  		}
  		return qBean;
  	}
    
    
}