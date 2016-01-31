package com.zxt.compplatform.formengine.upload;

import javax.servlet.http.HttpServletRequest;

/**
 * 文件上传监听器
 * 
 * @author 007
 */
public class UploadOutputStreamListener implements OutputStreamListener {
	/**
	 * 上传请求
	 */
	private HttpServletRequest request;
	/**
	 * 延迟
	 */
	private long delay = 0;
	/**
	 * 开始世间
	 */
	private long startTime = 0;
	/**
	 * 总共读取多少
	 */
	private int totalToRead = 0;
	/**
	 * 要读取的字节数
	 */
	private int totalBytesRead = 0;
	/**
	 * 总文件树
	 */
	private int totalFiles = -1;

	public UploadOutputStreamListener(HttpServletRequest request,
			long debugDelay) {
		this.request = request;
		this.delay = debugDelay;
		totalToRead = request.getContentLength();
		this.startTime = System.currentTimeMillis();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.formengine.upload.OutputStreamListener#start()
	 */
	public void start() {
		totalFiles++;
		updateUploadInfo("start");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.formengine.upload.OutputStreamListener#bytesRead(int)
	 */
	public void bytesRead(int bytesRead) {
		totalBytesRead = totalBytesRead + bytesRead;
		updateUploadInfo("progress");

		try {
			if (delay > 0) {
				Thread.sleep(delay);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.formengine.upload.OutputStreamListener#error(java.lang.String)
	 */
	public void error(String message) {
		updateUploadInfo("error");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.formengine.upload.OutputStreamListener#done()
	 */
	public void done() {
		updateUploadInfo("done");
	}

	/**
	 * @return
	 */
	private long getDelta() {
		return (System.currentTimeMillis() - startTime) / 1000;
	}

	/**
	 * 更新上传文件信息
	 * 
	 * @param status
	 */
	private void updateUploadInfo(String status) {
		long delta = (System.currentTimeMillis() - startTime) / 1000;
		request.getSession().setAttribute(
				"uploadInfo",
				new UploadInfo(totalFiles, totalToRead, totalBytesRead, delta,
						status));
	}

}
