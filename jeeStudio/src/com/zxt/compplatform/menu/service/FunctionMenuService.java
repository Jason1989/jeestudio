package com.zxt.compplatform.menu.service;

import java.util.List;
import java.util.Map;

public interface FunctionMenuService {
   /**
    * 根据父菜单返回子菜单的json
    * @param parentId
    * @return
    */
   public String load_findMenuJson(long parentId);
   /**
    * 根据父菜单返回子菜单的json
    * @param parentId
    * @return
    */
   public String update_findMenuJson(long parentId);
   /**
    * 所有子菜单
    * @param parentId
    * @return
    */
   public String findMenuJson();
   /**
    * 查询所有根菜单
    * @return
    */
   public List findParentMenus();
   /**
    * 
    */
   public Map load_findDataObjectToMap(String isCache);
   /**
    * 
    */
   public Map update_findDataObjectToMap(String isCache);
}
