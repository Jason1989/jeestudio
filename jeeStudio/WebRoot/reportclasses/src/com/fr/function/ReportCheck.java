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
		// ��ȡ��ʽ�еĲ���
		String cptname = args[0].toString(); // ��ȡ��������
		Integer colnumber = Integer.parseInt(args[2].toString()); // ��ȡ��Ԫ��������   
		Integer rownumber = Integer.parseInt(args[3].toString()); // ��ȡ��Ԫ��������
		
		// ���巵�ص�ֵ
        Object returnValue = null;
		
		// ���屨�����л������������ж�ȡ�ı���
        String envPath = "C:\\FineReport6.5\\WebReport\\WEB-INF";
        FRContext.setCurrentEnv(new LocalEnv(envPath));
                
        try {
        	// ��ȡģ��
            File cptFile = new File("C:\\FineReport6.5\\WebReport\\WEB-INF\\reportlets\\" + cptname);
            TemplateImporter templateimporter = new TemplateImporter();
			WorkBook workbook = templateimporter.generateTemplate(cptFile);
			
			// ��ȡ��Ҫ���ݸ�����Ĳ����������ֵ����ʽ��[{"name":para1name,"value":para1value},{"name":para2name,"value":para2value},......]
			JSONArray parasArray = new JSONArray(args[1].toString());
			JSONObject jo = new JSONObject();
			
			// ���屨��ִ��ʱʹ�õ�paraMap,�����������ֵ
			java.util.Map parameterMap = new java.util.HashMap();
			if(parasArray.length() > 0){
				for(int i = 0; i < parasArray.length(); i++){
					jo = parasArray.getJSONObject(i);
					parameterMap.put(jo.get("name"), jo.get("value"));
				}
			}
			
			// ִ�б���
			ResultWorkBook rworkbook = workbook.execute(parameterMap);
			
			//��ȡ�������ж�ӦCell��ֵ
			ResultReport rreport = rworkbook.getResultReport(0);
			CellElement Cell = rreport.getCellElement(colnumber, rownumber);
			returnValue = Cell.getValue();	
		} catch (Exception e) {
			e.printStackTrace();
		}
        
		return returnValue;
	}

}
