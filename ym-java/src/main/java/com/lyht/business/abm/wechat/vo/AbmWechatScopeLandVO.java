package com.lyht.business.abm.wechat.vo;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "征地范围--土地--列表")
public interface AbmWechatScopeLandVO {

	@ApiModelProperty("征地范围")
	String getScope();

	@ApiModelProperty("土地面积")
	BigDecimal getLandArea();

}