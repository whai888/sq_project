function onAssign(){
	$("input[type='hidden'][name='flag']").val('1')
	$("#assignForm").submit();
}
function onRemove(){
	$("input[type='hidden'][name='flag']").val('2')
	$("#assignForm").submit();
}