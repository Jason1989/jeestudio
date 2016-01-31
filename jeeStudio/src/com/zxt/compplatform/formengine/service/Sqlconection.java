package com.zxt.compplatform.formengine.service;

import java.util.List;

import com.zxt.compplatform.datasource.entity.DataSource;
import com.zxt.compplatform.formengine.entity.dataset.FieldDefVO;
import com.zxt.compplatform.formengine.entity.dataset.TableVO;
import com.zxt.compplatform.formengine.entity.dataset.WhereVO;

/**
 * 根据相应的实体，拼接sql
 * 
 * @author 007
 */
public class Sqlconection {

	/**
	 * 拼接子查询
	 * 
	 * @param table
	 * @param sql
	 * @return
	 * @throws Exception
	 */

	private StringBuffer componentSql(TableVO table, StringBuffer sql,
			DataSource ds) throws Exception {
		StringBuffer sunFrom = new StringBuffer(" from ");
		StringBuffer sunSelect = new StringBuffer(" (select ");
		StringBuffer sunWhere = new StringBuffer(" where ");
		sunSelect = this.findFieldListSql(table, sunSelect, ds);
		sunWhere = this.findWhereListSql(table, sunWhere, ds);
		sunFrom = findFromListSql(table, sunFrom, ds);
		if (sunWhere != null && sunWhere.toString().trim().length() > 7) {
			sunWhere.append(" ) ");
			sql.append(sunSelect.toString() + sunFrom.toString()
					+ sunWhere.toString());
		} else {
			sunFrom.append(" ) ");
			sql.append(sunSelect.toString() + sunFrom.toString());
		}
		return sql;
	}

	/**
	 * 字段列拼接
	 * 
	 * @param table
	 * @param select
	 * @return
	 * @throws Exception
	 */
	private StringBuffer findFieldListSql(TableVO table, StringBuffer select,
			DataSource ds) throws Exception {
		List fieldList = table.getFieldList();
		List fromList = table.getFromTableList();
		for (int j = 0; j < fieldList.size(); j++) {
			FieldDefVO fdv = (FieldDefVO) fieldList.get(j);
			boolean isExist = this.isExistFrom(fromList, fdv.getFromTable());
			if (!isExist) {
				select = this.componentSql(fdv.getFromTable(), select, ds);
				if (j == 0) {
					select.append(" as " + fdv.getToFieldName() + " ");
				} else {
					select.append("," + " as " + fdv.getToFieldName() + " ");
				}
			} else {
				if (j == 0) {
					select.append(fdv.toFieldString());
				} else {
					select.append("," + fdv.toFieldString());
				}
			}
		}
		return select;
	}

	/**
	 * 查询表单列表
	 * 
	 * @param table
	 * @param from
	 * @return
	 * @throws Exception
	 */
	private StringBuffer findFromListSql(TableVO table, StringBuffer from,
			DataSource ds) throws Exception {
		List fromList = table.getFromTableList();
		for (int j = 0; j < fromList.size(); j++) {
			TableVO tb = (TableVO) fromList.get(j);
			if (tb.getFromIndex() == null
					|| tb.getFromIndex().trim().length() == 0) {
				continue;
			}
			if (tb.getFromTableList() != null
					&& tb.getFromTableList().size() > 0) {
				from = this.componentSql(tb, from, ds);
			}
			if (j == 0) {
				from.append(tb.getName());
			} else {
				String relationship = tb.getRelationship();
				if (relationship != null && relationship.trim().length() > 0) {
					if (relationship.trim().equals("INNER")) {
						from.append(" inner join ");
					} else if (relationship.trim().equals("LEFTOUT")) {
						from.append(" left join ");
					} else if (relationship.trim().equals("RIGHTOUT")) {
						from.append(" right join ");
					}
					from.append(tb.getName());
					if (tb.getWhereList() != null
							&& tb.getWhereList().size() > 0) {
						from.append(" on ");
					}
					from = this.findWhereListSql(tb, from, ds);
				} else {
					from.append("," + tb.getName());
				}
			}
		}
		if (table.getGroupByList() != null && table.getGroupByList().size() > 0) {
			from.append(table.toGroupString(0));
		}
		if (table.getHavingList() != null && table.getHavingList().size() > 0) {
			from.append(table.toHavingString());
		}
		if (table.getOrderList() != null && table.getOrderList().size() > 0) {
			from.append(table.toOrderString());
		}
		return from;
	}

	/**
	 * 条件查询拼接
	 * 
	 * @param table
	 * @param where
	 * @return
	 * @throws Exception
	 */
	private StringBuffer findWhereListSql(TableVO table, StringBuffer where,
			DataSource ds) throws Exception {
		List fromList = table.getFromTableList();
		List whereList = table.getWhereList();
		for (int j = 0; j < whereList.size(); j++) {
			WhereVO wv = (WhereVO) whereList.get(j);
			boolean isExistFirst = true, isExistSecond = true;
			if (wv.getFirst().getDataSource() != null
					&& wv.getSecond().getDataSource() != null) {
				isExistFirst = this.isExistFrom(fromList, wv.getFirst());
				isExistSecond = this.isExistFrom(fromList, wv.getSecond());
			} else {
				if (wv.getFirst().getDataSource() != null) {
					isExistFirst = this.isExistFrom(fromList, wv.getFirst());
				}
				if (wv.getSecond().getDataSource() != null) {
					isExistSecond = this.isExistFrom(fromList, wv.getSecond());
				}
			}
			if (isExistFirst && isExistSecond) {
				if (j == 0) {
					where.append(wv.toWhereString(ds));
				} else {
					where.append(" and " + wv.toWhereString(ds));
				}
			} else {
				if (j != 0) {
					where.append(" and ");
				}
				if (isExistFirst) {
					where.append(wv.toFirstString(ds));
					;
				} else {
					where = this.componentSql(wv.getFirst(), where, ds);
				}
				where.append(" " + wv.getOperate() + " ");
				if (isExistSecond) {
					where.append(wv.toSecondString(ds));
				} else {
					where = this.componentSql(wv.getSecond(), where, ds);
				}
			}
		}
		return where;
	}

	/**
	 * 判断fromList是否出现此表
	 * 
	 * @param fromList
	 * @param table
	 * @return
	 * @throws Exception
	 */
	private boolean isExistFrom(List fromList, TableVO table) throws Exception {
		if (fromList == null) {
			return true;
		}
		for (int i = 0; i < fromList.size(); i++) {
			TableVO t = (TableVO) fromList.get(i);
			if (table.equals(t)
					|| t.getName().endsWith(table.getName()) == true) {

				return true;
			}
		}
		return false;
	}

}
