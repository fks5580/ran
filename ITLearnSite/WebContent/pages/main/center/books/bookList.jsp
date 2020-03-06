<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
	request.setCharacterEncoding("utf-8");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<c:set var="resourcesList" value="${resourcesMap.resourcesList}" />
<c:set var="totResources" value="${resourcesMap.totResources}" />
<c:set var="section" value="${resourcesMap.section}" />
<c:set var="pageNum" value="${resourcesMap.pageNum}" />
<c:set var="path" value="${pageContext.request.contextPath}" />

<c:set var="naverlink" value="${requestScope.result.items[0].link}"></c:set>
<link rel="stylesheet" href="${path}/css/bookList.css">
</head>

<div class="container">
		<div class="row align-items-end justify-content-center text-center">			
				<img src="${path }/images/book.png">			
		</div>
	</div>

<div class="custom-breadcrumns border-bottom">
	<div class="container">
		<a href="${path}/index.do">Home</a> <span
			class="mx-3 icon-keyboard_arrow_right"></span> <span class="current">도서</span>
	</div>
</div>
<body>
	<div class="container">
		<div class="pagewrap">
			<div class="wrapboard"></div>
			<hr>
		</div>
	</div>
	<script src="${path}/js/jquery-3.3.1.min.js"></script>
	<script src="${path}/js/makejs/bookList.js"></script>
</body>
</html>