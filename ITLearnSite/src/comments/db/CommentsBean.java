package comments.db;

import java.sql.Date;

public class CommentsBean {
	private int co_no;
	private int  res_no;
	private String co_email;
	private Date co_date;
	private String co_content;
	
	public int getCo_no() {
		return co_no;
	}
	public void setCo_no(int co_no) {
		this.co_no = co_no;
	}
	public int getRes_no() {
		return res_no;
	}
	public void setRes_no(int res_no) {
		this.res_no = res_no;
	}
	public String getCo_email() {
		return co_email;
	}
	public void setCo_email(String co_email) {
		this.co_email = co_email;
	}
	public Date getCo_date() {
		return co_date;
	}
	public void setCo_date(Date co_date) {
		this.co_date = co_date;
	}
	public String getCo_content() {
		return co_content;
	}
	public void setCo_content(String co_content) {
		this.co_content = co_content;
	}
	
}
