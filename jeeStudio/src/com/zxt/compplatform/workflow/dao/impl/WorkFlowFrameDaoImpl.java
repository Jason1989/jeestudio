package com.zxt.compplatform.workflow.dao.impl;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zxt.compplatform.formengine.entity.view.TreeAttributes;
import com.zxt.compplatform.formengine.entity.view.TreeData;
import com.zxt.compplatform.workflow.dao.WorkFlowFrameDao;
import com.zxt.compplatform.workflow.entity.JsonEntity;
import com.zxt.compplatform.workflow.entity.TaskFormNodeEntity;

public class WorkFlowFrameDaoImpl implements WorkFlowFrameDao{
	private JdbcTemplate jdbcTemplate;
	private DataSource initDataSource;
	private static final Logger log = Logger.getLogger(WorkFlowFrameDaoImpl.class);
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
	public List findTaskFormNodeEntity(){
		List workfolw=new ArrayList();
		 PreparedStatement pstm = null;
		 ResultSet rs = null;
		 Connection conn=null;
		try {
			jdbcTemplate.setDataSource(initDataSource);
			conn = jdbcTemplate.getDataSource().getConnection();
			StringBuffer sql = new StringBuffer();
			/*sql.append("select TF_ID,PRO_INS_ID,TASK_ID,FORM_ID,fo_name ");
			sql.append("from eng_form_task ");
           sql.append("left join eng_form_form ");
           sql.append("on eng_form_form.fo_id = form_id");*/
		   sql.append("select TF_ID,PRO_INS_ID,TASK_ID,FORM_ID,fo_name  from (select PRO_INS_ID ,max(form_id) form_id,max(TASK_ID) TASK_ID,max(TF_ID) TF_ID from eng_form_task group by PRO_INS_ID) eng_form_task"+
 " left join eng_form_form  on eng_form_form.fo_id = form_id");
           pstm = conn.prepareStatement(sql.toString());
			rs = pstm.executeQuery();
			while(rs.next()){
				TaskFormNodeEntity task=new TaskFormNodeEntity();
				task.setTaskFormID(rs.getString("TF_ID"));
				task.setProcessInstanceID(rs.getString("PRO_INS_ID"));
				task.setTaskNodeID(rs.getString("TASK_ID"));
				task.setFormID(rs.getString("FORM_ID"));				
				workfolw.add(task);
			}
		} catch (Exception e) {
			e.printStackTrace();
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
				//jdbcTemplate.setDataSource(initDataSource);
				//jdbcTemplate.getDataSource().getConnection()
			} catch (Exception e) {
				// TODO: handle exception
				log.error("切换初始化连接池 失败...");
			}
		}
		return workfolw;
	}
	public boolean insertTaskFormNodeEntity(TaskFormNodeEntity tfne){
		PreparedStatement pstm = null;
		 Connection conn=null;
		try {
			jdbcTemplate.setDataSource(initDataSource);
			conn = jdbcTemplate.getDataSource().getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("insert into eng_form_task(TF_ID,PRO_INS_ID,TASK_ID,FORM_ID,SORT_INDEX,IS_SHOWTAB) values(?,?,?,?,?,?)");
			pstm = conn.prepareStatement(sql.toString());
			int index=1;
			pstm.setString(index++, tfne.getTaskFormID());
			pstm.setString(index++, tfne.getProcessInstanceID());
			pstm.setString(index++, tfne.getTaskNodeID());
			pstm.setString(index++, tfne.getFormID());
			pstm.setString(index++, tfne.getSortIndex());
			pstm.setString(index++, tfne.getIsShowTab());
		    int i=pstm.executeUpdate();
		    if(i==0){return false;}else{return true;}
		} catch (Exception e) {
			e.printStackTrace();
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
	public boolean updateTaskFormNodeEntity(TaskFormNodeEntity tfne){
		PreparedStatement pstm = null;
		 Connection conn=null;
		try {
			jdbcTemplate.setDataSource(initDataSource);
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
	public boolean updateTaskFormNodeEntityT(TaskFormNodeEntity tfne){
		PreparedStatement pstm = null;
		Connection conn=null;
		try {
			jdbcTemplate.setDataSource(initDataSource);
			conn = jdbcTemplate.getDataSource().getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("update eng_form_task set PRO_INS_ID=?,TASK_ID=?,FORM_ID=?,SORT_INDEX=?,IS_SHOWTAB=? where TF_ID=?");
			pstm = conn.prepareStatement(sql.toString());
			int index=1;
			pstm.setString(index++, tfne.getProcessInstanceID());
			pstm.setString(index++, tfne.getTaskNodeID());
			pstm.setString(index++, tfne.getFormID());
			pstm.setString(index++, tfne.getSortIndex());
			pstm.setString(index++, tfne.getIsShowTab());
			pstm.setString(index++, tfne.getTfId());
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
	public boolean deleteTaskFormNodeEntity(String tf_id){
		PreparedStatement pstm = null;
		 Connection conn=null;
		try {
			jdbcTemplate.setDataSource(initDataSource);
			conn = jdbcTemplate.getDataSource().getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("delete from eng_form_task where TF_ID=?");
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, tf_id);
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
	public int findTotalRows() {
		int i=0;
		PreparedStatement pstm = null;
		 Connection conn=null;
		 ResultSet rs=null; 
		try {
			jdbcTemplate.setDataSource(initDataSource);
			conn = jdbcTemplate.getDataSource().getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("select count(*) from eng_form_task");
			pstm = conn.prepareStatement(sql.toString());
		    rs=pstm.executeQuery();
		    while(rs.next()){
		    	i=rs.getInt(1);
		    }
		    return i;
		} catch (Exception e) {
			e.printStackTrace();
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
		return i;
	}
	public TaskFormNodeEntity findById(String taskFormId){
		TaskFormNodeEntity taskFormNodeEntity =null;
		PreparedStatement pstm = null;
		Connection conn=null;
		ResultSet rs = null;
		
		try {
			jdbcTemplate.setDataSource(initDataSource);
			conn = jdbcTemplate.getDataSource().getConnection();
			String sql=" select t.TF_ID TF_ID,t.PRO_INS_ID PRO_INS_ID,t.TASK_ID TASK_ID,t.IS_SHOWTAB IS_SHOWTAB,t.FORM_ID FORM_ID,t.SORT_INDEX SORT_INDEX,t.SORT_INDEX SORT_INDEX,f.fo_name FormName from eng_form_task t join ENG_FORM_FORM f on t.form_id = f.fo_id and TF_ID =? ";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, taskFormId);
			rs = pstm.executeQuery();

			if (rs.next()) {
				taskFormNodeEntity=new TaskFormNodeEntity();
				taskFormNodeEntity.setTaskFormID(rs.getString("TF_ID"));
				taskFormNodeEntity.setProcessInstanceID(rs.getString("PRO_INS_ID"));
				taskFormNodeEntity.setTaskNodeID(rs.getString("TASK_ID"));
				taskFormNodeEntity.setFormID(rs.getString("FORM_ID"));
				taskFormNodeEntity.setSortIndex(rs.getString("SORT_INDEX"));
				taskFormNodeEntity.setIsShowTab(rs.getString("IS_SHOWTAB"));
				taskFormNodeEntity.setFormName(rs.getString("FormName"));
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return taskFormNodeEntity;
	}
	
	public List findTaskFormByNode(int id,String processInstanceID){
		
		PreparedStatement pstm = null;
		Connection conn=null;
		ResultSet rs = null;
		PreparedStatement pstm1 = null;
		ResultSet rs1 = null;
		List list = new ArrayList();
		try {
			jdbcTemplate.setDataSource(initDataSource);
			conn = jdbcTemplate.getDataSource().getConnection();
			DatabaseMetaData dbmd = conn.getMetaData();
			String url = dbmd.getURL();
			String sql = "";
			sql = "select task.*,form.FO_NAME FO_NAME from (select * from eng_form_task where TASK_ID = ? and PRO_INS_ID = ?) task left join eng_form_form form on task.form_id = form.fo_id  order by task.PRO_INS_ID ,abs(task.SORT_INDEX)";
			//String sql=" select TF_ID, FORM_ID, SORT_INDEX, IS_SHOWTAB from eng_form_task where TASK_ID = ? and PRO_INS_ID = ? order by PRO_INS_ID ,abs(SORT_INDEX)";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, id+"");
			pstm.setString(2, processInstanceID);
			rs = pstm.executeQuery();

			while(rs.next()) {
				TaskFormNodeEntity taskFormNodeEntity=new TaskFormNodeEntity();
				taskFormNodeEntity.setFormID(rs.getString("FORM_ID"));
				if(url.indexOf("sqlserver")!=-1){
					sql = "select (t2.dg_name+'/'+t1.do_name+'/'+t3.fo_name) FO_NAME from eng_form_dataobject t1 join eng_form_dataobject_group t2  on t1.do_group_id = t2.dg_id join eng_form_form t3 on t3.fo_do_id = t1.do_id and t3.fo_id = ?";
				}else{
					sql = "select (t2.dg_name||'/'||t1.do_name||'/'||t3.fo_name) FO_NAME from eng_form_dataobject t1 join eng_form_dataobject_group t2  on t1.do_group_id = t2.dg_id join eng_form_form t3 on t3.fo_do_id = t1.do_id and t3.fo_id = ?";
				}
				pstm1 = conn.prepareStatement(sql);
				pstm1.setString(1, rs.getString("FORM_ID"));
				rs1 = pstm1.executeQuery();
				while(rs1.next()){
					taskFormNodeEntity.setFormName(rs1.getString("FO_NAME"));
				}
				//taskFormNodeEntity.setFormName(rs.getString("FO_NAME"));
				taskFormNodeEntity.setTfId(rs.getString("TF_ID"));
				taskFormNodeEntity.setSortIndex(rs.getString("SORT_INDEX"));
				taskFormNodeEntity.setIsShowTab(rs.getString("IS_SHOWTAB"));
				list.add(taskFormNodeEntity);
			}
	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
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
		
		return list;
	}
	/**
	 * 获取表单树
	 */
	public List getWorkFlowTabPage(){
		PreparedStatement pstm = null;
		Connection conn=null;
		ResultSet rs = null;
		List listgroup = new ArrayList();
		List listData = new ArrayList();
		jdbcTemplate.setDataSource(initDataSource);
		try {
			conn = jdbcTemplate.getDataSource().getConnection();
			String sql = "select DG_ID,DG_NAME from eng_form_dataobject_group";
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while(rs.next()){
				JsonEntity jet = new JsonEntity();
				jet.setId(rs.getString("DG_ID"));
				jet.setText(rs.getString("DG_NAME"));
				jet.setState("closed");
				TreeData treeData = new TreeData();
				treeData.setId(rs.getString("DG_ID"));
				treeData.setText(rs.getString("DG_NAME"));
				treeData.setParentID("-1");
				treeData.setLevel("1");
				TreeAttributes attributes = new TreeAttributes();
				attributes.setUrl("1");
				treeData.setAttributes(attributes);
				//BeanUtils.populate(bean, properties)
				listgroup.add(jet);
				listData.add(treeData);
			}
			for(int i = 0;i<listgroup.size();i++){
				JsonEntity je = (JsonEntity) listgroup.get(i);
				String id = je.getId();
				sql = "select DO_ID,DO_NAME from eng_form_dataobject where do_group_id = '"+id+"'";
				pstm = conn.prepareStatement(sql);
				rs = pstm.executeQuery();
				List listobject = new ArrayList();
				while(rs.next()){
					JsonEntity jeo = new JsonEntity();
					jeo.setId(rs.getString("DO_ID"));
					jeo.setText(rs.getString("DO_NAME"));
					jeo.setState("closed");
					TreeData treeData1 = new TreeData();
					treeData1.setId(jeo.getId());
					treeData1.setText(jeo.getText());
					treeData1.setParentID(id);
					treeData1.setLevel("2");
					TreeAttributes attributes = new TreeAttributes();
					attributes.setUrl("2");
					treeData1.setAttributes(attributes);
					listData.add(treeData1);
					listobject.add(jeo);
				}
				for(int j = 0;j<listobject.size();j++){
					JsonEntity jef = (JsonEntity) listobject.get(j);
					String idf = jef.getId();
					sql = "select FO_ID,FO_NAME from eng_form_form where FO_DO_ID = '"+idf+"'";
					pstm = conn.prepareStatement(sql);
					rs = pstm.executeQuery();
					List listform = new ArrayList();
					while(rs.next()){
						JsonEntity jeof = new JsonEntity();
						jeof.setId(rs.getString("FO_ID"));
						jeof.setText(rs.getString("FO_NAME"));
						listform.add(jeof);
						TreeData treeData2 = new TreeData();
						treeData2.setId(jeof.getId());
						treeData2.setText(jeof.getText());
						treeData2.setParentID(idf);
						treeData2.setLevel("3");
						TreeAttributes attributes = new TreeAttributes();
						attributes.setUrl("3");
						treeData2.setAttributes(attributes);
						listData.add(treeData2);
					}
					if(listform!=null&&listform.size()>=1){
						jef.setChildren(listform);
						
					}else{
						jef.setId(null);
					}
				}
				if(listobject!=null&&listobject.size()>=1){
					je.setChildren(listobject);
				}else{
					je.setId(null);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
		
		//return listgroup;
		return listData;
	}
	
	/**
	 * 获取表单树
	 */
	public List getWorkFlowTabPage888(){
		PreparedStatement pstm = null;
		Connection conn=null;
		ResultSet rs = null;
		List listgroup = new ArrayList();
		List listgroup1 = new ArrayList();
		List listData = new ArrayList();
		jdbcTemplate.setDataSource(initDataSource);
		String stId="";
		try {
			conn = jdbcTemplate.getDataSource().getConnection();
			String sql = "select DG_ID,DG_NAME from eng_form_dataobject_group where  DG_PARENT_ID= '1'";
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while(rs.next()){
				TreeData treeData = new TreeData();
				treeData.setId(rs.getString("DG_ID"));
				treeData.setText(rs.getString("DG_NAME"));
				treeData.setParentID("-1");
				//treeData.setLevel("1");
				treeData.setFlag("1");
				listgroup.add(treeData);
			}
			for(int i = 0;i<listgroup.size();i++){
				TreeData je = (TreeData) listgroup.get(i);
				String id = je.getId();
				sql = "select DO_ID,DO_NAME from eng_form_dataobject where do_group_id = '"+id+"'";
				pstm = conn.prepareStatement(sql);
				rs = pstm.executeQuery();
				List listobject = new ArrayList();
				List listobject1 = new ArrayList();
				while(rs.next()){
					TreeData treeData1 = new TreeData();
					treeData1.setId(rs.getString("DO_ID"));
					treeData1.setText(rs.getString("DO_NAME"));
					treeData1.setParentID(id);
					//treeData1.setLevel("1");
					treeData1.setFlag("1");
					listobject.add(treeData1);
				}
				for(int j = 0;j<listobject.size();j++){
					TreeData jef = (TreeData) listobject.get(j);
					String idf = jef.getId();
					sql = "select FO_ID,FO_NAME from eng_form_form where FO_DO_ID = '"+idf+"'";
					pstm = conn.prepareStatement(sql);
					rs = pstm.executeQuery();
					List listform = new ArrayList();
					while(rs.next()){
						TreeData treeData2 = new TreeData();
						treeData2.setId(rs.getString("FO_ID"));
						treeData2.setText(rs.getString("FO_NAME"));
						treeData2.setParentID(idf);
						//treeData2.setLevel("2");
						treeData2.setFlag("1");
						listData.add(treeData2);
						listform.add(treeData2);
					}
					if(listform==null||listform.size()<=0){
						for(int g=0;g<listobject.size();g++){
							TreeData tr=(TreeData)listobject.get(g);
							if(tr.getId().equals(idf)){
								
								jef.setFlag("0");
								continue;
							}else{
								listobject1.add(tr);
							}
						}
						
					}
				}
				int m=0;
				String parentId="";
				for(int k=0;k<listobject1.size();k++){
					TreeData jef = (TreeData) listobject1.get(k);
					parentId=jef.getParentID();
					if("0".equals(jef.getFlag())){
						m++;
					}
					//listData.add(listobject1.get(k));
				}
				if(m!=listobject1.size()){
					for(int k=0;k<listobject1.size();k++){
						TreeData jef1 = (TreeData) listobject1.get(k);
						
						if(!"0".equals(jef1.getFlag())){
							listData.add(jef1);
						}
						
					}
				}else{
					stId+=parentId+",";
				}
				if(listobject==null||listobject.size()<=0){
					for(int g=0;g<listgroup.size();g++){
						TreeData tr=(TreeData)listgroup.get(g);
						if(tr.getId().equals(id)||stId.indexOf(tr.getId())!=-1){
							continue;
						}else{
							listgroup1.add(tr);
						}
					}
				}
			}
			for(int i=0;i<listgroup1.size();i++){
				listData.add(listgroup1.get(i));
			}
			
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//return listgroup;
		return listData;
	}
	
	
	/**
	 * formId获取流程ID
	 */
	public TaskFormNodeEntity findTaskFormNodeEntity(String formID) {
		// TODO Auto-generated method stub
		TaskFormNodeEntity taskFormNodeEntity =null;
		
		PreparedStatement pstm = null;
		Connection conn=null;
		ResultSet rs = null;
		
		try {
			jdbcTemplate.setDataSource(initDataSource);
			conn = jdbcTemplate.getDataSource().getConnection();
			String sql=" select TF_ID, PRO_INS_ID, TASK_ID, FORM_ID, fo_name " 
				+ " from eng_form_task "
			    + " left join eng_form_form "
			    + " on fo_id=FORM_ID "
			    + " where FORM_ID = ? ";
			 
			
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, formID);
			rs = pstm.executeQuery();

			if (rs.next()) {
				taskFormNodeEntity=new TaskFormNodeEntity();
				taskFormNodeEntity.setTaskFormID(rs.getString("TF_ID"));
				taskFormNodeEntity.setProcessInstanceID(rs.getString("PRO_INS_ID"));
				taskFormNodeEntity.setTaskNodeID(rs.getString("TASK_ID"));
				taskFormNodeEntity.setFormID(rs.getString("FORM_ID"));
				taskFormNodeEntity.setFormName(rs.getString("fo_name"));
			}
	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return taskFormNodeEntity;
	}
}
