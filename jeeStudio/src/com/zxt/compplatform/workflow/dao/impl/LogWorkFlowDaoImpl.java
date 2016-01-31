package com.zxt.compplatform.workflow.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.zxt.compplatform.workflow.dao.LogWorkFlowDao;
import com.zxt.compplatform.workflow.entity.WorkFlowDataStauts;
import com.zxt.framework.common.util.RandomGUID;

public class LogWorkFlowDaoImpl implements LogWorkFlowDao {

	private JdbcTemplate jdbcTemplate;
	/**
	 * 基础数据源
	 */
	private DataSource initDataSource;
	
	public void addWorkFlowLog(String userID, String currentTime,String app_id,WorkFlowDataStauts workflowDataStauts, String processDefId, String workitemId) {
		String sql = "insert into t_process_log(log_id,mid,user_id,deal_time,pioneer_status,pioneer_operate,app_id,process_def_id,workitemid) values(?,?,?,?,?,?,?,?,?)";
		jdbcTemplate.setDataSource(initDataSource);
		jdbcTemplate.update(sql, new Object[]{RandomGUID.geneGuid(),workflowDataStauts.getMid()
											,userID,currentTime,workflowDataStauts.getToTransferDefStauts_text()
											,workflowDataStauts.getToTransferDefStautsText(),app_id,processDefId,workitemId});
	}
	
	public void addWorkFlowLogETC(String mid,String userId,String currentTime,String pioneer_status,String pioneer_operate,String app_id,String processDefId, String workitemId) {
		String sql = "insert into t_process_log(log_id,mid,user_id,deal_time,pioneer_status,pioneer_operate,app_id,process_def_id,workitemid) values(?,?,?,?,?,?,?,?,?)";
		jdbcTemplate.setDataSource(initDataSource);
		jdbcTemplate.update(sql, new Object[]{RandomGUID.geneGuid(),mid
											,userId,currentTime,pioneer_status
											,pioneer_operate,app_id,processDefId,workitemId});
	}
	
	/**
	 * 查询流程日志详情
	 */
	public List findWorkFlowLogByAppID(String app_id){
		String sql = "select t_process_log.*,T_USERTABLE.uname from t_process_log left join T_USERTABLE on " +
				"t_process_log.user_id = T_USERTABLE.USERID where APP_ID='"+app_id+"' order by deal_time";
		jdbcTemplate.setDataSource(initDataSource);
		return jdbcTemplate.queryForList(sql);
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

}
