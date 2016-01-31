package com.zxt.framework.page.entity;

import java.util.List;
import java.util.Map;

import com.zxt.framework.common.entity.BasicEntity;

public class PaginationEntity extends BasicEntity {
	private static final long serialVersionUID = -1830556578990781550L;
	public static final int INIT_SIZE_BROWSER = 2000;
	private int currpage;//当前页
	private int rows;//每页多少行
	private int totalrows;//总行数
	private int totalpages;//总页数
	private int allflag;
	private List result;//查询结果
	private Map  mapResult; //结果集
	
	private String querySQL;//查询sql
	private String filter;//过滤
	private String string1;//sql
	private String string2;
	private List subPage;//分页
	public static final int ALLTAG_True = 1;//全选
	public static final int ALLTAG_False = 0;//全不选

	public PaginationEntity() {
		this.currpage = 1;
		this.rows = 2000;
	}

	public String getQuerySQL() {
		return this.querySQL;
	}

	public void setQuerySQL(String querySQL) {
		this.querySQL = querySQL;
	}

	public String getString1() {
		return this.string1;
	}

	public void setString1(String string1) {
		this.string1 = string1;
	}

	public String getString2() {
		return this.string2;
	}

	public void setString2(String string2) {
		this.string2 = string2;
	}

	public void setCurrpage(int currpage) {
		this.currpage = currpage;
	}

	public int getCurrpage() {
		return this.currpage;
	}

	public int getRows() {
		return this.rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getTotalpages() {
		return this.totalpages;
	}

	public void setTotalpages(int totalpages) {
		this.totalpages = totalpages;
	}

	public int getTotalrows() {
		return this.totalrows;
	}

	public void setTotalrows(int totalrows) {
		this.totalrows = totalrows;
	}

	public int getAllflag() {
		return this.allflag;
	}

	public void setAllflag(int allflag) {
		this.allflag = allflag;
	}

	public List getResult() {
		return this.result;
	}

	public void setResult(List result) {
		this.result = result;
	}

	public List getSubPage() {
		return this.subPage;
	}

	public void setSubPage(List subPage) {
		this.subPage = subPage;
	}

	public String getFilter() {
		return this.filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public Map getMapResult() {
		return mapResult;
	}

	public void setMapResult(Map mapResult) {
		this.mapResult = mapResult;
	}
}
