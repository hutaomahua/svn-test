package com.lyht.base.common.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.lyht.base.common.enums.ExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.response.ExceptionResponse;

/**
 * 异常处理器
 * 
 * @author hxl
 *
 */
@ControllerAdvice
public class LyhtExceptionHandler {
	@ExceptionHandler(LyhtRuntimeException.class) // 仅处理Lyht自定义异常
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ExceptionResponse handlerExc(LyhtRuntimeException e) {
		ExceptionEnums exceptionEnums = e.getExceptionEnums();
		if (exceptionEnums == null) {
			String message = e.getMessage();
			return new ExceptionResponse(message);
		}
		return new ExceptionResponse(exceptionEnums.getCode(), exceptionEnums.getMsg());
	}

}
