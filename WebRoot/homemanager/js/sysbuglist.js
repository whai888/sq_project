function onSub(){
	$("#jform").submit();
}
$(document).ready(function() {
	if($("input[type='hidden'][name='pageTag']").val() != '' )
		$("#pageTag").html($("input[type='hidden'][name='pageTag']").val());
});