package com.zxt.compplatform.workflow.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.googlecode.jsonplugin.JSONUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.zxt.compplatform.formengine.entity.view.EditColumn;
import com.zxt.compplatform.formengine.entity.view.EditPage;
import com.zxt.compplatform.formengine.entity.view.Tab;
import com.zxt.compplatform.formengine.service.ComponentsService;
import com.zxt.compplatform.formengine.service.PageService;
import com.zxt.compplatform.workflow.Util.ComParUtil;
import com.zxt.compplatform.workflow.entity.ActivityTabSettingVo;
import com.zxt.compplatform.workflow.entity.EngActionWorkflow;
import com.zxt.compplatform.workflow.entity.WorkFlowDataStauts;
import com.zxt.compplatform.workflow.service.ActivityTabService;

public class ActivityTabAction extends ActionSupport {
	private ActivityTabService activityTabService;
//	private SystemFrameService systemFrameService;
	private ComponentsService componentsService;
	private PageService pageService;
	private static final Logger log = Logger.getLogger(ActivityTabAction.class);
	private ActivityTabSettingVo activityTabSettingVo;
//执行
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		String APP_ID = ServletActionContext.getRequest()
				.getParameter("APP_ID");
		try {
			/**
			 * 此处集成时 调用Lims接口 产生mid 和 前驱状态。
			 */

			HttpServletRequest request = ServletActionContext.getRequest();
			String lprid = request.getParameter("lprid");
			String atw = request.getParameter("atw");
			String isAbleWorkFlow = request.getParameter("isAbleWorkFlow");
			String listpageId = request.getParameter("listpageId");
			request.setAttribute("lprid", lprid);
			request.setAttribute("listpageId", listpageId);
			request.setAttribute("isAbleWorkFlow", isAbleWorkFlow);
			request.setAttribute("atw", atw);
			String[] activityParmer = new String[2];
			activityParmer[0] = request.getParameter("mid");
			activityParmer[1] = request.getParameter("precursorId");
			
			EngActionWorkflow engActionWorkflow = activityTabService.getEngActionWorkflow(activityParmer[0], activityParmer[1]);
			if( engActionWorkflow!=null && StringUtils.equals("on", engActionWorkflow.getIsBundling()) ){
				String funContext = engActionWorkflow.getContext();
				if( StringUtils.isNotBlank(funContext) ){
					funContext = StringEscapeUtils.escapeJavaScript(funContext);
					request.setAttribute("activityFunContext", funContext);
					request.setAttribute("isbundling", "on");
				}
			}
			
			/**
			 * 标签List
			 */
			List activityTabList = activityTabService
					.findActivityTabList(activityParmer);
			/**
			 * 输出多标签 加载的业务主键 列表ID
			 */
			request.setAttribute("APP_ID", APP_ID);
			request.setAttribute("activityTabList", activityTabList);
			request.setAttribute("gridId", request.getParameter("gridId"));
			
//			String resId = (String)request.getAttribute("resId");
//			if( StringUtils.isNotBlank(resId) ){
//				TreeData data = systemFrameService.loadResource(resId);
//				if(data!=null && data.getAttributes()!=null){
//					request.setAttribute("comPar", data.getAttributes().getIsWorkFlowComPar());
//					request.setAttribute("comParId", data.getAttributes().getIsWorkFlowComParId());
//					String comparArrayStr = data.getAttributes().getIsWorkFlowComParArray();
//					if( StringUtils.isNotBlank(comparArrayStr) ){
//						Map map = ComParUtil.convertComParArray(comparArrayStr);
//						request.setAttribute("comParArray", map);
//					}
//				}
//			}
			
			/**
			 * 加载多标签按钮 以及按钮提交参数
			 */
			String userId = (String)request.getSession().getAttribute("userId");
			List list = activityTabService.findWorkItemIdByServiceID(APP_ID, userId);
			
			String formId = StringUtils.EMPTY;
			Tab maintab = null;
			for(int i=0;i<activityTabList.size();i++){
				Tab tab = (Tab)activityTabList.get(i);
				if( tab == null ){
					continue;
				}
				if( "on".equals(tab.getIsMainTable()) ){
					maintab = tab;
				}
			}
			if( maintab == null ){
				formId = request.getParameter("formId");
			}else{
				formId = maintab.getUrl();
			}
			String[] parComInsArray = this.getParComInsArray(APP_ID, formId);
			if (parComInsArray!=null) {
				list = this.filterButtonList(list, parComInsArray);
			} 
		
			
			request.setAttribute("buttonList", list);
			
			/**
			 * 返回自定义页面
			 */
			String customPath = request.getParameter("customPath");
			if ((customPath != null) && (!"".equals(customPath))) {
				try {
					request.getRequestDispatcher(customPath).forward(request,
							ServletActionContext.getResponse());
					return null;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					return null;
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error("参数传递不对，检查流程图。");
		}

		return "tab";
	}
	
	private List filterButtonList(List buttonList, String[] parComInsArray){
		if( CollectionUtils.isNotEmpty(buttonList) && ArrayUtils.isNotEmpty(parComInsArray) ){
			List resList = new ArrayList();
			for(int i=NumberUtils.INTEGER_ZERO;i<buttonList.size();i++){
				WorkFlowDataStauts data = (WorkFlowDataStauts)buttonList.get(i);
				if( data==null || StringUtils.isBlank(data.getStitchingParameter()) ){
					continue;
				}
				String stitchingParameter = data.getStitchingParameter();
				String conParam = stitchingParameter.substring(stitchingParameter.indexOf("&con_param="),stitchingParameter.length());
				conParam = conParam.replaceAll("&con_param=", StringUtils.EMPTY);
				if( conParam.indexOf("&") != -1 ){
					conParam = conParam.substring(NumberUtils.INTEGER_ZERO,conParam.indexOf("&"));
				}
				if( ArrayUtils.contains(parComInsArray, conParam) ){
					resList.add(data);
				}
			}
			buttonList = resList;
		}
		return buttonList;
	}
	
	private String[] getParComInsArray(String appId, String formId){
		Map listPageMap = pageService.load_service(formId);
		EditPage editPage = (EditPage)listPageMap.get("editPage");
		if( editPage!=null &&
				StringUtils.equals("on", editPage.getWorkflowParCom()) && 
				StringUtils.isNotBlank(appId) ){
			editPage.setId(formId);
			String parComId = editPage.getWorkflowParComId();
			String parComArray = editPage.getWorkflowParComArray();
			if( StringUtils.isNotBlank(parComId) && StringUtils.isNotBlank(parComArray) ){
				Map typeMap = ComParUtil.convertComParArray(parComArray);
				this.loadEditPageData(editPage, appId);
				List columnList = editPage.getEditColumn();
				String parComIns = this.getParComIns(columnList, typeMap, parComId);
				if( StringUtils.isNotBlank(parComIns) ){
					String[] parComInsArray = parComIns.split(",");
					return parComInsArray;
				}
			}
		}
		return ArrayUtils.EMPTY_STRING_ARRAY;
	}
	
	private String getParComIns(List columnList, Map typeMap, String parComId){
		if( CollectionUtils.isNotEmpty(columnList) && MapUtils.isNotEmpty(typeMap) ){
			for(int i=NumberUtils.INTEGER_ZERO;i<columnList.size();i++){
				EditColumn column = (EditColumn)columnList.get(i);
				if( column==null ){
					continue;
				}
				if( StringUtils.equals(column.getName(), parComId) ){
					String parComIns = (String)typeMap.get(column.getData());
					return parComIns;
				}
			}
		}
		return StringUtils.EMPTY;
	}
	
	private EditPage loadEditPageData(EditPage editPage, String appId){
		String initWorkflowSql = editPage.getFindSql().substring(0,
				editPage.getFindSql().indexOf("where"));
		editPage.setFindSql(initWorkflowSql + "  where APP_ID=? ");
		editPage = componentsService.loadEditPage(editPage,
				new String[] { appId });
//		List editColumnList = editPage.getEditColumn();
//		for (int i = 0; i < editColumnList.size(); i++) {
//			EditColumn editColumn = (EditColumn) editColumnList.get(i);
//			if ("APP_ID".equals(editColumn.getName())) {
//				((EditColumn) (editPage.getEditColumn().get(i)))
//						.setData(appId);
//			}
//		}
		return editPage;
	}

	/**
	 * 获取活动节点
	 * 
	 * @return
	 */
	public String findActivityIDs() {

		try {
			String workflowParmer[] = new String[2];
			workflowParmer[0] = ServletActionContext.getRequest().getParameter(
					"processId");
			workflowParmer[1] = ServletActionContext.getRequest().getParameter(
					"mid");

			List list = activityTabService.findActivityIDs(workflowParmer);

			String dataSourceJson = JSONUtil.serialize(list);
			ServletActionContext.getResponse().getWriter()
					.write(dataSourceJson);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 获取前驱注解状态
	 * 
	 * @return
	 */
	public String findPrecursorIDs() {

		try {
			String workflowParmer[] = new String[3];
			workflowParmer[0] = ServletActionContext.getRequest().getParameter(
					"processId");
			workflowParmer[1] = ServletActionContext.getRequest().getParameter(
					"mid");
			workflowParmer[2] = ServletActionContext.getRequest().getParameter(
					"activityId");

			List list = activityTabService.findPrecursorIDs(workflowParmer);

			String dataSourceJson = JSONUtil.serialize(list);
			ServletActionContext.getResponse().getWriter()
					.write(dataSourceJson);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 获取前驱多标签配置
	 * 
	 * @return
	 */
	public String findActivitySetList() {

		try {

			String[] activityParmer = new String[2];
			activityParmer[0] = ServletActionContext.getRequest().getParameter(
					"mid");
			activityParmer[1] = ServletActionContext.getRequest().getParameter(
					"precursorId");

			List activityTabList = activityTabService
					.findActivityTabList(activityParmer);
			Map map = new HashMap();
			if (activityTabList == null) {
				activityTabList = new ArrayList();
			}

			map.put("rows", activityTabList);
			map.put("total", new Integer(activityTabList.size()));

			String json = JSONUtil.serialize(map);
			ServletActionContext.getResponse().getWriter().write(json);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 加载节点多标签配置
	 * 
	 * @return
	 */
	public String loadActivityConfig() {
		String id = ServletActionContext.getRequest().getParameter("id");

		activityTabSettingVo = activityTabService.loadActivityConfig(id);

		// try {
		// String json = JSONUtil.serialize(activityTabService
		// .findEngEditForm());
		// ServletActionContext.getRequest()
		// .setAttribute("editFormJson", json);
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		return "editPage";
	}

	/**
	 * 保存节点多标签
	 * 
	 * @return
	 */
	public String saveActivityConfig() {
		activityTabService.saveActivityConfig(activityTabSettingVo);
		try {
			ServletActionContext.getResponse().getWriter().write("sucess");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 删除节点多标签
	 * 
	 * @return
	 */
	public String deleteActivityConfig() {
		String id = ServletActionContext.getRequest().getParameter("id");
		activityTabService.deleteActivityConfig(id);
		return null;
	}

	/**
	 * 查找所有编辑表单
	 * 
	 * @return
	 */
	public String findEditForm() {

		try {
			String json = JSONUtil.serialize(activityTabService
					.findEngEditForm());
			ServletActionContext.getResponse().getWriter().write(json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String editEvelset(){
		HttpServletRequest request = ServletActionContext.getRequest();
		try{
			String processId = request.getParameter("processId");
			String precursor = request.getParameter("precursor");
			EngActionWorkflow engActionWorkflow = activityTabService.getEngActionWorkflow(processId, precursor);
			String ibcontext = StringEscapeUtils.escapeJavaScript(engActionWorkflow.getContext());
			String ibremark = StringEscapeUtils.escapeJavaScript(engActionWorkflow.getRemark());
			request.setAttribute("keyid", engActionWorkflow.getId());
			request.setAttribute("isBuilding", engActionWorkflow.getIsBundling());
			request.setAttribute("ibcontext", ibcontext);
			request.setAttribute("ibremark", ibremark);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "editEvelset";
	}
	
	public String saveEvelset(){
		HttpServletRequest request = ServletActionContext.getRequest();
		try{
			String processId = request.getParameter("processId");
			String precursor = request.getParameter("precursor");
			String keyid = request.getParameter("keyid");
			String context = request.getParameter("buildingfunction");
			String remark = request.getParameter("buildingremark");
			String isBuilding = request.getParameter("isBuilding");
			if( !StringUtils.equals("on", isBuilding) ){
				activityTabService.delEngActionWorkflow(keyid);
				return null;
			}
			EngActionWorkflow engActionWorkflow = new EngActionWorkflow();
			engActionWorkflow.setIsBundling(isBuilding);
			engActionWorkflow.setId(keyid);
			engActionWorkflow.setPrecursor(precursor);
			engActionWorkflow.setProcessId(processId);
			engActionWorkflow.setRemark(remark);
			engActionWorkflow.setContext(context);
			activityTabService.saveEngActionWorkflow(engActionWorkflow);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ActivityTabService getActivityTabService() {
		return activityTabService;
	}

	public void setActivityTabService(ActivityTabService activityTabService) {
		this.activityTabService = activityTabService;
	}

	public ActivityTabSettingVo getActivityTabSettingVo() {
		return activityTabSettingVo;
	}

	public void setActivityTabSettingVo(
			ActivityTabSettingVo activityTabSettingVo) {
		this.activityTabSettingVo = activityTabSettingVo;
	}

//	public void setSystemFrameService(SystemFrameService systemFrameService) {
//		this.systemFrameService = systemFrameService;
//	}

	public void setComponentsService(ComponentsService componentsService) {
		this.componentsService = componentsService;
	}

	public void setPageService(PageService pageService) {
		this.pageService = pageService;
	}

}
