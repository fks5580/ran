<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%--JSTL CORE라이브러리 태그들 사용을 위한 선언 --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<c:set var="email" value="${sessionScope.email}"></c:set>
<c:set var="co_no" value="${sessionScope.co_no}"></c:set>
<c:set var="res_no" value="${requestScope.res_no}"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>자료실</title>
<%
	request.setCharacterEncoding("UTF-8");
	String email = (String)session.getAttribute("email"); 
%>

</head>
<body>

	 <div class="container">
		<div class="row align-items-end justify-content-center text-center">			
				<img src="${path }/images/file.png">			
		</div>
	</div>
    

	<div class="custom-breadcrumns border-bottom">
		<div class="container">
			<a href="${path}/index.do">Home</a> <span class="mx-3 icon-keyboard_arrow_right"></span> <span class="current">자료실</span>
		</div>
	</div>


	<div class="container mt-5 mb-5">
		<h1>자료실</h1>
		<form action="resourceModify.bo" method="post">
			<table class="table" style="text-align: center;">
				<tr>
					<td>번호</td>
					<td><input type="text" name="res_no" class="form-control" value="${rBean.res_no}" readonly></td>
				</tr>
				<tr>
					<td>제목</td>
					<td><input type="text" name="res_title" class="form-control" value="${rBean.res_title}" readonly></td>
				</tr>
				<tr>
					<td>글쓴이</td>
					<td><input type="text" name="res_email" class="form-control" value="${rBean.res_email}" readonly></td>
				</tr>
				<tr>
					<td>작성일</td>
					<td><input type="text" name="res_writedate" class="form-control" value="${rBean.res_writedate}" readonly></td>
				</tr>
				<tr>
					<td colspan="2"><textarea name="res_content" class="form-control" readonly rows="10" cols="120">${rBean.res_content}</textarea></td>
				</tr>
				<tr>
					<td>첨부파일</td>
					<td colspan="2"><a href="filedown.bo?res_no=${rBean.res_no}">${rBean.res_filename}</a></td>
				</tr>
			</table>
			<div class="text-right">
				<input type="button" value="목록으로" class="btn btn-outline-dark" onclick="location.href='resourceList.bo'"> 
				<c:if test="${ email == 'admin@admin.com'}" >
				<input type="submit" class="btn btn-outline-dark" value="수정"> 
				<input type="button" class="btn btn-outline-dark" value="삭제" onclick="location.href='resourceDelete.bo?res_no=${rBean.res_no}'">
				</c:if>
			</div>
		</form>
		<hr>
		<!-- 댓글자리 -->
		<div>
			<div>
				<span>
					<h5>댓글</h5>
				</span>
				<div class='row'>
					<table class="table">
						<tbody id="cmt">

						</tbody>
					</table>
				</div>
			</div>
			<hr>
			<c:if test="${email == null}">
				<textarea id="commentContent" placeholder="로그인 후 댓글 작성이 가능합니다" cols="120" rows="3" style="resize:none" readonly="readonly" onclick="nologin();"></textarea>
				<br>
				<input type="hidden" id="res_no" name="res_no" value="${res_no}">
				<div class="text-right">
				<input type="button" class="btn btn-outline-secondary" id="commentWrite" value="댓글 작성" onclick="nologin();">
				</div>
			</c:if>
			<c:if test="${email != null }">
				<form>
					<!-- co_no ? autoincrements? 로직 생각해보기-->
					<!-- 현재 글에 comments table을 조회해서 코멘트 순서 번호를 가져와야함 select  -->
					<input type="hidden" id="res_no" name="res_no" value="${res_no}"> 
					<input type="hidden" id="co_email" name="co_email" value="${email}">
					<textarea id="content" name="content" placeholder="바르고 고운말" cols="120" rows="3" style="resize:none"></textarea>
					<div class="text-right">
					<input type="button" class="btn btn-outline-dark" id="commentWrite" name="commentWrite" value="댓글 작성" onclick="comments();">
					</div>
				</form>
			</c:if>
			<br>
		</div>
		<hr>
	</div>
	<script src="${path}/js/jquery-3.3.1.min.js"></script>
	<script src="${path}/js/makejs/resourceView.js"></script>
</body>
</html>
