package com.sq.action.workday;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jacob.com.ComThread;
import com.sq.action.system.LoginAction;
import com.sq.model.vo.SqGroupTab;
import com.sq.model.vo.SqWorkdayManager;
import com.sq.tools.Public;
import com.sq.tools.WordBean;

/**
 *
 *@autor whai
 */
public class DeptWorkDay {
	private String userName ;
	private String deptName = "" ;
	private String startDate = "";
	private String endDate="";
	private Logger log = Logger.getLogger(DeptWorkDay.class);
	
	public void workDay(Map deptWorkMap,String userName,String deptName){
		this.userName = userName ;
		this.deptName = deptName ;
		Iterator it = deptWorkMap.entrySet().iterator();
		while (it.hasNext()) {
	          Map.Entry entry = (Map.Entry) it.next();
	          List workDayList = (List)entry.getValue();
	          for(int i=0 ; i<workDayList.size() ; i++){
	    		SqWorkdayManager sqWorkdayManager = (SqWorkdayManager)workDayList.get(i);
	    		
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
		System.out.println("====================" + startDate + "       " + endDate);
	}

	public String workDayExport(Map deptWorkMap,String fmlFileName , String filePath) {
		ComThread.InitSTA();
		int tableIndex = 1 ;
		int count = 1;
		WordBean msWordManager = new WordBean();
		WordBean msWordManager1 = new WordBean();
		String fileName = this.getDeptName() + "周报"+this.getStartDate()+"-"+this.getEndDate()+"("+this.getUserName()+").doc";
		try {
			//项目组周报
			msWordManager.openDocument(fmlFileName);
			msWordManager.save(filePath + fileName);
			msWordManager.closeDocument();
			
			msWordManager1.openDocument(filePath + fileName);
			//开始进行文本替换
			//所属部门
			msWordManager1.replaceAllText("$DEPTNAME$", this.getDeptName());
			//编写人姓名
			msWordManager1.replaceAllText("$USERNAME$", this.getUserName());
			//编写日期
			msWordManager1.replaceAllText("$DATE$", this.getStartDate()+"至"+ this.getEndDate());
			
			Iterator it = deptWorkMap.entrySet().iterator();
			it = deptWorkMap.entrySet().iterator();
			while (it.hasNext()) {
				int userCout = 0;
				int tempCount = 1 ;
				int contentSize = 0 ;	//插入周报内容的个数
				int dissSize = 0 ; 		//需协调解决的问题
				int workSubSize = 0;	//其它事项或建议
				StringBuffer userName = new StringBuffer("  ") ;
				Map.Entry entry = (Map.Entry) it.next();
				SqGroupTab sqGroupTab = (SqGroupTab)entry.getKey();
				List workDayList = (List)entry.getValue();
		          
		          //设置项目组表格标题
//				msWordManager1.putTxtToCell(tableIndex, tempCount, 2, sqGroupTab.getGroupName());
		          
		          //本周工作小结内容
		         tempCount = 3 + tempCount ;
		      	for(int i=0 ; i<workDayList.size() ; i++){
		      		SqWorkdayManager sqWorkdayManager = (SqWorkdayManager)workDayList.get(i);
		      		if(sqWorkdayManager.getWorkContent()!=null && !sqWorkdayManager.getWorkContent().equals("")){
		      			msWordManager1.addTableRow(tableIndex, tempCount+contentSize);
		      			msWordManager1.putTxtToCell(tableIndex, tempCount+contentSize, 1, Public.mapInt.get(i+1)+"");
		      			msWordManager1.putTxtToCell(tableIndex, tempCount+contentSize, 2, sqWorkdayManager.getWorkContent()+"\n");
		      			contentSize++;
		      		}
		      	}
		      	
		      	//需协调解决的问题
		      	tempCount = 3 + tempCount + contentSize ;
		    	for(int i=0 ; i<workDayList.size() ; i++){
		    		SqWorkdayManager sqWorkdayManager = (SqWorkdayManager)workDayList.get(i);
		    		if(sqWorkdayManager.getDiscussProblem()!=null && !sqWorkdayManager.getDiscussProblem().equals("")){
		    			msWordManager1.addTableRow(tableIndex, tempCount+dissSize);
		      			msWordManager1.putTxtToCell(tableIndex, tempCount+dissSize, 1, Public.mapInt.get(i+1)+"");
		      			msWordManager1.putTxtToCell(tableIndex, tempCount+dissSize, 2, sqWorkdayManager.getDiscussProblem()+"\n");
		      			dissSize++;
		    		}
		    	}
		      	
		    	//工作事项和建议
		    	tempCount = 3 + tempCount + dissSize ;
		    	for(int i=0 ; i<workDayList.size() ; i++){
		    		SqWorkdayManager sqWorkdayManager = (SqWorkdayManager)workDayList.get(i);
		    		if(sqWorkdayManager.getWorkSug()!=null && !sqWorkdayManager.getWorkSug().equals("")){
		    			msWordManager1.addTableRow(tableIndex, tempCount+workSubSize);
		      			msWordManager1.putTxtToCell(tableIndex, tempCount+workSubSize, 1, Public.mapInt.get(i+1)+"");
		      			msWordManager1.putTxtToCell(tableIndex, tempCount+workSubSize, 2, sqWorkdayManager.getWorkSug()+"\n");
		      			workSubSize++;
		    		}
		    	}
		    	
		    	//项目组人数
		    	tempCount = 3 + tempCount + workSubSize ;
		    	for(int i=0 ; i<workDayList.size() ; i++){
		       		SqWorkdayManager sqWorkdayManager = (SqWorkdayManager)workDayList.get(i);
		       		if(sqWorkdayManager.getRemark1() !=null) {
		       			String [] userTemp =  sqWorkdayManager.getRemark1().split("   ");
		       			for(int j=0 ; j<userTemp.length ; j++){
		       				if(userName. indexOf(userTemp[j]) == -1 ){
		       					userName.append(userTemp[j] + "   ");
		       					userCout ++ ;
		       				}
		       			}
		       		}
		       	}
		    	msWordManager1.putTxtToCell(tableIndex, tempCount+workSubSize, 1, userCout+"");
      			msWordManager1.putTxtToCell(tableIndex, tempCount+workSubSize, 2, userName.toString());
		    	
      			//开始对表格进行处理
      			tempCount = 1 ;
      			//表格头
      			msWordManager1.mergeCell(tableIndex, tempCount, 1, tempCount, msWordManager1.getTableColsCount(tableIndex));
      			msWordManager1.putTxtToCell(tableIndex, tempCount, 1, sqGroupTab.getGroupName());
      			//工作小结
      			tempCount = 1+tempCount ;
      			msWordManager1.mergeCell(tableIndex, tempCount, 1, tempCount, msWordManager1.getTableColsCount(tableIndex));
      			msWordManager1.putTxtToCell(tableIndex, tempCount, 1, "一、工作小结");
      			//需协调解决的事项
      			tempCount = 3+tempCount+ contentSize;
      			msWordManager1.mergeCell(tableIndex, tempCount, 1, tempCount, msWordManager1.getTableColsCount(tableIndex));
      			msWordManager1.putTxtToCell(tableIndex, tempCount, 1, "二、需要协调事项");
      			//其它事项或建议
      			tempCount = 3+tempCount+ dissSize;
      			msWordManager1.mergeCell(tableIndex, tempCount, 1, tempCount, msWordManager1.getTableColsCount(tableIndex));
      			msWordManager1.putTxtToCell(tableIndex, tempCount, 1, "三、其他事项或建议");
      			//项目组成员
      			tempCount = 3+tempCount+ workSubSize;
      			msWordManager1.mergeCell(tableIndex, tempCount, 1, tempCount, msWordManager1.getTableColsCount(tableIndex));
      			msWordManager1.putTxtToCell(tableIndex, tempCount, 1, "四、项目组成员");
      			
      			if(tableIndex < deptWorkMap.size()){
	      			//将表格进行拷贝
	      			msWordManager1.moveDown(6);
	      			msWordManager1.insertText("$SPLIT$");
	      			msWordManager1.moveStart();
	      			msWordManager1.replaceAllText("$SPLIT$", "$SPLIT$"+"\r\n\r\n\r\n");
	      			msWordManager1.moveStart();
	      			msWordManager1.copyTableFromAnotherDoc(fmlFileName, 1, "$SPLIT$");
      			}
		      	//将表格加1
		      	tableIndex ++ ;
			}
			
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

	public String getUserName() {
		return userName;
	}

	public String getDeptName() {
		return deptName;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}
	
}
