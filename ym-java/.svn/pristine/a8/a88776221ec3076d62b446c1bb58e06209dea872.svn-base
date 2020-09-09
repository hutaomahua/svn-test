package com.lyht.business.process.dto.response;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 同步接口响应对象
 * @author hxl
 *
 */
public class SyncDataResponse {

	// 响应识别码(0：成功，其他：失败)
	@JsonProperty(value = "ErrorCode")
	private Integer ErrorCode;
	// 响应信息
	@JsonProperty(value = "Message")
	private String Message;
	// 响应数据
	@JsonProperty(value = "Data")
	private String Data;
	// 响应状态(true:成功，false：失败)
	@JsonProperty(value = "Success")
	private Boolean Success;
	
	public Integer getErrorCode() {
		return ErrorCode;
	}

	public void setErrorCode(Integer errorCode) {
		ErrorCode = errorCode;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public String getData() {
		return Data;
	}

	public void setData(String data) {
		Data = data;
	}

	public Boolean getSuccess() {
		return Success;
	}

	public void setSuccess(Boolean success) {
		Success = success;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
