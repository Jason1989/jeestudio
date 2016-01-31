package com.fr.io;

import java.io.File;
import java.io.FileOutputStream;

import com.fr.base.FRContext;
import com.fr.base.dav.LocalEnv;
import com.fr.report.ResultReport;
import com.fr.report.ResultWorkBook;
import com.fr.report.WorkBook;
import com.fr.report.io.ExcelExporter;
import com.fr.report.io.TemplateImporter;
import com.fr.report.parameter.Parameter;

public class ExportReports {

	public static void main(String[] args) {
		// ���屨�����л���,����ִ�б���
		String envPath = "C:\\FineReport6.5\\WebReport\\WEB-INF";
		FRContext.setCurrentEnv(new LocalEnv(envPath));
		
		try{
			//δִ��ģ�幤����
			File cptfile = new File("C:\\FineReport6.5\\WebReport\\WEB-INF\\reportlets\\exportdemo1.cpt");
			TemplateImporter tplImp = new TemplateImporter();
			WorkBook workbook = tplImp.generateTemplate(cptfile);
						
			//����ֵΪChina�������������������rworkbook
			Parameter[] parameters = workbook.getParameters();
			java.util.Map parameterMap = new java.util.HashMap();
			for (int i = 0; i < parameters.length; i++) {
				parameterMap.put(parameters[i].getName(),"China");
			}
			ResultWorkBook rworkbook = workbook.execute(parameterMap);
			rworkbook.setReportName(0, "China");
			
			//���parametermap��������ֵ��ΪAmerica,�������ResultReport
			parameterMap.clear();
			for (int i = 0; i < parameters.length; i++) {
				parameterMap.put(parameters[i].getName(),"America");
			}
			ResultWorkBook rworkbook2 = workbook.execute(parameterMap);
			ResultReport rreport2 = rworkbook2.getResultReport(0);
			rworkbook.addReport("America", rreport2);
			
					
			//���������
			FileOutputStream outputStream;
			
			//���������������ΪExcel�ļ�
			outputStream = new FileOutputStream(new File("E:\\ExcelExport.xls"));
			ExcelExporter ExcelExport = new ExcelExporter(null);
			ExcelExport.export(outputStream, rworkbook);
		}catch (Exception e) {
			e.getStackTrace();
		}
	}
}
