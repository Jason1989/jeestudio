package com.zxt.compplatform.codegenerate.action;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.zxt.compplatform.codegenerate.entity.EngCodeLog;
import com.zxt.compplatform.codegenerate.service.ICodeGenerateService;
import com.zxt.compplatform.codegenerate.util.CodeGenerateException;
import com.zxt.compplatform.codegenerate.util.StaticHTMLFile;
import com.zxt.compplatform.form.service.IFormService;

/**
 * 代码生成控制器
 * 
 * @author zxt-hejun
 * @date:2010-9-14 上午02:36:02
 */
public class CodeGenerateAction extends ActionSupport {
	/**
	 * 代码生成业务逻辑接口
	 */
	private ICodeGenerateService codeGenerateService;
	
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
	 * 跳转到编辑页
	 * @return
	 */
	public String goEdit() {
		return "doEdit";
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
		return "goGenerate";
	}

	/**
	 * 表单列表
	 * 
	 * @return
	 */
	public String getFormsData() {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			HttpServletRequest req=ServletActionContext.getRequest();
//GUOWEIXIN
			int page = 1;
			if(req.getParameter("page") != null && !req.getParameter("page").equals("")){
				page = Integer.parseInt(req.getParameter("page"));
			}
			int rows = 0;
			if(req.getParameter("rows") != null && !req.getParameter("rows").equals("")){
				rows = Integer.parseInt(req.getParameter("rows"));
			}
			int totalRows=formService.findTotalRows();
	//-EnD		
			
			Map map = new HashMap();
			List formList = formService.findAllByPage(page, rows);
		
			map.put("rows", formList);
			map.put("total", new Integer(totalRows));
			String json = JSONObject.fromObject(map).toString();
			
			response.getWriter().write(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 表单固化日志列表页
	 * 
	 * @return
	 */
	public String goLogs() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String formId = request.getParameter("form_id");
		request.setAttribute("formId", formId);
		return "logs";
	}

	/**
	 * 表单固化日志列表数据
	 * 
	 * @return
	 */
	public String getCodeLogsData() {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			HttpServletRequest request = ServletActionContext.getRequest();
			String formId = request.getParameter("form_id");
			Map map = new HashMap();
			List formList = codeGenerateService.findCodeLogList(formId);
			map.put("rows", formList);
			map.put("total", new Integer(5));
			String json = JSONObject.fromObject(map).toString();
			response.getWriter().write(json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 表单列表固化日志详细信息
	 * 
	 * @return
	 */
	public String getCodeLogsDetail() {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			HttpServletRequest request = ServletActionContext.getRequest();
			String formId = request.getParameter("forms_id");
			String versionId = request.getParameter("version_id");
			EngCodeLog bpTCodeLog = codeGenerateService.findBpTCodeLog(formId,
					versionId);
			String[] files = bpTCodeLog.getCodeAllFile().split(";");
			request.setAttribute("log", bpTCodeLog);
			request.setAttribute("files", files);
			request.setAttribute("count", files.length - 1 + "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "detail";
	}
	/**
	 * 代码生成
	 * 
	 * @return
	 */
	public String generate() {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		String resultMsg = "1";
		try {
			String packageName = request.getParameter("package_name");
			String basePath = request.getRealPath(File.separator);
			String userBasePath = request.getParameter("base_path");
			String moduleName = request.getParameter("module_name");
			String jarNames = request.getParameter("jar_name");
			String jarName = jarNames.toLowerCase();
			String pagePath = request.getParameter("page_path");
			String formsId = request.getParameter("forms_id");
			String versionRemark = request.getParameter("version_remark");
			Long userId = new Long(1);
			try {
				boolean flag = codeGenerateService.saveGenerateCode(
						packageName, basePath, jarName, jarName, pagePath,
						userBasePath, formsId, versionRemark, userId);
			} catch (CodeGenerateException e) {
				e.printStackTrace();
				resultMsg = e.getMessage();
			}
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().print(resultMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 跳转到添加表单页
	 * @return
	 */
	public String toAddForm() {
		return "addform";
	}

	public void setCodeGenerateService(ICodeGenerateService codeGenerateService) {
		this.codeGenerateService = codeGenerateService;
	}

}
