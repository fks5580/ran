package cart.db;

import java.util.List;

public interface CartDAO{
    //장바구니 조회
	public List<CartBean> getcartlist(String email);
	
	//장바구니 상품 추가
	public int addCart(CartBean cbean);
	
	//장바구니 중복 체크
	public int cartDupChk(String pro_name, String email);
	
	//장바구니 상품 개수 수정
	public void editCart(int pro_cnt, int cart_num);
	
	//장바구니 상품 전체 삭제
	public int delAllCart(String email);
	
	//장바구니 담긴 갯수 확인
	public int cartMaxChk(String email);
	
	//장바구니 상품 삭제
	public int delCart(int cart_num);
}
