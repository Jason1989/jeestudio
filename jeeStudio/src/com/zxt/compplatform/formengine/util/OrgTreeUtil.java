package com.zxt.compplatform.formengine.util;

import java.util.ArrayList;
import java.util.List;

import com.zxt.compplatform.formengine.entity.view.TreeData;

/**
 * 组织结构树工具类
 * 
 * @author 007
 */
public class OrgTreeUtil {

	/**
	 * 把list 递归遍历 封装成节点连接的树
	 * 
	 * @param treeDataList
	 * @param parentID
	 * @return
	 */
	public static List treeAlgorithm(List treeDataList, String parentID) {
		List list = null;
		TreeData temData = null;
		for (int i = 0; i < treeDataList.size(); i++) {
			temData = (TreeData) treeDataList.get(i);

			if (temData.getParentID().equals(parentID)) {
				temData
						.setChildren(treeAlgorithm(treeDataList, temData
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
		return list;
	}
}
