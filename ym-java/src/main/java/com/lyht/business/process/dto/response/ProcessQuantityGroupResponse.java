package com.lyht.business.process.dto.response;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProcessQuantityGroupResponse {
	@ApiModelProperty(value = "流程名称")
	private String name;

	@ApiModelProperty(value = "待办--数量")
	private Long standbyCount;

	@ApiModelProperty(value = "已通过--数量")
	private Long approvedCount;

	@ApiModelProperty(value = "已拒绝--数量")
	private Long rejectedStatus;

	@ApiModelProperty(value = "已提交--数量")
	private Long submitCount;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
