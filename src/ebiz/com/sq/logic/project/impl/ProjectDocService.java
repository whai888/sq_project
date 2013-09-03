package com.sq.logic.project.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.sq.exception.SystemException;
import com.sq.logic.dept.impl.DeptService;
import com.sq.logic.project.IProjectDocService;
import com.sq.model.vo.SqDocmentManager;
import com.sq.service.IBaseDAO;
import com.sq.tools.Constant;

@Transactional(rollbackFor=SystemException.class) //指定回滚,遇到异常SystemException时回滚
public class ProjectDocService implements IProjectDocService {
	@Resource
	private IBaseDAO iBaseDao;
	private Logger log = Logger.getLogger(DeptService.class);
	/*
	 * 文件阶段保存
	 * (non-Javadoc)
	 * @see com.sq.logic.project.IProjectDocService#saveDocManager(com.sq.model.vo.SqDocmentManager)
	 */
	public void saveDocManager(SqDocmentManager sqDocmentManager) {
		//首先根据文档名、项目编号、阶段编号查询文档的信息，如果有记录则版本号加1 否则版本号设置为1.0
		String hql = "from SqDocmentManager as sqDocmentManager where sqDocmentManager.backfileName = '"+sqDocmentManager.getBackfileName()+"' and sqDocmentManager.projectId = '"+sqDocmentManager.getProjectId()+"' and sqDocmentManager.stepId = '"+sqDocmentManager.getStepId()+"' order by sqDocmentManager.fileVersion desc " ;
		List<SqDocmentManager> sqDocmentManagerList = iBaseDao.find(hql) ;
		if(sqDocmentManagerList.size() == 0 ){
			sqDocmentManager.setFileVersion("1.0");
		}else{
			SqDocmentManager sqDocmentManagerTemp = sqDocmentManagerList.get(0);
			sqDocmentManager.setFileVersion(sqDocmentManagerTemp.getFileVersion().substring(0, sqDocmentManagerTemp.getFileVersion().length()-1) + (Integer.parseInt(sqDocmentManagerTemp.getFileVersion().substring(sqDocmentManagerTemp.getFileVersion().length() -1 )) + 1 ));
		}
		//设置文件的编号
		sqDocmentManager.setDocId(iBaseDao.sequenceToId(Constant.SEQUENCE_PROJECT_DOC));
		//保存时文件的状态为正常
		sqDocmentManager.setStatus("0");
		iBaseDao.save(sqDocmentManager) ;
	}
	
	/**
	 * 修改文件状态
	 * @param sqDocmentManager
	 */
	public void updateDocManager(SqDocmentManager sqDocmentManager){
		SqDocmentManager sqDocmentManagerTemp = new SqDocmentManager();
		sqDocmentManagerTemp.setDocId(sqDocmentManager.getDocId());
		sqDocmentManagerTemp = (SqDocmentManager)iBaseDao.findFirstVO(sqDocmentManagerTemp, null);
		sqDocmentManagerTemp.setStatus(sqDocmentManager.getStatus());
		iBaseDao.update(sqDocmentManagerTemp);
	}
	
	/**
	 * 根据文件编号删除对应的文档
	 * @param sqDocmentManager
	 */
	public void deleteDocManager(SqDocmentManager sqDocmentManager){
		iBaseDao.delete(sqDocmentManager) ;
	}
	/**
	 * 根据项目编号和项目阶段查询该阶段下的文档
	 */
	public List projectDocFindInfo(SqDocmentManager sqDocmentManager)
			throws SystemException {
		List projectDocList = new ArrayList();
		String hql = "" ;
		if(sqDocmentManager.getStepId() != null && !sqDocmentManager.getStepId().equals(""))
			hql = "from SqDocmentManager as sqDocmentManager where sqDocmentManager.projectId = '" + sqDocmentManager.getProjectId() + "' and sqDocmentManager.stepId = '" + sqDocmentManager.getStepId() +"' order by sqDocmentManager.docId";
		else
			hql = "from SqDocmentManager as sqDocmentManager where sqDocmentManager.projectId = '" + sqDocmentManager.getProjectId() + "' order by sqDocmentManager.docId";
		projectDocList =  iBaseDao.find(hql);
		return projectDocList;
	}
	
	/**
	 * 查询日报内容
	 * @param sqDocmentManager
	 * @return
	 */
	public SqDocmentManager findDocManagerToRemark1(SqDocmentManager sqDocmentManager){
		try {
			String hql = "from SqDocmentManager as sqDocmentManager where sqDocmentManager.remark1 = '"+sqDocmentManager.getRemark1()+"' and sqDocmentManager.status='0' order by sqDocmentManager.docId desc";
			List projectDocList =  iBaseDao.find(hql);
			if(projectDocList.size()>0){
				return(SqDocmentManager)projectDocList.get(0);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		return null;
	}
}
