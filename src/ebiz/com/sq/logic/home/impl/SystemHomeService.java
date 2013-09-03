package com.sq.logic.home.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import com.sq.exception.SystemException;
import com.sq.logic.home.ISystemHomeService;
import com.sq.logic.user.IUserService;
import com.sq.model.vo.SqArticleManager;
import com.sq.model.vo.SqUserTab;
import com.sq.service.IBaseDAO;
import com.sq.tools.Constant;
import com.sq.tools.Public;

/**
 *
 *@autor whai
 */
@Transactional(rollbackFor=SystemException.class) //指定回滚,遇到异常SystemException时回滚
public class SystemHomeService implements ISystemHomeService{

	@Resource
	private IBaseDAO iBaseDao;
	@Resource
	private IUserService iUserService ;
	/**
	 * 保存我的投稿信息
	 * @param SqArticleManager
	 */
	public void iWantSubSave(SqArticleManager sqArticleManager , SqUserTab sqUserTab){
		//投稿编号
		sqArticleManager.setArtId(iBaseDao.sequenceToId(Constant.SEQUENCE_ARTICLE_INFO));
		//投稿日期
		sqArticleManager.setDelivefyDate(Public.getDateToAddRed(null, "yyyy-MM-dd", 0)) ;
		//投稿人
		sqArticleManager.setDelivefyUser(sqUserTab);
		//文章状态为 未审核
		sqArticleManager.setStatus("9");
		iBaseDao.save(sqArticleManager);
	}

	/**
	 * 根据投稿编号查询文章内容
	 * @param sqArticleManager
	 * @return
	 * @throws SystemException 
	 */
	public SqArticleManager findArtIdForContent(SqArticleManager sqArticleManager) throws SystemException{
		sqArticleManager= (SqArticleManager)iBaseDao.find("from SqArticleManager sqArticleManager where sqArticleManager.artId='" +sqArticleManager.getArtId()+"'").get(0);
		//加载撰写员工信息
//		sqArticleManager.setDelivefyUser(iUserService.findById(sqArticleManager.getDelivefyUserId()));
		//加载发布员工信息
//		sqArticleManager.setPublishUser(iUserService.findById(sqArticleManager.getPublishUserId()));
		return sqArticleManager;
	}
	
	/**
	 * 查询所有发布的文章
	 * @param plate	指定板块
	 * @param flag
	 * @return
	 * @throws SystemException 
	 */
	public List findArticleList(String plate) throws SystemException{
		String hql = "from SqArticleManager sqArticleManager where sqArticleManager.status='0' and sqArticleManager.plate='" +plate +"' and sqArticleManager.publishDate >= str_to_date('"+Public.getTimeToFormat(Public.getDateToAddRed(null, "yyyy-MM-dd" , 3), "yyyy-MM-dd")+"','%Y-%m-%d')" ;
		//只查询前10条
		List articleList = iBaseDao.findByNamedParam(hql , new String[0] , new Object[0] , 0 , 10);
//		List articleTempList = new ArrayList();
//		for (int i = 0; i < articleList.size(); i++) {
//			SqArticleManager sqArticleTemp = (SqArticleManager)articleList.get(i);
//			//投稿人人员信息查询
//			sqArticleTemp.setDelivefyUser(iUserService.findById(sqArticleTemp.getDelivefyUser())) ;
//			articleTempList.add(sqArticleTemp) ;
//		}
		return articleList ;
	}
	
	/**
	 * 投稿文章审核
	 */
	public void artAudit(SqArticleManager sqArticleManager , SqUserTab sqUserTab) throws SystemException{
		//根据文章编号查询文章内容
		String hql = "from SqArticleManager sqArticleManager where sqArticleManager.artId='" +sqArticleManager.getArtId() +"'";
		List artList = iBaseDao.find(hql);
		if(artList.size()== 0){
			throw new SystemException(SystemHomeService.class ,"审批的文章不存在，请确认后再进行。");
		}
		//取出需要审核的文章
		SqArticleManager artTemp = (SqArticleManager)artList.get(0);
		//页面上传过来的状态    0:发表   1:拒绝  2:关闭  9:删除
		if(sqArticleManager.getStatus().equals("0")){
			if(artTemp.getStatus().equals("0")){
				throw new SystemException(SystemHomeService.class ,"已经发表的文章不能再进行发表。");
			}
			artTemp.setPublishUser(sqUserTab);
			artTemp.setPublishDate(Public.getDateToAddRed(null, "yyyy-MM-dd", 0));
			artTemp.setStatus("0");
			iBaseDao.update(artTemp) ;
		}else if(sqArticleManager.getStatus().equals("1")){
			if(artTemp.getStatus().equals("1")){
				throw new SystemException(SystemHomeService.class ,"已经拒绝的文章不能再拒绝。");
			}
			artTemp.setPublishUser(sqUserTab);
			artTemp.setPublishDate(Public.getDateToAddRed(null, "yyyy-MM-dd", 0));
			artTemp.setStatus("1");
			iBaseDao.update(artTemp) ;
		}else if(sqArticleManager.getStatus().equals("2")){
			if(artTemp.getStatus().equals("2")){
				throw new SystemException(SystemHomeService.class ,"已经为关闭的文章，不能再进行关闭。");
			}
			artTemp.setPublishUser(sqUserTab);
			artTemp.setPublishDate(Public.getDateToAddRed(null, "yyyy-MM-dd", 0));
			artTemp.setStatus("2");
			iBaseDao.update(artTemp) ;
		}else{
			//删除文章
			iBaseDao.delete(artTemp) ;
		}
	}
	/**
	 * 系统BUG提交，状态为发布
	 * @param sqArticleManager
	 * @param sqUserTab
	 * @throws SystemException
	 */
	public void sysBugSave(SqArticleManager sqArticleManager , SqUserTab sqUserTab) throws SystemException{
		//投稿编号
		sqArticleManager.setArtId(iBaseDao.sequenceToId(Constant.SEQUENCE_ARTICLE_INFO));
		//投稿日期
		sqArticleManager.setDelivefyDate(Public.getDateToAddRed(null, "yyyy-MM-dd", 0)) ;
		//投稿人
		sqArticleManager.setDelivefyUser(sqUserTab);
		//文章状态为 发布
		sqArticleManager.setStatus("0");
		//为4  表示BUG报告
		sqArticleManager.setPlate("4");
		iBaseDao.save(sqArticleManager);
	}
	
	/**
	 * 根据指定的SQL进行查询
	 * @return
	 * @throws SystemException 
	 */
	public Map<String [], List<String[]>> systemSql(String sql) throws SystemException{
		Map<String [], List<String[]>> strMap = new TreeMap<String [], List<String[]>>();
		strMap = iBaseDao.excuteSql(sql);
		return strMap ;
	}
}
