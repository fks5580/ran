package cart.db;

public class CartBean {
	private int cart_num;
	private String email;
	private int product_no;
	private String pro_name;
	private int pro_cnt;
	private int pro_price;
	private String pro_sort;
	private String pro_img;
	
	public String getPro_img() {
		return pro_img;
	}
	public void setPro_img(String pro_img) {
		this.pro_img = pro_img;
	}
	public String getPro_sort() {
		return pro_sort;
	}
	public void setPro_sort(String pro_sort) {
		this.pro_sort = pro_sort;
	}
	public int getPro_price() {
		return pro_price;
	}
	public void setPro_price(int pro_price) {
		this.pro_price = pro_price;
	}
	
	public int getCart_num() {
		return cart_num;
	}
	public void setCart_num(int cart_num) {
		this.cart_num = cart_num;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPro_name() {
		return pro_name;
	}
	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}
	public int getPro_cnt() {
		return pro_cnt;
	}
	public void setPro_cnt(int pro_cnt) {
		this.pro_cnt = pro_cnt;
	}
	public int getProduct_no() {
		return product_no;
	}
	public void setProduct_no(int product_no) {
		this.product_no = product_no;
	}	
}