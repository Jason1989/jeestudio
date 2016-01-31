package com.zxt.compplatform.formengine.service;


import com.zxt.framework.dictionary.entity.DataDictionary;


/**
 * 组件树控制操作接口
 * @author 007
 */
public interface ComponentsTreeService {
	/**
	 * 获取树数据
	 * @param dataDictionary
	 * @param defalutValue
	 * @return
	 */
	public String[] treeData(DataDictionary dataDictionary,String defalutValue);
	
	/**
	 * 获取树展示数据
	 * @param dataDictionary
	 * @param defalutValue
	 * @param parentId
	 * @return
	 */
	public String[] treeData(DataDictionary dataDictionary,String defalutValue,String parentId);
	
	/**
	 * 获取组织机构数据
	 * @param dataDictionary
	 * @param defaultValue
	 * @param oid
	 * @return
	 */
	public String[] treeOrgData(DataDictionary dataDictionary,String defaultValue,String oid);
	
	/**
	 * 获取人员树
	 * @param dataDictionary
	 * @param defaultValue
	 * @param oid
	 * @return
	 */
	public String[] treeHumanData(DataDictionary dataDictionary,String defaultValue,String oid);
}
