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
<c:set var="opt" value="${resourcesMap.opt}"/>
<c:set var="condition" value="${resourcesMap.condition}"/>

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
    
    
	<div class="pagemargin">
		<div class="wrapboard">
			<div class="row">
				<div class="col-lg-12" style="display: inline-block; text-align: center;">
					<div class="btn-wrap">

						<div class="header-btn float-r">
							<a href=""> <!--  -->
								<span class="glyphicon glyphicon-pencil gi-2x"></span>
							</a>
						</div>
					</div>
				</div>
			</div>
			<div class="boardoutline">
				<div class="content">
					<table class="table">
						<thead>
							<tr class="tb_head">
								<td>번호</td>
								<td>제목</td>
								<td>글쓴이</td>
								<td>작성일</td>
							</tr>
						</thead>
						<c:choose>
							<%--
				BoaqrdController.java 서블릿으로부터 전달 받은 request 영역에 저장되어 
				articlesList 속성으로 바인딩된 ArrayList 객체가 저장되어 있지 않다면				
			 --%>
							<c:when test="${resourcesList == null }">
								<tr height="10">
									<td colspan="4"><p>
											<b><span style="font-size: 9pt;">등록된 글이 없습니다.</span></b>
										</p></td>
								</tr>
							</c:when>
							<c:when test="${resourcesList != null }">
								<%--
					BoardController.java 서블릿으로부터 전달받은 request 영역에
					articlesList 속성으로 바인된 ArrrayList 객체의 크기(검색한 글의 개수)만큼 반복, 
					검색한 글정보(ArticleVO)들을 ArrayList 객체 내부의 인덱스 위치로부터 글목록 표시 
				 --%>
								<c:forEach var="resource" items="${resourcesList }" varStatus="resourceNum">
									<tbody>
										<tr align="center">
											<%-- varStatus의 count 속성을 이용 글번호를 1부터 자동으로 표시 --%>
											<td width="5%">${resource.res_no }</td>
											<td align="left" width="35%">
														<a class="cls1" href="resourceView.bo?res_no=${resource.res_no}">${resource.res_title}</a>
											</td>
											<td width="10%">${resource.res_email }</td>
											<td width="10%"><fmt:formatDate value="${resource.res_writedate}" /></td>
										</tr>
									</tbody>
								</c:forEach>
							</c:when>
						</c:choose>
					</table>
				</div>
			</div>
			<div class="btn-wrap">
				<div class="header-btn float-l">
					<a href="resourceList.bo">
						<button class="btn btn-outline-dark">
							<span>전체글</span>
						</button>
					</a>
				</div>

				<c:if test="${ email == 'admin@admin.com'}" >
				<div class="header-btn float-r">
					<a href="resourceWrite.bo">
						<button class="btn btn-outline-dark">
							<span>글쓰기</span>
						</button>
					</a>
				</div>
				</c:if>
			</div>
			<div class="btn-wrap text-align" style="margin-top:20px; margin-bottom: 20px">
			
				<%--전체 글수에 따라 페이징 표시를 다르게 합니다. --%>
				<c:if test="${totResources != null }">
					<c:choose>
						<c:when test="${totResources >100 }">
							<!-- 전체 글수가 100보다 클때... -->
							<c:forEach var="page" begin="1" end="10" step="1">

								<%--섹션값 2부터는 앞 섹션으로 이동할수 있는 pre를 표시합니다. --%>
								<c:if test="${section >1 && page==1 }">
									<a class="no-uline" href="${path }/listArticles.do?section=${section-1}&pageNum=${(section-1)*10 +1 }">&nbsp; pre </a>
								</c:if>

								<%--게시글이 있는 페이지까지만 페이징 처리함 --%>
								<c:if test="${(section-1)*10 +page < totResources/10 +1}">
								<a class="no-uline" href="${path }/resourceList.bo?section=${section}&pageNum=${page}&opt=${opt}&condition=${condition}">${(section-1)*10 +page } </a>
								</c:if>

								<%--페이지번호 10 오른쪾에는 다음섹션으로 이동할수 있는 next를 표시합니다.--%>
								<c:if test="${page ==10 }">
									<c:if test="${(section-1)*10 +page < totResources/10 +1}">
										<a class="no-uline" href="${path }/board8/resourceList.bo?section=${section+1}&pageNum=${section*10+1}">&nbsp; next</a>
									</c:if>
								</c:if>
							</c:forEach>
						</c:when>

						<%--전체글수가 100개일떄는 첫번째 섹션의 10개의 페이지만 표시하면 됩니다. --%>
						<c:when test="${totResources ==100 }">
							<!--등록된 글 개수가 100개인경우  -->
							<c:forEach var="page" begin="1" end="10" step="1">
								<a class="no-uline" href="#">${page } </a>
							</c:forEach>
						</c:when>


						<c:when test="${totResources< 100 }">
							<!--등록된 글 개수가 100개 미만인 경우  -->
							<%--글수가 100개가 되지 않으므로 표시되는 페이지는 10개가 되지 않고, 전체 글수를 10으로 나누어 구한 몫에 1을 더한 페이지까지 표시합니다.--%>
							<c:forEach var="page" begin="1" end="${totResources/10 +1}" step="1">
								<c:choose>
									<%-- 페이지번호와 컨트롤러에서 넘어온 pageNum이 같은경우 페이지번호를 빨간색으로 표시하여 현재 사용자가 보고 있는 페이지임을 나타냄 --%>
									<c:when test="${page==pageNum }">
										<a class="sel-page" href="${path }/resourceList.bo?section=${section}&pageNum=${page}&opt=${opt}&condition=${condition}">${page } </a>
									</c:when>
									<%--페이지 번호를 클릭하면 section값과 pageNum값을 컨트롤러로 전송 합니다. --%>
									<c:otherwise>
										<a class="no-uline" href="${path}/resourceList.bo?section=${section}&pageNum=${page}&opt=${opt}&condition=${condition}">${page } </a>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:when>
					</c:choose>
				</c:if>
			</div>
<div class="btn-wrap text-align">
			<form action="resourceList.bo" method="post" >
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
						<button class="btn btn-light" type="submit"><img src="${path}/images/look.png" width="27px" height="27px"></button>
					</span>
				</div>
			</form>
			</div>
		</div>
	</div>
</body>
</html>