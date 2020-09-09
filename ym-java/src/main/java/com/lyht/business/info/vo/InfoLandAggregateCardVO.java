package com.lyht.business.info.vo;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("土地图表汇总")
public interface InfoLandAggregateCardVO {

	@ApiModelProperty("项目")
	String getProject();

	@ApiModelProperty("单位")
	String getUnit();

	@ApiModelProperty("土地分类编码")
	String getLandType();

	@ApiModelProperty("面积")
	BigDecimal getArea();

}
