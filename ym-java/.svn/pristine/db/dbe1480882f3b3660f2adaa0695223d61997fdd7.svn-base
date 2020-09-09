package com.lyht.business.info.vo;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("土地图表汇总")
public interface InfoLandAggregateVO {

	@ApiModelProperty("行政区--编码")
	String getCityCode();

	@ApiModelProperty("行政区--父级编码")
	String getParentCode();
	
	@ApiModelProperty("行政区--名称")
	String getName();
	
	@ApiModelProperty("行政区--全称")
	String getMergerName();
	
	@ApiModelProperty("行政区--级别")
	String getLevel();
	
	@ApiModelProperty("总面积")
	BigDecimal getTotal();
	
	@ApiModelProperty("水库淹没影响区--合计")
	BigDecimal getPivotTotal();

	@ApiModelProperty("枢纽工程建设区--合计")
	BigDecimal getReservoirTotal();

	@ApiModelProperty("枢纽临时")
	BigDecimal getTemporary();

	@ApiModelProperty("枢纽永久")
	BigDecimal getPermanent();

	@ApiModelProperty("水库淹没区")
	BigDecimal getFlood();

	@ApiModelProperty("水库影响区")
	BigDecimal getInfluence();

	@ApiModelProperty("垫高(临时)")
	BigDecimal getRaise();

	@ApiModelProperty("集镇新址占地区")
	BigDecimal getNewTown();

}
