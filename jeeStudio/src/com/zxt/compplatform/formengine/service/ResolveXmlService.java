package com.zxt.compplatform.formengine.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.zxt.compplatform.codegenerate.util.CodeGenerateException;
import com.zxt.compplatform.datasource.entity.DataSource;
import com.zxt.compplatform.formengine.dao.BaseDao;
import com.zxt.compplatform.formengine.entity.database.Dictionary;
import com.zxt.compplatform.formengine.entity.dataset.FieldDefVO;
import com.zxt.compplatform.formengine.entity.dataset.RelationshipVO;
import com.zxt.compplatform.formengine.entity.dataset.TableVO;
import com.zxt.compplatform.formengine.entity.dataset.WhereVO;
import com.zxt.compplatform.formengine.entity.view.Button;
import com.zxt.compplatform.formengine.entity.view.ColumnRoles;
import com.zxt.compplatform.formengine.entity.view.EditColumn;
import com.zxt.compplatform.formengine.entity.view.EditMode;
import com.zxt.compplatform.formengine.entity.view.EditRulesEngin;
import com.zxt.compplatform.formengine.entity.view.Event;
import com.zxt.compplatform.formengine.entity.view.Group;
import com.zxt.compplatform.formengine.entity.view.JSFunction;
import com.zxt.compplatform.formengine.entity.view.Layout;
import com.zxt.compplatform.formengine.entity.view.ListColumn;
import com.zxt.compplatform.formengine.entity.view.Page;
import com.zxt.compplatform.formengine.entity.view.Pagination;
import com.zxt.compplatform.formengine.entity.view.Param;
import com.zxt.compplatform.formengine.entity.view.QueryColumn;
import com.zxt.compplatform.formengine.entity.view.Tab;
import com.zxt.compplatform.formengine.entity.view.TextColumn;
import com.zxt.compplatform.formengine.entity.view.TitleColumn;
import com.zxt.compplatform.formengine.entity.view.ValidateRule;
import com.zxt.compplatform.formengine.entity.view.ViewColumn;
import com.zxt.compplatform.formula.entity.Formula;

/**
 * @author Lizf
 * 
 */
public class ResolveXmlService extends BaseDao implements IResolveXmlService {
	private static final Log log = LogFactory.getLog(ResolveXmlService.class);
	private ComponentsService componentsService;

	public ComponentsService getComponentsService() {
		return componentsService;
	}

	public void setComponentsService(ComponentsService componentsService) {
		this.componentsService = componentsService;
	}

	public ResolveXmlService() {

	}

	private String dataSetPath = "/DataMapping/DataSet";

	/**
	 * 解析Table
	 * 
	 * @param table
	 * @param tableVO
	 * @param dataSource
	 * @return查出dataSource对象
	 * @throws Exception
	 */

	public List resolveDataSource(Element dataSourceElement, DataSource dataSourcevo) throws Exception {
		List list = new ArrayList();
		if (dataSourceElement != null) {
			String dataSourceType = dataSourceElement.getAttributeValue("type");
			String dataType = dataSourceElement.getAttributeValue("dataType");
			Element dsName = dataSourceElement.getChild("DSName");
			String dsNameId = dsName.getText();
			Element displayNameElement = dataSourceElement.getChild("DisplayName");
			String displayName = displayNameElement.getText();
			dataSourcevo.setAccessType(dataSourceType);
			dataSourcevo.setDataType(dataType);
			dataSourcevo.setId(dsNameId);
			dataSourcevo.setName(dsNameId);
			list.add(dataSourcevo);
		}else {
			throw new CodeGenerateException("数据源节点不存在！");
		}
		return list;
	}

	public TableVO resolveTable(Element table, TableVO tableVO, List dataSource, Document doc)
			throws Exception {
		List list = new ArrayList();
		String tableType = table.getAttributeValue("type");
		/**
		 * GUOWEIXIN  目的是让ViewPage得到 表名称
		 * GetTable -->FromTables-->FromTable-->TableName
		 */
		Element getTableNode=table.getChild("GetTable");
		String type = getTableNode.getAttributeValue("type").trim();
		if (type != null && type.trim().length() > 0) {
			if (type.equals("COMPONENT")) {
				// type为组件

				List fromTables = getTableNode.getChild("FromTables").getChildren();
				for (int i = 0; i < fromTables.size(); i++) {
					Element fromTable = ((Element) fromTables.get(i));
					Element tableName = fromTable.getChild("TableName");
					tableVO = this.resolveTableName(tableName);
				}
			}
		}	
		//--end GUOWEXIN
		tableVO.setType(tableType);

		// 解析字段信息
		Element fieldDef = table.getChild("FieldDef");
		tableVO = this.resolveFieldDef(fieldDef, tableVO, dataSource);

		// 解析查询表信息ȡgetTable
		Element getTable = table.getChild("GetTable");
		tableVO = this.resolveGetTable(getTable, tableVO, dataSource);
		for (int i = 0; i < dataSource.size(); i++) {
			DataSource data = (DataSource) dataSource.get(i);
			tableVO.setDataSource(data);

		}
		// 解析存储表信息

		Element setTable = table.getChild("SetTable");
		if (setTable != null) {
			tableVO = this.resolveSetTable(setTable, tableVO, dataSource, doc);

		}
		return tableVO;
	}

	/**
	 * 解析fieldDef
	 * 
	 * @param fieldDef
	 * @param table
	 * @return
	 * @throws Exception
	 */
	public TableVO resolveFieldDef(Element fieldDef, TableVO table, List dataSource) throws Exception {
		List fieldVOlist = table.getFieldList();
		if (fieldVOlist == null) {
			fieldVOlist = new ArrayList();
		}
		String type = fieldDef.getAttributeValue("type").trim();
		if (type.equals("FIX")) {
			List fieldList = fieldDef.getChildren("Field");
			fieldVOlist = this.resolveFieldDefFix(fieldList, dataSource);
			table.setFieldList(fieldVOlist);
		} else if (type.equals("DYNAMICS")) {

		}
		return table;
	}

	/**
	 * 处理当fieldDef 的type是fix
	 * 
	 * @param fieldList
	 * @return
	 * @throws Exception
	 */
	private List resolveFieldDefFix(List fieldList, List dataSource) throws Exception {
		List fieldVOList = new ArrayList();
		if (fieldList != null && fieldList.size() > 0) {
			for (int i = 0; i < fieldList.size(); i++) {
				Element fromField = ((Element) fieldList.get(i)).getChild("FromField");
				String fromType = fromField.getAttributeValue("type");
				Element toField = ((Element) fieldList.get(i)).getChild("ToField");
				FieldDefVO fd = new FieldDefVO();
				fd.setFromFieldIsPrimKey(fromField.getChildTextTrim("IsPrimeKey"));
				if (fromType.equals("FIELD")) {
					Element fieldname = fromField.getChild("FieldName");
					FieldDefVO field = this.resolveFieldName(fieldname);
					TableVO table = this.resolveTableName(fieldname.getChild("TableName"));
					DataSource dsv = this.findSingleDataSource(dataSource, table.getDataSource().getName());
					table.setDataSource(dsv);
					fd.setFromFieldName(field.getToFieldName());
					fd.setFromFieldType(fromField.getChildTextTrim("Type"));
					fd.setFromTable(table);
					fd.setPrefix(table.getName());
					fd.setToFieldLength(toField.getChildTextTrim("Length"));
					fd.setToFieldType(toField.getChildTextTrim("Type"));
					fd.setToFieldName(toField.getChildTextTrim("Name"));
				} else if (fromType.equals("COMPUTER")) {
					//
				}
				fieldVOList.add(fd);
			}
		}
		return fieldVOList;
	}

	/**
	 * 解析Field
	 * 
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	public FieldDefVO resolveFieldName(Element fieldName) throws Exception {
		String fieldType = fieldName.getAttributeValue("type").trim();
		FieldDefVO field = new FieldDefVO();
		field.setType(fieldType);
		if (fieldType != null) {
			if (fieldType.equals("NAME")) {
				field.setToFieldName(fieldName.getChildTextTrim("Name"));
			} else if (fieldType.equals("INDEX")) {

				field.setToFieldIndex(fieldName.getChildTextTrim("INDEX"));
			}
		}
		return field;
	}

	/**
	 * 解析TableName
	 * 
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public TableVO resolveTableName(Element tableName) throws Exception {
		String tableNameType = tableName.getAttributeValue("type").trim();
		//GUOWEIXIN  得到表名称 String tableNameReal=tableName.getText();
		TableVO tb = new TableVO();
		if (tableNameType != null && tableNameType.trim().length() > 0) {
			if (tableNameType.equals("NAME")) {
				DataSource dsv = new DataSource();
				dsv.setName(tableName.getChildTextTrim("DSName"));
				tb.setDataSource(dsv);
				tb.setType(tableName.getChildTextTrim("STType"));
				tb.setName(tableName.getTextTrim());
			} else if (tableNameType.equals("EMBED")) {
				//
			}
		}
		return tb;
	}

	/**
	 * 解析GetTable
	 * 
	 * @param getTable
	 * @param tableVO
	 * @return
	 * @throws Exception
	 */
	public TableVO resolveGetTable(Element getTable, TableVO tableVO, List dataSource) throws Exception {
		String type = getTable.getAttributeValue("type").trim();
		if (type != null && type.trim().length() > 0) {
			if (type.equals("COMPONENT")) {
				// type为组件

				List fromTables = getTable.getChild("FromTables").getChildren();
				tableVO = this.resolveTableFrom(fromTables, tableVO, dataSource);
				// ȡwhere
				Element where = getTable.getChild("Where");
				if (where != null) {
					Element condition = where.getChild("Condition");
					tableVO = this.resolveTableCondition(condition, tableVO, dataSource, 0, null);
				}
				// ȡgroup by
				Element groupBy = getTable.getChild("GroupBy");
				if (groupBy != null) {
					tableVO = this.resolveGroupBy(tableVO, dataSource, groupBy);
				}
				// ȡorder by
				Element orderBy = getTable.getChild("OrderBy");
				if (orderBy != null) {
					tableVO = this.resolveOrderBy(orderBy, tableVO, dataSource);
				}
			} else if (type.equals("SQLTEXT")) {

			}
		}
		return tableVO;
	}

	/**
	 * 解析setTable
	 * ------------------------------------------------------------------------
	 * 
	 * @param setTable
	 * @param tableVO
	 * @param dataSource
	 * @return
	 * @throws Exception
	 */
	public TableVO resolveSetTable(Element setTable, TableVO tableVO, List dataSource, Document doc)
			throws Exception {
		String type = setTable.getAttributeValue("type").trim();
		List fromList = tableVO.getFromTableList();
		Element setOptionElement = setTable.getChild("SetOption");
		if (tableVO.getOprType() == null) {
			tableVO.setOprType(setOptionElement.getAttributeValue("oprRange"));
		}
		if (fromList == null) {
			fromList = new ArrayList();
		}
		if (type.equals("NO")) {
			return null;
		} else if (type.equals("DEFAULTTABLE")) {
			Element t = setTable.getChild("TableName");
			TableVO table = this.resolveTableName(t);
			// List sourceList = this.findDataSourceList(table,doc);
			// List dataSetList = this.findXml(doc,table);
			// if(dataSetList!=null&&dataSetList.size()>0){
			// Element tableEl = (Element) dataSetList.get(0);
			// table = this.resolveTable(tableEl,table,sourceList,doc);
			// table = this.recursiveTable(table,doc,sourceList,type);
			// }
			DataSource ds = this.findSingleDataSource(dataSource, table.getDataSource().getName());
			table.setDataSource(ds);
			List l = new ArrayList();
			List keyList = new ArrayList();
			for (int i = 0; i < tableVO.getFieldList().size(); i++) {
				FieldDefVO fd = (FieldDefVO) tableVO.getFieldList().get(i);
				fd.setFromFieldName(fd.getToFieldName());
				if (fd.getFromFieldIsPrimKey().equals("TRUE")) {
					keyList.add(fd);
				}
				l.add(fd);
			}
			if (table.getDataSource().getAccessType().equals("DB")) {// 改过
				if (tableVO.getOprType().equals("I")) {
					table.setInsertFieldList(l);
				} else if (tableVO.getOprType().equals("U")) {
					table.setUpdateFieldList(l);
					table.setKeyList(keyList);
				} else if (tableVO.getOprType().equals("D")) {
					table.setDeleteFieldList(l);
					table.setKeyList(keyList);
				}
				table.setOprType(tableVO.getOprType());
				fromList.add(table);
				tableVO.setFromTableList(fromList);
			}
			return tableVO;
		} else if (type.equals("CUSTOM")) {
			Element setOption = null;
			List SetOptionList = setTable.getChildren("SetOption");
			setOption = this.findSetOption(SetOptionList, tableVO.getOprType());
			tableVO = this.resolveSetOption(setOption, tableVO, dataSource, doc);
		}
		return tableVO;
	}

	// --------------------开始---------------

	/**
	 * 查找相应操作的setOption结构
	 * 
	 * @param SetOptionList
	 * @param oprType
	 * @param tableVO
	 * @param dataSource
	 * @return
	 * @throws Exception
	 */
	private Element findSetOption(List SetOptionList, String oprType) throws Exception {
		Element setOption = null;
		for (int i = 0; i < SetOptionList.size(); i++) {
			setOption = (Element) SetOptionList.get(i);
			String setOptionType = setOption.getAttributeValue("oprRange");
			if (setOptionType.indexOf(oprType) >= 0) {
				break;
			}
		}
		return setOption;
	}

	/**
	 * 解析每一个setOption
	 * 
	 * @param setOption
	 * @param tableVO
	 * @param dataSource
	 * @throws Exception
	 */
	private TableVO resolveSetOption(Element setOption, TableVO tableVO, List dataSource, Document doc)
			throws Exception {
		Element relationshipsList = setOption.getChild("Relationships");
		List setOptionItemList = setOption.getChildren("SetOptionItem");
		setOptionItemList = this.sortSetOption(setOptionItemList);
		// 取表之间的关系

		List relationList = this.resolveRelationships(relationshipsList);
		List fromList = tableVO.getFromTableList();
		if (fromList == null) {
			fromList = new ArrayList();
		}
		// 如果表之间有关系
		if (relationList != null && relationList.size() > 0) {
			for (int j = 0; j < relationList.size(); j++) {
				RelationshipVO relation = (RelationshipVO) relationList.get(j);
				if (relation.getType().equals("ONETOMANY")) {
					fromList = this.resolveRelation(relation, setOptionItemList, dataSource, "oneMany", doc,
							tableVO);
				} else if (relation.getType().equals("ONETOONE")) {
					fromList = this.resolveRelation(relation, setOptionItemList, dataSource, "oneOne", doc,
							tableVO);
				} else if (relation.getType().equals("MANYTOONE")) {
					fromList = this.resolveRelation(relation, setOptionItemList, dataSource, "oneMany", doc,
							tableVO);
				} else if (relation.getType().equals("MANYTOMANY")) {

				}
			}

		} else {
			// 表之间没有关系

			fromList = this.resolveSetOptionItemList(setOptionItemList, fromList, dataSource, doc, tableVO);
		}
		tableVO.setFromTableList(fromList);
		return tableVO;
	}

	/**
	 * 处理setOptinItem
	 * 
	 * @param setOptionItemList
	 * @param fromList
	 * @param dataSource
	 * @return
	 * @throws Exception
	 */
	private List resolveSetOptionItemList(List setOptionItemList, List fromList, List dataSource,
			Document doc, TableVO tableVO) throws Exception {
		if (setOptionItemList != null && setOptionItemList.size() > 0) {
			for (int k = 0; k < setOptionItemList.size(); k++) {
				Element setOptionItem = (Element) setOptionItemList.get(k);
				List temp = this.resolveSetOptionItem(setOptionItem, null, dataSource, "", doc, tableVO);
				fromList = this.addFromList(fromList, temp);
			}
		}
		return fromList;
	}

	/**
	 * 处理relation
	 * 
	 * @param relation
	 * @param setOptionItemList
	 * @param dataSource
	 * @param type
	 * @return
	 * @throws Exception
	 */
	private List resolveRelation(RelationshipVO relation, List setOptionItemList, List dataSource,
			String type, Document doc, TableVO tableVO) throws Exception {
		List fromList = new ArrayList();
		TableVO first = relation.getFirst();
		Element oneSetOptionItem = this.findSetOptionItem(first.getName(), setOptionItemList);
		fromList = this.resolveSetOptionItem(oneSetOptionItem, first.getFieldList(), dataSource, "one", doc,
				tableVO);
		setOptionItemList.remove(oneSetOptionItem);
		TableVO second = relation.getSecond();
		Element secondSetOptionItem = this.findSetOptionItem(second.getName(), setOptionItemList);
		List secondList = this.resolveSetOptionItem(secondSetOptionItem, null, dataSource, "many", doc,
				tableVO);
		setOptionItemList.remove(secondSetOptionItem);
		fromList = this.addFromList(fromList, secondList);
		if (setOptionItemList != null && setOptionItemList.size() > 0) {
			for (int k = 0; k < setOptionItemList.size(); k++) {
				Element setOptionItem = (Element) setOptionItemList.get(k);
				List temp = this
						.resolveSetOptionItem(setOptionItem, null, dataSource, "oneone", doc, tableVO);
				fromList = this.addFromList(fromList, temp);
			}
		}
		return fromList;
	}

	/**
	 * 解析每个setOptionItem
	 * 
	 * @param setOptionItem
	 * @param setOptionType
	 * @param dataSource
	 * @throws Exception
	 */
	private List resolveSetOptionItem(Element setOptionItem, List outList, List dataSource,
			String relationship, Document doc, TableVO tableVO) throws Exception {
		String setOptionItemType = setOptionItem.getAttributeValue("type");
		List list = new ArrayList();
		if (setOptionItemType.equals("SQLTEXT")) {
			Element sqlText = setOptionItem.getChild("SqlText");
			list = this.resolveSqlText(sqlText, outList, dataSource, relationship, tableVO);
		} else if (setOptionItemType.equals("STOREPRO")) {

		} else if (setOptionItemType.equals("OPRLIST")) {

		} else if (setOptionItemType.equals("TABLE")) {
			Element toTable = setOptionItem.getChild("ToTable");
			list = this.resolveTotable(toTable, tableVO, dataSource, relationship, doc);

		}
		return list;
	}

	/**
	 * 查询每个setOption中的SetOptionItem
	 * 
	 * @param name
	 * @param setOptionItemList
	 * @return
	 * @throws Exception
	 */
	private Element findSetOptionItem(String name, List setOptionItemList) throws Exception {
		for (int j = 0; j < setOptionItemList.size(); j++) {
			Element setOptionItem = (Element) setOptionItemList.get(j);
			String tempName = setOptionItem.getAttributeValue("name").trim();
			if (tempName.equals(name)) {
				return setOptionItem;
			}

		}
		return null;
	}

	/**
	 * 
	 * @param setOptionList
	 * @return
	 * @throws Exception
	 */
	private List sortSetOption(List setOptionItemList) throws Exception {
		List list = new ArrayList();
		for (int i = 0; i < setOptionItemList.size(); i++) {
			Element el = this.findSortSetOption(setOptionItemList, i + "");
			list.add(el);

		}
		return list;
	}

	/**
	 * 
	 * @param setOptionList
	 * @return
	 * @throws Exception
	 */
	private Element findSortSetOption(List setOptionItemList, String index) throws Exception {
		for (int i = 0; i < setOptionItemList.size(); i++) {
			Element el = (Element) setOptionItemList.get(i);
			String x = el.getChildTextTrim("Index");
			if (x.equals(index)) {
				return el;
			}

		}
		return null;
	}

	/**
	 * 往fromList里添加值

	 * 
	 * @param fromList
	 * @param list
	 * @return
	 * @throws Exception
	 */
	private List addFromList(List fromList, List list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			TableVO tb = (TableVO) list.get(i);
			fromList.add(tb);
		}
		return fromList;
	}

	// --------------------结束---------------

	// ---------------------解析关系开始-----------
	public List resolveRelationships(Element relationshipsList) throws Exception {
		List relationVOList = new ArrayList();
		if (relationshipsList != null) {
			List relationList = relationshipsList.getChildren("Relation");
			if (relationList != null && relationList.size() > 0) {
				for (int j = 0; j < relationList.size(); j++) {
					Element rel = (Element) relationList.get(j);
					RelationshipVO rs = new RelationshipVO();
					String relationType = rel.getAttributeValue("type");
					rs.setType(relationType.trim());
					if (relationType.equals("ONETOONE")) {

					} else if (relationType.equals("ONETOMANY")) {
						Element one = rel.getChild("One");
						TableVO first = new TableVO();
						first.setName(one.getChildTextTrim("OptionName"));
						// relationVOList.add(first);
						List fieldMapList = one.getChildren("FieldMap");
						List fieldList = new ArrayList();
						for (int k = 0; k < fieldMapList.size(); k++) {
							Element fieldMap = (Element) fieldMapList.get(k);
							String fieldMapType = fieldMap.getAttributeValue("type");
							Element operand = fieldMap.getChild("Operand");
							Element oprName = operand.getChild("OprName");
							String tableValue = oprName.getChildTextTrim("Name");
							FieldDefVO fd = new FieldDefVO();
							fd.setToFieldName(tableValue);
							fd.setToFieldType(fieldMapType);
							fieldList.add(fd);
							first.setFieldList(fieldList);
							rs.setFirst(first);

						}
						// relationVOList.add(rs);
						Element many = rel.getChild("Many");
						TableVO second = new TableVO();
						second.setName(many.getChildTextTrim("OptionName"));
						List fieldMapList1 = many.getChildren("FieldMap");
						List fieldList1 = new ArrayList();
						for (int t = 0; t < fieldMapList1.size(); t++) {
							Element fieldMap = (Element) fieldMapList1.get(t);
							String fieldMapType = fieldMap.getAttributeValue("type");
							Element operand = fieldMap.getChild("Operand");
							Element oprName = operand.getChild("OprName");
							String tableValue = oprName.getChildTextTrim("Name");
							FieldDefVO fd = new FieldDefVO();
							fd.setToFieldName(tableValue);
							fd.setToFieldType(fieldMapType);
							// Element oprValue = operand.getChild("OprValue");
							Element oprValue = operand.getChild("OprValue");
							String oprValueType = oprValue.getAttributeValue("type").trim();
							if (oprValueType.equals("VARIABLE")) {
								String table = oprValue.getChildText("Variable");// 字段
								fd.setFromFieldName(table);
								// fd.setFromType(oprValueType);
								// fieldList1.add(fd);
							} else if (oprValueType.equals("FORMULA")) {
								Element formula = oprValue.getChild("Formula");
								String cdata = formula.getChildTextTrim("CDATA").substring(1);
								fd.setFromFielddRule(cdata);
							}
							fieldList1.add(fd);
							second.setFieldList(fieldList1);
						}
						rs.setSecond(second);

					} else if (relationType.equals("MANYTOONE")) {

					} else if (relationType.equals("MANYTOMANY")) {

					}
					relationVOList.add(rs);
				}

			}
		}
		return relationVOList;
	}

	// ------------------------------------------
	// ------------resoveToTable开始--------------
	/**
	 * @param tableList
	 * @param table
	 * @param dataSource
	 * @param toTable
	 * @param oprType
	 * @return
	 * @throws Exception
	 */
	public List resolveTotable(Element table, TableVO tableVO, List dataSource, String relationship,
			Document doc) throws Exception {
		List tablelist = new ArrayList();
		List whereList = new ArrayList();
		// TableVO tableVo = new TableVO();
		String type = table.getAttributeValue("type");
		if (type.equals("NAME")) {
			List bigTableList = tableVO.getFieldList();
			Element tableName = table.getChild("TableName");
			TableVO smallTable = this.resolveTableName(tableName);
			smallTable.setRelationship(relationship);
			List dataSetList = this.findXml(doc, smallTable);
			if (dataSetList != null && dataSetList.size() > 0) {
				Element tableEl = (Element) dataSetList.get(0);
				// 取FieldDef
				Element fieldDef = tableEl.getChild("FieldDef");
				// smallTable = new
				// ResolveDataMappingFieldDef().resolveFieldDef(fieldDef,
				// tableVO, dataSource);
				List smallTableList = this.resolveFieldDef(fieldDef, tableVO, dataSource).getFieldList();

				String name = tableName.getChildText("Name");
				List list = new ArrayList();
				for (int k = 0; k < bigTableList.size(); k++) {
					FieldDefVO filedvo = (FieldDefVO) bigTableList.get(k);
					for (int j = 0; j < smallTableList.size(); j++) {
						FieldDefVO filedvo1 = (FieldDefVO) smallTableList.get(j);
						if (filedvo.getToFieldName().equals(filedvo1.getToFieldName())) {

							list.add(filedvo1);
						}
					}
					String fromFieldName = filedvo.getFromFieldName();
					String toFieldName1 = filedvo.getToFieldName();
					smallTable.setFieldList(list);
					tablelist.add(smallTable);
					Element where = table.getChild("where");
					if (where != null) {
						Element condition = where.getChild("Condition");
						smallTable = this.resolveTableCondition(condition, smallTable, dataSource, 0, null);
					}
					DataSource dsv = this.findSingleDataSource(dataSource, smallTable.getDataSource()
							.getName());
					if (dsv != null) {
						smallTable.setDataSource(dsv);
					}
					for (int i = 0; i < bigTableList.size(); i++) {
						FieldDefVO toField = (FieldDefVO) bigTableList.get(i);
						String toFromName = toField.getToFieldName();
						if (fromFieldName.indexOf(toFromName) > 0) {
							filedvo.setToFieldName(toField.getToFieldName());// 新加
							filedvo.setFromFieldName(toField.getToFieldName());
							filedvo.setToFieldType(toField.getFromFieldType());
							// toField.setToFieldType(filedvo.getFromFieldType());
						}
					}
					list.add(filedvo);
					if (tableVO.getOprType().indexOf("I") >= 0) {
						smallTable.setInsertFieldList(list);
						smallTable.setOprType("I");

					}

					tablelist.add(smallTable);
					if (tableVO.getOprType().indexOf("U") >= 0) {
						smallTable.setUpdateFieldList(list);
						tablelist.add(smallTable);
						smallTable.setOprType("U");
					}
					if (tableVO.getOprType().indexOf("D") >= 0) {
						smallTable.setDeleteFieldList(list);
						tablelist.add(smallTable);
						smallTable.setOprType("D");
					}
				}
			}
		}
		if (type.equals("CUSTOM")) {
			Element tableName = table.getChild("TableName");
			TableVO smallTable = this.resolveTableName(tableName);
			// smallTable.setOprType("I,U,D");
			DataSource dsv = this.findSingleDataSource(dataSource, smallTable.getDataSource().getName());
			if (dsv != null) {
				smallTable.setDataSource(dsv);
			}
			smallTable.setRelationship(relationship);
			List smallTableList = tableVO.getFieldList();
			Element where = table.getChild("Where");// 没有测试所以注释了

			if (where != null) {
				Element condition = where.getChild("Condition");
				if (condition != null) {
					if (!smallTable.getDataSource().getAccessType().equals("DB")) {
						TableVO t = smallTable.reSet();
						t.setFieldList(this.findFieldDefVOList(t, doc));
						t = this.resolveTableCondition(condition, t, dataSource, 0, null);
						smallTable.setWhereList(t.getWhereList());
					} else {
						smallTable.setFieldList(tableVO.getFieldList());
						smallTable = this.resolveTableCondition(condition, smallTable, dataSource, 0, null);
					}

				}
			}

			List fieldMapList = table.getChildren("FieldMap");
			List fieldList = new ArrayList();
			List outList = new ArrayList();
			List keyList = new ArrayList();

			List inList = new ArrayList();
			for (int k = 0; k < fieldMapList.size(); k++) {
				Element fieldMap = (Element) fieldMapList.get(k);
				String fieldMapType = fieldMap.getAttributeValue("type");

				if (fieldMapType.equals("IN")) {
					inList.add(fieldMap);

				}
				if (fieldMapType.equals("OUT")) {
					outList.add(fieldMap);
				}
				if (fieldMapType.equals("KEY")) {
					keyList.add(fieldMap);
				}
			}
			// key
			List key = new ArrayList();
			for (int k = 0; k < keyList.size(); k++) {
				Element infieldMap = (Element) keyList.get(k);
				Element inOperand = infieldMap.getChild("Operand");
				String operandType = inOperand.getAttributeValue("type");
				Element inoprName = inOperand.getChild("OprName");
				Element inoprValue = inOperand.getChild("OprValue");
				String oprValueType = inoprValue.getAttributeValue("type");
				FieldDefVO keyfieldVo = new FieldDefVO();
				String tofieldName = inoprName.getChildTextTrim("Name");
				// String tofromfieldName =
				// inoprValue.getChildTextTrim("Variable");

				keyfieldVo.setToFieldName(tofieldName);
				if (oprValueType.equals("FIELD")) {
					Element el = inoprValue.getChild("FieldName");
					FieldDefVO fd = this.resolveFieldName(el);
					// String inFieldName =
					// inoprValue.getChildTextTrim("FieldName");
					Element tael = el.getChild("TableName");
					TableVO tb = this.resolveTableName(tael);
					fd.setFromTable(tb);
					keyfieldVo.setFromFieldName("$F" + fd.getFromTable().getDataSource().getName() + "."
							+ fd.getFromTable().getName() + "." + fd.getToFieldName() + "$");
				} else if (oprValueType.equals("PARAM")) {
					String inParamName = inoprValue.getChildTextTrim("ParamName");
					keyfieldVo.setFromFieldName("$P" + inParamName + "$");
				} else if (oprValueType.equals("VALUE")) {
					String inValueName = inoprValue.getChildTextTrim("VALUE");
					keyfieldVo.setFromFieldName(inValueName);
				} else if (oprValueType.equals("FORMULA")) {
					String formulaName = this.resolveFormula(inoprValue.getChild("Formula"));
					keyfieldVo.setFromFieldName(formulaName);
				} else if (oprValueType.equals("VARIABLE")) {
					String toVariableName = inoprValue.getChildTextTrim("Variable");
					keyfieldVo.setFromFieldName("$V" + toVariableName + "$");
				}
				keyfieldVo.setToFieldType(operandType);
				key.add(keyfieldVo);

			}
			smallTable.setKeyList(key);
			// tablelist.add(smallTable);
			// in
			List inlist = new ArrayList();
			TableVO intableVo = new TableVO();
			for (int j = 0; j < inList.size(); j++) {

				Element infieldMap = (Element) inList.get(j);
				Element inOperand = infieldMap.getChild("Operand");
				String operandType = inOperand.getAttributeValue("type");
				Element inoprName = inOperand.getChild("OprName");
				Element inoprValue = inOperand.getChild("OprValue");
				String inoprValueType = inoprValue.getAttributeValue("type");
				FieldDefVO infieldVo = new FieldDefVO();
				if (inoprValueType.equals("FIELD")) {
					Element el = inoprValue.getChild("FieldName");
					FieldDefVO fd = this.resolveFieldName(el);
					// String inFieldName =
					// inoprValue.getChildTextTrim("FieldName");
					Element tael = el.getChild("TableName");
					TableVO tb = this.resolveTableName(tael);
					fd.setFromTable(tb);
					infieldVo.setFromFieldName("$F" + fd.getFromTable().getDataSource().getName() + "."
							+ fd.getFromTable().getName() + "." + fd.getToFieldName() + "$");
				} else if (inoprValueType.equals("PARAM")) {
					String inParamName = inoprValue.getChildTextTrim("ParamName");
					infieldVo.setFromFieldName("$P" + inParamName + "$");
				} else if (inoprValueType.equals("VALUE")) {
					String inValueName = inoprValue.getChildTextTrim("VALUE");
					infieldVo.setFromFieldName(inValueName);
				} else if (inoprValueType.equals("FORMULA")) {
					String formulaName = this.resolveFormula(inoprValue.getChild("Formula"));
					infieldVo.setFromFieldName(formulaName);
				} else if (inoprValueType.equals("VARIABLE")) {
					String toVariableName = inoprValue.getChildTextTrim("Variable");
					infieldVo.setFromFieldName("$V" + toVariableName + "$");
				}
				String tofieldName = inoprName.getChildTextTrim("Name");
				String toVariableName = inoprValue.getChildTextTrim("Variable");
				infieldVo.setToFieldName(tofieldName);
				// infieldVo.setFromFieldName(toVariableName);
				infieldVo.setToFieldType(operandType);
				inlist.add(infieldVo);
				String oprType = "";
				if (tableVO.getOprType().indexOf("I") >= 0) {
					smallTable.setInsertFieldList(inlist);
					// smallTable.setOprType("I");
					oprType += "I,";

				}
				if (tableVO.getOprType().indexOf("U") >= 0) {
					smallTable.setUpdateFieldList(inlist);
					oprType += "U,";
				}
				if (tableVO.getOprType().indexOf("D") >= 0) {
					smallTable.setDeleteFieldList(inlist);
					oprType += "D";
				}
				smallTable.setOprType(tableVO.getOprType());

			}

			tablelist.add(smallTable);
			if (tableVO.getOprType().equals("I")) {
				// out
				TableVO tv = new TableVO();
				if (outList != null && outList.size() > 0) {
					List fieList = new ArrayList();
					List variavleList = tv.getVariableList();
					for (int i = 0; i < outList.size(); i++) {
						Element fieldMap = (Element) outList.get(i);
						Element operand = fieldMap.getChild("Operand");
						String operandType = operand.getAttributeValue("type");
						Element oprName = operand.getChild("OprName");
						// String oprNameValue = oprName.getText();
						Element oprValue = operand.getChild("OprValue");
						// String toVariableName =
						// oprValue.getChildTextTrim("Variable");
						FieldDefVO fieldName = this.resolveFieldName(oprValue.getChild("FieldName"));
						tv = this.resolveTableName(oprValue.getChild("FieldName").getChild("TableName"));
						fieldName.setFromTable(tv);
						// fieldName.setToFieldType(operandType);
						// fieldName.setFromFieldName(oprNameValue);
						fieList.add(fieldName);
						tv.setRelationship(relationship);
						DataSource dsv1 = this.findSingleDataSource(dataSource, tv.getDataSource().getName());
						if (dsv1 != null) {
							tv.setDataSource(dsv1);
						}

						if (variavleList == null) {
							variavleList = new ArrayList();
						}
						variavleList.add(oprName.getChildText("Name"));

						tv.setOprType("S");
					}
					tv.setFieldList(fieList);
					tv.setVariableList(variavleList);
					if (inList.size() > 0) {
						for (int d = 0; d < inList.size(); d++) {
							Element infieldMap = (Element) inList.get(d);
							Element inOperand = infieldMap.getChild("Operand");
							String operandType = inOperand.getAttributeValue("type");
							Element inoprName = inOperand.getChild("OprName");
							Element inoprValue = inOperand.getChild("OprValue");
							FieldDefVO infieldVo = new FieldDefVO();
							String tofieldName = inoprName.getChildTextTrim("Name");
							// String toVariableName =
							// inoprValue.getChildTextTrim("Variable");
							String inoprValueType = inoprValue.getAttributeValue("type");
							TableVO second = new TableVO();
							if (inoprValueType.equals("FIELD")) {
								Element el = inoprValue.getChild("FieldName");
								FieldDefVO fd = this.resolveFieldName(el);
								// String inFieldName =
								// inoprValue.getChildTextTrim("FieldName");
								Element tael = el.getChild("TableName");
								TableVO tb = this.resolveTableName(tael);
								fd.setFromTable(tb);
								String inFieldName = inoprValue.getChildTextTrim("FieldName");
								second.setName("$F" + fd.getFromTable().getDataSource().getName() + "."
										+ fd.getFromTable().getName() + "." + fd.getToFieldName() + "$");
							} else if (inoprValueType.equals("PARAM")) {
								// TableVO second = new TableVO();
								String inParamName = inoprValue.getChildTextTrim("ParamName");
								// infieldVo.setFromFieldName("$P"+inParamName+"$");
								second.setName("$P" + inParamName + "$");
							} else if (inoprValueType.equals("VALUE")) {
								// TableVO second = new TableVO();
								String inValueName = inoprValue.getChildTextTrim("VALUE");
								second.setName(inValueName);
							} else if (inoprValueType.equals("FORMULA")) {
								// TableVO second = new TableVO();
								String formulaName = this.resolveFormula(inoprValue.getChild("Formula"));
								// infieldVo.setFromFieldName(formulaName);
								second.setName(formulaName);
							} else if (inoprValueType.equals("Variable")) {
								// TableVO second = new TableVO();
								String toVariableName = inoprValue.getChildTextTrim("Variable");
								// infieldVo.setFromFieldName("$V"+toVariableName);
								second.setName("$V" + toVariableName);
							}
							infieldVo.setToFieldName(tofieldName);
							// infieldVo.setFromFieldName(toVariableName);
							infieldVo.setToFieldType(operandType);
							// inlist.add(infieldVo);
							List valueList = new ArrayList();
							valueList.add(infieldVo);
							WhereVO whereVo = new WhereVO();
							TableVO first = new TableVO();
							first.setDataSource(tv.getDataSource());
							first.setName(tv.getName());
							first.setFieldList(valueList);
							whereVo.setFirst(first);
							// TableVO second = new TableVO();
							// second.setName(toVariableName);
							whereVo.setSecond(second);
							whereVo.setOperate("=");
							whereList.add(whereVo);

							tv.setWhereList(whereList);
						}
					}
					tablelist.add(tv);
				}

			}

		}
		return tablelist;
	}

	// --------resoveToTabel结束--------------
	/**
	 * 从数据结构文件中查找FieldDefVO
	 * 
	 * @param tb
	 * @param fd
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	public List findFieldDefVOList(TableVO tb, Document doc) throws Exception {
		List dataSetList = this.findXml(doc, tb);
		List sourceList = this.findDataSourceList(tb, doc);
		if (dataSetList != null && dataSetList.size() > 0) {
			Element table = (Element) dataSetList.get(0);
			Element fieldDef = table.getChild("FieldDef");
			tb = this.resolveFieldDef(fieldDef, tb, sourceList);
		}
		return tb.getFieldList();
	}

	/**
	 * 查找每个dataSet下的数据源

	 * 
	 * @param table
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	public List findDataSourceList(TableVO table, Document doc) throws Exception {
		Element root = doc.getRootElement();
		List list = new ArrayList();
		List dataSetList = XPath.selectNodes(root, dataSetPath + "[@name='" + table.getDataSource().getName()
				+ "']");
		List dataSource = ((Element) dataSetList.get(0)).getChild("DataSources").getChildren("DataSource");
		for (int i = 0; i < dataSource.size(); i++) {
			Element t = (Element) dataSource.get(i);
			DataSource dsv = new DataSource();
			String type = t.getAttributeValue("type");
			String dataType = t.getAttributeValue("dataType");
			String accessType = t.getAttributeValue("accessType");
			String name = t.getChildTextTrim("DSName");
			dsv.setAccessType(accessType);
			dsv.setDataType(dataType);
			dsv.setName(name);
			list.add(dsv);
		}
		return list;
	}

	// ------------解析SqlText开始-----------------

	/**
	 * @param setOptionItem
	 * @param tableVO
	 * @param dataSource
	 * @return
	 * @throws Exception
	 */
	public List resolveSqlText(Element sqlText, List outList, List dataSource, String relationship,
			TableVO tableVO) throws Exception {
		List list = new ArrayList();

		// Element sqlTextEL = setOptionItem.getChild("SqlText");
		String sqlTextData = sqlText.getChildTextTrim("CDATA").substring(1);
		String[] sqlTextArr = sqlTextData.split("#");

		for (int i = 0; i < sqlTextArr.length; i++) {
			TableVO table = new TableVO();
			String sql = sqlTextArr[i].trim();
			int indexInsert = sql.indexOf("insert");
			if (indexInsert != -1) {
				String tableName = sql.substring(sql.indexOf("into") + 4, sql.indexOf("(")).trim();
				String[] t = tableName.split("\\.");
				// 判断是否有数据源
				if (t.length == 2) {
					String dsName = t[0];
					DataSource dataSourceVo = new DataSource();
					dataSourceVo.setName(dsName);
					DataSource dsv = this.findSingleDataSource(dataSource, dsName);
					table.setDataSource(dsv);
					// table.setDataSource(dataSourceVo);
				} else {
					String dsName = table.getDataSource().getName();
					DataSource dsv = this.findSingleDataSource(dataSource, dsName);
					table.setDataSource(dsv);
				}
				table.setName(t[1].trim());
				String nameStr = sql.substring(sql.indexOf("("), sql.lastIndexOf("values"));
				String valuesStr = sql.substring(sql.indexOf("values") + 7, sql.lastIndexOf(")"));
				String[] nameStrs = nameStr.split(",");
				String[] valuesStr1 = valuesStr.split(",");
				List insertList = table.getInsertFieldList();
				if (insertList == null) {
					insertList = new ArrayList();
				}

				// table.setInsertFieldList(insertList);
				String name = nameStr.substring(1, nameStr.lastIndexOf(")"));
				String[] names = name.split(",");
				for (int j = 0; j < names.length; j++) {
					FieldDefVO fieldVo = new FieldDefVO();
					if (table.getDataSource().getAccessType().equals("DB")) {
						fieldVo.setType("0");
					}
					fieldVo.setFromFieldName(valuesStr1[j].trim());
					fieldVo.setToFieldName(names[j].trim());
					insertList.add(fieldVo);
				}
				table.setInsertFieldList(insertList);
				table.setOprType("I");
				table.setRelationship(relationship);
				// list.add(table);
				// table.setName("indexInsert");

			}
			int indexUpdate = sql.indexOf("update");
			if (indexUpdate != -1) {
				String uPtableName = sql.substring(sql.indexOf("update") + 7, sql.lastIndexOf("set")).trim();
				String[] up = uPtableName.split("\\.");
				// 判断是否有数据源
				if (up.length == 2) {
					String dsName = up[0];
					DataSource dataSourceVo = new DataSource();
					dataSourceVo.setName(dsName);
					DataSource dsv = this.findSingleDataSource(dataSource, dsName);
					table.setDataSource(dsv);
				}
				table.setName(up[1].trim());
				String str = "";
				String updataWhere = "";
				table.setOprType("U");
				List updataList = table.getUpdateFieldList();
				if (updataList == null) {
					updataList = new ArrayList();
				}
				if (sql.indexOf("where") != -1) {
					str = sql.substring(sql.indexOf("set") + 3, sql.lastIndexOf("where"));
					String[] strs = str.split(",");

					for (int j = 0; j < strs.length; j++) {
						String[] s = strs[j].split("=");
						String name = s[0].trim();// 字段名

						String value = s[1].trim();// 字段值

						FieldDefVO fieldVo = new FieldDefVO();
						if (table.getDataSource().getAccessType().equals("DB")) {
							fieldVo.setType("0");
						}
						fieldVo.setToFieldName(name);
						fieldVo.setFromFieldName(value);
						updataList.add(fieldVo);
					}
					updataWhere = sql.substring(sql.indexOf("where") + 5, sql.length());
					table.setWhere(updataWhere.trim());

				} else {
					str = sql.substring(sql.indexOf("set") + 3, sql.length());
					// System.out.println(str);
					String[] strs = str.split(",");
					// List updataList = table.getUpdateFieldList();
					for (int j = 0; j < strs.length; j++) {
						String[] s = strs[j].split("=");
						String name = s[0].trim();// 字段名

						String value = s[1].trim();// 字段值

						FieldDefVO fieldVo = new FieldDefVO();
						fieldVo.setToFieldName(name);
						fieldVo.setFromFieldName(value);
						updataList.add(fieldVo);
					}
					// list.add(table);
				}
				table.setUpdateFieldList(updataList);
				table.setRelationship(relationship);

			}
			int indexDelete = sql.indexOf("delete");
			if (indexDelete != -1) {
				String deletteName = sql.substring(sql.indexOf("from") + 6, sql.lastIndexOf("where")).trim();
				String[] t1 = deletteName.split("\\.");
				if (t1.length == 2) {
					String dsName = t1[0];
					DataSource dataSourceVo = new DataSource();
					dataSourceVo.setName(dsName);
					DataSource dsv = this.findSingleDataSource(dataSource, dsName);
					table.setDataSource(dsv);
				} else {
					String dsName = table.getDataSource().getName();
					DataSource dsv = this.findSingleDataSource(dataSource, dsName);
					table.setDataSource(dsv);
				}
				table.setName(t1[1]);

				String deleteWhere = sql.substring(sql.indexOf("where") + 5, sql.length()).trim();
				table.setWhere(deleteWhere.trim());
				table.setOprType("D");
				table.setRelationship(relationship);
			}
			int indexSelect = sql.indexOf("select");

			if (indexSelect != -1) {
				String tableName = "";
				String selectWhere = "";
				if (sql.indexOf("where") != -1) {
					tableName = sql.substring(sql.indexOf("from") + 4, sql.lastIndexOf("where")).trim();
					String[] sel = tableName.split("\\.");
					// 判断是否有数据源
					if (sel.length == 2) {
						String dsName = sel[0];
						DataSource dataSourceVo = new DataSource();
						dataSourceVo.setName(dsName);
						DataSource dsv = this.findSingleDataSource(dataSource, dsName);
						table.setDataSource(dsv);
					} else {
						String dsName = table.getDataSource().getName();
						DataSource dsv = this.findSingleDataSource(dataSource, dsName);
						table.setDataSource(dsv);
					}
					table.setName(sel[1]);
				} else {
					tableName = sql.substring(sql.indexOf("from") + 4, sql.length()).trim();
				}
				selectWhere = sql.substring(sql.indexOf("where") + 5, sql.length());
				table.setWhere(selectWhere.trim());
				String nameStr = sql.substring(sql.indexOf("select") + 6, sql.lastIndexOf("from"));

				String[] names = nameStr.split(",");
				List selectList = table.getFieldList();
				if (selectList == null) {
					selectList = new ArrayList();
				}
				for (int k = 0; k < names.length; k++) {
					FieldDefVO fieldVo = new FieldDefVO();
					// fieldVo.setFromFieldName(nameStr.trim());
					fieldVo.setToFieldName(names[k].trim());
					selectList.add(fieldVo);
				}
				table.setFieldList(selectList);
				table.setOprType("S");
				table.setRelationship(relationship);
				if (table.getOprType().equals("S")) {
					if (outList != null) {
						List vaList = new ArrayList();
						for (int k = 0; k < outList.size(); k++) {
							FieldDefVO fd = (FieldDefVO) outList.get(k);
							List fieldList = table.getFieldList();
							for (int m = 0; m < fieldList.size(); m++) {
								FieldDefVO md = (FieldDefVO) outList.get(m);
								if (fd.getToFieldName().indexOf(md.getToFieldName()) >= 0) {
									vaList.add(md);
								}

							}
							table.setVariableList(vaList);
						}
					}
					// list.add(table);
				}

			}
			list.add(table);
		}
		return list;
	}

	// ------------解析SqlText结束-----------------
	/**
	 * 从xml中查找元素

	 * 
	 * @param doc
	 * 
	 * @param tb
	 * @return
	 * @throws Exception
	 */
	public List findXml(Document doc, TableVO tb) throws Exception {

		Element root = doc.getRootElement();
		List dataSetList = XPath.selectNodes(root, dataSetPath + "[@name='" + tb.getDataSource().getName()
				+ "']/Table[@name='" + tb.getName() + "']");
		return dataSetList;
	}

	/**
	 * 解析order by
	 * 
	 * @param
	 * table---------------------------------------------------------------------
	 * @param dataSource
	 * @param groupBy
	 * @return
	 * @throws Exception
	 */
	public TableVO resolveOrderBy(Element orderBy, TableVO table, List dataSource) throws Exception {
		List fieldList = orderBy.getChildren("OrderField");
		List orderByList = table.getOrderList();
		if (orderByList == null) {
			orderByList = new ArrayList();
		}
		for (int i = 0; i < fieldList.size(); i++) {
			Element f = (Element) fieldList.get(i);
			String orderType = f.getAttributeValue("type");
			FieldDefVO fd = this.resolveFieldName(f.getChild("FieldName"));
			TableVO tb = this.resolveTableName(f.getChild("FieldName").getChild("TableName"));
			DataSource dsv = this.findSingleDataSource(dataSource, tb.getDataSource().getName());
			if (dsv != null) {
				tb.setDataSource(dsv);
			}
			fd.setFromTable(tb);
			fd.setOrderType(orderType);
			orderByList.add(fd);
		}
		table.setOrderList(orderByList);
		return table;
	}

	public TableVO resolveGroupBy(TableVO table, List dataSource, Element groupBy) throws Exception {
		List fieldList = groupBy.getChild("GroupField").getChildren("FieldName");
		List groupByList = table.getGroupByList();
		if (groupByList == null) {
			groupByList = new ArrayList();
		}
		for (int i = 0; i < fieldList.size(); i++) {
			Element f = (Element) fieldList.get(i);
			FieldDefVO fd = this.resolveFieldName(f);
			TableVO tb = this.resolveTableName(f.getChild("TableName"));
			DataSource dsv = this.findSingleDataSource(dataSource, tb.getDataSource().getName());
			if (dsv != null) {
				tb.setDataSource(dsv);
			}
			fd.setFromTable(tb);
			groupByList.add(fd);
		}
		table.setGroupByList(groupByList);
		Element condition = groupBy.getChild("Condition");
		if (condition != null) {
			table = this.resolveTableCondition(condition, table, dataSource, 1, null);
		}
		return table;
	}

	/**
	 * 处理condition
	 * 
	 * @param getTable
	 * @throws Exception
	 */
	public TableVO resolveTableCondition(Element condition, TableVO tableVO, List dataSource, int type,
			String preJoinType) throws Exception {
		if (condition != null) {
			String conditionType = condition.getAttributeValue("type");
			if (conditionType.equals("NORMAL")) {
				Element operate = condition.getChild("Operate");
				WhereVO where = this.splitOpr(operate, dataSource, tableVO);
				where.setType(conditionType);
				if (preJoinType != null && preJoinType.trim().length() > 0) {
					where.setPreviousCondition(preJoinType);
				}
				tableVO = this.addWhere(tableVO, where, type);
			} else if (conditionType.equals("FORMULA")) {
				Element formula = condition.getChild("Formula");
				String value = this.resolveFormula(formula);
				WhereVO where = new WhereVO();
				if (preJoinType != null && preJoinType.trim().length() > 0) {
					where.setPreviousCondition(preJoinType);
				}
				where.setType(conditionType);
				where.setWhere(value);
				tableVO = this.addWhere(tableVO, where, type);
			} else if (conditionType.equals("LIST")) {
				List conditionList = condition.getChildren("JoinCondition");
				for (int i = 0; i < conditionList.size(); i++) {
					Element el = (Element) conditionList.get(i);
					String joinType = el.getAttributeValue("type");
					Element cond = el.getChild("Condition");
					tableVO = this.resolveTableCondition(cond, tableVO, dataSource, type, joinType);
				}
			}
		}

		return tableVO;
	}

	/**
	 * 解决公式节点
	 * 
	 * @param formula
	 * @return
	 * @throws Exception
	 */
	public String resolveFormula(Element formula) throws Exception {
		String type = formula.getAttributeValue("type");
		String cData = formula.getChildTextTrim("CData");
		if (type.equals("AUTO")) {

		} else if (type.equals("COMMAND")) {

		} else if (type.equals("SERVER")) {

		}
		return cData.substring(1);
	}

	/**
	 * 根据type将where放到whereList或havingList
	 * 
	 * @param tableVO
	 * @param where
	 * @param type
	 * @return
	 * @throws Exception
	 */
	private TableVO addWhere(TableVO tableVO, WhereVO where, int type) throws Exception {
		if (type == 0) {
			if (tableVO.getWhereList() == null) {
				List whereList = new ArrayList();
				whereList.add(where);
				tableVO.setWhereList(whereList);
			} else {
				tableVO.getWhereList().add(where);
			}
		} else {
			if (tableVO.getHavingList() == null) {
				List haveList = new ArrayList();
				haveList.add(where);
				tableVO.setHavingList(haveList);
			} else {
				tableVO.getHavingList().add(where);
			}
		}
		return tableVO;
	}

	public DataSource findSingleDataSource(List list, String name) throws Exception {
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				DataSource dataSource = (DataSource) list.get(i);
				if (dataSource.getName().equals(name)) {
					return dataSource;
				}
			}
		}
		return null;
	}

	/**
	 * 解析condition
	 * 
	 * @param condition
	 * @param tableVO
	 * @return
	 * @throws Exception
	 */
	public TableVO resolveTableCondition(Element condition, List dataSource, TableVO tableVO)
			throws Exception {
		if (condition != null) {
			String conditionType = condition.getAttributeValue("type");
			if (conditionType.equals("NORMAL")) {
				Element operate = condition.getChild("Operate");
				WhereVO where = this.splitOpr(operate, dataSource, tableVO);
				if (tableVO.getWhereList() == null) {
					List whereList = new ArrayList();
					whereList.add(where);
					tableVO.setWhereList(whereList);
				} else {

					tableVO.getWhereList().add(where);

				}
			} else if (conditionType.equals("FORMULA")) {

			} else if (conditionType.equals("LIST")) {

			}
		}

		return tableVO;
	}

	/**
	 * 解析Opr
	 * 
	 * @param operate
	 * @param tableVO
	 * @return
	 * @throws Exception
	 */
	public WhereVO splitOpr(Element operate, List dataSource, TableVO tableVO) throws Exception {
		String operateType = operate.getAttributeValue("type");
		List operandsList = operate.getChild("Operands").getChildren();
		Element first = ((Element) operandsList.get(0));
		Element second = null;
		String oprNameType = first.getChild("OprName").getAttributeValue("type");
		if (oprNameType != null && oprNameType.trim().length() > 0) {
			if (oprNameType.equals("INDEX")) {
				String index = first.getChild("OprName").getChildTextTrim("Index");
				if (index != null) {
					if (index.equals("0")) {
						second = ((Element) operandsList.get(1));
					} else {
						try{
							first = ((Element) operandsList.get(1));
						}catch(Exception e){
							first = ((Element) operandsList.get(0));
							System.out.println("1517,ResolveXmlService.java");
						}
						second = ((Element) operandsList.get(0));
					}
				}
			} else {
				String name = first.getChildTextTrim("NAME");
				if (name == null || name.trim().length() == 0) {
					first = ((Element) operandsList.get(1));
					second = ((Element) operandsList.get(0));
				} else {
					second = ((Element) operandsList.get(1));
				}
			}
		}
		TableVO f = this.resolveOprValue(first, tableVO);
		TableVO s = this.resolveOprValue(second, tableVO);
		WhereVO where = new WhereVO();
		where.setFirst(f);
		where.setOperate(operateType);
		where.setSecond(s);
		return where;
	}

	/**
	 * 解析oprValue
	 * 
	 * @param operand
	 * @param tableVO
	 * @return table
	 * @throws Exception
	 */
	public TableVO resolveOprValue(Element operand, TableVO tableVO) throws Exception {
		String dataType = operand.getAttributeValue("type").trim();
		Element firstOprValue = operand.getChild("OprValue");
		String firstOprValueType = firstOprValue.getAttributeValue("type").trim();
		TableVO table = null;
		if (firstOprValueType != null) {
			if (firstOprValueType.equals("FIELD")) {
				Element firstField = firstOprValue.getChild("FieldName");
				FieldDefVO field = this.resolveFieldName(firstField);
				table = this.resolveTableName(firstField.getChild("TableName"));
				if (table != null) {
					// 
					List l = new ArrayList();
					List fieldList = tableVO.getFieldList();
					String t = operand.getAttributeValue("type");
					field.setToFieldType(t);
					l.add(field);
					table.setFieldList(l);
				}
			} else if (firstOprValueType.equals("VALUE")) {
				String value = firstOprValue.getChildTextTrim("Value");
				table = new TableVO();
				table.setName(value);
			} else if (firstOprValueType.equals("PARAM")) {
				String value = firstOprValue.getChildTextTrim("ParamName");
				String flagType=firstOprValue.getAttributeValue("type");
				table = new TableVO();
				table.setName("$P"+value+"$");
				table.setType("PARAM");
				
				List list = tableVO.getWhereParams();
				Param param = new Param();
				param.setKey(value);
				param.setType(dataType);
				param.setFlagType(flagType);
				param.setFlagType(firstOprValueType);
				if(null == list){
					list = new ArrayList();
				}
				list.add(param);
				tableVO.setWhereParams(list);
			}else if (firstOprValueType.equals("SYSPARAM")) {
				HttpServletRequest request = ServletActionContext.getRequest();
				String value = firstOprValue.getChildTextTrim("SysValue");
				String flagType=firstOprValue.getAttributeValue("type");
				table = new TableVO();
				
				table.setName("$P"+value+"$");
				table.setType("SYSPARAM");
				
				List list = tableVO.getWhereParams();
				Param param = new Param();
				param.setKey(value);
				param.setType(dataType);
				param.setFlagType(firstOprValueType);
				if(null == list){
					list = new ArrayList();
				}
				list.add(param);
				tableVO.setWhereParams(list);
				
//				if("SYSDATE".equals(value.toUpperCase())){
//					Date date = new Date();
//					int year = 1900 + date.getYear();
//					String ystr = year + "";
//					int month = date.getMonth() + 1;
//					String mstr = "";
//					if(month<10){
//						mstr = "0" + month;
//					}else{
//						mstr = month + "";
//					}
//					int day = date.getDate();
//					String dstr = "";
//					if(day<10){
//						dstr = "0" + day;
//					}else{
//						dstr = day + "";
//					}
//					String timestr = ystr + "-" + mstr + "-" + dstr; 
//					table.setName(timestr);
//				}else if("USERID".equals(value.toUpperCase())){
//					Object obj = request.getSession().getAttribute("userId");
//					String userid = "";
//					if(obj!=null){
//						userid = obj.toString();
//					}
//					table.setName(userid);
//				}else if("ORGID".equals(value.toUpperCase())){
//					Object obj = request.getSession().getAttribute("oid");
//					String oid = "";
//					if(obj!=null){
//						oid = obj.toString();
//					}
//					table.setName(oid);
//				}else if("ROLE".equals(value.toUpperCase())){
//					Object obj = request.getSession().getAttribute("roles");
//					String role = "";
//					if(obj!=null){
//						role = obj.toString();
//					}
//					table.setName(role);
//				}
			}else {
			}
		}

		return table;
	}

	/**
	 * 解析form table
	 * 
	 * @param fromTables
	 * @param tableVO
	 * @return tableVO
	 * @throws Exception
	 */
	/**
	 * 解析tableFrom
	 * 
	 * @param table
	 * @return
	 * @throws Exception
	 */
	public TableVO resolveTableFrom(List fromTables, TableVO tableVO, List dataSource) throws Exception {
		for (int i = 0; i < fromTables.size(); i++) {
			Element fromTable = ((Element) fromTables.get(i));
			Element tableName = fromTable.getChild("TableName");
			Element joinCon = ((Element) fromTables.get(i)).getChild("JoinCondition");
			TableVO tb = this.resolveTableName(tableName);
			tb.setOprType("S");
			DataSource dsv = this.findSingleDataSource(dataSource, tb.getDataSource().getName());
			tb.setDataSource(dsv);
			tb.setFromIndex(fromTable.getChildTextTrim("Index"));
			if (joinCon != null) {
				tb.setRelationship(joinCon.getAttributeValue("type").trim());
				Element condition = joinCon.getChild("Condition");
				tb = this.resolveTableCondition(condition, tb, dataSource, 0, null);

			}
			if (tableVO.getFromTableList() == null) {
				List list = new ArrayList();
				list.add(tb);
				tableVO.setFromTableList(list);
			} else {
				if (!tableVO.getFromTableList().contains(tb)) {
					tableVO.getFromTableList().add(tb);
				}
			}
		}
		List fromList = this.sortFromList(tableVO.getFromTableList());
		tableVO.setFromTableList(fromList);
		return tableVO;
	}

	/**
	 * 解析fromTable
	 * 
	 * @param fromList
	 * @return
	 * @throws Exception
	 */
	/**
	 * 对fromTable进行排序
	 * 
	 * @param fromList
	 * @return
	 * @throws Exception
	 */
	private List sortFromList(List fromList) throws Exception {
		List sortFromList = new ArrayList();
		List t = new ArrayList();
		for (int i = 0; i < fromList.size(); i++) {
			TableVO tb = execSortFromList(fromList, i + "");
			if (tb == null) {
				t.add(tb);
			}
			sortFromList.add(tb);
		}
		for (int i = 0; i < t.size(); i++) {
			TableVO tb = (TableVO) t.get(i);
			sortFromList.add(tb);
		}
		return sortFromList;
	}

	/**
	 * 
	 * @param fromList
	 * @param index
	 * @return
	 * @throws Exception
	 */
	private TableVO execSortFromList(List fromList, String index) throws Exception {
		for (int i = 0; i < fromList.size(); i++) {
			TableVO tb = (TableVO) fromList.get(i);
			if (tb.getFromIndex() == null || tb.getFromIndex().trim().length() == 0) {
				return null;
			}
			if (tb.getFromIndex().equals(index)) {
				return tb;
			}
		}
		return null;
	}

	/**
	 * 解析button
	 * 
	 * @param button
	 * @param buttonvo
	 * @return buttonvo
	 * @throws Exception
	 */
	public Pagination resolvePagination(Element pagination, Pagination paginationvo) throws Exception {
		if (pagination != null) {
			String visible = pagination.getAttributeValue("visible");
			if (visible.equals("true")) {

				paginationvo.setDisplay(true);

			} else if (visible.equals("false")) {

				paginationvo.setDisplay(false);
			}
		}
		return paginationvo;
	}

	public Button resolveButton(Element button, Button buttonvo) throws Exception {
		Event event = new Event();
		List eventList = new ArrayList();
		List paList = new ArrayList();
		Param param = new Param();
		if (button != null) {
			String buttonType = button.getAttributeValue("type");
			String buttonId = button.getAttributeValue("id");
			String buttonName = button.getAttributeValue("name");
			String buttonParam = button.getAttributeValue("buttonParam");
			// String cssName = button.getAttributeValue("cssName");
			// String desc = button.getAttributeValue("desc");
			String style = button.getAttributeValue("style");
			String visible = button.getAttributeValue("visible");
			Element eventElement = button.getChild("Event");
			if (eventElement != null) {
				String eventId = eventElement.getAttributeValue("eventId");
				String jsId = eventElement.getAttributeValue("JSId");
				List paramList = eventElement.getChildren("Param");
				for (int j = 0; j < paramList.size(); j++) {
					Element paramElement = (Element) paramList.get(j);
					String key = paramElement.getAttributeValue("key");
					String value = paramElement.getAttributeValue("value");
					String type = paramElement.getAttributeValue("fieldDataType");
					param.setValue(value);
					param.setKey(key);
					param.setType(type);
					paList.add(param);

				}
				event.setId(eventId);
				JSFunction jsFunction = new JSFunction();
				jsFunction.setId(jsId);
				event.setFunctions(jsFunction);
				event.setParas(paList);
				eventList.add(event);
			}
			buttonvo.setButtonId(buttonId);
			buttonvo.setButtonName(buttonName);
			buttonvo.setButtonParam(buttonParam);
			buttonvo.setVisible(visible);
			buttonvo.setType(buttonType);
			buttonvo.setStyle(style);

			buttonvo.setEvent(eventList);
		}
		return buttonvo;

	}

	public Tab resloveTab(Element page, Tab tabvo) throws Exception {
		if (page != null) {
			String tabId = page.getAttributeValue("id");
			String lazyLading = page.getAttributeValue("lazyLading");
			String sort = page.getAttributeValue("sortIndex");
			String type = page.getAttributeValue("type");
			String url = page.getAttributeValue("url");
//			String visible = page.getAttributeValue("visible");
			String title = page.getAttributeValue("title");
			String childAppID=page.getAttributeValue("childAppId");
			
			if (lazyLading != null) {
				tabvo.setLazyLoading(Boolean.valueOf(sort));
			}
			Page pageId = new Page();
			pageId.setFormId(tabId);
			tabvo.setPageId(pageId);
			if (sort != null) {
				tabvo.setSortIndex(Integer.parseInt(sort));
			}
			tabvo.setUrl(url);
			tabvo.setType(type);
			tabvo.setTitle(title);
//			if(visible!=null){
//			tabvo.setVisible(Boolean.valueOf(visible));
//			}
			tabvo.setChildAppID(childAppID) ;
		}
		return tabvo;
	}

	public Group resloveGroup(Element group, Group groupvo) throws Exception {

		if (group != null) {
			String id = group.getAttributeValue("id");
			String title = group.getAttributeValue("title");
			String sortIndex = group.getAttributeValue("sortIndex");
			String visible = group.getAttributeValue("visible");
			groupvo.setId(id);
			groupvo.setTitle(title);
			if ((sortIndex != null) && (!"".equals(sortIndex))) {
				try {
					groupvo.setSortIndex(Integer.parseInt(sortIndex));
				} catch (Exception e) {
					// TODO: handle exception
					throw new CodeGenerateException("查看页分组序号类型错误");
					
				}
				
			}
			groupvo.setVisible(Boolean.valueOf(visible));

		}
		return groupvo;
	}

	public Param resloveParam(Element param, Param paramvo) throws Exception {
		if (param != null) {
			String name = param.getAttributeValue("name");
			String type = param.getAttributeValue("type");
			String value = param.getAttributeValue("value");
			paramvo.setKey(name);
			paramvo.setType(type);
			paramvo.setValue(value);
		}
		return paramvo;
	}

	public QueryColumn resolveQueryColumn(Element queryColumn, QueryColumn queryColumnvo) throws Exception {
		List validateRuleList = new ArrayList();
		List formulaList = new ArrayList();
		int indexQuery = 0;
		if (queryColumn != null) {
			String queryId = queryColumn.getAttributeValue("id");
			String type = queryColumn.getAttributeValue("type");
			String queryName = queryColumn.getAttributeValue("name");
			String tableName = queryColumn.getAttributeValue("tableName");
			String text = queryColumn.getAttributeValue("text");
			String fieldDataType = queryColumn.getAttributeValue("fieldDataType");
			String visible = queryColumn.getAttributeValue("visible");
			String readOnly = queryColumn.getAttributeValue("readOnly");
			String cssClass = queryColumn.getAttributeValue("cssClass");
			String sortIndexString = queryColumn.getAttributeValue("sortIndex");
			String validateRuleString = queryColumn.getAttributeValue("validateRule");
			String style = queryColumn.getAttributeValue("style");
			String exclusiveLine = queryColumn.getAttributeValue("exclusiveLine");
			String operateType = queryColumn.getAttributeValue("operateType");
			String formulas = queryColumn.getAttributeValue("formula");
			String dateformat = queryColumn.getAttributeValue("dateformat");/*日期格式*/
			String dictionaryId=queryColumn.getAttributeValue("dictionaryId");
			String listSort = "";
			if(queryColumn.getAttributeValue("listsort") != null){
				listSort = queryColumn.getAttributeValue("listsort");
			}
			
			// TextColumn queryText = new TextColumn();
			// queryText.setName(text);
			int sortIndex = Integer.parseInt(sortIndexString);
			ValidateRule validateRule = new ValidateRule();
			validateRule.setName(validateRuleString);
			validateRuleList.add(validateRule);
			Formula formula = new Formula();
			formula.setName(formulas);
			formulaList.add(formula);
			queryColumnvo.setId(queryId);
			queryColumnvo.setType(type);
			queryColumnvo.setName(queryName);
			queryColumnvo.setTableName(tableName);
			queryColumnvo.setText(text);
			queryColumnvo.setDateformat(dateformat);
			queryColumnvo.setFieldDataType(fieldDataType);
			queryColumnvo.setVisible(Boolean.valueOf(visible));
			queryColumnvo.setDictionaryID(dictionaryId);
			if("".equals(operateType)||operateType==null){
				queryColumnvo.setOperateType(4);
			}else{
				queryColumnvo.setOperateType(Integer.parseInt(operateType));
			}
			queryColumnvo.setExclusiveLine(Boolean.valueOf(exclusiveLine));
			queryColumnvo.setReadOnly(readOnly);
			queryColumnvo.setCssClass(cssClass);
			queryColumnvo.setSortIndex(sortIndex);
			// queryZonevo.setText(queryText);
			queryColumnvo.setStyle(style);
			queryColumnvo.setType(type);
			queryColumnvo.setFormula(formulaList);
			queryColumnvo.setValidateRules(validateRuleList);
			queryColumnvo.setListSort(listSort);
		}
		return queryColumnvo;

	}

	/**
	 * 解析EditMode
	 * 
	 * @param editMode
	 * @param editModevo
	 * @return editModevo
	 * @throws Exception
	 */
	public EditMode resolveEditMode(Element editMode, EditMode editModevo) throws Exception {
		if (editMode != null) {
			String id = editMode.getAttributeValue("id");
			// String type = editMode.getAttributeValue("type");
			String dictionaryId = editMode.getAttributeValue("data");
			String reminder = editMode.getAttributeValue("reminder");
			String validateId = editMode.getAttributeValue("validateRule");
			//调用查找验证规则的方法：param id return name
			String validateName = componentsService.load_validate(validateId);
			String required = editMode.getAttributeValue("required");
			String minLength = editMode.getAttributeValue("minLength");
			String maxLength= editMode.getAttributeValue("maxLength");
			String maxLength2= editMode.getAttributeValue("maxLength2");
			String tdWidth= editMode.getAttributeValue("tdWidth");
			String textWidth= editMode.getAttributeValue("textWidth");
			if(minLength==null || "".equals(minLength)){
				minLength = "0";
			}
			if(maxLength==null || "".equals(maxLength)){
				maxLength = "10000";
			}
			if(maxLength2==null || "".equals(maxLength2)||Integer.parseInt(maxLength2)>Integer.parseInt(maxLength)){
				maxLength2 = maxLength;
			}
			
			String isExistDBSource = editMode.getAttributeValue("isExistDBSource");
			String isExistSQL= editMode.getAttributeValue("isExistSQL");
			String compDate  = editMode.getAttributeValue("compDate");
			String compCon = editMode.getAttributeValue("compCon");
			
			Dictionary dictionaryData = new Dictionary();
			ValidateRule validateRuleName = new ValidateRule();
			validateRuleName.setName(validateName);
			dictionaryData.setDictionaryId(dictionaryId);
			editModevo.setData(dictionaryData);
			// editModevo.setType(type);
			editModevo.setValidateRule(validateRuleName);
			editModevo.setId(id);
			editModevo.setReminder(reminder);
			editModevo.setRequired(Boolean.valueOf(required));
			editModevo.setMaxLength(maxLength);
			editModevo.setMaxLength2(maxLength2);
			editModevo.setMinLength(minLength);		
			editModevo.setIsExistSQL(isExistSQL);
			editModevo.setIsExistDBSource(isExistDBSource);
			editModevo.setCompDate(compDate);
			editModevo.setCompCon(compCon);
			editModevo.setTdWidth(tdWidth);
			editModevo.setTextWidth(textWidth);
			
		}
		return editModevo;

	}

	/**
	 * 解析列表页TitleColumn
	 * 
	 * @param textvo
	 * @throws Exception
	 */
	public TitleColumn resolveColumnText(Element columnText, TitleColumn columnvo) throws Exception {
		if (columnText != null) {
			String id = columnText.getAttributeValue("id");
			String name = columnText.getAttributeValue("name");
			String style = columnText.getAttributeValue("style");
			String sort = columnText.getAttributeValue("sort");
			String colspan = columnText.getAttributeValue("colspan");
			String align = columnText.getAttributeValue("align");
			String width = columnText.getAttributeValue("width");
			String rowspan = columnText.getAttributeValue("rowspan");
			String event = columnText.getAttributeValue("event");
			String visible = columnText.getAttributeValue("visible");
			String columnrules = columnText.getAttributeValue("columnrules");
			
			columnvo.setId(id);
			columnvo.setName(name);
			if (sort != null) {
				try {
					int sortIndex = Integer.parseInt(sort);
					columnvo.setSortIndex(sortIndex);
				} catch (Exception e) {
					throw new CodeGenerateException("sort type error!");
				}
				
				
			}
			columnvo.setStyle(style);
			columnvo.setColspan(colspan);
			columnvo.setAlign(align);
			columnvo.setWidth(width);
			columnvo.setRowspan(rowspan);
			columnvo.setEvents(event);
			columnvo.setVisible(visible);
			columnvo.setColumnrules(columnrules);
		}
		return columnvo;
	}

	/**
	 * 解析编辑页TextColumn
	 * 
	 * @param textvo
	 * @throws Exception
	 */
	public TextColumn resolveEditColumnText(Element columnText, TextColumn columnvo) throws Exception {
		if (columnText != null) {
			String id = columnText.getAttributeValue("id");
			String name = columnText.getAttributeValue("name");
			String style = columnText.getAttributeValue("style");
			String sort = columnText.getAttributeValue("sortIndex");
			String colspan = columnText.getAttributeValue("colspan");
			String align = columnText.getAttributeValue("align");
			String width = columnText.getAttributeValue("width");
			String rowspan = columnText.getAttributeValue("rowspan");
			String event = columnText.getAttributeValue("event");
			String visible = columnText.getAttributeValue("visible");
			String readOnly = columnText.getAttributeValue("readOnly");
			String groupId = columnText.getAttributeValue("groupId");
			String exclusiveLine = columnText.getAttributeValue("exclusiveLine");
			/**
			 * 得到是否设置变量的值
			 */
			String variantOrnot = columnText.getAttributeValue("variantOrnot");
			String variantType = columnText.getAttributeValue("variantType");
			String variantValue = columnText.getAttributeValue("variantValue");
			//是否是工作流字段
			String isworkflow = columnText.getAttributeValue("isworkflow");
			//组织机构树属性
			String ismultipart = columnText.getAttributeValue("ismultipart");
			String isleafcheck = columnText.getAttributeValue("isleafcheck");
			String isselforg = columnText.getAttributeValue("isselforg");
			String orgid = columnText.getAttributeValue("orgid");
			//人员列表树控件属性
			String ismultiparthuman = columnText.getAttributeValue("ismultiparthuman");
			String orgidhuman = columnText.getAttributeValue("orgidhuman");
			
			//是否是应急选择控件
			String issingletable = columnText.getAttributeValue("issingletable");//单表
			String isemergencytreesql = columnText.getAttributeValue("isemergencytreesql");//sql语句
			
			
			//是否使用列表页选择数据
			String is_listPageForvalue = columnText.getAttributeValue("is_listPageForvalue");
			String pageurl_listPage = columnText.getAttributeValue("pageurl_listPage");
			String listvalue = columnText.getAttributeValue("list_value");
			String listtext = columnText.getAttributeValue("list_text");
			columnvo.setId(id);
			columnvo.setName(name);
			if (sort != null) {
				try {
					columnvo.setSortIndex(Integer.parseInt(sort));
				} catch (Exception e) {
					throw new CodeGenerateException("sort Type error!");
				}
				
			}
			if (colspan != null) {
				columnvo.setColspan(Integer.parseInt(colspan));
			}
			if (rowspan != null) {
				columnvo.setRowspan(Integer.parseInt(rowspan));
			}
			columnvo.setStyle(style);
			columnvo.setAlign(align);
			columnvo.setWidth(width);
			columnvo.setEvents(event);
			if(""==groupId||null==groupId){
			columnvo.setGroupId("0");	
			}else{
				columnvo.setGroupId(groupId);
			}
			
			columnvo.setHidden(Boolean.valueOf(visible));
			columnvo.setReadOnly(Boolean.valueOf(readOnly));
			columnvo.setExclusiveLine(Boolean.valueOf(exclusiveLine));
			/**
			 * 设置 是否设置变量
			 */
			columnvo.setVariantOrnot(variantOrnot);
			columnvo.setVariantType(variantType);
			columnvo.setVariantValue(variantValue);
			
			/**
			 * 字段扩展属性

			 */
			columnvo.setDateformat(columnText.getAttributeValue("dateformat"));
			columnvo.setCols(columnText.getAttributeValue("cols"));
			columnvo.setRows(columnText.getAttributeValue("rows"));
			
			columnvo.setIsworkflow(isworkflow);
			columnvo.setIsmultipart(ismultipart);
			columnvo.setIsleafcheck(isleafcheck);
			columnvo.setIsselforg(isselforg);
			columnvo.setOrgid(orgid);
			
			/**
			 * 应急控件
			 */
			columnvo.setIssingletable(issingletable);
			columnvo.setIsemergencytreesql(isemergencytreesql);
			
			
			columnvo.setIsmultiparthuman(ismultiparthuman);
			columnvo.setOrgidhuman(orgidhuman);
			
			
			if("1".equals(is_listPageForvalue)){
				columnvo.setIs_listPageForvalue(new Boolean(true));
			}
			columnvo.setPageurl_listPage(pageurl_listPage);
			columnvo.setList_value(listvalue);
			columnvo.setList_text(listtext);
			
			columnvo.setListInside(columnText.getAttributeValue("listInside"));
			columnvo.setListInsideParmer(columnText.getAttributeValue("listInsideParmer"));
		}

		return columnvo;
	}

	/**
	 * 解析column
	 * 
	 * @param column
	 * @param columnvo
	 * @throws Exception
	 */
	public ListColumn resolveDataColumn(Element dataColumn, ListColumn listColumnvo) throws Exception {

		List formulaList = new ArrayList();
		if (dataColumn != null) {
			String id = dataColumn.getAttributeValue("id");
			String dataColumnName = dataColumn.getAttributeValue("name");
			String style = dataColumn.getAttributeValue("style");
			String event = dataColumn.getAttributeValue("event");
			String fieldDataType = dataColumn.getAttributeValue("fieldDataType");
			String dictionaryId = dataColumn.getAttributeValue("dictionaryId");
			String dicName = dataColumn.getAttributeValue("dictionary");
			String formulaData = dataColumn.getAttributeValue("formula");
			String primaryKey = dataColumn.getAttributeValue("primaryKey");
			//@GUOWEIXIN 用于得到该列的表名
			String tablename = dataColumn.getAttributeValue("tablename");
			if (primaryKey != null) {
				if (primaryKey.equals("true")) {
					listColumnvo.setPrimaryKey(true);
				} else if (primaryKey.equals("false")) {
					listColumnvo.setPrimaryKey(false);
				}
			}
			Dictionary dictionaryName = new Dictionary();
			dictionaryName.setDictionaryName(dicName);
			Formula formula = new Formula();
			formula.setName(formulaData);
			formulaList.add(formula);
			listColumnvo.setEvents(event);
			listColumnvo.setId(id);
			listColumnvo.setName(dataColumnName);
			listColumnvo.setStyle(style);
			listColumnvo.setDictionary(dictionaryName);
			listColumnvo.setDictionaryID(dictionaryId);
			listColumnvo.setFieldDataType(fieldDataType);
			listColumnvo.setFormula(formulaList);
			//@GUOWEIXIN
			listColumnvo.setTablename(tablename);
		}
		return listColumnvo;
	}

	/**
	 * 解析编辑页

	 * 
	 * @param editColumn
	 * @param editColumnvo
	 * @return
	 * @throws Exception
	 */
	public List resolveEditColumnText(Element textElement, EditColumn editColumnTextvo) throws Exception {
		List list = new ArrayList();
		if (textElement != null) {
			String idText = textElement.getAttributeValue("id");
			String nameText = textElement.getAttributeValue("name");
			String styleText = textElement.getAttributeValue("style");
			String eventsText = textElement.getAttributeValue("event");
			editColumnTextvo.setId(idText);
			editColumnTextvo.setName(nameText);
			editColumnTextvo.setStyle(styleText);
			editColumnTextvo.setEvents(eventsText);
		}
		return list;
	}

	public Page resolvePages(Element pageElement, Page pagevo) throws Exception {
		if (pageElement != null) {
			Element editPageElement = pageElement.getChild("EditPage");
			if (editPageElement != null) {
				String urlEdit = editPageElement.getAttributeValue("url");
				pagevo.setExtendsUrl(urlEdit);
			}
			Element viewPageElement = pageElement.getChild("ViewPage");
			if (viewPageElement != null) {
				String urlView = editPageElement.getAttributeValue("ViewPage");
				pagevo.setExtendsUrl(urlView);
			}

		}

		return pagevo;
	}

	public EditColumn resolveEditDataColumn(Element dataColumn, EditColumn editColumnvo) throws Exception {
		List formulaList = new ArrayList();
		if (dataColumn != null) {
			String id = dataColumn.getAttributeValue("id");
			String dataColumnName = dataColumn.getAttributeValue("name");
			String style = dataColumn.getAttributeValue("style");
			String event = dataColumn.getAttributeValue("event");
			String type = dataColumn.getAttributeValue("type");
			String fieldDataType = dataColumn.getAttributeValue("fieldDataType");
			String dictionaryId = dataColumn.getAttributeValue("dictionaryId");
			String dicName = dataColumn.getAttributeValue("dictionary");
			String formulaData = dataColumn.getAttributeValue("formula");
			String primaryKey = dataColumn.getAttributeValue("primaryKey");
			//@GUOWEIXIN 树控件所传的两个值。
			String isCheckBox=dataColumn.getAttributeValue("isCheckBox");
			String isSpread=dataColumn.getAttributeValue("isSpread");
			if("1".equals(isCheckBox)){
				editColumnvo.getTreeComponents().setIsCheckBox(true);
			}
			if("1".equals(isSpread)){
				editColumnvo.getTreeComponents().setOnlyLeafCheck(true);
			}
			if (primaryKey != null) {
				if (primaryKey.equals("true")) {
					editColumnvo.setPrimaryKey(true);
				} else if (primaryKey.equals("false")) {
					editColumnvo.setPrimaryKey(false);
				}
			}
			Dictionary dictionaryName = new Dictionary();
			dictionaryName.setDictionaryName(dicName);
			Formula formula = new Formula();
			formula.setName(formulaData);
			formulaList.add(formula);
			editColumnvo.setEvents(event);
			editColumnvo.setId(id);
			editColumnvo.setName(dataColumnName);
			editColumnvo.setStyle(style);
			editColumnvo.setType(type);
			editColumnvo.setDictionary(dictionaryName);
			editColumnvo.setDictionaryID(dictionaryId);
			editColumnvo.setFieldDataType(fieldDataType);
			editColumnvo.setFormula(formulaList);
		}
		return editColumnvo;

	}

	public Layout resolveLayout(Element layout, Layout layoutvo) throws Exception {
		return null;
	}

	/**
	 * 转换类型
	 * 
	 * @param type
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public String changeType(String type, String value) throws Exception {
		if (type.equalsIgnoreCase("Varchar") || type.equalsIgnoreCase("char")
				|| type.equalsIgnoreCase("boolean")) {
			return "'" + value + "'";
		} else if (type.equalsIgnoreCase("date")) {
			// if(value==null || value.trim().length()==0)
			String rePatUpdate1 = "yyyy-MM-dd";// this.judgeDateLength(value);
			return "to_date('" + value + "','" + rePatUpdate1 + "')";
		} else {
			return value;
		}
	}

	public String toWhereString(List whereList) throws Exception {
		return null;
	}

	/**
	 * 将select后的字段如果不是聚合字段放到groupList
	 * 
	 * @param fieldList
	 * @param dataSource
	 * @return
	 * @throws Exception
	 */
	public List filterAggregation(List fieldList, DataSource dataSource) throws Exception {
		List groupList = new ArrayList();
		for (int i = 0; i < fieldList.size(); i++) {
			FieldDefVO f = (FieldDefVO) fieldList.get(i);

			groupList.add(f);

		}
		return groupList;
	}

	public TableVO spellSqlQuery(TableVO table) throws Exception {
		if (table != null) {
			if (table.getType() == null) {
			} else {
				List fieldList = table.getFieldList();
				List groupList = table.getGroupByList();
				if (groupList == null) {
					groupList = new ArrayList();
				}
				if (fieldList != null) {
					int j = 0;
					for (j = 0; j < fieldList.size(); j++) {
						FieldDefVO fd = (FieldDefVO) fieldList.get(j);

					}
					if (j < fieldList.size() - 1) {
						table.setGroupByList(this.filterAggregation(fieldList, table.getDataSource()));
					}
				}
				table.setSelect("select " + table.toFieldString() + " from " + table.toFromString()
						+ " where " + table.toWhereString() + " " + table.toGroupString(1) + " "
						+ table.toHavingString() + table.toCollOrderString());

			}
			log.info("select:" + table.getSelect());

		}
		return table;
	}

	public StringBuffer spellJoinSql(TableVO table, DataSource ds) throws Exception {
		StringBuffer from = new StringBuffer("from ");
		StringBuffer select = new StringBuffer("select");
		select = this.findFieldListSql(table, select, ds);
		List fromList = table.getFromTableList();
		for (int j = 0; j < fromList.size(); j++) {
			TableVO tb = (TableVO) fromList.get(j);
			if (tb.getFromIndex() == null || tb.getFromIndex().trim().length() == 0) {
				continue;
			}
			if (tb.getFromTableList() != null && tb.getFromTableList().size() > 0) {
				from = this.componentSql(tb, from, ds);
			}
			if (j == 0) {
				from.append(tb.getName());
			} else {
				String relationship = tb.getRelationship();
				if (relationship != null && relationship.trim().length() > 0) {
					if (relationship.trim().equals("INNER")) {
						from.append(" inner join ");
					} else if (relationship.trim().equals("LEFTOUT")) {
						from.append(" left join ");
					} else if (relationship.trim().equals("RIGHTOUT")) {
						from.append(" right join ");
					}
					from.append(tb.getName());
					if (tb.getWhereList() != null && tb.getWhereList().size() > 0) {
						from.append(" on ");
					}
					from = this.findWhereListSql(tb, from, ds, "0");
				} else {
					from.append("," + tb.getName());
				}
			}
		}
//		System.out.println(select.toString() + from.toString());
		return from;
	}

	public Object[] spellStroreSql(List tableList, Document doc) throws Exception {
		Object[] sql = new Object[tableList.size()];
		if (tableList != null) {
			for (int i = 0; i < tableList.size(); i++) {
				List list = new ArrayList();
				TableVO table = (TableVO) tableList.get(i);
				table.setOprType("I");
				if (table.getOprType().equals("I")) {
					List temp = this.spellInsertSql(table, doc);
					table.setInsertFieldList(this.replaceCell(temp, table.getInsertFieldList()));
				} else if (table.getOprType().equals("U")) {
					List temp = this.spellSqlUpdate(table, doc);
					table.setUpdateFieldList(this.replaceCell(temp, table.getUpdateFieldList()));
				} else if (table.getOprType().equals("D")) {
					List temp = this.spellDeleteSql(table, doc);
					table.setDeleteFieldList(this.replaceCell(temp, table.getDeleteFieldList()));
				}
				list.add(table);
				sql[i] = list;
//				System.out.println(sql[i].toString());
			}
		}

		return sql;
	}

	public List spellSetTabelSql(List tablevoList) {
		TableVO table = new TableVO();
		if (tablevoList != null) {
			for (int i = 0; i < tablevoList.size(); i++) {
				table = (TableVO) tablevoList.get(i);
				if (table.getOprType() != null) {
					if (table.getOprType().indexOf("I") >= 0) {
						String oneSql = table.toInsertString();
						table.setInsert(oneSql);
					}
					if (table.getOprType().indexOf("U") >= 0) {
						table.setUpdate(table.toUpdateString());
					}
					if (table.getOprType().indexOf("D") >= 0) {
						String oneSql = table.toDeleteString();
						table.setDelete(oneSql);
					}
				}
			}
		}
		return tablevoList;
	}

	/**
	 * 拼写删除sql
	 * 
	 * @param table
	 * @throws Exception
	 */
	private List spellDeleteSql(TableVO table, Document doc) throws Exception {
		List sqlList = new ArrayList();
		List fromList = table.getFromTableList();
		for (int i = 0; i < fromList.size(); i++) {
			TableVO tb = (TableVO) fromList.get(i);
			if (tb.getOprType().equals("I")) {
				sqlList = this.findEvery(tb, doc, new ArrayList(), sqlList);
			} else if (tb.getOprType().equals("D")) {
				if (tb.getDataSource().getAccessType().equals("DB")) {
					sqlList.add(tb);
				} else {
					if (tb.getWhereList() != null && tb.getWhereList().size() > 0) {
						TableVO t = tb.reSet();
						List keyList = this.findKey(t.getFieldList());
						t.setFieldList(keyList);
						t.setFromTableList(null);
						TableVO temp = this.findQueryTable(t, doc);
						t.setSelect(temp.getSelect());
						t.setDataSource(temp.getDataSource());
						t.setOprType("S");
						List whereList = this.findKeyWhere(t);
						t.setWhereList(whereList);
						sqlList.add(t);
					} else {
						// 加主键条件

						if (tb.getWhere() == null || tb.getWhere().trim().length() == 0) {
							TableVO t = tb.reSet();
							List keyList = this.findKey(t.getFieldList());
							t.setFieldList(keyList);
							t.setFromTableList(null);
							List whereList = this.findKeyWhere(t);
							t.setWhereList(whereList);
							TableVO temp = this.findQueryTable(t, doc);
							t.setSelect(temp.getSelect());
							t.setDataSource(temp.getDataSource());
							t.setOprType("S");
							sqlList.add(t);
						} else {

						}
					}
					sqlList = this.findEvery(tb, doc, new ArrayList(), sqlList);
				}
			}
		}
		return sqlList;
	}

	/**
	 * 将变量转化为单元格的值

	 * 
	 * @param tableList
	 * @param fieldList
	 * @return
	 * @throws Exception
	 */
	public List replaceCell(List tableList, List fieldList) throws Exception {
		for (int j = 0; j < tableList.size(); j++) {
			TableVO table = (TableVO) tableList.get(j);
			if (table.getOprType().equals("I")) {
				List insertFieldList = table.getInsertFieldList();
				table.setInsertFieldList(this.replaceField(insertFieldList, fieldList, 0));
			} else if (table.getOprType().equals("U")) {
				List updateFieldList = table.getUpdateFieldList();
				table.setUpdateFieldList(this.replaceField(updateFieldList, fieldList, 0));
				// if(table.getSameTable()==null||table.getSameTable().trim().length()==0){
				List keyList = table.getKeyList();
				table.setKeyList(this.replaceField(keyList, fieldList, 1));
				// }
			} else if (table.getOprType().equals("D")) {
				// if(table.getSameTable()==null||table.getSameTable().trim().length()==0){
				List keyList = table.getKeyList();
				table.setKeyList(this.replaceField(keyList, fieldList, 1));
				// }
			} else if (table.getOprType().equals("S")) {
				String sql = table.getSelect();
				String where = sql.substring(sql.lastIndexOf("where"), sql.length());
				for (int i = 0; i < fieldList.size(); i++) {
					FieldDefVO fd = (FieldDefVO) fieldList.get(i);
					// CellVO cell = fd.getCell();
					// String c = "R"+cell.getRow()+"C"+cell.getCol()+"";
					String a = "＄F" + fd.getToFieldName() + "＄";
					String b = fd.getFromFieldName().replaceAll("\\u0024", "＄");
					sql = sql.replaceAll("\\u0024", "＄");
					sql = sql.replaceAll(a, b);
					// where = new
					// FormulaParser().replaceFieldCell(where,fd.getToFieldName(),fd.getFromFieldName());
					where = where.replaceAll("＄", "\\$");
				}
				table.setSelect(sql.substring(0, sql.lastIndexOf("where")) + where);
			}
			tableList.set(j, table);
		}
		return tableList;
	}

	/**
	 * 将字段替换为单元格

	 * 
	 * @param fieldList
	 * @param cellFieldList
	 * @return
	 * @throws Exception
	 */
	private List replaceField(List fieldList, List cellFieldList, int type) throws Exception {
		for (int k = 0; k < fieldList.size(); k++) {
			FieldDefVO f = (FieldDefVO) fieldList.get(k);
			for (int i = 0; i < cellFieldList.size(); i++) {
				FieldDefVO fd = (FieldDefVO) cellFieldList.get(i);
				if (type == 0) {
					String c = f.getFromFieldName().replaceAll("\\u0024", "＄");
					String a = "＄F" + fd.getToFieldName() + "＄";
					String b = fd.getFromFieldName().replaceAll("\\u0024", "＄");
					f.setFromFieldName(c.replaceAll(a, b));
					// f.setFromFieldName(new
					// FormulaParser().replaceFieldCell(f.getFromFieldName(),fd.getToFieldName(),fd.getFromFieldName()));
				} else {
					String b = fd.getFromFieldName().replaceAll("\\u0024", "＄");
					f.setFromFieldName(f.getFromFieldName().replaceAll(fd.getToFieldName(), b));
					// f.setFromFieldName(new
					// FormulaParser().replaceFieldCell(f.getFromFieldName(),fd.getToFieldName(),fd.getFromFieldName()));
				}

			}
			fieldList.set(k, f);
		}
		return fieldList;
	}

	/**
	 * 拼写添加sql
	 * 
	 * @param table
	 * @throws Exception
	 */
	private List spellInsertSql(TableVO table, Document doc) throws Exception {
		List sqlList = new ArrayList();
		List fromList = table.getFromTableList();
		for (int i = 0; i < fromList.size(); i++) {
			TableVO tb = (TableVO) fromList.get(i);
			sqlList = this.findEvery(tb, doc, new ArrayList(), sqlList);

		}
		return sqlList;
	}

	public List spellSqlUpdate(TableVO table, Document doc) throws Exception {
		List sqlList = new ArrayList();
		List formList = table.getFromTableList();
		for (int i = 0; i < formList.size(); i++) {
			TableVO tb = (TableVO) formList.get(i);
			if (tb.getOprType().equals("I")) {
				sqlList = this.findEvery(tb, doc, new ArrayList(), sqlList);
			} else if (tb.getOprType().equals("U")) {
				if (tb.getDataSource().getAccessType().equals("DB")) {
					sqlList.add(tb);
				} else {
					if (tb.getWhereList() != null && tb.getWhereList().size() > 0) {
						TableVO t = tb.reSet();
						List keyList = this.findKey(t.getFieldList());
						t.setFieldList(keyList);
						t.setFromTableList(null);
						TableVO temp = this.findQueryTable(t, doc);
						t.setSelect(temp.getSelect());
						t.setDataSource(temp.getDataSource());
						t.setOprType("S");
						sqlList.add(t);
					} else {
						// 加主键条件

						if (tb.getWhere() == null || tb.getWhere().trim().length() == 0) {
							TableVO t = tb.reSet();
							List keyList = this.findKey(t.getFieldList());
							t.setFieldList(keyList);
							t.setFromTableList(null);
							List whereList = this.findKeyWhere(t);
							t.setWhereList(whereList);
							TableVO temp = this.findQueryTable(t, doc);
							t.setSelect(temp.getSelect());
							t.setDataSource(temp.getDataSource());
							t.setOprType("S");
							sqlList.add(t);
						} else {

						}
					}
					sqlList = this.findEvery(tb, doc, new ArrayList(), sqlList);
				}
			}
		}
		return sqlList;
	}

	/**
	 * 查找where后的主键字段
	 * 
	 * @param table
	 * @param keyList
	 * @return
	 * @throws Exception
	 */
	private List findKeyWhere(TableVO tb) throws Exception {
		List whereList = new ArrayList();
		for (int j = 0; j < tb.getKeyList().size(); j++) {
			FieldDefVO fd = (FieldDefVO) tb.getKeyList().get(j);
			WhereVO where = new WhereVO();
			TableVO f = new TableVO();
			TableVO s = new TableVO();
			f.setDataSource(tb.getDataSource());
			f.setName(tb.getName());
			List fl = new ArrayList();
			fl.add(fd);
			f.setFieldList(fl);
			// s.setName(fd.getFromFieldName());
			s.setName(fd.getFromFieldName());
			where.setFirst(f);
			where.setSecond(s);
			where.setOperate("=");
			whereList.add(where);
		}
		return whereList;
	}

	/**
	 * 如果条件不是主键查询
	 * 
	 * @param tb
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	private TableVO findQueryTable(TableVO tb, Document doc) throws Exception {
		List sourceList = this.findDataSourceList(tb, doc);
		String sql = "";
		List dataSetList = this.findXml(doc, tb);
		if (dataSetList != null && dataSetList.size() > 0) {
			Element table = (Element) dataSetList.get(0);
			List whereList = tb.getWhereList();
			// tb.setWhereList(null);
			TableVO tt = tb.reSet();
			tt.setWhereList(null);
			tt = (TableVO) this.resolveTable(table, tt, sourceList, doc);
			// 如果是getTable循环取嵌套表
			tt = this.recursiveTable(tt, doc, sourceList);
			StringBuffer select = new StringBuffer(" select ");
			StringBuffer from = new StringBuffer(" from ");
			StringBuffer where = new StringBuffer(" where ");
			select.append(tb.toFieldString());
			from.append(this.spellPhysicalTable(tt, tb.getDataSource()));
			from.append(tt.getName());
			// List whereList = tb.getWhereList();
			// List mappingList = this.findWhere(tb,doc);
			// List tableList = new ArrayList();
			// tableList.add(tb);
			// tableList = this.isExistByFrom(tableList);
			List dataSource = this.findDataSource(tt);
			tb.setDataSource((DataSource) dataSource.get(0));
			if (tb.getWhere() == null || tb.getWhere().trim().length() == 0) {
				// tb.setWhereList(whereList);
				// tb.getDataSource().setType("DB");
				where.append(tb.toWhereStoreString());
			} else {
				where.append(tb.getWhere());
			}

			sql = select.toString() + " " + from.toString() + " " + where.toString();
			tb.setSelect(sql);
			// sql = sql.replaceAll("＄","\\$");
		}
		return tb;
	}

	/**
	 * 查找出现了几个数据源
	 * 
	 * @param dataSource
	 * @return
	 * @throws Exception
	 */
	public List findDataSource(TableVO tb) throws Exception {
		List fromList = tb.getFromTableList();
		List list = new ArrayList();
		for (int k = 0; k < fromList.size(); k++) {
			TableVO table = (TableVO) fromList.get(k);
			list = this.findDifferentDataSource(list, table);
		}
		return list;
	}

	/**
	 * 查找每片的数据源
	 * 
	 * @param list
	 * @param table
	 * @return
	 * @throws Exception
	 */
	private List findDifferentDataSource(List list, TableVO table) throws Exception {
		if (table.getFromTableList() != null) {
			List fromList = table.getFromTableList();
			for (int k = 0; k < fromList.size(); k++) {
				TableVO tb = (TableVO) fromList.get(k);
				list = this.findDifferentDataSource(list, tb);
			}
		} else {
			if (!list.contains(table.getDataSource()))
				list.add(table.getDataSource());
		}
		return list;
	}

	/**
	 * 根据表的类型判断执行
	 * 
	 * @param t
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	public TableVO execTable(TableVO t, Document doc, List dataSource) throws Exception {
		if (t.getType() == null) {
			return null;
		} else if (t.getDataSource().getAccessType().equals("DB")) {
			return null;
		} else if (t.getDataSource().getAccessType().equals("INSIDE")) {
			List dataSetList = this.findXml(doc, t);
			if (dataSetList != null && dataSetList.size() > 0) {
				Element tab = (Element) dataSetList.get(0);
				TableVO sunTable = (TableVO) this.resolveTable(tab, t, dataSource, doc);
				sunTable = this.recursiveTable(sunTable, doc, dataSource);
				List sunTableFromList = null;
				;
				if (t.getFromTableList() == null) {
					sunTableFromList = new ArrayList();
				} else {
					sunTableFromList = t.getFromTableList();
				}
				// sunTableFromList.add(sunTable);
				t.setFromTableList(sunTableFromList);
			}
		} else if (t.getType().equals("INSIDE")) {
			List dataSetList = this.findXml(doc, t);
			if (dataSetList != null && dataSetList.size() > 0) {
				Element tab = (Element) dataSetList.get(0);
				TableVO sunTable = (TableVO) this.resolveTable(tab, t, dataSource, doc);
				sunTable = this.recursiveTable(sunTable, doc, dataSource);
				List sunTableFromList = null;
				;
				if (t.getFromTableList() == null) {
					sunTableFromList = new ArrayList();
				} else {
					sunTableFromList = t.getFromTableList();
				}
				// sunTableFromList.add(sunTable);
				t.setFromTableList(sunTableFromList);
			}
		}// VIEW/EXCEL/TEXT/DATASET/INSIDETABLE
		return t;
	}

	/**
	 * 递归取表
	 * 
	 * @param table
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	public TableVO recursiveTable(TableVO table, Document doc, List sourceList) throws Exception {
		List fieldList = table.getFieldList();
		List fromList = table.getFromTableList();
		List whereList = table.getWhereList();
		List newFromList = new ArrayList();
		List newFieldList = new ArrayList();
		List newWhereList = new ArrayList();
		for (int i = 0; i < fromList.size(); i++) {
			TableVO t = (TableVO) fromList.get(i);
			if (t != null) {
				TableVO sunTable = this.execTable(t, doc, sourceList);
				if (sunTable == null) {
					newFromList.add(t);
				} else {
					newFromList.add(sunTable);
				}
			}
		}
		return table;
	}

	/**
	 * 查找主键
	 * 
	 * @param fieldList
	 * @return
	 * @throws Exception
	 */
	private List findKey(List fieldList) throws Exception {
		List keyList = new ArrayList();
		for (int i = 0; i < fieldList.size(); i++) {
			FieldDefVO fd = (FieldDefVO) fieldList.get(i);
			if (fd.getFromFieldIsPrimKey().equals("TRUE")) {
				keyList.add(fd);
			}
		}
		return keyList;
	}

	private List findEvery(TableVO tb, Document doc, List allList, List sqlList) throws Exception {
		allList.add(tb);
		if (tb.getOprType().equals("I")) {
			sqlList = this.resolveInsert(tb, doc, allList, sqlList);
		} else if (tb.getOprType().equals("S")) {
			if (!tb.getDataSource().getAccessType().equals("DB")) {
				TableVO temp = this.findQueryTable(tb, doc);
				tb.setSelect(temp.getSelect());
				tb.setDataSource(temp.getDataSource());
				// tb.setSelect(sql);
			} else {
				String sql = "";
				if (tb.getWhere() != null && tb.getWhere().trim().length() > 0) {
					sql = " select " + tb.toFieldString() + " from " + tb.getName() + " where "
							+ tb.getWhere();
				} else {
					sql = " select " + tb.toFieldString() + " from " + tb.getName() + " where "
							+ tb.toWhereStoreString();
				}
				tb.setSelect(sql);
			}
			sqlList.add(tb);
		} else if (tb.getOprType().equals("U")) {
			sqlList = this.resolveUpdate(tb, doc, allList, sqlList);
		} else if (tb.getOprType().equals("D")) {
			sqlList = this.resolveDelete(tb, doc, allList, sqlList);
		}
		return sqlList;
	}

	public Object[] spellSql(List tablevoList, List dataSourceList) throws Exception {
		Object[] sql = new Object[dataSourceList.size()];
		if (tablevoList != null) {
			for (int i = 0; i < tablevoList.size(); i++) {
				List list = new ArrayList();
				StringBuffer from = new StringBuffer(" ");
				TableVO foTable = (TableVO) tablevoList.get(i);
				List formList = foTable.getFromTableList();
				if (formList != null) {
					List differentList = this.splitDataSource(dataSourceList);
					if (differentList.size() > 1) {
						Map group = this.groupByDataSource(differentList, dataSourceList);
						list = this.selectCreatDataSource(list, group);
					}
					TableVO table = new TableVO();
					for (int k1 = 0; k1 < formList.size(); k1++) {
						TableVO t = (TableVO) formList.get(k1);
						if (k1 > 0) {
							from.append(", ");
						}
						from.append(this.spellPhysicalTable(t, (DataSource) differentList.get(0)));
						from.append(t.getName());
					}
					table.setPiecte(foTable.getPiecte());
					table.setDataSource((DataSource) differentList.get(0));
					table.setFieldList(foTable.getFieldList());
					table.setWhereList(foTable.getWhereList());
				}
				sql[i] = list;
			}
		}
		return sql;
	}

	/**
	 * 查找删除表的条件
	 * 
	 * @param tb
	 * @param doc
	 * @param allList
	 * @param sqlList
	 * @return
	 * @throws Exception
	 */
	private List resolveDelete(TableVO tb, Document doc, List allList, List sqlList) throws Exception {
		if (tb.getDataSource().getAccessType().equals("DB")) {
			for (int i = 0; i < allList.size() - 1; i++) {
				TableVO f = (TableVO) allList.get(i);
				if (f.getDeleteFieldList() == null) {
					for (int k = 0; k < f.getFieldList().size(); k++) {
						FieldDefVO fd = (FieldDefVO) f.getFieldList().get(k);
						TableVO s = (TableVO) allList.get(i + 1);
						s.setWhere(f.getWhere());
						for (int j = 0; j < s.getFieldList().size(); j++) {
							FieldDefVO sd = (FieldDefVO) s.getFieldList().get(j);
							s.setWhere(s.getWhere().replaceAll(sd.getToFieldName(), sd.getFromFieldName()));
						}
						allList.set(i + 1, s);
					}
				} else {
					for (int k = 0; k < f.getDeleteFieldList().size(); k++) {
						FieldDefVO fd = (FieldDefVO) f.getDeleteFieldList().get(k);
						TableVO s = (TableVO) allList.get(i + 1);
						List fieldList = new ArrayList();
						for (int j = 0; j < s.getDeleteFieldList().size(); j++) {
							FieldDefVO sd = (FieldDefVO) s.getDeleteFieldList().get(j);
							String a = "＄F" + fd.getToFieldName() + "＄";
							String b = fd.getFromFieldName().replaceAll("\\u0024", "＄");
							sd.setFromFieldName(sd.getFromFieldName().replaceAll(a, b));
							// sd.setFromFieldName(new
							// FormulaParser().replaceFieldCell(sd.getFromFieldName(),fd.getToFieldName(),fd.getFromFieldName()));
							fieldList.add(sd);
						}
						s.setDeleteFieldList(fieldList);
						allList.set(i + 1, s);
					}
					allList = this.findKeyList(allList);
				}
			}
			TableVO t1 = (TableVO) allList.get(allList.size() - 1);
			sqlList.add(t1);
		} else {
			for (int i = 0; i < tb.getFromTableList().size(); i++) {
				TableVO table = (TableVO) tb.getFromTableList().get(i);
				table.setSameTable("0");// 如果是嵌套表
				sqlList = this.findEvery(table, doc, allList, sqlList);
				allList = new ArrayList();
			}
		}
		return sqlList;
	}

	/**
	 * 查找修改字段和对应的值和条件
	 * 
	 * @param tb
	 * @param doc
	 * @param allList
	 * @param sqlList
	 * @return
	 * @throws Exception
	 */
	private List resolveUpdate(TableVO tb, Document doc, List allList, List sqlList) throws Exception {
		int t = 0;
		if (tb.getDataSource().getAccessType().equals("DB")) {
			for (int i = 0; i < allList.size() - 1; i++) {
				TableVO f = (TableVO) allList.get(i);
				for (int k = 0; k < f.getUpdateFieldList().size(); k++) {
					FieldDefVO fd = (FieldDefVO) f.getUpdateFieldList().get(k);
					TableVO s = (TableVO) allList.get(i + 1);
					List fieldList = new ArrayList();
					for (int j = 0; j < s.getUpdateFieldList().size(); j++) {
						FieldDefVO sd = (FieldDefVO) s.getUpdateFieldList().get(j);
						String a = "＄F" + fd.getToFieldName() + "＄";
						String b = fd.getFromFieldName().replaceAll("\\u0024", "＄");
						sd.setFromFieldName(sd.getFromFieldName().replaceAll(a, b));
						// sd.setFromFieldName(new
						// FormulaParser().replaceFieldCell(sd.getFromFieldName(),fd.getToFieldName(),fd.getFromFieldName()));
						fieldList.add(sd);
					}
					if (f.getWhere() != null && f.getWhere().trim().length() > 0) {
						s.setWhere(f.getWhere());
						for (int j = 0; j < s.getFieldList().size(); j++) {
							FieldDefVO sd = (FieldDefVO) s.getFieldList().get(j);
							s.setWhere(s.getWhere().replaceAll(sd.getToFieldName(), sd.getFromFieldName()));
						}
					} else {
						t++;
					}
					s.setUpdateFieldList(fieldList);
					allList.set(i + 1, s);
				}
			}
			if (t > 0) {
				allList = this.findKeyList(allList);
			}
			TableVO t1 = (TableVO) allList.get(allList.size() - 1);
			sqlList.add(t1);
		} else {
			for (int i = 0; i < tb.getFromTableList().size(); i++) {
				TableVO table = (TableVO) tb.getFromTableList().get(i);
				table.setSameTable("0");// 如果是嵌套表
				sqlList = this.findEvery(table, doc, allList, sqlList);
				allList = new ArrayList();
			}
		}
		return sqlList;
	}

	/**
	 * 
	 * @param keyList
	 * @return
	 * @throws Exception
	 */
	private List findKeyList(List allList) throws Exception {
		for (int i = 0; i < allList.size() - 1; i++) {
			TableVO f = (TableVO) allList.get(i);
			if (f.getSameTable() == null || f.getSameTable().trim().length() == 0) {
				for (int k = 0; k < f.getKeyList().size(); k++) {
					FieldDefVO fd = (FieldDefVO) f.getKeyList().get(k);
					TableVO s = (TableVO) allList.get(i + 1);
					List fieldList = new ArrayList();
					for (int j = 0; j < s.getKeyList().size(); j++) {
						FieldDefVO sd = (FieldDefVO) s.getKeyList().get(j);
						String o = fd.getToFieldName();
						String a = fd.getFromFieldName().replaceAll("\\$", "＄");
						sd.setFromFieldName(sd.getFromFieldName().replaceAll(fd.getToFieldName(), a));
						// sd.setFromFieldName(new
						// FormulaParser().replaceFieldCell(sd.getFromFieldName(),fd.getToFieldName(),fd.getFromFieldName()));
						sd.setFromFieldName(sd.getFromFieldName().replaceAll("＄", "\\$"));

						fieldList.add(sd);
					}
					s.setKeyList(fieldList);
					allList.set(i + 1, s);
				}
			}

		}
		return allList;
	}

	/**
	 * 拼接物理表

	 * 
	 * @param list
	 * @param table
	 * @return
	 * @throws Exception
	 */
	public String spellPhysicalTable(TableVO table, DataSource ds) throws Exception {
		StringBuffer sql = new StringBuffer(" ");
		sql = this.componentSql(table, sql, ds);
		return sql.toString();
	}

	/**
	 * 选择创建哪个数据源的表

	 * 
	 * @param list
	 * @param groupSql
	 * @return
	 * @throws Exception
	 */
	private List selectCreatDataSource(List list, Map groupSql) throws Exception {
		Iterator it = groupSql.keySet().iterator();
		while (it.hasNext()) {
			DataSource ds = (DataSource) it.next();
			List tableList = (List) groupSql.get(ds);
			list = this.spellDifferentSource(list, tableList);
		}
		return list;
	}

	/**
	 * 拼不同数据源的sql
	 * 
	 * @param list
	 * @param dataSourceList
	 * @return
	 * @throws Exception
	 */
	private List spellDifferentSource(List list, List dataSourceList) throws Exception {
		for (int i = 0; i < dataSourceList.size(); i++) {
			TableVO table = (TableVO) dataSourceList.get(i);
			list.add("create table" + table.getName());
			list.add("insert into ");
			list.add(" select * from  " + table.getDataSource().getName() + "." + table.getName());
		}
		return list;
	}

	/**
	 * 将表分为不同的数据源
	 * 
	 * @param dataSourceList
	 * @param tableList
	 * @return
	 * @throws Exception
	 */
	private Map groupByDataSource(List dataSourceList, List tableList) throws Exception {
		Map groupSql = new HashMap();
		for (int i = 0; i < dataSourceList.size(); i++) {
			DataSource ds = (DataSource) dataSourceList.get(i);
			List list = new ArrayList();
			for (int j = 0; j < tableList.size(); j++) {
				TableVO table = (TableVO) tableList.get(j);
				if (table.getDataSource().equals(ds)) {
					list.add(table);
				}
			}
			groupSql.put(ds, list);
		}
		return groupSql;
	}

	/**
	 * 看有几个不同的数据源
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	private List splitDataSource(List list) throws Exception {
		List differentList = new ArrayList();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TableVO table = (TableVO) list.get(i);
				int j = 0;
				if (differentList.size() == 0) {
					differentList.add(table.getDataSource());
				} else {
					for (j = 0; j < differentList.size(); j++) {
						DataSource tb = (DataSource) differentList.get(j);
						if (tb.equals(table.getDataSource())) {
							break;
						}
					}
				}
				if (j > differentList.size()) {
					differentList.add(table.getDataSource());
				}
			}
		}
		return differentList;
	}

	/**
	 * 查找添加字段和对应的值

	 * 
	 * @param tb
	 * @param doc
	 * @param allList
	 * @param sqlList
	 * @return
	 * @throws Exception
	 */
	private List resolveInsert(TableVO tb, Document doc, List allList, List sqlList) throws Exception {
		if (tb.getDataSource().getAccessType().equals("DB")) {
			for (int i = 0; i < allList.size() - 1; i++) {
				TableVO f = (TableVO) allList.get(i);
				for (int k = 0; k < f.getInsertFieldList().size(); k++) {
					FieldDefVO fd = (FieldDefVO) f.getInsertFieldList().get(k);
					TableVO s = (TableVO) allList.get(i + 1);
					List fieldList = new ArrayList();
					for (int j = 0; j < s.getInsertFieldList().size(); j++) {
						FieldDefVO sd = (FieldDefVO) s.getInsertFieldList().get(j);
						String a = "＄F" + fd.getToFieldName() + "＄";
						String b = fd.getFromFieldName().replaceAll("\\u0024", "＄");
						String c = sd.getFromFieldName().replaceAll("\\u0024", "＄");
						// sd.setFromFieldName(c.replaceAll(a,b));
						// sd.setFromFieldName(new
						// FormulaParser().replaceFieldCell(sd.getFromFieldName(),fd.getToFieldName(),fd.getFromFieldName()));
						fieldList.add(sd);
					}
					s.setInsertFieldList(fieldList);
					allList.set(i + 1, s);
				}
			}
			TableVO t1 = (TableVO) allList.get(allList.size() - 1);
			sqlList.add(t1);
		} else {
			for (int i = 0; i < tb.getFromTableList().size(); i++) {
				TableVO table = (TableVO) tb.getFromTableList().get(i);
				sqlList = this.findEvery(table, doc, allList, sqlList);
				allList = new ArrayList();
			}
		}
		return sqlList;
	}

	/**
	 * 拼接子查询

	 * 
	 * @param table
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public StringBuffer componentSql(TableVO table, StringBuffer sql, DataSource ds) throws Exception {
		StringBuffer sunFrom = new StringBuffer(" from ");
		StringBuffer sunSelect = new StringBuffer(" select ");
		StringBuffer sunWhere = new StringBuffer(" where 1=1 ");
		List fromList = table.getFromTableList();
		if( CollectionUtils.isNotEmpty(fromList) ){
			for(int i=NumberUtils.INTEGER_ZERO;i<fromList.size();i++){
				TableVO tvo = (TableVO) fromList.get(i);
				String name = tvo.getName();
				if( StringUtils.isNotBlank(name) ){
					sunWhere.append(" and ("+name+".IS_PSEUDO_DELETED<>'1' or "+name+".IS_PSEUDO_DELETED is null) ");
				}
			}
		}
		sunSelect = this.findFieldListSql(table, sunSelect, ds);
		if (table.getWhereList() != null && table.getWhereList().size() > 0) {
			sunWhere = this.findWhereListSql(table, sunWhere, ds, null);
		}
		sunFrom = findFromListSql(table, sunFrom, ds);
		if (sunWhere != null && sunWhere.toString().trim().length() >9) {
			// sunWhere.append(" ) ");
			sql.append(sunSelect.toString() + sunFrom.toString() + sunWhere.toString());
		} else {
			// sunFrom.append(" ) ");
			sql.append(sunSelect.toString() + sunFrom.toString());
		}
		return sql;
	}
	
	public StringBuffer componentEditSql(TableVO table, StringBuffer sql, DataSource ds) throws Exception {
		StringBuffer sunFrom = new StringBuffer(" from ");
		StringBuffer sunSelect = new StringBuffer(" select ");
		StringBuffer sunWhere = new StringBuffer(" where 1=1 ");
		List fromList = table.getFromTableList();
		if( CollectionUtils.isNotEmpty(fromList) ){
			for(int i=NumberUtils.INTEGER_ZERO;i<fromList.size();i++){
				TableVO tvo = (TableVO) fromList.get(i);
				String name = tvo.getName();
				if( StringUtils.isNotBlank(name) ){
					sunWhere.append(" and ("+name+".IS_PSEUDO_DELETED<>'1' or "+name+".IS_PSEUDO_DELETED is null) ");
				}
			}
		}
		sunSelect = this.findEditFieldListSql(table, sunSelect, ds);
		if (table.getWhereList() != null && table.getWhereList().size() > 0) {
			sunWhere = this.findWhereListSql(table, sunWhere, ds, null);
		}
		sunFrom = findFromListSql(table, sunFrom, ds);
		if (sunWhere != null && sunWhere.toString().trim().length() >9) {
			// sunWhere.append(" ) ");
			sql.append(sunSelect.toString() + sunFrom.toString() + sunWhere.toString());
		} else {
			// sunFrom.append(" ) ");
			sql.append(sunSelect.toString() + sunFrom.toString());
		}
		return sql;
	}

	/**
	 * 
	 * @param table
	 * @param select
	 * @return
	 * @throws Exception
	 */
	private StringBuffer findFieldListSql(TableVO table, StringBuffer select, DataSource ds) throws Exception {
		List fieldList = table.getFieldList();
		List fromList = table.getFromTableList();
		for (int j = 0; j < fieldList.size(); j++) {
			FieldDefVO fdv = (FieldDefVO) fieldList.get(j);
			boolean isExist = this.isExistFrom(fromList, fdv.getFromTable());
		
				if (!isExist) {
					select = this.componentSql(fdv.getFromTable(), select, ds);
					String tablename = fdv.getFromTable().getName() + ".";
					if (j == 0) {
						select.append(" as " + tablename + fdv.getToFieldName() + " ");
						select.append("");
					} else {
						select.append("," + " as " + tablename + fdv.getToFieldName() + " ");
						select.append(",");
					}
				} else {
					if (j == 0) {
						select.append(fdv.toFieldString());
					} else {
						select.append("," + fdv.toFieldString());
					}
				}
		
			
		}
		return select;
	}
	
	private StringBuffer findEditFieldListSql(TableVO table, StringBuffer select, DataSource ds) throws Exception {
		List fieldList = table.getFieldList();
		List fromList = table.getFromTableList();
		for (int j = 0; j < fieldList.size(); j++) {
			FieldDefVO fdv = (FieldDefVO) fieldList.get(j);
			boolean isExist = this.isExistFrom(fromList, fdv.getFromTable());
		
				if (!isExist) {
					select = this.componentEditSql(fdv.getFromTable(), select, ds);
					String tablename = fdv.getFromTable().getName() + "__";
					if (j == 0) {
						select.append(" as " + tablename + fdv.getToFieldName() + " ");
						select.append("");
					} else {
						select.append("," + " as " + tablename + fdv.getToFieldName() + " ");
						select.append(",");
					}
				} else {
					if (j == 0) {
						select.append(fdv.toEditFieldString());
					} else {
						select.append("," + fdv.toEditFieldString());
					}
				}
		
			
		}
		return select;
	}

	/**
	 * 拼接form 字段
	 * 
	 * @param table
	 * @param from
	 * @return
	 * @throws Exception
	 */
	private StringBuffer findFromListSql(TableVO table, StringBuffer from, DataSource ds) throws Exception {
		List fromList = table.getFromTableList();
		for (int j = 0; j < fromList.size(); j++) {
			TableVO tb = (TableVO) fromList.get(j);
			if (tb.getFromIndex() == null || tb.getFromIndex().trim().length() == 0) {
				continue;
			}
			if (tb.getFromTableList() != null && tb.getFromTableList().size() > 0) {
				from = this.componentSql(tb, from, ds);
			}
			if (j == 0) {
				from.append(tb.getName());
			} else {
				String relationship = tb.getRelationship();
				if (relationship != null && relationship.trim().length() > 0) {
					if (relationship.trim().equals("INNER")) {
						from.append(" inner join ");
					} else if (relationship.trim().equals("LEFTOUT")) {
						from.append(" left join ");
					} else if (relationship.trim().equals("RIGHTOUT")) {
						from.append(" right join ");
					}
					from.append(tb.getName());
					if (tb.getWhereList() != null && tb.getWhereList().size() > 0) {
						from.append(" on ");
					}
					from = this.findWhereListSql(tb, from, ds, "0");
//					System.out.println(from.toString());
				} else {
					from.append("," + tb.getName());
				}
			}
		}
		if (table.getGroupByList() != null && table.getGroupByList().size() > 0) {
			from.append(table.toGroupString(0));
		}
		if (table.getHavingList() != null && table.getHavingList().size() > 0) {
			from.append(table.toHavingString());
		}
		if (table.getOrderList() != null && table.getOrderList().size() > 0) {
			from.append(table.toOrderString());
		}
		return from;
	}

	/**
	 * 
	 * @param table
	 * @param where
	 * @return
	 * @throws Exception
	 */
	private StringBuffer findWhereListSql(TableVO table, StringBuffer where, DataSource ds, String type) throws Exception {
		List fromList = table.getFromTableList();
		List whereList = table.getWhereList();
				if (whereList != null) {
					for (int j = 0; j < whereList.size(); j++) {
						WhereVO wv = (WhereVO) whereList.get(j);
						boolean isExistFirst = true, isExistSecond = true;
						if (wv.getFirst().getDataSource() != null && wv.getSecond().getDataSource() != null) {
							isExistFirst = this.isExistFrom(fromList, wv.getFirst());
							isExistSecond = this.isExistFrom(fromList, wv.getSecond());
						} else {
							if (wv.getFirst().getDataSource() != null) {
								isExistFirst = this.isExistFrom(fromList, wv.getFirst());
							}
							if (wv.getSecond().getDataSource() != null) {
								isExistSecond = this.isExistFrom(fromList, wv.getSecond());
							}
						}
						if (isExistFirst && isExistSecond) {
							
							if (j == 0) {
								if("0".equals(type)){
								where.append(wv.toWhereString(ds));
								}else{
									where.append(" AND " + wv.toWhereString(ds));
								}
							} else {
								if(wv.getPreviousCondition()==null){
									where.append(" AND " + wv.toWhereString(ds));
								}else{
									where.append(wv.getPreviousCondition()+" " + wv.toWhereString(ds));
								}
								
							
							
								
							}
						} else {
							if (j != 0) {
								where.append(" and ");
							}
							if (isExistFirst) {
								where.append(wv.toFirstString(ds));
								;
							} else {
								where = this.componentSql(wv.getFirst(), where, ds);
							}
							where.append(" " + wv.getOperate() + " ");
							if (isExistSecond) {
								where.append(wv.toSecondString(ds));
							} else {
								where = this.componentSql(wv.getSecond(), where, ds);
							}
						}
					}
				}else if(whereList==null||whereList.size()==0){
					if (fromList != null) {
						 for(int i=1;i<fromList.size();i++){
								TableVO ftablevo = (TableVO) fromList.get(i);
								List formwhereList = ftablevo.getWhereList();
								if (formwhereList != null) {
									for (int j = 0; j < formwhereList.size(); j++) {
										WhereVO wv = (WhereVO) formwhereList.get(j);
										boolean isExistFirst = true, isExistSecond = true;
										if (wv.getFirst().getDataSource() != null && wv.getSecond().getDataSource() != null) {
											isExistFirst = this.isExistFrom(fromList, wv.getFirst());
											isExistSecond = this.isExistFrom(fromList, wv.getSecond());
										} else {
											if (wv.getFirst().getDataSource() != null) {
												isExistFirst = this.isExistFrom(fromList, wv.getFirst());
											}
											if (wv.getSecond().getDataSource() != null) {
												isExistSecond = this.isExistFrom(fromList, wv.getSecond());
											}
										}
										if (isExistFirst && isExistSecond) {
											if (j == 0) {
												where.append(wv.toWhereString(ds));
											} else {
												where.append(" and " + wv.toWhereString(ds));
											}
										} else {
											if (j != 0) {
												where.append(" and ");
											}
											if (isExistFirst) {
												where.append(wv.toFirstString(ds));
												;
											} else {
												where = this.componentSql(wv.getFirst(), where, ds);
											}
											where.append(" " + wv.getOperate() + " ");
											if (isExistSecond) {
												where.append(wv.toSecondString(ds));
											} else {
												where = this.componentSql(wv.getSecond(), where, ds);
											}
										}
									}
								}
							
							
						 }
						
					}
				}
				return where;
	}

	/**
	 * 判断fromList是否出现此表
	 * 
	 * @param fromList
	 * @param table
	 * @return
	 * @throws Exception
	 */
	private boolean isExistFrom(List fromList, TableVO table) throws Exception {
		if (fromList == null) {
			return true;
		}
		for (int i = 0; i < fromList.size(); i++) {
			TableVO t = (TableVO) fromList.get(i);
			boolean flag=true;
			try {
				 flag=table.equals(t);
			} catch (Exception e) {
				// TODO: handle exception
			}
			boolean flag_tName=t.getName().endsWith(table.getName());
			if ((flag || flag_tName) == true) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 转换类型
	 * 
	 * @param type
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public String changeOtherType(String type, String value) throws Exception {
		if (type.equalsIgnoreCase("Varchar") || type.equalsIgnoreCase("char")
				|| type.equalsIgnoreCase("boolean")) {
			return "''" + value + "''";
		} else if (type.equalsIgnoreCase("date")) {
			// if(value==null || value.trim().length()==0)
			String rePatUpdate1 = "yyyy-MM-dd";// this.judgeDateLength(value);
			return "to_date(''" + value + "'','" + rePatUpdate1 + "')";
		} else {
			return value;
		}
	}

	/**
	 * 转换类型
	 * 
	 * @param type
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public String changeValueType(String type, String value) throws Exception {
		if (type.equalsIgnoreCase("Varchar") || type.equalsIgnoreCase("char")
				|| type.equalsIgnoreCase("boolean")) {
			return "'" + value + "'";
		} else if (type.equalsIgnoreCase("date")) {
			// if(value==null || value.trim().length()==0)
			String rePatUpdate1 = "yyyy-MM-dd";// this.judgeDateLength(value);
			return "to_date('" + value + "','" + rePatUpdate1 + "')";
		} else {
			return value;
		}
	}

	public TextColumn resolveViewColumnText(Element viewColumnText, TextColumn viewColumnvo) throws Exception {
		if (viewColumnText != null) {
			String id = viewColumnText.getAttributeValue("id");
			String name = viewColumnText.getAttributeValue("name");
			String style = viewColumnText.getAttributeValue("style");
			String sort = viewColumnText.getAttributeValue("sortIndex");
			String colspan = viewColumnText.getAttributeValue("colspan");
			String align = viewColumnText.getAttributeValue("align");
			String width = viewColumnText.getAttributeValue("width");
			String rowspan = viewColumnText.getAttributeValue("rowspan");
			String event = viewColumnText.getAttributeValue("event");
			String visible = viewColumnText.getAttributeValue("visible");
			String groupId = viewColumnText.getAttributeValue("groupId");
			String exclusiveLine = viewColumnText.getAttributeValue("exclusiveLine");
			String readOnly = viewColumnText.getAttributeValue("readOnly");
			
			
			viewColumnvo.setId(id);
			viewColumnvo.setName(name);
			if (readOnly!=null) {
				viewColumnvo.setReadOnly(Boolean.valueOf(readOnly));
			}
			if (sort != null) {
				viewColumnvo.setSortIndex(Integer.parseInt(sort));
			}
			if (colspan != null) {
				viewColumnvo.setColspan(Integer.parseInt(colspan));
			}
			if (rowspan != null) {
				viewColumnvo.setRowspan(Integer.parseInt(rowspan));
			}
			viewColumnvo.setStyle(style);
			viewColumnvo.setAlign(align);
			viewColumnvo.setWidth(width);
			viewColumnvo.setEvents(event);
			if("".equals(groupId)||groupId==null){
				viewColumnvo.setGroupId("0");
			}else{
				viewColumnvo.setGroupId(groupId);
			}
			
			viewColumnvo.setVisible(Boolean.valueOf(visible));
			viewColumnvo.setExclusiveLine(Boolean.valueOf(exclusiveLine));
		}
		return viewColumnvo;
	}

	public ViewColumn resolveViewDataColumn(Element viewDataColumn, ViewColumn viewDataColumnvo)
			throws Exception {
		List formulaList = new ArrayList();
		if (viewDataColumn != null) {
			String id = viewDataColumn.getAttributeValue("id");
			String dataColumnName = viewDataColumn.getAttributeValue("name");
			String style = viewDataColumn.getAttributeValue("style");
			String event = viewDataColumn.getAttributeValue("event");
			String type = viewDataColumn.getAttributeValue("type");
			String fieldDataType = viewDataColumn.getAttributeValue("fieldDataType");
			String dictionaryId = viewDataColumn.getAttributeValue("dictionaryId");
			String dicName = viewDataColumn.getAttributeValue("dictionary");
			String formulaData = viewDataColumn.getAttributeValue("formula");
			String primaryKey = viewDataColumn.getAttributeValue("primaryKey");
			String tablename=viewDataColumn.getAttributeValue("tablename");
			if (primaryKey != null) {
				if (primaryKey.equals("true")) {
					viewDataColumnvo.setPrimaryKey(true);
				} else if (primaryKey.equals("false")) {
					viewDataColumnvo.setPrimaryKey(false);
				}
			}
			Dictionary dictionaryName = new Dictionary();
			dictionaryName.setDictionaryName(dicName);
			Formula formula = new Formula();
			formula.setName(formulaData);
			formulaList.add(formula);
			viewDataColumnvo.setEvents(event);
			viewDataColumnvo.setId(id);
			viewDataColumnvo.setName(dataColumnName);
			viewDataColumnvo.setStyle(style);
			viewDataColumnvo.setType(type);
			viewDataColumnvo.setDictionary(dictionaryName);
			viewDataColumnvo.setDictionaryID(dictionaryId);
			viewDataColumnvo.setFieldDataType(fieldDataType);
			viewDataColumnvo.setFormula(formulaList);
			viewDataColumnvo.setTablename(tablename);
		}
		return viewDataColumnvo;
	}

	public List resolveEditColumnInput(Element inputElement, EditColumn editColumnInputvo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] spitFindSql(String selectSql) {
		int count = 0;
		String a[] = null;// 存儲所有条件

		String dynamicString[] = null;// 存储所有变量


		if (selectSql.indexOf("1=1") < 0) {
			return null;
		}
		selectSql = selectSql.substring(selectSql.indexOf("1=1") + 3);
		selectSql = selectSql.substring(selectSql.indexOf("and") + 3);
		String array[] = selectSql.split("and");

		String temString[] = null;
		a = new String[array.length];
		for (int i = 0; i < array.length; i++) {

			temString = array[i].split("=");
			if (temString[1].indexOf("$") >= 0) {
				count++;
			}
		}
		dynamicString = new String[count];
		count = 0;
		for (int i = 0; i < array.length; i++) {
			temString = array[i].split("=");
			if (temString[1].indexOf("$") >= 0) {
				dynamicString[count++] = temString[0].substring(temString[0].indexOf(".") + 1);
			}
		}

		return dynamicString;
	}
	/**
	 * 事件规则
	 */
	public EditRulesEngin resolveEditRulesEngin(Element rulesEngin,
			EditRulesEngin editRulesEngin) throws Exception {
		if (rulesEngin != null) {
			String rulesService = rulesEngin.getAttributeValue("rulesService");
			String eventTypes = rulesEngin.getAttributeValue("eventTypes");
			String rulesRowsParmer = rulesEngin.getAttributeValue("rulesRowsParmer");
			String xxx[] = null;
			if(eventTypes!=null && !"".equals(eventTypes)){
				xxx = eventTypes.split(",");
			}
			rulesService = rulesService.replaceAll("\'", "%22");
			editRulesEngin.setEventTypes(xxx);
			editRulesEngin.setRulesRowsParmer(rulesRowsParmer);
			editRulesEngin.setRulesService(rulesService);
			editRulesEngin.setIs_jilian(rulesEngin.getAttributeValue("is_jilian"));
			editRulesEngin.setJilian_column(rulesEngin.getAttributeValue("jilian_column"));
			editRulesEngin.setJilian_column_dictionaryId(rulesEngin.getAttributeValue("jilian_column_dictionaryId"));
		}
		return editRulesEngin;
	}
	
	public ColumnRoles resolveColumnRoles(Element rolesEle, ColumnRoles roles) throws Exception {
		if( roles != null ){
			roles.setIsCustomRole(rolesEle.getAttributeValue("isCustomRole"));
			roles.setIsCustomRoleRead(rolesEle.getAttributeValue("isCustomRoleRead"));
			roles.setIsCustomRoleReadId(rolesEle.getAttributeValue("isCustomRoleReadId"));
			roles.setIsCustomRoleReadName(rolesEle.getAttributeValue("isCustomRoleReadName"));
			roles.setIsCustomRoleWrite(rolesEle.getAttributeValue("isCustomRoleWrite"));
			roles.setIsCustomRoleWriteId(rolesEle.getAttributeValue("isCustomRoleWriteId"));
			roles.setIsCustomRoleWriteName(rolesEle.getAttributeValue("isCustomRoleWriteName"));
		}
		return roles;
	}
}