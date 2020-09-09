package com.lyht.business.abm.household.common.enums;

import com.lyht.base.common.enums.ExceptionEnums;

/**
 * lyht系统异常枚举
 * 
 * @author hxl
 *
 */
public enum SplitHouseholdExceptionEnums implements ExceptionEnums {
	NOT_EXIST_OR_HAS_EXPIRED("-7777", "分户数据不存在或已过期，请重新开始分户！");

	private String code;
	private String msg;

	SplitHouseholdExceptionEnums(String code, String msg) {
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
