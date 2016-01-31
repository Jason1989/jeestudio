package com.zxt.compplatform.formula.entity;

import com.zxt.framework.common.entity.BasicEntity;

/**
 * Title: Formula
 * Description:  
 * Create DateTime: 2010-9-10
 * @author xxl
 * @since v1.0
 * 
 */
public class Formula extends BasicEntity{
	private String id;
	private String name;
	private String expression;
	private String desc;
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
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
