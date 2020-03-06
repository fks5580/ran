<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%--JSTL CORE라이브러리 태그들 사용을 위한 선언 --%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>     
<c:set var="path" value="${pageContext.request.contextPath}"></c:set> 
  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script type="text/javascript">
	function origin_delivery(){
		document.getElementById("pay_name").value ="${mBean.name}";
		document.getElementById("pay_phonenumber").value = "${mBean.phonenumber}";
		document.getElementById("address").value = "${mBean.address}";
		document.getElementById("address1").value = "${mBean.address1}";
		document.getElementById("address2").value = "${mBean.address2}";
	}
	
	function new_delivery(){
		document.getElementById("pay_name").value = "";
		document.getElementById("pay_phonenumber").value = "";
		document.getElementById("address").value = "";
		document.getElementById("address1").value = "";
		document.getElementById("address2").value = "";
	
	}
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${path}/css/member.css" rel="stylesheet"> 
<link href="${path}/css/style.css" rel="stylesheet"> 
<link href="${path}/css/create.css" rel="stylesheet"> 
<title>결제 페이지</title>
<style type="text/css">
 @font-face { font-family: 'NIXGONM-Vb'; src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_six@1.2/NIXGONM-Vb.woff') format('woff'); font-weight: normal; font-style: normal; }
h5{
font-family: 'NIXGONM-Vb';}
</style>
</head>
<c:set var="email" value="${sessionScope.email}"></c:set>
<c:if test="${email eq null}">
	<script type="text/javascript">
		alert("로그인 후 결제 가능합니다.");
		location.href="${path}/index.do";
	</script>
</c:if>

 <div class="container">
		<div class="row align-items-end justify-content-center text-center">			
				<img src="${path }/images/order.png">			
		</div>
	</div>

    <div class="custom-breadcrumns border-bottom">
      <div class="container">
        <a href="index.jsp">Home</a>
        <span class="mx-3 icon-keyboard_arrow_right"></span>
        <span class="current">주문하기</span>
      </div>
    </div>
	
	<!-- 주문페이지 시작  -->

	<form class="form" action="payment1.pay" method="post" class="form">
	<div class="site-section">
	<div class="row justify-content-center">
	
	<div class="row row_line"> 
		<!-- 회원&배송지 정보 시작 -->
		<div class="left">
		<h5>배송지 정보</h5>
			<div class="col-md-12 form-group">
        	<label>아이디*</label>
            <input type="text" id="pay_email" name="pay_email" value="${mBean.email}" readonly="readonly" class="form-control form-control-lg form-text">
            </div>
            
            <div class="col-md-12 form-group">
            <label>이름*</label>
           	<input type="text" id="pay_name" name="pay_name" class="form-control form-control-lg form-text" required>
            </div>
            
            <div class="col-md-12 form-group">
            <label>전화번호*</label>
           	<input type="text" id="pay_phonenumber" name="pay_phonenumber" class="form-control form-control-lg form-text" required>
            </div>
            
            <div class="col-md-12 form-group">
	            <label for="address">주소</label><input type="button" onclick="DaumPostcode();" class="btn btn-outline-dark post_position" value="우편번호 찾기">
	            <input type="text" name="address" id="address" placeholder="우편번호" readonly="readonly" class="form-control form-control-lg form-text" onblur="addressChk();" required>
				<input type="text" name="address1" id="address1" placeholder="주소" readonly="readonly" class="form-control form-control-lg form-text" onblur="addressChk();" required> 
				<input type="text" name="address2" id="address2" placeholder="상세주소" class="form-control form-control-lg form-text" onblur="addressChk();" required><br>
			</div>
	
			<hr>
			
           
           	<input id="deliveryOption1" name="delivery-option" checked="checked" type="radio" value="기본 배송지" onclick="origin_delivery()" required>
            <label for="deliveryOption1">기존 배송지</label>
                                   
            <input id="deliveryOption2" name="delivery-option" type="radio" value="새로운 배송지" onclick="new_delivery()"  required>
            <label for="deliveryOption2">새로 입력</label>

            
       </div>
        <!-- 회원&배송지 정보 끝 -->
        
        <!-- 장바구니 정보--> 
		<div class="right">
		<h5>계좌이체</h5>
			
            <div>
            	<small>(입금 확인 후 주문 완료됩니다)</small><br>
            	부산은행 아이티윌<br>
            	123-45-678901-2
            </div>
        <hr>
        <h5>장바구니</h5>    	
        	
        	<c:set var="total" value="0"/>
        	<c:forEach  var="cartlist"   items="${requestScope.cartlist}">
        	<c:set var="j" value="0"/>
       		(${cartlist.pro_sort })
       		${cartlist.pro_name }
       		가격:${cartlist.pro_price }
       		개수:${cartlist.pro_cnt }
       		<br>
       		<c:set var="pro_price" value="${cartlist.pro_price}"/>
       		<c:set var="total" value="${total+pro_price}"/>
       			<c:if test="${j==0}">
       			<input type="hidden" value="${cartlist.product_no}" name="pay_pro1_no">
				<input type="hidden" value="${cartlist.pro_name }" name="pay_pro1_name">
				<input type="hidden" value="${cartlist.pro_cnt }" name="pay_pro1_cnt">
				<input type="hidden" value="${cartlist.pro_price }" name="pay_pro1_price">
				<input type="hidden" value="${cartlist.pro_sort }" name="pay_pro1_sort">
				</c:if>
				<c:if test="${j==1}">
				<input type="hidden" value="${cartlist.product_no}" name="pay_pro2_no">
				<input type="hidden" value="${cartlist.pro_name }" name="pay_pro2_name">
				<input type="hidden" value="${cartlist.pro_cnt }" name="pay_pro2_cnt">
				<input type="hidden" value="${cartlist.pro_price }" name="pay_pro2_price">
				<input type="hidden" value="${cartlist.pro_sort }" name="pay_pro2_sort">
				</c:if>
				
				<c:if test="${j==2}">	
				<input type="hidden" value="${cartlist.product_no}" name="pay_pro3_no">		
				<input type="hidden" value="${cartlist.pro_name }" name="pay_pro3_name">
				<input type="hidden" value="${cartlist.pro_cnt }" name="pay_pro3_cnt">
				<input type="hidden" value="${cartlist.pro_price }" name="pay_pro3_price">
				<input type="hidden" value="${cartlist.pro_sort }" name="pay_pro3_sort">
				</c:if>
				
				<c:if test="${j==3}">
				<input type="hidden" value="${cartlist.product_no}" name="pay_pro4_no">	
				<input type="hidden" value="${cartlist.pro_name }" name="pay_pro4_name">
				<input type="hidden" value="${cartlist.pro_cnt }" name="pay_pro4_cnt">
				<input type="hidden" value="${cartlist.pro_price }" name="pay_pro4_price">
				<input type="hidden" value="${cartlist.pro_sort }" name="pay_pro4_sort">
				</c:if>
				
				<c:if test="${j==4}">
				<input type="hidden" value="${cartlist.product_no}" name="pay_pro5_no">			
				<input type="hidden" value="${cartlist.pro_name }" name="pay_pro5_name">
				<input type="hidden" value="${cartlist.pro_cnt }" name="pay_pro5_cnt">
				<input type="hidden" value="${cartlist.pro_price }" name="pay_pro5_price">
				<input type="hidden" value="${cartlist.pro_sort }" name="pay_pro5_sort">
				</c:if>
       		<c:set var="j" value="${j+1}" />
       		</c:forEach>
       		
       			
			<div class="pay_position">
			<hr>
            <h5>총금액</h5>
            <div>${total}원</div> 
                                
            <h5>할인금액</h5>
            <div>${total*0.05}원</div>                       
            
            <hr>
                               
            <h5>결제금액</h5>
            <div>${total - total*0.05}원</div>
            <input type="hidden" name="pay_total" value="${total - total*0.05}">
            <div class="btn_right">
            <button type="submit" class="btn btn-outline-dark">주문하기</button>
            </div>
            </div>
    	</div>
  	</div>
  	</div>
  	</div>
  
  	</form>

    <!-- 주문 페이지 끝 -->
    <script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
	<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script type="text/javascript" src="${path}/js/DaumPostAPI.js"></script>
</body>
</html>