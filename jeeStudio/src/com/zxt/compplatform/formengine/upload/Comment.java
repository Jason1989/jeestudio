package com.zxt.compplatform.formengine.upload;

import java.sql.Date;

/**
 * 注释实体
 * 
 * @author 007
 */
public class Comment {
	/**
	 * 主键
	 */
	private int id;
	/**
	 * 用户id
	 */
	private int userId;
	/**
	 * 视频主键
	 */
	private int videoId;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 创建时间
	 */
	private Date createTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getVideoId() {
		return videoId;
	}

	public void setVideoId(int videoId) {
		this.videoId = videoId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Comment(int userId, int videoId, String content, Date createTime) {
		super();
		this.userId = userId;
		this.videoId = videoId;
		this.content = content;
		this.createTime = createTime;
	}

	public Comment() {
	}

}
