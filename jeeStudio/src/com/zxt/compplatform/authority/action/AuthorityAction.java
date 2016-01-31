package com.zxt.compplatform.authority.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.zxt.compplatform.authority.entity.Authority;
import com.zxt.compplatform.authority.entity.RARelationShip;
import com.zxt.compplatform.authority.service.AuthorityService;
import com.zxt.compplatform.authority.service.RARelationShipService;
import com.zxt.compplatform.authority.service.RoleService;
import com.zxt.compplatform.form.entity.Form;
import com.zxt.compplatform.formengine.entity.view.TreeData;
import com.zxt.compplatform.menu.entity.TreeDataJson;

/**
 * 权限管理Action
 * @author 007
 */
public class AuthorityAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	/**
	 * 角色业务逻辑操作实体
	 */
	private RoleService roleService;
	/**
	 * 关系业务逻辑操作实体
	 */
	private RARelationShipService relationShipService;
	/**
	 * 权限业务逻辑操作实体
	 */
	private AuthorityService authorityService;
	
	
	/**
	 * 向前台返回权限列表
	 * @return
	 */
	public String listRights(){
		HttpServletResponse res = ServletActionContext.getResponse();
		res.setContentType("text/x-json;charset=UTF-8 ");
		//String rStr="ROLE_USER";
		String rStr="ROLE_ADMIN,ROLE_USER";
		List dataSourceList = relationShipService.findAll(rStr);
		int totalRows =dataSourceList.size();
		Map map = new HashMap();
		if(dataSourceList == null){
			dataSourceList = new ArrayList();
		}
		map.put("rows", dataSourceList);
		map.put("total", new Integer(totalRows));
		String dataSourceJson = JSONObject.fromObject(map).toString();
		try {
			res.getWriter().write(dataSourceJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 查询所有的资源
	 * @return
	 */
	public String getAllModule(){
		HttpServletResponse res = ServletActionContext.getResponse();
		res.setContentType("text/x-json;charset=UTF-8");
		try{
			List moduleList = authorityService.findAllModule();
			/*if(moduleList == null){
				moduleList = new ArrayList();
			}
		
			String dataJson = JSONArray.fromObject(moduleList).toString();
			res.getWriter().write(dataJson);
			*/
			
			Iterator moduleListIt = moduleList.iterator();
			List menuList = new ArrayList();
			while(moduleListIt.hasNext()){
				Authority moduleTemp = (Authority) moduleListIt.next();
				TreeDataJson child = new TreeDataJson();
				child.setId(moduleTemp.getAjaxTreeId());
				child.setText(moduleTemp.getText());
				menuList.add(child);
			}
			String dataJson = JSONArray.fromObject(menuList).toString();
			res.getWriter().write(dataJson);
		}catch(IOException e){
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取所有的角色
	 * @return
	 * @throws IOException
	 */
	public String getAllRole()throws IOException{
		HttpServletResponse res = ServletActionContext.getResponse();
		res.setContentType("text/plain;charset=UTF-8");
		List roleList = roleService.findAll();
		String dataJson = JSONArray.fromObject(roleList).toString();
		res.getWriter().write(dataJson);
		return null;
	}

	/**
	 * 跳转到roleMenuConfig页面
	 * @return
	 */
	public String toConfig(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String orgid = request.getParameter("orgid");
		String isall = request.getParameter("isall");
		String roleId=request.getParameter("roleId");
		request.setAttribute("orgid", orgid);
		request.setAttribute("isall", isall);
		request.setAttribute("roleId", request.getParameter("roleId"));
		return "managerGrant";//修改跳到了一个Tabs  managerGrant.jsp页面 GUOWEIXIN
	}
	/**
	 *通过角色id选出响应模板 
	 */
	@SuppressWarnings("unchecked")
	public String getMenuByRoleId(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String systemID = request.getParameter("moduleId");
		
		/**
		 *  查询权限集
		 */
		
		String templateRoleId = request.getParameter("templateRoleId");
		String roleID = request.getParameter("roleID");
		List list = authorityService.findMenuByRoleId(templateRoleId);
		
		/**
		 * 查询角色已选中资源IDs
		 */
		String resourceCheckIDs ="";
		if (roleID!=null&&!"".equals(roleID)) {
				resourceCheckIDs=relationShipService.findResourceIDsByRoleID(roleID);
		}
		String json =authorityService.dealWithResource(systemID, list, resourceCheckIDs);

		//		JSONArray jsonarray = JSONArray.fromObject(list1);
		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 查询全部资源
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getAllMenu(){
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		String systemID = req.getParameter("moduleId");
		String roleID = req.getParameter("roleId");
		
		/**
		 * 查询角色已选中资源IDs
		 */
		String resourceCheckIDs ="";
		if (roleID!=null&&!"".equals(roleID)) {
				resourceCheckIDs=relationShipService.findResourceIDsByRoleID(roleID);
		}
		
		// res.setContentType("text/x-json;charset=UTF-8");
		try {
			List<TreeData> list=authorityService.findAllResource();
			String json =authorityService.dealWithResource(systemID, list, resourceCheckIDs);
			res.getWriter().write(json);
//			Authority authority = authorityService.findById(moduleId);//"f9a4c118d5ed175593e6a724ce88f6e7");
//			if(authority != null){
//				TreeDataJson treeDataJson = ListToTreeJson(authority);
//				String dataJson = JSONArray.fromObject(treeDataJson).toString();
//				res.getWriter().write(dataJson);
//			}
		} catch(Exception e){
			e.printStackTrace();
		}
		
//  已选中
//		String roleId = req.getParameter("roleId");
//		res.setContentType("text/x-json;charset=UTF-8");
//		List menuList = authorityService.getAuthorizedMenuByModuleIdRoleId(moduleId,roleId);

		
		return null;
	}
	/**
	 * 获取所有的权限资源id
	 * @return
	 */
	public String getAuthorizedMenuIds(){
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		String moduleId = req.getParameter("moduleId");
		String roleId = req.getParameter("roleId");
		res.setContentType("text/x-json;charset=UTF-8");
		try {
			List menuList = authorityService.getAuthorizedMenuByModuleIdRoleId(moduleId,roleId);
			String dataJson = JSONArray.fromObject(menuList).toString();
			res.getWriter().write(dataJson);
		} catch (IOException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取未授权的资源菜单
	 * @return
	 */
	public String getUnauthorizedMenu(){
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		String roleId = req.getParameter("roleId");
		String moduleId = req.getParameter("moduleId");
		res.setContentType("text/x-json;charset=UTF-8");
		try {
			Authority authority = authorityService.findById(moduleId);//"f9a4c118d5ed175593e6a724ce88f6e7");
			if(authority != null){
				TreeDataJson treeDataJson = ListToTreeJson(authority,roleId,"0");
				if(treeDataJson.getChildren().size()<1)
					res.getWriter().write("");
				else{
					String dataJson = JSONArray.fromObject(treeDataJson).toString();
					res.getWriter().write(dataJson);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取授权的资源菜单
	 * @return
	 */
	public String getAuthorizedMenu(){
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		String roleId = req.getParameter("roleId");
		String moduleId = req.getParameter("moduleId");
		res.setContentType("text/x-json;charset=UTF-8");
		try {
			Authority authority = authorityService.findById(moduleId);//"f9a4c118d5ed175593e6a724ce88f6e7");
			if(authority != null){
				TreeDataJson treeDataJson = ListToTreeJson(authority,roleId,"1");
				if(treeDataJson.getChildren().size()<1)
					res.getWriter().write("");
				else{
					String dataJson = JSONArray.fromObject(treeDataJson).toString();
					res.getWriter().write(dataJson);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 将权限菜单数据转换成json数据
	 * @param authority
	 * @param roleId
	 * @param flag 0 未授权，1 已授权
	 * @return
	 */
	private TreeDataJson ListToTreeJson(Authority authority,String roleId,String flag){
		TreeDataJson treeDataJson = new TreeDataJson();
		treeDataJson.setId(authority.getAjaxTreeId());
		treeDataJson.setText(authority.getText());
		List authorityList = authorityService.findAllByPIdRoleId(authority.getAjaxTreeId(),roleId,flag);
		Iterator authorityListIt = authorityList.iterator();
		List children = new ArrayList();
		while(authorityListIt.hasNext()){
			Authority authorityTemp = (Authority) authorityListIt.next();
			children.add(ListToTreeJson(authorityTemp,roleId,flag));
		}
		treeDataJson.setChildren(children);
		return treeDataJson;
	}
	/**
	 *  将权限菜单数据转换成json数据
	 * @param authority
	 * @return
	 */
	private TreeDataJson ListToTreeJson(Authority authority){
		TreeDataJson treeDataJson = new TreeDataJson();
		treeDataJson.setId(authority.getAjaxTreeId());
		treeDataJson.setText(authority.getText());
		List authorityList = authorityService.findAllByPid(authority.getAjaxTreeId());
		Iterator authorityListIt = authorityList.iterator();
		List children = new ArrayList();
		while(authorityListIt.hasNext()){
			Authority authorityTemp = (Authority) authorityListIt.next();
			children.add(ListToTreeJson(authorityTemp));
		}
		treeDataJson.setChildren(children);
		return treeDataJson;
	}
	/**
	 * 保存角色权限配置
	 * @return
	 */
	public String roleMenuConfigSave(){
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		String roleId = req.getParameter("roleId");
		String treeIds = req.getParameter("treeIds");
		String systemRescId = req.getParameter("systemRescId");
		String result = "error";
		try {
			if(roleId != null && !roleId.equals("")){
				relationShipService.roleMenuConfigInsert(roleId,treeIds,systemRescId);
			}
			/*
			String[] treeIdsArr = treeIds.split(",");
			if(roleId != null && !roleId.equals("")){
				relationShipService.deleteByRoleId(roleId);
				for(int i=0;i<treeIdsArr.length;i++){				
					RARelationShip roleMenu = new RARelationShip(new Long(Integer.parseInt(roleId)),treeIdsArr[i]);
					relationShipService.insert(roleMenu);
				}
			}*/		
			result = "success";
			res.getWriter().write(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 角色菜单删除
	 * @return
	 */
	public String roleMenuConfigDelete(){
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		String roleId = req.getParameter("roleId");
		String treeIds = req.getParameter("treeIds");
		String result = "error";
		String[] treeIdsArr = treeIds.split(",");
		if(roleId != null && !roleId.equals("")){
			for(int i=0;i<treeIdsArr.length;i++){				
				RARelationShip roleMenu = new RARelationShip(new Long(Integer.parseInt(roleId)),treeIdsArr[i]);
				relationShipService.delete(roleMenu);
			}
			result = "success";
		}
		try {
			res.getWriter().write(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 跳转到角色配置页面
	 * @return
	 */
	public String toFormUrlConfig(){
		HttpServletRequest req = ServletActionContext.getRequest();
		req.setAttribute("roleId", req.getParameter("roleId"));
		return "roleformconfig";
	}
	/**
	 * 按照类型获取表单
	 * @return
	 */
	public String getAllFormByType(){
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		String formTypeId = req.getParameter("formTypeId");
		res.setContentType("text/x-json;charset=UTF-8");
		try {
			List formList = authorityService.findFormByTypeId(formTypeId);				
			Iterator formListIt = formList.iterator();
			List resultList = new ArrayList();
			while(formListIt.hasNext()){
				Form formTemp = (Form) formListIt.next();
				TreeDataJson child = new TreeDataJson();
				child.setId(formTemp.getId());
				child.setText(formTemp.getFormName());
				resultList.add(child);
			}
			String dataJson = JSONArray.fromObject(resultList).toString();
			res.getWriter().write(dataJson);
		
		} catch (IOException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取所有授权额表单id
	 * @return
	 */
	public String getAuthorizedFormIds(){
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		String formTypeId = req.getParameter("formTypeId");
		String roleId = req.getParameter("roleId");
		res.setContentType("text/x-json;charset=UTF-8");
		try {
			List formList = authorityService.findAuthorizedFormByTypeIdRoleId(formTypeId,roleId);
			String dataJson = JSONArray.fromObject(formList).toString();
			res.getWriter().write(dataJson);
		} catch (IOException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 角色表单url配置保存
	 * @return
	 */
	public String roleFormUrlConfigSave(){
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		String roleId = req.getParameter("roleId");
		String formIds = req.getParameter("formIds");
		String result = "error";
		try {
			if(roleId != null && !roleId.equals("")){
				authorityService.roleFormConfigInsert(roleId,formIds);
			}
			result = "success";
			res.getWriter().write(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	public void setRelationShipService(RARelationShipService relationShipService) {
		this.relationShipService = relationShipService;
	}
	public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}
	
}
