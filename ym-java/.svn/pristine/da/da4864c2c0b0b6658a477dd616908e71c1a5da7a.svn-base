package com.lyht.business.cost.vo;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("补偿费用汇总")
public interface CostAggregateVO {

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

	@ApiModelProperty("补偿费用总额")
	BigDecimal getAmount();

	@ApiModelProperty("房屋及房屋装修补偿费用")
	BigDecimal getHouseAmount();

	@ApiModelProperty("附属建筑物及农副业设施补偿费用")
	BigDecimal getBuildingAmount();

	@ApiModelProperty("零星果木补偿费用")
	BigDecimal getTreesAmount();

	@ApiModelProperty("成片青苗及林木补偿费用")
	BigDecimal getYoungCropsAmount();

	@ApiModelProperty("其他补偿补助费用")
	BigDecimal getOtherAmount();

}
