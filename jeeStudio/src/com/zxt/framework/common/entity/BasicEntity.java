package com.zxt.framework.common.entity;

import java.io.Serializable;

/**
 * 实体基类
 * @author 007
 */
public class BasicEntity implements Serializable, Cloneable {

	private static final long serialVersionUID = 8748910752975451451L;

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}