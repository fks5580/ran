<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>

<%--JSTL CORE라이브러리 태그들 사용을 위한 선언 --%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>     
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">	<meta charset="UTF-8">
<title>고객센터 수정하기</title>
<%
	request.setCharacterEncoding("UTF-8");
	String email = (String)session.getAttribute("email"); 
%>


</head>
	<body>
	
	<div class="container">
		<div class="row align-items-end justify-content-center text-center">			
				<img src="${path }/images/service.png">			
		</div>
	</div>
	
	
    <div class="custom-breadcrumns border-bottom">
      <div class="container">
        <a href="${path}/index.do">Home</a>
        <span class="mx-3 icon-keyboard_arrow_right"></span>
        <span class="current">고객센터</span>
      </div>
    </div>
	
	
	<div class="container mt-5 mb-5">
	 <h1>고객센터 수정하기</h1>
	<form action="updateQuestion.ques" method="post">
		<table  class="table table-striped" style="text-align: center;">
			<tr>
				<td>번호</td>
				<td><input type="text" name="ques_no" class="form-control" value="${qBean.ques_no}" readonly></td>
			</tr>
			<tr>
				<td>제목</td>
				<td><input type="text" name="ques_title" class="form-control" value="${qBean.ques_title}" ></td>
			</tr>
			<tr>
				<td>글쓴이</td>
				<td><input type="text" name="ques_email" class="form-control" value="${qBean.ques_email}" readonly></td>
			</tr>
			<tr>
				<td>작성일</td>
				<td><input type="text" class="form-control" value="${qBean.ques_writedate}" readonly></td>
			</tr>
			<tr>
				<td colspan="2"><textarea name="ques_content" class="form-control" rows="10" cols="120">${qBean.ques_content}</textarea></td>
			</tr>
			<tr>
				<c:if test="${qBean.isSecret == 'n'}">
				<td>비밀글</td>
				<td colspan="3"><input type="checkbox" name="isSecret" value="y"></td>
				</c:if>
				<c:if test="${qBean.isSecret == 'y'}">
				<td>비밀글</td>
				<td colspan="3"><input type="checkbox" name="isSecret" value="y" checked="checked"></td>
				</c:if>
			</tr>
			</table>
			<div class="text-right">
				<input type="button" value="목록으로" class="btn btn-outline-dark" onclick="location.href='questionList.ques'">
				<input type="submit"  class="btn btn-outline-dark" value="수정하기">
			</div>
	</form> 
	</div>
	</body>
</html> 