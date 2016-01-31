package com.zxt.compplatform.formengine.service;

import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import com.zxt.compplatform.datasource.entity.DataSource;
import com.zxt.compplatform.formengine.entity.dataset.FieldDefVO;
import com.zxt.compplatform.formengine.entity.dataset.TableVO;
import com.zxt.compplatform.formengine.entity.dataset.WhereVO;
import com.zxt.compplatform.formengine.entity.view.Button;
import com.zxt.compplatform.formengine.entity.view.ColumnRoles;
import com.zxt.compplatform.formengine.entity.view.EditColumn;
import com.zxt.compplatform.formengine.entity.view.EditMode;
import com.zxt.compplatform.formengine.entity.view.EditRulesEngin;
import com.zxt.compplatform.formengine.entity.view.Group;
import com.zxt.compplatform.formengine.entity.view.ListColumn;
import com.zxt.compplatform.formengine.entity.view.Pagination;
import com.zxt.compplatform.formengine.entity.view.Param;
import com.zxt.compplatform.formengine.entity.view.QueryColumn;
import com.zxt.compplatform.formengine.entity.view.Tab;
import com.zxt.compplatform.formengine.entity.view.TextColumn;
import com.zxt.compplatform.formengine.entity.view.TitleColumn;
import com.zxt.compplatform.formengine.entity.view.ViewColumn;

/**
 * xml解析
 * 
 * @author 007
 */
public interface IResolveXmlService {

	/**
	 * 解析数据源
	 * 
	 * @param dataSourceElement
	 * @param dataSourcevo
	 * @return
	 * @throws Exception
	 */
	public List resolveDataSource(Element dataSourceElement,
			DataSource dataSourcevo) throws Exception;

	/**
	 * 解析Table
	 * 
	 * @param table
	 * @param tableVO
	 * @throws Exception
	 */
	public TableVO resolveTable(Element table, TableVO tableVO,
			List dataSource, Document doc) throws Exception;

	/**
	 * 解析FieldDef
	 * 
	 * @param fieldDef
	 * @param table
	 * @throws Exception
	 */
	public TableVO resolveFieldDef(Element fieldDef, TableVO table,
			List dataSource) throws Exception;

	/**
	 * 解析FieldName
	 * 
	 * @param fieldName
	 * @throws Exception
	 */
	public FieldDefVO resolveFieldName(Element fieldName) throws Exception;

	/**
	 * 解析TalbeName
	 * 
	 * @param tableName
	 * @throws Exception
	 */
	public TableVO resolveTableName(Element tableName) throws Exception;

	/**
	 * 解析getTable
	 * 
	 * @param getTable
	 * @param tableVO
	 * @throws Exception
	 */
	public TableVO resolveGetTable(Element getTable, TableVO tableVO,
			List dataSource) throws Exception;

	/**
	 * 解析Condition条件
	 * 
	 * @param condition
	 * @param tableVO
	 * @throws Exception
	 */
	public TableVO resolveTableCondition(Element condition, TableVO tableVO,
			List dataSource, int type, String preJoinType) throws Exception;

	/**
	 * 解析oprValue
	 * 
	 * @param operand
	 * @param tableVO
	 * @throws Exception
	 */
	public WhereVO splitOpr(Element operate, List dataSource, TableVO tableVO)
			throws Exception;

	public TableVO resolveOprValue(Element operand, TableVO tableVO)
			throws Exception;

	/**
	 * 解析fromTable
	 * 
	 * @param fromTables
	 * @param tableVO
	 * @throws Exception
	 */
	public TableVO resolveTableFrom(List fromTables, TableVO tableVO,
			List dataSource) throws Exception;

	/**
	 * 解析Group
	 * 
	 * @param group
	 * @param groupvo
	 * @return
	 * @throws Exception
	 */
	public Group resloveGroup(Element group, Group groupvo) throws Exception;

	/**
	 * 解析参数
	 * 
	 * @param param
	 * @param paramvo
	 * @return
	 * @throws Exception
	 */
	public Param resloveParam(Element param, Param paramvo) throws Exception;

	/**
	 * 解析参数文本
	 * 
	 * @param columnText
	 * @param columnvo
	 * @return
	 * @throws Exception
	 */
	public TextColumn resolveViewColumnText(Element columnText,
			TextColumn columnvo) throws Exception;

	public ViewColumn resolveViewDataColumn(Element viewDataColumn,
			ViewColumn viewDataColumnvo) throws Exception;

	/**
	 * 解析Tab
	 * 
	 * @param page
	 * @param tabvo
	 * @return
	 * @throws Exception
	 */
	public Tab resloveTab(Element page, Tab tabvo) throws Exception;

	/**
	 * 解析pagination
	 * 
	 * @param pagination
	 * @param paginationvo
	 * @return
	 * @throws Exception
	 */
	public Pagination resolvePagination(Element pagination,
			Pagination paginationvo) throws Exception;

	/**
	 * 解析button
	 * 
	 * @param button
	 * @param buttonvo
	 * @throws Exception
	 */
	public Button resolveButton(Element button, Button buttonvo)
			throws Exception;

	/**
	 * 解析queryZone
	 * 
	 * @param queryColumns
	 * @param queryZonevo
	 * @throws Exception
	 */
	public QueryColumn resolveQueryColumn(Element queryColumn,
			QueryColumn queryColumnvo) throws Exception;

	/**
	 * 解析editMode
	 * 
	 * @param editMode
	 * @param editModevo
	 * @throws Exception
	 */
	public EditMode resolveEditMode(Element editMode, EditMode editModevo)
			throws Exception;

	/**
	 * 解析TitleColumn
	 * 
	 * @param titleColumn
	 * @param titleColumnvo
	 * @throws Exception
	 */
	public ListColumn resolveDataColumn(Element dataColumn,
			ListColumn lsitColumnvo) throws Exception;

	/**
	 * 解析编辑列
	 * 
	 * @param dataColumn
	 * @param lsitColumnvo
	 * @return
	 * @throws Exception
	 */
	public EditColumn resolveEditDataColumn(Element dataColumn,
			EditColumn lsitColumnvo) throws Exception;

	/**
	 * 解析ListColumn
	 * 
	 * @param listColumn
	 * @param lsitColumnvo
	 * @throws Exception
	 */

	public TitleColumn resolveColumnText(Element columnText,
			TitleColumn columnvo) throws Exception;

	/**
	 * @param columnText
	 * @param columnvo
	 * @return
	 * @throws Exception
	 */
	public TextColumn resolveEditColumnText(Element columnText,
			TextColumn columnvo) throws Exception;

	/**
	 * 解析编辑页
	 * 
	 * @param editColumn
	 * @param editColumnvo
	 * @throws Exception
	 */
	public List resolveEditColumnText(Element textElement,
			EditColumn editColumnTextvo) throws Exception;

	/**
	 * @param inputElement
	 * @param editColumnInputvo
	 * @throws Exception
	 */
	public List resolveEditColumnInput(Element inputElement,
			EditColumn editColumnInputvo) throws Exception;

	/**
	 * 拼接sql查询语句
	 * 
	 * @param tableList
	 * @return
	 * @throws Exception
	 */
	public TableVO spellSqlQuery(TableVO table) throws Exception;

	/**
	 * 拼接where
	 * 
	 * @param whereList
	 * @return
	 * @throws Exception
	 */
	public String toWhereString(List whereList) throws Exception;

	/**
	 * 解析规则
	 * 
	 * @param rulesEngin
	 * @param editRulesEngin
	 * @return
	 * @throws Exception
	 */
	public EditRulesEngin resolveEditRulesEngin(Element rulesEngin,
			EditRulesEngin editRulesEngin) throws Exception;

	/**
	 * 解析column角色
	 * 
	 * @param rolesEle
	 * @param roles
	 * @return
	 * @throws Exception
	 */
	public ColumnRoles resolveColumnRoles(Element rolesEle, ColumnRoles roles)
			throws Exception;
}
