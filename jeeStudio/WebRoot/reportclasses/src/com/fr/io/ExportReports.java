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
		// 定义报表运行环境,才能执行报表
		String envPath = "C:\\FineReport6.5\\WebReport\\WEB-INF";
		FRContext.setCurrentEnv(new LocalEnv(envPath));
		
		try{
			//未执行模板工作薄
			File cptfile = new File("C:\\FineReport6.5\\WebReport\\WEB-INF\\reportlets\\exportdemo1.cpt");
			TemplateImporter tplImp = new TemplateImporter();
			WorkBook workbook = tplImp.generateTemplate(cptfile);
						
			//参数值为China计算结果，将结果保存至rworkbook
			Parameter[] parameters = workbook.getParameters();
			java.util.Map parameterMap = new java.util.HashMap();
			for (int i = 0; i < parameters.length; i++) {
				parameterMap.put(parameters[i].getName(),"China");
			}
			ResultWorkBook rworkbook = workbook.execute(parameterMap);
			rworkbook.setReportName(0, "China");
			
			//清空parametermap，将参数值改为America,计算后获得ResultReport
			parameterMap.clear();
			for (int i = 0; i < parameters.length; i++) {
				parameterMap.put(parameters[i].getName(),"America");
			}
			ResultWorkBook rworkbook2 = workbook.execute(parameterMap);
			ResultReport rreport2 = rworkbook2.getResultReport(0);
			rworkbook.addReport("America", rreport2);
			
					
			//定义输出流
			FileOutputStream outputStream;
			
			//将结果工作薄导出为Excel文件
			outputStream = new FileOutputStream(new File("E:\\ExcelExport.xls"));
			ExcelExporter ExcelExport = new ExcelExporter(null);
			ExcelExport.export(outputStream, rworkbook);
		}catch (Exception e) {
			e.getStackTrace();
		}
	}
}
