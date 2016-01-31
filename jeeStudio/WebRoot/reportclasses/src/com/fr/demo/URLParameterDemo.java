package com.fr.demo;

import com.fr.base.FRContext;
import com.fr.base.dav.LocalEnv;
import com.fr.report.TemplateWorkBook;
import com.fr.report.parameter.Parameter;
import com.fr.web.Reportlet;
import com.fr.web.ReportletRequest;

public class URLParameterDemo extends Reportlet{

	public TemplateWorkBook createReport(ReportletRequest reportletRequest){
		//���屨�����л�������ȡ�����µı���
		String envPath ="C:\\FineReport6.5\\WebReport\\WEB-INF";
		FRContext.setCurrentEnv(new LocalEnv(envPath));      

		//��ȡ�ⲿ�����Ĳ���
		String countryValue = reportletRequest.getParameter("para").toString();
		try {
			TemplateWorkBook wbTpl = FRContext.getCurrentEnv().readTemplate("gettingstarted.cpt");
			//��ȡ��������飬����ԭģ��ֻ��countryһ�����������ֱ��ȡindexΪ0�Ĳ����������ⲿ�����ֵ�����ò���
			Parameter[] ps = wbTpl.getParameters();
			ps[0].setValue(countryValue);
			
			//ԭģ�嶨���в������棬�����Ѿ����ⲿ��ã�ȥ������ҳ��
			//�����뱣���������棬��ģ������Ϊ���ӳٱ���չʾ���ٴ��������ֱ�Ӹ��ݲ���ֵ��ʾ�����������Ҫ�ٴε����ѯ��ť
			wbTpl.getReportParameterAttr().setParameterUI(null);
			
			return wbTpl;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
}

}
