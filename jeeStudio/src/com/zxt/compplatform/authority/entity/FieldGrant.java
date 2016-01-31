package com.zxt.compplatform.authority.entity;

import com.zxt.framework.common.entity.BasicEntity;
/**
 * 字段授权
 * @author GUOWEIXIN
 *
 */
public class FieldGrant extends BasicEntity{
	/**
	 * 关联主键
	 */
	private String id;
	/**
	 * 角色id
	 */
	private Long rid;
	/**
	 * 表名称
	 */
	private String tablename;
	/***
	 * 字段名称
	 */
	private String fieldname;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getRid() {
		return rid;
	}
	public void setRid(Long rid) {
		this.rid = rid;
	}public void setRid(String rid) {
		this.rid =new Long(rid);
	}
	public String getTablename() {
		return tablename;
	}
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
	public String getFieldname() {
		return fieldname;
	}
	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}

}
