package com.zxt.compplatform.formengine.util;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.util.ClassLoaderUtils;

/**
 * 从配置文件中获取子系统的加密方式
 * 
 * @author 007
 */
public class PropertiesUtil {
	private static final Logger log = Logger.getLogger(PropertiesUtil.class);

	public static String basePath=findSystemPath();
	/**
	 * 选择平台加密方式
	 * 
	 * @param propertiesPath
	 * @param propertiesKey
	 * @return
	 */
	public static String findPropertiesValue(String propertiesPath,
			String propertiesKey) {
		Properties properties = new Properties();
		String propertiesValue = "";
		try {
			properties.load(ClassLoaderUtils.getResourceAsStream(
					propertiesPath, PropertiesUtil.class));
			propertiesValue = properties.getProperty(propertiesKey);
		} catch (Exception e) {
			log.error("获取配置参数失败");
		}
		return propertiesValue;
	}

	/**
	 * 测试平台加密方式
	 * 
	 * @param propertiesPath
	 * @param propertiesKey
	 * @return
	 */
	public static void main(String[] args) {

		// System.out.println(findPropertiesValue("plat_parameter.properties","PASSWORD_TYPE"));
		//System.out.println(findSystemPath());

		System.out.println(findPropertiesValue("plat_parameter.properties",
				"PASSWORD_TYPE"));

	}
	/**
	 * 跨域配置读取
	 * @return
	 */
	public static String findSystemPath(){
		String pathString="";
		String isAbleAbsolutePath=findPropertiesValue("plat_parameter.properties","IS_ABLE_ABSOLUTEPATH"); 
		String absolutePath=findPropertiesValue("plat_parameter.properties","ABSOLUTE_PATH"); 
		if ("true".equals(isAbleAbsolutePath)) {
			pathString=absolutePath;
		}
		
		return pathString;
	}
	/**
	 * 跨域配置兼容不跨域读写
	 * @param request
	 * @return
	 */
	public static String findSystemPath(HttpServletRequest request){
		String pathString=request.getContextPath()+"/";
		String isAbleAbsolutePath=findPropertiesValue("plat_parameter.properties","IS_ABLE_ABSOLUTEPATH"); 
		String absolutePath=findPropertiesValue("plat_parameter.properties","ABSOLUTE_PATH"); 
		if ("true".equals(isAbleAbsolutePath)) {
			pathString=absolutePath;
		}
		
		return pathString;
	}
}
