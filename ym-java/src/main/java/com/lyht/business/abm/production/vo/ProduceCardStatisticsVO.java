package com.lyht.business.abm.production.vo;

import io.swagger.annotations.ApiModelProperty;

public interface ProduceCardStatisticsVO {
	
	@ApiModelProperty(value ="安置方式")
	String getName();
	
	@ApiModelProperty(value ="户数")
	Integer getHouseHolds();
	
	@ApiModelProperty(value = "人数")
	Integer getPeople();

}
