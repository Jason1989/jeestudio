package com.zxt.compplatform.formengine.action;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.zxt.compplatform.formengine.entity.dataset.FieldDefVO;
import com.zxt.compplatform.formengine.entity.view.EditColumn;
import com.zxt.compplatform.formengine.entity.view.EditMode;
import com.zxt.compplatform.formengine.entity.view.EditPage;
import com.zxt.compplatform.formengine.service.PageService;
import com.zxt.compplatform.formengine.service.ValidateService;

/**
 * 规则验证Action
 * 
 * @author 007
 */
public class ValidateAction extends ActionSupport {

	/**
	 * 验证规则业务操作接口
	 */
	private ValidateService validateService;
	/**
	 * 页面操作接口
	 */
	private PageService pageService;

	public String execute() throws Exception {
		return null;
	}

	/*
	 * 该方法用于验证信息在库中是否已经存在
	 */
	public String isExist() {
		try {
			String flag = "unexist";
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/plain;charset=UTF-8");

			String name = request.getParameter("name");// 要验证的字段的输入值
			String myName = request.getParameter("myname");// 需要验证的字段
			String formID = request.getParameter("id");
			String initData = request.getParameter("initData");// 原始值
			String opertorType = request.getParameter("opertorType");
			Map map = new HashMap();
			/**
			 * 查询编辑页
			 */
			map = pageService.load_service(formID);
			// map = pageService.update_service(formID);
			EditPage editPage = null;
			if (map != null) {
				if (map.get("editPage") != null) {
					editPage = (EditPage) map.get("editPage");
					if (editPage != null) {
						editPage.setId(formID);
					}
				}
			}

			List editColumns = editPage.getEditColumn();
			EditColumn editColumn;
			EditMode editMode;
			String sql;
			String datasource;
			int index = 0;
			int len = editColumns.size();
			for (int i = 0; i < len; i++) {
				editColumn = (EditColumn) editColumns.get(i);
				String tablename = editColumn.getTablename();
				if (StringUtils.isNotBlank(tablename)) {
					if (!StringUtils.equals(tablename, editPage
							.getInsertTableName())) {
						continue;
					}
				}
				String currName = editColumn.getName();
				if (myName != null) {
					if (myName.equals(currName)) {
						editMode = editColumn.getEditMode();
						sql = editMode.getIsExistSQL();
						datasource = editMode.getIsExistDBSource();
						if (name != null && !name.equals(initData)) {// 输入值和初始值不同时才发生数据库查询操作
							if (StringUtils.equals(opertorType, "0")) {
								index = validateService.isExist(datasource,
										sql, name);
							} else {
								List keyVoList = editPage.getKeyList();
								if (CollectionUtils.isNotEmpty(keyVoList)) {
									FieldDefVO keyVo = (FieldDefVO) keyVoList
											.get(0);
									if (keyVo != null
											&& StringUtils.isNotBlank(keyVo
													.getToFieldName())) {
										sql += " and "
												+ editPage.getInsertTableName()
												+ "." + keyVo.getToFieldName()
												+ " <>?";
										String id = request
												.getParameter("keyId");
										index = validateService.isExist(
												datasource, sql, name, id);
									}
								}
							}
						}
						break;
					}
				}
			}

			if (index > 0) {
				flag = "exist";
			}

			response.getWriter().write(flag);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ValidateService getValidateService() {
		return validateService;
	}

	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}

	public PageService getPageService() {
		return pageService;
	}

	public void setPageService(PageService pageService) {
		this.pageService = pageService;
	}

	public static void main(String[] args) {
		String a = "С��";
		try {
			a = new String(a.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(a);
	}
}
