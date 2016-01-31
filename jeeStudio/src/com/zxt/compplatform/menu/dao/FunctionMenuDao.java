package com.zxt.compplatform.menu.dao;

import java.util.List;

public interface FunctionMenuDao {
	/**
	 * 查询所有的根菜单
	 * @return
	 */
     public List findParentMenus();
     /**
      *  根据父菜单查询所有的子菜单
      * @param parentId
      * @return
      */
     public List findMenus(long parentId);
     /**
      *  查询所有的菜单
      * @param parentId
      * @return
      */
     public List findMenus();
}
