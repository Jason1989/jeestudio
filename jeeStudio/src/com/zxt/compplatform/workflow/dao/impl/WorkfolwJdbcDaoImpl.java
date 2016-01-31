package com.zxt.compplatform.workflow.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zxt.compplatform.formengine.service.SystemFrameService;
import com.zxt.compplatform.workflow.dao.WorkfolwJdbcDao;
import com.zxt.compplatform.workflow.entity.TaskFormNodeEntity;

public class WorkfolwJdbcDaoImpl implements  WorkfolwJdbcDao{
	/**
	 * 基础数据源
	 */
	private JdbcTemplate jdbcTemplate;
	private DataSource initDataSource;
	private SystemFrameService systemFrameService;
	private static final Logger log = Logger.getLogger(WorkfolwJdbcDaoImpl.class);
	

	public List excute(){
		 List workfolw=new ArrayList();
		 PreparedStatement pstm = null;
		 ResultSet rs = null;
		 Connection conn=null;
		try {
			conn = jdbcTemplate.getDataSource().getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("select TF_ID,PRO_INS_ID,TASK_ID,FORM_ID,fo_name ");
			sql.append("from eng_form_task ");
            sql.append("left join eng_form_form ");
            sql.append("on eng_form_form.fo_id = form_id");
            pstm = conn.prepareStatement(sql.toString());
			rs = pstm.executeQuery();
			while(rs.next()){
				TaskFormNodeEntity task=new TaskFormNodeEntity();
				task.setTaskFormID(rs.getString("TF_ID"));
				task.setProcessInstanceID(rs.getString("PRO_INS_ID"));
				task.setTaskFormID("TASK_ID");
				task.setFormID("FORM_ID");
				task.setFormName("fo_name");
				workfolw.add(task);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			try {
				conn.close();//放回连接池
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/**
			 * jdbc操作类 切换至初始化根连接池
			 */
			try {
				jdbcTemplate.setDataSource(initDataSource);
			} catch (Exception e) {
				// TODO: handle exception
				log.error("切换初始化连接池 失败...");
			}
		}
		return workfolw;
	}
	public boolean update(TaskFormNodeEntity tfne){
		PreparedStatement pstm = null;
		 ResultSet rs = null;
		 Connection conn=null;
		try {
			conn = jdbcTemplate.getDataSource().getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("update eng_form_task set PRO_INS_ID=?,TASK_ID=?,FORM_ID=? where TF_ID=?");
			pstm = conn.prepareStatement(sql.toString());
			int index=1;
			pstm.setString(index++, tfne.getProcessInstanceID());
			pstm.setString(index++, tfne.getTaskNodeID());
			pstm.setString(index++, tfne.getFormID());
			pstm.setString(index++, tfne.getTaskFormID());
		    int i=pstm.executeUpdate();
		    if(i==0){return false;}else{return true;}
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			try {
				conn.close();//放回连接池
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/**
			 * jdbc操作类 切换至初始化根连接池
			 */
			try {
				jdbcTemplate.setDataSource(initDataSource);
			} catch (Exception e) {
				// TODO: handle exception
				log.error("切换初始化连接池 失败...");
			}
		}
		return false;
	}
	public boolean delete(String i){
		PreparedStatement pstm = null;
		 ResultSet rs = null;
		 Connection conn=null;
		try {
			conn = jdbcTemplate.getDataSource().getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("delete from eng_form_task where TF_ID=?");
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, i);
		    int n=pstm.executeUpdate();
		    if(n==0){return false;}else{return true;}
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			try {
				conn.close();//放回连接池
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/**
			 * jdbc操作类 切换至初始化根连接池
			 */
			try {
				jdbcTemplate.setDataSource(initDataSource);
			} catch (Exception e) {
				// TODO: handle exception
				log.error("切换初始化连接池 失败...");
			}
		}
		return false;
	}
	public boolean insert(TaskFormNodeEntity tfne){
		PreparedStatement pstm = null;
		 ResultSet rs = null;
		 Connection conn=null;
		try {
			conn = jdbcTemplate.getDataSource().getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("insert into eng_form_task(PRO_INS_ID,TASK_ID,FORM_ID) values(?,?,?,?)");
			pstm = conn.prepareStatement(sql.toString());
			int index=1;
			pstm.setString(index++, tfne.getProcessInstanceID());
			pstm.setString(index++, tfne.getTaskNodeID());
			pstm.setString(index++, tfne.getFormID());
		    int i=pstm.executeUpdate();
		    if(i==0){return false;}else{return true;}
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			try {
				conn.close();//放回连接池
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/**
			 * jdbc操作类 切换至初始化根连接池
			 */
			try {
				jdbcTemplate.setDataSource(initDataSource);
			} catch (Exception e) {
				// TODO: handle exception
				log.error("切换初始化连接池 失败...");
			}
		}
		return false;
	}
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	public DataSource getInitDataSource() {
		return initDataSource;
	}
	public void setInitDataSource(DataSource initDataSource) {
		this.initDataSource = initDataSource;
	}
	public SystemFrameService getSystemFrameService() {
		return systemFrameService;
	}
	public void setSystemFrameService(SystemFrameService systemFrameService) {
		this.systemFrameService = systemFrameService;
	}
	
}
