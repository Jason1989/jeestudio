package com.zxt.compplatform.formengine.entity.view;

import java.util.List;
import java.util.Map;

/**
 * 列表页设置
 * 
 * @author 007
 */
public class ListPage extends BasePage {

	/**
	 * 此 处用于得到list-page.jsp中的 表单列 request.setAttribute("listPageRander",RandomGUID.geneGuid());//生成表单列ID
	 */
	private String listPageRander;
	/**
	 * 是否显示分页
	 */
	private boolean canShowPagination;
	/**
	 * 是否一直显示查询区域
	 */
	private boolean canShowQuery;
	/**
	 * 是否有批量操作(复选框)
	 */
	private boolean canBatch;
	/**
	 * 列表查询区
	 */
	private List queryZone;
	/**
	 * 列表事件
	 */
	private List EditMode;
	/**
	 * 列表样式
	 */
	private List css;
	/**
	 * 自定义样式
	 */
	private String coustomCss;

	/**
	 * 批量操作按钮
	 */
	private List gridButton;
	/**
	 * 批量操作按钮自定义
	 */
	private List gridButton2;
	/**
	 * 行操作列表
	 */
	private List rowButton;
	/**
	 * 行操作列自定义
	 */
	private List rowButton2;
	/**
	 * @GUOWEIXIN 用于设置 选择添加页的ID属性值
	 */
	private String addPageAttribute;
	/**
	 * 查看页
	 */

	/**
	 * 编辑页
	 */
	private EditPage editPage;
	/**
	 * 查看页
	 */
	private ViewPage viewPage;
	/**
	 * 复制页
	 */
	private CopyPage copyPage;
	/**
	 * 列表页列
	 */
	private List gridColumns;
	/**
	 * 列表标题列
	 */
	// private List TitleColumn ;
	// /**已经迁到BasePage中
	// * 列表数据列
	// */
	// private List ListColumn ;
	/**
	 * 查询列
	 */
	private List queryColumns;

	private Map queryColumnsTable;
	/**
	 * 删除语句
	 */
	private String deleteSql;
	/**
	 * 修改参数
	 */
	private String updateParamSql;

	// 
	/**
	 * 修改参数 表名 -- 字段列表
	 */
	private Map updateParam;

	/**
	 * 删除参数 表名 -- 字段列表
	 */
	private List deleteParams;

	/**
	 * 删除参数列表
	 */
	private Map deleteParam;

	// private List fields;
	/**
	 * 
	 */
	private String keyTable;

	/**
	 * 是否显示 操作列
	 * 
	 * @return
	 */
	/**
	 * 列表页参数
	 */
	private String[] selectSqlParam;

	/**
	 * 操作列排序方式
	 */
	private String opeColumnAlign = "end";

	/**
	 * 列表页参数
	 */
	private List listPageParams;

	private String isShowOperator = "false";
	/**
	 * 组合查询区域面板设置
	 */
	private QueryZone queryZonePanel;
	/**
	 * 列表页自适应宽度百分比
	 * 
	 * @return
	 */
	private String atuoWidthPen;
	/**
	 * 列表页的请求参数
	 */
	private String gridUrl;
	/**
	 * 组合查询区域高度
	 */
	private String queryZoneHeight;//
	/**
	 * 主对象id
	 */
	private String parentAppId;

	private String isPseudoDeleted;

	/**
	 * 初始sql
	 */
	private String isOriginalSql;
	/**
	 * 初始上下文
	 */
	private String isOriginalSqlContext;
	/**
	 * 初始sql参数
	 */
	private String isOriginalSqlParam;

	public String getParentAppId() {
		return parentAppId;
	}

	public void setParentAppId(String parentAppId) {
		this.parentAppId = parentAppId;
	}

	public String getGridUrl() {
		return gridUrl;
	}

	public String getIsPseudoDeleted() {
		return isPseudoDeleted;
	}

	public void setIsPseudoDeleted(String isPseudoDeleted) {
		this.isPseudoDeleted = isPseudoDeleted;
	}

	public void setGridUrl(String gridUrl) {
		this.gridUrl = gridUrl;
	}

	public QueryZone getQueryZonePanel() {
		if (queryZone != null) {
			if (queryZone.size() != 0) {
				queryZonePanel = (QueryZone) queryZone.get(0);
			}
		}

		return queryZonePanel;
	}

	public String getAtuoWidthPen() {
		return atuoWidthPen;
	}

	public void setAtuoWidthPen(String atuoWidthPen) {
		this.atuoWidthPen = atuoWidthPen;
	}

	public void setQueryZonePanel(QueryZone queryZonePanel) {
		this.queryZonePanel = queryZonePanel;
	}

	public boolean isCanShowPagination() {
		return canShowPagination;
	}

	public void setCanShowPagination(boolean canShowPagination) {
		this.canShowPagination = canShowPagination;
	}

	public boolean isCanShowQuery() {
		return canShowQuery;
	}

	public void setCanShowQuery(boolean canShowQuery) {
		this.canShowQuery = canShowQuery;
	}

	public boolean isCanBatch() {
		return canBatch;
	}

	public void setCanBatch(boolean canBatch) {
		this.canBatch = canBatch;
	}

	public List getCss() {
		return css;
	}

	public void setCss(List css) {
		this.css = css;
	}

	public String getCoustomCss() {
		return coustomCss;
	}

	public void setCoustomCss(String coustomCss) {
		this.coustomCss = coustomCss;
	}

	public List getGridButton() {
		return gridButton;
	}

	public void setGridButton(List gridButton) {
		this.gridButton = gridButton;
	}

	public List getRowButton() {
		return rowButton;
	}

	public void setRowButton(List rowButton) {
		this.rowButton = rowButton;
	}

	public EditPage getEditPage() {
		return editPage;
	}

	public void setEditPage(EditPage editPage) {
		this.editPage = editPage;
	}

	public ViewPage getViewPage() {
		return viewPage;
	}

	public void setViewPage(ViewPage viewPage) {
		this.viewPage = viewPage;
	}

	public List getGridColumns() {
		return gridColumns;
	}

	public void setGridColumns(List gridColumns) {
		this.gridColumns = gridColumns;
	}

	public List getQueryColumns() {
		return queryColumns;
	}

	public void setQueryColumns(List queryColumns) {
		this.queryColumns = queryColumns;
	}

	public void setQueryZone(List queryZone) {
		this.queryZone = queryZone;
	}

	public List getEditMode() {
		return EditMode;
	}

	public void setEditMode(List editMode) {
		EditMode = editMode;
	}

	public List getQueryZone() {
		return queryZone;
	}

	// public List getTitleColumn() {
	// return TitleColumn;
	// }
	// public void setTitleColumn(List titleColumn) {
	// TitleColumn = titleColumn;
	// }
	// public List getListColumn() {
	// return ListColumn;
	// }
	// public void setListColumn(List listColumn) {
	// ListColumn = listColumn;
	// }
	// public List getFields() {
	// return fields;
	// }
	// public void setFields(List fields) {
	// this.fields = fields;
	// }
	public Map getQueryColumnsTable() {
		return queryColumnsTable;
	}

	public void setQueryColumnsTable(Map queryColumnsTable) {
		this.queryColumnsTable = queryColumnsTable;
	}

	public String getDeleteSql() {
		return deleteSql;
	}

	public void setDeleteSql(String deleteSql) {
		this.deleteSql = deleteSql;
	}

	public Map getDeleteParam() {
		return deleteParam;
	}

	public void setDeleteParam(Map deleteParam) {
		this.deleteParam = deleteParam;
	}

	public String getUpdateParamSql() {
		return updateParamSql;
	}

	public void setUpdateParamSql(String updateParamSql) {
		this.updateParamSql = updateParamSql;
	}

	public Map getUpdateParam() {
		return updateParam;
	}

	public void setUpdateParam(Map updateParam) {
		this.updateParam = updateParam;
	}

	public List getDeleteParams() {
		return deleteParams;
	}

	public void setDeleteParams(List deleteParams) {
		this.deleteParams = deleteParams;
	}

	public String getKeyTable() {
		return keyTable;
	}

	public void setKeyTable(String keyTable) {
		this.keyTable = keyTable;
	}

	public String getIsShowOperator() {
		return isShowOperator;
	}

	public void setIsShowOperator(String isShowOperator) {
		this.isShowOperator = isShowOperator;
	}

	public String[] getSelectSqlParam() {
		return selectSqlParam;
	}

	public void setSelectSqlParam(String[] selectSqlParam) {
		this.selectSqlParam = selectSqlParam;
	}

	public List getListPageParams() {
		return listPageParams;
	}

	public void setListPageParams(List listPageParams) {
		this.listPageParams = listPageParams;
	}

	public String getQueryZoneHeight() {

		try {

			QueryZone qZone = (QueryZone) queryZone.get(0);
			int rows = (qZone.getQueryColumns().size() / 4);
			if ((qZone.getQueryColumns().size() % 4) > 0) {
				rows = rows + 1;
			}

			queryZoneHeight = (30 * rows) + "";

		} catch (Exception e) {
			// TODO: handle exception
			queryZoneHeight = "1";
		}
		return queryZoneHeight;
	}

	public void setQueryZoneHeight(String queryZoneHeight) {
		this.queryZoneHeight = queryZoneHeight;
	}

	public CopyPage getCopyPage() {
		return copyPage;
	}

	public void setCopyPage(CopyPage copyPage) {
		this.copyPage = copyPage;
	}

	public List getRowButton2() {
		return rowButton2;
	}

	public void setRowButton2(List rowButton2) {
		this.rowButton2 = rowButton2;
	}

	public String getIsOriginalSql() {
		return isOriginalSql;
	}

	public void setIsOriginalSql(String isOriginalSql) {
		this.isOriginalSql = isOriginalSql;
	}

	public String getIsOriginalSqlContext() {
		return isOriginalSqlContext;
	}

	public void setIsOriginalSqlContext(String isOriginalSqlContext) {
		this.isOriginalSqlContext = isOriginalSqlContext;
	}

	public String getIsOriginalSqlParam() {
		return isOriginalSqlParam;
	}

	public void setIsOriginalSqlParam(String isOriginalSqlParam) {
		this.isOriginalSqlParam = isOriginalSqlParam;
	}

	public String getOpeColumnAlign() {
		return opeColumnAlign;
	}

	public void setOpeColumnAlign(String opeColumnAlign) {
		this.opeColumnAlign = opeColumnAlign;
	}

	public List getGridButton2() {
		return gridButton2;
	}

	public void setGridButton2(List gridButton2) {
		this.gridButton2 = gridButton2;
	}

	public String getAddPageAttribute() {
		return addPageAttribute;
	}

	public void setAddPageAttribute(String addPageAttribute) {
		this.addPageAttribute = addPageAttribute;
	}

	public String getListPageRander() {
		return listPageRander;
	}

	public void setListPageRander(String listPageRander) {
		this.listPageRander = listPageRander;
	}

}
