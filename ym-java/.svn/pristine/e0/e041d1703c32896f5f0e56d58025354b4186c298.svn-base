package com.lyht.base.common.enums.imp;

import com.lyht.base.common.constant.LyhtResultConst;
import com.lyht.base.common.enums.ExceptionEnums;

/**
 * 	lyht系统异常枚举
 * 
 * @author hxl
 *
 */
public enum LyhtExceptionEnums implements ExceptionEnums{
	SUCCESS(LyhtResultConst.SUCCESS, "成功"), FAILURE(LyhtResultConst.FAILURE, "失败"),
	SERVER_ERROR(LyhtResultConst.SERVER_ERROR, "系统异常"), INVALID_PARAMETERS(LyhtResultConst.INVALID_PARAMETERS, "无效的参数"),DICT_NAME_FALSE(LyhtResultConst.DICT_NAME_FALSE, "字典名称重复"),
	SORT_INVALID(LyhtResultConst.SORT_INVALID, "排序参数无效"),LOGINFALSE(LyhtResultConst.FAILURE, "登录失败"),LOGINPWDFALSE(LyhtResultConst.LOGINPWDFALSE, "密码错误"),LOGINUSERNAMEFALSE(LyhtResultConst.LOGINUSERNAMEFALSE, "用户名未找到"),
	DOWNLOAD_FAILURE(LyhtResultConst.DOWNLOAD_FAILURE, "附件下载失败"), LOGINNAMEFALSE(LyhtResultConst.LOGINUSERNAMEFALSE, "账号未找到"),LOGINNAMEHAVEFALSE(LyhtResultConst.LOGINNAMEHAVEFALSE, "账号已存在"),
	OLD_PASSWORD_ERROR(LyhtResultConst.OLD_PASSWORD_ERROR , "原密码不正确！"),
	ENTERED_PASSWORDS_DIFFER(LyhtResultConst.ENTERED_PASSWORDS_DIFFER , "两次输入密码不一致，请重新输入！"),
	ENTERED_PASSWORDS_DIFFERS(LyhtResultConst.ENTERED_PASSWORDS_DIFFERS , "新密码不能与原密码一致，请重新输入！"),
	SUPER_ERROR(LyhtResultConst.SUPER_ERROR, "所选父类无效，请重新选择"),
	DUPLICATE_DATA(LyhtResultConst.DUPLICATE_DATA, "数据重复 不可添加重复数据"),
	DYNAMIC_ERROR("-99",""), DATA_UNREPEATABLE(LyhtResultConst.DATA_UNREPEATABLE, "参数不可重复"),
	FILE_COUNT_EXCEPTION(LyhtResultConst.FILE_COUNT_EXCEPTION, "补偿协议附件未上传"),ESCROW_NOTFOUND(LyhtResultConst.ESCROW_NOTFOUND, "资金代管协议未签订"),
	THE_MAX_NUMBER(LyhtResultConst.THE_MAX_NUMBER, "复核申请编号已达当日最大值"),ESCROW_FILE_COUNT_EXCEPTION(LyhtResultConst.ESCROW_FILE_COUNT_EXCEPTION, "资金代管协议未上传附件");

	private String code;
	private String msg;

	LyhtExceptionEnums(String code, String msg) {
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

	public static LyhtExceptionEnums getDynamicException(String code, String msg) {
		DYNAMIC_ERROR.code = code;
		DYNAMIC_ERROR.msg = msg;
		return DYNAMIC_ERROR;
	}
}
