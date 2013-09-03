function onSub(){
	if(!isNull($("input[type='text'][name='sqArticleManager.artTitle']") , '投稿标题')){
		return false;
	}
	$("#jform").submit();
}