package com.zxt.ht.service.impl;




import com.zxt.ht.dao.PlanLimitDao;
import com.zxt.ht.service.PlanLimitSerice;

public class PlanLimitSeriviceImpl implements PlanLimitSerice{
	private static PlanLimitDao planLimitDao;
	
	public PlanLimitDao getPlanLimitDao() {
		return planLimitDao;
	}

	public void setPlanLimitDao(PlanLimitDao planLimitDao) {
		this.planLimitDao = planLimitDao;
	}

	@Override
	public void save(String sql,Object[] args) {
		planLimitDao.insertTo(sql,args);
	}

	@Override
	public String findPtXml(String sql) {
		// TODO Auto-generated method stub
		String str = planLimitDao.findPt(sql);
		return str;
		
	}
	
}
