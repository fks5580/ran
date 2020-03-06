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
<c:set var="questionsList1" value="${questionsMap1.questionsList1}" /> <!-- 공지사항리스트 -->
<c:set var="questionsList2" value="${questionsMap2.questionsList2}" /> <!-- 일반글리스트 -->
<c:set var="totQuestions" value="${questionsMap2.totQuestions}" /> <!-- 일반글 개수 -->
<c:set var="countNotice" value="${countNotice}" /> <!-- 공지사항 개수 -->
<c:set var="section" value="${questionsMap2.section}" />
<c:set var="pageNum" value="${questionsMap2.pageNum}" />
<c:set var="email" value="${email}" />
<c:set var="path" value="${pageContext.request.contextPath}" />
<c:set var="opt" value="${questionsMap2.opt}"/>
<c:set var="condition" value="${questionsMap2.condition}"/>

<script src='https://kit.fontawesome.com/a076d05399.js'></script>
<style type="text/css">

 #width_writedate {
 clear:both;
    width:20%;
  }


</style>
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
					<table class="table table_width">
						<thead id="noticethead" class="table-light">
							<tr class="tb_head">
								<td>번호</td>
								<td>제목</td>
								<td>글쓴이</td>
								<td id="width_writedate">작성일</td>
								<td width="10%">조회수</td>
							</tr>
						</thead>
						
						<tbody>
							<%-- 공지사항이 있으면 공지사항 표시 --%>
							<c:if test="${questionsList1 != null }">
								<c:forEach var="question" items="${questionsList1 }" varStatus="questionNum">
										<tr align="center" class="table-light">
											<td width="5%" class="text-success">공지</td>
											<td align="left" width="30%">
												<a class="cls1" href="questionView.ques?ques_no=${question.ques_no}">${question.ques_title}</a>
											</td>
											<td width="10%">${question.ques_email }</td>
											<td width="10%"><fmt:formatDate value="${question.ques_writedate}" /></td>
											<td width="10%">${question.ques_readcount }</td>
										</tr>
								</c:forEach>
							</c:if>
							<%-- 일반글이 없으면 --%>
							<c:if test="${questionsList2 == null }">
								<tr align="center">
									<td colspan="5">
										<p><b><span style="font-size: 9pt;">등록된 글이 없습니다.</span></b></p>
									</td>
								</tr>
							</c:if>
							<%-- 일반글이 있으면 --%>
							<c:if test="${questionsList2 != null }">
								<%--request 영역에 바인딩된 ArrrayList 객체의 크기(검색한 글의 개수)만큼 반복, 글목록 표시  --%>
								<c:forEach var="question" items="${questionsList2 }" varStatus="questionNum">
										<tr align="center">
											<td width="5%">${question.ques_no }</td>
											<td align="left" width="30%">
												<%-- 비밀글이면 자물쇠 그림 표시 --%>
												 <c:if test="${question.isSecret == 'y' }">
														<span><i class='fas fa-lock'></i></span>
												</c:if>
												
												 <c:choose>
												 	<%-- 답변글(level값이 1보다 클 때) --%>
													<c:when test="${question.level > 1 }"> 
														<%-- 들여쓰기하여 답글표시 --%>
														<c:forEach begin="1" end="${question.level }" step="1">
															<span style="padding-left: 10px;"></span>
														</c:forEach>
														<span style="font-size: 12px;">[답변]</span>
														
 														<%-- 비밀글이면 관리자이거나 세션의 아이디가 부모글의 작성자와 같을 때만 타이틀 링크를 보여줌--%>
														<c:if test="${question.isSecret == 'y' }">
															<c:choose>
																<c:when test="${email == 'admin@admin.com' or email == question.ques_parentemail}">
																<a class="cls1" href="questionView.ques?ques_no=${question.ques_no}">${question.ques_title}</a>
																</c:when>
																<c:otherwise> 
																<a class="cls1">비밀글입니다.</a>
																</c:otherwise>
															</c:choose>
														</c:if>
														<%-- 공개글이면  --%>
														<c:if test="${question.isSecret == 'n' }">
																<a class="cls1" href="questionView.ques?ques_no=${question.ques_no}">${question.ques_title}</a>
														</c:if>
													</c:when> 
													<%-- 답변글 종료 --%>
													<%-- 부모글 시작 --%>
													<c:otherwise> 
														<%-- 비밀글이면 세션의 아이디가 관리자이거나 글 작성자와 같을 때 타이틀 링크 출력  --%>
														<c:if test="${question.isSecret == 'y' }">
															<c:choose>
																<c:when test="${email == 'admin@admin.com' or email == question.ques_email}">
																<a class="cls1" href="questionView.ques?ques_no=${question.ques_no}">${question.ques_title}</a>
																</c:when>
																<c:otherwise>
																<a class="cls1">비밀글입니다.</a>
																</c:otherwise>
															</c:choose>
														</c:if>
														<%-- 공개글이면  --%>
														<c:if test="${question.isSecret == 'n' }">
															<a class="cls1" href="questionView.ques?ques_no=${question.ques_no}">${question.ques_title}</a>
														</c:if>
													</c:otherwise>
													<%-- 부모글 종료 --%>
												</c:choose>
											</td>
											<td width="7%">${question.ques_email }</td>
											<td width="7%"><fmt:formatDate value="${question.ques_writedate}" /></td>
											<td width="7%">${question.ques_readcount }</td>
										</tr>
								</c:forEach>
							</c:if>
						</tbody>
					</table>
				</div>
			</div>
			
			
			<div class="btn-wrap">
				<div class="header-btn float-l">
					<a href="questionList.ques">
						<button class="btn btn-outline-dark">
							<span>전체글</span>
						</button>
					</a>
				</div>

				<div class="header-btn float-r">
					<a href="questionWrite.ques">
						<button class="btn btn-outline-dark">
							<span>글쓰기</span>
						</button>
					</a>
				</div>
			</div>
			<div class="btn-wrap text-align" style="margin-top:20px; margin-bottom: 20px">
				<%--전체 글수에 따라 페이징 표시를 다르게 합니다. --%>
				<c:if test="${totQuestions != null }">
					<c:choose>
						<c:when test="${totQuestions >(10-countNotice)*10 }">
							<!-- 공지사항을 제외한 전체 글 수가 10페이지를 넘길 때 -->
							<c:forEach var="page" begin="1" end="10" step="1">
								<%--섹션값 2부터는 앞 섹션으로 이동할수 있는 pre를 표시합니다. --%>
								<c:if test="${section >1 && page==1 }">
									<a class="no-uline" href="${path }/questionList.ques?section=${section-1}&pageNum=1">&nbsp; pre </a>
								</c:if>
								
								<%--게시글이 있는 페이지까지만 페이징 처리함 --%>
								<c:if test="${(section-1)*10 +page < totQuestions/(10-countNotice) +1}">
									<a class="no-uline" href="${path }/questionList.ques?section=${section}&pageNum=${page}&opt=${opt}&condition=${condition}">${(section-1)*10 +page } </a>
								</c:if>
								
								<%--페이지번호 10 오른쪽에는 다음섹션으로 이동할수 있는 next를 표시합니다.--%>
								<c:if test="${page ==10 }">
									<c:if test="${(section-1)*10 +page < totQuestions/(10-countNotice) +1}">
									<a class="no-uline" href="${path }/questionList.ques?section=${section+1}&pageNum=1">&nbsp; next</a>
									</c:if>
								</c:if>
							</c:forEach>
						</c:when>
						
						<%--공지사항을 제외한 전체글수가 10페이지일 때 10개의 페이지만 표시 --%>
						<c:when test="${totQuestions == (10-countNotice)*10  }">
							<c:forEach var="page" begin="1" end="10" step="1">
								<a class="no-uline" href="${path }/questionList.ques?section=${section}&pageNum=${page}&opt=${opt}&condition=${condition}">${page } </a>
							</c:forEach>
						</c:when>


						<c:when test="${totQuestions < (10-countNotice)*10  }">
							<!--등록된 글 개수가 10페이지 미만일 경우  -->
							<%--글수가 10페이지를 채우지 않으므로 표시되는 페이지는 10개가 되지 않고, 공지사항을 제외한 전체 글수를 한페이지에 들어갈 글 수로 나누어 구한 몫에 1을 더한 페이지까지 표시합니다. --%>
							<c:forEach var="page" begin="1" end="${totQuestions/(10-countNotice) +1}" step="1">
								<c:choose>
									<%-- 페이지번호와 컨트롤러에서 넘어온 pageNum이 같은경우 페이지번호를 빨간색으로 표시하여 현재 사용자가 보고 있는 페이지임을 나타냄 --%>
									<c:when test="${page==pageNum }">
										<a class="sel-page" href="${path }/questionList.ques?section=${section}&pageNum=${page}&opt=${opt}&condition=${condition}">${page } </a>
									</c:when>

									<%--페이지 번호를 클릭하면 section값과 pageNum값을 컨트롤러로 전송 합니다. --%>
									<c:otherwise>
										<a class="no-uline" href="${path}/questionList.ques?section=${section}&pageNum=${page}&opt=${opt}&condition=${condition}">${page } </a>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:when>
					</c:choose>
				</c:if>
			</div>
			<div class="btn-wrap text-align">
			<form action="questionList.ques" method="post" >
				<div class="selector-wrap">
					<select class="box selectbox" name="opt">
							<option value="ques_title" selected="selected">제목</option>
							<option value="ques_content">내용</option>
							<option value="ques_email">글쓴이</option>							
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