package com.lyht.business.process.dto.response;


import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author HuangTianhao
 * @date 2019年12月16日
 * 流程删除响应对象
 */
public class ProcessRemoveResponse {

	@JSONField(name = "Data")
	private boolean data;
	@JSONField(name = "ErrorCode")
	private int errorcode;
	@JSONField(name = "Message")
	private String message;
	@JSONField(name = "Success")
	private boolean success;

	public void setData(boolean data) {
		this.data = data;
	}

	public boolean getData() {
		return data;
	}

	public void setErrorcode(int errorcode) {
		this.errorcode = errorcode;
	}

	public int getErrorcode() {
		return errorcode;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean getSuccess() {
		return success;
	}

}