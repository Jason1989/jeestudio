/**
* Copyright© 2010 zxt Co. Ltd.
* All right reserved.
* 
*/
package com.zxt.framework.common.dao;
/**
 * Title: ICacheListener
 * Description:  
 * Create DateTime: 2010-9-10
 * @author xxl
 * @since v1.0
 * 
 */
public abstract interface ICacheListener
{
  public static final int INSERT = 1;
  public static final int UPDATE = 2;
  public static final int DELETE = 3;
  public static final int REFRESH = 4;

  public abstract void updateCache(Class paramClass, int paramInt);
}