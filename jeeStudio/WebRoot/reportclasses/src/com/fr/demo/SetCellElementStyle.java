package com.fr.demo;

import java.awt.Color;

import com.fr.base.Constants;
import com.fr.base.FRFont;
import com.fr.base.Style;
import com.fr.base.background.ColorBackground;
import com.fr.report.CellElement;
import com.fr.report.DefaultCellElement;
import com.fr.report.TemplateWorkBook;
import com.fr.report.WorkBook;
import com.fr.report.WorkSheet;
import com.fr.third.com.lowagie.text.Font;
import com.fr.web.Reportlet;
import com.fr.web.ReportletRequest;

public class SetCellElementStyle extends Reportlet{

	public TemplateWorkBook createReport(ReportletRequest arg0){
		//�½�����
		WorkBook workbook = new WorkBook();
		WorkSheet worksheet = new WorkSheet();
		
		//�½�һ����Ԫ��λ��Ϊ(1,1),��ռ2��Ԫ����ռ2��Ԫ���ı�ֵΪ "FineReport"
		CellElement cellElement = new DefaultCellElement(1,1,2,2,"FineReport");  
	
		//�����п�Ϊ300px�������и�Ϊ30px
		worksheet.setColumnWidth(1, 200);      
		worksheet.setRowHeight(1, 30);   
		
		//�õ�CellElement����ʽ�����û���½�Ĭ����ʽ   
        Style style = cellElement.getStyle();   
        if(style == null) {   
            style = Style.getInstance();   
        } 

        // ���������ǰ������ɫ   
        FRFont frFont = FRFont.getInstance("Dialog", Font.BOLD, 16);   
        frFont = frFont.applyForeground(new Color(21, 76, 160));   
        style = style.deriveFRFont(frFont);   
           
        // ���ñ���   
	        ColorBackground background = ColorBackground.getInstance(new Color(255, 255, 177));   
        style = style.deriveBackground(background);   
           
        // ����ˮƽ����   
        style = style.deriveHorizontalAlignment(Constants.CENTER);   
           
        // ���ñ߿�   
        
	        style = style.deriveBorder(Constants.LINE_DASH, Color.red, Constants.LINE_DOT, Color.gray, Constants.LINE_DASH_DOT, Color.BLUE, Constants.LINE_DOUBLE, Color.CYAN);              
        // �ı䵥Ԫ�����ʽ   
        cellElement.setStyle(style);   
           
        // ����Ԫ�����ӵ�������   
        worksheet.addCellElement(cellElement); 
        
        workbook.addReport(worksheet);		
		return workbook;
	}

}



