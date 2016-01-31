package com.zxt.compplatform.formengine.entity.dataset;

import java.util.HashMap;

/**
 * 数据源vo
 * 
 * @author 007
 */
public class DataSourceVO extends BaseVO {
	/**
	 * 数据类型，访问类型
	 */
	String dataType, accessType;
	/**
	 * 参数集合
	 */
	private HashMap parameterMap;

	public String getAccessType() {
		return accessType;
	}

	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public HashMap getParameterMap() {
		return parameterMap;
	}

	public void setParameterMap(HashMap parameterMap) {
		this.parameterMap = parameterMap;
	}

}
