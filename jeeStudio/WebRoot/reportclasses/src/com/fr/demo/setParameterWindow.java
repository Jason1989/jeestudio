package com.fr.demo;

import java.io.File;
import java.io.FileOutputStream;

import com.fr.report.ReportParameterAttr;
import com.fr.report.WorkBook;
import com.fr.report.io.TemplateExporter;
import com.fr.report.io.TemplateImporter;

public class setParameterWindow {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			//��ȡģ�屣��ΪWorkBook����
			File cptfile = new File("C:\\FineReport6.5\\WebReport\\WEB-INF\\reportlets\\gettingstarted.cpt");
			TemplateImporter tplimp = new TemplateImporter();
			WorkBook workbook = tplimp.generateTemplate(cptfile);
			
			//��ȡWorkBook�������Ĳ�������ReportParameterAttr
			ReportParameterAttr paraAttr = workbook.getReportParameterAttr();
			
			/*
			 * �����������ʾλ��
			 * 0/ReportParameterAttr.POPUP : ������ʾ
			 * 1/ReportParameterAttr.EMBED : ��Ƕ��ʾ
			 */
			paraAttr.setWindowPosition(ReportParameterAttr.POPUP);
			
			/*
			 * ����������ѡ�񵯳���ʾ����������õ����Ĳ������ڱ���
			 * String title
			 */
			paraAttr.setParameterWindowTitle("��������parameterAttr��ʹ��");
			
			//�������ò�������,�������ս��
			workbook.setReportParameterAttr(paraAttr);
			FileOutputStream outputStream = new FileOutputStream(new File("D:\\newGettingStarted.cpt"));
			TemplateExporter templateExporter = new TemplateExporter();
			templateExporter.export(outputStream, workbook);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
