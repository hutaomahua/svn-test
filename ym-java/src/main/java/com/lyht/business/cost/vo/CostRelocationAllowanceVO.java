package com.lyht.business.cost.vo;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("搬迁补助")
public interface CostRelocationAllowanceVO {

	@ApiModelProperty("权属人编码")
	String getOwnerNm();

	@ApiModelProperty("项目")
	String getProjectName();

	@ApiModelProperty("单位")
	String getUnit();

	@ApiModelProperty("单价")
	BigDecimal getPrice();

	@ApiModelProperty("数量")
	BigDecimal getNum();

	@ApiModelProperty("总额")
	BigDecimal getAmount();
	
	@ApiModelProperty("类型(近迁：recent，远迁：remote)")
	String getType();

}
