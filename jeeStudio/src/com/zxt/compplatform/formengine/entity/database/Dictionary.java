package com.zxt.compplatform.formengine.entity.database;

/**
 * Title: CacheInterface Description: 数据字典
 * Create DateTime: 2010-9-27
 * 
 * @author xxl
 * @since v1.0
 * 
 */
public class Dictionary implements java.io.Serializable {

	/**
	 * 数据字典主键
	 */
	private String dictionaryId;// 临时
	/**
	 * 数据字典名称
	 */
	private String dictionaryName;// 临时

	public String getDictionaryId() {
		return dictionaryId;
	}

	public void setDictionaryId(String dictionaryId) {
		this.dictionaryId = dictionaryId;
	}

	public String getDictionaryName() {
		return dictionaryName;
	}

	public void setDictionaryName(String dictionaryName) {
		this.dictionaryName = dictionaryName;
	}

}
