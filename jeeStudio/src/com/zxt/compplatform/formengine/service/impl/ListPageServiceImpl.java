package com.zxt.compplatform.formengine.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zxt.compplatform.formengine.constant.Constant;
import com.zxt.compplatform.formengine.entity.view.Button;
import com.zxt.compplatform.formengine.entity.view.ListPage;
import com.zxt.compplatform.formengine.entity.view.Param;
import com.zxt.compplatform.formengine.service.ListPageService;
import com.zxt.compplatform.formengine.util.StrTools;

/**
 * 列表页业务操作实现
 * 
 * @author 007
 */
public class ListPageServiceImpl implements ListPageService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.formengine.service.ListPageService#controlOperationField(com.zxt.compplatform.formengine.entity.view.ListPage)
	 */
	public ListPage controlOperationField(ListPage listPage) {

		/**
		 * 列表页 数据展示区 操作列显示
		 * 
		 */
		List rowButton = listPage.getRowButton();
		Button button = null;
		for (int i = 0; i < rowButton.size(); i++) {
			button = (Button) rowButton.get(i);
			if (Constant.UPDATEOPERATOR.equals(button.getButtonName())) {
				listPage.setIsShowOperator("true");
			} else if (Constant.VIEWOPERATOR.equals(button.getButtonName())) {
				listPage.setIsShowOperator("true");
			} else if (Constant.DELETEOPERATOR.equals(button.getButtonName())) {
				listPage.setIsShowOperator("true");
			}
		}
		return listPage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.formengine.service.ListPageService#findGridUrl(com.zxt.compplatform.formengine.entity.view.ListPage,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	public String findGridUrl(ListPage listPage, HttpServletRequest request) {
		String listPageActionUrl=Constant.DATAGRID_URL + listPage.getId();
		String gridUrl = "";
		String isAbleWorkFlow = request.getParameter("isAbleWorkFlow");
		String workflow_fiter = request.getParameter("workflow_fiter");

		String menuId = request.getParameter("menuId");
		if ((menuId != null) && (!"".equals(menuId))) {
			gridUrl = gridUrl + "&menuId=" + menuId;
		}
		if (listPage.getListPageParams() != null) {
			if (listPage.getListPageParams().size() != 0) {
				String value = "";
				String key = "";
				Param param = null;
				for (int i = 0; i < listPage.getListPageParams().size(); i++) {
					param = (Param) listPage.getListPageParams().get(i);
					key = param.getKey().trim();
					value = request.getParameter(key);
					if ((value != null) && (!"".equals(value))) {
						value=StrTools.charsetFormat(value,
								"ISO8859-1", "UTF-8");
						gridUrl = gridUrl + "&" + key + "=" + value.trim();
					}
				}
			}
		}
		if ((workflow_fiter != null) && (!"".equals(workflow_fiter))) {
			gridUrl = gridUrl + "&workflow_fiter=" + workflow_fiter;
		}
		if ((isAbleWorkFlow != null) && (!"".equals(isAbleWorkFlow))) {
			gridUrl = gridUrl + "&isAbleWorkFlow=" + isAbleWorkFlow;
		}
		
		request.setAttribute("listPageParamerUrl", gridUrl);
		gridUrl=listPageActionUrl+gridUrl;
		return gridUrl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.formengine.service.ListPageService#installBackValue(javax.servlet.http.HttpServletRequest,
	 *      com.zxt.compplatform.formengine.entity.view.ListPage)
	 */
	public void installBackValue(HttpServletRequest request, ListPage listPage) {
		String formId = request.getParameter("formId");
		String isAbleWorkFlow = request.getParameter("isAbleWorkFlow");
		String workflow_fiter = request.getParameter("workflow_fiter");
		listPage.setParentAppId(request.getParameter("PARENT_APP_ID"));
		request.setAttribute("listPage", listPage);
		request.setAttribute("workflowFiter", workflow_fiter);
		request.setAttribute("isAbleWorkFlow", isAbleWorkFlow);
	}
}
