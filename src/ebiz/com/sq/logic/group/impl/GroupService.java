package com.sq.logic.group.impl;

import java.util.List;
import java.util.Vector;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.sq.exception.SystemException;
import com.sq.logic.group.IGroupService;
import com.sq.logic.user.IUserService;
import com.sq.model.vo.SqDeptTab;
import com.sq.model.vo.SqGroupTab;
import com.sq.model.vo.SqUserTab;
import com.sq.service.IBaseDAO;
import com.sq.sys.reload.IFndCache;
import com.sq.tools.Constant;
import com.sq.tools.SortList;

@Transactional(rollbackFor=SystemException.class) //指定回滚,遇到异常SystemException时回滚
public class GroupService implements IGroupService {
	@Resource
	private IBaseDAO iBaseDao;
	@Resource
	private IUserService iUserService ;
	private Logger log = Logger.getLogger(GroupService.class);

	/**
	 * 查找所有的项目组
	 */
	public List findByAll() {
		String hql = "from SqGroupTab sqGroupTab order by sqGroupTab.succDate desc" ;
		List groupTemp = iBaseDao.find(hql);
//		List groupList = new Vector();
//		for (int i = 0; i < groupTemp.size(); i++) {
//			SqGroupTab sqGroupTab = (SqGroupTab)groupTemp.get(i);
//			SqUserTab sqUserTab = new SqUserTab();
//			sqUserTab.setUserId(sqGroupTab.getGroupUser());
//			sqGroupTab.setSqUserTab((SqUserTab)iBaseDao.findFirstVO(sqUserTab, "userId"));
//			groupList.add(sqGroupTab);
//		}
//		SortList<SqGroupTab> sortList = new SortList<SqGroupTab>();  
//		sortList.Sort(groupList, "getGroupNo", "desc");  
		return groupTemp;
	}

	/**
	 * 根据项目组ID查询项目组信息
	 * @throws SystemException 
	 */
	public SqGroupTab findById(String groupId) throws SystemException {
		SqGroupTab sqGroupTab = new SqGroupTab();
		sqGroupTab.setGroupNo(groupId);
		sqGroupTab = (SqGroupTab) iBaseDao.findFirstVO(sqGroupTab , null);
		if(sqGroupTab == null){
			throw new SystemException(GroupService.class ,"对应的项目组不存在或者状态异常，请联系管理员。");
		}
//		SqUserTab sqUserTab = new SqUserTab();
//		sqUserTab.setUserId(sqGroupTab.getGroupUser());
//		//加载项目组负责人信息
//		sqGroupTab.setSqUserTab((SqUserTab)iBaseDao.findFirstVO(sqUserTab, null));
		return sqGroupTab;
	}

	/**
	 * 根据项目组编号查询项目组员工信息
	 */
	public List findUserByGroupNo(String groupNo) {
		String hql = "from SqUserTab sqUserTab where sqUserTab.sqGroupTab.groupNo = '"+groupNo+"' and sqUserTab.status!='1'" ;
		List userList = iBaseDao.find(hql);
		return userList;
	}
	
	/**
	 * 保存项目组信息
	 * @throws SystemException 
	 */
	public void saveGroup(SqGroupTab sqGroupTab) throws SystemException {
		//项目组编号
		sqGroupTab.setGroupNo(iBaseDao.sequenceToId(Constant.SEQUENCE_GROUP_INFO));
		//检查项目组负责人工号在员工表是否有存在
		SqUserTab sqUserTab = iUserService.findById(sqGroupTab.getSqUserTab().getUserId());
		if(sqUserTab == null)
			throw new SystemException(GroupService.class ,"对应的负责人员工编号"+sqGroupTab.getSqUserTab().getUserId()+"不存在。");
		if(sqUserTab.getStatus().equals("1"))
			throw new SystemException(GroupService.class ,"对应的负责人员工编号"+sqGroupTab.getSqUserTab().getUserId()+"已经注销，不能作为负责人。");
		//修改员工所在的部门编号
		sqUserTab.setSqGroupTab(sqGroupTab);
		iBaseDao.update(sqUserTab);
		//保存项目组信息
		iBaseDao.save(sqGroupTab);
	}

	/**
	 * 修改项目组信息
	 * @throws SystemException 
	 */
	public void updateGroup(SqGroupTab sqGroupTab) throws SystemException {
		//检查项目组负责人工号在员工表是否有存在
		SqUserTab sqUserTab = iUserService.findById(sqGroupTab.getSqUserTab().getUserId());
		if(sqUserTab == null)
			throw new SystemException(GroupService.class ,"对应的员工编号"+sqGroupTab.getSqUserTab().getUserId()+"不存在。");
		if(sqUserTab.getStatus().equals("1"))
			throw new SystemException(GroupService.class ,"对应的负责人员工编号"+sqGroupTab.getSqUserTab().getUserId()+"已经注销，不能作为负责人。");
		
		//如果项目组的状态修改为不正常，则查询项目组下是否还存在对应的员工
		if(sqGroupTab.getStatus().equals("1")){
			String hql = "from SqUserTab as sqUserTab where sqUserTab.sqGroupTab.groupNo = '" +sqGroupTab.getGroupNo()+ "' and sqUserTab.status != '1'";
			List userList = iBaseDao.find(hql); 
			if(userList.size() > 0 )
				throw new SystemException(GroupService.class ,"对应的项目组还存在员工信息，不能关闭。");
		}
		//清除对象的缓存
		iBaseDao.evict(sqUserTab.getSqGroupTab());
		iBaseDao.update(sqGroupTab);
	}
	/**
	 * 查询部门下所有的项目组
	 * @param sqdeptTab
	 * @return
	 */
	public List findGroupBydeptNo(SqDeptTab sqdeptTab){
		String hql = "from SqGroupTab sqGroupTab where sqGroupTab.sqDeptTab.deptNo = '"+sqdeptTab.getDeptNo()+"' order by sqGroupTab.groupNo desc" ;
		List groupTemp = iBaseDao.find(hql);
		return groupTemp ;
	}
}
