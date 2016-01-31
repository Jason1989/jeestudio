package com.zxt.compplatform.formengine.entity.dataset;

/**
 * 附件实体
 * 
 * @author 007
 */
public class AttachementVo extends BaseVO {
	private static final long serialVersionUID = 1L;
	/**
	 * 文件id
	 */
	private String fileid;
	/**
	 * 文件名称
	 */
	private String filename;
	/**
	 * 重新命名
	 */
	private String filername;
	/**
	 * 文件大小
	 */
	private String filesize;
	/**
	 * 文件类型
	 */
	private String filetype;
	/**
	 * 上传时间
	 */
	private String uploaddate;
	/**
	 * 用户id
	 */
	private String userid;
	/**
	 * 用户名称
	 */
	private String username;
	/**
	 * 字段列id
	 */
	private String columnid;
	/**
	 * 文件路径
	 */
	private String filepath;

	public String getFileid() {
		return fileid;
	}

	public void setFileid(String fileid) {
		this.fileid = fileid;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilesize() {
		return filesize;
	}

	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}

	public String getFiletype() {
		return filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	public String getUploaddate() {
		return uploaddate;
	}

	public void setUploaddate(String uploaddate) {
		this.uploaddate = uploaddate;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getColumnid() {
		return columnid;
	}

	public void setColumnid(String columnid) {
		this.columnid = columnid;
	}

	public String getFilername() {
		return filername;
	}

	public void setFilername(String filername) {
		this.filername = filername;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

}
