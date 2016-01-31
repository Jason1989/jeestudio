package com.zxt.compplatform.workflow.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.synchrobit.synchroflow.api.bean.SynchroFLOWBean;
import com.synchrobit.synchroflow.api.bean.WorkitemBean;
import com.synchrobit.synchroflow.enactmentservice.exception.DBException;
import com.zxt.compplatform.workflow.dao.Daibanfl3WorkFlowDao;

public class Daibanfl3WorkFlowDaoImpl implements Daibanfl3WorkFlowDao {

	public List findAppid(String userId) {
		SynchroFLOWBean sf = new SynchroFLOWBean();
		StringBuffer sb = new StringBuffer();
		sb.append("'-1'");
		Map mp=new HashMap();
		List l=new ArrayList();
		try {
			List works = sf.getWorkitemListByUserId(userId);
			for (int i = 0; i < works.size(); i++) {
				WorkitemBean wib = (WorkitemBean) works.get(i);
				Object[][] obj = wib.getArgs();
				for (int i1 = 0; i1 < obj.length; i1++) {
					for (int i2 = 0; i2 < obj[i1].length-1; i2++) {
						if (obj[i1][i2].equals("APP_ID")&&obj[i1][i2+1]!=null) {
							int i3 = i2 + 1;
							Object obj1=obj[i1][i3];
							if(null!=obj[i1][i3] && !"".equals(obj[i1][i3]+"")){
								mp.put(obj[i1][i3],new Integer(wib.getWorkitemId()));
								sb.append(",");
								sb.append("'"+(String) obj[i1][i3]+"'");
								
							}
						}
					}
				}
				
			}
		} catch (DBException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		l.add(sb.toString());
		l.add(mp);
		return l;

	}

	
}

