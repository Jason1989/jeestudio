package com.zxt.compplatform.menu.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.zxt.compplatform.menu.service.FunctionMenuService;

public class FunctionMenuAction extends ActionSupport  {
	
	private FunctionMenuService functionMenuService;
	
	
	public void setFunctionMenuService(FunctionMenuService functionMenuService) {
		this.functionMenuService = functionMenuService;
	}
	/**
	 * 数据对象管理——>数据对象总数
	 */
	public String execute(){
		HttpServletRequest request = ServletActionContext.getRequest();
		try{
			functionMenuService.update_findDataObjectToMap("true");//刷新数据对象分组
			List menus = functionMenuService.findParentMenus();
			request.setAttribute("menus", menus);
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String main(){
		return "goMain";
	}
	/**
	 * 数据对象分组菜单——>数据对象总数
	 * @return
	 */
	public String show(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			String parentId = request.getParameter("parentId");
			if(null != parentId){
				long id = Long.parseLong(parentId);
				String menuJson = functionMenuService.update_findMenuJson(id);
				System.out.println(menuJson);
				response.getWriter().print(menuJson);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public String list(){
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
				String menuJson = functionMenuService.findMenuJson();
				response.getWriter().print(menuJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
