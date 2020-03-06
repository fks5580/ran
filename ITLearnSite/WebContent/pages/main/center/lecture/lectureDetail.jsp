<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
	request.setCharacterEncoding("utf-8");
%>
<c:set var="path" value="${pageContext.request.contextPath}" />
<c:set var="lecBean" value="${lec_DetailMap.lec_Detail }" />
<c:set var="lec_list" value="${lec_DetailMap.lec_list }" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="${path}/css/lectureDetail.css" rel="stylesheet">
<style type="text/css">
body {
	word-break: break-all;
}
</style>
</head>

<body>
	<div class="container">
		<div class="row align-items-end justify-content-center text-center">
			<img src="${path }/images/lecture.png">
		</div>
	</div>
	
	<div class="custom-breadcrumns border-bottom">
		<div class="container">
			<a href="${path}/index.do">Home</a> <span class="mx-3 icon-keyboard_arrow_right"></span> <a href="${path}/lectureList.lec">강의실</a> <span
				class="mx-3 icon-keyboard_arrow_right"></span> <span class="current">강의 상세</span>
		</div>
	</div>


	<div class="pagewrap">
		<div class="list">
			<div class='wrap'>
				<div class='bookimg'>
					<img src='${path }/pages/main/center/lecture/temp/${lecBean.lec_imgfile}'>
				</div>
				<div class='bookinfo'>
					<span class='booktitle'> ${lecBean.lec_title}</span> <span class='bookdetail'>
						<div class='detail-align'>
							<p class='p1'>강사 :</p>
							<p class='p2'>${lecBean.lec_teacher}</p>
						</div>
						<div class='detail-align'>
							<p class='p1'>30일 :</p>
							<p class='p2'>${lecBean.lec_price }원</p>
						</div> <!-- <div class='detail-align'>
							<p class='p1'></p>
							<p class='p2'>
								<a href='#' class="btn btn-primary rounded-0 px-4"></a>
							</p>
						</div>
						<div class='detail-align'>
							<p class='p1'></p>
							<p class='p2'>
								<a href='#' class="btn btn-primary rounded-0 px-4"></a>
							</p>
						</div> -->
					</span>
					<!-- <span class='bookdetail2'><div class='detail-align'>
							<p class='p1'></p>
							<p class='p2'></p>
						</div>
						<div class='detail-align'>
							<p class='p1'></p>
							<p class='p2'></p>
						</div></span> -->
				</div>
			</div>
		</div>
	</div>


	<div class="pagebottom">
		<div class="divide">
			<h3>강의 설명</h3>
			<hr>

			<p>${lecBean.lec_content}</p>
			<h3>목차</h3>
			<hr>
			<div class="boardoutline" style="margin-bottom: 100px;">
				<div class="content">
					<table class="table-striped">
						<thead>
							<tr class="tb_head">
								<td>번호</td>
								<td>목차</td>

							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${lec_list == null}">

									<tr align="center">
										<td colspan="4"><p>
												<b><span style="font-size: 9pt;">등록된 강의가 없습니다.</span></b>
											</p></td>
									</tr>
								</c:when>
								<c:when test="${lec_list != null}">
									<c:forEach var="listBean" items="${lec_list }">
										<tr align="center">
											<td width="30%">${listBean.list_no }</td>
											<td width="70%">${listBean.list_titleStr }</td>
										<tr>
									</c:forEach>
								</c:when>
							</c:choose>
							<tr class="tb_head" align="center">
								<td colspan="4">&nbsp;&nbsp;&nbsp;</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>


		</div>
	</div>
	<script src="${path}/js/jquery-3.3.1.min.js"></script>
</body>
</html>