package com.lyht.business.info.vo;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "权属人")
public interface InfoOwnerDetailVO{
	
	@ApiModelProperty(value = "唯一ID(新增不填，修改必填)")
	Integer getId();
	@ApiModelProperty(value = "内码")
	String getNm();
	@ApiModelProperty(value = "姓名")
	String getName();
	@ApiModelProperty(value = "身份证")
	String getIdCard();
	@ApiModelProperty(value = "行政区编码")
	String getRegion();
	@ApiModelProperty(value = "征地范围编码")
	String getScope();
	@ApiModelProperty(value = "民族编码")
	String getNational();
	@ApiModelProperty(value = "阶段编码")
	String getStage();
	@ApiModelProperty(value = "经度")
	String getLgtd();
	@ApiModelProperty(value = "纬度")
	String getLttd();
	
	@ApiModelProperty(value = "行政区")
	String getRegionValue();
	@ApiModelProperty(value = "征地范围")
	String getScopeValue();
	@ApiModelProperty(value = "民族编码")
	String getNationalValue();
	@ApiModelProperty(value = "阶段")
	String getStageValue();
	
	@ApiModelProperty(value = "家庭总人口数量")
	BigDecimal getPopulation();
	@ApiModelProperty(value = "房屋总面积")
	BigDecimal getHouseArea();
	@ApiModelProperty(value = "房屋装修总面积")
	BigDecimal getHouseDecorationArea();
	@ApiModelProperty(value = "土地总面积")
	BigDecimal getLandArea();
	
}