

package com.zxt.compplatform.acegi.util;

import org.springframework.dao.DataAccessException;
import org.springframework.security.providers.encoding.PasswordEncoder;



/**
 * 对特定的字符串进行BaseMD5盐值加密
 * @author 007
 *
 */
public class BaseMD5Encoder implements PasswordEncoder {

	/** 
	 * 对字符串进行盐值BAseMD5加密
	 * @see org.springframework.security.providers.encoding.PasswordEncoder#encodePassword(java.lang.String, java.lang.Object)
	 */
	public String encodePassword(String pass, Object salt)
			throws DataAccessException {
		  //String encrptKey = new MD5().getMD5ofStr(pass);
			String encrptKey="";
		    return encrptKey;
	}

	/**
	 * 对比两个密码是不是相同
	 * @see org.springframework.security.providers.encoding.PasswordEncoder#isPasswordValid(java.lang.String, java.lang.String, java.lang.Object)
	 */
	public boolean isPasswordValid(String encPass, String rawPass, Object salt)
			throws DataAccessException {
		 String pass1 = "" + encPass;   
	     String pass2 = encodePassword(rawPass, salt);   
	     return pass1.equals(pass2);   
	}

}
