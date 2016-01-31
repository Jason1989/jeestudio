package com.zxt.compplatform.formengine.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringEscapeUtils;
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
import com.zxt.compplatform.form.service.IFormService;
import com.zxt.compplatform.formengine.constant.Constant;
import com.zxt.compplatform.formengine.entity.dataset.FieldDefVO;
import com.zxt.compplatform.formengine.entity.view.EditColumn;
import com.zxt.compplatform.formengine.entity.view.EditMode;
import com.zxt.compplatform.formengine.entity.view.EditPage;
import com.zxt.compplatform.formengine.entity.view.Field;
import com.zxt.compplatform.formengine.entity.view.ListPage;
import com.zxt.compplatform.formengine.entity.view.Param;
import com.zxt.compplatform.formengine.entity.view.Tab;
import com.zxt.compplatform.formengine.entity.view.TextColumn;
import com.zxt.compplatform.formengine.service.ComponentsService;
import com.zxt.compplatform.formengine.service.IQueryXmlDataService;
import com.zxt.compplatform.formengine.service.PageService;
import com.zxt.compplatform.formengine.util.StrTools;
import com.zxt.compplatform.formengine.util.TabPageUtil;
import com.zxt.compplatform.organization.dao.OrganizationDao;
import com.zxt.framework.common.util.RandomGUID;

/**
 * 编辑页操作Action
 * 
 * @author 007
 */
public class EditPageAction extends ActionSupport {

	private static final Log log = LogFactory.getLog(EditPageAction.class);
	
	/**
	 * result 动态页面地址
	 */
	String pageUrl;

	/**
	 * 暂时存储区域
	 */
	public static Map map = new HashMap();
	/**
	 * 页面业务层操作接口
	 */
	private PageService pageService;
	/**
	 * 界面组件业务操作接口
	 */
	private ComponentsService componentsService;
	/**
	 * xml查询操作接口
	 */
	private IQueryXmlDataService queryXmlDataService;
	/**
	 * 多标签页操作工具类
	 */
	private TabPageUtil tabPageUtil;
	/**
	 * 代码生成业务操作接口
	 */
	private ICodeGenerateService codeGenerateService;
	/**
	 * 表单业务操作接口
	 */
	private IFormService formService;

	/**
	 * 展示编辑页
	 * 
	 * @return
	 */
	public String executeEditPage() {
		/**
		 * 设置参数
		 */
		HttpServletRequest request = null;
		request = ServletActionContext.getRequest();
		String formID = request.getParameter("formId");
		String method = request.getParameter("method");
		String lprid = request.getParameter("lprid");

		String isAbleWorkFlow = request.getParameter("isAbleWorkFlow");
		String listpageId = request.getParameter("listpageId");
		request.setAttribute("lprid", lprid);
		request.setAttribute("listpageId", listpageId);
		request.setAttribute("isAbleWorkFlow", isAbleWorkFlow);
		
		//第一次固化时。此参数值用于区分是否 在编辑页时VALUE显示的内容。
		String valueDefine=request.getParameter("valueDefine");
		if(valueDefine==null) valueDefine="";
		request.setAttribute("valueDefine",valueDefine);
		
		String[] arr = new String[2];
		if ("copy".equals(method)) {
			arr = formID.split("@");
			formID = arr[0];
		}
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
			} else if (map.get("viewPage") != null) {
				try {
					ServletActionContext.getResponse().sendRedirect(
							Constant.VIEWPAGE_ACTION_PATH + formID + "&APP_ID="
									+ request.getParameter("APP_ID")
									+ "&PARENT_APP_ID="
									+ request.getParameter("PARENT_APP_ID"));
					return null;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else if (map.get("listPage") != null) {
				try {
					String path = "../formengine/listPageAction.action?formId="
							+ formID + "&APP_ID="
							+ request.getParameter("APP_ID")
							+ "&PARENT_APP_ID="
							+ request.getParameter("PARENT_APP_ID");
					ServletActionContext.getResponse().sendRedirect(path);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		}
		if ("copy".equals(method)) {
			map = pageService.load_service(arr[1]);
			EditPage editPage1 = null;
			if (map != null) {
				if (map.get("editPage") != null) {
					editPage1 = (EditPage) map.get("editPage");
					if (editPage1 != null) {
						editPage.setEditPageParams(editPage1
								.getEditPageParams());
						editPage.setFindSql(editPage1.getFindSql());
					}
				}
			}
		}
		/**
		 * 创建参数对象
		 */
		String key = "";
		String value = "";
		boolean flag = true;// 编辑页加载数据的状态值
		String[] parmerNameArray = null;
		Param param = null;
		EditColumn editColumn = null;

		if (editPage != null) {
			/**
			 * 保存多标签编辑窗口的ID
			 */
			editPage.setTabWindowID(request.getParameter("tabWindowID"));
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
			List list = editPage.getEditColumn();
			for (int i = 0; i < list.size(); i++) {
				EditColumn editCol = (EditColumn) list.get(i);
				EditMode editMode = editCol.getEditMode();
				if (editMode != null) {
					String compDate = editMode.getCompDate();
					if (compDate != null && (!"".equals(compDate))) {
						for (int j = 0; j < list.size(); j++) {
							EditColumn editcol = (EditColumn) list.get(j);
							String name = editcol.getName();
							if (compDate.equals(name)) {
								editMode.setCompIndex(j + "");
							}
						}
					}
				}
			}
		}

		/**
		 * 编辑页不为空，&& 不是新增 加载数据
		 */
		String opertorType = request.getParameter("opertorType");

		/**
		 * 工作流加载表单
		 */
		if ("true".equals(request.getParameter("workflowForm"))) {
			if ((request.getParameter("APP_ID") != null)
					&& (!"".equals(request.getParameter("APP_ID")))) {

				// String initWorkflowSql = editPage.getFindSql().substring(0,
				// editPage.getFindSql().indexOf("where"));
				// editPage.setFindSql(initWorkflowSql + " where APP_ID=? ");
				String initWorkflowSql = editPage.getFindSql();
				if (StringUtils.isNotBlank(initWorkflowSql)) {
					initWorkflowSql = initWorkflowSql.substring(0,
							initWorkflowSql.indexOf("where"));
					initWorkflowSql += " where "
							+ editPage.getInsertTableName() + ".APP_ID=?";
					editPage.setFindSql(initWorkflowSql);
				}

				/**
				 * 工作流加载表单
				 */
				try {
					editPage = componentsService.loadEditPage(editPage,
							new String[] { request.getParameter("APP_ID") });
				} catch (Exception e) {
					e.printStackTrace();
				}

				List editColumnList = editPage.getEditColumn();

				for (int i = 0; i < editColumnList.size(); i++) {
					editColumn = (EditColumn) editColumnList.get(i);
					if ("APP_ID".equals(editColumn.getName())) {

						if (("".equals(editColumn.getData()))
								|| (editColumn.getData() == null)) {
							opertorType = "0";
						}

						((EditColumn) (editPage.getEditColumn().get(i)))
								.setData(request.getParameter("APP_ID"));

					}
				}
			}
		}

		try {
			if (editPage != null) {
				if (opertorType != null) {
					if (opertorType.indexOf(Constant.FORM_STATUS_EDIT) != -1) {
						if (flag) {
							editPage = componentsService.loadEditPage(editPage,
									parmerNameArray);
						}
					} else {
						int columnType = -1;
						editColumn = null;
						Map dictionartData = null;
						for (int i = 0; i < editPage.getEditColumn().size(); i++) {// 循环column

							editColumn = (EditColumn) editPage.getEditColumn()
									.get(i);
							/**
							 * 是否设置变量
							 */
							if (Constant.VARIANT_YES.equals(editColumn
									.getTextColumn().getVariantOrnot())) {
								String variantType = editColumn.getTextColumn()
										.getVariantType();
								String variantValue = editColumn
										.getTextColumn().getVariantValue();
								if (Constant.SYSTEM_VARIANT_TYPE
										.equals(variantType)) { // 如果是系统变量
									if (Constant.SYSTEM_VARIANT_DATE
											.equals(variantValue)) {
										((EditColumn) editPage.getEditColumn()
												.get(i))
												.setData(new SimpleDateFormat(
														"yyyy-MM-dd")
														.format(new Date()));
									} else if (Constant.SYSTEM_VARIANT_DATETIME
											.equals(variantValue)) {
										((EditColumn) editPage.getEditColumn()
												.get(i))
												.setData(new SimpleDateFormat(
														"yyyy-MM-dd HH:mm:ss")
														.format(new Date()));
									} else if (Constant.SYSTEM_AUTO_CODE
											.equals(variantValue)) {
										((EditColumn) editPage.getEditColumn()
												.get(i)).setData(RandomGUID
												.geneGuid());
									} else {
										((EditColumn) editPage.getEditColumn()
												.get(i))
												.setData((String) request
														.getSession()
														.getAttribute(
																variantValue));
									}
								} else { // 如果是常量
									((EditColumn) editPage.getEditColumn().get(
											i)).setData(variantValue);
								}
							}
							/**
							 * 设置APP_ID
							 */
							if (editColumn.getName().equals("APP_ID")) {
								if (("".equals(editColumn.getData()))
										|| (editColumn.getData() == null)) {// 当没有APP_ID时
									((EditColumn) editPage.getEditColumn().get(// 随机生成一个
											i)).setData(RandomGUID.geneGuid());
								}

							}

							if ("".equals(editColumn.getType())
									|| (editColumn.getType() == null)) {
								columnType = 0;
							} else {
								columnType = Integer.parseInt(editColumn
										.getType());
							}
							switch (columnType) {
							case Constant.FORM_FIELD_TYPE_SELECT:
								dictionartData = componentsService
										.update_Dictionary(editColumn
												.getDictionaryID(), request);
								((EditColumn) editPage.getEditColumn().get(i))
										.setDictionaryData(dictionartData);
								break;
							case Constant.FORM_FIELD_TYPE_CHECKBOX:
								if ((editColumn.getDictionaryID() != null)
										&& (!"".equals(editColumn
												.getDictionaryID()))) {
									dictionartData = componentsService
											.update_Dictionary(editColumn
													.getDictionaryID(), request);
									((EditColumn) editPage.getEditColumn().get(
											i))
											.setDictionaryData(dictionartData);
								}

								break;
							case Constant.FORM_FIELD_TYPE_AJAXBOX_TREE:
								String[] array = componentsService
										.loadTreeData(editColumn
												.getDictionaryID(), editColumn
												.getData());
								((EditColumn) editPage.getEditColumn().get(i))
										.getTreeComponents().setJsonTreeData(
												array[0]);
								((EditColumn) editPage.getEditColumn().get(i))
										.getTreeComponents()
										.setConversionDataValue(array[1]);
								((EditColumn) editPage.getEditColumn().get(i))
										.setData("0");// 设置根节点
								break;
							case Constant.FORM_FIELD_TYPE_AJAXBOX_TREE_ORG:
								String oid = "1";
								String selfoid = "";
								Object obj = request.getSession().getAttribute(
										"oid");
								if (obj != null) {
									selfoid = obj.toString();
								}
								String isselforg = ((EditColumn) editPage
										.getEditColumn().get(i))
										.getTextColumn().getIsselforg();
								String orgid = ((EditColumn) editPage
										.getEditColumn().get(i))
										.getTextColumn().getOrgid();
								if (orgid != null && !"".equals(orgid)) {
									selfoid = orgid;
								}
								if ("true".equals(isselforg)) {
									oid = selfoid;
								}
								String[] obj_array = componentsService
										.loadTreeOrgData(editColumn
												.getDictionaryID(), editColumn
												.getData(), oid);
								((EditColumn) editPage.getEditColumn().get(i))
										.getTreeComponents().setJsonTreeData(
												obj_array[0]);
								((EditColumn) editPage.getEditColumn().get(i))
										.getTreeComponents()
										.setConversionDataValue(obj_array[1]);
								((EditColumn) editPage.getEditColumn().get(i))
										.getTreeComponents()
										.setIsCheckBox(
												(Boolean
														.valueOf(((EditColumn) editPage
																.getEditColumn()
																.get(i))
																.getTextColumn()
																.getIsmultipart())));
								((EditColumn) editPage.getEditColumn().get(i))
										.getTreeComponents()
										.setOnlyLeafCheck(
												(Boolean
														.valueOf(((EditColumn) editPage
																.getEditColumn()
																.get(i))
																.getTextColumn()
																.getIsleafcheck())));

								break;
							case Constant.FORM_FIELD_TYPE_AJAXBOX_TREE_ORG_HUMAN:
								String human_oid = "1";
								Object human_obj = request.getSession()
										.getAttribute("oid");
								String human_orgid = ((EditColumn) editPage
										.getEditColumn().get(i))
										.getTextColumn().getOrgidhuman();
								if (human_orgid != null
										&& !"".equals(human_orgid)) {
									human_oid = human_orgid;
								} else {
									human_oid = human_obj.toString();
								}
								String[] human_obj_array = componentsService
										.loadTreeHumanData(editColumn
												.getDictionaryID(), editColumn
												.getData(), "1");
								((EditColumn) editPage.getEditColumn().get(i))
										.getTreeComponents().setJsonTreeData(
												human_obj_array[0]);
								((EditColumn) editPage.getEditColumn().get(i))
										.getTreeComponents()
										.setConversionDataValue(
												human_obj_array[1]);
								((EditColumn) editPage.getEditColumn().get(i))
										.getTreeComponents().setDictionaryID(
												editColumn.getDictionaryID());
								((EditColumn) editPage.getEditColumn().get(i))
										.getTreeComponents()
										.setIsCheckBox(
												(Boolean
														.valueOf(((EditColumn) editPage
																.getEditColumn()
																.get(i))
																.getTextColumn()
																.getIsmultiparthuman())));
								break;
							case Constant.FORM_FIELD_TYPE_RADIO:
								dictionartData = componentsService
										.update_Dictionary(editColumn
												.getDictionaryID(), request);
								((EditColumn) editPage.getEditColumn().get(i))
										.setDictionaryData(dictionartData);
								break;
							case Constant.FORM_FIELD_TYPE_CHOOSE_TREE: // 应急选择树

								String chooseoid = "1";

								String[] chooseobj_array = componentsService
										.loadTreeOrgData(editColumn
												.getDictionaryID(), editColumn
												.getData(), chooseoid);

								((EditColumn) editPage.getEditColumn().get(i))
										.getTreeComponents().setJsonTreeData(
												chooseobj_array[0]);

								((EditColumn) editPage.getEditColumn().get(i))
										.getTreeComponents()
										.setConversionDataValue(
												chooseobj_array[1]);

								((EditColumn) editPage.getEditColumn().get(i))
										.getTreeComponents()
										.setIsCheckBox(
												(Boolean
														.valueOf(((EditColumn) editPage
																.getEditColumn()
																.get(i))
																.getTextColumn()
																.getIsmultipart())));

								((EditColumn) editPage.getEditColumn().get(i))
										.getTreeComponents()
										.setOnlyLeafCheck(
												(Boolean
														.valueOf(((EditColumn) editPage
																.getEditColumn()
																.get(i))
																.getTextColumn()
																.getIsleafcheck())));

								break;
							default:
								break;
							}
						}
					}
					/**
					 * 设置parent_app_id
					 */
					if (editPage.getEditColumn() != null) {
						for (int j = 0; j < editPage.getEditColumn().size(); j++) {
							editColumn = (EditColumn) editPage.getEditColumn()
									.get(j);
							// 子标签列表页的编辑页 加载parentAppId
							if ("PARENT_APP_ID".equals(editColumn.getName())) {
								String data = editColumn.getData();
								if (data != null) {
									if ("".equals(data.trim())) {
										((EditColumn) editPage.getEditColumn()
												.get(j)).setData(request
												.getParameter("parentAppId"));
									}
								} else {
									((EditColumn) editPage.getEditColumn().get(
											j)).setData(request
											.getParameter("parentAppId"));
								}
							}
				///**			
							// 判断是否参与级联
							if (editColumn.getEditRulesEngin() != null) {
								if ("1".equals(editColumn.getEditRulesEngin()
										.getIs_jilian())) {
									String jilian_status = getStatus(editPage
											.getEditColumn(), editColumn
											.getEditRulesEngin().getJilian_column());
									editColumn.setJilian_status(jilian_status);
								}
							}
							
					//	**/	
						}
					}

				} else {
					/**
					 * 预览
					 */
					int columnType = -1;
					Map dictionartData = null;
					for (int i = 0; i < editPage.getEditColumn().size(); i++) {
						editColumn = (EditColumn) editPage.getEditColumn().get(
								i);
						// 判断是否参与级联
						if (editColumn.getEditRulesEngin() != null) {
							if ("1".equals(editColumn.getEditRulesEngin()
									.getIs_jilian())) {
								String jilian_status = getStatus(editPage
										.getEditColumn(), editColumn
										.getEditRulesEngin().getJilian_column());
								editColumn.setJilian_status(jilian_status);
							}
						}
						/**
						 * 设置APP_ID
						 */
						if (editColumn.getName().equals("APP_ID")) {
							((EditColumn) editPage.getEditColumn().get(i))
									.setData(RandomGUID.geneGuid());
						}

						if ("".equals(editColumn.getType())
								|| (editColumn.getType() == null)) {
							columnType = 0;
						} else {
							columnType = Integer.parseInt(editColumn.getType());
						}
						switch (columnType) {
						case Constant.FORM_FIELD_TYPE_SELECT:
							dictionartData = componentsService.load_Dictionary(
									editColumn.getDictionaryID(), request);
							((EditColumn) editPage.getEditColumn().get(i))
									.setDictionaryData(dictionartData);
							break;
						case Constant.FORM_FIELD_TYPE_CHECKBOX:
							if ((editColumn.getDictionaryID() != null)
									&& (!""
											.equals(editColumn
													.getDictionaryID()))) {
								dictionartData = componentsService
										.update_Dictionary(editColumn
												.getDictionaryID(), request);
								((EditColumn) editPage.getEditColumn().get(i))
										.setDictionaryData(dictionartData);
							}

							break;
						case Constant.FORM_FIELD_TYPE_AJAXBOX_TREE:
							String[] array = componentsService.loadTreeData(
									editColumn.getDictionaryID(), editColumn
											.getData());
							((EditColumn) editPage.getEditColumn().get(i))
									.getTreeComponents().setJsonTreeData(
											array[0]);
							((EditColumn) editPage.getEditColumn().get(i))
									.getTreeComponents()
									.setConversionDataValue(array[1]);
							((EditColumn) editPage.getEditColumn().get(i))
									.setData("0");// 设置根节点
							break;
						case Constant.FORM_FIELD_TYPE_AJAXBOX_TREE_ORG:
							String oid = "1";
							String selfoid = "";
							Object obj = request.getSession().getAttribute(
									"oid");
							if (obj != null) {
								selfoid = obj.toString();
							}
							String isselforg = ((EditColumn) editPage
									.getEditColumn().get(i)).getTextColumn()
									.getIsselforg();
							String orgid = ((EditColumn) editPage
									.getEditColumn().get(i)).getTextColumn()
									.getOrgid();
							if (orgid != null && !"".equals(orgid)) {
								selfoid = orgid;
							}
							if ("true".equals(isselforg)) {
								oid = selfoid;
							}
							String[] obj_array = componentsService
									.loadTreeOrgData(editColumn
											.getDictionaryID(), editColumn
											.getData(), oid);
							((EditColumn) editPage.getEditColumn().get(i))
									.getTreeComponents().setJsonTreeData(
											obj_array[0]);
							((EditColumn) editPage.getEditColumn().get(i))
									.getTreeComponents()
									.setConversionDataValue(obj_array[1]);
							((EditColumn) editPage.getEditColumn().get(i))
									.getTreeComponents()
									.setIsCheckBox(
											(Boolean
													.valueOf(((EditColumn) editPage
															.getEditColumn()
															.get(i))
															.getTextColumn()
															.getIsmultipart())));
							((EditColumn) editPage.getEditColumn().get(i))
									.getTreeComponents()
									.setOnlyLeafCheck(
											(Boolean
													.valueOf(((EditColumn) editPage
															.getEditColumn()
															.get(i))
															.getTextColumn()
															.getIsleafcheck())));
							break;
						case Constant.FORM_FIELD_TYPE_AJAXBOX_TREE_ORG_HUMAN:
							String human_oid = "1";
							Object human_obj = request.getSession()
									.getAttribute("oid");
							String human_orgid = ((EditColumn) editPage
									.getEditColumn().get(i)).getTextColumn()
									.getOrgidhuman();
							if (human_orgid != null && !"".equals(human_orgid)) {
								human_oid = human_orgid;
							} else {
								human_oid = human_obj.toString();
							}
							String[] human_obj_array = componentsService
									.loadTreeHumanData(editColumn
											.getDictionaryID(), editColumn
											.getData(), human_oid);
							((EditColumn) editPage.getEditColumn().get(i))
									.getTreeComponents().setJsonTreeData(
											human_obj_array[0]);
							((EditColumn) editPage.getEditColumn().get(i))
									.getTreeComponents()
									.setConversionDataValue(human_obj_array[1]);
							((EditColumn) editPage.getEditColumn().get(i))
									.getTreeComponents().setDictionaryID(
											editColumn.getDictionaryID());
							((EditColumn) editPage.getEditColumn().get(i))
									.getTreeComponents()
									.setIsCheckBox(
											(Boolean
													.valueOf(((EditColumn) editPage
															.getEditColumn()
															.get(i))
															.getTextColumn()
															.getIsmultiparthuman())));
							break;
						case Constant.FORM_FIELD_TYPE_RADIO:
							dictionartData = componentsService
									.update_Dictionary(editColumn
											.getDictionaryID(), request);
							((EditColumn) editPage.getEditColumn().get(i))
									.setDictionaryData(dictionartData);
							break;
						case Constant.FORM_FIELD_TYPE_CHOOSE_TREE: // 应急选择树

							String chooseoid = "1";
							String[] chooseobj_array = componentsService
									.loadTreeOrgData(editColumn
											.getDictionaryID(), editColumn
											.getData(), chooseoid);

							((EditColumn) editPage.getEditColumn().get(i))
									.getTreeComponents().setJsonTreeData(
											chooseobj_array[0]);

							((EditColumn) editPage.getEditColumn().get(i))
									.getTreeComponents()
									.setConversionDataValue(chooseobj_array[1]);

							((EditColumn) editPage.getEditColumn().get(i))
									.getTreeComponents().setIsCheckBox(true);

							((EditColumn) editPage.getEditColumn().get(i))
									.getTreeComponents().setOnlyLeafCheck(true);

							break;

						default:
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		request.setAttribute("editPage", editPage);
		request.setAttribute("opertorType", opertorType);

		/**
		 * 编辑页多标签
		 */

		try {
			Tab tab = null;
			if (editPage != null) {
				if (editPage.getIsUseTab().booleanValue()) {
					List tabPageList = editPage.getTabs();
					if (tabPageList != null) {
						/**
						 * 加载多标签，如果是子标签列表页 加载parentAppId
						 */
						String parentAppId = "";
						if (editPage != null) {
							if (editPage.getEditColumn() != null) {
								for (int j = 0; j < editPage.getEditColumn()
										.size(); j++) {
									editColumn = (EditColumn) editPage
											.getEditColumn().get(j);
									if ("APP_ID".equals(editColumn.getName())) {
										parentAppId = editColumn.getData();
										request.setAttribute("APP_ID",
												parentAppId);
									}
								}
							}
						}
						/**
						 * 加载多标签
						 */
						tabPageList = tabPageUtil.initTabList(tabPageList,
								request, parentAppId);
						editPage.setTabs(tabPageList);
						/**
						 * 封装多标签表单 提交的ids
						 */
						List idsList = new ArrayList();
						Map idsMap = null;
						for (int i = 0; i < tabPageList.size(); i++) {
							tab = (Tab) tabPageList.get(i);
							if (tab.getType().equals(Constant.TAB_EDITPAGE)) {
								idsMap = new HashMap();
								idsMap.put("formId", tab.getTabId());
								idsList.add(idsMap);
							}
							if (tab.getUrl().equals(editPage.getId())) {
								((Tab) tabPageList.get(i)).setPage(editPage);
							}
						}
						editPage.setTabs(tabPageList);
						editPage.setIdsJson(JSONUtil.serialize(idsList));
						/**
						 * 设置主子表关系
						 */
						String tabChildTitle = "";
						String tabTitle = "";
						for (int i = 0; i < tabPageList.size(); i++) {
							tabChildTitle = ((Tab) tabPageList.get(i))
									.getChildAppID();// 子节点标题
							if ((tabChildTitle != null)
									&& (!"".equals(tabChildTitle))) {
								for (int j = 0; j < tabPageList.size(); j++) {
									tabTitle = ((Tab) tabPageList.get(j))
											.getTitle();
									if (tabChildTitle.equals(tabTitle)) {
										((Tab) tabPageList.get(j))
												.setParentAppID(((Tab) tabPageList
														.get(i)).getTabId());
									}
								}
							}
						}
						editPage.setTabs(tabPageList);
						/**
						 * 加载标签结束
						 */
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		installBackValue(request);// 设置request域 返回值

		this.opInsideList(editPage);

		List keyVoList = editPage.getKeyList();
		if (CollectionUtils.isNotEmpty(keyVoList)) {
			FieldDefVO keyVo = (FieldDefVO) keyVoList.get(0);
			if (keyVo != null && StringUtils.isNotBlank(keyVo.getToFieldName())) {
				request.setAttribute("editPageKeyId", keyVo.getToFieldName());
			}
		}
		/**
		 * AUTHOR:GUOWEIXIN 1得到角色ID，表名 字段授权 编辑页--> 设计
		 * 2查询出数据库该角色相应的字段权限。再和现有的对比，筛选到相应结果
		 */
		Long rid = null;
		/**
		 * 得到当前角色名称，根据名称得到其角色ID
		 */
		String roleName = (String) request.getSession().getAttribute(
				"stwitchRole");// 在session范围中得到用户ID
		String ridReal = authorityFrameService
				.initAuthorityRidFrameByAccount(roleName);
		if (ridReal != null)
			rid = new Long(ridReal);
		else
			return forWardPage(request, editPage);
		String tableName = editPage.getInsertTableName();
		Map<Long, Map> mapRoot = (Map<Long, Map>) fieldGrantService
				.load_service(rid);
		if (mapRoot != null) {
			Map<String, List> mapTable = mapRoot.get(rid);
			List strFields = mapTable.get(tableName);
			if (strFields != null) {
				// 所有字段列
				List listAll = editPage.getEditColumn();
				List resultAll = new ArrayList();// 结果字段
				if (listAll != null) {
					for (int i = 0; i < listAll.size(); i++) {
						EditColumn field = (EditColumn) listAll.get(i);
						if (field != null) {
							for (int j = 0; j < strFields.size(); j++) {
								String comStrField = (String) strFields.get(j);
								if (comStrField.equals(field.getName())) {
									resultAll.add(field);
								}
							}
						}
					}
					editPage.setEditColumn(resultAll);
				}
			}
		}

		// -----end GUOWEIXIN
		return forWardPage(request, editPage);// 返回路径选择
	}

	/**
	 * 展示编辑页
	 * 
	 * @param editPage
	 */
	private void opInsideList(EditPage editPage) {
		try {
			List editlist = editPage.getEditColumn();
			Map<String, EditColumn> map = new HashMap<String, EditColumn>();
			EditColumn editColumnParmer = null;

			for (int i = 0; i < editPage.getEditColumn().size(); i++) {
				editColumnParmer = (EditColumn) editPage.getEditColumn().get(i);
				map.put(editColumnParmer.getName(), editColumnParmer);
			}

			if (CollectionUtils.isEmpty(editlist)) {
				return;
			}
			Iterator iterator = editlist.iterator();
			for (int i = 0; i < editlist.size(); i++) {
				EditColumn editColumn = (EditColumn) editlist.get(i);
				if (editColumn == null) {
					continue;
				}
				String type = editColumn.getType();
				if (!StringUtils.equals("20", type)) {
					continue;
				}
				TextColumn textColumn = editColumn.getTextColumn();
				if (textColumn == null) {
					continue;
				}
				StringBuilder params = new StringBuilder();
				String insideFormId = textColumn.getListInside();
				String insideParmer = textColumn.getListInsideParmer();
				String urlParmer = "";

				String[] parmeArray = null;
				String[] keyValueArray = null;
				if (insideParmer.indexOf("[") >= 0) {
					insideParmer = insideParmer.substring(1, insideParmer
							.indexOf("]"));
					parmeArray = insideParmer.split(",");
					for (int j = 0; j < parmeArray.length; j++) {
						keyValueArray = parmeArray[j].split("=");
						urlParmer = "&" + keyValueArray[0] + "="
								+ map.get(keyValueArray[1]).getData();
					}
				}

				if (StringUtils.isBlank(insideFormId)) {
					continue;
				} else {
					params.append("formengine/listPageAction.action?formId=")
							.append(insideFormId);
					params.append(urlParmer);
				}

				if (StringUtils.isNotBlank(insideParmer)) {
					String[] parmers = insideParmer.split(",");
					if (ArrayUtils.isEmpty(parmers)) {
						continue;
					}
					for (int j = 0; j < parmers.length; j++) {
						if (StringUtils.isBlank(parmers[j])) {
							continue;
						}
						while (iterator.hasNext()) {
							EditColumn editColumnK = (EditColumn) iterator
									.next();
							if (editColumnK == null) {
								continue;
							}
							TextColumn textColumnK = editColumnK
									.getTextColumn();
							if (textColumnK == null) {
								continue;
							}
							String str1 = textColumnK.getName();
							String str2 = parmers[j];
							if (StringUtils.equals(str1, str2)) {
								String value = StringEscapeUtils
										.escapeHtml(editColumnK.getData());
								params.append("&").append(parmers[j]).append(
										"=").append(value);
							}
						}
					}
				}
				editColumn.setUrl(params.toString());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	/**
	 * 跳转页面
	 * 
	 * @param request
	 * @param editPage
	 * @return
	 */
	public String forWardPage(HttpServletRequest request, EditPage editPage) {
		String isPreview = request.getParameter("preview");
		String method = request.getParameter("method");
		String atw = request.getParameter("atw");// 工作流请求编辑页 atw=1 不显示标签页的按钮。
		request.setAttribute("atw", atw);
		String disabletab = request.getParameter("disabletab") + "";
		String forwardType = request.getParameter("forwardType");//固化功能 动态result
		String pageUrl = request.getParameter("pageUrl");
		this.setPageUrl(pageUrl);//result 动态页面地址
		
		if ("curing".equals(forwardType)){
			return "curingPage"; //struts2 动态result 地址
		}
		if ("0".equals(isPreview)) {
			return "preview-editPage";
		}
		if (editPage != null && editPage.getIsUseTab().booleanValue()
				&& (!"true".equals(disabletab))) {
			request.setAttribute("atw", "1");// 多标签页不显示 每个标签页的 保存按钮
			return "loadTabEditPage";
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
		if ("copy".equals(method)) {
			return "load-copyPage";
		}

		return "load-editPage";
	}

	/**
	 * 获取状态
	 * 
	 * @param list
	 * @param jilian_column
	 * @return
	 */
	private String getStatus(List list, String jilian_column) {
		// TODO Auto-generated method stub
		for (int i = 0; i < list.size(); i++) {
			EditColumn editColumn = (EditColumn) list.get(i);
			if (jilian_column.equals(editColumn.getName())) {
				return i + "";
			}
		}
		return "-1";
	}

	public static Map getMap() {
		return map;
	}

	public static void setMap(Map map) {
		EditPageAction.map = map;
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

	public IFormService getFormService() {
		return formService;
	}

	public void setFormService(IFormService formService) {
		this.formService = formService;
	}

	/**
	 * 插入返回数据
	 * 
	 * @param request
	 */
	public void installBackValue(HttpServletRequest request) {
		request.setAttribute("listPageRander", request
				.getParameter("listPageRander"));
		String formID = request.getParameter("listFormID");
		ListPage listPage = null;
		if (map != null) {
			if (map.get("listPage") != null) {
				listPage = (ListPage) map.get("listPage");
				if (listPage != null) {
					listPage.setId(formID);
				}
			}
		} else {
			listPage = new ListPage();
			listPage.setId(formID);
		}

		request.setAttribute("listPage", listPage);
	}

	/**
	 * 字段授权 操作业务逻辑实体 SET注入 GUOWEIXIN
	 */
	private FieldGrantService fieldGrantService;

	public void setFieldGrantService(FieldGrantService fieldGrantService) {
		this.fieldGrantService = fieldGrantService;
	}

	/**
	 * 权限框架业务操作接口 根据角色名称 得到其角色ID SET注入：GUOWEIXIN
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
