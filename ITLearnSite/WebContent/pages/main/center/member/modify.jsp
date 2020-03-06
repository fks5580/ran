<%@page import="member.db.MemberBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
<%
	request.setCharacterEncoding("UTF-8");
	String email = (String)session.getAttribute("email"); 
%>


<style type="text/css">
.form {
	width: 475px;
}
 @font-face { font-family: 'NIXGONM-Vb'; src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_six@1.2/NIXGONM-Vb.woff') format('woff'); font-weight: normal; font-style: normal; }
h2,h4{
font-family: 'NIXGONM-Vb';}
input[type=password] {
font-family: fantasy;
}


</style>

</head>

<body data-spy="scroll" data-target=".site-navbar-target" data-offset="300">

 <div class="container">
	<div class="row align-items-end justify-content-center text-center">			
			<img src="${path }/images/mypage1.png">			
	</div>
</div>


		<div class="custom-breadcrumns border-bottom">
			<div class="container">
				<a href="index.do">Home</a> <span class="mx-3 icon-keyboard_arrow_right"></span> <span class="current">마이페이지</span>
			</div>
		</div>


		<div class="justify-content-center text-center section-title-underline mt-5">
			<h2>
				<span>회원정보 수정</span>
			</h2>
		</div>

		<div class="site-section">
			<div class="container">
				<div class="row justify-content-center">
					<div class="col-md-5">
						<div class="row">
							<form class="form" onsubmit="return modifySubmitter();" action="UpdateMember.do" method="post">
								<div class="col-md-12 form-group">
									<div class="form-group">
										<h4>기본 정보</h4>
									</div>
									<label for="email">아이디</label> <input type="email" name="email" id="email" readonly value="${mBean.email}" class="form-control form-control-lg">
								</div>
								<div class="col-md-12 form-group">
									<label for="pw1">패스워드</label> <input type="password" name="pw1" id="pw1" class="form-control form-control-lg" onkeyup="pwdRegChk();"
										required="required">
								</div>
								<div class="col-md-12 form-group">
									<label for="pw2">패스워드 확인</label> <input type="password" name="pw2" id="pw2" class="form-control form-control-lg" onkeyup="pwdEqualChk();"
										required="required">
								</div>
								<div class="col-md-12 form-group">
									<label for="name">이름</label> <input type="text" name="name" id="name" value="${mBean.name}" readonly="readonly" class="form-control form-control-lg">
								</div>
								<div class="col-md-12 form-group">
									<label for="gender">성별</label><br>
									<c:if test="${mBean.gender eq '1'}">
										<input type="radio" name="gender" id="gender1" value="1" required="required" checked="checked">남 &nbsp; 
										<input type="radio" name="gender" id="gender2" value="2" required="required" disabled="disabled">여<br>
									</c:if>
									<c:if test="${mBean.gender eq '2'}">
										<input type="radio" name="gender" id="gender1" value="1" required="required" disabled="disabled">남 &nbsp; 
										<input type="radio" name="gender" id="gender2" value="2" required="required" checked="checked">여<br>
									</c:if>
								</div>
								<div class="col-md-12 form-group mb-3">
									<label for="birth">생년월일</label><br> <label for="birth_year">년</label> <input type="text" name="birth_year" value="${mBean.birth_year}" readonly
										class="form-control form-control-lg"> <label for="birth_month">월</label> <input type="text" name="birth_month" value="${mBean.birth_month}"
										readonly class="form-control form-control-lg"> <label for="birth_day">일</label> <input type="text" name="birth_day" value="${mBean.birth_day}"
										readonly class="form-control form-control-lg">
								</div>

								<div class="form-group mt-5">
									<h4>추가 정보</h4>
								</div>
								<div class="col-md-12 form-group">
									<label for="phonenumber">휴대전화</label> <input type="text" name="phonenumber" id="phonenumber" value="${mBean.phonenumber}" required="required"
										class="form-control form-control-lg">
								</div>
								<div class="col-md-12 form-group">
									<label for="address">주소</label> <input type="text" name="address" id="address" placeholder="우편번호" value="${mBean.address}" readonly="readonly"
										class="form-control form-control-lg"> <br> <input type="button" onclick="DaumPostcode();" class="btn btn-outline-dark btn-lg" value="우편번호 찾기"><br>
									<input type="text" name="address1" id="address1" placeholder="주소" value="${mBean.address1}" readonly="readonly" required="required"
										class="mt-5 form-control form-control-lg"> <br> <input type="text" name="address2" id="address2" placeholder="상세주소"
										value="${mBean.address2}" required="required" class="form-control form-control-lg"> <br>
								</div>
								<div class="col-md-12 form-group">
									<label for="address">sms수신동의</label><br> <input type="checkbox" class="mb-5" name="sms" id="sms" value="1"><br>
								</div>
								<div class="col-md-12 form-group text-center">
									<input type="submit" id="submit" value="수정하기" class="btn btn-outline-success btn-lg"> <input type="button" value="탈퇴하기"
										onclick="location.href='${path}/MemberDeleteAction.do'" class="btn btn-outline-secondary btn-lg">
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
	<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script type="text/javascript" src="${path}/js/DaumPostAPI.js"></script>
	<script type="text/javascript" src="${path}/js/commons.js"></script>
</body>
</html>
