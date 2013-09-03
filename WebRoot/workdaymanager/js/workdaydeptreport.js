function onAdd(){
	if(!isNull($("input[type='text'][name='sqWorkdayManager.complePercen']") , '完成比例')){
		return false;
	}
	$("#jform").attr("enctype" , "multipart/form-data");
	if($("input[type='hidden'][name='sqWorkdayManager.workdayId']").val() !='' && 
			$("input[type='hidden'][name='sqWorkdayManager.type']").val() =='3'){
		$("#jform").attr("action" , "myworkdepteditupdate.shtml");
	}else{
		$("input[type='hidden'][name='sqWorkdayManager.type']").val('3');
		$("#jform").attr("action" , "workdaydepteditadd.shtml");
	}
	$("#jform").submit();
}
function onDeptView(){
	$("input[type='hidden'][name='returnPage']").val('deptworkview')
	$("#jform").attr("action" , "deptworkview.shtml");
	$("#jform").submit();
}
function onDeptSub(){
	if(confirm("确定要提交本周工作内容")){
		//请求地址
		var url = 'workday.shtml' ;
		//传送的值
		var params = {type:		 '3'}; 
		//使用$.post方式	
			$.post(url, //服务器要接受的url
				params, //传递的参数		
				function cbf(data) {
					if(data.ajaxStr == 'SUCCEOKK'){
						$("input[type='hidden'][name='returnPage']").val('workdaydepteditlist')
						$("#qform").attr("action" , "deptworksub.shtml");
						$("#qform").submit();
					}else if(data.ajaxStr.substr(0,5) == 'SUCCE'){
						if(confirm(data.ajaxStr.substr(5))){
							$("input[type='hidden'][name='returnPage']").val('workdaydepteditlist')
							$("#qform").attr("action" , "deptworksub.shtml");
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
	$("#jform").attr("action" , "myworkdeptedit.shtml");
	$("#jform").submit();
}
function isFileCheck(){
	var val=$('input:radio[name="isFile"]:checked').val();
	//编写周报的时候将文件域值不可编辑
	if(val == '1'){
		$("#fileweek").hide();
		$("textarea[name='sqWorkdayManager.workContent']").attr("disabled" , false);
		$("textarea[name='sqWorkdayManager.discussProblem']").attr("disabled" , false);
		$("textarea[name='sqWorkdayManager.workSug']").attr("disabled" , false);
	}else{
		$("#fileweek").show();
		$("textarea[name='sqWorkdayManager.workContent']").attr("disabled" , true);
		$("textarea[name='sqWorkdayManager.discussProblem']").attr("disabled" , true);
		$("textarea[name='sqWorkdayManager.workSug']").attr("disabled" , true);
	}
}
function onLoadFile(){
	var flag = $("input[type='hidden'][name='onloadFlag']").val() ;
	if(flag == 'true'){
		$('input[name="isFile"][value=2]').attr("checked",true);
	}else{
		$('input[name="isFile"][value=1]').attr("checked",true);
	}
	isFileCheck();
}