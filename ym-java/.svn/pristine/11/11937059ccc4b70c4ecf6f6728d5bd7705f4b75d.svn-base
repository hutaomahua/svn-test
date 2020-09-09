package com.lyht.business.cost.vo;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("宅基地补偿费")
public interface CostHomesteadVO {

	@ApiModelProperty("权属人编码")
	String getOwnerNm();

	@ApiModelProperty("项目")
	String getProjectName();

	@ApiModelProperty("单位")
	String getUnit();
	
	@ApiModelProperty("类型（dispersed：分散安置，focus：集中安置）")
	String getType();

	@ApiModelProperty("单价")
	BigDecimal getPrice();

	@ApiModelProperty("面积（亩）")
	BigDecimal getArea();
	
	@ApiModelProperty("扣减面积（亩）")
	BigDecimal getDeductionArea();
	
	@ApiModelProperty("剩余面积（亩）")
	BigDecimal getSurplusArea();

	@ApiModelProperty("总额")
	BigDecimal getAmount();

}
