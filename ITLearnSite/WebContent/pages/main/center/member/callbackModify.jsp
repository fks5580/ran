<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript"
	src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js"
	charset="utf-8"></script>
<script src="${path}/js/jquery-3.3.1.min.js"></script>
</head>
<body>
<%-- 	<%=response.sendRedirect("callbackModify.do") %> --%>
	<script type="text/javascript">
		var naver_id_login = new naver_id_login("NhEDrGuvXvRCwueSRLso",
				"http://localhost:8090/ITLearnSite/callbackModify.do");
		var email = "";

		var url = "naverModifylogin.do";
		naver_id_login.get_naver_userprofile("naverSignInCallback()");
		// 네이버 사용자 프로필 조회 이후 프로필 정보를 처리할 callback function
		function naverSignInCallback() {
			email = naver_id_login.getProfileData("email");
			console.log("email : " + email);
			
			var form_data = {
				email : email,
			}
			$.ajax({
						type : "post",
						url : url,
						data : form_data,
						dataType : "text",
						contentType : "application/x-www-form-urlencoded; charset=UTF-8",
						success : function(getData) {
							console.log("asdf" + getData);
							if(getData == "success")
							{
								window.close();
								$(opener.document).find("#form").attr("action", 'naverModify.do').submit();
							}
						},
						error : function(request, status, error) {
							alert("code = " + request.status + " message = "
									+ request.responseText + " error = "
									+ error);
							console.log(error);
						}
					});
		}
	</script>
</body>
</html>
