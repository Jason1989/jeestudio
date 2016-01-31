package com.zxt.ht.dao;


public interface PlanLimitDao  {
	public void insertTo(String sql,Object[] args);
	public String findPt(String sql);
}
