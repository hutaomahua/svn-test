package com.lyht.business.abm.wechat.vo;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "行政区--权属人--列表信息")
public interface AbmWechatRegionOwnerVO {

	@ApiModelProperty("行政区--全称")
	String getMergerName();
	
	@ApiModelProperty("行政区--简称")
	String getName();
	
	@ApiModelProperty("行政区--编码")
	String getCityCode();

	@ApiModelProperty("户数")
	BigDecimal getHouseholds();

	@ApiModelProperty("人口")
	BigDecimal getPopulation();

}