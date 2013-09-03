package com.sq.tools;

import java.util.Map;

public class WorkDayTools {

	/**
	 * 组装工作成果
	 * @param unitsMap
	 * @param workLoad
	 * @param units
	 * @return
	 */
	public static String workLoadStr(Map unitsMap , String workLoad , String units){
		String [] workLoadSplit = workLoad.split("\\|");
		StringBuffer strBuff = new StringBuffer();
		for (int i = 0; i < workLoadSplit.length; i++) {
			if(!Public.isEmpty(workLoadSplit[i])){
				strBuff.append(workLoadSplit[i] + unitsMap.get(i+"") + "  ");
			}
		}
		if(Public.isEmpty(strBuff.toString())){
			strBuff.append("无");
		}
		return strBuff.toString();
	}
}
