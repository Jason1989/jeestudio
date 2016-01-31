package com.zxt.compplatform.datatable.entity;

import com.zxt.framework.common.entity.BasicEntity;

/**
 * 数据对象分组实体
 * 
 * @author 007
 */
public class DataObjectGroup extends BasicEntity {

	private static final long serialVersionUID = 1L;
	/**
	 * 数据对象分组跟节点id
	 */
	public static final String DATA_OBJECT_GROUP_ROOT_ID = "1";
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 分组名称
	 */
	private String name;
	/**
	 * 父节点id
	 */
	private String pid;
	/**
	 * 序号
	 */
	private Integer sortNo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}
}
