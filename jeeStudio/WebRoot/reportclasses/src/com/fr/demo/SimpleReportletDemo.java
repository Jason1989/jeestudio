package com.fr.demo;

import java.io.File;

import com.fr.report.TemplateWorkBook;
import com.fr.report.WorkBook;
import com.fr.report.io.TemplateImporter;
import com.fr.web.Reportlet;
import com.fr.web.ReportletRequest;

public class SimpleReportletDemo extends Reportlet{
	public TemplateWorkBook createReport(ReportletRequest reportletrequest){
		//新建一个WorkBook对象，用于保存最终返回的报表
		WorkBook  workbook = new WorkBook();
		try {
			// 读取模板，将模板保存为workbook对象并返回
			File cptfile = new File("C:\\FineReport6.5\\WebReport\\WEB-INF\\reportlets\\gettingstarted.cpt");
			TemplateImporter tplimp = new TemplateImporter();
			workbook = tplimp.generateTemplate(cptfile);
		}catch (Exception e) {
			e.getStackTrace();
		}
		return workbook;
	}

}
