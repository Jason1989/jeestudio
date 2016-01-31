package com.zxt.framework.templatefile.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.zxt.compplatform.datatable.entity.DataObjectGroup;
import com.zxt.compplatform.datatable.service.IDataObjectGroupService;
import com.zxt.compplatform.formengine.entity.view.Param;
import com.zxt.compplatform.formengine.entity.view.ViewColumn;
import com.zxt.compplatform.formengine.entity.view.ViewPage;
import com.zxt.compplatform.formengine.service.ComponentsService;
import com.zxt.compplatform.formengine.service.PageService;
import com.zxt.compplatform.formengine.util.StrTools;
import com.zxt.framework.common.util.ActionHelpper;
import com.zxt.framework.templatefile.entity.FormTemplateFile;
import com.zxt.framework.templatefile.service.FormTemplateFileService;

public class FormTemplateFileAction extends ActionSupport {

	private static final long serialVersionUID = 7962650379024436946L;

	private FormTemplateFileService formTemplateFileService;// 表单模板业务类
	private IDataObjectGroupService dataObjectGroupService;// 数据对象分组业务类
	private PageService pageService;// 分页业务层
	private ComponentsService componentsService;// 组件业务层
	private File file;// 导出文件实体类
	private FormTemplateFile formTemplateFile;// 表单模板文件
	/**
	 * 获取数据对象分组列表
	 * @param 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String grouplist() {
		HttpServletResponse res = ServletActionContext.getResponse();
		// HttpServletRequest req = ServletActionContext.getRequest();
		// int page = ActionHelpper.getPage(req);
		// int size = ActionHelpper.getSize(req);
		// DataObjectGroup root =
		// dataObjectGroupService.findById(DataObjectGroup.DATA_OBJECT_GROUP_ROOT_ID);
		List dataObjectGroups = dataObjectGroupService.findAll();
		Map map = new HashMap();
		List result = new ArrayList();
		if (dataObjectGroups != null) {
			for (int i = 0; i < dataObjectGroups.size(); i++) {
				DataObjectGroup group = (DataObjectGroup) dataObjectGroups
						.get(i);
				if (group == null) {
					continue;
				}
				if (!StringUtils.equals(group.getPid(), "-1")) {
					result.add(group);
				}
			}
			map.put("rows", result);
			map.put("total", Integer.valueOf(result.size()));
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
	 * 获取数据对象列表
	 * @param 
	 * @return
	 */
	public String list() {
		HttpServletResponse res = ServletActionContext.getResponse();
		HttpServletRequest req = ServletActionContext.getRequest();
		int page = ActionHelpper.getPage(req);
		int size = ActionHelpper.getSize(req);
		String id = req.getParameter(ActionHelpper.PARAM_ID);
		/**
		 * 获取模板列表
		 */
		List list = formTemplateFileService.list(id, page, size);
		Map map = new HashMap();
		/**
		 * 分页代码
		 */
		if (map != null) {
			map.put("rows", list);
			map.put("total", Integer.valueOf(list.size()));
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
	 * 保存模板
	 * @param 
	 * @return
	 */
	public String save() {
		HttpServletResponse res = ServletActionContext.getResponse();
		try {
			/**
			 * 写入模板
			 */
			if (formTemplateFile == null) {
				res.getWriter().write(ActionHelpper.RETURN_FAILED);
				return null;
			}
			/**
			 * 判断模板是否存在
			 */
			boolean flag = formTemplateFileService.exists(formTemplateFile
					.getName());
			if (flag) {
				res.getWriter().write(ActionHelpper.RETURN_EXIST);
				return null;
			}
			formTemplateFile.setData(file);
			formTemplateFileService.save(formTemplateFile);
			res.getWriter().write(ActionHelpper.RETURN_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 删除模板
	 * @param 
	 * @return
	 */
	public String delete() {
		HttpServletResponse res = ServletActionContext.getResponse();
		HttpServletRequest req = ServletActionContext.getRequest();
		try {
			/**
			 * 获取模板参数
			 */
			String id = req.getParameter(ActionHelpper.PARAM_ID);
			if (StringUtils.isBlank(id)) {
				res.getWriter().write(ActionHelpper.RETURN_FAILED);
			}
			/**
			 * 删除模板
			 */
			formTemplateFileService.delete(id);
			res.getWriter().write(ActionHelpper.RETURN_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 导出模板
	 * @return
	 */
	public String export() {
		HttpServletResponse res = ServletActionContext.getResponse();
		HttpServletRequest req = ServletActionContext.getRequest();
		/**
		 * 获取模板参数
		 */
		String formId = req.getParameter("formId");
		String fileId = req.getParameter("fileId");
		String fileName = req.getParameter("fileName");
		ViewPage viewPage = null;
		Param param = null;
		String key = "";
		String value = "";
		String urlParmer = "";
		try {
			req.setAttribute("fileName", fileName);
			Map map = pageService.load_service(formId);
			if (map != null) {
				viewPage = (ViewPage) map.get("viewPage");
			}

			/**
			 * 多标签获取主键参数，应替换获取页面参数
			 */
			if (viewPage.getViewPageParams() != null) {
				for (int j = 0; j < viewPage.getViewPageParams().size(); j++) {
					param = (Param) viewPage.getViewPageParams().get(j);
					key = param.getKey().trim();

					value = req.getParameter(key);
					value = StrTools.charsetFormat(value, "ISO8859-1", "UTF-8");
					if ((value != null) && (!"".equals(value))) {
						urlParmer = "&" + key + "=" + value;
					}
				}
				req.setAttribute("urlParmer", urlParmer);
			}

			viewPage.setId(formId.trim());
			viewPage = componentsService.loadViewPage(viewPage, req);

		} catch (Exception e) {
			e.printStackTrace();
		}
		/**
		 * 生成模板页面的表达式
		 */
		try {
			Map formTemplateFile = formTemplateFileService.get(fileId);
			String fileStr = new String((byte[]) formTemplateFile
					.get("templatefile_data"));

			List viewColumn = viewPage.getViewColumn();
			if (CollectionUtils.isNotEmpty(viewColumn)) {
				for (int i = 0; i < viewColumn.size(); i++) {
					ViewColumn column = (ViewColumn) viewColumn.get(i);
					String parname = "__" + column.getName() + "__";
					String pardata = column.getData();
					fileStr = fileStr.replaceAll(parname, pardata);
				}
			}
			if (CollectionUtils.isNotEmpty(viewColumn)) {
				for (int i = 0; i < viewColumn.size(); i++) {
					ViewColumn column = (ViewColumn) viewColumn.get(i);
					String parname = "__" + column.getName() + "__";
					fileStr = fileStr.replaceAll(parname, StringUtils.EMPTY);
				}
			}
			res.setContentType("application/msword;charset=utf-8");
			res.setHeader("Content-disposition", "attachment; filename="
					+ fileName + ".doc");
			res.getWriter().print(fileStr);
			res.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "export";
	}
	/**
	 * 
	 * @param formTemplateFileService
	 */
	public void setFormTemplateFileService(
			FormTemplateFileService formTemplateFileService) {
		this.formTemplateFileService = formTemplateFileService;
	}

	public void setDataObjectGroupService(
			IDataObjectGroupService dataObjectGroupService) {
		this.dataObjectGroupService = dataObjectGroupService;
	}

	public File getFile() {
		return file;
	}
	/**
	 * 
	 * @param file
	 */
	public void setFile(File file) {
		this.file = file;
	}

	public FormTemplateFile getFormTemplateFile() {
		return formTemplateFile;
	}

	public void setFormTemplateFile(FormTemplateFile formTemplateFile) {
		this.formTemplateFile = formTemplateFile;
	}

	public void setPageService(PageService pageService) {
		this.pageService = pageService;
	}

	public void setComponentsService(ComponentsService componentsService) {
		this.componentsService = componentsService;
	}
}
