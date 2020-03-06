function deleteQues(p_no){

	var Ques = confirm("선택한 책을 지우시겠습니까?");
	if(Ques == true){
		location.href='bookdelete.text?product_no='+ p_no	
	}
	else if(Ques == false)
	{
		return false;
	}
}
function modify(p_no){
	//수정
	//가격 수량
	var price = $("#price"+p_no).val();
	var stock = $("#stock"+p_no).val();
	var p_no = p_no;
	var url = "stockmodify.text";
	alert("수정될 가격 :"+price + "변경될 재고 수량 : "+ stock + "제품 번호 :" + p_no);
	
	var form_data = {
			price : price,
			stock : stock,
			p_no : p_no,
	}
	
	$.ajax({
		type : "post",
		url : url,
		data : form_data,
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		success : function(getData) {
			console.log("bookstock update result = " + getData);
			if(getData == 1)
			{
				alert("수정 완료");
			}
			else if(getData == 0)
			{
				alert("수정 실패");
			}
		},
		error : function(request,status,error){
			alert("code = "+ request.status + " message = " + request.responseText + " error = " + error);
			console.log(error);
		}
	});
	
	
}