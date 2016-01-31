package com.zxt.framework.templatefile.dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.zxt.framework.common.util.RandomGUID;
import com.zxt.framework.templatefile.common.TemplateFileType;
import com.zxt.framework.templatefile.dao.FormTemplateFileDao;
import com.zxt.framework.templatefile.entity.FormTemplateFile;

public class FormTemplateFileDaoImpl implements FormTemplateFileDao {
	
	private JdbcTemplate jdbcTemplate;
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	/**
	 * 获取数据对象列表
	 * @param dgId 数据对象分组ID，页号，每页大小
	 * @return
	 */
	public List list(String dgId,int page,int size){
		String sql = "select * from eng_form_templatefile where 1=1";
		if( StringUtils.isNotBlank(dgId)){
			dgId = StringEscapeUtils.escapeSql(dgId);
			sql += " and dg_id='"+dgId+"'";
		}
		sql += " order by update_time desc";
		List list = jdbcTemplate.queryForList(sql);
		return list;
	}
	
	public int count(String dgId){
		String sql = "select count(templatefile_id) from eng_form_templatefile where 1=1";
		if( StringUtils.isNotBlank(dgId)){
			dgId = StringEscapeUtils.escapeSql(dgId);
			sql += " and dg_id='"+dgId+"'";
		}
		int count = jdbcTemplate.queryForInt(sql);
		return count;
	}
	/**
	 * 获取模板键值对
	 * @param conn
	 * @return
	 */
	public Map get(String id){
		if( StringUtils.isBlank(id) ){
			return null;
		}else{
			id = StringEscapeUtils.escapeSql(id);
		}
		String sql = "select * from eng_form_templatefile where templatefile_id='"+id+"'";
		Map map = jdbcTemplate.queryForMap(sql);
		return map;
	}
	/**
	 * 判断模板是否重复
	 * @param conn
	 * @return
	 */
	public boolean exists(String name){
		if( StringUtils.isBlank(name) ){
			return false;
		}else{
			name = StringEscapeUtils.escapeSql(name);
		}
		String sql = "select templatefile_id from eng_form_templatefile where templatefile_name='"+name+"'";
		//Map map = jdbcTemplate.queryForMap(sql);
		SqlRowSet set = jdbcTemplate.queryForRowSet(sql);
		if( set!=null && set.next() ){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 删除模板
	 * @param id 模板id
	 * @return
	 */
	public String delete(String id){
		if( StringUtils.isBlank(id) ){
			return StringUtils.EMPTY;
		}else{
			id = StringEscapeUtils.escapeSql(id);
		}
		String sql = "delete eng_form_templatefile where templatefile_id='"+id+"'";
		jdbcTemplate.update(sql);
		return StringUtils.EMPTY;
	}
	/**
	 * 保存表单模板
	 * @param data 模板数据
	 * @return
	 */
	public String saveOrUpdate(FormTemplateFile data){
		if( data == null ){
			return StringUtils.EMPTY;
		}
		String id = data.getId();
		Map map = this.get(id);
		if( MapUtils.isEmpty(map) ){
			return this.save(data);
		}else{
			return this.update(data);
		}
	}
	
	public String save(FormTemplateFile data) {
		if( data==null || data.getData()==null ){
			return StringUtils.EMPTY;
		}
		String sql = "insert into eng_form_templatefile(" +
				"  templatefile_id,dg_id,templatefile_name," +
				"  templatefile_data,templatefile_remark,templatefile_type," +
				"  update_time) " +
				" values(?,?,?,?,?,?,?)";
		String id = RandomGUID.geneGuid();
		data.setId(id);
		data.setType(TemplateFileType.EXPORT_WORD);
		try{
			final File file = data.getData();
			final InputStream is = new FileInputStream(file);
			final LobHandler lobHandler = new DefaultLobHandler();
			final FormTemplateFile DataObj = data;
			jdbcTemplate.execute(sql, new AbstractLobCreatingPreparedStatementCallback(lobHandler){
				protected void setValues(PreparedStatement pstmt, LobCreator lobCreator)
						throws SQLException, DataAccessException {
					pstmt.setString(1, DataObj.getId());
					pstmt.setString(2, DataObj.getDgId());
					pstmt.setString(3, DataObj.getName());
					lobCreator.setBlobAsBinaryStream(pstmt,4,is,(int)(file.length()));
					pstmt.setString(5, DataObj.getRemark());
					pstmt.setString(6, DataObj.getType());
					pstmt.setTimestamp(7, new java.sql.Timestamp(new Date().getTime()));
				} 
			});
			is.close();
		}catch(Exception e){
			e.printStackTrace();
			return StringUtils.EMPTY;
		}
		return id;
	}
	
	public String update(FormTemplateFile data){
		return StringUtils.EMPTY;
	}
}
