package com.zxt.compplatform.formengine.entity.view;

import java.util.List;

import com.zxt.framework.common.entity.BasicEntity;

/**
 * 对应jquery框架定义的数据实体
 * 
 * @author Administrator
 * 
 */
public class TreeOrgData extends BasicEntity {

	/**
	 * 主键
	 */
	private String id;
	/**
	 * 文本
	 */
	private String text;
	/**
	 * 主节点的id
	 */
	private String parentID = "0";
	/**
	 * 子节点
	 */
	private List children;
	/**
	 * 标识
	 */
	private String flag;
	/**
	 * 附带属性
	 */
	private TreeAttributes attributes = new TreeAttributes();

	/**
	 * 图标
	 */
	private String iconCls = "";//
	/**
	 * 是否展开
	 */
	private String state = "open";//
	/**
	 * 多选 是否选中
	 */
	private boolean checked = false;// 

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
	 * 作为标记标记是组织机构还是用户
	 */
	private String isuser; //

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

	public String getIsuser() {
		return isuser;
	}

	public void setIsuser(String isuser) {
		this.isuser = isuser;
	}

}
