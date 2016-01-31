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
			//读取模板保存为WorkBook对象
			File cptfile = new File("C:\\FineReport6.5\\WebReport\\WEB-INF\\reportlets\\gettingstarted.cpt");
			TemplateImporter tplimp = new TemplateImporter();
			WorkBook workbook = tplimp.generateTemplate(cptfile);
			
			//获取WorkBook工作薄的参数属性ReportParameterAttr
			ReportParameterAttr paraAttr = workbook.getReportParameterAttr();
			
			/*
			 * 参数界面的显示位置
			 * 0/ReportParameterAttr.POPUP : 弹出显示
			 * 1/ReportParameterAttr.EMBED : 内嵌显示
			 */
			paraAttr.setWindowPosition(ReportParameterAttr.POPUP);
			
			/*
			 * 若参数界面选择弹出显示，则可以设置弹出的参数窗口标题
			 * String title
			 */
			paraAttr.setParameterWindowTitle("参数属性parameterAttr的使用");
			
			//重新设置参数属性,导出最终结果
			workbook.setReportParameterAttr(paraAttr);
			FileOutputStream outputStream = new FileOutputStream(new File("D:\\newGettingStarted.cpt"));
			TemplateExporter templateExporter = new TemplateExporter();
			templateExporter.export(outputStream, workbook);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
