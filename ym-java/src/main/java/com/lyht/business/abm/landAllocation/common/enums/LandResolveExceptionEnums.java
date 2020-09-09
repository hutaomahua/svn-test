package com.lyht.business.abm.landAllocation.common.enums;

import com.lyht.base.common.enums.ExceptionEnums;

/**
 * 	lyht系统异常枚举
 * 
 * @author hxl
 *
 */
public enum LandResolveExceptionEnums implements ExceptionEnums{
	INVALID_AREA_TYPE("-7101", "无效的类型（面积）"), INVALID_LEVEL("-7102", "无效的级别");

	private String code;
	private String msg;

	LandResolveExceptionEnums(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	@Override
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
