function openPopup(){
	var str = window.showModalDialog("../usermanage/userpopuplist.shtml?id="+RndNum(15), "dialogWidth=400px;dialogHeight=600px");
	//如果返回的有值，则对值进行处理
	if(str.length>5){
		$("input[type='hidden'][name='sqDeptTab.deptUser']").val(str.split("|")[0]);
		$("input[type='text'][name='deptUser']").val(str.split("|")[1]);
	}
}
$(document).ready(function(){
	$("#openzhuli").click(function(){
		var str = window.showModalDialog("../usermanage/userpopupchecklist.shtml?sqDeptTab.deptNo="+$("input[type='hidden'][name='sqDeptTab.deptNo']").val()+"&id="+RndNum(15), "dialogWidth=400px;dialogHeight=600px");
		var userStr = str.split("#");
		var userId = '' ;
		var userName = '' ;
		for(var i =0 ; i<userStr.length-1 ; i++){
			userId += userStr[i].split("|")[0] + "|";
			userName += userStr[i].split("|")[1] + "  ";
		}
		$("input[type='hidden'][name='zhuliuserId']").val(userId);
		$("input[type='text'][name='zhuliuserName']").val(userName);
	});
});
function onSub(){
	if(!isNull($("input[type='text'][name='sqDeptTab.deptName']") , '部门名称')){
		return false;
	}
	if(!isNull($("input[type='text'][name='deptUser']") , '部门负责人名称')){
		return false;
	}
	if(!isNull($("input[type='text'][name='sqDeptTab.succDate']") , '部门成立时间')){
		return false;
	}
	var deptUser = $("input[type='hidden'][name='sqDeptTab.deptUser']").val() ;
	var zhuliuserId = $("input[type='hidden'][name='zhuliuserId']").val();
	if(zhuliuserId.indexOf(deptUser) != -1){
		alert('部门领导人与负责人不能相同');
		return false ;
	}
	$("#jform").submit();
}
function RndNum(n){
	var rnd="";
	for(var i=0;i<n;i++)
	rnd+=Math.floor(Math.random()*10);
	return rnd;
	}
