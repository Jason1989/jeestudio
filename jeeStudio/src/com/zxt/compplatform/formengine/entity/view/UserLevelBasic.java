package com.zxt.compplatform.formengine.entity.view;

/**
 * 用户级别基本信息
 * 
 * @author 007
 */
public class UserLevelBasic {

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

	/**
	 * 中文名
	 */
	private String uname;

	/**
	 * 
	 */
	private Long num;

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

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}
}
