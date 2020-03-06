<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--JSTL CORE라이브러리 태그들 사용을 위한 선언 --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${path}/css/member.css" rel="stylesheet">
<title>장바구니</title>
<%
	request.setCharacterEncoding("utf-8");
%>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript">
	$(function() {
		var pro_cnt = 0;
		var cart_num = 0;

		$('.bt_up').click(function() {
			var n = $('.bt_up').index(this);
			pro_cnt = $("input[name=pro_cnt" + n + "]").val();
			$("input[name=pro_cnt" + n + "]").val(pro_cnt * 1 + 1);
			$('#link' + n).click(function() {
				pro_cnt = $("input[name=pro_cnt" + n + "]").val();
				cart_num = $("input[name=cart_num" + n + "]").val();
				edit(cart_num, pro_cnt);
			});

		});
		$('.bt_down').click(function() {
			var n = $('.bt_down').index(this);

			pro_cnt = $("input[name=pro_cnt" + n + "]").val();
			$("input[name=pro_cnt" + n + "]").val(pro_cnt * 1 - 1);

			$('#link' + n).click(function() {
				pro_cnt = $("input[name=pro_cnt" + n + "]").val();
				cart_num = $("input[name=cart_num" + n + "]").val();
				edit(cart_num, pro_cnt);
			});
		});
	});

	function edit(cart_num, pro_cnt) {
		//개수 구하기
		location.href = "cartEdit.cart?cart_num=" + cart_num + "&pro_cnt=" + pro_cnt;
	}
</script>
<link href="${path}/css/create.css" rel="stylesheet">

</head>
 <div class="container">
		<div class="row align-items-end justify-content-center text-center">			
				<img src="${path }/images/cart2.png">			
		</div>
	</div>

    <div class="custom-breadcrumns border-bottom">
      <div class="container">
        <a href="index.jsp">Home</a>
        <span class="mx-3 icon-keyboard_arrow_right"></span>
        <span class="current">장바구니</span>
      </div>
    </div>
	

	<!-- 장바구니에 대한 정보 출력 -->
	<center>
		<div class="pagemargin">
			<div class="content">
				<c:set var="total" value="0"/>
				<form action="payment.pay" method="post" name="cfr">
					<table class="table tablesize">
						<c:set var="j" value="0" />
						<!-- MemberListController에서 넘겨 받은 request영역 안에 있는 list사이즈 만큼 반복 -->
						<tr align="center">
						<tr>
							<th style="width: 200px;">상품사진</th>
							<th style="width: 500px;">상품이름</th>
							<th style="width: 200px;">수량</th>
							<th style="width: 100px;">가격</th>
							<th style="width: 100px;">분류</th>
							<th style="width: 50px;"></th>
						</tr>
						<c:set var="i" value="0"></c:set>
						<c:forEach var="cartlist" items="${requestScope.cartlist}">
							<tr>
								<c:if test="${cartlist.pro_sort == '강의'}">
									<td class="img" style="width: 200px;"><img src="${path }/pages/main/center/lecture/temp/${cartlist.pro_img }" class="pro_img"></td>
									<td class="name" style="width: 500px;">${cartlist.pro_name }</td>
									<td class="count" style="width: 250px;">
										<div class="quantity" style="display: none">
											<a id="minusbtn"><img src="${path}/images/minus.png" alt="" width="20px" height="20px" class="bt_down" /></a>
											 <input type="hidden"
												name="cart_num${i}" value="${cartlist.cart_num}"> 
												<input type="hidden" name="pro_cnt${i}" value="${cartlist.pro_cnt}" class="num">
											<%-- 										<c:out value="${i}"></c:out> --%>
											<%-- 										<c:out value="${cartlist.pro_cnt}"></c:out> --%>
											<a id="plusbtn"><img src="${path}/images/plus.png" alt="" width="20px" height="20px" class="bt_up" /></a> 
											<input id="link${i}"
												type="hidden" class="btn btn-outline-success" value="변경">
										</div>
									</td>
								</c:if>

								<c:if test="${cartlist.pro_sort == '도서'}">
									<td class="img" style="width: 200px;"><img src="${cartlist.pro_img }" class="pro_img"></td>
									<td class="name" style="width: 500px;">${cartlist.pro_name }</td>
									<td class="count" style="width: 250px;">
										<div class="quantity">
											<a id="minusbtn"><img src="${path}/images/minus.png" alt="" width="20px" height="20px" class="bt_down" /></a> 
											<input type="hidden" name="cart_num${i}" value="${cartlist.cart_num}" > 
											<input type="text" name="pro_cnt${i}" value="${cartlist.pro_cnt}" class="num" style="width:100px"> 
											<%-- 										<c:out value="${i}"></c:out> --%>
											<%-- 										<c:out value="${cartlist.pro_cnt}"></c:out> --%>
											<a id="plusbtn"><img src="${path}/images/plus.png" alt="" width="20px" height="20px" class="bt_up" /></a> <input id="link${i}"
												type="button" class="btn btn-outline-success" value="변경">

										</div>
									</td>
								</c:if>

								<c:if test="${i >= 0}">
									<c:set var="i" value="${i+1}"></c:set>
								</c:if>
								<td class="price" style="width: 100px;">${cartlist.pro_price }</td>
								<td class="sort" style="width: 100px;">${cartlist.pro_sort }</td>
								<td class="delete"><input type="button" class="btn btn-outline-danger" value="삭제"
									onclick="location.href='cartDelete.cart?cart_num=${cartlist.cart_num}'"></td>
							</tr>
							<!-- j변수 값 1씩 증가 -->
							<c:set var="j" value="${j+1}" />
							<c:set var="total" value="${total+cartlist.pro_price}"/>
						</c:forEach>
					</table>
					<div>
						<table class="table table-condensed">
							<tr>
								<td>상품 금액 : ${total} </td>
							</tr>					
						</table>
					</div>
					<div class="btn">
	
						<input type="button" class="btn btn-outline-secondary" value="장바구니 비우기" onclick="location.href='cartAllDelete.cart'"> <input type="button"
							class="btn btn-outline-dark" value="전체상품주문" onclick="location.href='payment.pay'">
					</div>
				</form>

			</div>
		</div>
	</center>


</body>
</html>