function onSub(currentPage){
	var currentStr = '';
	if(currentPage == '-1'){
		currentStr = $("input[type='text'][name='currentPage']").attr("value") ;
	}else{
		currentStr = currentPage ;
	}
	$("input[type='hidden'][name='currentStr']").val(currentStr);
	$("#jform").submit();
}
$(document).ready(function() {
	if($("input[type='hidden'][name='pageTag']").val() != '' )
		$("#pageTag").html($("input[type='hidden'][name='pageTag']").val());
});