<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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

<c:set var="path" value="${pageContext.request.contextPath}" />



</head>
<body>
	<div class="pagemargin">
		<div class="wrapboard">
			<div class="row">
				<div class="col-lg-12"
					style="display: inline-block; text-align: center;">
					<div class="btn-wrap">
						<div class="header-btn float-l">
							<a href="resourceList.bo">
								<button class="btn btn-color1">
									<span>전체글</span>
								</button>
							</a>
						</div>

						<div class="header-btn float-r">
							<a href=""> <!--  --> <span
								class="glyphicon glyphicon-pencil gi-2x"></span>
							</a>
						</div>
					</div>
				</div>
			</div>
			<div class="boardoutline">
				<div class="content">

					<!-- 테이블 시작 -->
					<table class="table">
						<thead>
							<tr class="tb_head">
								<td>번호</td>
								<td>제목</td>
								<td>글쓴이</td>
								<td>작성일</td>
							</tr>
						</thead>
						<c:set var="j" value="0" />
						<c:forEach var="list" items="${requestScope.ResourceList}">						
							
							<tr>
								<td>${list.res_no}</td>
								<td>${list.res_title }</td>
								<td>${list.res_email }</td>
								<td>${list.res_writedate}</td>
							</tr>
							<!-- j변수 값 1씩 증가 -->
							<c:set var="j" value="${j+1}" />
						</c:forEach>
					</table>
				</div>
			</div>

			<div class="btn-wrap">
				<div class="header-btn float-l">
					<a href="resourceList.bo">
						<button class="btn btn-color1">
							<span>전체글</span>
						</button>
					</a>
				</div>

				<div class="header-btn float-r">
					<a href="resourceWrite.bo">
						<button class="btn btn-color1">
							<span>글쓰기</span>
						</button>
					</a>
				</div>
			</div>

			<div class="btn-wrap text-align">
				<form action="resourceSelect.bo" method="post">
					<div class="selector-wrap">
						<select class="box selectbox" name="opt">
							<option value="res_title" selected="selected">제목</option>
							<option value="res_content">내용</option>
							<option value="res_email">글쓴이</option>
						</select>
					</div>
					<div class="search-wrap">
						<input class="box inputbox" type="text" name="condition">
						<span class="lookimg">
							<button type="submit">
								<img src="${path}/images/look.png" width="30px" height="35px">
							</button>
						</span>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>