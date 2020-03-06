package lecture.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class LectureDAOImpl implements LectureDAO {
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

	//강의실 목록
	@Override
	public List selectAllLectures(Map pagingMap) {
		List lecturesList = new ArrayList();

		// 전달 받은 section 값과 pageNum 값을 가져옴
		int section = (Integer) pagingMap.get("section");
		int pageNum = (Integer) pagingMap.get("pageNum");
		try {

			con = getConnection();
			sql = "select * from ( " + "select ROWNUM as recNum, lvl, "
					+ "product_no, lec_no, lec_parentno, lec_title, lec_price, lec_teacher, lec_imgfile, lec_spofile, product_type "
					+ "from (select level as lvl, "
					+ "product_no, lec_no, lec_parentno, lec_title, lec_price, lec_teacher, lec_imgfile, lec_spofile, product_type " + "from lecture_table "
					+ "start with lec_parentno=0 " + "connect by prior lec_no = lec_parentno "
					+ "order siblings by lec_no desc))" + " where recNum between(?-1)*30+(?-1)*3+1 "
					+ "and (?-1)*30+?*3";
	
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, section);
			pstmt.setInt(2, pageNum);
			pstmt.setInt(3, section);
			pstmt.setInt(4, pageNum);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int product_no = rs.getInt("product_no");
				int level = rs.getInt("lvl");
				int lec_no = rs.getInt("lec_no");
				int lec_parentno = rs.getInt("lec_parentno");
				String lec_title = rs.getString("lec_title");
				int lec_price = rs.getInt("lec_price");
				String lec_teacher = rs.getString("lec_teacher");
				String lec_imgfile = rs.getString("lec_imgfile");
				String lec_spofile = rs.getString("lec_spofile");
				String product_type = rs.getString("product_type");

				LectureBean bean = new LectureBean();
				bean.setProduct_no(product_no);
				bean.setLevel(level);
				bean.setLec_no(lec_no);
				bean.setLec_parentno(lec_parentno);
				bean.setLec_title(lec_title);
				bean.setLec_price(lec_price);
				bean.setLec_teacher(lec_teacher);
				bean.setLec_imgfile(lec_imgfile);
				bean.setLec_spofile(lec_spofile);
				bean.setProduct_type(product_type);

				lecturesList.add(bean);
			
			}

		} catch (Exception e) {
		    System.out.println("selectAllLectures()메소드에서 오류  : " + e);
		} finally {
			closeConnection();
		}

		return lecturesList;
	}

	// 전체 글 개수
	@Override
	public int selectTotLectures() {
		try {
			con = getConnection();

			sql = "select count(lec_no) from lecture_table";
			pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return (rs.getInt(1));
			}

		} catch (Exception e) {
		    System.out.println("selectTotLectures()메소드에서 오류  : " + e);
		} finally {
			closeConnection();
		}
		return 0;
	}

	//강의 등록
	@Override
	public void lectureRegister(LectureBean lBean) {

		try {
			con = getConnection();
			// 파일 이름 테이블에 데이터를 넣기 위한 변수
			ArrayList<String> saveFiles = lBean.getSaveFiles();
			ArrayList<String> originFiles = lBean.getOriginFiles();
			ArrayList<String> list_title = lBean.getList_title();

			sql = "select count(*) from lecture_table";
			pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			int lec_no = rs.getInt(1) + 1;

			sql = "insert into lecture_table(product_no, lec_no, lec_title, lec_price, lec_teacher, lec_content, lec_imgfile, lec_spofile, product_type) "
					+ "values(BOOK_PRODUCT_SEQ.nextval,?,?,?,?,?,?,?,'강의')";

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, lec_no);
			pstmt.setString(2, lBean.getLec_title());
			pstmt.setInt(3, lBean.getLec_price());
			pstmt.setString(4, lBean.getLec_teacher());
			pstmt.setString(5, lBean.getLec_content());
			pstmt.setString(6, saveFiles.get(0));
			pstmt.setString(7, saveFiles.get(1));
			pstmt.executeUpdate();

			sql = "insert into lecture_list(lec_no, list_no, lec_title, list_title, list_savefile, list_originalfile) values(?,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);

			for (int i = 1; i < saveFiles.size(); i++) {
				pstmt.setInt(1, lec_no);
				pstmt.setInt(2, i);
				pstmt.setString(3, lBean.getLec_title());
				pstmt.setString(4, list_title.get(i - 1));
				pstmt.setString(5, saveFiles.get(i));
				pstmt.setString(6, originFiles.get(i));
				pstmt.executeUpdate();
			}

		} catch (Exception e) {
		    System.out.println("lectureRegister()메소드에서 오류  : " + e);
		} finally {
			closeConnection();
		}
	}

	//강의 정보
	@Override
	public LectureBean lectureDetail(int lec_no) {

		LectureBean bean = new LectureBean();

		try {
			con = getConnection();
			sql = "select * from lecture_table  where lec_no = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, lec_no);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				bean.setLec_no(rs.getInt("lec_no"));
				bean.setLec_title(rs.getString("lec_title"));
				bean.setLec_price(rs.getInt("lec_price"));
				bean.setLec_teacher(rs.getString("lec_teacher"));
				bean.setLec_content(rs.getString("lec_content"));
				bean.setLec_imgfile(rs.getString("lec_imgfile"));
			}

		} catch (Exception e) {
		    System.out.println("lectureDetail()메소드에서 오류  : " + e);
		} finally {
			closeConnection();
		}

		return bean;
	}

	//강의 정보
	@Override
	public LectureBean lectureDetail(String lec_title) {

		LectureBean bean = new LectureBean();

		try {
			con = getConnection();
			sql = "select * from lecture_table  where lec_title = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, lec_title);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				bean.setLec_no(rs.getInt("lec_no"));
				bean.setLec_title(rs.getString("lec_title"));
				bean.setLec_price(rs.getInt("lec_price"));
				bean.setLec_teacher(rs.getString("lec_teacher"));
				bean.setLec_content(rs.getString("lec_content"));
				bean.setLec_imgfile(rs.getString("lec_imgfile"));
				bean.setLec_spofile(rs.getString("lec_spofile"));
			}

		} catch (Exception e) {
		    System.out.println("lectureDetail()메소드에서 오류  : " + e);
		} finally {
			closeConnection();
		}
		return bean;
	}

	//강의 상세 리스트(파일 저장 테이블)
	@Override
	public List lectureList(String lec_title) {

		List<LectureBean> lectureList = new ArrayList<LectureBean>();

		try {
			con = getConnection();
			sql = "select * from lecture_list where lec_title = ? order by list_no";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, lec_title);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int list_no = rs.getInt("list_no");
				int lec_no = rs.getInt("lec_no");
				lec_title = rs.getString("lec_title");
				String list_titleStr = rs.getString("list_title");
				String list_savefile = rs.getString("list_savefile");
				String list_originalfile = rs.getString("list_originalfile");

				LectureBean bean = new LectureBean();

				bean.setList_no(list_no);
				bean.setLec_no(lec_no);
				bean.setLec_title(lec_title);
				bean.setList_titleStr(list_titleStr);
				bean.setList_savefile(list_savefile);
				bean.setList_originalfile(list_originalfile);

				lectureList.add(bean);

			}

		} catch (Exception e) {
		    System.out.println("lectureList()메소드에서 오류  : " + e);
		} finally {
			closeConnection();
		}
		return lectureList;
	}

	//강의 상세 리스트(파일 저장 테이블)
	@Override
	public List lectureList(int lec_no) {

		List<LectureBean> lectureList = new ArrayList<LectureBean>();

		try {
			con = getConnection();
			sql = "select * from lecture_list where lec_no = ? order by list_no";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, lec_no);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {

				int list_no = rs.getInt("list_no");
				String lec_title = rs.getString("lec_title");
				String list_titleStr = rs.getString("list_title");
				String list_savefile = rs.getString("list_savefile");
				String list_originalfile = rs.getString("list_originalfile");

				LectureBean bean = new LectureBean();

				bean.setList_no(list_no);
				bean.setLec_title(lec_title);
				bean.setList_titleStr(list_titleStr);
				bean.setList_savefile(list_savefile);
				bean.setList_originalfile(list_originalfile);

				lectureList.add(bean);

			}

		} catch (Exception e) {
		    System.out.println("lectureList()메소드에서 오류  : " + e);
		} finally {
			closeConnection();
		}
		return lectureList;
	}

	//강의 삭제
	@Override
	public void deleteLecture(int lec_no) {
		try {
			con = getConnection();
			sql = "delete from lecture_table where lec_no = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, lec_no);
			pstmt.executeUpdate();

		} catch (Exception e) {
		    System.out.println("deleteLecture()메소드에서 오류  : " + e);
		} finally {
		    closeConnection();
		}
	}

	//내 강의실
	@Override
	public List myLecture(String email) {
		List mylist = new ArrayList();
		try {
			con = getConnection();
			sql = "select set_lec_title, round(set_endDate - sysdate) from set_pay_lec_table where set_email = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				PaylecBean bean = new PaylecBean();
				bean.setSet_lec_title(rs.getString(1));
				bean.setBetween(rs.getInt(2));
				mylist.add(bean);
			}
		} catch (Exception e) {
		    System.out.println("myLecture()메소드에서 오류  : " + e);
		} finally {
			closeConnection();
		}
		return mylist;
	}

	//댓글 번호 
	@Override
	public int getCommentsNo() {
		int check = 0;
		try {
			con = getConnection();
			sql = "select max(co_no) from lecture_comment";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				check = rs.getInt(1);
			}
		} catch (Exception e) {
		    System.out.println("getCommentsNo()메소드에서 오류  : " + e);
		} finally {
			closeConnection();
		}
		return check;
	}

	//댓글 작성
	@Override
	public int insertComments(CommentsBean cBean) {
		int check = 0;
		try {
			int co_no = getCommentsNo();
			con = getConnection();
			sql = "insert into lecture_comment(co_no, list_no, lec_no, co_email, co_content) values(?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, co_no + 1);
			pstmt.setInt(2, cBean.getList_no());
			pstmt.setInt(3, cBean.getLec_no());
			pstmt.setString(4, cBean.getCo_email());
			pstmt.setString(5, cBean.getCo_content());

			check = pstmt.executeUpdate();
		} catch (Exception e) {
		    System.out.println("insertComments()메소드에서 오류  : " + e);
		} finally {
			closeConnection();
		}
		return check;
	}

	//댓글 가져오기
	@Override
	public ArrayList<CommentsBean> selectCommentsList(CommentsBean cBean) {
		ArrayList<CommentsBean> list = new ArrayList<CommentsBean>();
		try {
			con = getConnection();
			sql = "select * from lecture_comment where list_no = ? and lec_no = ? order by co_date desc";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cBean.getList_no());
			pstmt.setInt(2, cBean.getLec_no());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				CommentsBean bean = new CommentsBean();
				bean.setCo_no(rs.getInt(1));
				bean.setList_no(rs.getInt(2));
				bean.setLec_no(rs.getInt(3));
				bean.setCo_email(rs.getString(4));
				bean.setCo_date(rs.getDate(5));
				bean.setCo_content(rs.getString(6));
				list.add(bean);
			}

		} catch (Exception e) {
		    System.out.println("selectCommentsList()메소드에서 오류  : " + e);
		} finally {
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
			sql = "delete from lecture_comment where co_no = ? and co_email= ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, co_no);
			pstmt.setString(2, email);
			check = pstmt.executeUpdate();
			if (check != 0) {
				check = 1;
			} else {
				check = 0;
			}
		} catch (Exception e) {
		    System.out.println("commentsDelete()메소드에서 오류  : " + e);
		} finally {
			closeConnection();
		}
		return check;
	}

}
