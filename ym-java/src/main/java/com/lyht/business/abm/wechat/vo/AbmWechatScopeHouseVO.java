package com.lyht.business.abm.wechat.vo;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "征地范围--房屋--列表")
public interface AbmWechatScopeHouseVO {

	@ApiModelProperty("征地范围")
	String getScope();

	@ApiModelProperty("房屋面积")
	BigDecimal getHouseArea();

}