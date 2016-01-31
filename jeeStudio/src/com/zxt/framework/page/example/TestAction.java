package com.zxt.framework.page.example;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.zxt.compplatform.datasource.entity.DataSource;
import com.zxt.framework.page.entity.PaginationEntity;

public class TestAction {
	/**
	 * 分页示例
	 */
	TestService testService = new TestService();

	/**
	 * public String execute(){ HttpServletRequest request =
	 * ServletActionContext.getRequest(); try { PaginationEntity page = new
	 * PaginationEntity(); page = setPage(request); String sql=""; page =
	 * testService.getEntityByPage(sql, page, Page.class); List result =
	 * page.getResult(); } catch (Exception e) { e.printStackTrace(); } return
	 * SUCCESS; }
	 */
	/**
	 * 列表
	 */
	public String list() {
		HttpServletResponse res = ServletActionContext.getResponse();
		HttpServletRequest req = ServletActionContext.getRequest();
		int page = 1;
		if (req.getParameter("page") != null
				&& !req.getParameter("page").equals("")) {
			page = Integer.parseInt(req.getParameter("page"));
		}
		int rows = 0;
		if (req.getParameter("rows") != null
				&& !req.getParameter("rows").equals("")) {
			rows = Integer.parseInt(req.getParameter("rows"));
		}

		PaginationEntity pageing = new PaginationEntity();
		pageing.setCurrpage(page);
		pageing.setRows(rows);
		pageing = testService.getEntityByPage(
				"select * from ENG_FORM_DATASOURCE  where t.ds_type=2",
				pageing, DataSource.class);
		List entitiesList = pageing.getResult();

		Map map = new HashMap();
		if (entitiesList != null) {
			map.put("rows", entitiesList);
			map.put("total", new Integer(pageing.getTotalrows()));
		}
		String dataSourceJson = JSONObject.fromObject(map).toString();
		try {
			res.getWriter().write(dataSourceJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
