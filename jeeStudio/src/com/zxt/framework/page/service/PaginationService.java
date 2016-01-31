package com.zxt.framework.page.service;

import java.util.Collection;

import com.zxt.framework.common.exceptions.AppException;
import com.zxt.framework.page.entity.PaginationEntity;

/**
 * Title: PaginationEntity
 * Description: 分页服务
 * Create DateTime: 2010-9-29
 * @author xxl
 * @since v1.0
 * 
 */
public abstract interface PaginationService {
	/**
	 * 分页接口
	 * @param paramInt
	 * @param paramString
	 * @param page
	 * @param paramCollection
	 * @return
	 * @throws AppException
	 */
	 public abstract PaginationEntity findPageBySql(int paramInt, String paramString, PaginationEntity page, Collection paramCollection)
	    throws AppException;

}
