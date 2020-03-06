<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>

<div class="site-mobile-menu site-navbar-target">
	<div class="site-mobile-menu-header">
		<div class="site-mobile-menu-close mt-3">
			<span class="icon-close2 js-menu-toggle"></span>
		</div>
	</div>
	<div class="site-mobile-menu-body"></div>
</div>

<div class="py-2 bg-light">
	<div class="container">
		<div class="row align-items-center"></div>
	</div>
</div>
<header class="site-navbar py-4 js-sticky-header site-navbar-target" role="banner">

	<div class="container">
		<div class="d-flex align-items-center">
			<div class="site-logo">
				<a href="index.do" class="d-block"> <img src="${path}/images/logo.jpg" alt="Image" class="img-fluid">
				</a>
			</div>
			<div class="mr-auto">
				<nav class="site-navigation position-relative text-right" role="navigation">
					<ul class="site-menu main-menu js-clone-nav mr-auto d-none d-lg-block">
						<li><a href="lectureList.lec" class="nav-link text-left">강의</a></li>
						<li><a href="bookList.text" class="nav-link text-left">도서</a></li>
						<li><a href="resourceList.bo" class="nav-link text-left">자료실</a></li>
						<li><a href="questionList.ques" class="nav-link text-left">고객센터</a></li>

						<li class="has-children"><a href="#" class="nav-link text-left">마이페이지</a>
							<ul class="dropdown">
								<li><a href="relogin.do">회원정보 수정</a></li>
								<li><a href="${path}/myLecture.lec?email=${email}">내 강의실</a></li>
								<li><a href="paymentCheck.pay">주문 확인</a></li>
							</ul></li>
						<c:if test="${email == 'admin@admin.com' }">
						<li class="has-children"><a href="#" class="nav-link text-left">관리자</a>
							<ul class="dropdown">
								<li><a href="memberlist.admin">회원 관리</a></li>
								<li><a href="memberpaymentlist.admin">주문 관리</a></li>
								<li><a href="bookaddpage.text">도서 등록</a></li>
								<li><a href="bookstock.text">도서 재고 관리</a></li>
								<li><a href="lectureForm.lec">강의 등록</a></li>
							</ul>
						</li>
						</c:if>
					</ul>
				</nav>
			</div>

			<div class="col-lg-3 text-right">
				<c:if test="${email == null }">
					<a href="login.do" class="small mr-3"><span class="icon-unlock-alt"></span> 로그인</a>
					<a href="joinMember.do" class="small btn btn-primary px-4 py-2 rounded-0"><span class="icon-users"></span> 회원가입</a>
				</c:if>

				<c:if test="${email != null }">
					<small>${email} 로그인 중</small>
					<ul>
						<a href="logout.do" class="small mr-3"><span class="icon-unlock-alt"></span> 로그아웃</a>
						<!-- 장바구니 -->
						<a href="cart.cart"><img src="${path}/images/cart.png" height="40px" width="40px"></a>
					</ul>
					<%-- <a href="${path}/member/register.jsp" class="small btn btn-primary px-4 py-2 rounded-0"><span class="icon-users"></span> 회원가입</a> --%>
				</c:if>
			</div>
		</div>
	</div>
</header>
