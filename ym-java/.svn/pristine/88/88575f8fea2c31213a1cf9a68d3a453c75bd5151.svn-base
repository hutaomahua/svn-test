package com.lyht.base.common.vo.response;

import com.alibaba.fastjson.JSON;

import lombok.Data;

/**
 * 异常返回
 * 
 * @author hxl
 *
 */
@Data
public class ExceptionResponse {

	private String code;
	private String msg;

	public ExceptionResponse() {
		this.code = "-1";
		this.msg = "失败";
	}

	public ExceptionResponse(String msg) {
		this.code = "-1";
		this.msg = msg;
	}

	public ExceptionResponse(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
