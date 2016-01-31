package com.zxt.compplatform.form.service;

import java.util.List;

import org.jdom.Document;

import com.zxt.compplatform.form.entity.Form;

/**
 * 表单业务逻辑操作接口
 * 
 * @author 007
 */
public interface IFormService {
	/**
	 * 根据Id查找对象
	 * 
	 * @param id
	 * @return
	 */
	public Form findById(String id);

	/**
	 * 查找所有对象
	 * 
	 * @return
	 */
	public List findAll();

	/**
	 * 根据ID查找所有对象
	 * 
	 * @param ids
	 *            ID
	 * @return
	 */
	public List findAllByIds(String ids);

	/**
	 * 查找总记录数
	 * 
	 * @return
	 */
	public int findTotalRows();

	/**
	 * 分页查找
	 * 
	 * @param page
	 *            页码
	 * @param rows
	 *            每页记录数
	 * @return
	 */
	public List findAllByPage(int page, int rows);

	/**
	 * 查找总记录数
	 * 
	 * @return
	 */
	public int findTotalRowsByDataObjectId(String dataObjectId);

	/**
	 * 分页查找
	 * 
	 * @param page
	 *            页码
	 * @param rows
	 *            每页记录数
	 * @return
	 */
	public List findAllByPageAndDataObjectId(int page, int rows,
			String dataObjectId);

	/**
	 * 根据页面类型和主对象查找页面
	 * 
	 * @param mainObjectId
	 *            主对象ID
	 * @param pageType
	 *            页面类型
	 * @return
	 */
	public List findPageByTypeAndMainObjectId(String mainObjectId,
			String pageType);

	/**
	 * 插入对象
	 * 
	 * @param Form
	 */
	public void insert(Form form);

	/**
	 * 修改对象
	 * 
	 * @param Form
	 */
	public void update(Form form);

	/**
	 * 删除对象
	 * 
	 * @param Form
	 */
	public void delete(Form form);

	/**
	 * 删除对象
	 * 
	 * @param paramCollection
	 */
	public void deleteAll(List paramCollection);

	/**
	 * 根据Id删除对象
	 * 
	 * @param id
	 */
	public void deleteById(String id);

	/**
	 * 根绝数据对象的id查询出相应的编辑、查看、列表的表单xml。
	 * 
	 * @param dataObjectId
	 *            数据对象的id
	 */
	public boolean updateFormFrameByDoId(String dataObjectId, String columnId);

	/**
	 * 方法描述 通过字段名 更新全部表单
	 */
	public boolean updateAllFormBydeleteField(String dataObjectId,
			String fieldName);

	/**
	 * 方法描述 通过字段名 更新列表表单
	 */
	/*
	 * public boolean updateListFormBydeleteField(Document document,String
	 * fieldName,String formId);
	 *//**
		 * 方法描述 通过字段名 更新编辑表单
		 */
	/*
	 * public boolean updateEditFormBydeleteField(Document document,String
	 * fieldName,String formId);
	 *//**
		 * 方法描述 通过字段名 更新详情页表单
		 */
	/*
	 * public boolean updateViewFormBydeleteField(Document document,String
	 * fieldName,String formId);
	 */
	/**
	 * 更新数据表单xml
	 */
	public boolean updateFormSettingById(Document document, String formId);

	/**
	 * 更新缓存数据
	 * 
	 * @param dataObjectId
	 */
	public void updatePageService(String dataObjectId);

	/**
	 * 根据对象id删除对应的表单
	 * 
	 * @param objectId
	 */
	public void deleteAllByObjectId(String objectId);

	/**
	 * 根据数据对象id获取所有的表单
	 * 
	 * @param objectId
	 * @return
	 */
	public List findAllByObjectId(String objectId);

}
