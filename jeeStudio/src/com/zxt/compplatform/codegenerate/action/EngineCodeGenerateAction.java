package com.zxt.compplatform.codegenerate.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.demo.ReadWrite;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Mkdir;

import com.fr.third.org.apache.poi.hssf.record.formula.functions.Request;
import com.opensymphony.xwork2.ActionSupport;
import com.zxt.compplatform.codegenerate.entity.EngCodeLog;
import com.zxt.compplatform.codegenerate.service.IEngineCodeGenerateService;
import com.zxt.compplatform.codegenerate.util.CodeGenerateException;
import com.zxt.compplatform.codegenerate.util.StaticHTMLFile;
import com.zxt.compplatform.form.service.IFormService;
import com.zxt.compplatform.formengine.entity.view.EditPage;
import com.zxt.compplatform.formengine.entity.view.ListPage;
import com.zxt.compplatform.formengine.service.PageService;
import com.zxt.compplatform.formengine.util.StrTools;
import com.zxt.framework.common.util.RandomGUID;

/**
 * 
 * @author huanglin
 * @Description TODO
 * @created Nov 12, 2012 3:57:52 PM
 * @History 
 * @version v1.0
 */
public class EngineCodeGenerateAction extends ActionSupport {
	/**
	 * 代码生成业务逻辑接口
	 */
	private IEngineCodeGenerateService codeGenerateService;
	/**
	 * 页面业务操作接口
	 */
	private PageService pageService;
	/**表单操作业务逻辑接口
	 * 
	 */
	private IFormService formService;

	public void setFormService(IFormService formService) {
		this.formService = formService;
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute() {
		return SUCCESS;
	}
	/**
	 * 代码生成向导页
	 * 
	 * @return
	 */
	public String goGenerate() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String formId = request.getParameter("form_id");
		request.setAttribute("formId", formId);
		//String formName = request.getParameter("form_formName");
		String formName=StrTools.charsetFormat(request.getParameter("form_formName"),"ISO8859-1", "gbk");
		request.setAttribute("formName", formName);
		String type = request.getParameter("form_type");
		request.setAttribute("type", type);
		return "goGenerate";
	}
	/**
	 * 动态页面静态化的代码生成
	 * 
	 * @return
	 */
	public String generateCode() {
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String resultMsg = "1";
		
		String base_path=request.getParameter("base_path");//得到
		String formNameJsp=request.getParameter("formNameJsp");//得到生成的JSP名称
		String basePath=request.getParameter("basePath");//得到服务的前缀。页面的BASEPATH  forms_id
		String forms_id=request.getParameter("forms_id");//得到表单的ID
		String form_type=request.getParameter("form_type");//得到表单的类型
		
		String listPageRander=request.getParameter("listPageRander");//得到生成ID
		try {
			Map map = null;
			ListPage listPage = null;
			if ((forms_id != null) && (!"".equals(forms_id))) {
				map = pageService.load_service(forms_id);
			  //map = pageService.update_service(formId);
				if (map != null && map.get("listPage") != null) {
					listPage = (ListPage) map.get("listPage");
					listPage.setId(forms_id.trim());
					listPage.setListPageRander(RandomGUID.geneGuid());
					listPageRander=listPage.getListPageRander();
				} 
			} 
			
			
			
			
			createDir(base_path);
			String[] targetPage=new String[4];//目标地址 和名称
			String[] url_page=new String[4];//目标URL
			
			if("listPage".equals(form_type)){//如果是列表 让其生成相应的四个固化功能即可。
				/**
				 * 1列表
				 * 2编辑
				 * 3添加
				 * 4显示
				 */
				targetPage[0]=base_path+"/"+formNameJsp+".jsp";
				targetPage[1]=base_path+"/"+formNameJsp+"编辑.jsp";
				targetPage[2]=base_path+"/"+formNameJsp+"添加.jsp";
				targetPage[3]=base_path+"/"+formNameJsp+"详情.jsp";
				url_page[0]=basePath+"formengine/"+form_type+"Action.action"+"?formId="+forms_id+"&listPageRander="+listPageRander;
				url_page[1]=basePath+"formengine/editPageAction.action?formId="+listPage.getEditPage().getId()+"&opertorType=1&listpageId="+forms_id+"&lprid="+listPageRander+"&valueDefine=1";
				url_page[2]=basePath+"formengine/editPageAction.action?formId="+listPage.getEditPage().getId()+"&opertorType=0&listpageId="+forms_id+"&lprid="+listPageRander;
				url_page[3]=basePath+"formengine/viewPageAction.action?formId="+listPage.getViewPage().getId()+"&viewPageDivId=viewPageWindow_"+listPageRander+"&valueDefine=1";
				for(int i=0;i<targetPage.length;i++){
					StaticHTMLFile.PrintPage(targetPage[i], url_page[i]);
				}	
			}
			
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().print(resultMsg);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultMsg=e.getMessage();
		}	
		return null;
	}
	/**
	 * 
	 * @Description 创建文件夹目录
	 * @param path 
	 * @return void
	 * @time Nov 20, 2012
	 */
	private void createDir(String path) {
		Project prj = new Project();
		Mkdir mkdir = new Mkdir();
		mkdir.setProject(prj);
		mkdir.setDir(new File(path));
		mkdir.execute();
	}
	public void setCodeGenerateService(IEngineCodeGenerateService codeGenerateService) {
		this.codeGenerateService = codeGenerateService;
	}
	public PageService getPageService() {
		return pageService;
	}

	public void setPageService(PageService pageService) {
		this.pageService = pageService;
	}
}
