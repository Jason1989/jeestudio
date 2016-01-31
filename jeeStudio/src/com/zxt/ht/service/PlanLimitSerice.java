package com.zxt.ht.service;


public interface PlanLimitSerice {
	public void save(String sql,Object[] args);
	public String findPtXml(String sql);
}
