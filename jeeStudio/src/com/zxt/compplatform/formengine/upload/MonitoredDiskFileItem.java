package com.zxt.compplatform.formengine.upload;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.fileupload.disk.DiskFileItem;

/**
 * 系统文件监听
 * 
 * @author 007
 */
public class MonitoredDiskFileItem extends DiskFileItem {
	/**
	 * 监听器数据输出流
	 */
	private MonitoredOutputStream monitoredOutputStream = null;
	/**
	 * 数据流监听器
	 */
	private OutputStreamListener outputStreamListener;

	public MonitoredDiskFileItem(String fieldName, String contentType,
			boolean isFormField, String fileName, int sizeThreshold,
			File repository, OutputStreamListener outputStreamListener) {
		super(fieldName, contentType, isFormField, fileName, sizeThreshold,
				repository);
		this.outputStreamListener = outputStreamListener;
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.fileupload.disk.DiskFileItem#getOutputStream()
	 */
	public OutputStream getOutputStream() throws IOException {
		if (monitoredOutputStream == null) {
			monitoredOutputStream = new MonitoredOutputStream(super
					.getOutputStream(), outputStreamListener);
		}
		return monitoredOutputStream;
	}
}
