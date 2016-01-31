package com.zxt.compplatform.formengine.entity.view;

import java.util.List;

import com.zxt.framework.common.entity.BasicEntity;

/**
 * Title: Tab
 * Description:  超链接
 * Create DateTime: 2010-9-27
 * @author xxl
 * @since v1.0
 * 
 */
public class Link extends BasicEntity {
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 样式名称
	 */
	private String cssClass;
	/**
	 * 参数
	 */
	private List params;
	/**
	 * 目标url
	 */
	private String targetURL;
	/**
	 * 是否打开窗口
	 */
	private boolean openNewWindow;
	
}
