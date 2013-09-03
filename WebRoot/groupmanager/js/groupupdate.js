function openPopup(){
	var str = window.showModalDialog("../usermanage/userpopuplist.shtml?id="+RndNum(15), "dialogWidth=400px;dialogHeight=600px");
	//如果返回的有值，则对值进行处理
	if(str.length>5){
		$("input[type='hidden'][name='sqGroupTab.sqUserTab.userId']").val(str.split("|")[0]);
		$("input[type='text'][name='groupUser']").val(str.split("|")[1]);
	}
}
function onSub(){
	if(!isNull($("input[type='text'][name='sqGroupTab.groupName']") , '项目组名称')){
		return false;
	}
	if(!isNull($("input[type='text'][name='groupUser']") , '项目组负责人名称')){
		return false;
	}
	if(!isNull($("input[type='text'][name='sqGroupTab.succDate']") , '项目组成立时间')){
		return false;
	}
	$("#jform").submit();
}
function RndNum(n){
	var rnd="";
	for(var i=0;i<n;i++)
	rnd+=Math.floor(Math.random()*10);
	return rnd;
}
