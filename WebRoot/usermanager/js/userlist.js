function onSub(currentPage){
	var currentStr = '';
	var status0 = '9' ;
	var status1 = '9' ;
	var status2 = '9' ;
	var status3 = '9' ;
	if(!isNull($("input[type='text'][name='sqUserTab.enterYear']") , '入职年份')){
		return false;
	}
	
	if(currentPage == '-1'){
		currentStr = $("input[type='text'][name='currentPage']").attr("value") ;
	}else{
		currentStr = currentPage ;
	}
	if($("input[type='checkbox'][name='sqUserTab.status0']").attr("checked") == true )
		status0 = $("input[type='checkbox'][name='sqUserTab.status0']").attr("value") ;
	if($("input[type='checkbox'][name='sqUserTab.status1']").attr("checked") == true )
		status1 = $("input[type='checkbox'][name='sqUserTab.status1']").attr("value") ;
	if($("input[type='checkbox'][name='sqUserTab.status2']").attr("checked") == true )
		status2 = $("input[type='checkbox'][name='sqUserTab.status2']").attr("value") ;
	if($("input[type='checkbox'][name='sqUserTab.status3']").attr("checked") == true )
		status3 = $("input[type='checkbox'][name='sqUserTab.status3']").attr("value") ;
	//请求地址
	var url = 'page.shtml' ;
	//传送的值
	var params = {enterYear: $("input[type='text'][name='sqUserTab.enterYear']").attr("value"),
				  end:  $("input[type='text'][name='endYear']").attr("value")  ,	
				  userName:  $("input[type='text'][name='sqUserTab.userName']").attr("value")  ,
				  deptno:  $("select[name='sqUserTab.deptno']").val()  ,
				  job:  $("select[name='sqUserTab.sqJobTab.jobId']").val()  ,
				  status0:   status0    ,
				  status1:   status1    ,
				  status2:   status2    ,
				  status3:   status3    ,
				  HQL:       $("input[type='hidden'][name='HQL']").attr("value")     ,
				  currentStr:currentStr}; 
	//使用$.post方式	
		$.post(url, //服务器要接受的url
			params, //传递的参数		
			function cbf(data) { //服务器返回后执行的函数 参数 data保存的就是服务器发送到客户端的数据
				var toolsVal = '';
				var userFlag = $("input[type='hidden'][name='flag']").val();
				var str = '';
				$("#comm tr:not(:first)").remove();	//保留第一行，删除其它所有行
					for(var i=data.listData.length-1 ; i>=0 ; i-- ){
						if(userFlag == 'true')
							$("#tab").after("<tr><td height='22' class='bgcolor1 fontcenter fontcolor3'>"+data.listData[i][3]+"</td><td height='22' class='bgcolor1 fontcenter fontcolor3'><img src='../images/edt.gif' width='16' height='16' /><a class='form_font' href='userfindvo.shtml?sqUserTab.userId="+data.listData[i][3]+"'>"+data.listData[i][1]+"</a></td><td height='22' class='bgcolor1 fontcenter fontcolor3'>"+data.listData[i][0].substring(0,10)+"</td><td height='22' class='bgcolor1 fontcenter fontcolor3'>"+data.listData[i][2]+"</td><td height='22' class='bgcolor1 fontcenter fontcolor3'>"+data.listData[i][8]+"</td><td height='22' class='bgcolor1 fontcenter fontcolor3'>"+data.listData[i][9]+"</td><td height='22' class='bgcolor1 fontcenter fontcolor3'>"+data.listData[i][4]+"</td><td height='22' class='bgcolor1 fontcenter fontcolor3'>"+data.listData[i][5]+"</td><td height='22' class='bgcolor1 fontcenter fontcolor3'>"+data.listData[i][6]+"</td><td height='22' class='bgcolor1 fontcenter fontcolor3'>"+data.listData[i][7]+"</td><td height='22' class='bgcolor1 fontcenter fontcolor3'><img src='../images/edt.gif' width='16' height='16' /><a class='form_font' href='userrolequery.shtml?sqUserTab.userId="+data.listData[i][3]+"'>角色分配</a></img></td></tr>") ;
						else
							$("#tab").after("<tr><td height='22' class='bgcolor1 fontcenter fontcolor3'>"+data.listData[i][3]+"</td><td height='22' class='bgcolor1 fontcenter fontcolor3'>"+data.listData[i][1]+"</td><td height='22' class='bgcolor1 fontcenter fontcolor3'>"+data.listData[i][0].substring(0,10)+"</td><td height='22' class='bgcolor1 fontcenter fontcolor3'>"+data.listData[i][2]+"</td><td height='22' class='bgcolor1 fontcenter fontcolor3'>"+data.listData[i][8]+"</td><td height='22' class='bgcolor1 fontcenter fontcolor3'>"+data.listData[i][9]+"</td><td height='22' class='bgcolor1 fontcenter fontcolor3'>"+data.listData[i][4]+"</td><td height='22' class='bgcolor1 fontcenter fontcolor3'>"+data.listData[i][5]+"</td><td height='22' class='bgcolor1 fontcenter fontcolor3'>"+data.listData[i][6]+"</td><td height='22' class='bgcolor1 fontcenter fontcolor3'>"+data.listData[i][7]+"</td><td height='22' class='bgcolor1 fontcenter fontcolor3'>角色分配</td></tr>") ;
					}
					$("#pageTag").html(data.pageTag);
			}, 
			'json' //数据传递的类型  json
		);
}