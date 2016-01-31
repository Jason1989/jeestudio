package com.zxt.compplatform.workflow.dao;

import java.util.List;

public interface DaibanfaiWorkFlowDao {
	public List findappid(String userId);
	public List findprocessid(String userId,String sid) ;
}
