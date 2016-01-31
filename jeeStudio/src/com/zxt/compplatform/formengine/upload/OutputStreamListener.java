package com.zxt.compplatform.formengine.upload;

/**
 * 数据流监听器
 * 
 * @author 007
 */
public interface OutputStreamListener {
	/**
	 * 开始
	 */
	public void start();

	/**
	 * 字节读取
	 * 
	 * @param bytesRead
	 */
	public void bytesRead(int bytesRead);

	/**
	 * 错误
	 * 
	 * @param message
	 */
	public void error(String message);

	/**
	 * 完成
	 */
	public void done();
}
