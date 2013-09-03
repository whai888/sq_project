package com.sq.action.workday;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.jacob.com.ComThread;
import com.sq.logic.workday.impl.WorkDaySortList;
import com.sq.model.vo.SqWorkdayManager;
import com.sq.sys.ApplicationDate;
import com.sq.tools.Public;
import com.sq.tools.WordBean;
import com.sq.tools.WorkDayTools;


/**
 *
 *@autor whai
 */
public class MyWorkDay {
	private int noworkSize = 0;
	private int nextPlanSize = 0;
	private int discussSize = 0;
	private int workSugSize = 0 ;
	private int repeat = 0 ;
	private String startDate = "";
	private String endDate = "" ;
	private String tempProjectId = "";
	private String tempProjectId1 = "";
	private String tempProjectId2 = "";
	private String tempProjectId3 = "";
	private String tempProjectId4 = "";
	private String userName = "" ;
	private String groupName = "" ;
	private  List workContentList = new ArrayList() ;
	private  List noWorkContentList = new ArrayList() ;
	private  List workNextPlanList = new ArrayList() ;
	private  List discussProblemList= new ArrayList() ;
	private  List workSugList = new ArrayList() ;
	
	public void workDay(List<SqWorkdayManager> workDayList){
		WorkDaySortList workDay = new WorkDaySortList();
		workDay.Sort(workDayList , "desc") ;
		for(int i =0 ; i<workDayList.size() ; i++){
			SqWorkdayManager sqWorkdayManager = (SqWorkdayManager)workDayList.get(i);
			boolean flag = tempProjectId.equals(sqWorkdayManager.getSqProjectInfo().getProjectId()) ;
			if(!flag){
				tempProjectId = sqWorkdayManager.getSqProjectInfo().getProjectId();
			}else{
				repeat ++ ;
			}
			if(sqWorkdayManager.getNoWorkContent()!=null && !sqWorkdayManager.getNoWorkContent().equals("") && !tempProjectId1.equals(sqWorkdayManager.getSqProjectInfo().getProjectId())){
				noworkSize += 1;
				tempProjectId1 = sqWorkdayManager.getSqProjectInfo().getProjectId();
			}
			if(sqWorkdayManager.getDiscussProblem()!=null && !sqWorkdayManager.getDiscussProblem().equals("") && !tempProjectId2.equals(sqWorkdayManager.getSqProjectInfo().getProjectId())){
				discussSize += 1;
				tempProjectId2 = sqWorkdayManager.getSqProjectInfo().getProjectId();
			}
			if(sqWorkdayManager.getWorkNextPlan()!=null && !sqWorkdayManager.getWorkNextPlan().equals("") && !tempProjectId3.equals(sqWorkdayManager.getSqProjectInfo().getProjectId())){
				nextPlanSize += 1;
				tempProjectId3 = sqWorkdayManager.getSqProjectInfo().getProjectId();
			}
			if(sqWorkdayManager.getWorkSug()!=null && !sqWorkdayManager.getWorkSug().equals("") && !tempProjectId4.equals(sqWorkdayManager.getSqProjectInfo().getProjectId())){
				workSugSize += 1;
				tempProjectId4 = sqWorkdayManager.getSqProjectInfo().getProjectId();
			}
			String temp = Public.getTimeToFormat(sqWorkdayManager.getWorkDate() , "yyyy年MM月dd日") ;
			if(startDate.equals("")){
				startDate = temp;
				endDate = temp;
			}
			if(sqWorkdayManager.getSqUserTab() !=null )
			userName = sqWorkdayManager.getSqUserTab().getUserName();
			if(sqWorkdayManager.getSqGroupTab() !=null)
			groupName = sqWorkdayManager.getSqGroupTab().getGroupName();
			//根据指定 String 大于、等于还是小于此 String（不考虑大小写），分别返回一个负整数、0 或一个正整数。
			if(temp.compareToIgnoreCase(startDate) < 0)
				startDate = temp ;
			if(temp.compareToIgnoreCase(endDate) > 0)
				endDate = temp ;
		}
		
		//员工周报内容
		int count1 = 0 ;
	    String temp = "";	//记录项目编号
	    StringBuffer strBuff = new StringBuffer();
	    Map mapStatus = ApplicationDate.getSysPara(Arrays.asList("sq_project_stepwork_units"));
	    for(int i=0 ; i<workDayList.size() ; i++){
	    	strBuff = new StringBuffer();
	    	List<String> workContentTemp = new ArrayList();
		   SqWorkdayManager sqWorkdayManager = (SqWorkdayManager)workDayList.get(i);
		   count1 ++ ;
		   workContentTemp.add(count1+"");
		   workContentTemp.add(sqWorkdayManager.getSqProjectInfo().getProjectName());
		   temp = sqWorkdayManager.getSqProjectInfo().getProjectId();
		   int icount = 1;
		    for(int j=i ; j<workDayList.size() ; j++){
		    	SqWorkdayManager sqWorkdayManagerTemp = (SqWorkdayManager)workDayList.get(j);
		    	if(temp.equals(sqWorkdayManagerTemp.getSqProjectInfo().getProjectId())){
		    		strBuff.append(Public.mapInt.get(icount++) + "、" + "项目阶段："+sqWorkdayManager.getSqProjectStep().getStepName()+"，日期："+Public.getTimeToFormat(sqWorkdayManagerTemp.getWorkDate(),"yyyy-MM-dd")+"任务时长："+sqWorkdayManagerTemp.getTaskTime()+"小时，工作成果："+WorkDayTools.workLoadStr(mapStatus ,sqWorkdayManager.getWorkLoad(),sqWorkdayManager.getWorkUnits())+"\n"+sqWorkdayManagerTemp.getWorkContent()+"\n");
		    		i = j ;
		    	}
		    }
		    workContentTemp.add(strBuff.toString());
		    workContentTemp.add(sqWorkdayManager.getComplePercen()+"%");
		    workContentList.add(workContentTemp);
	   }
	    
	    //未完成工作及情况
	    count1 = 0 ;
	    temp = "";	//记录项目编号
	    for(int i=0 ; i<workDayList.size() ; i++){
	    	strBuff = new StringBuffer();
	    	List<String> noWorkContentTemp = new ArrayList();
		   SqWorkdayManager sqWorkdayManager = (SqWorkdayManager)workDayList.get(i);
		   if(sqWorkdayManager.getNoWorkContent()!=null && !sqWorkdayManager.getNoWorkContent().equals("")){
		   count1 ++ ;
		   temp = sqWorkdayManager.getSqProjectInfo().getProjectId();
		   noWorkContentTemp.add(count1+"");
		   noWorkContentTemp.add(sqWorkdayManager.getSqProjectInfo().getProjectName());
		    int icount = 1;
		    	SqWorkdayManager sqWorkdayManagerTemp = (SqWorkdayManager)workDayList.get(i);
		    	if(temp.equals(sqWorkdayManagerTemp.getSqProjectInfo().getProjectId()) && sqWorkdayManagerTemp.getNoWorkContent()!=null && !sqWorkdayManagerTemp.getNoWorkContent().equals("")){
		    		strBuff.append(Public.mapInt.get(icount++) + "、" + Public.getTimeToFormat(sqWorkdayManagerTemp.getWorkDate(),"yyyy-MM-dd")+"   " + sqWorkdayManagerTemp.getNoWorkContent()+"\n");
		    	}
		    	noWorkContentTemp.add(strBuff.toString());
		    	noWorkContentList.add(noWorkContentTemp);
		    }
		}
	    
	    //下周工作计划
	    count1 = 0 ;
	    for(int i=0 ; i<workDayList.size() ; i++){
	    	strBuff = new StringBuffer();
	    	 List<String> workNextPlanTemp = new ArrayList();
  		   SqWorkdayManager sqWorkdayManager = (SqWorkdayManager)workDayList.get(i);
  		   if(sqWorkdayManager.getWorkNextPlan()!=null && !sqWorkdayManager.getWorkNextPlan().equals("")){
  		   count1 ++ ;
  			temp = sqWorkdayManager.getSqProjectInfo().getProjectId();
  			workNextPlanTemp.add(count1+"");
  			workNextPlanTemp.add(sqWorkdayManager.getSqProjectInfo().getProjectName());
  		    int icount = 1;
  		    	SqWorkdayManager sqWorkdayManagerTemp = (SqWorkdayManager)workDayList.get(i);
  		    	if(temp.equals(sqWorkdayManagerTemp.getSqProjectInfo().getProjectId()) && sqWorkdayManagerTemp.getWorkNextPlan()!=null && !sqWorkdayManagerTemp.getWorkNextPlan().equals("")){
  		    		strBuff.append(Public.mapInt.get(icount++) + "、" + Public.getTimeToFormat(sqWorkdayManagerTemp.getWorkDate(),"yyyy-MM-dd")+"   " + sqWorkdayManagerTemp.getWorkNextPlan()+"\n");
  		    	}
  		    workNextPlanTemp.add(strBuff.toString());
  	  		  workNextPlanList.add(workNextPlanTemp);
  		    }
  		}
	    
	    //待协调解决的问题
	    count1 = 0 ;
	    for(int i=0 ; i<workDayList.size() ; i++){
	    	strBuff = new StringBuffer();
	    	List<String> discussProblemTemp = new ArrayList();
  		   SqWorkdayManager sqWorkdayManager = (SqWorkdayManager)workDayList.get(i);
  		   if(sqWorkdayManager.getDiscussProblem()!=null && !sqWorkdayManager.getDiscussProblem().equals("")){
  		   count1++;
  		   temp = sqWorkdayManager.getSqProjectInfo().getProjectId();
  		   discussProblemTemp.add(count1+"");
  		   int icount = 1;
  		    	SqWorkdayManager sqWorkdayManagerTemp = (SqWorkdayManager)workDayList.get(i);
  		    	if(temp.equals(sqWorkdayManagerTemp.getSqProjectInfo().getProjectId()) && sqWorkdayManagerTemp.getDiscussProblem()!=null && !sqWorkdayManagerTemp.getDiscussProblem().equals("")){
  		    		strBuff.append(Public.mapInt.get(icount++) + "、" + Public.getTimeToFormat(sqWorkdayManagerTemp.getWorkDate(),"yyyy-MM-dd")+"   " + sqWorkdayManagerTemp.getDiscussProblem()+"\n");
  		    	}
  		    	discussProblemTemp.add(strBuff.toString());
  		    	discussProblemList.add(discussProblemTemp);
  		   }
	  	}
	    
	    //工作建议
	    count1 = 0;
	    for(int i=0 ; i<workDayList.size() ; i++){
	    	strBuff = new StringBuffer();
	    	List<String> workSugTemp = new ArrayList();
 		   SqWorkdayManager sqWorkdayManager = (SqWorkdayManager)workDayList.get(i);
 		   if(sqWorkdayManager.getWorkSug()!=null && !sqWorkdayManager.getWorkSug().equals("")){
 		   count1++ ;
 		   temp = sqWorkdayManager.getSqProjectInfo().getProjectId();
 		   workSugTemp.add(count1+"");
 		    int icount = 1;
 		    	SqWorkdayManager sqWorkdayManagerTemp = (SqWorkdayManager)workDayList.get(i);
 		    	if(temp.equals(sqWorkdayManagerTemp.getSqProjectInfo().getProjectId()) && sqWorkdayManagerTemp.getWorkSug()!=null && !sqWorkdayManagerTemp.getWorkSug().equals("")){
 		    		strBuff.append(Public.mapInt.get(icount++) + "、" + Public.getTimeToFormat(sqWorkdayManagerTemp.getWorkDate(),"yyyy-MM-dd")+"   " + sqWorkdayManagerTemp.getWorkSug()+"\n");
 		    	}
 		    	workSugTemp.add(strBuff.toString());
 		    	workSugList.add(workSugTemp);
 		    }
	    }
	}
	
	public String workDayExport(String fmlFileName , String filePath) {
		ComThread.InitSTA();
		int tableIndex = 1 ;
		WordBean msWordManager = new WordBean();
		WordBean msWordManager1 = new WordBean();
		String fileName = "员工工作周报"+this.getStartDate()+"到"+this.getEndDate()+"("+this.getUserName()+").doc";
		try {
			//员工周报
			msWordManager.openDocument(fmlFileName);
			msWordManager.save(filePath + fileName);
			msWordManager.closeDocument();
			
			msWordManager1.openDocument(filePath + fileName);
			//开始进行文本替换
			//编写人姓名
			msWordManager1.replaceAllText("$USERNAME$", this.getUserName());
			//所属项目组
			msWordManager1.replaceAllText("$GROUPNAME$", this.getGroupName());
			//日期
			msWordManager1.replaceAllText("$WOKRDATE$", this.getStartDate()+"到" + this.getEndDate());
			int tempCount = 3 ;
			//周报工作内容
			for (int i = this.getWorkContentList().size()-1; i >=0; i--) {
				List temp = (List)this.getWorkContentList().get(i);
				//从第tempCount行开始前面插入一行
				msWordManager1.addTableRow(tableIndex, tempCount);
				for (int j = 0; j < temp.size(); j++) {
					//在指定的单元格中填写数据
					msWordManager1.putTxtToCell(tableIndex, tempCount, 2+j, (String)temp.get(j));
				}
			}
			//未完成工作及情况
			tempCount = 4+tempCount+this.getWorkContentList().size() ;
			for (int i = this.getNoWorkContentList().size()-1; i >=0; i--) {
				List temp = (List)this.getNoWorkContentList().get(i);
				//从第7+this.getWorkContentList().size()行开始前面插入一行
				msWordManager1.addTableRow(tableIndex, tempCount);
				for (int j = 0; j < temp.size(); j++) {
					//在指定的单元格中填写数据
					msWordManager1.putTxtToCell(tableIndex, tempCount, 2+j, (String)temp.get(j));
				}
			}
			//下日计划
			tempCount = 3+tempCount+this.getNoWorkContentList().size() ;
			for (int i = this.getWorkNextPlanList().size()-1; i >=0; i--) {
				List temp = (List)this.getWorkNextPlanList().get(i);
				//从第N行开始前面插入一行
				msWordManager1.addTableRow(tableIndex,tempCount );
				for (int j = 0; j < temp.size(); j++) {
					//在指定的单元格中填写数据
					msWordManager1.putTxtToCell(tableIndex, tempCount, 2+j, (String)temp.get(j));
				}
			}
			//待协调及解决的事项
			tempCount = 3+tempCount+this.getWorkNextPlanList().size() ;
			for (int i = this.getDiscussProblemList().size()-1; i >=0; i--) {
				List temp = (List)this.getDiscussProblemList().get(i);
				//从第三行开始前面插入一行
				msWordManager1.addTableRow(tableIndex, tempCount);
				for (int j = 0; j < temp.size(); j++) {
					//在指定的单元格中填写数据
					msWordManager1.putTxtToCell(tableIndex, tempCount, 2+j, (String)temp.get(j));
				}
			}
			//工作建议
			tempCount = 3+tempCount+this.getDiscussProblemList().size() ;
			for (int i = this.getWorkSugList().size()-1; i >=0; i--) {
				List temp = (List)this.getWorkSugList().get(i);
				//从第三行开始前面插入一行
				msWordManager1.addTableRow(tableIndex, tempCount);
				for (int j = 0; j < temp.size(); j++) {
					//在指定的单元格中填写数据
					msWordManager1.putTxtToCell(tableIndex, tempCount, 2+j, (String)temp.get(j));
				}
			}
			
			//开始合并单元格(行)
			tempCount = 1 ;
			//工作完成情况合并
			msWordManager1.mergeCell(tableIndex, tempCount, 2, tempCount, msWordManager1.getTableColsCount(tableIndex));
			msWordManager1.putTxtToCell(tableIndex,tempCount,2,"工作完成情况");
			
			//未完成工作情况说明
			tempCount = 4+tempCount+this.getWorkContentList().size() ;
			msWordManager1.mergeCell(tableIndex, tempCount, 2, tempCount, msWordManager1.getTableColsCount(tableIndex));
			msWordManager1.putTxtToCell(tableIndex,tempCount,2,"未完成工作情况说明");
			msWordManager1.mergeCell(tableIndex, tempCount+1, 4, tempCount+1, msWordManager1.getTableColsCount(tableIndex));
			msWordManager1.putTxtToCell(tableIndex,tempCount+1,4,"工作任务说明");
			for (int i = 0; i < this.getNoWorkContentList().size(); i++) {
				String content = msWordManager1.getContent(tableIndex, tempCount+2+i, 4);
				msWordManager1.mergeCell(tableIndex, tempCount+2+i, 4, tempCount+2+i, msWordManager1.getTableColsCount(tableIndex));
				msWordManager1.putTxtToCell(tableIndex,tempCount+2+i,4,content);
			}
			
			//工作事项
			tempCount = 4+tempCount+this.getNoWorkContentList().size() ;
			msWordManager1.mergeCell(tableIndex, tempCount-2, 4, tempCount-2, msWordManager1.getTableColsCount(tableIndex));
			msWordManager1.putTxtToCell(tableIndex,tempCount-2,4,"");
			msWordManager1.mergeCell(tableIndex, tempCount-1, 4, tempCount-1, msWordManager1.getTableColsCount(tableIndex));
			msWordManager1.putTxtToCell(tableIndex,tempCount-1,4,"");
			for (int i = 0; i < this.getWorkNextPlanList().size(); i++) {
				String content = msWordManager1.getContent(tableIndex, tempCount+1+i, 4);
				msWordManager1.mergeCell(tableIndex, tempCount+1+i, 4, tempCount+1+i, msWordManager1.getTableColsCount(tableIndex));
				msWordManager1.putTxtToCell(tableIndex,tempCount+1+i,4,content);
			}
			
			msWordManager1.mergeCell(tableIndex, tempCount, 3,tempCount, msWordManager1.getTableColsCount(tableIndex));
			msWordManager1.putTxtToCell(tableIndex,tempCount,3,"工作事项");
			//待解决问题说明
			tempCount = 3+tempCount+this.getWorkNextPlanList().size() ;
			msWordManager1.mergeCell(tableIndex, tempCount-2, 3, tempCount-2, msWordManager1.getTableColsCount(tableIndex));
			msWordManager1.putTxtToCell(tableIndex,tempCount-2,3,"");
			msWordManager1.mergeCell(tableIndex, tempCount-1, 3, tempCount-1, msWordManager1.getTableColsCount(tableIndex));
			msWordManager1.putTxtToCell(tableIndex,tempCount-1,3,"");
			msWordManager1.mergeCell(tableIndex, tempCount, 3, tempCount, msWordManager1.getTableColsCount(tableIndex));
			msWordManager1.putTxtToCell(tableIndex,tempCount,3,"待协调及解决事项说明");
			for (int i = 0; i < this.getDiscussProblemList().size(); i++) {
				String content = msWordManager1.getContent(tableIndex, tempCount+1+i, 3);
				msWordManager1.mergeCell(tableIndex, tempCount+1+i, 3, tempCount+1+i, msWordManager1.getTableColsCount(tableIndex));
				msWordManager1.putTxtToCell(tableIndex,tempCount+1+i,3,content);
			}
			
			//工作建议
			tempCount = 3+tempCount+this.getDiscussProblemList().size() ;
			msWordManager1.mergeCell(tableIndex, tempCount-2, 3, tempCount-2, msWordManager1.getTableColsCount(tableIndex));
			msWordManager1.putTxtToCell(tableIndex,tempCount-2,3,"");
			msWordManager1.mergeCell(tableIndex, tempCount-1, 3, tempCount-1, msWordManager1.getTableColsCount(tableIndex));
			msWordManager1.putTxtToCell(tableIndex,tempCount-1,3,"");
			msWordManager1.mergeCell(tableIndex, tempCount, 3, tempCount, msWordManager1.getTableColsCount(tableIndex));
			msWordManager1.putTxtToCell(tableIndex,tempCount,3,"工作建议");
			for (int i = 0; i < this.getWorkSugList().size(); i++) {
				String content = msWordManager1.getContent(tableIndex, tempCount+1+i, 3);
				msWordManager1.mergeCell(tableIndex, tempCount+1+i, 3, tempCount+1+i, msWordManager1.getTableColsCount(tableIndex));
				msWordManager1.putTxtToCell(tableIndex,tempCount+1+i,3,content);
			}
			msWordManager1.mergeCell(tableIndex, msWordManager1.getTableRowsCount(tableIndex)-1, 3, msWordManager1.getTableRowsCount(tableIndex)-1, msWordManager1.getTableColsCount(tableIndex));
			msWordManager1.putTxtToCell(tableIndex,msWordManager1.getTableRowsCount(tableIndex)-1,3,"");
			msWordManager1.mergeCell(tableIndex, msWordManager1.getTableRowsCount(tableIndex), 3, msWordManager1.getTableRowsCount(tableIndex), msWordManager1.getTableColsCount(tableIndex));
			msWordManager1.putTxtToCell(tableIndex,msWordManager1.getTableRowsCount(tableIndex),3,"");
			
			//开始合并单元格(列)
			//本周工作计划
			int workContentSize = 8+this.getWorkContentList().size()+this.getNoWorkContentList().size() ;
			msWordManager1.mergeCell(tableIndex, 1, 1, workContentSize, 1);
			//下周工作计划
			int nextWorkContentSize = 6+workContentSize+this.getWorkNextPlanList().size()+this.getDiscussProblemList().size();
			msWordManager1.mergeCell(tableIndex,  workContentSize+1, 1, nextWorkContentSize, 1);
			//工作建议
			int workSub = 3 + nextWorkContentSize + this.getWorkSugList().size();
			msWordManager1.mergeCell(tableIndex, nextWorkContentSize+1, 1, workSub, 1);
			
			
			msWordManager1.closeDocument();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			msWordManager.close();
			msWordManager1.close();
			ComThread.Release();
		}
		return fileName;
	}
	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List getWorkContentList() {
		return workContentList;
	}

	public int getWorkSugSize() {
		return workSugSize;
	}

	public List getWorkNextPlanList() {
		return workNextPlanList;
	}

	public List getWorkSugList() {
		return workSugList;
	}

	public List getNoWorkContentList() {
		return noWorkContentList;
	}

	public List getDiscussProblemList() {
		return discussProblemList;
	}

}
