function onSub(){
	if(!isNull($("input[type='text'][name='sqUserTab.userName']") , '员工姓名')){
		return false;
	}
	if(!isNull($("input[type='text'][name='sqUserTab.birthday']") , '生日')){
		return false;
	}
	if(!isNull($("input[type='text'][name='sqUserTab.enterYear']") , '入职年份')){
		return false;
	}
	if(!isNull($("input[type='text'][name='sqUserTab.practitionersYear']") , '从业年份')){
		return false;
	}
	if(!isNull($("input[type='text'][name='sqUserTab.mobile']") , '手机号码')){
		return false;
	}
	//手机号码效验
	if(!isMobile($("input[type='text'][name='sqUserTab.mobile']").attr("value"))){
		alert('手机号码输入不正确，请重新输入')
		return false;
	}
	if(!isNull($("input[type='text'][name='sqUserTab.email']") , 'EMAIL')){
		return false;
	}
	//邮箱验证
//	if(!isEmail($("input[type='text'][name='sqUserTab.email']").attr("value"))){
//		alert('EMAIL输入不正确，请重新输入')
//		return false;
//	}
	$("#jform").submit();
}
function onChanageOffice(){
	//请求地址
	var url = 'page.shtml' ;
	//传送的值
	var params = {type: $("select[name='sqUserTab.level']").val(),
				  HQL:       'LEVEL_TO_OFFICE'     ,
				  currentStr:'1'}; 
	//使用$.post方式	
		$.post(url, //服务器要接受的url
			params, //传递的参数		
			function cbf(data) { //服务器返回后执行的函数 参数 data保存的就是服务器发送到客户端的数据
				$("select[name='sqUserTab.sqOfficeTab.officeId']").empty();	//清空下拉列表中所有的内容
				var toolsVal = '';
					for(var i=data.listData.length-1 ; i>=0 ; i-- ){
						$("select[name='sqUserTab.sqOfficeTab.officeId']").append("<option value='"+data.listData[i][0]+"'>"+data.listData[i][1]+"</option>");
					}
			}, 
			'json' //数据传递的类型  json
		);
}
$(document).ready(function() {
	onChanageOffice();
});