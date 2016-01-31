package com.zxt.compplatform.formengine.entity.view;

import java.util.List;
import java.util.Map;

import com.zxt.compplatform.datasource.entity.DataSource;
import com.zxt.framework.common.entity.BasicEntity;

/**
 * 基础页面实体
 * 
 * @author 007
 */
public class BasePage extends BasicEntity {
	/**
	 * basePageId
	 */
	private String id;
	/**
	 * 页面标识 取值：listPage/editPage/viewPage
	 */
	private String flag;
	/**
	 * 页面title
	 */
	private String title;
	/**
	 * 页面，DATAGRID看其是否显示多条
	 */
	private String isshowsingleSelect;
	/**
	 * 是否对当前表单操作 加入日志记录 ENG_SYSTEM_ACTIONLOG
	 */
	private String isSystemActionlog;
	
	
	/**
	 * 多标签页
	 */
	private List tabs;
	/**
	 * 参数
	 */
	private List params;
	/**
	 * 数据源
	 */
	private DataSource dataSource;

	/**
	 * 主键列
	 */
	private Map objectPkColumn;
	/**
	 * dataSet对象
	 */
	private List table;
	/**
	 * 列数据
	 */
	private List columnDatas;
	/**
	 * sql 信息
	 */
	private String sql;
	/**
	 * 列表页标题列
	 */
	private List TitleColumn;
	/**
	 * 列表数页据列
	 */
	private List ListColumn;
	/**
	 * 编辑页标题
	 */
	private List TextColumn;
	/**
	 * 编辑页数据列
	 */
	private List EditColumn;
	/**
	 * 存放列表页列标题和数据列的的需要信息
	 */
	private List fields;
	/**
	 * 是否使用多标签
	 */
	private Boolean isUseTab = new Boolean(false);
	/**
	 * 实体映射 数据对象-- 数据列集合
	 */
	private Map objectColumn;
	/**
	 * 实体映射 数据对象-- 数据列集合
	 */
	private String[] selectSqlParam;
	/**
	 * 查询语句参数
	 */

	private Boolean isGroupVisible = new Boolean(false);
	/**
	 * 编辑页，查看页 是否显示分组，默认不显示
	 */

	private String selfDefineWidth;
	private String selfDefineHeight;

	/**
	 * 数据对象列操作
	 */
	private Map objectColumnOperate;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List getTabs() {
		return tabs;
	}

	public void setTabs(List tabs) {
		this.tabs = tabs;
	}

	public List getParams() {
		return params;
	}

	public void setParams(List params) {
		this.params = params;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Map getObjectColumn() {
		return objectColumn;
	}

	public void setObjectColumn(Map objectColumn) {
		this.objectColumn = objectColumn;
	}

	public Map getObjectColumnOperate() {
		return objectColumnOperate;
	}

	public void setObjectColumnOperate(Map objectColumnOperate) {
		this.objectColumnOperate = objectColumnOperate;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Map getObjectPkColumn() {
		return objectPkColumn;
	}

	public void setObjectPkColumn(Map objectPkColumn) {
		this.objectPkColumn = objectPkColumn;
	}

	public List getTable() {
		return table;
	}

	public void setTable(List table) {
		this.table = table;
	}

	public List getColumnDatas() {
		return columnDatas;
	}

	public void setColumnDatas(List columnDatas) {
		this.columnDatas = columnDatas;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List getTitleColumn() {
		return TitleColumn;
	}

	public void setTitleColumn(List titleColumn) {
		TitleColumn = titleColumn;
	}

	public List getListColumn() {
		return ListColumn;
	}

	public void setListColumn(List listColumn) {
		ListColumn = listColumn;
	}

	public List getFields() {
		return fields;
	}

	public void setFields(List fields) {
		this.fields = fields;
	}

	public List getTextColumn() {
		return TextColumn;
	}

	public void setTextColumn(List textColumn) {
		TextColumn = textColumn;
	}

	public List getEditColumn() {
		return EditColumn;
	}

	public void setEditColumn(List editColumn) {
		EditColumn = editColumn;
	}

	public Boolean getIsUseTab() {
		return isUseTab;
	}

	public void setIsUseTab(Boolean isUseTab) {
		this.isUseTab = isUseTab;
	}

	public String[] getSelectSqlParam() {
		return selectSqlParam;
	}

	public void setSelectSqlParam(String[] selectSqlParam) {
		this.selectSqlParam = selectSqlParam;
	}

	public Boolean getIsGroupVisible() {
		return isGroupVisible;
	}

	public void setIsGroupVisible(Boolean isGroupVisible) {
		this.isGroupVisible = isGroupVisible;
	}

	public String getSelfDefineWidth() {
		return selfDefineWidth;
	}

	public void setSelfDefineWidth(String selfDefineWidth) {
		this.selfDefineWidth = selfDefineWidth;
	}

	public String getSelfDefineHeight() {
		return selfDefineHeight;
	}

	public void setSelfDefineHeight(String selfDefineHeight) {
		this.selfDefineHeight = selfDefineHeight;
	}

	public String getIsshowsingleSelect() {
		return isshowsingleSelect;
	}

	public void setIsshowsingleSelect(String isshowsingleSelect) {
		this.isshowsingleSelect = isshowsingleSelect;
	}

	public String getIsSystemActionlog() {
		return isSystemActionlog;
	}

	public void setIsSystemActionlog(String isSystemActionlog) {
		this.isSystemActionlog = isSystemActionlog;
	}

}
