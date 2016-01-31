package com.zxt.compplatform.acegi.util;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.ConfigAttributeEditor;
import org.springframework.security.intercept.web.DefaultFilterInvocationDefinitionSource;
import org.springframework.security.intercept.web.FilterInvocationDefinitionSource;
import org.springframework.security.intercept.web.RequestKey;
import org.springframework.security.util.AntUrlPathMatcher;
import org.springframework.security.util.UrlMatcher;
/**
 * 
 * 系统资源管理类
 * @author 007
 *
 */
public class JdbcFilterInvocationDefinitionSourceFactoryBean
extends JdbcDaoSupport implements FactoryBean {
	
	private String resourceQuery;
	
	public boolean isSingleton() {
		return true;
	}
	
	public Class getObjectType() {
		return FilterInvocationDefinitionSource.class;
	}
	public Object getObject() {
		return new DefaultFilterInvocationDefinitionSource(this
		.getUrlMatcher(), this.buildRequestMap());
	}
	
	/**
	 * 根据数据源和查询语句，查询资源列表
	 * @return
	 */
	protected List findResources() {
		ResourceMapping resourceMapping = new ResourceMapping(getDataSource(),
				resourceQuery);
		return resourceMapping.execute();
	}
	/**
	 * 构建请求集合
	 * @return
	 */
	protected LinkedHashMap buildRequestMap() {
		LinkedHashMap requestMap = null;
		requestMap = new LinkedHashMap();
		ConfigAttributeEditor editor = new ConfigAttributeEditor();
		List resourceList = this.findResources();
		//for (Resource resource : resourceList) {
		//	}
		for (int i=0;i< resourceList.size();i++) {
			Resource resource =(Resource)resourceList.get(i);
			RequestKey key = new RequestKey(resource.getUrl(), null);
			editor.setAsText(resource.getRole());
			requestMap.put(key,
			(ConfigAttributeDefinition) editor.getValue());
		}
		return requestMap;
	}
	/**
	 * 定义新的URLMatcher实体
	 * @return
	 */
	protected UrlMatcher getUrlMatcher() {
		return new AntUrlPathMatcher();
	}
	public void setResourceQuery(String resourceQuery) {
		this.resourceQuery = resourceQuery;
	}
	/**
	 * 资源实体
	 * @author 007
	 */
	private class Resource {
		private String url;
		private String role;
		public Resource(String url, String role) {
			this.url = url;
			this.role = role;
		}
		public String getUrl() {
			return url;
		}
		public String getRole() {
			return role;
		}
	}
	/**
	 * 资源映射
	 * @author 007
	 */
	private class ResourceMapping extends MappingSqlQuery {
		protected ResourceMapping(DataSource dataSource,
		String resourceQuery) {
			super(dataSource, resourceQuery);
			compile();
		}
		/* (non-Javadoc)
		 * @see org.springframework.jdbc.object.MappingSqlQuery#mapRow(java.sql.ResultSet, int)
		 */
		protected Object mapRow(ResultSet rs, int rownum)
		throws SQLException {
			String url = rs.getString(1);
			String role = rs.getString(2);
			Resource resource = new Resource(url, role);
			return resource;
		}
	}
}