$(function(){
	var path = window.location.pathname;
	var path = path.split("/");
	var path = path[2].trim();
	var num = 1;
	
	var direction = path;
	if(direction != null){
		console.log("요청 페이지=" +direction);
		booklist(direction, num);
	}
});

function Request(){
	var requestParam ="";
        this.getParameter = function(param){
    	var url = unescape(location.href); //현재 주소를 decoding
        var paramArr = (url.substring(url.indexOf("?")+1,url.length)).split("&"); //파라미터만 자르고, 다시 &그분자를 잘라서 배열에 넣는다. 
        for(var i = 0 ; i < paramArr.length ; i++){

            var temp = paramArr[i].split("="); //파라미터 변수명을 담음
            if(temp[0].toUpperCase() == param.toUpperCase()){
            	requestParam = paramArr[i].split("=")[1]; // 변수명과 일치할 경우 데이터 삽입
                break;
            }
        }
        return requestParam;
    };
}

function setBooklist(getData){
	var string ="";
	
	if(getData == null)
	{
		$(".wrapboard").html("<div style='text-align:center;'> " + "등록된 도서가 없습니다." + "</div>");
	}
	else
	{
		for(var i = 0; i < getData.list.length; i++) {
			var booktitle = decodeURIComponent(getData.list[i].book_title);
			
			/*pasing 책이름*/
			var title = booktitle.split('[');
			title = title[0];
//			title = title.substring(0, title.length-1);
			var uri = decodeURIComponent(getData.list[i].book_image);
			console.log(uri);
			var p_no =  getData.list[i].product_no;
			
			var comments = 
				"<div class='booklist'>"
				+ 	"<div class='bookimg'>"
				+		"<img src='"+decodeURIComponent(getData.list[i].book_image)+"'>"
				+	"</div>"
				+	"<div class='content_wrap'>"
				+		"<a href='bookdetail.text?product_no=" + getData.list[i].product_no +"'>"
				+			"<p class='booktitle'>" + booktitle + "</p>"
				+		"</a>"
				+			"<p class='book_price'> 가격 : " + getData.list[i].book_price + "원</p>"
				+		"<div class='buttons'>"
				+			"<a href='cartAdd.cart?&product_no="+getData.list[i].product_no+ "&pro_name="+decodeURIComponent(getData.list[i].book_title)+"&pro_price="+getData.list[i].book_price+"&pro_img="+decodeURIComponent(getData.list[i].book_image)+"&pro_sort="+getData.list[i].product_type+"&pro_cnt=1' class='btn btn-outline-success rounded-0 px-4'> 담기 </a>"
				+		"</div>"
				+	"</div>"
				+"</div>";
			string = string + comments;
		}
		console.log(string2);
		
	}
	
	//######################################################
	//페이징 처리
	//페이지수
	var count = getData.count;
	//시작 번호
	var startRow = getData.page.startRow;
	//그룹 번호
	var numPageGroup = getData.page.numPageGroup;
	//페이지 그룹 수
	var pageGroupCount = getData.page.pageGroupCount;

	//페이지의 마지막 번호
	var endRow = getData.page.endRow;
	
	//페이지 사이즈
	var pageSize = getData.page.pageSize;
	
	//현재 페이지
	var currentPage = getData.page.currentPage;
	
	//페이지 그룹 크기
	var pageGroupSize = getData.page.pageGroupSize;
	
	var pageTag = "";
	var string2 ="";
	var frontTag = ""; 
	var middleTag = "";
	var backTag = "";
	if(count > 0)
	{
		var pageCount = count / pageSize + (count % pageSize == 0 ? 0 : 1 );
		
		var startPage = pageGroupSize * (numPageGroup - 1) + 1;
		
		var endPage = startPage + pageGroupSize - 1;
		
		if(endPage > pageCount)
		{
		   endPage = pageCount;
		}
		
		if(numPageGroup > 1)
		{
			var page = ((numPageGroup - 2) * pageGroupSize + 1);
			frontTag = 
				"<button class='btn btn-light' onclick = " +'"'+ "booklist('bookList.text'," + "'" +page+ "')" + '"'+">" + "[이전]" + "</button>";
			
		}
		
		for(var i = startPage; i <= endPage ; i++)
		{
			middleTag = "<button class='btn btn-light' onclick = " +'"'+ "booklist('bookList.text'," + "'" +i+ "')" + '"'+">" + i + "</button>";
			string2  = string2 + middleTag ;
		}
		
		if(numPageGroup < pageGroupCount)
		{
			var page = (numPageGroup * pageGroupSize + 1);
			backTag = "<button class='btn btn-light' onclick = " +'"'+ "booklist('bookList.text'," + "'" +page+ "')" + '"'+">" + "[다음]" + "</button>";
		}
	}
	$(".wrapboard").html(string+"<div style='text-align:center'> "+frontTag + string2 + backTag + "</div>");
}

function setBookDetail(getData){
	var request = new Request();
	var product_no = request.getParameter("product_no");
	var url = "detailload.text";
	
	var form_data = {
		product_no : product_no
	}
	$.ajax({
		type : "post",
		url : url,
		data : form_data,
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		success : function(getData) {
			
		},
		error : function(request,status,error){
			alert("code = "+ request.status + " message = " + request.responseText + " error = " + error);
			console.log(error);
		}
	});
	
}
function num(no){
	var num = no;
}

function booklist(direction, num)
{
	var url = "bookselect.text?pageNum="+num;
	
	var form_data = {
			/**/
		
	}
	
	$.ajax({
		type : "post",
		url : url,
		data : form_data,
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		dataType : "json",
		success : function(getData) {
			console.log(getData);
			if(direction == "bookList.text")
			{
				setBooklist(getData);
			}
			else if(direction == "bookdetail.text")
			{
				setBookDetail(getData);
			}
		},
		error : function(request,status,error){
			setBooklist(null);
		}
	});
}
