package com.zxt.compplatform.formengine.entity.dataset;

import java.util.List;

import com.zxt.compplatform.formengine.entity.view.ListPage;

/**
 * 界面vo
 * 
 * @author 007
 */
public class PageVO extends BaseVO {

	private static final long serialVersionUID = 1L;

	public PageVO() {
	}

	/**
	 * 页面元素集合
	 */
	private List tableLists;
	// private List editModeList;
	// private List titleColumnList;
	// private List listColumnsList;
	// private List buttonList;
	// private List queryZoneList;
	// private List editColumnTextList;
	// private List editColumnInputList;
	// private List editColumnEditModeList;
	/**
	 * 界面列表
	 */
	private ListPage listPage;

	public List getTableLists() {
		return tableLists;
	}

	public ListPage getListPage() {
		return listPage;
	}

	public void setListPage(ListPage listPage) {
		this.listPage = listPage;
	}

	public void setTableLists(List tableLists) {
		this.tableLists = tableLists;
	}

}
