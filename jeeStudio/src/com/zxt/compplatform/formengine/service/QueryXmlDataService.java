package com.zxt.compplatform.formengine.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.zxt.compplatform.formengine.constant.Constant;
import com.zxt.compplatform.formengine.dao.ComponentsDao;
import com.zxt.compplatform.formengine.dao.IQueryXmlData;
import com.zxt.compplatform.formengine.entity.dataset.TableVO;
import com.zxt.compplatform.formengine.entity.view.ListPage;
import com.zxt.compplatform.formengine.entity.view.PagerEntiy;
import com.zxt.compplatform.formengine.entity.view.QueryColumn;
import com.zxt.compplatform.formengine.entity.view.QueryZone;
import com.zxt.compplatform.workflow.dao.Daibanfl3WorkFlowDao;

/**
 * 表单配置xml查询实现
 * 
 * @author 007
 */
public class QueryXmlDataService implements IQueryXmlDataService {
	
	private static final Logger log = Logger
			.getLogger(QueryXmlDataService.class);
	/**
	 * xml查询接口
	 */
	private IQueryXmlData queryXmlData;
	/**
	 * 表单组件持久化操作
	 */
	private ComponentsDao componentsDao;
	/**
	 * 待办dao
	 */
	private Daibanfl3WorkFlowDao daibandao;

	public ComponentsDao getComponentsDao() {
		return componentsDao;
	}

	public void setComponentsDao(ComponentsDao componentsDao) {
		this.componentsDao = componentsDao;
	}

	public QueryXmlDataService() {

	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.formengine.service.IQueryXmlDataService#queryBlobByFormId(java.lang.String)
	 */
	public String queryBlobByFormId(String url) throws Exception {
		String str = "select fo_settings from eng_form_form where fo_id ='"
				+ url + "' ";
		return null;
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.formengine.service.IQueryXmlDataService#queryFormData(com.zxt.compplatform.formengine.entity.view.ListPage, java.lang.String[], javax.servlet.http.HttpServletRequest)
	 */
	public PagerEntiy queryFormData(ListPage listPage, String[] parmerValue,
			HttpServletRequest request) throws Exception {
		String sql=null;
		//guoweixin  自定义SQL
		if("on".equals(listPage.getIsOriginalSql())){
			sql=listPage.getIsOriginalSqlContext();
		}
		else  sql = listPage.getSql();
		List queryZoneList = null;
		QueryZone queryZone = null;
		List queryColumns = null;
		QueryColumn queryColumn = null;
		/**
		 * 拼接组合查询
		 */
		String workflow_fiter = request.getParameter("workflow_fiter");
		String isAbleWorkFlow = request.getParameter("isAbleWorkFlow");
		String condition_fiter = "";
		String orderBy = "";

		TableVO tbv = (TableVO) listPage.getTable().get(0);
		TableVO xxxx = (TableVO) tbv.getFromTableList().get(0);

		String tablename = xxxx.getName();
		if (Constant.WORKFLOW_ENABLE.equals(isAbleWorkFlow)
				&& workflow_fiter.equals("caogao")) {
			condition_fiter = " and " + tablename + ".env_datastate = '"
					+ Constant.WORKFLOW_QIDONG_STATE + "'";
		} else if (Constant.WORKFLOW_ENABLE.equals(isAbleWorkFlow)
				&& workflow_fiter.equals("daibanxiang")) {

			String userId = request.getSession().getAttribute("userId")
					.toString();
			List list = daibandao.findAppid(userId);
			if (list != null && list.size() != 0) {
				condition_fiter += " and " + tablename + ".app_id in ("
						+ list.get(0) + ")";
			}

		} else if (Constant.WORKFLOW_ENABLE.equals(isAbleWorkFlow)
				&& workflow_fiter.equals("yibanxiang")) {

			String userId = request.getSession().getAttribute("userId")
					.toString();
			List list = componentsDao.findAppidsInLog(userId);

			if (list != null && list.size() != 0) {
				for (int i = 0; i < list.size(); i++) {
					if (i == 0) {
						condition_fiter += " and " + tablename
								+ ".app_id in ('"
								+ ((Map) list.get(0)).get("app_id") + "'";
					} else {
						condition_fiter += ",'"
								+ ((Map) list.get(i)).get("app_id") + "'";
					}
				}
				condition_fiter += ")";
			} else {
				//condition_fiter += " and " + tablename + ".app_id in ('-1')";
				//此处工作流BUG，是因为 所查的工作流日志没有添加进来，郭伟新记得添加哦。
			}

		}
		queryZoneList = listPage.getQueryZone();
		if (queryZoneList != null) {
			try {
				String start = "";
				String end = "";
				for (int j = 0; j < queryZoneList.size(); j++) {
					queryZone = (QueryZone) queryZoneList.get(j);
					queryColumns = queryZone.getQueryColumns();

					if (sql.indexOf("where") < 0) {
						sql = sql + " where 1=1";
					}
					for (int i = 0; i < queryColumns.size(); i++) {
						queryColumn = (QueryColumn) queryColumns.get(i);
						
						/*
						 * 构件排序方式
						 */
						if (!queryColumn.getListSort().equals("")) {
							if (orderBy.equals("")) {

								orderBy = " order by ";
								orderBy += queryColumn.getTableName() + "."
										+ queryColumn.getName() + " "
										+ queryColumn.getListSort();
							} else {

								orderBy = orderBy + " ,"
										+ queryColumn.getTableName() + "."
										+ queryColumn.getName() + " "
										+ queryColumn.getListSort();
							}
						}

						switch (queryColumn.getOperateType()) {
						case 1:// 等于
							if ((request.getParameter(queryColumn.getName()) != null)
									&& (!""
											.equals(request
													.getParameter(queryColumn
															.getName())))) {
								sql = sql
										+ " and "
										+ queryColumn.getTableName()+"."+queryColumn.getName()
										+ " = '"
										+ request.getParameter(queryColumn
												.getName()) + "'";
							}
							break;
						case 2:// 大于
							if ((request.getParameter(queryColumn.getName()) != null)
									&& (!""
											.equals(request
													.getParameter(queryColumn
															.getName())))) {
								try {
									String parmer=request.getParameter(queryColumn.getName());
//									int parmer = Integer
//											.parseInt(request
//													.getParameter(queryColumn
//															.getName()));
									sql = sql + " and " +queryColumn.getTableName()+"."+queryColumn.getName()
											+ " >'" + parmer + "' ";
								} catch (Exception e) {
									// TODO: handle exception
									log.error("大于 操作符，控件未配置验证。。");
								}

							}
							break;
						case 3:// 小于
							if ((request.getParameter(queryColumn.getName()) != null)
									&& (!""
											.equals(request
													.getParameter(queryColumn
															.getName())))) {
								try {
									String parmer=request.getParameter(queryColumn.getName());
//									int parmer = Integer
//											.parseInt(request
//													.getParameter(queryColumn
//															.getName()));
									sql = sql + " and " +queryColumn.getTableName()+"."+queryColumn.getName()
											+ " <'" + parmer + "' ";
								} catch (Exception e) {
									// TODO: handle exception
									log.error("小于 操作符，控件未配置验证。。");
								}

							}
							break;
						case 4:// 模糊查询
							if ((request.getParameter(queryColumn.getName()) != null)
									&& (!""
											.equals(request
													.getParameter(queryColumn
															.getName())))) {
								sql = sql
										+ " and "
										+queryColumn.getTableName()+"."+queryColumn.getName()
										+ " like '%"
										+ request.getParameter(queryColumn
												.getName()) + "%'";
							}
							break;

						case 5:// in
							if ((request.getParameter(queryColumn.getName()) != null)
									&& (!""
											.equals(request
													.getParameter(queryColumn
															.getName())))) {
								sql = sql
										+ " and "
										+queryColumn.getTableName()+"."+queryColumn.getName()
										+ " in ('"
										+ request.getParameter(queryColumn
												.getName()) + "')";
							}
							break;

						case 6:// 范围查询
							start = request.getParameter(queryColumn.getName()
									+ "_st");
							end = request.getParameter(queryColumn.getName()
									+ "_end");

							if ((!"".equals(start) && (start != null))
									&& ((!"".equals(end))) && (end != null)) {

								try {
									// int st=Integer.parseInt(start);
									// int ed=Integer.parseInt(end);

									sql = sql + " and ("
											+ queryColumn.getTableName()+"."+queryColumn.getName()
											+ " between '" + start + "' and  '"
											+ end + "')";
								} catch (Exception e) {
									// TODO: handle exception
									log.error("范围查询，控件未配置验证，或控件类型不对。。");
								}

							} else {
								if (("".equals(start) && (start == null))
										&& ("".equals(end)) && (end == null)) {
									continue;
								} else if ((!"".equals(start) && (start != null))) {
									try {
										sql = sql + " and "
												+queryColumn.getTableName()+"."+queryColumn.getName()
												+ " > '" + start + "'";
									} catch (Exception e) {
										// TODO: handle exception
										log.error("范围查询，控件未配置验证，或控件类型不对。。");
									}
								} else if (!("".equals(end)) && (end != null)) {
									try {
										sql = sql + " and "
												+ queryColumn.getTableName()+"."+queryColumn.getName()
												+ " < '" + end + "'";
									} catch (Exception e) {
										// TODO: handle exception
										log.error("范围查询，控件未配置验证，或控件类型不对。。");
									}
								}
							}
							break;
						case 7:// 闭区间
							start = request.getParameter(queryColumn.getName()
									+ "_st");
							end = request.getParameter(queryColumn.getName()
									+ "_end");

							if ((!"".equals(start) && (start != null))
									&& ((!"".equals(end))) && (end != null)) {

								try {
									// int st=Integer.parseInt(start);
									// int ed=Integer.parseInt(end);

									sql = sql + " and " +queryColumn.getTableName()+"."+queryColumn.getName()
											+ " >= '" + start + "'";
									sql = sql + " and " +queryColumn.getTableName()+"."+queryColumn.getName()
											+ " <= '" + end + "'";
								} catch (Exception e) {
									// TODO: handle exception
									log.error("范围查询，控件未配置验证，或控件类型不对。。");
								}

							} else {
								if (("".equals(start) && (start == null))
										&& ("".equals(end)) && (end == null)) {
									continue;
								} else if ((!"".equals(start) && (start != null))) {
									try {
										sql = sql + " and "
												+ queryColumn.getTableName()+"."+queryColumn.getName()
												+ " >= '" + start + "'";
									} catch (Exception e) {
										// TODO: handle exception
										log.error("范围查询，控件未配置验证，或控件类型不对。。");
									}
								} else if (!("".equals(end)) && (end != null)) {
									try {
										sql = sql + " and "
												+ queryColumn.getTableName()+"."+queryColumn.getName()
												+ " <= '" + end + "'";
									} catch (Exception e) {
										// TODO: handle exception
										log.error("范围查询，控件未配置验证，或控件类型不对。。");
									}
								}
							}
							break;
						default:
							if ((request.getParameter(queryColumn.getName()) != null)
									&& (!""
											.equals(request
													.getParameter(queryColumn
															.getName())))) {
								sql = sql
										+ " and "
										+ queryColumn.getTableName()+"."+queryColumn.getName()
										+ " like '%"
										+ request.getParameter(queryColumn
												.getName()) + "%'";
							}
							break;
						}
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
		if (sql.indexOf("where") < 0) {
			sql = sql + " where 1=1";
		}
		sql = sql + condition_fiter;
		sql = sql + orderBy;

		return componentsDao.queryFormData(sql, parmerValue,
				listPage, request);
	}

	public IQueryXmlData getQueryXmlData() {
		return queryXmlData;
	}

	public void setQueryXmlData(IQueryXmlData queryXmlData) {
		this.queryXmlData = queryXmlData;
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.formengine.service.IQueryXmlDataService#findBolbById(java.lang.String)
	 */
	public String findBolbById(String url) throws Exception {
		String str = "select fo_settings from eng_form_form where fo_id =? ";

		return componentsDao.findBlobXMLById(str, url);
	}

	public Daibanfl3WorkFlowDao getDaibandao() {
		return daibandao;
	}

	public void setDaibandao(Daibanfl3WorkFlowDao daibandao) {
		this.daibandao = daibandao;
	}

}
