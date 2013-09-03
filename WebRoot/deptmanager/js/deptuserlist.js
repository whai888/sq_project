function onSub(currentPage){
	var currentStr = '';
	if(currentPage == '-1'){
		currentStr = $("input[type='text'][name='currentPage']").attr("value") ;
	}else{
		currentStr = currentPage ;
	}
	//请求地址
	var url = 'page.shtml' ;
	//传送的值
	var params = {userName:  $("input[type='text'][name='sqUserTab.userName']").attr("value")  ,
				  deptno:  $("select[name='sqUserTab.sqDeptTab.deptNo']").val()  ,
				  HQL:       $("input[type='hidden'][name='HQL']").attr("value")     ,
				  currentStr:currentStr}; 
	//使用$.post方式	
		$.post(url, //服务器要接受的url
			params, //传递的参数		
			function cbf(data) { //服务器返回后执行的函数 参数 data保存的就是服务器发送到客户端的数据
				var toolsVal = '';
				$("#comm tr:not(:first)").remove();	//保留第一行，删除其它所有行
					for(var i=data.listData.length-1 ; i>=0 ; i-- ){
						$("#tab").after("<tr><td height='22'>"+data.listData[i][1]+"</td><td height='22'>"+data.listData[i][0].substring(0,10)+"</td><td height='22'>"+data.listData[i][2]+"</td><td height='22'>"+data.listData[i][8]+"</td><td height='22'>"+data.listData[i][9]+"</td><td height='22'>"+data.listData[i][4]+"</td><td height='22'>"+data.listData[i][5]+"</td><td height='22'>"+data.listData[i][6]+"</td><td height='22'>"+data.listData[i][7]+"</td></tr>") ;
					}
					$("#pageTag").html(data.pageTag);
			}, 
			'json' //数据传递的类型  json
		);
}