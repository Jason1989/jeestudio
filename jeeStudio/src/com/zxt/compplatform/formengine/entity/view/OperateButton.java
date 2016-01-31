package com.zxt.compplatform.formengine.entity.view;

import com.zxt.framework.common.entity.BasicEntity;

/**
 * Title: Button Description: 按钮 Create DateTime: 2010-9-27
 * 
 * @author xxl
 * @since v1.0
 * 
 */
public class OperateButton extends BasicEntity {

	/**
	 * 按钮名称
	 */
	private String buttonname;//
	/**
	 * 按钮图标
	 */
	private String buttonicon;
	/**
	 * 按钮规则
	 */
	private String buttonrules;
	/**
	 * 是否显示
	 */
	private String isshow;

	/**
	 * 编辑页
	 */
	private EditPage editpage;
	/**
	 * 查看页
	 */
	private ViewPage viewpage;
	/**
	 * 列表页
	 */
	private ListPage listpage;

	public ViewPage getViewpage() {
		return viewpage;
	}

	public void setViewpage(ViewPage viewpage) {
		this.viewpage = viewpage;
	}

	public ListPage getListpage() {
		return listpage;
	}

	public void setListpage(ListPage listpage) {
		this.listpage = listpage;
	}

	public EditPage getEditpage() {
		return editpage;
	}

	public void setEditpage(EditPage editpage) {
		this.editpage = editpage;
	}

	public String getButtonname() {
		return buttonname;
	}

	public void setButtonname(String buttonname) {
		this.buttonname = buttonname;
	}

	public String getButtonicon() {
		return buttonicon;
	}

	public void setButtonicon(String buttonicon) {
		this.buttonicon = buttonicon;
	}

	public String getButtonrules() {
		return buttonrules;
	}

	public void setButtonrules(String buttonrules) {
		this.buttonrules = buttonrules;
	}

	public String getIsshow() {
		return isshow;
	}

	public void setIsshow(String isshow) {
		this.isshow = isshow;
	}

}
