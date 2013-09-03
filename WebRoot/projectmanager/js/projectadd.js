$(document).ready(function() {
    //移到右边
    $("#button1").click(function() {
        //获取选中的选项，删除并追加给对方
    	var str = $("#select1 option:selected").val() ;
        $("#select1 option:selected").appendTo("#select2");
        var temp = $("input[type='hidden'][name='mgnuser']").val();
        $("input[type='hidden'][name='mgnuser']").val(strHandle(temp , str , "0")) ;
    });
    //移到左边
    $("#button2").click(function() {
    	var str = $("#select2 option:selected").val() ;
        $("#select2 option:selected").appendTo("#select1");
        var temp = $("input[type='hidden'][name='mgnuser']").val();
        $("input[type='hidden'][name='mgnuser']").val(strHandle(temp , str , "1")) ;
    });
  //移到右边
    $("#button3").click(function() {
        //获取选中的选项，删除并追加给对方
    	var str = $("#select1 option:selected").val() ;
        $("#select1 option:selected").appendTo("#select3");
        var temp = $("input[type='hidden'][name='memuser']").val();
        $("input[type='hidden'][name='memuser']").val(strHandle(temp , str , "0")) ;
    });
    //移到左边
    $("#button4").click(function() {
    	var str = $("#select3 option:selected").val() ;
        $("#select3 option:selected").appendTo("#select1");
        var temp = $("input[type='hidden'][name='memuser']").val();
        $("input[type='hidden'][name='memuser']").val(strHandle(temp , str , "1")) ;
    });
    //双击选项
    $("#select1").dblclick(function() { //绑定双击事件
        //获取全部的选项,删除并追加给对方
    	var str = $("option:selected", this).val() ;
        $("option:selected", this).appendTo("#select2"); //追加给对方
        var temp = $("input[type='hidden'][name='mgnuser']").val();
        $("input[type='hidden'][name='mgnuser']").val(strHandle(temp , str , "0")) ;
    });
    //双击选项
    $("#select2").dblclick(function() {
    	var str = $("option:selected", this).val() ;
        $("option:selected", this).appendTo("#select1");
        var temp = $("input[type='hidden'][name='mgnuser']").val();
        $("input[type='hidden'][name='mgnuser']").val(strHandle(temp , str , "1")) ;
    });
  //双击选项
    $("#select3").dblclick(function() {
    	var str = $("option:selected", this).val() ;
        $("option:selected", this).appendTo("#select1");
        var temp = $("input[type='hidden'][name='memuser']").val();
        $("input[type='hidden'][name='memuser']").val(strHandle(temp , str , "1")) ;
    });
    
	$("#openpopup").click(function(){
		var str = window.showModalDialog("../usermanage/userpopuplist.shtml?id="+RndNum(15), "dialogWidth=400px;dialogHeight=600px");
		$("input[type='hidden'][name='sqProjectInfo.sqUserTab.userId']").val(str.split("|")[0]);
		$("input[type='text'][name='userId']").val(str.split("|")[1]);
	});
});
function onSub(){
	if(!isNull($("input[type='text'][name='sqProjectInfo.projectName']") , '项目名称')){
		return false;
	}
	if(!isNull($("input[type='text'][name='userId']") , '项目经理')){
		return false;
	}
	if(!isNull($("input[type='text'][name='sqProjectInfo.startDate']") , '计划开始时间')){
		return false;
	}
	if(!isNull($("input[type='text'][name='sqProjectInfo.advanceDate']") , '计划结束时间')){
		return false;
	}
	$("#jform").submit();
}