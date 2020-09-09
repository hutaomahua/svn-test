package com.lyht.business.process.dto.response;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

import io.swagger.annotations.ApiModelProperty;

public class ProcessMessage<T> {
	@ApiModelProperty(value = "响应识别码(0：成功，其他：失败)")
	@JSONField(name = "errorCode")
	private Integer errorCode;

	@ApiModelProperty(value = "响应信息")
	@JSONField(name = "message")
	private String message;

	@ApiModelProperty(value = "响应数据")
	@JSONField(name = "data")
	private T data;

	@ApiModelProperty(value = "响应状态(true:成功，false：失败)")
	@JSONField(name = "success")
	private Boolean success;

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
