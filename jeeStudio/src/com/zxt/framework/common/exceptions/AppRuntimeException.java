/**
* Copyright© 2010 zxt Co. Ltd.
* All right reserved.
* 
*/
package com.zxt.framework.common.exceptions;
/**
 * Title: ExceptionFormatter
 * Description:  
 * Create DateTime: 2010-9-10
 * @author xxl
 * @since v1.0
 *  
 *  */
import org.springframework.dao.DataAccessException;

public class AppRuntimeException extends DataAccessException {
	
	private static final long serialVersionUID = 8627024721131510633L;
	
	public AppRuntimeException(String msg) {
		super(msg);
	}

	public AppRuntimeException(String msg, Throwable ex) {
		super(msg, ex);
	}
}