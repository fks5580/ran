<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<div class="footer">
      <div class="container">
        <div class="row">
          <div class="col-lg-3">
            <p class="mb-4"><img src="${path }/images/logo.png" alt="Image" class="img-fluid"></p>
            <p><b>부산 부산진구 동천로 109</b><br>
           <b>삼한골든게이트 7층</b></p>
            <p><b>아이티윌/아이티런</b><br>
            TEL. 051-803-0909</p> 
           
          </div>
          <div class="col-lg-3">
            <h3 class="footer-heading"><span>개발팀</span></h3>
            <ul class="list-unstyled">
                <li>김혜란</li>
                <li>배재영</li>
                <li>김하은</li>
                <li>문정빈</li>
                <li>김수진</li>
                <li>김태균</li>
            </ul>
          </div>
          <div class="col-lg-3">
              <h3 class="footer-heading"><span>자주 찾는 메뉴</span></h3>
              <ul class="list-unstyled">
                  <li><a href="lectureList.lec">강의</a></li>
                  <li><a href="bookList.text">도서</a></li>
                  <li><a href="questionList.ques">고객센터</a></li>
                  <li><a href="resourceList.bo">자료실</a></li>  
              </ul>
          </div>
          <div class="col-lg-3">
              <h3 class="footer-heading"><span>홈페이지</span></h3>
              <ul class="list-unstyled">
                  <li><a href="login.do">로그인</a></li>
                  <li><a href="joinMember.do">회원가입</a></li>             
              </ul>
          </div>
        </div>

        <div class="row">
          <div class="col-12">
            <div class="copyright">
                <p>
                    <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
                    Copyright &copy;<script>document.write(new Date().getFullYear());</script> All rights reserved </a>
                    <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
                    </p>
            </div>
          </div>
        </div>
      </div>
    </div>