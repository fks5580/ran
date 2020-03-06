<%@ page language="java" contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  /> 
<%
	request.setCharacterEncoding("utf-8");
%>
<head>
<meta charset="UTF-8">
<title>고객센터 글쓰기</title>


<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
 
</head>
<c:set var="email" value="${sessionScope.email}"></c:set>

<c:if test="${email eq null}">
	<script type="text/javascript">
		alert("로그인 후 글쓰기가 가능합니다.");
		location.href="questionList.ques";
	</script>
</c:if>
<body>

<div class="site-wrap">
	
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

	<div class="container mt-5 mb-5">
	<h1>고객센터 글쓰기</h1>
  	<form name="quesForm" method="post" action="addQuestion.ques">
    <table class="table table-striped" style="text-align: center;">
				<tr>
					<td>글쓴이</td>  
					<td><input type="text" name="ques_email" class="form-control" readonly="readonly" value="${email}"></td>
				</tr>
				<tr>
					<td>제목</td>
					<td colspan="3"><input type="text" name="ques_title" class="form-control" placeholder="제목을 입력하세요." required="required"></td>
				</tr>
				<tr>
					<td>내용</td>
					<td colspan="3"><textarea class="form-control" id="ques_content" name="ques_content" placeholder="내용을 입력하세요." rows="10" cols="120"></textarea></td>
				</tr>
				<tr>
					<td>비밀글</td>
					<td colspan="3"><input type="checkbox" name="isSecret" value="y"></td>
				</tr>
				<c:if test="${email == 'admin@admin.com'}">
				<tr>
					<td>공지글</td>
					<td colspan="3"><input type="checkbox" name="isNotice" value="y"></td>
				</tr>
				</c:if>
			</table>
			<div class="text-right">
				<input type="button" value="목록으로" class="btn btn-outline-dark" onclick="location.href='questionList.ques'">
				<input type="submit" value="글쓰기" class="btn btn-outline-dark">
			</div>
  </form>
  
  </div>
  </div>
  
 <%--  <script src="${path }/js/makejs/summerNote.js">
  
  </script> --%>
  
</body>
</html>