package com.zxt.compplatform.workflow.Util;

import com.synchrobit.synchroflow.api.util.DbTools;
import com.synchrobit.synchroflow.api.util.ReadPropertyConfig;
import com.synchrobit.synchroflow.enactmentservice.exception.DBException;

public class Idchangename {
	public String namefindid(int userid) {
		String monitorRmiStr = ReadPropertyConfig.getInstance().getDbrmi();
		StringBuffer s = new StringBuffer();
		s.append("select username from t_usertable where userid='");
		s.append(userid);
		s.append("'");
		String name = null;
		try {
			Object obj[][] = DbTools.queryDb(monitorRmiStr, s.toString());
			for (int i = 0; i < obj.length; i++) {
				for (int j = 0; j < obj.length; j++) {
					name = (String) obj[i][j];
				}
			}
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return name;

	}
}
