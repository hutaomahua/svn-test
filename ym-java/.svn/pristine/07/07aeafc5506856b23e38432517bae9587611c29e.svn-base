package com.lyht.business.process.vo;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @author hxl
 *
 */
@ApiModel(description = "流程数量汇总")
@Data
public class ProcessCountVO {

	@ApiModelProperty(value = "待办数量")
	private Long standby;

	@ApiModelProperty(value = "已办数量")
	private Long handled;

	@ApiModelProperty(value = "已提交数量")
	private Long submit;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}