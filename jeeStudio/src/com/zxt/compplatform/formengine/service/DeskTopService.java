package com.zxt.compplatform.formengine.service;

import com.zxt.compplatform.formengine.entity.view.TreeData;

/**
 * 桌面版系统业务操作接口
 * 
 * @author 007
 */
public interface DeskTopService {
	/**
	 * 查找菜单对象
	 * 
	 * @param menuID
	 * @return
	 */
	public TreeData findTreeData(String menuID);
}
