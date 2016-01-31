package com.zxt.compplatform.formula.service;

import java.util.List;

import com.zxt.compplatform.formula.entity.Formula;

public interface FurmulServiceInterface {
	
	public Object execute(Formula formula);
	public Object execute(List formulas);
	
}
