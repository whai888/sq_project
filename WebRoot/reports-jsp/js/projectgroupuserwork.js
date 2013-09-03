function onQuery(){
	if($("input[name='startDate']").val()>$("input[name='endDate']").val()){
		alert("开始日期不能大于截止日期");
		return false ;
	}
	$("input[name='groupName']").val($("#groupNo").find("option:selected").text());
	jform.submit();
}