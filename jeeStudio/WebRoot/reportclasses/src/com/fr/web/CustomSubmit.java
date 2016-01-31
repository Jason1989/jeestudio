package com.fr.web;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fr.base.ColumnRow;
import com.fr.report.Report;
import com.fr.report.core.FormReport;
import com.fr.report.core.PackedReport;
import com.fr.web.ParameterConsts;
import com.fr.web.core.SessionIDInfor;
import com.fr.web.core.WebUtils;
import com.fr.web.core.service.SessionDealWith;

public class CustomSubmit {
	public static void dealWithTest(HttpServletRequest req, HttpServletResponse res)throws Exception {
		String sessionID = WebUtils.getHTTPRequestParameter(req,ParameterConsts.SESSION_ID);
		SessionIDInfor sessionIDInfor = SessionDealWith.getSessionIDInfor(sessionID);
		List cellRelation =new ArrayList();
		for(int i = 0, len = sessionIDInfor.getWorkBook2Show().getReportCount(); i < len; i++) {
			Report report = sessionIDInfor.getWorkBook2Show().getReport(i);
		    if (report instanceof FormReport && report instanceof PackedReport) {
		           Report fr = (PackedReport)report;
		           //请注意这步，我想要取的是A1和B1两个格子扩展以后的他们之间关系和值，如果是想拿A1,B1,D5，对应添加ColumnRow.valueOf("D5")既可		         
		           List rl = ((FormReport)report).getExtendedColumnRowList(new ColumnRow[] {ColumnRow.valueOf("A1"), ColumnRow.valueOf("B1")}, null);
		           for (int c = 0, cl = rl.size(); c < cl; c++) {
		        	   ColumnRow[] crs = (ColumnRow[])rl.get(c);
		               ColumnRowValue[] crvs =new ColumnRowValue[crs.length];
		               for (int index = 0, il = crs.length; index < il; index++) {
		            	   	crvs[index] =new ColumnRowValue(crs[index], fr.getCellValue(crs[index].column, crs[index].row));
		               }
		             
		               cellRelation.add(crvs);
		           }
		     }
		}       
		
		//cellRelation里面存放着扩展以后的格子之间的关系和格子的值
		//以111.cpt模板为例，数据列扩展后 城市NewYork对应ID 1和4，在cellRelation里面就是 cellRelation.get(0)和cellRelation.get(1)，对应着两个 ColumnRowValue[]
		//第一个ColumnRowValue数组的ColumnRowValue[0]的ColumnRow是A1，Value是NewYork,ColumnRowValue[1]的ColumnRow是B1，Value是1
		//第二个ColumnRowValue数组的ColumnRowValue[0]的ColumnRow是A1，Value是NewYork,ColumnRowValue[1]的ColumnRow是B2，Value是4    
		if (cellRelation.size() > 0) {
		    PrintWriter writer = WebUtils.createPrintWriter(res);
		    for (int i = 0, len = cellRelation.size(); i < len; i++) {
		         ColumnRowValue[] crvs = (ColumnRowValue[])cellRelation.get(i);
		         StringBuffer sb =new StringBuffer();
		         for (int v = 0, vl = crvs.length; v < vl; v++) {
		              if (v != 0) {
		            	  sb.append(" ");
		              }
		              sb.append(crvs[v].toString());
		              if (v == vl - 1) {
		                  sb.append("<br />");
		              }
		          }
		              writer.println(sb.toString());
		    }                     
		    writer.flush();
		    writer.close();
		 }
	}

	public static class ColumnRowValue {
		private ColumnRow cr;
		private Object value;

		public ColumnRowValue(ColumnRow cr, Object value) {
			this.cr = cr;
			this.value = value;
		}

		public ColumnRow getColumnRow() {
			return cr;
		}

		public Object getValue() {
			return value;
		}

		public String toString() {
			return new StringBuffer().append(cr.toString()).append(":").append(value.toString()).toString();
		}
	}
}
