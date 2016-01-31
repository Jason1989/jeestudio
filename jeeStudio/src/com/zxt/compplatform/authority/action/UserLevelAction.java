package com.zxt.compplatform.authority.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.zxt.compplatform.authority.entity.UserLevel;
import com.zxt.compplatform.authority.service.UserLevelService;

/**
 * 用户级别操作action
 * @author 007
 */
public class UserLevelAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	/**
	 * 用户级别业务逻辑实体
	 */
	private UserLevelService userLevelService;
	/**
	 * 用户级别实体
	 */
	private UserLevel userlevel ;
	/**
	 * 用户级别的id
	 */
	private String  id;

	/**
	 * 跳转到级别管理页面
	 */
	public String toList(){
		return "toList";
	}
	
	/**
	 * 跳转到级别添加页面 
	 */
	public String toAddUserLevel(){
		return "toadduserlevel";
	}
	
	/**
	 * 添加级别的保存
	 * @return
	 */
	public String saveAddUserLevel() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		
		response.setContentType("text/plain;charset=UTF-8 ");
		//System.out.println("userlevel----->"+userlevel);
		String result = userLevelService.addUserLevel(userlevel);
		response.getWriter().write(result);
		return null ;
	}
	
	/**
	 * 修改级别跳转
	 */
	public String updateUserLevel(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		
		response.setContentType("text/plain;charset=UTF-8 ");
		String levelid = request.getParameter("id");
	    userlevel =  userLevelService.updateUserLevelById(levelid);
		return "toupdateuserlevel" ;
	}
	
	/**
	 * 修改级别的保存
	 * @return
	 */
	public String saveUpdateUserLevel() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		
		response.setContentType("text/plain;charset=UTF-8 ");
		String result = userLevelService.updateUserLevel(userlevel);
		response.getWriter().write(result);
		return null ;
	}

	/**
	 * 删除级别 
	 * @return
	 */
	public String deleteUserLevel(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain;charset=UTF-8 ");
		String id = request.getParameter("id");
		userLevelService.deleteUserLevel(id);
		return null;
	}
	
	/**
	 * 查询所有级别 (用户修改和级别修改使用)
	 */
	public String getAllLevel() throws IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
		
		response.setContentType("text/plain;charset=UTF-8 ");
		List list = userLevelService.findAll();
		JSONArray jsonarray = JSONArray.fromObject(list);
		String jsonString  = jsonarray.toString();
		String a = jsonString.replace("[{", "[{\"id\":-1,\"levelname\" :\"请选择\"},{");
		response.getWriter().write(a);
		return null;
	}
	
	/**
	 * 查询所有级别 (添加使用)
	 */
	public String getAllLevelAdd() throws IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
		
		response.setContentType("text/plain;charset=UTF-8 ");
		List list = userLevelService.findAll();
		JSONArray jsonarray = JSONArray.fromObject(list);
		String jsonString  = jsonarray.toString();
		String a = jsonString.replace("[{", "[{\"id\":-1,\"levelname\" :\"请选择\",\"selected\":true},{");
		response.getWriter().write(a);
		return null;
	}
	
	/**
	 * 级别分页
	 */
	public String getUserLevelList() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain;charset=UTF-8 ");
		
		int page = 1;
		if(request.getParameter("page") != null && !request.getParameter("page").equals("")){
			page = Integer.parseInt(request.getParameter("page"));
		}
		int rows = 0;
		if(request.getParameter("rows") != null && !request.getParameter("rows").equals("")){
			rows = Integer.parseInt(request.getParameter("rows"));
		}
		
		//获取所需数据
		List userLevelList = userLevelService.findUserLevel(page, rows);
		int count = userLevelService.findCount();
		
		Map map = new HashMap();
		if(userLevelList !=null){
			map.put("rows", userLevelList);
			map.put("total", new Integer(count));
		}
		
		String dataJson = JSONObject.fromObject(map).toString();
		response.getWriter().write(dataJson);
		return null;
	}
	
	/**
	 * 级别已存在的用户 
	 */
	public String usersUnderLevel() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain;charset=UTF-8 ");
		
		int page = 1;
		if(request.getParameter("page") != null && !request.getParameter("page").equals("")){
			page = Integer.parseInt(request.getParameter("page"));
		}
		int rows = 0;
		if(request.getParameter("rows") != null && !request.getParameter("rows").equals("")){
			rows = Integer.parseInt(request.getParameter("rows"));
		}
		
		//获取所需数据
		List userLevelList = userLevelService.findUsersUnderLevel(id, rows, page);
		int count = userLevelService.getTotleUnderLevel(id);
		
		Map map = new HashMap();
		if(userLevelList !=null){
			map.put("rows", userLevelList);
			map.put("total", new Integer(count));
		}
		
		String dataJson = JSONObject.fromObject(map).toString();
		response.getWriter().write(dataJson);
		return null;
	}
	
	/**
	 * 级别用户管理
	 */
	public String toManagerUser(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain;charset=UTF-8 ");
		return "tomanageruser" ;
	}
	
	/**
	 * 在角色的管理用户下跳转全部用户的页面
	 */
	public String toLevelUnderUserAll(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain;charset=UTF-8 ");
		return "tolevelunderuserall" ;
	}
	
	/**
	 * 移除用户级别
	 */
	public String removeUserLevel(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/x-json;charset=UTF-8");
		String userids = request.getParameter("userid");
		userLevelService.levelRemoveUser(id, userids);
		return null;
	}
	
	/**
	 * 级别添加用户 
	 */
	public String addUserUserLevel(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/x-json;charset=UTF-8");
		String userids = request.getParameter("userid");
		userLevelService.levelAddUser(id, userids);
		return null;
	}
	
	public void setUserLevelService(UserLevelService userLevelService) {
		this.userLevelService = userLevelService;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public UserLevel getUserlevel() {
		return userlevel;
	}
	public void setUserlevel(UserLevel userlevel) {
		this.userlevel = userlevel;
	}
}
