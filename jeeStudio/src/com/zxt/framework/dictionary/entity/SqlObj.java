package com.zxt.framework.dictionary.entity;

import java.util.List;

import com.zxt.framework.common.entity.BasicEntity;

public class SqlObj extends BasicEntity{
	
	private String sqldicid;
	private String sqldicname;
	private String sqldic_expression;
	private String sqldic_type;
	private String sqldic_remark;
	private List sqlParam;
	private String sqldic_dataSourceid;
	
	public String getSqldicid() {
		return sqldicid;
	}
	public void setSqldicid(String sqldicid) {
		this.sqldicid = sqldicid;
	}
	public String getSqldicname() {
		return sqldicname;
	}
	public void setSqldicname(String sqldicname) {
		this.sqldicname = sqldicname;
	}
	public String getSqldic_expression() {
		return sqldic_expression;
	}
	public void setSqldic_expression(String sqldic_expression) {
		this.sqldic_expression = sqldic_expression;
	}
	public String getSqldic_type() {
		return sqldic_type;
	}
	public void setSqldic_type(String sqldic_type) {
		this.sqldic_type = sqldic_type;
	}
	public String getSqldic_remark() {
		return sqldic_remark;
	}
	public void setSqldic_remark(String sqldic_remark) {
		this.sqldic_remark = sqldic_remark;
	}
	public String getSqldic_dataSourceid() {
		return sqldic_dataSourceid;
	}
	public void setSqldic_dataSourceid(String sqldic_dataSourceid) {
		this.sqldic_dataSourceid = sqldic_dataSourceid;
	}
	public List getSqlParam() {
		return sqlParam;
	}
	public void setSqlParam(List sqlParam) {
		this.sqlParam = sqlParam;
	}
}
