package com.zxt.compplatform.formengine.entity.dataset;

/**
 * 文件vo
 * 
 * @author 007
 */
public class DefineFileVO extends BaseVO {
	/**
	 * 数据文件名称
	 */
	private String dataFileName;
	/**
	 * 数据url
	 */
	private String dataUrl;

	public String getDataFileName() {
		return dataFileName;
	}

	public void setDataFileName(String dataFileName) {
		this.dataFileName = dataFileName;
	}

	public String getDataUrl() {
		return dataUrl;
	}

	public void setDataUrl(String dataUrl) {
		this.dataUrl = dataUrl;
	}
}
