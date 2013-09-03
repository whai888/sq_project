package com.sq.logic.workday.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.sq.model.vo.SqWorkdayManager;

/**
 * 
 *@autor whai
 */
public class WorkDaySortList{
	//按照周报的项目编号排序
	public void Sort(List<SqWorkdayManager> list, final String sort) {
		Collections.sort(list, new Comparator() {
			public int compare(Object a, Object b) {
				int ret = 0;
				try {
					SqWorkdayManager sqWorkDaya = (SqWorkdayManager) a ;
					SqWorkdayManager sqWorkDayb = (SqWorkdayManager)b ;
					if (sort != null && "desc".equals(sort))// 倒序
						ret = sqWorkDaya.getSqProjectInfo().getProjectId().compareTo(sqWorkDayb.getSqProjectInfo().getProjectId()) ;
					else
						// 正序
						ret = sqWorkDayb.getSqProjectInfo().getProjectId().compareTo(sqWorkDaya.getSqProjectInfo().getProjectId()) ;
				} catch (Exception it) {
					System.out.println(it);
				}
				return ret;
			}
		});
	}
}
