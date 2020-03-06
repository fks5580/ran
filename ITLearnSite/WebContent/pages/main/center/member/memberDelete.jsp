<%@page import="com.mysql.cj.Session"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="${path}/css/container.css" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic&display=swap" rel="stylesheet">
<link href="${path}/css/memberDelete.css" rel="stylesheet">

<script type="text/javascript">
	function checkForm() {
		if (!document.del.agree.checked) {
			alert("탈퇴 안내를 확인하고 동의해 주세요.");
			return false;
		}
	}
</script>
<%
	String email = (String) session.getAttribute("email");
%>
</head>
<body>
	<div class="header">
	</div>
	<div class="container">
		<div class="showcaseWrap">
			<div class="showcaseHead">
				<div class="inner">
					<div class="showcaseContent">
						<h1 class="showcaseTitle">탈퇴 안내</h1>
						<p class="showcaseDescription">
							회원 탈퇴를 하기 전에 <em>안내사항</em>을 꼭 확인해주세요
						</p>

					</div>

					<div class="content">
						<p class="msg">
							사용하고 계신 아이디는 탈퇴할 경우 재사용 및 복구가 불가능합니다.<br> 탈퇴한 아이디는 본인과 타인 모두 재사용 및 복구가 불가하오니 신중하게 선택하시기 바랍니다.<br> <br> 탈퇴 후 회원정보 및 개인형 서비스 이용기록은
							모두 삭제됩니다.<br> 회원정보 및 메일, 블로그, 주소록 등 개인형 서비스 이용기록은 모두 삭제되며, 삭제된 데이터는 복구되지 않습니다.<br> 삭제되는 내용을 확인하시고 필요한 데이터는 미리 백업을 해주세요.<br> 탈퇴
							후에도 게시판형 서비스에 등록한 게시물은 그대로 남아 있습니다.<br> 삭제를 원하는 게시글이 있다면 반드시 탈퇴 전 비공개 처리하거나 삭제하시기 바랍니다.<br> 탈퇴 후에는 회원정보가 삭제되어 본인 여부를 확인할 수 있는 방법이 없어,
							게시글을 임의로 삭제해드릴 수 없습니다.<br> 탈퇴 후에는 아이디로 다시 가입할 수 없으며 아이디와 데이터는 복구할 수 없습니다.<br> 게시판형 서비스에 남아 있는 게시글은 탈퇴 후 삭제할 수 없습니다.<br>
						</p>
						<form action="MemberDeleteAction1.do" name="del" id="del" method="post" onsubmit="return checkForm();">
							<p>
								안내사항을 모두 확인하였으며, 이에 동의합니다. <input type="checkbox" id="agree" />
							</p>
							<input type="hidden" name="email" id="email"> <input type="submit" value="탈퇴" class="btn_1">
						</form>

					</div>
				</div>

			</div>

		</div>
	</div>
	<div>
	</div>
</body>
</html>