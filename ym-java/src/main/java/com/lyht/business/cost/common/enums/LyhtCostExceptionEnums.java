package com.lyht.business.cost.common.enums;

import com.lyht.base.common.enums.ExceptionEnums;

/**
 * 
 * @author hxl
 *
 */
public enum LyhtCostExceptionEnums implements ExceptionEnums{
	COST_FAILURE("-19001", "计算失败"),
	COST_NON_EXIST("-19002", "补偿费用记录不存在");
	

	private String code;
	private String msg;

	LyhtCostExceptionEnums(String code, String msg) {
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
