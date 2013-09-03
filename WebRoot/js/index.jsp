<%@page import="com.sq.tools.Public"%>
<html>
<script type="text/javascript" src="../js/jquery-calendar.js"></script> 
		<link rel="stylesheet" type="text/css" href="../css/jquery-calendar.css" /> 
<script language="javascript"><!--
$(document).ready(function(){
	//日期控件格式
	//$('#datepick1').click(calendar($('#datepick1')));
	if($('#datepick1').val()=='')
		$('#datepick1').val('<%=Public.getSystemTimeToFormat("yyyy-MM-dd").substring(0,8)+"01"%>')
//	$('#datepick2').click(WdatePicker);
	if($('#datepick2').val()=='')
		$('#datepick2').val('<%=Public.getSystemTimeToFormat("yyyy-MM-dd")%>');
//	$('#datepick3').click(WdatePicker);
	if($('#datepick3').val()=='')
		$('#datepick3').val('<%=Public.getSystemTimeToFormat("yyyy-MM-dd").substring(0,8)+"01"%>');
//	$('#datepick4').click(WdatePicker);
	if($('#datepick4').val()=='')
		$('#datepick4').val('<%=Public.getSystemTimeToFormat("yyyy-MM-dd")%>');
//	$('#datepick5').click(WdatePicker);
	if($('#datepick5').val()=='')
		$('#datepick5').val('<%=Public.getSystemTimeToFormat("yyyy-MM-dd")%>');
})
function RndNum(n){
	var rnd="";
	for(var i=0;i<n;i++)
	rnd+=Math.floor(Math.random()*10);
	return rnd;
}
--></script>
</html>