package com.lyht.business.process.dto.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import lombok.ToString;

/**
 * 同步接口响应对象
 * 
 * @author hxl
 *
 */
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
@Data
@ToString
public class ProcessResponse {

	// 响应识别码(0：成功，其他：失败)
	//@JsonIgnore
	private Integer errorCode;
	// 响应信息
	//@JsonIgnore
	private String message;
	// 响应数据
	//@JsonIgnore
	public String data;
	// 响应状态(true:成功，false：失败)
	//@JsonIgnore
	private Boolean success;

}
