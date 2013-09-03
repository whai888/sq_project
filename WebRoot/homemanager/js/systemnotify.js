function onSub(currentStr){
	$("input[type='hidden'][name='currentStr']").val(currentStr)
	$("#jform").submit();
}
$(document).ready(function() {
if($("input[type='hidden'][name='pageTag']").val() != '' )
	$("#pageTag").html($("input[type='hidden'][name='pageTag']").val());
});