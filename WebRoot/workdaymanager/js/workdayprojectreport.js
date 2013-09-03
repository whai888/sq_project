function onAdd(){
	if(!isNull($("input[type='text'][name='sqWorkdayManager.complePercen']") , '完成比例')){
		return false;
	}
	$("#jform").attr("enctype" , "application/x-www-form-urlencoded");
	if($("input[type='hidden'][name='sqWorkdayManager.workdayId']").val() !=''){
		$("#jform").attr("action" , "myworkprojecteditupdate.shtml");
	}else{
		$("#jform").attr("action" , "workdayproeditadd.shtml");
	}
	$("#jform").submit();
}
function onProjectView(){
	$("#jform").attr("action" , "projectworkview.shtml");
	$("#jform").submit();
}
function onProjectSub(){
	if(confirm("确定要提交本周工作内容")){
		//请求地址
		var url = 'workday.shtml' ;
		//传送的值
		var params = {type:		 '1'}; 
		//使用$.post方式	
			$.post(url, //服务器要接受的url
				params, //传递的参数		
				function cbf(data) {
					if(data.ajaxStr == 'SUCCEOKK'){
						$("#qform").attr("action" , "projectworksub.shtml");
						$("#qform").submit();
					}else if(data.ajaxStr.substr(0,5) == 'SUCCE'){
						if(confirm(data.ajaxStr.substr(5))){
							$("#qform").attr("action" , "projectworksub.shtml");
							$("#qform").submit();
						}
					}else if(data.ajaxStr.substr(0,5) == 'ERROR'){
						alert(data.ajaxStr.substr(5));
					}
				}, 
				'json' //数据传递的类型  json
			);
	}
}
function onAddFile(){
	if(!isNull($("input[type='file'][name='fileName']") , '上传的文件名')){
		return false;
	}
	if(!isNull($("input[type='hidden'][name='sqDocmentManager.projectId']") , '选择的日报信息')){
		return false;
	}
	$("#jform").attr("enctype" , "multipart/form-data");
	$("#jform").attr("action" , "projectdocupload.shtml");
	$("#jform").submit();
}
function onChanageProject(){
	$("#jform").attr("action" , "workdayproeditlist.shtml");
	$("#jform").submit();
}