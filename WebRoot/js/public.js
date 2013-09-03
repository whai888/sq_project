/**
 * 判断给定的字符串是否为指定长度的maxlength数字
 * 
 * @param str
 * @param len
 * @return
 */
function isCheckMaxLenth(inputStr) {
	var len = 0;
	for ( var i = 0; i < inputStr.value.length; i++) {
		char = inputStr.value.charCodeAt(i);
		if (!(char > 255)) {
			len = len + 1;
		} else {
			len = len + 2;
		}
	}
	if (len > inputStr.maxLength) {
		alert('输入域的长度不能超过' + inputStr.maxLength + '，中文是两个字符');
		inputStr.focus();
		return false;
	}
	return true;
}

/**
 * 查看form1表单中inputName域是否有值
 * 
 * @param form1Input
 *            表单的值
 * @param alterTip
 *            提示信息
 * @return
 */
function isNull(form1Input, alterTip) {
	if (form1Input.val() == '' || form1Input.val() == null || form1Input.val().length == 0 ) {
		alert(alterTip + '必须输入');
		return false;
	}
	return true;
}

/**
 * 查看form1表单中inputName域是否有值
 * 
 * @param form1Input
 *            表单的值
 * @param alterTip
 *            提示信息
 * @return	为空false  非空为true
 */
function isNullNoTip(form1Input) {
	if (form1Input.val() == '' || form1Input.val() == null || form1Input.val().length == 0 ) {
		return false;
	}
	return true;
}

/**
 * 判断输入是否有邮箱
 * 
 * @param vEMail
 * @return
 */
function isEmail(vEMail) {
	if (vEMail != "") {
		var regValid = /^([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
		return regValid.test(vEMail);
	}
}

/**
 * 手机号码检查
 * 
 * @param mobile
 * @return
 */
function isMobile(mobile) {
	//return (/^0{0,1}(13[0-9]|15[7-9]|153|156|18[7-9])[0-9]{8}$/.test(mobile.Trim()));
	return true ;
}

/**
 * 计算两个日期是否在一定的跨度之内
 * @param startDate	开始日期
 * @param endDate	结束日期
 * @param span	跨度 以月为单位
 * @return	符合要求返回true 否则返回false
 */
function dateToBiff(startDate , endDate , span){
	if(startDate > endDate){
		alert('开始日期不能大于结束日期');
		return false ;
	}
	var dt1=new Date(startDate.replace(new RegExp("-","g"),'/')); 
	var date = DateAdd("M" , (span + 1)  , dt1 ) ;
	if(date.Format("yyyy-MM-dd") < endDate){
		alert('查询日期之间的跨度不能超过' + span + '月，请重新选择日期');
		return false ;
	}
	return true ;
}

/**
 * 日期格式化函数
 * var ddd = new Date();
 * document.write (ddd.format('yy-MM-dd hh:mm:ss'));
 */
Date.prototype.format = function(format)
{
    var o =
    {
        "M+" : this.getMonth()+1, //month
        "d+" : this.getDate(),    //day
        "h+" : this.getHours(),   //hour
        "m+" : this.getMinutes(), //minute
        "s+" : this.getSeconds(), //second
        "q+" : Math.floor((this.getMonth()+3)/3),  //quarter
        "S" : this.getMilliseconds() //millisecond
    }
    if(/(y+)/.test(format))
    format=format.replace(RegExp.$1,(this.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)
    if(new RegExp("("+ k +")").test(format))
    format = format.replace(RegExp.$1,RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
    return format;
}

/**
 * 日期相加减，得到新的日期
 * 调用方式change_date(3)
 */
var change_date = function(days) {   
    // 参数表示在当前日期下要增加的天数   
    var now = new Date();   
    // + 1 代表日期加，- 1代表日期减   
    now.setDate((now.getDate() + 1) + 1 * days);   
    var year = now.getFullYear();   
    var month = now.getMonth() + 1;   
    var day = now.getDate();   
    if (month < 10) {   
        month = '0' + month;   
    }   
    if (day < 10) {   
        day = '0' + day;   
    }   
    return year + '-' + month + '-' + day;   
}

/**
 * 控制输入域必须为全数字
 * 
 * @param inputStr
 */
function isInputCheckNumber(inputStr) {
	inputStr.value = inputStr.value.replace(/[^\d]/g, '')
	inputStr.focus();
}

String.prototype.Trim = function() {
	var m = this.match(/^\s*(\S+(\s+\S+)*)\s*$/);
	return (m == null) ? "" : m[1];
}

/**
 * 字符串处理函数
 * @param str 要处理的字符串函数
 * @param substr 要处理的子字符串函数
 * @param flag 需要删除子串，要添加子串 0:添加   1:删除
 */
function strHandle(str , substr , flag){
	if(flag ==0 ){
		return substr + "|" + str ;
	}else if(flag == 1){
		var temp = str.split(substr + "|") ;
		return temp[0] + temp[1] ;
	}
}
