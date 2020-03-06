$(function(){
	cmtlist();
});
function nologin()
{
	var results = confirm("로그인 하시겠습니까?");
	if(results == true)
	{
		location.href = "login.do";
	}
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
	var url = "commentsList.co";
	var res_no = $("#res_no").val();
	var addr ="";
	
	var form_data = {
			res_no : res_no,
	}
	
	$.ajax({
		type : "post",
		url : url,
		data : form_data,
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		dataType : "json",
		success : function(getData) {
			var string ="";
			
			for (var i = 0; i < getData.list.length; i++) {
				addr = "commentsDelete.co?co_no="+getData.list[i].co_no;
				var comments = 
					"<tr>"
					+ 	"<td width='10%'>"+getData.list[i].co_email+"</td>"
					+		"<td width='65%'>"+getData.list[i].co_content+"</td>"
					+ 		"<td width='15%'>"+getData.list[i].co_date+"</td>"
					+		"<td width='7%'>" 
					+			"<button class='btn btn-light' onclick='deleteComment("+'"'+addr+'"'+");'" +">삭제</button>" 
					+		"</td>"
					+ 	"</tr>";
				
				string = string + comments;
			}
			$("#cmt").html(string);
		},
		error : function(request,status,error){
			alert("code = "+ request.status + " message = " + request.responseText + " error = " + error);
			console.log(error);
		}
	});
	/* 페이지 요청시 댓글을 불러온다.*/
}


function comments(){
	
	var url = "commentsWrite.co";
	var co_no = $("#co_no").val();
	var res_no = $("#res_no").val();
	var co_email = $("#co_email").val();
	var content = $("#content").val();
	
	var form_data = {
			co_no : co_no,
			res_no : res_no,
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
