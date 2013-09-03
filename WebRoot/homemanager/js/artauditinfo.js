function onSub(){
	if(!isNull($("input[type='radio'][name='sqArticleManager.status']") , '审批状态')){
		return false;
	}
	var status = $("input[type='hidden'][name='status']").val() ;
	var checkstatus = $("input[type='radio'][name='sqArticleManager.status']").val() ;
	if(status ==0 && status == checkstatus){
		alert('已经发布的文章不能再进行发布');
		return false ;
	}
	if(status ==1 && status == checkstatus){
		alert('已经拒绝的文章不能再拒绝');
		return false ;
	}
	if(status ==2 && status == checkstatus){
		alert('审核不通过的文章不能进行关闭');
		return false ;
	}
	$("#jform").submit();
}