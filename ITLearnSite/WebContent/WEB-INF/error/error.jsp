<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<% response.setStatus(200); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
.error{
	margin-top: 100px;
	margin-left: 350px;
}
.blk{
	color: blue;
	margin-top: 30px;
	margin-left: 1000px;
	text-decoration: none;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>오류페이지</title>
</head>
<body>
<img class="error" alt="" src="./images/error.png" width="1000px"><br>
<a class="blk" href="./index.do">메인화면으로 이동</a>
</body>
</html>