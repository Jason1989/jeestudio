package com.zxt.compplatform.workflow.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.zxt.compplatform.formengine.entity.view.Tab;
import com.zxt.compplatform.workflow.Util.WorkFlowUtil;
import com.zxt.compplatform.workflow.dao.ActivityTabDao;
import com.zxt.compplatform.workflow.entity.ActivityTabSettingVo;
import com.zxt.compplatform.workflow.entity.EngActionWorkflow;
import com.zxt.compplatform.workflow.entity.WorkFlowDataStauts;
import com.zxt.framework.common.util.RandomGUID;

public class ActivityTabDaoImpl implements ActivityTabDao {
	private JdbcTemplate jdbcTemplate;
	/**
	 * 基础数据源
	 */
	private DataSource initDataSource;

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

	public List findActivityTabList(String[] workflowParmer) {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		Tab tab = null;

		String sql = " SELECT NODE_SET_ID, TAB_TITLE,TAB_URL,TAB_SORT,IS_MAIN_TABLE  FROM ENG_NODETAB_SET "
				+ " where mid=?  and FORMTRANSFER_ID=? order  by TAB_SORT ";

		jdbcTemplate.setDataSource(initDataSource);
		SqlRowSet temRowSet = jdbcTemplate.queryForRowSet(sql, new Object[] {
				workflowParmer[0], workflowParmer[1] });

		if (temRowSet != null) {
			while (temRowSet.next()) {
				tab = new Tab();
				if (temRowSet.getString("TAB_TITLE") != null) {
					tab.setTitle(temRowSet.getString("TAB_TITLE"));
				}
				if (temRowSet.getString("TAB_URL") != null) {
					tab.setUrl(temRowSet.getString("TAB_URL"));
				}
				if (temRowSet.getString("NODE_SET_ID") != null) {
					tab.setTabId(temRowSet.getString("NODE_SET_ID"));
				}
				if (temRowSet.getString("IS_MAIN_TABLE") != null) {
					tab.setIsMainTable(temRowSet.getString("IS_MAIN_TABLE"));
				}
				tab.setSortIndex(temRowSet.getInt("TAB_SORT"));
				list.add(tab);
			}
		}
		return list;
	}

	public ActivityTabSettingVo loadActivityConfig(String id) {
		// TODO Auto-generated method stub
		String sql = "SELECT MID,PROCESS_ID,ACTIVITY_ID,FORMTRANSFER_ID,TAB_URL,TAB_TITLE,TAB_SORT,NODE_SET_ID,IS_MAIN_TABLE"
				+ " FROM ENG_NODETAB_SET where NODE_SET_ID=? ";
		ActivityTabSettingVo activityTabSettingVo = null;
		jdbcTemplate.setDataSource(initDataSource);

		SqlRowSet temSet = jdbcTemplate
				.queryForRowSet(sql, new Object[] { id });

		if (temSet != null) {
			if (temSet.next()) {
				activityTabSettingVo = new ActivityTabSettingVo();
				if (temSet.getString("NODE_SET_ID") != null) {
					activityTabSettingVo.setId(temSet.getString("NODE_SET_ID"));
				}
				if (temSet.getString("TAB_URL") != null) {
					activityTabSettingVo.setUrl(temSet.getString("TAB_URL"));
				}
				if (temSet.getString("TAB_TITLE") != null) {
					activityTabSettingVo
							.setTitle(temSet.getString("TAB_TITLE"));
				}
				if (temSet.getString("TAB_SORT") != null) {
					activityTabSettingVo.setSortIndex(temSet
							.getString("TAB_SORT"));
				}
				if (temSet.getString("IS_MAIN_TABLE") != null) {
					activityTabSettingVo.setIsMainTable(temSet
							.getString("IS_MAIN_TABLE"));
				}
			}
		}
		return activityTabSettingVo;
	}

	public String deleteActivityConfig(String id) {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM ENG_NODETAB_SET WHERE NODE_SET_ID=? ";
		jdbcTemplate.setDataSource(initDataSource);
		jdbcTemplate.update(sql, new Object[] { id });
		return null;
	}

	public String saveActivityConfig(ActivityTabSettingVo activityTabSettingVo) {
		// TODO Auto-generated method stub
		try {
			String sql = " INSERT INTO ENG_NODETAB_SET  ("
				+ "MID," 
				+ "PROCESS_ID," 
				+ "ACTIVITY_ID," 
				+ "FORMTRANSFER_ID," 
				+ "TAB_URL," 
				+ "TAB_TITLE," 
				+ "TAB_SORT," 
				+ "NODE_SET_ID,IS_MAIN_TABLE )"
				+ " VALUES (?,?,?,?,?,?,?,?,?) ";
				if ((activityTabSettingVo.getId() != null)
						&& (!"".equals(activityTabSettingVo.getId()))) {
					deleteActivityConfig(activityTabSettingVo.getId());
				}
				jdbcTemplate.setDataSource(initDataSource);
				jdbcTemplate.update(sql, new Object[] { activityTabSettingVo.getMid(),
						activityTabSettingVo.getProcessId(),
						activityTabSettingVo.getActivityId(),
						activityTabSettingVo.getToActivityId(),
		
						activityTabSettingVo.getUrl(), activityTabSettingVo.getTitle(),
						activityTabSettingVo.getSortIndex(), RandomGUID.geneGuid(),activityTabSettingVo.getIsMainTable() });

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return null;
	}

	public List findEngEditForm() {
		// TODO Auto-generated method stub
		String sql = "SELECT FO_ID,FO_NAME FROM ENG_FORM_FORM  where FO_FTYPE='editpage' ";
		jdbcTemplate.setDataSource(initDataSource);
		return jdbcTemplate.queryForList(sql);
	}

	public List findWorkItemIdByServiceID(String APP_ID, String userId) throws Exception {
		// TODO Auto-generated method stub
		WorkFlowDataStauts  workFlowDataStauts=new WorkFlowDataStauts();
		String sql =  " SELECT   tw.workitem_id,tw.m_id,tw.process_def_id,ta.activity_def_id "
					+ " FROM t_relevant_data  td "
					
					+ " LEFT join t_workitem  tw "
					+ " ON td.process_ins_id=tw.process_ins_id  "
					
					+ " LEFT join t_activity_ins  ta "
					+ " ON tw.activity_ins_id=ta.activity_ins_id "  
					
					+ " WHERE td.data_value=? and tw.participant=?";
				SqlRowSet temSet =jdbcTemplate.queryForRowSet(sql, new Object[] { APP_ID, userId });
				if (temSet!=null) {
					if (temSet.next()) {
						if (temSet.getString("workitem_id")!=null) {
							workFlowDataStauts.setPreWorkItemId(temSet.getString("workitem_id"));
						}
						if (temSet.getString("m_id")!=null) {
							workFlowDataStauts.setMid(temSet.getString("m_id"));
						}
						if (temSet.getString("process_def_id")!=null) {
							workFlowDataStauts.setProcessDefId(temSet.getString("process_def_id"));
						}
						if (temSet.getString("activity_def_id")!=null) {
							workFlowDataStauts.setActivityDefId(temSet.getString("activity_def_id"));
						}
					}
				}
		/**
		 * 获取前驱状态
		 */
		List list=	WorkFlowUtil.findBranchByActivity(workFlowDataStauts);
		return list;
	}
	
	public EngActionWorkflow getEngActionWorkflow(String processId, String precursor){
		EngActionWorkflow data = new EngActionWorkflow();
		String sql = "select id,is_bundling,process_id,precursor,context,remark from eng_action_workflow where process_id=? and precursor=?";
		SqlRowSet temSet = jdbcTemplate.queryForRowSet(sql, new Object[] { processId, precursor });
		if (temSet!=null) {
			if (temSet.next()) {
				if (temSet.getString("id")!=null) {
					data.setId(temSet.getString("id"));
				}
				if (temSet.getString("is_bundling")!=null) {
					data.setIsBundling(temSet.getString("is_bundling"));
				}
				if (temSet.getString("process_id")!=null) {
					data.setProcessId(temSet.getString("process_id"));
				}
				if (temSet.getString("precursor")!=null) {
					data.setPrecursor(temSet.getString("precursor"));
				}
				if (temSet.getString("context")!=null) {
					data.setContext(temSet.getString("context"));
				}
				if (temSet.getString("remark")!=null) {
					data.setRemark(temSet.getString("remark"));
				}
			}
		}
		return data;
	}
	
	public void delEngActionWorkflow(String id){
		String sql = "delete eng_action_workflow where id=?";
		jdbcTemplate.update(sql, new String[]{id});
	}
	
	public void delEngActionWorkflow(String processId,String precursor){
		String sql = "delete eng_action_workflow where process_id=? and precursor=?";
		jdbcTemplate.update(sql, new String[]{processId,precursor});
	}
	
	public void saveEngActionWorkflow(EngActionWorkflow data){
		if( data == null ){
			return;
		}
		if( StringUtils.isNotBlank(data.getId()) ){
			this.delEngActionWorkflow(data.getId());
		}
		this.delEngActionWorkflow(data.getProcessId(), data.getPrecursor());
		String sql = " INSERT INTO eng_action_workflow("
			+ "id," 
			+ "is_bundling,"
			+ "process_id," 
			+ "precursor," 
			+ "context," 
			+ "remark )"
			+ " VALUES (?,?,?,?,?,?) ";
		data.setId(RandomGUID.geneGuid());
		Object[] param = new Object[] { 
				data.getId(),
				data.getIsBundling(),
				data.getProcessId(),
				data.getPrecursor(),
				data.getContext(),
				data.getRemark()
		};
		jdbcTemplate.update(sql, param);
	}
	
}