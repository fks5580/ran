package cart.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cart.db.CartBean;
import cart.db.CartDAOImpl;

public class CartServiceImpl implements CartService{
CartDAOImpl dao;

    public CartServiceImpl(){
    	dao= new CartDAOImpl();
    	
    }
    
    //장바구니 조회
	public ArrayList<CartBean> getcartlist(String email) {
		ArrayList<CartBean> cartlist=dao.getcartlist(email);
		return cartlist; 
	}
	
	//장바구니 상품 추가
	public int addCart(CartBean caBean){
		int result=dao.addCart(caBean);
		return result;
		
	}
	
	//장바구니 중복 체크
	public int cartDupChk(String pro_name, String email){	
		int result=dao.cartDupChk(pro_name, email);		
		return result;
	}
	
	//장바구니 상품 개수 수정
	public void cartEdit(int pro_cnt, int cart_num){
		dao.editCart(pro_cnt, cart_num);
	}
	
	//장바구니 상품 삭제
	public int delcart(int cart_num){
		int check=dao.delCart(cart_num);
		return check;
	}
	
	//장바구니 상품 전체 삭제
	public int delAllcart(String email){
		int dch=dao.delAllCart(email);
		return dch;
	}
	
	//장바구니 담긴 갯수 확인
    public int cartMaxChk(String email){
    	int result1 = dao.cartMaxChk(email);
    	return result1;
    }

}
