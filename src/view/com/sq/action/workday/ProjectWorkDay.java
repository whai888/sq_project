package com.sq.action.workday;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.jacob.com.ComThread;
import com.sq.model.vo.SqWorkdayManager;
import com.sq.sys.ApplicationDate;
import com.sq.tools.Public;
import com.sq.tools.WordBean;

/**
 *
 *@autor whai
 */
public class ProjectWorkDay {
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
	private String userName = "";
	private String groupName = "";
	private  List workContentList = new ArrayList() ;
	private  List noWorkContentList = new ArrayList() ;
	private  List workNextPlanList = new ArrayList() ;
	private  List discussProblemList= new ArrayList() ;
	private  List workSugList = new ArrayList() ;
	private  List userList = new ArrayList() ;	//项目人数
	
	public void workDay(List<SqWorkdayManager> workViewList,String userName,String groupName){
		this.userName = userName ;
		this.groupName = groupName;
		for(int i =0 ; i<workViewList.size() ; i++){
			SqWorkdayManager sqWorkdayManager = (SqWorkdayManager)workViewList.get(i);
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
		
		Map workManageMap = ApplicationDate.getSysPara(Arrays.asList("sq_workday_managertask_status"));
		//项目工作内容
		int count1 = 0 ;
		StringBuffer strBuff = new StringBuffer();
	    String temp = "";	//记录项目编号
	    for(int i=0 ; i<workViewList.size() ; i++){
	    	strBuff = new StringBuffer();
	    	List<String> workContentTemp = new ArrayList();
			   SqWorkdayManager sqWorkdayManager = (SqWorkdayManager)workViewList.get(i);
			   if(sqWorkdayManager.getWorkContent()!=null && !sqWorkdayManager.getWorkContent().equals("")){
				   count1 ++ ;
				   temp = sqWorkdayManager.getSqProjectInfo().getProjectId();
				   workContentTemp.add(count1+"");
				   workContentTemp.add(sqWorkdayManager.getSqProjectInfo().getProjectName());
				   workContentTemp.add(sqWorkdayManager.getSqProjectStep().getStepName());
			    }
			    int icount = 1;
			    for(int j=i ; j<workViewList.size() ; j++){
			    	SqWorkdayManager sqWorkdayManagerTemp = (SqWorkdayManager)workViewList.get(j);
			    	if(temp.equals(sqWorkdayManagerTemp.getSqProjectInfo().getProjectId())){
			    		strBuff.append(Public.mapInt.get(icount++) + "、" + sqWorkdayManagerTemp.getWorkContent()+"\n");
			    		i = j ;
			    	}
			    }
			    workContentTemp.add(strBuff.toString());
			    workContentTemp.add((String) workManageMap.get(sqWorkdayManager.getTaskStatus()));
			    workContentList.add(workContentTemp);
	    }
	    
	    //未完成工作情况
	    count1 = 0 ;
	    temp = "";	//记录项目编号
	    for(int i=0 ; i<workViewList.size() ; i++){
	    	strBuff = new StringBuffer();
	    	List<String> noWorkContentTemp = new ArrayList();
			   SqWorkdayManager sqWorkdayManager = (SqWorkdayManager)workViewList.get(i);
			   if(sqWorkdayManager.getNoWorkContent()!=null && !sqWorkdayManager.getNoWorkContent().equals("")){
			   count1 ++ ;
			   temp = sqWorkdayManager.getSqProjectInfo().getProjectId();
			   noWorkContentTemp.add(count1+"");
			   noWorkContentTemp.add(sqWorkdayManager.getSqProjectInfo().getProjectName());
			   int icount = 1;
			    for(int j=i ; j<workViewList.size() ; j++){
			    	SqWorkdayManager sqWorkdayManagerTemp = (SqWorkdayManager)workViewList.get(j);
			    	if(temp.equals(sqWorkdayManagerTemp.getSqProjectInfo().getProjectId())){
			    		strBuff.append(Public.mapInt.get(icount++) + "、" + sqWorkdayManagerTemp.getNoWorkContent()+"\n");
			    		i = j ;
			    	}
			    }
			    noWorkContentTemp.add(strBuff.toString());
			    noWorkContentList.add(noWorkContentTemp);
			  }
	    }
	    
	    //下日计划
	    count1 = 0 ;
	    temp = "";	//记录项目编号
	    for(int i=0 ; i<workViewList.size() ; i++){
	    	strBuff = new StringBuffer();
	    	 List<String> workNextPlanTemp = new ArrayList();
			   SqWorkdayManager sqWorkdayManager = (SqWorkdayManager)workViewList.get(i);
			   if(sqWorkdayManager.getWorkNextPlan()!=null && !sqWorkdayManager.getWorkNextPlan().equals("")){
			   count1 ++ ;
			   temp = sqWorkdayManager.getSqProjectInfo().getProjectId();
			   workNextPlanTemp.add(count1+"");
			   workNextPlanTemp.add(sqWorkdayManager.getSqProjectInfo().getProjectName());
			    }
			   	int icount = 1;
			    for(int j=i ; j<workViewList.size() ; j++){
			    	SqWorkdayManager sqWorkdayManagerTemp = (SqWorkdayManager)workViewList.get(j);
			    	if(temp.equals(sqWorkdayManagerTemp.getSqProjectInfo().getProjectId())){
			    		strBuff.append(Public.mapInt.get(icount++) + "、" + sqWorkdayManagerTemp.getWorkNextPlan()+"\n");
			    		i = j ;
			    	}
			    }
			    workNextPlanTemp.add(strBuff.toString());
	  	  		workNextPlanList.add(workNextPlanTemp);
	    }
	    
	    
	    //需协调解决的问题
	    count1 = 0 ;
	    temp = "";	//记录项目编号
	    for(int i=0 ; i<workViewList.size() ; i++){
	    	strBuff = new StringBuffer();
	    	List<String> discussProblemTemp = new ArrayList();
			   SqWorkdayManager sqWorkdayManager = (SqWorkdayManager)workViewList.get(i);
			   if(sqWorkdayManager.getDiscussProblem()!=null && !sqWorkdayManager.getDiscussProblem().equals("")){
			   count1 ++ ;
			   temp = sqWorkdayManager.getSqProjectInfo().getProjectId();
			   discussProblemTemp.add(count1+"");
			   discussProblemTemp.add(sqWorkdayManager.getSqProjectInfo().getProjectName());
			    }int icount = 1;
			    for(int j=i ; j<workViewList.size() ; j++){
			    	SqWorkdayManager sqWorkdayManagerTemp = (SqWorkdayManager)workViewList.get(j);
			    	if(temp.equals(sqWorkdayManagerTemp.getSqProjectInfo().getProjectId())){
			    		strBuff.append(Public.mapInt.get(icount++) + "、" + sqWorkdayManagerTemp.getDiscussProblem()+"\n");
			    		i = j ;
			    	}
			    }
			    discussProblemTemp.add(strBuff.toString());
  		    	discussProblemList.add(discussProblemTemp);
  		  }
	    
	    //项目人数
	    strBuff = new StringBuffer();
	    int userCout = 0 ;
	    List<String> userListTemp = new ArrayList(); 
	    for(int i=0 ; i<workViewList.size() ; i++){
    		SqWorkdayManager sqWorkdayManager = (SqWorkdayManager)workViewList.get(i);
    		if(sqWorkdayManager.getRemark1() !=null) {
    			String [] userTemp =  sqWorkdayManager.getRemark1().split("   ");
    			for(int j=0 ; j<userTemp.length ; j++){
    				if(strBuff. indexOf(userTemp[j]) == -1 ){
    					strBuff.append(userTemp[j] + "     ");
    					userCout ++ ;
    				}
    			}
    		}
    	}
	    userListTemp.add(userCout+"");
	    userListTemp.add(strBuff.toString());
	    userList.add(userListTemp);
	    
	    //工作建议
	    count1 = 0 ;
	    temp = "";	//记录项目编号
	    for(int i=0 ; i<workViewList.size() ; i++){
	    	strBuff = new StringBuffer();
	    	List<String> workSugTemp = new ArrayList();
		   SqWorkdayManager sqWorkdayManager = (SqWorkdayManager)workViewList.get(i);
		   if(sqWorkdayManager.getWorkSug()!=null && !sqWorkdayManager.getWorkSug().equals("")){
			   strBuff.append(Public.mapInt.get(++count1) + "、" + sqWorkdayManager.getWorkSug()+"\n");
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
		String fileName = "项目周报"+this.getStartDate()+"-"+this.getEndDate()+"("+this.getUserName()+").doc";
		try {
			//项目周报
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
			int tempCount = 2 ;
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
			tempCount = 3+tempCount+this.getWorkContentList().size() ;
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
			//人数
			tempCount = 3+tempCount+this.getDiscussProblemList().size() ;
			for (int i = this.getUserList().size()-1; i >=0; i--) {
				List temp = (List)this.getUserList().get(i);
				for (int j = 0; j < temp.size(); j++) {
					//在指定的单元格中填写数据
					msWordManager1.putTxtToCell(tableIndex, tempCount, 1+j, (String)temp.get(j));
				}
			}
			
			//工作建议
			tempCount = 1+tempCount+this.getDiscussProblemList().size() ;
			for (int i = this.getWorkSugList().size()-1; i >=0; i--) {
				List temp = (List)this.getWorkSugList().get(i);
				for (int j = 0; j < temp.size(); j++) {
					//在指定的单元格中填写数据
					msWordManager1.putTxtToCell(tableIndex, tempCount, 3+j, (String)temp.get(j));
				}
			}
			
			//开始合并单元格(行)
			tempCount = 1 ;
			
			//未完成工作情况说明
			tempCount = 3+tempCount+this.getWorkContentList().size() ;
			msWordManager1.mergeCell(tableIndex, tempCount, 4, tempCount, msWordManager1.getTableColsCount(tableIndex));
			msWordManager1.putTxtToCell(tableIndex,tempCount,4,"未完成工作情况说明");
			for (int i = 0; i < this.getNoWorkContentList().size(); i++) {
				String content = msWordManager1.getContent(tableIndex, tempCount+1+i, 4);
				msWordManager1.mergeCell(tableIndex, tempCount+1+i, 4, tempCount+1+i, msWordManager1.getTableColsCount(tableIndex));
				msWordManager1.putTxtToCell(tableIndex,tempCount+1+i,4,content);
			}
			
			//下周计划
			tempCount = 3+tempCount+this.getNoWorkContentList().size() ;
			msWordManager1.mergeCell(tableIndex, tempCount-2, 4, tempCount-2, msWordManager1.getTableColsCount(tableIndex));
			msWordManager1.putTxtToCell(tableIndex,tempCount-2,4,"");
			msWordManager1.mergeCell(tableIndex, tempCount-1, 4, tempCount-1, msWordManager1.getTableColsCount(tableIndex));
			msWordManager1.putTxtToCell(tableIndex,tempCount-1,4,"");
			msWordManager1.mergeCell(tableIndex, tempCount, 4, tempCount, msWordManager1.getTableColsCount(tableIndex));
			msWordManager1.putTxtToCell(tableIndex,tempCount,4,"下周计划");
			for (int i = 0; i < this.getWorkNextPlanList().size(); i++) {
				String content = msWordManager1.getContent(tableIndex, tempCount+1+i, 4);
				msWordManager1.mergeCell(tableIndex, tempCount+1+i, 4, tempCount+1+i, msWordManager1.getTableColsCount(tableIndex));
				msWordManager1.putTxtToCell(tableIndex,tempCount+1+i,4,content);
			}
			
			//待解决问题说明
			tempCount = 3+tempCount+this.getWorkNextPlanList().size() ;
			msWordManager1.mergeCell(tableIndex, tempCount-2, 4, tempCount-2, msWordManager1.getTableColsCount(tableIndex));
			msWordManager1.putTxtToCell(tableIndex,tempCount-2,4,"");
			msWordManager1.mergeCell(tableIndex, tempCount-1, 4, tempCount-1, msWordManager1.getTableColsCount(tableIndex));
			msWordManager1.putTxtToCell(tableIndex,tempCount-1,4,"");
			msWordManager1.mergeCell(tableIndex, tempCount, 4, tempCount, msWordManager1.getTableColsCount(tableIndex));
			msWordManager1.putTxtToCell(tableIndex,tempCount,4,"需协调解决的问题");
			for (int i = 0; i < this.getDiscussProblemList().size(); i++) {
				String content = msWordManager1.getContent(tableIndex, tempCount+1+i, 4);
				msWordManager1.mergeCell(tableIndex, tempCount+1+i, 4, tempCount+1+i, msWordManager1.getTableColsCount(tableIndex));
				msWordManager1.putTxtToCell(tableIndex,tempCount+1+i,4,content);
			}
			//项目参与人员
			tempCount = 3+tempCount+this.getDiscussProblemList().size() ;
			msWordManager1.mergeCell(tableIndex, tempCount-2, 4, tempCount-2, msWordManager1.getTableColsCount(tableIndex));
			msWordManager1.putTxtToCell(tableIndex,tempCount-2,4,"");
			msWordManager1.mergeCell(tableIndex, tempCount-1, 4, tempCount-1, msWordManager1.getTableColsCount(tableIndex));
			msWordManager1.putTxtToCell(tableIndex,tempCount-1,4,"");
			msWordManager1.mergeCell(tableIndex, tempCount, 2, tempCount, msWordManager1.getTableColsCount(tableIndex));
			msWordManager1.putTxtToCell(tableIndex,tempCount,2,"名单");
			String content = msWordManager1.getContent(tableIndex, tempCount+1, 2);
			msWordManager1.mergeCell(tableIndex, tempCount+1, 2, tempCount+1, msWordManager1.getTableColsCount(tableIndex));
			msWordManager1.putTxtToCell(tableIndex,tempCount+1,2,content);
			
			//工作建议
			tempCount = 2+tempCount ;
			msWordManager1.mergeCell(tableIndex, tempCount, 1, tempCount, 2);
			msWordManager1.putTxtToCell(tableIndex,tempCount,1,"其它事项说明和建议");
			content = msWordManager1.getContent(tableIndex, tempCount, 2);
			msWordManager1.mergeCell(tableIndex, tempCount, 2, tempCount, msWordManager1.getTableColsCount(tableIndex)-1);
			msWordManager1.putTxtToCell(tableIndex,tempCount,2,content);
			
			//开始合并单元格(列)
			//本周工作计划
			int workContentSize = 6+this.getWorkContentList().size()+this.getNoWorkContentList().size() ;
			msWordManager1.mergeCell(tableIndex, 1, 1, workContentSize, 1);
			//下周工作计划
			int nextWorkContentSize = 6+workContentSize+this.getWorkNextPlanList().size()+this.getDiscussProblemList().size();
			msWordManager1.mergeCell(tableIndex,  workContentSize+1, 1, nextWorkContentSize, 1);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			msWordManager1.closeDocument();
			msWordManager.close();
			msWordManager1.close();
			ComThread.Release();
		}
		return fileName;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public List getWorkContentList() {
		return workContentList;
	}

	public List getNoWorkContentList() {
		return noWorkContentList;
	}

	public List getWorkNextPlanList() {
		return workNextPlanList;
	}

	public List getDiscussProblemList() {
		return discussProblemList;
	}

	public List getWorkSugList() {
		return workSugList;
	}

	public List getUserList() {
		return userList;
	}

	public String getUserName() {
		return userName;
	}

	public String getGroupName() {
		return groupName;
	}
	
}
