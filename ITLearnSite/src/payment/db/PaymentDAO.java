package payment.db;

import java.util.List;

import lecture.db.PaylecBean;

public interface PaymentDAO{
	
	//전체 주문 목록
	public List<PaymentBean> getPaymentlist();
	
	//회원 주문 확인
	public List<PaymentBean> callPayment(String email);
	
	//회원 주문 하기
	public void insertPayment(PaymentBean pBean);
	
	//회원 주문 취소
	public void deletePayment(String email,int pay_no);

	//회원 결제 확인
    public void updatePayment(int pay_no,int pay_option);
	
    //결제 확인 시 강의리스트 insert
    public PaylecBean setPay_lec(int pay_no);
    
    //주문 강의 중복 체크
    public int payDupChk(String pro_name, String email);
   
    //장바구니에 상품이 있는지 체크
    public int cartnullChk(String email);
    
    //주문 후 도서 재고 줄이기
    public void deleteBookstock(int product_no,int book_stock);
    
    //주문 후 도서 재고 원래대로 하기 위해 상품 주문 정보 가져오기
    public PaymentBean selectPay(int pay_no);
    
    //주문 취소 후 도서 재고 원래대로 
    public void originBookstock(int product_no,int book_stock);
}
