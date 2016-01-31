package com.zxt.compplatform.workflow.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.synchrobit.synchroflow.api.bean.ActivityDefBean;
import com.synchrobit.synchroflow.api.bean.ActivityInsBean;
import com.synchrobit.synchroflow.api.bean.ModelBean;
import com.synchrobit.synchroflow.api.bean.ProcessDefBean;
import com.synchrobit.synchroflow.api.bean.SynchroFLOWBean;
import com.synchrobit.synchroflow.api.bean.TransitionDefBean;
import com.synchrobit.synchroflow.api.bean.WorkitemBean;
import com.synchrobit.synchroflow.api.util.DbTools;
import com.synchrobit.synchroflow.api.util.ReadPropertyConfig;
import com.synchrobit.synchroflow.enactmentservice.exception.DBException;
import com.zxt.compplatform.formengine.entity.view.AuthorityButton;
import com.zxt.compplatform.formengine.entity.view.EventForButton;
import com.zxt.compplatform.formengine.entity.view.JSFunction;
import com.zxt.compplatform.formengine.entity.view.Param;
import com.zxt.compplatform.workflow.entity.WorkFlowDataStauts;

public class WorkFlowUtil {
	private static final Logger log = Logger.getLogger(WorkFlowUtil.class);

	public static void main(String[] args) {
		// backAllTransByWorkItemId(147);
		// backAllParticipantByactivityDefId(2);
		//findInitActivity("1886903799");
	}

	/**
	 * 通过代办项的主键，取得所有线的注解
	 */
	public static List backAllTransByWorkItemId(int workItemId) {
		WorkitemBean wb = new WorkitemBean();
		wb.setWorkitemId(workItemId);
		try {
			wb.init();
			wb.getActivityInsId();
			int activityInsId = wb.getActivityInsId();
			ActivityInsBean activity = new ActivityInsBean();
			activity.setActivityInsId(activityInsId);
			activity.init();
			int activityDefId = activity.getActivityDefId();
			ProcessDefBean pb = new ProcessDefBean();
			pb.setModelId(wb.getMId());
			pb.setProcessDefId(wb.getProcessDefId());
			pb.init();
			List ls = pb.getTransitionDefList();
			List list_desc = new ArrayList();
			for (int i = 0; i < ls.size(); i++) {
				TransitionDefBean td = (TransitionDefBean) ls.get(i);
				/**
				 * activityDefId == td.getFromActivityId()前驱 activityDefId ==
				 * td.getToActivityId() 后继
				 */

				if (activityDefId == td.getFromActivityId()
						|| activityDefId == td.getToActivityId()) {
					list_desc.add(td.getDesc());

					/**
					 * 转移条件
					 */
					System.out.println("getCondition():  " + td.getCondition());
					/**
					 * 描述
					 */
					System.out.println("td.getDesc():  " + td.getDesc());
				}
			}
			return list_desc;
		} catch (DBException e) {
			e.printStackTrace();
		}
		return new ArrayList();

	}

	/**
	 * 通过代办项的主键，取得前驱线注解
	 */
	public static List findFromActivityFromWorkItemId(int workItemId) {
		WorkitemBean wb = new WorkitemBean();
		wb.setWorkitemId(workItemId);
		try {
			wb.init();
			wb.getActivityInsId();
			int activityInsId = wb.getActivityInsId();
			ActivityInsBean activity = new ActivityInsBean();
			activity.setActivityInsId(activityInsId);
			activity.init();
			int activityDefId = activity.getActivityDefId();
			ProcessDefBean pb = new ProcessDefBean();
			pb.setModelId(wb.getMId());
			pb.setProcessDefId(wb.getProcessDefId());
			pb.init();
			List ls = pb.getTransitionDefList();
			List list_desc = new ArrayList();
			for (int i = 0; i < ls.size(); i++) {
				TransitionDefBean td = (TransitionDefBean) ls.get(i);
				/**
				 * activityDefId == td.getFromActivityId()前驱 activityDefId ==
				 * td.getToActivityId() 后继
				 */

				if (activityDefId == td.getFromActivityId()) {
					list_desc.add(td.getDesc());

					/**
					 * 转移条件
					 */

					System.out.println("getCondition():  " + td.getCondition());

					/**
					 * 描述
					 */
					System.out.println("td.getDesc():  " + td.getDesc());
				}
			}
			return list_desc;
		} catch (DBException e) {
			e.printStackTrace();
		}
		return new ArrayList();

	}

	/**
	 * 通过代办项的主键，取得前驱线注解
	 * 
	 * @param processDefId
	 *            流程模板ID
	 * @param activityInsId
	 *            流程节点ID
	 * @return
	 */
	public static List findTransfrByActivId(int processDefId, int activityInsId) {

		try {

			ActivityInsBean activity = new ActivityInsBean();
			activity.setActivityInsId(activityInsId);
			activity.init();
			int activityDefId = activity.getActivityDefId();

			ProcessDefBean processDefBean = new ProcessDefBean();
			processDefBean.setModelId(findModelIdByProcessId(processDefId));// 获取值
			processDefBean.setProcessDefId(processDefId);
			processDefBean.init();

			List ls = processDefBean.getTransitionDefList();
			List list_desc = new ArrayList();
			for (int i = 0; i < ls.size(); i++) {
				TransitionDefBean td = (TransitionDefBean) ls.get(i);
				/**
				 * activityDefId == td.getFromActivityId()前驱 activityDefId ==
				 * td.getToActivityId() 后继
				 */
				if (activityDefId == td.getFromActivityId()) {
					list_desc.add(td.getDesc());
					/**
					 * 描述
					 */
					System.out.println("td.getDesc():  " + td.getDesc());
				}
			}
			return list_desc;
		} catch (DBException e) {
			e.printStackTrace();
		}
		return new ArrayList();

	}

	/**
	 * 通过代办项的主键，取得后继线注解
	 */
	public static List findToActivityFromWorkItemId(int workItemId) {
		WorkitemBean wb = new WorkitemBean();
		wb.setWorkitemId(workItemId);
		try {
			wb.init();
			wb.getActivityInsId();
			int activityInsId = wb.getActivityInsId();

			ActivityInsBean activity = new ActivityInsBean();
			activity.setActivityInsId(activityInsId);
			activity.init();
			int activityDefId = activity.getActivityDefId();

			ProcessDefBean pb = new ProcessDefBean();
			pb.setModelId(wb.getMId());
			pb.setProcessDefId(wb.getProcessDefId());
			pb.init();

			List ls = pb.getTransitionDefList();
			List list_desc = new ArrayList();

			for (int i = 0; i < ls.size(); i++) {
				TransitionDefBean td = (TransitionDefBean) ls.get(i);
				/**
				 * activityDefId == td.getFromActivityId()前驱 activityDefId ==
				 * td.getToActivityId() 后继
				 */

				if (activityDefId == td.getToActivityId()) {
					list_desc.add(td.getDesc());

					/**
					 * 转移条件
					 */

					System.out.println("getCondition():  " + td.getCondition());

					/**
					 * 描述
					 */
					System.out.println("td.getDesc():  " + td.getDesc());
				}
			}
			return list_desc;
		} catch (DBException e) {
			e.printStackTrace();
		}
		return new ArrayList();

	}

	/**
	 * 活动定义主键，取得该活动下的参与者，仅限于参与者为某个确定的角色
	 */
	public static List backAllParticipantByactivityDefId(int activityDefId,
			int m_id) {
		try {
			String sql = "select t1.userId,t2.uname from t_role_user t1 left join t_usertable t2 on t1.userId=t2.userId where rid in(select rid from t_role where rname in (select participant_name from t_activity_def where activity_def_id="
					+ activityDefId + " and m_id=" + m_id + "))";
			String monitorRmiStr = ReadPropertyConfig.getInstance().getDbrmi();
			try {
				Object obj[][] = DbTools.queryDb(monitorRmiStr, sql);
				List list_user = new ArrayList();
				Map userMap = null;
				for (int i = 0; i < obj.length; i++) {
					userMap = new HashMap();
					userMap.put("userId", obj[i][0]);
					userMap
							.put("userName", obj[i][1] == null ? "-"
									: obj[i][1]);
					list_user.add(userMap);
				}
				return list_user;
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList();
	}

	/**
	 * 查询当前业务数据的状态。 当前驱线多于一条时，依据分支条件来业务数据状态。
	 */
	public static String[] findServiceDataStauts(int workItemId) {
		String status[] = new String[2];
		/**
		 * 当前业务数据的状态
		 */

		WorkitemBean wb = new WorkitemBean();
		wb.setWorkitemId(workItemId);
		try {
			wb.init();

			Object[][] argsObjects = null;
			/**
			 * 获取分支条件
			 */
			String key = "";
			String value = "";
			argsObjects = wb.getArgs();

			/**
			 * 活动节点定义对象
			 */

			ActivityInsBean activity = new ActivityInsBean();
			activity.setActivityInsId(wb.getActivityInsId());
			activity.init();
			int activityDefId = activity.getActivityDefId();
			/**
			 * 
			 */
			ActivityInsBean preActivity = new ActivityInsBean();
			activity.setActivityInsId(wb.getActivityInsId());
			activity.init();

			/**
			 * 流程定义对象
			 */
			ProcessDefBean pb = new ProcessDefBean();
			pb.setModelId(wb.getMId());
			pb.setProcessDefId(wb.getProcessDefId());
			pb.init();
			/**
			 * 获取所有转移线
			 */
			List ls = pb.getTransitionDefList();

			/**
			 * 
			 */
			// preActivity.previous(1);
			/**
			 * 封装当前节点下的所有转移线
			 */
			List list_desc = new ArrayList();
			for (int i = 0; i < ls.size(); i++) {
				TransitionDefBean td = (TransitionDefBean) ls.get(i);
				/**
				 * activityDefId == td.getFromActivityId()前驱 activityDefId ==
				 * td.getToActivityId() 后继
				 */
				if (activityDefId == td.getToActivityId()) {
					list_desc.add(td.getDesc());
				}
			}
			if (list_desc.size() > 1) {
				/**
				 * 有条件分支
				 */
				String temNodeDesc = "";// 前驱线描述临时变量
				String fromTransKey = "";//
				String fromTransValue = "";// 

				for (int i = 0; i < argsObjects.length; i++) {
					key = argsObjects[i][0].toString();
					value = argsObjects[i][1].toString();
					for (int j = 0; j < list_desc.size(); j++) {
						temNodeDesc = list_desc.get(j).toString().trim();
						if (!"".equals(temNodeDesc)) {
							fromTransKey = temNodeDesc.substring(temNodeDesc
									.indexOf("[{") + 2, temNodeDesc
									.indexOf(","));
							fromTransValue = temNodeDesc.substring(temNodeDesc
									.indexOf(",") + 1, temNodeDesc
									.indexOf("}]"));
							if (key.equals(fromTransKey)
									&& value.equals(fromTransValue)) {
								String temString[] = temNodeDesc.split(";");
								status[0] = temString[1].substring(temString[1]
										.indexOf("[") + 1, temString[1]
										.indexOf(","));
								status[1] = temString[1].substring(temString[1]
										.indexOf(",") + 1, temString[1]
										.indexOf("]"));
								;
								return status;
							}
						}

					}
				}
			} else {
				/**
				 * 无条件分支
				 */
				String temString = "";
				temString = list_desc.get(0).toString().trim();
				if (!"".equals(temString)) {
					if (temString.contains(";")) {
						String[] temString1 = temString.split(";");

						status[1] = temString1[1].substring(temString1[1]
								.indexOf(",") + 1, temString1[1].indexOf("]"));
						status[0] = temString1[1].substring(temString1[1]
								.indexOf("[") + 1, temString1[1].indexOf(","));
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * 通过流程定义ID找到当前最新版本工作流模型
	 * 
	 * @param processId
	 * @return
	 */
	public static int findModelIdByProcessId(int processId) {
		int modelId = -1;
		SynchroFLOWBean synchroFLOWBean = new SynchroFLOWBean();
		try {
			List modelList = synchroFLOWBean.getModelList();
			ModelBean modelBean = null;
			for (int i = 0; i < modelList.size(); i++) {
				modelBean = (ModelBean) modelList.get(i);
				if ((processId == modelBean.getProcessId())
						&& (modelBean.getModelId() > modelId)) {
					modelId = modelBean.getModelId();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return modelId = -1;
		}

		return modelId;
	}

	/**
	 * by流程定义ID查找所有的历史模型
	 * 
	 * @param processId
	 * @return
	 */
	public static List findModelListByProcessId(int processId) {
		List resultModelList = new ArrayList();
		SynchroFLOWBean synchroFLOWBean = new SynchroFLOWBean();
		try {
			List modelList = synchroFLOWBean.getProcessDefList();
			ProcessDefBean processDefBean = null;
			// ModelBean modelBean = null;
			for (int i = 0; i < modelList.size(); i++) {
				processDefBean = (ProcessDefBean) modelList.get(i);
				if (processId == processDefBean.getProcessDefId()) {
					Map map = new HashMap();
					map.put("modelId", processDefBean.getModelId() + "");
					map.put("modelName", processDefBean.getProcessDefName()
							+ "(" + processDefBean.getModelId() + ")");
					resultModelList.add(map);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultModelList;
	}

	/**
	 * 模型定义bean查找 活动节点bean
	 * 
	 * @param processId
	 * @param modelId
	 * @return
	 */
	public static List findActivityDef(int processId, int modelId) {
		ProcessDefBean processDefBean = new ProcessDefBean();
		processDefBean.setModelId(modelId);
		processDefBean.setProcessDefId(processId);
		try {
			processDefBean.init();
		} catch (DBException e) {
			e.printStackTrace();
		}
		List ActivityDefBeanList = null;
		try {
			ActivityDefBeanList = processDefBean.getActivityDefList();
		} catch (DBException e) {
			e.printStackTrace();
		}

		return ActivityDefBeanList;
	}

	/**
	 * 查找当前版本流程图节点的所有前驱线描述
	 * 
	 * @param processId
	 *            流程定义id
	 * @param mid
	 *            模板历史版本id
	 * @param activityId
	 *            节点id
	 * @return
	 */
	public static List findAllTransfer(int processId, int mid, int activityId) {
		ProcessDefBean processDefBean = new ProcessDefBean();
		processDefBean.setModelId(mid);
		processDefBean.setProcessDefId(processId);

		Map map = null;
		String temString = "";

		List fromTransfer = new ArrayList();

		try {
			processDefBean.init();
			TransitionDefBean transitionDefBean = null;
			List transitionDefList = processDefBean.getTransitionDefList();
			String[] temTransfer = null;
			for (int i = 0; i < transitionDefList.size(); i++) {
				transitionDefBean = (TransitionDefBean) transitionDefList
						.get(i);
				if (activityId == transitionDefBean.getToActivityId()) {
					temString = transitionDefBean.getDesc();
					if (!"".equals(temString)) {
						temString = temString.substring(temString
								.indexOf("status=[") + 8, temString.length());
						temString = temString.substring(0, temString
								.indexOf("]"));
						temTransfer = temString.split(",");

						map = new HashMap();
						map.put("key", temTransfer[0]);
						map.put("value", temTransfer[1]);
						fromTransfer.add(map);
					}
				}
			}
		} catch (DBException e) {
			e.printStackTrace();
		}
		return fromTransfer;
	}

	/**
	 * 查询所有的流程定义模板
	 * 
	 * @return
	 */
	public static List findAllModelFromSyn() {
		SynchroFLOWBean synchroFLOWBean = new SynchroFLOWBean();
		List modelList = null;
		List list = new ArrayList();
		try {
			modelList = synchroFLOWBean.getModelList();
			ModelBean modelBean = null;
			Map map = null;
			for (int j = 0; j < modelList.size(); j++) {
				modelBean = (ModelBean) modelList.get(j);
				map = new HashMap();
				map.put("key", modelBean.getProcessId() + "");
				map.put("value", modelBean.getProcessName());
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 通过工作项id 获取相应的模型id和节点id
	 * 
	 * @param workItemId
	 * @return
	 */
	public static String[] findMidAndActivtyIdByWorkItemId(int workItemId) {
		WorkitemBean wb = new WorkitemBean();
		ActivityInsBean activity = new ActivityInsBean();
		String[] strings = new String[3];
		wb.setWorkitemId(workItemId);

		try {
			wb.init();
			activity.setActivityInsId(wb.getActivityInsId());
			activity.init();
		} catch (Exception e) {
			e.printStackTrace();
		}

		/**
		 * 设置模型主键
		 */
		strings[0] = wb.getMId() + "";
		/**
		 * 设置活动定义ID
		 */
		strings[1] = activity.getActivityDefId() + "";
		strings[2] = WorkFlowUtil.findServiceDataStauts(workItemId)[0];

		return strings;
	}

	/**
	 * 将配置好的权限数据对象转化成相应的字符串
	 * 
	 * @param setting
	 * @return
	 */
	public static String transferButtonSettingToString(List setting,
			HttpServletRequest request, Map data, String status) {
		if (setting != null && setting.size() > 0) {
			StringBuffer buttonString = new StringBuffer(" ");
			for (int i = 0; i < setting.size(); i++) {
				AuthorityButton button = (AuthorityButton) setting.get(i);

				buttonString.append(" <a href='javascript:void(0)' ");

				List eventList = button.getEventForButtonList();
				for (int j = 0; j < eventList.size(); j++) {
					EventForButton eventForButton = (EventForButton) eventList
							.get(j);
					String eventType = eventForButton.getType();
					buttonString.append("on"
							+ StringUtils.capitalize(eventType) + "='");

					List jsfunctionList = eventForButton.getJSFunctionList();
					for (int k = 0; k < jsfunctionList.size(); k++) {
						JSFunction jsFunction = (JSFunction) jsfunctionList
								.get(k);

						String functionName = jsFunction.getName();
						buttonString.append(functionName + "(");

						List paramList = jsFunction.getParmers();
						String[] paramArray = new String[paramList.size()];
						for (int l = 0; l < paramList.size(); l++) {
							Param param = (Param) paramList.get(l);

							String paramType = param.getType();
							String paramValue = param.getValue();
							String paramName = param.getKey();
							int paramIndex = param.getSortIndex();
							// 排序
							if ("instan".equals(paramType)) {
								paramArray[paramIndex - 1] = "\'" + paramValue
										+ "\'";
							} else if ("lineparam".equals(paramType)) {
								String key = paramValue.substring(paramValue
										.indexOf(".") + 1);
								if ("status".equals(key)) {
									paramArray[paramIndex - 1] = "\"" + status
											+ "\"";
								} else {
									paramArray[paramIndex - 1] = "\""
											+ data.get(key) + "\"";
								}
							} else if ("pageParam".equals(paramType)) {
								String requestOrSeeionValue = request
										.getParameter(paramType);
								if (requestOrSeeionValue == null) {
									requestOrSeeionValue = request.getSession()
											.getAttribute(paramType).toString();
								}
								paramArray[paramIndex - 1] = "\""
										+ request.getParameter(paramValue)
										+ "\"";
							}

						}
						StringBuffer paramString = new StringBuffer();
						for (int l = 0; l < paramArray.length; l++) {
							if (l == paramArray.length - 1) {
								paramString.append(paramArray[l]);
							} else {
								paramString.append(paramArray[l]).append(",");
							}

						}
						buttonString.append(paramString + ");'");
					}
				}

				String buttonName = button.getName();
				// 配装按钮
				if ("edit".equals(buttonName)) {
					buttonString
							.append("><img src='jquery-easyui-1.1.2/themes/icons/green/icon_edit.png'>");
				} else if ("view".equals(buttonName)) {
					buttonString
							.append("><img src='jquery-easyui-1.1.2/themes/icons/blue/view.png'>");
				} else if ("delete".equals(buttonName)) {
					buttonString
							.append("><img src='jquery-easyui-1.1.2/themes/icons/blue/delete.png'>");
				}
				buttonString.append("</a> ");

			}
			return buttonString.toString();
		}
		return "";
	}

	/**
	 * 已转出
	 * 
	 * @param workItemId
	 * @param m_id
	 * @return
	 */
	public static Object[][] backArgsByWorkItemId(int workItemId, int m_id) {
		Object[][] object = null;
		try {
			String sql = "select top 3 t.name,d.data_value from t_relevant_data d inner join t_model_global_data t on t.data_id=d.data_id and t.m_id="
					+ m_id
					+ " and d.process_ins_id=(select process_ins_id from t_workitem_his where workitem_id="
					+ workItemId + " ) order by t.data_id desc ";
			String monitorRmiStr = ReadPropertyConfig.getInstance().getDbrmi();
			// Map map = new HashMap();
			try {
				object = DbTools.queryDb(monitorRmiStr, sql);
				// List list_user = new ArrayList();
				// for (int i = 0; i < obj.length; i++) {
				// for (int j = 0; j < obj[i].length - 1; j++) {
				// map.put(obj[i][j], obj[i][j + 1]);
				// }
				// }
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	/*
	 * @function:通过用户主键，业务主键取得特定的工作项主键 @return:工作项主键
	 */
	public static String findWorkItemId(String sid, String userId) {
		SynchroFLOWBean sf = new SynchroFLOWBean();
		String workItemId = "";
		try {
			List works = sf.getWorkitemListByUserId(userId);
			for (int i = 0; i < works.size(); i++) {
				WorkitemBean wib = (WorkitemBean) works.get(i);
				Object[][] obj = wib.getArgs();
				for (int i1 = 0; i1 < obj.length; i1++) {
					for (int i2 = 0; i2 < obj[i1].length - 1; i2++) {
						if (obj[i1][i2].equals("SID")) {
							int i3 = i2 + 1;
							if (obj[i1][i3] != null
									&& !"".equals(obj[i1][i3].toString())
									&& sid.trim().equals(
											obj[i1][i3].toString().trim())) {
								workItemId = wib.getWorkitemId() + "";
							}
						}
					}
				}

			}
		} catch (DBException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return workItemId;
	}

	/**
	 * 动态表单启动流程时 保存工作流状态
	 * 
	 * @param processDefId
	 * @return initActivity[0] mid ,initActivity[1] 前驱状态
	 */
	public static WorkFlowDataStauts findInitActivity(String processDefId,String saveType) {

		SynchroFLOWBean sb = new SynchroFLOWBean();
		List activitydfs = new ArrayList();
		WorkFlowDataStauts workFlowDataStauts = new WorkFlowDataStauts();
		int mid = 0;

		String[] initActivity = new String[2];

		try {
			List listprocess = sb.getProcessDefList();// 获得工作流过程定义列表
			ProcessDefBean pdb = null;
			for (int i = 0; i < listprocess.size(); i++) {
				pdb = (ProcessDefBean) listprocess.get(i);// 得到工作流过程定义对象
				if (Integer.toString(pdb.getProcessDefId())// 判断processDefId
						.equals(processDefId)) {
					if (pdb.getModelId() > mid) {
						mid = pdb.getModelId();
					}
				}
			}
			/**
			 * 查找最新模板的开始节点
			 */
			pdb.setProcessDefId(Integer.parseInt(processDefId));
			pdb.setModelId(mid);
			pdb.init();
			List adl = pdb.getActivityDefList();
//			for (int j = 0; j < adl.size(); j++) {
//
//				ActivityDefBean adb = (ActivityDefBean) adl.get(j);// synchroflow内置对象
//				if (adb.getActivityDefId() == 1) {
//					workFlowDataStauts.setProcessDefId(processDefId);
//					workFlowDataStauts.setMid(mid + "");
//					workFlowDataStauts.setActivityDefId(adb.getActivityDefId()
//							+ "");
//				}
//			}
			ActivityDefBean adb = (ActivityDefBean) adl.get(0);
			workFlowDataStauts.setProcessDefId(processDefId);
			workFlowDataStauts.setMid(mid + "");
			workFlowDataStauts.setActivityDefId(adb.getActivityDefId()
					+ "");
			/**
			 * 获取开始节点的后继 查找该后继的前驱状态
			 */
			List transitionDefList = pdb.getTransitionDefList();
			TransitionDefBean transitionDefBean = null;
			for (int i = 0; i < transitionDefList.size(); i++) {
				transitionDefBean = (TransitionDefBean) transitionDefList
						.get(i);
				/**
				 * 开始节点的后继线
				 */
				if (transitionDefBean.getFromActivityId() == Integer
						.parseInt(workFlowDataStauts.getActivityDefId())) {
					int toActivityDefId = transitionDefBean
							.getTransitionDefId() + 1;
					/**
					 * 当前后继的前驱
					 */
					transitionDefBean.setModelId(mid);
					transitionDefBean.setTransitionDefId(toActivityDefId);
					transitionDefBean.init();
					if(saveType!=null&&"transave".equals(saveType.trim())){
						workFlowDataStauts.setToTransferDefStautsValue("-1");
						workFlowDataStauts.setToTransferDefStauts_text("暂存");
						workFlowDataStauts.setToTransferDefStautsText("草稿");
					}else{
						String transitionDefDesc = transitionDefBean.getDesc();
						workFlowDataStauts.setToTransferDefStauts_text(transitionDefDesc.split(";")[2].split("=")[1]);
						transitionDefDesc = transitionDefDesc.substring(
								transitionDefDesc.indexOf("status=[") + 8,
								transitionDefDesc.lastIndexOf("]"));
						workFlowDataStauts
						.setToTransferDefStautsValue(transitionDefDesc
								.split(",")[0]);
						workFlowDataStauts
						.setToTransferDefStautsText(transitionDefDesc
								.split(",")[1]);
					}

				}
			}
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return workFlowDataStauts;
	}

	/**
	 * 查找当前节点的所有后继分支
	 * 
	 * @throws Exception
	 */
	public static List findBranchByActivity(WorkFlowDataStauts workFlowDataStauts)
			throws Exception {
		/**
		 * 构建流程定义对象
		 */
		ProcessDefBean processDefBean = new ProcessDefBean();
		processDefBean.setModelId(Integer.parseInt(workFlowDataStauts.getMid()));
		processDefBean.setProcessDefId(Integer.parseInt(workFlowDataStauts.getProcessDefId()));
		processDefBean.init();
		/**
		 * 流程所有的转移线
		 */
		List list = processDefBean.getTransitionDefList();
		TransitionDefBean transitionDefBean = null;

		/**
		 * 定义每条分支 的状态(前驱) ，分支的参数(后继)
		 */
		List temStautsList = new ArrayList();
		WorkFlowDataStauts temStauts = null;
	

		String transitionDefDesc = "";
		String condition = "";
		String stitchingParameter="";
		/**
		 * 获取当前节点的所有后继转移线
		 */
		for (int i = 0; i < list.size(); i++) {
			transitionDefBean = (TransitionDefBean) list.get(i);
			if (transitionDefBean.getFromActivityId() == (Integer
					.parseInt(workFlowDataStauts.getActivityDefId()))) {
				/**
				 * 封转输出的提交按钮的参数
				 */
				condition = transitionDefBean.getCondition();
				temStauts=new WorkFlowDataStauts();
				String desc=transitionDefBean.getDesc();
				String fenzhiTiao[]=desc.substring(desc.indexOf("con_param"), desc.indexOf("}")).split(",");
				/**
				 * 查找当前后继的前驱状态
				 */
				transitionDefBean.setTransitionDefId(transitionDefBean.getTransitionDefId() + 1);
				transitionDefBean.setModelId(Integer.parseInt(workFlowDataStauts.getMid()));
				transitionDefBean.init();
				transitionDefDesc = transitionDefBean.getDesc();
				temStauts.setToTransferDefStauts_text(transitionDefDesc.split(";")[2].split("=")[1]);
				transitionDefDesc = transitionDefDesc.substring(transitionDefDesc.indexOf("status=[") + 8,transitionDefDesc.lastIndexOf("]"));
				temStauts.setToTransferDefStautsValue(transitionDefDesc.split(",")[0]);
				temStauts.setToTransferDefStautsText(transitionDefDesc.split(",")[1]);
				temStauts.setProcessDefId(workFlowDataStauts.getProcessDefId());
				temStauts.setMid(workFlowDataStauts.getMid());
				temStauts.setCondition(condition);
				temStauts.setPreWorkItemId(workFlowDataStauts.getPreWorkItemId());
				temStauts.setActivityDefId(workFlowDataStauts.getActivityDefId());
				
				stitchingParameter=
				      "workitemId="+temStauts.getPreWorkItemId()
					+ "&mid=" + temStauts.getMid() 
					+ "&processDefId=" + temStauts.getProcessDefId()
					+ "&activityDefId=" + temStauts.getActivityDefId()
					+ "&toTransferDefStautsText=" + temStauts.getToTransferDefStautsText()
					+ "&toTransferDefStautsValue="+ temStauts.getToTransferDefStautsValue()
					+ "&con_param="+fenzhiTiao[1];
					
				
				temStauts.setStitchingParameter(stitchingParameter);
				temStautsList.add(temStauts);
			}
		}
		return temStautsList;
	}
	/**
	 * 返回APP_ID
	 * @param userId
	 * @return
	 */
	public List findServiceID(String userId) {
		SynchroFLOWBean synchroFLOWBean=new SynchroFLOWBean();
	
		StringBuffer sb = new StringBuffer();
		sb.append("'-1'");
		Map mp=new HashMap();
		List resrultList=new ArrayList();
		try {
			List  workitemList=synchroFLOWBean.getWorkitemListByUserId(userId);
			WorkitemBean workitemBean=null;
			for (int i = 0; i < workitemList.size(); i++) {
				workitemBean=(WorkitemBean)workitemList.get(i);
				Object[][] obj = workitemBean.getArgs();
				for (int i1 = 0; i1 < obj.length; i1++) {
					for (int i2 = 0; i2 < obj[i1].length-1; i2++) {
						if (obj[i1][i2].equals("APP_ID")&&obj[i1][i2+1]!=null) {
							int i3 = i2 + 1;
							Object obj1=obj[i1][i3];
							if(null!=obj[i1][i3] && !"".equals(obj[i1][i3]+"")){
								mp.put(obj[i1][i3],new Integer(workitemBean.getWorkitemId()));
								sb.append(",");
								sb.append("'"+(String) obj[i1][i3]+"'");
								
							}
						}
					}
				}
				
			}
		}  catch (Exception e) {
			e.printStackTrace();
		}
		resrultList.add(sb.toString());
		resrultList.add(mp);
		return resrultList;
	}
}