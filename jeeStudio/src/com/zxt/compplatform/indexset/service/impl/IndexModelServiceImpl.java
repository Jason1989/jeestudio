package com.zxt.compplatform.indexset.service.impl;

import java.util.List;

import com.zxt.compplatform.indexset.dao.IndexModelDao;
import com.zxt.compplatform.indexset.entity.ModelPart;
import com.zxt.compplatform.indexset.service.IndexModelService;

public class IndexModelServiceImpl implements IndexModelService {

	private IndexModelDao indexModelDao;

	public void add(ModelPart model) {
		indexModelDao.create(model);
	}

	public void delete(ModelPart model) {
		indexModelDao.delete(model);
	}
	
	public void deleteById(String id) {
		indexModelDao.delete(findById(id));
	}
	
	public void update(ModelPart model) {
		indexModelDao.update(model);
	}

	public List findAll() {
		return indexModelDao.find("from ModelPart");
	}

	public ModelPart findById(String id) {
		String paramString = " from ModelPart m where m.id = '" + id + "' ";
		List list = indexModelDao.find(paramString);
		if(list != null && list.size()>0){
			return (ModelPart) list.get(0);
		}
		return null;
	}
	
	public void deleteAll(List paramCollection) {
		indexModelDao.deleteAll(paramCollection);
	}

	public List findAllByIds(String ids) {
		String paramString = " from ModelPart mp where mp.id in (" + ids + ") ";
		return indexModelDao.find(paramString);
	}

	public List findAllByPage(int page, int rows) {
		String paramString = " from ModelPart mp order by mp.id ";
		return indexModelDao.findAllByPage(paramString,page,rows);
	}

	public int findTotalRows() {
		String queryString = " select count(id) from ModelPart mp"; 
		return indexModelDao.findTotalRows(queryString);
	}

	// *************************getters and setters*****************************

	public IndexModelDao getIndexModelDao() {
		return indexModelDao;
	}

	public void setIndexModelDao(IndexModelDao indexModelDao) {
		this.indexModelDao = indexModelDao;
	}

	

	

}
