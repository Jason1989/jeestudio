package com.zxt.compplatform.formengine.entity.view;

import com.zxt.framework.common.entity.BasicEntity;

/**
 * Title: CacheInterface 
 * Description: 界面提示信息 
 * Create DateTime: 2010-9-27
 * 
 * @author xxl
 * @since v1.0
 * 
 */
public class Note extends BasicEntity {
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 提示文本
	 */
	private String noteText;
	/**
	 * 提示样式
	 */
	private String noteStyle;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNoteText() {
		return noteText;
	}

	public void setNoteText(String noteText) {
		this.noteText = noteText;
	}

	public String getNoteStyle() {
		return noteStyle;
	}

	public void setNoteStyle(String noteStyle) {
		this.noteStyle = noteStyle;
	}
}
