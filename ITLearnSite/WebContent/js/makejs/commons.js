var ctx = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
var //status
chkEmail = false
, chkPw1 = false
, chkPw2 = false
, chkName = false 
, chkPhone = false
, chkGender = false
, chkAddr = false;
/*회원가입 유효성*/

/*이메일(아이디) 유효성 체크 */
function emailDupChk(){
	var url = "emailDupChk.do";
	var email = $("#email").val();
	var emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
	
	if(!emailPattern.test(email))
	{
		$("#email ~ p").remove();
		$("#email").after("<p style = 'color : red; '> 올바른 형식이 아닙니다 </p>");
		chkEmail = false;
	}
	else
	{
		data = {
				email : email,
		}
		$.ajax({
			type : "post",
			url : url,
			data : data,
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			dataType : "text",
			success : function(getData){
				console.log(getData);
				
				if(getData == 1)
				{
					$("#email ~ p").remove();
					$("#email").after("<p style = 'color : red; '> 중복 </p>");
					console.log("중복");
					chkEmail = false;
				}
				if(getData == 0)
				{
					$("#email ~ p").remove();
					$("#email").after("<p style = 'color : green; '> 사용가능 </p>");
					console.log("사용가능");
					chkEmail = true;
				}
			}
		});
	}
}

function pwdRegChk(){
	var pw1 = $("#pw1").val();
	//숫자, 특수문자 1회이상 , 영문 2개 이상 8자리 이상 입력
	var pwPattern=/(?=.*\d{1,50})(?=.*[~`!@#$%\^&*()-+=]{1,50})(?=.*[a-zA-Z]{2,50}).{8,50}$/;

	if(!pwPattern.test(pw1))
	{
		$("#pw1 ~ p").remove();
		$("#pw1").after("<p style = 'color : red; '> 숫자, 특수문자 1회이상 , 영문 2개 이상 8자리 이상 입력 </p>");
		chkPw1 = false;
	}
	else
	{
		$("#pw1 ~ p").remove();
		$("#pw1").after("<p style = 'color : green; '> 사용가능 </p>");
		chkPw1 = true;
	}
}

function pwdEqualChk(){
	var pw1 = $("#pw1").val();
	var pw2 = $("#pw2").val();
	
	if(pw1 == pw2)
	{
		$("#pw2 ~ p").remove();
		$("#pw2").after("<p style = 'color : green; '> 일치함 </p>");
		chkPw2 = true;
	}
	else
	{
		$("#pw2 ~ p").remove();
		$("#pw2").after("<p style = 'color : red; '> 일치하지 않음 </p>");
		chkPw2 = false;
	}
}

function nameChk(){
	var name = $("#name").val();
	var namePattern = /^[가-힣]{2,4}$/;
	
	if(!namePattern.test(name))
	{
		$("#name ~ p").remove();
		$("#name").after("<p style = 'color : red;'> 이름은 2~4자까지 입니다</p>");
		chkName = false;
	}
	else
	{
		$("#name ~ p").remove();
		$("#name").after("<p style = 'color : green;'> 사용가능 </p>");
		chkName = true;
	}
}

function selectGenderChk(){
	var man = $("#gender1").val();
	var woman = $("#gender2").val();
	
	if(man == 1)
	{
		chkGender = true;
	}
	else if(woman == 2)
		
	{
		chkGender = true;
	}
	else
	{
		chkGender = false;
	}
	
}

function phoneChk(){
	var phonenumber = $("#phonenumber").val();
	var phonePattern = /^[0-9]*$/;
	
	if(!phonePattern.test(phonenumber))
	{
		alert("유효하지 않은 휴대폰번호입니다.");
		$("#phonenumber").val("");
		chkPhone = false;
	}
	else
	{
		chkPhone = true;
	}
}

function addressChk(){
	var addr = $("#address").val();
	var addr1 = $("#address1").val();
	var addr2 = $("#address2").val();
	
	if(addr != "" && addr1 != "" && addr2 != "")
	{
		chkAddr = true;
	}
}


function submitter(){
	alert("chkEmail=" + chkEmail 
			+ "\nchkPw1=" + chkPw1 
			+ "\nchkPw2=" + chkPw2 
			+ "\nchkName=" + chkName
			+"\nchkGender=" + chkGender 
			+ "\nchkPhone=" + chkPhone 	  
			+ "\nchkAddr=" + chkAddr );
	
	if(chkEmail != true || $("#email").val() == "")
	{
		$("#email").focus();
		return false;
	}
	else if(chkPw1 != true || $("#pw1").val() == "" || chkPw1 != chkPw2)
	{
		$("#pw1").focus();
		return false;
	}
	else if(chkPw2 != true || $("#pw2").val() == "")
	{
		$("#pw2").focus();
		return false;
	}
	else if(chkName != true || $("#name").val() == "")
	{
		$("#name").focus();
		return false;
	}
	else if(chkGender != true || $("#gender").val() == "")
	{
		$("#gender").focus();
		return false;
	}
	else if(chkAddr != true)
	{
		$("#address").focus();
		return false;
	}
	else
	{
		return true;
	}
}

function modifySubmitter(){
	alert(
			 "\nchkPw1=" + chkPw1 
			+ "\nchkPw2=" + chkPw2 
			);
	
	
	if(chkPw1 != true || $("#pw1").val() == "" || chkPw1 != chkPw2)
	{
		$("#pw1").focus();
		return false;
	}
	else if(chkPw2 != true || $("#pw2").val() == "")
	{
		$("#pw2").focus();
		return false;
	}
	else if($("#address").val() == "")
	{
		$("#address").focus();
		return false;
	}
	else if($("#address1").val() == "")
	{
		$("#address1").focus();
		return false;
	}
	else if($("#address2").val() == "")
	{
		$("#address2").focus();
		return false;
	}
	else if($("#phonenumber").val() == "")
	{
		$("#phonenumber").focus();
		return false;
	}
	else
	{
		return true;
	}
}

function naverModifySubmitter(){
		
	
	if($("#address").val() == "")
	{
		$("#address").focus();
		return false;
	}
	else if($("#address1").val() == "")
	{
		$("#address1").focus();
		return false;
	}
	else if($("#address2").val() == "")
	{
		$("#address2").focus();
		return false;
	}
	else if($("#phonenumber").val() == "")
	{
		$("#phonenumber").focus();
		return false;
	}
	else
	{
		return true;
	}
}





