package com.zxt.compplatform.datasource.entity;

import java.util.Map;

import com.zxt.framework.common.entity.BasicEntity;

/**
 * 数据源实体
 * 
 * @author 007
 */
public class DataSource extends BasicEntity {

	private static final long serialVersionUID = 1L;
	/**
	 * 数据源主键
	 */
	private String id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 数据源类型 1：oracle 2：sqlserver
	 */
	private String dbType;
	/**
	 * 数据源地址
	 */
	private String ipAddress;
	/**
	 * 端口
	 */
	private Integer port;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 状态
	 */
	private String state;
	/**
	 * 排序号
	 */
	private Integer sortNo;
	/**
	 * 实例名称
	 */
	private String sid;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 访问类型
	 */
	private String dataType, accessType;
	/**
	 * 参数集合
	 */
	private Map parameterMap;

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

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getAccessType() {
		return accessType;
	}

	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}

	public Map getParameterMap() {
		return parameterMap;
	}

	public void setParameterMap(Map parameterMap) {
		this.parameterMap = parameterMap;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
