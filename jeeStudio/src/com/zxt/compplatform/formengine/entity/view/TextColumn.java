package com.zxt.compplatform.formengine.entity.view;

/**
 * Title: Column Description: 编辑页text Create DateTime: 2010-9-27
 * 
 * @author xxl
 * @since v1.0
 * 
 */
public class TextColumn extends Column {
	/**
	 * 是否隐藏
	 */
	private Boolean hidden = new Boolean(true);//
	/**
	 * 跨列
	 */
	private int colspan;
	/**
	 * 跨行
	 */
	private int rowspan;
	/**
	 * 对齐方式
	 */
	private String align;
	/**
	 * 宽度
	 */
	private String width;
	/**
	 * 事件
	 */
	private String event;
	/**
	 * 默认的分组
	 */
	private String groupId = "0";//
	/**
	 * 是否独占行
	 */
	private Boolean exclusiveLine = new Boolean(false);//

	/**
	 * 是否只读
	 */
	private Boolean readOnly = new Boolean(false);//
	/***************************************************************************
	 * 字段扩展属性
	 **************************************************************************/
	/**
	 * 时间格式转换
	 */
	private String dateformat;//
	/**
	 * 文本域 行
	 */
	private String rows = "";//
	/**
	 * 文本域 列
	 */
	private String cols = "";//
	/***************************************************************************
	 * 是否设置默认值
	 **************************************************************************/
	/**
	 * 是否设置系统变量
	 */
	private String variantOrnot;//
	/**
	 * 变量类型
	 */
	private String variantType = "";//
	/**
	 * 变量值
	 */
	private String variantValue = "";//

	/**
	 * 是否是工作流字段
	 */
	private String isworkflow; //

	/**
	 * 组织机构树控件是否可以多选
	 */
	private String ismultipart;//
	/**
	 * 组织机构树控件是否子节点可选
	 */
	private String isleafcheck;//
	/**
	 * 组织机构树控件是否本部门可选
	 */
	private String isselforg;//
	/**
	 * 如果本部门可选选择的组织机构树
	 */
	private String orgid;//

	/**
	 * 人员列表树控件是否可以多选
	 */
	private String ismultiparthuman;//
	/**
	 * 人员列表树控件选择的组织机构
	 */
	private String orgidhuman;//

	/**
	 * 应急选择控件是否单表数据
	 */
	private String issingletable;//
	/**
	 * 应急选择控件树sql语句
	 */
	private String isemergencytreesql;//

	/**
	 * 是否使用列表页选择数据
	 */
	private Boolean is_listPageForvalue = new Boolean(false);//
	/**
	 * 列表页formId
	 */
	private String pageurl_listPage;//

	private String listInside;
	private String listInsideParmer;

	/**
	 * 列表页value项列名
	 */
	private String list_value; //
	/**
	 * 列表页text项列名
	 */
	private String list_text; //

	public Boolean getIs_listPageForvalue() {
		return is_listPageForvalue;
	}

	public void setIs_listPageForvalue(Boolean is_listPageForvalue) {
		this.is_listPageForvalue = is_listPageForvalue;
	}

	public String getPageurl_listPage() {
		return pageurl_listPage;
	}

	public void setPageurl_listPage(String pageurl_listPage) {
		this.pageurl_listPage = pageurl_listPage;
	}

	public String getIsmultipart() {
		return ismultipart;
	}

	public void setIsmultipart(String ismultipart) {
		this.ismultipart = ismultipart;
	}

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public String getIsleafcheck() {
		return isleafcheck;
	}

	public void setIsleafcheck(String isleafcheck) {
		this.isleafcheck = isleafcheck;
	}

	public String getIsworkflow() {
		return isworkflow;
	}

	public void setIsworkflow(String isworkflow) {
		this.isworkflow = isworkflow;
	}

	public String getVariantOrnot() {
		return variantOrnot;
	}

	public void setVariantOrnot(String variantOrnot) {
		this.variantOrnot = variantOrnot;
	}

	public String getVariantType() {
		return variantType;
	}

	public void setVariantType(String variantType) {
		this.variantType = variantType;
	}

	public String getVariantValue() {
		return variantValue;
	}

	public void setVariantValue(String variantValue) {
		this.variantValue = variantValue;
	}

	public Boolean getHidden() {
		return hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

	public int getColspan() {
		return colspan;
	}

	public void setColspan(int colspan) {
		this.colspan = colspan;
	}

	public int getRowspan() {
		return rowspan;
	}

	public void setRowspan(int rowspan) {
		this.rowspan = rowspan;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getGroupId() {
		if (("".equals(groupId)) || (groupId == null)) {
			groupId = "-1";
		}
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public Boolean getExclusiveLine() {
		return exclusiveLine;
	}

	public void setExclusiveLine(Boolean exclusiveLine) {
		this.exclusiveLine = exclusiveLine;
	}

	public Boolean getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}

	public String getDateformat() {
		return dateformat;
	}

	public void setDateformat(String dateformat) {
		this.dateformat = dateformat;
	}

	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	public String getCols() {
		return cols;
	}

	public void setCols(String cols) {
		this.cols = cols;
	}

	public Boolean getVisible() {
		return super.isVisible();
	}

	public String getIsselforg() {
		return isselforg;
	}

	public String getListInside() {
		return listInside;
	}

	public void setListInside(String listInside) {
		this.listInside = listInside;
	}

	public String getListInsideParmer() {
		return listInsideParmer;
	}

	public void setListInsideParmer(String listInsideParmer) {
		this.listInsideParmer = listInsideParmer;
	}

	public void setIsselforg(String isselforg) {
		this.isselforg = isselforg;
	}

	public String getList_value() {
		return list_value;
	}

	public void setList_value(String list_value) {
		this.list_value = list_value;
	}

	public String getList_text() {
		return list_text;
	}

	public void setList_text(String list_text) {
		this.list_text = list_text;
	}

	public String getIsmultiparthuman() {
		return ismultiparthuman;
	}

	public void setIsmultiparthuman(String ismultiparthuman) {
		this.ismultiparthuman = ismultiparthuman;
	}

	public String getOrgidhuman() {
		return orgidhuman;
	}

	public void setOrgidhuman(String orgidhuman) {
		this.orgidhuman = orgidhuman;
	}

	public String getIssingletable() {
		return issingletable;
	}

	public void setIssingletable(String issingletable) {
		this.issingletable = issingletable;
	}

	public String getIsemergencytreesql() {
		return isemergencytreesql;
	}

	public void setIsemergencytreesql(String isemergencytreesql) {
		this.isemergencytreesql = isemergencytreesql;
	}

}
