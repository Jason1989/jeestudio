package com.zxt.compplatform.indexgenerate.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

import com.zxt.compplatform.indexgenerate.dao.PageDao;
import com.zxt.compplatform.indexgenerate.entity.PageUnit;
import com.zxt.compplatform.indexgenerate.service.PageService;
import com.zxt.compplatform.indexgenerate.util.PageXmlUtil;
import com.zxt.framework.common.util.RandomGUID;

public class PageServiceImpl implements PageService {
	private PageDao pagedao;

	public Map load_Index(String subSystemId) {
		// 1. 验证是否存在子系统 不存在返回null
		PageUnit unit = pagedao.findById(subSystemId);
		
		String xml =null;
		if (unit!=null) {
			xml= unit.getPagexml();
		}else {
			return new HashMap();
		}
		
		if (StringUtils.isEmpty(xml))
			return new HashMap();
		// 2.将查找的xml配置解析成List结果
		return PageXmlUtil.xmlToPage(xml);
	}

	public Map update_Index(String subSystemId) {
		PageUnit unit = pagedao.findById(subSystemId);
		String xml = unit.getPagexml();
		if (xml == null)
			return null;
		return PageXmlUtil.xmlToPage(xml);
	}

	public void delete_Index(String subSystemId) {

	}

	public void add(PageUnit page) {
		pagedao.create(page);
	}

	public void delete(String subSystemId) {
		PageUnit pu = new PageUnit();
		pu.setId(subSystemId);
		pagedao.delete(pu);
	}

	public void update(String subSystemId, String xmlparam) {
		PageUnit pu = pagedao.findById(subSystemId);
		if (pu==null) {
			pu=new PageUnit();
			//pu.setId(RandomGUID.geneGuid());
		}
		pu.setPagexml(xmlparam);
		pagedao.update(pu);
	}

	public List findAllByIds(String ids) {
		return null;
	}

	public void update(PageUnit pageunit) {
		PageUnit pu = pagedao.findById(pageunit.getId());
		pu.setName(pageunit.getName());
		pu.setDescription(pageunit.getDescription());
		pagedao.update(pu);
	}

	public int findTotalRows() {
		return pagedao.findTotal();
	}

	public List listPage(int page, int rows) {
		return pagedao.listPage(page, rows);
	}

	public String findtemplateurl(String subSystemId) {
		PageUnit pu = pagedao.findById(subSystemId);
		if (pu != null)
			return pu.getDescription();
		return null;
	}

	public PageUnit findById(String subSystemId) {
		return pagedao.findById(subSystemId);
	}

	public String fillDefaultModel(String keyword,int num) {
		//查找系统下的模块
		List list=pagedao.findmodel(keyword,num);
		//生成xml
		Map map=new HashMap();
		int size=list.size();
		if(list!=null&&size>0){
			int j=0;
			for (int i = 0; i < num; i++) {
				//要判断模块数少于要显示的模块数的情况
				if(j>=size)
					j-=size;
				map.put("node"+(i+1), list.get(j));
				j++;
			}
		}
		String str = PageXmlUtil.PageToxml(map);
		return str;
	}

	// -------------getter and setter-----------------------\\
	public PageDao getPagedao() {
		return pagedao;
	}

	public void setPagedao(PageDao pagedao) {
		this.pagedao = pagedao;
	}

}
