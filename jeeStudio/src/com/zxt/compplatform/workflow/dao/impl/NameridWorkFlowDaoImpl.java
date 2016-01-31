package com.zxt.compplatform.workflow.dao.impl;

import com.synchrobit.synchroflow.api.util.DbTools;
import com.synchrobit.synchroflow.api.util.ReadPropertyConfig;
import com.synchrobit.synchroflow.enactmentservice.exception.DBException;

public class NameridWorkFlowDaoImpl {
	/**
	 * 登录名获得用户id
	 * @param name
	 * @return   userid String 用户id
	 */
	public static String namefindid(String name) {
		String monitorRmiStr = ReadPropertyConfig.getInstance().getDbrmi();
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

	}
}
