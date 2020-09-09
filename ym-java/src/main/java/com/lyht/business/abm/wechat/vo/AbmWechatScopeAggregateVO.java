package com.lyht.business.abm.wechat.vo;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "指标统计--按征地范围")
public interface AbmWechatScopeAggregateVO {

	@ApiModelProperty("征地范围")
	String getScope();

	@ApiModelProperty("户数")
	Long getHouseholds();

	@ApiModelProperty("人口")
	Long getPopulation();

	@ApiModelProperty("房屋面积")
	BigDecimal getHouseArea();

	@ApiModelProperty("土地面积")
	BigDecimal getLandArea();

}