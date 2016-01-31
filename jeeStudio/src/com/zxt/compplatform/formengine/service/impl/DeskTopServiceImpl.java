package com.zxt.compplatform.formengine.service.impl;

import com.zxt.compplatform.formengine.dao.DeskTopDao;
import com.zxt.compplatform.formengine.entity.view.TreeData;
import com.zxt.compplatform.formengine.service.DeskTopService;

/**
 * 桌面版界面业务操作实现
 * @author 007
 */
public class DeskTopServiceImpl implements DeskTopService {
	/**
	 * 桌面版持久化操作
	 */
	private DeskTopDao deskTopDao;

	public DeskTopDao getDeskTopDao() {
		return deskTopDao;
	}

	public void setDeskTopDao(DeskTopDao deskTopDao) {
		this.deskTopDao = deskTopDao;
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.formengine.service.DeskTopService#findTreeData(java.lang.String)
	 */
	public TreeData findTreeData(String menuID) {
		return deskTopDao.findTreeData(menuID);
	}

}
