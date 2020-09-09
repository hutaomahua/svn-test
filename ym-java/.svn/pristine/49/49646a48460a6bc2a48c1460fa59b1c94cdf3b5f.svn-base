package com.lyht.base.common.exception;

import com.lyht.base.common.enums.ExceptionEnums;

public class LyhtRuntimeException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	private ExceptionEnums exceptionEnums;
	
    public LyhtRuntimeException(){
        super();
    }
    
    public LyhtRuntimeException(String message){
        super(message);
    }
    
    public LyhtRuntimeException(String message, Throwable cause){
        super(message,cause);
    }
    
    public LyhtRuntimeException(Throwable cause){
        super(cause);
    }
    
    public LyhtRuntimeException(ExceptionEnums  exceptionEnums) {
        this.exceptionEnums = exceptionEnums;
    }

	public ExceptionEnums getExceptionEnums() {
		return exceptionEnums;
	}

	public void setExceptionEnums(ExceptionEnums exceptionEnums) {
		this.exceptionEnums = exceptionEnums;
	}
}
