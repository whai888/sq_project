function onSub(){
	if(!isNull($("input[type='text'][name='sqRole.roleName']") , '角色名称')){
		return false;
	}
	$("#jform").submit();
}