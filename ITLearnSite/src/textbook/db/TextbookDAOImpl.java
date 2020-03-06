package textbook.db;

import java.net.InetAddress;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class TextbookDAOImpl implements TextbookDAO {
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
	
	//도서 상세
	@Override
	public TextbookBean bookdetail(int product_no) {
		TextbookBean textBean = new TextbookBean();
		try {
			con = getConnection();
			sql = "select * from book_table where product_no = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, product_no);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				String decodeTitle = URLDecoder.decode(rs.getString(2),"utf-8");
				String decodelink = URLDecoder.decode(rs.getString(3),"utf-8");
				String decodeImage = URLDecoder.decode(rs.getString(4),"utf-8");
				String decodeDescription = URLDecoder.decode(rs.getString(11),"utf-8");
				textBean.setProduct_no(rs.getInt(1));
				textBean.setBook_title(decodeTitle);
				textBean.setBook_link(decodelink);
				textBean.setBook_image(decodeImage);
				textBean.setBook_author(rs.getString(5));
				textBean.setBook_price(rs.getInt(6));
				textBean.setBook_discount(rs.getInt(7));
				textBean.setBook_publisher(rs.getString(8));
				textBean.setBook_pubdate(String.valueOf(rs.getDate(9)));
				textBean.setBook_isbn(rs.getString(10));
				textBean.setBook_description(decodeDescription);
				textBean.setBook_stock(rs.getInt(12));
			}
		} catch (Exception e) {
		    System.out.println("bookdetail()메소드에서 오류  : " + e);
		} finally {
			closeConnection();
		}
		return textBean;
	}

	//도서 등록
	@Override
	public int insertBook(TextbookBean tBean) {
		int result = 0;
		try {
			con = getConnection();
			sql = "insert into book_table values(BOOK_PRODUCT_SEQ.nextval,?,?,?,?,?,?,?,To_Date(?, 'yyyymmdd'),?,?,?,'도서')";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, tBean.getBook_title());
			pstmt.setString(2, tBean.getBook_link());
			pstmt.setString(3, tBean.getBook_image());
			pstmt.setString(4, tBean.getBook_author());
			pstmt.setInt(5, tBean.getBook_price());
			pstmt.setInt(6, tBean.getBook_price());
			pstmt.setString(7, tBean.getBook_publisher());
			pstmt.setString(8, tBean.getBook_pubdate());
			pstmt.setString(9, tBean.getBook_isbn());
			pstmt.setString(10, tBean.getBook_description());
			pstmt.setInt(11, tBean.getBook_stock());

			result = pstmt.executeUpdate();

		} catch (Exception e) {
		    System.out.println("insertBook()메소드에서 오류  : " + e);
		} finally {
			closeConnection();
		}
		return result;
	}

	//도서리스트
	@Override
	public ArrayList<TextbookBean> selectBookList(int startRow, int endRow) {
		
		ArrayList<TextbookBean> list = new ArrayList<TextbookBean>();
		try {
			con = getConnection();
			sql = "select * from ("
					+ "select rownum rnum, book_table.* from"
					+ " (select * from book_table order by product_no desc) book_table)"
					+ " where rnum between ? and ?";
			pstmt = con.prepareStatement(sql);
			
			//num > pagenum 
			// 1 ~ 5번  num-1 + 1  +   ~ num-1 + 5
			// 6 ~ 10번 (num - 1) * 5  ~ 
			// 11 ~ 15번
			
			//page당 글 갯수
			int block = 5;
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			int i = 0;
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				TextbookBean textBean = new TextbookBean();
				textBean.setNum(rs.getInt(1));
				textBean.setProduct_no(rs.getInt(2));
				textBean.setBook_title(URLDecoder.decode(rs.getString(3),"utf-8"));
				textBean.setBook_link(URLDecoder.decode(rs.getString(4),"utf-8"));
				textBean.setBook_image(URLDecoder.decode(rs.getString(5),"utf-8"));
				textBean.setBook_author(rs.getString(6));
				textBean.setBook_price(rs.getInt(7));
				textBean.setBook_discount(rs.getInt(8));
				textBean.setBook_publisher(rs.getString(9));
				textBean.setBook_pubdate(String.valueOf(rs.getDate(10)));
				textBean.setBook_isbn(rs.getString(11));
				textBean.setBook_description(rs.getString(12));
				textBean.setBook_stock(rs.getInt(13));
				textBean.setProduct_type(rs.getString(14));
				list.add(textBean);
			}
		} catch (Exception e) {
		    System.out.println("selectBookList()메소드에서 오류  : " + e);
		} finally {
			closeConnection();
		}
		return list;
	}
	
	//도서 삭제
	@Override
	public int bookdelete(int product_no) {
		int check = 0;
		try {
			con = getConnection();
			sql = "delete from book_table where product_no=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, product_no);
			check = pstmt.executeUpdate();
			
		} catch (Exception e) {
		    System.out.println("bookdelete()메소드에서 오류  : " + e);
		} finally {
			closeConnection();
		}
		return check;
	}
	
	//도서 재고/가격 수정
	@Override
	public int stockmodify(int p_no, int price, int stock) {
		int check = 0;
		try {
			con = getConnection();
			sql = "update book_table set book_price = ?, book_stock = ? where product_no = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, price);
			pstmt.setInt(2, stock);
			pstmt.setInt(3, p_no);
			check = pstmt.executeUpdate();
		} catch (Exception e) {
		    System.out.println("stockmodify()메소드에서 오류  : " + e);
		} finally {
			closeConnection();
		}
		return check;
	}
	
	//전체 글 갯수
	@Override
	public int count() {
		int count = 0;
		try {
			con = getConnection();
			sql = "select count(*) from book_table";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
		    System.out.println("count()메소드에서 오류  : " + e);
		} finally {
			closeConnection();
		}
		return count;
	}
}

