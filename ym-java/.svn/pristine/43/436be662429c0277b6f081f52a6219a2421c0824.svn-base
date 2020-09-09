package com.lyht.business.tab.common.enums;

import com.lyht.base.common.enums.ExceptionEnums;

/**
 * 	lyht系统异常枚举
 * 
 * @author hxl
 *
 */
public enum TabExceptionEnums implements ExceptionEnums{
	PARAM_IS_NOT_NULL("-1001", "参数不能为空"), TABLE_NAME_IS_NOT_NULL("-1002","表名、表中文名不能为空"),
	TABLE_IS_UNIQUE("-1003","表名已存在，不能重复"),ID_IS_NOT_NULL("-1004","ID不能为空"),
	NM_IS_NOT_NULL("-1005", "内码不能为空"),TABLE_NON_EXISTENT("-1006","表不存在"),
	TABLE_FIELD_NON_EXISTENT("-1007","字段不存在"),TABLE_FIELD_IS_UNIQUE("-1011","字段名已存在，不能重复"),
	INSERT_FAILURE("-1008","新增失败"), UPDATE_FAILURE("-1009","修改失败"),
	DELETE_FAILURE("-1010", "删除失败"),CREATE_TABLE_FAILURE("-1012", "创建表失败"),
	UPDATE_TABLE_FAILURE("-1013","修改表失败"),DELETE_TABLE_FAILURE("-1014","删除表失败"),
	DEFAULT_VALUE_LENGTH_TOO_LONG("-1015", "字段默认值过长"), DEFAULT_VALUE_INVALID("-1016","默认值与字段类型不匹配"),
	DECIMAL_TOO_LONG("-1017","小数位数超长");

	private String code;
	private String msg;

	TabExceptionEnums(String code, String msg) {
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
