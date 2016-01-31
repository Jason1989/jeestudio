package com.fr.demo;

import java.awt.Color;

import com.fr.base.FRFont;
import com.fr.base.Style;
import com.fr.report.CellElement;
import com.fr.report.DefaultCellElement;
import com.fr.report.TemplateWorkBook;
import com.fr.report.WorkBook;
import com.fr.report.WorkSheet;
import com.fr.third.com.lowagie.text.Font;
import com.fr.web.Reportlet;
import com.fr.web.ReportletRequest;

public class CreatReportletDemo extends Reportlet{

	public TemplateWorkBook createReport(ReportletRequest arg0){
		//����һ��WorkBook���������ڹ������в���һ��WorkSheet
		WorkBook workbook = new WorkBook();
		WorkSheet sheet1 = new WorkSheet();		
		//����һ����Ԫ��new DefaultCellElement(int column, int row, Object value)
		//��Ϊ0����Ϊ0��ֵΪFineReport����A1��Ԫ�񣬲����õ�Ԫ�����ʽ
		CellElement CellA1 = new DefaultCellElement(0,0,"FineReport");
		Style style = Style.getInstance();
		//����ΪArial,���壬�ֺ�20����ɫ
		FRFont frfont = FRFont.getInstance("Arial",Font.BOLD,20,Color.red);
		style = style.deriveFRFont(frfont);
		CellA1.setStyle(style);
		sheet1.addCellElement(CellA1);	
		//���õ�0���п�Ϊ120px����0���и�Ϊ35px
		sheet1.setColumnWidth(0, 120);
		sheet1.setRowHeight(0, 35);
		workbook.addReport(sheet1);		
		return workbook;
	}

}
