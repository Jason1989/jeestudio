package com.zxt.framework.dictionary.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.zxt.compplatform.formengine.entity.view.TreeData;
import com.zxt.compplatform.formengine.util.TreeUtil;
import com.zxt.framework.common.util.RandomGUID;
import com.zxt.framework.dictionary.entity.DataDictionary;
import com.zxt.framework.dictionary.entity.DictionaryGroup;
import com.zxt.framework.dictionary.service.IDataDictionaryService;

public class DictionaryGroupAction {
	private IDataDictionaryService dataDictionaryService;
	private DictionaryGroup dictionaryGroup;
	/**
	 * 列表入口
	 * @return
	 */
	public String toList(){
		return "list";		
	}
	/**
	 * 列表
	 * GUOWEIXIN 和下方方法list功能一样。只不过此处返回Tree
	 */
	public String listTree(){
		HttpServletResponse res = ServletActionContext.getResponse();
		HttpServletRequest req = ServletActionContext.getRequest();
		int page = 1;
		if(req.getParameter("page") != null && !req.getParameter("page").equals("")){
			page = Integer.parseInt(req.getParameter("page"));
		}
		int rows = 0;
		if(req.getParameter("rows") != null && !req.getParameter("rows").equals("")){
			rows = Integer.parseInt(req.getParameter("rows"));
		}
		int totalRows = dataDictionaryService.findDictGroupTotalRows();
		List dictionaryGroupList = dataDictionaryService.findAllDictGroupByPage(page,20);
		
		//加上树
		List<TreeData> listTree=new ArrayList<TreeData>();
		TreeData treeRoot=new TreeData();
		treeRoot.setId("1");
		treeRoot.setText("字典分组/查看所有");
		listTree.add(treeRoot);
		for (int i = 0; i < dictionaryGroupList.size(); i++) {
			TreeData treeData = new TreeData();
			DictionaryGroup dictionaryGroup=(DictionaryGroup)dictionaryGroupList.get(i);
			try{
			treeData.setId(dictionaryGroup.getId());
			treeData.setText(dictionaryGroup.getName());
			treeData.setParentID("1");
			//treeData.setFlag(dictionaryGroup.getId()); //此处用于存入其ID
			listTree.add(treeData);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		List list = new ArrayList();
		list = TreeUtil.treeAlgorithmForTreeData(listTree, "1");
				
		JSONArray jsonarray = JSONArray.fromObject(list);
		try {
			String jsonStr=jsonarray.toString(); 
			res.getWriter().write(jsonarray.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 列表
	 * @return
	 */
	public String list(){
		HttpServletResponse res = ServletActionContext.getResponse();
		HttpServletRequest req = ServletActionContext.getRequest();
		int page = 1;
		if(req.getParameter("page") != null && !req.getParameter("page").equals("")){
			page = Integer.parseInt(req.getParameter("page"));
		}
		int rows = 0;
		if(req.getParameter("rows") != null && !req.getParameter("rows").equals("")){
			rows = Integer.parseInt(req.getParameter("rows"));
		}
		int totalRows = dataDictionaryService.findDictGroupTotalRows();
		List dictionaryGroupList = dataDictionaryService.findAllDictGroupByPage(page,rows);
		Map map = new HashMap();
		if(dictionaryGroupList != null){
			map.put("rows", dictionaryGroupList);
			map.put("total", new Integer(totalRows));
		}
		String dataJson = JSONObject.fromObject(map).toString();
		try {
			res.getWriter().write(dataJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 跳转添加页面
	 * @return
	 */
	public String toAdd(){
		return "toadd";
	}
	/**
	 * 添加
	 * @return
	 */
	public String add(){
		HttpServletResponse res = ServletActionContext.getResponse();
		if(dictionaryGroup.getId() == null || dictionaryGroup.getId().equals("")){
			dictionaryGroup.setId(RandomGUID.geneGuid());
		}
		try {
			if(dataDictionaryService.findDictGroupById(dictionaryGroup.getId()) != null){				
				res.getWriter().write("exist");
			}
			else if(dataDictionaryService.findGroupByName(dictionaryGroup.getName())!=null){
				res.getWriter().write("groupExist");	
			}
			else{
				dataDictionaryService.insertDictGroup(dictionaryGroup);
				res.getWriter().write("success");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 跳转修改页面
	 * @return
	 */
	public String toUpdate(){
		HttpServletRequest req = ServletActionContext.getRequest();
		String dictionaryGroupId = req.getParameter("dictionaryGroupId");
		if(dictionaryGroupId!= null && !dictionaryGroupId.equals("")){
			dictionaryGroup = dataDictionaryService.findDictGroupById(dictionaryGroupId);
		}
		return "toupdate";
	}
	/**
	 * 修改
	 * @return
	 */
	public String update(){
		HttpServletResponse res = ServletActionContext.getResponse();
		try {
			String groupId = dictionaryGroup.getId();
			String groupName = dictionaryGroup.getName();
			boolean flag = dataDictionaryService.isDicGroupExistUpdate(groupId, groupName);
			if(flag){
				res.getWriter().write("failure");	
			}
			else{
				dataDictionaryService.updateDictGroup(dictionaryGroup);
				res.getWriter().write("success");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 删除
	 * @return
	 */
	public String delete(){
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		String dictionarygGroupId = req.getParameter("dictionaryGroupId");
		String dictionaryGroupIds = req.getParameter("dictionaryGroupIds");
		try {
			/**
			 * 数据对象分组ID
			 */
			if(dictionarygGroupId!= null && !dictionarygGroupId.equals("")){
				List dictionaryList = dataDictionaryService.findByDictGroupId(dictionarygGroupId);
				if(dictionaryList != null && dictionaryList.size()>0){
					res.getWriter().write("error");
				}else{
					dataDictionaryService.deleteDictGroupById(dictionarygGroupId);
					res.getWriter().write("success");
				}
			}
			/**
			 * 数据对象分组id
			 */
			if(dictionaryGroupIds!= null && !dictionaryGroupIds.equals("")){
				List dictionaryList = dataDictionaryService.findAllByGroupIds(dictionaryGroupIds);
				if(dictionaryList != null && dictionaryList.size()>0){
					res.getWriter().write("error");
				}else{
					dataDictionaryService.deleteAllDictGroup(dataDictionaryService.findAllDictGroupByIds(dictionaryGroupIds));
					res.getWriter().write("success");
				}
			}
		} catch (IOException e) {				
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取全部数据对象分组
	 * @return
	 */
	public String getAllItem(){
		HttpServletResponse res = ServletActionContext.getResponse();
		try{
			/**
			 * 数据对象分组
			 */
			List list = dataDictionaryService.findAllDictGroup();
			DictionaryGroup dictionaryGroup = new DictionaryGroup();
			dictionaryGroup.setId("-1");
			dictionaryGroup.setName("请选择");
			dictionaryGroup.setSortNo(new Integer(-1));
			list.add(0,dictionaryGroup);
			/**
			 * 设置数据对象根节点
			 */
			String dataJson = JSONArray.fromObject(list).toString();		
			res.getWriter().write(dataJson);
		} catch (IOException e) {
			e.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	public String nameExist() {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setContentType("text/plain;charset=UTF-8 ");

		String name = req.getParameter("name");

		DictionaryGroup dataObjGroup = dataDictionaryService.findGroupByName(name);

		try {
			if (dataObjGroup != null) {
				resp.getWriter().write("exist");
			} else {
				resp.getWriter().write("unexist");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
	public void setDataDictionaryService(
			IDataDictionaryService dataDictionaryService) {
		this.dataDictionaryService = dataDictionaryService;
	}

	public DictionaryGroup getDictionaryGroup() {
		return dictionaryGroup;
	}
	public void setDictionaryGroup(DictionaryGroup dictionaryGroup) {
		this.dictionaryGroup = dictionaryGroup;
	}
}
