package com.lyht.business.cost.vo;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("个体工商户补偿费")
public interface CostIndividualVO {

	@ApiModelProperty("权属人编码")
	String getOwnerNm();

	@ApiModelProperty("项目")
	String getProjectName();
	
	@ApiModelProperty("搬迁物资数量")
	BigDecimal getMoveMaterialNum();

	@ApiModelProperty("搬迁物资车数")
	BigDecimal getMoveVehicleNumber();

	@ApiModelProperty("搬迁物资金额")
	BigDecimal getMoveMaterialMoney();

	@ApiModelProperty("停业补助面积")
	BigDecimal getClosureAssistanceArea();

	@ApiModelProperty("停业补助金额")
	BigDecimal getClosureAssistanceMoney();

	@ApiModelProperty("总额")
	BigDecimal getAmount();

}
