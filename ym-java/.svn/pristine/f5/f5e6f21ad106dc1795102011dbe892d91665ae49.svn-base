package com.lyht.business.abm.household.vo;

import java.math.BigDecimal;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("分户对象")
@Data
public class AbmSplitHouseholdVO {
	
	@ApiModelProperty("户主NM")
	private String ownerNm;

	@ApiModelProperty("户主姓名")
	private String ownerName;

	@ApiModelProperty("户主身份证")
	private String idCard;
	
	@ApiModelProperty("民族")
	private String national;
	
	@ApiModelProperty("征地范围")
	private String scope;
	
	@ApiModelProperty("行政区--全称")
	private String mergerName;
	
	@ApiModelProperty(value = "家庭成员总数")
	private Integer population;

	@ApiModelProperty(value = "房屋总面积")
	private BigDecimal houseArea;

	@ApiModelProperty(value = "房屋装修总面积")
	private BigDecimal houseDecorationArea;

	@ApiModelProperty(value = "土地总面积")
	private BigDecimal landArea;
	
	@ApiModelProperty("分户对象--节点")
	private List<AbmSplitHouseholdNodeVO> nodes;
	
}