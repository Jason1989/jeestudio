package com.zxt.compplatform.formengine.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.zxt.compplatform.authority.entity.Role;
import com.zxt.compplatform.authority.service.AuthorityFrameService;
import com.zxt.compplatform.authority.service.FieldGrantService;
import com.zxt.compplatform.codegenerate.service.ICodeGenerateService;
import com.zxt.compplatform.formengine.entity.dataset.TableVO;
import com.zxt.compplatform.formengine.entity.view.EditColumn;
import com.zxt.compplatform.formengine.entity.view.Param;
import com.zxt.compplatform.formengine.entity.view.Tab;
import com.zxt.compplatform.formengine.entity.view.ViewColumn;
import com.zxt.compplatform.formengine.entity.view.ViewPage;
import com.zxt.compplatform.formengine.service.ComponentsService;
import com.zxt.compplatform.formengine.service.IQueryXmlDataService;
import com.zxt.compplatform.formengine.service.PageService;
import com.zxt.compplatform.formengine.util.StrTools;
import com.zxt.compplatform.formengine.util.TabPageUtil;
import com.zxt.compplatform.organization.dao.OrganizationDao;

/**
 * 查看页Action
 * @author 007
 */
public class ViewPageAction extends ActionSupport {
	
	private static final Log log = LogFactory.getLog(ViewPageAction.class);
	
	private static final long serialVersionUID = 1L;
	/**
	 * result 动态页面地址
	 */
	String pageUrl;
	/**
	 * 多标签页操作工具
	 */
	private TabPageUtil tabPageUtil;

	/**
	 * 暂时存储区域
	 */
	public static Map map = new HashMap();

	/**
	 * 页面业务操作接口
	 */
	private PageService pageService;
	/**
	 * xml数据查询业务操作接口
	 */
	private IQueryXmlDataService queryXmlDataService;

	/**
	 * 代码生成操作接口
	 */
	private ICodeGenerateService codeGenerateService;
	/**
	 * 页面组件操作接口
	 */
	private ComponentsService componentsService;

	/**
	 * 展示查看页
	 * @return
	 */
	public String executeViewPage() {
		HttpServletRequest request = null;
		ViewPage viewPage = null;
		String urlParmer = "";//加载多标签传的页面参数
		try {
			request = ServletActionContext.getRequest();
			String formId = request.getParameter("formId");
			request.setAttribute("formId", formId);
			String viewPageDivId = request.getParameter("viewPageDivId");
			request.setAttribute("viewPageDivId", viewPageDivId);
			String parentAppId = request.getParameter("parentAppId");
			viewPage = null;
			Param param=null;
			String key="";
			String value="";
			
			//第一次固化时。此参数值用于区分是否 在编辑页时VALUE显示的内容。
			String valueDefine=request.getParameter("valueDefine");
			if(valueDefine==null) valueDefine="";
			request.setAttribute("valueDefine",valueDefine);
			
			
			map = pageService.load_service(formId);
			if (map != null) {
				viewPage = (ViewPage) map.get("viewPage");
				
			}

			if (viewPage == null) {
				if ("0".equals(request.getParameter("preview"))) {
					return "preview-viewPage";
				} else {
					return "view-page";
				}
			}
			
			/**
			 * 多标签获取主键参数，应替换获取页面参数
			 */
			if (viewPage.getViewPageParams() != null) {
				for (int j = 0; j < viewPage.getViewPageParams().size(); j++) {
					param = (Param) viewPage.getViewPageParams().get(j);
					key = param.getKey().trim();
					
					value = request.getParameter(key);
					value = StrTools.charsetFormat(value, "ISO8859-1", "UTF-8");
					if ((value != null) && (!"".equals(value))) {
						urlParmer="&"+key+"="+value;
					}
				}
				request.setAttribute("urlParmer", urlParmer);
			}
			
			
			viewPage.setId(request.getParameter("formId").trim());
			viewPage = componentsService.loadViewPage(viewPage, request);
			List list = viewPage.getViewColumn();
			String appid = "";
			for(int i = 0;i < list.size();i++){
				ViewColumn viewColumn = (ViewColumn)list.get(i);
				if("APP_ID".equals(viewColumn.getName())){
					appid = viewColumn.getData();
					break;
				}
			}
			request.setAttribute("appid", appid);
			/**
			 * 编辑页多标签
			 */
			if (viewPage != null) {
				if (viewPage.getTabs() != null) {
					if (viewPage.getIsUseTab() != null) {
						if (viewPage.getIsUseTab().booleanValue()) {
							List tabPageList = viewPage.getTabs();
							if (tabPageList != null) {
								Tab tab = null;
								for (int i = 0; i < tabPageList.size(); i++) {
									tab = (Tab) tabPageList.get(i);
									if (tab.getUrl().equals(viewPage.getId())) {
										tabPageList.remove(i);
									}
								}
								tabPageList = tabPageUtil.initTabList(
										tabPageList, request, null);
								viewPage.setTabs(tabPageList);
							}
						}
					}
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
				
		request.setAttribute("viewPage", viewPage);
		if ("0".equals(request.getParameter("preview"))) {
			return "preview-viewPage";
		}

		if (viewPage != null && viewPage.getIsUseTab() != null
				&& viewPage.getIsUseTab().booleanValue()) {

			return "loadTabViewPage";

		}

		try {
			String customPath = request.getParameter("customPath");
			if ((customPath != null) && (!"".equals(customPath))) {
				request.getRequestDispatcher(customPath).forward(request,
						ServletActionContext.getResponse());
				return null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("跳转异常");
		}
//目的得到表名称：
	String tableNameReal=null;
	 List listView=viewPage.getTable();
	  TableVO tableVO=(TableVO)listView.get(0);
	  if(tableVO.getName()==null){
		  log.error("ViewPageAction  in tableName  is null...//表名为空，需要在表单列表--》详情页中 重新保存一次即可解决。");
			return "view-page";
	  }else{
		  tableNameReal=tableVO.getName();
	  }
	 /**
		 * AUTHOR:GUOWEIXIN
		 * 1得到角色ID，表名 字段授权 编辑页--> 设计
		 * 2查询出数据库该角色相应的字段权限。再和现有的对比，筛选到相应结果
		 */
		Long rid=null;
		/**
		 * 得到当前角色名称，根据名称得到其角色ID
		 */
		String roleName=(String)request.getSession().getAttribute("stwitchRole");//在session范围中得到用户ID
		String ridReal = authorityFrameService.initAuthorityRidFrameByAccount(roleName);
		if(ridReal!=null)
			rid=new Long(ridReal);
		else return "view-page";		
		Map<Long,Map> mapRoot=(Map<Long,Map>)fieldGrantService.load_service(rid);
		if(mapRoot!=null){
			Map<String,List> mapTable=mapRoot.get(rid);
			List strFields=mapTable.get(tableNameReal);
			if(strFields!=null){
				//所有字段列
				List listAll=viewPage.getViewColumn();
				List resultAll=new ArrayList();//结果字段
				  if(listAll!=null){
						for (int i = 0; i < listAll.size(); i++) {
							ViewColumn field=(ViewColumn)listAll.get(i);
							if(field!=null){
								for(int j=0;j<strFields.size();j++){
									String comStrField=(String)strFields.get(j);
									if(comStrField.equals(field.getName())){
										resultAll.add(field);
									}
								}
							}
						}
						viewPage.setViewColumn(resultAll);
				  }	
			}	
		}
		
		//-----end GUOWEIXIN
		
		//-----start lenny
		String forwardType = request.getParameter("forwardType");//固化功能 动态result
		String pageUrl = request.getParameter("pageUrl");
		this.setPageUrl(pageUrl);//result 动态页面地址
		
		if ("curing".equals(forwardType)){
			return "curingPage"; //struts2 动态result 地址
		}
		//-----end lenny
		
		return "view-page";
	}

	public PageService getPageService() {
		return pageService;
	}

	public void setPageService(PageService pageService) {
		this.pageService = pageService;
	}

	public IQueryXmlDataService getQueryXmlDataService() {
		return queryXmlDataService;
	}

	public void setQueryXmlDataService(IQueryXmlDataService queryXmlDataService) {
		this.queryXmlDataService = queryXmlDataService;
	}

	public ICodeGenerateService getCodeGenerateService() {
		return codeGenerateService;
	}

	public void setCodeGenerateService(ICodeGenerateService codeGenerateService) {
		this.codeGenerateService = codeGenerateService;
	}

	public ComponentsService getComponentsService() {
		return componentsService;
	}

	public void setComponentsService(ComponentsService componentsService) {
		this.componentsService = componentsService;
	}

	public TabPageUtil getTabPageUtil() {
		return tabPageUtil;
	}

	public void setTabPageUtil(TabPageUtil tabPageUtil) {
		this.tabPageUtil = tabPageUtil;
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

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	
}
