package com.zxt.compplatform.workflow.Util;

import java.util.HashMap;
import java.util.Map;

public class WorkConfig {
	
	public static String[] workUserIdStrings = new String[]{"11","23","29"};
	
	
	public static String getStatus(String pointId){
		Map map = new HashMap();
		map.put("11", "3");
		map.put("23", "6");
		map.put("29", "8");
		
		return map.get(pointId)+"";
	}
	public static String getJsFunction(Map map,String modelIdAndActivityId){
		StringBuffer jsStr = new StringBuffer("   ");
		
		jsStr.append(" <a href='javascript:void(0)' ");
		
		String[] str = workUserIdStrings;
		for (int i = 0; i < str.length; i++) {
			if(str[i].equals(modelIdAndActivityId)){
				jsStr.append("onClick='task_"+modelIdAndActivityId+"_view(");
				jsStr.append("\""+map.get("tsk_id")+"\",\""+getStatus(modelIdAndActivityId)
						+"\",\""+map.get("prj_name")+"\",\""+map.get("construction_unit")
						+"\",\""+map.get("tsk_code")+"\");'");
			}
		}
		jsStr.append("><img src='jquery-easyui-1.1.2/themes/icons/blue/view.png'></a>");
		
		jsStr.append("   <a href='javascript:void(0)' ");
		for (int i = 0; i < str.length; i++) {
			if(str[i].equals(modelIdAndActivityId)){
				jsStr.append("onClick='task_"+modelIdAndActivityId+"_edit(");
				jsStr.append("\""+map.get("tsk_id")+"\",\""+getStatus(modelIdAndActivityId)
						+"\",\""+map.get("prj_name")+"\",\""+map.get("construction_unit")
						+"\",\""+map.get("tsk_code")+"\");'");
			}
		}
		jsStr.append("><img src='jquery-easyui-1.1.2/themes/icons/green/icon_edit.png'></a>");
		return jsStr+"";
	}
}
