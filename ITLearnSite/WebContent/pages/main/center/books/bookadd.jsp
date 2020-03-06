<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="path" value="${pageContext.request.contextPath}" />
<!DOCTYPE>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<title>Starter Template for Bootstrap 4.4.1</title>
<link rel="shortcut icon" href="">

<style>
 @font-face { font-family: 'NIXGONM-Vb'; src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_six@1.2/NIXGONM-Vb.woff') format('woff'); font-weight: normal; font-style: normal; }
body, h3{
font-family: 'NIXGONM-Vb';}
</style>

</head>
<div class="container">
	<div class="row align-items-end justify-content-center text-center">			
			<img src="${path }/images/admin3.png">			
	</div>
</div>
 <div class="custom-breadcrumns border-bottom">
      <div class="container">
        <a href="index.jsp">Home</a>
        <span class="mx-3 icon-keyboard_arrow_right"></span>
        <span class="current">관리자</span>
        <span class="mx-3 icon-keyboard_arrow_right"></span>
        <span class="current">도서 등록</span>
      </div>
    </div>
   
<body>
	<div class="container pb-5">
		<div class="row">
			<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5">
				<form action="insertBook.text" method="post" role="form">
					<!-- <span class="label label-[ENTER NAME HERE]">상품 등록</span> -->
					<div class="addtag">
						<div class="mt-5">
							<h3>도서 정보</h3>
						</div>
						<div class='form-group'>
							<input type='text' class='form-control' id='book_title' name='book_title' placeholder='책제목' required>
						</div>
						<div class='form-group'>
							<input type='text' class='form-control' id='book_link' name='book_link' placeholder='책링크' required>
						</div>
						<div class='form-group'>
							<input type='text' class='form-control' id='book_image' name='book_image' placeholder='책이미지' required>
						</div>
						<div class='form-group'>
							<input type='text' class='form-control' id='book_author' name='book_author' placeholder='작가' required>
						</div>
						<div class='form-group'>
							<input type='text' class='form-control' id='book_price' name='book_price' placeholder='책가격' required>
						</div>
						<div class='form-group'>
							<input type='text' class='form-control' id='book_discount' name='book_discount' placeholder='할인금액' required>
						</div>
						<div class='form-group'>
							<input type='text' class='form-control' id='book_publisher' name='book_publisher' placeholder='출판사' required>
						</div>
						<div class='form-group'>
							<input type='text' class='form-control' id='book_pubdate' name='book_pubdate' placeholder='출판일' required>
						</div>
						<div class='form-group'>
							<input type='text' class='form-control' id='book_isbn' name='book_isbn' placeholder='isbn' required>
						</div>
						<div class='form-group'>
							<input type='text' class='form-control' id='book_description' name='book_description' placeholder='내용' required>
						</div>
						<div class='form-group'>
							<input type='text' class='form-control' id='book_description' name='book_stock' placeholder='재고' required>
						</div>
						<br>
						<div class="text-right">
						<button type='submit' class="btn btn-outline-success">등록</button>
						</div>
					</div>
					<br>
				</form>
			</div>
			<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2"></div>
			<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5">
				<div class="row mt-5">
					<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
						<span class="label">네이버 책 검색</span> 
						<input type="text" class="form-control" id="word" name="word" placeholder="Search">
					</div>
					<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3">
						<br> <span class="input-group-btn">
							<button type="button" class="btn btn-outline-primary" onclick="bookSearch(1);">Go!</button>
						</span>
					</div>
				</div>
				<div class="row mt-5">
					<br> <br> <span class="label">[검색 결과 : <span class="find"></span>건]
					</span>
					<table class="table">
						<tbody>
							<tr>
								<th style="width: 20%">title</th>
								<td class="title"></td>
							</tr>
							<tr>
								<th>link</th>
								<td class="link"></td>
							</tr>
							<tr>
								<th>image</th>
								<td class="image"></td>
							</tr>
							<tr>
								<th>author</th>
								<td class="author"></td>
							</tr>
							<tr>
								<th>price</th>
								<td class="price"></td>
							</tr>
							<tr>
								<th>discount</th>
								<td class="discount"></td>
							</tr>
							<tr>
								<th>publisher</th>
								<td class="publisher"></td>
							</tr>
							<tr>
								<th>pubdate</th>
								<td class="pubdate"></td>
							</tr>
							<tr>
								<th>isbn</th>
								<td class="isbn"></td>
							</tr>
							<tr>
								<th>description</th>
								<td class="description"></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="paging row justify-content-center align-self-center">
					<a class="btn btn-outline-dark" role="button">이전</a>
					<button class="pagenum btn btn-default" role="button" onclick="bookSearch(1);">1</button>
					<button class="pagenum btn btn-default" role="button" onclick="bookSearch(2);">2</button>
					<button class="pagenum btn btn-default" role="button" onclick="bookSearch(3);">3</button>
					<button class="pagenum btn btn-default" role="button" onclick="bookSearch(4);">4</button>
					<button class="pagenum btn btn-default" role="button" onclick="bookSearch(5);">5</button>
					<button class="pagenum btn btn-default" role="button" onclick="bookSearch(6);">6</button>
					<button class="pagenum btn btn-default" role="button" onclick="bookSearch(7);">7</button>
					<button class="pagenum btn btn-default" role="button" onclick="bookSearch(8);">8</button>
					<button class="pagenum btn btn-default" role="button" onclick="bookSearch(9);">9</button>
					<button class="pagenum btn btn-default" role="button" onclick="bookSearch(10);">10</button>
					<button class="btn btn-outline-dark" role="button">다음</button>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${path }/js/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="${path }/js/makejs/product.js"></script>
</body>
</html>