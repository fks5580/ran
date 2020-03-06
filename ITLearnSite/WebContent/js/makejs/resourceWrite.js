function canclebtn(){
	var value = confirm("취소하시겠습니까?");
	
	if(value == true)
	{
		location.href = "resourceList.bo";
	}
}