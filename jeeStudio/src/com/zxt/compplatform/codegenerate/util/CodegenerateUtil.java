package com.zxt.compplatform.codegenerate.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;



public class CodegenerateUtil {

	
	
	
	public static org.jdom.Element transformElement(org.dom4j.Element element) {
		String xml = element.asXML();
		org.jdom.Element ele = new org.jdom.Element(xml);
		return ele;
	}

	public static String transformTableName(String tableName) {
		StringBuffer sb = new StringBuffer();
		boolean nextIsUpper = false;
		if(tableName != null && tableName.length() > 0){
			sb.append(tableName.substring(0,1).toUpperCase());
			for(int i = 1; i< tableName.length();i++){
				String s = tableName.substring(i, i+1);
				if(s.equals("_")){
					nextIsUpper = true;
					continue;
				}
				if(nextIsUpper){
					sb.append(s.toUpperCase());
					nextIsUpper = false;
				}else{
					sb.append(s.toLowerCase());
				}
			}
		}
		return sb.toString();
	}

	public static String transformColumnName(String columnName) {
		StringBuffer sb = new StringBuffer();
		boolean nextIsUpper = false;
		if(columnName != null && columnName.length() > 0){
			sb.append(columnName.substring(0,1).toLowerCase());
			for(int i = 1; i< columnName.length();i++){
				String s = columnName.substring(i, i+1);
				if(s.equals("_")){
					nextIsUpper = true;
					continue;
				}
				if(nextIsUpper){
					sb.append(s.toUpperCase());
					nextIsUpper = false;
				}else{
					sb.append(s.toLowerCase());
				}
			}
		}
		return sb.toString();
	}

	public static Map getDataSourcesAndObjects(Document doc) {
		Map map = new HashMap();
		try{
//			Format format = Format.getPrettyFormat();
//			format.setEncoding("UTF-8");
//			XMLOutputter xmlout = new XMLOutputter();
//			ByteArrayOutputStream bo = new ByteArrayOutputStream();
//			xmlout.output(doc, bo);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
	
	public static List copyBySerialize(List list){
		List dest = null;
		try{
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(byteOut);
			out.writeObject(list);
			
			ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
			ObjectInputStream in = new ObjectInputStream(byteIn);
			dest = (List)in.readObject();
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return dest;
	}
	
	
	
	

}
