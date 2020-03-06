<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
	request.setCharacterEncoding("utf-8");
%>
<c:set var="path" value="${pageContext.request.contextPath}" />
<c:set var="lecBean" value="${lec_DetailMap.lec_Detail }" />
<c:set var="lec_list" value="${lec_DetailMap.lec_list }" />
<c:set var="email" value="${sessionScope.email}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="/ITLearnSite/css/bootstrap.min.css">
<style>
 @font-face { font-family: 'NIXGONM-Vb'; src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_six@1.2/NIXGONM-Vb.woff') format('woff'); font-weight: normal; font-style: normal; }
body{
font-family: 'NIXGONM-Vb';}

html, body {
	height: 100%;
	padding : 0;
	margin: 0;
	padding: 0;
	word-break: break-all;
}

 .wrap {
	width: 100%;
	height: 100%;
} 

.div {
	background-color: #fff;
	color: #5aa;
	float: left;
	border: 1px solid #353535; 
	color: #353535;
}

.tb {
	width: 100%;
} 

#d1 {
	width: 70%;
	height: 70%; 
}

#d2 {
	width: 29.5%;
	height: 70%;
	overflow-y: scroll;
}

#d3 {
	width: 70%;
	height: 29.5%;
	overflow-y: scroll;
}

#d4 {
	width: 29.5%;
	height: 29.5%;
	overflow-y: scroll;
}

#content {
width: 95%;
}

.tb th {
	background-color: #353535;
	color: #fff;
	text-align: center;
	text-transform: uppercase;
}

.tb tr {
	background-color: #fff;
	color: #353535;
}

.tb tr:HOVER {
	background-color: #F2FFED;
	cursor: pointer;
}

.list{
	width:100px;
}

a {
	text-decoration: none;
}
</style>
<%--${path }/pages/main/center/lecture/temp/${listBean.list_savefile} --%>
<script src="${path}/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript">

	function play(list_no, lec_no, list_savefile) {
		
		document.getElementById("lecture").setAttribute("src", "${path }/pages/main/center/lecture/temp/" + list_savefile);
		document.getElementById("list_no").setAttribute("value", list_no );
		document.getElementById("lec_no").setAttribute("value", lec_no );
		cmtlist();	
		
	}
	
	function deleteComment(addr)
	{
		var question = confirm("댓글을 삭제 할까요?");
		if(question == true){
			/*네 삭제합니다*/
			$.ajax({
				type : "post",
				url : addr,
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				dataType : "text",
				success : function(getData) {
					if(getData == 0)
					{
						alert('본인 글만 지울수 있습니다');
					}
					cmtlist();
				},
				error : function(request,status,error){
					alert("code = "+ request.status + " message = " + request.responseText + " error = " + error);
					console.log(error);
				}
			});
		}
		/* 페이지 요청시 댓글을 불러온다.*/
	}
	function cmtlist()
	{
		var url = "commentsList.lec";
		var list_no = $("#list_no").val();
		var lec_no = $("#lec_no").val();
		var addr ="";
		
		var form_data = {
				list_no : list_no,
				lec_no : lec_no
		}
		
		$.ajax({
			type : "post",
			url : url,
			data : form_data,
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			dataType : "json",
			success : function(getData) {
				var string ="";
				
				/* 
				<table class="table">
					<tbody id="cmt">

					</tbody>
				</table>
				 */
				 string = "<button class='btn btn-light ml-3' onclick='cmtlist();'" +">새로고침</button><table class='table'><tbody id='cmt'>"
				for (var i = 0; i < getData.list.length; i++) {
					addr = "commentsDelete.lec?co_no="+getData.list[i].co_no;
					var comments = 
						"<tr>"
						+ 	"<td width='20%'>"+getData.list[i].co_email+"</td>"
						+		"<td width='50%'>"+getData.list[i].co_content+"</td>"
						+ 		"<td width='20%'>"+getData.list[i].co_date+"</td>"
						+		"<td width='10%'>" 
						+			"<button class='btn btn-light' onclick='deleteComment("+'"'+addr+'"'+");'" +">삭제</button>" 
						+		"</td>"
						+ 	"</tr>";
					
					string = string + comments;
				}
				string += "</tbody></table>"
				$("#comments").html(string);
			},
			error : function(request,status,error){
				alert("code = "+ request.status + " message = " + request.responseText + " error = " + error);
				console.log(error);
			}
		});
		/* 페이지 요청시 댓글을 불러온다.*/
	}


	function comments(){
		
		var url = "commentsWrite.lec";
		
		var list_no = $("#list_no").val();
		var lec_no = $("#lec_no").val();
		var co_email = $("#co_email").val();
		var content = $("#content").val();
		
		var form_data = {				
				list_no : list_no,
				lec_no : lec_no,
				co_email : co_email,
				content : content,
		}
		$.ajax({
			type : "post",
			url : url,
			data : form_data,
			dataType : "text",
			success : function(getData) {
				if(getData == 1)
				{
					$("#content").val("");
					cmtlist();
				}
			}
		});
		
	}

</script>
</head>
<body>
<!-- <body onresize="parent.resizeTo(1000,800)" onload="parent.resizeTo(1000,800)"> -->
	<div class="wrap">
		<div id="d1" class="div">
			<iframe frameborder="0" id="lecture" name="lecture"
				<%-- src="${path }/pages/main/center/lecture/temp/${lecBean.lec_spofile }" --%>
			style="width: 100%; height: 100%; background-color: black;"></iframe>
		</div>
		<div id="d2" class="div">
			<table class="tb">
				<tr class="tb_head">
					<th>번호</th>
					<th style="width:200px;">목차</th>
				</tr>
				<c:choose>
					<c:when test="${lec_list == null}">

						<tr align="center">
							<td colspan="4"><p>
									<b><span style="font-size: 9pt;">등록된 강의가 없습니다.</span></b>
								</p></td>
						</tr>
					</c:when>
					<c:when test="${lec_list != null}">
						<c:forEach var="listBean" items="${lec_list }">
							<tr align="center">
								<td width="30%">${listBean.list_no }</td>
								<%--${path }/pages/main/center/lecture/temp/${listBean.list_savefile} --%>
								<td width="70%"><a href="javascript:void(0);"
									onclick="play(${listBean.list_no }, ${listBean.lec_no }, '${listBean.list_savefile }');">${listBean.list_titleStr }</a></td>
							</tr>
						</c:forEach>
					</c:when>
				</c:choose>
				<tr align="center">
					<td colspan="2">&nbsp;&nbsp;&nbsp;</td>
				</tr>
				</tbody>
			</table>

		</div>
		<div id="d3" class="div">
			<div class="ml-3 mt-3">
				<h5>댓글</h5>
			</div>
			<hr>

			<!-- co_no ? autoincrements? 로직 생각해보기-->
			<!-- 현재 글에 comments table을 조회해서 코멘트 순서 번호를 가져와야함 select  -->
			<input type="hidden" id="list_no" name="list_no" value="" /> <input
				type="hidden" id="lec_no" name="lec_no" value="" /> <input
				type="hidden" id="co_email" name="co_email" value="${email}" />
			<textarea class="ml-3" id="content" name="content" placeholder="바르고 고운말" cols="80"
				rows="3" style="resize: none"></textarea>
			<div class="text-right mr-4">
			<input type="button" class="btn btn-outline-dark" id="commentWrite" name="commentWrite"
				value="댓글 작성" onclick="comments();">
			</div>
			<hr>
			<div>
				<div class='row' id="comments"></div>
			</div>			
		</div>
		<div id="d4" class="div">
			<div class="ml-3 mt-5">
			강의명 : ${lecBean.lec_title }<br>
			강사명 : ${lecBean.lec_teacher}<br> 
			강의내용 : <br> ${lecBean.lec_content }<br>
			</div>
		</div>
	</div>
</body>
</html>