package com.zxt.compplatform.authority.entity;

import com.zxt.framework.common.entity.BasicEntity;

/**
 * 用户级别实体
 * @author 007
 */
public class UserLevel extends BasicEntity {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 级别ID
	 */
	private Long id;
	
	/**
	 * 级别名称
	 */
	private String levelname;
	
	/**
	 * 级别号
	 */
	private Long levelnumber;
	
	/**
	 * 级别描述
	 */
	private String levelnote;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLevelname() {
		return levelname;
	}

	public void setLevelname(String levelname) {
		this.levelname = levelname;
	}

	public Long getLevelnumber() {
		return levelnumber;
	}

	public void setLevelnumber(Long levelnumber) {
		this.levelnumber = levelnumber;
	}

	public String getLevelnote() {
		return levelnote;
	}

	public void setLevelnote(String levelnote) {
		this.levelnote = levelnote;
	}
	
}
