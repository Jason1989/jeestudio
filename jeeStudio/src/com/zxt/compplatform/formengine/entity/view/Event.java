package com.zxt.compplatform.formengine.entity.view;

import java.util.List;
import java.util.Map;

import com.zxt.framework.common.entity.BasicEntity;

/**
 * Title: Event Description: 界面事件 Create DateTime: 2010-9-27
 * 
 * @author xxl
 * @since v1.0
 * 
 */
public class Event extends BasicEntity {
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 事件类型
	 */
	private String type;// ex:[onclick,ondbclick....]
	/**
	 * 事件函数
	 */
	private JSFunction functions;
	/**
	 * 参数
	 */
	private Map params;
	/**
	 * 参数
	 */
	private Param param;//
	/**
	 * 参数列表
	 */
	private List paras;

	public List getParas() {
		return paras;
	}

	public void setParas(List paras) {
		this.paras = paras;
	}

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public JSFunction getFunctions() {
		return functions;
	}

	public void setFunctions(JSFunction functions) {
		this.functions = functions;
	}

	public Param getParam() {
		return param;
	}

	public void setParam(Param param) {
		this.param = param;
	}

	public Map getParams() {
		return params;
	}

	public void setParams(Map params) {
		this.params = params;
	}

}
