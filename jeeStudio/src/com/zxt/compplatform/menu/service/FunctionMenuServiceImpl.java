package com.zxt.compplatform.menu.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import com.zxt.compplatform.datatable.entity.DataObjectGroup;
import com.zxt.compplatform.datatable.service.IDataObjectGroupService;
import com.zxt.compplatform.datatable.service.IDataTableService;
import com.zxt.compplatform.formengine.action.ComponentsAction;
import com.zxt.compplatform.menu.dao.FunctionMenuDao;
import com.zxt.compplatform.menu.entity.EngFunctionMenu;
import com.zxt.compplatform.menu.entity.TreeDataJson;

public class FunctionMenuServiceImpl implements FunctionMenuService {
	private FunctionMenuDao functionMenuDao;
	private static final Logger log = Logger.getLogger(ComponentsAction.class);
	private IDataObjectGroupService dataObjectGroupService;
	private IDataTableService dataTableService;
	private Map dataTableMap = new HashMap();

	public void setDataObjectGroupService(
			IDataObjectGroupService dataObjectGroupService) {
		this.dataObjectGroupService = dataObjectGroupService;
	}

	public void setFunctionMenuDao(FunctionMenuDao functionMenuDao) {
		this.functionMenuDao = functionMenuDao;
	}

	public String load_findMenuJson(long parentId) {
		log.info("更新数据对象分组菜单_load");
		List menus = functionMenuDao.findMenus();
		TreeDataJson treeDataJson = new TreeDataJson();
		treeDataJson.setId(parentId + "");
		iterateList(treeDataJson, menus);
		List treeDatas = treeDataJson.getChildren();
		if (null == treeDatas) {
			treeDatas = new ArrayList();
		}
		if (parentId == EngFunctionMenu.MENU_BASE_DATA_MANAGE_ID
				|| parentId == EngFunctionMenu.MENU_FORM_MANAGE_ID) {
			DataObjectGroup root = dataObjectGroupService
					.findById(DataObjectGroup.DATA_OBJECT_GROUP_ROOT_ID);
			List dataObjectGroups = dataObjectGroupService.findAll();
			TreeDataJson groupJson = new TreeDataJson();
			groupJson.setId(root.getId());
			String doNumber = "0";
			if (null != dataTableMap.get(root.getId())) {
				doNumber = dataTableMap.get(root.getId()).toString();
			}
			groupJson.setText(root.getName() + "<font color='blue'>("
					+ doNumber + ")</font>");
			Map attributes = new HashMap();

			groupJson.setAttributes(attributes);
			String url = "";
			if (parentId == EngFunctionMenu.MENU_BASE_DATA_MANAGE_ID) {
				url = "datatable/dataTable!toList.action?groupId=";
				iterateDataObjectGroup(groupJson, dataObjectGroups, url);
				treeDatas.add(
						EngFunctionMenu.MENU_DATA_OBJECT_MANAGE_SORT_INDEX,
						groupJson);
			} else {
				url = "form/form!menuDOList.action?groupId=";
				iterateDataObjectGroup(groupJson, dataObjectGroups, url);
				treeDatas.add(groupJson);
			}
			attributes.put("url", url + "1");
		}
		String json = "";
		if (null != treeDatas && treeDatas.size() > 0) {
			json = JSONArray.fromObject(treeDatas).toString();
		}
		return json;
	}
	public String update_findMenuJson(long parentId) {
		List menus = functionMenuDao.findMenus();
		TreeDataJson treeDataJson = new TreeDataJson();
		treeDataJson.setId(parentId + "");
		iterateList(treeDataJson, menus);
		List treeDatas = treeDataJson.getChildren();
		if (null == treeDatas) {
			treeDatas = new ArrayList();
		}
		if (parentId == EngFunctionMenu.MENU_BASE_DATA_MANAGE_ID
				|| parentId == EngFunctionMenu.MENU_FORM_MANAGE_ID || parentId==EngFunctionMenu.MENU_CODE_GENERATE) {
			DataObjectGroup root = dataObjectGroupService
					.findById(DataObjectGroup.DATA_OBJECT_GROUP_ROOT_ID);
			List dataObjectGroups = dataObjectGroupService.findAll();
			TreeDataJson groupJson = new TreeDataJson();
			groupJson.setId(root.getId());
			String doNumber = "0";
			if (null != dataTableMap.get(root.getId())) {
				doNumber = dataTableMap.get(root.getId()).toString();
			}
			groupJson.setText(root.getName() + "<font color='blue'>("
					+ doNumber + ")</font>");
			Map attributes = new HashMap();

			groupJson.setAttributes(attributes);
			String url = "";
			if (parentId == EngFunctionMenu.MENU_BASE_DATA_MANAGE_ID) {
				url = "datatable/dataTable!toList.action?groupId=";
				iterateDataObjectGroup(groupJson, dataObjectGroups, url);
				treeDatas.add(
						EngFunctionMenu.MENU_DATA_OBJECT_MANAGE_SORT_INDEX,
						groupJson);
			}else if (parentId == EngFunctionMenu.MENU_FORM_MANAGE_ID){
				url = "form/form!menuDOList.action?groupId=";
				iterateDataObjectGroup(groupJson, dataObjectGroups, url);
				treeDatas.add(groupJson);
			}
			else if (parentId == EngFunctionMenu.MENU_CODE_GENERATE){ //用于菜单中 用于 代码生成 列表
				url = "form/form!menuDOList.action?codeFlag=codeFlag&groupId=";
				iterateDataObjectGroup(groupJson, dataObjectGroups, url);
				treeDatas.add(groupJson);
			}
			attributes.put("url", url + "1");
		}
		String json = "";
		if (null != treeDatas && treeDatas.size() > 0) {
			json = JSONArray.fromObject(treeDatas).toString();
		}
		return json;
	}
	public List findParentMenus() {
		return functionMenuDao.findParentMenus();
	}

	private void iterateList(TreeDataJson treeDataJson, List menus) {
		String parentId = treeDataJson.getId();
		List childrenModel = null;
		Iterator iterator = menus.iterator();
		while (iterator.hasNext()) {
			EngFunctionMenu menu = (EngFunctionMenu) iterator.next();
			String groupParentId = String.valueOf(menu.getMenuParentId());
			if (groupParentId.equals(parentId)) {
				childrenModel = treeDataJson.getChildren();
				if (childrenModel == null) {
					childrenModel = new ArrayList();
				}
				TreeDataJson childModel = new TreeDataJson();
				childModel.setId(menu.getMenuId() + "");
				childModel.setText(menu.getMenuName());
				childModel.setIconCls(menu.getMenuIcon() == null ? "":menu.getMenuIcon());
				childModel
						.setSortIndex(menu.getMenuSort() == null ? Long.MAX_VALUE
								: Long.parseLong(menu.getMenuSort() + ""));
				Map attributes = new HashMap();
				attributes.put("url", menu.getMenuUrl());
				childModel.setAttributes(attributes);
				childrenModel.add(childModel);
				iterator.remove();
				Collections.sort(childrenModel);
				treeDataJson.setChildren(childrenModel);
			}
		}
		if (childrenModel != null && !childrenModel.isEmpty()
				&& !menus.isEmpty()) {
			Iterator treeIt = childrenModel.iterator();
			while (treeIt.hasNext()) {
				TreeDataJson model = (TreeDataJson) treeIt.next();
				iterateList(model, menus);
			}
		}
	}

	private void iterateDataObjectGroup(TreeDataJson treeDataJson,
			List dataObjectGroups, String url) {
		String parentId = treeDataJson.getId();
		List childrenModel = null;
		Iterator iterator = dataObjectGroups.iterator();
		while (iterator.hasNext()) {
			DataObjectGroup group = (DataObjectGroup) iterator.next();
			String groupParentId = group.getPid();
			if (groupParentId.equals(parentId)) {
				childrenModel = treeDataJson.getChildren();
				if (childrenModel == null) {
					childrenModel = new ArrayList();
				}
				TreeDataJson childModel = new TreeDataJson();
				childModel.setId(group.getId() + "");
				String doNumber = "0";
				if (null != dataTableMap.get(group.getId())) {
					doNumber = dataTableMap.get(group.getId()).toString();
				}
				childModel.setText(group.getName() + "<font color='blue'>("
						+ doNumber + ")</font>");
				childModel
						.setSortIndex(group.getSortNo() == null ? Long.MAX_VALUE
								: Long.parseLong(group.getSortNo() + ""));
				Map attributes = new HashMap();
				attributes.put("url", url + group.getId());
				childModel.setAttributes(attributes);
				childrenModel.add(childModel);
				iterator.remove();
				Collections.sort(childrenModel);
				treeDataJson.setChildren(childrenModel);
			}
		}
		if (childrenModel != null && !childrenModel.isEmpty()
				&& !dataObjectGroups.isEmpty()) {
			Iterator treeIt = childrenModel.iterator();
			while (treeIt.hasNext()) {
				TreeDataJson model = (TreeDataJson) treeIt.next();
				iterateDataObjectGroup(model, dataObjectGroups, url);
			}
		}
	}

	public String findMenuJson() {
		List menus = functionMenuDao.findMenus();
		TreeDataJson childModel = new TreeDataJson();
		childModel.setId(EngFunctionMenu.MENU_ROOT_PARENT_ID + "");
		childModel.setText("菜单管理");
		iterateList(childModel, menus);
		String json = JSONArray.fromObject(childModel).toString();
		return json;
	}

	public IDataTableService getDataTableService() {
		return dataTableService;
	}

	public void setDataTableService(IDataTableService dataTableService) {
		this.dataTableService = dataTableService;
	}

	public Map getDataTableMap() {
		return dataTableMap;
	}

	public void setDataTableMap(Map dataTableMap) {
		this.dataTableMap = dataTableMap;
	}

	public FunctionMenuDao getFunctionMenuDao() {
		return functionMenuDao;
	}

	public IDataObjectGroupService getDataObjectGroupService() {
		return dataObjectGroupService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map load_findDataObjectToMap(String isCache) {
		// TODO Auto-generated method stub
		log.info("更新数据对象分组的 数据对象数量 加入缓存load_");
		dataTableMap.clear();
		List groupList = dataObjectGroupService.findAll();
		if (groupList != null) {
			Iterator groupIt = groupList.iterator();
			while (groupIt.hasNext()) {
				DataObjectGroup dataObjectGroup = (DataObjectGroup) groupIt
						.next();
				List groupIdList = dataObjectGroupService
						.findChildrenIdById(dataObjectGroup.getId());
				String groupIds = "";
				int totalRows = 0;
				if (groupIdList != null) {
					for (int i = 0; i < groupIdList.size(); i++) {
						groupIds += groupIds == "" ? "'" + groupIdList.get(i)
								+ "'" : ",'" + groupIdList.get(i) + "'";
					}
					totalRows = dataTableService
							.findTotalRowsByGroupIds(groupIds);
				}
				dataTableMap.put(dataObjectGroup.getId(),
						new Integer(totalRows));
			}
		}
		return dataTableMap;
	}

	@Override
	public Map update_findDataObjectToMap(String isCache) {
		// TODO Auto-generated method stub
		log.info("更新数据对象分组的 数据对象数量 加入缓存");
		dataTableMap.clear();
		List groupList = dataObjectGroupService.findAll();
		if (groupList != null) {
			Iterator groupIt = groupList.iterator();
			while (groupIt.hasNext()) {
				DataObjectGroup dataObjectGroup = (DataObjectGroup) groupIt
						.next();
				List groupIdList = dataObjectGroupService
						.findChildrenIdById(dataObjectGroup.getId());
				String groupIds = "";
				int totalRows = 0;
				if (groupIdList != null) {
					for (int i = 0; i < groupIdList.size(); i++) {
						groupIds += groupIds == "" ? "'" + groupIdList.get(i)
								+ "'" : ",'" + groupIdList.get(i) + "'";
					}
					totalRows = dataTableService
							.findTotalRowsByGroupIds(groupIds);
				}
				dataTableMap.put(dataObjectGroup.getId(),
						new Integer(totalRows));
			}
		}
		return dataTableMap;
	}

}
