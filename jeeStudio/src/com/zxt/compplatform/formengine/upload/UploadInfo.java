package com.zxt.compplatform.formengine.upload;

/**
 * 上传文件信息
 * 
 * @author 007
 */
public class UploadInfo {
	/**
	 * 总数目
	 */
	private long totalSize = 0;
	/**
	 * 读取字节数
	 */
	private long bytesRead = 0;
	/**
	 * 耗时
	 */
	private long elapsedTime = 0;
	/**
	 * 完成
	 */
	private String status = "done";
	/**
	 * 文件指数
	 */
	private int fileIndex = 0;

	public UploadInfo() {
	}

	public UploadInfo(int fileIndex, long totalSize, long bytesRead,
			long elapsedTime, String status) {
		this.fileIndex = fileIndex;
		this.totalSize = totalSize;
		this.bytesRead = bytesRead;
		this.elapsedTime = elapsedTime;
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
	}

	public long getBytesRead() {
		return bytesRead;
	}

	public void setBytesRead(long bytesRead) {
		this.bytesRead = bytesRead;
	}

	public long getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	/**
	 * 是否文件上传正在进行
	 * 
	 * @return
	 */
	public boolean isInProgress() {
		return "progress".equals(status) || "start".equals(status);
	}

	public int getFileIndex() {
		return fileIndex;
	}

	public void setFileIndex(int fileIndex) {
		this.fileIndex = fileIndex;
	}
}
