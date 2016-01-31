package com.zxt.compplatform.formengine.entity.dataset;

/**
 * 实体联系VO
 * 
 * @author 007
 */
public class RelationshipVO extends BaseVO {
	/**
	 * first one
	 */
	private TableVO first;
	/**
	 * second one
	 */
	private TableVO second;

	public TableVO getFirst() {
		return first;
	}

	public void setFirst(TableVO first) {
		this.first = first;
	}

	public TableVO getSecond() {
		return second;
	}

	public void setSecond(TableVO second) {
		this.second = second;
	}
}
