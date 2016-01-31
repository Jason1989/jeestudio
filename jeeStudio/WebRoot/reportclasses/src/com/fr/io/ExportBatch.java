package com.fr.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Arrays;

import com.fr.base.FRContext;
import com.fr.base.dav.LocalEnv;
import com.fr.report.ResultWorkBook;
import com.fr.report.TemplateWorkBook;
import com.fr.report.io.ExcelExporter;
import com.fr.util.Utils;

public class ExportBatch {
	

	public static void main(String[] args){
		try {
			// ���屨�����л���,����ִ�б���
			String envpath = "C:\\FineReport6.5\\WebReport\\WEB-INF";
			FRContext.setCurrentEnv(new LocalEnv(envpath));
			
			// ��ȡ�����µ�ģ���ļ�
			TemplateWorkBook workbook = FRContext.getCurrentEnv().readTemplate("ExportBatch.cpt");
			
			// ��ȡ���ڱ���Ĳ���ֵ��txt�ļ�
			File parafile = new File("E:\\para.txt");
			FileInputStream fileinputstream;
			fileinputstream = new FileInputStream(parafile);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileinputstream));
			
			// ���屣�������map������ִ�б���
			java.util.Map paramap = new java.util.HashMap();
			
			/*
			 * ��������ֵ����txt�ļ���txt�ļ��в���������ʽΪ
			 * para1,para2
			 * ����,Alex
			 * ����,Anna
			 * ����ȡ����һ�б����������
			 * ����ÿ��������ϣ���para1=���ա�para2=Alex�����ݲ���ִ��ģ�壬�����������excel
			 * excel�ļ���Ϊ����+�������
			 */
						
			// ����һ�У������������
			String lineText = bufferedReader.readLine();
			lineText = lineText.trim();
			String[] paraname = Utils.splitString(lineText, ",");
			System.out.println(Arrays.toString(paraname));
			
			// ����ÿ��������ϣ�ִ��ģ�壬�������
			int number = 0;
			while((lineText = bufferedReader.readLine()) != null) {
				lineText = lineText.trim();
				String[] paravalue = Utils.splitString(lineText, ",");
				for (int j = 0; j < paravalue.length; j++) {
					paramap.put(paraname[j], paravalue[j]);
				}
				ResultWorkBook result = workbook.execute(paramap);
				OutputStream  outputdtream = new FileOutputStream(new File("E:\\ExportEg" + number + ".xls"));
				ExcelExporter excelexporter = new ExcelExporter();
				excelexporter.export(outputdtream, result);
				
				// ���Ҫ���һ�²���map�������´μ���
				paramap.clear();
				
				number++;
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
