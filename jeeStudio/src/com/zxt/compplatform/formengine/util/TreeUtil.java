package com.zxt.compplatform.formengine.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspWriter;

import org.apache.log4j.Logger;

import com.zxt.compplatform.formengine.constant.Constant;
import com.zxt.compplatform.formengine.entity.view.TreeData;
import com.zxt.compplatform.formengine.entity.view.TreeOrgData;

/**
 * 树控件工具类
 * 
 * @author 007
 */
public class TreeUtil {
	private static final Logger log = Logger.getLogger(TreeUtil.class);
	/**
	 * 把list 递归遍历 封装成节点连接的树 不包含传入的节点
	 * 
	 * @param treeDataList
	 * @param parentID
	 * @return
	 */
	public static List treeAlgorithm(List treeDataList, String parentID) {
		List list = null;
		TreeData temData = null;
		if (treeDataList != null && treeDataList.size() > 0) {
			try {
				for (int i = 0; i < treeDataList.size(); i++) {
					temData = (TreeData) treeDataList.get(i);
					if (temData.getParentID().equals(parentID)) {
						temData.setChildren(treeAlgorithm(treeDataList, temData
								.getId()));
						if (temData.getChildren() != null) {
							temData.setState("closed");
						}
						if (list == null) {
							list = new ArrayList();
						}
						list.add(temData);
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				log.error(temData.getText() + " 菜单，级别为：" + temData.getLevel()
						+ " 没有父节点");
				e.printStackTrace();
			}
		}

		return list;
	}

	/**
	 * 组织机构带人员的树的顶级目录的组成 把list 递归遍历 封装成节点连接的树 不包含传入的节点
	 * 
	 * @param treeDataList
	 * @param parentID
	 * @return
	 */
	public static List treeOrgAlgorithm(List treeDataList, String parentID) {
		List list = null;
		TreeOrgData temData = null;
		if (treeDataList != null && treeDataList.size() > 0) {
			try {
				for (int i = 0; i < treeDataList.size(); i++) {
					temData = (TreeOrgData) treeDataList.get(i);
					if (temData.getParentID().equals(parentID)) {
						if ("0".equals(temData.getIsuser())) {
							temData.setChildren(treeOrgAlgorithmForSon(
									treeDataList, temData.getId()));
						}
						// temData.setState("open");
						if (temData.getChildren() != null) {
							temData.setState("closed");
						}
						if (list == null) {
							list = new ArrayList();
						}
						list.add(temData);
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				log.error(temData.getText() + " 菜单，级别为：" + temData.getLevel()
						+ " 没有父节点");
				e.printStackTrace();
			}
		}

		return list;
	}

	/**
	 * 人员列表树控件左侧组织机构 把list 递归遍历 封装成节点连接的树 不包含传入的节点
	 * 
	 * @param treeDataList
	 * @param parentID
	 * @return
	 */
	public static List treeHumanAlgorithm(List treeDataList, String parentID) {
		List list = null;
		TreeOrgData temData = null;
		if (treeDataList != null && treeDataList.size() > 0) {
			try {
				for (int i = 0; i < treeDataList.size(); i++) {
					temData = (TreeOrgData) treeDataList.get(i);
					temData.setState("open");
					if (temData.getParentID().equals(parentID)) {
						if ("0".equals(temData.getIsuser())) {
							temData.setChildren(treeOrgAlgorithmForSon(
									treeDataList, temData.getId()));
						}
						if (temData.getChildren() != null) {
							// temData.setState("closed");
						}
						if (list == null) {
							list = new ArrayList();
						}
						list.add(temData);
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				log.error(temData.getText() + " 菜单，级别为：" + temData.getLevel()
						+ " 没有父节点");
				e.printStackTrace();
			}
		}

		return list;
	}	
	/**
	 * 人员列表树控件左侧组织机构 把list 递归遍历 封装成节点连接的树 包含传入的节点
	 * 
	 * @param treeDataList
	 * @param parentID
	 * @return
	 */
	public static List treeHumanAlgorithmRoot(List treeDataList, String parentID) {
		List list = new ArrayList();
		TreeOrgData temData = null;
		if(treeDataList != null && treeDataList.size() > 0 ){
			try {
					for (int i = 0; i < treeDataList.size(); i++) {
						temData = (TreeOrgData) treeDataList.get(i);
						temData.setState("open");
						if (temData.getId().equals(parentID)) {
							temData.setChildren(
								treeHumanAlgorithm(treeDataList, parentID));
							
							list.add(temData);
						}
					}
			} catch (Exception e) {
				// TODO: handle exception
				log.error(temData.getText() + " 菜单，级别为：" + temData.getLevel()
						+ " 没有父节点");
				 e.printStackTrace();
			}
		}

		return list;
	}
	/**
	 * 组织机构带人员的树的子节点的组成 把list 递归遍历 封装成节点连接的树 不包含传入的节点
	 * 
	 * @param treeDataList
	 * @param parentID
	 * @return
	 */
	public static List treeOrgAlgorithmForSon(List treeDataList, String parentID) {

		List list = null;
		TreeOrgData temData = null;
		if (treeDataList != null && treeDataList.size() > 0) {
			try {
				for (int i = 0; i < treeDataList.size(); i++) {
					temData = (TreeOrgData) treeDataList.get(i);
					if (temData.getParentID().equals(parentID)) {
						if ("0".equals(temData.getIsuser())) {
							temData.setChildren(treeOrgAlgorithm(treeDataList,
									temData.getId()));
						}
						temData.setState("closed");
						if (temData.getChildren() != null) {
						}
						if (list == null) {
							list = new ArrayList();
						}
						list.add(temData);
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				log.error(temData.getText() + " 菜单，级别为：" + temData.getLevel()
						+ " 没有父节点");
				e.printStackTrace();
			}
		}

		return list;
	}

	/**
	 * 把list 递归遍历 封装成节点连接的树 包含传入的节点
	 * 
	 * @param treeDataList
	 * @param parentID
	 * @return
	 */
	public static List treeAlgorithmForTreeData(List treeDataList,
			String parentID) {
		List list = null;
		TreeData temData = null;

		for (int i = 0; i < treeDataList.size(); i++) {
			temData = (TreeData) treeDataList.get(i);
			if (temData.getId().equals(parentID)) {
				list = new ArrayList();
				temData
						.setChildren(treeAlgorithm(treeDataList, temData
								.getId()));
				if (temData.getChildren() != null) {
					temData.setState("closed");
				}
				list.add(temData);
			}
		}
		return list;
	}
	
	

	
	/**
	 * 获取所有树子节点的id
	 * 
	 * @param list
	 * @param parentId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String findAllChildTreeID(List list, String parentId) {
		String childIDs = "";
		TreeData treeData = null;
		for (int i = 0; i < list.size(); i++) {
			treeData = (TreeData) list.get(i);
			if (parentId.equals(treeData.getParentID())) {
				childIDs = childIDs + "'" + treeData.getId() + "',"
						+ findAllChildTreeID(list, treeData.getId());
			}
		}
		return childIDs;
	}

	/**
	 * 返回全部下级资源
	 * 
	 * @param treeDataList
	 * @param parentID
	 * @return
	 */
	public static List findChildResource(List treeDataList, String parentID) {
		List list = new ArrayList();
		TreeData temData = null;

		for (int i = 0; i < treeDataList.size(); i++) {
			temData = (TreeData) treeDataList.get(i);
			if (temData.getParentID().equals(parentID)) {

				list.add(temData);
			}
		}

		return list;
	}

	/**
	 * 从默认值 读取ID集合 选中树节点
	 * 
	 * @param treeDataList
	 * @param defalutValue
	 * @return
	 */
	public static List treeChecked(List treeDataList, String defalutValue) {
		try {
			if ((defalutValue != null) && (!"".equals(defalutValue))) {
				String[] textArray = defalutValue.split(",");
				for (int i = 0; i < treeDataList.size(); i++) {
					for (int j = 0; j < textArray.length; j++) {
						if (((TreeData) treeDataList.get(i)).getId().equals(
								textArray[j])) {
							((TreeData) treeDataList.get(i)).setChecked(true);
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return treeDataList;
	}

	/**
	 * 从默认值 读取ID集合 转换成字典文本
	 * 
	 * @param treeDataList
	 * @param defalutValue
	 * @return
	 */
	public static String dictionaryValue(List treeDataList, String defalutValue) {
		String dictionaryValue = "";
		int spiltStat = 0;
		if ((defalutValue != null) && (!"".equals(defalutValue))
				&& treeDataList != null) {
			String[] textArray = defalutValue.split(",");
			for (int i = 0; i < treeDataList.size(); i++) {
				for (int j = 0; j < textArray.length; j++) {

					if (((TreeData) treeDataList.get(i)).getId().equals(
							textArray[j])) {
						if (spiltStat == 1)
							dictionaryValue += ",";
						dictionaryValue += ((TreeData) treeDataList.get(i))
								.getText();
						spiltStat = 1;
					}
				}
			}
		}

		return dictionaryValue;
	}

	/**
	 * 数据字典值
	 * 
	 * @param treeDataList
	 * @param defalutValue
	 * @return
	 */
	public static String dictionaryOrgValue(List treeDataList,
			String defalutValue) {
		String dictionaryValue = "";
		int spiltStat = 0;
		if ((defalutValue != null) && (!"".equals(defalutValue))
				&& treeDataList != null) {
			String[] textArray = defalutValue.split(",");
			for (int i = 0; i < treeDataList.size(); i++) {
				for (int j = 0; j < textArray.length; j++) {

					if (((TreeOrgData) treeDataList.get(i)).getId().equals(
							textArray[j])) {
						if (spiltStat == 1)
							dictionaryValue += ",";
						dictionaryValue += ((TreeOrgData) treeDataList.get(i))
								.getText();
						spiltStat = 1;
					}
				}
			}
		}

		return dictionaryValue;
	}

	/**
	 * 模糊匹配
	 * 
	 * @param treeDataList
	 * @param defalutValue
	 * @return
	 */
	public static String serchLike(List treeDataList, String matchText) {
		String rootNodeString = Constant.TREE_ROOT;
		if ((matchText == null) || ("".equals(matchText))) {
			return rootNodeString;
		} else {
			int indexOf = -1;
			for (int i = 0; i < treeDataList.size(); i++) {
				indexOf = ((TreeData) treeDataList.get(i)).getText().indexOf(
						matchText);
				if (indexOf != -1) {
					rootNodeString = ((TreeData) treeDataList.get(i)).getId();
					break;
				} else {
					if (i == (treeDataList.size() - 1)) {
						rootNodeString = "-1";// 沒有匹配到
					}
				}
			}
		}
		return rootNodeString;
	}

	/**
	 * 返回本身 以及本身以下的节点
	 * 
	 * @return
	 */
	public static List selfNode(List list, String nodeID) {
		List responseList = new ArrayList();// 返回的list
		List childList = null;

		if ("0".equals(nodeID)) {
			responseList = treeAlgorithm(list, nodeID);
			return responseList;
		}
		for (int i = 0; i < list.size(); i++) {
			if (((TreeData) list.get(i)).getId().equals(nodeID)) {

				childList = treeAlgorithm(list, nodeID);
				if (childList != null) {
					((TreeData) list.get(i)).setState("closed");
				}
				if (childList == null) {
					childList = new ArrayList();
				}
				((TreeData) list.get(i)).setChildren(childList);
				responseList.add(list.get(i));
			}
		}
		return responseList;
	}

	/**
	 * 把list desktop展示菜单树
	 * 
	 * @param treeDataList
	 * @param parentID
	 * @return
	 */
	public static String treeAlgorithm(List treeDataList, String parentID,
			JspWriter out, Integer menuDivId) {
		TreeData temData = null;
		String menuDivString = "";

		try {
			boolean initFlag = false;// 是否已经初始化
			boolean isExitChildNode = false;// 是否有子节点

			for (int i = 0; i < treeDataList.size(); i++) {
				temData = (TreeData) treeDataList.get(i);
				if (temData.getParentID().equals(parentID)) {
					if (!initFlag) {
						menuDivString = menuDivString
								+ "<div id='desktopMenuButton" + parentID
								+ "' style='width:150px;'>";

						initFlag = true;
						isExitChildNode = true;
					}
					if (Constant.RESOURCE_LEVEL_FUNTION_MENU.equals(temData
							.getLevel())) {
						menuDivString = menuDivString
								+ "<div>"
								+ "<a menu='desktopMenuButton"
								+ parentID
								+ "' href='javascript:void(0);'  class='easyui-menubutton'  icon='icon-edit'  onclick=\"javascript:createShorcytWindow('"
								+ temData.getId() + "','" + temData.getText()
								+ "','" + temData.getAttributes().getUrl()
								+ "');\" >" + temData.getText() + "</a>";
					} else {
						menuDivString = menuDivString
								+ "<div>"
								+ "<a menu='desktopMenuButton"
								+ parentID
								+ "' href='javascript:void(0);'  class='easyui-menubutton'  icon='icon-edit' >"
								+ temData.getText() + "</a>";
					}

					menuDivString = menuDivString
							+ treeAlgorithm(treeDataList, temData.getId(), out,
									null);
					menuDivString = menuDivString + "</div>";
				}

			}

			if (isExitChildNode) {
				menuDivString = menuDivString + "</div>";
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return menuDivString;
	}

	/**
	 * 构建menu
	 * 
	 * @param treeDataList
	 * @param parentID
	 * @return
	 */
	public static String createMenuButton(List treeDataList, String parentID,
			JspWriter out) {

		String outMenu = "";
		String menuButton = "";

		TreeData temData = null;
		List menuButtonlist = new ArrayList();
		/**
		 * 封装改系统下所有的系统菜单
		 */
		if ((treeDataList != null) && (parentID != null)) {
			for (int i = 0; i < treeDataList.size(); i++) {
				temData = (TreeData) treeDataList.get(i);
				if (Constant.RESOURCE_LEVEL_SYSTEM_MENUN.equals(temData
						.getLevel())
						&& parentID.equals(temData.getParentID())) {
					menuButtonlist.add(temData);
				}
			}
		}
		/**
		 * 遍历改系统下所有的系统菜单 加载其子菜单
		 */
		for (int i = 0; i < menuButtonlist.size(); i++) {
			temData = (TreeData) menuButtonlist.get(i);

			// outMenu=outMenu+"<a href='javascript:void(0);'
			// onclick=\"javascript:createShorcytWindow('"+temData.getId()+"','"+temData.getText()+"','"+temData.getAttributes().getUrl()+"');\"
			// class='easyui-menubutton' menu='#desktopMenuButton"+i+"'
			// >"+temData.getText()+"</a>";
			outMenu = outMenu
					+ "<a  href='javascript:void(0);'   class='easyui-menubutton' menu='#desktopMenuButton"
					+ temData.getId() + "' >" + temData.getText() + "</a>";
			/**
			 * 递归添加改当前系统菜单下的子菜单
			 */
			menuButton = treeAlgorithm(treeDataList, temData.getId(), out,
					new Integer(i));
			outMenu = outMenu + menuButton;
		}
		return outMenu;
	}
	
	
	/**
	 * 当前用户的组织机构包括下级组织机构的ID
	 * 
	 * @param treeDataList
	 * @param parentID
	 * @return
	 */
	public static String orgAlgorithmForTreeIds(List treeDataList,
			String parentID) {
		List list = null;
		TreeData temData = null;
		String orgIds="";
		
		for (int i = 0; i < treeDataList.size(); i++) {
			temData = (TreeData) treeDataList.get(i);
			if (temData.getId().equals(parentID)) {
				orgIds="'"+temData.getId()+"'";
				orgIds+=orgAlgorithmIds(treeDataList, temData
						.getId());
				
			}
		}
		return orgIds;
	}
	/**
	 * 当前用户的下级组织机构的ID
	 * 
	 * @param treeDataList
	 * @param parentID
	 * @return
	 */
	public static String orgAlgorithmIds(List treeDataList, String parentID) {
		List list = null;
		TreeData temData = null;
		String orgIds="";
		if (treeDataList != null && treeDataList.size() > 0) {
			try {
				for (int i = 0; i < treeDataList.size(); i++) {
					temData = (TreeData) treeDataList.get(i);
					if (temData.getParentID().equals(parentID)) {
						orgIds+=temData.getId()+",";
						orgIds+=orgAlgorithmIds(treeDataList, temData
								.getId());
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

		return orgIds;
	}
}
