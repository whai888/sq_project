function onAdd(){
	if(!isNull($("select[name='sqWorkdayManager.sqProjectStep.stepId']") , '项目里程碑')){
		return false;
	}
	if(!isNull($("input[type='text'][name='sqWorkdayManager.taskTime']") , '任务时长')){
		return false;
	}
//	if (($("input[type='text'][name='workLoad0']").val() == '' || $("input[type='text'][name='workLoad0']").val() == null || $("input[type='text'][name='workLoad0']").val().length == 0) &&
//		($("input[type='text'][name='workLoad1']").val() == '' || $("input[type='text'][name='workLoad1']").val() == null || $("input[type='text'][name='workLoad1']").val().length == 0) &&
//		($("input[type='text'][name='workLoad2']").val() == '' || $("input[type='text'][name='workLoad2']").val() == null || $("input[type='text'][name='workLoad2']").val().length == 0)) {
//		alert('工作成果必须选择一项输入');
//		return false;
//	}
	if(!isNull($("input[type='text'][name='sqWorkdayManager.complePercen']") , '完成比例')){
		return false;
	}
	//if(!isNull($("#sqWorkdayManager.workContent") , '日报内容')){
	//	return false;
	//}
	
	var workLoad = $("input[type='text'][name='workLoad0']").val() + '|'+$("input[type='text'][name='workLoad1']").val()+'|'+$("input[type='text'][name='workLoad2']").val()+'||';
	$("input[type='hidden'][name='sqWorkdayManager.workLoad']").val(workLoad);
	
	var workUnits = '';
	if($("input[type='text'][name='workLoad0']").val() == '' || $("input[type='text'][name='workLoad0']").val() == null || $("input[type='text'][name='workLoad0']").val().length == 0){
		workUnits = workUnits + '0|';
	}else{
		workUnits = workUnits + '1|';
	}
	if($("input[type='text'][name='workLoad1']").val() == '' || $("input[type='text'][name='workLoad1']").val() == null || $("input[type='text'][name='workLoad1']").val().length == 0){
		workUnits = workUnits + '0|';
	}else{
		workUnits = workUnits + '1|';
	}
	if($("input[type='text'][name='workLoad2']").val() == '' || $("input[type='text'][name='workLoad2']").val() == null || $("input[type='text'][name='workLoad2']").val().length == 0){
		workUnits = workUnits + '0|';
	}else{
		workUnits = workUnits + '1|';
	}
	workUnits = workUnits + '0|';
	$("input[type='hidden'][name='sqWorkdayManager.workUnits']").val(workUnits);
	
	$("#jform").attr("enctype" , "multipart/form-data");
	if($("input[type='hidden'][name='sqWorkdayManager.workdayId']").val() !=''){
		$("#jform").attr("action" , "myworkdayeditupdate.shtml");
	}else{
		if(isNullNoTip($("input[type='text'][name='workLoad0']"))|| isNullNoTip($("input[type='text'][name='workLoad2']"))){
			if(!isNull($("input[type='file'][name='fileName']") , '工作成果选择了文档或案例，上传的文件')){
				return false;
			}
		}
		$("#jform").attr("action" , "myworkdayeditadd.shtml");
	}
	$("#jform").submit();
}
function onWorkView(){
	$("#jform").attr("action" , "myworkview.shtml");
	$("#jform").submit();
}
function onWorkSub(){
	if(confirm("确定要提交本周工作内容")){
		$("#qform").attr("action" , "myworksub.shtml");
		$("#qform").submit();
	}
}
function onAddFile(){
	if(!isNull($("input[type='file'][name='fileName']") , '上传的文件名')){
		return false;
	}
	var projectId = $("input[type='hidden'][name='sqDocmentManager.projectId']") ;
	if (projectId.val() == '' || projectId.val().length == 0 ){
		alert('指定需要上传文件的日报');
		return false;
	}
	$("#jform").attr("enctype" , "multipart/form-data");
	$("#jform").attr("action" , "projectdocupload.shtml");
	$("#jform").submit();
}
function onChanageProject(){
	//请求地址
	var url = 'page.shtml' ;
	//传送的值
	var params = {projectId: $("select[name='sqWorkdayManager.sqProjectInfo.projectId']").val(),
				  HQL:       'PROJECT_TO_STEPINFO'     ,
				  status:-99,
				  currentStr:'1'}; 
	//使用$.post方式	
		$.post(url, //服务器要接受的url
			params, //传递的参数		
			function cbf(data) { //服务器返回后执行的函数 参数 data保存的就是服务器发送到客户端的数据
				$("select[name='sqWorkdayManager.sqProjectStep.stepId']").empty();	//清空下拉列表中所有的内容
				var toolsVal = '';
					for(var i=data.listData.length-1 ; i>=0 ; i-- ){
						$("select[name='sqWorkdayManager.sqProjectStep.stepId']").append("<option value='"+data.listData[i][0]+"'>"+data.listData[i][1]+"</option>");
					}
					//初始化的时候，设置项目的里程碑
					if(isNullNoTip($("input[type='hidden'][name='stepId']"))){
						$("select[name='sqWorkdayManager.sqProjectStep.stepId']").val($("input[type='hidden'][name='stepId']").val());
					}
			}, 
			'json' //数据传递的类型  json
		);
}
function onload(){
	for(var i=-1 ; i>-3 ; i-- ){
		$("select[name='sqWorkdayManager.workDate']").append("<option value='"+change_date(i)+"'>"+change_date(i)+"</option>");
	}
	//设置工作成果的显示
	var workLoad = $("input[type='hidden'][name='sqWorkdayManager.workLoad']").val();
	var temp = workLoad.split("|");
	if(temp.length >= 4){
		$("input[type='text'][name='workLoad0']").val(temp[0]);
		$("input[type='text'][name='workLoad1']").val(temp[1]);
		$("input[type='text'][name='workLoad2']").val(temp[2]);
	}
	onChanageProject();
};
function exportForm(){
	$("#exportform").submit();
}
