package com.fr.demo;

import java.io.File;
import java.io.FileOutputStream;

import com.fr.base.FRContext;
import com.fr.base.dav.LocalEnv;
import com.fr.report.ResultWorkBook;
import com.fr.report.WorkBook;
import com.fr.report.io.ExcelExporter;
import com.fr.report.io.TemplateImporter;

public class ExcuteDemo {

	public static void main(String[] args) {
		try {
			// ��ȡģ��
			File cptfile = new File("C:\\FineReport6.5\\WebReport\\WEB-INF\\reportlets\\gettingstarted.cpt");
			TemplateImporter tplimp = new TemplateImporter();
			WorkBook workbook = tplimp.generateTemplate(cptfile);
			
			/*
			 * ���ɲ���map��ע��������Ӧ��ֵ������ִ�б���
			 * ��ģ����ֻ��һ������country�����丳ֵChina
			 * �������ڷ�������ʱ������������ͨ��req.getParameter(name)���
			 * ��õĲ���put��map�У�paraMap.put(paraname,paravalue)
			 */
			java.util.Map paraMap = new java.util.HashMap();
			paraMap.put("country", "China");
			
			//������Ҫ����ִ�����ڵĻ���������������ȷ��ȡ���ݼ���Ϣ
			String envPath = "C:\\FineReport6.5\\WebReport\\WEB-INF";
			FRContext.setCurrentEnv(new LocalEnv(envPath));
			//ʹ��paraMapִ�����ɽ��
			ResultWorkBook result = workbook.execute(paraMap);
			
			//ʹ�ý���絼����excel
			FileOutputStream outputStream = new FileOutputStream(new File("D:\\gettingstarted.xls"));
			ExcelExporter excelExporter = new ExcelExporter();
			excelExporter.export(outputStream, result);				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
