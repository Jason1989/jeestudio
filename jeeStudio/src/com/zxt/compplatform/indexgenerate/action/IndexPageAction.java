package com.zxt.compplatform.indexgenerate.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.zxt.compplatform.indexgenerate.entity.PageUnit;
import com.zxt.compplatform.indexgenerate.service.PageService;
import com.zxt.compplatform.indexgenerate.util.PageXmlUtil;
import com.zxt.compplatform.indexset.entity.ModelPart;
import com.zxt.framework.common.util.RandomGUID;

public class IndexPageAction extends ActionSupport {
	private PageService pageservice;
	private String subSystemId;
	private String xmlparam;
	private PageUnit pageunit;

	/**
	 * 生成新的子系统首页
	 * 
	 * @return
	 */
	public String add() {
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			pageservice.add(pageunit);
			response.getWriter().write("success");
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 更新首页的基本信息 
	 * 非配置xml
	 * @return
	 */
	public String update() {
		if(pageunit!=null){
			pageservice.update(pageunit);
			HttpServletResponse res = ServletActionContext.getResponse();
			try {
				res.getWriter().write("success");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 通过子系统ID来加载系统首页 param:subSystemId
	 * 
	 * @return 跳回页面显示页
	 */

	public String load_Index() {
		HttpServletRequest request = ServletActionContext.getRequest();

		String indexPageSettingId = request.getParameter("indexSetingId");
		String tempateId = request.getParameter("indexPageTemplateValue");
		
		subSystemId = indexPageSettingId;
		templateLocation = tempateId;
		
		Map map = null;
		if (subSystemId != null && subSystemId.length() != 0) {
			map = pageservice.load_Index(subSystemId);
		}
		request.setAttribute("divmap", map);
		String isconfig =request.getParameter("isconfig");
		if(isconfig!=null){
			request.setAttribute("isconfig", isconfig);
		}
		return "indexShowPage";
	}
	
	
	/**
	 * 获取div初试设置的模块
	 * @return
	 */
	public String getInitDiv(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		if(subSystemId!=null& id!=null){
			Map map = pageservice.load_Index(subSystemId);
			ModelPart mp= (ModelPart) map.get(id);
			String result=mp.getId()+","+mp.getName();
			HttpServletResponse res = ServletActionContext.getResponse();
			try {
				res.getWriter().write(result);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 通才子系统ID,提交的xml配置修改首页
	 * 
	 * @return
	 */
	public String update_Index() {
		HttpServletResponse res = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		
		try {
			if (subSystemId != null && subSystemId.length() != 0) {
				// 去 重
				String parameter = request.getParameter("xmlparam");
				if (parameter != null) {
					/*String xml = new String(parameter.getBytes("ISO-8859-1"),
							"UTF-8");*/
					Map map = PageXmlUtil.xmlToPage(parameter);
					pageservice.update(subSystemId, PageXmlUtil.PageToxml(map));
					pageservice.update_Index(subSystemId);
					res.setContentType("text/html;charset=UTF-8");  
					res.getWriter().write("设置成功!");
				}
			} else {
				res.getWriter().write("ID 不能为空!");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 从数据库中删除
	 * 
	 * @return
	 */
	public String delete() {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		String id = req.getParameter("id");
		String temp = req.getParameter("ids");
		try {
			if (id != null && !id.equals("")) {
				pageservice.delete(id);
				pageservice.delete_Index(id);
				res.getWriter().write("success");
			}
			if (temp != null && !temp.equals("")) {
				String[] ids = temp.split(",");
				for (int i = 0; i < ids.length; i++) {
					pageservice.delete(ids[i]);
					pageservice.delete_Index(ids[i]);
				}
				res.getWriter().write("success");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 跳转到配置页
	 * 
	 * @return
	 * 
	 */
	public String toConfig() {
		HttpServletRequest request = ServletActionContext.getRequest();
		
		Map map = null;
		if (subSystemId != null && subSystemId.length() != 0) {
			map = pageservice.load_Index(subSystemId);
		}
		request.setAttribute("divmap", map);
		xmlparam = PageXmlUtil.PageToxml(map);
		String replace = xmlparam.replace("\n", "").replaceAll("\t", "");
		request.setAttribute("xmlparam", replace);
		return "toconfig";
	}
	
	/**
	 * 资源配置跳转到配置页面
	 * 访问地址：indexpage/indexpage!toIndexConfigPage.action
	 * @return
	 */
	public String toIndexConfigPage(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//一、添加新的资源时
		
			//1、获取模板id
		String tempateId = request.getParameter("indexPageTemplateValue");
		
		//二、修改现有的资源的时候
			//1、获取相应首页配置页面的id
		String indexPageSettingId = request.getParameter("indexSetingId");
		
		//查看是不是更换了模板如果更换了模板则需要将原来的设计删掉，并添加新的
		String renew = request.getParameter("renew");
		
		
		Map indexSetting = null;
		
		//首页配置xml
		String xmlSetting = "";
		
		if(StringUtils.isNotEmpty(indexPageSettingId)){
			if(StringUtils.isNotEmpty(renew)){
				pageservice.delete(indexPageSettingId);
				
				subSystemId = RandomGUID.geneGuid();//如果不存在id，则创建相应的id
				
				PageUnit pageUnit = new PageUnit();
				pageUnit.setId(subSystemId);
				pageUnit.setName("fillValue");
				//填充默认xml
				String keyword = request.getParameter("model_Keyword");
				String sum = request.getParameter("model_sum_num");
				int num = Integer.parseInt(sum);
				if(keyword!=null && !"".equals(keyword)){
					String pagexml=pageservice.fillDefaultModel(keyword,num);
					pageUnit.setPagexml(pagexml);
				}
				pageservice.add(pageUnit);
				
			}else{
				//修改，返回已配置的首页xml
				subSystemId = indexPageSettingId;
			}
		}else{
			subSystemId = RandomGUID.geneGuid();//如果不存在id，则创建相应的id
			
			PageUnit pageUnit = new PageUnit();
			pageUnit.setId(subSystemId);
			pageUnit.setName("fillValue");
			//填充默认xml
			String keyword = request.getParameter("model_Keyword");
			String sum = request.getParameter("model_sum_num");
			int num = Integer.parseInt(sum);
			if(keyword!=null && !"".equals(keyword)){
				String pagexml=pageservice.fillDefaultModel(keyword,num);
				pageUnit.setPagexml(pagexml);
			}
			pageservice.add(pageUnit);
		}
		
		indexSetting = pageservice.load_Index(subSystemId);
		//数据库中不存在
		if(indexSetting != null ){
			xmlSetting = PageXmlUtil.PageToxml(indexSetting);
			if(StringUtils.isNotEmpty(xmlSetting)){
				xmlSetting =  xmlSetting.replace("\n", "").replaceAll("\t", "");
			}
		}else{
			xmlSetting = "";
		}
		
		request.setAttribute("xmlparam",xmlSetting);
		request.setAttribute("indexPageTemplateValue", tempateId);
		
		return "toconfig";
	}

	/**
	 * 跳转到添加页
	 * 
	 * @return
	 */
	public String toAdd() {
		return "toAdd";
	}
	
	/**
	 * 跳转到配置页
	 * 
	 * @return
	 */
	public String toPageUpdate() {
		if (subSystemId != null && subSystemId.length() != 0) {
			PageUnit pu = pageservice.findById(subSystemId);
			if(pu!=null){
				HttpServletRequest request = ServletActionContext.getRequest();
				request.setAttribute("page", pu);
			}
		}
		return "toPageConf";
	}
	
	/**
	 * 显示所有页面的列表 不含xml配置
	 * 
	 * @return
	 */
	public String allPageList() {
		HttpServletResponse res = ServletActionContext.getResponse();
		res.setContentType("text/x-json;charset=UTF-8 ");
		HttpServletRequest req = ServletActionContext.getRequest();
		int page = 1;
		if (req.getParameter("page") != null
				&& !req.getParameter("page").equals("")) {
			page = Integer.parseInt(req.getParameter("page"));
		}
		int rows = 0;
		if (req.getParameter("rows") != null
				&& !req.getParameter("rows").equals("")) {
			rows = Integer.parseInt(req.getParameter("rows"));
		}
		int totalRows = pageservice.findTotalRows();
		List list = pageservice.listPage(page, rows);
		Map map = new HashMap();
		if (list == null) {
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

	// -------------getter and setter-----------------------\\
	String templateLocation = null;

	public String getTemplateLocation() {
		return templateLocation;
	}

	public void setTemplateLocation(String templateLocation) {
		this.templateLocation = templateLocation;
	}

	public String getSubSystemId() {
		return subSystemId;
	}

	public void setSubSystemId(String subSystemId) {
		this.subSystemId = subSystemId;
	}

	public PageService getPageservice() {
		return pageservice;
	}

	public void setPageservice(PageService pageservice) {
		this.pageservice = pageservice;
	}

	public String getXmlparam() {
		return xmlparam;
	}

	public void setXmlparam(String xmlparam) {
		this.xmlparam = xmlparam;
	}

	public PageUnit getPageunit() {
		return pageunit;
	}

	public void setPageunit(PageUnit pageunit) {
		this.pageunit = pageunit;
	}

}
