package com.lyht.business.abm.wechat.vo;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "指标统计--按行政区")
public interface AbmWechatRegionAggregateVO {

	@ApiModelProperty("行政区--全称")
	String getMergerName();
	
	@ApiModelProperty("行政区--简称")
	String getName();

	@ApiModelProperty("户数")
	Long getHouseholds();

	@ApiModelProperty("人口")
	Long getPopulation();

	@ApiModelProperty("房屋面积")
	BigDecimal getHouseArea();

	@ApiModelProperty("土地面积")
	BigDecimal getLandArea();

}