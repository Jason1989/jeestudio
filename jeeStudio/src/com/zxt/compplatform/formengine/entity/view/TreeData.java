package com.zxt.compplatform.formengine.entity.view;

import java.util.List;

import com.zxt.framework.common.entity.BasicEntity;

/**
 * 对应jquery框架定义的数据实体
 * 
 * @author Administrator
 * 
 */
public class TreeData extends BasicEntity {

	/**
	 * 主键文本
	 */
	private String id;
	/**
	 * 显示文本
	 */
	private String text;
	/**
	 * 父节点
	 */
	private String parentID = "0";
	/**
	 * 子节点
	 */
	private List children;
	/**
	 * 组织机构中 被选中作为模板的 标志字段
	 */
	private String flag;//
	/**
	 * 组织名称
	 */
	private String oname;
	/**
	 * 属性
	 */
	private TreeAttributes attributes = new TreeAttributes();

	/**
	 * 图标
	 */
	private String iconCls = "";// 
	/**
	 * 树节点状态：open close
	 */
	private String state = "open";//
	/**
	 * 多选 是否选中
	 */
	private boolean checked = false;// 
	/**
	 * 是否选中
	 */
	private boolean selected = false;

	/**
	 * 菜单级别
	 */
	private String level;// 
	/**
	 * 资源标识
	 */
	private String resKey;// 
	/**
	 * 资源类型
	 */
	private String resType;// 
	/**
	 * 是否菜单项
	 */
	private String isMenu;// 
	/**
	 * 排序
	 */
	private String resSort;//

	/**
	 * 图片地址
	 */
	private String imgsrc;//
	/**
	 * 一行多少个
	 */
	private String row_num;//
	/**
	 * 是否可用皮肤编辑功能
	 */
	private String selectSkinEnable;//
	/**
	 * 选择默认使用的皮肤功能
	 */
	private String defaultSkin;//

	public String getDefaultSkin() {
		return defaultSkin;
	}

	public void setDefaultSkin(String defaultSkin) {
		this.defaultSkin = defaultSkin;
	}

	public String getImgsrc() {
		return imgsrc;
	}

	public void setImgsrc(String imgsrc) {
		this.imgsrc = imgsrc;
	}

	public String getRow_num() {
		return row_num;
	}

	public void setRow_num(String row_num) {
		this.row_num = row_num;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getParentID() {
		return parentID;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
	}

	public List getChildren() {
		return children;
	}

	public void setChildren(List children) {
		this.children = children;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public TreeAttributes getAttributes() {
		return attributes;
	}

	public void setAttributes(TreeAttributes attributes) {
		this.attributes = attributes;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getResSort() {
		return resSort;
	}

	public void setResSort(String resSort) {
		this.resSort = resSort;
	}

	public String getResKey() {
		return resKey;
	}

	public void setResKey(String resKey) {
		this.resKey = resKey;
	}

	public String getResType() {
		return resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
	}

	public String getIsMenu() {
		return isMenu;
	}

	public void setIsMenu(String isMenu) {
		this.isMenu = isMenu;
	}

	public String getSelectSkinEnable() {
		return selectSkinEnable;
	}

	public void setSelectSkinEnable(String selectSkinEnable) {
		this.selectSkinEnable = selectSkinEnable;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getOname() {
		return oname;
	}

	public void setOname(String oname) {
		this.oname = oname;
	}

}
