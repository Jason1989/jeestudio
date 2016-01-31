package com.zxt.compplatform.formengine.entity.view;

import java.util.List;

import com.zxt.framework.common.entity.BasicEntity;

/**
 * 树控件所需要的数据
 * 
 * @author 007
 */
public class TreeDataBasic extends BasicEntity {

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 展示文本
	 */
	private String text;
	/**
	 * 父节点id
	 */
	private Long parentid;
	/**
	 * 子节点
	 */
	private List children;
	/**
	 * 加载数据url
	 */
	private String url;
	/**
	 * 附带属性
	 */
	private TreeAttributes attributes;
	/**
	 * 图标
	 */
	private String iconCl = "icon-save";
	/**
	 * 
	 */
	private String state = "open";
	/**
	 * 多选 是否选中
	 */
	private boolean checked = false;
	/**
	 * 菜单级别
	 */
	private String level;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIconCl() {
		return iconCl;
	}

	public void setIconCl(String iconCl) {
		this.iconCl = iconCl;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getParentid() {
		return parentid;
	}

	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
}
