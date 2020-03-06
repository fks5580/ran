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
<c:set var="lecturesList" value="${lecturesMap.lecturesList}" />
<c:set var="totLectures" value="${lecturesMap.totLectures}" />
<c:set var="section" value="${lecturesMap.section}" />
<c:set var="pageNum" value="${lecturesMap.pageNum}" />
<c:set var="path" value="${pageContext.request.contextPath}" />

<script type="text/javascript">
	
	function deleteNo(no, title) {
		
		var result = confirm( title + " 강의를 삭제 하겠습니까??");
		if(result){
			location.href = "${path }/deleteLecture.lec?lec_no=" + no;
		}
		
	}
	
</script>

</head>


<div class="container">
	<div class="row align-items-end justify-content-center text-center">			
			<img src="${path }/images/lecture.png">			
	</div>
</div>

<div class="custom-breadcrumns border-bottom">
	<div class="container">
		<a href="${path}/index.do">Home</a> <span
			class="mx-3 icon-keyboard_arrow_right"></span> <span class="current">강의 구매</span>
	</div>
</div>



<body>

	<div class="site-section">
		<div class="container">
			<div class="row">
				<c:choose>
					<%--
				BoaqrdController.java 서블릿으로부터 전달 받은 request 영역에 저장되어 
				articlesList 속성으로 바인딩된 ArrayList 객체가 저장되어 있지 않다면				
			 --%>
					<c:when test="${lecturesList == null }">
						<b><span style="font-size: 9pt;">등록된 강의가 없습니다.</span></b>
					</c:when>
					<c:when test="${lecturesList != null }">
						<%--
					BoardController.java 서블릿으로부터 전달받은 request 영역에
					articlesList 속성으로 바인된 ArrrayList 객체의 크기(검색한 글의 개수)만큼 반복, 
					검색한 글정보(ArticleVO)들을 ArrayList 객체 내부의 인덱스 위치로부터 글목록 표시 
				 --%>
						<c:forEach var="lecture" items="${lecturesList }" varStatus="lectureNum">
							<div class="col-lg-4 col-md-6 mb-4">
								<div class="course-1-item">
									<figure class="thumnail">
										<a href="${path }/lectureDetail.lec?lec_no=${lecture.lec_no}"><img style="width: 350px;height: 300px;" src="${path }/pages/main/center/lecture/temp/${lecture.lec_imgfile }"
											alt="Image" class="img-fluid"></a>
										<div class="price">￦${lecture.lec_price }</div>
										<div class="category">
											<h3>${lecture.lec_title }</h3>
										</div>
									</figure>
									<div class="course-1-content pb-4">
										<h2>강사 : ${lecture.lec_teacher }</h2>
										<!-- <div class="rating text-center mb-3">
											<span class="icon-star2 text-warning"></span>
										</div> -->
										<p class="desc mb-4"></p>
										<p>


											<a
												href="cartAdd.cart?&product_no=${lecture.product_no}&pro_name=${lecture.lec_title }&pro_price=${lecture.lec_price }&pro_img=${lecture.lec_imgfile}&pro_sort=${lecture.product_type}&pro_cnt=1"
												class="btn btn-outline-success rounded-0 px-4">장바구니</a> <a href="directPay.pay?product_no=${lecture.product_no}&pro_name=${lecture.lec_title }&pro_price=${lecture.lec_price}&pro_sort=강의&pro_cnt=1"
												class="btn btn-outline-dark rounded-0 px-4">구매하기</a>
												<a style="margin: 5px;"	href="${path }/pages/main/center/lecture/temp/${lecture.lec_spofile }" class="btn btn-outline-primary rounded-0 px-4" data-fancybox="">맛보기</a>
											<c:if test="${email eq 'admin@admin.com' }">
												<a class="btn btn-outline-secondary rounded-0 px-4" href="javascript:void(0);" onclick="deleteNo(${lecture.lec_no}, '${lecture.lec_title }');">삭제</a>
											</c:if>
										</p>
									</div>
								</div>
							</div>

						</c:forEach>
					</c:when>
				</c:choose>
			</div>
		</div>
	</div>

	<div class="btn-wrap text-align" style="margin-top: 20px; margin-bottom: 20px; width: 100%; flex; justify-content: center; align-content: center;">
		<%--전체 글수에 따라 페이징 표시를 다르게 합니다. --%>
		<c:if test="${totLectures != null }">
			<c:choose>
				<c:when test="${totLectures >30 }">
					<!-- 전체 글수가 30보다 클때... -->
					<c:forEach var="page" begin="1" end="${totLectures/3 + 1}" step="1">

						<%--섹션값 2부터는 앞 섹션으로 이동할수 있는 pre를 표시합니다. --%>
						<c:if test="${section >1 && page==1 }">
							<a class="no-uline" href="${path }/lectureList.lec?section=${section-1}&pageNum=${(section-1)*10 +1 }">&nbsp; pre </a>
						</c:if>

						<a class="no-uline" href="${path }/lectureList.lec?section=${section}&pageNum=${page}">${(section-1)*10 +page } </a>

						<%--페이지번호 10 오른쪾에는 다음섹션으로 이동할수 있는 next를 표시합니다.--%>
						<c:if test="${page ==10 }">
							<a class="no-uline" href="${path }/lectureList.lec?section=${section+1}&pageNum=${section*10+1}">&nbsp; next</a>
						</c:if>
					</c:forEach>
				</c:when>

				<%--전체글수가 30개일떄는 첫번째 섹션의 10개의 페이지만 표시하면 됩니다. --%>
				<c:when test="${totLectures == 30 }">
					<!--등록된 글 개수가 30개인경우  -->
					<c:forEach var="page" begin="1" end="10" step="1">
						<a class="no-uline" href="#">${page} </a>
					</c:forEach>
				</c:when>


				<c:when test="${totLectures< 30 }">
					<!--등록된 글 개수가 30개 미만인 경우  -->
					<%--
						글수가 30개가 되지 않으므로 표시되는 페이지는
	     				10개가 되지 않고, 전체 글수를 10으로 나누어
	     				구한 몫에 1을 더한 페이지까지 표시합니다.
	     			 --%>
					<c:forEach var="page" begin="1" end="${totLectures/3 +1}" step="1">
						<c:choose>
							<%--
						페이지번호와 컨트롤러에서 넘어온 pageNum이 같은경우
	         			페이지번호를 빨간색으로 표시하여 현재 사용자가 보고 있는 페이지임을 나타냄
	         	 	--%>
							<c:when test="${page==pageNum }">
								<a class="sel-page" href="${path }/lectureList.lec?section=${section}&pageNum=${page}">${page } </a>
							</c:when>

							<%--페이지 번호를 클릭하면 section값과 pageNum값을 컨트롤러로 전송 합니다. --%>
							<c:otherwise>
								<a class="no-uline" href="${path}/lectureList.lec?section=${section}&pageNum=${page}">${page } </a>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</c:when>
			</c:choose>
		</c:if>
	</div>
</body>
</html>