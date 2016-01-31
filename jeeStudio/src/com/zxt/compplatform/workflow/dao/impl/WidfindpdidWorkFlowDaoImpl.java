package com.zxt.compplatform.workflow.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.synchrobit.synchroflow.api.bean.ActivityInsBean;
import com.synchrobit.synchroflow.api.bean.WorkitemBean;
import com.synchrobit.synchroflow.enactmentservice.exception.DBException;
import com.zxt.compplatform.workflow.dao.WidfindpdidWorkFlowDao;
import com.zxt.compplatform.workflow.entity.Workitem;

public class WidfindpdidWorkFlowDaoImpl implements WidfindpdidWorkFlowDao {
	private JdbcTemplate jdbcTemplate;
	private DataSource initDataSource;

	/**
	 * 通过工作项id得到 活动定义id 和流程定义id
	 * 
	 * @param workitemId
	 * @return
	 */
	public Workitem widfindpdid(int workitemId) {

		WorkitemBean wib = new WorkitemBean();
		wib.setWorkitemId(workitemId);
		Workitem wt = null;
		try {
			wib.init();
			int activityInsId = wib.getActivityInsId();
			ActivityInsBean atib = new ActivityInsBean();
			atib.setActivityInsId(activityInsId);
			atib.init();
			int aidi = atib.getActivityDefId();// 活动定义id
			int processDefId = atib.getProcessDefId(); // 流程定义id
			String formId = "-1";

			PreparedStatement pstm = null;
			Connection conn = null;
			ResultSet rs = null;

			jdbcTemplate.setDataSource(initDataSource);
			conn = jdbcTemplate.getDataSource().getConnection();
			String sql = " select TF_ID, PRO_INS_ID, TASK_ID, FORM_ID "
					+ " from eng_form_task "
					+ " where PRO_INS_ID = ? and TASK_ID=? ";

			pstm = conn.prepareStatement(sql);
			// pstm.setString(1, processDefId);
			// pstm.setString(2, formID);
			rs = pstm.executeQuery();
			if (rs.next()) {
				formId = rs.getString("FORM_ID");
			}

			wt = new Workitem();
			wt.setProcessDefName(wib.getProcessDefName()); // 过程定义名
			wt.setActivityInsName(wib.getActivityInsName());// 活动实例名
			wt.setCreatedTime(wib.getCreatedTime());// 工作项创建时间
			wt.setWorkitemId(wib.getWorkitemId());// 工作项id
			wt.setArgs(wib.getArgs());// dates
			wt.setFormId(formId);// 项目id
			return wt;
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return wt;

	}

	/**
	 * 通过工作项id得到 活动定义id 和流程定义id
	 * 
	 * @param workitemId
	 * @return String[] workItemInfo 工作项所对应的活动定义id和流程定义id
	 */

	public String[] workItemInfo(int workitemId) {
		WorkitemBean wib = new WorkitemBean();
		wib.setWorkitemId(workitemId);
		String[] workItemInfo = new String[2];
		try {
			wib.init();
			int activityInsId = wib.getActivityInsId();
			ActivityInsBean atib = new ActivityInsBean();
			atib.setActivityInsId(activityInsId);
			atib.init();
			int aidi = atib.getActivityDefId();// 活动定义id
			int processDefId = atib.getProcessDefId(); // 流程定义id
			workItemInfo[0] = processDefId + "";
			workItemInfo[1] = aidi + "";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return workItemInfo;
	}
	/**
	 * 通过workitemId find 工作流中的APP_ID
	 * 
	 * @param workitemId
	 * @return int APP_ID
	 */
	public String findAppid(int workitemId) {
		WorkitemBean wb=new WorkitemBean();
		wb.setWorkitemId(workitemId);
		try {
			wb.init();
			Object[][] o=wb.getArgs();//拿到工作项对应的APP_ID  即工作流上一节点传入的参数
			String appid="";
			try {
				appid =(String) o[0][1];
			} catch (RuntimeException e) {
				e.printStackTrace();
				System.out.println("APP_ID参数错误");
			}
			return appid;
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public String findsgx(int workitemId) {
		WorkitemBean wb=new WorkitemBean();
		wb.setWorkitemId(workitemId);
		try {
			wb.init();
			Object[][] o=wb.getArgs();//拿到工作项对应的APP_ID  即工作流上一节点传入的参数
			String sgx="";
			try {
				sgx =(String) o[1][1];
			} catch (RuntimeException e) {
				e.printStackTrace();
				System.out.println("sgx参数错误");
			}
			return sgx;
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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
