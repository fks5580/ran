<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<c:set var="path" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${path}/css/bookdetail.css">
<style type="text/css">
.star_rating {font-size:0; letter-spacing:-4px;}
.star_rating a {
    font-size:22px;
    letter-spacing:0;
    display:inline-block;
    margin-left:5px;
    color:#ccc;
    text-decoration:none;
}
.star_rating a:first-child {margin-left:0;}
.star_rating a.on {color:#183661;}
.color_b{
	color:#183661;
}
.container_b{
	color:black;
}

</style>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">

$( document ).ready(function() {
	$( ".star_rating a" ).click(function() {
	    $(this).parent().children("a").removeClass("on");
	    $(this).addClass("on").prevAll("a").addClass("on");	    
	    var n = $(this).attr("id");
	    document.getElementById("bo_evaluation").value = n;
	    return false;
	});
});

</script>
</head>
<div class="container">
	<div class="row align-items-end justify-content-center text-center">			
			<img src="${path }/images/book.png">			
	</div>
</div>

<div class="custom-breadcrumns border-bottom">
	<div class="container">
		<a href="${path}/index.do">Home</a> <span class="mx-3 icon-keyboard_arrow_right"></span> <span class="current">도서보기</span>
	</div>
</div>
<body>
	<div class="container container_b">
		<div class="row">
			<div class="wrap">
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<div class="info">
							<div class="imgwrap">
								<c:if test="${1 == 1}">
									<img class="img1" src="${detail.book_image}">
								</c:if>
							</div>
							<div class="info_left">
								<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">

									<div class="col-margin col-xs-12 col-sm-12 col-md-12 col-lg-12">
										<p class="label label-1">상품번호 : ${detail.product_no}</p>
									</div>

									<div class="col-margin col-xs-12 col-sm-12 col-md-12 col-lg-12">
										<p class="label label-1">책 제목 : ${detail.book_title}</p>
									</div>

									<div class="col-margin col-xs-12 col-sm-12 col-md-12 col-lg-12">
										<p class="label label-1">저 자 : ${detail.book_author}</p>
									</div>

									<div class="col-margin col-xs-12 col-sm-12 col-md-12 col-lg-12">
										<p class="label label-1">가 격 : ${detail.book_price}</p>
									</div>

									<div class="col-margin col-xs-12 col-sm-12 col-md-12 col-lg-12">
										<p class="label label-1">출판사 : ${detail.book_publisher }</p>
									</div>

									<div class="col-margin col-xs-12 col-sm-12 col-md-12 col-lg-12">
										<p class="label label-1">출판일 : ${detail.book_pubdate}</p>
									</div>

									<div class="col-margin col-xs-12 col-sm-12 col-md-12 col-lg-12">
										<p class="label label-1">ISBN : ${detail.book_isbn }</p> 
									</div>

									<div class="col-margin col-xs-12 col-sm-12 col-md-12 col-lg-12">
										<p class="label label-1">남은 수량 : ${detail.book_stock}</p> 
									</div>

								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="bookcontent">
					<h3>내용</h3>
					<hr>
					<p>${detail.book_description }</p>
				</div>

			</div>

		</div>
	
	
	
	<!--구매 후기 -->
	<div class="row">
	<div class="wrap">

			<div>
				<span>
					<h5>구매 후기</h5>
				</span>
				<div>
					<table class="table">
						<tbody id="cmt">

						</tbody>
					</table>
				</div>
			</div>
			<hr>
			<c:if test="${email == null}">
				<textarea id="commentContent" placeholder="로그인 후 후기 작성이 가능합니다" cols="120" rows="3" style="resize:none" readonly="readonly" onclick="nobooklogin();"></textarea>
				<br>
				<input type="hidden" id="product_no" name="product_no" value="${detail.product_no}">
				<div class="text-right">
				<input type="button" class="btn btn-outline-secondary" id="commentWrite" value="후기 작성" onclick="nobooklogin();">
				</div>
			</c:if>
			<c:if test="${email != null }">
				<form class="justify-content-center ">
					<p class="star_rating">
					    <a href="#" class="on" id="1">★</a>
					    <a href="#" class="on" id="2">★</a>
					    <a href="#" class="on" id="3">★</a>
					    <a href="#" id="4">★</a>
					    <a href="#" id="5">★</a>
					</p>
					<input type="hidden" id="bo_evaluation" name="bo_evaluation">
					<input type="hidden" id="product_no" name="product_no" value="${detail.product_no}"> 
					<input type="hidden" id="bo_email" name="bo_email" value="${email}">
					<textarea id="bo_content" name="bo_content" placeholder="바르고 고운말" cols="120" rows="3" style="resize:none"></textarea>
					<div class="text-right">
					<input type="button" class="btn btn-outline-dark" id="commentWrite" name="commentWrite" value="후기 작성" onclick="bookcomments();">
					</div>
				</form>
			</c:if>
			<br>

	</div>
	</div>
	<hr>
	</div>
</body>
<script src="${path}/js/jquery-3.3.1.min.js"></script>
<script src="${path}/js/makejs/bookView.js"></script>

</html>