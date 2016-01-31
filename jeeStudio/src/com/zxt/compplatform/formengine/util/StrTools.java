package com.zxt.compplatform.formengine.util;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.util.Map;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zxt.compplatform.formengine.service.SystemFrameService;

/**
 * 字符串工具类
 * StrTools.charsetFormat(request.getParameter("name"),"ISO8859-1", "UTF-8");
 * @author Jesus & 何舸维
 * @date 2009年6月5日14:50:27
 */
public class StrTools {

	public static String charsetFormat(String str, String from, String to) {

			byte[] bytes = null;
			try {
				bytes = str.getBytes(from);
				if (bytes != null) {
					str = new String(bytes, to);
				}
			} catch (UnsupportedEncodingException e) {
			
			}
	
		return str;
	}
	
	
	/**
	 * 读取configSQL.properties中的相关KEY，根据其数据源ID得到其数据连接
	 * @param map 加载configSQL.properties资源文件
	 * @param systemFrameService
	 * @return Connection的连接
	 * @throws Exception
	 * @author 郭伟新
	 */
	
	public static Connection configPropertiesUtil(Map map,SystemFrameService systemFrameService) throws Exception{
		// 获取缓存中的连接池
		Map poolsMap = systemFrameService.load_connectPools("true");
		String dataSourceID=map.get("is_use_reference_dataSourceID").toString();
		ComboPooledDataSource  connectPool=(ComboPooledDataSource) poolsMap.get(dataSourceID);		
		return connectPool.getConnection();
	}
	
	
}
