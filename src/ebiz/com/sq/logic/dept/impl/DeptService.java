package com.sq.logic.dept.impl;

import java.util.List;
import java.util.Vector;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.sq.exception.SystemException;
import com.sq.logic.dept.IDeptService;
import com.sq.logic.group.impl.GroupService;
import com.sq.logic.user.IUserService;
import com.sq.model.vo.SqDeptTab;
import com.sq.model.vo.SqDeptUsermanager;
import com.sq.model.vo.SqDeptUsermanagerId;
import com.sq.model.vo.SqUserTab;
import com.sq.service.IBaseDAO;
import com.sq.sys.reload.IFndCache;
import com.sq.tools.Constant;
import com.sq.tools.Public;

@Transactional(rollbackFor=SystemException.class) //指定回滚,遇到异常SystemException时回滚
public class DeptService implements IDeptService {
	@Resource
	private IBaseDAO iBaseDao;
	@Resource
	private IUserService iUserService ;
	@Resource
	private IFndCache iFndCache ;
	private Logger log = Logger.getLogger(DeptService.class);

	/**
	 * 查找所有的部门
	 */
	public List findByAll() {
		List deptTemp = iBaseDao.findByObject(new SqDeptTab(), "succDate");
		List deptList = new Vector();
		for (int i = 0; i < deptTemp.size(); i++) {
			SqDeptTab sqDeptTab = (SqDeptTab)deptTemp.get(i);
			SqUserTab sqUserTab = new SqUserTab();
			sqUserTab.setUserId(sqDeptTab.getDeptUser());
			sqDeptTab.setSqUserTab((SqUserTab)iBaseDao.findFirstVO(sqUserTab, "userId"));
			deptList.add(sqDeptTab);
		}
		//刷新内存中的部门信息
		iFndCache.initDeptTab();
		
		return deptList;
	}
	
	/**
	 * 查找员工所属的部门
	 */
	public List findUserByDept(SqUserTab sqUserTab) {
		String SQL = "select sqDeptTab from SqDeptTab sqDeptTab where sqDeptTab.deptNo in (select sqDeptUsermanager.id.deptNo from SqDeptUsermanager sqDeptUsermanager where sqDeptUsermanager.id.userTab='"+sqUserTab.getUserId()+"') order by sqDeptTab.deptNo desc ";
		List deptTemp = iBaseDao.find(SQL);
		List deptList = new Vector();
		//如果没有数据，则返回自己的部门信息
		if(deptTemp.size() == 0 ){
			deptTemp.add(sqUserTab.getSqDeptTab());
		}
		for (int i = 0; i < deptTemp.size(); i++) {
			SqDeptTab sqDeptTab = (SqDeptTab)deptTemp.get(i);
			SqUserTab sqUserTabTemp = new SqUserTab();
			sqUserTabTemp.setUserId(sqDeptTab.getDeptUser());
			sqDeptTab.setSqUserTab((SqUserTab)iBaseDao.findFirstVO(sqUserTabTemp, "userId"));
			deptList.add(sqDeptTab);
		}
		return deptList;
	}

	/**
	 * 根据部门ID查询部门信息
	 */
	public SqDeptTab findById(String deptId) {
		SqDeptTab sqDeptTab = new SqDeptTab();
		sqDeptTab.setDeptNo(deptId);
		sqDeptTab = (SqDeptTab) iBaseDao.findFirstVO(sqDeptTab , null);
		SqUserTab sqUserTab = new SqUserTab();
		sqUserTab.setUserId(sqDeptTab.getDeptUser());
		sqDeptTab.setSqUserTab((SqUserTab)iBaseDao.findFirstVO(sqUserTab, null));
		return sqDeptTab;
	}

	/**
	 * 保存部门信息
	 * @throws SystemException 
	 */
	public void saveDept(SqDeptTab sqDeptTab , String userDeptList) throws SystemException {
		//部门编号
		sqDeptTab.setDeptNo(iBaseDao.sequenceToId(Constant.SEQUENCE_DEPT_INFO));
		//检查部门负责人工号在员工表是否有存在
		SqUserTab sqUserTab = iUserService.findById(sqDeptTab.getDeptUser());
		if(sqUserTab == null)
			throw new SystemException(DeptService.class ,"对应的员工"+sqDeptTab.getSqUserTab().getUserName()+"不存在。");
		if(sqUserTab.getStatus().equals("1"))
			throw new SystemException(DeptService.class ,"对应的负责人员工"+sqDeptTab.getSqUserTab().getUserName()+"已经注销，不能作为负责人。");
		//修改员工所在的部门编号
		sqUserTab.setSqDeptTab(sqDeptTab);
		//新增时默认将部门的周报类型赋值为"0"
		sqDeptTab.setWeekStatus("0");
		iBaseDao.update(sqUserTab);
		//保存部门信息
		iBaseDao.save(sqDeptTab);
		String sql = "insert into sq_dept_usermanager(dept_no,user_id) values('"+sqDeptTab.getDeptNo()+"','"+sqDeptTab.getDeptUser()+"')";
		iBaseDao.sqlUpdate(sql);
		
		//如果存在部门助理，则将部门助理的信息提交进去
		String [] userList = userDeptList.split("\\|");
		for (int i = 0; i < userList.length; i++) {
			if(!Public.isEmpty(userList[i])){
				sql = "insert into sq_dept_usermanager(dept_no,user_id) values('"+sqDeptTab.getDeptNo()+"','"+userList[i]+"')";
				iBaseDao.sqlUpdate(sql);
			}
		}

		//刷新内存中的部门信息
		iFndCache.initDeptTab();
	}

	/**
	 * 修改部门信息
	 * @throws SystemException 
	 */
	public void updateDept(SqDeptTab sqDeptTab, String userDeptList) throws SystemException {
		SqDeptTab sqDeptTabTemp = this.findById(sqDeptTab.getDeptNo()) ;
		sqDeptTabTemp.setDeptName(sqDeptTab.getDeptName());
		sqDeptTabTemp.setDeptUser(sqDeptTab.getDeptUser());
		sqDeptTabTemp.setSuccDate(sqDeptTab.getSuccDate());
		sqDeptTabTemp.setRemark(sqDeptTab.getRemark());
		sqDeptTabTemp.setStatus(sqDeptTab.getStatus());
		sqDeptTabTemp.setModifyDate(Public.getSystemTimeToFormat("yyyy-MM-dd HH:mm:ss"));
		
		//检查部门负责人工号在员工表是否有存在
		SqUserTab sqUserTab = iUserService.findById(sqDeptTabTemp.getDeptUser());
		if(sqUserTab == null)
			throw new SystemException(DeptService.class ,"对应的员工"+sqDeptTabTemp.getSqUserTab().getUserName()+"不存在。");
		if(sqUserTab.getStatus().equals("1"))
			throw new SystemException(DeptService.class ,"对应的负责人员工"+sqDeptTabTemp.getSqUserTab().getUserName()+"已经注销，不能作为负责人。");
		
		//如果部门的状态修改为不正常，则查询部门下是否还有员工
		if(sqDeptTabTemp.getStatus().equals("1")){
			String hql = "from SqUserTab as sqUserTab where sqUserTab.sqDeptTab.deptNo = '" +sqDeptTabTemp.getDeptNo() + "' and sqUserTab.status != '1'";
			List userList = iBaseDao.find(hql); 
			if(userList.size() > 0 )
				throw new SystemException(GroupService.class ,"对应的部门下还存在员工信息，不能操作。");
		}
		//删除部门助理的信息
		String sql = "delete from sq_dept_usermanager where dept_no='"+sqDeptTabTemp.getDeptNo()+"'";
		iBaseDao.sqlUpdate(sql);
		
		sql = "insert into sq_dept_usermanager(dept_no,user_id) values('"+sqDeptTabTemp.getDeptNo()+"','"+sqDeptTabTemp.getDeptUser()+"')";
		iBaseDao.sqlUpdate(sql);
		
		SqDeptUsermanager sqDeptUsermanager = null ;
		SqDeptUsermanagerId sqDeptUsermanagerId = null ;
		SqUserTab sqUserTabTemp = null ;
		//如果存在部门助理，则将部门助理的信息提交进去
		String [] userList = userDeptList.split("\\|");
		for (int i = 0; i < userList.length; i++) {
			if(!Public.isEmpty(userList[i])){
				sql = "insert into sq_dept_usermanager(dept_no,user_id) values('"+sqDeptTabTemp.getDeptNo()+"','"+userList[i]+"')";
				iBaseDao.sqlUpdate(sql);
			}
		}
		sql = "update sq_dept_tab set dept_name='"+sqDeptTabTemp.getDeptName()+"'"
		;
		//清除对象的缓存
		iBaseDao.evict(sqDeptTabTemp);
		iBaseDao.update(sqDeptTabTemp);
		iFndCache.initDeptTab();
		
	}

}
