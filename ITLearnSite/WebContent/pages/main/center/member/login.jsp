<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="ko">

<head>
<style type="text/css">
#loginbtn {
	width: 48%;
}

#naver_id_login{
	  float: right;  
}

input[type=password] {
	font-family: fantasy;
}
</style>
<script type="text/javascript"
	src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js"
	charset="utf-8"></script>
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="${path}/js/makejs/socialLogin.js"></script>
</head>
<body data-spy="scroll" data-target=".site-navbar-target"
	data-offset="300">
	<c:if test="${ loginResult == -1 || loginResult == 0 }">
		<script>
			alert("이메일 혹은 비밀번호가 틀렸습니다.");
		</script>
	</c:if>

	<div class="container">
		<div class="row align-items-end justify-content-center text-center">
			<img src="${path }/images/login.png">
		</div>
	</div>



	<div class="custom-breadcrumns border-bottom">
		<div class="container">
			<a href="index.jsp">Home</a> <span
				class="mx-3 icon-keyboard_arrow_right"></span> <span class="current">로그인</span>
		</div>
	</div>

	<div class="site-section">
		<div class="container">

			<form id="form" action="${path}/login1.do" method="post">
				<div class="row justify-content-center">
					<div class="col-md-5">
						<div class="row">
							<div class="col-md-12 form-group">
								<label for="email">이메일</label> <input type="text" id="email"
									name="email" class="form-control form-control-lg">
							</div>
							<div class="col-md-12 form-group">
								<label for="password">비밀번호</label> 
								<input type="password"
									id="password" name="pw" id="pw"
									class="form-control form-control-lg mb-3">
							</div>
						</div>
						<div class="text-center">
								<input type="submit" id="loginbtn" value="로그인"
									class="btn btn-primary btn-lg px-5">
								<div id="naver_id_login"></div>
						</div>
						
						
						<script type="text/javascript">
							var naver_id_login = new naver_id_login(
									"NhEDrGuvXvRCwueSRLso",
									"http://localhost:8090/ITLearnSite/callback.do");
							
							
							var state = naver_id_login.getUniqState();
							naver_id_login.setButton("white", 3, 50);
							naver_id_login.setDomain("http://localhost:8090/ITLearnSite");
							naver_id_login.setState(state);
							naver_id_login.setPopup();
							naver_id_login.init_naver_id_login();
						</script>
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
