package com.zxt.compplatform.datatable.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.zxt.compplatform.datasource.entity.DataSource;
import com.zxt.compplatform.datatable.entity.DataObjectGroup;
import com.zxt.compplatform.datatable.entity.TreeDataJson;
import com.zxt.compplatform.datatable.service.IDataObjectGroupService;
import com.zxt.compplatform.datatable.service.IDataTableService;
import com.zxt.framework.common.util.RandomGUID;

/**
 * 数据对象分组action
 * @author 007
 */
public class DataObjectGroupAction {
	/**
	 * 数据对象分组业务操作接口
	 */
	private IDataObjectGroupService dataObjectGroupService;
	/**
	 * 数据对象业务操作接口
	 */
	private IDataTableService dataTableService;
	/**
	 * 数据对象分组实体
	 */
	private DataObjectGroup dataObjectGroup;
	
	/**
	 * 将数据对象分组实体转换成TreeDataJson
	 * @param dataObjectGroup
	 * @return
	 */
	private TreeDataJson ListToTreeJson(DataObjectGroup dataObjectGroup){
		DataObjectGroup group = dataObjectGroup;
		TreeDataJson treeDataJson = new TreeDataJson();
		treeDataJson.setId(group.getId());
		treeDataJson.setText(group.getName());
		List dataObjectGroups = dataObjectGroupService.findAllByPid(group.getId());
		Iterator dataObjectGroupListIt = dataObjectGroups.iterator();
		List children = new ArrayList();
		while(dataObjectGroupListIt.hasNext()){
			DataObjectGroup objectGroup = (DataObjectGroup) dataObjectGroupListIt.next();
			children.add(ListToTreeJson(objectGroup));
		}
		treeDataJson.setChildren(children);
		return treeDataJson;
	}
	
	
	/**
	 * 列表
	 * @return
	 */
	public String list(){
		HttpServletResponse res = ServletActionContext.getResponse();
		res.setContentType("text/x-json;charset=UTF-8 ");
		dataObjectGroup = dataObjectGroupService.findById("1");
		TreeDataJson treeDataJson = ListToTreeJson(dataObjectGroup);
		String dataJson = JSONArray.fromObject(treeDataJson).toString();
		try {
			res.getWriter().write(dataJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 根据父节点id查询所有子节点数据
	 * @return
	 */
	public String getAllItemByParentId(){
		HttpServletResponse res = ServletActionContext.getResponse();
		HttpServletRequest req = ServletActionContext.getRequest();
		res.setContentType("text/x-json;charset=UTF-8 ");
		try{
			String pid = req.getParameter("parentId");
			if(pid != null && !pid.equals("")){
				List list = dataObjectGroupService.findAllByPid(pid);
				List tempList = new ArrayList();
				for (int i=0;i<list.size();i++){
					Map map = new HashMap();
					dataObjectGroup = (DataObjectGroup) list.get(i);
					map.put("id", dataObjectGroup.getId());
					map.put("text", dataObjectGroup.getName());
					List li = dataObjectGroupService.findAllByPid(dataObjectGroup.getId());
					if(li != null && li.size() > 0)
						map.put("state", "closed");
					tempList.add(map);
				}
				String dataJson = JSONArray.fromObject(tempList).toString();		
				System.out.println(dataJson);
				res.getWriter().write(dataJson);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	/**
	 * 根据ID查找父对象
	 * @return
	 */
	public String getParentById(){
		HttpServletResponse res = ServletActionContext.getResponse();
		HttpServletRequest req = ServletActionContext.getRequest();
		res.setContentType("text/x-json;charset=UTF-8 ");
		String id = req.getParameter("dataObjectGroupId");
		dataObjectGroup = dataObjectGroupService.findParentById(id);
		String dataJson = JSONArray.fromObject(dataObjectGroup).toString();		
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
		res.setContentType("text/plain;charset=UTF-8 ");
		if(dataObjectGroup.getId() == null || dataObjectGroup.getId().equals("")){
			dataObjectGroup.setId(RandomGUID.geneGuid());
		}
		try {
			if(dataObjectGroupService.findById(dataObjectGroup.getId()) != null){				
				res.getWriter().write("exist");
			}else{
				dataObjectGroupService.insert(dataObjectGroup);
				res.getWriter().write("success");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	/**
	 * 跳转修改页面
	 * @return
	 */
	public String toUpdate(){
		HttpServletRequest req = ServletActionContext.getRequest();
		String dataObjectGroupId = req.getParameter("dataObjectGroupId");
		if(dataObjectGroupId!= null && !dataObjectGroupId.equals("")){
			dataObjectGroup = dataObjectGroupService.findById(dataObjectGroupId);
		}
		return "toupdate";
	}
	/**
	 * 修改
	 * @return
	 */
	public String update(){
		HttpServletResponse res = ServletActionContext.getResponse();
		res.setContentType("text/plain;charset=UTF-8 ");
		try {
			dataObjectGroupService.update(dataObjectGroup);
			res.getWriter().write("success");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
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
		res.setContentType("text/plain;charset=UTF-8 ");
		String dataObjectGroupId = req.getParameter("dataObjectGroupId");		
		try {
			if(dataObjectGroupId!= null && !dataObjectGroupId.equals("")){
				if(dataObjectGroupId.equals("1")){
					res.getWriter().write("nodelete");
				}else{
					List dtlist = dataTableService.findByGroupId(dataObjectGroupId);
					List grouplist = dataObjectGroupService.findAllByPid(dataObjectGroupId);
					if((dtlist != null && dtlist.size() > 0) || (grouplist != null && grouplist.size() > 0)){
						res.getWriter().write("children");
					}else{
						dataObjectGroupService.deleteById(dataObjectGroupId);
						res.getWriter().write("success");
					}
				}
			}			
		} catch (IOException e) {				
			e.printStackTrace();
		}
		return null;
	}
	
	public String nameExist() {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setContentType("text/plain;charset=UTF-8 ");

		String name = req.getParameter("name");
		String pgroup = req.getParameter("bd_dataobjectgroup_pid");

		DataObjectGroup dataObjGroup = dataObjectGroupService.findByName(name);

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
	public DataObjectGroup getDataObjectGroup() {
		return dataObjectGroup;
	}
	public void setDataObjectGroup(DataObjectGroup dataObjectGroup) {
		this.dataObjectGroup = dataObjectGroup;
	}
	public void setDataObjectGroupService(
			IDataObjectGroupService dataObjectGroupService) {
		this.dataObjectGroupService = dataObjectGroupService;
	}
	public void setDataTableService(IDataTableService dataTableService) {
		this.dataTableService = dataTableService;
	}

}
