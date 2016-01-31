package com.zxt.framework.common.util;


public class SQLFomatter {

	/**
	 * 将以逗号分开的字符串转换成sql格式的逗号分开的字符串
	 * @param methodIds
	 * @return
	 */
	public static String changeToSqlType(String methodIds){
		String []methodsArray = methodIds.trim().split(",");
		for (int i = 0; i < methodsArray.length; i++) {
			methodsArray[i] = "'"+methodsArray[i]+"'";
		}
		methodIds = "";
		for (int i = 0; i < methodsArray.length; i++) {
			if(i == methodsArray.length-1){
				methodIds += methodsArray[i];
			}else{
				methodIds += methodsArray[i]+",";
			}
		}
		return methodIds;
	} 
}
