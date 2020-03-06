package resource.db;

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

public class ResourceDAOImpl implements ResourceDAO {
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
			System.out.println("closeConnection()메소드에서 오류 : " + e);
		}
	}

	

	//새 글번호 검색
	private int getNewNo(){
		try {
			con = getConnection();
			String query = "SELECT  max(res_no) from resource_table ";
			pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery(query);
			if (rs.next())
				return (rs.getInt(1) + 1);
		} catch (Exception e) {
		    System.out.println("getNewNo()메소드에서 오류 : " + e);
		} finally{
			closeConnection();
		}
		return 0;
	}
	
	//자료실 글쓰기
	@Override
	public int insertResource(ResourceBean rBean){
		int res_no = getNewNo();
		try {
			con = getConnection();
			int res_parentno = rBean.getRes_parentno();
			String res_title = rBean.getRes_title();
			String res_email = rBean.getRes_email();
			String res_content = rBean.getRes_content();
			String res_filename = rBean.getRes_filename();
			
			String query = "INSERT INTO resource_table (RES_NO, RES_PARENTNO, RES_TITLE, RES_EMAIL, RES_CONTENT, RES_FILENAME)"
					+ " VALUES (?, ? ,?, ?, ?, ?)";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, res_no);
			pstmt.setInt(2, res_parentno);
			pstmt.setString(3, res_title);
			pstmt.setString(4, res_email);
			pstmt.setString(5, res_content);
			pstmt.setString(6, res_filename);
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("insertResource()메소드에서 오류 :" + e);
		} finally{
			closeConnection();
		}
		return res_no;
	}
	
	
	//자료실 글수정
	@Override
	public void updateResource(ResourceBean rBean){
		
		int res_no = rBean.getRes_no();
		String res_title = rBean.getRes_title();
		String res_content = rBean.getRes_content();
		String res_filename = rBean.getRes_filename();
		
		try {
			con = getConnection();
			String query = "update resource_table set res_title=?,res_content=?";
			if (res_filename != null && res_filename.length() != 0) {
			    query += ",res_filename=?";
			}
				query += ",res_writedate=sysdate where res_no=?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, res_title);
			pstmt.setString(2, res_content);
			
			if (res_filename != null && res_filename.length() != 0) {
				pstmt.setString(3, res_filename);
				pstmt.setInt(4, res_no);
			} else {
				pstmt.setInt(3, res_no);
			}
			 pstmt.executeUpdate();
			 
		} catch (Exception e) {
			System.out.println("updateResource()메소드에서 오류 :" + e);
		} finally{
			closeConnection();
		}
	}

	// 자료실 내용
	@Override
	public ResourceBean resourceView(int res_no) {
		ResourceBean rBean = new ResourceBean();
		try {
			con = getConnection();
			String sql = "select * from resource_table where res_no=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, res_no);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				rBean.setRes_no(res_no);
				rBean.setRes_title(rs.getString("res_title"));
				rBean.setRes_email(rs.getString("res_email"));
				rBean.setRes_content(rs.getString("res_content"));
				rBean.setRes_filename(rs.getString("res_filename"));
				rBean.setRes_writedate(rs.getDate("res_writedate"));
			}

		} catch (Exception e) {
			System.out.println("resourceView()메소드에서 오류 :" + e);
		} finally {
			closeConnection();
		}
		return rBean;
	}

	// 자료실 내용 삭제
	@Override
	public void resourceDelete(int res_no) {
		try {
			// 커넥션풀로부터 커넥션 얻기
			con = getConnection();
			String query = "Delete From resource_table where res_no = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, res_no);
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("resourceDelete()메소드에서 오류 :" + e);
		} finally {
			closeConnection();
		}

	}

	// 자료실 목록
	@Override
	public List selectAllResources(Map pagingMap,String opt, String condition) {
		List resoursesList = new ArrayList();

		int section = (Integer) pagingMap.get("section");
		int pageNum = (Integer) pagingMap.get("pageNum");

		try {
			con = getConnection();
			if(opt == null || opt.isEmpty() == true ){
			    sql = "select * from ( " + "select ROWNUM as recNum, lvl, "
					+ "res_no, res_parentno, res_title, res_content, res_email, res_writedate " + "from (select level as lvl, "
					+ "res_no, res_parentno, res_title, res_content, res_email, res_writedate " + "from resource_table " + "start with res_parentno=0 "
					+ "connect by prior res_no = res_parentno " + "order siblings by res_no desc))"
					+ "where recNum between(?-1)*100+(?-1)*10+1 " + "and (?-1)*100+?*10";
			//" where " + opt + " LIKE '%' || ? || '%'" and 가능
			// section과 pageNum 값으로 레코드 번호의 범위를 조건으로 정한
			// (이들 값이 각각 1로 전송 시, between 1 and 10이 됨)

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, section);
			pstmt.setInt(2, pageNum);
			pstmt.setInt(3, section);
			pstmt.setInt(4, pageNum);
			
			}else {
				
				sql = "select * from ( " + "select ROWNUM as recNum, lvl, "
					+ "res_no, res_parentno, res_title, res_content, res_email, res_writedate " + "from (select level as lvl, "
					+ "res_no, res_parentno, res_title, res_content, res_email, res_writedate " + "from resource_table " + "start with res_parentno=0 "
					+ "connect by prior res_no = res_parentno " + "order siblings by res_no desc))"
					+ " where " + opt + " LIKE '%' || ? || '%' "
					+ "and recNum between(?-1)*100+(?-1)*10+1 " + "and (?-1)*100+?*10";

				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, condition);
				pstmt.setInt(2, section);
				pstmt.setInt(3, pageNum);
				pstmt.setInt(4, section);
				pstmt.setInt(5, pageNum);
			}
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int level = rs.getInt("lvl");
				int res_no = rs.getInt("res_no");
				int res_parentno = rs.getInt("res_parentno");
				String res_title = rs.getString("res_title");
				String res_email = rs.getString("res_email");
				Date res_writedate = rs.getDate("res_writedate");
				
				ResourceBean bean = new ResourceBean();

				bean.setLevel(level);
				bean.setRes_no(res_no);
				bean.setRes_parentno(res_parentno);
				bean.setRes_title(res_title);
				bean.setRes_email(res_email);
				bean.setRes_writedate(res_writedate);

				resoursesList.add(bean);	
			}
		} catch (Exception e) {
		    System.out.println("selectAllResources()메소드에서 오류 :" + e);
		} finally {
			closeConnection();
		}

		return resoursesList;
	}
	
	//전체 글 개수
	@Override
	public int selectTotResources(String opt, String condition) {
		try {
			con = getConnection();
			
			if(opt == null || opt.isEmpty() == true ){
			    sql = "select count(res_no) from resource_table";
			    pstmt = con.prepareStatement(sql);
			}else {
				sql = "select count(res_no) from resource_table"
						+ " where " + opt + " LIKE '%' || ? || '%' ";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, condition);
			}
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				return (rs.getInt(1));				
			}
								
		} catch (Exception e) {
		    System.out.println("selectTotResources()메소드에서 오류 :" + e);
		}finally {	
			closeConnection();		
		}
		return 0;	
	}
	
	//자료실 검색
    @Override
    public ArrayList<ResourceBean> resourceSelect(HashMap<String, Object> listOpt){
    	
    	 ArrayList<ResourceBean> ResourceList = new ArrayList<ResourceBean>();
    	 String opt=(String)listOpt.get("opt"); //검색옵션	 
    	 String condition = (String)listOpt.get("condition"); //검색내용
    	 try{
             con = getConnection();
             sql="select * from resource_table where " + opt + " LIKE '%' || ? || '%'";
        	 pstmt = con.prepareStatement(sql);
        	 pstmt.setString(1, condition);            	 
        	 rs=pstmt.executeQuery();
              
             while(rs.next()){
                 ResourceBean rBean= new ResourceBean();
                 rBean.setRes_no(rs.getInt("res_no"));
                 rBean.setRes_title(rs.getString("res_title"));
                 rBean.setRes_email(rs.getString("res_email"));
                 rBean.setRes_writedate(rs.getDate("res_writedate"));             
                 ResourceList.add(rBean);
             }             
            
             }catch(Exception e){
                 System.out.println("resourceSelect()메소드에서 오류 : " +e);
             }finally{
                 closeConnection();
             }
             return ResourceList;                    
         }
}




