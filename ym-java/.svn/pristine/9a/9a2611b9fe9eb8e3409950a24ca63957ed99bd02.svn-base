package com.lyht.business.info.vo;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("基础数据汇总--按行政区汇总数据")
public interface InfoRegionAggregateVO {

	@ApiModelProperty("行政区域名称")
	String getRegionName();

	@ApiModelProperty("行政区域编码")
	String getCityCode();

	@ApiModelProperty("行政区域父级编码")
	String getParentCode();

	@ApiModelProperty("行政区域全称")
	String getMergerName();
	
	@ApiModelProperty("行政区域级别")
	String getLevel();

	@ApiModelProperty("人口(人)")
	BigDecimal getPopulation();

	@ApiModelProperty("企事业单位数量(个)")
	BigDecimal getEnterNumber();

	@ApiModelProperty("房屋(㎡)")
	BigDecimal getHouseArea();

	@ApiModelProperty("房屋装修(㎡)")
	BigDecimal getHouseDecorationArea();

	@ApiModelProperty("零星果木(棵)")
	BigDecimal getTreeNumber();

	@ApiModelProperty("宅基地(㎡)")
	BigDecimal getHomesteadArea();

}
