var groupNo = '' ;
var count = 0 ;	//记录分页的编号
$(document).ready(function(){
    var x = 150;  
    var y = -40;
    $(".ashow").mouseover(function(e){
    	var pos=$(this).offset();
        $(".roundedcorner").css({"top":(pos.top - y) + "px","left":(pos.left + x) + "px"}).fadeIn("slow");
        $(".preview").html('请稍后，正在查询...');
        groupNo = $(this).find(".groupNo").html();
        count=0;
        onChanagerProject();
    })
//   $(".preview").mouseout(function(){
//  		$(".preview").hide();
// 	})
//	$(".preview").mouseover(function(){
//	  	$(".preview").show();
//	}) 
})
function onChanagerProject(){
	//请求地址
	var url = 'page.shtml' ;
	//传送的值
	var params = {groupNo: groupNo,
				  HQL:       'WORKDAY_TO_GROUPNO'     ,
				  type:		 '2',
				  currentStr: ++count };
	//使用$.post方式	
		$.post(url, //服务器要接受的url
			params, //传递的参数		
			function cbf(data) { //服务器返回后执行的函数 参数 data保存的就是服务器发送到客户端的数据
				var weekDateVal = "<div class='more' style='float:left;width:'>";
				var weekDate = "";
					for(var i=0 ; i<data.listData.length ; i++ ){
						if(parseInt((i+1)%2) == 0){
							weekDate = weekDate + "<a href='javascript:void(0)' class='form_font' onclick='window.open(\"workdaymanager/workdaygrouplist.shtml?sqWorkdayManager.sqGroupTab.groupNo="+groupNo+"&sqWorkdayManager.weekDate="+data.listData[i][1]+"&returnPage=groupworkview\")'>"+data.listData[i][1] + "</a><br/><br/>";
						}else{
							weekDate = weekDate + "<a href='javascript:void(0)' class='form_font' onclick='window.open(\"workdaymanager/workdaygrouplist.shtml?sqWorkdayManager.sqGroupTab.groupNo="+groupNo+"&sqWorkdayManager.weekDate="+data.listData[i][1]+"&returnPage=groupworkview\")'>"+data.listData[i][1] + "</a>&nbsp;&nbsp;&nbsp;&nbsp;";
						}
					}
					if(count>1){
						$(".more").append(weekDate);
					}if(data.listData.length==0){
						$(".preview").html('');
					}else{
						weekDateVal = weekDateVal +weekDate+ "</div><div style='text-align:right'><a href='javascript:void(0)' class='form_font' onclick=\"onChanagerProject()\">>>more</a></div>";
						$(".preview").html(weekDateVal);
					}
			}, 
			'json' //数据传递的类型  json
		);
}