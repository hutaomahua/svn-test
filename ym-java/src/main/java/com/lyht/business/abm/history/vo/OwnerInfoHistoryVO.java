package com.lyht.business.abm.history.vo;

import io.swagger.annotations.ApiModelProperty;

public interface OwnerInfoHistoryVO {

	@ApiModelProperty(value= "权属人")
	String getName();

	@ApiModelProperty(value = "身份证号")
	String getIdCard();
	
	@ApiModelProperty(value = "权属人历史信息")
	String getInfoJson();

	@ApiModelProperty(value = "移民人口json")
	String getImmigrantPopulationJson();

	@ApiModelProperty(value = "土地json")
	String getLandJson();

	@ApiModelProperty(value = "房屋json")
	String getHouseJson();

	@ApiModelProperty(value = "树 json")
	String getTreeJson();

	@ApiModelProperty(value = "其他附属json")
	String getOtherJson();

	@ApiModelProperty(value = "房屋装修")
	String getFitmentJson();

	@ApiModelProperty(value = "个体户")
	String getIndividualHouseholdJson();

	@ApiModelProperty(value = "变更操作人")
	String getChangeOperatorName();

	@ApiModelProperty(value = "操作时间")
	String getOperatorTime();

}
