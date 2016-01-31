package com.zxt.compplatform.formengine.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;

import com.zxt.compplatform.codegenerate.util.CodeGenerateException;
import com.zxt.compplatform.datacolumn.service.IDataColumnService;
import com.zxt.compplatform.datasource.entity.DataSource;
import com.zxt.compplatform.datasource.service.IDataSourceService;
import com.zxt.compplatform.form.entity.Form;
import com.zxt.compplatform.form.service.IFormService;
import com.zxt.compplatform.formengine.entity.dataset.FieldDefVO;
import com.zxt.compplatform.formengine.entity.dataset.TableVO;
import com.zxt.compplatform.formengine.entity.view.BasePage;
import com.zxt.compplatform.formengine.entity.view.Button;
import com.zxt.compplatform.formengine.entity.view.ColumnData;
import com.zxt.compplatform.formengine.entity.view.ColumnRoles;
import com.zxt.compplatform.formengine.entity.view.CopyPage;
import com.zxt.compplatform.formengine.entity.view.EditColumn;
import com.zxt.compplatform.formengine.entity.view.EditMode;
import com.zxt.compplatform.formengine.entity.view.EditPage;
import com.zxt.compplatform.formengine.entity.view.EditRulesEngin;
import com.zxt.compplatform.formengine.entity.view.Event;
import com.zxt.compplatform.formengine.entity.view.Field;
import com.zxt.compplatform.formengine.entity.view.Group;
import com.zxt.compplatform.formengine.entity.view.ListColumn;
import com.zxt.compplatform.formengine.entity.view.ListPage;
import com.zxt.compplatform.formengine.entity.view.OperateButton;
import com.zxt.compplatform.formengine.entity.view.Pagination;
import com.zxt.compplatform.formengine.entity.view.Param;
import com.zxt.compplatform.formengine.entity.view.QueryColumn;
import com.zxt.compplatform.formengine.entity.view.QueryZone;
import com.zxt.compplatform.formengine.entity.view.Tab;
import com.zxt.compplatform.formengine.entity.view.TextColumn;
import com.zxt.compplatform.formengine.entity.view.TitleColumn;
import com.zxt.compplatform.formengine.entity.view.ViewColumn;
import com.zxt.compplatform.formengine.entity.view.ViewPage;
import com.zxt.framework.common.util.SQLBlobUtil;
import com.zxt.framework.common.util.XMLParse;

/**
 * 表单对象解析
 * 
 * @author 007
 */
public class ResolveObjectDefService implements IResolveObjectDefService {
	private static final Log log = LogFactory
			.getLog(ResolveObjectDefService.class);

	public ResolveObjectDefService() {
	}

	/**
	 * xml解析接口
	 */
	private IResolveXmlService resolveXmlService;
	/**
	 * 表单操作接口
	 */
	private IFormService formService;
	/**
	 * 数据源操作接口
	 */
	private IDataSourceService dataSourceService;
	/**
	 * 数据列操作接口
	 */
	private IDataColumnService dataColumnService;

	public void setDataSourceService(IDataSourceService dataSourceService) {
		this.dataSourceService = dataSourceService;
	}

	public void setFormService(IFormService formService) {
		this.formService = formService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.formengine.service.IResolveObjectDefService#resolveObject(org.jdom.Document)
	 */
	public BasePage resolveObject(Document doc) throws Exception {
		TableVO tablevo = new TableVO();
		List tableLists = new ArrayList();
		List queryZoneList = new ArrayList();
		List titleColumnvo = new ArrayList();
		List listColumnvo = new ArrayList();
		List editColumnvo = new ArrayList();
		List dataSource = new ArrayList();
		List tab = new ArrayList();
		ListColumn listColumn = new ListColumn();
		EditColumn editColumn = new EditColumn();
		ViewColumn viewColumn = new ViewColumn();
		QueryColumn querycolumnvo = new QueryColumn();
		List queryColumns = new ArrayList();
		QueryZone qyeryZone = new QueryZone();
		List gridButton = new ArrayList();
		List gridButton2 = new ArrayList();
		List rowButton = new ArrayList();
		List rowButton2 = new ArrayList();
		List viewColumnvo = new ArrayList();
		boolean canBatch = false;
		ListPage listPage = new ListPage();
		if (doc != null) {
			Element form = doc.getRootElement();
			// System.out.println(doc);
			String operateType = form.getAttributeValue("type");
			String title = form.getAttributeValue("title");
			//GUOWEIXIN 验证当前页面是否可操作多行
			String isshowsingleSelect=form.getAttributeValue("isshowsingleSelect");
			
			//是否对当前表单操作加入记录 isSystemActionlog
			String isSystemActionlog=form.getAttributeValue("isSystemActionlog");
			
			
			String selfDefine = form.getAttributeValue("selfDefine");
			String selfDefineHeight = form
					.getAttributeValue("selfDefineHeight");
			String selfDefineWidth = form.getAttributeValue("selfDefineWidth");
			String cssClass = form.getAttributeValue("cssClass");
			String css = form.getAttributeValue("css");
			String description = form.getAttributeValue("description");
			//GUOWEIXIN添加 编辑页的两个属性： editPage_before_sql和 editPage_after_sql
			String before_sql=form.getAttributeValue("editPage_before_sql");
			String after_sql=form.getAttributeValue("editPage_after_sql");
			if (("listPage").equals(operateType)) {
				// resolve listPage begin;
				Element dataMapping = form.getChild("DataMapping");
				List dataSetList = dataMapping.getChildren("DataSet");

				if (dataMapping.getParentElement() != null) {
					listPage.setIsPseudoDeleted(dataMapping.getParentElement()
							.getAttributeValue("isPseudoDeleted"));
					listPage.setIsOriginalSql(dataMapping.getParentElement()
							.getAttributeValue("isOriginalSql"));
					listPage.setIsOriginalSqlContext(dataMapping
							.getParentElement().getAttributeValue(
									"isOriginalSql_context"));
					listPage.setIsOriginalSqlParam(dataMapping
							.getParentElement().getAttributeValue(
									"isOriginalSql_param"));
				}

				for (int i = 0; i < dataSetList.size(); i++) {
					Element dataSet = (Element) dataSetList.get(i);
					List dataSourceList = dataSet.getChildren("DataSources");

					for (int m = 0; m < dataSourceList.size(); m++) {
						Element dataSourcesElement = (Element) dataSourceList
								.get(m);
						Element dataSourceElement = dataSourcesElement
								.getChild("DataSource");
						dataSource = resolveXmlService.resolveDataSource(
								dataSourceElement, new DataSource());
					}
					// dataSource.add(listPage);
					List tableList = dataSet.getChildren("Table");
					for (int j = 0; j < tableList.size(); j++) {
						Element table = (Element) tableList.get(j);
						tablevo = resolveXmlService.resolveTable(table,
								tablevo, dataSource, doc);
					}
					tableLists.add(tablevo);
					listPage.setTable(tableLists);
				}
				Element paginationElement = form.getChild("Pagination");
				Pagination paginationvo = new ResolveXmlService()
						.resolvePagination(paginationElement, new Pagination());
				listPage.setCanShowPagination(paginationvo.isDisplay());
				Element operateColumElement = form.getChild("EditColumnsSet");
				if (operateColumElement != null) {
					String setValue = operateColumElement
							.getAttributeValue("setValue");
					listPage.setOpeColumnAlign(setValue);
				}

				Element pages = form.getChild("Pages");

				if (pages != null) {
					Element editPageElement = pages.getChild("EditPage");
					EditPage editPage = new EditPage();
					if (editPageElement != null) {
						String editFormId = editPageElement
								.getAttributeValue("formId");
						editPage.setId(editFormId);
						listPage.setEditPage(editPage);
					}

					/**
					 * * 添加页 读取XML的
					 * 
					 * @GUOWEIXIN
					 * 
					 */
					Element addPageElement = pages.getChild("AddPage");
					if (addPageElement != null) {
						String addFormId = addPageElement
								.getAttributeValue("formId");
						listPage.setAddPageAttribute(addFormId);
					}

					Element viewPageElement = pages.getChild("ViewPage");
					ViewPage viewPage = new ViewPage();
					if (viewPageElement != null) {
						String viewFormId = viewPageElement
								.getAttributeValue("formId");
						viewPage.setId(viewFormId);
						listPage.setViewPage(viewPage);
					}
					Element copyPageElement = pages.getChild("CopyPage");
					CopyPage copyPage = new CopyPage();
					if (copyPageElement != null) {
						String copyFormId = copyPageElement
								.getAttributeValue("formId");
						copyPage.getEditPage().setId(copyFormId);
						listPage.setCopyPage(copyPage);
					}
				}
				Element buttons = form.getChild("Buttons");
				if (buttons != null) {
					List buttonsElementList = buttons.getChildren("Button");
					List buttonvo = new ArrayList();
					for (int m = 0; m < buttonsElementList.size(); m++) {
						Element button = (Element) buttonsElementList.get(m);

						Button buttonVo = resolveXmlService.resolveButton(
								button, new Button());
						if (!buttonVo.getVisible().equals("false")) {
							buttonvo.add(buttonVo);
						}
					}
					gridButton = new ArrayList();
					rowButton = new ArrayList();
					if (buttonvo != null && buttonvo.size() > 0) {
						for (int i = 0; i < buttonvo.size(); i++) {
							Button bon = (Button) buttonvo.get(i);
							int type = Integer.parseInt(bon.getType().trim());
							if (type < Button.BUTTON_TYPE_EDGE) {
								gridButton.add(bon);
							} else {
								rowButton.add(bon);
							}
						}
					}
					// 自定义toolbar按钮
					Element toolbarButtons = buttons.getChild("ToolbarButtons");
					if (toolbarButtons != null) {
						List toolbarButtonList = toolbarButtons
								.getChildren("ToolbarButton");
						if (toolbarButtonList.size() > 0) {
							for (int x = 0; x < toolbarButtonList.size(); x++) {
								Element toolbarButton = (Element) toolbarButtonList
										.get(x);
								OperateButton ob = new OperateButton();
								ob.setButtonname(toolbarButton
										.getAttributeValue("buttonname"));
								ob.setButtonicon(toolbarButton
										.getAttributeValue("buttonicon"));
								ob.setButtonrules(toolbarButton
										.getAttributeValue("buttonrules"));
								ob.setIsshow(toolbarButton
										.getAttributeValue("isshow"));
								gridButton2.add(ob);
							}
						}
					}
					if (gridButton != null) {
						canBatch = true;
					}

					// rowButtons2
					Element operateButtons = buttons.getChild("OperateButtons");
					if (operateButtons != null) {
						List operateButtonList = operateButtons
								.getChildren("OperateButton");
						if (operateButtonList.size() > 0) {
							for (int x = 0; x < operateButtonList.size(); x++) {
								Element operateButton = (Element) operateButtonList
										.get(x);
								OperateButton ob = new OperateButton();
								ob.setButtonname(operateButton
										.getAttributeValue("buttonname"));
								ob.setButtonicon(operateButton
										.getAttributeValue("buttonicon"));
								ob.setButtonrules(operateButton
										.getAttributeValue("buttonrules"));
								ob.setIsshow(operateButton
										.getAttributeValue("isshow"));
								rowButton2.add(ob);
							}
						}
					}

					listPage.setCanBatch(canBatch);
					listPage.setGridButton(gridButton);
					listPage.setGridButton2(gridButton2);
					listPage.setRowButton(rowButton);
					listPage.setRowButton2(rowButton2);
				}
				Element tabs = form.getChild("Tabs");
				if (tabs != null) {
					String isuse = tabs.getAttributeValue("isuse");
					if (isuse != null) {
						listPage.setIsUseTab(Boolean.valueOf(isuse));
					}
					List pagesList = tabs.getChildren("Page");
					for (int i = 0; i < pagesList.size(); i++) {
						Element page = (Element) pagesList.get(i);
						Tab tabvo = resolveXmlService.resloveTab(page,
								new Tab());
						// if(tabvo.getVisible().equals(new Boolean(true))){
						tab.add(tabvo);
						// }

					}
					listPage.setTabs(tab);
				}

				Element queryZone = form.getChild("QueryZone");
				if (queryZone != null) {
					String visible = queryZone.getAttributeValue("visible");
					String showType = queryZone.getAttributeValue("showType");
					if (visible.equals("true")) {
						List queryColumnsList = queryZone
								.getChildren("QueryColumn");
						for (int n = 0; n < queryColumnsList.size(); n++) {
							Element queryColumn = (Element) queryColumnsList
									.get(n);
							querycolumnvo = resolveXmlService
									.resolveQueryColumn(queryColumn,
											new QueryColumn());
							List editModesElementList = queryColumn
									.getChildren("EditMode");

							for (int t = 0; t < editModesElementList.size(); t++) {
								Element editMode = (Element) editModesElementList
										.get(t);
								EditMode editModevo1 = resolveXmlService
										.resolveEditMode(editMode,
												new EditMode());
								querycolumnvo.setEditMode(editModevo1);
							}
							if (new Boolean(true).equals(querycolumnvo
									.isVisible())) {
								queryColumns.add(querycolumnvo);
							}
						}
						qyeryZone.setShowQuery(showType);
						qyeryZone.setQueryColumns(queryColumns);
						queryZoneList.add(qyeryZone);
						listPage.setQueryZone(queryZoneList);
					}
				}
				Element columns = form.getChild("Columns");
				if (columns != null) {
					List columnsList = columns.getChildren("Column");
					List fileds = new ArrayList();
					for (int p = 0; p < columnsList.size(); p++) {
						Field field = new Field();
						Element column = (Element) columnsList.get(p);
						Element columnText = column.getChild("Text");
						TitleColumn titleColumn = resolveXmlService
								.resolveColumnText(columnText,
										new TitleColumn());
						titleColumnvo.add(titleColumn);

						Element dataColumn = column.getChild("Data");

						listColumn = resolveXmlService.resolveDataColumn(
								dataColumn, new ListColumn());
						listColumnvo.add(listColumn);
						if (listColumn != null) {
							field.setDictionaryId(listColumn.getDictionaryID());
						}

						field.setVisible(titleColumn.getVisible());
						field.setAlign(titleColumn.getAlign());
						field.setDataColumn(listColumn.getName());
						field.setName(titleColumn.getName());
						field.setWidth(titleColumn.getWidth());
						field.setColumnrules(titleColumn.getColumnrules());
						field.setIsParmerKey(listColumn.getPrimaryKey() + "");

						fileds.add(field);

					}
					listPage.setTitleColumn(titleColumnvo);
					listPage.setFields(fileds);
					listPage.setListColumn(listColumnvo);
				}
				listPage.setFlag("listPage");
				listPage.setTitle(title);
				listPage.setIsshowsingleSelect(isshowsingleSelect);//GUOWEIXIN 是否对列表选择数据单选
				listPage.setIsSystemActionlog(isSystemActionlog);//GUOWEIXIN   是否对当前表单操作加入记录
				
				return listPage;

			}// resolve listPage end;
			else if (("editPage").equals(operateType)) {
				EditPage editPage = new EditPage();
				editPage.setTitle(title);
				editPage.setIsSystemActionlog(isSystemActionlog);//GUOWEIXIN   是否对当前表单操作加入记录				
				editPage.setSelfDefine(selfDefine);
				if ("on".equals(selfDefine)) {
					editPage.setSelfDefineWidth(selfDefineWidth);
					editPage.setSelfDefineHeight(selfDefineHeight);
				} else {
					editPage.setSelfDefineWidth("");
					editPage.setSelfDefineHeight("");
				}
				//GUOWEIXIN 添加 保存前SQL和保存后SQL
				editPage.setAfter_sql(after_sql);
				editPage.setBefore_sql(before_sql);
				
				// resolve editPage begin;
				Element dataMapping = form.getChild("DataMapping");
				List dataSetList = dataMapping.getChildren("DataSet");
				if (dataMapping.getParentElement() != null) {
					editPage.setWorkflowParCom(dataMapping.getParentElement()
							.getAttributeValue("workflowParCom"));
					editPage.setWorkflowParComId(dataMapping.getParentElement()
							.getAttributeValue("workflowParComId"));
					editPage.setWorkflowParComArray(dataMapping
							.getParentElement().getAttributeValue(
									"workflowParComArray"));
				}

				for (int i = 0; i < dataSetList.size(); i++) {
					Element dataSet = (Element) dataSetList.get(i);
					List dataSourceList = dataSet.getChildren("DataSources");

					for (int m = 0; m < dataSourceList.size(); m++) {
						Element dataSourcesElement = (Element) dataSourceList
								.get(m);
						Element dataSourceElement = dataSourcesElement
								.getChild("DataSource");
						dataSource = resolveXmlService.resolveDataSource(
								dataSourceElement, new DataSource());
					}
					// dataSource.add(listPage);
					List tableList = dataSet.getChildren("Table");
					for (int j = 0; j < tableList.size(); j++) {
						Element table = (Element) tableList.get(j);
						tablevo = resolveXmlService.resolveTable(table,
								new TableVO(), dataSource, doc);
					}
					tableLists.add(tablevo);
					List list = tablevo.getKeyList();
					editPage.setTable(tableLists);
				}
				Element tabs = form.getChild("Tabs");
				if (tabs != null) {
					String isuse = tabs.getAttributeValue("isuse");
					if (isuse != null) {
						editPage.setIsUseTab(Boolean.valueOf(isuse));
					}
					List pagesList = tabs.getChildren("Page");
					for (int i = 0; i < pagesList.size(); i++) {
						Element page = (Element) pagesList.get(i);
						Tab tabvo = resolveXmlService.resloveTab(page,
								new Tab());
						tab.add(tabvo);
					}
					editPage.setTabs(tab);
				}
				Element buttons = form.getChild("Buttons");
				if (buttons != null) {
					List buttonsElementList = buttons.getChildren("Button");
					List buttonvo = new ArrayList();
					for (int m = 0; m < buttonsElementList.size(); m++) {
						Element button = (Element) buttonsElementList.get(m);
						buttonvo.add(new ResolveXmlService().resolveButton(
								button, new Button()));
					}
					editPage.setButton(buttonvo);
				}
				Element groups = form.getChild("Groups");
				if (groups != null) {
					String isGroupVisible = groups.getAttributeValue("visible");
					editPage.setIsGroupVisible(Boolean.valueOf(isGroupVisible));
					String jsRules = groups.getAttributeValue("jsRules");
					if (jsRules != null) {
						editPage.setJsRules(jsRules);
					}
					List groupList = groups.getChildren("Group");
					List groupvo = new ArrayList();
					for (int i = 0; i < groupList.size(); i++) {
						Element groupElement = (Element) groupList.get(i);
						Group group = resolveXmlService.resloveGroup(
								groupElement, new Group());
						groupvo.add(group);
					}
					editPage.setGroups(groupvo);
				}
				Element params = form.getChild("Params");
				if (params != null) {
					List paramList = params.getChildren("Param");
					List paramvo = new ArrayList();
					for (int j = 0; j < paramList.size(); j++) {
						Element paramElement = (Element) paramList.get(j);
						Param param = resolveXmlService.resloveParam(
								paramElement, new Param());
						paramvo.add(param);
					}
					editPage.setParams(paramvo);
				}
				Element columns = form.getChild("Columns");
				if (columns != null) {
					List columnsList = columns.getChildren("Column");
					List fileds = new ArrayList();
					TextColumn textColumn = null;
					for (int p = 0; p < columnsList.size(); p++) {
						Field field = new Field();
						Element column = (Element) columnsList.get(p);
						Element columnText = column.getChild("Text");
						textColumn = resolveXmlService.resolveEditColumnText(
								columnText, new TextColumn());

						// titleColumnvo.add(titleColumn);
						Element dataColumn = column.getChild("Data");
						editColumn = resolveXmlService.resolveEditDataColumn(
								dataColumn, new EditColumn());

						if (StringUtils.isNotBlank(dataColumn
								.getAttributeValue("tablename"))) {
							String tablename = dataColumn
									.getAttributeValue("tablename");
							editColumn.setTablename(tablename);
						}

						List editModesElementList = column
								.getChildren("EditMode");

						for (int t = 0; t < editModesElementList.size(); t++) {
							Element editMode = (Element) editModesElementList
									.get(t);
							EditMode editModevo1 = resolveXmlService
									.resolveEditMode(editMode, new EditMode());
							editColumn.setEditMode(editModevo1);
						}
						if (textColumn.getHidden().equals(new Boolean("false"))) {
							editColumn.setType("11");

						}
						editColumn.setTextColumn(textColumn);

						// 触发事件规则
						Element RulesEngin = (Element) column
								.getChild("RulesEngin");
						EditRulesEngin editRulesEngin = null;
						editRulesEngin = resolveXmlService
								.resolveEditRulesEngin(RulesEngin,
										new EditRulesEngin());
						editColumn.setEditRulesEngin(editRulesEngin);

						Element rolesEle = (Element) column.getChild("Roles");
						if (rolesEle != null) {
							ColumnRoles roles = resolveXmlService
									.resolveColumnRoles(rolesEle,
											new ColumnRoles());
							editColumn.setRoles(roles);
						} else {
							editColumn.setRoles(new ColumnRoles());
						}
						editColumnvo.add(editColumn);
					}
					editPage.setEditColumn(editColumnvo);
				}
				listPage.setFlag("editPage");
				listPage.setEditPage(editPage);
				return listPage;
			}// resolve editPage end
			else if (("viewPage").equals(operateType)) {
				// 解析查看页开始
				ViewPage viewPage = new ViewPage();
				viewPage.setTitle(title);
				Element dataMapping = form.getChild("DataMapping");
				List dataSetList = dataMapping.getChildren("DataSet");
				for (int i = 0; i < dataSetList.size(); i++) {
					Element dataSet = (Element) dataSetList.get(i);
					List dataSourceList = dataSet.getChildren("DataSources");

					for (int m = 0; m < dataSourceList.size(); m++) {
						Element dataSourcesElement = (Element) dataSourceList
								.get(m);
						Element dataSourceElement = dataSourcesElement
								.getChild("DataSource");
						dataSource = resolveXmlService.resolveDataSource(
								dataSourceElement, new DataSource());
					}
					// dataSource.add(listPage);
					List tableList = dataSet.getChildren("Table");
					for (int j = 0; j < tableList.size(); j++) {
						Element table = (Element) tableList.get(j);
						tablevo = resolveXmlService.resolveTable(table,
								new TableVO(), dataSource, doc);
					}
					tableLists.add(tablevo);
					viewPage.setTable(tableLists);
				}
				Element tabs = form.getChild("Tabs");
				if (tabs != null) {
					String isuse = tabs.getAttributeValue("isuse");
					if (isuse != null) {
						viewPage.setIsUseTab(Boolean.valueOf(isuse));
					}
					List pagesList = tabs.getChildren("Page");
					for (int i = 0; i < pagesList.size(); i++) {
						Element page = (Element) pagesList.get(i);
						Tab tabvo = resolveXmlService.resloveTab(page,
								new Tab());
						tab.add(tabvo);
					}
					viewPage.setTabs(tab);
				}
				Element groups = form.getChild("Groups");
				if (groups != null) {
					String isGroupVisible = groups.getAttributeValue("visible");
					viewPage.setIsGroupVisible(Boolean.valueOf(isGroupVisible));
					String jsRules = groups.getAttributeValue("jsRules");
					if (jsRules != null) {
						viewPage.setJsRules(jsRules);
					}
					List groupList = groups.getChildren("Group");
					List groupvo = new ArrayList();
					for (int i = 0; i < groupList.size(); i++) {
						Element groupElement = (Element) groupList.get(i);
						Group group = resolveXmlService.resloveGroup(
								groupElement, new Group());
						groupvo.add(group);
					}
					viewPage.setGroups(groupvo);
				}
				Element buttons = form.getChild("Buttons");
				if (buttons != null) {
					List buttonsElementList = buttons.getChildren("Button");
					List buttonvo = new ArrayList();
					for (int m = 0; m < buttonsElementList.size(); m++) {
						Element button = (Element) buttonsElementList.get(m);
						buttonvo.add(new ResolveXmlService().resolveButton(
								button, new Button()));
					}
					viewPage.setButton(buttonvo);
				}
				Element params = form.getChild("Params");
				if (params != null) {
					List paramList = params.getChildren("Param");
					List paramvo = new ArrayList();
					for (int j = 0; j < paramList.size(); j++) {
						Element paramElement = (Element) paramList.get(j);
						Param param = resolveXmlService.resloveParam(
								paramElement, new Param());
						paramvo.add(param);
					}
					viewPage.setParams(paramvo);
				}
				Element columns = form.getChild("Columns");
				if (columns != null) {
					List columnsList = columns.getChildren("Column");
					List fileds = new ArrayList();
					TextColumn textColumn = null;
					for (int p = 0; p < columnsList.size(); p++) {
						Field field = new Field();
						Element column = (Element) columnsList.get(p);
						Element columnText = column.getChild("Text");
						textColumn = resolveXmlService.resolveViewColumnText(
								columnText, new TextColumn());

						// titleColumnvo.add(titleColumn);
						Element viewDataColumn = column.getChild("Data");
						viewColumn = resolveXmlService.resolveViewDataColumn(
								viewDataColumn, new ViewColumn());
						viewColumn.setTextColumn(textColumn);

						Element rolesEle = column.getChild("Roles");
						if (rolesEle != null) {
							ColumnRoles roleColumn = resolveXmlService
									.resolveColumnRoles(rolesEle,
											new ColumnRoles());
							viewColumn.setRoles(roleColumn);
						}

						viewColumnvo.add(viewColumn);
					}
					viewPage.setViewColumn(viewColumnvo);
				}
				listPage.setFlag("viewPage");
				listPage.setViewPage(viewPage);
				return listPage;
			}
		} else {
			throw new CodeGenerateException("doc为空或不存在！");
		}
		return null;
	}

	public void setResolveXmlService(IResolveXmlService resolveXmlService) {
		this.resolveXmlService = resolveXmlService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.formengine.service.IResolveObjectDefService#resolveObject(java.lang.String)
	 */
	public BasePage resolveObject(String formId) throws CodeGenerateException {
		Form form = formService.findById(formId);
		if (null == form)
			throw new CodeGenerateException("没有对应的表单");
		ListPage listPage = null;
		Document doc = null;
		try {
			String xml = SQLBlobUtil.blobToString(form.getSettings());
			doc = XMLParse.jdomXmlToDoc(xml);
		} catch (Exception e) {
			throw new CodeGenerateException("表单xml格式错误");
		}
		String keyTable = form.getDataTable().getName();
		if (null == keyTable || "".equals(keyTable)) {
			throw new CodeGenerateException("主键表不能为空");
		}
		try {
			listPage = (ListPage) resolveObject(doc);
		} catch (Exception e) {
			throw new CodeGenerateException("表单xml解析错误");
		}
		String flag = listPage.getFlag();
		List tables = null;
		if ("listPage".equals(flag)) {
			tables = listPage.getTable();
		} else if ("editPage".equals(flag)) {
			tables = listPage.getEditPage().getTable();
		} else if ("viewPage".equals(flag)) {
			tables = listPage.getViewPage().getTable();
		}
		Iterator tableIt = tables.iterator();
		TableVO tableVo = null;
		StringBuffer joinSql = new StringBuffer();
		// 对象映射 数据对象名--数据列集合
		Map objectColumn = new HashMap();
		while (tableIt.hasNext()) {
			tableVo = (TableVO) tableIt.next();
			DataSource ds = tableVo.getDataSource();
			DataSource source = dataSourceService.findByName(ds.getName());
			List tableVos = tableVo.getFromTableList();
			Iterator tableVosIt = tableVos.iterator();
			while (tableVosIt.hasNext()) {
				TableVO vo = (TableVO) tableVosIt.next();
				String tableName = vo.getName();
				List dataColumns = dataColumnService
						.findAllByTableName(tableName);
				if (null == dataColumns) {
					throw new CodeGenerateException("数据对象没有对应的数据列");
				}
				objectColumn.put(tableName, dataColumns);
			}
			try {
				joinSql = new ResolveXmlService().componentSql(tableVo,
						joinSql, source);
			} catch (Exception e) {
				throw new CodeGenerateException("拼接查询sql出错");
			}
			List tabs = listPage.getTabs();
			List basePages = new ArrayList();
			if (null != tabs) {
				Iterator tabsIt = tabs.iterator();
				while (tabsIt.hasNext()) {
					Tab tab = (Tab) tabsIt.next();
					BasePage basePage = (BasePage) resolveObject(tab.getUrl());
					basePages.add(basePage);
				}
			}
			if ("listPage".equals(flag)) {
				listPage.setDataSource(source);
				listPage.setTabs(basePages);
				StringBuffer querySB = new StringBuffer();
				List queryParams = new ArrayList();
				List queryZones = listPage.getQueryZone();
				if (null != queryZones && queryZones.size() > 0) {
					Iterator queryZonesIt = queryZones.iterator();
					while (queryZonesIt.hasNext()) {
						QueryZone queryZone = (QueryZone) queryZonesIt.next();
						List queryColumns = queryZone.getQueryColumns();
						Iterator queryColumnsIt = queryColumns.iterator();
						while (queryColumnsIt.hasNext()) {
							if (!"".equals(querySB.toString())) {
								querySB.append(" and ");
							}
							QueryColumn queryColumn = (QueryColumn) queryColumnsIt
									.next();
							int queryType = ColumnData.DATACOLUMN_UI_TYPE_INPUT;
							try {
								queryType = Integer.parseInt(queryColumn
										.getType());
							} catch (Exception e) {
								// throw new
								// CodeGenerateException("列表页的查询区域的控件类型应为数字");
							}

							if (queryType == ColumnData.DATACOLUMN_UI_TYPE_INPUT) {
								querySB.append(queryColumn.getName()
										+ " like '%'||?||'%' ");
							} else {
								querySB.append(queryColumn.getName() + " = ? ");
							}
							Param param = new Param();
							param.setKey(queryColumn.getName());
							param.setType(queryColumn.getFieldDataType());
							queryParams.add(param);
						}
					}
				}
				if (joinSql.indexOf("where") > 0
						&& !"".equals(querySB.toString())) {
					joinSql.insert(joinSql.indexOf("where") + 6, querySB
							.toString()
							+ " and ");
				} else if (joinSql.indexOf("where") < 0
						&& !"".equals(querySB.toString())) {
					joinSql.append("  where " + querySB.toString());
				}
				listPage.setSql(joinSql.toString());
				List whereParams = tableVo.getWhereParams();
				if (null != whereParams) {
					queryParams.addAll(whereParams);
				}
				listPage.setParams(queryParams);
				listPage.setObjectColumn(objectColumn);
				String deleteSql = "DELETE FROM " + keyTable.toUpperCase()
						+ " WHERE ";
				listPage.setKeyTable(keyTable);
				StringBuffer deleteSB = new StringBuffer();
				List deleteParams = new ArrayList();
				List rows = listPage.getRowButton();
				if (null != rows && rows.size() > 0) {
					Iterator rowsIt = rows.iterator();
					while (rowsIt.hasNext()) {
						Button button = (Button) rowsIt.next();
						int type = Integer.parseInt(button.getType().trim());
						List events = button.getEvent();
						if (null != events && events.size() > 0) {
							Event event = (Event) button.getEvent().get(0);
							List params = event.getParas();
							Iterator paramsIt = params.iterator();
							while (paramsIt.hasNext()) {
								Param param = (Param) paramsIt.next();
								if (Button.BUTTON_SINGLE_DELETE == type) {
									if (!"".equals(deleteSB.toString())) {
										deleteSB.append(" and ");
									}
									deleteParams.add(param);
									deleteSB
											.append("  " + param.getKey() + " ");
								}
								// else if (Button.BUTTON_SINGLE_EDIT ==
								// type) {
								// if (!"".equals(updateSB.toString())) {
								// updateSB.append(" and ");
								// }
								// updateParams.add(param);
								// updateSB.append(" " + param.getKey() + "
								// = ? ");
								// }
							}
						}
					}
				}
				listPage.setDeleteSql(deleteSql + deleteSB.toString());
				Map deleteParamMap = new HashMap();
				deleteParamMap.put(keyTable, deleteParams);
				listPage.setDeleteParam(deleteParamMap);
				String tableName = listPage.getKeyTable();
				EditPage editPage = listPage.getEditPage();
				if (null != editPage && null != editPage.getId()) {
					EditPage edit = null;
					try {
						edit = (EditPage) resolveObject(editPage.getId());
					} catch (CodeGenerateException e) {
						throw e;
					}
					Map findParams = new HashMap();
					findParams.put(tableName, edit.getFindParam());
					edit.setFindParams(findParams);
					listPage.setEditPage(edit);
				}
				ViewPage viewPage = listPage.getViewPage();
				if (null != viewPage && null != viewPage.getId()) {
					ViewPage view = null;
					try {
						view = (ViewPage) resolveObject(viewPage.getId());
					} catch (Exception e) {
						throw new CodeGenerateException("列表页的查看页解析出错");
					}
					listPage.setViewPage(view);
				}
				return listPage;
			} else if ("editPage".equals(flag)) {
				EditPage editPage = listPage.getEditPage();
				editPage.setTabs(basePages);
				editPage.setDataSource(source);
				editPage.setObjectColumn(objectColumn);
				try {
					tableVo = new ResolveXmlService().spellSqlQuery(tableVo);
				} catch (Exception e) {
					throw new CodeGenerateException("编辑页拼接查询sql出错");
				}
				String selectSql = tableVo.getSelect();
				editPage.setFindSql(selectSql);
				editPage.setFindParam(tableVo.getWhereParams());
				List list = tableVo.getFromTableList();
				list = new ResolveXmlService().spellSetTabelSql(list);
				Iterator listIt = list.iterator();
				while (listIt.hasNext()) {
					TableVO table = (TableVO) listIt.next();
					editPage.setInsertSql(table.getInsert());
					List keys = table.getKeyList();
					List keyList = new ArrayList();
					if (null != keys) {
						Iterator keyIt = keys.iterator();
						while (keyIt.hasNext()) {
							FieldDefVO field = (FieldDefVO) keyIt.next();
							keyList.add(field.getToFieldName());
						}
					}
					editPage.setKeyList(keyList);
					editPage.setInsertParam(table.getInsertParams());
					editPage.setUpdateSql(table.getUpdate());
					// if (null == table.getWhereParams()) {
					// throw new CodeGenerateException("编辑页查询没有差数！");
					// }
					editPage.setUpdateParam(table.getUpdateParams());
				}
				return editPage;
			} else if ("viewPage".equals(flag)) {
				ViewPage viewPage = listPage.getViewPage();
				viewPage.setTabs(basePages);
				try {
					tableVo = new ResolveXmlService().spellSqlQuery(tableVo);
				} catch (Exception e) {
					throw new CodeGenerateException("查看页拼接查询sql出错");
				}
				String selectSql = tableVo.getSelect();
				viewPage.setFindSql(selectSql);
				viewPage.setObjectColumn(objectColumn);
				return viewPage;
			} else {
				EditPage editPage = listPage.getEditPage();
				editPage.setTabs(basePages);
				editPage.setDataSource(source);
				editPage.setObjectColumn(objectColumn);
				try {
					tableVo = new ResolveXmlService().spellSqlQuery(tableVo);
				} catch (Exception e) {
					throw new CodeGenerateException("编辑页拼接查询sql出错");
				}
				String selectSql = tableVo.getSelect();
				editPage.setFindSql(selectSql);
				editPage.setFindParam(tableVo.getWhereParams());
				List list = tableVo.getFromTableList();
				list = new ResolveXmlService().spellSetTabelSql(list);
				Iterator listIt = list.iterator();
				while (listIt.hasNext()) {
					TableVO table = (TableVO) listIt.next();
					editPage.setInsertSql(table.getInsert());
					List keys = table.getKeyList();
					List keyList = new ArrayList();
					if (null != keys) {
						Iterator keyIt = keys.iterator();
						while (keyIt.hasNext()) {
							FieldDefVO field = (FieldDefVO) keyIt.next();
							keyList.add(field.getToFieldName());
						}
					}
					editPage.setKeyList(keyList);
					editPage.setInsertParam(table.getInsertParams());
					editPage.setUpdateSql(table.getUpdate());
					// if (null == table.getWhereParams()) {
					// throw new CodeGenerateException("编辑页查询没有差数！");
					// }
					editPage.setUpdateParam(table.getUpdateParams());
				}
				return editPage;
			}
		}
		return null;
	}

	public void setDataColumnService(IDataColumnService dataColumnService) {
		this.dataColumnService = dataColumnService;
	}

}
