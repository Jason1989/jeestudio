package com.zxt.compplatform.formengine.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.zxt.compplatform.formengine.constant.Constant;
import com.zxt.compplatform.formengine.dao.ComponentsDao;
import com.zxt.compplatform.formengine.entity.view.EditPage;
import com.zxt.compplatform.formengine.entity.view.Field;
import com.zxt.compplatform.formengine.entity.view.ListPage;
import com.zxt.compplatform.formengine.entity.view.PagerEntiy;
import com.zxt.compplatform.formengine.entity.view.Param;
import com.zxt.compplatform.formengine.entity.view.QueryColumn;
import com.zxt.compplatform.formengine.entity.view.QueryZone;
import com.zxt.compplatform.formengine.service.ComponentsService;
import com.zxt.compplatform.formengine.service.PageService;
import com.zxt.compplatform.formengine.service.QueryXmlDataService;
import com.zxt.compplatform.formengine.service.SystemFrameService;
import com.zxt.compplatform.formengine.util.StrTools;
import com.zxt.compplatform.formengine.util.WorkFlowDataStautsXmlUtil;
import com.zxt.compplatform.workflow.Util.WorkFlowUtil;
import com.zxt.compplatform.workflow.entity.TaskFormNodeEntity;
import com.zxt.compplatform.workflow.entity.WorkFlowDataStauts;
import com.zxt.compplatform.workflow.service.WorkFlowFrameService;
import com.zxt.framework.dictionary.entity.DataDictionary;
import com.zxt.framework.dictionary.entity.SqlObj;
import com.zxt.framework.dictionary.service.IDataDictionaryService;
import com.zxt.framework.dictionary.service.ISqlDicService;

/**
 * 界面组件操作Action
 * @author 007
 */
public class ComponentsAction extends ActionSupport {

	private static final Logger log = Logger.getLogger(ComponentsAction.class);
	private ComponentsService componentsService;
	/**
	 * 字典操作接口 GUOWEIXIN
	 */
	private ISqlDicService sqlDicService;
	/**
	 * 表单组件持久化接口 GUOWEIXIN
	 */
	private ComponentsDao componentsDao;
	
	/**
	 * 界面业务操作接口
	 */
	private PageService pageService;
	/**
	 * xml数据查询操作接口
	 */
	private QueryXmlDataService queryXmlDataService;
	/**
	 * 系统业务操作接口
	 */
	private SystemFrameService systemFrameService;
	/**
	 * 工作流业务操作接口
	 */
	private WorkFlowFrameService workFlowFrameService;
	/**
	 * 数据字典业务操作接口
	 */
	private IDataDictionaryService iDataDictionaryService;

	/**
	 * json传递数据
	 */
	private String json;
	/**
	 * 表单id
	 */
	private String formId;
	/**
	 * 选择列
	 */
	private String[] selectColumns;

	public String[] getSelectColumns() {
		return selectColumns;
	}

	public void setSelectColumns(String[] selectColumns) {
		this.selectColumns = selectColumns;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String execute() throws Exception {
		return null;
	}

	/**
	 * 级联下拉框
	 * 
	 * @return
	 */
	public String loadCasCadeSel() {

		String dataSourceId = Request().getParameter("dataSourceId");
		String querySql = Request().getParameter("querySql");

		String dictionaryId = Request().getParameter("dictionaryId");
		String newValue = Request().getParameter("newValue");
		Object[] conditions = new Object[] {};
		if (dictionaryId != null && !dictionaryId.equals("")) {
			DataDictionary dictionary = iDataDictionaryService
					.findById(dictionaryId);
			querySql = dictionary.getExpression();
			dataSourceId = dictionary.getDataSource().getId();
			conditions = new Object[] { newValue };
		}
		// dataSourceId = "0a83565f28b013048632ca023d812125";
		// querySql = "select f.id id,f.name value from LMS_D_TSK_TYPE f";
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter out = null;
		try {
			out = response.getWriter();
			List selList = componentsService.queryByDataSource(dataSourceId,
					querySql, conditions);
			out.print(JSONArray.fromObject(selList).toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	/**
	 * 删除数据
	 * 
	 * @return
	 */
	public String deleteData() {
		String formId = Request().getParameter("listFormID");
		ListPage listPage = null;

		Map map = pageService.load_service(formId);
		if (map != null) {
			if (map.get("listPage") != null) {
				listPage = (ListPage) map.get("listPage");
				listPage.setId(formId);
				componentsService.deleteData(listPage, Request());
			}
		}
		return null;
	}

	/**
	 * 刷新列表数据 + 组合查询
	 * 
	 * @return
	 */
	public String reshData() {
		String formId = Request().getParameter("formId");
		Map map = pageService.load_service(formId);
		PagerEntiy pagerEntiy=null;
		if (map != null) {
			ListPage listPage = (ListPage) map.get("listPage");
			if (listPage != null) {
				List listParmer = listPage.getListPageParams();
				listPage.setId(formId);
				Param param = null;
				String[] value = null;
				boolean flag = true;
				/**
				 * 获取 参数值
				 * 
				 */
				if (listParmer != null) {
					if (listParmer.size() != 0) {
						String key = "";
						String parmerValue = "";
						value = new String[listParmer.size()];
						for (int i = 0; i < value.length; i++) {
							param = (Param) listParmer.get(i);
							key = param.getKey().trim();
							if ("SYSPARAM".equals(param.getFlagType())) {
								parmerValue = Request().getSession().getAttribute(key)+"";
							}else {
								parmerValue = Request().getParameter(key).trim();
								parmerValue = StrTools.charsetFormat(parmerValue,
										"ISO8859-1", "UTF-8");
							}
							if ((parmerValue == null)
									|| "".equals(parmerValue.trim())) {
								flag = false;
							}
							value[i] = parmerValue;
						}
					}
				}

				Map data = new HashMap();
				List formList = null;
				try {
					if (flag) {
						
							pagerEntiy	 = queryXmlDataService.queryFormData(listPage,
								value, Request());
							formList=pagerEntiy.getResult();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
				}
				if (formList != null) {
					String menuId = Request().getParameter("menuId");

					formList = componentsService.findMenuFilter(menuId,
							formList);
					String isPreview = Request().getParameter("preview");
					if ("extjs".equals(isPreview)) {
						data.put("success", "true");
						//data.put("result", pagger(Request(), formList));
						data.put("totalCounts", new Integer(formList.size()));
						json = JSONObject.fromObject(data).toString();
					} else {
						data.put("total", pagerEntiy.getTotal());
						//formList = pagger(Request(), formList);
						formList = WorkFlowDataStautsXmlUtil
								.transferListWorkFlowStauts(formList);

						data.put("rows", formList);

						json = JSONObject.fromObject(data).toString();
					}
					json = JSONObject.fromObject(data).toString();

				} else {
					json = "{total:0,rows:[]}";
				}
			}
		} else {
			json = "{total:0,rows:[]}";
		}
		return "json";
	}

	/**
	 * 查询人员列表
	 * 
	 * @return
	 */
	public String queryForHumanList() {
		String orgid = Request().getParameter("orgid");
		String dictionaryID = Request().getParameter("dictionaryId");
		String state = Request().getParameter("state");
		List list = componentsService.queryForHumanList(orgid, dictionaryID,
				state);
		Map map = new HashMap();
		if (list == null) {
			list = new ArrayList();
		}
		map.put("rows", list);
		map.put("total", new Integer(list.size()));
		String json = JSONObject.fromObject(map).toString();
		try {
			ServletActionContext.getResponse().getWriter().write(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 保存编辑页数据
	 * 
	 * @return
	 */
	public String dynamicSave() {
		String savetype = Request().getParameter("type");
		String method = Request().getParameter("method");
		try {
			EditPage editPage = null;
			String formId = Request().getParameter("formId");
			Map map = null;
			map = pageService.load_service(formId);

			if (map != null) {
				if (map.get("editPage") != null) {
					editPage = (EditPage) map.get("editPage");
				}
			}

			if (editPage != null) {
				editPage.setId(formId);
				String opertorType = Request().getParameter("opertorType");
				/**
				 * 0 不启用工作 -1 启动异常
				 */
				WorkFlowDataStauts workflowDataStauts = new WorkFlowDataStauts();
				/**
				 * 启动流程实例 start
				 */
				if (savetype != null && "save".equals(savetype.trim())) {
					TaskFormNodeEntity taskFormNodeEntity = workFlowFrameService
							.findTaskFormNodeEntity(formId);
					if (taskFormNodeEntity != null) {
						HttpServletRequest req = Request();
						// workFlowFrameService.startProcessInstance(taskFormNodeEntity,
						// req);
						workFlowFrameService.startProcessInstance(
								taskFormNodeEntity, req, editPage);

						/**
						 * 获取工作流状态信息对象 xml格式
						 */
						// workflowDataStauts = WorkFlowDataStautsXmlUtil
						// .workFlowDataStautsToXml(WorkFlowUtil
						// .findInitActivity(taskFormNodeEntity
						// .getProcessInstanceID()));
						/**
						 * 动态表单启动流程时 保存工作流状态
						 */
						String processInstanceID = "";
						if (Request().getParameter(
								Constant.CUSTOM_PROCESSDEFID_ID) != null
								&& !"".equals(Request().getParameter(
										Constant.CUSTOM_PROCESSDEFID_ID))) {
							processInstanceID = Request().getParameter(
									Constant.CUSTOM_PROCESSDEFID_ID);
						} else {
							processInstanceID = taskFormNodeEntity
									.getProcessInstanceID();
						}
						workflowDataStauts = WorkFlowUtil.findInitActivity(
								processInstanceID, savetype);
					}
				} else if (savetype != null
						&& "transave".equals(savetype.trim())) {
					workflowDataStauts
							.setToTransferDefStauts_text(Constant.WORKFLOW_QIDONG_STATE);
				}
				/**
				 * 启动流程实例 end
				 */
				// 保存业务数据
				if ("copy".equals(method)) {
					// editPage.setId(RandomGUID.geneGuid());
				}
				/**
				 * 测试大数据量
				 */
					componentsService.dynamicSave(Request(), editPage,
							workflowDataStauts);
				//此处取数据 得到保存前SQL和保存后SQL的 ID 此处只要保存后SQL  
				//String beforeSqlId=editPage.getBefore_sql();
				String afterSqlId=editPage.getAfter_sql();
					saveAfterSql(afterSqlId);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error("启用了工作流，提交数据时用户未登录");
		}
		try {
			ServletActionContext.getResponse().getWriter().write("sucess");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
 
	/**
	 * 	保存后SQL
	 * @param afterSqlId:String  SQL语句/触发器/存储过程ID 
	 */
	public void saveAfterSql(String afterSqlId){
		if(!"".equals(afterSqlId) && null!=afterSqlId){
			SqlObj sqlObj=sqlDicService.findSqlById(afterSqlId);
			//得到其SqlObj 对象 进行执行该SQL条件语句 
			/*
			 * param1:数据源ID，param2:执行的SQL语句,param3:条件 null,null
			 */
			String[] conditions=null;
			List listParam=new ArrayList();
			listParam=sqlObj.getSqlParam();
			if(listParam.size()>0){
				conditions=new String[listParam.size()];
			}
			for(int i=0;i<listParam.size();i++){
				Param param=(Param)listParam.get(i);
				if("1".equals(param.getType())){//手动输入
					conditions[i]= param.getValue();
				}
				if("3".equals(param.getType())){//变量
					conditions[i]= ServletActionContext.getRequest().getParameter(param.getValue());
				}
			}	
			List list = componentsDao.queryForAfterSql(sqlObj.getSqldic_dataSourceid(),sqlObj.getSqldic_expression(), conditions, Request());
			
		}
	}
	/**
	 * 获取 数据字典值 动态 ，静态
	 * 
	 * @return
	 */
	public String loadDictionary() {
		Map map = new HashMap();
		String dictionaryID = Request().getParameter("dictionaryID");

		componentsService.load_Dictionary(dictionaryID);

		return null;
	}

	public ComponentsService getComponentsService() {
		return componentsService;
	}

	public void setComponentsService(ComponentsService componentsService) {
		this.componentsService = componentsService;
	}

	public HttpServletRequest Request() {

		return ServletActionContext.getRequest();
	}

	public QueryXmlDataService getQueryXmlDataService() {
		return queryXmlDataService;
	}

	public void setQueryXmlDataService(QueryXmlDataService queryXmlDataService) {
		this.queryXmlDataService = queryXmlDataService;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public PageService getPageService() {
		return pageService;
	}

	public void setPageService(PageService pageService) {
		this.pageService = pageService;
	}

    public List pagger(HttpServletRequest request, List data) {
		List temList = null;
		try {
			temList = new ArrayList();

			/**
			 * jquery方式
			 */
			int pageNum = Integer.parseInt(Request().getParameter("page"));
			int size = Integer.parseInt(Request().getParameter("rows"));
			int end = pageNum * size;
			int start = end - size;

			// int size = Integer.parseInt(Request().getParameter("limit"));
			// int start = Integer.parseInt(Request().getParameter("start"));
			// int end = start + size;

			/**
			 * ExtJs方式
			 */
			int maxSize = data.size();
			if (end > maxSize) {
				end = maxSize;
			}
			if (start < 0) {
				start = 0;
			}
			for (int i = start; i < end; i++) {
				temList.add(data.get(i));
			}
		} catch (Exception e) {
			// TODO: handle exception
			temList = data;
		}
		return temList;
	}
	/**
	 * 批量删除
	 * 
	 * @return
	 */
	public String bulkDelete() {
		try {

			String formId = Request().getParameter("listFormID");

			ListPage listPage = (ListPage) (pageService.load_service(formId)
					.get("listPage"));
			listPage.setId(formId);
			componentsService.bulkDelete(listPage, Request());
		} catch (Exception e) {
			// TODO: handle exception
			log.error(" bulkDelete  id error and listPage id error.. ");
		}
		return null;
	}

	/**
	 * 导出datagrid的流
	 * 
	 * @return
	 */
	public String exportForListPage() {
		return "export";
	}

	/**
	 * 导出datagrid的流
	 * 
	 * @return
	 */
	public InputStream getDownloadFile() {
		// TODO Auto-generated method stub
		ListPage listPage = null;
		if(formId==null)
			 formId = Request().getParameter("formId");
		Map map = pageService.load_service(formId);
		String sql="";
		if (map != null) {
			listPage = (ListPage) map.get("listPage");
			sql=listPage.getSql();
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		QueryZone queryZone = null;
		List queryColumns = null;
		QueryColumn queryColumn = null;
		String orderBy = ""; 
		String condition_fiter = "";
		List queryZoneList =listPage.getQueryZone();
		if (queryZoneList!=null) {
			try {
				for (int j = 0; j < queryZoneList.size(); j++) {
					queryZone = (QueryZone) queryZoneList.get(j);
					queryColumns = queryZone.getQueryColumns();
					
					if (sql.indexOf("where")<0) {
						sql=sql+" where 1=1";
					}
					for (int i = 0; i < queryColumns.size(); i++) {
						queryColumn = (QueryColumn) queryColumns.get(i);
						
						/*
						 * 构件排序方式
						 */
						if(!queryColumn.getListSort().equals("")){
							if(orderBy.equals("")){
								
								orderBy = " order by ";
								orderBy += queryColumn.getTableName() + "." + queryColumn.getName() + " " + queryColumn.getListSort();
							}else{
								
								orderBy = orderBy + " ," + queryColumn.getTableName() + "." + queryColumn.getName() + " " + queryColumn.getListSort();								
							}							
						}
						
						switch (queryColumn.getOperateType()) {
						case 1://模糊查询
							if ((request.getParameter(queryColumn.getName()) != null)
									&& (!"".equals(request.getParameter(queryColumn
											.getName())))) {
								sql = sql + " and " + queryColumn.getName() + " = '"
										+ request.getParameter(queryColumn.getName())
										+ "'";
							}
							break;	
						case 2://大于
							if ((request.getParameter(queryColumn.getName()) != null)
									&& (!"".equals(request.getParameter(queryColumn
											.getName())))) {
								try {
									int parmer=Integer.parseInt(request.getParameter(queryColumn.getName()));
									sql = sql + " and " + queryColumn.getName() + " >"
											+ parmer+" ";
								} catch (Exception e) {
									// TODO: handle exception
									log.error("大于 操作符，控件未配置验证。。");
								}
								
							}
							break;
						case 3://小于
							if ((request.getParameter(queryColumn.getName()) != null)
									&& (!"".equals(request.getParameter(queryColumn
											.getName())))) {
								try {
									int parmer=Integer.parseInt(request.getParameter(queryColumn.getName()));
									sql = sql + " and " + queryColumn.getName() + " <"
											+ parmer+" ";
								} catch (Exception e) {
									// TODO: handle exception
									log.error("小于 操作符，控件未配置验证。。");
								}
								
							}
							break;		
						case 4://模糊查询
							if ((request.getParameter(queryColumn.getName()) != null)
									&& (!"".equals(request.getParameter(queryColumn
											.getName())))) {
								sql = sql + " and " + queryColumn.getName() + " like '%"
										  + StrTools.charsetFormat(request.getParameter(queryColumn.getName()).trim(),
												"ISO8859-1", "UTF-8")
										  + "%'";
							}
							break;	
							
						case 5://in
							if ((request.getParameter(queryColumn.getName()) != null)
									&& (!"".equals(request.getParameter(queryColumn
											.getName())))) {
								sql = sql + " and " + queryColumn.getName() + " in ('"
										+ request.getParameter(queryColumn.getName())
										+ "')";
							}
							break;	
							
						case 6://范围查询
							String start=request.getParameter(queryColumn.getName()+"_st");
							String end=request.getParameter(queryColumn.getName()+"_end");
							
							if ((!"".equals(start)&&(start!=null))&&((!"".equals(end)))&&(end!=null)) {
								
								try {
									
									sql = sql + " and (" + queryColumn.getName() + " between '"
									+ start+ "' and  '"+end+"')";
								} catch (Exception e) {
									// TODO: handle exception
									log.error("范围查询，控件未配置验证，或控件类型不对。。");
								}
								
							}else{
								if (("".equals(start)&&(start==null))&&("".equals(end))&&(end==null)){
									continue;
								}else if((!"".equals(start)&&(start!=null))){
									try {
										sql = sql + " and " + queryColumn.getName() + " > '"+start+"'";
									} catch (Exception e) {
										// TODO: handle exception
										log.error("范围查询，控件未配置验证，或控件类型不对。。");
									}
								}else if(!("".equals(end))&&(end!=null)){
									try {
										sql = sql + " and " + queryColumn.getName() + " < '"+end+"'";
									} catch (Exception e) {
										// TODO: handle exception
										log.error("范围查询，控件未配置验证，或控件类型不对。。");
									}
								}
							}
						break;
						default:
							if ((request.getParameter(queryColumn.getName()) != null)
									&& (!"".equals(request.getParameter(queryColumn
											.getName())))) {
								sql = sql + " and " + queryColumn.getName() + " like '%"
										+ request.getParameter(queryColumn.getName())
										+ "%'";
							}
							break;
						}
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		if (sql.indexOf("where")<0) {
			sql=sql+" where 1=1";
		}
		sql= sql +condition_fiter;
		sql = sql + orderBy;		
		listPage.setSql(sql);   
		return componentsService.exportForListPage(formId, listPage, Request(),
				selectColumns);
	}

	/**
	 * 
	 * @author HouLiangWei
	 * @createTime Jul 9, 2011 2:43:48 PM
	 * 
	 * @param
	 * @function 确定导出excel工作簿的名字
	 * @return
	 * @throws
	 */
	public String getDownloadChineseFileName() {
		String downloadChineseFileName = "数据列表.xls";
		try {
			downloadChineseFileName = new String(downloadChineseFileName
					.getBytes(), "ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return downloadChineseFileName;
	}

	/**
	 * 通过页面id获取字段
	 * 
	 * @return
	 */
	public void findFiedsByPageId() {
		// TODO Auto-generated method stub
		ListPage listPage = null;
		Map map = pageService.load_service(formId);
		if (map != null) {
			listPage = (ListPage) map.get("listPage");
		}
		List list = listPage.getFields();
		List newlist = new ArrayList();

		for (int i = 0; i < list.size(); i++) {
			Field column = (Field) list.get(i);
			if (!column.getVisible().equals("false")) {// 判断是否 只显示列表字段的导出
				Map mapxxx = new HashMap();
				mapxxx.put("column", column.getDataColumn());
				mapxxx.put("name", column.getName());
				newlist.add(mapxxx);
			}
		}
		String xxx = JSONArray.fromObject(newlist).toString();
		try {
			ServletActionContext.getResponse().getWriter().print(xxx);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public SystemFrameService getSystemFrameService() {
		return systemFrameService;
	}

	public void setSystemFrameService(SystemFrameService systemFrameService) {
		this.systemFrameService = systemFrameService;
	}

	public WorkFlowFrameService getWorkFlowFrameService() {
		return workFlowFrameService;
	}

	public void setWorkFlowFrameService(
			WorkFlowFrameService workFlowFrameService) {
		this.workFlowFrameService = workFlowFrameService;
	}

	public IDataDictionaryService getIDataDictionaryService() {
		return iDataDictionaryService;
	}

	public void setIDataDictionaryService(
			IDataDictionaryService dataDictionaryService) {
		iDataDictionaryService = dataDictionaryService;
	}

	public ISqlDicService getSqlDicService() {
		return sqlDicService;
	}

	public void setSqlDicService(ISqlDicService sqlDicService) {
		this.sqlDicService = sqlDicService;
	}

	public ComponentsDao getComponentsDao() {
		return componentsDao;
	}

	public void setComponentsDao(ComponentsDao componentsDao) {
		this.componentsDao = componentsDao;
	}

}
