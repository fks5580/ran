<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>

<%--JSTL CORE라이브러리 태그들 사용을 위한 선언 --%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>     
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">	<meta charset="UTF-8">
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
        <a href="${path}/index.do">Home</a>
        <span class="mx-3 icon-keyboard_arrow_right"></span>
        <span class="current">자료실</span>
      </div>
    </div>
	
	
	<div class="container mt-5 mb-5">
	 <h1>자료실</h1>
	<form action="updateResource.bo" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
		<table  class="table" style="text-align: center;">
			<tr>
				<td>번호</td>
				<td><input type="text" name="res_no" class="form-control" value="${rBean.res_no}" readonly></td>
			</tr>
			<tr>
				<td>제목</td>
				<td><input type="text" name="res_title" class="form-control" value="${rBean.res_title}" required="required"></td>
			</tr>
			<tr>
				<td>글쓴이</td>
				<td><input type="text" name="res_email" class="form-control" value="${rBean.res_email}" readonly></td>
			</tr>
			<tr>
				<td>작성일</td>
				<td><input type="text" class="form-control" value="${rBean.res_writedate}" readonly></td>
			</tr>
			<tr>
				<td colspan="2"><textarea name="res_content" class="form-control" rows="10" cols="120">${rBean.res_content}</textarea></td>
			</tr>
			<tr>
				<td>기존파일</td>
				<td colspan="2">${rBean.res_filename}</a></td>
			</tr>
			<tr>
			     <td>파일변경</td>
				 <td colspan="2"><input type="file" name="res_filename"/></td>
			</tr>
			</table>
			<div class="text-right">
				<input type="button" value="목록으로" class="btn btn-outline-dark" onclick="location.href='resourceList.bo'">
				<input type="submit"  class="btn btn-outline-dark" value="수정하기">
			</div>
	</form> 
	</div>
	
	</body>
</html> 