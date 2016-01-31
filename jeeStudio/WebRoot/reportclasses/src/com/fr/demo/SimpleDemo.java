package com.fr.demo;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;

import com.fr.base.FRFont;
import com.fr.base.Style;
import com.fr.report.CellElement;
import com.fr.report.Report;
import com.fr.report.WorkBook;
import com.fr.report.io.TemplateExporter;
import com.fr.report.io.TemplateImporter;

public class SimpleDemo {
	public static void main(String[] args) {
		try {
			// ��ȡģ��
			File cptfile = new File("C:\\FineReport6.5\\WebReport\\WEB-INF\\reportlets\\gettingstarted.cpt");
			TemplateImporter tplimp = new TemplateImporter();
			WorkBook workbook = tplimp.generateTemplate(cptfile);
			
			//���WorkBook�е�WorkSheet�������޸�A2��Ԫ���ǰ��ɫΪ��ɫ
			Report report = workbook.getReport(0);
			//getCellElement(int column, int row),column��row����0��ʼ�����A2��Ԫ����ǵ�0�е�1��
			CellElement cellA2 = report.getCellElement(0,1);
			FRFont frFont = FRFont.getInstance();
			frFont = frFont.applyForeground(Color.red);
			Style style = Style.getInstance();
			style = style.deriveFRFont(frFont);
			cellA2.setStyle(style);

			// ����ģ��
			FileOutputStream outputStream = new FileOutputStream(new File("D:\\newGettingStarted.cpt"));
			TemplateExporter templateExporter = new TemplateExporter();
			templateExporter.export(outputStream, workbook);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
