package com.fr.demo;

import com.fr.base.FRContext;
import com.fr.base.dav.LocalEnv;
import com.fr.report.TemplateWorkBook;
import com.fr.report.parameter.Parameter;
import com.fr.web.Reportlet;
import com.fr.web.ReportletRequest;

public class URLParameterDemo extends Reportlet{

	public TemplateWorkBook createReport(ReportletRequest reportletRequest){
		//定义报表运行环境，读取环境下的报表
		String envPath ="C:\\FineReport6.5\\WebReport\\WEB-INF";
		FRContext.setCurrentEnv(new LocalEnv(envPath));      

		//获取外部传来的参数
		String countryValue = reportletRequest.getParameter("para").toString();
		try {
			TemplateWorkBook wbTpl = FRContext.getCurrentEnv().readTemplate("gettingstarted.cpt");
			//提取报表参数组，由于原模板只有country一个参数，因此直接取index为0的参数，并将外部传入的值赋给该参数
			Parameter[] ps = wbTpl.getParameters();
			ps[0].setValue(countryValue);
			
			//原模板定义有参数界面，参数已经从外部获得，去掉参数页面
			//若您想保留参数界面，则将模板设置为不延迟报表展示，再传入参数后直接根据参数值显示结果，否则还需要再次点击查询按钮
			wbTpl.getReportParameterAttr().setParameterUI(null);
			
			return wbTpl;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
}

}
