package com.lyht.business.abm.signed.vo;

import io.swagger.annotations.ApiModelProperty;

public interface ProtocolItemVO {

	@ApiModelProperty(value = "签订项名称")
	String getName();

	@ApiModelProperty(value = "金额")
	Double getTotal();

	@ApiModelProperty(value = "状态 (0：正常，1：有补偿，允许签订协议 启用，2：无补偿，不允许签订协议 禁用,3:已签订协议，不允许重复签订 禁用） ")
	Integer getFlag();

}