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
<title>자료실 글쓰기</title>


<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
 
</head>
<c:set var="email" value="${sessionScope.email}"></c:set>
<%-- <c:if test="${email eq null}">
	<script type="text/javascript">
		alert("로그인 후 글쓰기가 가능합니다.");
		location.href="resourceList.bo";
	</script>
</c:if> --%>
<body>

<div class="site-wrap">
	
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


	<div class="container mt-5 mb-5">
	<h1>자료실 글쓰기</h1>
  	<form name="resForm" method="post" action="addResource.bo" enctype="multipart/form-data" accept-charset="UTF-8">
    <table class="table" style="text-align: center;">
				<tr>
					<td>글쓴이</td>  
					<td colspan="3"><input type="text" name="res_email" class="form-control" readonly="readonly" value="${email}"></td>
				</tr>
				<tr>
					<td>제목</td>
					<td colspan="3"><input type="text" name="res_title" class="form-control" placeholder="제목을 입력하세요." required="required"></td>
				</tr>
				<tr>
					<td>내용</td>
					<td colspan="3"><textarea name="res_content" class="form-control" placeholder="내용을 입력하세요." rows="10" cols="120"></textarea></td>
				</tr>
				<tr>
			        <td align="right">파일 첨부</td>
				    <td> <input type="file" name="res_filename"/></td>
				</tr>
			</table>
			<div class="text-right">
				<input type="button" value="목록으로" class="btn btn-outline-dark" onclick="location.href='resourceList.bo'">
				<input type="submit" value="글쓰기" class="btn btn-outline-dark">
			</div>
  </form>
  </div>
  </div>
  
  
</body>
</html>