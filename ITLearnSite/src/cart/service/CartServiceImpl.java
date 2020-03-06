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
	
	
	public ArrayList<CartBean> getcartlist(String email) {
		System.out.println("cartlist service");	
		ArrayList<CartBean> cartlist=dao.getcartlist(email);
		return cartlist; 
	}
	
	public int addCart(CartBean caBean){
		int result=dao.addCart(caBean);
		return result;
		
	}
	
	public int cartDupChk(String pro_name, String email){
		System.out.println("cartDupChk");		
		int result=dao.cartDupChk(pro_name, email);		
		return result;
	}
	

	
	public void cartEdit(int pro_cnt, int cart_num){
		System.out.println("cartEdit");		
		dao.editCart(pro_cnt, cart_num);
	
		
	}
	public int Delcart(int cart_num){
		System.out.println("Delcart");
		int check=dao.delCart(cart_num);
		return check;
	}
	
	public int DelAllcart(String email){
		System.out.println("delallcart");
		int dch=dao.delAllCart(email);
		return dch;
	}
	
    public int cartMaxChk(String email){
    	int result1 = dao.cartMaxChk(email);
    	return result1;
    }

}
