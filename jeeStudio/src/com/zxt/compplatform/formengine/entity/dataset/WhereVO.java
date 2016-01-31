package com.zxt.compplatform.formengine.entity.dataset;

import com.zxt.compplatform.datasource.entity.DataSource;
import com.zxt.compplatform.formengine.service.ResolveXmlService;

/**
 * 跟where条件相关的属性
 */
public class WhereVO extends BaseVO {

	/**
	 * 存放第一个操作数
	 */
	private TableVO first;// 
	/**
	 * 存放第二个操作数
	 */
	private TableVO second;// 
	/**
	 * 操作符
	 */
	private String operate;// 
	/**
	 *  连接符号 and ，or
	 */
	private String previousCondition;//
	/**
	 * 条件
	 */
	private String where;

	public TableVO getFirst() {
		return first;
	}

	public void setFirst(TableVO first) {
		this.first = first;
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public TableVO getSecond() {
		return second;
	}

	public void setSecond(TableVO second) {
		this.second = second;
	}

	public String toString() {
		return first.getName() + operate + second.getName();
	}

	public String toWhereString(DataSource dataSource) throws Exception {
		StringBuffer where = new StringBuffer("");
		if (first.getDataSource() != null) {
			where.append(first.getName()
					+ "."
					+ ((FieldDefVO) first.getFieldList().get(0))
							.getToFieldName());
		} else {
			where.append(first.getName());
		}
		where.append(" " + operate + " ");
		// System.out.println(where.toString());
		if (second.getDataSource() != null) {
			where.append(second.getName()
					+ "."
					+ ((FieldDefVO) second.getFieldList().get(0))
							.getToFieldName());
		} else {
			if (first.getFieldList() != null) {
				if (second.getName().indexOf("$P") >= 0) {
					// where.append(second.getName());
					where.append(" ? ");
				} else {
					if ("LIKE".equals(operate)) {
						String temp = new ResolveXmlService().changeType(
								((FieldDefVO) first.getFieldList().get(0))
										.getToFieldType(), second.getName());
						temp = temp.substring(1, temp.length() - 1);
						temp = "'%" + temp + "%'";
						where.append(temp);
					} else {
						where.append(new ResolveXmlService().changeType(
								((FieldDefVO) first.getFieldList().get(0))
										.getToFieldType(), second.getName()));
					}

				}

			} else {
				if (second.getName().indexOf("$P") >= 0) {
					// where.append(second.getName());
					where.append(" ? ");
				} else {
					where.append(new ResolveXmlService().changeType(first
							.getDataType(), second.getName()));
				}
			}
		}
		// System.out.println(where.toString());
		return where.toString();

	}

	public String toWhereString(DataSource dataSource, WhereVO wherevo)
			throws Exception {
		StringBuffer where = new StringBuffer("");
		if (first.getDataSource() != null) {
			where.append(first.getName()
					+ "."
					+ ((FieldDefVO) first.getFieldList().get(0))
							.getToFieldName());
		} else {
			where.append(first.getName());
		}
		where.append(" " + operate + " ");
		// System.out.println(where.toString());
		if (second.getDataSource() != null) {
			where.append(second.getName()
					+ "."
					+ ((FieldDefVO) second.getFieldList().get(0))
							.getToFieldName());
		} else {
			if (first.getFieldList() != null) {
				if (second.getName().indexOf("$F") >= 0) {
					where.append(second.getName());
				} else {
					where.append(" ? ");
				}

			} else {
				if (second.getName().indexOf("$F") >= 0) {
					where.append(second.getName());
				} else {
					where.append(new ResolveXmlService().changeType(first
							.getDataType(), second.getName()));
				}
				if (second.getType().equals("PARAM")) {

				}
			}
		}
		// System.out.println(where.toString());
		return where.toString();

	}

	public String toFirstString(DataSource dataSource) throws Exception {
		StringBuffer where = new StringBuffer("");
		if (this.getType() != null) {
			where.append(this.where);
		} else {
			if (first.getDataSource() != null) {
				where.append(first.getName()
						+ "."
						+ ((FieldDefVO) first.getFieldList().get(0))
								.getToFieldName());
			} else {
				where.append(first.getName());
			}
		}
		return where.toString();
	}

	public String toSecondString(DataSource dataSource) throws Exception {
		StringBuffer where = new StringBuffer("");
		String name = null;
		if (second.getDataSource() != null) {
			where.append(second.getName()
					+ "."
					+ ((FieldDefVO) second.getFieldList().get(0))
							.getToFieldName());
		} else {
			if (first.getFieldList() != null) {
				name = second.getName();
				if (second.getName().indexOf("$F") >= 0) {
					where.append(name);
				} else {
					if (second.getType().equals("VALUE")) {
						name = name.replaceAll("\'", "\'\'");
						where.append(new ResolveXmlService().changeValueType(
								((FieldDefVO) first.getFieldList().get(0))
										.getToFieldType(), name));
					} else {
						where.append(new ResolveXmlService().changeOtherType(
								((FieldDefVO) first.getFieldList().get(0))
										.getToFieldType(), name));
					}
				}
			} else {
				String secondName = second.getName();
				if (second.getName().indexOf("$F") >= 0) {
					where.append(secondName);
				} else {
					if (second.getType().equals("VALUE")) {
						secondName = name.replaceAll("\'", "\'\'");
						where.append(new ResolveXmlService().changeValueType(
								first.getDataType(), secondName));
					} else if (second.getType().equals("PARAM")) {
						where.append("?");
					} else {
						where.append(new ResolveXmlService().changeOtherType(
								first.getDataType(), secondName));
					}
				}
			}
		}

		return where.toString();
	}

	public String toWhereStoreString(DataSource dataSource) throws Exception {
		StringBuffer where = new StringBuffer("");
		if (first.getDataSource() != null) {
			where.append(first.getName()
					+ "."
					+ ((FieldDefVO) first.getFieldList().get(0))
							.getToFieldName());
		} else {
			// String firstName = new
			// Formula().formulaParsre(dataSource.getType() + "#"+
			// dataSource.getDataType(), 0,first.getName());
			// firstName = new FormulaParser().deleteFirstDot(firstName);
			// where.append(firstName);
		}
		where.append(" " + operate + " ");
		if (second.getDataSource() != null) {
			where.append(second.getName()
					+ "."
					+ ((FieldDefVO) second.getFieldList().get(0))
							.getToFieldName());
		} else {
			if (first.getFieldList() != null) {
				// String name = new
				// Formula().formulaParsre(dataSource.getType() + "#"+
				// dataSource.getDataType(), 0,second.getName());
				// name = new FormulaParser().deleteFirstDot(name);
				where.append(new ResolveXmlService().changeType(
						((FieldDefVO) first.getFieldList().get(0))
								.getToFieldType(), second.getName()));
			} else {
				// String secondName = new
				// Formula().formulaParsre(dataSource.getType() + "#" +
				// dataSource.getDataType(), 0,second.getName());
				// secondName = new FormulaParser().deleteFirstDot(secondName);
				where.append(new ResolveXmlService().changeType(first
						.getDataType(), second.getName()));
			}
		}
		return where.toString();
	}

	public String getPreviousCondition() {
		return previousCondition;
	}

	public void setPreviousCondition(String previousCondition) {
		this.previousCondition = previousCondition;
	}

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}

}
