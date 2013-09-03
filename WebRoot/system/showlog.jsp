<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>操作日志查询</title>
		<link type="text/css" rel="stylesheet" href="../css/style-home.css" />
		<link type="text/css" rel="stylesheet" href="../css/style-sys.css" />
		<script type="text/javascript" src="../js/public.js"></script>
		<script type="text/javascript" src="../js/jquerybak.js"></script>
		<jsp:include page="../js/index.jsp"></jsp:include>
	</head>
	<script type="text/javascript">
<!--
function onSub(currentPage){
	var currentStr = '';
	var status0 = '9' ;
	var status1 = '9' ;
	if(!isNull($("input[type='text'][name='start']") , '操作开始日期')){
		return false;
	}
	if(!isNull($("input[type='text'][name='end']") , '操作结束日期')){
		return false;
	}
	if(currentPage == '-1'){
		currentStr = $("input[type='text'][name='currentPage']").attr("value") ;
	}else{
		currentStr = currentPage ;
	}
	if($("input[type='checkbox'][name='sqUserlogTab.status0']").attr("checked") == true )
		status0 = $("input[type='checkbox'][name='sqUserlogTab.status0']").attr("value") ;
	if($("input[type='checkbox'][name='sqUserlogTab.status1']").attr("checked") == true )
		status1 = $("input[type='checkbox'][name='sqUserlogTab.status1']").attr("value") ;
		
	//请求地址
	var url = '../page.shtml' ;
	//传送的值
	var params = {userNo: $("input[type='text'][name='sqUserlogTab.userNo']").attr("value"),
				  start: $("input[type='text'][name='start']").attr("value"),
				  end:  $("input[type='text'][name='end']").attr("value")  ,
				  status0:   status0    ,
				  status1:   status1    ,
				  HQL:       $("input[type='hidden'][name='HQL']").attr("value")     ,
				  currentStr:currentStr}; 
	//使用$.post方式	
		$.post(url, //服务器要接受的url
			params, //传递的参数		
			function cbf(data) { //服务器返回后执行的函数 参数 data保存的就是服务器发送到客户端的数据
				var toolsVal = '';
				$("#comm tr:not(:first)").remove();	//保留第一行，删除其它所有行
					for(var i=data.listData.length-1 ; i>=0 ; i-- ){
						$("#tab").after("<tr><td height='22' class='bgcolor1 fontcenter fontcolor3'>"+data.listData[i][0]+"</td><td height='22' class='bgcolor1 fontcenter fontcolor3'>"+data.listData[i][1]+"</td><td height='22' class='bgcolor1 fontcenter fontcolor3'>"+data.listData[i][2]+"</td><td height='22' class='bgcolor1 fontcenter fontcolor3'>"+data.listData[i][3].substr(0,10)+"</td><td height='22' class='bgcolor1 fontcenter fontcolor3'>"+data.listData[i][4]+"</td><td height='22' class='bgcolor1 fontcenter fontcolor3'>"+data.listData[i][5]+"</td><td height='22' class='bgcolor1 fontcenter fontcolor3'>"+data.listData[i][6]+"</td></tr>") ;
					}
					$("#pageTag").html(data.pageTag);
			}, 
			'json' //数据传递的类型  json
		);
}
//-->
</script>
	<body>
		<div class="right">
			<div class="right_bt">
				<h1 class="right_bth1">
					用户管理
				</h1>
			</div>
			<form action="" method="post" name="jform" id="jform">
				<div class="right_centerwk">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="form_btimg form_bt">
								<span>&nbsp;</span>
								<h1>
									操作日志查询
								</h1>
							</td>
						</tr>
						<tr>
							<td>
								<table border="0" width="100%" class="form_table_l">
									<tr>
										<td height="32" style="font-weight: bold; text-align: right">
											用户编号：
										</td>
										<td height="32" class="bgcolor1 fontleft fontbold">
											<input type="text" name="sqUserlogTab.userNo"
												value="<s:property value="sqUserlogTab.userNo"/>"
												onblur="isCheckMaxLenth(this)" maxlength="12">
										</td>
										<td rowspan="3" style="text-align: center">
											<div style="text-align: center;">
									<div style="width:70px;margin: 0 auto;">
											<a class="btn" onClick="onSub('1')" >
											<cite>查&nbsp;&nbsp;&nbsp;询</cite></a>
											</div>
											</div>
										</td>
									</tr>
									<tr>
										<td height="32" style="font-weight: bold; text-align: right">
											操作日期：
										</td>
										<td height="32">
											<input type="text" name="start" value="" id="datepick1"
												maxlength="10" onfocus="$(this).calendar()" readonly="readonly">
											&nbsp;&nbsp;—&nbsp;&nbsp;
											<input type="text" name="end" value="" id="datepick2"
												maxlength="10" onfocus="$(this).calendar()" readonly="readonly">
										</td>
									</tr>
									<tr>
										<td height="32" style="font-weight: bold; text-align: right">
											状态：
										</td>
										<td height="32">
											<input type="checkbox" name="sqUserlogTab.status0" value="0"
												checked="checked">
											正常
											<input type="checkbox" name="sqUserlogTab.status1" value="1"
												checked="checked">
											失败
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
				<input type="hidden" name="HQL" value="USER_LOG_LIST" />
			</form>
			<div class="right_centerwk">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="from_btimg form_bt">
							<span>&nbsp;</span>
							<h1>
								查询结果
							</h1>
						</td>
					</tr>
					<tr>
						<td>
							<table align="center" border="0" width="100%" class="form_table"
								id="comm">
								<tr id="tab">
									<td height="22" style="font-weight: bold" width="150px">
										流水号
									</td>
									<td height="22" style="font-weight: bold" width="200px">
										用户信息
									</td>
									<td height="22" style="font-weight: bold" width="100px">
										操作IP
									</td>
									<td height="22" style="font-weight: bold" width="100px">
										操作日期
									</td>
									<td height="22" style="font-weight: bold" width="100px">
										操作时间
									</td>
									<td height="22" style="font-weight: bold">
										描述
									</td>
									<td height="22" style="font-weight: bold" width="100px">
										状态
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					bgcolor="#e9f2f7">
					<tr height="35">
						<td align="right">
							<div id="pageTag"></div>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</body>
</html>