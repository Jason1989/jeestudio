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
		//创建一个WorkBook工作薄，在工作薄中插入一个WorkSheet
		WorkBook workbook = new WorkBook();
		WorkSheet sheet1 = new WorkSheet();		
		//创建一个单元格new DefaultCellElement(int column, int row, Object value)
		//列为0，行为0，值为FineReport，即A1单元格，并设置单元格的样式
		CellElement CellA1 = new DefaultCellElement(0,0,"FineReport");
		Style style = Style.getInstance();
		//字体为Arial,粗体，字号20，红色
		FRFont frfont = FRFont.getInstance("Arial",Font.BOLD,20,Color.red);
		style = style.deriveFRFont(frfont);
		CellA1.setStyle(style);
		sheet1.addCellElement(CellA1);	
		//设置第0列列宽为120px，第0行行高为35px
		sheet1.setColumnWidth(0, 120);
		sheet1.setRowHeight(0, 35);
		workbook.addReport(sheet1);		
		return workbook;
	}

}
