function onAdd(){
	if(!isNull($("input[type='text'][name='sqProjectStep.stepName']") , '本级阶段名称')){
		return false;
	}
	if(!isNull($("input[type='text'][name='sqProjectStep.startDate']") , '开始时间')){
		return false;
	}
	if(!isNull($("input[type='text'][name='sqProjectStep.endDate']") , '结束时间')){
		return false;
	}
	if(!isNull($("input[type='text'][name='sqProjectStep.upPercent']") , '占上级阶段百分比')){
		return false;
	}
	if(!isNull($("input[type='text'][name='sqProjectStep.succPercent']") , '任务完成百分比')){
		return false;
	}
	if(!isNull($("input[type='text'][name='sqProjectStep.lagDay']") , '任务提前滞后天数')){
		return false;
	}
	$("#jform").attr("action" , "projectaddstep.shtml");
	$("#jform").submit();
}
function onDelete(){
	if(($("input[type='hidden'][name='sqProjectStep.stepId']").val()=='')){
		alert('请选择下面的阶段再删除');
		return false;
	}
	$("#jform").attr("action" , "projectdeletestep.shtml");
	$("#jform").submit();
}
function onUpdate(){
	if(!isNull($("input[type='text'][name='sqProjectStep.stepName']") , '本级阶段名称')){
		return false;
	}
	if(!isNull($("input[type='text'][name='sqProjectStep.startDate']") , '开始时间')){
		return false;
	}
	if(!isNull($("input[type='text'][name='sqProjectStep.endDate']") , '结束时间')){
		return false;
	}
	if(!isNull($("input[type='text'][name='sqProjectStep.upPercent']") , '占上级阶段百分比')){
		return false;
	}
	if(!isNull($("input[type='text'][name='sqProjectStep.succPercent']") , '任务完成百分比')){
		return false;
	}
	if(!isNull($("input[type='text'][name='sqProjectStep.lagDay']") , '任务提前滞后天数')){
		return false;
	}
	$("#jform").attr("action" , "projectupdatestep.shtml");
	$("#jform").submit();
}
function update(stepId ,upStepId,stepOrder, stepName,startDate,endDate,upPercent,succPercent,lagDay, stepRemark,dayStatus , keyStatus , stepStatus ){
	$("input[type='hidden'][name='sqProjectStep.stepId']").val(stepId);
	$("input[type='text'][name='sqProjectStep.stepName']").val(stepName);
	$("input[type='text'][name='sqProjectStep.startDate']").val(startDate);
	$("input[type='text'][name='sqProjectStep.endDate']").val(endDate);
	$("input[type='text'][name='sqProjectStep.upPercent']").val(upPercent);
	$("input[type='text'][name='sqProjectStep.succPercent']").val(succPercent);
	$("input[type='text'][name='sqProjectStep.lagDay']").val(lagDay);
	$("textarea[name='sqProjectStep.stepRemark']").val(stepRemark);
	$("select[name='sqProjectStep.stepOrder']").val(upStepId);
	$("select[name='sqProjectStep.keyStatus']").val(keyStatus);
	$("select[name='sqProjectStep.dayStatus']").val(dayStatus);
	$("select[name='sqProjectStep.stepStatus']").val(stepStatus);
}
