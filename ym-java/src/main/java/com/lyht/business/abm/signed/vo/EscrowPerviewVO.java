package com.lyht.business.abm.signed.vo;

import io.swagger.annotations.ApiModelProperty;

public interface EscrowPerviewVO {
	
	@ApiModelProperty(value = "协议名称")
	String getName();
	
	@ApiModelProperty(value = "安置点")
	String getPlace();
	
	@ApiModelProperty(value = "权属人名字")
	String getOwnerName();
	
	@ApiModelProperty(value = "宅基地编号")
	String getHomesteadCode();
	
	@ApiModelProperty(value = "户型")
	String getHouseType();
	
	@ApiModelProperty(value = "安置费用")
	Double getPlacementMoney();
	
	@ApiModelProperty(value = "代管资金")
	Double getEscrowMoney();
	
	@ApiModelProperty(value = "代建单位")
	String getCompany();
	
	@ApiModelProperty(value = "可编辑内容")
	String getContent();
}
