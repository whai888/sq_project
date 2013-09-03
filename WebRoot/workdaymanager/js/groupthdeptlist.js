function onQuery(){
	$("#jform").submit();
}
function onExportAll(){
	var count=0;
    $("[name='workData'][checked]").each(function(){
    	count += 1 ;
    	//alert($(this).val());
    })
    if(count == 0){
    	alert('请选择要导出的项目');
    	return false ;
    }
    if(confirm("确定将所选的项目全部导出?")){
    	$("#qform").submit();
    }
}
function allCheck(){
	$("[name='workData']").attr("checked",'true');//全选
}
function allRemoveCheck(){
	$("[name='workData']").removeAttr("checked");//取消全选
}