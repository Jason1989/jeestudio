package com.zxt.compplatform.validationrule.service;

import java.util.List;

import com.zxt.compplatform.validationrule.entity.ValidationRule;

public interface IValidationRuleService {
	/**
	 * 根据Id查找对象
	 * @param id
	 * @return
	 */
	public ValidationRule findById(String id);
	/**
	 * 判断对象是否存在
	 * @param id
	 * @param name
	 * @return
	 */
	public boolean isExist(String id,String name);
	/**
	 * 判断对象是否存在
	 * @param id
	 * @param name
	 * @return
	 */
	public boolean isExistUpdate(String id,String name);
	/**
	 * 根据ID查找所有对象
	 * @param ids  
	 * @return
	 */
	public List findAllByIds(String ids);
	/**
	 * 查找所有对象
	 * @return
	 */
	public List findAll();
	/**
	 * 查找总记录数
	 * @return
	 */
	public int findTotalRows();
	/**
	 * 分页查找
	 * @param page 页码
	 * @param rows 每页记录数
	 * @return
	 */
	public List findAllByPage(int page,int rows);
	/**
	 * 插入对象
	 * @param ValidationRule
	 */
	public void insert(ValidationRule validationRule);
	/**
	 * 修改对象
	 * @param ValidationRule
	 */
	public void update(ValidationRule validationRule);
	/**
	 * 删除对象
	 * @param ValidationRule
	 */
	public void delete(ValidationRule validationRule);
	/**
	 * 根据Id删除对象
	 * @param id
	 */
	public void deleteById(String id);
	/**
	 * 删除对象
	 * @param paramCollection
	 */
	public void deleteAll(List paramCollection);
}
