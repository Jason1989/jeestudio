package com.fr.io;

import java.io.File;
import java.io.FileOutputStream;

import com.fr.base.FRContext;
import com.fr.base.dav.LocalEnv;
import com.fr.report.ResultWorkBook;
import com.fr.report.WorkBook;
import com.fr.report.io.CSVExporter;
import com.fr.report.io.ExcelExporter;
import com.fr.report.io.PDFExporter;
import com.fr.report.io.TemplateExporter;
import com.fr.report.io.TemplateImporter;
import com.fr.report.io.TextExporter;
import com.fr.report.io.WordExporter;
import com.fr.report.io.core.EmbeddedTableDataExporter;
import com.fr.report.parameter.Parameter;

public class ExportApi {
	public static void main(String[] args) {
		// ���屨�����л���,����ִ�б���
		String envPath = "C:\\FineReport6.5\\WebReport\\WEB-INF";
		FRContext.setCurrentEnv(new LocalEnv(envPath));
		ResultWorkBook rworkbook = null;
		
		try{
			//δִ��ģ�幤����
			File cptfile = new File("C:\\FineReport6.5\\WebReport\\WEB-INF\\reportlets\\exportdemo.cpt");
			TemplateImporter tplImp = new TemplateImporter();
			WorkBook workbook = tplImp.generateTemplate(cptfile);
			
			//��ȡ��������������ֵ�������������ݼ�ʱ���ݼ�����ݲ���ֵ��ѯ������Ӷ�תΪ�������ݼ�
			Parameter[] parameters = workbook.getParameters();
			parameters[0].setValue("China");
			
			//����parametermap����ִ�б�������ִ�к�Ľ������������ΪrworkBook
			java.util.Map parameterMap = new java.util.HashMap();
			for (int i = 0; i < parameters.length; i++) {
				parameterMap.put(parameters[i].getName(),parameters[i].getValue());
			}
			
            //���������
			FileOutputStream outputStream;
			
			//��δִ��ģ�幤��������Ϊ�������ݼ�ģ��
			outputStream = new FileOutputStream(new File("E:\\EmbExport.cpt"));   
			EmbeddedTableDataExporter EmbExport = new EmbeddedTableDataExporter();
			EmbExport.export(outputStream, workbook);
			
			//��ģ�幤��������ģ���ļ����ڵ���ǰ�����Ա༭�����ģ�幤�������ɲο����������½�
			outputStream = new FileOutputStream(new File("E:\\TmpExport.cpt"));
			TemplateExporter TmpExport = new TemplateExporter();
			TmpExport.export(outputStream, workbook);
			
			//���������������ΪExcel�ļ�
			outputStream = new FileOutputStream(new File("E:\\ExcelExport.xls"));
			ExcelExporter ExcelExport = new ExcelExporter(null);
			ExcelExport.export(outputStream, workbook.execute(parameterMap));
			
			//���������������ΪWord�ļ�
			outputStream = new FileOutputStream(new File("E:\\WordExport.doc"));
			WordExporter WordExport = new WordExporter();
			WordExport.export(outputStream, workbook.execute(parameterMap));
			
			//���������������ΪPdf�ļ�
			outputStream = new FileOutputStream(new File("E:\\PdfExport.pdf"));
			PDFExporter PdfExport = new PDFExporter();
			PdfExport.export(outputStream, workbook.execute(parameterMap));
			
			//���������������ΪTxt�ļ���txt�ļ�������֧�ֱ���ͼ���ȣ�������ģ��һ��Ϊ��ϸ����
			outputStream = new FileOutputStream(new File("E:\\TxtExport.txt"));
			TextExporter TxtExport =  new TextExporter();
			TxtExport.export(outputStream, workbook.execute(parameterMap));
			
			//���������������ΪCsv�ļ�
			outputStream = new FileOutputStream(new File("E:\\CsvExport.csv"));
			CSVExporter CsvExport = new CSVExporter();
			CsvExport.export(outputStream, workbook.execute(parameterMap));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}