package com.zxt.compplatform.workflow.dao;

import java.util.List;

public interface Daibanfl1WorkFlowDao {
	/**
	 * 待办分类
	 * @return 待办工作类别 List String  中可能有重复数据 请处理   可显示为  
	 * 工作项类别  个数     重复数据用于统计数据
	 */
	public List daibanyi(String userId);

}
