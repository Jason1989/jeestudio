package com.zxt.compplatform.datacolumn.service;

import java.util.List;

import com.zxt.compplatform.datacolumn.entity.DataColumn;

/**
 * 数据对象列操作接口
 * 
 * @author 007
 */
public interface IDataColumnService {
	/**
	 * 根据Id查找对象
	 * 
	 * @param id
	 * @return
	 */
	public DataColumn findById(String id);

	/**
	 * 判断对象是否存在
	 * 
	 * @param id
	 * @param name
	 * @return
	 */
	public boolean isExist(String id, String name);

	/**
	 * 判断对象是否存在
	 * 
	 * @param id
	 * @param name
	 * @return
	 */
	public boolean isExist(String id, String name, String tableName);

	/**
	 * 判断对象是否存在
	 * 
	 * @param id
	 * @param name
	 * @return
	 */
	public boolean isExistUpdate(String id, String name, String datatableId);

	/**
	 * 根据ID查找所有对象
	 * 
	 * @param ids
	 * @return
	 */
	public List findAllByIds(String ids);

	/**
	 * 查找所有对象
	 * 
	 * @return
	 */
	public List findAll();

	/**
	 *  根据 表的 名称（非ID）得到其列
	 * @GUOWEIXIN
	 */
	public List findAllByTableNames(List tableIds, String isTemp);

	/**
	 * 根据TableId查找所有对象
	 * 
	 * @return
	 */
	public List findAllByTableId(String tableId);

	/**
	 * 创建表单 检查是否包含主键
	 */
	public String checkTableIsParmerkey(String tableId);

	/**
	 * 根据TableName查找所有对象
	 * 
	 * @return
	 */
	public List findAllByTableName(String tableName);

	/**
	 * 根据TableIds查找所有对象
	 * 
	 * @return
	 */
	public List findAllByTableIds(String tableIds, String isTemp);

	/**
	 * 查找总记录数
	 * 
	 * @return
	 */
	public int findTotalRowsByTableId(String tableId);

	/**
	 * 查找总记录数
	 * 
	 * @return
	 */
	public int findTotalRowsByTableIds(String tableIds);

	/**
	 * 查找总记录数,判断字段是不是已经被导入
	 * 
	 * @return
	 */
	public int findTotalRowsByTableId(String tableIds, String isTemp);

	/**
	 * 分页查找
	 * 
	 * @param page
	 *            页码
	 * @param rows
	 *            每页记录数
	 * @return
	 */
	public List findAllByPageAndTableId(int page, int rows, String tableId);

	/**
	 * GUOWEIXIN
	 * tableIdArr:存放表的ID数组。查找里面有 ID的字段列全部显示
	 * select * from ENG_DATAOBJECT_COLUMN  where DF_DO_ID in('34333d9330a1ba7c343c92842eb08b41' ,'11ebc66b9d5864fed961f227a3a0e3b1')
	 */
	public int findTotalRowsByTableIdS(String[] tableIdArr, String isTemp) ;
	/**
	 * GUOWEIXIN
	 * tableIdArr:存放表的ID数组。查找里面有 ID的字段列全部显示
	 * select * from ENG_DATAOBJECT_COLUMN  where DF_DO_ID in('34333d9330a1ba7c343c92842eb08b41' ,'11ebc66b9d5864fed961f227a3a0e3b1')
	 */
	public List findAllByPageAndTableIdS(int page, int rows, String[] tableIdArr,
			String isTemp);
	
	/**
	 * 分页查找判断字段是不是已经被导入
	 * 
	 * @param page
	 *            页码
	 * @param rows
	 *            每页记录数
	 * @return
	 */
	public List findAllByPageAndTableId(int page, int rows, String tableId,
			String isTemp);

	/**
	 * 插入对象
	 * 
	 * @param dataColumn
	 */
	public void insert(DataColumn dataColumn);

	/**
	 * 修改对象
	 * 
	 * @param dataColumn
	 */
	public void update(DataColumn dataColumn);

	/**
	 * 删除对象
	 * 
	 * @param dataColumn
	 */
	public void delete(DataColumn dataColumn);

	/**
	 * 根据Id删除对象
	 * 
	 * @param id
	 */
	public void deleteById(String id);

	/**
	 * 删除对象
	 * 
	 * @param paramCollection
	 */
	public void deleteAll(List paramCollection);

	/**
	 * 批量更新列的状态
	 * 
	 * @param ids
	 * @param state
	 */
	public void updateBatchCloumnTemp(String ids, String state);

	/**
	 * 通过数据对象的id和状态查找数据列
	 * 
	 * @param tableId
	 * @param status
	 * @return
	 */
	public List findAllByTableIdAndStatus(String tableId, String status);

}
