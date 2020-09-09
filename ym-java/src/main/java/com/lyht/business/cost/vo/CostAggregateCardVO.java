package com.lyht.business.cost.vo;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("补偿费用汇总--分项")
public interface CostAggregateCardVO {

	@ApiModelProperty("项目")
	String getProject();

	@ApiModelProperty("单位")
	String getUnit();

	@ApiModelProperty("枢纽工程建设区--合计")
	BigDecimal getReservoirTotal();

	@ApiModelProperty("书库淹没影响区--合计")
	BigDecimal getPivotTotal();

	@ApiModelProperty("枢纽临时")
	BigDecimal getTemporary();

	@ApiModelProperty("枢纽永久")
	BigDecimal getPermanent();

	@ApiModelProperty("水库淹没区")
	BigDecimal getFlood();

	@ApiModelProperty("水库影响区")
	BigDecimal getInfluence();

	@ApiModelProperty("垫高（临时）")
	BigDecimal getRaise();

	@ApiModelProperty("集镇新址占地区")
	BigDecimal getNewTown();

}
