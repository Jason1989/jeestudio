package com.zxt.compplatform.formengine.entity.dataset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zxt.compplatform.datasource.entity.DataSource;
import com.zxt.compplatform.formengine.entity.view.Param;

/**
 * 列表页vo
 * 
 * @author 007
 */
public class TableVO extends BaseVO {

	/**
	 * 数据源
	 */
	private DataSource dataSource;//
	/**
	 * 变量列表
	 */
	private List variableList;
	/**
	 * 主键
	 */
	private List keyList;//
	/**
	 * 保存增加字段
	 */
	private List insertFieldList;//
	/**
	 * 保存修改字段
	 */
	private List updateFieldList;//
	/**
	 * 保存删除字段
	 */
	private List deleteFieldList;//
	/**
	 * 字段集合
	 */
	private List fieldList;//
	/**
	 * 来源表集合
	 */
	private List fromTableList;//
	/**
	 * 保存where条件的字段
	 */
	private List whereList;//
	/**
	 * 保存group条件的字段
	 */
	private List groupByList;//
	/**
	 * 保存having条件的字段
	 */
	private List havingList;//
	/**
	 * 保存order
	 */
	private List orderList;//
	/**
	 * 
	 */
	private List dependList;

	/**
	 * 
	 */
	private int isWhere;
	/**
	 * 
	 */
	private int isWrite;

	/**
	 * 
	 */
	private boolean isAggregation;

	/**
	 * 手写sql语句
	 */
	private String sqlText;//
	/**
	 * 
	 */
	private String fromIndex;
	/**
	 * 关系
	 */
	private String relationship;//
	/**
	 * 操作类型
	 */
	private String oprType;//
	/**
	 * 存放where 语句
	 */
	private String where; //
	/**
	 * 存放select语句
	 */
	private String select;//
	/**
	 * 存放insert语句
	 */
	private String insert;//
	/**
	 * 存放update语句
	 */
	private String update;//
	/**
	 * 存delete语句
	 */
	private String delete;//
	/**
	 * 数据类型
	 */
	private String dataType;//
	/**
	 * 
	 */
	private String piecte;
	/**
	 * 
	 */
	private String sameTable;
	/**
	 * 
	 */
	private String region;

	/**
	 * 存放insert参数
	 * 
	 * @author 何军
	 */
	private Map insertParams; //  
	/**
	 * 存放update参数
	 * 
	 * @author 何军
	 */
	private Map updateParams; //  
	/**
	 * 存放select参数
	 * 
	 * @author 何军
	 */
	private Map selectParams; //  

	/**
	 * 存放查询参数
	 * 
	 * @author 何军
	 */
	private List whereParams;// 

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public List getFieldList() {
		return fieldList;
	}

	public void setFieldList(List fieldList) {
		this.fieldList = fieldList;
	}

	public String getSqlText() {
		return sqlText;
	}

	public void setSqlText(String sqlText) {
		this.sqlText = sqlText;
	}

	public List getFromTableList() {
		return fromTableList;
	}

	public void setFromTableList(List fromTableList) {
		this.fromTableList = fromTableList;
	}

	public boolean equals(Object obj) {
		if (obj instanceof TableVO) {
			if (this.dataSource.getName().equals(
					((TableVO) obj).getDataSource().getName())
					&& this.getName().equals(((TableVO) obj).getName())) {
				return true;
			}
		}
		return false;
	}

	public int hashCode() {
		return 0;
	}

	public List getWhereList() {
		return whereList;
	}

	public void setWhereList(List whereList) {
		this.whereList = whereList;
	}

	public String getFromIndex() {
		return fromIndex;
	}

	public void setFromIndex(String fromIndex) {
		this.fromIndex = fromIndex;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String toString() {
		// return dataSource.getName() + "." + this.getName() + "." + fieldList
		// + "where" + whereList;修改了
		return this.getName() + "." + fieldList + "where" + whereList;
	}

	public String toFieldDefString() {
		StringBuffer field = new StringBuffer("");
		for (int i = 0; i < fieldList.size(); i++) {
			FieldDefVO f = (FieldDefVO) fieldList.get(i);
			field.append(f.toFieldString() + ",");
		}
		return field.toString().substring(0, field.toString().length() - 1);
	}

	public String toFieldString() {
		StringBuffer field = new StringBuffer("");
		for (int i = 0; i < fieldList.size(); i++) {
			FieldDefVO f = (FieldDefVO) fieldList.get(i);
			// field.append(this.getName() + "." + f.getFromFieldName() +
			// ",");下边是修改过的
			// field.append(f.getPrefix() + "." + f.getFromFieldName() + ",");
			field.append(f.getPrefix() + "." + f.getToFieldName() + ",");
		}

		// System.out.println(field.toString().substring(0,
		// field.toString().length() - 1));
		return field.toString().substring(0, field.toString().length() - 1);
	}

	public String toCollFieldString() throws Exception {
		StringBuffer field = new StringBuffer("");
		for (int i = 0; i < fieldList.size(); i++) {
			FieldDefVO f = (FieldDefVO) fieldList.get(i);

			field.append(f.getPrefix() + "." + f.getToFieldName() + ",");
		}
		if (field.toString().length() > 1)
			return field.toString().substring(0, field.toString().length() - 1);
		else
			return field.toString();
	}

	public String toFromString() {
		StringBuffer from = new StringBuffer("");
		for (int i = 0; i < fromTableList.size(); i++) {
			TableVO f = (TableVO) fromTableList.get(i);
			if (("S").equals(f.getOprType())) {
				// from.append(f.getDataSource().getName() + "." + f.getName() +
				// ",");
				from.append(f.getName() + ",");
			}
			// from.append(f.getName() + ",");
		}

		// System.out.println(from.toString().substring(0,
		// from.toString().length() - 1));
		return from.toString().substring(0, from.toString().length() - 1);
	}

	public String toWhereString() throws Exception {
		StringBuffer where = new StringBuffer(" 1=1");
		if (whereList != null) {
			for (int i = 0; i < whereList.size(); i++) {
				WhereVO f = (WhereVO) whereList.get(i);
				if (f.getPreviousCondition() != null
						&& f.getPreviousCondition().trim().length() > 0) {

					where.append(f.getPreviousCondition() + " "
							+ f.toWhereString(this.getDataSource()));
				} else {
					where.append(" and "
							+ f.toWhereString(this.getDataSource()));
				}
			}
		}
		// System.out.println(where.toString());
		return where.toString();
	}

	public String toInsertString() {

		StringBuffer insert = new StringBuffer(" insert into " + this.getName());
		StringBuffer field = new StringBuffer(" (");
		StringBuffer value = new StringBuffer(" (");
		// 何军加
		insertParams = new HashMap();
		List insertParam = new ArrayList();
		insertParams.put(this.getName(), insertParam);
		insertFieldList.addAll(0, keyList);

		// for(int k=0;k<keyList.size();k++){
		// FieldDefVO fd1 = (FieldDefVO) keyList.get(k);
		// }
		for (int i = 0; i < insertFieldList.size(); i++) {
			FieldDefVO fd = (FieldDefVO) insertFieldList.get(i);
			if (i > 0) {
				field.append(",");
				value.append(",");
			}
			if (fd!=null && fd.getType() != null && "0".equals(fd.getType())) {
				value.append(fd.getFromFieldName());
			} else {
				String v = fd.getFromFieldName();
				v = v.replaceAll("＄", "\\$");
				// String formFieldName = new
				// Formula().formulaParsre(this.getDataSource().getAccessType()
				// + "#"+
				// this.getDataSource().getDataType(), 0,v);
				// 何军改
				// value.append(this.changeType(fd.getToFieldType(), v));
				value.append(" ? ");
				// 何军加 插入参数

				Param param = new Param();
				param.setKey(fd.getToFieldName());
				param.setType(fd.getToFieldType());
				insertParam.add(param);
			}

			field.append(fd.getToFieldName());

		}
		field.append(")");
		value.append(")");
		insert.append(field + " values" + value);
		// System.out.println(insert.toString());
		return insert.toString();

	}

	public String toUpdateString() {
		StringBuffer update = new StringBuffer(" update " + this.getName()
				+ " ");
		StringBuffer field = new StringBuffer(" set ");
		StringBuffer where = new StringBuffer(" where 1=1 ");
		// 何军加
		updateParams = new HashMap();
		List updateParam = new ArrayList();
		updateParams.put(this.getName(), updateParam);
		updateFieldList.removeAll(keyList);
		for (int i = 0; i < updateFieldList.size(); i++) {
			FieldDefVO fd = (FieldDefVO) updateFieldList.get(i);
			if (i > 0) {
				field.append(",");
			}
			field.append(fd.getToFieldName() + "=");
			if (fd.getType() != null && fd.getType().equals("0")) {
				field.append(fd.getFromFieldName());
			} else {
				String v = fd.getFromFieldName();
				v = v.replaceAll("＄", "\\$");
				// String formFieldName = new
				// Formula().formulaParsre(this.getDataSource().getAccessType()
				// + "#"+
				// this.getDataSource().getDataType(), 0, v);

				// 何军改
				// field.append(this.changeType(fd.getToFieldType(), v));
				field.append(" ? ");
				// 何军加 修改参数
				Param param = new Param();
				param.setKey(fd.getToFieldName());
				param.setType(fd.getToFieldType());
				updateParam.add(param);
			}

		}
		if (this.where == null || this.where.trim().length() == 0) {
			if (keyList != null) {
				// 修改了
				for (int i = 0; i < keyList.size(); i++) {
					FieldDefVO fd = (FieldDefVO) keyList.get(i);
					String v = fd.getFromFieldName();
					v = v.replaceAll("＄", "\\$");
					// String formFieldName = new
					// Formula().formulaParsre(this.getDataSource().getDataType(),
					// 0,v);

					// where.append("and " + fd.getToFieldName() + "="
					// + this.changeType(fd.getToFieldType(), v));
					// 何军改
					where.append("and " + fd.getToFieldName() + " = ?");

					// 何军加 修改参数
					Param param = new Param();
					param.setKey(fd.getToFieldName());
					param.setType(fd.getToFieldType());
					updateParam.add(param);
				}
				// for (int i = 0; i < keyList.size(); i++) {
				// FieldDefVO fd = (FieldDefVO) keyList.get(i);
				// String formFieldName = fd.getFromFieldName();
				// v = v.replaceAll("＄", "\\$");
				// String formFieldName = new
				// Formula().formulaParsre(this.getDataSource().getDataType(),
				// 0,
				// v);
				// where.append("and " + fd.getToFieldName() + "="
				// + this.changeType(fd.getToFieldType(), formFieldName));
				// }
			}
		} else {
			where.append(this.where);
		}
		update.append(field + " ");
		update.append(where);
		// System.out.println(update.toString());
		return update.toString();
	}

	public String toOrderString() throws Exception {
		StringBuffer order = new StringBuffer(" order by ");
		if (orderList != null) {
			for (int i = 0; i < orderList.size(); i++) {
				FieldDefVO fd = (FieldDefVO) orderList.get(i);
				if (fd.getToFieldType() != null) {
					order.append(fd.getFromTable().getName() + "."
							+ fd.getToFieldName() + "" + fd.getOrderType()
							+ ",");
				}
				if (orderList.size() > 0) {
					return order.toString().substring(0,
							order.toString().length() - 1);

				}
			}
		}
		return "";
	}

	public String toCollOrderString() throws Exception {
		StringBuffer order = new StringBuffer(" order by ");
		if (groupByList != null) {
			for (int i = 0; i < groupByList.size(); i++) {
				FieldDefVO fd = (FieldDefVO) groupByList.get(i);
				if (fd.getToFieldType() != null) {

					order.append(fd.getToFieldName() + ",");
				}
			}
			if (groupByList != null && groupByList.size() > 0) {
				return order.toString().substring(0,
						order.toString().length() - 1);
			}
		}
		return "";
	}

	public String toWhereStoreString() throws Exception {
		StringBuffer where = new StringBuffer(" 1=1");
		if (whereList != null) {
			for (int i = 0; i < whereList.size(); i++) {
				WhereVO f = (WhereVO) whereList.get(i);
				if (f.getPreviousCondition() != null
						&& f.getPreviousCondition().trim().length() > 0) {
					if (f.getType() != null && f.getType().equals("FORMULA")) {
						// String whereStr = new
						// Formula().formulaParsre(dataSource.getType() + "#"+
						// dataSource.getDataType(), 0,this.where);
						// whereStr = new
						// FormulaParser().deleteFirstDot(whereStr);
						where.append(f.getPreviousCondition() + " " + where);
					} else {
						where.append(f.getPreviousCondition() + " "
								+ f.toWhereStoreString(this.getDataSource()));
					}
				} else {
					if (f.getType() != null && f.getType().equals("FORMULA")) {
						// String whereStr = new
						// Formula().formulaParsre(dataSource.getAccessType() +
						// "#"+ dataSource.getDataType(), 0,this.where);
						// whereStr = new
						// FormulaParser().deleteFirstDot(whereStr);
						where.append(" and " + this.where);
					} else {
						where.append(" and "
								+ f.toWhereStoreString(this.getDataSource()));
					}
				}
			}
		}
		return where.toString();
	}

	public String toGroupString(int type) throws Exception {
		StringBuffer group = new StringBuffer(" group by ");
		if (this.groupByList != null) {
			for (int i = 0; i < groupByList.size(); i++) {
				FieldDefVO fd = (FieldDefVO) groupByList.get(i);

				if (type == 0) {
					group.append(fd.getFromTable().getName() + "."
							+ fd.getToFieldName() + ",");
				} else {
					group.append(fd.getPrefix() + "." + fd.getToFieldName()
							+ ",");
				}
			}
		}
		if (groupByList != null && groupByList.size() > 0) {
			return group.toString().substring(0, group.toString().length() - 1);
		}

		return "";
	}

	public String toHavingString() throws Exception {
		StringBuffer having = new StringBuffer(" having 1=1");
		if (havingList != null) {
			for (int i = 0; i < havingList.size(); i++) {
				WhereVO f = (WhereVO) havingList.get(i);
				if (f.getPreviousCondition() != null) {

					having.append(f.getPreviousCondition() + " "
							+ f.toWhereString(this.getDataSource()));
				} else {
					having.append(" and "
							+ f.toWhereString(this.getDataSource()));
				}
			}
		}
		if (havingList != null && havingList.size() > 0) {
			return having.toString();
		}

		return "";
	}

	public String toOneSelectString() {
		StringBuffer select = new StringBuffer(" select ");
		StringBuffer field = new StringBuffer(" ");
		for (int i = 0; i < fieldList.size(); i++) {
			FieldDefVO fd = (FieldDefVO) fieldList.get(i);
			if (i > 0) {
				field.append(",");
			}
			field.append(fd.getToFieldName());
		}
		select.append(field + " ");
		return select.toString();
	}

	/**
	 * 转换类型
	 * 
	 * @param type
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public String changeType(String type, String value) {
		if (type.equalsIgnoreCase("Varchar") || type.equalsIgnoreCase("char")
				|| type.equalsIgnoreCase("boolean")) {
			return "'" + value + "'";
		} else if (type.equalsIgnoreCase("date")) {
			// if(value==null || value.trim().length()==0)
			String rePatUpdate1 = "yyyy-MM-dd";// this.judgeDateLength(value);
			return "to_date('" + value + "','" + rePatUpdate1 + "')";
		} else {
			return value;
		}
	}

	public List getGroupByList() {
		return groupByList;
	}

	public void setGroupByList(List groupByList) {
		this.groupByList = groupByList;
	}

	public List getDependList() {
		return dependList;
	}

	public void setDependList(List dependList) {
		this.dependList = dependList;
	}

	public List getHavingList() {
		return havingList;
	}

	public void setHavingList(List havingList) {
		this.havingList = havingList;
	}

	public List getOrderList() {
		return orderList;
	}

	public void setOrderList(List orderList) {
		this.orderList = orderList;
	}

	public int getIsWhere() {
		return isWhere;
	}

	public void setIsWhere(int isWhere) {
		this.isWhere = isWhere;
	}

	public TableVO reSet() {
		TableVO tb = new TableVO();
		tb.setDataSource(this.getDataSource());
		tb.setFieldList(this.getFieldList());
		tb.setFromTableList(this.getFromTableList());
		List w = new ArrayList();
		if (this.whereList != null) {
			for (int i = 0; i < this.whereList.size(); i++) {
				WhereVO where = (WhereVO) this.whereList.get(i);
				w.add(where);
			}
			tb.setWhereList(w);
		}
		tb.setPiecte(this.piecte);
		tb.setInsertFieldList(this.getInsertFieldList());
		tb.setUpdateFieldList(this.getUpdateFieldList());
		tb.setDeleteFieldList(this.getDeleteFieldList());
		tb.setKeyList(this.keyList);
		tb.setVariableList(this.variableList);
		tb.setName(this.getName());
		tb.setType(this.getType());
		tb.setRelationship(this.getRelationship());
		tb.setIsWhere(this.getIsWhere());
		tb.setOprType(this.oprType);
		tb.setSelect(this.select);
		tb.setInsert(this.insert);
		tb.setUpdate(this.update);
		tb.setDelete(this.delete);
		tb.setRegion(this.region);
		return tb;
	}

	public String toDeleteString() {
		StringBuffer delete = new StringBuffer(" delete from " + this.getName()
				+ " ");
		StringBuffer field = new StringBuffer();
		StringBuffer where = new StringBuffer(" where 1=1 ");
		if (this.where == null || this.where.trim().length() == 0) {
			if (keyList != null) {
				for (int i = 0; i < keyList.size(); i++) {
					FieldDefVO fd = (FieldDefVO) keyList.get(i);
					String v = fd.getFromFieldName();
					v = v.replaceAll("＄", "\\$");
					// String formFieldName = new
					// Formula().formulaParsre(this.getDataSource().getAccessType()
					// + "#"+ this.getDataSource().getDataType(), 0, v);
					// where.append("and " + fd.getToFieldName() + "="+
					// this.changeType(fd.getToFieldType(), v));
					where.append("and " + fd.getToFieldName() + "=" + " ? ");
				}
			}
		} else {
			where.append(this.where);
		}
		delete.append(field);
		delete.append(where);
		return delete.toString();
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public List getDeleteFieldList() {
		return deleteFieldList;
	}

	public void setDeleteFieldList(List deleteFieldList) {
		this.deleteFieldList = deleteFieldList;
	}

	public List getInsertFieldList() {
		return insertFieldList;
	}

	public void setInsertFieldList(List insertFieldList) {
		this.insertFieldList = insertFieldList;
	}

	public List getUpdateFieldList() {
		return updateFieldList;
	}

	public void setUpdateFieldList(List updateFieldList) {
		this.updateFieldList = updateFieldList;
	}

	public String getOprType() {
		return oprType;
	}

	public void setOprType(String oprType) {
		this.oprType = oprType;
	}

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	public List getVariableList() {
		return variableList;
	}

	public void setVariableList(List variableList) {
		this.variableList = variableList;
	}

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}

	public String getDelete() {
		return delete;
	}

	public void setDelete(String delete) {
		this.delete = delete;
	}

	public String getInsert() {
		return insert;
	}

	public void setInsert(String insert) {
		this.insert = insert;
	}

	public String getUpdate() {
		return update;
	}

	public void setUpdate(String update) {
		this.update = update;
	}

	public List getKeyList() {
		return keyList;
	}

	public void setKeyList(List keyList) {
		this.keyList = keyList;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getPiecte() {
		return piecte;
	}

	public void setPiecte(String piecte) {
		this.piecte = piecte;
	}

	public String getSameTable() {
		return sameTable;
	}

	public void setSameTable(String sameTable) {
		this.sameTable = sameTable;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public boolean getIsAggregation() {
		return isAggregation;
	}

	public void setIsAggregation(boolean isAggregation) {
		this.isAggregation = isAggregation;
	}

	public int getIsWrite() {
		return isWrite;
	}

	public void setIsWrite(int isWrite) {
		this.isWrite = isWrite;
	}

	public void setAggregation(boolean isAggregation) {
		this.isAggregation = isAggregation;
	}

	public Map getInsertParams() {
		return insertParams;
	}

	public void setInsertParams(Map insertParams) {
		this.insertParams = insertParams;
	}

	public Map getUpdateParams() {
		return updateParams;
	}

	public void setUpdateParams(HashMap updateParams) {
		this.updateParams = updateParams;
	}

	public Map getSelectParams() {
		return selectParams;
	}

	public void setSelectParams(Map selectParams) {
		this.selectParams = selectParams;
	}

	public List getWhereParams() {
		return whereParams;
	}

	public void setWhereParams(List whereParams) {
		this.whereParams = whereParams;
	}

}
