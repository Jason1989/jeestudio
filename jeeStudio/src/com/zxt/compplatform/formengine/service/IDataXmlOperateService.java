package com.zxt.compplatform.formengine.service;

import com.zxt.compplatform.formengine.entity.dataset.DefineFileVO;

/**
 * xml操作接口
 * 
 * @author 007
 */
public interface IDataXmlOperateService {

	/**
	 * 分析xml文件
	 * 
	 * @param df
	 * @return
	 * @throws Exception
	 */
	public String analysisFile(DefineFileVO df) throws Exception;

}
