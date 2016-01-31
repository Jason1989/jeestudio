package com.zxt.compplatform.formengine.entity.view;

import com.zxt.framework.common.entity.BasicEntity;

/**
 * Title: Page
 * Description:  分页
 * Create DateTime: 2010-9-27
 * @author xxl
 * @since v1.0
 * 
 */
public class Pagination extends BasicEntity {
	
	/**
	 * 是否显示
	 */
	private boolean display;

	public boolean isDisplay() {
		return display;
	}

	public void setDisplay(boolean display) {
		this.display = display;
	}

}
