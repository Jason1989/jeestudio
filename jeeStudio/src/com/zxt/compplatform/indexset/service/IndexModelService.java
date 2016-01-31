package com.zxt.compplatform.indexset.service;

import java.util.List;

import com.zxt.compplatform.indexset.entity.ModelPart;

public interface IndexModelService {

	public void add(ModelPart model);

	public void update(ModelPart model);

	public void delete(ModelPart model);

	public List findAll();
	
	public void deleteAll(List paramCollection);
	
	public void deleteById(String id);
	
	public List findAllByIds(String ids);
	
	public ModelPart findById(String id);
	
	public int findTotalRows();

	public List findAllByPage(int page, int rows);

	
}
