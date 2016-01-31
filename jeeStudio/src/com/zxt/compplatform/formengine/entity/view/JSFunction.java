package com.zxt.compplatform.formengine.entity.view;

import java.util.List;

import com.zxt.framework.common.entity.BasicEntity;

/**
 * javascript函数实体
 * 
 * @author 007
 */
public class JSFunction extends BasicEntity {
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 函数体
	 */
	private String body;
	/**
	 * 排序
	 */
	private String SORTINDEX;
	/**
	 * 参数
	 */
	private List parmers;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getSORTINDEX() {
		return SORTINDEX;
	}

	public void setSORTINDEX(String sortindex) {
		SORTINDEX = sortindex;
	}

	public List getParmers() {
		return parmers;
	}

	public void setParmers(List parmers) {
		this.parmers = parmers;
	}

}
