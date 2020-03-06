<%@ page contentType="text/html; charset=UTF-8"%>
<html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="path" value="${pageContext.request.contextPath}" />

<head>
<title></title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
	function fnWrite() {
		var f = document.writeForm;
		var cnt = f.elements.length;

		//누락된 파일업로드 위치를 나타낼 변수
		var filecnt = 1;

		for (i = 0; i < cnt; i++) {
			// <input type="file">인 태그 중에서
			if (f.elements[i].type == "file") {
				// 업로드할 파일을 지정하지 않았다면?
				if (f.elements[i].value == "") {
					var msg = filecnt + "번째 파일이 누락 되었습니다. \n 업로드할 파일을 선택해 주세요";
					alert(msg);

					f.elements[i].focus();
					return; // submit하지 않고, 함수 종료 
				}

				filecnt++; // <input>태그의 type이 file일때만 1씩증가
			}
		}

		document.writeForm.submit();
	}

	var inputCnt = 1; // input태그 구분 번호
	// 첨부파일추가 버튼을 누를때 마다 file태그 하나씩 추가
	function addInput() {
		// input태그를 추가할 div태그 위치 주소 가져오기
		var div = document.getElementById("inputDiv");
		var msg1 = "<div class='col-md-6 form-group'><input type ='text' name='list_title" + inputCnt + "' id='list_title" + inputCnt + "' placeholder='동영상설명 " + inputCnt + "' class='form-control form-control-lg'/></div>";
		var msg2 = "<div class='col-md-6 form-group'><input type='file' name='upfile" + inputCnt + "' id='upfile" + inputCnt + "' class='form-control form-control-lg'/></div>";
		/* var msg4 = "<br id='br" + inputCnt + "' />"; */
		inputCnt++;

		div.innerHTML += msg1;
		div.innerHTML += msg2;
		/* div.innerHTML += msg4; */
	}

	function complete() {
		var con = confirm("동영상 업로드 갯수가 " + (inputCnt - 1) + "개가 맞습니까?");
		var div = document.getElementById("inputDiv");
		if (con) {

			div.innerHTML += "<input type='hidden' value='" + (inputCnt - 1)
					+ "' name='cnt'/>";
		} else {
			location.reload();
			/* div.innerHTML = ""; */
		}
	}

	function readURL(input) {

		//크롬 웹브라우저의 F12 개발자 모드로 가서 console 탭에 띄운로그 메시지 확인
		console.debug(input);
		console.debug(input.files);

		/* 
			<input type="file"/> 인 태그 객체의 files 메서드 호출 시
			FileList 라는 배열이 생성, FileList 배열 내부의 0번째 인덱스 위치에
			아래에서 선택한(업로드할) 파일 정보를 key:value 쌍으로 저장한 File객체에 저장
		 */
		//<input type="file"> 태그에서 업로드를 하기 위한 파일 선택 시
		//FileList라는 배열 존재하고, FileList라는 배열의 0번째 인덱스 위치 아래에 
		//파일업로드를 위해 선택한 File 객체가 저장되어 있다면?
		if (input.files && input.files[0]) {

			//동적으로 <img> 태그 만들어 추가
			$("#tdImg").html(
					"<img id = 'preview' src='#' width=200 height= 200 />");

			//파일을 읽어올 객체 생성
			var reader = new FileReader();

			//지정한 img태그에 첫번째 파일 input에 첨부한  파일에 대한 File 객체를 읽음
			reader.readAsDataURL(input.files[0]);

			//파일 내용을 모두 읽어 들였다면?
			//읽어 들인 File 객체 정보는 매개변수로 넘어오는 ProgressEvent 객체 내부의
			//target 속성에 대응되는 객체(JSON객체 데이터 형식)으로 저장
			//또한 JSON 객체 데이터 내부에는 result 속성에 읽어 들인 File 객체 정보가 저장			
			reader.onload = function(ProgressEvent) {

				console.debug(ProgressEvent);

				//id 속성값이 preview인 <img> 태그에 attr() 메서드를 이용,
				//파일 첨부 시 미리보기 이미지를 나타내기 위해
				//<img> 태그의 src 속성에 new FileReader()객체 이용, 
				//읽어들인 첨부할 File 객체 정보를 지정, 추가하여 이미지 파일의 미리보기 기능 가능
				$("#preview").attr("src", ProgressEvent.target.result);

			}
		}
	}
</script>


</head>

<body>
<div class="container">
	<div class="row align-items-end justify-content-center text-center">			
			<img src="${path }/images/admin5.png">			
	</div>
</div>
	<div class="custom-breadcrumns border-bottom">
		<div class="container">
			<a href="${path}/index.do">Home</a> 
			<span class="mx-3 icon-keyboard_arrow_right"></span> 
			<span class="current">관리자</span>
			<span class="mx-3 icon-keyboard_arrow_right"></span> 
			<span class="current">강의 등록</span>
		</div>
	</div>

	<div class="site-wrap">
		<div class="site-section">
			<div class="container">
				<form method="post" action="lectureRegister.lec"
					enctype="multipart/form-data" name="writeForm">


					<div class="row">

						<div class="col-md-6 form-group">
							<label for="lec_title">강의명</label> <input type="text"
								name="lec_title" id="lec_title"
								class="form-control form-control-lg" required>
						</div>
						<div class="col-md-6 form-group">
							<label for="lec_teacher">강사</label><input type="text"
								name="lec_teacher" id="lec_teacher"
								class="form-control form-control-lg" required>
						</div>
						<div class="col-md-6 form-group">
							<label for="lec_price">금액</label><input type="text"
								name="lec_price" id="lec_price"
								class="form-control form-control-lg" required>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12 form-group">
							<label for="lec_content">설명 </label>
							<textarea name="lec_content" id="lec_content" rows="10" cols="50"
								class="form-control" required></textarea>
						</div>
					</div>
					<div class="row">
						<div style="padding: 10px;">
							<h3 class="section-title-underline mb-5">
                            <span>사용법</span>
                        </h3>
							<p>
							1. 이미지명과 동영상명을 동일하게 동명상명01 부터 시작하여 파일명을 작성한다.<br>
							ex) 이미지: 인강 / 동영상: 인강01 <br>
							2. 이미지 파일 추가를 위해 파일 선택 버튼을 누른 후 업로드한다.<br>
							3. 업로드할 동영상 파일 수 만큼 추가 버튼을 눌러 추가한 후 확인 버튼을 누른다.<br>
							4. 동영상 설명과 동영상 업로드 후 등록 버튼을 누른다.</p>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12 form-group">
							<label>이미지 파일 추가</label> <input type="file" name="lec_imgfile"
								id="lec_imgfile" onchange="readURL(this);"
								class="form-control form-control-lg">
						</div>
						<div id="tdImg" class="col-lg-6 order-1 order-lg-2 mb-4 mb-lg-0"></div>
					</div>
					<div class="text-right" style="margin: 10px">
						<input type="button" value="추가" onclick="addInput()"
							class="btn btn-outline-danger btn-lg px-5" style="margin: 10px;">
						<input type="button" value="확인" onclick="complete();"
							class="btn btn-outline-dark btn-lg px-5" style="margin: 10px;" />
					</div>
					<div class="row">
						<div id="inputDiv" class="row"></div>
					</div>
					<div class="text-center mt-5">
						<input type="button" value="등록" onclick="fnWrite()"
							class="btn btn-outline-success btn-lg px-5 submit" style="margin: 10px;" />
						<input type="reset" value="다시작성"
							class="btn btn-outline-secondary btn-lg px-5 cancel" style="margin: 10px;" />
					</div>
				</form>

			</div>
		</div>
	</div>
</body>
</html>