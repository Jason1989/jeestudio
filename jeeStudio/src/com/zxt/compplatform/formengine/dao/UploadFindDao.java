package com.zxt.compplatform.formengine.dao;

import java.util.List;

/**
 * 文件上传
 * 
 * @author 007
 */
public interface UploadFindDao {
	/**
	 * 查询
	 * 
	 * @param sql
	 * @return
	 */
	public List find(String sql);

	/**
	 * 删除
	 * 
	 * @param sql
	 */
	public void delete(String sql);
}
