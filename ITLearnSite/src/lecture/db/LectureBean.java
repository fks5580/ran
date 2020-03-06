package lecture.db;

import java.sql.Date;
import java.util.ArrayList;

public class LectureBean {
	
	private int product_no;
	private String product_type;
	
	private int lec_no, level;
	private int lec_parentno;
	private String lec_title;
	private String lec_teacher;
	private int lec_price;
	private String lec_content;
	private String lec_imgfile;
	private String lec_spofile;
	private Date lec_uploaddate;

	private int list_no;
	private ArrayList list_title, saveFiles, originFiles;
	private String list_titleStr, list_savefile, list_originalfile;

	
	

	public String getLec_teacher() {
		return lec_teacher;
	}

	public void setLec_teacher(String lec_teacher) {
		this.lec_teacher = lec_teacher;
	}

	public String getList_titleStr() {
		return list_titleStr;
	}

	public void setList_titleStr(String list_titleStr) {
		this.list_titleStr = list_titleStr;
	}

	public String getList_savefile() {
		return list_savefile;
	}

	public void setList_savefile(String list_savefile) {
		this.list_savefile = list_savefile;
	}

	public String getList_originalfile() {
		return list_originalfile;
	}

	public void setList_originalfile(String list_originalfile) {
		this.list_originalfile = list_originalfile;
	}

	public int getLec_no() {
		return lec_no;
	}

	public void setLec_no(int lec_no) {
		this.lec_no = lec_no;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getLec_parentno() {
		return lec_parentno;
	}

	public void setLec_parentno(int lec_parentno) {
		this.lec_parentno = lec_parentno;
	}

	public String getLec_title() {
		return lec_title;
	}

	public void setLec_title(String lec_title) {
		this.lec_title = lec_title;
	}

	public int getLec_price() {
		return lec_price;
	}

	public void setLec_price(int lec_price) {
		this.lec_price = lec_price;
	}

	public String getLec_content() {
		return lec_content;
	}

	public void setLec_content(String lec_content) {
		this.lec_content = lec_content;
	}

	public String getLec_imgfile() {
		return lec_imgfile;
	}

	public void setLec_imgfile(String lec_imgfile) {
		this.lec_imgfile = lec_imgfile;
	}

	public String getLec_spofile() {
		return lec_spofile;
	}

	public void setLec_spofile(String lec_spofile) {
		this.lec_spofile = lec_spofile;
	}

	public Date getLec_uploaddate() {
		return lec_uploaddate;
	}

	public void setLec_uploaddate(Date lec_uploaddate) {
		this.lec_uploaddate = lec_uploaddate;
	}

	public int getList_no() {
		return list_no;
	}

	public void setList_no(int list_no) {
		this.list_no = list_no;
	}

	public ArrayList getList_title() {
		return list_title;
	}

	public void setList_title(ArrayList list_title) {
		this.list_title = list_title;
	}

	public ArrayList getSaveFiles() {
		return saveFiles;
	}

	public void setSaveFiles(ArrayList saveFiles) {
		this.saveFiles = saveFiles;
	}

	public ArrayList getOriginFiles() {
		return originFiles;
	}

	public void setOriginFiles(ArrayList originFiles) {
		this.originFiles = originFiles;
	}

	public int getProduct_no() {
		return product_no;
	}

	public void setProduct_no(int product_no) {
		this.product_no = product_no;
	}

	public String getProduct_type() {
		return product_type;
	}

	public void setProduct_type(String product_type) {
		this.product_type = product_type;
	}

}
