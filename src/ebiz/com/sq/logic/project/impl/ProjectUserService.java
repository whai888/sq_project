package com.sq.logic.project.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.sq.exception.SystemException;
import com.sq.logic.project.IProjectUserService;
import com.sq.model.vo.SqProjectUser;
import com.sq.model.vo.SqUserTab;
import com.sq.service.IBaseDAO;

/**
 *
 *@autor whai
 */
@Transactional(rollbackFor=SystemException.class) //指定回滚,遇到异常SystemException时回滚
public class ProjectUserService implements IProjectUserService {
	@Resource
	private IBaseDAO iBaseDao;
	private Logger log = Logger.getLogger(ProjectUserService.class);
	
	/**
	 * 查询项目下所有的员工
	 */
	public List findByProjectId(SqProjectUser sqProjectUser) throws SystemException {
		String hql = "from SqProjectUser sqProjectUser where sqProjectUser.id.projectId = '" + sqProjectUser.getId().getProjectId() + "' and sqProjectUser.status='" +sqProjectUser.getStatus()+ "'";
		List projectUserList = iBaseDao.find(hql);
		List projectUserTemp = new ArrayList();
		//加载员工详细信息
		for (int i = 0; i < projectUserList.size(); i++) {
			SqProjectUser temp = (SqProjectUser)projectUserList.get(i);
			SqUserTab sqUserTab = new SqUserTab();
			sqUserTab.setUserId(temp.getId().getUserId());
			temp.setSqUserTab((SqUserTab) iBaseDao.findFirstVO(sqUserTab, "userId"));
			projectUserTemp.add(temp);
		}
		return projectUserTemp ;
	}
}
