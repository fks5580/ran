 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
 <style type="text/css">
  .form{
 width: 475px;}
 </style>
</head>

 <div class="container">
	<div class="row align-items-end justify-content-center text-center">			
			<img src="${path }/images/join.png">			
	</div>
</div>

<body data-spy="scroll" data-target=".site-navbar-target" data-offset="300">
 <div class="site-wrap">

    <div class="custom-breadcrumns border-bottom">
      <div class="container">
        <a href="${path}/index.do">Home</a>
        <span class="mx-3 icon-keyboard_arrow_right"></span>
        <span class="current">회원가입</span>
      </div>
    </div>

    <div class="site-section">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-5">
                    <div class="row">
                    <form class="form" onsubmit="return submitter();" action="insertMember.do" method="post">
                        <div class="col-md-12 form-group">
                            <label for="email">이메일</label>
                            <input type="email" name="email" id="email" class="form-control form-control-lg" onkeyup="emailDupChk();">
                        </div>
                        <div class="col-md-12 form-group">
                            <label for="pw1">패스워드</label>
                            <input type="password" name="pw1" id="pw1" class="form-control form-control-lg" onkeyup="pwdRegChk();">
                        </div>
                        <div class="col-md-12 form-group">
                            <label for="pw2">패스워드 확인</label>
                            <input type="password" name="pw2" id="pw2" class="form-control form-control-lg" onkeyup="pwdEqualChk();">
                        </div>
                        <div class="col-md-12 form-group">
                            <label for="name">이름</label>
                            <input type="text" name="name" id="name" class="form-control form-control-lg" onkeyup="nameChk();">
                        </div>
                         <div class="col-md-12 form-group">
                            <label for="gender">성별</label><br>
                            <input type="radio" name="gender" id="gender1" value="1" onchange="selectGenderChk();">남 &nbsp; 
							<input type="radio" name="gender" id="gender2" value="2" onchange="selectGenderChk();">여<br>
                        </div>
                         <div class="col-md-12 form-group">
                            <label for="birth">생년월일</label><br>
                           	<label for="birth_year"></label>
							<c:set var="i" value="1980"></c:set>
							<!-- int i = 0 와 같은 의미 -->
							<select name="birth_year">
								<c:forEach begin="1900" end="2020" step="1" var="year">
									<option value="${year}" <c:if test="${year==i}"> selected </c:if>>${year}년</option>
								</c:forEach>
							</select>
							<label for="birth_month"></label>
							<select name="birth_month">
								<c:forEach begin="1" end="12" step="1" var="month">
									<option value="${month}">${month}월</option>
								</c:forEach>
							</select>
							<label for="birth_day"></label>
							<select name="birth_day">
								<c:forEach begin="1" end="31" step="1" var="day">
									<option value="${day}">${day}일</option>
								</c:forEach>
							</select>
						</div>
				        <div class="col-md-12 form-group">
                            <label for="phonenumber">휴대전화</label>
                            <input type="text" name="phonenumber" id="phonenumber" onkeyup="phoneChk();" class="form-control form-control-lg">
                        </div>            
                        <div class="col-md-12 form-group">
                            <label for="address">주소</label>
                          	<input type="text" name="address" id="address" placeholder="우편번호" readonly="readonly" class="form-control form-control-lg" onblur="addressChk();"> <br> 
							<input type="button" onclick="DaumPostcode();" class="btn btn-primary btn-lg" value="우편번호 찾기"><br>
							<input type="text" name="address1" id="address1" placeholder="주소" readonly="readonly" class="mt-5 form-control form-control-lg" onblur="addressChk();"> <br> 
							<input type="text" name="address2" id="address2" placeholder="상세주소" class="form-control form-control-lg" onblur="addressChk();"> <br>
				        </div>
				        <div class="col-md-12 form-group">
                         	<label for="address">sms수신동의</label>
                    		<input type="checkbox" class="mb-5" name="sms" id="sms" value="1"><br> 
                    		<input type="submit" class="btn btn-primary btn-lg" value="이메일 인증">
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