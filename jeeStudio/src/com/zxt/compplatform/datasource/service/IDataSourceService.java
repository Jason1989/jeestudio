package com.zxt.compplatform.datasource.service;

import java.sql.SQLException;
import java.util.List;

import com.zxt.compplatform.datasource.entity.DataSource;

/**
 * 数据源业务操作接口实现
 * @author 007
 */
public interface IDataSourceService {
	/**
	 * 根据Id查找对象
	 * @param id
	 * @return
	 */
	public DataSource findById(String id);
	/**
	 * 判断数据源是否存在
	 * @param id
	 * @param name
	 * @return
	 */
	public boolean isExist(String id,String name);
	/**
	 * 判断数据源是否存在
	 * @param id
	 * @param name
	 * @return
	 */
	public boolean isExistUpdate(String id,String name);
	/**
	 * 根据Name查找对象
	 * @param name
	 * @return
	 */
	public DataSource findByName(String name);
	/**
	 * 查找所有对象
	 * @return
	 */
	public List findAll();
	/**
	 * 查找所有可用对象
	 * @return
	 */
	public List findAllAvailable();
	/**
	 * 根据ID查找所有对象
	 * @param ids ID
	 * @return
	 */
	public List findAllByIds(String ids);
	
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
	 * @param dataSource
	 */
	public void insert(DataSource dataSource);
	/**
	 * 修改对象
	 * @param dataSource
	 */
	public void update(DataSource dataSource);
	/**
	 * 删除对象
	 * @param dataSource
	 */
	public void delete(DataSource dataSource);
	/**
	 * 删除对象
	 * @param paramCollection
	 */
	public void deleteAll(List paramCollection);
	/**
	 * 根据Id删除对象
	 * @param id
	 */
	public void deleteById(String id);
	
	/**
	 * 测试sql是不是可以运行
	 * @param datasourceID
	 * @param sql
	 * @return
	 */
	public List testSQL(String datasourceID,String sql) throws SQLException ;
	/**
	 * 查询数据源是否被使用
	 * @param ids
	 * @return
	 */
	public String checkDataSourceIsUse(String  ids);
}
