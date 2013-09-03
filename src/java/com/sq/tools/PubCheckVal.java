package com.sq.tools;

/**
 *
 *@autor whai
 */
public class PubCheckVal {

	/**
	 * 检查字符串是否为空字符 null 或者空
	 * @param strVal
	 * @return  是返回true 否则返回false
	 */
	public static boolean isNull(String strVal){
		if(strVal==null || strVal.equals("") || strVal.equals("null")){
			return true ;
		}else{
			return false ;
		}
	}
}
