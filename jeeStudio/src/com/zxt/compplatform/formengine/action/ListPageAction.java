package com.zxt.compplatform.formengine.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.xwork.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.googlecode.jsonplugin.JSONUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.zxt.compplatform.authority.entity.Role;
import com.zxt.compplatform.authority.service.AuthorityFrameService;
import com.zxt.compplatform.authority.service.FieldGrantService;
import com.zxt.compplatform.codegenerate.service.ICodeGenerateService;
import com.zxt.compplatform.formengine.constant.Constant;
import com.zxt.compplatform.formengine.entity.view.CopyPage;
import com.zxt.compplatform.formengine.entity.view.EditPage;
import com.zxt.compplatform.formengine.entity.view.Field;
import com.zxt.compplatform.formengine.entity.view.ListPage;
import com.zxt.compplatform.formengine.entity.view.OperateButton;
import com.zxt.compplatform.formengine.entity.view.Param;
import com.zxt.compplatform.formengine.entity.view.QueryColumn;
import com.zxt.compplatform.formengine.entity.view.Tab;
import com.zxt.compplatform.formengine.entity.view.ViewPage;
import com.zxt.compplatform.formengine.service.ComponentsService;
import com.zxt.compplatform.formengine.service.DeskTopService;
import com.zxt.compplatform.formengine.service.ListPageService;
import com.zxt.compplatform.formengine.service.PageService;
import com.zxt.compplatform.formengine.service.SystemFrameService;
import com.zxt.compplatform.formengine.util.StrTools;
import com.zxt.compplatform.formengine.util.TabPageUtil;
import com.zxt.compplatform.organization.dao.OrganizationDao;
import com.zxt.framework.common.util.RandomGUID;

/**
 * 列表页展示操作
 * 
 * @author 007
 */
public class ListPageAction extends ActionSupport {

	private static final Log log = LogFactory.getLog(ListPageAction.class);
	/**
	 * 页面业务操作接口
	 */
	private PageService pageService;
	/**
	 * 代码生成业务操作接口
	 */
	private ICodeGenerateService codeGenerateService;
	/**
	 * 多标签页操作工具
	 */
	private TabPageUtil tabPageUtil;
	/**
	 * 页面组件业务操作接口
	 */
	private ComponentsService componentsService;
	/**
	 * 桌面版业务操作接口
	 */
	private DeskTopService deskTopService;
	/**
	 * 系统框架业务操作接口
	 */
	private SystemFrameService systemFrameService;
	/**
	 * 列表页业务操作接口
	 */
	private ListPageService listPageService;

	public ListPageAction() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute() {
		return SUCCESS;
	}

	/**
	 * 展示列表页
	 * 
	 * @return
	 * @throws Exception
	 */
	public String executeListPage() throws Exception {

		HttpServletRequest request = ServletActionContext.getRequest();
		String formId = request.getParameter("formId");
		request.setAttribute("resId", request.getParameter("is_resid"));
		Map map = null;
		ListPage listPage = null;
		if ((formId != null) && (!"".equals(formId))) {
			map = pageService.load_service(formId);
		  //map = pageService.update_service(formId);
			if (map != null && map.get("listPage") != null) {
				listPage = (ListPage) map.get("listPage");
				listPage.setId(formId.trim());
			   
				//这一步是 第一次固化时 使用到的。 listPageRander=lprid 用于在编辑而面中控制 关闭，保存窗口
				listPage.setListPageRander(request.getParameter("listPageRander"));
				if(listPage.getListPageRander()==null){	
					listPage.setListPageRander(RandomGUID.geneGuid());
				}
				request.setAttribute("listPageRander",listPage.getListPageRander());
				
			} else {
				return "BLANK_PAGE";
			}
		} else {
			return "BLANK_PAGE";
		}
		this.originalSql(listPage);//设置查询语句
		listPage = controlListTab(listPage, request);// 加载多标签数据
		listPage = listPageService.controlOperationField(listPage);// 操作列显示
		listPage = loadCasecadePage(listPage, request);// 添加级联的编辑和查看页
		listPage = loadCasecadePageByzidingyi(listPage, request);
		String gridUrl = listPageService.findGridUrl(listPage, request);// dataGrid数据查询的参数
		listPage.setGridUrl(gridUrl);// 设置列表数据加载链接
		listPage = initQueryCloumnValue(listPage); // 设置组合查询区域 下拉选 下拉树的值
		listPageService.installBackValue(request, listPage);// 作用域 设置返回值
		
		/**
		 * AUTHOR:GUOWEIXIN
		 * 1得到角色ID，表名 字段授权 列表页--> 设计
		 */
		
		/**
		 * 得到当前角色名称，根据名称得到其角色ID
		 */
		Long rid=null;
		String roleName=(String)request.getSession().getAttribute("stwitchRole");//在session范围中得到用户ID
		if(roleName==null)return forWardPage(request);
		String ridReal = authorityFrameService.initAuthorityRidFrameByAccount(roleName);
		if(ridReal!=null)
			rid=new Long(ridReal);
		else return forWardPage(request);
		
		String tableName=listPage.getKeyTable();
		Map<Long,Map> mapRoot=(Map<Long,Map>)fieldGrantService.load_service(rid);
		if(mapRoot!=null){
			Map<String,List> mapTable=mapRoot.get(rid);
			List strFields=mapTable.get(tableName);
			if(strFields!=null){
				//所有字段列
				List listAll=listPage.getFields();
				List resultAll=new ArrayList();//结果字段
				  if(listAll!=null){
						for (int i = 0; i < listAll.size(); i++) {
							Field field=(Field)listAll.get(i);	
							for(int j=0;j<strFields.size();j++){
								String comStrField=(String)strFields.get(j);
								if(comStrField.equals(field.getDataColumn())){
									resultAll.add(field);
								}
							}
						}
						listPage.setFields(resultAll);
				  }	
			}	
		}
		//-----------end GUOWEIXIN
		return forWardPage(request);
	}

	/**
	 * 设置查询语句
	 * 
	 * @param listPage
	 */
	private void originalSql(ListPage listPage) {
		if (listPage == null
				|| !StringUtils.equals(listPage.getIsOriginalSql(), "on")) {
			return;
		}

		String originalSql = listPage.getIsOriginalSqlContext();
		if (StringUtils.isBlank(originalSql)) {
			return;
		} else {
			listPage.setSql(originalSql);
		}

		String param = listPage.getIsOriginalSqlParam();
		if (StringUtils.isBlank(param)) {
			return;
		}
		List parList = new ArrayList();
		String[] params = param.split(",");
		for (int i = NumberUtils.INTEGER_ZERO; i < params.length; i++) {
			if (StringUtils.isBlank(params[i])) {
				continue;
			}
			Param pageParam = new Param();
			pageParam.setKey(params[i]);
			pageParam.setType("varchar");
			parList.add(pageParam);
		}
		listPage.setListPageParams(parList);
	}

	/**
	 * 方法描述 加载多标签
	 * 
	 */
	public ListPage controlListTab(ListPage listPage, HttpServletRequest request) {
		List tabPageList = listPage.getTabs();
		try {
			if (listPage.getIsUseTab().booleanValue()) {
				if (tabPageList != null) {
					Tab tab = null;
					/**
					 * 移出 相同的多标签页
					 * 
					 */
					for (int i = 0; i < tabPageList.size(); i++) {
						tab = (Tab) tabPageList.get(i);
						if (tab.getUrl().equals(listPage.getId())) {
							tabPageList.remove(i);
						}
					}

					tabPageList = tabPageUtil.initTabList(tabPageList, request,
							null);
					listPage.setTabs(tabPageList);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return listPage;
	}

	/**
	 * 
	 * 方法描述 页面跳转方法
	 * 
	 */
	public String forWardPage(HttpServletRequest request) {

		String isPreview = request.getParameter("preview");
		String isAbleWorkFlow = request.getParameter("isAbleWorkFlow");
		String menuId = request.getParameter("menuId");
		String customPath = request.getParameter("customPath");

		@SuppressWarnings("unused")
		String isShowTreeMenu = request.getParameter("isShowTreeMenu");
		String treelistpageID = request.getParameter("treelistpageID");
		String manageName;
		String treeID = request.getParameter("treeID");
		if (request.getParameter("manageName") == null) {
			manageName = "修改树";
		} else {
			manageName = request.getParameter("manageName");
			manageName = StrTools.charsetFormat(manageName, "ISO8859-1",
					"UTF-8");
		}
		request.setAttribute("manageName", manageName);
		request.setAttribute("isShowTreeMenu", isShowTreeMenu);
		request.setAttribute("treelistpageID", treelistpageID);
		request.setAttribute("treeID", treeID);
		
		String themes=request.getParameter("themes");
		
		if (themes==null || "".equals(themes)) {
			themes="blue";
		}
		request.setAttribute("listpageViewThemes", themes);
	
		
		if ((customPath != null) && (!"".equals(customPath))) {
			try {
				request.getRequestDispatcher(customPath).forward(request,
						ServletActionContext.getResponse());
				return null;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return null;
			}
		//isPreview：0是无工作流访问，easyuiview是平台配置浏览
		} else if ("0".equals(isPreview)) {
			return "list-tab-extjs";
		} else if ("easyuiview".equals(isPreview)) {
			return "easyui-view";
		}
		// else {// 查询菜单 是否启用工作流
		// if ((menuId != null) && (!"".equals(menuId))) {
		// TreeData treeData = systemFrameService.loadResource(menuId);
		// if ((treeData != null)
		// && (treeData.getAttributes() != null)
		// && (Constant.WORKFLOW_ENABLE.equals(treeData
		// .getAttributes().getIsAbleWorkFlow()
		// + ""))) {
		//				
		// }
		// }
		// }
		
		return "list-tab-easyui";
	}

	/**
	 * 加载编辑页
	 * 
	 * @param request
	 * @param formID
	 */
	@SuppressWarnings("unchecked")
	public EditPage editPageforListPage(HttpServletRequest request,
			String formID, EditPage editPage) {

		Map map = null;
		try {
			if ((formID != null) && (!"".equals(formID))
					&& (formID.indexOf(".") == -1)) {
				map = pageService.load_service(formID.trim());
				if (map!=null&map.get("editPage")!=null) {
					editPage = (EditPage) map.get("editPage");
				}
				editPage.setId(formID);
			}

			/**
			 * 功能菜单 定义窗口大小参数
			 */
			String editPageWidth = request.getParameter("editPageWidth");
			String editPageHeight = request.getParameter("editPageHeight");
			editPage.setSelfDefineWidth(editPageWidth);
			editPage.setSelfDefineHeight(editPageHeight);

			request.setAttribute("opertorType", "0");// 新增
			request.setAttribute("editPage", editPage);
		} catch (Exception e) {
			// TODO: handle exception
			//e.printStackTrace();
		}
		return editPage;
	}

	/**
	 * 加载查看页
	 * 
	 * @param request
	 * @param formID
	 */
	public ViewPage viewPageforListPage(HttpServletRequest request,
			String formID, ViewPage viewPage) {

		Map map = null;
		try {
			if ((formID != null) && (!"".equals(formID))
					&& (formID.indexOf(".") != -1)) {
				map = pageService.load_service(formID.trim());
				if (map.get("viewPage")!=null) {
					viewPage = (ViewPage) map.get("viewPage");
				}
				viewPage.setId(formID);
			}

			map = pageService.load_service(formID.trim());

			viewPage = (ViewPage) map.get("viewPage");
			viewPage.setId(formID);
			String viewPageWidth = request.getParameter("viewPageWidth");
			String viewPageHeight = request.getParameter("viewPageHeight");
			viewPage.setSelfDefineWidth(viewPageWidth);
			viewPage.setSelfDefineHeight(viewPageHeight);

			request.setAttribute("viewPage", viewPage);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return viewPage;
	}

	/**
	 * 加载复制页
	 * 
	 * @param request
	 * @param formID
	 */
	public CopyPage copyPageforListPage(HttpServletRequest request,
			String formID, String editFormId) {

		Map map = null;
		CopyPage copyPage = new CopyPage();
		try {
			if ((formID != null) && (!"".equals(formID))
					&& (formID.indexOf(".") == -1)) {
				map = pageService.load_service(editFormId.trim());
				EditPage editpage = (EditPage) map.get("editPage");
				copyPage.setEditPage(editpage);
			}

			map = pageService.load_service(editFormId.trim());

			EditPage editpage = (EditPage) map.get("editPage");
			editpage.setId(formID);
			copyPage.setEditPage(editpage);
			request.setAttribute("copyPage", copyPage);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return copyPage;
	}

	/**
	 * 
	 * 方法描述 设置组合查询区域的值
	 */
	public ListPage initQueryCloumnValue(ListPage listPage) {
		// TODO Auto-generated method stub

		try {
			if (listPage != null) {
				if (listPage.getQueryZonePanel() != null) {
					QueryColumn queryColumn = null;
					for (int i = 0; i < listPage.getQueryZonePanel()
							.getQueryColumns().size(); i++) {
						queryColumn = (QueryColumn) listPage
								.getQueryZonePanel().getQueryColumns().get(i);
						if ((Constant.FORM_FIELD_TYPE_AJAXBOX_TREE + "")
								.equals(queryColumn.getType())) {
							queryColumn
									.setDicDataJson(componentsService
											.loadTreeData(queryColumn
													.getDictionaryID(), "")[0]);
						}
						if ((Constant.FORM_FIELD_TYPE_SELECT + "")
								.equals(queryColumn.getType())) {
							List selectList = new ArrayList();
							Map selectDicMap = componentsService
									.update_Dictionary(queryColumn
											.getDictionaryID());
							Map temSelectMap = null;
							if (selectDicMap != null) {
								Iterator it = selectDicMap.entrySet()
										.iterator();
								/**
								 * 添加默认
								 */
								temSelectMap = new HashMap();
								temSelectMap.put("value", "");
								temSelectMap.put("text", "请选择");
								selectList.add(temSelectMap);

								while (it.hasNext()) {
									Map.Entry entry = (Map.Entry) it.next();
									Object value = entry.getKey();
									Object text = entry.getValue();
									temSelectMap = new HashMap();
									temSelectMap.put("value", value);
									temSelectMap.put("text", text);
									selectList.add(temSelectMap);
								}

							}
							queryColumn.setDicDataJson(JSONUtil
									.serialize(selectList));
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return listPage;
	}

	/**
	 * 
	 * 方法描述 添加级联页面
	 * </p>
	 */
	public ListPage loadCasecadePage(ListPage listPage,
			HttpServletRequest request) {

		try {
			if ((listPage.getEditPage() != null)
					&& (!"".equals(listPage.getEditPage().getId()))
					&& (listPage.getEditPage().getId().indexOf(".") == -1)) {
				listPage.setEditPage(editPageforListPage(request, listPage
						.getEditPage().getId(), listPage.getEditPage()));
			} else {

				EditPage editPage = new EditPage();
				editPage.setTitle("");
				if (listPage.getEditPage() != null
						&& listPage.getEditPage().getId() != null) {
					editPage.setId(listPage.getEditPage().getId() + "");
				}

				listPage.setEditPage(editPage);
			}

			if ((listPage.getViewPage() != null)
					&& (!"".equals(listPage.getViewPage().getId()))
					&& (listPage.getViewPage().getId().indexOf(".") == -1)) {
				listPage.setViewPage(viewPageforListPage(request, listPage
						.getViewPage().getId(), listPage.getViewPage()));
			}

			if ((listPage.getCopyPage() != null)
					&& (!""
							.equals(listPage.getCopyPage().getEditPage()
									.getId()))) {// &&
				// (listPage.getCopyPage().getEditPage().getId().indexOf(".")
				// == -1)不明白什么意思
				listPage.setCopyPage(copyPageforListPage(request, listPage
						.getCopyPage().getEditPage().getId(), listPage
						.getEditPage().getId()));
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return listPage;
	}

	/**
	 * 
	 * 方法描述 添加级联页面 s
	 * </p>
	 */
	public ListPage loadCasecadePageByzidingyi(ListPage listPage,
			HttpServletRequest request) {
		List rowButton2 = listPage.getRowButton2();
		if (rowButton2 != null && rowButton2.size() > 0) {
			for (int i = 0; i < rowButton2.size(); i++) {
				OperateButton ob = (OperateButton) rowButton2.get(i);
				String buttrulesstr = ob.getButtonrules();
				JSONObject buttonrules = JSONObject.fromObject(buttrulesstr);
				if ("jiekou".equals(buttonrules.get("isJS").toString())) {
					if ("editPage".equals(buttonrules.get("tabspagetype")
							.toString())
							&& (!"".equals(buttonrules.get("tabspageurl")
									.toString()))
							&& (buttonrules.get("tabspageurl").toString()
									.indexOf(".") == -1)) {
						Map map = pageService.load_service(buttonrules.get(
								"tabspageurl").toString());
						EditPage editPage = (EditPage) map.get("editPage");
						editPage.setId(buttonrules.get("tabspageurl")
								.toString());
						ob.setEditpage(editPage);
						System.out.println(rowButton2);
					} else if ("viewPage".equals(buttonrules
							.get("tabspagetype").toString())
							&& (!"".equals(buttonrules.get("tabspageurl")
									.toString()))
							&& (buttonrules.get("tabspageurl").toString()
									.indexOf(".") == -1)) {

					} else if ("listPage".equals(buttonrules
							.get("tabspagetype").toString())
							&& (!"".equals(buttonrules.get("tabspageurl")
									.toString()))
							&& (buttonrules.get("tabspageurl").toString()
									.indexOf(".") == -1)) {

					}
				}
			}
		}
		return listPage;
	}

	/**
	 * tree初始化
	 * 
	 * @return
	 */
	public String loadTreeDataAction() {
		HttpServletRequest request = ServletActionContext.getRequest();

		String dicId = request.getParameter("dicId");// 字典ID
		String parentId = request.getParameter("parentId");// 树结构的父节点ID
		String[] strarr = componentsService.loadTreeData(dicId, "", parentId);// (字典ID,"",父节点ID)
		try {
			if (strarr != null) {
				ServletActionContext.getResponse().getWriter().print(strarr[0]);
			} else {
				ServletActionContext.getResponse().getWriter().print("");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public PageService getPageService() {
		return pageService;
	}

	public void setPageService(PageService pageService) {
		this.pageService = pageService;
	}

	public ICodeGenerateService getCodeGenerateService() {
		return codeGenerateService;
	}

	public void setCodeGenerateService(ICodeGenerateService codeGenerateService) {
		this.codeGenerateService = codeGenerateService;
	}

	public TabPageUtil getTabPageUtil() {
		return tabPageUtil;
	}

	public void setTabPageUtil(TabPageUtil tabPageUtil) {
		this.tabPageUtil = tabPageUtil;
	}

	public ComponentsService getComponentsService() {
		return componentsService;
	}

	public void setComponentsService(ComponentsService componentsService) {
		this.componentsService = componentsService;
	}

	public DeskTopService getDeskTopService() {
		return deskTopService;
	}

	public void setDeskTopService(DeskTopService deskTopService) {
		this.deskTopService = deskTopService;
	}

	public SystemFrameService getSystemFrameService() {
		return systemFrameService;
	}

	public void setSystemFrameService(SystemFrameService systemFrameService) {
		this.systemFrameService = systemFrameService;
	}

	public ListPageService getListPageService() {
		return listPageService;
	}

	public void setListPageService(ListPageService listPageService) {
		this.listPageService = listPageService;
	}
	
	/**
	 * 字段授权 操作业务逻辑实体 SET注入 GUOWEIXIN
	 */
	private FieldGrantService fieldGrantService;
	public void setFieldGrantService(FieldGrantService fieldGrantService) {
		this.fieldGrantService = fieldGrantService;
	}
	/**
	 * 权限框架业务操作接口  根据角色名称 得到其角色ID SET注入：GUOWEIXIN
	 */
	private AuthorityFrameService authorityFrameService;
	public AuthorityFrameService getAuthorityFrameService() {
		return authorityFrameService;
	}

	public void setAuthorityFrameService(
			AuthorityFrameService authorityFrameService) {
		this.authorityFrameService = authorityFrameService;
	}
}
