package com.sq.action.home;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.sq.action.system.PageAction;
import com.sq.exception.SystemException;
import com.sq.logic.home.ISystemHomeService;
import com.sq.logic.tools.IUserLog;
import com.sq.logic.user.IUserService;
import com.sq.model.vo.SqArticleManager;
import com.sq.model.vo.SqUserTab;
import com.sq.tools.PubCheckVal;
import com.sq.tools.Public;
import com.sq.vo.ErrorForm;

/**
 *
 *@autor whai
 */
public class SystemHomeAction  extends PageAction{

	@Resource
	private ISystemHomeService iSystemHomeService;
	@Resource
	private IUserService iUserService;
	@Resource
	private IUserLog iUserLog;
	private ErrorForm errorForm = new ErrorForm();
	private SqArticleManager sqArticleManager ;
	private HttpServletRequest request = ServletActionContext.getRequest();
	private HttpSession session = request.getSession(false);
	/**
	 * 系统通知查询
	 * @return
	 */
	public String findSystemNotify(){
		//查询条件的标志
		String flag = request.getParameter("FLAG");
		if(PubCheckVal.isNull(flag)){
			flag = "0" ;
		}
		String content = request.getParameter("CONTENT") ;
		StringBuffer hql = new StringBuffer();
		String [] paramNames = new String[1];
		Object[] values = new Object[1] ;
		
		hql.append( "from SqArticleManager sqArticleManager where sqArticleManager.status='0' and sqArticleManager.plate='1' " );
		if(flag.equals("1") && !Public.isEmpty(content)){
			//根据发布日期查询
			hql.append("and sqArticleManager.publishDate = :startDate ");
			paramNames[0] = "startDate" ;
			values[0] = Public.getStringToDate(content, "yyyy-MM-dd");
		}else if(flag.equals("2") && !Public.isEmpty(content)){
			//根据标题查询
			hql.append("and sqArticleManager.artTitle like '%'||:content||'%'");
			paramNames[0] = "content" ;
			values[0] = content;
		}else if(flag.equals("3")&& !Public.isEmpty(content)){
			//根据发布人查询
			hql.append("and sqArticleManager.publishUserId = (select sqUserTab.userId from SqUserTab sqUserTab where sqUserTab.userName=:userName) ");
			paramNames[0] = "userName" ;
			values[0] = content;
		}else{
			//查询前3个月的系统通知内容
			hql.append("and sqArticleManager.publishDate >= :startDate ");
			paramNames[0] = "startDate" ;
			values[0] = Public.getDateToAddRed(null, "yyyy-MM-dd" , 3*60);
		}
		hql.append(" order by sqArticleManager.artId desc") ;
		
		this.executeHql(hql.toString(), paramNames, values, Integer.parseInt(this.getCurrentStr())) ;
		
		request.setAttribute("sqArticleManagerList", this.getListData()) ;
		
		//将得到的数据进行解析
//		try {
//			List sqArticleManagerList = new ArrayList();
//			for (int i = 0; i < this.getListData().size(); i++) {
//				SqArticleManager temp = (SqArticleManager)this.getListData().get(i);
//				//加载员工信息
//				SqUserTab sqUserTab = iUserService.findById(temp.getPublishUserId());
//				temp.setPublishUser(sqUserTab);
//				sqArticleManagerList.add(temp) ;
//			}
//			request.setAttribute("sqArticleManagerList", sqArticleManagerList) ;
//		} catch (SystemException e) {
//			iUserLog.logwrite(request, e);
//			errorForm.setMessage(e.getMessage());
//			return "error";
//		}
		return "systemnotify";
	}
	
	/**
	 * 成长心路查询
	 * @return
	 */
	public String findGroupMind(){
		//查询条件的标志
		StringBuffer hql = new StringBuffer();
		String [] paramNames = new String[1];
		Object[] values = new Object[1] ;
		
		hql.append( "from SqArticleManager sqArticleManager where sqArticleManager.status='0' and sqArticleManager.plate='2' " );
		//根据发布日期查询
		hql.append("and sqArticleManager.publishDate >= :startDate ");
		paramNames[0] = "startDate" ;
		values[0] = Public.getDateToAddRed(null, "yyyy-MM-dd" , 3*30);
		
		hql.append(" order by sqArticleManager.publishDate desc") ;
		
		this.executeHql(hql.toString(), paramNames, values, Integer.parseInt(this.getCurrentStr())) ;
		request.setAttribute("sqArticleManagerList", this.getListData()) ;
		//将得到的数据进行解析
//		try {
//			List sqArticleManagerList = new ArrayList();
//			for (int i = 0; i < this.getListData().size(); i++) {
//				SqArticleManager temp = (SqArticleManager)this.getListData().get(i);
//				//加载员工信息
//				SqUserTab sqUserTab = iUserService.findById(temp.getPublishUserId());
//				temp.setPublishUser(sqUserTab);
//				sqArticleManagerList.add(temp) ;
//			}
//			request.setAttribute("sqArticleManagerList", this.getListData().size()) ;
//		} catch (SystemException e) {
//			iUserLog.logwrite(request, e);
//			errorForm.setMessage(e.getMessage());
//			return "error";
//		}
		
		
//		try {
//			//从最近的10篇文章中选取一篇
//			List artGroupMindList = iSystemHomeService.findArticleList("2");
//			Random random = new Random();
//			//nextInt(n)  返回一个伪随机数，它是取自此随机数生成器序列的、在 0（包括）和指定值（不包括）之间均匀分布的 int 值。
//			//如果可以查询到值，则取第一条，否则赋值为空
//			if(artGroupMindList.size()!=0)
//				sqArticleManager = (SqArticleManager)artGroupMindList.get(random.nextInt(artGroupMindList.size()));
//			else{
//				sqArticleManager = new SqArticleManager();
//			}
//		} catch (SystemException e) {
//			iUserLog.logwrite(request, e);
//			errorForm.setMessage(e.getMessage());
//			return "error";
//		}
		return "groupupmind" ;
	}
	/**
	 * 开心一刻查询
	 * @return
	 */
	public String findHappyMoment(){
		try {
			//从最近的10篇文章中选取一篇
			List artGroupMindList = iSystemHomeService.findArticleList("3");
			Random random = new Random();
			//nextInt(n)  返回一个伪随机数，它是取自此随机数生成器序列的、在 0（包括）和指定值（不包括）之间均匀分布的 int 值。
			//如果可以查询到值，则取第一条，否则赋值为空
			if(artGroupMindList.size()!=0)
				sqArticleManager = (SqArticleManager)artGroupMindList.get(random.nextInt(artGroupMindList.size()));
			else{
				sqArticleManager = new SqArticleManager();
			}
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "happymoment";
	}
	
	/**
	 * 根据文章编号查询文章内容
	 * @return
	 */
	public String findArtIdForContent(){
		String returnPage = request.getParameter("returnPage");
		try {
			sqArticleManager = iSystemHomeService.findArtIdForContent(sqArticleManager);
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return returnPage;
	}
	/**
	 * 我要投稿提交
	 * @return
	 */
	public String iWantSubmisEdit(){
		SqUserTab sqUserTab = (SqUserTab) session.getAttribute("sqUserTab");
		iSystemHomeService.iWantSubSave(sqArticleManager, sqUserTab);
		return "success" ;
	}

	/**
	 * 投稿文章审核查询
	 * @return
	 */
	public String artAuditQuery(){
		//查询条件的标志
		String flag = request.getParameter("FLAG");
		if(PubCheckVal.isNull(flag)){
			flag = "0" ;
		}
		String content = request.getParameter("CONTENT") ;
		StringBuffer hql = new StringBuffer();
		String [] paramNames = new String[1];
		Object[] values = new Object[1] ;
		
		hql.append( "from SqArticleManager sqArticleManager where sqArticleManager.status='9' " );
		if(flag.equals("1")){
			//根据撰写日期查询
			hql.append("and sqArticleManager.delivefyDate >= :startDate ");
			paramNames[0] = "startDate" ;
			values[0] = Public.getStringToDate(content, "yyyy-MM-dd");
		}else if(flag.equals("2")){
			//根据标题查询
			hql.append("and sqArticleManager.artTitle like '%'||:content||'%' ");
			paramNames[0] = "content" ;
			values[0] = content;
		}else if(flag.equals("3")){
			//根据撰写人查询
			hql.append("and sqArticleManager.delivefyUserId = (select sqUserTab.userId from SqUserTab sqUserTab where sqUserTab.userName=:userName) ");
			paramNames[0] = "userName" ;
			values[0] = content;
		}else{
			//查询前3个月的系统通知内容
			hql.append("and sqArticleManager.delivefyDate >= :startDate ");
			paramNames[0] = "startDate" ;
			values[0] = Public.getDateToAddRed(null, "yyyy-MM-dd" , 3*60);
		}
		hql.append("order by sqArticleManager.delivefyDate desc") ;
		
		this.executeHql(hql.toString(), paramNames, values, Integer.parseInt(this.getCurrentStr())) ;
		
		request.setAttribute("sqArticleManagerList", this.getListData()) ;
		//将得到的数据进行解析
//		try {
//			List sqArticleManagerList = new ArrayList();
//			for (int i = 0; i < this.getListData().size(); i++) {
//				SqArticleManager temp = (SqArticleManager)this.getListData().get(i);
//				//加载投稿员工信息
//				SqUserTab sqUserTab = iUserService.findById(temp.getDelivefyUser());
//				temp.setDelivefyUser(sqUserTab);
//				sqArticleManagerList.add(temp) ;
//			}
//			request.setAttribute("sqArticleManagerList", this.getListData().size()) ;
//		} catch (SystemException e) {
//			iUserLog.logwrite(request, e);
//			errorForm.setMessage(e.getMessage());
//			return "error";
//		}
		return "artauditquery" ;
	}
	
	/**
	 * 投稿文章审核
	 * @return
	 */
	public String artAudit(){
		SqUserTab sqUserTab = (SqUserTab) session.getAttribute("sqUserTab");
		try {
			iSystemHomeService.artAudit(sqArticleManager, sqUserTab);
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "success" ;
	}
	
	/**
	 * 系统BUG提交
	 * @return
	 */
	public String sysBugSubmit(){
		SqUserTab sqUserTab = (SqUserTab) session.getAttribute("sqUserTab");
		try {
			iSystemHomeService.sysBugSave(sqArticleManager, sqUserTab);
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		return "success" ;
	}
	
	/**
	 * 系统BUG查询
	 * @return
	 */
	public String sysBugQuery(){
		//查询条件的标志
		String flag = request.getParameter("FLAG");
		if(PubCheckVal.isNull(flag)){
			flag = "0" ;
		}
		String content = request.getParameter("CONTENT") ;
		StringBuffer hql = new StringBuffer();
		String [] paramNames = new String[1];
		Object[] values = new Object[1] ;
		
		hql.append( "from SqArticleManager sqArticleManager where sqArticleManager.status='0' and sqArticleManager.plate='4' " );
		if(flag.equals("1")){
			//根据撰写日期查询
			hql.append("and　sqArticleManager.delivefyDate = :startDate ");
			paramNames[0] = "startDate" ;
			values[0] = Public.getStringToDate(content, "yyyy-MM-dd");
		}else if(flag.equals("2")){
			//根据标题查询
			hql.append("and sqArticleManager.artTitle like '%||:content||%'");
			paramNames[0] = "content" ;
			values[0] = content;
		}else if(flag.equals("3")){
			//根据撰写人查询
			hql.append("and sqArticleManager.delivefyUserId = (select sqUserTab.userId from SqUserTab sqUserTab where sqUserTab.userName=:userName) ");
			paramNames[0] = "userName" ;
			values[0] = content;
		}else{
			//查询前3个月的系统通知内容
			hql.append("and sqArticleManager.delivefyDate >= :startDate ");
			paramNames[0] = "startDate" ;
			values[0] = Public.getDateToAddRed(null, "yyyy-MM-dd" , 3*60);
		}
		hql.append("order by sqArticleManager.delivefyDate") ;
		
		this.executeHql(hql.toString(), paramNames, values, Integer.parseInt(this.getCurrentStr())) ;
		
		request.setAttribute("sqArticleManagerList", this.getListData()) ;
		//将得到的数据进行解析
//		try {
//			List sqArticleManagerList = new ArrayList();
//			for (int i = 0; i < this.getListData().size(); i++) {
//				SqArticleManager temp = (SqArticleManager)this.getListData().get(i);
//				//加载投稿员工信息
//				SqUserTab sqUserTab = iUserService.findById(temp.getDelivefyUserId());
//				temp.setDelivefyUser(sqUserTab);
//				sqArticleManagerList.add(temp) ;
//			}
//			request.setAttribute("sqArticleManagerList", sqArticleManagerList) ;
//		} catch (SystemException e) {
//			iUserLog.logwrite(request, e);
//			errorForm.setMessage(e.getMessage());
//			return "error";
//		}
		return "sysbugquery";
	}
	
	/**
	 * 执行指定的SQL脚本
	 * @return
	 */
	public String systemSql(){
		String sql = request.getParameter("SQL");
		Map<String [], List<String[]>> strMap;
		try {
			strMap = iSystemHomeService.systemSql(sql);
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error";
		}
		request.setAttribute("strMap", strMap);
		request.setAttribute("sql", sql);
		return "sysemsql";
	}
	public SqArticleManager getSqArticleManager() {
		return sqArticleManager;
	}

	public void setSqArticleManager(SqArticleManager sqArticleManager) {
		this.sqArticleManager = sqArticleManager;
	}

	public ErrorForm getErrorForm() {
		return errorForm;
	}

	public void setErrorForm(ErrorForm errorForm) {
		this.errorForm = errorForm;
	}
	
	
}
