package com.zxt.compplatform.formengine.entity.dataset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 操作vo
 * 
 * @author 007
 */
public class OperateDefVO extends BaseVO {

	/**
	 * 符号表达式；函数 保留字
	 */
	private int operateType;//  
	/**
	 * 0表示第一个，依次类推
	 */
	private int symbolIndex;//
	/**
	 * 表示是算术、比较、关系、逻辑哪种运算符
	 */
	private int symbolType;//
	/**
	 * 合计
	 */
	private boolean isAggregation;
	/**
	 * 优先级高的数值小
	 */
	public static final int ARITHMETIC_SYMBOL = 1;//
	public static final int RELATIONAL_SYMBOL = 2;
	public static final int LOGICAL_SYMBOL = 3;
	// public static final int COMPARISON_SYMBOL=4;(不要了)

	/**
	 * 单元符号的操作数位置
	 */
	private int oneSymbolword;//
	/**
	 * 操作数在前
	 */
	public static final int FORWORD = 0;//
	/**
	 * 操作数在后
	 */
	public static final int BACKWORD = 1;//
	/**
	 * 操作数在前在后皆可
	 */
	public static final int BOTHWORD = 2;//
	/**
	 * 符号的优先级，当符号的操作数相同、类型也相同时用于表达符号的优先级，通过整数的大小表示，愈小的优先级越高
	 */
	private int symbolPriority;//
	/**
	 * 操作列表
	 */
	private List operate = new ArrayList();
	/**
	 * 操作名称
	 */
	private String operateName; // 
	/**
	 * 操作数个数
	 */
	private int operateNum; // 

	/**
	 * 返回值类型
	 */
	private String returnType; // 
	/**
	 * 转换规则
	 */
	private Map operateMappingDef;// 

	public int getOneSymbolword() {
		return oneSymbolword;
	}

	public void setOneSymbolword(int oneSymbolword) {
		this.oneSymbolword = oneSymbolword;
	}

	public List getOperate() {
		return operate;
	}

	public void setOperate(Object operate) {
		this.operate.add(operate);
	}

	public Map getOperateMappingDef() {
		return operateMappingDef;
	}

	public void setOperateMappingDef(Map operateMappingDef) {
		this.operateMappingDef = operateMappingDef;
	}

	public String getOperateName() {
		return operateName;
	}

	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}

	public int getOperateNum() {
		return operateNum;
	}

	public void setOperateNum(int operateNum) {
		this.operateNum = operateNum;
	}

	public int getOperateType() {
		return operateType;
	}

	public void setOperateType(int operateType) {
		this.operateType = operateType;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public int getSymbolIndex() {
		return symbolIndex;
	}

	public void setSymbolIndex(int symbolIndex) {
		this.symbolIndex = symbolIndex;
	}

	public int getSymbolPriority() {
		return symbolPriority;
	}

	public void setSymbolPriority(int symbolPriority) {
		this.symbolPriority = symbolPriority;
	}

	public int getSymbolType() {
		return symbolType;
	}

	public void setSymbolType(int symbolType) {
		this.symbolType = symbolType;
	}

	public boolean getIsAggregation() {
		return isAggregation;
	}

	public void setAggregation(boolean isAggregation) {
		this.isAggregation = isAggregation;
	}

}
