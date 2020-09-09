package com.lyht.business.info.vo;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("搬迁安置人口汇总")
public interface InfoMoveAggregateVO {

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
	
	@ApiModelProperty("总户数")
	BigDecimal getHouseholds();
	
	@ApiModelProperty("总人口")
	BigDecimal getPopulation();

	@ApiModelProperty("水库淹没影响区--合计(户数)")
	BigDecimal getPivotTotalHouseholds();
	
	@ApiModelProperty("水库淹没影响区--合计(人口)")
	BigDecimal getPivotTotalPopulation();

	@ApiModelProperty("枢纽工程建设区--合计(户数)")
	BigDecimal getReservoirTotalHouseholds();
	
	@ApiModelProperty("枢纽工程建设区--合计(人口)")
	BigDecimal getReservoirTotalPopulation();

	@ApiModelProperty("集镇新址占地区(户数)")
	BigDecimal getNewTownHouseholds();
	
	@ApiModelProperty("集镇新址占地区(人口)")
	BigDecimal getNewTownPopulation();

}
