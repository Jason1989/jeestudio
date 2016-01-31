package com.fr.function;

import java.io.File;

import com.fr.base.FRContext;
import com.fr.base.core.json.JSONArray;
import com.fr.base.core.json.JSONObject;
import com.fr.base.dav.LocalEnv;
import com.fr.report.CellElement;
import com.fr.report.ResultReport;
import com.fr.report.ResultWorkBook;
import com.fr.report.WorkBook;
import com.fr.report.io.TemplateImporter;
import com.fr.report.script.NormalFunction;

public class ReportCheck extends NormalFunction{

	public Object run(Object[] args) {
		// 获取公式中的参数
		String cptname = args[0].toString(); // 获取报表名称
		Integer colnumber = Integer.parseInt(args[2].toString()); // 所取单元格所在列   
		Integer rownumber = Integer.parseInt(args[3].toString()); // 所取单元格所在行
		
		// 定义返回的值
        Object returnValue = null;
		
		// 定义报表运行环境，才能运行读取的报表
        String envPath = "C:\\FineReport6.5\\WebReport\\WEB-INF";
        FRContext.setCurrentEnv(new LocalEnv(envPath));
                
        try {
        	// 读取模板
            File cptFile = new File("C:\\FineReport6.5\\WebReport\\WEB-INF\\reportlets\\" + cptname);
            TemplateImporter templateimporter = new TemplateImporter();
			WorkBook workbook = templateimporter.generateTemplate(cptFile);
			
			// 获取需要传递给报表的参数名与参数值，格式如[{"name":para1name,"value":para1value},{"name":para2name,"value":para2value},......]
			JSONArray parasArray = new JSONArray(args[1].toString());
			JSONObject jo = new JSONObject();
			
			// 定义报表执行时使用的paraMap,保存参数名与值
			java.util.Map parameterMap = new java.util.HashMap();
			if(parasArray.length() > 0){
				for(int i = 0; i < parasArray.length(); i++){
					jo = parasArray.getJSONObject(i);
					parameterMap.put(jo.get("name"), jo.get("value"));
				}
			}
			
			// 执行报表
			ResultWorkBook rworkbook = workbook.execute(parameterMap);
			
			//获取报表结果中对应Cell的值
			ResultReport rreport = rworkbook.getResultReport(0);
			CellElement Cell = rreport.getCellElement(colnumber, rownumber);
			returnValue = Cell.getValue();	
		} catch (Exception e) {
			e.printStackTrace();
		}
        
		return returnValue;
	}

}
