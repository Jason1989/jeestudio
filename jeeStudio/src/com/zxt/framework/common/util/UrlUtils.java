package com.zxt.framework.common.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * URL字符串处理
 * @author chinazxt
 *
 */
public class UrlUtils {

	/**
	 * 
	 * @param url(不含有汉字的url)
	 * @return
	 */
	public static Map getQueryParams(String urlString){
		Map queryParams = new HashMap();
		//解析url并封装到queryParams中
		if(StringUtils.isNotEmpty(urlString) && urlString.indexOf("?") != -1){
			String[] paramKeyVals = urlString.substring(urlString.indexOf("?")+1).split("&");
			for (int i = 0; i < paramKeyVals.length; i++) {
				String keyVal = paramKeyVals[i].toString();
				if(StringUtils.isNotEmpty(keyVal) && keyVal.indexOf("=") != -1){
					String[] keyValArray = keyVal.split("=");
					queryParams.put(keyValArray[0], keyValArray[1]);
				}
			}
		}
		return queryParams;
	}
}
