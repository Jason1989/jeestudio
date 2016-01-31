package com.zxt.framework.templatefile.service;

import java.util.List;
import java.util.Map;

import com.zxt.framework.templatefile.entity.FormTemplateFile;

public interface FormTemplateFileService {
	/**
	 * 获取数据对象列表
	 * @param dgId 数据对象分组ID，页号，每页大小
	 * @return
	 */
	public List list(String dgId, int page, int size);
	/**
	 * 保存表单模板
	 * @param data 模板数据
	 * @return
	 */
	public String save(FormTemplateFile data);
	/**
	 * 删除模板
	 * @param id 模板id
	 * @return
	 */
	public String delete(String id);
	/**
	 * 获取模板键值对
	 * @param conn
	 * @return
	 */
	public Map get(String id);
	/**
	 * 判断模板是否重复
	 * @param conn
	 * @return
	 */
	public boolean exists(String name);
}
