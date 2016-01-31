package com.zxt.compplatform.indexset.action;

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

import com.opensymphony.xwork2.ActionSupport;
import com.zxt.compplatform.indexset.entity.ModelPart;
import com.zxt.compplatform.indexset.service.IndexModelService;

/**
 * 模板实例定义
 * 
 * @author chinazxt
 *
 */
public class IndexModelAction extends ActionSupport {

	private IndexModelService indexModelService;
	private ModelPart model=new ModelPart(); 
	private String ids;
	
	/**
	 * 添加模板实例
	 * 访问地址：/index/indexModel!add.action
	 * @return
	 */
	public String add(){
		if(model.getId()==null || model.getId().equals("")){
			indexModelService.add(model);
		}else{
			indexModelService.update(model);
		}
		HttpServletResponse res = ServletActionContext.getResponse();
		try {
			res.getWriter().write("success");
			res.getWriter().close();
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
	 * 跳转更新页面
	 * @return
	 */
	public String toupdate(){
		String id=model.getId();
		ModelPart model=null;
		if(id!=null)
			model = indexModelService.findById(id);
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("model", model);
		return "toupdate";
	}
	
	/**
	 * 跳转查看页面
	 * @return
	 */
	public String toView(){
		String id=model.getId();
		ModelPart model=null;
		if(id!=null)
			model = indexModelService.findById(id);
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("model", model);
		return "toview";
	}
	
	/**
	 * 删除模板实例
	 * @return
	 */
	public String delete(){
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		String id = req.getParameter("id");
		String ids = req.getParameter("ids");
		try {
			if(id!= null && !id.equals("")){
				indexModelService.deleteById(id);
				res.getWriter().write("success");
				res.getWriter().close();
			}
			if(ids!= null && !ids.equals("")){
				indexModelService.deleteAll(indexModelService.findAllByIds(ids));
				res.getWriter().write("success");
				res.getWriter().close();
			}
		} catch (IOException e) {				
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 返回模板实例列表
	 * @return
	 */
	public String list(){
		HttpServletResponse res = ServletActionContext.getResponse();
		res.setContentType("text/x-json;charset=UTF-8 ");
		HttpServletRequest req = ServletActionContext.getRequest();
		int page = 1;
		if(req.getParameter("page") != null && !req.getParameter("page").equals("")){
			page = Integer.parseInt(req.getParameter("page"));
		}
		int rows = 0;
		if(req.getParameter("rows") != null && !req.getParameter("rows").equals("")){
			rows = Integer.parseInt(req.getParameter("rows"));
		}
		int totalRows = indexModelService.findTotalRows();
		List list = indexModelService.findAllByPage(page,rows);
		Map map = new HashMap();
		if(list == null){
			list = new ArrayList();
		}
		map.put("rows", list);
		map.put("total", new Integer(totalRows));
		String json = JSONObject.fromObject(map).toString();
		try {
			res.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 返回json数组用来填充combobox等实例
	 * @return
	 */
	public String comboList(){
		HttpServletResponse res = ServletActionContext.getResponse();
		res.setContentType("text/x-json;charset=UTF-8 ");
		HttpServletRequest req = ServletActionContext.getRequest();
		List list = indexModelService.findAll();
		String json = JSONArray.fromObject(list).toString();
		try {
			res.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	// -----------------getter and setter------------------------\\
	public IndexModelService getIndexModelService() {
		return indexModelService;
	}

	public void setIndexModelService(IndexModelService indexModelService) {
		this.indexModelService = indexModelService;
	}
	public ModelPart getModel() {
		return model;
	}
	public void setModel(ModelPart model) {
		this.model = model;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
}
