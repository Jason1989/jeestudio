package com.zxt.compplatform.indexgenerate.dao;

import java.util.List;

import com.zxt.compplatform.indexgenerate.entity.PageUnit;
import com.zxt.framework.common.dao.IDAOSupport;

public interface PageDao extends IDAOSupport{

	public PageUnit findById(String subSystemId);

	public List listPage(int page, int rows);

	public int findTotal();

	public List findmodel(String keyword, int num);

}
