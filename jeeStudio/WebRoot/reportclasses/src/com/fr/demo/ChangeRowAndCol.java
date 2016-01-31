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
		//定义最终需要返回的WorkBook对象
		WorkBook workbook = new WorkBook();
		WorkSheet newworksheet = new WorkSheet();
		String change = "0";
		try{
			//读取模板保存为WorkBook对象
			File cptfile = new File("C:\\FineReport6.5\\WebReport\\WEB-INF\\reportlets\\cross.cpt");
			TemplateImporter tplimp = new TemplateImporter();
			workbook = tplimp.generateTemplate(cptfile);
			
			//读取请求中的参数判断是否需要切换行列显示，0表示不切换，1表示切换
			if(reportletrequest.getParameter("change") != null){
				change = reportletrequest.getParameter("change").toString();
			}
			
			if(change.equals("1")){
				//获得单元格需要首先获得单元格所在的报表
				Report report = workbook.getReport(0);
			
				//遍历单元格
				int col=0,row=0;
				byte direction = 0;
				java.util.Iterator it = report.cellIterator();
				while(it.hasNext()){
					CellElement cell = (CellElement) it.next();
					//获取单元格的行号与列号并互换
					col = cell.getColumn();
					row = cell.getRow();
					cell.setColumn(row);
					cell.setRow(col);
					//获取原单元格的扩展方向，0表示纵向扩展，1表示横向扩展
					direction = cell.getCellExpandAttr().getDirection();
					if(direction == 0){
						cell.getCellExpandAttr().setDirection((byte) 1);
					}else if(direction == 1){
						cell.getCellExpandAttr().setDirection((byte) 0);
					}	
					//将改变后的单元格添加进新的WorkSheet中
					newworksheet.addCellElement(cell);
				}
				//替换原sheet
				workbook.setReport(0, newworksheet);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return workbook;
	}
}
