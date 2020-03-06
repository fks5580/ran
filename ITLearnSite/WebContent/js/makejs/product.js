//동적 태그 생성 하는 법      

//function select() {

//	if ($("#type").val() == 'book') {
//		var string = "<legend>도서 정보</legend>";
//		string += "<div class='form-group'>";
//		string += "<input type='text' class='form-control' id='book_title' name ='book_title' placeholder='책제목'>";
//		string += "</div>"
//		string += "<div class='form-group'>";
//		string += "<input type='text' class='form-control' id='book_link' name ='book_link' placeholder='책링크'>";
//		string += "</div>";
//		string += "<div class='form-group'>";
//		string += "<input type='text' class='form-control' id='book_image' name ='book_image' placeholder='책이미지'>";
//		string += "</div>";
//		string += "<div class='form-group'>";
//		string += "<input type='text' class='form-control' id='book_author' name ='book_author' placeholder='작가'>";
//		string += "</div>";
//		string += "<div class='form-group'>";
//		string += "<input type='text' class='form-control' id='book_price' name ='book_price' placeholder='책가격'>";
//		string += "</div>";
//		string += "<div class='form-group'>";
//		string += "<input type='text' class='form-control' id='book_discount' name ='book_discount' placeholder='할인금액'>";
//		string += "</div>";
//		string += "<div class='form-group'>";
//		string += "<input type='text' class='form-control' id='book_publisher' name ='book_publisher' placeholder='출판사'>";
//		string += "</div>";
//		string += "<div class='form-group'>";
//		string += "<input type='text' class='form-control' id='book_pubdate' name ='book_pubdate' placeholder='출판일'>";
//		string += "</div>";
//		string += "<div class='form-group'>";
//		string += "<input type='text' class='form-control' id='book_isbn' name ='book_isbn' placeholder='isbn'>";
//		string += "</div>";
//		string += "<div class='form-group'>";
//		string += "<input type='text' class='form-control' id='book_description' name ='book_description' placeholder='내용'>";
//		string += "</div>"
//		string += "<div class='form-group'>";
//		string += "<input type='text' class='form-control' id='book_description' name ='book_stock' placeholder='재고'>";
//		string += "</div>"
//		string += "<br>";
//		string += "<button type='submit' class='btn btn-primary'>등록</button>";
//	
////		$(".addtag").html(string);
//	} else if ($("#type").val() == 'lecture') {
//		var string = "<h1>강의 정보</h1>";
//		string += "<hr>";
//		string += "강의 제목 : <input type='text' name='book_title'><br><br>";
//		string += "강의 내용 : <textarea name ='book_content'></textarea><br><br>";
//		string += "<input type='submit' value='작성'>";
//		$(".addtag").html(string);
//	}
//};

function mod(text){
	var gettext = text;
	
}

function bookSearch(page) {
	var url = "bookSearch.text";
	var word = $("#word").val();/* 네이버 책 검색 */
	var listArr = null;
	var data = {
		word : word,
	}

	$.ajax({
		type : "post",
		url : url,
		data : data,
		dataType : "json",
		success : function(getData) {
			console.log(getData);
			console.log(getData.total);
			var total = getData.total;
			var start = getData.start;
			var display = getData.display;
			/* 페이지= index+1 */
			$(".find").html(total);
			listArr = new Array();
			for (var i = 0; i < display; i++) {
				listArr.push(getData.items[i]);
				console.log(listArr[i].title);
			}
			var i = page-1;
			$(".title ~ p").remove();
			$(".title").after("<p>" + listArr[i].title + "</p>");
			/*문자열 태그 제거필요*/
			var titleEncode = encodeURIComponent(listArr[i].title);
			$("#book_title").val(titleEncode);
			
			
			$(".link ~ p").remove();
			$(".link").after("<p>" + listArr[i].link + "</p>");
			var linkEncode = encodeURIComponent(listArr[i].link);
			$("#book_link").val(linkEncode);
			
			
			$(".image ~ p").remove();
			$(".image").after("<p>" + listArr[i].image + "</p>");
			var imgEncode = encodeURIComponent(listArr[i].image);
			$("#book_image").val(imgEncode);
			
			$(".author ~ p").remove();
			$(".author").after("<p>" + listArr[i].author + "</p>");
			$("#book_author").val(listArr[i].author);
			
			$(".price ~ p").remove();
			$(".price").after("<p>" + listArr[i].price + "</p>");
			$("#book_price").val(listArr[i].price);
			
			$(".discount ~ p").remove();
			$(".discount").after("<p>" + listArr[i].discount + "</p>");
			$("#book_discount").val(listArr[i].discount);
			
			$(".publisher ~ p").remove();
			$(".publisher").after("<p>" + listArr[i].publisher + "</p>");
			$("#book_publisher").val(listArr[i].publisher);
			
			$(".pubdate ~ p").remove();
			$(".pubdate").after("<p>" + listArr[i].pubdate + "</p>");
			$("#book_pubdate").val(listArr[i].pubdate);
			
			$(".isbn ~ p").remove();
			$(".isbn").after("<p>" + listArr[i].isbn + "</p>");
			$("#book_isbn").val(listArr[i].isbn);
			
			$(".description ~ p").remove();
			$(".description").after("<p>" + listArr[i].description + "</p>");
			
			var encode = encodeURIComponent(listArr[i].description);
			var decode = decodeURIComponent(listArr[i].description);
			console.log(encode);
			console.log(decode);
			$("#book_description").val(encode);
			
		},
		error : function(request, status, error) {
			alert("code = " + request.status + " message = "
					+ request.responseText + " error = " + error);
			console.log(error);
		}
	});
};

