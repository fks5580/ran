<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div style="margin-top: 100px;">
		<h1>이메일 인증이 완료 되었습니다. 이제 로그인이 가능합니다.</h1>
		<a href="index.do">
			<button>메인으로</button>
		</a>
	</div>
</body>
</html>