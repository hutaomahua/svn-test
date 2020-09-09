package com.lyht.business.process.common.enums;

import com.lyht.base.common.enums.ExceptionEnums;

/**
 * 
 * @author hxl
 *
 */
public enum LyhtProcessExceptionEnums implements ExceptionEnums{
    
	PROCESS_NOT_EXIST("-17009","流程不存在"),
	PROCESS_CANCLED_ALREAD("-17008","流程已取消"),
	TOKEN_CREATE_FAILURE("-17007", "流程token获取失败"),
	PROCESS_PROCEED_FAILURE("-17006", "流程审批失败"),
	TASKID_IS_NOT_NULL("-17005", "流程ID与步骤ID不能为空"),
	PROCESS_FAILURE("-17004", "流程失败"),
	RESULT_IS_NOT_NULL("-17003", "审批类型不能为空（Approved:同意，Rejected: 拒绝, PickBack:退回）"),
	USER_IS_NOT_NULL("-17002", "用户ID不能为空（用户信息未同步到流程系统）"),
	TASKID_AND_STEPID_IS_NOT_NULL("-17001", "流程ID与步骤ID不能为空"),
	CONNECTION_TIME_OUT("-17010", "请求超时,请稍后重试!");
	
	

	private String code;
	private String msg;

	LyhtProcessExceptionEnums(String code, String msg) {
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
