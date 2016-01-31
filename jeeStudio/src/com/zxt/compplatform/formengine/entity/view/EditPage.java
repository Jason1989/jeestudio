package com.zxt.compplatform.formengine.entity.view;

import java.util.List;
import java.util.Map;

/**
 * 编辑页
 * 
 * @author 007
 */
public class EditPage extends BasePage {
	/** GUOWEIXIN添加 EditPage的页面信息配置
	 * 保存前SQL
	 */
	private String before_sql;
	/** GUOWEIXIN添加 EditPage的页面信息配置
	 * 保存后SQL
	 */
	private String after_sql;
	
	/**
	 * 分组
	 */
	private List groups;
	/**
	 * 插入语句
	 */
	private String insertSql;
	/**
	 * 修改语句
	 */
	private String updateSql;
	/**
	 * 编辑页查询语句
	 */
	private String findSql;
	/**
	 * 编辑页查询参数
	 */
	private List findParam;
	/**
	 * 修改的参数 表名--list<ColumnData>
	 */
	private Map updateParam;
	/**
	 * 添加参数
	 */
	private Map insertParam;
	/**
	 * 查询参数
	 */
	private Map findParams;
	/**
	 * 更新参数
	 */
	private List updateParams;

	/**
	 * 查询sql参数
	 */
	private String[] findSqlParmer;
	/**
	 * 删除sql参数
	 */
	private String[] deleteSqlParmer;
	/**
	 * 编辑页参数
	 */
	private List editPageParams;
	/**
	 * 修改表的名称
	 */
	private String updataTableName;
	/**
	 * 插入语句参数
	 */
	private List insertParams;
	/**
	 * 插入表的名称
	 */
	private String insertTableName;
	/**
	 * 插入时的主键
	 */
	private List keyList;

	/**
	 * 按钮
	 */
	private List button;
	/**
	 * 多标签表单的ids json
	 * 
	 * @return
	 */
	private String idsJson;

	/**
	 * 使用工作流
	 */
	private String workFlowEnable;

	/**
	 * 工作流参数
	 */
	private String workflowParCom;
	/**
	 * 工作流id
	 */
	private String workflowParComId;
	/**
	 * 工作流参数数组
	 */
	private String workflowParComArray;
	/**
	 * 编辑页是多标签时 保存的窗口ID
	 */

	private String tabWindowID;

	/**
	 * js规则引擎
	 */
	private String jsRules;

	/**
	 * 编辑页自定义宽高
	 */
	private String selfDefine;

	/**
	 * 添加标题
	 */
	private String addTitle;
	/**
	 * 编辑标题
	 */
	private String editTitle;

	public String getAddTitle() {
		return (getTitle() + "").split(";")[0];
	}

	public void setAddTitle(String addTitle) {
		this.addTitle = addTitle;
	}

	public String getEditTitle() {
		String value[] = (getTitle() + "").split(";");
		if (value.length != 2) {
			return value[0];
		} else {
			return value[1];
		}

	}

	public void setEditTitle(String editTitle) {
		this.editTitle = editTitle;
	}

	public String getSelfDefine() {
		return selfDefine;
	}

	public void setSelfDefine(String selfDefine) {
		this.selfDefine = selfDefine;
	}

	public String getJsRules() {
		return jsRules;
	}

	public void setJsRules(String jsRules) {
		this.jsRules = jsRules;
	}

	public String getTabWindowID() {
		return tabWindowID;
	}

	public void setTabWindowID(String tabWindowID) {
		this.tabWindowID = tabWindowID;
	}

	public String getWorkFlowEnable() {
		return workFlowEnable;
	}

	public void setWorkFlowEnable(String workFlowEnable) {
		this.workFlowEnable = workFlowEnable;
	}

	public String getIdsJson() {
		return idsJson;
	}

	public void setIdsJson(String idsJson) {
		this.idsJson = idsJson;
	}

	public List getGroups() {
		return groups;
	}

	public void setGroups(List groups) {
		this.groups = groups;
	}

	public String getInsertSql() {
		return insertSql;
	}

	public void setInsertSql(String insertSql) {
		this.insertSql = insertSql;
	}

	public String getUpdateSql() {
		return updateSql;
	}

	public String getWorkflowParCom() {
		return workflowParCom;
	}

	public void setWorkflowParCom(String workflowParCom) {
		this.workflowParCom = workflowParCom;
	}

	public String getWorkflowParComId() {
		return workflowParComId;
	}

	public void setWorkflowParComId(String workflowParComId) {
		this.workflowParComId = workflowParComId;
	}

	public String getWorkflowParComArray() {
		return workflowParComArray;
	}

	public void setWorkflowParComArray(String workflowParComArray) {
		this.workflowParComArray = workflowParComArray;
	}

	public void setUpdateSql(String updateSql) {
		this.updateSql = updateSql;
	}

	public List getButton() {
		return button;
	}

	public void setButton(List button) {
		this.button = button;
	}

	public Map getUpdateParam() {
		return updateParam;
	}

	public void setUpdateParam(Map updateParam) {
		this.updateParam = updateParam;
	}

	public String getFindSql() {
		return findSql;
	}

	public void setFindSql(String findSql) {
		this.findSql = findSql;
	}

	public List getFindParam() {
		return findParam;
	}

	public void setFindParam(List findParam) {
		this.findParam = findParam;
	}

	public List getUpdateParams() {
		return updateParams;
	}

	public void setUpdateParams(List updateParams) {
		this.updateParams = updateParams;
	}

	public Map getInsertParam() {
		return insertParam;
	}

	public void setInsertParam(Map insertParam) {
		this.insertParam = insertParam;
	}

	public Map getFindParams() {
		return findParams;
	}

	public void setFindParams(Map findParams) {
		this.findParams = findParams;
	}

	public String[] getFindSqlParmer() {
		return findSqlParmer;
	}

	public void setFindSqlParmer(String[] findSqlParmer) {
		this.findSqlParmer = findSqlParmer;
	}

	public String[] getDeleteSqlParmer() {
		return deleteSqlParmer;
	}

	public void setDeleteSqlParmer(String[] deleteSqlParmer) {
		this.deleteSqlParmer = deleteSqlParmer;
	}

	public List getEditPageParams() {
		return editPageParams;
	}

	public void setEditPageParams(List editPageParams) {
		this.editPageParams = editPageParams;
	}

	public String getUpdataTableName() {
		return updataTableName;
	}

	public void setUpdataTableName(String updataTableName) {
		this.updataTableName = updataTableName;
	}

	public List getInsertParams() {
		return insertParams;
	}

	public void setInsertParams(List insertParams) {
		this.insertParams = insertParams;
	}

	public String getInsertTableName() {
		return insertTableName;
	}

	public void setInsertTableName(String insertTableName) {
		this.insertTableName = insertTableName;
	}

	public List getKeyList() {
		return keyList;
	}

	public void setKeyList(List keyList) {
		this.keyList = keyList;
	}

	public String getBefore_sql() {
		return before_sql;
	}

	public void setBefore_sql(String before_sql) {
		this.before_sql = before_sql;
	}

	public String getAfter_sql() {
		return after_sql;
	}

	public void setAfter_sql(String after_sql) {
		this.after_sql = after_sql;
	}

}
