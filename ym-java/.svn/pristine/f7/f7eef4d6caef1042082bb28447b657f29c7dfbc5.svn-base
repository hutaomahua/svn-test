package com.lyht.business.info.vo;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("实物指标汇总--户主/企事业单位")
public interface InfoOwnerAggregateVO {

	@ApiModelProperty("权属人(户主/企事业单位)编码")
	String getNm();

	@ApiModelProperty("户主姓名")
	String getName();

	@ApiModelProperty("城乡区划(农村/集镇)")
	String getZblx();

	@ApiModelProperty("征地范围")
	String getScope();
	
	@ApiModelProperty("类型（owner：权属人；enter：企事业单位）")
	String getType();

	@ApiModelProperty("移民人口(人)")
	BigDecimal getPopulation();

	@ApiModelProperty("房屋(㎡)")
	BigDecimal getHouseArea();

	@ApiModelProperty("房屋装修(㎡)")
	BigDecimal getHouseDecorationArea();

	@ApiModelProperty("零星果木(棵)")
	BigDecimal getTreeNumber();

	@ApiModelProperty("宅基地(㎡)")
	BigDecimal getHomesteadArea();

}
