<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<c:set var="path" value="${pageContext.request.contextPath}" />
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-1.12.4.js"></script>
<script src="http://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>


<style type="text/css">
#loading {
 width: 100%;  
 height: 100%;  
 top: 0px;
 left: 0px;
 position: fixed;  
 display: block;  
 opacity: 0.7;  
 background-color: #fff;  
 z-index: 99;  
 text-align: center; } 
  
#loading-image {  
 position: absolute;  
 top: 50%;  
 left: 50%; 
 z-index: 100; }


    #popWindow {
        position:fixed;
        display:none;
        background-color:#000000;
        right:1px;
        bottom:1px;
        z-index:2;
    }

    #closeBtn {
        position:absolute;
        width:24px;
        z-index:1;
        margin-left:-16px;
        margin-top:10px;
        
    }

    #popBottom {
        height:28px;
        text-align:left;
        font-weight:bold;
        color:#FFFFFF;
        padding-left:10px;
        padding-right:10px;
    }
    
    #popClose{
    float:right;
    }
    
    .bookimg{
    width:80px;
    height:30px;
    }
</style>

<script type="text/javascript">
$(document).ready(function(){             
        
       var result = getCookie('popWindow');
 	   if (result == 'end') {
 	      $("#popWindow").css("display","none");
 	      }else{
 	    	  $("#popWindow").css("display", "block");
 	         $("#popWindow").draggable();   
 	      }
 	
 	   
})

$(window).load(function() {    
     $('#loading').hide();  
    });


    function popWindowClose() {
        jQuery("#popWindow").css("display", "none");
    }
    
    
	function noshow(){	   
	   setCookie( "popWindow", "end" , 1);
	   $("#popWindow").css("display","none");	   
	}
	  
	 //셋쿠키 
	function setCookie(cname, value, expire) {
	      var todayValue = new Date();
	      // 오늘 날짜를 변수에 저장
	      todayValue.setDate(todayValue.getDate() + expire);
	      document.cookie = cname + "=" + encodeURI(value) + "; expires=" + todayValue.toGMTString() + "; path=/;";
	   }   
	 
	 function getCookie(name) { 
	      var cookieName = name + "=";
	      var x = 0;
	      while ( x <= document.cookie.length ) { 
	         var y = (x+cookieName.length); 
	         if ( document.cookie.substring( x, y ) == cookieName) { 
	            if ((lastChrCookie=document.cookie.indexOf(";", y)) == -1) 
	               lastChrCookie = document.cookie.length;
	            return decodeURI(document.cookie.substring(y, lastChrCookie));
	         }
	         x = document.cookie.indexOf(" ", x ) + 1; 
	         if ( x == 0 )
	            break; 
	         } 
	      return "";
	   }    
  
</script>


</head>
 

<body data-spy="scroll" data-target=".site-navbar-target" data-offset="300">



	<div class="hero-slide owl-carousel site-blocks-cover">
		<div class="intro-section">
			<img src="images/hero_1.jpg" data-aos="fade-up">
		</div>
		<div class="intro-section">
			<img src="images/hero_3.jpg" data-aos="fade-up">
		</div>
	</div>
	<div></div>

<div id="loading"><img id="loading-image" src="images/loadingImg.gif" alt="Loading..." /></div>



	<!-- 팝업 -->
			
			<div>
				<div id="popWindow">
				
					<!-- <a href="javascript:;" onClick=""> --> <img
						src="${path}/images/sale.png" style="position: static;"/>
					
					<div id="popBottom">
						<label> <input type="checkbox" onClick="noshow();"> 오늘 하루 이 창을 열지 않습니다.
						
						</label>
						<div id="popClose" style="text-align: right;">
						<a href="javascript:;" onClick="popWindowClose();">close</a>
					</div>
					</div>
					
				</div>
			</div>
			

	<div class="site-section">
		<div class="container">

			<div class="row mb-5 justify-content-center text-center">
				<div class="col-lg-6 mb-5">
					<h2 class="section-title-underline mb-3">
						<span>인기 강좌</span>
					</h2>
				</div>
			</div>

			<div class="row">
				<div class="col-12">
					<div class="owl-slide-3 owl-carousel">
						<div class="course-1-item">
							<figure class="thumnail">
								<a href="${path}/lectureList.lec"><img src="pages/main/center/lecture/temp/java강의.png" alt="Image" class="img-fluid"></a>
								<div class="price">50000원</div>
								<div class="category">
									<h3>java 강의</h3>
								</div>
							</figure>							
						</div>
						<div class="course-1-item">
							<figure class="thumnail">
								<a href="${path}/lectureList.lec"><img src="pages/main/center/lecture/temp/c강의.png" alt="Image" class="img-fluid"></a>
								<div class="price">50000원</div>
								<div class="category">
									<h3>c언어 강의</h3>
								</div>
							</figure>					
						</div>
						<div class="course-1-item">
							<figure class="thumnail">
								<a href="${path}/lectureList.lec"><img src="pages/main/center/lecture/temp/oracle강의.png" alt="Image" class="img-fluid"></a>
								<div class="price">50000원</div>
								<div class="category">
									<h3>oracle 강의</h3>
								</div>
							</figure>		
						</div>		
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- // 05 - Block -->
	<div class="site-section">
		<div class="container">
			<div class="row mb-5 justify-content-center text-center">
				<div class="col-lg-4">
					<h2 class="section-title-underline">
						<span>인기 교재</span>
					</h2>
				</div>
			</div>

			<div class="owl-slide owl-carousel">
				<div class="ftco-testimonial-1">
					<div class="ftco-testimonial-vcard d-flex align-items-center mb-4">
						<img src="images/Java.jpg" alt="Image" class="img-fluid mr-3" width=100%;>
						<div>
							<h3>자바의 정석</h3>
							<span>남궁성</span>
						</div>
					</div>
					<div>
						<p>&ldquo;학생들에게 직접 검증받고 호평받은 책. 코딩을 처음 배운 사람에게도 쉽게 자바를 배울 수 있도록 한다. &rdquo;</p>
					</div>
				</div>

				<div class="ftco-testimonial-1">
					<div class="ftco-testimonial-vcard d-flex align-items-center mb-4">
						<img src="images/ncs.jpg" alt="Image" class="img-fluid mr-3">
						<div>
							<h3>2020 시나공 정보처리기사 실기</h3>
							<span>길벗</span>
						</div>
					</div>
					<div>
						<p>&ldquo;『시나공 정보처리기사 실기』는 NCS 학습 모듈을 가이드 삼아 실무에서 방대하게 다뤄지는 내용들을 압축하여 최대한 쉽게 수록했다. 예제나 문제는 간단해 보이지만 원론을 이해하기 쉽도록 간략하게 구성한 것으로 교재의 내용만 이해하면 어떠한 변형 문제도 풀 수 있을 것이다. 실기 시험은 외워서는 절대 합격할 수 없다. 이 책에는 컴퓨터 관련 초보자도 수월하게 공부할 수 있도록 자세하고 쉬운 설명이 들어 있다. 또한 1장 프로그래밍 언어 활용, 8장 SQL 응용 그리고 프로그램 코드가 나오는 어려운 부분은 모두 동영상 강의를 제공하니 QR코드를 스캔해 보자. &rdquo;</p>
					</div>
				</div>

				<div class="ftco-testimonial-1">
					<div class="ftco-testimonial-vcard d-flex align-items-center mb-4">
						<img src="images/jsp.jpg" alt="Image" class="img-fluid mr-3">
						<div>
							<h3>JSPStudy의 JSP & Servlet 웹 프로그래밍 입문 + 활용</h3>
							<span>정동진, 최주호, 윤성훈 저</span>
						</div>
					</div>
					<div>
						<p>&ldquo;JSP & Servlet의 동작원리를 도식화로 알기 쉽게 설명하였고, 기초 문법부터 웹사이트 실전 제작 실습까지 구체적인 구현 방법을 처음 시작하는 초보자의 눈높이에 맞춘 입문 활용서이다. 책의 핵심 구성은 “웹과 서블릿 및 JSP 동작원리 이해 → 알기 쉬운 기초 문법 → 바로 적용하여 구현할 수 있는 실무 완성”이며, 각 과정은 다양한 도식화와 예제를 통해 실행 흐름을 쉽게 이해할 수 있고 실무 프로젝트를 통해 구체적인 구현 방법을 습득할 수 있다. &rdquo;</p>
					</div>
				</div>
			</div>
			
			
			<br><br><br>
			 <div class="site-section">
      <div class="container">    
        <div class="row">
          <div class="col-lg-4 col-md-6 mb-4 mb-lg-0">

            <div class="feature-1 border">
              <div class="icon-wrapper bg-primary">
                <span class="flaticon-mortarboard text-white"></span>
              </div>
              <div class="feature-1-content">
                <h2>자료실</h2>
                <p>시험/자격증 등의 각종 자료를 무료로 찾아보세요. (회원만 이용가능)</p>
                <p><a href="resourceList.bo" class="btn btn-primary px-4 rounded-0">바로가기</a></p>
              </div>
            </div>
          </div>
          <div class="col-lg-4 col-md-6 mb-4 mb-lg-0">
            <div class="feature-1 border">
              <div class="icon-wrapper bg-primary">
                <span class="flaticon-school-material text-white"></span>
              </div>
              <div class="feature-1-content">
                <h2>내 강의실</h2>
                <p>인터넷 강의를 구매한 분들만 이용하실 수 있는 강의실입니다.</p>
                <p><a href="myLecture.lec" class="btn btn-primary px-4 rounded-0">바로가기</a></p>
              </div>
            </div> 
          </div>
          <div class="col-lg-4 col-md-6 mb-4 mb-lg-0">
            <div class="feature-1 border">
              <div class="icon-wrapper bg-primary">
                <span class="flaticon-library text-white"></span>
              </div>
              <div class="feature-1-content">
                <h2>고객센터</h2>
                <p>ITLearn에 궁금한 사항과 사이트 공지사항을 확인해보세요.</p>
                <p><a href="questionList.ques" class="btn btn-primary px-4 rounded-0">바로가기</a></p>
              </div>
            </div> 
          </div>
        </div>
      </div>
    </div>
			
			
		</div>
	</div>
</body>
</html>