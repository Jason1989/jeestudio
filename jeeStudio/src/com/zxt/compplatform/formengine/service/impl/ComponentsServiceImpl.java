package com.zxt.compplatform.formengine.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.struts2.ServletActionContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zxt.compplatform.formengine.constant.Constant;
import com.zxt.compplatform.formengine.dao.ComponentsDao;
import com.zxt.compplatform.formengine.dao.ComponentsTreeDao;
import com.zxt.compplatform.formengine.entity.dataset.FieldDefVO;
import com.zxt.compplatform.formengine.entity.view.Button;
import com.zxt.compplatform.formengine.entity.view.EditColumn;
import com.zxt.compplatform.formengine.entity.view.EditPage;
import com.zxt.compplatform.formengine.entity.view.Event;
import com.zxt.compplatform.formengine.entity.view.ListPage;
import com.zxt.compplatform.formengine.entity.view.Param;
import com.zxt.compplatform.formengine.entity.view.TextColumn;
import com.zxt.compplatform.formengine.entity.view.ViewColumn;
import com.zxt.compplatform.formengine.entity.view.ViewPage;
import com.zxt.compplatform.formengine.service.ComponentsService;
import com.zxt.compplatform.formengine.service.ComponentsTreeService;
import com.zxt.compplatform.formengine.util.EditColumnUtil;
import com.zxt.compplatform.formengine.util.StrTools;
import com.zxt.compplatform.formengine.util.WorkFlowDataStautsXmlUtil;
import com.zxt.compplatform.workflow.dao.impl.TijiaoWorkFlowDaoImpl;
import com.zxt.compplatform.workflow.entity.WorkFlowDataStauts;
import com.zxt.framework.common.util.RandomGUID;
import com.zxt.framework.dictionary.entity.DataDictionary;
import com.zxt.framework.dictionary.service.IDataDictionaryService;

/**
 * 表单控件业务实现
 * 
 * @author 007
 */
public class ComponentsServiceImpl implements ComponentsService {
	private static final Logger log = Logger
			.getLogger(ComponentsServiceImpl.class);

	/**
	 * 表单组件持久化接口
	 */
	private ComponentsDao componentsDao;
	/**
	 * 组件树持久化接口
	 */
	private ComponentsTreeDao componentsTreeDao;
	/**
	 * 数据字典业务操作接口
	 */
	private IDataDictionaryService iDataDictionaryService;
	/**
	 * 树控件操作接口
	 */
	private ComponentsTreeService componentsTreeService;
	/**
	 * 工作流提交实现
	 */
	private TijiaoWorkFlowDaoImpl tijiaoWorkFlowDaoImpl;

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.formengine.service.ComponentsService#queryByDataSource(java.lang.String, java.lang.String, java.lang.Object[])
	 */
	public List queryByDataSource(String dataSourceId, String querySql,
			Object[] conditions) {
		DataSource ds = componentsDao.queryForDataSource(dataSourceId);
		JdbcTemplate jt = new JdbcTemplate();
		jt.setDataSource(ds);
		List list = new ArrayList();
		list = jt.queryForList(querySql, conditions);
		List queryList = new ArrayList();
	
		if (list != null && list.size() != 0) {
			for (int i = 0; i < list.size(); i++) {
				Map rec = new HashMap();
				Map map = (Map) list.get(i);
				Set keys = map.keySet();
				Iterator it = keys.iterator();

				String key = it.next().toString();
				rec.put("key", map.get(key));
				key = it.next().toString();
				rec.put("value", map.get(key));
				queryList.add(rec);
			}
		}

		return queryList;
	}

	/**
	 * 获取人员树控件列表数据
	 * 
	 * @param orgid
	 * @return
	 */
	public List queryForHumanList(String orgid, String dictionaryID,
			String state) {
		String sql = "";
		if ("open".equals(state) || "closed".equals(state)) {
			sql = "select userid as id,uname as text,oid as parent_i_d,oname from user_union_view where oid in (select t.oid as oid  from t_organization t left join t_org_org t_o on t.oid = t_o.downid where  t_o.UPID = '"
					+ orgid + "')";
		} else {
			sql = "select userid as id,uname as text,oid as parent_i_d,oname from user_union_view where oid in (select t.oid as oid  from t_organization t left join t_org_org t_o on t.oid = t_o.downid where  t.OID = '"
					+ orgid + "')";
		}
		DataDictionary dataDictionary = iDataDictionaryService
				.findById(dictionaryID);
		List list = componentsTreeDao.treeOrgData(sql, componentsDao
				.queryForDataSource(dataDictionary.getDataSource().getId()));
		return list;
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.formengine.service.ComponentsService#deleteData(com.zxt.compplatform.formengine.entity.view.ListPage, javax.servlet.http.HttpServletRequest)
	 */
	public int deleteData(ListPage listPage, HttpServletRequest request) {
		// TODO Auto-generated method stub
		String sql = StringUtils.EMPTY;
		if (StringUtils.equals("on", listPage.getIsPseudoDeleted())) {
			sql = " update " + listPage.getKeyTable()
					+ " set IS_PSEUDO_DELETED='1' where 1=1 ";
		} else {
			sql = " DELETE FROM " + listPage.getKeyTable() + " WHERE 1=1 ";
		}
		String[] parmers = null;

		Event event = null;
		Button button = null;
		Param param = null;
		/**
		 * 封装过滤条件
		 */
		if (listPage != null) {
			if (listPage.getRowButton() != null) {
				for (int i = 0; i < listPage.getRowButton().size(); i++) {
					button = ((Button) listPage.getRowButton().get(i));
					if (Constant.DELETEOPERATOR.equals(button.getButtonName())) {
						event = (Event) button.getEvent().get(0);
						param = (Param) event.getParas().get(0);
						parmers = param.getValue().split(",");
						break;
					}
				}
			}
		}

		/**
		 * 拼接sql 封装参数值
		 * 
		 */
		if (parmers != null) {
			for (int j = 0; j < parmers.length; j++) {

				sql = sql + " and " + parmers[j] + "=? ";
				parmers[j] = request.getParameter(parmers[j].trim());
				parmers[j] = StrTools.charsetFormat(parmers[j], "ISO8859-1",
						"UTF-8");
			}
		}

		return componentsDao.deleteData(sql, parmers, listPage);
	}
 	
	/* (non-Javadoc)
	 * @see com.zxt.compplatform.formengine.service.ComponentsService#dynamicSave(javax.servlet.http.HttpServletRequest, com.zxt.compplatform.formengine.entity.view.EditPage, com.zxt.compplatform.workflow.entity.WorkFlowDataStauts)
	 */
	public int dynamicSave(HttpServletRequest request, EditPage editPage,
			WorkFlowDataStauts workFlowDataStautsAdd) {
		// TODO Auto-generated method stub
		/**
		 * 获取 xml生成的 insert原始sql,定义参数数组（参数顺序从sql中获取）
		 */
		String sql = "";
		String[] endParmer = null;
		String key = "";// 参数名
		String value = "";// 参数值
		Param param = null;// 参数实体
		String type = "";// 参数类型
		String method = request.getParameter("method");
		Map<String, EditColumn> editColumnMap = EditColumnUtil
				.transformEditCloumnListToMap(editPage);// 数据字段名
		// 获取editcolumn的map
		Map map = null;//
		List<Param> list = null;//
		List<EditColumn> editColumnList = editPage.getEditColumn();
		if (Constant.FORM_STATUS_ADD
				.equals(request.getParameter("opertorType"))) {
			String[] idParmer = null;
			sql = editPage.getInsertSql();

			List keyList = editPage.getKeyList();
			if (keyList != null) {
				idParmer = new String[keyList.size()];
				for (int i = 0; i < keyList.size(); i++) {
					FieldDefVO f = (FieldDefVO) keyList.get(i);
					idParmer[i] = f.getToFieldName();
				}
			}

			if (editPage.getInsertParams() != null) {
				EditColumn editColumn = null;
				for (int i = 0; i < editPage.getInsertParams().size(); i++) {
					map = (Map) (editPage.getInsertParams().get(i));
					list = (List) map.get(editPage.getInsertTableName());
					endParmer = new String[list.size()];
					/**
					 * 设置参数 end
					 */
					boolean flag = true;
					for (int j = 0; j < list.size(); j++) {
						flag = true;
						param = list.get(j);
						type = param.getType();
						key = param.getKey();
						/**
						 * 判断是否是主键
						 */
						for (int k = 0; k < idParmer.length; k++) {
							if (idParmer[k].equals(key)) {
								if ("int".equals(type)) {
									Random random=new Random();
									endParmer[j] =random.nextInt()+"";
								}else if("numeric".equals(type)) {
									Random random=new Random();
									endParmer[j] =random.nextInt(999999999)+"";
								}else if("bigint".equals(type)) {
									Random random=new Random();
									endParmer[j] =random.nextInt(999999999)+"";
								}else{
									endParmer[j] = RandomGUID.geneGuid();
								}
								
								flag = false;
								// for (int k2 = 0; k2 <
								// editPage.getEditColumn().size(); k2++) {
								// editColumn=(EditColumn)editPage.getEditColumn().get(k2);
								// if (editColumn.getName().equals(key)) {
								// if
								// ("NUMBER".equals(editColumn.getFieldDataType()))
								// {
								// endParmer[j]=new
								// java.util.Date().getTime()+new
								// Random().nextInt(9999)+"";
								// }
								// }
								// }
								break;
							}
						}
						/**
						 * 非主键
						 * 
						 */
						if (flag) {
							/**
							 * 设置工作流字段
							 */
							if (key.equals("ENV_DATAMETER")) {
								endParmer[j] = WorkFlowDataStautsXmlUtil
										.workFlowDataStautsToXml(workFlowDataStautsAdd);
							} else if (key.equals("ENV_DATASTATE")) {
								endParmer[j] = workFlowDataStautsAdd
										.getToTransferDefStauts_text()
										+ "";
							} else {
								value = EditColumnUtil.cloumnSetValue(request,
										editColumnMap, key);
								endParmer[j] = value;
							}
						}

					}
					break;
					/**
					 * 设置参数 end
					 */
				}
			}
		} else if (Constant.FORM_STATUS_EDIT.equals(request
				.getParameter("opertorType"))) {

			/**
			 * 工作流处理
			 */

			WorkFlowDataStauts workFlowDataStauts = new WorkFlowDataStauts();
			workFlowDataStauts.setProcessDefId(request
					.getParameter("processDefId"));
			workFlowDataStauts.setMid(request.getParameter("mid"));
			workFlowDataStauts.setActivityDefId(request
					.getParameter("activityDefId"));

			if (request.getParameter("toTransferDefStautsText") != null) {
				workFlowDataStauts.setToTransferDefStautsText(StrTools
						.charsetFormat(request
								.getParameter("toTransferDefStautsText"),
								"ISO8859-1", "UTF-8"));
			} else {
				workFlowDataStauts.setToTransferDefStautsText(request
						.getParameter("ENV_DATASTATE"));
			}
			workFlowDataStauts.setToTransferDefStautsValue(request
					.getParameter("toTransferDefStautsValue"));

			if (request.getParameter("status_text") != null) {
				workFlowDataStauts.setToTransferDefStauts_text(StrTools
						.charsetFormat(request.getParameter("status_text"),
								"ISO8859-1", "UTF-8"));
			}

			String envDataState = request.getParameter("ENV_DATASTATE");
			if (Constant.WORKFLOW_QIDONG_STATE.equals(envDataState)) {
				workFlowDataStauts = workFlowDataStautsAdd;
			}

			/**
			 * 修改数据
			 */
			sql = editPage.getUpdateSql();

			for (int i = 0; i < editPage.getUpdateParams().size(); i++) {
				map = (Map) editPage.getUpdateParams().get(i);
				list = (List) map.get(editPage.getUpdataTableName());
				endParmer = new String[list.size()];

				for (int j = 0; j < list.size(); j++) {
					param = list.get(j);
					key = param.getKey();
					value = request.getParameter(key);
					/**
					 * 设置工作流字段
					 */
					if (key.equals("ENV_DATAMETER")) {
						if ((workFlowDataStauts.getMid() != null)
								&& (!"".equals(workFlowDataStauts.getMid()))) {
							endParmer[j] = WorkFlowDataStautsXmlUtil
									.workFlowDataStautsToXml(workFlowDataStauts);
						} else {

							endParmer[j] = value;
						}
					} else if (key.equals("ENV_DATASTATE")) {
						if ((workFlowDataStauts.getToTransferDefStauts_text() != null)
								&& (!"".equals(workFlowDataStauts
										.getToTransferDefStauts_text()))) {
							endParmer[j] = workFlowDataStauts
									.getToTransferDefStauts_text()
									+ "";
						} else {

							endParmer[j] = value;
						}
					} else {
						endParmer[j] = EditColumnUtil.cloumnSetValue(request,
								editColumnMap, key);
					}
				}
				break;
			}
		}
		int result=0;
		//是复制功能
		if ("copy".equals(method)) {
			int batchsize=Integer.parseInt(request.getParameter("batchsize"));//在复制功能中：批量添加记录 数。
			System.out.println("---->批量添加条数："+batchsize);
			for(int i=0;i<batchsize;i++){
				result = dynamicSaveCopy(sql,endParmer,editPage,map,list,param,key,type,workFlowDataStautsAdd,request,value,editColumnMap);
			}
			System.out.println("---->批量添加条数："+batchsize+"------------------->结束");
		}else {
			 result = componentsDao.dynamicSave(sql, endParmer, editPage,editColumnMap, list);
		}
		
		
		/**
		 * 工作流提交
		 */
		String REC_ID = "";
		EditColumn editColumn;
		TextColumn textColumn;
		for (int i = 0; i < editColumnList.size(); i++) {
			editColumn = editColumnList.get(i);
			textColumn = editColumn.getTextColumn();
			if ("true".equals(textColumn.getIsworkflow())) {
				REC_ID = request.getParameter(editColumn.getName());
				break;
			}
		}
		// 这个REC_ID 是用作标识某个节点的参与者，可以选择人员、角色或者组织机构
		String[][] obj = new String[3][2];
		obj[0][0] = "APP_ID";
		obj[0][1] = request.getParameter("APP_ID");
		obj[1][0] = Constant.DEFAULT_WORKFLOW_PARMER_KEY;
		obj[1][1] = request.getParameter(Constant.DEFAULT_WORKFLOW_PARMER_KEY)
				+ "";
		obj[2][0] = "REC_ID";
		obj[2][1] = REC_ID;

		try {

			String mainTable = request.getParameter("MAIN_APP_ID");// 主表业务主键
			if (request.getSession().getAttribute("userId") != null) {
				if ((request.getParameter("workitemId") != null)
						&& (!"".equals(request.getParameter("workitemId")))) {

					String userId = request.getSession().getAttribute("userId")
							.toString();
					int workitemId = Integer.parseInt(request
							.getParameter("workitemId"));

					if ((mainTable != null) && (!"".equals(mainTable))) {
						if (mainTable.equals(request.getParameter("APP_ID"))) {
							if (Constant.FORM_STATUS_EDIT.equals(request
									.getParameter("opertorType"))) {
								tijiaoWorkFlowDaoImpl.wancheng(userId, obj,
										workitemId);

							}

						}
					}
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
		// return 0;
	}

	/***
	 * 复制 批量添加  
	 * dynamicSaveCopy
	 */
	  public int dynamicSaveCopy(String sql,String[] endParmer,EditPage editPage,Map map,List<Param> list,Param param,String key,String type,
			  WorkFlowDataStauts workFlowDataStautsAdd,HttpServletRequest request,String value,Map<String, EditColumn> editColumnMap){
			String[] idParmer = null;
			sql = editPage.getInsertSql();
			List keyList = editPage.getKeyList();
			if (keyList != null) {
				idParmer = new String[keyList.size()];
				for (int i = 0; i < keyList.size(); i++) {
					FieldDefVO f = (FieldDefVO) keyList.get(i);
					idParmer[i] = f.getToFieldName();
				}
			}

			if (editPage.getInsertParams() != null) {
				EditColumn editColumn = null;
				for (int i = 0; i < editPage.getInsertParams().size(); i++) {
					map = (Map) (editPage.getInsertParams().get(i));
					list = (List) map.get(editPage.getInsertTableName());
					endParmer = new String[list.size()];
					/**
					 * 设置参数 end
					 */
					boolean flag = true;
					for (int j = 0; j < list.size(); j++) {
						flag = true;
						param = list.get(j);
						type = param.getType();
						key = param.getKey();
						/**
						 * 判断是否是主键
						 */
						for (int k = 0; k < idParmer.length; k++) {
							if (idParmer[k].equals(key)) {

								endParmer[j] = RandomGUID.geneGuid();
								flag = false;
								// for (int k2 = 0; k2 <
								// editPage.getEditColumn().size(); k2++) {
								// editColumn=(EditColumn)editPage.getEditColumn().get(k2);
								// if (editColumn.getName().equals(key)) {
								// if
								// ("NUMBER".equals(editColumn.getFieldDataType()))
								// {
								// endParmer[j]=new
								// java.util.Date().getTime()+new
								// Random().nextInt(9999)+"";
								// }
								// }
								// }
								break;
							}
						}
						/**
						 * 非主键
						 * 
						 */
						if (flag) {
							/**
							 * 设置工作流字段
							 */
							if (key.equals("ENV_DATAMETER")) {
								endParmer[j] = WorkFlowDataStautsXmlUtil
										.workFlowDataStautsToXml(workFlowDataStautsAdd);
							} else if (key.equals("ENV_DATASTATE")) {
								endParmer[j] = workFlowDataStautsAdd
										.getToTransferDefStauts_text()
										+ "";
							} else if ((request.getParameter(key) == null)
									|| ("".equals(request.getParameter(key)))) {
								if ("datetime".equals(type)) {
									endParmer[j] = "";
								} else {
									endParmer[j] = Constant.DB_FIELD_DEFAULT_VALUE;
								}
								if ("numeric".equals(type)) {
									endParmer[j] = "0";
								} else {
									endParmer[j] = Constant.DB_FIELD_DEFAULT_VALUE;
								}
								if ("decimal".equals(type)) {
									endParmer[j] = "0";
								} else {
									endParmer[j] = Constant.DB_FIELD_DEFAULT_VALUE;
								}
							} else {
								value = request.getParameter(key);
								endParmer[j] = value;
							}
						}

					}
					break;
					/**
					 * 设置参数 end
					 */
				}
			}  
			return componentsDao.dynamicSave(sql, endParmer, editPage,
					editColumnMap, list);
	  }	
	
	/**
	 * 
	 */
	/* (non-Javadoc)
	 * @see com.zxt.compplatform.formengine.service.ComponentsService#loadEditPage(com.zxt.compplatform.formengine.entity.view.EditPage, java.lang.String[])
	 */
	public EditPage loadEditPage(EditPage editPage, String[] parmerNameArray) {
		// TODO Auto-generated method stub

		editPage = componentsDao.loadEditPage(editPage, parmerNameArray);
		/**
		 * 加载数据字典值
		 * 
		 */
		int columnType = -1;
		EditColumn editColumn = null;
		Map dictionartData = null;
		EditColumn temColumn = null;
		String viewData = "";// 显示值
		String[] checkBoxValues;

		for (int i = 0; i < editPage.getEditColumn().size(); i++) {
			editColumn = (EditColumn) editPage.getEditColumn().get(i);
			/**
			 * 设置默认控件类型值
			 */
			if ("".equals(editColumn.getType())
					|| (editColumn.getType() == null)) {
				columnType = Constant.FORM_FIELD_TYPE_TEXT;
			} else {
				columnType = Integer.parseInt(editColumn.getType());
			}
			switch (columnType) {
			case Constant.FORM_FIELD_TYPE_SELECT:
				dictionartData = update_Dictionary(editColumn.getDictionaryID());
				((EditColumn) editPage.getEditColumn().get(i))
						.setDictionaryData(dictionartData);
				break;
			case Constant.FORM_FIELD_TYPE_AJAXBOX_TREE:
				String[] array = loadTreeData(editColumn.getDictionaryID(),
						editColumn.getData());
				((EditColumn) editPage.getEditColumn().get(i))
						.getTreeComponents().setJsonTreeData(array[0]);
				((EditColumn) editPage.getEditColumn().get(i))
						.getTreeComponents().setConversionDataValue(array[1]);
				break;
			case Constant.FORM_FIELD_TYPE_RADIO:
				dictionartData = update_Dictionary(editColumn.getDictionaryID());
				((EditColumn) editPage.getEditColumn().get(i))
						.setDictionaryData(dictionartData);
				break;
			case Constant.FORM_FIELD_TYPE_CHECKBOX:

				dictionartData = update_Dictionary(editColumn.getDictionaryID());
				
				Set<String> key = dictionartData.keySet();
				String dictionartDataValue="";
				if (dictionartData != null) {
					checkBoxValues = editColumn.getData().split(",");
					for (int j = 0; j < checkBoxValues.length; j++) {
						for (Iterator it = key.iterator(); it.hasNext();) {
				            String s = (String) it.next();
				            dictionartDataValue=dictionartData.get(s)+"";
				            if (dictionartDataValue.equals(checkBoxValues[j])) {
				            	dictionartData.put(s, "checked");
				            	break;
				            }
				            
				        }
						
						
					}
					((EditColumn) editPage.getEditColumn().get(i))
							.setDictionaryData(dictionartData);
				}

				break;
			case Constant.FORM_FIELD_TYPE_TEXT:
				dictionartData = update_Dictionary(editColumn.getDictionaryID());
				((EditColumn) editPage.getEditColumn().get(i))
						.setDictionaryData(dictionartData);
				/**
				 * 设置文本框显示值
				 */
				String data = editColumn.getData();
				boolean flag = false;
				if (dictionartData != null && StringUtils.isNotBlank(data)) {
					if (StringUtils.contains(data, ",")) {
						String[] datas = data.split(",");
						String temdata = StringUtils.EMPTY;
						for (int j = 0; j < datas.length; j++) {
							if (dictionartData.get(datas[j]) != null) {
								String thisdata = dictionartData.get(datas[j])
										.toString();
								temdata += thisdata + ",";
							}
						}
						if (StringUtils.isNotBlank(temdata)) {
							data = temdata.substring(0, temdata.length() - 1);
							flag = true;
						}
					} else {
						if (dictionartData.get(data) != null) {
							data = dictionartData.get(data).toString();
							flag = true;
						}
					}

					if(flag&&editColumn!=null&&editColumn.getTextColumn()!=null&&editColumn.getTextColumn().getIs_listPageForvalue()){
						if(editColumn.getTextColumn().getIs_listPageForvalue()){
							editColumn.getDictionary().setDictionaryName(data);
							break;
						}
					}else if (flag) {
						// 保存数据字典value temColumn
						temColumn = new EditColumn();
						temColumn.setName(editColumn.getName());
						temColumn.setData(editColumn.getData());
						temColumn.setType(Constant.FORM_FIELD_TYPE_HIDDEN + "");
						temColumn.setTextColumn(editColumn.getTextColumn());

						// 显示数据字典text
						editColumn.setData(data);
						editColumn.setName(editColumn.getName()
								+ "_ENV_DIC_VIEWDATA");
						editPage.getEditColumn().add(temColumn);
					}
				}
				break;
			case Constant.FORM_FIELD_TYPE_AJAXBOX_TREE_ORG:
				String oid = "1";
				String selfoid = "";
				HttpServletRequest request = ServletActionContext.getRequest();
				Object obj = request.getSession().getAttribute("oid");
				if (obj != null) {
					selfoid = obj.toString();
				}
				String isselforg = ((EditColumn) editPage.getEditColumn()
						.get(i)).getTextColumn().getIsselforg();
				String orgid = ((EditColumn) editPage.getEditColumn().get(i))
						.getTextColumn().getOrgid();
				if (orgid != null && !"".equals(orgid)) {
					selfoid = orgid;
				}
				if ("true".equals(isselforg)) {
					oid = selfoid;
				}
				String[] obj_array = loadTreeOrgData(editColumn
						.getDictionaryID(), editColumn.getData(), oid);
				((EditColumn) editPage.getEditColumn().get(i))
						.getTreeComponents().setJsonTreeData(obj_array[0]);
				((EditColumn) editPage.getEditColumn().get(i))
						.getTreeComponents().setConversionDataValue(
								obj_array[1]);
				((EditColumn) editPage.getEditColumn().get(i))
						.getTreeComponents().setIsCheckBox(
								(Boolean.valueOf(((EditColumn) editPage
										.getEditColumn().get(i))
										.getTextColumn().getIsmultipart())));
				((EditColumn) editPage.getEditColumn().get(i))
						.getTreeComponents().setOnlyLeafCheck(
								(Boolean.valueOf(((EditColumn) editPage
										.getEditColumn().get(i))
										.getTextColumn().getIsleafcheck())));
				break;
			case Constant.FORM_FIELD_TYPE_AJAXBOX_TREE_ORG_HUMAN:
				String human_oid = "1";
				Object human_obj = ServletActionContext.getRequest()
						.getSession().getAttribute("oid");
				String human_orgid = ((EditColumn) editPage.getEditColumn()
						.get(i)).getTextColumn().getOrgidhuman();
				if (human_orgid != null && !"".equals(human_orgid)) {
					human_oid = human_orgid;
				} else {
					human_oid = human_obj.toString();
				}
				String[] human_obj_array = loadTreeHumanData(editColumn
						.getDictionaryID(), editColumn.getData(), human_oid);
				((EditColumn) editPage.getEditColumn().get(i))
						.getTreeComponents()
						.setJsonTreeData(human_obj_array[0]);
				((EditColumn) editPage.getEditColumn().get(i))
						.getTreeComponents().setConversionDataValue(
								human_obj_array[1]);
				((EditColumn) editPage.getEditColumn().get(i))
						.getTreeComponents().setDictionaryID(
								editColumn.getDictionaryID());
				((EditColumn) editPage.getEditColumn().get(i))
						.getTreeComponents()
						.setIsCheckBox(
								(Boolean.valueOf(((EditColumn) editPage
										.getEditColumn().get(i))
										.getTextColumn().getIsmultiparthuman())));
				break;
			case Constant.FORM_FIELD_TYPE_LISTPAGE:
				
			
				dictionartData = update_Dictionary(editColumn.getDictionaryID());
				editColumn= (EditColumn)editPage.getEditColumn().get(i);
				if (dictionartData!=null&&dictionartData.get(editColumn.getData())!=null) {
					String  textString=dictionartData.get(editColumn.getData()).toString();
				}
				
				
				((EditColumn) editPage.getEditColumn().get(i))
						.setDictionaryData(dictionartData);
				break;
			default:
				break;
			}
		}
		return editPage;
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.formengine.service.ComponentsService#loadViewPage(com.zxt.compplatform.formengine.entity.view.ViewPage, javax.servlet.http.HttpServletRequest)
	 */
	public ViewPage loadViewPage(ViewPage viewPage, HttpServletRequest request) {
		// TODO Auto-generated method stub

		String sql = viewPage.getFindSql();
		boolean flag = true;
		String[] parmer = null;
		Param param = null;

		String key = "";
		String value = "";

		if (viewPage.getViewPageParams() != null) {
			parmer = new String[viewPage.getViewPageParams().size()];
			for (int j = 0; j < viewPage.getViewPageParams().size(); j++) {
				param = (Param) viewPage.getViewPageParams().get(j);
				key = param.getKey().trim();
				value = request.getParameter(key);
				value = StrTools.charsetFormat(value, "ISO8859-1", "UTF-8");
				if ((value == null) || "".equals(value)) {
					flag = false;
					break;
				}
				parmer[j] = value;
			}

		}

		if (flag) {
			viewPage = componentsDao.loadViewPage(sql, parmer, viewPage);
			ViewColumn viewColumn = null;
			String data = "";
			if (viewPage.getViewColumn() != null) {
				for (int i = 0; i < viewPage.getViewColumn().size(); i++) {
					viewColumn = (ViewColumn) (viewPage.getViewColumn().get(i));
					if (!"".equals(viewColumn.getDictionaryID())) {
						// 单选框null值判断
						if (viewColumn.getData() == null) {
							continue;
						}
						String dictionaryID = viewColumn.getDictionaryID();
						Map map = load_Dictionary(dictionaryID);
						data = viewColumn.getData();
						if (map != null) {
							if (StringUtils.contains(data, ",")) {
								String[] datas = data.split(",");
								String temdata = StringUtils.EMPTY;
								for (int j = 0; j < datas.length; j++) {
									if (map.get(datas[j]) != null) {
										String thisdata = map.get(datas[j])
												.toString();
										temdata += thisdata + ",";
									}
								}
								data = temdata.substring(0,
										temdata.length() - 1);
							} else {
								if (map.get(data) != null) {
									data = map.get(data).toString();
								}
							}
							((ViewColumn) (viewPage.getViewColumn().get(i)))
									.setData(data);
						}
					}
				}
			}
		}

		return viewPage;
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.formengine.service.ComponentsService#load_Dictionary(java.lang.String)
	 */
	public Map load_Dictionary(String dictionaryID) {
		// TODO Auto-generated method stub
		String[] array = null;
		String[] mapString = null;
		Map map = new HashMap();
		DataDictionary dictionary = null;
		/**
		 * 静态数据字典 拆分表达式 ；返回map
		 */
		try {
			dictionary = iDataDictionaryService.findById(dictionaryID);
			if (dictionary.getType().equals(Constant.DICTIONARY_STATIC)) {
				array = dictionary.getExpression().split(",");
				for (int i = 0; i < array.length; i++) {
					mapString = array[i].split("=");
					map.put(mapString[0], mapString[1]);
				}
			} else if (dictionary.getType().equals(Constant.DICTIONARY_DYNAMIC)) {
				map = componentsDao.loadDynamicDictionary(dictionary
						.getExpression(), dictionary.getDataSource().getId());
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error("static dictionary or dynamic dictionary is turn.. ");
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * @GUOWEIXIN
	 *  数据字典中 设置动态SQL语句的解析和查询功能。
	 *  例如：[]在request范围中取。{}在session范围中取。
	 *  select * from TableName where first='[first]' and second='{second}'
	 */
	public Map load_Dictionary(String dictionaryID,HttpServletRequest request) {
		// TODO Auto-generated method stub
		String[] array = null;
		String[] mapString = null;
		Map map = new HashMap();
		DataDictionary dictionary = null;
		/**
		 * 静态数据字典 拆分表达式 ；返回map
		 */
		try {
			dictionary = iDataDictionaryService.findById(dictionaryID);
			if (dictionary.getType().equals(Constant.DICTIONARY_STATIC)) {
				array = dictionary.getExpression().split(",");
				for (int i = 0; i < array.length; i++) {
					mapString = array[i].split("=");
					map.put(mapString[0], mapString[1]);
				}
			} else if (dictionary.getType().equals(Constant.DICTIONARY_DYNAMIC)) {
				map = componentsDao.loadDynamicDictionary(dictionary
						.getExpression(), dictionary.getDataSource().getId(),request);
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error("static dictionary or dynamic dictionary is turn.. ");
		}
		return map;
	}

	
	public ComponentsTreeService getComponentsTreeService() {
		return componentsTreeService;
	}

	public void setComponentsTreeService(
			ComponentsTreeService componentsTreeService) {
		this.componentsTreeService = componentsTreeService;
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.formengine.service.ComponentsService#loadTreeData(java.lang.String, java.lang.String)
	 */
	public String[] loadTreeData(String dictionaryID, String defalutValue) {
		// TODO Auto-generated method stub

		String json = "";
		DataDictionary dataDictionary = null;
		String[] array = null;

		dataDictionary = iDataDictionaryService.findById(dictionaryID);
		
		if (Constant.DICTIONARY_DYNAMIC.equals(dataDictionary.getType())) {
			try {
				/**
				 * 返回封装后 初始化的树形结构 json和 业务数据id 对应的 name
				 */
				array = componentsTreeService.treeData(dataDictionary,
						defalutValue);

			} catch (Exception e) {
				// TODO Auto-generated catch block
			}
		}

		return array;
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.formengine.service.ComponentsService#loadTreeData(java.lang.String, java.lang.String, java.lang.String)
	 */
	public String[] loadTreeData(String dictionaryID, String defalutValue,
			String parentId) {

		String json = "";
		DataDictionary dataDictionary = null;
		String[] array = null;

		dataDictionary = iDataDictionaryService.findById(dictionaryID);
		if (Constant.DICTIONARY_DYNAMIC.equals(dataDictionary.getType())) {
			try {
				/**
				 * 返回封装后 初始化的树形结构 json和 业务数据id 对应的 name
				 */
				array = componentsTreeService.treeData(dataDictionary,
						defalutValue, parentId);

			} catch (Exception e) {
				// TODO Auto-generated catch block
			}
		}

		return array;

	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.formengine.service.ComponentsService#loadTreeOrgData(java.lang.String, java.lang.String, java.lang.String)
	 */
	public String[] loadTreeOrgData(String dictionaryID, String defalutValue,
			String oid) {
		// TODO Auto-generated method stub

		String json = "";
		DataDictionary dataDictionary = null;
		String[] array = null;

		dataDictionary = iDataDictionaryService.findById(dictionaryID);
		if (Constant.DICTIONARY_DYNAMIC.equals(dataDictionary.getType())) {
			try {
				/**
				 * 返回封装后 初始化的树形结构 json和 业务数据id 对应的 name
				 */
				array = componentsTreeService.treeOrgData(dataDictionary,
						defalutValue, oid);

			} catch (Exception e) {
				// TODO Auto-generated catch block
			}
		}
		/**
		 * 返回封装后 初始化的树形结构 json和 业务数据id 对应的 name
		 */
		return array;
	}

	/**
	 * 加载treejson
	 */
	public String[] loadTreeHumanData(String dictionaryID, String defalutValue,
			String oid) {
		// TODO Auto-generated method stub

		String json = "";
		DataDictionary dataDictionary = null;
		String[] array = null;

		dataDictionary = iDataDictionaryService.findById(dictionaryID);
		if (Constant.DICTIONARY_DYNAMIC.equals(dataDictionary.getType())) {
			try {
				/**
				 * 返回封装后 初始化的树形结构 json和 业务数据id 对应的 name
				 */
				array = componentsTreeService.treeHumanData(dataDictionary,
						defalutValue, oid);

			} catch (Exception e) {
				// TODO Auto-generated catch block
			}
		}
		/**
		 * 返回封装后 初始化的树形结构 json和 业务数据id 对应的 name
		 */
		return array;
	}

	public String[] load_XMLConfig() {
		// TODO Auto-generated method stub
		return componentsDao.load_XMLConfig();
	}

	public ComponentsDao getComponentsDao() {
		return componentsDao;
	}

	public void setComponentsDao(ComponentsDao componentsDao) {
		this.componentsDao = componentsDao;
	}

	public IDataDictionaryService getIDataDictionaryService() {
		return iDataDictionaryService;
	}

	public void setIDataDictionaryService(
			IDataDictionaryService dataDictionaryService) {
		iDataDictionaryService = dataDictionaryService;
	}

	public String load_validate(String id) {
		// TODO Auto-generated method stub

		return componentsDao.serchValidate(id);
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.formengine.service.ComponentsService#bulkDelete(com.zxt.compplatform.formengine.entity.view.ListPage, javax.servlet.http.HttpServletRequest)
	 */
	public String bulkDelete(ListPage listPage, HttpServletRequest request) {
		// TODO Auto-generated method stub
		Button button = null;
		Event event = null;
		Param param = null;
		String[] parmers = null;

		if (listPage != null) {
			if (listPage.getRowButton() != null) {
				for (int i = 0; i < listPage.getRowButton().size(); i++) {
					button = ((Button) listPage.getRowButton().get(i));
					if (Constant.DELETEOPERATOR.equals(button.getButtonName())) {
						event = (Event) button.getEvent().get(0);
						param = (Param) event.getParas().get(0);
						parmers = param.getValue().split(",");
						break;
					}
				}
			}
		}

		String value = "";
		String[] valueParmers = null;
		String sql = StringUtils.EMPTY;
		if (StringUtils.equals("on", listPage.getIsPseudoDeleted())) {
			sql = " update " + listPage.getKeyTable()
					+ " set IS_PSEUDO_DELETED='1' where 1=1 ";
		} else {
			sql = " DELETE FROM " + listPage.getKeyTable() + " WHERE 1=1 ";
		}

		int length = 0;

		if (parmers != null) {
			if (parmers.length == 1) {// 单主键

				value = StrTools.charsetFormat(request.getParameter(parmers[0]
						.trim()), "ISO8859-1", "UTF-8");
				valueParmers = value.split(",");

				sql = sql + " and " + parmers[0] + " in (";

				for (int j = 0; j < valueParmers.length; j++) {
					if (j == 0) {
						sql = sql + "?";
					} else {
						sql = sql + ",? ";
					}
				}

				sql = sql + ")";
				componentsDao.deleteData(sql, valueParmers, listPage);

			} else if (parmers.length > 1) {// 多主键

				value = StrTools.charsetFormat(request.getParameter(parmers[0]
						.trim()), "ISO8859-1", "UTF-8");
				valueParmers = value.split(",");
				length = valueParmers.length;// 删除数据条数
				sql = sql + " and (";
				for (int i = 0; i < length; i++) {
					if (i == 0) {
						sql = sql + "(";
					} else {
						sql = sql + " or (";
					}
					/**
					 * 每条记录联合主键的拼接
					 * 
					 */
					for (int j = 0; j < parmers.length; j++) {

						value = StrTools.charsetFormat(request
								.getParameter(parmers[j].trim()), "ISO8859-1",
								"UTF-8");
						valueParmers = value.split(",");

						if (j == 0) {
							sql = sql + parmers[j] + "= '" + valueParmers[i]
									+ "'";
						} else {
							sql = sql + " and " + parmers[j] + "= '"
									+ valueParmers[i] + "'";
						}
					}
					/**
					 * 
					 */
					sql = sql + ")";
				}
				sql = sql + " )";
				componentsDao.deleteData(sql, null, listPage);
				log.info(sql);
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.formengine.service.ComponentsService#update_Dictionary(java.lang.String)
	 */
	public Map update_Dictionary(String dictionaryID) {
		// TODO Auto-generated method stub

		String[] array = null;
		String[] mapString = null;
		Map map = new HashMap();
		DataDictionary dictionary = null;
		/**
		 * 静态数据字典 拆分表达式 ；返回map
		 */
		try {
			dictionary = iDataDictionaryService.findById(dictionaryID);
			if (dictionary.getType().equals(Constant.DICTIONARY_STATIC)) {
				array = dictionary.getExpression().split(",");
				for (int i = 0; i < array.length; i++) {
					mapString = array[i].split("=");
					map.put(mapString[0], mapString[1]);
				}
			} else if (dictionary.getType().equals(Constant.DICTIONARY_DYNAMIC)) {
				map = componentsDao.loadDynamicDictionary(dictionary
						.getExpression(), dictionary.getDataSource().getId());
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error("static dictionary or dynamic dictionary is turn.. ");
		}
		return map;
	}

	/**
	 * @GUOWEIXIN
	 *  数据字典中 设置动态SQL语句的解析和查询功能。
	 *  例如：[]在request范围中取。{}在session范围中取。
	 *  select * from TableName where first='[first]' and second='{second}'
	 */
	public Map update_Dictionary(String dictionaryID,HttpServletRequest request) {
		// TODO Auto-generated method stub

		String[] array = null;
		String[] mapString = null;
		Map map = new HashMap();
		DataDictionary dictionary = null;
		/**
		 * 静态数据字典 拆分表达式 ；返回map
		 */
		try {
			dictionary = iDataDictionaryService.findById(dictionaryID);
			if (dictionary.getType().equals(Constant.DICTIONARY_STATIC)) {
				array = dictionary.getExpression().split(",");
				for (int i = 0; i < array.length; i++) {
					mapString = array[i].split("=");
					map.put(mapString[0], mapString[1]);
				}
			} else if (dictionary.getType().equals(Constant.DICTIONARY_DYNAMIC)) {
				map = componentsDao.loadDynamicDictionary(dictionary
						.getExpression(), dictionary.getDataSource().getId(),request);
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage()+"static dictionary or dynamic dictionary is turn.. ");
			//e.printStackTrace();
		}
		return map;
	}
	
	public TijiaoWorkFlowDaoImpl getTijiaoWorkFlowDaoImpl() {
		return tijiaoWorkFlowDaoImpl;
	}

	public void setTijiaoWorkFlowDaoImpl(
			TijiaoWorkFlowDaoImpl tijiaoWorkFlowDaoImpl) {
		this.tijiaoWorkFlowDaoImpl = tijiaoWorkFlowDaoImpl;
	}

	/**
	 * 菜单过滤数据
	 */
	/* (non-Javadoc)
	 * @see com.zxt.compplatform.formengine.service.ComponentsService#findMenuFilter(java.lang.String, java.util.List)
	 */
	public List findMenuFilter(String menuId, List initList) {
		// TODO Auto-generated method stub
		// b7ecb012fea167f799c27756bc3f2f66 工单受理

		String[] paramFilter = new String[2];
		paramFilter[0] = "ENV_DATAMETER";
		if ("b7ecb012fea167f799c27756bc3f2f66".equals(menuId)) {
			paramFilter[1] = "1";// 工单受理
		} else if ("f3ad34d954c81340aca62c3255302581".equals(menuId)) {
			paramFilter[1] = "2";// 拣送
		} else if ("687d333133d5a79993475746367f6e8d".equals(menuId)) {
			paramFilter[1] = "5";// 回访
		}

		List list = new ArrayList();
		Map temMap = null;
		String paramValue = "";
		for (int i = 0; i < initList.size(); i++) {
			temMap = (Map) initList.get(i);
			if (temMap.get(paramFilter[0]) == null) {
				list.add(initList.get(i));
			} else {

				paramValue = temMap.get(paramFilter[0]).toString();

				try {
					WorkFlowDataStauts workFlowDataStauts = WorkFlowDataStautsXmlUtil
							.xmlToWorkFlowDataStauts(paramValue);

					if ("5".equals(paramFilter[1])) {
						if (!"1".equals(workFlowDataStauts
								.getToTransferDefStautsValue())
								&& (!"2".equals(workFlowDataStauts
										.getToTransferDefStautsValue()))) {
							list.add(initList.get(i));
							continue;
						}
					}

					if (paramFilter[1].equals(workFlowDataStauts
							.getToTransferDefStautsValue())) {
						list.add(initList.get(i));
					}
				} catch (Exception e) {
					// TODO: handle exception
					list.add(initList.get(i));
				}

				// if (paramFilter[1].equals(paramValue)) {
				// list.add(initList.get(i));
				// }
			}
		}
		return list;
	}

	/**
	 * 设置导出到的excel的样式
	 * 
	 * @param wb
	 * @return
	 */
	public static HSSFCellStyle contentStyle(HSSFWorkbook wb) {
		HSSFCellStyle style = wb.createCellStyle();
		HSSFFont f = wb.createFont();
		f.setFontHeightInPoints((short) 11);// 字号
		f.setColor(HSSFColor.BLACK.index);
		f.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		style.setFont(f);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setWrapText(true);
		return style;
	}

	/**
	 * 导出数据
	 */
	public InputStream exportForListPage(String formId, ListPage listPage,
			HttpServletRequest request, String[] selectColumns) {
		String sql = listPage.getSql();
		String[] conditions = new String[] {};
		List list = componentsDao.queryForExport(formId, sql, conditions,
				listPage, request);
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFCellStyle headStyle = contentStyle(wb);
		HSSFSheet sheet = wb.createSheet();
		sheet.setDefaultColumnWidth(26);
		wb.setSheetName(0, "数据列表");
		HSSFRow row = sheet.createRow(0);// 行
		row.setHeight((short) 450);
		HSSFCell cell;// 单元格
		// 写表头

		List headKeys = new ArrayList();

		for (int i = 0; i < selectColumns.length; i++) {
			headKeys.add(selectColumns[i].split("&#")[0]);
			cell = row.createCell(i);
			cell
					.setCellValue(selectColumns[i].split("&#").length > 1 ? selectColumns[i]
							.split("&#")[1]
							: "");
			cell.setCellStyle(headStyle);
		}
		HSSFCellStyle contentStyle = contentStyle(wb);
		// 写内容
		for (int k = 0; k < list.size(); k++) {
			Map taskMap = (Map) list.get(k);// 获取到map之后，利用headkey得到此map的值，写入excel
			row = sheet.createRow(k + 1);// 从第二行开始写
			row.setHeight((short) 400);
			for (int m = 0; m < headKeys.size(); m++) {
				cell = row.createCell(m);
				cell.setCellValue(taskMap.get(headKeys.get(m)) + "");
				cell.setCellStyle(contentStyle);
			}
		}
		// 将流读到内存里面，在内存里构造好一个输入输出流，直接传到浏览器端,不会生成临时文件
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			wb.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] content = os.toByteArray();
		InputStream is = null;
		is = new ByteArrayInputStream(content);
		try {
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return is;
	}

	public ComponentsTreeDao getComponentsTreeDao() {
		return componentsTreeDao;
	}

	public void setComponentsTreeDao(ComponentsTreeDao componentsTreeDao) {
		this.componentsTreeDao = componentsTreeDao;
	}
}