package question.db;

import java.sql.Date;
public class QuestionBean {

	private int ques_no, ques_parentno, level;
	private String ques_title;
	private String  ques_email;
	private String  ques_content;
	private String isSecret, isNotice;
	private Date ques_writedate;
	private int  ques_readcount;
	private int ques_ref;
	private String ques_parentemail;   
		
	
	public String getQues_parentemail() {
		return ques_parentemail;
	}
	public void setQues_parentemail(String ques_parentemail) {
		this.ques_parentemail = ques_parentemail;
	}
	public int getQues_ref() {
		return ques_ref;
	}
	public void setQues_ref(int ques_ref) {
		this.ques_ref = ques_ref;
	}
	public int getQues_no() {
		return ques_no;
	}
	public void setQues_no(int ques_no) {
		this.ques_no = ques_no;
	}
	public int getQues_parentno() {
		return ques_parentno;
	}
	public void setQues_parentno(int ques_parentno) {
		this.ques_parentno = ques_parentno;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getQues_title() {
		return ques_title;
	}
	public void setQues_title(String ques_title) {
		this.ques_title = ques_title;
	}
	public String getQues_email() {
		return ques_email;
	}
	public void setQues_email(String ques_email) {
		this.ques_email = ques_email;
	}
	public String getQues_content() {
		return ques_content;
	}
	public void setQues_content(String ques_content) {
		this.ques_content = ques_content;
	}
	public String getIsSecret() {
		return isSecret;
	}
	public void setIsSecret(String isSecret) {
		this.isSecret = isSecret;
	}
	public String getIsNotice() {
		return isNotice;
	}
	public void setIsNotice(String isNotice) {
		this.isNotice = isNotice;
	}
	public Date getQues_writedate() {
		return ques_writedate;
	}
	public void setQues_writedate(Date ques_writedate) {
		this.ques_writedate = ques_writedate;
	}
	public int getQues_readcount() {
		return ques_readcount;
	}
	public void setQues_readcount(int ques_readcount) {
		this.ques_readcount = ques_readcount;
	}
	
}
