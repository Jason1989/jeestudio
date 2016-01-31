package com.fr.demo;

import java.io.File;

import com.fr.report.TemplateWorkBook;
import com.fr.report.WorkBook;
import com.fr.report.io.TemplateImporter;
import com.fr.web.Reportlet;
import com.fr.web.ReportletRequest;

public class SimpleReportletDemo extends Reportlet{
	public TemplateWorkBook createReport(ReportletRequest reportletrequest){
		//�½�һ��WorkBook�������ڱ������շ��صı���
		WorkBook  workbook = new WorkBook();
		try {
			// ��ȡģ�壬��ģ�屣��Ϊworkbook���󲢷���
			File cptfile = new File("C:\\FineReport6.5\\WebReport\\WEB-INF\\reportlets\\gettingstarted.cpt");
			TemplateImporter tplimp = new TemplateImporter();
			workbook = tplimp.generateTemplate(cptfile);
		}catch (Exception e) {
			e.getStackTrace();
		}
		return workbook;
	}

}
