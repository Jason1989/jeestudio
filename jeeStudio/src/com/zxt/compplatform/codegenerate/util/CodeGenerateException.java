package com.zxt.compplatform.codegenerate.util;

import org.apache.commons.lang.exception.NestableRuntimeException;

public class CodeGenerateException extends NestableRuntimeException {
	public CodeGenerateException(){
    }
	public CodeGenerateException(String msg){
		super(msg, null);
	}


    public CodeGenerateException(String msg, Throwable cause){
        super(msg, cause);
    }

    public CodeGenerateException(Throwable cause)
    {
        super(cause != null ? cause.toString() : null, cause);
    }
}
