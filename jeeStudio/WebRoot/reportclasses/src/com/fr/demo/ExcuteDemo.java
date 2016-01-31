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
			// 读取模板
			File cptfile = new File("C:\\FineReport6.5\\WebReport\\WEB-INF\\reportlets\\gettingstarted.cpt");
			TemplateImporter tplimp = new TemplateImporter();
			WorkBook workbook = tplimp.generateTemplate(cptfile);
			
			/*
			 * 生成参数map，注入参数与对应的值，用于执行报表
			 * 该模板中只有一个参数country，给其赋值China
			 * 若参数在发送请求时传过来，可以通过req.getParameter(name)获得
			 * 获得的参数put进map中，paraMap.put(paraname,paravalue)
			 */
			java.util.Map paraMap = new java.util.HashMap();
			paraMap.put("country", "China");
			
			//首先需要定义执行所在的环境，这样才能正确读取数据集信息
			String envPath = "C:\\FineReport6.5\\WebReport\\WEB-INF";
			FRContext.setCurrentEnv(new LocalEnv(envPath));
			//使用paraMap执行生成结果
			ResultWorkBook result = workbook.execute(paraMap);
			
			//使用结果如导出至excel
			FileOutputStream outputStream = new FileOutputStream(new File("D:\\gettingstarted.xls"));
			ExcelExporter excelExporter = new ExcelExporter();
			excelExporter.export(outputStream, result);				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
