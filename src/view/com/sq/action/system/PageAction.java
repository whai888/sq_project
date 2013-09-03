package com.sq.action.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.sq.action.workday.DeptWorkDay;
import com.sq.action.workday.GroupWorkDay;
import com.sq.action.workday.MyWorkDay;
import com.sq.action.workday.ProjectWorkDay;
import com.sq.exception.SystemException;
import com.sq.logic.system.IPageService;
import com.sq.logic.workday.impl.WorkDayService;
import com.sq.model.vo.SqDocmentManager;
import com.sq.model.vo.SqUserTab;
import com.sq.model.vo.SqWorkdayManager;
import com.sq.tools.Constant;
import com.sq.tools.Public;
import com.sq.tools.WordBean;

public class PageAction {
	@Resource
	private IPageService iPageService;
	private List listData = new ArrayList();			//页面上需要显示的数据
	private String pageTag ;		//分页标签
	private String currentStr = "1";		//从第几页开始
	private int totalAcount ;		//总的记录数
	public File fileName ;
	public String fileNameFileName; // 上传文件名
	private String filenameInCN;
	private String fileNameCN;
	private String revision;
	public String directory ;
	public String targetFileName ;
	private String projectId ;

	private Logger log = Logger.getLogger(PageAction.class);
	private HttpServletRequest request = ServletActionContext.getRequest();
	private HttpSession session = request.getSession();

	public String execute() {
		Map reqMap = new HashMap();

		// 获取相关的属性名
		Enumeration eum = request.getParameterNames();
		// 循环迭代属性名
		while (eum.hasMoreElements()) {
			String name = (String) eum.nextElement();
			if(name.equals("MATHRANDOMNUM"))
				continue ;
			reqMap.put(name, request.getParameter(name));
		}
		try {
			this.listData = iPageService.findAllList(reqMap , Integer.parseInt(currentStr));
			this.totalAcount = iPageService.findAllCount(reqMap);
			this.getHtmlPageTag() ;
		} catch (Exception e) {
			log.info("标签加载错误" + e.getMessage());
			e.printStackTrace();
		}
		return "page";
	}
	
	public void executeHql(String hql ,String [] paramNames ,Object [] values , int currentStr){
		try {
			//根据指定的信息查询
			this.listData = iPageService.findAllList(hql , paramNames ,values ,currentStr );
			this.totalAcount = iPageService.findAllCount(hql , paramNames ,values);
			this.getHtmlPageTag() ;
		} catch (Exception e) {
			log.info("标签加载错误" + e.getMessage());
			e.printStackTrace();
		}
	}
	/**
	 * 根据给定的HQL语句执行
	 * @return
	 */
	public String ajaxHql(){
		Map reqMap = new HashMap();

		// 获取相关的属性名
		Enumeration eum = request.getParameterNames();
		// 循环迭代属性名
		while (eum.hasMoreElements()) {
			String name = (String) eum.nextElement();
			reqMap.put(name, request.getParameter(name));
		}
		try {
			this.listData = iPageService.findAllList(reqMap , Integer.parseInt(currentStr));
			this.totalAcount = iPageService.findAllCount(reqMap);
		} catch (Exception e) {
			log.info("标签加载错误" + e.getMessage());
			e.printStackTrace();
		}
		return "ajax" ;
	}
	
	public void setChanagePageTab(List listWeek){
		this.totalAcount = listWeek.size();
	}
	
	public void getHtmlPageTag(){
		StringBuffer strBuff = new StringBuffer();
		int totalPage = 1 ;
		if(this.totalAcount == 0) {
				totalPage = 1 ;
		} 
		else {
			totalPage = (this.totalAcount + Constant.PAGE_COUNT - 1) / Constant.PAGE_COUNT ;
		}
		strBuff.append(" <table width='100%' border='0' cellspacing='0' cellpadding='0'> " + 
				" <tr> " +
				" <td width='15%' height='29' nowrap='nowrap'>" + 
				" <table width='242' border='0' cellspacing='0' cellpadding='0'>" + 
				" <tr>" + 
				" <td width='44%'>当前页："+currentStr+"/"+totalPage+"页</td>" + 
				" <td><span >数据总量"+this.totalAcount+" </span></td>" + 
				" </tr>" + 
				" </table></td>" + 
				" <td width='75%' valign='top'><div align='right'>" + 
				" <table width='352' height='20' border='0' cellpadding='0' cellspacing='0'>" + 
				" <tr>" +
				" <td width='62' height='22' valign='middle'><div align='right'><a class='form_font' onclick=\"onSub('1')\"><img src='../images/page_first_1.gif' width='48' height='20'/></a></div></td>" + 
				" <td width='50' height='22' valign='middle'><div align='right'>");
				//如果当前页为第一页  则没有上一页
				if(currentStr.equals("1")){
					strBuff.append("<img src='../images/page_back_1.gif' width='55' height='20' />");
				}else{
					strBuff.append("<a class='form_font'  onclick=\"onSub('"+(Integer.parseInt(currentStr) -1 )+"')\"><img src='../images/page_back_1.gif' width='55' height='20' /></a>");
				}
				//如果当前页为最后一页  则没有下一页
				if(currentStr.equals(totalPage + "")){
					strBuff.append("</div></td><td width='54' height='22' valign='middle'><div align='right'><img src='../images/page_next.gif' width='58' height='20' /></div></td>");
				}else{
					strBuff.append("</div></td><td width='54' height='22' valign='middle'><div align='right'><a class='form_font'  onclick=\"onSub('"+(Integer.parseInt(currentStr) + 1 )+"')\"><img src='../images/page_next.gif' width='58' height='20' /></a></div></td>");
				}
				strBuff.append("<td width='49' height='22' valign='middle'><div align='right'><a class='form_font'  onclick=\"onSub('"+(this.totalAcount + Constant.PAGE_COUNT - 1) / Constant.PAGE_COUNT+"')\"><img src='../images/page_last.gif' width='52' height='20' /></a></div></td>" +
				" <td width='59' height='22' valign='middle'><div align='right'>转到第></div></td>" + 
				" <td width='25' height='22' valign='middle'>" + 
				"  <input name='currentPage' id='currentPage' type='text' style='height:13px; width:25px;' size='5' />" + 
				" </td>" + 
				" <td width='23' height='22' valign='middle'>页</td>" + 
				" <td width='30' height='22' valign='middle'><img src='../images/go.gif' width='26' height='20' onclick=\"onSub('-1')\" /></td>" + 
				" </tr>" + 
				" </table>" + 
				" </div></td>" + 
				" </tr>" + 
				" </table> ");
		this.pageTag = strBuff.toString() ;
	}

	/**
	 * 需要将文件进行上传，其中sqDocmentManager的 getProjectId项目编号必须有值
	 * @param sqDocmentManager
	 * @throws SystemException
	 */
	public void projectDocUpload(SqDocmentManager sqDocmentManager) throws SystemException{
		if(fileNameFileName!=null){
			try {
				String sysDate = Public.getSystemTimeToFormat("yyyy-MM-dd");
				//得到当期时间自1970年1月1日0时0分0秒开始流逝的毫秒数，将这个毫秒数作为上传文件新的文件名
				long now = new Date().getTime();
				//得到保存上传文件的目录的真实路径   upload + 项目编号
				String targetDirectory = ServletActionContext.getServletContext().getRealPath(File.separator + "upload" + File.separator + sqDocmentManager.getProjectId() + File.separator);
				this.directory = File.separator + "upload" + File.separator + sqDocmentManager.getProjectId() + File.separator;
				//将上传上的文件名修改为本地文件名  本地文件为now+文件扩展名
				this.targetFileName = now + fileNameFileName.substring(fileNameFileName.lastIndexOf("."));
				File target = new File(targetDirectory, targetFileName);
				//如果目录没有，则新建
	//			if(!target.exists()){
	//				target.mkdir();
	//			}
				//将fileName 拷贝给target
				FileUtils.copyFile(fileName, target);
				
			} catch (Exception e) {
				throw new SystemException(PageAction.class ,"文件上传失败，只允许上传doc、excel、zip、pdf、txt  " + e.getMessage());
			}
		}
	}
	public String getFilenameInCN() {  
        return filenameInCN;  
    }  
   
    public void setFilenameInCN(String filenameInCN) {  
        try { 
        	if(filenameInCN !=null )
        		this.filenameInCN = new String(filenameInCN.getBytes(), "ISO-8859-1");  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
   
    public InputStream getDownloadFile() {
       //找到相关文件返回流 
    	if(fileNameCN!=null){
	    	try {
				String targetDirectory = ServletActionContext.getServletContext().getRealPath(File.separator + "upload" + File.separator+ projectId );
				return new FileInputStream(targetDirectory + File.separator+ fileNameCN );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
		return null;
    } 
    
    /**
     * 日报文件下载
     * @return
     */
    public InputStream getWorkDayDownloadFile() {
        //找到相关文件返回流 
    	String returnPage = request.getParameter("returnPage");
    	if(returnPage!=null){
	    	String filePath = ServletActionContext.getServletContext().getRealPath(File.separator + "upload");
	    	
	    	String fileName = "" ;
			if(returnPage.equals("9997")){
				//员工周报
				List<SqWorkdayManager> workDayList = (List)session.getAttribute("myWorkDayViewList");
				MyWorkDay myWorkDay = new MyWorkDay();
				myWorkDay.workDay(workDayList);
				fileName = myWorkDay.workDayExport(filePath+File.separator +"fml"+File.separator+"myworkday.doc" , filePath+File.separator+returnPage+File.separator);
				
			}else if(returnPage.equals("9999")){
				//部门周报
				Map deptWorkMap = (Map)session.getAttribute("myWorkDayViewList");
				//用户名
				String userName = request.getParameter("filenameInCN");
				//项目组名称
				String deptName = request.getParameter("filenameCN");
				DeptWorkDay deptWorkDay = new DeptWorkDay();
				deptWorkDay.workDay(deptWorkMap,userName,deptName);
				fileName = deptWorkDay.workDayExport(deptWorkMap , filePath+File.separator +"fml"+File.separator+"deptworkday.doc" , filePath+File.separator+returnPage+File.separator);

			}else if(returnPage.equals("9998")){
				//项目组周报
				List<SqWorkdayManager> workDayList = (List)session.getAttribute("myWorkDayViewList");
				//周例会
				SqDocmentManager sqDocmentManager = (SqDocmentManager)session.getAttribute("sqDocmentManager");
				//用户名
				String userName = request.getParameter("filenameInCN");
				//项目组名称
				String groupName = request.getParameter("filenameCN");
				GroupWorkDay groupWorkDay = new GroupWorkDay();
				groupWorkDay.workDay(workDayList,userName,groupName);
				fileName = groupWorkDay.workDayExport(filePath+File.separator +"fml"+File.separator+"groupworkday.doc" , filePath+File.separator+returnPage+File.separator , sqDocmentManager);
			}else if(returnPage.equals("9996")){
				//项目周报
				List<SqWorkdayManager> workDayList = (List)session.getAttribute("myWorkDayViewList");
				//用户名
				String userName = request.getParameter("filenameInCN");
				//项目组名称
				String groupName = request.getParameter("filenameCN");
				ProjectWorkDay projectWorkDay = new ProjectWorkDay();
				projectWorkDay.workDay(workDayList,userName,groupName);
				fileName = projectWorkDay.workDayExport(filePath+File.separator +"fml"+File.separator+"projectworkday.doc" , filePath+File.separator+returnPage+File.separator);
			}
			this.setFilenameInCN(fileName);
			fileNameCN = fileName;
	     	if(fileNameCN!=null){
	 	    	try {
	 				String targetDirectory = filePath + File.separator+returnPage+File.separator ;
	 				return new FileInputStream(targetDirectory + fileName);
	 			} catch (Exception e) {
	 				// TODO Auto-generated catch block
	 				e.printStackTrace();
	 			}
	     	}
    	}
 		return null;
     }

  
    public String getFileNameCN() {
		return fileNameCN;
	}

	public void setFileNameCN(String fileNameCN) {
		this.fileNameCN = fileNameCN;
	}

	public String getRevision() {  
        return revision;  
    }  
  
    public void setRevision(String revision) {  
        this.revision = revision;  
    }  

	public List getListData() {
		return listData;
	}

	public void setListData(List listData) {
		this.listData = listData;
	}

	public String getPageTag() {
		return pageTag;
	}

	public void setPageTag(String pageTag) {
		this.pageTag = pageTag;
	}

	public String getCurrentStr() {
		return currentStr;
	}

	public void setCurrentStr(String currentStr) {
		this.currentStr = currentStr;
	}

	public File getFileName() {
		return fileName;
	}

	public void setFileName(File fileName) {
		this.fileName = fileName;
	}

	public String getFileNameFileName() {
		return fileNameFileName;
	}

	public void setFileNameFileName(String fileNameFileName) {
		this.fileNameFileName = fileNameFileName;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

}
