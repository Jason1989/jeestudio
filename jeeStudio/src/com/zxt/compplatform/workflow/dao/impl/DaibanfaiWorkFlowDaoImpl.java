package com.zxt.compplatform.workflow.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.synchrobit.synchroflow.api.bean.SynchroFLOWBean;
import com.synchrobit.synchroflow.api.bean.WorkitemBean;
import com.synchrobit.synchroflow.api.util.DbTools;
import com.synchrobit.synchroflow.api.util.ReadPropertyConfig;
import com.synchrobit.synchroflow.enactmentservice.exception.DBException;
import com.zxt.compplatform.workflow.dao.DaibanfaiWorkFlowDao;

public class DaibanfaiWorkFlowDaoImpl implements DaibanfaiWorkFlowDao {

	public List findappid(String userId) {
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
						if (obj[i1][i2].equals("SID")&&obj[i1][i2+1]!=null) {
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
	
	
	public List findprocessid(String userId,String sid) {
		SynchroFLOWBean sf = new SynchroFLOWBean();
		StringBuffer sb = new StringBuffer();
		Map mp=new HashMap();
		List l=new ArrayList();
		try {
			List works = sf.getWorkitemListByUserId(userId);
			for (int i = 0; i < works.size(); i++) {
				WorkitemBean wib = (WorkitemBean) works.get(i);
				Object[][] obj = wib.getArgs();
				for (int i1 = 0; i1 < obj.length; i1++) {
					for (int i2 = 0; i2 < obj[i1].length-1; i2++) {
						if (obj[i1][i2].equals("SID")) {
							int i3 = i2 + 1;
							Object obj1=obj[i1][i3];
							if(sid.equals(obj1)){
								mp.put(obj[i1][i3],new Integer(wib.getProcessInsId()));
								sb.append("'"+(String) obj[i1][i3]+"'");
								sb.append(",");
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

	public List findhprocessid(String userId,String sid){
		SynchroFLOWBean sf = new SynchroFLOWBean();
		StringBuffer sb = new StringBuffer();
		Map mp=new HashMap();
		List l=new ArrayList();
		try {
			String sql="select distinct g.process_ins_id from t_relevant_data t ,t_workitem_his g where t.data_value='"+sid+"' and t.process_ins_id=g.process_ins_id";
			String monitorRmiStr = ReadPropertyConfig.getInstance().getDbrmi();

			Object obj[][] = DbTools.queryDb(monitorRmiStr, sql);
			//mp.put(obj[i1][i3],new Integer(wib.getProcessInsId()));
			sb.append("'"+(String) obj[0][0]+"'");
			sb.append(",");
			/*for (int i = 0; i < obj.length; i++) {
				for (int j = 0; j < obj.length; j++) {
					userid=(String) obj[i][j];
				}
			}*/

			/*List works = sf.getHWorkitemListByUserId(userId);
			for (int i = 0; i < works.size(); i++) {
				WorkitemBean wib = (WorkitemBean) works.get(i);
				//Object[][] obj = wib.getArgs();
				for (int i1 = 0; i1 < obj.length; i1++) {
					for (int i2 = 0; i2 < obj[i1].length-1; i2++) {
						if (obj[i1][i2].equals("SID")) {
							int i3 = i2 + 1;
							Object obj1=obj[i1][i3];
							if(sid.equals(obj1)){
								mp.put(obj[i1][i3],new Integer(wib.getProcessInsId()));
								sb.append("'"+(String) obj[i1][i3]+"'");
								sb.append(",");
							}
						}
					}
				}

			}*/
		} catch (DBException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		l.add(sb.toString());
		l.add(mp);
		return l;
	}



	/*
	 * String monitorRmiStr = ReadPropertyConfig.getInstance().getDbrmi();
		StringBuffer s=new StringBuffer();
		s.append("select userid from t_usertable where username='");
		s.append(name);
		s.append("'");
		String userid = null; 
		try {
			Object obj[][] = DbTools.queryDb(monitorRmiStr, s.toString());
			for (int i = 0; i < obj.length; i++) {
				for (int j = 0; j < obj.length; j++) {
					userid=(String) obj[i][j];
				}
			}
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return userid;
	 */
}

