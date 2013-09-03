$(document).ready(function(){
	$("#openpopup").click(function(){
		var str = window.showModalDialog("../usermanage/userpopuplist.shtml?id="+RndNum(15), "dialogWidth=400px;dialogHeight=600px");
		$("input[type='hidden'][name='sqGroupTab.sqUserTab.userId']").val(str.split("|")[0]);
		$("input[type='text'][name='groupUser']").val(str.split("|")[1]);
	});
});

function onSub(){
	if(!isNull($("input[type='text'][name='sqGroupTab.groupName']") , '项目组名称')){
		return false;
	}
	if(!isNull($("input[type='hidden'][name='sqGroupTab.sqUserTab.userId']") , '项目组负责人')){
		return false;
	}
	if(!isNull($("input[type='text'][name='sqGroupTab.succDate']") , '项目组成立时间')){
		return false;
	}
	$("#jform").submit();
}
