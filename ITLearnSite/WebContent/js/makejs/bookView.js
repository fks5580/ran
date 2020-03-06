$(function(){
	cmtlist();
});
function nobooklogin()
{
	var results = confirm("로그인 하시겠습니까?");
	if(results == true)
	{
		location.href = "login.do";
	}
}
function deleteTextbookComments(addr)
{
	var question = confirm("후기를 삭제 할까요?");
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
	var url = "TextbookCommentsList.tbc";
	var product_no = $("#product_no").val();
	var addr ="";
	
	var form_data = {
			product_no : product_no,
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
				addr = "TextbookCommentsDelete.tbc?bo_no="+getData.list[i].bo_no;
				var bo_evaluation="";
				for(var j=0;j<getData.list[i].bo_evaluation;j++){
					bo_evaluation += "★";
				}
				var comments = 
					"<tr>"
					+ 	"<td>"+getData.list[i].bo_email+"</td>"
					+ 	"<td class='color_b'>"+bo_evaluation+"</td>"			
					+		"<td width='500px' style='word-break:break-all'>"+getData.list[i].bo_content+"</td>"
					+ 		"<td>"+getData.list[i].bo_date+"</td>"
					+		"<td>" 
					+			"<button class='btn btn-outline-dark' onclick='deleteTextbookComments("+'"'+addr+'"'+");'" +">삭제</button>" 
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
}

function bookcomments(){
	
	var url = "TextbookCommentsWrite.tbc";
	var bo_no = $("#bo_no").val();
	var product_no = $("#product_no").val();
	var bo_email = $("#bo_email").val();
	var bo_content = $("#bo_content").val();
	var bo_evaluation = $("#bo_evaluation").val();
	
	var form_data = {
			bo_no : bo_no,
			product_no : product_no,
			bo_email : bo_email,
			bo_content : bo_content,
			bo_evaluation : bo_evaluation
	}
	$.ajax({
		type : "post",
		url : url,
		data : form_data,
		dataType : "text",
		success : function(getData) {
			if(getData == 1)
			{
				$("#bo_content").val("");
				cmtlist();
			}
		}
	});
	
}
