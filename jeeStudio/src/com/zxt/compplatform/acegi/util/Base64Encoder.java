package com.zxt.compplatform.acegi.util;

import org.springframework.dao.DataAccessException;
import org.springframework.security.providers.encoding.PasswordEncoder;

import com.synchroflow.common.encrypt.BASE64Encoder;


/**
 * 采用BASE64加密算法，给特定的密码进行加密
 * @author 007
 *
 */
public class Base64Encoder implements PasswordEncoder {

	/**
	 * 对密码进行盐值加密
	 * @see org.springframework.security.providers.encoding.PasswordEncoder#encodePassword(java.lang.String, java.lang.Object)
	 */
	public String encodePassword(String pass, Object salt)
			throws DataAccessException {
		BASE64Encoder baseEncoder = new BASE64Encoder();
	    byte[] bpassword = pass.getBytes();
	    String encrptKey = baseEncoder.encodeBuffer(bpassword);
	    return encrptKey;
	}

	/**
	 * 比较已经加密的和未加密的密码是否相同 
	 * @see org.springframework.security.providers.encoding.PasswordEncoder#isPasswordValid(java.lang.String, java.lang.String, java.lang.Object)
	 */
	public boolean isPasswordValid(String encPass, String rawPass, Object salt)
			throws DataAccessException {
		 String pass1 = "" + encPass;   
	     String pass2 = encodePassword(rawPass, salt);   
	     return pass1.equals(pass2);   
	}

}
