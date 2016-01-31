package com.zxt.compplatform.formengine.dao;

import com.zxt.compplatform.formengine.entity.view.TreeData;

/**
 * 桌面版系统持久化dao
 * @author 007
 */
public interface DeskTopDao {
	/**
	 * 查找菜单对象
	 * 
	 * @param menuID
	 * @return
	 */
	public TreeData findTreeData(String menuID);
}
