package com.zxt.compplatform.formengine.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.zxt.compplatform.formengine.constant.Constant;
import com.zxt.compplatform.formengine.entity.view.Button;
import com.zxt.compplatform.formengine.entity.view.EditPage;
import com.zxt.compplatform.formengine.entity.view.ListPage;
import com.zxt.compplatform.formengine.entity.view.Param;
import com.zxt.compplatform.formengine.entity.view.Tab;
import com.zxt.compplatform.formengine.entity.view.ViewPage;
import com.zxt.compplatform.formengine.service.ComponentsService;
import com.zxt.compplatform.formengine.service.PageService;
import com.zxt.framework.common.util.RandomGUID;

/**
 * 多标签页工具类
 * 
 * @author 007
 */
public class TabPageUtil {
	private static final Logger log = Logger.getLogger(TabPageUtil.class);

	/**
	 * 列表页service
	 */
	private PageService pageService;
	private ComponentsService componentsService;

	/**
	 * 返回列表页信息
	 * 
	 * @return
	 */
	public ListPage loadTabListPage(String formId, String parentAppId) {
		Map map = new HashMap();

		map = pageService.load_service(formId);
		ListPage listPage = new ListPage();

		if (map != null) {
			listPage = (ListPage) map.get("listPage");
		} else {
			return null;
		}
		if (listPage == null) {
			return null;
		}

		listPage.setId(formId.trim());
		String sql = listPage.getSql();
		if ((sql.indexOf(" where ") < 0) && (sql.indexOf(" where ") < 0)) {
			listPage.setSql(sql + " where 1=1 ");
		}

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

		/**
		 * 编辑 返回json， tab?js, div js
		 */

		try {
			if (listPage.getEditPage() != null) {
				listPage.setEditPage(editPageforListPage(listPage.getEditPage()
						.getId()));
			}
			listPage.setViewPage(viewPageforListPage(listPage.getViewPage()
					.getId()));
		} catch (Exception e) {
			// TODO: handle exception
		}

		if (listPage.getViewPage() != null) {
			if (listPage.getViewPage().getId() != null) {
				listPage.getViewPage().setId(
						listPage.getViewPage().getId().trim());
			}
		}

		/**
		 * 传递 dataGrid数据查询的参数
		 * 
		 */
		String gridUrl = Constant.DATAGRID_URL + listPage.getId();
		gridUrl = gridUrl + "&PARENT_APP_ID=" + parentAppId;
		listPage.setParentAppId(parentAppId);

		if (listPage != null) {
			if (listPage.getFields() != null) {
				if ("0".equals(listPage.getFields().size() + "")) {
					listPage.setAtuoWidthPen("1");
				} else {
					listPage.setAtuoWidthPen(listPage.getFields().size() + "");
				}
			}
		}
		if (listPage.getAtuoWidthPen() == null) {
			listPage.setAtuoWidthPen("1");
		}
		listPage.setGridUrl(gridUrl);
		return listPage;
	}

	/**
	 * 返回查看页 信息
	 * 
	 * @return
	 */
	public ViewPage loadTabViewPage(String formID) {
		Map map = null;
		map = pageService.load_service(formID);
		ViewPage viewPage = (ViewPage) map.get("viewPage");
		viewPage.setId(formID);
		return viewPage;
	}

	/**
	 * 返回 编辑页信息
	 * 
	 * @param request
	 * @return
	 */
	public EditPage loadTabEditPage(String formID, HttpServletRequest request) {
		/**
		 * 加载编辑页模版数据
		 */
		Map map = new HashMap();

		map = pageService.load_service(formID);
		EditPage editPage = (EditPage) map.get("editPage");
		editPage.setId(formID);

		/**
		 * 创建参数对象
		 */
		String key = "";
		String value = "";
		boolean flag = true;// 编辑页加载数据的状态值
		String[] parmerNameArray = null;
		Param param = null;
		if (editPage != null) {
			if (editPage.getEditPageParams() != null) {
				parmerNameArray = new String[editPage.getEditPageParams()
						.size()];
				for (int i = 0; i < editPage.getEditPageParams().size(); i++) {
					param = (Param) editPage.getEditPageParams().get(i);
					parmerNameArray[i] = param.getKey();
					value = request.getParameter(parmerNameArray[i]);
					if ((value == null) || "".equals(value)) {
						flag = false;// 参数不全 不加载数据
						break;
					}
					value = StrTools.charsetFormat(value, "ISO8859-1", "UTF-8");
					parmerNameArray[i] = value;
				}
			}
		}

		editPage = componentsService.loadEditPage(editPage, parmerNameArray);
		/**
		 * 封装参数 加载编辑页数据的参数
		 */

		return editPage;
	}

	/**
	 * 多标签页 查询各标签页 页面信息
	 * 
	 * @param request
	 * @param tabPageList
	 * @return
	 */
	public List initTabList(List tabPageList, HttpServletRequest request,
			String parentAppID) {
		try {
			Tab tab = null;

			EditPage editPage = null;
			ViewPage viewPage = null;
			ListPage listPage = null;

			for (int i = 0; i < tabPageList.size(); i++) {
				tab = (Tab) tabPageList.get(i);
				if (tab.getType().equals(Constant.TAB_LISTPAGE)) {
					// listPage = loadTabListPage(tab.getUrl(), parentAppID);
					// listPage.setIsUseTab(new Boolean(false));
					// ((Tab) tabPageList.get(i)).setPage(listPage);
					((Tab) tabPageList.get(i)).setTabId(RandomGUID.geneGuid());
				} else if (tab.getType().equals(Constant.TAB_EDITPAGE)) {
					editPage = loadTabEditPage(tab.getUrl(), request);
					editPage.setIsUseTab(new Boolean(false));
					((Tab) tabPageList.get(i)).setPage(editPage);
					((Tab) tabPageList.get(i)).setTabId(RandomGUID.geneGuid());
				} else if (tab.getType().equals(Constant.TAB_VIEWPAGE)) {
					viewPage = loadTabViewPage(tab.getUrl());
					viewPage.setIsUseTab(new Boolean(false));
					((Tab) tabPageList.get(i)).setPage(viewPage);
					((Tab) tabPageList.get(i)).setTabId(RandomGUID.geneGuid());
				}
			}
		} catch (Exception e) {
		}
		return tabPageList;
	}

	public PageService getPageService() {
		return pageService;
	}

	public void setPageService(PageService pageService) {
		this.pageService = pageService;
	}

	public ComponentsService getComponentsService() {
		return componentsService;
	}

	public void setComponentsService(ComponentsService componentsService) {
		this.componentsService = componentsService;
	}

	/**
	 * 加载编辑页
	 * 
	 * @param request
	 * @param formID
	 */
	public EditPage editPageforListPage(String formID) {
		EditPage editPage = null;
		Map map = null;
		try {

			map = pageService.load_service(formID.trim());
			editPage = (EditPage) map.get("editPage");
			editPage.setId(formID);
		} catch (Exception e) {
		}
		return editPage;
	}

	/**
	 * 加载查看页
	 * 
	 * @param request
	 * @param formID
	 */
	public ViewPage viewPageforListPage(String formID) {
		ViewPage viewPage = null;
		Map map = null;
		try {
			map = pageService.load_service(formID.trim());
			viewPage = (ViewPage) map.get("viewPage");
			viewPage.setId(formID);

		} catch (Exception e) {
		}
		return viewPage;
	}
}