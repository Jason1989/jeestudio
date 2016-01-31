package com.zxt.compplatform.workflow.Util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

public class ComParUtil {

	public static Map convertComParArray(String comParArray){
		if( StringUtils.isBlank(comParArray) ){
			return MapUtils.EMPTY_MAP;
		}
		Map map = new HashMap();
		String[] array = comParArray.split(";");
		if( array == null ){
			return MapUtils.EMPTY_MAP;
		}
		for(int i=0;i<array.length;i++){
			String key2val = array[i];
			if( StringUtils.isBlank(key2val) ){
				continue;
			}
			String[] keyandval = key2val.split("=");
			if(keyandval!=null && keyandval.length==2){
				map.put(keyandval[0], keyandval[1]);
			}
		}
		return map;
	}
	
	public static void main(String... args){
		String test = "1=1,2,3,4,5;2=6,7,8,9,10;3=5,6,7,97;4=12,23,45,67,77";
		convertComParArray(test);
	}
}
