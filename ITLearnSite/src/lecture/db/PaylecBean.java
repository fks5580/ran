package lecture.db;

import java.sql.Date;

public class PaylecBean {
	private int set_no;
	private String set_email;
	private String set_lec_title;
	private Date set_startDate;
	private int between;
	
	public int getBetween() {
		return between;
	}
	public void setBetween(int between) {
		this.between = between;
	}
	public int getSet_no() {
		return set_no;
	}
	public void setSet_no(int set_no) {
		this.set_no = set_no;
	}
	public String getSet_email() {
		return set_email;
	}
	public void setSet_email(String set_email) {
		this.set_email = set_email;
	}
	public String getSet_lec_title() {
		return set_lec_title;
	}
	public void setSet_lec_title(String set_lec_title) {
		this.set_lec_title = set_lec_title;
	}
	public Date getSet_startDate() {
		return set_startDate;
	}
	public void setSet_startDate(Date set_startDate) {
		this.set_startDate = set_startDate;
	}
}
