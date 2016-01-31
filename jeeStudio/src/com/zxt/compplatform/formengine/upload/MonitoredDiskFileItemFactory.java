package com.zxt.compplatform.formengine.upload;

import java.io.File;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;

/**
 * 系统硬盘文件监听工厂
 * 
 * @author 007
 */
public class MonitoredDiskFileItemFactory extends DiskFileItemFactory {
	/**
	 * 输出流监听器
	 */
	private OutputStreamListener outputStreamListener = null;

	public MonitoredDiskFileItemFactory(
			OutputStreamListener outputStreamListener) {
		super();
		this.outputStreamListener = outputStreamListener;
	}

	public MonitoredDiskFileItemFactory(int sizeThreshold, File repository,
			OutputStreamListener outputStreamListener) {
		super(sizeThreshold, repository);
		this.outputStreamListener = outputStreamListener;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.commons.fileupload.disk.DiskFileItemFactory#createItem(java.lang.String,
	 *      java.lang.String, boolean, java.lang.String)
	 */
	public FileItem createItem(String fieldName, String contentType,
			boolean isFormField, String fileName) {
		return new MonitoredDiskFileItem(fieldName, contentType, isFormField,
				fileName, getSizeThreshold(), getRepository(),
				outputStreamListener);
	}
}
