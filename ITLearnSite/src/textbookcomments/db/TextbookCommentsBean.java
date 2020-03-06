package textbookcomments.db;

import java.sql.Date;

public class TextbookCommentsBean {
	private int bo_no;
	private int  product_no;
	private String bo_email;
	private Date bo_date;
	private String bo_content;
	private int bo_evaluation;
	public int getBo_no() {
		return bo_no;
	}
	public void setBo_no(int bo_no) {
		this.bo_no = bo_no;
	}
	public int getProduct_no() {
		return product_no;
	}
	public void setProduct_no(int product_no) {
		this.product_no = product_no;
	}
	public String getBo_email() {
		return bo_email;
	}
	public void setBo_email(String bo_email) {
		this.bo_email = bo_email;
	}
	public Date getBo_date() {
		return bo_date;
	}
	public void setBo_date(Date bo_date) {
		this.bo_date = bo_date;
	}
	public String getBo_content() {
		return bo_content;
	}
	public void setBo_content(String bo_content) {
		this.bo_content = bo_content;
	}
	public int getBo_evaluation() {
		return bo_evaluation;
	}
	public void setBo_evaluation(int bo_evaluation) {
		this.bo_evaluation = bo_evaluation;
	}
	
	
}
