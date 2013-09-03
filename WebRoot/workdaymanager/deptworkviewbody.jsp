<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.sq.sys.ApplicationDate"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="com.sq.model.vo.SqWorkdayManager"%>
<%@page import="com.sq.tools.Public"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.sq.model.vo.SqGroupTab"%>
<%@page import="java.util.Set"%>
<%@page import="com.sq.model.vo.SqUserTab"%>
<%@page import="com.sq.model.vo.SqDeptTab"%>
<%
	boolean isShow = false ;
	int count = 1 ;
	SqUserTab sqUserTabTemp = (SqUserTab)request.getAttribute("sqUserTab");
	SqDeptTab sqDeptTab = (SqDeptTab)request.getAttribute("sqDeptTab");
	Map deptWorkMap = (Map)request.getAttribute("deptWorkMap");
	session.removeAttribute("myWorkDayViewList");
	session.setAttribute("myWorkDayViewList",deptWorkMap);
	Iterator it = deptWorkMap.entrySet().iterator();
	String startDate = "";
	String endDate="";
	while (it.hasNext()) {
          Map.Entry entry = (Map.Entry) it.next();
          List workDayList = (List)entry.getValue();
          for(int i=0 ; i<workDayList.size() ; i++){
    		SqWorkdayManager sqWorkdayManager = (SqWorkdayManager)workDayList.get(i);
    		if(sqWorkdayManager.getWeekDate()==null || sqWorkdayManager.getWeekDate().equals("")){
    			isShow = true ;
    		}
    		sqUserTabTemp = sqWorkdayManager.getSqUserTab();
    		String startDateTemp = Public.getTimeToFormat(sqWorkdayManager.getWorkStartDate() , "yyyy-MM-dd") ;
			String endDateTemp = Public.getTimeToFormat(sqWorkdayManager.getWorkEndDate() , "yyyy-MM-dd") ;
			if(startDate.equals("")){
				startDate = startDateTemp;
			}
			if(endDate.equals("")){
				endDate = endDateTemp;
			}
			//根据指定 String 大于、等于还是小于此 String（不考虑大小写），分别返回一个负整数、0 或一个正整数。
			if(startDateTemp.compareToIgnoreCase(startDate) < 0)
				startDate = startDateTemp ;
			if(endDateTemp.compareToIgnoreCase(endDate) < 0)
				endDate = endDateTemp ;
				
	    	}
     }
%>
			<table width="100%" cellpadding="0" cellspacing="0" border="0">
  <tr>
      <td height="50" colspan="4" align="center" bgcolor="#FFFFFF"><font  style="font-weight:bold" size="6"><%=sqDeptTab.getDeptName() %>周报</font></td>
    </tr>
    <tr>
      <td height="20" width="7%" align="right" nowrap bgcolor="#FFFFFF"><strong>编写人：</strong></td>
      <td height="20" width="80%" align="left" nowrap bgcolor="#FFFFFF"><%=sqUserTabTemp.getUserName() %></td>
      <td height="20" width="7%" align="right" nowrap bgcolor="#FFFFFF"><strong>周报日期：</strong></td>
      <td height="20" align="left" nowrap bgcolor="#FFFFFF"><%=startDate %>至<%=endDate %></td>
    </tr>
</table>
<%
	it = deptWorkMap.entrySet().iterator();
	while (it.hasNext()) {
			int userCout = 0;
			StringBuffer userName = new StringBuffer("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;") ;
          Map.Entry entry = (Map.Entry) it.next();
          SqGroupTab sqGroupTab = (SqGroupTab)entry.getKey();
          List workDayList = (List)entry.getValue();
%>
<table width="100%" border="0" cellpadding="0" cellspacing="0" id="deptworkview">
  <tr bgcolor="#F79646">
    <td width="100%"  height="50" colspan="3" align="center"><font color="#FFFFFF" style="font-size:28px;font-weight:bold"><%=sqGroupTab.getGroupName() %></font></td>
  </tr>
  <tr>
    <td width="100%" height="50" colspan="3" bgcolor="#E6E6E6"><font style="font-size:18px;">一、工作小结</font></td>
  </tr>
  <tr>
    <td height="35" align="center"><font style="font-size:16px;">序号</font></td>
    <td colspan="2" align="center"><font style="font-size:16px;">项目组工作概要情况</font></td>
  </tr>
    <%
    	count = 1 ;
    	for(int i=0 ; i<workDayList.size() ; i++){
    		SqWorkdayManager sqWorkdayManager = (SqWorkdayManager)workDayList.get(i);
    		if(sqWorkdayManager.getWorkContent()!=null && !sqWorkdayManager.getWorkContent().equals("")){
    		out.println("<tr><td width='58' align='center'>&nbsp;&nbsp;" + Public.mapInt.get(i+1) + "、 </td><br/>");
    		out.println("<td width='602' colspan='2'><font style='font-size:14px; font-weight:bold'>" + sqWorkdayManager.getWorkContent().replaceAll("\\r\\n" , "<br/>") + "<br/></font></td></tr>");
    	}}
    %>
    <tr>
    <td width="58" align="center">&nbsp;</td>
    <td colspan="2"><font style="font-size:14px; font-weight:bold">
    &nbsp;</font></td>
  </tr>
  <tr>
    <td width="660" height="50" colspan="3" bgcolor="#E6E6E6"><font style="font-size:18px;">二、需要协调事项</font> </td>
  </tr>
  <tr>
   <td height="35" align="center"><font style="font-size:16px;">序号</font></td>
    <td colspan="2" align="center"><font style="font-size:16px;">待协调事项说明</font></td>
  </tr>
  <%
    	count = 1 ;
    	for(int i=0 ; i<workDayList.size() ; i++){
    		SqWorkdayManager sqWorkdayManager = (SqWorkdayManager)workDayList.get(i);
    		if(sqWorkdayManager.getDiscussProblem()!=null && !sqWorkdayManager.getDiscussProblem().equals("")){
    		out.println("<tr><td width='58' align='center'>&nbsp;&nbsp;" + Public.mapInt.get(i+1) + "、 </td><br/>");
    		out.println("<td width='602' colspan='2'><font style='font-size:14px; font-weight:bold'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + sqWorkdayManager.getDiscussProblem().replaceAll("\\r\\n" , "<br/>") + "<br/></font></td></tr>");
    	}}
    %>
    <tr>
    <td width="58" align="center">&nbsp;</td>
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td width="660" height="50" colspan="3" bgcolor="#E6E6E6"><font style="font-size:18px;">三、其它事项或建议</font> </td>
  </tr>
  <tr>
    <td height="35" align="center"><font style="font-size:16px;">序号</font></td>
    <td colspan="2" align="center"><font style="font-size:16px;">事项说明</font></td>
  </tr>
  <%
    	count = 1 ;
    	for(int i=0 ; i<workDayList.size() ; i++){
    		SqWorkdayManager sqWorkdayManager = (SqWorkdayManager)workDayList.get(i);
    		if(sqWorkdayManager.getWorkSug()!=null && !sqWorkdayManager.getWorkSug().equals("")){
    		out.println("<tr><td width='58' align='center'>&nbsp;&nbsp;" + Public.mapInt.get(i+1) + "、 </td><br/>");
    		out.println("<td width='602' colspan='2'><font style='font-size:14px; font-weight:bold'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + sqWorkdayManager.getWorkSug().replaceAll("\\r\\n" , "<br/>") + "<br/></font></td></tr>");
    	}}
    %>
    <tr>
    <td width="58" align="center">&nbsp;</td>
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td width="660" height="50" colspan="3" bgcolor="#E6E6E6"><font style="font-size:18px;">四、项目组成员</font> </td>
  </tr>
  <% for(int i=0 ; i<workDayList.size() ; i++){
   		SqWorkdayManager sqWorkdayManager = (SqWorkdayManager)workDayList.get(i);
   		if(sqWorkdayManager.getRemark1() !=null) {
   			String [] userTemp =  sqWorkdayManager.getRemark1().split("   ");
   			for(int j=0 ; j<userTemp.length ; j++){
   				if(userName. indexOf(userTemp[j]) == -1 ){
   					userName.append(userTemp[j] + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
   					userCout ++ ;
   				}
   			}
   		}
   	} %>
  <tr>
    <td height="35"  align="center"><font style="font-size:16px;">人数</font></td>
    <td colspan="2" align="center"><font style="font-size:16px;">名单</font></td>
  </tr>
  <tr>
    <td width="58" align="center"><p><%=userCout %></p></td>
    <td colspan="2">
    <%=userName%>&nbsp;
    </td>
  </tr>
</table>
<br/><br/><br/><br/><br/>
<%} %>			<p>
				&nbsp;
			</p>
			<input type="hidden" name="returnPage">
