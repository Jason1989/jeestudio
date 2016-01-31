package com.fr.demo;

import java.io.File;
import java.util.logging.Level;

import com.fr.base.FRContext;
import com.fr.report.CellElement;
import com.fr.report.Report;
import com.fr.report.TemplateWorkBook;
import com.fr.report.WorkBook;
import com.fr.report.WorkSheet;
import com.fr.report.io.TemplateImporter;
import com.fr.web.Reportlet;
import com.fr.web.ReportletRequest;

public class ChangeRowAndCol extends Reportlet{
	public TemplateWorkBook createReport(ReportletRequest reportletrequest){
		//����������Ҫ���ص�WorkBook����
		WorkBook workbook = new WorkBook();
		WorkSheet newworksheet = new WorkSheet();
		String change = "0";
		try{
			//��ȡģ�屣��ΪWorkBook����
			File cptfile = new File("C:\\FineReport6.5\\WebReport\\WEB-INF\\reportlets\\cross.cpt");
			TemplateImporter tplimp = new TemplateImporter();
			workbook = tplimp.generateTemplate(cptfile);
			
			//��ȡ�����еĲ����ж��Ƿ���Ҫ�л�������ʾ��0��ʾ���л���1��ʾ�л�
			if(reportletrequest.getParameter("change") != null){
				change = reportletrequest.getParameter("change").toString();
			}
			
			if(change.equals("1")){
				//��õ�Ԫ����Ҫ���Ȼ�õ�Ԫ�����ڵı���
				Report report = workbook.getReport(0);
			
				//������Ԫ��
				int col=0,row=0;
				byte direction = 0;
				java.util.Iterator it = report.cellIterator();
				while(it.hasNext()){
					CellElement cell = (CellElement) it.next();
					//��ȡ��Ԫ����к����кŲ�����
					col = cell.getColumn();
					row = cell.getRow();
					cell.setColumn(row);
					cell.setRow(col);
					//��ȡԭ��Ԫ�����չ����0��ʾ������չ��1��ʾ������չ
					direction = cell.getCellExpandAttr().getDirection();
					if(direction == 0){
						cell.getCellExpandAttr().setDirection((byte) 1);
					}else if(direction == 1){
						cell.getCellExpandAttr().setDirection((byte) 0);
					}	
					//���ı��ĵ�Ԫ����ӽ��µ�WorkSheet��
					newworksheet.addCellElement(cell);
				}
				//�滻ԭsheet
				workbook.setReport(0, newworksheet);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return workbook;
	}
}
