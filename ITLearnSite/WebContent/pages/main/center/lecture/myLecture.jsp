<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
	request.setCharacterEncoding("utf-8");
%>
<c:set var="path" value="${pageContext.request.contextPath}" />
<c:set var="email" value="${sessionScope.email}"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="${path}/css/lectureDetail.css" rel="stylesheet">

<script type="text/javascript">
	function play(title) {
		var width = 1000;
		var height = 800;
		/* 
		var winL = (screen.width - 10) / 2;
		var winT = (screen.height - 200) / 2;
		"left=" + winL + "," + "top=" + winT + 
		 */
		/*  width = screen.width - 10;
		 height = screen.width - 220; */
		var property = "width=" + width + "," + "height=" + height + ","
				+  " menubar=no, scrollbars=no, status=no, location=no, toolbar=no, menubar=no,resizable=no";
		
		var url = "lecturePlay.lec?lec_title="+title;
		/* var url = "resourceWrite.bo"; */

		window.open( url, "강의보기", property);

	}
</script>

</head>
<style>
 @font-face { font-family: 'NIXGONM-Vb'; src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_six@1.2/NIXGONM-Vb.woff') format('woff'); font-weight: normal; font-style: normal; }
h3{
font-family: 'NIXGONM-Vb';}
</style>

<body>
	 <div class="container">
	<div class="row align-items-end justify-content-center text-center">			
			<img src="${path }/images/mypage2.png">			
	</div>
</div>
	<div class="custom-breadcrumns border-bottom">
		<div class="container">
			<a href="${path}/index.do">Home</a> 
			<span class="mx-3 icon-keyboard_arrow_right"></span> 
			<span class="current">마이페이지</span>
			<span class="mx-3 icon-keyboard_arrow_right"></span> 
			<span class="current">내 강의실</span>
		</div>
	</div>

	<div class="container">
		<div class="pagewrap">
			<div class="pagebottom">
				<div class="divide">
					<h3>내 강의실</h3>
					<hr>

					<div class="boardoutline" style="margin-bottom: 100px;">
						<div class="content">
							<table class="table-striped">
								<thead>
									<tr class="tb_head">
										<td>번호</td>
										<td>강의명</td>
										<td>만료일</td>										

									</tr>
								</thead>
								<tbody>
									<c:choose>
										<c:when test="${myList == null}">

											<tr align="center">
												<td colspan="4"><p>
														<b><span style="font-size: 9pt;">결제하신 강의가 없습니다.</span></b>
													</p></td>
											</tr>
										</c:when>
										<c:when test="${myList != null}">
											<c:forEach var="bean" items="${myList }" varStatus="num">
												<tr align="center">
													<td width="20%">${num.count }</td>
													<td width="60%"><a href="javascript:void(0);"
													onclick="play('${bean.set_lec_title }');">${bean.set_lec_title }</a></td>
													<td width="20%">${bean.between }일남았습니다.</td>													
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
		</div>
	</div>

	<script src="${path}/js/jquery-3.3.1.min.js"></script>
</body>
</html>