package com.zxt.compplatform.formengine.entity.view;

import java.util.List;

import com.zxt.framework.common.entity.BasicEntity;

/**
 * Title: Tab Description: 表单页面 Create DateTime: 2010-9-27
 * 
 * @author xxl
 * @since v1.0
 * 
 */
public class Page extends BasicEntity {

	/**
	 * 表单id
	 */
	private String formId;
	/**
	 * 表单名称
	 */
	private String formName;
	/**
	 * 基URL
	 */
	private String baseUrl;
	/**
	 * 扩展URL
	 */
	private String extendsUrl;
	/**
	 * 类型：列表，编辑，查看
	 */
	private String type;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 标签页
	 */
	private List tabs;// 
	/**
	 * 分页设置
	 */
	private Pagination pagination;//
	/**
	 * SQL
	 */
	private String data;//
	/**
	 * 查询区域
	 */
	private QueryZone queryZone;
	/**
	 * 按钮
	 */
	private List buttons;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getExtendsUrl() {
		return extendsUrl;
	}

	public void setExtendsUrl(String extendsUrl) {
		this.extendsUrl = extendsUrl;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

}
