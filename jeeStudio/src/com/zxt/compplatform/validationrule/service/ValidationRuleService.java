package com.zxt.compplatform.validationrule.service;

import java.util.List;

import com.zxt.compplatform.validationrule.dao.IValidationRuleDao;
import com.zxt.compplatform.validationrule.entity.ValidationRule;

public class ValidationRuleService implements IValidationRuleService {
	
	private IValidationRuleDao validationRuleDao;
	
	public void delete(ValidationRule validationRule) {
		validationRuleDao.delete(validationRule);
	}

	public void deleteById(String id) {
		validationRuleDao.delete(findById(id));
	}
	public void deleteAll(List paramCollection) {
		validationRuleDao.deleteAll(paramCollection);
	}
	public List findAll() {
		String paramString = " from ValidationRule t "; 
		return validationRuleDao.find(paramString);
	}

	public ValidationRule findById(String id) {
		String paramString = " from ValidationRule t where t.id = '" + id + "' ";
		List list = validationRuleDao.find(paramString);
		if(list != null && list.size()>0){
			return (ValidationRule) list.get(0);
		}
		return null;
	}
	public boolean isExist(String id,String name){
		String paramString = " from ValidationRule t where t.id = '" + id + "' or t.name = '" + name + "' ";
		List list = validationRuleDao.find(paramString);
		if(list != null && list.size()>0){
			return true;
		}
		return false;
	}

	public boolean isExistUpdate(String id,String name){
		String paramString = " from ValidationRule t where t.id <> '" + id + "' and t.name = '" + name + "' ";
		List list = validationRuleDao.find(paramString);
		if(list != null && list.size()>0){
			return true;
		}
		return false;
	}
	public List findAllByIds(String ids){
		String paramString = " from ValidationRule t where t.id in (" + ids + ")"; 
		return validationRuleDao.find(paramString);
	}
	public int findTotalRows(){
		String queryString = " select count(id) from ValidationRule t "; 
		return validationRuleDao.findTotalRows(queryString);
	}
	public List findAllByPage(int page,int rows){
		String paramString = " from ValidationRule t ";
		return validationRuleDao.findAllByPage(paramString,page,rows);
	}
	public void insert(ValidationRule validationRule) {
		validationRuleDao.create(validationRule);
	}

	public void update(ValidationRule validationRule) {
		validationRuleDao.update(validationRule);
	}

	public void setValidationRuleDao(IValidationRuleDao validationRuleDao) {
		this.validationRuleDao = validationRuleDao;
	}

}
