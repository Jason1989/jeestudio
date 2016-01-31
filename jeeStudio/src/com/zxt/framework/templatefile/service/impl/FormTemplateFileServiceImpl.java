package com.zxt.framework.templatefile.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import com.zxt.framework.templatefile.dao.FormTemplateFileDao;
import com.zxt.framework.templatefile.entity.FormTemplateFile;
import com.zxt.framework.templatefile.service.FormTemplateFileService;

public class FormTemplateFileServiceImpl implements FormTemplateFileService {
	private FormTemplateFileDao formTemplateFileDao;
	public void setFormTemplateFileDao(FormTemplateFileDao formTemplateFileDao) {
		this.formTemplateFileDao = formTemplateFileDao;
	}
	/**
	 * 获取数据对象列表
	 * @param dgId 数据对象分组ID，页号，每页大小
	 * @return
	 */
	public List list(String dgId, int page, int size){
		List list = formTemplateFileDao.list(dgId, 1, 10);
		if( CollectionUtils.isNotEmpty(list) ){
			List result = new ArrayList();
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				if( MapUtils.isEmpty(map) ){
					continue;
				}
				Map newmap = new HashMap();
				newmap.put("id", map.get("templatefile_id"));
				newmap.put("name", map.get("templatefile_name"));
				newmap.put("type", map.get("templatefile_type"));
				newmap.put("remark", map.get("templatefile_remark"));
				Timestamp time = (Timestamp)map.get("update_time");
				if( time!=null ){
					String str = time.toString();
					newmap.put("time", str);
				}
				result.add(newmap);
			}
			return result;
		}else{
			return null;
		}
		
	}
	
	public int count(String dgId){
		return formTemplateFileDao.count(dgId);
	}
	/**
	 * 保存表单模板
	 * @param data 模板数据
	 * @return
	 */
	public String save(FormTemplateFile data){
		return formTemplateFileDao.saveOrUpdate(data);
	}
	/**
	 * 删除模板
	 * @param id 模板id
	 * @return
	 */
	public String delete(String id){
		return formTemplateFileDao.delete(id);
	}
	/**
	 * 获取模板键值对
	 * @param conn
	 * @return
	 */
	public Map get(String id){
		return formTemplateFileDao.get(id);
	}
	/**
	 * 判断模板是否重复
	 * @param conn
	 * @return
	 */
	public boolean exists(String name){
		return formTemplateFileDao.exists(name);
	}
}
