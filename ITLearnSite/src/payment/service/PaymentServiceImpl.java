package payment.service;

import java.util.List;

import lecture.db.PaylecBean;
import payment.db.PaymentBean;
import payment.db.PaymentDAOImpl;

public class PaymentServiceImpl implements PaymentService{

	PaymentDAOImpl pDao = new PaymentDAOImpl();
	
	//전체 주문 목록 
	@Override
	public List<PaymentBean> getPaymentlist() {
		List<PaymentBean> listPayment = pDao.getPaymentlist();
		return listPayment;
	}
	
	//회원 전체주문 확인
    @Override
    public List<PaymentBean> callPayment(String email){
    	List<PaymentBean> pBean = pDao.callPayment(email);
		return pBean;
    }
  	
  	//회원 주문 하기
    @Override
  	public void insertPayment(PaymentBean pBean){
    	pDao.insertPayment(pBean);		
    }
  	
  	//회원 주문 취소
    @Override
  	public void deletePayment(String email,int pay_no){
    	pDao.deletePayment(email,pay_no);		
    }
    
    //회원 결제 확인
    @Override
    public void updatePayment(int pay_no,int pay_option){
    	pDao.updatePayment(pay_no,pay_option);	
    }
    
    //결제 확인 시 강의리스트 insert
    @Override
    public PaylecBean setPay_lec(int pay_no){
    	PaylecBean plBean = pDao.setPay_lec(pay_no);
    	return plBean;
    }
    
    //주문 강의 중복 체크
    @Override
    public int payDupChk(String pro_name, String email){
    	int result;
    	result = pDao.payDupChk(pro_name,email);
    	return result;
    }
    
    //장바구니에 상품이 있는지 체크
    @Override
    public int cartnullChk(String email){
    	int result;
    	result = pDao.cartnullChk(email);
    	return result;
    }
    
    //주문 후 도서 재고 줄이기
    @Override
    public void deleteBookstock(int product_no,int book_stock){
    	pDao.deleteBookstock(product_no,book_stock);
    }
    
    //주문 후 도서 재고 원래대로 하기 위해 상품 주문 정보 가져오기
    @Override
    public PaymentBean selectPay(int pay_no){
    	PaymentBean pBean = new PaymentBean();
    	pBean = pDao.selectPay(pay_no);
    	return pBean;
    }

    
    //주문 취소 후 도서 재고 원래대로 
    @Override
    public void originBookstock(int product_no,int book_stock){
    	pDao.originBookstock(product_no,book_stock);
    }
  
}
