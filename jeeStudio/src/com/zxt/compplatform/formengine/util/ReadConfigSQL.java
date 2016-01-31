package com.zxt.compplatform.formengine.util;

import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

/*******************************************************************************
 * 
 * @author GUOWEIXIN 读取configSQL.properties 文件
 */
public class ReadConfigSQL {
	
	
	public static  String readValue(String filePath, String key) {
		Properties props = new Properties();
		try {
//			InputStream in = new BufferedInputStream(new FileInputStream(
//					filePath));
			InputStream in=ReadConfigSQL.class.getResourceAsStream(filePath); 
			props.load(in);
			String value = props.getProperty(key);
			//System.out.println(key +"=="+ value);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 读取properties的全部信息
	public static Map readProperties(String filePath) {
		Properties props = new Properties();
		Map map=new HashMap();
		try {
//			InputStream in = new BufferedInputStream(new FileInputStream(
//					filePath));
			try{
			InputStream in=ReadConfigSQL.class.getResourceAsStream(filePath); 
			props.load(in);
			}catch(Exception e){
				InputStream in=ReadConfigSQL.class.getResourceAsStream("/configSQL1.properties"); 
				props.load(in);
				e.printStackTrace();
			}
			Enumeration en = props.propertyNames();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				String Property = props.getProperty(key);
				map.put(key, Property);
				//System.out.println(key +"~~"+ Property);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	// 写入properties信息
	public static void writeProperties(String filePath, String parameterName,
			String parameterValue) {
		Properties prop = new Properties();
		try {
			InputStream fis = new FileInputStream(filePath);
			// 从输入流中读取属性列表（键和元素对）
			prop.load(fis);
			// 调用 Hashtable 的方法 put。使用 getProperty 方法提供并行性。
			// 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
			OutputStream fos = new FileOutputStream(filePath);
			prop.setProperty(parameterName, parameterValue);
			// 以适合使用 load 方法加载到 Properties表中的格式，
			// 将此 Properties 表中的属性列表（键和元素对）写入输出流
			prop.store(fos, "Update '" + parameterName + "' value");
		} catch (IOException e) {
			System.err.println("Visit " + filePath + " for updating "
					+ parameterName + " value error");
		}
	}

	public static void main(String[] args) {
	//	String value=ReadConfigSQL.readValue("/configSQL.properties", "organization-get_jsonListByClassify");
//		writeProperties("info.properties", "age", "22");
	 Map map=readProperties("/configSQL.properties");//读所有
	 //遍历MAP
	    Set<Entry<String,String>> set=map.entrySet();
	 	for(Entry<String,String> entry : set){
	 		System.out.println(entry.getKey()+"~~"+entry.getValue());
	 	}
	 	System.out.println("OK");
	}

}
